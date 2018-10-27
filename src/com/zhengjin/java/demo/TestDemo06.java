package com.zhengjin.java.demo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	@Test
	public void testExample03() {
		// LinkedList used as Deque
		Deque<Integer> list = new LinkedList<>();

		System.out.println("LinkedList as queue, FIFO");
		final int count = 10;
		for (int i = 0; i < count; i++) {
			assertTrue(list.offerLast(i));
		}
		System.out.println(list);

		Integer item;
		while ((item = list.pollFirst()) != null) {
			System.out.print(item + " ");
		}
		System.out.println();

		System.out.println("\nLinkedList as stack, LIFO");
		for (int i = 0; i < count; i++) {
			assertTrue(list.offerFirst(i));
		}
		System.out.println(list);

		while ((item = list.pollFirst()) != null) {
			System.out.print(item + " ");
		}
		System.out.println();
	}

	@Test
	public void testExample04() {
		// Java8 stream
		List<String> actual = Stream.of("CCC", "A", "BB", "BB").filter(str -> str.length() > 1).sorted().distinct()
				.collect(Collectors.toList());
		TestUtils.printLog("Output: " + actual);

		List<String> expected = Arrays.asList("BB", "CCC");
		assertEquals(expected, actual);
	}

	@Test
	public void testExample05() {
		// reverse by LinkedList
		// input: aa, bb, cc, dd
		// output: "dd, cc, bb, aa"
		String[] input = { "aa", "bb", "cc", "dd" };
		LinkedList<String> list = new LinkedList<>();
		for (int i = 0, len = input.length; i < len; i++) {
			list.addFirst(input[i]);
		}
		list.forEach(item -> TestUtils.printLog("item: " + item));

		String output = String.join(", ", list);
		assertEquals("dd, cc, bb, aa", output);
	}

	@Test
	public void testExample06() {
		// get resource
		// https://www.cnblogs.com/dingyingsi/p/6055845.html
		TestUtils.printLog("classpath: " + System.getenv("classpath"));

		// path: ${project}\bin\com\zhengjin\demo\test.properties
		final String propertiesFile = "test.properties";
//		InputStream in = this.getClass().getResourceAsStream(propertiesFile);
//		InputStream in = this.getClass().getResourceAsStream("/com/zhengjin/java/demo" + File.separator + propertiesFile);
		InputStream in = ClassLoader
				.getSystemResourceAsStream("com/zhengjin/java/demo" + File.separator + propertiesFile);
		assertNotNull(in);

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		TestUtils.printLog("Output: " + p.getProperty("key1", "null"));
	}

	@Test
	public void testExample07() {
		// check chars sequence
		String testInput = "ABCDEFH";
		char[] letters = testInput.toCharArray();

		int srcNum = '1';
		for (int i = 0, len = letters.length; i < len; i++) {
			int num = letters[i];
			System.out.printf("Output: %c => %d\n", letters[i], num);
			assertTrue(num > srcNum);
			srcNum = num;
		}
	}

}
