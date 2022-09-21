package com.star.thread;

/**
 * 线程核心基础知识总结
 *
 * @Author: zzStar
 * @Date: 10-19-2020 14:40
 */
public class ThreadCoreKnowledge {

    /**
     * 实现线程两种方法，本质是一种（最终调用target.run(),整个run方法被重写），优先选择Runnable接口及其优点
     *      ->  也就是说，一种创建线程的方式，二种实现执行单元的方式
     * 开启线程的正确方法start
     *      -> 启动新线程检查线程状态，加入线程组，调用native方法start0()
     * 停止线程的正确方法interrupt来请求
     *      -> 优先选择：传递中断
     */

    /**
     * 处理不可中断的阻塞
     * 线程的六大状态（生命周期）
     * 消费者生产者设计模式的实现
     * 线程重要属性
     * 线程的重要方法
     * wait,notify,sleep异同
     * join期间线程处于waiting状态
     * 守护线程和普通线程的区别
     * 线程未捕获异常的处理UncaughtExceptionHandler
     */
}
