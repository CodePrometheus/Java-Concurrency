package com.star.jmm.outoforder;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序的现象，测试小概率事件
 * 重排序 -> 代码实际执行的顺序和代码在Java文件中的顺序不一致，也即代码指令并不是严格按照代码语句顺序执行的
 * 1.编译器优化
 * 2.CPU指令重排
 * 3.内存的重排序
 *
 * @Author: zzStar
 * @Date: 10-19-2020 16:37
 */
public class OutOfOrderExecution {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

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
