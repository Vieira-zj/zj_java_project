package com.zhengjin.app.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {

		final int count = 3;
		final ConnectPool pool = new ConnectPool(count);

		for (int i = 0; i < (count + 3); i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					String pName = Thread.currentThread().getName();
					try {
						System.out.println(pName + " wait for a connect ...");
						Connect c = pool.getConn();
						System.out.println(pName + " get a connect " + c.getName());
						Thread.sleep(new Random().nextInt(5) * 1000L);
						pool.releaseConn(c);
						System.out.println(pName + " done, and release a connect " + c.getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	private static class Connect {

		private String name;

		public Connect(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	private static class ConnectPool {

		private List<Connect> pool;
		private Semaphore semaphore = new Semaphore(3);

		public ConnectPool(int count) {
			pool = new ArrayList<Connect>(count);
			for (int i = 0; i < count; i++) {
				pool.add(new Connect("conn_" + String.valueOf(i)));
			}
		}

		public Connect getConn() throws InterruptedException {
			Connect c = null;
			semaphore.acquire();
			synchronized (pool) { // need sync here
				c = pool.remove(0);
			}
			return c;
		}

		public void releaseConn(Connect c) {
			pool.add(c);
			semaphore.release();
		}
	}

}
