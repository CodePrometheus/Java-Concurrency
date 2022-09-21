package com.star.cas;

/**
 * 模拟CAS操作，等价代码
 *
 * @Author: zzStar
 * @Date: 10-10-2020 14:19
 */
public class SimulatedCAS implements Runnable {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) throws InterruptedException {
        SimulatedCAS cas = new SimulatedCAS();
        cas.value = 0;
        Thread thread1 = new Thread(cas, "Thread1");
        Thread thread2 = new Thread(cas, "Thread2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(cas.value);
    }

    @Override
    public void run() {
        //将之改为1
        compareAndSwap(0, 1);
    }
}
