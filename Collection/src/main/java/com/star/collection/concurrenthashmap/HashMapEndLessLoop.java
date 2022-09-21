package com.star.collection.concurrenthashmap;

import java.util.HashMap;

/**
 * 演示hashmap在多线程情况下造成死循环的情况 导致循环链表，造成CPU100%，出现OOM
 * 注意：仅在JDK7及以前存在！
 * hashmap定位就不是高并发
 * <p>
 * hashmap非线程安全，但只读的并发是安全的
 *
 * @Author: zzStar
 * @Date: 10-12-2020 09:02
 */
public class HashMapEndLessLoop {

    // 扩容的时候问题出现,当内容超过4，触发扩容机制
    private static HashMap<Integer, String> map = new HashMap<>(2, 1.5f);

    public static void main(String[] args) {
        map.put(5, "c");
        map.put(7, "b");
        map.put(3, "a");
        new Thread(() -> {
            map.put(15, "d");
            System.out.println(map);
        }, "thread1").start();

        new Thread(() -> {
            map.put(1, "e");
            System.out.println(map);
        }, "thread2").start();

    }
}
