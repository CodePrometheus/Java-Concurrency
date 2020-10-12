package concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 组合操作并不保证ConcurrentHashMap的线程安全
 *
 * @Author: zzStar
 * @Date: 10-12-2020 10:08
 */
public class OptionNotSafe implements Runnable {
    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<String, Integer>();

    public static void main(String[] args) throws InterruptedException {
        scores.put("zzStar", 0);
        Thread thread1 = new Thread(new OptionNotSafe());
        Thread thread2 = new Thread(new OptionNotSafe());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(scores);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
/*
            //同步的类锁实现线程安全
            synchronized (concurrenthashmap.OptionNotSafe.class) {
                Integer score = scores.get("zzStar");
                int newScore = score + 1;
                scores.put("zzStar", newScore);
            }
*/
            while (true) {
                Integer score = scores.get("zzStar");
                int newScore = score + 1;
                //运用replace先检查值是否为原值，是则替换，线程安全
                boolean replace = scores.replace("zzStar", score, newScore);
                if (replace) {
                    break;
                }
            }

        }
    }
}
