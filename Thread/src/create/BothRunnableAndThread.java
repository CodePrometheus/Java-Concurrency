package create;

/**
 * 同时使用runnable和Thread两种方式实现多线程
 *
 * @Author: zzStar
 * @Date: 10-08-2020 10:10
 */
public class BothRunnableAndThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("来自Runnable的方法")) {

            //这里的run方法已经把父类的run方法覆盖
            @Override
            public void run() {
                System.out.println("来自Thread的方法");
            }
        };
        thread.start();//来自Thread的方法
    }
}
