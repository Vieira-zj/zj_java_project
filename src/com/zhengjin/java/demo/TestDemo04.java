package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo04 {

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

}
