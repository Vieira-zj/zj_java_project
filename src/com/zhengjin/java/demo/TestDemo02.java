package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		System.out.println("updateStringValue: " + tmpStr);
	}

	private void updateListValue(List<String> lst) {
		lst.add("test");
		System.out.println("updateListValue: " + lst);
	}

}
