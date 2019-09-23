package com.zhengjin.app.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsInOrder {

	// 三个线程交替打印1~99
	public static void main(String[] args) throws InterruptedException {

		MyResource res = new MyResource();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				res.printNum(res.c01, res.c02);
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				res.printNum(res.c02, res.c03);
			}
		}, "t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				res.printNum(res.c03, res.c01);
			}
		}, "t3");

		Thread[] threads = new Thread[] { t1, t2, t3 };
		for (int i = 0, n = threads.length; i < n; i++) {
			// 保证线程按顺序启动
			threads[i].start();
			Thread.sleep(100L);
		}

		System.out.println("Wait for threads done...");
		for (int i = 0, n = threads.length; i < n; i++) {
			threads[i].join();
		}
		System.out.println("All threads done.");
	}

	private static class MyResource {

		private int num = 0;
		private Lock lock = new ReentrantLock();
		Condition c01 = lock.newCondition();
		Condition c02 = lock.newCondition();
		Condition c03 = lock.newCondition();

		public void printNum(Condition self, Condition next) {
			lock.lock();
			try {
				while (num < 99) {
					System.out.println(String.format("Thread %s: %d", Thread.currentThread().getName(), ++num));
					next.signal();
					self.await();
				}
				// 保证下一个线程不在阻塞状态
				next.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
}
