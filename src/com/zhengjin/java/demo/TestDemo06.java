package com.zhengjin.java.demo;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDemo06 {

	ExCls ex1 = new ExCls("from test"); // #4

	static {
		TestUtils.printLog("test class static"); // #1
	}

	public TestDemo06() {
		TestUtils.printLog("test class constructor"); // #5
	}

	public static void mainTest(String[] args) {
		// test class init order
		new SubTest();
	}

	static class ExCls {

		static {
			TestUtils.printLog("ex class static"); // #3
		}

		public ExCls(String str) {
			TestUtils.printLog("ex class: " + str);
		}
	}

	static class SubTest extends TestDemo06 {

		ExCls ex = new ExCls("from subTest"); // #6

		static {
			TestUtils.printLog("sub test static"); // #2
		}

		public SubTest() {
			TestUtils.printLog("sub test class constructor"); // #7
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testExample01() {
		// priority queue
		final int total = 5;
		PriorityBlockingQueue<PriorityElement> queue = new PriorityBlockingQueue<>(total);

		for (int i = 0; i < total; i++) {
			int priority = new Random().nextInt(10);
			queue.put(new PriorityElement(priority));
		}

		while (!queue.isEmpty()) {
			try {
				TestUtils.printLog(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class PriorityElement implements Comparable<PriorityElement> {

		private int priority;

		public PriorityElement(int priority) {
			this.priority = priority;
		}

		@Override
		public int compareTo(PriorityElement other) {
			return this.priority > other.getPriority() ? 1 : -1;
		}

		@Override
		public String toString() {
			return "PriorityElement [priority=" + priority + "]";
		}

		public int getPriority() {
			return this.priority;
		}
	}

	@Test
	public void testExample02() {
		// delay queue and fixed thread pool
		final int total = 5;
		DelayQueue<DelayedElement> queue = new DelayQueue<>();
		ExecutorService exec = Executors.newFixedThreadPool(total);

		for (int i = 0; i < total; i++) {
			long delay = 1000 + (new Random().nextInt(5) * 1000);
			TestUtils.printLog("put delay element with " + delay);
			queue.put(new DelayedElement(String.format("cache %d time", delay), delay));
		}

		while (!queue.isEmpty()) {
			try {
				exec.execute(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		exec.shutdown();
		TestUtils.printLog("execute done.");
	}

	private static class DelayedElement implements Delayed, Runnable {

		String name;
		private long delay;
		private long expired;

		public DelayedElement(String name, long delay) {
			this.name = name;
			this.delay = delay;
			this.expired = System.currentTimeMillis() + this.delay;
		}

		@Override
		public int compareTo(Delayed d) {
			return ((DelayedElement) d).getExpired() > this.expired ? 1 : -1;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return (expired - System.currentTimeMillis());
		}

		public long getExpired() {
			return this.expired;
		}

		@Override
		public void run() {
			TestUtils.printLog("DelayedElement [delay=" + delay + ", name=" + name + "]");
		}
	}

}
