package com.zhengjin.java.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemos01 {

	@Test
	public void test01Demo() {
		// exception
		try {
			boolean ret = true;
			ret = myExceptionTest01();
			TestUtils.printLog("Return value: " + ret);
		} catch (Exception e) {
			TestUtils.printLog("Error message: " + e.getMessage());
		}
	}

	@SuppressWarnings("finally")
	boolean myExceptionTest01() throws Exception {
		// return in try .. catch .. finally block
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
			// do not use return in finally block
			TestUtils.printLog("myExceptionDemo01, finally; return value="
					+ ret);
			return ret;
		}
	}

	@Test
	public void test02Demo() {
		// exception
		try {
			boolean ret = true;
			ret = myExceptionTest02();
			TestUtils.printLog("Return value: " + ret);
		} catch (Exception e) {
			TestUtils.printLog("Error message: " + e.getMessage());
		}
	}

	@SuppressWarnings("finally")
	boolean myExceptionTest02() throws Exception {
		// return in try .. catch .. finally block
		boolean ret = true;
		try {
			ret = this.myExceptionTest01();
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
		// access private fields
		MyTestClass01 testCls = new MyTestClass01(3);
		boolean ret = testCls.Compare(new MyTestClass01(1));
		TestUtils.printLog("Results: " + ret);
	}

	@Test
	public void test04Demo() {
		// byte
		String tmpStr = "ZJTest";
		byte[] bytesArr = tmpStr.getBytes();

		if (tmpStr.length() == bytesArr.length) {
			TestUtils.printLog("Size equal.");
		}

		for (int i = 0, length = bytesArr.length; i < length; i++) {
			byte tmpByte = bytesArr[i];

			TestUtils.printLog("Char: " + (char) tmpByte);
			TestUtils.printLog("Oct: " + tmpByte);

			// for binary, if length less than 8, prefix with 0
			String strByteAsBin = Integer.toBinaryString(tmpByte);
			TestUtils.printLog("Binary: " + strByteAsBin);
			char[] tmpInitArr = { '0', '0', '0', '0', '0', '0', '0', '0' };
			final int bytesLen = 8;
			if (strByteAsBin.length() != bytesLen) {
				for (int j = 0, len = strByteAsBin.length(); j < len; j++) {
					tmpInitArr[j + bytesLen - len] = strByteAsBin.charAt(j);
				}
			}
			TestUtils.printLog("Binary: " + String.valueOf(tmpInitArr));

			String byteAsHex = Integer.toHexString(tmpByte & 0xFF);
			TestUtils.printLog("Hex: " + byteAsHex.toUpperCase() + "\n");
		}
	}

	@Test
	public void test05Demo() {
		// byte (8 bits, ascii), char (2 bytes, unicode)
		char tmpChar = 'A';
		TestUtils.printLog("Char: " + tmpChar);
		TestUtils.printLog("Oct: " + (byte) tmpChar);
		TestUtils.printLog("Binary: " + Integer.toBinaryString(tmpChar));
		TestUtils.printLog("Hex: " + Integer.toHexString(tmpChar & 0xFF).toUpperCase());
		
		char c = 65;
		TestUtils.printLog("Char: " + c);
		int i = 'h';
		TestUtils.printLog("Int: " + i);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void test06Demo() {
		// Arrays.asList()
		String[] tmpStrArr = new String[] { "Java", "C++", "C#", "JS" };
		List<String> tmpStrLst = Arrays.asList(tmpStrArr);
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		TestUtils.printLog("Array updated => ");
		tmpStrArr[3] = "Python";
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		TestUtils.printLog("List updated => ");
		tmpStrLst.add("NodeJs"); // throw UnsupportedOperationException
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void test0701Demo() {
		// remove list element in for loop, from start
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));

		// throw IndexOutOfBoundsException in loop
		for (int i = 0, size = tmpStrLst.size(); i < size; i++) {
			if ("java".equalsIgnoreCase(tmpStrLst.get(i))) {
				TestUtils.printLog("Item removed: " + tmpStrLst.remove(i));
			}
		}
	}

	@Test
	public void test0702Demo() {
		// remove list element in for loop, from end
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		for (int i = tmpStrLst.size() - 1; i >= 0; i--) {
			if ("C#".equalsIgnoreCase(tmpStrLst.get(i))) {
				TestUtils.printLog("Item removed: " + tmpStrLst.remove(i));
			}
		}

		TestUtils.printLog("AFTER REMOVED => ");
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void test0801Demo() {
		// remove element in list by iterator
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));

		// throw ConcurrentModificationException in loop
		for (String item : tmpStrLst) {
			if ("JS".equalsIgnoreCase(item)) {
				TestUtils.printLog("Item removed: " + tmpStrLst.remove(item));
			}
		}
	}

	@Test
	public void test0802Demo() {
		// remove element in list by iterator
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}

		Iterator<String> tmpIter = tmpStrLst.iterator();
		while (tmpIter.hasNext()) {
			String tmpStr = tmpIter.next();
			if ("C++".equalsIgnoreCase(tmpStr)) {
				tmpIter.remove();
			}
		}

		TestUtils.printLog("AFTER REMOVED => ");
		for (String str : tmpStrLst) {
			TestUtils.printLog("Item: " + str);
		}
	}

	@Test
	public void test09Demo() {
		// format
		int frames = Integer.parseInt("00035673", 16); // 218635, 218697, 218739
		System.out.println("frames: " + String.valueOf(frames));

		float fps = 1.011f;
		System.out.printf("fps: %.1f", fps);
	}

	@Test
	public void test10Demo() {
		// reflection
		Class<?> cls = TestDemos01.class;

		TestUtils.printLog("Test methods in "
				+ TestDemos01.class.getSimpleName() + ": ");
		Method[] methods = cls.getMethods();
		for (Method m : methods) {
			Annotation annotation = m.getAnnotation(org.junit.Test.class);
			if (annotation != null) {
				TestUtils.printLog(m.getName());
			}
		}
	}

	@Test
	public void test11RunnableTaskDemo() {
		// Runnable, return null
		ExecutorService executorService = Executors.newCachedThreadPool();
		@SuppressWarnings("unchecked")
		Future<String> future = (Future<String>) executorService
				.submit(new TaskRunnable());

		try {
			TestUtils.printLog(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	private static class TaskRunnable implements Runnable {

		@Override
		public void run() {
			TestUtils.printLog("run");
		}
	}

	@Test
	public void test12CallableTaskDemo() {
		// Callable, return
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<Integer> future = (Future<Integer>) executorService
				.submit(new TaskCallable());

		try {
			TestUtils.printLog(future.get().toString());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	@Test
	public void test13FutureTaskDemo() {
		// FutureTask, return
		ExecutorService executorService = Executors.newCachedThreadPool();
		FutureTask<Integer> futureTask = new FutureTask<>(new TaskCallable());

		executorService.submit(futureTask);
		try {
			TestUtils.printLog(futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

	@Test
	public void test14FutureTaskDemo() {
		// FutureTask, return
		ExecutorService executorService = Executors.newCachedThreadPool();
		FutureTask<Integer> futureTask = new FutureTask<>(new Runnable() {

			@Override
			public void run() {
				TestUtils.printLog("FutureTask2 run");
			}
		}, fibc(30)); // the result to return on successful completion

		executorService.submit(futureTask);
		try {
			TestUtils.printLog(futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	@Test
	public void test15FutureTaskDemo() {
		// FutureTask, return
		ExecutorService executorService = Executors.newCachedThreadPool();
		FutureTask<Void> futureTask = new FutureTask<>(new Runnable() {

			@Override
			public void run() {
				TestUtils.printLog("FutureTask3 run");
			}
		}, null);

		executorService.submit(futureTask);
		try {
			TestUtils.printLog(futureTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	private static class TaskCallable implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			TestUtils.printLog("call");
			return fibc(30);
		}
	}

	private static int fibc(int num) {
		if (num == 0) {
			return 0;
		}
		if (num == 1) {
			return 1;
		}
		return fibc(num - 1) + fibc(num - 2);
	}

	@Test
	public void test16Demo() {
		// ENUM object
		EnumStatus status = EnumStatus.DISABLE;
		
		switch (status) {
		case DELETE: {
			TestUtils.printLog("Status is DELETE.");
			TestUtils.printLog("Code: " + EnumStatus.DELETE.getCode());
			TestUtils.printLog("Tag: " + EnumStatus.DELETE.name());
			break;
		}
		case ENABLE: {
			TestUtils.printLog("Status is ENABLE.");
			TestUtils.printLog("Code: " + EnumStatus.ENABLE.getCode());
			TestUtils.printLog("Tag: " + EnumStatus.ENABLE.name());
			break;
		}
		case DISABLE: {
			TestUtils.printLog("Status is DISABLE.");
			TestUtils.printLog("Code: " + status.getCode());
			TestUtils.printLog("Tag: " + status.name());
			break;
		}
		default: {
			TestUtils.printLog("Status is invalid!");
			break;
		}
		}
	}

	@Test
	public void test17Demo() {
		// ENUM object
		TestUtils.printLog("Tag: " + BasicOperation.PLUS.toString());
		TestUtils.printLog(BasicOperation.PLUS.apply(1.0, 3.0));
	}

	@Test
	public void test18Demo() {
		// create object by Builder
		MyTestClass02 testCls = new MyTestClass02.Builder(1, 2).setC(3).build();
		testCls.myPrint();
	}

}
