package copyonwrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 演示CopyOnWriteArrayList可以在迭代过程中修改数组内容，但是ArrayList不行
 *
 * @Author: zzStar
 * @Date: 10-12-2020 10:55
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
//        ArrayList<String> list = new ArrayList<>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println("list = " + list);
            String next = iterator.next();
            System.out.println("next = " + next);

            if (next.equals("2")) {
                list.remove("4");
            }

            if (next.equals("3")) {
                list.add("3 found");
            }
        }
//        list = [1, 2, 3, 3 found]
//        next = 4


        /**
         * CopyOnWriteArrayList的实现原理
         *      因为适用场景对数据的实时性要求并不高
         *      创建新副本，读写分离，读和写使用完全不同的容器
         *      不可变原理
         *      迭代的时候使用的是旧数组，迭代器拿到的数据取决于生成的时候，而不取决于迭代时间
         */
    }
}
