package com.star.interview;

import java.util.concurrent.*;

/**
 * @Author: zzStar
 * @Date: 2021/9/9
 * @Description: 交叉打印ABC，Semaphore
 */
public class printABCExample03 {

    private int times = 10;

    // 只有A 初始信号量为1,第一次获取到的只能是A
    private static Semaphore A = new Semaphore(1);

    private static Semaphore B = new Semaphore(0);

    private static Semaphore C = new Semaphore(0);

    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor(
                3,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        printABCExample03 printAbc = new printABCExample03();
        executorService.execute(() -> printAbc.print("A", A, B));
        executorService.execute(() -> printAbc.print("B", B, C));
        executorService.execute(() -> printAbc.print("C", C, A));


        executorService.shutdown();
    }

    private void print(String name, Semaphore current, Semaphore next) {
        for (int i = 0; i < times; i++) {
            try {
                current.acquire();
                System.out.println(name);
                next.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
