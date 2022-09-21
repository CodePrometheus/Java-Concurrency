/**
 * @Author: Starry
 * @Date: 08-23-2022 21:21
 */
public class print_num_char_2 {

    // 锁对象
    static Object lock = new Object();
    // true:打印字母， false:打印数字
    static boolean sw = true;

    static char[] c1 = "12345".toCharArray();
    static char[] c2 = "abcde".toCharArray();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (!sw) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < c1.length; i++) {
                    System.out.print(c1[i]);
                    sw = false;
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }, "pa").start();

        new Thread(() -> {
            synchronized (lock) {
                while (sw) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < c2.length; i++) {
                    System.out.print(c2[i]);
                    sw = true;
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }, "pb").start();
    }
}

