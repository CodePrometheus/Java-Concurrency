package com.star.jmm.volatileusage;

/**
 * Volatile总结
 *
 * @Author: zzStar
 * @Date: 10-20-2020 07:43
 */
public class VolatileSummary {

    /**
     * Volatile是一种同步机制，比synchronized或者Lock相关类更轻量，因为使用Volatile并不会发生上下文切换等开销很大的行为
     *    如果一个变量被修饰Volatile，JVM就知道这个变量可能被并发修改
     *    注意❗ Volatile做不到synchronized那样的原子保护，仅在很有限的场景下才能发挥作用
     */


    /**
     *  适用场合1 -> Boolean flag 如果一个共享变量自始至终只被各个线程赋值，而没有其他操作，那么就可以用Volatile来代替synchronized或者原子变量
     *              因为赋值自身是由原子性的，而Volatile又保证了可见性
     *         2 -> 作为刷新之前变量的触发器，实现轻量级同步
     */

    /**
     * 注意 -> volatile属性的读写操作都是无锁的，不能代替synchronized，因为它没有提供原子性和互斥性
     *         也正因为无锁，不需要花费时间在获取锁和释放锁上，所以说它是低成本的
     *      并且，Volatile只能修饰属性，不像synchronized还可以修饰方法
     */


    /**
     * Volatile 两点作用
     *   -> 可见性 ： 读一个volatile变量之前，需要先使相应的本地缓存失效，这样就必须到主内存读取最新值，写一个Volatile属性会立即刷入到主内存
     *               任何一个线程对其的修改立马对其他线程可见，Volatile属性不会被线程缓存，始终从主存中读取
     *   -> 禁止指令重排序优化 ： 解决单例双重锁乱序问题
     */


    /**
     * Volatile提供了happens-before保证
     *      此外，Volatile能使得long和double的赋值是原子的
     */


    /**
     * synchronized也具备近朱者赤
     *  -> synchronized不仅防止了一个线程在操作某对象时收到其他线程的干扰，同时还保证了修改好以后，可用立即被其他线程看到
     */
}
