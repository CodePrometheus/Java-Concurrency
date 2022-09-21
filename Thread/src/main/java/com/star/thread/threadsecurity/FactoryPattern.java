package com.star.thread.threadsecurity;

/**
 * 工厂模式修复初始化问题
 *
 * @Author: zzStar
 * @Date: 10-19-2020 13:01
 */
public class FactoryPattern {

    int count;
    private EventListener listener;

    // 保护起来
    private FactoryPattern(MySource mySource) {

        listener = e -> System.out.println("得到的数字是" + count);

        for (int i = 0; i < 1000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    // 工厂方法
    public static FactoryPattern getInstance(MySource mySource) {
        FactoryPattern factoryPattern = new FactoryPattern(mySource);
        // 完成所有准备工作再注册
        mySource.registerListener(factoryPattern.listener);
        return factoryPattern;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();

        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mySource.eventCome(new Event() {
            });
        }).start();

        // 初始化
        FactoryPattern factoryPattern = new FactoryPattern(mySource);
    }

    static class MySource {

        // 注册监听器
        private EventListener listener;

        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }

        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {

    }

}
