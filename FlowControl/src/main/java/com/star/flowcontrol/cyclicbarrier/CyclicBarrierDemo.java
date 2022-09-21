package com.star.flowcontrol.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 演示CyclicBarrier
 * <p>
 * 注意！CyclicBarrier是用于线程的，要等到固定数量的线程都到达了栅栏位置才能继续执行，能直接做一个统一的工作（比如下例的人满出发）
 * CountDownLatch用于事件，只需等待数字到0，此外，CountDownLatch不可重用，除非新建新的实例
 *
 * @Author: zzStar
 * @Date: 10-12-2020 17:20
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("人满出发");
        });

        //可重用，5个一组，人满就出发
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(i, cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable {
        private int id;
        private CyclicBarrier cyclicBarrier;

        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("thread" + id + "现在前往集合地点");
            try {
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("thread" + id + "到了集合地点，开始等待其他人到达");
                cyclicBarrier.await();
                System.out.println("thread" + id + "出发了");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }
}
