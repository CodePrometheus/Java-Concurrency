package com.star.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 手写实现
 * Condition + ReentrantLock
 *
 * @Author: zzStar
 * @Date: 04-30-2022 23:37
 */
public class BlockingQueue_2 {

    private List<Integer> buffer = new ArrayList<>();
    private volatile int size;
    private volatile int capacity;
    private Lock lock = new ReentrantLock();

    private final Condition isNull = lock.newCondition();
    private final Condition isFull = lock.newCondition();

    BlockingQueue_2(int capacity) {
        this.capacity = capacity;
    }

    private void set(int n) {
        try {
            lock.lock();
            try {
                while (size >= capacity) {
                    System.out.println("阻塞队列满了");
                    isFull.await();
                }
            } catch (InterruptedException e) {
                isFull.signal();
                e.printStackTrace();
            }
            ++size;
            buffer.add(n);
            isNull.signal();
        } finally {
            lock.unlock();
        }
    }

    private int get() {
        try {
            lock.lock();
            try {
                while (size == 0) {
                    System.out.println("阻塞队列空了");
                    isNull.await();
                }
            } catch (InterruptedException e) {
                isNull.signal();
                e.printStackTrace();
            }
            --size;
            int res = buffer.get(0);
            buffer.remove(0);
            isFull.signal();
            return res;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueue_2 qu = new BlockingQueue_2(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                qu.set(i);
                System.out.println("塞入" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("消费" + qu.get());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

}
