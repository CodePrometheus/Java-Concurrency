package com.star.deadlock;

/**
 * @Author: Starry
 * @Date: 05-08-2022 15:16
 */
public class DeadLock_2 {

    private static Object resourceA = new Object(), resourceB = new Object();

    public static void main(String[] args) {
        Thread A = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " Get A --> ");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " Waiting Get B --> ");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + " Get B --> ");
                }
            }
        });

        Thread B = new Thread(() -> {
            // A B 顺序改变即可解除死锁
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " Get B --> ");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " Waiting Get A --> ");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + " Get A --> ");
                }
            }
        });
        A.start();
        B.start();
    }

}
