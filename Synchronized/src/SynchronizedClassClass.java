/**
 * .class代码块
 *
 * @Author: zzStar
 * @Date: 10-21-2020 19:14
 */
public class SynchronizedClassClass implements Runnable {
    static SynchronizedClassClass instance1 = new SynchronizedClassClass();
    static SynchronizedClassClass instance2 = new SynchronizedClassClass();


    @Override
    public void run() {
        try {
            method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void method() throws InterruptedException {
//        synchronized (this) {
        synchronized (SynchronizedClassClass.class) {
            System.out.println("SynchronizedClassClass,I am " + Thread.currentThread().getName());
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }

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
