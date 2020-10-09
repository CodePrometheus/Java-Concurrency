import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示多线程预定电影院座位
 *总结
 * @Author: zzStar
 * @Date: 10-09-2020 14:52
 */
public class CinemaBookSeatDemo {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static void bookSeat() {
        //保证无法线程同时预定座位
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预订座位");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "完成预订座位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
    }
}
