package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.runner.JUnitCore;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo02 {

	@Test
	public void test01Demo() {
		Assert.fail("Test assert fail.");
	}

	@Test
	@Ignore
	public void test02Demo() {
		Assert.assertTrue("Test assert ignore.", true);
	}

	@Test
	public void test03Demo() {
		Assert.assertTrue("Test assert true.", true);
	}

	// run with customized test listener
	// public static void main(String[] args) {
	// JUnitCore runner = new JUnitCore();
	// runner.addListener(new ExecutionListener());
	// runner.run(TestDemo02.class);
	// }

	@Test
	public void test04RegExpDemo() {
		// regression express, group()
		String regEx = "\\w(\\d\\d)(\\w+)";
		String tmpStr = "A22happy";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(tmpStr);

		if (m.find()) {
			int count = m.groupCount();
			TestUtils.printLog("Group count: " + count);
			for (int i = 0; i <= count; i++) {
				TestUtils.printLog("Group " + i + ": " + m.group(i));
			}
		}
	}

	@Test
	public void test05RegExpDemo() {
		// regression express, find(), group(), start() and end()
		String tmpStr = "My name is Bond. James Bond.";
		Pattern p = Pattern.compile("Bond");
		Matcher m = p.matcher(tmpStr);

		if (m.find()) {
			TestUtils.printLog("Group value: " + m.group());
			int startIdx = m.start();
			TestUtils.printLog("1st start index: " + startIdx);
			int endIdx = m.end();
			TestUtils.printLog("1st end index: " + endIdx);
			TestUtils.printLog("Value: " + tmpStr.substring(startIdx, endIdx));
		}

		if (m.find()) {
			TestUtils.printLog("Group value: " + m.group());
			int startIdx = m.start();
			TestUtils.printLog("2nd start index: " + startIdx);
			int endIdx = m.end();
			TestUtils.printLog("2nd end index: " + endIdx);
			TestUtils.printLog("Value: " + tmpStr.substring(startIdx, endIdx));
		}
	}

	@Test
	public void test06RegExpDemo() {
		// find MAC by RegExp
		String tmpStr = "eth0      Link encap:Ethernet  HWaddr 28:76:CD:0D:00:2D";
		Pattern p = Pattern.compile("[0-9A-F]{2}(:[0-9A-F]{2}){5}");
		Matcher m = p.matcher(tmpStr);

		if (m.find()) {
			TestUtils.printLog("MAC: " + m.group());
		}
	}

	@Test
	public void test07RegExpDemo() {
		// find 2 bit number
		String tmpStr = "FD5551A-SU";
		Pattern p = Pattern.compile("[0-9]{2}");
		Matcher m = p.matcher(tmpStr);

		while (m.find()) {
			TestUtils.printLog(m.group());
		}
	}

	@Test
	public void test08RegExpDemo() {
		// RegExp in split()
		String tmpStr = "inet addr:172.17.5.30  Bcast:172.17.5.255  Mask:255.255.255.0";
		String[] items = tmpStr.split("\\s+");
		for (String item : items) {
			if (item.startsWith("addr")) {
				TestUtils.printLog(item.split(":")[1]);
			}
		}
	}

	@Test
	public void test09Demo() {
		// list to array
		List<Integer> tmpLst = new ArrayList<>(10);
		tmpLst.add(1);
		tmpLst.add(2);
		tmpLst.add(3);

		Integer[] tmpArr = tmpLst.toArray(new Integer[tmpLst.size()]);
		// Integer[] tmpArr = tmpLst.toArray(new Integer[0]);

		TestUtils.printLog("Array elements: ");
		for (int i = 0, length = tmpArr.length; i < length; i++) {
			TestUtils.printLog("element: " + tmpArr[i]);
		}
	}

	@Test
	public void test10RefDemo() {
		// args as value or reference
		int i1 = 1;
		TestUtils.printLog(i1);
		this.updateIntValue(i1);
		TestUtils.printLog(i1);

		Integer i2 = new Integer(2);
		TestUtils.printLog(i2);
		this.updateIntValue(i2);
		TestUtils.printLog(i2);

		String s1 = "test1";
		TestUtils.printLog(s1);
		this.updateStringValue(s1);
		TestUtils.printLog(s1);

		String s2 = new String("test2");
		TestUtils.printLog(s2);
		this.updateStringValue(s2);
		TestUtils.printLog(s2);

		List<String> lst = new ArrayList<>(Arrays.asList(new String[] { "Java",
				"C++" }));
		TestUtils.printLog(lst);
		this.updateListValue(lst);
		TestUtils.printLog(lst);
	}

	private void updateIntValue(int input) {
		input += 1;
		TestUtils.printLog("updateIntValue: " + String.valueOf(input));
	}

	private void updateStringValue(String tmpStr) {
		tmpStr += "x";
		TestUtils.printLog("updateStringValue: " + tmpStr);
	}

	private void updateListValue(List<String> lst) {
		lst.add("test");
		TestUtils.printLog("updateListValue: " + lst);
	}

	@Test
	public void test11Demo() {
		// replace()
		String tmpStr = "Test Demo 11";
		TestUtils.printLog(tmpStr.replace(" ", ""));
	}

	@Test
	public void test12Demo() {
		// split(regex)
		String tmpStr = ";ABBD;;;AS;D;;";
		String[] results = tmpStr.split(";");
		TestUtils.printLog(Arrays.toString(results));

		results = tmpStr.split(";+");
		TestUtils.printLog(Arrays.toString(results));
	}

	@Test
	public void test13Demo() {
		// StringTokenizer
		String tmpStr = "1,2;3;4,5";
		List<String> tmpLst = new ArrayList<>(20);

		StringTokenizer token = new StringTokenizer(tmpStr, ",;");
		while (token.hasMoreTokens()) {
			tmpLst.add(token.nextToken());
		}
		TestUtils.printLog(Arrays.toString(tmpLst.toArray(new String[0])));
	}

	@Test
	public void test14Demo() {
		// StringTokenizer
		String tmpStr = ";ABBD;;;AS;D;;";
		List<String> tmpLst = new ArrayList<>(20);

		StringTokenizer token = new StringTokenizer(tmpStr, ";");
		while (token.hasMoreTokens()) {
			tmpLst.add(token.nextToken());
		}
		TestUtils.printLog(Arrays.toString(tmpLst.toArray(new String[0])));
	}

	@Test
	public void test15Demo() {
		// Enumeration
		Hashtable<String, String> ht = new Hashtable<>(20);
		for (int i = 0; i < 5; i++) {
			ht.put("Key=" + i, "Val=" + i);
		}

		TestUtils.printLog("Keys:");
		Enumeration<String> keys = ht.keys();
		while (keys.hasMoreElements()) {
			TestUtils.printLog(keys.nextElement());
		}

		TestUtils.printLog("Values:");
		for (Enumeration<String> eles = ht.elements(); eles.hasMoreElements();) {
			TestUtils.printLog(eles.nextElement());
		}
	}

	@Test
	public void test16Demo() {
		// Iterator
		Hashtable<String, String> ht = new Hashtable<>(20);
		for (int i = 0; i < 5; i++) {
			ht.put("Key=" + i, "Val=" + i);
		}

		TestUtils.printLog("Keys:");
		Iterator<String> keys = ht.keySet().iterator();
		while (keys.hasNext()) {
			TestUtils.printLog(keys.next());
		}

		TestUtils.printLog("Value:");
		for (Iterator<Entry<String, String>> entries = ht.entrySet().iterator(); entries
				.hasNext();) {
			Entry<String, String> en = entries.next();
			TestUtils.printLog(en.getValue());
		}
	}

	@Test
	public void test17Demo() {
		// distinct, array to set
		String[] tmpArr = { "a", "a", "b", "c", "c", "d" };
		Set<String> tmpSet = new HashSet<>(Arrays.asList(tmpArr));

		for (String item : tmpSet) {
			TestUtils.printLog("Item: " + item);
		}
	}

	@Test
	public void test18Demo() {
		// distinct, list to set
		List<String> tmpList = new ArrayList<>();
		tmpList.add("a");
		tmpList.add("b");
		tmpList.add("c");
		tmpList.add("c");
		tmpList.add("d");
		tmpList.add("a");

		TestUtils.printLog("List items: ");
		for (String item : tmpList) {
			TestUtils.printLog("Item => " + item);
		}

		TestUtils.printLog("Set items: ");
		Set<String> tmpSet = new HashSet<>(tmpList);
		for (String item : tmpSet) {
			TestUtils.printLog("Item => " + item);
		}
	}

	@Test
	public void test19Demo() {
		// overload
		TestDemo02 demo = new TestDemo02();
		demo.myPrint("overload test");
		demo.myPrint(10);
	}

	private void myPrint(int value) {
		TestUtils.printLog("Print int value: " + String.valueOf(value));
	}

	private void myPrint(String text) {
		TestUtils.printLog("Print string: " + text);
	}

	@Test
	public void test20Demo() {
		// instanceof
		MyTestClass01 testCls = new MyTestClass02("test", 10);
		if (testCls instanceof MyTestClass02) {
			TestUtils.printLog("testCls is instanceof MyTestClass02.");
		}
	}

	@Test
	public void test21Demo() {
		// contains()
		List<String> tmpLst = new ArrayList<>(Arrays.asList(new String[] {
				"Java", "C++", "Python" }));
		if (tmpLst.contains("Java")) {
			TestUtils.printLog("Java incldude.");
		}
		if (tmpLst.contains("JAVA")) {
			TestUtils.printLog("JAVA incldude.");
		}
	}

	@Test
	public void test22Demo() {
		// ConcurrentHashMap
		ConcurrentHashMap<String, String> tmpMap = new ConcurrentHashMap<>(20);
		tmpMap.put("key1", "value1");
		tmpMap.put("key2", "value2");
		tmpMap.put("key3", "value3");

		Iterator<Entry<String, String>> tmpEntries = tmpMap.entrySet()
				.iterator();
		while (tmpEntries.hasNext()) {
			// update map in loop
			Entry<String, String> tmpEntry = tmpEntries.next();
			if (tmpEntry.getKey().equals("key2")) {
				tmpMap.put(tmpEntry.getKey() + "new ", tmpEntry.getValue()
						+ "new");
			}
		}

		for (Entry<String, String> entry : tmpMap.entrySet()) {
			TestUtils.printLog("Key: " + entry.getKey() + " Value: "
					+ entry.getValue());
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test23Demo() {
		// Collections.unmodifiableCollection
		List<String> tmpLst = new ArrayList<>(Arrays.asList(new String[] {
				"test1", "test2", "test3" }));
		Collection<String> tmpCollection = Collections
				.unmodifiableCollection(tmpLst);

		TestUtils.printLog("Size = " + tmpCollection.size());
		tmpCollection.add("test4"); // unmodifiable
	}

	@Test
	public void test24Demo() {
		// Threads
		TestRunnable testRunnable = new TestRunnable();
		Thread[] threads = { new Thread(testRunnable, "Thread1"),
				new Thread(testRunnable, "Thread2"),
				new Thread(testRunnable, "Thread3") };

		for (Thread t : threads) {
			t.start();
		}

		try {
			for (Thread t : threads) {
				t.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class TestRunnable implements Runnable {

		// private int ticket = 10;
		private AtomicInteger ticket = new AtomicInteger(10);

		@Override
		public void run() {
			for (int i = 0; i <= 20; i++) {
				if (this.ticket.get() > 0) {
					TestUtils.printLog(Thread.currentThread().getName()
							+ " get and ticket: "
							+ this.ticket.decrementAndGet());
				}
			}
		}
	}

}
