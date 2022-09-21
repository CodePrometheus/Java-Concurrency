package com.star.thread.threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 并行的线程池，线程池不会保证任务的顺序执行，抢占式的工作
 * newWorkStealingPool适合使用在很耗时的操作，它是新的线程池类ForkJoinPool的扩展，
 * 但是都是在统一的一个Executors类中实现，由于能够合理的使用CPU进行对任务操作（并行操作）
 * 所以适合使用在很耗时的任务中
 *
 * @Author: zzStar
 * @Date: 10-08-2020 14:18
 */
public class WorkStealingPool {
    public static void main(String[] args) {

        ExecutorService service = Executors.newWorkStealingPool();

        // CPU 核数  workStealingPool 会自动启动cpu核数个线程去执行任务
        System.out.println(Runtime.getRuntime().availableProcessors());

        //cpu核数为12 启动13个线程,其中第一个是1s执行完毕,其余都是2s执行完毕,
        //有一个任务会进行等待,当第一个执行完毕后,会再次偷取第十三个任务执行
        service.execute(new R(1));
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            service.execute(new R(2));
        }


        try {
            //由于产生的是deamon精灵线程（守护线程、后台线程），主程序不阻塞的话看不到打印信息
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class R implements Runnable {

        int time;

        R(int runTime) {
            this.time = runTime;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
