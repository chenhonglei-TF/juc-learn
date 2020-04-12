package com.chenhl.myproject.threads.threadbase.controll;

/**
 * 线程提供了一个让一个线程等待其他线程的方法，join方法：
 * 当在某个程序的执行流中，调用了其他线程的join方法，调用线程将被阻塞，直到被join线程加入的方法执行完为止
 * 在主线程中调用了线程B的join方法，那么主线程必须等到线程B运行完了，才能运行，而主线程处于阻塞状态
 * @author lenovo
 *
 */
public class JoinThreadTest {

	public static void main(String[] args) throws InterruptedException {
		new JoinThread("线程A").start();//启动一个子线程
		
		for(int i=0;i<100;i++){
			if(i==20){
				Thread jt = new JoinThread("线程B");
				jt.start();
				
				jt.join();
			}
			System.out.println(Thread.currentThread().getName() + ": " +i);
		}
		
		
	}
}

class JoinThread extends Thread {
	
	public JoinThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName() + ": " + i);
		}
	}
}

