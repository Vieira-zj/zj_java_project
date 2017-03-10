package com.zhengjin.java.demo;

public enum EnumStatus {

	DELETE("0", "DEL"), ENABLE("1", "AVAILABLE"), DISABLE("2", "UNAVAILABLE");

	private final String code;
	private final String name;

	private EnumStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getCode() {
		return this.code;
	}

}
