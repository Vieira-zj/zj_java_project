package com.zhengjin.java.demo;

import com.zhengjin.apis.testutils.TestUtils;

public class MyTestClass01 {

	int value;

	public MyTestClass01(int value) {
		this.value = value;
	}

	public boolean Compare(MyTestClass01 another) {
		TestUtils.printLog("Compare() invoked from MyTestClass01");
		return (this.value - another.value) > 0 ? true : false;
	}

}
