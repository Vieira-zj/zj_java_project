package com.zhengjin.app.demo;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
import java.util.Vector;

import com.zhengjin.apis.testutils.TestUtils;

public final class ThreadWaitNotifyTest {

	public static void main(String args[]) {
		// lock, wait() and notify() are based on 'goods'
		Vector<String> goods = new Vector<>();
//		List<String> goods = Collections.synchronizedList(new ArrayList<String>(10));
		
		Thread consumer = new Thread(new Consumer(goods));
		Thread productor = new Thread(new Productor(goods));
		consumer.start();
		productor.start();
	}

	private static class Consumer implements Runnable {

		private Vector<String> goods;

		public Consumer(Vector<String> goods) {
			this.goods = goods;
		}

		@Override
		public void run() {
			// if get lock of 'goods', then run sync block
			synchronized (goods) {
				while (true) {
					try {
						TestUtils.printLog("Consumer start ...");
						while (goods.size() == 0) {
							TestUtils.printLog("Consumer wait ...");
							// wait, and release lock on 'goods'
							goods.wait();
							// in Product thread, if notify() and release lock,
							// continue here
						}
						TestUtils.printLog("Goods size: " + goods.size());
						TestUtils.printLog("Consumer: goods have been taken");
						goods.clear(); // consume goods
						// awake Product thread (but not release lock here)
						goods.notify();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}  // release lock
		}
	}

	public static class Productor implements Runnable {

		private Vector<String> goods;

		public Productor(Vector<String> goods) {
			this.goods = goods;
		}

		@Override
		public void run() {
			// if get lock of 'goods', then run sync block
			synchronized (goods) {
				while (true) {
					try {
						TestUtils.printLog("Productor start ...");
						while (goods.size() != 0) {
							TestUtils.printLog("Productor wait ...");
							// wait, and release lock on 'goods'
							goods.wait();
							// in Consumer thread, if notify() and release lock,
							// continue here
						}
						// take time to product goods
						goods.add(new String("apples"));
						Thread.sleep(2000);
						// awake Consumer thread (but not release lock)
						TestUtils.printLog("Productor: goods are ready");
						goods.notify();
						Thread.sleep(2000);
						TestUtils.printLog("Productor: do somethings else");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}  // release lock
		}
	}
}
