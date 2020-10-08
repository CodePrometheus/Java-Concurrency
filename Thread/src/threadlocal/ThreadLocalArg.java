package threadlocal;

/**
 * 使用ThreadLocal解决参数传递问题
 * 避免参数传递的麻烦
 * <p>
 * 利用ThreadLocal
 * 1.达到线程安全
 * 2.无需加锁，提高执行效率
 * 3.更高效地利用内存，节省开销
 * 4.免去传参地繁琐
 *
 * @Author: zzStar
 * @Date: 10-08-2020 15:26
 */
public class ThreadLocalArg {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("zzStar");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        //2中直接拿即可
        User user = UserContextHolder.holder.get();
        System.out.println("Service2 user = " + user.name);

        UserContextHolder.holder.remove();
        user = new User("Star");
        UserContextHolder.holder.set(user);

        /**set源码
         *     public void set(T value) {
         *         Thread t = Thread.currentThread();
         *         ThreadLocalMap map = getMap(t);
         *         if (map != null) {
         *             map.set(this, value);
         *         } else {
         *             createMap(t, value);
         *         }
         *     }
         *
         * remove源码
         *          public void remove() {
         *          ThreadLocalMap m = getMap(Thread.currentThread());
         *          if (m != null) {
         *              m.remove(this);
         *          }
         *      }
         */

//        User user = UserContextHolder.holder.get();
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3 user = " + user.name);

        //避免内存泄漏
        UserContextHolder.holder.remove();
    }

    /**get源码
     *     public T get() {
     *         Thread t = Thread.currentThread();
     *         //取出当前线程的ThreadLocalMap
     *         //注意这个map以及map中的key和value都是保存在线程中的，而不是保存在ThreadLocal中
     *         ThreadLocalMap map = getMap(t);
     *         if (map != null) {
     *             ThreadLocalMap.Entry e = map.getEntry(this);
     *             if (e != null) {
     *                 @SuppressWarnings("unchecked")
     *                 T result = (T)e.value;
     *                 return result;
     *             }
     *         }
     *         return setInitialValue();
     *     }
     */
}

/**
 * 注意和第一个例子initialValue区别
 * initialValue中，对象初始化时机可以由我们控制
 * 而set中，需要保存到ThreadLocal里的对象的生成时机不由我们随意控制，由拦截器控制
 * 故用set放入ThreadLocal，以便后续使用
 */
class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}


class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}