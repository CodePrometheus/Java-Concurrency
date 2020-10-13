import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试CacheUsage5
 *
 * @Author: zzStar
 * @Date: 10-13-2020 14:39
 */
public class CacheUsage5Test {

    static CacheUsage5<String, Integer> cache = new CacheUsage5<>(new ExpensiveFunc());

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        //实现压测，同一时刻大量请求
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            pool.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "准备中.....");
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getName() + " 放行:" + TimeHolder.get().format(new Date()));
                    Integer result = cache.compute("666");
                    System.out.println("result=" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(3000);
        countDownLatch.countDown();
        pool.shutdown();
    }
}

class TimeHolder {
    //每个线程需要独享的对象
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static SimpleDateFormat get() {
        return threadLocal.get();
    }
}

