package com.star.lock;

/**
 * 非公平锁 指的是不完全按照请求的顺序，在一定的情况下，可以插队
 *
 * @Author: zzStar
 * @Date: 10-09-2020 15:46
 */
public class NonFairLock {
/*
    final boolean nonfairTryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            //不管有没有在队列中，都尝试获取锁
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
*/

}
