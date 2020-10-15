package stopthread.rightway;

/**
 * 线程在每次迭代后都阻塞,也即每次循环都会调用sleep或wait方法，则不需要每次迭代都检查是否已中断
 *
 * @Author: zzStar
 * @Date: 10-14-2020 17:02
 */
public class RightWayStopThreadWithSleepEveryLoop {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                //这里无需在要一个判断
                while (num <= 3000) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                    Thread.sleep(100);
                }
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
