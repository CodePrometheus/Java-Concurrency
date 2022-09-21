package com.star.collection.concurrenthashmap;

/**
 * jdk1.8后，结构和1.8后的hashmap类似
 * <p>
 * 1.7是16个Segment，每个Segment里面是类似hashmap的结构
 * 1.8是链表+红黑树
 * <p>
 * ConcurrentHashMap源码浅析
 *
 * @Author: zzStar
 * @Date: 10-11-2020 20:22
 */
public class ConcurrentHashMapAnalysis {
}

/*
    public V get(Object key) {
        ConcurrentHashMap.Node<K,V>[] tab; ConcurrentHashMap.Node<K,V> e, p; int n, eh; K ek;
        //得到hash值
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (e = tabAt(tab, (n - 1) & h)) != null) {
            if ((eh = e.hash) == h) {
            //槽点和key符合则直接返回value
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            //负数说明，得到的是一个红黑树结点或者为转移结点，调用find方法取值
            else if (eh < 0)
                return (p = e.find(h, key)) != null ? p.val : null;
            //若均不是，则遍历链表取值
            while ((e = e.next) != null) {
                if (e.hash == h &&
                        ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }
*/


/*
    final V putVal(K key, V value, boolean onlyIfAbsent) {
        //判断是否为空，不允许为空
        if (key == null || value == null) throw new NullPointerException();
        //计算hash值
        int hash = spread(key.hashCode());
        int binCount = 0;
        //根据对应位置节点的类型来赋值，或者helpTransfer，或者增长链表，或者给红黑树增加节点
        for (ConcurrentHashMap.Node<K,V>[] tab = table;;) {
            ConcurrentHashMap.Node<K,V> f; int n, i, fh; K fk; V fv;
            if (tab == null || (n = tab.length) == 0)
                //初始化tab
                tab = initTable();
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                //cas操作，直接放进去
                if (casTabAt(tab, i, null, new ConcurrentHashMap.Node<K,V>(hash, key, value)))
                    break;                   // no lock when adding to empty bin
            }
            //MOVED转移结点，说明槽点正在扩容，帮助进行
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else if (onlyIfAbsent // check first node without acquiring lock
                    && fh == hash
                    && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                    && (fv = f.val) != null)
                return fv;
            else {
                V oldVal = null;
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            binCount = 1;
                            for (ConcurrentHashMap.Node<K,V> e = f;; ++binCount) {
                                K ek;
                                //判断当前是否存在这个key
                                if (e.hash == hash &&
                                        ((ek = e.key) == key ||
                                                (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                //不存在就新创建一个结点
                                ConcurrentHashMap.Node<K,V> pred = e;
                                if ((e = e.next) == null) {
                                    pred.next = new ConcurrentHashMap.Node<K,V>(hash, key, value);
                                    break;
                                }
                            }
                        }
                        //红黑树
                        else if (f instanceof ConcurrentHashMap.TreeBin) {
                            ConcurrentHashMap.Node<K,V> p;
                            binCount = 2;
                            if ((p = ((ConcurrentHashMap.TreeBin<K,V>)f).putTreeVal(hash, key,
                                    value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                        else if (f instanceof ConcurrentHashMap.ReservationNode)
                            throw new IllegalStateException("Recursive update");
                    }
                }
                //完成添加后，判断是否需要将链表转为树
                if (binCount != 0) {
                    //大于阈值8，则转为树
                    if (binCount >= TREEIFY_THRESHOLD)
                        //treeifyBin还要满足最低容量条件64
                        treeifyBin(tab, i);
                    if (oldVal != null)
                        return oldVal;
                    break;
                }
            }
        }
        addCount(1L, binCount);
        return null;
    }
*/

/**
 * 为什么超过8要转为红黑树？
 * 1.默认是链表，红黑树每个节点占用的空间是链表的2倍
 * 2.经过泊松分布，hash冲突到达8的时候概率小于千万分之1 (0.00000006)，正常情况下，链表的长度不会达到8，但为保证极端情况下，仍有较高的查询效率，转为红黑树
 */
