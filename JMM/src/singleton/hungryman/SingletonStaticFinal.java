package singleton.hungryman;

/**
 * 单例实现之一 -> 饿汉式（静态常量）
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:20
 */
public class SingletonStaticFinal {

    // 类的加载是由JVM自身保证线程安全
    private final static SingletonStaticFinal INSTANCE = new SingletonStaticFinal();

    public static SingletonStaticFinal getINSTANCE() {
        return INSTANCE;
    }

    private SingletonStaticFinal() {
    }

}
