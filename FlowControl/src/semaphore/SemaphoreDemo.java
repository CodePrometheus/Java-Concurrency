package semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore用法
 *
 * @Author: zzStar
 * @Date: 10-12-2020 16:17
 */
public class SemaphoreDemo {

    //3个为一组，一般设置true更合理
    static Semaphore semaphore = new Semaphore(3, true);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            service.submit(new Task());
        }
        service.shutdown();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                //可设置，例如一次性拿3个许可证
                semaphore.acquire(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "拿到了许可证");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "释放了许可证");
            //注意，获取和释放的许可证数量必须一致
            semaphore.release(3);
        }
    }
}
