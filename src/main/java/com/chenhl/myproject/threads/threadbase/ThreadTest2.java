package com.chenhl.myproject.threads.threadbase;

public class ThreadTest2 {

	public static void main(String[] args) {

//		Thread t = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				for (int i = 0; i < 50; i++) {
//					System.out.println("hello: " + i);
//				}
//			}
//
//		});
//
//		t.start();
		
		Thread t1 = new Thread(new MyThread());
		Thread t2 = new Thread(new MyThread2());
		
		t1.start();
		t2.start();
	}

}

class MyThread implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			System.out.println("hello: " + i);
		}
	}
}

class MyThread2 implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			System.out.println("world: " + i);
		}
	}
}