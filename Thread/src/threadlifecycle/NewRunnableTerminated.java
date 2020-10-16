package threadlifecycle;

/**
 * 展示三种状态
 * <p>
 * 即使是正在运行，也是Runnable状态，而不是Running
 *
 * @Author: zzStar
 * @Date: 10-16-2020 19:04
 */
public class NewRunnableTerminated implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new NewRunnableTerminated());
        //NEW
        System.out.println(thread.getState());
        thread.start();
        //RUNNABLE
        System.out.println(thread.getState());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //RUNNABLE!!!
        System.out.println(thread.getState());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TERMINATED
        System.out.println(thread.getState());
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
