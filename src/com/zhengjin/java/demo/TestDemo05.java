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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo05 {

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
	public void testDemo06() {
		// sort for HashMap by entry value
		Map<String, Integer> inputMap = new HashMap<>(20);
		inputMap.put("Five", 5);
		inputMap.put("Seven", 7);
		inputMap.put("Eight", 8);
		inputMap.put("One", 1);
		inputMap.put("Two", 2);
		inputMap.put("Three", 3);

		Map<String, Integer> sortedMap = sortMapByValue(inputMap);
		TestUtils.printLog("\nMap keys and values after sort:");
		for (Entry<String, Integer> entry : sortedMap.entrySet()) {
			TestUtils.printLog(entry.getKey() + "-" + entry.getValue());
		}
	}

	private Map<String, Integer> sortMapByValue(Map<String, Integer> inputMap) {
		Set<Entry<String, Integer>> mapEnties = inputMap.entrySet();

		// HashMap entries print as random
		TestUtils.printLog("Map keys and values before sort:");
		for (Entry<String, Integer> entry : mapEnties) {
			TestUtils.printLog(entry.getKey() + "-" + entry.getValue());
		}

		// sort by LinkedList
		List<Entry<String, Integer>> tmpList = new LinkedList<>(mapEnties);
		Collections.sort(tmpList, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> ele1, Entry<String, Integer> ele2) {
				return ele1.getValue().compareTo(ele2.getValue());
			}
		});

		// store sorted entries by LinkedHashMap
		Map<String, Integer> sortedMap = new LinkedHashMap<>(20);
		for (Entry<String, Integer> entry : tmpList) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
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
		String[] testArr = { "a", "d", "c", "c", "e", "c", "x", "d", "a", "w" };
		String[] retArr;

		ArrayDistinct myDistinct = new ArrayDistinct();
		retArr = myDistinct.arrayDistinct01(Arrays.copyOf(testArr, testArr.length));
		TestUtils.printLog("Distinct: " + Arrays.toString(retArr));

		retArr = myDistinct.arrayDistinct02(Arrays.copyOf(testArr, testArr.length));
		TestUtils.printLog("Distinct: " + Arrays.toString(retArr));

		retArr = myDistinct.arrayDistinct03(Arrays.copyOf(testArr, testArr.length));
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
			Arrays.sort(srcArr);
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

}
