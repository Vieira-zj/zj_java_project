package com.zhengjin.java.demo;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDemo06 {

	ExCls ex1 = new ExCls("from test"); // #4
	
	static {
		TestUtils.printLog("test class static"); // #1
	}
	
	public TestDemo06() {
		TestUtils.printLog("test class constructor"); // #5
	}
	
	public static void main(String[] args) {
		// test class init order
		new SubTest();
	}
	
	static class ExCls {
		
		static {
			TestUtils.printLog("ex class static"); // #3
		}
		
		public ExCls(String str) {
			TestUtils.printLog("ex class: " + str);
		}
	}
	
	static class SubTest extends TestDemo06 {
		
		ExCls ex = new ExCls("from subTest"); // #6
		
		static {
			TestUtils.printLog("sub test static"); // #2
		}
		
		public SubTest() {
			TestUtils.printLog("sub test class constructor"); // #7
		}
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
}
