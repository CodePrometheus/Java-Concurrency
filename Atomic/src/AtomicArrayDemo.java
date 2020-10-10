import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子数组的使用方法
 *
 * @Author: zzStar
 * @Date: 10-10-2020 09:55
 */
public class AtomicArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);

        Decrement decrement = new Decrement(atomicIntegerArray);
        Incrementer incrementer = new Incrementer(atomicIntegerArray);

        Thread[] threadsDecrement = new Thread[20];
        Thread[] threadsIncrementer = new Thread[20];

        for (int i = 0; i < 20; i++) {
            threadsDecrement[i] = new Thread(decrement);
            threadsIncrementer[i] = new Thread(incrementer);
            threadsDecrement[i].start();
            threadsIncrementer[i].start();
        }
//        Thread.sleep(10000);
        for (int i = 0; i < 20; i++) {
            try {
                threadsDecrement[i].join();
                threadsIncrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //判断每一个是否为0
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("发现了错误" + i);
            }
//            System.out.println(atomicIntegerArray.get(i));
        }
        //用原子类的确保证了每个都为0
        System.out.println("over");
    }
}

class Decrement implements Runnable {
    private AtomicIntegerArray array;

    public Decrement(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i);
        }
    }
}

class Incrementer implements Runnable {
    private AtomicIntegerArray array;

    public Incrementer(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}

