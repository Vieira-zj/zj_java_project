package com.zhengjin.app.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueTest {

	public static void main(String[] args) {
		List<Producer> producers = new ArrayList<Producer>(10);
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);

		for (int i = 0; i < 3; i++) {
			producers.add(new Producer(queue));
		}
		Consumer consumer = new Consumer(queue);

		ExecutorService service = Executors.newCachedThreadPool();
		for (Producer p : producers) {
			service.execute(p);
		}
		service.execute(consumer);

        try {
        	for (int i = 0; i < 11; i++) {
    			Thread.sleep(1000L);
				System.out.println( "queue size: " + queue.size());
        	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Producer p : producers) {
			p.stop();
		}

        try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        service.shutdown();
	}

	private static class Producer implements Runnable {

		private static AtomicInteger count = new AtomicInteger(1);

		private volatile boolean isRunning = true;
		private BlockingQueue<Integer> queue;

		public Producer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			String tag = String.format("[%s] Producer, ", Thread.currentThread().getName());
			int int_num = 0;
			Random r = new Random();

			System.out.println(tag + "start.");
			try {
				while (isRunning) {
					System.out.println(tag + "building a number ...");
					int_num = count.incrementAndGet();
					System.out.println(tag + String.format("put a number %d into queue.", int_num));
					if (!this.queue.offer(int_num, 2, TimeUnit.SECONDS)) {
						System.out.println(tag + "put number FAILED: " + int_num);
					}
					Thread.sleep(r.nextInt(3) * 1000L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println(tag + "exit.");
			}
		}
	
		public void stop() {
	        this.isRunning = false;
	    }
	}

	private static class Consumer implements Runnable {

		private BlockingQueue<Integer> queue;

		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		public void run() {
			String tag = String.format("[%s] Consumer, ", Thread.currentThread().getName());
			System.out.println(tag + "start.");
			Random r = new Random();

			try {
				while (true) {
					System.out.println(tag + "consuming a number ...");
					Integer int_num = queue.poll(2, TimeUnit.SECONDS);
					if (int_num == null) {
						return;
					}
					Thread.sleep(r.nextInt(3) * 1000L);
					System.out.println(tag + "get a number and consume: " + int_num);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println(tag + "exit.");
			}
		}
	}
	
}
