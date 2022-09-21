package com.star.deadlock;

import java.util.Random;

/**
 * 多人同时转账，依然很危险
 *
 * @Author: zzStar
 * @Date: 10-20-2020 15:42
 */
public class MultiTransferMoney {

    private static final int NUM_ACCOUNTS = 200;
    private static final int NUM_MONEY = 1000;
    private static final int NUM_ITERATIONS = 10000;
    private static final int NUM_THREADS = 20;

    public static void main(String[] args) {
        Random random = new Random();
        TransferMoney.Account[] accounts = new TransferMoney.Account[NUM_ACCOUNTS];

        // 初始化数组
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new TransferMoney.Account(NUM_MONEY);
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAccount = random.nextInt(NUM_ACCOUNTS);
                    int toAccount = random.nextInt(NUM_ACCOUNTS);
                    int amount = random.nextInt(NUM_MONEY);
                    TransferMoney.transferMoney(accounts[fromAccount], accounts[toAccount], amount);
                }
                System.out.println("over");
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }
}
