package com.star.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定线程数量的线程池,使用的线程任务队列:LinkedBlockingQueue
 * 且由于LinkedBlockingQueue是没有容量上限的，请求堆积时，可能会导致OOM, gc overhead
 *
 * @Author: zzStar
 * @Date: 10-08-2020 13:38
 */
public class FixedThreadPool {
    public static void main(String[] args) {
        //固定为4,不会超过这个范围
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Task());
        }
    }
/**
 * 源码如下
 *     public static ExecutorService newFixedThreadPool(int nThreads) {
 *         return new ThreadPoolExecutor(nThreads, nThreads,
 *                                       0L, TimeUnit.MILLISECONDS,
 *                                       new LinkedBlockingQueue<Runnable>());
 *     }
 */
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());

    }
}
