package com.chenhl.juc.threads.condition;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private final Lock lock = new ReentrantLock();

    private final Condition cond = lock.newCondition();

    private boolean flag = false;

    private String accountNo;

    private double balance;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public Account() {
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void draw(double drawAmount) {
        lock.lock();

        try {
            if (!flag) {
                cond.await();
            } else {
                System.out.println(Thread.currentThread().getName() + " 取钱：" + drawAmount);
                balance -= drawAmount;
                System.out.println("账户余额为：" + balance);
                flag = false;
                cond.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public void deposit(double depositAmount) {
        lock.lock();

        try {
            if (flag) {
                cond.await();
            } else {
                System.out.println(Thread.currentThread().getName() + " 存钱：" + depositAmount);
                balance += depositAmount;
                System.out.println("账户余额为：" + balance);
                flag = true;
                cond.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                Objects.equals(accountNo, account.accountNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountNo, balance);
    }
}
