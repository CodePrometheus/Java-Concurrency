package com.star.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 利用工具定位死锁
 *
 * @Author: zzStar
 * @Date: 10-21-2020 08:09
 */
public class ThreadMXBeanDetection implements Runnable {

    int flag = 1;

    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection threadMXBeanDetection1 = new ThreadMXBeanDetection();
        ThreadMXBeanDetection threadMXBeanDetection2 = new ThreadMXBeanDetection();
        threadMXBeanDetection1.flag = 1;
        threadMXBeanDetection2.flag = 0;
        new Thread(threadMXBeanDetection1).start();
        new Thread(threadMXBeanDetection2).start();
        Thread.sleep(1000);
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                String threadName = threadInfo.getThreadName();
                System.out.println("发现死锁 = " + threadName);
            }
        }
    }

    @Override
    public void run() {
        System.out.println("flag = " + flag);

        // 两个线程进入，分别以不同的顺序拿着两把锁
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("Thread1 get all locks");
                }
            }
        }

        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("Thread2 get all locks");
                }
            }
        }
    }

}
