package queue;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ArrayBlockingQueue
 *
 * @Author: zzStar
 * @Date: 10-12-2020 12:21
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        Interviewer interviewer = new Interviewer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(interviewer).start();
        new Thread(consumer).start();
    }
}

class Interviewer implements Runnable {
    BlockingQueue<String> queue;

    public Interviewer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("10个人来了");
        for (int i = 0; i < 10; i++) {
            String candidate = "candidate" + i;
            try {
                queue.put(candidate);
                System.out.println("安排好了" + candidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //结束信号
        try {
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    BlockingQueue<String> queue;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg;
        try {
            while (!(msg = queue.take()).equals("stop")) {
                System.out.println(msg + "到了");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
    public void put(E e) throws InterruptedException {
        Objects.requireNonNull(e);
        final ReentrantLock lock = this.lock;
        //可以被中断
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            //未满则入队
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }
*/
