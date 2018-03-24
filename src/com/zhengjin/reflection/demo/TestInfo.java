package com.zhengjin.reflection.demo;

public class TestInfo {

	private String name;
	private int age;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("TestInfo [name= %s age=%d]", this.name, this.age);
	}
}
