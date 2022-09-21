package com.star.thread.threadsecurity;

/**
 * 初始化未完毕，就this赋值
 *
 * @Author: zzStar
 * @Date: 10-19-2020 12:02
 */
public class ThisEscape {
    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
        // 受时间影响
        Thread.sleep(10);
        //        Thread.sleep(105);
        if (point != null) {
            System.out.println(point);
        }
    }
}


class Point {

    private final int x, y;

    public Point(int x, int y) throws InterruptedException {
        this.x = x;
        ThisEscape.point = this;
        Thread.sleep(100);
        // 这里x，y有先后
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class PointMaker extends Thread {
    @Override
    public void run() {
        try {
            new Point(1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
