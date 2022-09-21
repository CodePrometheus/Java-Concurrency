package com.star.lock;

/**
 * 要么多读，要么一写
 * <p>
 * 注意！
 * 读读共享、其他都互斥(写写互斥、读写互斥、写读互斥)
 *
 * @Author: zzStar
 * @Date: 10-09-2020 18:40
 */
public class ReadWriteLock {

    /**
     * 共享锁和排他锁总结
     *
     * 1.多个线程只申请读锁，都可以申请到
     * 2.如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁
     * 3.如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁
     * 4.要么是一个或多个线程同时有读锁，要么是一个线程有写锁，但是两者不会同时出现
     */

    /**
     * 为了防止饥饿，读锁不能插队
     * 只能降级，不能升级，也即只能从写锁降为读锁
     */

    /**
     * ReentrantReadWriteLock适用于 读多写少 的情况
     * ReentrantLock适用于一般场合
     */
}