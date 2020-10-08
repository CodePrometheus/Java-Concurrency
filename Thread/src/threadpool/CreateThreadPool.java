package threadpool;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程池
 *
 * @Author: zzStar
 * @Date: 10-08-2020 13:09
 */
public class CreateThreadPool {

    /**
     * 了解几个线程池构造函数的参数
     * <p>
     * corePoolSize
     * 核心线程数,即在没有任务需要执行的时候线程池的大小,这里需要注意的是：
     * 在刚刚创建ThreadPoolExecutor的时候，线程并不会立即启动，而是要等到有任务提交时才会启动,
     * 所以没有任务需要执行的时候，线程池的大小不一定是corePoolSize。
     * <p>
     * maximumPoolSize
     * 线程池中允许的最大线程数,线程池中的当前线程数目不会超过该值
     * 如果队列中任务已满，并且当前线程个数小于maximumPoolSize，那么会创建新的线程来执行任务
     * <p>
     * 也就是说，线程池有可能会在核心线程数的基础上，额外增加一些线程，但是新增的有上限就是maxPoolSize
     * 规则：线程数 < corePoolSize，即使其他工作线程处于空闲，也会创建新线程来执行任务
     * maximumPoolSize > 线程数 >= corePoolSize 将任务放入队列
     * 若队列已满，并且线程数 < maximumPoolSize,创建新线程来执行任务
     * 若队列已满，并且线程数 >= maximumPoolSize,拒绝任务
     * <p>
     * 将maximumPoolSize和corePoolSize设置相同，可创建固定大小的线程池
     * <p>
     * keepAliveTime
     * 线程空闲时间，也即保持存活时间
     * 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
     * 如果allowCoreThreadTimeout=true，则会直到线程数量=0
     * <p>
     * unit
     * 线程空闲时间的单位
     * <p>
     * workQueue(类型BlockingQueue)
     * 1.直接交接SynchronousQueue
     * 2.无界队列LinkedBlockingQueue
     * 3.有界队列ArrayBlockingQueue
     * 工作队列
     * <p>
     * threadFactory
     * 生成新的线程
     * <p>
     * Handler(类型RejectedExecutionHandler)
     * 无法提交任务时候的拒绝策略
     */

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(4);
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor
                        (2,
                                4,
                                60,
                                TimeUnit.SECONDS,
                                queue
                                , new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 8; i++) {
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "阻塞");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println(queue);
        //8 个任务 : 2--> 核心线程 , 4 个等待  2->新创建
        //9 个任务 : 拒绝策略:抛出异常 java.util.concurrent.RejectedExecutionException

    }
}
