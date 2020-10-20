package volatileusage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile适用场景之一
 *
 * @Author: zzStar
 * @Date: 10-20-2020 08:00
 */
public class VolatileApplicableOne implements Runnable {


    volatile boolean done = false;
    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        VolatileApplicableOne volatileApplicableOne = new VolatileApplicableOne();
        Thread thread1 = new Thread(volatileApplicableOne);
        Thread thread2 = new Thread(volatileApplicableOne);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(volatileApplicableOne.done);// <20000
        System.out.println(volatileApplicableOne.realA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            setDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        // 和之前的状态无关
        done = true;
    }

}
