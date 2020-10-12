package queue;

/**
 * SynchronousQueue容量为0，因为SynchronousQueue无需去持有元素，直接传递(direct handoff)，效率高
 *
 * SynchronousQueue是线程池Executors.newCachedThreadPool()使用的阻塞队列
 *
 * @Author: zzStar
 * @Date: 10-12-2020 13:14
 */
public class SynchronousQueueAnalysis {
}
