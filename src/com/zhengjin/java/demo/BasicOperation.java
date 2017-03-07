package com.zhengjin.java.demo;

public enum BasicOperation implements IOperation {

	PLUS("+") {
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		public double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		public double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/") {
		public double apply(double x, double y) {
			return x / y;
		}
	};

	@SuppressWarnings("unused")
	private final String symbool;

	private BasicOperation(String symbool) {
		this.symbool = symbool;
	}

	@Override
	public double apply(double x, double y) {
		return 0;
	}

}
