package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 两个线程打印日期
 *
 * @Author: zzStar
 * @Date: 10-08-2020 14:35
 */
public class SimpleDateFormatSingle {

    public static void main(String[] args) {
        new Thread(() -> {
            String date = new SimpleDateFormatSingle().date(10);
            System.out.println("date = " + date);
        }).start();
        new Thread(() -> {
            String date = new SimpleDateFormatSingle().date(108707);
            System.out.println("date = " + date);
        }).start();
    }

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
