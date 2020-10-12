package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo2 -> 多个线程等待某一个线程的信号，同时开始执行
 *
 * @Author: zzStar
 * @Date: 10-12-2020 15:45
 */
public class CountDownLatchDemo2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                System.out.println("No." + no + "准备完毕，等待发令枪");
                try {
                    //全部调用await，等待
                    latch.await();
                    System.out.println("No." + no + "running!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(2000);
        System.out.println("发令枪响，开始！");
        //所有调用await的5个线程统一的开始
        latch.countDown();
        service.shutdown();
    }
}
