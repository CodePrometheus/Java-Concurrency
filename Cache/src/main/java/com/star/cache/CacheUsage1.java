package com.star.cache;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 最简单的缓存形式 -> HashMap
 * 性能差 + 代码复用性差（侵入性高） + 并发不安全
 *
 * @Author: zzStar
 * @Date: 10-13-2020 12:43
 */
public class CacheUsage1 {

    private final HashMap<String, Integer> cache = new HashMap<>();

    public synchronized Integer computer(String userId) throws InterruptedException {
        //先检查hashmap里面有没有保存过之前的计算结果
        Integer result = cache.get(userId);
        if (result == null) {
            //缓存中没有，则现在计算,计算后保存到cache里
            result = doCompute(userId);
            cache.put(userId, result);
        }
        return result;
    }

    //业务代码
    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return Integer.valueOf(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        CacheUsage1 cacheUsage1 = new CacheUsage1();
        System.out.println("calculating");
        Integer result = cacheUsage1.computer("13");
        System.out.println("result = " + result);
        result = cacheUsage1.computer("13");
        System.out.println("result = " + result);
    }
}
