package com.star.cas;

/**
 * CAS原理  Compare and Swap，即比较再交换
 *
 * @Author: zzStar
 * @Date: 10-10-2020 14:11
 */
public class CASSummary {
    /**
     *CAS是一种无锁算法，CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。
     * 当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
     * 最后返回现在的V值
     *
     * CAS（比较并交换）是CPU指令级的操作，只有一步原子操作，所以非常快。
     * 而且CAS避免了请求操作系统来裁定锁的问题，不用麻烦操作系统，直接在CPU内部就搞定了。
     */

    /**
     * 应用场景
     * 1.乐观锁
     * 2.并发容器
     * 3.原子类
     */

    /**
     * Unsafe是CAS的核心类，Java无法直接访问底层操作系统，而是通过本地native方法来访问，不过尽管如此
     * jdk中的Unsafe类，提供了硬件级别的原子操作
     *
     * Unsafe根据内存偏移地址获取数据的原值，底层代码由C++实现
     */

    /**
     * Java中是如何利用CAS实现原子操作的
     *  Unsafe类中的compareAndSwapInt方法，拿到变量value在内存中的地址
     *  通过Atomic::cmpxchg实现原子性的比较和替换
     *  return (jint)(Atomic::cmpxchg(x,addr,e)) == e;
     */

    /**
     * CAS 缺点
     *  1.ABA问题
     *      因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，
     *      但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A
     *  2.自旋时间过长，消耗CPU
     */
}
