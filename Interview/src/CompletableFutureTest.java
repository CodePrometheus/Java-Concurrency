import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class CompletableFutureTest {

    // 模拟的请求数量
    private static final int THREAD_NUM = 1000;

    // 倒计数器 juc包中常用工具类
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    // 积攒 请求。（每隔N毫秒批量处理一次）
    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws IOException {
        // 创建 并不是马上发起请求
        for (int i = 0; i < THREAD_NUM; i++) {
            final String code = "code-" + (i + 1); // 番号
            // 多线程模拟用户查询请求
            Thread thread = new Thread(() -> {
                try {
                    // 代码在这里等待，等待countDownLatch为0，代表所有线程都start，再运行后续的代码
                    countDownLatch.await();
                    // http请求，实际上就是多线程调用这个方法
                    CompletableFutureTest queryRpc = new CompletableFutureTest();
                    Map<String, Object> result = queryRpc.queryCommodity(code);
                    System.out.println(Thread.currentThread().getName() + " 查询结束，结果是：" + result);
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " 线程执行出现异常:" + e.getMessage());
                }
            });
            thread.setName("price-thread-" + code);
            thread.start();
            countDownLatch.countDown();
        }

        // 输入任意内容退出
        System.in.read();
    }

    // 1000 用户请求，1000个线程
    public Map<String, Object> queryCommodity(String movieCode) throws ExecutionException, InterruptedException {
        // 1000次 怎么样才能变成  更少的接口
        // 思路： 将不同用户的同类请求合并起来
        // 并非立刻发起接口调用，请求 收集起来，再进行
        Request request = new Request();
        request.commodityCode = movieCode;
        // 异步编程： 获取异步处理的结果
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        request.future = future;
        queue.add(request);
        return future.get(); // 此处get方法，会阻塞线程运行，直到future有返回
    }

    // 定时任务的实现,N秒钟处理一次数据
    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 1、 取出queue的请求，生成一次批量查询
            int size = queue.size();
            if (size == 0) {
                return;
            }
            ArrayList<Request> requests = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Request request = queue.poll();
                requests.add(request);
            }
            System.out.println("批量处理数据量:" + size);
            // 2、 组装一个批量查询（一定需要 目的资源能够支持批量查询。 http）
            ArrayList<String> commodityCodes = new ArrayList<>();
            for (Request request : requests) {
                commodityCodes.add(request.commodityCode);
            }
            QueryRpc queryRpc = new QueryRpc();
            // 组装一个批量查询
            List<Map<String, Object>> responses = queryRpc.queryCommodityByCodeBatch(commodityCodes);

            // 3、将结果响应 分发给每一个单独的用户请求。  由定时任务处理线程 --> 1000个用户的请求线程
            HashMap<String, Map<String, Object>> responseMap = new HashMap<>();
            for (Map<String, Object> response : responses) {
                String code = response.get("code").toString();
                responseMap.put(code, response);
            }
            for (Request request : requests) {
                // 根据请求中携带的能表示唯一参数，去批量查询的结果中找响应
                Map<String, Object> result = responseMap.get(request.commodityCode);
                // 将结果返回到对应的请求线程
                request.future.complete(result);
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }
}

class QueryRpc {
    /**
     * 调用远程的商品信息查询接口
     *
     * @param code 商品编码
     * @return 返回商品信息，map格式
     */
    public HashMap<String, Object> queryCommodityByCode(String code) {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commodityId", new Random().nextInt(999999999));
        hashMap.put("code", code);
        hashMap.put("phone", "huawei");
        hashMap.put("isOk", "true");
        hashMap.put("price", "4000");
        return hashMap;
    }

    /**
     * 批量查询 - 调用远程的商品信息查询接口
     *
     * @param codes 多个商品编码
     * @return 返回多个商品信息
     */
    public List<Map<String, Object>> queryCommodityByCodeBatch(List<String> codes) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String code : codes) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("commodityId", new Random().nextInt(999999999));
            hashMap.put("code", code);
            hashMap.put("phone", "huawei");
            hashMap.put("isOk", "true");
            hashMap.put("price", "4000");
            result.add(hashMap);
        }
        return result;
    }
}

// 请求包装类
class Request {
    String commodityCode;
    CompletableFuture<Map<String, Object>> future; // 接受结果

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public CompletableFuture<Map<String, Object>> getFuture() {
        return future;
    }

    public void setFuture(CompletableFuture<Map<String, Object>> future) {
        this.future = future;
    }
}
