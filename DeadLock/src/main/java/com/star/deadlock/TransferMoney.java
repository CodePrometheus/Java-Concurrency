package com.star.deadlock;

/**
 * 转账时遇到死锁
 * 利用对象的哈希值来决定获取锁的顺序
 * 类似的，实际中主键也起到哈希值的作用
 *
 * @Author: zzStar
 * @Date: 10-20-2020 15:24
 */
public class TransferMoney implements Runnable {

    private int flag;
    static Account a = new Account(500);
    static Account b = new Account(500);
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        TransferMoney transferMoney1 = new TransferMoney();
        TransferMoney transferMoney2 = new TransferMoney();
        transferMoney1.flag = 1;
        transferMoney2.flag = 0;
        Thread thread1 = new Thread(transferMoney1);
        Thread thread2 = new Thread(transferMoney2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("a.balance = " + a.balance);
        System.out.println("b.balance = " + b.balance);
    }

    static class Account {
        int balance;

        public Account(int balance) {
            this.balance = balance;
        }
    }


    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        }

        if (flag == 0) {
            transferMoney(b, a, 200);
        }
    }

    public static void transferMoney(Account from, Account to, int amount) {

        class Helper {
            public void transfer() {
                if (from.balance - amount < 0) {
                    System.out.println("dont enough");
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println("成功转账" + amount + "元");
            }
        }

        // 获取对象的哈希值
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            // 避免哈希冲突的情况，冲突需要加时赛
            synchronized (lock) {
                synchronized (to) {
                    synchronized (from) {
                        new Helper().transfer();
                    }
                }
            }
        }
/*
        synchronized (from) {
            // 加上则陷入死锁情况
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 卡在此处
            synchronized (to) {
            }
        }
    }
*/
    }
}

