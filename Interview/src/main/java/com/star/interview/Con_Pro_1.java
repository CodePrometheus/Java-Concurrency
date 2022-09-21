package com.star.interview;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者
 *
 * @Author: Starry
 * @Date: 08-23-2022 12:06
 */
public class Con_Pro_1 {
    private int size = 10;
    private Queue<Integer> queue = new PriorityQueue<>(size);
    private Lock lock = new ReentrantLock();
    private Condition consumer = lock.newCondition();
    private Condition produce = lock.newCondition();

    class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        System.out.println("队列空，等待数据");
                        try {
                            consumer.await(); // 消费者等待
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    queue.poll();
                    produce.signalAll(); // 唤醒生产者
                    System.out.println("队列剩余: " + queue.size() + " 个元素");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class produce extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == size) {
                        System.out.println("队列已满");
                        try {
                            produce.await(); // 生产者等待
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    queue.offer(1);
                    consumer.signalAll(); // 唤醒消费者
                    System.out.println("队列现存: " + queue.size() + " 个元素");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        Con_Pro_1 test = new Con_Pro_1();
        Consumer con = test.new Consumer();
        Con_Pro_1.produce pro = test.new produce();
        pro.start();
        con.start();
    }
}
