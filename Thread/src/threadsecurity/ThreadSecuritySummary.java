package threadsecurity;

/**
 * 线程安全总结
 *
 * @Author: zzStar
 * @Date: 10-18-2020 19:32
 */
public class ThreadSecuritySummary {

    /**
     * 都不需要额外做任何额外的处理（即可以像单线程编程一样），程序也可以正常运行
     * 不会因为多线程而出错  -> 线程安全
     */

    /**
     * 线程安全问题的出现情况
     *   -> 运行结果出错
     *   -> 活跃性问题
     *   -> 对象发布和初始化的时候的安全问题
     */

    /**
     *  逸出 ->
     *      1.方法返回一个private对象
     *      2.还未完成初始化（构造函数没完全执行完毕）就把对象提供给外界
     *          a)在构造函数中未初始化完毕就this赋值
     *          b)隐式逸出--注册监听事件
     *          c)构造函数中运行线程
     */

    /**
     *  ※ 需要考虑线程安全的情况
     *   -> 访问共享的变量或资源，会有并发风险，比如对象的属性、静态变量、共享缓存、数据库等
     *   -> 所有依赖时序的操作，即使每一步操作都是线程安全的，还是存在并发问题：read-modify-write，check-then-act
     *   -> 不同的数据之间存在捆绑关系的时候（如IP和端口号）
     *   -> 我们使用其他类的时候，如果对方没有声明自己是线程安全的（如hashmap）
     */

    /**
     * 多线程带来性能问题的主要原因
     *  -> 调度：上下文切换
     *  -> 协作：内存同步
     */
}
