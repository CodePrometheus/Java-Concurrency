package stopthread.wrongway;

/**
 * 演示Volatile的局限 -> part1 看似可行
 *
 * @Author: zzStar
 * @Date: 10-15-2020 16:52
 */
public class WrongWayVolatile implements Runnable {
    //可见性
    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 10000 && !canceled) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WrongWayVolatile wrongWayVolatile = new WrongWayVolatile();
        Thread thread = new Thread(wrongWayVolatile);
        thread.start();
        try {
            //使当前线程休眠，进入阻塞状态（暂停执行）
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wrongWayVolatile.canceled = true;
    }
}
