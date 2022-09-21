package com.star.future.future;

import java.util.concurrent.*;

/**
 * 抛出异常的时机
 * <p>
 * 注意，无论自定义什么异常，get抛出的都是ExecutionException异常
 * 且只有执行到get方法是，才会抛出异常
 *
 * @Author: zzStar
 * @Date: 10-13-2020 10:12
 */
public class GetException {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.isDone());
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException");
        }
        service.shutdown();
    }

    private static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            throw new IllegalArgumentException("callable抛出异常");
        }
    }
}
