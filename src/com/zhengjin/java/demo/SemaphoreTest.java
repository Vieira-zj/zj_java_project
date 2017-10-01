package com.zhengjin.java.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	private static class Conn {
	}

	private static class ConnectPool {
		private final List<Conn> pool = new ArrayList<Conn>(3);
		private final Semaphore semaphore = new Semaphore(3);

		public ConnectPool() {
			pool.add(new Conn());
			pool.add(new Conn());
			pool.add(new Conn());
		}

		public Conn getConn() throws InterruptedException {
			semaphore.acquire();
			Conn c = null;
			synchronized (pool) { // still need sync here
				c = pool.remove(0); // remove an element
			}
			System.out.println(Thread.currentThread().getName() + " get a conn " + c);
			return c;
		}

		public void releaseConn(Conn c) {
			pool.add(c);
			System.out.println(Thread.currentThread().getName() + " release a conn " + c);
			semaphore.release();
		}
	}

	public static void main(String[] args) {
		final ConnectPool pool = new ConnectPool();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Conn c = pool.getConn();
					Thread.sleep(3000);
					pool.releaseConn(c);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Conn c = pool.getConn();
						Thread.sleep(4000);
						pool.releaseConn(c);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}).start();
		}
	}
}
