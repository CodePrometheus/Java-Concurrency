package startthread;

/**
 * 保证t1.t2.t3顺序执行，用join即可
 *
 * @Author: zzStar
 * @Date: 06-19-2021 15:12
 */
public class OrderStartThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> System.out.println("t1"));
        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");
        });
        t1.start();
        t3.start();
        t2.start();
    }
}
