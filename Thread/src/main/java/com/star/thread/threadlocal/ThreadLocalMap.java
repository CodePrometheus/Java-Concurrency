package com.star.thread.threadlocal;

/**
 * 分析原理
 *
 * @Author: zzStar
 * @Date: 10-08-2020 18:10
 */
public class ThreadLocalMap {
    /**
     static class Entry extends WeakReference<ThreadLocal<?>> {
     //The value associated with this ThreadLocal.
     Object value;

     Entry(ThreadLocal<?> k, Object v) {
     super(k);
     value = v;
     }
     }
     */

    /**
     * 可以看到，ThreadLocalMap的每个Entry都是一个对key的弱引用
     * 同时，每个Entry都包含了一个对value的强引用
     * 正常情况下，当线程终止时，保证在ThreadLocal里的value会被gc
     *
     * 但是，如果线程不终止，那么key对应的value就不能被回收
     * 从而导致value和Thread之间还存在强引用链路，value无法回收，可能出现OOM
     * Thread -> ThreadLocalMap -> Entry(key为null) -> value
     * JDK已经考虑到这个问题，所以在set，remove，rehash方法中会扫描key为null的Entry
     * 并把对应的value设置为null，这样value对象就可以被回收
     *
     * 但是，如果一个ThreadLocal不被使用，实际上set，remove，rehash方法也不会被调用
     * 同时线程又不停止，导致OOM
     *
     * 所以！阿里规约：使用完ThreadLocal之后，应该手动调用remove方法
     *
     * 弱引用的特点是如果这个对象只被弱引用关联（没有任何强引用关联），那么这个对象就可以被回收
     * 所以弱引用不会阻止gc
     */


    /**
     * 此外，ThreadLocalMap不同与HashMap
     * ThreadLocalMap遇到hash冲突的时候，采用的是线性探测法，也就是如果发送冲突
     * 就继续找下一个空位置，而不同于HashMap的链表拉链红黑树
     */


}
