import java.util.concurrent.*;

/**
 * FutureTask用法
 *
 * @Author: zzStar
 * @Date: 10-13-2020 10:58
 */
public class FutureTaskUsage {
    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
//        Thread thread = new Thread(futureTask);
//        thread.start();

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(futureTask);
        service.shutdown();

        //拿出结果
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("========");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
