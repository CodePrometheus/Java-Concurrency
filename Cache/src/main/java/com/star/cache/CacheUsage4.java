package com.star.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (使用)ConcurrentHashMap >>>> 问题:出现线程重复计算的问题
 *
 * @Author: zzStar
 * @Date: 10-13-2020 13:03
 */
public class CacheUsage4<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computable;

    public CacheUsage4(Computable<A, V> computable) {
        this.computable = computable;
    }


    @Override
    public V compute(A arg) throws Exception {
        System.out.println(Thread.currentThread().getName() + " 进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }


    public static void main(String[] args) {
        CacheUsage4<String, Integer> usage3 = new CacheUsage4<>(new ExpensiveFunc());
        new Thread(() -> {
            try {
                Integer result = usage3.compute("5656");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage3.compute("5656");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage3.compute("9090");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
