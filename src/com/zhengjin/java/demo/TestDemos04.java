package com.zhengjin.java.demo;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemos04 {

	@Test
	public void test01Demo() {
		// Arrays.equals()
		String[] tmpArr1 = {"Java", "Javascript", "C++"};
		String[] tmpArr2 = {"Java", "Javascript", "C++"};
		String[] tmpArr3 = {"Javascript", "Java", "C++"};
		
		Assert.assertTrue("Content and position equal.", Arrays.equals(tmpArr1, tmpArr2));
		Assert.assertTrue("Only content equal.", Arrays.equals(tmpArr1, tmpArr3));
	}
	
}
