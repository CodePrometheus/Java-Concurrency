package singleton.lazyman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author: zzStar
 * @Date: 12-08-2021 14:03
 */
public class Singleton {

    private static Singleton singleton = null;

    private Integer x = 1;

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
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
            List<Callable<Singleton>> list = new ArrayList<>();
            for (int j = 0; j < 500; j++) {
                list.add(() -> {
                    barrier.await();
                    Singleton instance = Singleton.getInstance();
                    if (instance.x == null) {
                        throw new RuntimeException("拿到了未被初始化的对象");
                    }
                    return instance;
                });
            }
            List<Future<Singleton>> futures = pool.invokeAll(list);
            Set<Singleton> set = new HashSet<>();
            for (Future<Singleton> future : futures) {
                set.add(future.get());
            }
            if (set.size() > 1) {
                System.out.println("并发生成多实例");
                throw new RuntimeException();
            }
            Singleton.removeInstance();
        }
        System.out.println("正常结束");
    }

}
