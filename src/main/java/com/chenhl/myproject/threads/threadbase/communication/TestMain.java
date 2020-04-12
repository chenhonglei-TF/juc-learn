package com.chenhl.myproject.threads.threadbase.communication;

public class TestMain {

	public static void main(String[] args) {
		
		Sample sample = new Sample();
		
		new IncreaseThread("加1线程A", sample).start();
		new DecreaseThread("减1线程C", sample).start();
		
		new IncreaseThread("加1线程B", sample).start();
		new DecreaseThread("减1线程D", sample).start();
	}
}
