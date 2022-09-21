package com.star.jmm.singleton.lazyman;

/**
 * 单例之五 -> 懒汉式（线程不安全，同步代码块）不可用，引申出双重检查
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:41
 */
public class SingletonSynchronizationCodeBlock {

    private static SingletonSynchronizationCodeBlock instance;

    private SingletonSynchronizationCodeBlock() {
    }

    public static SingletonSynchronizationCodeBlock getInstance() {

        // 线程仍不安全
        if (instance == null) {
            // 两个线程可能同时进入
            synchronized (SingletonSynchronizationCodeBlock.class) {
                // 第二个线程进来之后再来复制一遍，创建多个实例
                instance = new SingletonSynchronizationCodeBlock();
            }
        }

        return instance;
    }

}
