package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

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
				TimeUnit.SECONDS.sleep(2);
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
			TimeUnit.SECONDS.sleep(3);
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
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main(testExample02) thread is done.");
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-24")
	public void testExample03() {
		// ConcurrentModificationException for ArrayList, HashMap
		List<String> list = new ArrayList<String>(20);
		for (int i = 0; i < 10; i++) {
			list.add(String.valueOf(i));
		}

		// #1
		System.out.println("#1. IndexOutOfBoundsException");
		try {
			for (int i = 0; i < 10; i++) {
//			for (int i = 0; i < list.size(); i++) {
				System.out.println("item: " + list.get(i));
				if (i == 2) {
					list.remove(i);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Exception: " + e.toString());
		}

		// #2
		System.out.println("\n#2. iterator and ConcurrentModificationException");
		try {
			for (String item : list) {
				System.out.println("item: " + item);
				if ("3".equals(item)) {
					list.remove(item);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Exception: " + e.toString());
		}

		// #3
		System.out.println("\n#3. Pass");
		List<String> tmp = Arrays.asList(new String[] { "a", "b", "c", "d", "e" });
		list = new ArrayList<String>(tmp);
		try {
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				String item = iter.next();
				System.out.println("item: " + item);
				if ("c".equals(item)) {
					iter.remove(); // remove current item
					// iter.remove(); // duplicated remove, and throw IllegalStateException
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println(e.toString());
		}
		System.out.println("Modified list: " + list);
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-24")
	public void testExample04() {
		// #1
		System.out.println("#1. Vector, ConcurrentModificationException");
		List<String> v = new Vector<String>(20);
		for (int i = 0; i < 10; i++) {
			v.add(String.valueOf(i));
		}

		try {
			for (String item : v) {
				System.out.println("item: " + item);
				if ("2".equals(item)) {
					v.remove(item);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Exception: " + e.toString());
		}

		// #2
		System.out.println("\n#2. CopyOnWriteArrayList");
		List<String> list = new CopyOnWriteArrayList<String>(v);
		for (String item : list) {
			if (Integer.parseInt(item) % 2 != 0) {
				list.remove(item);
			}
		}
		System.out.println("list: " + list.toString());

		/**
		 * ArrayList和CopyOnWriteArrayList
		 * 
		 * ArrayList在迭代的过程中，如果发生增加，删除等导致modCount发生变化的操作时会抛出异常。
		 * CopyOnWriteArrayList,在多线程环境中读多写少场景是比较有效的，
		 * 它使用写时变更到新数组，然后修改引用指向，读还是在旧数组上读，实现读写分离，
		 * 写和读有所延迟，数据不保证实时一致性，只能保证最终一致性，另外需要注意这种拷贝对象会耗费不少的内存。
		 */
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-25")
	public void testExample05() throws InterruptedException {
		// #1
		System.out.println("#1. Hashtable, ConcurrentModificationException");
		Map<Integer, String> table = new Hashtable<Integer, String>(20);
		for (int i = 0; i < 10; i++) {
			table.put(i, String.valueOf(i));
		}

		try {
			for (int key : table.keySet()) {
				System.out.println(String.format("entry: %d=%s", key, table.get(key)));
				if (key == 2) {
					table.remove(key);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Exception: " + e.toString());
		}

		// #2
		System.out.println("\n#2. ConcurrentHashMap");
		ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<Integer, String>();
		for (int i = 0; i < 10; i++) {
			map.put(i, String.valueOf(i));
		}

		for (int key : map.keySet()) {
			System.out.println(String.format("entry: %d=%s", key, map.get(key)));
			if (key == 3) {
				table.remove(key);
			}
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2020-02-16")
	public void testExample06() {
		int key;
		int count80 = 0;
		for (int i = 0; i < 100; i++) {
			key = getKey(100);
			System.out.println("Get key: " + String.valueOf(key));
			if (key <= 20) {
				count80++;
			}
		}
		System.out.println("count 80%: " + String.valueOf(count80));
	}

	/**
	 * 80%的请求访问20%的热点数据。
	 * 
	 * @param keyCount
	 * @return
	 */
	public static int getKey(int keyCount) {
		Random random = new Random();
		int val = (int) (keyCount * 0.2 * random.nextDouble());
		int r = random.nextInt(100);
		if (r <= 80) {
			return val;
		} else {
			return keyCount - val;
		}
	}

}
