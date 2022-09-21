package com.star.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * LongAccumulator
 * 适合于需要大量计算并且是并行计算的场景，利用多核同时进行计算
 * 适合于计算的顺序不能成为瓶颈的场景，也即对顺序没有要求
 *
 * @Author: zzStar
 * @Date: 10-10-2020 11:24
 */
public class AccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> 2 * x * y, 1);
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        IntStream.range(1, 10).forEach(i -> executorService.submit(() -> accumulator.accumulate(i)));
//        accumulator.accumulate(2);
//        accumulator.accumulate(2);

        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println(accumulator.getThenReset());
    }
}
