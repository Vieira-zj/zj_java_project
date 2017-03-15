package com.zhengjin.java.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.Semaphore;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

import static com.zhengjin.apis.testutils.TestUtils.printLog;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo03 {

	@Test
	public void test01Demo() {
		// Arrays.equals()
		String[] tmpArrSrc = { "test1", "test2", "test3" };
		String[] tmpArrTarget = new String[] { "test1", "test2", "test3" };
		boolean ret = Arrays.equals(tmpArrSrc, tmpArrTarget);
		printLog("Compare results: " + ret);
	}

	@Test
	public void test02Demo() {
		// Arrays.copyOf()
		String[] tmpArr = { "1", "2", "3", "4", "5", "6", "7", "8" };
		tmpArr = Arrays.copyOf(tmpArr, 5);
		for (String item : tmpArr) {
			printLog(item);
		}
	}

	@Test
	public void test03Demo() {
		// string reverse
		StringBuilder sb = new StringBuilder("abcd");
		printLog(sb.reverse().toString());
	}

	@Test
	public void test04Demo() {
		// System.arraycopy()
		String[] arrSrc = { "1", "2", "3", "4", "5", "6", "7", "8" };
		String[] arrTarget = { "a", "b", "c", "d" };
		for (String item : arrTarget) {
			printLog(item);
		}

		// Copy 4 elements from array 'arrSrc' starting at offset 3 to array
		// 'arrTarget' starting at offset 0
		printLog("After update: ");
		System.arraycopy(arrSrc, 3, arrTarget, 0, 4);
		for (String item : arrTarget) {
			printLog(item);
		}
	}

	@Test
	public void test05Demo() {
		// reflection
		String tmpStr = "Zheng Jin";
		try {
			Field valueFieldOfString = String.class.getDeclaredField("value");
			valueFieldOfString.setAccessible(true);
			char[] value = (char[]) valueFieldOfString.get(tmpStr);
			value[5] = '_';
		} catch (Exception e) {
			e.printStackTrace();
		}

		printLog("After updated, tmpStr = " + tmpStr);
	}

	@Test
	public void test06Demo() {
		// serialized
		Employee employee = new Employee();
		employee.name = "Reyan Ali";
		employee.address = "Phokka Kuan, Ambehta Peer";
		employee.SSN = 11122333;
		employee.number = 101;

		try {
			FileOutputStream fileOut = new FileOutputStream("d:\\employee.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(employee);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test07Demo() {
		// de-serialized
		Employee employee = null;

		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("d:\\employee.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			employee = (Employee) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		printLog("Deserialized Employee...");
		printLog("Name: " + employee.name);
		printLog("Address: " + employee.address);
		printLog("SSN: " + employee.SSN);
		printLog("Number: " + employee.number);
	}

	@SuppressWarnings({ "serial" })
	private static class Employee implements Serializable, Comparable<Employee> {

		public String name;
		public String address;
		public transient int SSN;
		public int number;

		public void mailCheck() {
			printLog("Mailing a check to " + name + " " + address);
		}

		@Override
		public String toString() {
			return String.format("(name = %s, address = %s, number = %s)",
					this.name, this.address, this.number);
		}

		@Override
		public int compareTo(Employee another) {
			return this.number < another.number ? 1 : -1;
		}
	}

	@Test
	public void test08Demo() {
		// field defined as final in thread
		// when test method finished, sub thread is terminated,
		// invoked runnable in main() instead
		Employee employee = new Employee();
		employee.name = "Reyan Ali";
		employee.address = "Phokka Kuan, Ambehta Peer";
		employee.SSN = 11122333;
		employee.number = 101;

		this.showMessage(employee, "test08Demo");
		TestUtils.printLog("test08Demo done.");
	}

	public static void testMain0(String[] args) {
		Employee employee = new Employee();
		employee.name = "Reyan Ali";
		employee.address = "Phokka Kuan, Ambehta Peer";
		employee.SSN = 11122333;
		employee.number = 101;

		TestDemo03 demo = new TestDemo03();
		String text = "test08Demo";
		demo.showMessage(employee, text);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		employee.name = "Reyan Ali(updated)";
		text = "test08Demo_updated";
		TestUtils.printLog("main is done => "
				+ Thread.currentThread().getName());
	}

	private void showMessage(final Employee employee, final String message) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 5; i++) {
					employee.mailCheck();
					TestUtils.printLog("MSG: " + message);
					System.out.printf("%s, run at %d time, and wait 1 sec.\n",
							Thread.currentThread().getName(), i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Test
	public void test09Demo() {
		// Comparator
		Integer[] integers = { new Integer(-1), new Integer(-2),
				new Integer(0), new Integer(1), new Integer(-1) };

		TestUtils.printLog(Arrays.asList(integers));

		Arrays.sort(integers);
		TestUtils.printLog(Arrays.asList(integers));

		Arrays.sort(integers, new AbsComparator());
		TestUtils.printLog(Arrays.asList(integers));
	}

	private static class AbsComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer i1, Integer i2) {
			int value1 = i1.intValue();
			int value2 = i2.intValue();
			return value1 > value2 ? 1 : (value1 == value2 ? 0 : -1);
		}
	}

	@Test
	public void test10Demo() {
		// Comparable
		Employee employee1 = new Employee();
		employee1.name = "Reyan Ali";
		employee1.address = "Phokka Kuan, Ambehta Peer";
		employee1.SSN = 11122333;
		employee1.number = 301;

		Employee employee2 = new Employee();
		employee2.name = "Wade";
		employee2.address = "Bei Jing, China";
		employee2.SSN = 11133556;
		employee2.number = 201;

		Employee[] ees = { employee1, employee2 };
		Arrays.sort(ees);
		for (Employee e : ees) {
			e.mailCheck();
		}

		// override Employee toString() method, and print list
		TestUtils.printLog(Arrays.asList(ees));
	}

	@Test
	public void test11Demo() {
		// RandomAccess vs iterator
		List<String> tmpLst = new ArrayList<>(Arrays.asList(new String[] {
				"test1", "test2", "test3" }));
		this.loopOnList(tmpLst);

		List<String> tmpLinkedLst = new LinkedList<>(tmpLst);
		this.loopOnList(tmpLinkedLst);
	}

	private void loopOnList(List<String> lst) {
		if (lst instanceof RandomAccess) {
			TestUtils.printLog("access by for loop");
			for (int i = 0, length = lst.size(); i < length; i++) {
				TestUtils.printLog("item: " + lst.get(i));
			}
		} else {
			TestUtils.printLog("access by for iterator");
			Iterator<String> iterator = lst.iterator();
			while (iterator.hasNext()) {
				TestUtils.printLog("item: " + iterator.next());
			}
		}
	}

	public static void testMain1(String[] args) {
		// Semaphore
		final MutexPrint print = new MutexPrint();

		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						print.print("Semaphore test");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		TestUtils.printLog("main done: " + Thread.currentThread().getName());
	}

	private static class MutexPrint {

		// 通过初始值为1的Semaphore, 很好的实现了资源的互斥访问
		private final Semaphore semaphore = new Semaphore(1);

		public void print(String str) throws InterruptedException {
			semaphore.acquire();

			TestUtils.printLog(Thread.currentThread().getName() + " enter ...");
			Thread.sleep(1000);
			TestUtils.printLog(Thread.currentThread().getName() + "printing..."
					+ str);
			TestUtils.printLog(Thread.currentThread().getName() + " out ...");

			semaphore.release();
		}
	}

	@Test
	public void test20Demo() {
		// TODO
	}

}
