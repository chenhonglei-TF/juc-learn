package com.chenhl.juc.threads.threadbase.demo;

public class DeadLockTest {

	public static void main(String[] args) {
		
		DeadLockThread d = new DeadLockThread();
		new Thread(d).start();
		d.init();
		
	}
}

class DeadLockThread implements Runnable {
	
	A a = new A();
	B b = new B();
	
	public void init(){
		Thread.currentThread().setName("主线程");
		
		a.foo(b);
		
		System.out.println("进入了主线程之后");
	}

	@Override
	public void run() {
		Thread.currentThread().setName("副线程");
		
		b.bar(a);
		
		System.out.println("进入了副线程之后");
		
	}
	
}

class A {

	public synchronized void foo (B b) {
		System.out.println("当前线程： " + Thread.currentThread().getName() +" 进入了A的foo方法");
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("当前线程： "+ Thread.currentThread().getName() + " 企图调用b的last方法");
		b.last();
	}
	
	public synchronized void last(){
		System.out.println("当前线程： "+ Thread.currentThread().getName() + " 进入了A的last方法内部");
	}
}

class B{
	public synchronized void bar (A a) {
		System.out.println("当前线程： " + Thread.currentThread().getName() +" 进入了B的bar方法");
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("当前线程： "+ Thread.currentThread().getName() + " 企图调用a的last方法");
		a.last();
	}
	
	public synchronized void last(){
		System.out.println("当前线程： "+ Thread.currentThread().getName() + " 进入了b的last方法内部");
	}
}
