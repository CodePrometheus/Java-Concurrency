package uncaughtexception;

/**
 * @Author: zzStar
 * @Date: 10-18-2020 16:45
 */
public class UserOwnUncaughtExceptionHandler implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("MyHandler"));

        new Thread(new UserOwnUncaughtExceptionHandler(), "th1").start();
        Thread.sleep(300);
        new Thread(new UserOwnUncaughtExceptionHandler(), "th2").start();
        Thread.sleep(300);
        new Thread(new UserOwnUncaughtExceptionHandler(), "th3").start();
        Thread.sleep(300);
        new Thread(new UserOwnUncaughtExceptionHandler(), "th4").start();
        Thread.sleep(300);

        System.out.println("Caught Exception");


    }


    @Override
    public void run() {
        throw new RuntimeException();
    }
}
