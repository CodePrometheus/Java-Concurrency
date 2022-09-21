package com.star.thread.stopthread.wrongway;

/**
 * 错误地停止线程stop()，会导致线程运行一半突然停止，没办法完成一个基本单位地操作
 * 造成脏数据
 * <p>
 * 注意！
 * 因为stop本质上是不安全的，停止线程会导致它解锁！已锁定的所有监视器，
 * 如果先前受这些监视器保护的任何对象处于不一致状态，则其他线程现在可以以不一致的状态查看这些对象
 *
 * @Author: zzStar
 * @Date: 10-15-2020 16:35
 */
public class StopThread implements Runnable {
    @Override
    public void run() {
        //5个联队
        for (int i = 0; i < 5; i++) {
            System.out.println("连队" + i + "领取武器");
            //每个连队10个人
            for (int j = 0; j < 10; j++) {
                System.out.println(j);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队" + i + "已经领取完毕");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new StopThread());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        thread.stop();
        //suspend会让一个线程挂起，在恢复之前锁不会释放，也就是带着锁休息，易造成死锁
        thread.suspend();
        thread.resume();
*/
    }
}
