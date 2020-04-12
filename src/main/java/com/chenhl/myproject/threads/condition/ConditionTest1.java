package com.chenhl.myproject.threads.condition;

public class ConditionTest1 {

    public static void main(String[] args) {
        Account acct = new Account("123456", 0);

        new DrawThread("取钱者A", acct, 100).start();
        new DepositThread("存钱者B", acct, 100).start();
    }

}
