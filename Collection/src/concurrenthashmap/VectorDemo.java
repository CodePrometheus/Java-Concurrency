package concurrenthashmap;

import java.util.Vector;

/**
 * Vector源码
 * 大量的synchronized导致性能缺失
 *
 * @Author: zzStar
 * @Date: 10-11-2020 20:05
 */
public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("Vector");
        System.out.println(vector.get(0));
    }
}

//下面是源码
/*
    public synchronized E get(int index) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
    }
*/
