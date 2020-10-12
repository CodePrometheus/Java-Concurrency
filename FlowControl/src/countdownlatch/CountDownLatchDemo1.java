package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch
 * 倒数结束之前，一直处于等待状态，直到倒计时结束了，此线程才继续工作
 *
 * Demo1 -> 一个线程等待多个线程都执行完，再继续自己的工作
 *
 * @Author: zzStar
 * @Date: 10-12-2020 15:26
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                try {
                    Thread.sleep((long) Math.random() * 10000);
                    System.out.println("No." + no + "完成了检查");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //减1，直到为0，等待的线程才会被唤起
                    latch.countDown();
                }
            };
            service.submit(runnable);
        }
        System.out.println("等待5个人检查完");
        //线程被挂起，等待直到count值为0才继续执行
        latch.await();
        System.out.println("所有完成工作");
        service.shutdown();
    }
}
