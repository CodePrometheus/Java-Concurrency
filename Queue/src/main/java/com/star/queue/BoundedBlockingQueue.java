package com.star.queue;

import java.util.LinkedList;

public class BoundedBlockingQueue {

    private int capacity;
    private LinkedList<Integer> list;
    private Object obj = new Object();


    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        list = new LinkedList<>();
    }

    public void enqueue(int element) throws InterruptedException {
        synchronized (obj) {
            while (size() >= capacity) {
                obj.wait();
            }
            list.add(element);
            obj.notifyAll();
        }
    }

    public int dequeue() throws InterruptedException {
        int res;
        synchronized (obj) {
            while (size() <= 0) {
                obj.wait();
            }
            res = list.removeFirst();
            obj.notifyAll();
        }
        return res;
    }

    public int size() {
        return list.size();
    }

}
