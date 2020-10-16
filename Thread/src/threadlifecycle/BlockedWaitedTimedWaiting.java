package threadlifecycle;

/**
 * 展示Blocked,Waited,TimedWaiting
 * 一般习惯将这三种都称之为阻塞状态
 *
 * @Author: zzStar
 * @Date: 10-16-2020 19:37
 */
public class BlockedWaitedTimedWaiting implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        BlockedWaitedTimedWaiting blockedWaitedTimedWaiting = new BlockedWaitedTimedWaiting();
        //两个线程公用一个实例
        Thread thread1 = new Thread(blockedWaitedTimedWaiting);
        thread1.start();
        Thread thread2 = new Thread(blockedWaitedTimedWaiting);
        thread2.start();
        Thread.sleep(5);
        // TIMED_WAITING，因为正在执行Thread.sleep(1000)
        System.out.println(thread1.getState());
        // BLOCKED，因为thread2想拿得到sync()🔒却拿不到
        System.out.println(thread2.getState());
        Thread.sleep(1500);
        // WAITING
        System.out.println(thread1.getState());
        thread1.interrupt();
        thread2.interrupt();
    }

    @Override
    public void run() {
        syn();
    }

    // run方法里要实现一个线程获取到，一个等待
    private synchronized void syn() {
        try {
            Thread.sleep(1000);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
