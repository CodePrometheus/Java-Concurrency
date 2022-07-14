import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Request Collapsing
 *
 * @Author: zzStar
 * @Date: 06-29-2022
 */
public class RequestCollapsing {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        RequestCollapsing collapsing = new RequestCollapsing();
        collapsing.mergeJob();
        Thread.sleep(2000);

        List<Future<Result>> futureList = new ArrayList<>();
        CountDownLatch downLatch = new CountDownLatch(10);
        for (int i = 0; i < 12; i++) {
            final Long orderId = i + 100L;
            final Long userId = (long) i;
            Future<Result> future = executorService.submit(() -> {
                downLatch.countDown();
                downLatch.await(1000, TimeUnit.SECONDS);
                return collapsing.operate(new UserRequest(orderId, userId, 1));
            });
            futureList.add(future);
        }

        futureList.forEach(v -> {
            try {
                Result result = v.get(300, TimeUnit.MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + " : 客户端请求响应: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 库存
     */
    private Integer stock = 10;

    private BlockingQueue<RequestPromise> queue = new LinkedBlockingDeque<>(10);

    public void mergeJob() {
        new Thread(() -> {
            List<RequestPromise> promiseList = new ArrayList<>();
            while (true) {
                if (queue.isEmpty()) {
                    try {
                        Thread.sleep(10);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int batchSize = queue.size();
                for (int i = 0; i < batchSize; i++) {
                    promiseList.add(queue.poll());
                }

                System.out.println(Thread.currentThread().getName() + " : 合并请求扣减库存 : " + promiseList);

                int sum = promiseList.stream().mapToInt(v -> v.getRequest().getCount()).sum();

                if (sum <= stock) {
                    stock -= sum;
                    // notify user
                    promiseList.forEach(v -> {
                        v.setResult(new Result(true, "ok"));
                        synchronized (v) {
                            v.notify();
                        }
                    });
                    promiseList.clear();
                    continue;
                }

                promiseList.forEach(v -> {
                    int cnt = v.getRequest().getCount();
                    if (cnt <= stock) {
                        stock -= cnt;
                        v.setResult(new Result(true, "ok"));
                    } else {
                        v.setResult(new Result(false, "库存不足"));
                    }
                    synchronized (v) {
                        v.notify();
                    }
                });
                promiseList.clear();
            }
        }, "mergeJob").start();
    }

    /**
     * 用户库存扣减
     *
     * @param request
     * @return
     */
    public Result operate(UserRequest request) throws InterruptedException {
        RequestPromise promise = new RequestPromise(request);
        // 等待 100 毫秒，如果超时，则返回失败
        synchronized (promise) {
            boolean enqueueSuccess = queue.offer(promise, 100, TimeUnit.MILLISECONDS);
            if (!enqueueSuccess) {
                return new Result(false, "请求超时");
            }
            try {
                promise.wait(200);
                if (promise.getResult() == null) {
                    return new Result(false, "请求超时");
                }
            } catch (InterruptedException e) {
                return new Result(false, "被中断");
            }
        }
        return promise.getResult();
    }
}

/**
 * 请求和响应
 */
class RequestPromise {
    private UserRequest request;
    private Result result;

    public RequestPromise(UserRequest request) {
        this.request = request;
    }

    public UserRequest getRequest() {
        return request;
    }

    public void setRequest(UserRequest request) {
        this.request = request;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RequestPromise{" +
                "request=" + request +
                ", result=" + result +
                '}';
    }
}


class Result {
    private Boolean success;
    private String msg;

    public Result(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}

class UserRequest {
    private Long orderId;
    private Long userId;
    private Integer count;

    public UserRequest(Long orderId, Long userId, Integer count) {
        this.orderId = orderId;
        this.userId = userId;
        this.count = count;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", count=" + count +
                '}';
    }
}
