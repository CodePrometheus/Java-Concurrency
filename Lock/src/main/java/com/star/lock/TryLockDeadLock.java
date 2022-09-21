package com.star.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 引入trylock避免死锁
 *  超时就放弃
 *
 * @Author: zzStar
 * @Date: 10-08-2020 20:09
 */
public class TryLockDeadLock implements Runnable {

    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadLock lock1 = new TryLockDeadLock();
        TryLockDeadLock lock2 = new TryLockDeadLock();
        lock1.flag = 1;
        lock2.flag = 0;
        new Thread(lock1).start();
        new Thread(lock2).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("线程1获取到锁1");
                            Thread.sleep(new Random().nextInt(1000));

                            //如果2释放了
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程1获取到锁2");
                                    System.out.println("线程1成功获取了两把锁");
                                    break;
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("线程1获取锁2失败，已重试");
                            }

                        } finally {
                            lock1.unlock();
                            //给出一定时间让2拿锁
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程1获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (flag == 0) {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("线程2获取到锁2");
                            Thread.sleep(new Random().nextInt(1000));

                            if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("线程2获取到锁1");
                                    System.out.println("线程2成功获取了两把锁");
                                    break;
                                } finally {
                                    lock1.unlock();
                                }
                            } else {
                                System.out.println("线程2获取锁1失败，已重试");
                            }

                        } finally {
                            lock2.unlock();
                            //给出一定时间让2拿锁
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程2获取锁2失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 规范：
     *    <p>A typical usage idiom for this method would be:
     *    <pre> {@code
     *    Lock lock = ...;
     *    if (lock.tryLock()) {
     *      try {
     *        // manipulate protected state
     *      } finally {
     *        lock.unlock();
     *      }
     *    } else {
     *      // perform alternative actions
     *    }}</pre>
     */

    /**
     *     boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
     */
}
