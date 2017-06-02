package com.zhengjin.java.demo;

import java.util.Random;

import static com.zhengjin.apis.testutils.TestUtils.printLog;

public final class VarAsThreadLocal {

	private final static ThreadLocal<People> threadLocal = new ThreadLocal<>();

	public static void main(String[] agrs) {
		VarAsThreadLocal demo = new VarAsThreadLocal();

		Thread t1 = new Thread(demo.new myRunnable());
		Thread t2 = new Thread(demo.new myRunnable());
		t1.start();
		t2.start();
	}

	private class myRunnable implements Runnable {

		@Override
		public void run() {
			String curThreadName = Thread.currentThread().getName();
			printLog(curThreadName + " is running ...");

			int age = new Random().nextInt(100);
			printLog("thread " + curThreadName + " set age to: " + age);
			People people = VarAsThreadLocal.this.getPeople();
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
