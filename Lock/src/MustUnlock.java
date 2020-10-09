import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock不会像synchronized一样，异常的时候自动释放锁
 * 所以，应在finally手动释放锁！以便保证发生异常的时候锁一定被释放
 * <p>
 * lock()方法不能被中断 -> 故一旦陷入死锁，lock()就会陷入永久等待
 *
 * @Author: zzStar
 * @Date: 10-08-2020 20:01
 */
public class MustUnlock {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName() + "开始执行任务");
        } finally {
            lock.unlock();
        }
    }
}
