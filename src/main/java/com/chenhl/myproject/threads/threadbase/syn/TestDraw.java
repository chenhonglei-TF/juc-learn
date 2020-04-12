package com.chenhl.myproject.threads.threadbase.syn;

public class TestDraw {

	public static void main(String[] args) {
		
		Account account = new Account("12133323", 1000);
		
		new DrawThread("A", account, 800).start();
		new DrawThread("B", account, 800).start();
	}
}
