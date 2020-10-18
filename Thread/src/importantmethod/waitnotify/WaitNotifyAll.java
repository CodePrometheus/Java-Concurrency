package importantmethod.waitnotify;

/**
 * 三个线程，线程1和线程2首先被阻塞，线程3唤醒
 * 注意❗ start先执行并不代表线程先启动
 *
 * @Author: zzStar
 * @Date: 10-17-2020 14:29
 */
public class WaitNotifyAll implements Runnable {

    private static final Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {
        WaitNotifyAll waitNotifyAll = new WaitNotifyAll();
        Thread threadA = new Thread(waitNotifyAll);
        Thread threadB = new Thread(waitNotifyAll);
        Thread threadC = new Thread(() -> {
            synchronized (resourceA) {
                //唤醒2个线程,若是notify其中1个将一直等待
                resourceA.notifyAll();
                System.out.println("ThreadC notified");
            }
        });
        threadA.start();
        threadB.start();
        //这里不休眠无法保证顺序
        Thread.sleep(200);
        threadC.start();
    }

    @Override
    public void run() {
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " get resourceA lock");
            try {
                System.out.println(Thread.currentThread().getName() + " waits to start");
                resourceA.wait();
                System.out.println(Thread.currentThread().getName() + " is waiting to end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
