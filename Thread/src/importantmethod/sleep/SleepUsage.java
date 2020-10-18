package importantmethod.sleep;

/**
 * 1.让线程在预期的时间执行，其他时候不要占用cpu资源
 * 2.不释放锁
 *
 * @Author: zzStar
 * @Date: 10-17-2020 16:31
 */
public class SleepUsage implements Runnable {

    public static void main(String[] args) {
        SleepUsage sleepUsage = new SleepUsage();
        Thread thread1 = new Thread(sleepUsage);
        Thread thread2 = new Thread(sleepUsage);
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        System.out.println(Thread.currentThread().getName() + "获取到了monitor");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "退出了同步代码块");
    }
}
