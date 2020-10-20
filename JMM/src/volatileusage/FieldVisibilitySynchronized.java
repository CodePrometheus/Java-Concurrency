package volatileusage;

/**
 * @Description:演示synchronized近朱者赤
 * @Author: zzStar
 * @Date: 2020/10/20 09:48
 */
public class FieldVisibilitySynchronized {

    int a = 1;
    int b = 2;
    int c = 2;
    int d = 2;

    private void change() {
        a = 3;
        b = 4;
        c = 5;

        synchronized (this) {
            d = 6;
        }
    }

    // 加上这两个就实现了可见性 + 原子性
    private void print() {

        synchronized (this) {
            int aa = a;
        }
        int bb = b;
        int cc = c;
        int dd = d;

        System.out.println("b = " + b + ",a = " + a);
    }


    public static void main(String[] args) {

        while (true) {

            FieldVisibilitySynchronized fieldVisibility = new FieldVisibilitySynchronized();
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


}
