package com.star.jmm.outoforder;

import java.util.concurrent.CountDownLatch;

/**
 * volatile禁止重排序
 *
 * @Author: zzStar
 * @Date: 10-19-2020 16:37
 */
public class OutOfOrderExecutionVolatile {

    private volatile static int x = 0, y = 0;
    private volatile static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;
        for (; ; ) {
            i++;
            // 回到开始
            x = 0;
            y = 0;
            a = 0;
            b = 0;


            CountDownLatch countDownLatch = new CountDownLatch(1);

            Thread one = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });

            Thread two = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });
            one.start();
            two.start();
            countDownLatch.countDown();
            one.join();
            two.join();

            String result = "No." + i + "次(" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }
}
