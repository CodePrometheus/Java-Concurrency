package com.star.interview;

/**
 * 打印奇偶数 1，2
 * Synchronized + wait/notify
 *
 * @Author: zzStar
 * @Date: 09-08-2021 22:42
 */
public class printParityExample01 {

    public static void main(String[] args) {
        print print = new print();
        Thread a = new Thread(print, "A");
        Thread b = new Thread(print, "B");
        a.start();
        b.start();
    }

}

class print implements Runnable {

    int i = 1;

    /**
     * 一个线程打印后唤醒另一个，再把自己挂起
     */
    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                // notify和wait只能用于synchronized内部
                this.notify();
                if (i < 100) {
                    System.out.println(Thread.currentThread().getName() + " == " + i++);
                } else {
                    return;
                }
                try {
                    // wait的前提是当前线程持有锁
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
