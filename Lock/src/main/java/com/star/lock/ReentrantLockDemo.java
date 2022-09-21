package com.star.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * 也就是同一线程可以多次获取同一把锁，再次申请无需提前释放，又叫递归锁
 * 可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁
 * 可避免死锁，提高封装性
 *
 * ReentrantLock上锁的过程核心是AQS
 *
 * @Author: zzStar
 * @Date: 10-09-2020 15:09
 */
public class ReentrantLockDemo {

    private static ReentrantLock reentrantLock = new ReentrantLock(false);

    public static void main(String[] args) {
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.lock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());
        reentrantLock.unlock();
        System.out.println(reentrantLock.getHoldCount());
    }

    /**
     @ReservedStackAccess
     final boolean nonfairTryAcquire(int acquires) {
     final Thread current = Thread.currentThread();
     int c = getState();
     if (c == 0) {
     if (compareAndSetState(0, acquires)) {
     setExclusiveOwnerThread(current);
     return true;
     }
     }
     else if (current == getExclusiveOwnerThread()) {
     int nextc = c + acquires;
     if (nextc < 0) // overflow
     throw new Error("Maximum lock count exceeded");
     setState(nextc);
     return true;
     }
     return false;
     }

     @ReservedStackAccess
     protected final boolean tryRelease(int releases) {
     int c = getState() - releases;
     if (Thread.currentThread() != getExclusiveOwnerThread())
     throw new IllegalMonitorStateException();
     boolean free = false;
     if (c == 0) {
     free = true;
     setExclusiveOwnerThread(null);
     }
     setState(c);
     return free;
     }
     */
}
