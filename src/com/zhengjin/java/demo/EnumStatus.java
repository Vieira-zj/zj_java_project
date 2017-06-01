package com.zhengjin.java.demo;

public enum EnumStatus {
	// 枚举中还有一个ordinal()方法返回一个int值，这是每个enum实例在声明时的次序，从0开始。
	// 枚举类还实现了Compareable接口，所以他具有compareTo()方法。
	// 同时还实现了Serializable接口，还自动为你提供了equals()和hashCode()方法。
	// 除了不能继承一个枚举类之外，我们基本上可以把枚举类当成一个常规的Java类，可以往其中添加新的方法，包括抽象方法甚至main方法。

	DELETE("0", "DEL"), ENABLE("1", "AVAILABLE"), DISABLE("2", "UNAVAILABLE");

	private final String code;
	private final String name;

	private EnumStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

}
