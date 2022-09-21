package com.star.cache;

import java.util.HashMap;

/**
 * 多线程访问的性能问题:在多线程的情况下,只能一个线程进入cache 的计算方法
 *
 * @Author: zzStar
 * @Date: 10-13-2020 13:03
 */
public class CacheUsage3<A, V> implements Computable<A, V> {

    private final HashMap<A, V> cache = new HashMap<>();

    private final Computable<A, V> computable;

    public CacheUsage3(Computable<A, V> computable) {
        this.computable = computable;
    }

/*
    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println(Thread.currentThread().getName() + " 进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
*/

    //缩小锁的粒度,仍不意味着线程安全，还需要考虑到同时读写等情况
    @Override
    public V compute(A arg) throws Exception {
        System.out.println(Thread.currentThread().getName() + " 进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            synchronized (this) {
                cache.put(arg, result);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        CacheUsage3<String, Integer> usage3 = new CacheUsage3<>(new ExpensiveFunc());
        new Thread(() -> {
            try {
                Integer result = usage3.compute("7878");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage3.compute("7878");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage3.compute("8989");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
