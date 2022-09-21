package com.star.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 把普通变量升级为原子变量
 * <p>
 * 注意：其背后利用了反射
 * 不支持static
 *
 * @Author: zzStar
 * @Date: 10-10-2020 10:23
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {

    static Candidate tom;
    static Candidate peter;

    public static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater
            .newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            peter.score++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    public static class Candidate {
        //这里加static报错
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();

        AtomicIntegerFieldUpdaterDemo atomicIntegerFieldUpdaterDemo = new AtomicIntegerFieldUpdaterDemo();
        Thread thread1 = new Thread(atomicIntegerFieldUpdaterDemo);
        Thread thread2 = new Thread(atomicIntegerFieldUpdaterDemo);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("peter = " + peter.score);
        System.out.println("tom = " + tom.score);
    }
}
