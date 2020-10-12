package condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示用Condition实现生产者消费者模式
 *
 * 实际上，如果说Lock用来代替synchronized，那么Condition就是用来代替相对应Object.wait/notify,所以在用法和性质上，几乎都一样
 * await方法会自动释放持有的lock锁，和Object.wait一样，不需要自己手动先释放锁
 * 调用await之前，必须持有锁，否则抛出异常
 *
 * @Author: zzStar
 * @Date: 10-12-2020 16:48
 */
public class ConditionDemo2 {
    private int queueSize = 10;
    private PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo2 conditionDemo2 = new ConditionDemo2();
        produce produce = conditionDemo2.new produce();
        Consumer consumer = conditionDemo2.new Consumer();
        produce.start();
        consumer.start();
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                lock.lock();
                try {
                    while (priorityQueue.size() == 0) {
                        System.out.println("队列空，等待数据");
                        try {
                            //不空这个条件满足时唤醒
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //进过while队列不是空的了，拿出数据
                    priorityQueue.poll();
                    //队列出现了空闲，唤醒生产者
                    notFull.signalAll();
                    System.out.println("从队列取走数据" + priorityQueue.size() + "个元素");
                } finally {
                    lock.unlock();
                }
            }
        }


    }

    class produce extends Thread {
        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                lock.lock();
                try {
                    while (priorityQueue.size() == queueSize) {
                        System.out.println("队列已满");
                        try {
                            //等待不满这个条件
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    priorityQueue.offer(1);
                    //唤醒消费者
                    notEmpty.signalAll();
                    System.out.println("队列剩余" + (queueSize - priorityQueue.size()) + "个元素");
                } finally {
                    lock.unlock();
                }
            }
        }


    }

}