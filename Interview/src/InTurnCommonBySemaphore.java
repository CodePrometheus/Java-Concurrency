import java.util.concurrent.Semaphore;

/**
 * @Author: zzStar
 * @Date: 2021/9/9
 * @Description: 通用解法：交替打印任意
 */
public class InTurnCommonBySemaphore {

    // 规定的次数
    private static final int times = 10;

    // 需要打印任意就任意个信号量，但是第一个必须为1
    static Semaphore a = new Semaphore(1);
    static Semaphore b = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(() -> print("a", a, b)).start();
        new Thread(() -> print("b", b, a)).start();
    }

    private static void print(String name, Semaphore cur, Semaphore next) {
        for (int i = 0; i < times; i++) {
            try {
                cur.acquire();
                System.out.println(name);
                next.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
