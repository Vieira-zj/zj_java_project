package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
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
			System.out.println(e.toString());
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
			System.out.println(e.toString());
		}

		// #3
		System.out.println("\n#3. Pass");
		try {
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				String item = iter.next();
				System.out.println("item: " + item);
				if ("4".equals(item)) {
					iter.remove();
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	@TestInfo(author = "zhengjin", date = "2019-07-24")
	public void testExample04() {
		// ConcurrentModificationException for Vector
		List<String> v = new Vector<String>(20);
		for (int i = 0; i < 10; i++) {
			v.add(String.valueOf(i));
		}

		// #1
		System.out.println("#1. ConcurrentModificationException");
		try {
			for (String item : v) {
				System.out.println("item: " + item);
				if ("2".equals(item)) {
					v.remove(item);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println(e.toString());
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

}
