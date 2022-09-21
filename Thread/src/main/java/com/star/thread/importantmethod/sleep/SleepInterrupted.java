package com.star.thread.importantmethod.sleep;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 实现每一秒钟输出当前时间，被中断，观察
 *
 * @Author: zzStar
 * @Date: 10-18-2020 11:18
 */
public class SleepInterrupted implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        SleepInterrupted sleepInterrupted = new SleepInterrupted();
        Thread thread = new Thread(sleepInterrupted);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("被中断");
                e.printStackTrace();
            }
        }
    }
}
