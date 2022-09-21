package com.star.thread.threadattributes;

/**
 * 守护线程 -> 给用户线程提供服务
 *
 * @Author: zzStar
 * @Date: 10-18-2020 15:37
 */
public class DaemonThread {

    /**
     * 1.线程默认继承自父线程
     * 2.通常，守护线程都是由JVM自动启动
     * 3.不影响JVM的退出
     */

    /**
     * 和普通线程的区别 ->
     *      整体无区别
     *      唯一的区别在于是否影响JVM的退出
     */

    /**
     * 不应该把线程设置为守护线程，因为JVM可能退出，导致操作过程中间被强行停止，导致数据不一致
     */
}
