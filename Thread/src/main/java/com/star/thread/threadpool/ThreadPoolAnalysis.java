package com.star.thread.threadpool;

/**
 * 线程池分析
 *
 * @Author: zzStar
 * @Date: 10-12-2020 14:07
 */
public class ThreadPoolAnalysis {

    /**
     * 线程池的组分部分：
     *      线程池管理器
     *      工作线程
     *      任务队列（blocking queue）
     *      任务接口（Task）
     */

    /**
     * Executor <- ExecutorService <- AbstractExecutorService <- ThreadPoolExecutor
     */

    /**
     * 线程池实现任务复用的原理
     *      相同线程执行不同的任务，线程池是让固定的线程不断地从队列中取出任务并执行
     */

    /**
     * 线程池五种状态
     * RUNNING
     * SHUTDOWN 不接受新任务，但处理排队任务
     * STOP
     * TIDYING 所有任务都终止，workerCount为0时，线程会转到TIDYING状态，并将运行terminate()钩子方法
     * TERMINATED terminate()运行完成
     */

    /**
     * 使用线程池注意点：
     *      避免任务堆积
     *      避免线程数过度增加
     *      排查线程泄露（线程回收不了，任务逻辑可能出问题）
     */
}
/*
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        */
/*
         * Proceed in 3 steps:
         *
         * 1. If fewer than corePoolSize threads are running, try to
         * start a new thread with the given command as its first
         * task.  The call to addWorker atomically checks runState and
         * workerCount, and so prevents false alarms that would add
         * threads when it shouldn't, by returning false.
         *
         * 2. If a task can be successfully queued, then we still need
         * to double-check whether we should have added a thread
         * (because existing ones died since last checking) or that
         * the pool shut down since entry into this method. So we
         * recheck state and if necessary roll back the enqueuing if
         * stopped, or start a new thread if there are none.
         *
         * 3. If we cannot queue task, then we try to add a new
         * thread.  If it fails, we know we are shut down or saturated
         * and so reject the task.
         *//*

        //ctl记录了线程状态和线程数
        int c = ctl.get();
        //少于则创建一个线程
        if (workerCountOf(c) < corePoolSize) {
            //任务command
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        // >=corePoolSize,先放入工作队列中
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            //防止任务提交却没有线程来执行
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }

    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            //work不会停止
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if ((runStateAtLeast(ctl.get(), STOP) ||
                     (Thread.interrupted() &&
                      runStateAtLeast(ctl.get(), STOP))) &&
                    !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    try {
                        task.run();
                        afterExecute(task, null);
                    } catch (Throwable ex) {
                        afterExecute(task, ex);
                        throw ex;
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
*/

