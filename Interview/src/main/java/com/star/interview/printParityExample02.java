package com.star.interview;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 打印奇偶数 1，2
 * ReentrantLock
 *
 * @Author: zzStar
 * @Date: 09-08-2021 22:53
 */
public class printParityExample02 {

    private int start = 1;

    private volatile boolean flag = false;

    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        printParityExample02 example02 = new printParityExample02();
        Thread t1 = new Thread(new odd(example02), "A");
        Thread t2 = new Thread(new even(example02), "B");
        t1.start();
        t2.start();
    }

    public static class odd implements Runnable {

        private printParityExample02 number;

        public odd(printParityExample02 number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start < 100) {
                if (!number.flag) {
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "===" + number.start);
                        number.start++;
                        number.flag = true;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }

    public static class even implements Runnable {

        private printParityExample02 number;

        public even(printParityExample02 number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start < 100) {
                if (number.flag) {
                    try {
                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "===" + number.start);
                        number.start++;
                        number.flag = false;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }

}
