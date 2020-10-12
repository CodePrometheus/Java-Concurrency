package copyonwrite;

import java.util.Arrays;

/**
 * @Author: zzStar
 * @Date: 10-12-2020 10:40
 */
public class CopyOnWriteArrayListAnalysis {

    /**
     * 代替Vector和SynchronizedList，因为这两者🔒的粒度太大，并发效率相比较低，并且迭代时无法编辑
     */

    /**
     * 适用场景
     *      读操作可以尽可能地快，而写即使慢一些也不要紧 -> 读多写少
     */

    /**
     * 读写规则
     *      在读写锁规则上升级 -> 读取是完全不用加锁的，并且更厉害的是，写入也不会阻塞读取的操作
     *      只有写入和写入之间需要进行同步等待
     */

    /**
     * 缺点
     *      数据一致性问题：CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性，也就是说，写入的数据无法马上读到
     *      内存占用问题：CopyOnWrite写的是复制机制，所有在进行写操作的时候，内存中会同时驻扎两个对象的内存
     */

/*
    public boolean add(E e) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            setArray(es);
            return true;
        }
    }

        //get并未加锁，在任何情况下都可以使用,不会出现阻塞
        public E get(int index) {
        return elementAt(getArray(), index);
    }


        static <E> E elementAt(Object[] a, int index) {
        return (E) a[index];
    }

*/

}
