/**
 * 栈封闭：在方法里新建的局部变量，实际上存储在每个线程私有的栈空间，而每个栈的栈空间是不能被其他线程所访问的，所有无线程安全问题
 *
 * 演示栈封闭的两种特殊情况，基本变量和对象
 *
 * @Author: zzStar
 * @Date: 10-11-2020 18:19
 */
public class StackConfinement implements Runnable {

    //被两个线程所共享，不具备栈封闭的能力
    int index = 0;

    public void inThread() {
        int neverGoOut = 0;
        for (int i = 0; i < 10000; i++) {
            neverGoOut++;
        }
        System.out.println("栈内保护的数字是线程安全的:" + neverGoOut);
    }


    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement confinement = new StackConfinement();
        Thread thread1 = new Thread(confinement);
        Thread thread2 = new Thread(confinement);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(confinement.index);
    }
}
