package com.star.future.completable;

import java.util.concurrent.*;

/**
 * @Author: zzStar
 * @Date: 09-07-2021 22:23
 */
public class CompletableFutureUsage01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>());

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        System.out.println("future.get() = " + future.get());

        System.out.println(future.complete(-11) + "\t" + future.get());
        executor.shutdown();
    }

}
