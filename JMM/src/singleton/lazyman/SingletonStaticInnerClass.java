package singleton.lazyman;

/**
 * 单例之七 -> 静态内部类的方式（推荐使用）
 *
 * @Author: zzStar
 * @Date: 10-20-2020 12:22
 */
public class SingletonStaticInnerClass {

    private SingletonStaticInnerClass() {
    }

    public static SingletonStaticInnerClass getInstance() {
        return SingletonInstance.INSTANCE;
    }


    private static class SingletonInstance {
        // 里面的实例是不会被初始化的，只当调用时才初始化
        private static final SingletonStaticInnerClass INSTANCE = new SingletonStaticInnerClass();
    }
}
