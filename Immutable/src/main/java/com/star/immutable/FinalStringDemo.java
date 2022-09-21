package com.star.immutable;

/**
 * 题目演示
 *
 * @Author: zzStar
 * @Date: 10-11-2020 18:35
 */
public class FinalStringDemo {
    public static void main(String[] args) {

        String a = "zzStar2";

        //加上final修饰之后，编译器会将b当作编译时期的常量来使用，用到时，直接访问常量
        final String b = "zzStar";

        //方法所获得，编译器无法确定final对象的值
        final String f = getName();


        String d = "zzStar";

        //c会直接指向和a一样的地址，不再新建一个对象
        String c = b + 2;

        //e会在运行时确定，在堆上生成zzStar2，而a，c指向的是常量池
        String e = d + 2;

        //g也是在运行时生成的值
        String g = f + 2;

        System.out.println((a == c));//true
        System.out.println((a == e));//false
        System.out.println((a == g));//false
    }

    private static String getName() {
        return "zzStar";
    }
}
