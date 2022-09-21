package com.star.thread.importantmethod.join;

/**
 * join线程状态
 *
 * @Author: zzStar
 * @Date: 10-18-2020 12:48
 */
public class JoinThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(mainThread.getState());//WAITING
                System.out.println("Thread-0运行结束🔚");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("等待子线程运行完毕");
        thread.join();// 主线程等待
        System.out.println("子线程运行完毕");
    }
}
