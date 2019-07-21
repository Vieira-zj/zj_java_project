package com.zhengjin.java.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDemo07 {

	private static class ThreadExceptionRunner implements Runnable {

		@Override
		public void run() {
			System.out.println(String.format("sub thread %s is running...", Thread.currentThread().getName()));
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("mock exception!");
		}
	}

	private static class MyUncaughtExceptionHandle implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println(String.format("from thread %s, catch exception: %s", t.getName(), e.getMessage()));
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-20")
	public void testExample01() {
		// exception in sub thread will not cause main crash.

		// catch exception in sub thread
		// set default exception handler global
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandle());
		Thread t = new Thread(new ThreadExceptionRunner());
		t.start();

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main(testExample01) thread is done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-20")
	public void testExample02() {
		// catch exception in sub thread
		// set default exception handler for specified thread
		Thread t = new Thread(new ThreadExceptionRunner());
		t.setName("mock_thread_02");
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandle());
		t.start();

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main(testExample02) thread is done.");
	}

}
