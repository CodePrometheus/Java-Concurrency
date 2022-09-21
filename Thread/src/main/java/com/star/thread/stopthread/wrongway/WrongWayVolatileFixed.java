package com.star.thread.stopthread.wrongway;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用中断来修复无尽等待的问题 part3
 *
 * @Author: zzStar
 * @Date: 10-15-2020 17:24
 */
public class WrongWayVolatileFixed {
    public static void main(String[] args) throws InterruptedException {

        //非静态内部类要实例化，外部类先要实例化
        WrongWayVolatileFixed wrongWayVolatileFixed = new WrongWayVolatileFixed();

        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        Producer producer = wrongWayVolatileFixed.new Producer(blockingQueue);
        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(1000);

        Consumer consumer = wrongWayVolatileFixed.new Consumer(blockingQueue);
        //需要的时候消费
        while (consumer.needMoreNums()) {
            System.out.println(consumer.blockingQueue.take() + "被消费了");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多num了");

        //RightWay
        thread.interrupt();
        System.out.println(producer.canceled);

/*正确结束
        消费者不需要更多num了
        false
        生产者停止运行
        java.lang.InterruptedException
*/

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
                while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
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
}
