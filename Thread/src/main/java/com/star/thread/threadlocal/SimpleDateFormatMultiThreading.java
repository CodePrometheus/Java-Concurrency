package com.star.thread.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 十个线程打印日期
 *
 * @Author: zzStar
 * @Date: 10-08-2020 14:35
 */
public class SimpleDateFormatMultiThreading {

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
