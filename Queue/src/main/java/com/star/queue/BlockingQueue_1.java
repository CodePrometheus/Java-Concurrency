package com.star.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Starry
 * @Date: 04-30-2022 19:40
 */
public class BlockingQueue_1 {

    public static void main(String[] args) throws InterruptedException {
        // ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(1024);
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
        Thread.sleep(3000);
    }

}

class Producer implements Runnable {

    protected BlockingQueue<Object> queue;

    public Producer(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1>>> " + System.currentTimeMillis());
            Thread.sleep(1000);
            queue.put("2>>> " + System.currentTimeMillis());
            Thread.sleep(5000);
            queue.put("3>>> " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

class Consumer implements Runnable {

    protected BlockingQueue<Object> queue;

    public Consumer(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take() + " " + System.currentTimeMillis());
            System.out.println(queue.take() + " " + System.currentTimeMillis());
            System.out.println(queue.take() + " " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
