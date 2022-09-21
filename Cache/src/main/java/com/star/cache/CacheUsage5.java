package com.star.cache;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description: 利用future避免重复计算 + 缓存过期到期自动失效
 * @Author: zzStar
 * @Date: 2020/10/13 13:45
 */
public class CacheUsage5<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computable;

    public CacheUsage5(Computable<A, V> computable) {
        this.computable = computable;
    }


    @Override
    public V compute(A arg) {
        Future<V> future = cache.get(arg);
        if (future == null) {
            Callable<V> callable = () -> computable.compute(arg);
            FutureTask<V> futureTask = new FutureTask<>(callable);

            //原子组合操作，每一个线程进来都先做一次判断
            future = cache.putIfAbsent(arg, futureTask);
            if (future == null) {
                future = futureTask;
                System.out.println("从FutureTask调用计算函数");
                futureTask.run();
            }
            //计算之前放入缓存
            //cache.put(arg, futureTask);
        }
        V v = null;
        try {
            //计算的时候有可能会出现异常
            v = future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("出现异常 " + e.getMessage());
            //取消线程任务,发送中断信号
            future.cancel(true);
        }
        //在有结果之前会阻塞
        return v;
    }

    //延迟功能
    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

    //缓存过期功能,重载
    public V compute(A arg, long expire) {
        //超时时间>0
        if (expire > 0) {
            service.schedule(() -> {
                //如果还在计算,则直接取消任务
                Future<V> vFuture = cache.get(arg);
                if (vFuture != null) {
                    if (!vFuture.isDone()) {
                        vFuture.cancel(true);
                    }
                }
                cache.remove(arg);
                System.out.println("过期时间到了,清除缓存:" + arg);
            }, expire, TimeUnit.MILLISECONDS);
        }
        service.shutdown();
        return compute(arg);
    }

    //把缓存的时间设置为随机，过期时间不设置为统一，避免缓存雪崩
    public V computeRandomExpire(A arg) {
        long expire = (long) (Math.random() * 10000);
        return compute(arg, expire);
    }

    public static void main(String[] args) {
        CacheUsage5<String, Integer> usage5 = new CacheUsage5<>(new ExpensiveFunc());
        new Thread(() -> {
            try {
                Integer result = usage5.computeRandomExpire("1222");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage5.compute("1222");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = usage5.compute("1222");
                System.out.println(Thread.currentThread().getName() + " result=" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
