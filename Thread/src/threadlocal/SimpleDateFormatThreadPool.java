package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池打印日期
 * 可以发现出现重复的时间
 * 所有线程都共用一个SimpleDateFormat对象，线程不安全
 * 所以必须加锁
 *
 * @Author: zzStar
 * @Date: 10-08-2020 14:35
 */
public class SimpleDateFormatThreadPool {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);
    //避免重复创建对象浪费资源
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.submit(new Thread(() -> {
                String date = new SimpleDateFormatThreadPool().date(finalI);
                System.out.println("date = " + date);
            }));
        }
        executorService.shutdown();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        String format = null;
        //类锁
        synchronized (SimpleDateFormatThreadPool.class) {
            format = dateFormat.format(date);
        }
        return format;
    }
}
