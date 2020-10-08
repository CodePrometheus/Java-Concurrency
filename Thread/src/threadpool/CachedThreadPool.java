package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 无限线程池。 SynchronousQueue
 * 可缓存线程池，具有自动回收多余线程的功能
 *
 * @Author: zzStar
 * @Date: 10-08-2020 13:52
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Task());
        }
    }

    /**
     * 源码如下
     *     public static ExecutorService newCachedThreadPool() {
     *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
     *                                       60L, TimeUnit.SECONDS,
     *                                       new SynchronousQueue<Runnable>());
     *     }
     *     由于maximumPoolSize被设置为Integer.MAX_VALUE，这会创建非常多的线程，甚至导致OOM
     *     空闲60秒回收线程
     */

}
