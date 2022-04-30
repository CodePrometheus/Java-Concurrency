import java.util.concurrent.SynchronousQueue;

/**
 * @Author: Starry
 * @Date: 04-30-2022 19:55
 */
public class SynchronousQueue_1 {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Thread put = new Thread(() -> {
            System.out.println("put start -> ");
            try {
                queue.put(1);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("put stop -> ");
        });

        Thread take = new Thread(() -> {
            System.out.println("take start -> ");
            try {
                System.out.println("take from putThread -> " + queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("take stop -> ");
        });

        put.start();
        Thread.sleep(1000);
        take.start();
    }

}
