package com.star.interview;

import java.util.concurrent.Semaphore;

/**
 * @Author: zzStar
 * @Date: 2021/9/9
 * @Description: N个线程顺序打印0-100，Semaphore
 */
public class print100Example01 {

    // 定义N的值
    private final static int n = 100;
    static int result = 0;
    static int maxNum = 100;

    public static void main(String[] args) throws InterruptedException {
        final Semaphore[] semaphores = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            // 非公平信号量，每个信号量初始计数都为1
            semaphores[i] = new Semaphore(1);
            if (i != n - 1) {
                // System.out.println(i + "===" + semaphores[i].getQueueLength());
                // 获取一个许可前线程将一直阻塞, for 循环之后只有 semaphores[2] 没有被阻塞
                semaphores[i].acquire();
            }
        }
        for (int i = 0; i < n; i++) {
            // 初次执行，上一个信号量是 semaphores[2]
            final Semaphore lastSemaphore = i == 0 ? semaphores[n - 1] : semaphores[i - 1];
            final Semaphore currentSemaphore = semaphores[i];
            final int index = i;
            new Thread(() -> {
                try {
                    while (true) {
                        // 初次执行，让第一个 for 循环没有阻塞的 semaphores[2] 先获得令牌阻塞了
                        lastSemaphore.acquire();
                        System.out.println("thread" + index + ": " + result++);
                        if (result > maxNum) {
                            System.exit(0);
                        }
                        // 释放当前的信号量，semaphores[0] 信号量此时为 1，下次 for semaphores[0]
                        currentSemaphore.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
