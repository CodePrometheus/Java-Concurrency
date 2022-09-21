package com.star.synchronize;

/**
 * 方法锁
 *
 * @Author: zzStar
 * @Date: 10-21-2020 19:00
 */
public class SynchronizedObjectMethod implements Runnable {
    static SynchronizedObjectMethod instance = new SynchronizedObjectMethod();

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        System.out.println("SynchronizedObjectMethod，I am " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();

        // 死循环
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("finished");
    }

}
