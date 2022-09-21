package com.star.future.future;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示批量提交任务时，用list来批量接收结果
 *
 * @Author: zzStar
 * @Date: 10-13-2020 09:57
 */
public class MultiFutures {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ArrayList<Future> futures = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Future<Integer> future = service.submit(new CallableTask());
            //添加到数组中
            futures.add(future);
        }

        //获取结果,将会每两个打印一次
        for (int i = 0; i < 20; i++) {
            Future future = futures.get(i);
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }

    private static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws InterruptedException {
            Thread.sleep(2000);
            return new Random().nextInt();
        }
    }
}
