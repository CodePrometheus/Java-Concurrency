import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 加锁和释放锁的原理
 *
 * @Author: zzStar
 * @Date: 10-21-2020 19:45
 */
public class SynchronizedToLock {

    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        SynchronizedToLock synchronizedToLock = new SynchronizedToLock();
        synchronizedToLock.method1();
        synchronizedToLock.method2();
    }

    public synchronized void method1() {
        System.out.println("synchronized形式的锁");
    }

    // 等价方法
    public void method2() {
        lock.lock();
        try {
            System.out.println("我是lock形式的锁");
        } finally {
            lock.unlock();
        }
    }

}
