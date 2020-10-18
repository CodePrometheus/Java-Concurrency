package importantmethod.waitnotify;

/**
 * 实现两个线程交替打印0-100的奇偶数，采用synchronized
 * 但效率不高
 *
 * @Author: zzStar
 * @Date: 10-17-2020 15:48
 */
public class WaitNotifyPrintOddEvenSyn {

    private static int count;
    private static final Object lock = new Object();

    public static void main(String[] args) {

        //能不能拿到锁取决于线程间的竞争关系

        new Thread(() -> {
            while (count < 100) {
                synchronized (lock) {
                    //若该线程始终抢到，又不满足条件，则效率大大降低
                    if ((count & 1) == 0) {
                        System.out.println(Thread.currentThread().getName() + ":" + count++);
                    }
                }
            }
        }, "even").start();

        new Thread(() -> {
            while (count < 100) {
                synchronized (lock) {
                    if ((count & 1) == 1) {
                        System.out.println(Thread.currentThread().getName() + ":" + count++);
                    }
                }
            }
        }, "odd").start();
    }

}
