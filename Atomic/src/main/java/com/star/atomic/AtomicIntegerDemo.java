package com.star.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无需加锁，也可以保证线程安全
 *
 * @Author: zzStar
 * @Date: 10-10-2020 09:43
 */
public class AtomicIntegerDemo implements Runnable {
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    //加上可见性  用于对比,不安全
    private static volatile int basicCount = 0;

    //这里加synchronized也能实现线程安全
    public void incrementBasic() {
        basicCount++;
    }


    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo atomicIntegerDemo = new AtomicIntegerDemo();
        Thread thread1 = new Thread(atomicIntegerDemo);
        Thread thread2 = new Thread(atomicIntegerDemo);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("原子类的结果: " + atomicInteger.get());
        System.out.println("普通变量的结果: " + basicCount);
    }
}
