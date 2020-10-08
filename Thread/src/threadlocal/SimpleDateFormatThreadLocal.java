package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 利用ThreadLocal给每个线程分配自己的dateFormat对象
 * 也就是是实现了不同线程之间不相互共享
 * 保证了线程安全，高效利用内存，没有synchronized锁带来的性能问题
 *
 * @Author: zzStar
 * @Date: 10-08-2020 15:02
 */
public class SimpleDateFormatThreadLocal {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                String date = new SimpleDateFormatMultiThreading().date(finalI);
                System.out.println("date = " + date);
            }).start();
            Thread.sleep(100);
        }
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        /**
         * 第一次使用get方法访问变量时，将调用initialValue()方法
         * 注意：除非线程先前调用了set方法，在这种情况下，不会为线程调用本initialValue()方法
         * 见传参例子
         *
         * 此外，每个线程最多调用一次initialValue()
         * 但如果调用了remove()后，再调用get()，可再次调用initialValue()
         */
        SimpleDateFormat dateFormat = ThreadSafeFormatter.formatThreadLocal.get();
        return dateFormat.format(date);
    }
}

/**
 * 生产出线程安全的dateFormat对象
 */
class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal() {
        /**
         * initialValue()方法会返回当前线程对应地初始值，这是一个延迟加载地方法
         * 只有在调用get的时候，才会触发
         *
         * @return
         */
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }

        /**
         *     protected T initialValue() {
         *         return null;
         *     }
         */
    };

    //用lambda和上面一致
    public static ThreadLocal<SimpleDateFormat> formatThreadLocal = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
}
