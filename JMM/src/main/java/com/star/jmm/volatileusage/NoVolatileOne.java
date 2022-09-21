package com.star.jmm.volatileusage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不适用Volatile的场景a++
 *
 * @Author: zzStar
 * @Date: 10-20-2020 07:48
 */
public class NoVolatileOne implements Runnable {

    volatile int a;
    // 用原子整形统计正确的数
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        NoVolatileOne noVolatileOne = new NoVolatileOne();
        Thread thread1 = new Thread(noVolatileOne);
        Thread thread2 = new Thread(noVolatileOne);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(noVolatileOne.a);// <20000
        System.out.println(noVolatileOne.realA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            a++;
            realA.incrementAndGet();
        }
    }
}
