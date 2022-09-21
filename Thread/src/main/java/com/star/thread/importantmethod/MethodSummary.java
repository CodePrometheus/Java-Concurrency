package com.star.thread.importantmethod;

/**
 * @Author: zzStar
 * @Date: 10-17-2020 14:56
 */
public class MethodSummary {

    /**
     * wait,notify,notifyAll
     *  1.用必须先拥有monitor
     *  2.只能唤醒其中一个，取决于jvm的实现
     *  3.都属于Object类，任何对象都可以调用，native final
     *  4.类似功能的Condition
     *  5.同时持有多个锁的情况，释放现在wait所对应的对象的那把锁，注意避免死锁的发生
     */

    /**
     * 特殊情况注意 ❗
     *     -> 从Object.wait()状态刚被唤醒时，通常不能立刻抢到monitor锁，（因为其他线程唤醒也需要持有同一把锁）
     *           那就会从waiting先进入blocked状态，抢到锁后再转换到Runnable状态
     *     -> 如果发生异常，可以直接跳转到终止Terminated状态，不必再遵循路径，比如可以从waiting直接到Terminated
     */

    /**
     * wait()需要放在同步代码块里面执行的原因是为了让通信变得可靠，防止死锁、永久等待的发生
     * 线程间需要协同配合的方式放入同步代码块中实现
     */

    /**
     * 为什么wait,notify,notifyAll被定义在Object里面？
     *      ->锁级别的操作，属于对象，而非线程中
     *      ->Thread.wait，线程退出的时候，源码中会自动notify
     */

    /**
     *  Sleep ❗ 方法可以让线程进入waiting状态，并且不占用CPU资源，但是不释放锁，
     *   直到规定时间后再执行，休眠期间如果被中断，会抛出异常并清除中断状态
     */

    /**
     * wait ，notify ，sleep 异同
     *      -> 相同 1.阻塞
     *             2.响应中断
     *
     *      -> 不同 1.wait ，notify必须在同步方法（见上述原因）
     *              2.Sleep不释放锁
     *              3.sleep要指定时间，wait不传则直到自己被唤醒
     *              4.所属类不同（见上述原因）
     */

    /**
     *  join 期间，线程处于waiting的状态
     */

    /**
     * yield释放CPU时间片，但不释放锁，也不会陷入阻塞，仍然Runnable
     */
}
