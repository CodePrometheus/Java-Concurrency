package com.star.deadlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用tryLock来避免死锁
 *
 * @Author: zzStar
 * @Date: 10-21-2020 09:25
 */
public class TryLockDeadLock implements Runnable {

    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    private int flag = 1;

    public static void main(String[] args) {
        TryLockDeadLock tryLockDeadLock1 = new TryLockDeadLock();
        TryLockDeadLock tryLockDeadLock2 = new TryLockDeadLock();
        tryLockDeadLock1.flag = 1;
        tryLockDeadLock2.flag = 0;
        new Thread(tryLockDeadLock1).start();
        new Thread(tryLockDeadLock2).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {

            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                        Thread.sleep(new Random().nextInt(1000));
                        // 继续获取第二把锁
                        if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                            System.out.println(Thread.currentThread().getName() + "成功获取了两把锁");
                            lock2.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁2");
                            lock1.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁1");
                            break;
                        } else {
                            System.out.println(Thread.currentThread().getName() + "已得到锁1,但获取锁2失败");
                            lock1.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁1");
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取锁1失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if (flag == 0) {
                try {
                    if (lock2.tryLock(2000, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                        Thread.sleep(new Random().nextInt(1000));
                        // 继续获取第二把锁
                        if (lock1.tryLock(2000, TimeUnit.MILLISECONDS)) {
                            System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                            System.out.println(Thread.currentThread().getName() + "成功获取了两把锁");
                            lock1.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁1");
                            lock2.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁2");
                            break;
                        } else {
                            System.out.println(Thread.currentThread().getName() + "尝试获取锁2失败");
                            lock2.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放锁2");
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取锁1失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
