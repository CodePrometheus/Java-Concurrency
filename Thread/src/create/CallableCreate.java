package create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable接口提供了一个call（）方法作为线程执行体，call()方法比run()方法功能要强大
 * call()方法可以有返回值
 * call()方法可以声明抛出异常
 *
 * @Author: zzStar
 * @Date: 10-07-2020 19:13
 */
public class CallableCreate {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         *Future是一个接口,代表了一个异步计算的结果,接口中的方法用来检查计算是否完成,等待完成和得到计算结果;
         * 　　当计算完成后,只能通过get()方法得到结果,get()方法会阻塞,一直到线程的计算结果完成并返回;
         * FutureTask类实现了RunnableFuture接口,而RunnableFuture接口是继承了Runnable和Future接口,所以说FutureTask是一个提供异步计算结果的任务;
         * 　　FutureTask可以用来包装Callable或者Runnable接口的实现对象,因为FutureTask实现了Runnable接口,所以FutureTask也可以提交给线程池
         */
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("call方法执行了");
                return "call方法返回值";
            }
        });
        futureTask.run();
        System.out.println("获取返回值: " + futureTask.get());

        FutureTask futureTask1 = new FutureTask(new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("call方法执行了1");
                return "call方法返回值1";
            }
        });
        futureTask1.run();
        System.out.println("获取返回值1: " + futureTask1.get());
    }
}
