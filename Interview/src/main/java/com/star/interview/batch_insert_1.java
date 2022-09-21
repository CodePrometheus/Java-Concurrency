package com.star.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 上游接口获取数据，先存入队列 ArrayBlockingQueue<Integer> queue 容量 capacity
 * 定时启动 线程池 @Scheduled
 * 取出数据定量数据
 * 对数据分片 size 个 List<List<Object>>
 * 线程池里 for 循环 size 次 取出分完片的数据
 * 多线程批次插入 countDownLatch
 *
 * @Author: Starry
 * @Date: 08-23-2022 12:40
 */
public class batch_insert_1<T> {
    // 线程名称前缀，可自定义
    private String threadNamePrefix = "reportInsert-";
    // 是否已经开始处理
    private boolean started;
    // 用于等待线程处理结束后的收尾处理
    private CountDownLatch cdl;
    // 是否还会产生数据: 用于配合 queue.size() 判断线程是否该结束
    private volatile boolean isProduceData = true;
    // 实体数据容器队列，队列满，则限制生产方的生产速度
    private ArrayBlockingQueue<T> queue;

    // 能存储数据时，就调用该方法给使用方，使用方可以调用存储接口存储
    private StorageConsumer<T> consumer;
    // 批量插入时，每次最多插入多少条 每段最大步长
    private int maxItemCount;
    private List<WorkThread> workThreadList;


    public batch_insert_1() {
        this(1000);
    }

    /**
     * <pre>
     *    capacity ：利用队列的阻塞 put，来调节生产速度和消费速度的差别
     *      当生产速度明显大于插入速度时，该参数用来限制生产的速度，达到该上限时，生成方就会阻塞，知道有新的容量空闲出来
     * </pre>
     *
     * @param capacity 队列能接收的最大容量
     */
    public batch_insert_1(int capacity) {
        queue = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * 配置线程名称前缀
     *
     * @param threadNamePrefix
     */
    public synchronized void setThreadNamePrefix(String threadNamePrefix) {
        if (started) {
            throw new RuntimeException("已经开始处理，不能再线程名称前缀");
        }
        this.threadNamePrefix = threadNamePrefix;
    }

    public void start(StorageConsumer<T> consumer) {
        this.start(consumer, 4);
    }

    public void start(StorageConsumer<T> consumer, int workThreadCount) {
        this.start(consumer, workThreadCount, 0);
    }

    /**
     * @param consumer        每次保存实体时，调用该方法，由该方法处理保存逻辑
     * @param workThreadCount 并发数量：用几个多线程同时处理数据
     * @param maxItemCount    批量插入数量，每次插入最多多少条：将单个实体缓存起来，达到该数量再入库，如果使用批量插入功能，则必须大于 1
     */
    public synchronized void start(StorageConsumer<T> consumer, int workThreadCount, int maxItemCount) {
        if (started) {
            throw new RuntimeException("处理中");
        }
        started = true;
        this.consumer = consumer;
        this.maxItemCount = maxItemCount;
        this.cdl = new CountDownLatch(workThreadCount);
        workThreadList = IntStream.range(0, workThreadCount)
                .mapToObj(i -> {
                    final WorkThread workThread = new WorkThread(threadNamePrefix + i, maxItemCount);
                    workThread.start();
                    return workThread;
                })
                .collect(Collectors.toList());
    }

    /**
     * 将实体交给处理器，处理器的线程会消费该实体；
     * <pre>
     *  当容器队列已满时，则会阻塞，以此达到生产方暂停生产的目的；可以防止生产速度过快（消费速度过慢），导致占用过多内存
     * </pre>
     *
     * @param entity
     */
    public void put(T entity) {
        try {
            queue.put(entity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 等待，处理器处理完成；此方法会阻塞
     */
    public void await() {
        if (!started) {
            throw new RuntimeException("还未运行");
        }
        try {
            isProduceData = false;
            cdl.await();
            for (WorkThread workThread : workThreadList) {
                workThread.clearEntity();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即停止，只适合在生产方不生产数据时，调用
     */
    public void stop() {
        if (!started) {
            throw new RuntimeException("还未运行");
        }
        isProduceData = false;
        queue.clear();
    }

    private class WorkThread extends Thread {
        // 批量插入时，用于缓存实体的容器
        private List<T> batchCacheContainer;
        private int maxItemCount;

        public WorkThread(String name, int maxItemCount) {
            super(name);
            this.maxItemCount = maxItemCount;
            if (maxItemCount > 0) {
                batchCacheContainer = new ArrayList<>(maxItemCount);
            }
        }

        @Override
        public void run() {
            while (true) {
                // 如果不产生数据了，队列也会空，则退出线程
                if (!isProduceData && queue.size() == 0) {
                    break;
                }
                final T entity;
                try {
                    entity = queue.poll(500, TimeUnit.MILLISECONDS);
                    if (entity == null) {
                        continue;
                    }
                    if (maxItemCount > 0) {
                        batchCacheContainer.add(entity);
                        if (batchCacheContainer.size() >= maxItemCount) {
                            consumer.accept(null, batchCacheContainer);
                            batchCacheContainer.clear();
                        }
                    } else {
                        consumer.accept(entity, null);
                    }
                } catch (InterruptedException e) {
                    cdl.countDown();
                    e.printStackTrace();
                }
            }
            cdl.countDown();
        }

        public void clearEntity() {
            if (maxItemCount > 0 && batchCacheContainer.size() > 0) {
                consumer.accept(null, batchCacheContainer);
                batchCacheContainer.clear();
            }
        }
    }

    public interface StorageConsumer<T> {
        /**
         * 需要使用方存储数据时，会调用该方法
         *
         * @param t
         * @param ts
         */
        void accept(T t, List<T> ts);
    }
}
