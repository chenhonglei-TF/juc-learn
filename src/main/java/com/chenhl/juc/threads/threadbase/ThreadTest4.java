package com.chenhl.juc.threads.threadbase;

public class ThreadTest4 {

	public static void main(String[] args) {
		Example example = new Example();
		
		Thread t1 = new TheThread(example);
		
		example = new Example();
		Thread t2 = new TheThread2(example);
		
		t1.setName("A");
		t2.setName("B");
		
		t1.start();
		t2.start();
	}
}

class Example {
	
	public synchronized static void execute(){
		for(int i=0;i<20;i++){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("execute1: "+Thread.currentThread().getName() + ": " + i);
		}
	}
	
	public synchronized static void execute2(){
		for(int i=0;i<20;i++){
			try {
				Thread.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("execute2: "+Thread.currentThread().getName() + ": " + i);
		}
	}
}

class TheThread extends Thread{
	
	private Example example;
	
	
	public TheThread(Example example) {
		super();
		this.example = example;
	}

	public void run(){
		example.execute();
	}
}

class TheThread2 extends Thread{
	
	private Example example;
	
	
	public TheThread2(Example example) {
		super();
		this.example = example;
	}

	public void run(){
		example.execute2();
	}
}