package com.star.thread.startthread;

/**
 * 对比start & run
 *
 * 一个线程两次调用start方法时会抛出异常，见源码对线程状态的检查
 * 调用start才是真正意义上的启动线程，经历线程的各个周期
 *
 * @Author: zzStar
 * @Date: 10-13-2020 20:09
 */
public class StartAndRunMethod {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
        };

        runnable.run();//main

        new Thread(runnable).start();//Thread-0

    }
}

/*
    private volatile int threadStatus;

    public synchronized void start() {
        */
/**
 * This method is not invoked for the main method thread or "system"
 * group threads created/set up by the VM. Any new functionality added
 * to this method in the future may have to also be added to the VM.
 * <p>
 * A zero status value corresponds to state "NEW".
 *//*

        //检查线程状态，初始化就是0
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        */
/* Notify the group that this thread is about to be started
 * so that it can be added to the group's list of threads
 * and the group's unstarted count can be decremented. *//*

        //加入线程组
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                */
/* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack *//*

            }
        }
    }

    //jdk源码
    private native void start0();
*/

