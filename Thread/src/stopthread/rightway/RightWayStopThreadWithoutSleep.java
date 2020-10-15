package stopthread.rightway;

import static java.lang.Integer.MAX_VALUE;

/**
 * run方法内没有sleep或wait方法时，停止线程
 *
 * @Author: zzStar
 * @Date: 10-14-2020 16:37
 */
public class RightWayStopThreadWithoutSleep implements Runnable {

    @Override
    public void run() {
        int num = 0;
        while (!Thread.currentThread().isInterrupted() && num <= MAX_VALUE / 2) {
            if (num % 10000 == 0) {
                System.out.println(num + "是10000的倍数");
            }
            num++;
        }
        System.out.println("任务结束了");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleep());
        thread.start();
        Thread.sleep(1000);
        Thread.interrupted();
    }
}
