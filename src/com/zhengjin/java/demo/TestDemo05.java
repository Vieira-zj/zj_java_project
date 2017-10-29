package com.zhengjin.java.demo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	}

	@Test
	public void testDemo05() {
		// sort for HashMap by entry value
		Map<String, Integer> inputMap = new HashMap<>(20);
		inputMap.put("Five", 5);
		inputMap.put("Seven", 7);
		inputMap.put("Eight", 8);
		inputMap.put("One", 1);
		inputMap.put("Two", 2);
		inputMap.put("Three", 3);

		sortMapByValue(inputMap);
	}

	private void sortMapByValue(Map<String, Integer> inputMap) {
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

		TestUtils.printLog("\nMap keys and values after sort:");
		for (Entry<String, Integer> entry : sortedMap.entrySet()) {
			TestUtils.printLog(entry.getKey() + "-" + entry.getValue());
		}
	}

}
