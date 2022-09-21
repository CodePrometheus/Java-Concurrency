package com.star.jmm.singleton.lazyman;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例之八 -> 枚举（生产实践推荐使用）
 * -> 单元素的枚举类型已经成为实现Singleton的最佳方法
 * -> 线程安全有保障，避免反序列化破坏单例
 *
 * @Author: zzStar
 * @Date: 10-20-2020 12:28
 */
public enum SingletonEnum {

    INSTANCE;

    public SingletonEnum getInstance() {
        return INSTANCE;
    }

    /**
     * 检查反射攻击
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SingletonEnum singleton1 = SingletonEnum.INSTANCE;
        SingletonEnum singleton2 = SingletonEnum.INSTANCE;
        System.out.println("正常情况下，实例化两个实例是否相同：" + (singleton1 == singleton2));
        Constructor<SingletonEnum> constructor;
        // 一旦一个类声明为枚举，实际上就是继承了 Enum，所以会有（String.class,int.class）的构造器
        constructor = SingletonEnum.class.getDeclaredConstructor(String.class, int.class); // 其父类的构造器
        constructor.setAccessible(true);
        SingletonEnum singleton3;
        singleton3 = constructor.newInstance("testInstance", 66);
        System.out.println(singleton1 + "\n" + singleton2 + "\n" + singleton3);
        /**
         * if ((clazz.getModifiers() & Modifier.ENUM) != 0)
         *             throw new IllegalArgumentException("Cannot reflectively create enum objects");
         */
        System.out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (singleton1 == singleton3));
    }
}
