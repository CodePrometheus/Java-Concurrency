package com.star.future.future;

/**
 * @Author: zzStar
 * @Date: 10-13-2020 09:34
 */
public class FutureAnalysis {
    /**
     * Callable & Future 的关系
     *      Future是一个存储器，它存储了call()这个任务的结果，
     *      而这个任务的执行时间是无法提前确定的，因为这完全取决于call()方法执行的情况
     *      相互配合的关系 （运动员和终点线的裁判的关系）
     */

    /**
     *  注意点 ->
     *      当for循环批量获取future的结果时，容易发生一部分线程很慢的情况，get方法调用时应使用timeout加以限制
     *      future的生命周期不能后退，和线程池的生命周期一样，一旦完全完成了任务，就永远停在了已完成的状态
     */
}
