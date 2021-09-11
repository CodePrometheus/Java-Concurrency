package completable;

import org.junit.Test;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: zzStar
 * @Date: 09-11-2021 11:38
 */
public class CompletableFutureUsage02 {

    public static void sleepSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printMessage(String tag) {
        String res = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag).toString();
        System.out.println(res);
    }

    @Test
    public void supplyAsync() {
        // supplyAsync开启
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printMessage("First");
            sleepSecond(2);
            printMessage("Second");
            sleepSecond(1);
            return "done";
        });
        printMessage("Play");
        printMessage(future.join());
    }

    @Test
    public void thenCombine() {
        // thenCombine合并
        CompletableFuture<String> combine = CompletableFuture.supplyAsync(() -> {
            printMessage("1");
            sleepSecond(1);
            return "1";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            printMessage("2");
            sleepSecond(2);
            return "2";
        }), (a, b) -> {
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            return String.format("%s + %s", a, b);
        });
        printMessage("3");
        printMessage(combine.join());
    }

    @Test
    public void thenApply() {
        // 相当于stream().map()
        CompletableFuture<String> apply = CompletableFuture.supplyAsync(() -> {
            printMessage("over");
            sleepSecond(1);
            return "1";
            // thenApplyAsync会将后面的代码块当作一个独立的任务并且创建新的线程执行
        }).thenApply(res -> {
            printMessage(res + " -> res");
            sleepSecond(2);
            return "2";
        });
        printMessage("3");
        printMessage(apply.join());
    }

    @Test
    public void applyToEither() {
        CompletableFuture<String> apply = CompletableFuture.supplyAsync(() -> {
            printMessage("1");
            sleepSecond(3);
            return "1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            printMessage("2");
            sleepSecond(2);
            return "2";
        }), res -> res);
        printMessage(apply.join());
    }

}
