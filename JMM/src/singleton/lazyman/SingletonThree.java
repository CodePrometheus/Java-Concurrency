package singleton.lazyman;

/**
 * 单例之三 -> 懒汉式（线程不安全）不可用 -> 也即在需要用的时候才加载进来
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:26
 */
public class SingletonThree {

    private static SingletonThree instance;

    private SingletonThree() {
    }

    public static SingletonThree getInstance() {
        // 多线程下可能会多次创建实例
        if (instance == null) {
            instance = new SingletonThree();
        }
        return instance;
    }
}
