package com.star.thread.create;

/**
 * 实现多线程的方法总结
 *
 * @Author: zzStar
 * @Date: 10-13-2020 19:06
 */
public class CreateThreadSummary {

    /**
     * 两种方法！创建新的执行线程
     *      1.将类声明为子类Thread，重写类的run方法Thread，也即继承Thread类
     *      2.声明一个实现Runnable接口的类，实现run方法
     *
     *   准确地说，创建线程只有一种方式就是构造Thread类，而实现线程的执行单元有两种方式
     *      1.实现Runnable接口的run方法，并把Runnable实例传给Thread类
     *      2.重写Thread的run方法（继承Thread类）
     */


    /**
     * 优先选择Runnable接口
     *      1.解耦
     *      2.传入实例可以反复利用同一个线程
     *      3.Java不支持双继承
     */
}
