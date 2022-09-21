package com.star.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建单个线程的线程池  LinkedBlockingQueue
 * 同样请求堆积时，可能会导致OOM
 * 只会用唯一的工作线程来执行任务，原理和FixedThreadPool一样，但是此时线程数量被设置为了1
 *
 * @Author: zzStar
 * @Date: 10-08-2020 13:48
 */
public class SingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Task());
        }
    }
    /**
     * 源码如下
     *     public static ExecutorService newSingleThreadExecutor() {
     *         return new FinalizableDelegatedExecutorService
     *             (new ThreadPoolExecutor(1, 1,
     *                                     0L, TimeUnit.MILLISECONDS,
     *                                     new LinkedBlockingQueue<Runnable>()));
     *     }
     */
}
