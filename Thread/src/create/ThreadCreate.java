package create;

/**
 * 线程创建的方式
 *
 * @Author: zzStar
 * @Date: 2020-10-07 18:44
 */
public class ThreadCreate {
    public static void main(String[] args) {

        //创建Runnable实现类的实例，并用这个实例作为Thread的target来创建Thread对象，
        //这个Thread对象才是真正的线程对象
        //最终调用target.run()
        new Thread(new MyThread1(), "thread 1").start();

        //继承所实现的直接start
        //run()整个都被重写
        new MyThread2("thread 2").start();
    }
}

/**
 * 通过实现Runnable接口来创建Thread线程,重写run()方法
 * 优先选择
 */
class MyThread1 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "-->run...");
    }
}

/**
 * 通过继承Thread类来创建一个线程
 * 这种创建线程的方法不够好，主要是因为其涉及运行机制问题，影响程序性能
 */
class MyThread2 extends Thread {

    //调用super（name）后，getName才会返回你创建新实例时传入的name
    MyThread2(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "-->run...");
    }
}

/*
    //run源码
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
*/

