package com.star.thread.importantmethod.waitnotify;

/**
 * 交替打印0-100的奇偶数，采用wait/notify，少去废操作
 * ->拿到锁，就打印，不用判断奇偶数；打印完，唤醒其他线程，自己就休眠
 *
 * @Author: zzStar
 * @Date: 10-17-2020 16:02
 */
public class WaitNotifyPrintOddEvenWait {

    private static int count = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new TurningRunner(), "even").start();
        //防止奇偶错乱
        Thread.sleep(100);
        new Thread(new TurningRunner(), "odd").start();
    }

    static class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    //拿锁即打印
                    System.out.println(Thread.currentThread().getName() + ":" + count++);
                    //唤醒
                    lock.notify();
                    if (count <= 100) {
                        try {
                            //如果任务还没结束，就让出当前的锁，并进入休眠
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
