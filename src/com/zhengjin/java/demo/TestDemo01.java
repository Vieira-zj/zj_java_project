package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.zhengjin.apis.testutils.TestUtils;

public final class TestDemo01 {

	@Test
	public void test01ExceptionDemo() {
		try {
			boolean ret = true;
			ret = myExceptionDemo01();
			TestUtils.printLog("Return value: " + ret);
		} catch (Exception e) {
			TestUtils.printLog("Error message: " + e.getMessage());
		}
	}

	@SuppressWarnings("finally")
	boolean myExceptionDemo01() throws Exception {
		boolean ret = true;
		int c;
		try {
			int b = 12;
			for (int i = 2; i >= -2; i--) {
				c = b / i;
				TestUtils.printLog("i=" + i);
				TestUtils.printLog("c=" + c);
			}
			return true;
		} catch (Exception e) {
			TestUtils.printLog("myExceptionDemo01, catch exception");
			ret = false;
			throw e;
		} finally {
			TestUtils.printLog("myExceptionDemo01, finally; return value="
					+ ret);
			return ret;
		}
	}

	@Test
	public void test02ExceptionDemo() {
		try {
			boolean ret = true;
			ret = myExceptionDemo02();
			TestUtils.printLog("Return value: " + ret);
		} catch (Exception e) {
			TestUtils.printLog("Error message: " + e.getMessage());
		}

	}

	@SuppressWarnings("finally")
	boolean myExceptionDemo02() throws Exception {
		boolean ret = true;
		try {
			ret = this.myExceptionDemo01();
			if (!ret) {
				return false;
			}
			TestUtils.printLog("myExceptionDemo02, at the end of try");
			return ret;
		} catch (Exception e) {
			TestUtils.printLog("myExceptionDemo02, catch exception");
			ret = false;
			throw e;
		} finally {
			TestUtils.printLog("myExceptionDemo02, finally; return value="
					+ ret);
			return ret;
		}
	}

	@Test
	public void test03Demo() {
		MyTestClass01 testCls = new MyTestClass01(3);
		boolean ret = testCls.Compare(new MyTestClass01(1));
		TestUtils.printLog("Results: " + ret);
	}

	@Test
	public void test04Demo() {
		// getBytes()
		String tmpStr = "ZJTest";
		byte[] bytesArr = tmpStr.getBytes();
		for (int i = 0, length = bytesArr.length; i < length; i++) {
			TestUtils.printLog("Char:" + tmpStr.charAt(i));
			TestUtils.printLog("Oct:" + bytesArr[i]);

			String bin = Integer.toBinaryString(bytesArr[i]);
			TestUtils.printLog("Binary: " + bin);

			String hex = Integer.toHexString(bytesArr[i] & 0xFF);
			TestUtils.printLog("Hex: " + hex.toUpperCase());
		}
	}

	@Test
	public void test05Demo() {
		// char
		char tmpChar = 'Z';
		TestUtils.printLog("Char: " + tmpChar);

		String bin = Integer.toBinaryString(tmpChar);
		TestUtils.printLog("Binary: " + bin);

		String hex = Integer.toHexString(tmpChar & 0xFF);
		TestUtils.printLog("Hex: " + hex.toUpperCase());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test06Demo() {
		// Arrays.asList()
		String[] tmpStrArr = new String[] { "Java", "C++", "C#", "JS" };
		List<String> tmpStrLst = Arrays.asList(tmpStrArr);
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		TestUtils.printLog("AFTER ARRAY UPDATED => ");
		tmpStrArr[3] = "Python";
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		TestUtils.printLog("AFTER LIST UPDATED => ");
		tmpStrLst.add("NodeJs");
		// try {
		// tmpStrLst.add("NodeJs");
		// } catch (UnsupportedOperationException e) {
		// TestUtils.printLog("Unsupported update operations on list.");
		// }
	}

	@Test
	public void test07Demo() {
		// remove elements in list
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		for (int i = tmpStrLst.size() - 1; i >= 0; i--) {
			if ("JS".equals(tmpStrLst.get(i).toUpperCase())) {
				tmpStrLst.remove(i);
			}
		}

		TestUtils.printLog("AFTER REMOVED => ");
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}
	}

	@Test
	public void test08Demo() {
		// remove elements in list
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		Iterator<String> tmpIter = tmpStrLst.iterator();
		while (tmpIter.hasNext()) {
			String tmpStr = tmpIter.next();
			if ("JS".equals(tmpStr.toUpperCase())) {
				tmpIter.remove();
			}
		}

		TestUtils.printLog("AFTER REMOVED => ");
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}
	}

}
