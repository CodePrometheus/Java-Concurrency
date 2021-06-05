package singleton.lazyman;

/**
 * 单例之六 -> 双重检查（面试推荐使用）
 * -> 线程安全，延迟加载，效率较高
 *
 * @Author: zzStar
 * @Date: 10-20-2020 10:47
 */
public class SingletonDoubleCheck {

    // 新建对象中的三个步骤，重排序会带来的NPE，防止重排序
    private volatile static SingletonDoubleCheck instance;

    private SingletonDoubleCheck() {
    }

    public static SingletonDoubleCheck getInstance() {

        if (instance == null) {
            synchronized (SingletonDoubleCheck.class) {
                // 第二个线程进来做一个判断检查，依然是空，才创建实例
                if (instance == null) {
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }

}
