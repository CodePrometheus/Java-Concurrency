package com.star.thread.threadsecurity;

/**
 * 观察者模式
 * 利用工厂模式解决问题
 *
 * @Author: zzStar
 * @Date: 10-19-2020 12:23
 */
public class Observer {

    int count;

    public Observer(MySource mySource) {

        // 匿名内部类中持有外部类的引用
        mySource.registerListener((Event e) -> {
            // 我得到的数字是0
            System.out.println("\n我得到的数字是" + count);
        });

        for (int i = 0; i < 1000; i++) {
            System.out.print(i);
        }
        count = 100;
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
        Observer observer = new Observer(mySource);
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
