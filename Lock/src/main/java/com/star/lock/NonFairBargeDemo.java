package com.star.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示非公平和公平的ReentrantReadWriteLock策略
 * <p>
 * writers can always barge
 *
 * @Author: zzStar
 * @Date: 10-09-2020 17:32
 */
public class NonFairBargeDemo {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + "得到读锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            Thread.sleep(40);
            System.out.println(Thread.currentThread().getName() + "得到写锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(), "Thread1").start();
        new Thread(() -> read(), "Thread2").start();
        new Thread(() -> read(), "Thread3").start();
        new Thread(() -> write(), "Thread4").start();
        new Thread(() -> read(), "Thread5").start();

        //模拟插队
        new Thread(() -> {
            Thread[] threads = new Thread[20];
            for (int i = 0; i < 20; i++) {
                threads[i] = new Thread(() -> read(), "子线程创建的Thread" + i);
            }
            //启动100个线程
            for (int i = 0; i < 20; i++) {
                threads[i].start();
            }
        }).start();
    }
}
