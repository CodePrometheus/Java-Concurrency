package threadsecurity;

/**
 * 演示死锁 发布逸出
 *
 * @Author: zzStar
 * @Date: 10-19-2020 11:46
 */
public class DeadLock implements Runnable {

    int flag = 1;
    static Object object1 = new Object();
    static Object object2 = new Object();

    public static void main(String[] args) {
        DeadLock deadLock1 = new DeadLock();
        DeadLock deadLock2 = new DeadLock();
        deadLock1.flag = 1;
        deadLock2.flag = 0;
        new Thread(deadLock1).start();
        new Thread(deadLock2).start();
    }

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (object1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (object2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println("1");
                }
            }
        }

    }
}
