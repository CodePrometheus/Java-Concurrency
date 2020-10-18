package importantmethod.waitnotify;

/**
 * 证明wait只释放当前的那把锁
 *
 * @Author: zzStar
 * @Date: 10-17-2020 14:44
 */
public class WaitNotifyReleaseOwnMonitor {
    private static volatile Object resource1 = new Object();
    private static volatile Object resource2 = new Object();

    public static void main(String[] args) {
        var thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread1 got resource1 lock");
                synchronized (resource2) {
                    System.out.println("Thread1 got resource2 lock");
                    try {
                        //把1释放
                        System.out.println("Thread1 releases resource1 lock");
                        resource1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (resource1) {
                System.out.println("Thread2 got resource1 lock");
                System.out.println("Thread2 tries to resource2 lock");
                synchronized (resource2) {
                    System.out.println("Thread2 got resource2 lock");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

}
