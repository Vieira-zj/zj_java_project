package com.zhengjin.app.demo;

/**
 * 
 * 1. 通过ThreadLocal创建的副本是存储在每个线程自己的threadLocals中的；
 * 2. 为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象？
 * 因为每个线程中可有多个threadLocal变量，就像longLocal和stringLocal；
 * 3. 在进行get之前，必须先set，否则会报空指针异常；或者重写initialValue()方法。
 * 
 */
public class ThreadLocalTest2 {

	ThreadLocal<Long> longLocal = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return Thread.currentThread().getId();
		}
	};

	ThreadLocal<String> stringLocal = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return Thread.currentThread().getName();
		}
	};

	private long getLong() {
		return longLocal.get();
	}

	private String getString() {
		return stringLocal.get();
	}

	void printThreadInfo() {
		System.out.println(String.format("thread-id:%d, thread-name:%s", this.getLong(), this.getString()));
	}

	void clear() {
		// help GC
		longLocal.remove();
		stringLocal.remove();
	}

	public static void main(String[] args) throws InterruptedException {

		final ThreadLocalTest2 test = new ThreadLocalTest2();
		test.printThreadInfo();

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				test.printThreadInfo();
			}
		});
		t.start();
		t.join();

		test.printThreadInfo();
		test.clear();
		Thread.sleep(1000L);
		System.out.println(test.getClass().getSimpleName() + " Done");
	}
}
