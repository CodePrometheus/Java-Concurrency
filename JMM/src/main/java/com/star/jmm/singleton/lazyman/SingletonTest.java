package com.star.jmm.singleton.lazyman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author: zzStar
 * @Date: 12-08-2021 14:03
 */
public class SingletonTest {

    private static SingletonTest singleton = null;

    private Integer x = 1;

    public static SingletonTest getInstance() {
        if (singleton == null) {
            synchronized (SingletonTest.class) {
                if (singleton == null) {
                    singleton = new SingletonTest();
                }
            }
        }
        return singleton;
    }

    private static void removeInstance() {
        singleton = null;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newCachedThreadPool();
        // 500个线程并发，执行1000_000次
        CyclicBarrier barrier = new CyclicBarrier(500);
        for (int i = 0; i < 1000_000; i++) {
            barrier.reset();
            System.out.println("i = " + i);
            List<Callable<SingletonTest>> list = new ArrayList<>();
            for (int j = 0; j < 500; j++) {
                list.add(() -> {
                    barrier.await();
                    SingletonTest instance = SingletonTest.getInstance();
                    if (instance.x == null) {
                        throw new RuntimeException("拿到了未被初始化的对象");
                    }
                    return instance;
                });
            }
            List<Future<SingletonTest>> futures = pool.invokeAll(list);
            Set<SingletonTest> set = new HashSet<>();
            for (Future<SingletonTest> future : futures) {
                set.add(future.get());
            }
            if (set.size() > 1) {
                System.out.println("并发生成多实例");
                throw new RuntimeException();
            }
            SingletonTest.removeInstance();
        }
        System.out.println("正常结束");
    }

}
