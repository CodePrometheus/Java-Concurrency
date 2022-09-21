package com.star.thread.create;


import java.util.Random;
import java.util.concurrent.*;

/**
 * 一个使用Callable的具体例子
 *
 * @Author: zzStar
 * @Date: 10-07-2020 19:39
 */
public class UserCallable {
    /**
     * 这里使用调度器
     * 在Java 5之后，并发编程引入了一堆新的启动、调度和管理线程的API。
     * Executor框架便是Java 5中引入的，其内部使用了线程池机制，
     * 它在java.util.concurrent 包下，通过该框架来控制线程的启动、执行和关闭，可以简化并发编程的操作。
     * 因此，在Java 5之后，通过Executor来启动线程比使用Thread的start方法更好，
     * 除了更易管理，效率更好（用线程池实现，节约开销）外，还有关键的一点：
     * 有助于避免this逃逸问题——如果我们在构造器中启动一个线程，因为另一个任务可能会在构造器结束之前开始执行，
     * 此时可能会访问到初始化了一半的对象用Executor在构造器中。
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runner star = new Runner();
        star.setName("star");
        Runner nanduo = new Runner();
        nanduo.setName("鸣人");
        //将这个对象扔到线程池中，线程池自动分配一个线程来运行star这个对象的call方法
        //Future接受线程内部call方法的返回值
        Future<Integer> result1 = executorService.submit(star);
        Future<Integer> result2 = executorService.submit(nanduo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //关闭线程池，释放资源
        executorService.shutdown();
        System.out.println("star跑了" + result1.get() + "m");
        System.out.println("nanduo跑了" + result2.get() + "m");
    }
}

class Runner implements Callable {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        Integer speed = new Random().nextInt(100);
        Integer distinct = 0;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            distinct = i * speed;
            System.out.println(this.name + " 距离distinct = " + distinct + "m" + " 速度是" + speed + "m/s");
        }
        return distinct;
    }
}
