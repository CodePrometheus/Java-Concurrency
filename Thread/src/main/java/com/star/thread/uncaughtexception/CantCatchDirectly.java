package com.star.thread.uncaughtexception;

/**
 * 说明线程的异常不能用传统方法捕获
 *
 * @Author: zzStar
 * @Date: 10-18-2020 16:15
 */
public class CantCatchDirectly implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        // 失效的原因 -> try语句里只能捕获对应线程内的异常，是针对主线程的，没有办法捕获到子线程中的异常
        try {
            new Thread(new CantCatchDirectly(), "th1").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "th2").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "th3").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(), "th4").start();
            Thread.sleep(300);
        } catch (RuntimeException e) {
            // 并未执行到
            System.out.println("Caught Exception");
        }

    }

/*
    @Override
    public void run() {
        throw new RuntimeException();
    }
*/

    // 方案一，手动在每个run方法里进行try catch
    @Override
    public void run() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }
    }
}
