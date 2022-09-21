package com.star.thread.uncaughtexception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 方案二 -> UncaughtExceptionHandler
 *
 * @Author: zzStar
 * @Date: 10-18-2020 16:33
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String name;

    public MyUncaughtExceptionHandler(String name) {
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.WARNING, "线程异常，终止" + t.getName(), e);
        System.out.println(name + "捕获了异常" + t.getName() + "异常" + e);
    }


}

/*
    public void uncaughtException(Thread t, Throwable e) {
        if (parent != null) {
            // 递归，一直向上找
            parent.uncaughtException(t, e);
        } else {
            // 调用setDefaultUncaughtExceptionHandler方法设置的全局handler进行处理
            Thread.UncaughtExceptionHandler ueh =
                    Thread.getDefaultUncaughtExceptionHandler();
            if (ueh != null) {
                ueh.uncaughtException(t, e);
            } else if (!(e instanceof ThreadDeath)) {
                // 全局handler不存在就输出异常栈
                System.err.print("Exception in thread \""
                        + t.getName() + "\" ");
                e.printStackTrace(System.err);
            }
        }
    }
*/
