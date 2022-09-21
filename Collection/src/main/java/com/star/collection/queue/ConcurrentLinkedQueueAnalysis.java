package com.star.collection.queue;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 并发包里的非阻塞队列只有ConcurrentLinkedQueue这一种
 * 使用链表作为其数据结构，使用CAS非阻塞算法来实现线程安全（不具备阻塞功能），适合用在对性能要求较高的并发场景
 *
 * @Author: zzStar
 * @Date: 10-12-2020 13:27
 */
public class ConcurrentLinkedQueueAnalysis {

/*
    public boolean offer(E e) {
        final ConcurrentLinkedQueue.Node<E> newNode = new ConcurrentLinkedQueue.Node<E>(Objects.requireNonNull(e));

        //for死循环
        for (ConcurrentLinkedQueue.Node<E> t = tail, p = t;;) {
            ConcurrentLinkedQueue.Node<E> q = p.next;
            if (q == null) {
                // p is last node
                if (NEXT.compareAndSet(p, null, newNode)) {
                    // Successful CAS is the linearization point
                    // for e to become an element of this queue,
                    // and for newNode to become "live".
                    if (p != t) // hop two nodes at a time; failure is OK
                        TAIL.weakCompareAndSet(this, t, newNode);
                    return true;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)
                // We have fallen off list.  If tail is unchanged, it
                // will also be off-list, in which case we need to
                // jump to head, from which all live nodes are always
                // reachable.  Else the new tail is a better bet.
                p = (t != (t = tail)) ? t : head;
            else
                // Check for tail updates after two hops.
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }
*/

}
