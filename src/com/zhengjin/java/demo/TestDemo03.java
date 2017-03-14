package com.zhengjin.java.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.zhengjin.apis.testutils.TestUtils.printLog;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo03 {

	@Test
	public void test01Demo() {
		// Arrays.equals()
		String[] tmpArrSrc = { "test1", "test2", "test3" };
		String[] tmpArrTarget = new String[] { "test1", "test2", "test3" };
		boolean ret = Arrays.equals(tmpArrSrc, tmpArrTarget);
		printLog("Compare results: " + ret);
	}

	@Test
	public void test02Demo() {
		// Arrays.copyOf()
		String[] tmpArr = { "1", "2", "3", "4", "5", "6", "7", "8" };
		tmpArr = Arrays.copyOf(tmpArr, 5);
		for (String item : tmpArr) {
			printLog(item);
		}
	}

	@Test
	public void test03Demo() {
		// string reverse
		StringBuilder sb = new StringBuilder("abcd");
		printLog(sb.reverse().toString());
	}

	@Test
	public void test04Demo() {
		// System.arraycopy()
		String[] arrSrc = { "1", "2", "3", "4", "5", "6", "7", "8" };
		String[] arrTarget = { "a", "b", "c", "d" };
		for (String item : arrTarget) {
			printLog(item);
		}

		// Copy 4 elements from array 'arrSrc' starting at offset 3 to array
		// 'arrTarget' starting at offset 0
		printLog("After update: ");
		System.arraycopy(arrSrc, 3, arrTarget, 0, 4);
		for (String item : arrTarget) {
			printLog(item);
		}
	}

	@Test
	public void test05Demo() {
		// reflection
		String tmpStr = "Zheng Jin";
		try {
			Field valueFieldOfString = String.class.getDeclaredField("value");
			valueFieldOfString.setAccessible(true);
			char[] value = (char[]) valueFieldOfString.get(tmpStr);
			value[5] = '_';
		} catch (Exception e) {
			e.printStackTrace();
		}

		printLog("After updated, tmpStr = " + tmpStr);
	}

	@Test
	public void test06Demo() {
		// serialized
		Employee employee = new Employee();
		employee.name = "Reyan Ali";
		employee.address = "Phokka Kuan, Ambehta Peer";
		employee.SSN = 11122333;
		employee.number = 101;

		try {
			FileOutputStream fileOut = new FileOutputStream("d:\\employee.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(employee);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test07Demo() {
		// de-serialized
		Employee employee = null;

		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("d:\\employee.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			employee = (Employee) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		printLog("Deserialized Employee...");
		printLog("Name: " + employee.name);
		printLog("Address: " + employee.address);
		printLog("SSN: " + employee.SSN);
		printLog("Number: " + employee.number);
	}

	@SuppressWarnings({ "serial", "unused" })
	private static class Employee implements Serializable {

		public String name;
		public String address;
		public transient int SSN;
		public int number;

		public void mailCheck() {
			printLog("Mailing a check to " + name + " " + address);
		}
	}

	@Test
	public void test20Demo() {
		// TODO
	}

}
