/**
 * 不可变对象，实现其他类无法修改这个对象，public也不行
 *
 * @Author: zzStar
 * @Date: 10-11-2020 15:45
 */
public class Person {

    /**
     * final作用：
     * 1.类防止被继承、方法防止被重写、变量防止被修改
     * 2.天生是线程安全的，而不需要额外的同步开销
     */

    //必须初始化,否则构造器或者代码块里赋值
    final int age = 19;

    final String name = "zzStar";

//    {
//        age = 10;
//    }


}
