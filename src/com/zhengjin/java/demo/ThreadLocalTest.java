package com.zhengjin.java.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.zhengjin.apis.testutils.TestUtils.printLog;

public final class ThreadLocalTest {

	// defined as global
	private final static ThreadLocal<People> threadLocal = new ThreadLocal<>();

	public static void main(String[] agrs) {
		ThreadLocalTest demo = new ThreadLocalTest();

		Thread t1 = new Thread(demo.new myRunnable());
		Thread t2 = new Thread(demo.new myRunnable());
		t1.setName("test_thread_01");
		t2.setName("test_thread_02");
		
		Thread t3 = new Thread(demo.new myDaemonRnnable());
		t3.setName("test_daemon_thread_01");
		t3.setDaemon(true);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			final long wait = 3000L;
			t1.join(wait);
			t2.join(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread test done.");
	}

	private class myRunnable implements Runnable {

		@Override
		public void run() {
			String curThreadName = Thread.currentThread().getName();
			printLog(curThreadName + " is running ...");

			int age = new Random().nextInt(100);
			printLog("thread " + curThreadName + " set age to: " + age);
			People people = ThreadLocalTest.this.getPeople();
			people.setAge(age);

			printLog("thread " + curThreadName + " first read age is: " + people.getAge());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			printLog("thread " + curThreadName + " second read age is: " + people.getAge());

		}
	}

	private class myDaemonRnnable implements Runnable {
		
		@Override
		public void run() {
			while(true) {
				System.out.println("I'm daemon thread ...");
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private People getPeople() {
		People people = threadLocal.get();
		if (people == null) {
			printLog("Create people instance and save in threadlocal.");
			people = new People();
			threadLocal.set(people);
		}
		return people;
	}

	private class People {

		private int age = 0;

		public int getAge() {
			return this.age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}
