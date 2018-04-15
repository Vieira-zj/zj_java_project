package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class IteratorTest {

	public static void main(String[] args) {

		MyMultiIterator iter = new MyMultiIterator();

		System.out.println("Iterator:");
		for (String word : iter) {
			System.out.print(word + " ");
			System.out.println();
		}

		System.out.println("\nReverse Iterator:");
		for (String word : iter.reverseIterator()) {
			System.out.print(word + " ");
			System.out.println();
		}

		System.out.println("\nRandomized Iterator:");
		for (String word : iter.randomizedIterator()) {
			System.out.print(word + " ");
			System.out.println();
		}
	}

	private static class MyMultiIterator implements Iterable<String> {

		private String[] words = "This is multiple iterator test".split(" ");

		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {

				private int index = 0;

				@Override
				public boolean hasNext() {
					return index < words.length;
				}

				@Override
				public String next() {
					return words[index++];
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		public Iterable<String> reverseIterator() {
			return new Iterable<String>() {

				@Override
				public Iterator<String> iterator() {
					return new Iterator<String>() {

						private int index = words.length - 1;

						@Override
						public boolean hasNext() {
							return index > -1;
						}

						@Override
						public String next() {
							return words[index--];
						}

						@Override
						public void remove() {
							throw new UnsupportedOperationException();
						}
					};
				}
			};
		}

		public Iterable<String> randomizedIterator() {
			List<String> shuffled = new ArrayList<>(Arrays.asList(words));
			Collections.shuffle(shuffled, new Random(47));
			return shuffled;
		}
	}
}
