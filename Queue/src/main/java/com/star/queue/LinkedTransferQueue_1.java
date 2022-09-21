package com.star.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @Author: Starry
 * @Date: 04-30-2022 20:21
 */
public class LinkedTransferQueue_1 {

    static LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Produce produce = new Produce(transferQueue);
        Consume consume = new Consume(transferQueue);
        pool.execute(produce);
        pool.execute(consume);
        pool.shutdown();
    }

}

class Produce implements Runnable {

    LinkedTransferQueue<String> transferQueue;

    public Produce(LinkedTransferQueue<String> queue) {
        this.transferQueue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("Producer is waiting to transfer...");
                transferQueue.transfer("A" + i);
                System.out.println("producer transfer element: A" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Consume implements Runnable {

    LinkedTransferQueue<String> transferQueue;

    public Consume(LinkedTransferQueue<String> queue) {
        this.transferQueue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("Consumer is waiting to take element...");
                String s = transferQueue.take();
                System.out.println("Consumer received Element: " + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
