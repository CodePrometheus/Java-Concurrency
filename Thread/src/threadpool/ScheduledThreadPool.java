package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 支持定时及周期性任务执行的线程池
 *
 * @Author: zzStar
 * @Date: 10-08-2020 13:56
 */
public class ScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.schedule(new Task(), 5, TimeUnit.SECONDS);
        //周期性执行
        scheduledExecutorService.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS);
    }

    /**
     * 源码如下
     *     public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
     *         return new ScheduledThreadPoolExecutor(corePoolSize);
     *     }
     */
}
