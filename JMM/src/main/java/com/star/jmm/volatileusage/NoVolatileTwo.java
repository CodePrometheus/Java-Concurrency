package com.star.jmm.volatileusage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile 不适用的场景2
 *
 * @Author: zzStar
 * @Date: 10-20-2020 08:36
 */
public class NoVolatileTwo implements Runnable {

    volatile boolean done = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        VolatileApplicableOne volatileApplicableOne = new VolatileApplicableOne();
        Thread thread1 = new Thread(volatileApplicableOne);
        Thread thread2 = new Thread(volatileApplicableOne);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(volatileApplicableOne.done);// <20000
        System.out.println(volatileApplicableOne.realA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            flipDone();
            realA.incrementAndGet();
        }
    }

    private void flipDone() {
        // 取决于之前的状态，结果不可控
        done = !done;
    }

}
