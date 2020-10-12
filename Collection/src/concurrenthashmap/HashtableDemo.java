package concurrenthashmap;

import java.util.Hashtable;

/**
 * 同样大量在方法上加上synchronized，并发性能差
 *
 * @Author: zzStar
 * @Date: 10-11-2020 20:10
 */
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("I love Java", "forever");
        String s = hashtable.get("I love Java");
        System.out.println("s = " + s);
    }
}

/*
    public synchronized V get(Object key) {
        Hashtable.Entry<?,?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Hashtable.Entry<?,?> e = tab[index]; e != null ; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                return (V)e.value;
            }
        }
        return null;
    }
*/
