package com.chenhl.myproject.threads.threadbase;

public class ThreadTest {

	public static void main(String[] args) {
		Thread1 t = new Thread1("线程A");
		Thread1 t2 = new Thread1("线程B");
		
		t.start();
		t2.start();
	}
}

class Thread1 extends Thread {
	
	public Thread1(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++) {
			System.out.println(Thread.currentThread().getName()+": " + i);
		}
	}
}

class Thread2 extends Thread {
	public Thread2(String name) {
		super(name);
	}
	@Override
	public void run() {
		for(int i=0;i<100;i++) {
			System.out.println(Thread.currentThread().getName()+": " + i);
		}
	}
}