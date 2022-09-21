package com.star.jmm.volatileusage;

/**
 * 演示可见性带来的问题（local cache 和 shared cache 的同步问题）
 * 第二个线程可能看不到前一个线程的操作，出现b=3,a=1
 * <p>
 * 普通三种情况
 * a=3,b=2
 * a=1,b=2
 * a=3,b=2
 *
 * @Author: zzStar
 * @Date: 10-19-2020 17:07
 */
public class FieldVisibility {

    // 使之具备happens-before -> 动作A发生在动作B之前，B保证能看见A
    int a = 1;

    // 这里仅仅只加在b上也可以保证，b写入之前的所有操作在读取b的时候都可以看得到
    // "近朱者赤" 实现了轻量级的同步
    // 作为刷新之前变量的触发器
    volatile int b = 2;
    // 不加volatile会出现b=3,a=1
    // int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    public static void main(String[] args) {

        while (true) {

            FieldVisibility fieldVisibility = new FieldVisibility();
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.change();
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.print();
            }).start();

        }

    }

    private void print() {
        System.out.println("b = " + b + ",a = " + a);
    }


}
