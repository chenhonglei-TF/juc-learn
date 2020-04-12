package com.chenhl.myproject.threads.threadbase.controll;

/**
 * 后台线程：为其他线程提供服务
 * 特征：如果所有的前台线程都死亡，后台线程会自动消亡
 * 
 * @author lenovo
 *
 */
public class DaemonThreadTest {

	public static void main(String[] args) {
		Thread daemon = new DaemonThread("后台线程");
		daemon.setDaemon(true);//将该线程设为后台线程
		daemon.start();
		
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName() + ": " +i);
		}
	}
}

class DaemonThread extends Thread {
	
	
	public DaemonThread(String name) {
		super(name);
	}
	@Override
	public void run() {
	for(int i=0;i<1000;i++){
		System.out.println(getName() + ": " +i);
	}
	}
}
