package singleton.lazyman;

/**
 * 单例之八 -> 枚举（生产实践推荐使用）
 * -> 单元素的枚举类型已经成为实现Singleton的最佳方法
 * -> 线程安全有保障，避免反序列化破坏单例
 *
 * @Author: zzStar
 * @Date: 10-20-2020 12:28
 */
public enum SingletonEnum {

    INSTANCE;

    public void whatever() {

    }
}
