package com.zhengjin.java.demo;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo02 {

	@Test
	public void test01Demo() {
		Assert.fail("Test assert fail.");
	}

	@Test
	@Ignore
	public void test02Demo() {
		Assert.assertTrue("Test assert ignore.", true);
	}

	@Test
	public void test03Demo() {
		Assert.assertTrue("Test assert true.", true);
	}

//	public static void main(String[] args) {
//		JUnitCore runner = new JUnitCore();
//		runner.addListener(new ExecutionListener());
//		runner.run(TestDemo02.class);
//	}

}
