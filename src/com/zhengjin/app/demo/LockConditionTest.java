package com.zhengjin.app.demo;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {

	private static final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {

		BoundedQueue queue = new BoundedQueue(5);

		for (int i = 0; i < 7; i++) {
			new Thread(new Producter(queue), String.valueOf(i)).start();
		}

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < 8; i++) {
			new Thread(new Consumer(queue), String.valueOf(i)).start();
		}

		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 3; i++) {
			new Thread(new Producter(queue), String.valueOf(i)).start();
		}

		System.out.println("lock condition test done.");
	}

	static class Producter implements Runnable {
		private BoundedQueue queue;

		public Producter(BoundedQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				queue.add(new Integer(random.nextInt(100)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class Consumer implements Runnable {
		private BoundedQueue queue;

		public Consumer(BoundedQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				queue.remove();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 同步阻塞队列
	private static class BoundedQueue {
		private Integer[] items;
		private Lock lock = new ReentrantLock();
		// 设置多个同步条件
		private Condition addCondition = lock.newCondition();
		private Condition removeCondition = lock.newCondition();

		private int count;
		private int addIndex, removeIndex;

		public BoundedQueue(int size) {
			this.items = new Integer[size];
		}

		public void add(Integer object) throws InterruptedException {
			lock.lock();
			try {
				while (count == items.length) {
					System.out.println(Thread.currentThread() + " wait for not full.");
					addCondition.await();
				}
				items[addIndex] = object;
				if (++addIndex == items.length) {
					addIndex = 0;
				}
				count++;
				System.out.println(Thread.currentThread() + " insert a item, and array: " + Arrays.toString(items));
				removeCondition.signal();
			} finally {
				lock.unlock();
			}
		}

		public Integer remove() throws InterruptedException {
			lock.lock();
			try {
				while (count == 0) {
					System.out.println(Thread.currentThread() + " wait for not empty.");
					removeCondition.await();
				}
				Integer retInt = items[removeIndex];
				items[removeIndex] = null;
				System.out.println(Thread.currentThread() + " remove a item, and array: " + Arrays.toString(items));
				if (++removeIndex == items.length) {
					removeIndex = 0;
				}
				count--;
				addCondition.signal();
				return retInt;
			} finally {
				lock.unlock();
			}
		}
	}

}
