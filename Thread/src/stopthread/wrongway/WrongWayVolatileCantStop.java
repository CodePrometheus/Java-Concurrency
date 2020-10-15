package stopthread.wrongway;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 演示Volatile的局限 -> part2
 * 陷入阻塞时，volatile是无法stop线程的
 * 比如，生产者生产速度 > 消费者消费速度，阻塞队列满了以后，生产者会阻塞，等待消费者进一步消费
 *
 * @Author: zzStar
 * @Date: 10-15-2020 17:01
 */
public class WrongWayVolatileCantStop {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        Producer producer = new Producer(blockingQueue);
        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(1000);

        Consumer consumer = new Consumer(blockingQueue);
        //需要的时候消费
        while (consumer.needMoreNums()) {
            System.out.println(consumer.blockingQueue.take() + "被消费了");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多num了");

        //同时也让生产者停下来，但实际上程序无法正常停止
        producer.canceled = true;
        System.out.println(producer.canceled);
    }

}

class Producer implements Runnable {

    public volatile boolean canceled = false;

    BlockingQueue blockingQueue;

    public Producer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 10000 && !canceled) {
                if (num % 100 == 0) {
                    //将会不停的等在这里，不会走while里的逻辑
                    blockingQueue.put(num);
                    System.out.println(num + "是100的倍数,被放入仓库中");
                }
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行");
        }
    }
}

class Consumer {
    BlockingQueue blockingQueue;

    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public boolean needMoreNums() {
        if (Math.random() > 0.95) {
            return false;
        }
        return true;
    }
}