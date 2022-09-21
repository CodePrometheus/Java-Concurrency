package com.star.thread.importantmethod.sleep;

/**
 * 1.让线程在预期的时间执行，其他时候不要占用cpu资源
 * 2.不释放锁，直到规定时间后再执行，休眠期间如果被中断，会抛出异常并清除中断状态
 * 3.Sleep方法可以让线程进入waiting状态，并且不占用CPU资源
 *
 * @Author: zzStar
 * @Date: 10-17-2020 16:31
 */
public class SleepUsage implements Runnable {

    public static void main(String[] args) {
        SleepUsage sleepUsage = new SleepUsage();
        Thread thread1 = new Thread(sleepUsage);
        Thread thread2 = new Thread(sleepUsage);
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        System.out.println(Thread.currentThread().getName() + "获取到了monitor");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "退出了同步代码块");
    }
}
