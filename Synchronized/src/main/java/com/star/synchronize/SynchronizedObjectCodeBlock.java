package com.star.synchronize;

/**
 * 对象锁之代码块形式
 *
 * @Author: zzStar
 * @Date: 10-21-2020 18:43
 */
public class SynchronizedObjectCodeBlock implements Runnable {

    static SynchronizedObjectCodeBlock instance = new SynchronizedObjectCodeBlock();
    // 自定义锁
    Object lock1 = new Object();
    Object lock2 = new Object();

    @Override
    public void run() {
//        synchronized (this) {
        synchronized (lock1) {
            System.out.println("SynchronizedObjectCodeBlock,I am " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }

        // 可以并行执行
        synchronized (lock2) {
            System.out.println("SynchronizedObjectCodeBlock,I am " + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行结束");
        }
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
