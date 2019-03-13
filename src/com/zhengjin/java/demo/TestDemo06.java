package com.zhengjin.java.demo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
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
		TestUtils.printLog("test class TestDemo06 static"); // #1
	}

	public TestDemo06() {
		TestUtils.printLog("test class TestDemo06 constructor"); // #5
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
	@TestInfo(author = "zhengjin", date = "2018-10-27")
	public void testExample04() {
		// Java8 stream
		List<String> actual = Stream.of("CCC", "A", "BB", "BB").filter(str -> str.length() > 1).sorted().distinct()
				.collect(Collectors.toList());
		TestUtils.printLog("Output: " + actual);

		List<String> expected = Arrays.asList("BB", "CCC");
		assertEquals(expected, actual);
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-10-27")
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
	@TestInfo(author = "zhengjin", date = "2018-10-27")
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
	@TestInfo(author = "zhengjin", date = "2018-10-27")
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

	@Test
	@TestInfo(author = "zhengjin", date = "2018-11-7")
	public void testExample08() {
		// number comparison
		final String num1 = "10.1";
		final String num2 = "9.9";
		assertTrue(Double.parseDouble(num1) > Double.parseDouble(num2));
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-12-5")
	public void testExample09() {
		// pass in anonymous list
		testIterator(Arrays.asList(new String[] { "hello", "world" }));
	}

	private void testIterator(List<String> inputLst) {
		for (String item : inputLst) {
			System.out.println("item: " + item);
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2018-12-18")
	public void testExample10() {
		// object clone
		List<String> grade = new ArrayList<>(10);
		grade.add("a");
		grade.add("b");

		CloneClazz srcObj = new CloneClazz(0, "clone test object", grade);
		CloneClazz cloneObj = null;
		try {
			cloneObj = (CloneClazz) srcObj.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		assertNotNull(cloneObj);

		cloneObj.incrIndex();
		System.out.println("before, src object => " + srcObj);
		System.out.println("clone object => " + cloneObj);

		grade.add("c");
		System.out.println("after, src object => " + srcObj);
		System.out.println("clone object => " + cloneObj);
	}

	private static class CloneClazz implements Cloneable {

		private int index;
		private String desc;
		private List<String> grade;

		public CloneClazz(int index, String desc, List<String> grade) {
			this.index = index;
			this.desc = desc;
			this.grade = grade;
		}

		@Override
		public String toString() {
			return String.format("Index: %d, desc: %s, grade: %s", this.index, this.desc, this.grade);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		public void incrIndex() {
			this.index++;
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-01-01")
	public void testExample11() {
		// CountDownLatch
		int runCount = 3;
		CountDownLatch countDownLatch = new CountDownLatch(runCount);

		for (int i = 0; i < runCount; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("thread: " + Thread.currentThread().getName() + " wait");
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						countDownLatch.countDown();
					}
				}
			}).start();
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("all threads done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-01-02")
	public void testExample12() {
		// CyclicBarrier
		int runCount = 3;
		List<Thread> pool = new ArrayList<>(runCount * 3);
		CyclicBarrier barrier = new CyclicBarrier(runCount);

		for (int i = 0; i < (runCount * 2); i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					String pName = Thread.currentThread().getName();
					try {
						int wait = new Random().nextInt(5);
						System.out.println(pName + " running secs: " + wait);
						Thread.sleep(wait * 1000L);
						System.out.println(pName + " wait...");
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					System.out.println(pName + " continue to run.");
				}
			});
			pool.add(t);
			t.start();
		}

		for (Thread t : pool) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("main test done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-01-03")
	public void testExample13() {
		// multi threads for arraylist
		int tCount = 30;
		CountDownLatch countDown = new CountDownLatch(tCount);
		int itemCount = 1000;

		// non sync list
//		List<String> lst = new ArrayList<>(2 * tCount * itemCount);
		// sync list
		List<String> lst = Collections.synchronizedList(new ArrayList<>(2 * tCount * itemCount));

		for (int i = 0; i < tCount; i++) {
			final int idx = i;

			new Thread(new Runnable() {
				@Override
				public void run() {
					String pName = Thread.currentThread().getName();
					String prefix = pName + "_" + String.valueOf(idx) + "_";
					for (int j = 0; j < itemCount; j++) {
						lst.add(prefix + "j");
					}
					countDown.countDown();
				}
			}).start();
		}

		try {
			countDown.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("list size: " + lst.size());
		lst.clear();

		System.out.println("multi threads for arraylist test done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-03-13")
	public void testExample14() {
		// Java8 stream
		// #1
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		List<Integer> newNumbers = numbers.stream().map(num -> num * 2).distinct().sorted()
				.collect(Collectors.toList());
		System.out.println("new numbers: " + newNumbers);

		// #2, flatMap
		Stream<List<Integer>> inputStream = 
				Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
		Stream<Integer> outputStream = inputStream.flatMap((numberList) -> numberList.stream());
		numbers = outputStream.collect(Collectors.toList());
		System.out.println("all numbers: " + newNumbers);

		// #3, peek
		List<String> words = Stream.of("one", "two", "three", "four").filter(e -> (e.length() > 3))
				.peek(e -> System.out.println("Filtered value: " + e)).map(String::toUpperCase)
				.peek(e -> System.out.println("Mapped value: " + e)).collect(Collectors.toList());
		System.out.println("updated words: " + words);

		// #4, reduce
		int sum = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
		System.out.println("sum: " + sum);

		String concat = Stream.of("a", "B", "c", "D", "e", "F")
				.filter(x -> (x.compareTo("Z") > 0)).reduce("", String::concat);
		System.out.println("concat string: " + concat);

		double min = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		System.out.println("min number: " + min);

		// #5, get max length
		Stream<String> langs = Stream.of("Java", "JavaScript", "C++", "Golang", "Python");
		int maxLength = langs.mapToInt(String::length).max().getAsInt();
		System.out.println("max lang length: " + maxLength);

		// #6, match
		Stream<Integer> ages = Stream.of(10, 21, 34, 6, 55);
		boolean isAllAdult = ages.allMatch(num -> (num > 18));
		System.out.println("is all adult: " + isAllAdult);

		ages = Stream.of(10, 21, 34, 6, 55);
		boolean isThereAnyChild = ages.anyMatch(num -> (num < 18));
		System.out.println("is there any child: " + isThereAnyChild);
	}

}
