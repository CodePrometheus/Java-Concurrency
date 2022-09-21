package com.star.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * public interface ReadWriteLockReadWriteLock 维护了一对相关的锁，一个用于只读操作，另一个用于写入操作。只要没有 writer，读取锁可以由多个 reader 线程同时保持。写入锁是独占的。
 * 读写锁实际是一种特殊的自旋锁
 * <p>
 * 所有 ReadWriteLock 实现都必须保证 writeLock 操作的内存同步效果也要保持与相关 readLock 的联系。也就是说，成功获取读锁的线程会看到写入锁之前版本所做的所有更新。
 * <p>
 * 与互斥锁相比，读-写锁允许对共享数据进行更高级别的并发访问。虽然一次只有一个线程（writer 线程）可以修改共享数据，但在许多情况下，任何数量的线程可以同时读取共享数据（reader 线程），读-写锁利用了这一点。
 * 从理论上讲，与互斥锁相比，使用读-写锁所允许的并发性增强将带来更大的性能提高。在实践中，只有在多处理器上并且只在访问模式适用于共享数据时，才能完全实现并发性增强。
 * <p>
 * 与互斥锁相比，使用读-写锁能否提升性能则取决于读写操作期间读取数据相对于修改数据的频率，以及数据的争用——即在同一时间试图对该数据执行读取或写入操作的线程数。例如，某个最初用数据填充并且之后不经常对其进行修改的 collection，因为经常对其进行搜索（比如搜索某种目录），
 * 所以这样的 collection 是使用读-写锁的理想候选者。
 * 但是，如果数据更新变得频繁，数据在大部分时间都被独占锁，这时，就算存在并发性增强，也是微不足道的。更进一步地说，如果读取操作所用时间太短，则读-写锁实现（它本身就比互斥锁复杂）的开销将成为主要的执行成本,
 * 在许多读-写锁实现仍然通过一小段代码将所有线程序列化时更是如此。最终，只有通过分析和测量，才能确定应用程序是否适合使用读-写锁。
 * <p>
 * 尽管读-写锁的基本操作是直截了当的，但实现仍然必须作出许多决策，这些决策可能会影响给定应用程序中读-写锁的效果。这些策略的例子包括：
 * 在 writer 释放写入锁时，reader 和 writer 都处于等待状态，在这时要确定是授予读取锁还是授予写入锁。Writer 优先比较普遍，因为预期写入所需的时间较短并且不那么频繁。
 * Reader 优先不太普遍，因为如果 reader 正如预期的那样频繁和持久，那么它将导致对于写入操作来说较长的时延。公平或者“按次序”实现也是有可能的。
 * <p>
 * 在 reader 处于活动状态而 writer 处于等待状态时，确定是否向请求读取锁的 reader 授予读取锁。Reader 优先会无限期地延迟 writer，而 writer 优先会减少可能的并发。
 * 确定是否重新进入锁：可以使用带有写入锁的线程重新获取它吗？可以在保持写入锁的同时获取读取锁吗？可以重新进入写入锁本身吗？
 * 可以将写入锁在不允许其他 writer 干涉的情况下降级为读取锁吗？可以优先于其他等待的 reader 或 writer 将读取锁升级为写入锁吗？
 * 当评估给定实现是否适合您的应用程序时，应该考虑所有这些情况。
 *
 * 注意：读锁插队策略
 * 公平锁 -> 不允许插队
 * 非公平锁 -> 写锁可以随时插队
 *            读锁仅在等待队列头结点 不是想获取写锁的线程的时候 可以插队
 *
 * @Author: zzStar
 * @Date: 10-09-2020 16:47
 */
public class ReadWriteLockDemo {

    public static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        //读锁能同时得到，而写锁不能
        new Thread(() -> write(), "Thread4").start();
        new Thread(() -> read(), "Thread1").start();
        new Thread(() -> read(), "Thread2").start();
        new Thread(() -> write(), "Thread3").start();
        new Thread(() -> read(), "Thread5").start();
    }



}
