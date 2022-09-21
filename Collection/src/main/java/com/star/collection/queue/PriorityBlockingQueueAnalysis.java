package com.star.collection.queue;

/**
 * PriorityBlockingQueue支持优先级，自然顺序(而不是先进先出)，无界队列(不够会进行扩容)
 * 可看作是PriorityQueue的线程安全版本
 *
 * @Author: zzStar
 * @Date: 10-12-2020 13:05
 */
public class PriorityBlockingQueueAnalysis {

/*
    public void put(E e) {
        offer(e); // never need to block
    }

    public boolean offer(E e, long timeout, TimeUnit unit) {
        return offer(e); // never need to block
    }

    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n, cap;
        Object[] es;
        while ((n = size) >= (cap = (es = queue).length))
            tryGrow(es, cap);
        try {
            final Comparator<? super E> cmp;
            if ((cmp = comparator) == null)
                siftUpComparable(n, e, es);
            else
                siftUpUsingComparator(n, e, es, cmp);
            size = n + 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return true;
    }


*/

}
