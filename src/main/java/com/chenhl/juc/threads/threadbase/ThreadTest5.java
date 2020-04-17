package com.chenhl.juc.threads.threadbase;

public class ThreadTest5 {

	public static void main(String[] args) {
		Example2 e = new Example2();

		Thread t3 = new TheThread3(e);
		Thread t4 = new TheThread4(e);

		t3.start();
		t4.start();
	}
}

class Example2 {

	private Object obj = new Object();

	public void execute() {
		synchronized (this) {
			for (int i = 0; i < 20; i++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("execute1: " + Thread.currentThread().getName() + ": " + i);
			}
		}

	}

	public void execute2() {
		synchronized (this) {//也可以使用obj
//			synchronized (obj) {//也可以使用this

			for (int i = 0; i < 20; i++) {
				try {
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("execute2: " + Thread.currentThread().getName() + ": " + i);
			}
		}
	}
}

class TheThread3 extends Thread {

	private Example2 example;

	public TheThread3(Example2 example) {
		super();
		this.example = example;
	}

	public void run() {
		example.execute();
	}
}

class TheThread4 extends Thread {

	private Example2 example;

	public TheThread4(Example2 example) {
		super();
		this.example = example;
	}

	public void run() {
		example.execute2();
	}
}