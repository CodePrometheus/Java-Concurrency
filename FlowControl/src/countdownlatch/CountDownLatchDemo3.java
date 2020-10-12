package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 整合Demo1&Demo2
 * @Author: zzStar
 * @Date: 2020/10/12 15:57
 */
public class CountDownLatchDemo3 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                System.out.println("No." + no + "准备完毕，等待发令枪");
                try {
                    //全部调用await，等待
                    latch.await();
                    System.out.println("No." + no + "running!");
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("No." + no + "跑到终点了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(2000);
        System.out.println("发令枪响，开始！");
        //所有调用await的5个线程统一的开始
        latch.countDown();
        //阻塞，等待5个人都到终点线
        end.await();
        System.out.println("所有人都到终点线，over");

        service.shutdown();
    }
}
