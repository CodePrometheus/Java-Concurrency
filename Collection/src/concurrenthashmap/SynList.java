package concurrenthashmap;

import java.util.*;
import java.util.ArrayList;

/**
 * Collections.synchronizedList同样性能不高
 *
 * @Author: zzStar
 * @Date: 10-11-2020 20:16
 */
public class SynList {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        list.add(5);
        System.out.println(list.get(0));
    }
}

/*
    public static <T> List<T> synchronizedList(List<T> list) {
        return (list instanceof RandomAccess ?
                new Collections.SynchronizedRandomAccessList<>(list) :
                new Collections.SynchronizedList<>(list));
    }
*/

/*
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
*/

/*
    public E get(int index) {
        synchronized (mutex) {return list.get(index);}
    }
    public E set(int index, E element) {
        synchronized (mutex) {return list.set(index, element);}
    }
    public void add(int index, E element) {
        synchronized (mutex) {list.add(index, element);}
    }
    public E remove(int index) {
        synchronized (mutex) {return list.remove(index);}
    }
*/

