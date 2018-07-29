package com.zhengjin.java.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo05 {

	private String testInstanceValue = "test";

	@Test
	public void testDemo01() {
		// Hamcrest demo
		User user = new User("Tester", "13868000000", "ShangHai");

		MatcherAssert.assertThat("user name check", user.getUserName(), Matchers.equalTo("Tester"));
		MatcherAssert.assertThat("mobile check", user.getMobile(), Matchers.equalTo("13868000000"));
		MatcherAssert.assertThat("address check", user.getAddress(), Matchers.equalTo("ShangHai"));

		MatcherAssert.assertThat("mobile check", user.getMobile(),
				Matchers.allOf(Matchers.is(Matchers.notNullValue()), Matchers.startsWith("138")));
	}

	@Test
	public void testDemo02() {
		// Hamcrest examples
		// Number
		MatcherAssert.assertThat(5, Matchers.allOf(Matchers.greaterThan(1), Matchers.lessThan(10)));

		// String
		String tmpStr = "test";
		MatcherAssert.assertThat(tmpStr, Matchers.is("test"));
		MatcherAssert.assertThat(tmpStr, Matchers.equalToIgnoringCase("TEST"));
		MatcherAssert.assertThat(tmpStr, Matchers.not("tester"));
		MatcherAssert.assertThat(tmpStr, Matchers.containsString("st"));

		// List
		List<String> tmpLst = Arrays.asList(new String[] { "a", "b", "c" });
		MatcherAssert.assertThat(tmpLst, Matchers.hasItem("b"));

		// Map
		Map<String, String> tmpMap = new HashMap<>(10);
		tmpMap.put("k1", "v1");
		tmpMap.put("k2", "v2");
		tmpMap.put("k3", "v3");
		MatcherAssert.assertThat(tmpMap, Matchers.hasEntry("k1", "v1"));
		MatcherAssert.assertThat(tmpMap, Matchers.hasKey("k2"));
		MatcherAssert.assertThat(tmpMap, Matchers.hasValue("v3"));
	}

	@Test
	public void testDemo03() {
		// AssertJ demo
		User user = new User("Tester", "13868000000", "ShangHai");

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(user.getUserName().equals("Tester"));
			softly.assertThat(user.getMobile().equals("13868000000"));
			softly.assertThat(user.getAddress().equals("ShangHai"));
		});

		Assertions.assertThat(user.getMobile()).isNotNull().startsWith("138").hasSize(11);
	}

	@Test
	public void testDemo04() {
		// AssertJ examples
		// Number
		Assertions.assertThat(5).isGreaterThan(1).isLessThan(10);

		// String
		Assertions.assertThat("test").isInstanceOf(String.class).isEqualToIgnoringCase("TEST");
		Assertions.assertThat("hello world").startsWith("hello").endsWith("world");

		// Collection
		List<String> tmpLst = Arrays.asList(new String[] { "a", "b", "c" });
		Assertions.assertThat(tmpLst).hasSize(3).contains("a", "c").doesNotContain("test");
	}

	private static class User {

		private final String name;
		private final String mobile;
		private final String address;

		public User(String userName, String mobile, String address) {
			this.name = userName;
			this.mobile = mobile;
			this.address = address;
		}

		public String getUserName() {
			return this.name;
		}

		public String getMobile() {
			return this.mobile;
		}

		public String getAddress() {
			return this.address;
		}

		@Override
		public String toString() {
			return this.name + " - " + this.mobile + " - " + this.address;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}

			User another;
			if (obj instanceof User) {
				another = (User) obj;
			} else {
				return false;
			}

			return this.name.equals(another.name) && this.mobile.equals(another.mobile)
					&& this.address.equals(another.address);
		}

		@Override
		public int hashCode() {
			return this.name.hashCode() + this.mobile.hashCode() * 17;
		}
	}

	@Test
	public void testDemo05() {
		// HaspMap
		User user1 = new User("user1", "11111111", "ShangHai");
		User user2 = new User("user1", "11111111", "ShangHai");
		User user3 = new User("user1", "11111111", "WuHan");
		User user4 = new User("user4", "44444444", "ShengZhen");
		User user5 = new User("user5", "55555555", "GuangZhou");

		HashMap<User, String> map = new HashMap<>(10);
		map.put(user1, "equals1");
		map.put(user2, "equals2"); // override "user1" value
		map.put(user3, "hashcode equals");
		map.put(user4, "new user4");
		map.put(user5, "new user5");

		for (Entry<User, String> entry : map.entrySet()) {
			TestUtils.printLog("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
	}

	@Test
	public void testDemo0601() {
		// sort map by entry value
		Map<String, Integer> inputMap = new HashMap<>(20);
		inputMap.put("Five", 5);
		inputMap.put("Seven", 7);
		inputMap.put("Eight", 8);
		inputMap.put("One", 1);
		inputMap.put("Two", 2);
		inputMap.put("Three", 3);

		// HashMap entries print as random
		TestUtils.printLog("Map keys and values before sort:");
		TestUtils.printLog(inputMap.entrySet().toString());

		SortMapByValue sortMap = new SortMapByValue();
		Map<String, Integer> sortedMap01 = sortMap.sortMapByValue01(inputMap);
		TestUtils.printLog("\nMap keys and values after sort:");
		TestUtils.printLog(sortedMap01.entrySet().toString());

		Map<String, Integer> sortedMap02 = sortMap.sortMapByValue02(inputMap);
		TestUtils.printLog("\nMap keys and values after sort:");
		TestUtils.printLog(sortedMap02.entrySet().toString());
	}

	private static class SortMapByValue {

		public Map<String, Integer> sortMapByValue01(Map<String, Integer> inputMap) {
			// sort by LinkedList
			List<Entry<String, Integer>> tmpList = new LinkedList<>(inputMap.entrySet());
			Collections.sort(tmpList, new MyEntryValueComparator());
			return buildSortedMapByEntries(tmpList);
		}

		public Map<String, Integer> sortMapByValue02(Map<String, Integer> inputMap) {
			// sort by ArrayList
			List<Entry<String, Integer>> tmpList = new ArrayList<>(inputMap.entrySet());
			Collections.sort(tmpList, new MyEntryValueComparator());
			return buildSortedMapByEntries(tmpList);
		}

		private Map<String, Integer> buildSortedMapByEntries(List<Entry<String, Integer>> inputList) {
			// store sorted entries by LinkedHashMap
			Map<String, Integer> sortedMap = new LinkedHashMap<>(20);
			for (Entry<String, Integer> entry : inputList) {
				sortedMap.put(entry.getKey(), entry.getValue());
			}
			return sortedMap;
		}

		private class MyEntryValueComparator implements Comparator<Entry<String, Integer>> {
			@Override
			public int compare(Entry<String, Integer> ele1, Entry<String, Integer> ele2) {
				return ele1.getValue().compareTo(ele2.getValue());
			}
		}
	}

	@Test
	public void testDemo0602() {
		// sort map by key base TreeMap
		Map<Integer, String> tmpMap = new LinkedHashMap<>(20);
		tmpMap.put(5, "Five");
		tmpMap.put(7, "Seven");
		tmpMap.put(8, "Eight");
		tmpMap.put(1, "One");
		tmpMap.put(2, "Two");
		tmpMap.put(3, "Three");

		TestUtils.printLog("Map keys and values before sort:");
		TestUtils.printLog(tmpMap.entrySet().toString());

		SortedMap<Integer, String> sortedMap = new TreeMap<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer v1, Integer v2) {
				return v1.compareTo(v2);
			}
		});
		sortedMap.putAll(tmpMap);

		TestUtils.printLog("Map keys and values after sort:");
		TestUtils.printLog(sortedMap.entrySet().toString());
	}

	@Test
	public void testDemo07() {
		// list file for directory
		this.printListFile("d:\\");
	}

	private void printListFile(String absDirPath) {
		File dir = new File(absDirPath);
		if (!dir.exists()) {
			TestUtils.printLog("The dir is not exists => " + dir.getAbsolutePath());
			return;
		}
		if (!dir.isDirectory()) {
			TestUtils.printLog("Dir is not directory => " + dir.getAbsolutePath());
		}

		String[] children = dir.list();
		if (children.length == 0) {
			TestUtils.printLog("There is no dir and file in directory => " + dir.getAbsolutePath());
			return;
		}

		TestUtils.printLog("list file:");
		TestUtils.printLog(Arrays.toString(children));
	}

	@Test
	public void testDemo08() {
		// list file for directory, and filter by file name
		FilenameFilter myFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("ZJ");
			}
		};
		printListFileByFilenameFilter("d:\\", myFilter);
	}

	private void printListFileByFilenameFilter(String absDirPath, FilenameFilter filter) {
		File dir = new File(absDirPath);
		String[] children = dir.list(filter);
		if (children == null) {
			TestUtils.printLog("Either dir does not exist or is not a directory.");
			return;
		}

		TestUtils.printLog("list file:");
		TestUtils.printLog(Arrays.toString(children));
	}

	@Test
	public void testDemo09() {
		// list file for directory, and filter by File
		printListFileByFileFilter("D:\\ZJ_Tmp_files", new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
	}

	private void printListFileByFileFilter(String absDirPath, FileFilter filter) {
		File dir = new File(absDirPath);
		File[] children = dir.listFiles(filter);
		if (children == null) {
			TestUtils.printLog("Either dir does not exist or is not a directory.");
			return;
		}

		TestUtils.printLog("list file:");
		for (int i = 0, len = children.length; i < len; i++) {
			TestUtils.printLog(children[i].getAbsolutePath());
		}
	}

	@Test
	public void testDemo10() {
		// resize array
		int[] tmpArray = { 1, 2, 3 };
		tmpArray = (int[]) resizeArray(tmpArray, 5);
		tmpArray[3] = 4;
		tmpArray[4] = 5;
		TestUtils.printLog("Results: " + Arrays.toString(tmpArray));
	}

	private Object resizeArray(Object oldArray, int newSize) {
		// use Object for all types array
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);

		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	@Test
	public void testDemo11() {
		// array elements distinct
		String[] testArr = { "a", "d", "c", "c", "e", "c", "x", "d", "a", "w" };
		String[] retArr;

		ArrayDistinct myDistinct = new ArrayDistinct();
		retArr = myDistinct.arrayDistinct01(testArr);
		TestUtils.printLog("Distinct: " + Arrays.toString(retArr));

		retArr = myDistinct.arrayDistinct02(Arrays.copyOf(testArr, testArr.length));
		TestUtils.printLog("Distinct: " + Arrays.toString(retArr));

		retArr = myDistinct.arrayDistinct03(testArr);
		TestUtils.printLog("Distinct: " + Arrays.toString(retArr));

		List<String> tmpList = Arrays.asList(testArr);
		TestUtils.printLog("Source: " + tmpList.toString());
		HashSet<String> tmpHashSet = new HashSet<>(tmpList);
		TestUtils.printLog("Distinct by HashSet: " + tmpHashSet.toString());
		LinkedHashSet<String> tmpLinkedSet = new LinkedHashSet<>(tmpList);
		TestUtils.printLog("Distinct by LinkedHashSet: " + tmpLinkedSet.toString());
	}

	private static class ArrayDistinct {

		public String[] arrayDistinct01(String[] srcArr) {
			List<String> retList = new ArrayList<>(srcArr.length * 2);

			TestUtils.printLog("Source: " + Arrays.toString(srcArr));
			for (String word : srcArr) {
				if (retList.indexOf(word) == -1) {
					retList.add(word);
				}
			}
			return retList.toArray(new String[0]);
		}

		public String[] arrayDistinct02(String[] srcArr) {
			List<String> retList = new ArrayList<>(srcArr.length * 2);

			TestUtils.printLog("Source: " + Arrays.toString(srcArr));
			Arrays.sort(srcArr); // change source array
			retList.add(srcArr[0]);
			for (int i = 1, len = srcArr.length; i < len; i++) {
				if (!srcArr[i].equals(retList.get(retList.size() - 1))) {
					retList.add(srcArr[i]);
				}
			}
			return retList.toArray(new String[retList.size()]);
		}

		public String[] arrayDistinct03(String[] srcArr) {
			LinkedHashMap<String, Integer> tmpMap = new LinkedHashMap<>(srcArr.length * 2);

			TestUtils.printLog("Source: " + Arrays.toString(srcArr));
			for (String word : srcArr) {
				if (!tmpMap.containsKey(word)) {
					tmpMap.put(word, 1);
				}
			}
			return tmpMap.keySet().toArray(new String[0]);
		}
	}

	@Test
	public void testDemo12() {
		// WeakHashMap
		Map<String, User> map = new WeakHashMap<>(20);
		String key1 = "reference key1";
		String key2 = "weak reference key2";

		map.put(key1, new User("user1", "11112222", "WuHan"));
		map.put(key2, new User("user2", "22223333", "ShangHai"));
		map.put(new String("weak reference key3"), new User("user3", "12345678", "BeiJing"));
		TestUtils.printLog("size: " + map.size());
		for (Map.Entry<String, User> entry : map.entrySet()) {
			TestUtils.printLog("key: " + entry.getKey() + " => value: " + entry.getValue());
		}

		key2 = null;
		System.gc();
		try {
			final long wait = 3000L;
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TestUtils.printLog("After GC");
		TestUtils.printLog("size: " + map.size());
		TestUtils.printLog(map);
	}

	@Test
	public void testDemo13() {
		TestDemo05 td1 = new TestDemo05();
		TestUtils.printLog("instance value: " + td1.testInstanceValue);
		td1.testInstanceValue = "update test";
		TestUtils.printLog("instance value, updated: " + td1.testInstanceValue);

		TestDemo05 td2 = new TestDemo05();
		TestUtils.printLog("instance value: " + td2.testInstanceValue);

		TestUtils.printLog("instance value: " + this.testInstanceValue);
	}

	@Test
	public void testDemo14() {
		// TreeSet for sort
		Set<Student> students = new TreeSet<>();
		students.add(new Student("zhangsan", 2));
		students.add(new Student("lisi", 1));
		students.add(new Student("wangwu", 3));
		students.add(new Student("mazi", 3));
		TestUtils.printLog("students: " + students);

		TestUtils.printLog("students:");
		Iterator<Student> iter = students.iterator();
		while (iter.hasNext()) {
			Student s = iter.next();
			System.out.printf("serial no=%d, name=%s\n", s.num, s.name);
		}
	}

	private static class Student implements Comparable<Student> {

		int num;
		String name;

		Student(String name, int num) {
			this.num = num;
			this.name = name;
		}

		public String toString() {
			return "serial no: " + this.num + ", name: " + this.name;
		}

		@Override
		public int compareTo(Student t) {
			int result = this.num > t.num ? 1 : (this.num == t.num ? 0 : -1);
			if (result == 0) {
				result = this.name.compareTo(t.name);
			}
			return result;
		}
	}

}
