package threadsecurity;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.运行结果出错
 * 计数不准确，并且找出具体出错的位置
 *
 * @Author: zzStar
 * @Date: 10-18-2020 19:38
 */
public class MultiThreadError implements Runnable {

    int index = 0;

    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();

    // 等待共同出发
    static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    final boolean[] marked = new boolean[30000];

    static MultiThreadError multiThreadError = new MultiThreadError();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(multiThreadError);
        Thread thread2 = new Thread(multiThreadError);
        thread1.start();
        thread2.start();
        // 使得主线程等待两个子线程停止
        thread1.join();
        thread2.join();
        System.out.println("表面上运行的结果" + multiThreadError.index);//<20000
        System.out.println("真正运行的次数" + realIndex.get());
        System.out.println("错误运行的次数" + wrongCount.get());
    }


    @Override
    public void run() {
/*
        while (index < 1000) {
            index++;
        }
*/
        marked[0] = true;

        for (int i = 0; i < 10000; i++) {

            // 两个线程都到await才继续
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            index++;

            // 都++后就才继续
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            // 原子操作下准确的值
            realIndex.incrementAndGet();

            // 这里需要同步，确保不被打断，并且保证可见性
            synchronized (multiThreadError) {

                // 前一位也必须是true
                if (marked[index] && marked[index - 1]) {
                    System.out.println("发生错误已被标记" + index);
                    // 发生的错误数也增加
                    wrongCount.incrementAndGet();
                }

                // 存进marked，标记
                marked[index] = true;
            }
        }
    }


}
