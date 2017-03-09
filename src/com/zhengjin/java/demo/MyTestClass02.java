package com.zhengjin.java.demo;

public final class MyTestClass02 extends MyTestClass01 {

	private String key;
	private int value;
	
	public MyTestClass02(String key, int value) {
		super(value);
		this.key = key;
		this.value = value;
	}
	
	public boolean Compare(MyTestClass02 another) {
		if (this.key != another.key) {
			return false;
		}
		return (this.value - another.value) > 0 ? true : false;
	}

}
