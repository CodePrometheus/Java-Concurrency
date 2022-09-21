package com.star.thread.stopthread;

/**
 * 停止线程总结 错误
 *
 * @Author: zzStar
 * @Date: 10-14-2020 16:25
 */
public class StopThreadSummary {

    /**
     * 正确停止线程
     *      -> 使用interrupt来通知，而不是强制 （让被停止的线程本身决定后续工作）
     *      -> 想停止线程，要请求方、被停止方、子方法被调用者方相互配合
     *      -> stop/suspend 已废弃，volatile的boolean无法处理长时间阻塞的情况
     */
}

