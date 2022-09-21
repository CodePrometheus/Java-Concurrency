package com.star.immutable;

/**
 * 测试final能否被修改
 *
 * @Author: zzStar
 * @Date: 10-11-2020 15:46
 */
public class TestFinal {
    public static void main(String[] args) {
        Person person = new Person();
//        person.age = 19;
        int age = person.age;
        System.out.println("age = " + age);
    }
}
