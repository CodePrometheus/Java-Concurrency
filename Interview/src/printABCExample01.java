import java.util.concurrent.*;

/**
 * @Author: zzStar
 * @Date: 2021/9/9
 * @Description: 交叉打印ABC，Synchronized + wait/notify
 */
public class printABCExample01 {

    private static int counter = 0;

    private static final Object OBJ = new Object();

    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor(
                3,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        executorService.execute(() -> {
            int cnt = 0;
            while (cnt < 10) {
                synchronized (OBJ) {
                    if (counter % 3 != 0) {
                        try {
                            OBJ.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("A");
                        cnt++;
                        counter++;
                    }
                    OBJ.notifyAll();
                }
            }
        });

        executorService.execute(() -> {
            int cnt = 0;
            while (cnt < 10) {
                synchronized (OBJ) {
                    if (counter % 3 != 1) {
                        try {
                            OBJ.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("B");
                        cnt++;
                        counter++;
                    }
                    OBJ.notifyAll();
                }
            }
        });

        executorService.execute(() -> {
            int cnt = 0;
            while (cnt < 10) {
                synchronized (OBJ) {
                    if (counter % 3 != 2) {
                        try {
                            OBJ.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("C");
                        cnt++;
                        counter++;
                    }
                    OBJ.notifyAll();
                }
            }
        });

        executorService.shutdown();
    }
}
