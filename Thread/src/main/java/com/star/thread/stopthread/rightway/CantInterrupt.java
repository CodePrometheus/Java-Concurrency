package com.star.thread.stopthread.rightway;

/**
 * 注意 ->
 * 如果while里面放try/catch，会导致中断失效
 *
 * 因为sleep设计时一旦响应中断，就会把线程interrupt标记位为清除
 *
 * @Author: zzStar
 * @Date: 10-14-2020 17:10
 */
public class CantInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            //补充中断也效果
            while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                //程序将会继续运行
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
