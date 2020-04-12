package com.chenhl.myproject.threads.threadbase.syn;

public class Account {
	
	private String accountNo;// 账号
	private double balance;// 余额
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public double getBalance() {
		return balance;
	}
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}
	public Account(String accountNo, double balance) {
		super();
		this.accountNo = accountNo;
		this.balance = balance;
	}
	public Account() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNo == null) {
			if (other.accountNo != null)
				return false;
		} else if (!accountNo.equals(other.accountNo))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		return true;
	}
	
	public synchronized void draw(double drawAmount){
		synchronized (this) {

			if (balance >= drawAmount) {

				System.out.println(Thread.currentThread().getName() + " 取钱成功，吐出钱数： " + drawAmount);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				balance -= drawAmount;
				System.out.println("\t余额为：" + balance);
			} else {
				System.out.println(Thread.currentThread().getName() +": 取钱失败，余额不足！");
			}
		}
	}

}
