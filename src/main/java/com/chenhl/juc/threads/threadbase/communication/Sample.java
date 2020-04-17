package com.chenhl.juc.threads.threadbase.communication;

/**
 * 线程的协调
 * @author lenovo
 *
 */
public class Sample {

	private int number;
	
	public synchronized void increase(){
		
		while(0 != number) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		number++;
		System.out.println(Thread.currentThread().getName() + ": " + number);
		
		notify();
	}
	
	public synchronized void decrease(){
		
		while(0 == number){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		number--;
		System.out.println(Thread.currentThread().getName() + ": " + number);

		notify();
	}
}
