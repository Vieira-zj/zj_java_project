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
		// access private fields
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
		tmpStrLst.add("NodeJs"); // throw exception
		// try {
		// tmpStrLst.add("NodeJs");
		// } catch (UnsupportedOperationException e) {
		// TestUtils.printLog("Unsupported update operations on list.");
		// }
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void test0701Demo() {
		// remove elements in list
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));

		for (int i = 0, size = tmpStrLst.size(); i < size; i++) {
			if ("Java".equals(tmpStrLst.get(i))) {
				tmpStrLst.remove(i);
			}
		}
	}

	@Test
	public void test0702Demo() {
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

	@Test(expected = ConcurrentModificationException.class)
	public void test0801Demo() {
		// iterator, remove elements in list
		List<String> tmpStrLst = new ArrayList<>(Arrays.asList(new String[] {
				"js", "Java", "C++", "C#", "JS" }));

		for (String item : tmpStrLst) {
			if ("JS".equals(item.toUpperCase())) {
				tmpStrLst.remove(item);
			}
		}
	}

	@Test
	public void test0802Demo() {
		// iterator, remove elements in list
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
		Class<?> cls = TestDemo01.class;

		TestUtils.printLog("Test methods in "
				+ TestDemo01.class.getSimpleName() + ": ");
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
			TestUtils.printLog("Code: " + EnumStatus.DISABLE.getCode());
			TestUtils.printLog("Tag: " + EnumStatus.DISABLE.name());
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
		MyTestClass02 testCls = new MyTestClass02.Builder(1, 2).setC(3).setD(4)
				.build();
		testCls.myPrint();
	}

}
