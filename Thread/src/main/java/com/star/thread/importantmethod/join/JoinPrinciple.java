package com.star.thread.importantmethod.join;

/**
 * 通过join原理，分析出join的代替写法
 *
 * @Author: zzStar
 * @Date: 10-18-2020 13:03
 */
public class JoinPrinciple {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });

        thread1.start();
        System.out.println("开始等待子线程运行完毕");
//        thread1.join();

        // 等价代码
        synchronized (thread1) {
            // 直到thread1执行完毕才继续
            thread1.wait();
        }
        System.out.println("所有子线程全部执行完毕");
    }

}
