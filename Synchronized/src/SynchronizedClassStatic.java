/**
 * 类锁的第一种形式 -> static形式
 *
 * @Author: zzStar
 * @Date: 10-21-2020 19:07
 */
public class SynchronizedClassStatic implements Runnable {

    static SynchronizedClassStatic instance1 = new SynchronizedClassStatic();
    static SynchronizedClassStatic instance2 = new SynchronizedClassStatic();

    @Override
    public void run() {
        method();
    }

    // 加上static，全局同步方法
    public static synchronized void method() {
        System.out.println("SynchronizedClassStatic，I am " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);
        thread1.start();
        thread2.start();

        // 死循环
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("finished");

    }
}
