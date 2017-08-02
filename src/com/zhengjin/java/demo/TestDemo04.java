package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo04 {

	private static final String TAG = TestDemo04.class.getSimpleName();

	private String testValue = "test";

	public TestDemo04() {
		// must be public for Junit invoke
		testValue = "zj_test";
	}

	private class innerCls {

		String testValue = "inner test";

		public void testPrint() {
			TestUtils.printLog("outer static tag: " + TestDemo04.TAG);
			TestUtils.printLog("inner value: " + this.testValue);
			TestUtils.printLog("outer value: " + TestDemo04.this.testValue);
		}
	}

	@Test
	public void test01Demo() {
		// Arrays.equals()
		String[] tmpArr1 = { "Java", "Javascript", "C++" };
		String[] tmpArr2 = { "Java", "Javascript", "C++" };
		String[] tmpArr3 = { "Javascript", "Java", "C++" };

		Assert.assertTrue("Content and position equal.",
				Arrays.equals(tmpArr1, tmpArr2));
		Assert.assertTrue("Only content equal.",
				Arrays.equals(tmpArr1, tmpArr3));
	}

	@Test
	public void test02Demo() {
		// Collections.unmodifiableCollection()
		Collection<User> originalCollection = new ArrayList<>();
		originalCollection.add(new User("name1"));
		originalCollection.add(new User("name2"));
		originalCollection.add(new User("name3"));

		Collection<User> copiedCollection = Collections
				.unmodifiableCollection(originalCollection);
		TestUtils.printLog("Before update:");
		for (User u : copiedCollection) {
			TestUtils.printLog(u.name);
		}

		for (User u : originalCollection) {
			u.name = "test";
		}
		TestUtils.printLog("\nAfter update item value:");
		for (User u : copiedCollection) {
			TestUtils.printLog(u.name);
		}

		originalCollection.add(new User("ZJ"));
		TestUtils.printLog("\nAfter update collection:");
		for (User u : copiedCollection) {
			TestUtils.printLog(u.name);
		}

		TestUtils.printLog("\nUpdate unmodified copied collection:");
		try {
			copiedCollection.add(new User("ZJ"));
		} catch (UnsupportedOperationException e) {
			TestUtils.printLog("Exception: " + e.toString());
		}
	}

	private static class User {
		public String name;

		public User(String name) {
			this.name = name;
		}
	}

	@Test
	public void test03Demo() {
		// ConcurrentHashMap, modify when iterator
		Map<String, String> myMap = new ConcurrentHashMap<>();
		myMap.put("key1", "test1");
		myMap.put("key2", "test2");
		myMap.put("key3", "test3");
		myMap.putIfAbsent("key2", "test2");
		TestUtils.printLog("ConcurrentHashMap before iterator: " + myMap);

		Iterator<String> it = myMap.keySet().iterator();
		while (it.hasNext()) {
			String tmpKey = it.next();
			if (tmpKey.equals("key2")) {
				myMap.put("key3_new", "test3_new");
			}
		}

		TestUtils.printLog("ConcurrentHashMap after iterator: " + myMap);
	}

	@Test
	public void test04GenericDemo() {
		// generic class
		Info<String> info = new InfoImpl<>("ZJ Test");
		TestUtils.printLog("results: " + info.getVar());
	}

	private static class InfoImpl<T> implements Info<T> {

		private T var;

		public InfoImpl(T var) {
			this.setVar(var);
		}

		public void setVar(T var) {
			this.var = var;
		}

		@Override
		public T getVar() {
			return this.var;
		}
	}

	private interface Info<T> {
		public T getVar();
	}

	@Test
	public void test05GenericDemo() {
		// generic method
		GenericTestInfo info = new GenericTestInfo();
		TestUtils.printLog(info.fun("coder", "print second generic param"));
		TestUtils.printLog(info.fun(30, "print second param again"));
	}

	private static class GenericTestInfo {

		public <T, S> T fun(T t, S s) {
			TestUtils.printLog(s.toString());
			return t;
		}
	}

	@Test
	public void test06Demo() {
		// Arrays.asList() and Collections.singletonList()
		String[] initArr = { "One", "Two", "Three", "Four", };

		List<String> tmpLst1 = new ArrayList<>(Arrays.asList(initArr));
		TestUtils.printLog(tmpLst1);
		tmpLst1.set(0, "first"); // update value
		tmpLst1.add("Five"); // update structure
		TestUtils.printLog(tmpLst1);

		TestUtils.printLog("After update, array items:");
		for (String item : initArr) {
			TestUtils.printLog("item: " + item);
		}

		// the capacity of the List returned by
		// Collections.singletonList(something) will always be 1
		List<String> tmpLst2 = Collections.singletonList("OnlyOneElement");
		TestUtils.printLog(tmpLst2);

		try {
			tmpLst2.set(0, "update");
			TestUtils.printLog(tmpLst2);
		} catch (UnsupportedOperationException e) {
			TestUtils.printLog("Any changes made to the List returned by  "
					+ "Collections.singletonList(something) will result in "
					+ "UnsupportedOperationException");
		}
	}

	@Test
	public void test07Demo() {
		// test instance variable initialize sequence
		TestUtils.printLog("class: " + this.getClass().getSimpleName());
		TestUtils.printLog("value: " + this.testValue);

		// variables access in inner class
		innerCls cls = this.new innerCls();
		cls.testPrint();
	}

}
