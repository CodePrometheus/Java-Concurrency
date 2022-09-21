package com.star.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {

    /**
     * 将一个list均分成n个list：
     *
     * @param source
     * @param n
     * @return
     */
    public static <T> List<List<T>> avgList(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        //(先计算出余数)
        int remainder = source.size() % n;
        //然后是商
        int number = source.size() / n;
        //偏移量
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 将list分成每份count的数量的list：
     *
     * @param resList
     * @param count
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> resList, int count) {
        if (resList == null || count < 1) {
            return null;
        }

        //long t1 = System.currentTimeMillis();
        List<List<T>> result = new ArrayList<List<T>>();
        int size = resList.size();
        if (size <= count) {
            // 数据量不足count指定的大小
            result.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            // 前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                result.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                result.add(itemList);
            }
        }
        /*long t2 = System.currentTimeMillis();
        System.out.println("splitList====>>> resList.size:" + resList.size()
        + ", count:" +count + ", costs time:" + (t2 - t1) + " ms");*/
        return result;
    }

    public static <T> List<List<T>> splitList2(List<T> list, int count) { //效率低不推荐使用
        long t1 = System.currentTimeMillis();
        int limit = (list.size() + count - 1) / count;
        // 方法一：使用流遍历操作
		/*List<List<T>> result = new ArrayList<>();
		Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
			result.add(list.stream().skip(i * count).limit(count).collect(Collectors.toList()));
		});*/
        //方法二：获取分割后的集合
        List<List<T>> result = Stream.iterate(0, n -> n + 1).limit(limit).parallel().map(a -> list.stream().skip(a * count).limit(count).parallel().collect(Collectors.toList())).collect(Collectors.toList());
        long t2 = System.currentTimeMillis();
        System.out.println("splitList====>>> resList.size:" + list.size()
                + ", count:" + count + ", costs time:" + (t2 - t1) + " ms");
        return result;
    }

    public static void main(String[] args) {
		/*List<Integer> list=new ArrayList<>();
		for (int i = 0; i < 17; i++) {
			list.add(i);
		}
		List<List<Integer>> avgList = avgList(list, 5);
		System.out.println("avgList: " + avgList);

		List<List<Integer>> splitList = splitList(list, 5);
		System.out.println("splitList: " + splitList);

		ArrayList<Integer> arr_list = new ArrayList<>();
		for (int i = 0; i < splitList.size(); i++) {
			List<Integer> subList = splitList.get(i);
			arr_list.addAll(subList);
		}
		System.out.println("arr_list: " + arr_list);*/

    }

}
