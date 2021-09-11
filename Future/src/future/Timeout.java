package future;

import java.util.concurrent.*;

/**
 * 演示get的超时方法
 * cancel方法 —> 取消任务的执行
 * 如果任务已经开始执行了，那么这个取消方法不会直接取消该任务，而是根据传入的参数做判断
 * <p>
 * true -> 任务能处理interrupt
 * false -> 仅用于避免启动尚未启动的任务（适用于未能处理interrupt、不清楚任务是否支持取消、需要等待已经开始的任务执行完成）
 *
 * @Author: zzStar
 * @Date: 10-13-2020 10:21
 */
public class Timeout {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    static class Ad {
        String name;

        public Ad(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    //任务 -> 3秒中的时间返回广告
    static class FetchTask implements Callable<Ad> {
        @Override
        public Ad call() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("sleep期间被中断了");
                return new Ad("被中断时候的默认广告");
            }
            return new Ad("Alibaba");
        }
    }

    public void printAd() {
        Future<Ad> adFuture = executorService.submit(new FetchTask());
        Ad ad;
        try {
            //给出4秒的超时时间
            ad = adFuture.get(4000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            ad = new Ad("被中断时候的默认广告");
        } catch (ExecutionException e) {
            ad = new Ad("异常时候的默认广告");
        } catch (TimeoutException e) {
            ad = new Ad("超时时候的默认广告");
            System.out.println("超时，未获取到广告");

            //是否中断正在执行的任务
            //传入false，还未被执行的任务会被标记，任务会被正常取消，未来也不会被执行
            boolean cancel = adFuture.cancel(false);
            System.out.println("cancel = " + cancel);
        }
        executorService.shutdown();
        System.out.println(ad);
    }

    public static void main(String[] args) {
        Timeout timeout = new Timeout();
        timeout.printAd();
    }

}
