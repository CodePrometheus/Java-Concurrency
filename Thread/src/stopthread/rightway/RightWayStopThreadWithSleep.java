package stopthread.rightway;

/**
 * 带有sleep中断线程的写法
 *
 * @Author: zzStar
 * @Date: 10-14-2020 16:51
 */
public class RightWayStopThreadWithSleep {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                while (num <= 300 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();//java.lang.InterruptedException: sleep interrupted
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
