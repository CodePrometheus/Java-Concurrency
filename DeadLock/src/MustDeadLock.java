/**
 * 演示死锁的发生
 *
 * @Author: zzStar
 * @Date: 10-20-2020 15:01
 */
public class MustDeadLock implements Runnable {

    int flag = 1;

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        MustDeadLock mustDeadLock1 = new MustDeadLock();
        MustDeadLock mustDeadLock2 = new MustDeadLock();
        mustDeadLock1.flag = 1;
        mustDeadLock2.flag = 0;
        new Thread(mustDeadLock1).start();
        new Thread(mustDeadLock2).start();
    }

    @Override
    public void run() {
        System.out.println("flag = " + flag);

        // 两个线程进入，分别以不同的顺序拿着两把锁
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("Thread1 get all locks");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("Thread2 get all locks");
                }
            }
        }
    }
}
