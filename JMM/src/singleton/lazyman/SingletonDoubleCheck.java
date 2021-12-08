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
    // NPE: 由于重排序，可能会有线程获取到成员未初始化好的对象
    // new不是JVM原子指令，初始化与变量的引用绑定存储地址可能乱序，导致并发下会获取未初始化实例
    private volatile static SingletonDoubleCheck instance;

    private SingletonDoubleCheck() {
    }

    public static SingletonDoubleCheck getInstance() {

        if (instance == null) {
            // 锁instance本身会出现空指针
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
