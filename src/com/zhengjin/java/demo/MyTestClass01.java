package com.zhengjin.java.demo;

public class MyTestClass01 {

	private int value;

	public MyTestClass01(int value) {
		this.value = value;
	}

	public boolean Compare(MyTestClass01 another) {
		return (this.value - another.value) > 0 ? true : false;
	}

}
