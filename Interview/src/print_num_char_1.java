import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Starry
 * @Date: 08-23-2022 21:12
 */
public class print_num_char_1 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition con1 = lock.newCondition();
        Condition con2 = lock.newCondition();
        char[] c1 = "12345".toCharArray();
        char[] c2 = "abcde".toCharArray();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < c1.length; i++) {
                    System.out.println(c1[i]);
                    con2.signal(); // 唤醒2
                    con1.await(); // 阻塞1
                }
                con2.signal(); // 唤醒2
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < c2.length; i++) {
                    System.out.println(c2[i]);
                    con1.signal(); // 唤醒1
                    con2.await(); // 阻塞2
                }
                con1.signal(); // 唤醒1
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
