package com.zhengjin.app.demo;

public class WaitNotifyTest1 {

	private static String lock = "test";

	public static void main(String args[]) {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				String pName = Thread.currentThread().getName();
				System.out.println(pName + " started ...");
				synchronized (lock) {
					System.out.println(pName + " get lock and running ...");
					try {
						Thread.sleep(2000L);
						System.out.println(pName + " release lock and wait.");
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(pName + " get lock and running ...");
				}
				System.out.println(pName + " continue running ...");
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				String pName = Thread.currentThread().getName();
				System.out.println(pName + " started ...");
				synchronized (lock) {
					System.out.println(pName + " get lock and running ...");
					try {
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					lock.notify();
					System.out.println(pName + " released lock and running 2 secs.");
					try {
						Thread.sleep(2000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(pName + " continue running ...");
			}
		});

		t1.start();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("wait notify test done.");
	}

}
