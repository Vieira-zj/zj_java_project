package com.zhengjin.generic.demo;

import java.util.Arrays;
import java.util.List;

public class GenericMain {

	private static class MyIntMapper implements Mapper<Integer, Integer> {

		@Override
		public Integer map(Integer item) {
			return item << 1;
		}
	}

	private static class MyStrMapper implements Mapper<Integer, String> {

		@Override
		public String map(Integer item) {
			return "num_" + String.valueOf(item);
		}
	}

	private static class PrintIntHandler implements Handler<Integer> {

		@Override
		public void handle(Integer item) {
			System.out.println("number: " + item);
		}
	}

	private static class PrintStrHandler implements Handler<String> {

		@Override
		public void handle(String item) {
			System.out.println("item: " + item);
		}
	}

	public static void main(String[] args) {

		List<Integer> testList = Arrays.asList(new Integer[] { 1, 2, 3 });

		// #1
		MyRunner<Integer, Integer> intRunner = new MyRunner<Integer, Integer>();
		intRunner.init(testList).map(new MyIntMapper()).forEach(new PrintIntHandler());
		System.out.println();

		// #2
		MyRunner<Integer, String> strRunner = new MyRunner<Integer, String>();
		strRunner.init(testList).map(new MyStrMapper()).forEach(new PrintStrHandler());
	}

}
