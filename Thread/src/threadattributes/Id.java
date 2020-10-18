package threadattributes;

/**
 * 注意 ❗ Id 是从 1 开始，JVM运行起来之后，自己创建的线程ID早已不是2
 * 特殊的一点
 *
 * @Author: zzStar
 * @Date: 10-18-2020 13:34
 */
public class Id {
    public static void main(String[] args) {
        Thread thread = new Thread();

        System.out.println("主线程的ID" + Thread.currentThread().getId());//1
        System.out.println("子线程的ID" + thread.getId());//14
    }
}

/*原因如下源码 ->  先++再返回
    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }
*/

/* 而这是线程名字的源码实现 -> 从0开始
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }
*/


