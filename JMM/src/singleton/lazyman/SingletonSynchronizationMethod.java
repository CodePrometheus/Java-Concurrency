package singleton.lazyman;

/**
 * 单例之四 -> 懒汉式（线程安全，同步方法）不推荐使用 -> 效率不高
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:35
 */
public class SingletonSynchronizationMethod {

    private static SingletonSynchronizationMethod instance;

    private SingletonSynchronizationMethod() {
    }

    public synchronized static SingletonSynchronizationMethod getInstance() {
        // 适用于多线程环境
        if (instance == null) {
            instance = new SingletonSynchronizationMethod();
        }
        return instance;
    }

}
