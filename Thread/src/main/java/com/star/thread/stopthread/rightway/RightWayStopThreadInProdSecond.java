package com.star.thread.stopthread.rightway;

/**
 * @Description: 在catch子语句中调用Thread.currentThread().interrupt()来恢复设置中断状态，以便在后续的执行中
 * 依然能够检查到刚才发生了中断
 * @Author: zzStar
 * @Date: 2020/10/14 17:47
 */
public class RightWayStopThreadInProdSecond implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted,程序运行结束🔚");
                break;
            }
            reInterrupt();
        }
    }

    private void reInterrupt() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //没有这个调用run方法里不会打印出，无法感知中断
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProdSecond());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
