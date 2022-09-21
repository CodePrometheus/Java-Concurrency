package com.star.aqs;

/**
 * AbstractQueuedSynchronizer
 *
 * @Author: zzStar
 * @Date: 10-12-2020 19:02
 */
public class AqsAnalysis {

    /**
     * 不仅是ReentrantLock，Semaphore，包括CountDownLatch，ReentrantReadWriterLock都有这样类似的协作功能
     * 底层都用了一个共同的基类AQS
     * 所以在很多工作类似的基础之上抽象提取出一个工具类 -> AQS
     */

    /**
     * AQS最核心的三大部分：
     *      state (The synchronization state)     private volatile int state;
     *      控制线程抢锁和配合的FIFO队列（先进先出）
     *      期望协作工具类去实现的获取/释放等重要方法
     */

    /**
     * state -> 具体含义会根据实现类的不同而不同，可表示剩余的许可证的数量、还需要倒数的数量
     *  被volatile修饰，会被并发地修改，所以所有修改state的方法都需要保证线程安全(依赖于juc下的atomic包的支持CAS)
     *  在ReentrantLock中，表示锁的占有情况，包括可重入计数
     */

    /**
     * 控制线程抢锁和配合的FIFO队列
     *      这些队列用来存放 等待的线程  AQS就是排队管理器，当多个线程争用同一把锁时，必须有排队机制将那些没能拿到锁的线程串在一起
     *      锁释放时，锁管理器就会挑选一个合适的线程来占有这个刚刚释放的锁
     *
     *   AQS会维护一个等待的线程队列，把线程都放在这个双向队列里
     *
     *         +------+  prev +-----+       +-----+
     *  head |      | <---- |     | <---- |     |  tail
     *       +------+       +-----+       +-----+
     */


    /**
     * 期望协作工具类去实现的获取/释放等重要方法
     *      由协作类自己去实现，并且含义各不相同   （比如acquire，release）
     *   获取方法 -> 依赖于state变量，经常会阻塞（比如获取不到锁的时候）
     */
}
