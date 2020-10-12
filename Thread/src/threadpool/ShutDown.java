package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 关闭线程池
 *
 * @Author: zzStar
 * @Date: 10-12-2020 13:48
 */
public class ShutDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            executorService.execute(new ShutDownTask());
        }
        Thread.sleep(1000);
//        System.out.println("executorService.awaitTermination(3L, TimeUnit.SECONDS) = " + executorService.awaitTermination(3L, TimeUnit.SECONDS));
//        System.out.println("executorService.isShutdown() = " + executorService.isShutdown());
//        executorService.shutdown();
//        System.out.println("executorService.isShutdown() = " + executorService.isShutdown());
//        System.out.println("executorService.isTerminated() = " + executorService.isTerminated());
//        System.out.println("==========");
//        Thread.sleep(2000);
//        System.out.println("executorService.isTerminated() = " + executorService.isTerminated());
//        executorService.execute(new ShutDownTask());RejectedExecutionException
        executorService.shutdownNow();//可得到未执行的线程数组
    }
}

class ShutDownTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}