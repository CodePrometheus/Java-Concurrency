package com.star.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 为什么不支持锁的升级，也即在持有读锁想拿到写锁
 * 因为这样会导致死锁
 *
 * 如果有两个读锁同时升级为写锁。那么只有一个能升级成功。但是这两个线程同时拥有读锁。其中一个线程还一直在申请写锁。这就会造成死锁。
 * 但是锁降级是可以的。因为写锁只有一个线程占有，降级锁是从写锁降级为读锁，此时，同一时间拿到写锁的只有一个线程，可以直接降级为读锁，不会造成冲突
 *
 * @Description: 支持锁的降级，不支持升级
 * @Author: zzStar
 * @Date: 2020/10/09 18:21
 */
public class Upgrading {

    public static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void readUpgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
            System.out.println("升级会带来阻塞");
            //持有读锁想拿到写锁,无法成功
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，升级成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void writeDowngrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
            //持有写锁的情况下，拿到读锁
            readLock.lock();
            System.out.println("在不释放写锁的情况下，直接获取读锁，成功降级");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("降级是可以的");
        Thread thread1 = new Thread(() -> writeDowngrading(), "Thread1");
        thread1.start();
        thread1.join();


        System.out.println("======================");

        System.out.println("升级是不行的");
        new Thread(() -> readUpgrading(), "Thread2").start();

    }


}
