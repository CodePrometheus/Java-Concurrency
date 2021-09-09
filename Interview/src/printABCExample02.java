import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zzStar
 * @Date: 2021/9/9
 * @Description: 交叉打印ABC，ReentrantLock
 */
public class printABCExample02 {

    // 通过state的值来确定是哪个线程打印
    private static int state = 0;

    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 10; ) {
                LOCK.lock();
                try {
                    while (state % 3 == 0) {
                        System.out.println("A");
                        state++;
                        i++;
                    }
                } finally {
                    LOCK.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; ) {
                LOCK.lock();
                try {
                    while (state % 3 == 1) {
                        System.out.println("B");
                        state++;
                        i++;
                    }
                } finally {
                    LOCK.unlock();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; ) {
                LOCK.lock();
                try {
                    while (state % 3 == 2) {
                        System.out.println("C");
                        state++;
                        i++;
                    }
                } finally {
                    LOCK.unlock();
                }
            }
        }).start();

    }

}
