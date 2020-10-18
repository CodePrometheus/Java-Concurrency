package importantmethod.waitnotify;

/**
 * 展示wait和notify的基本用法
 * -> 代码执行顺序
 * -> 证明wait释放锁
 *
 * @Author: zzStar
 * @Date: 10-17-2020 14:12
 */
public class WaitUsage {

    public static Object object = new Object();

    static class Thread1 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " starting");
                //等待期间中断则抛出异常
                try {
                    //释放锁
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
            }
        }
    }


    //线程2用来在线程1陷入阻塞的过程中唤醒1
    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "调用了notify()");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        Thread.sleep(200);
        thread2.start();

//        Thread-0 starting
//        Thread-1调用了notify()
//        Thread-0获取到了锁
    }
}
