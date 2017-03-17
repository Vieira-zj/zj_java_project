package com.zhengjin.java.demo;

public final class MyTestClass02 extends MyTestClass01 {

	private String key;

	public MyTestClass02(String key, int value) {
		super(value);
		this.key = key;
	}

	public boolean Compare(MyTestClass02 another) {
		if (this.key != another.key) {
			return false;
		}
		return (this.value - another.value) > 0 ? true : false;
	}

	// Builder mode
	private int a;
	private int b;
	private int c;
	private int d;

	public static class Builder {

		private final int a;
		private final int b;
		private int c = 0; // set default value manual
		private int d = 0;

		public Builder(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public Builder setC(int c) {
			this.c = c;
			return this;
		}

		public Builder setD(int d) {
			this.d = d;
			return this;
		}

		public MyTestClass02 build() {
			return new MyTestClass02(this);
		}
	}

	public MyTestClass02(Builder builder) {
		super(builder.a);
		this.a = builder.a;
		this.b = builder.b;
		this.c = builder.c;
		this.d = builder.d;
	}

	public void myPrint() {
		System.out.printf("a=%d, b=%d, c=%d, d=%d", a, b, c, d);
	}

}
