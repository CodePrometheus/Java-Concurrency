import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示一个Future的使用方法,get的基本用法，lambda形式
 *
 * @Author: zzStar
 * @Date: 10-13-2020 09:46
 */
public class OneFuture {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Callable<Integer> callable = () -> {
            Thread.sleep(2000);
            return new Random().nextInt();
        };
        Future<Integer> future = service.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

/*
    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
*/
}
