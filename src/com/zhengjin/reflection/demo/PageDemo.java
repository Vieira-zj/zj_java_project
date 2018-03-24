package com.zhengjin.reflection.demo;

public class PageDemo {

	public PageDemo() {
		PageUtil.initialLevel(this);
	}

	@FindBy(name = "tester1", age = 21)
	private TestInfo info1;

	@FindBy(name = "tester2", age = 25, level = 1)
	private TestInfo info2;

	public static void main(String[] args) {

		PageDemo demo = new PageDemo();
		System.out.println("info1: " + demo.info1);
		System.out.println("info2: " + demo.info2);

		PageUtil.initialLevel(demo, 1);
		System.out.println("info2: " + demo.info2);
	}

}
