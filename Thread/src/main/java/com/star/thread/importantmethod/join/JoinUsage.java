package com.star.thread.importantmethod.join;

/**
 * 新的线程加入了，要等待它执行完再出发
 * 注意 ！ join的底层 native方法  线程执行后自动notifyAll()
 *
 * @Author: zzStar
 * @Date: 10-18-2020 12:11
 */
public class JoinUsage {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });

        thread1.start();
        thread2.start();
        System.out.println("开始等待子线程运行完毕");
        //使得主线程main等待其执行完毕
        thread1.join();
        thread2.join();
        System.out.println("所有子线程全部执行完毕");
    }
}
