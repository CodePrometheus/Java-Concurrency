package threadattributes;

/**
 * 线程的优先级
 *
 * @Author: zzStar
 * @Date: 10-18-2020 15:48
 */
public class ThreadPriority {
    /**
     * 线程一共10个优先级
     *  -> 默认是5
     *  -> 程序设计不应依赖于优先级，不同操作系统不一样，优先级会被操作系统改变
     *          操作系统对优先级的映射和调度不一样，甚至可能被忽略
     */

    /**
     * The minimum priority that a thread can have.
     */
    public static final int MIN_PRIORITY = 1;

    /**
     * The default priority that is assigned to a thread.
     */
    public static final int NORM_PRIORITY = 5;

    /**
     * The maximum priority that a thread can have.
     */
    public static final int MAX_PRIORITY = 10;

}
