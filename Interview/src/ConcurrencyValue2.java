import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrencyValue2 {

    public static void main(String[] args) {
        int count = 10000000;
        List<Long> listKey = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            listKey.add(114560315500000000L + i);
        }
        ConcurrencyValue2 concurrencyTest = new ConcurrencyValue2();
        List<Long> valueList2 = concurrencyTest.getValueList2(listKey);
        System.out.println("====>> getValueList2 valueList.size: " + valueList2.size());
    }


    /**
     * 模拟一千万请求数据，创建一个线程池10个线程控制最大并发数，设置请求的线程数为100，平均处理这一千万请求数据。（用于控制请求线程数的业务场景）
     *
     * @param listKey 请求处理的总数据量
     * @return
     */
    public List<Long> getValueList2(List<Long> listKey) {

        /**
         （1）newCachedThreadPool		创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         （2）newFixedThreadPool 		创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         （3）newScheduledThreadPool	创建一个定长线程池，支持定时及周期性任务执行。
         （4）newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
        // 创建一个线程池
        final ExecutorService executorService = Executors.newFixedThreadPool(10); //用于控制同时并发执行的线程数，使用线程池创建资源提高效率
        List<Long> list_val = Collections.synchronizedList(new ArrayList<>()); //保证多线程操作的是同一个List
        try {
            long t1 = System.currentTimeMillis();
            int threadCount = 100; // 请求的线程数
            List<List<Long>> newList = ListUtils.avgList(listKey, threadCount);
            int runSize = threadCount; // 请求开启的线程数

            /**
             * CountDownLanch 只需要在子线程执行之前, 赋予初始化countDownLanch, 并赋予线程数量为初始值。
             * 每个线程执行完毕的时候, 就countDown一下。主线程只需要调用await方法, 可以等待所有子线程执行结束。
             */
            final CountDownLatch countDownLatch = new CountDownLatch(runSize); // 计数器

            /**
             * Semaphore（信号量）是用来控制同时访问特定资源的线程数量，拿到信号量的线程可以进入，否则就等待。
             * 通过acquire()和release()获取和释放访问许可。
             */
            final Semaphore semaphore = new Semaphore(runSize); // 信号量
            // 循环创建线程
            for (int j = 0; j < runSize; j++) {
                final int i = j;
                executorService.execute(() -> {
                    try {
                        semaphore.acquire();
                        // 执行程序
                        List<Long> subList = newList.get(i);
                        List<Long> sub_ret = getValue(subList);
                        list_val.addAll(sub_ret);
                        System.out.println(Thread.currentThread().getName() + ": 当前线程/总线程: [" + i + "/" + runSize + "]"
                                + ", 处理的数据条数：" + subList.size());
                        semaphore.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 计数器减一
                        countDownLatch.countDown();
                    }
                });
            }

            // 阻塞主线程等待所有线程执行完成
            countDownLatch.await();
            // 所有线程执行完，之后才能执行的部分
            long t2 = System.currentTimeMillis();
            System.out.printf("Call getValueList2 success... ret: {} size, threadCount: {},  costs time: {} ms" +  list_val.size() + runSize + (t2 - t1));
            return list_val;
        } catch (Exception e) {
            return null;
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }

    private List<Long> getValue(List<Long> listKey) {
        //具体操作省略
        return listKey;
    }
}
