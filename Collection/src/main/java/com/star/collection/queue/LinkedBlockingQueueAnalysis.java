package com.star.collection.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LinkedBlockingQueue无界，容量2147483647，内部结构Node，两把锁
 *
 * @Author: zzStar
 * @Date: 10-12-2020 12:54
 */
public class LinkedBlockingQueueAnalysis {
/*
    static class Node<E> {
        E item;

        /**
         * One of:
         * - the real successor Node
         * - this Node, meaning the successor is head.next
         * - null, meaning there is no successor (this is the last node)
         *
    LinkedBlockingQueue.Node<E> next;

        Node(E x) { item = x; }
    }


    private final ReentrantLock takeLock = new ReentrantLock();

    private final ReentrantLock putLock = new ReentrantLock();

    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        final int c;
        final LinkedBlockingQueue.Node<E> node = new LinkedBlockingQueue.Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            */
    /*
     * Note that count is used in wait guard even though it is
     * not protected by lock. This works because count can
     * only decrease at this point (all other puts are shut
     * out by lock), and we (or some other waiting put) are
     * signalled if it ever changes from capacity. Similarly
     * for all other uses of count in other wait guards.
     *//*
            //满了则阻塞
            while (count.get() == capacity) {
                notFull.await();
            }
            //e被包装成node
            enqueue(node);
            //返回的c是旧值
            c = count.getAndIncrement();
            //当前未满，唤醒
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
    }
*/

}
