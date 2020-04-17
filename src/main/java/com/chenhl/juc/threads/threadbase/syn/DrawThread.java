package com.chenhl.juc.threads.threadbase.syn;

public class DrawThread extends Thread {

	private Account account;// 用户的账户

	private double drawAmount;// 要取的钱数

	public DrawThread(String name, Account account, double drawAmount) {
		super(name);
		this.account = account;
		this.drawAmount = drawAmount;
	}

	public void run() {

		account.draw(drawAmount);
	}
}
