package uncaughtexception;

/**
 * 单线程 -> 抛出，处理，有异常堆栈
 * 多线程 -> 子线程异常无法用传统方法捕获
 *
 * @Author: zzStar
 * @Date: 10-18-2020 16:11
 */
public class ExceptionInChildThread implements Runnable {

    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        // 主线程并未受到任何影响
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
