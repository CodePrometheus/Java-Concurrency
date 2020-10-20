package singleton.hungryman;

/**
 * 单例实现之二 -> 饿汉式（静态代码块）
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:24
 */
public class SingletonStaticCodeBlock {

    static {
        INSTANCE = new SingletonStaticCodeBlock();
    }

    private final static SingletonStaticCodeBlock INSTANCE;

    public static SingletonStaticCodeBlock getINSTANCE() {
        return INSTANCE;
    }

    private SingletonStaticCodeBlock() {
    }

}
