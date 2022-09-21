package com.star.thread.importantmethod.join;

/**
 * join期间被中断
 *
 * @Author: zzStar
 * @Date: 10-18-2020 12:30
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                // 中断主线程
                mainThread.interrupt();
                Thread.sleep(2000);
                System.out.println("Thread1 finished");
            } catch (InterruptedException e) {
//                e.printStackTrace();
                System.out.println("子线程中断");
            }
        });

        thread.start();
        System.out.println("等待子线程运行完毕");
        try {
            // 但主线程在等待子线程
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "主线程被中断了");
//            e.printStackTrace();
            thread.interrupt();
        }
        // 注意，子线程根本就没有运行完毕，仍继续打出Thread1 finished
        System.out.println("子线程执行完毕");
    }
}
