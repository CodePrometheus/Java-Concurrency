import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 低竞争下，AtomicLong和LongAdder两个类具有相似的特征
 * 但是在竞争激烈的情况下，LongAdder的预期吞吐量要高得多，但要消耗更多的空间
 * <p>
 * LongAdder适合的场景是统计求和计数的场景，空间换时间cell数组
 * 而且LongAdder基本只提供了add方法，而AtomicLong还具有CAS方法
 *
 * @Author: zzStar
 * @Date: 10-10-2020 11:04
 */
public class LongAdderDemo {
    public static void main(String[] args) {
        LongAdder counter = new LongAdder();
        ExecutorService service = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(counter));
        }
        service.shutdown();

        while (!service.isTerminated()) {

        }

        long end = System.currentTimeMillis();
        System.out.println(counter.sum());
        System.out.println("LongAdder耗时: " + (end - start) + " s");
    }

    private static class Task implements Runnable {
        private LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
        }
    }
}

/**
 * public long sum() {
 * //竞争激烈，各个线程分散累加到自己的槽cell[i]中
 * Cell[] cs = cells;
 * //竞争不激烈的时候，直接累加到base变量上
 * long sum = base;
 * if (cs != null) {
 * for (Cell c : cs)
 * if (c != null)
 * sum += c.value;
 * }
 * return sum;
 * }
 */
