package com.zhengjin.app.demo;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * A task generator create task and put into a queue.
 * A task worker take a task from queue and handle task.
 */
public class ThreadPoolWorkerTest {

	private static void wait(int sec) {
		try {
			Thread.sleep(sec * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		final String tag = "[ThreadPoolWorkerTest] main: ";

		Runnable firstTask = new Runnable() {

			@Override
			public void run() {
				String tag = String.format("[%s]: ", Thread.currentThread().getName());
				System.out.println(tag + "first task is running ...");
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					// throw unchecked exception in run()
					throw new RuntimeException(tag + "task is interrupted.");
				}
			}
		};

		// create a task generator
		TaskGenerator generator = new TaskGenerator();
		Thread tGenerator = new Thread(generator, "task_generator");
		generator.thread = tGenerator;
		tGenerator.start();

		// create a task worker
		TaskWorker worker = new TaskWorker(firstTask, generator);
		Thread tWorker = new Thread(worker, "task_worker");
		worker.thread = tWorker;
		tWorker.start();
		wait(8);

		System.out.println(tag + "shutdown task generator.");
		generator.shutDown();
//		wait(5); // make worker blocked for getting a task
		System.out.println(tag + "shutdown task worker.");
		worker.stop();
		System.out.println(tag + "terminated.");
	}

	/**
	 * 
	 * Get a task from queue, and run task.
	 */
	private static class TaskWorker implements Runnable {

		private volatile boolean isRunning = true;
		private Runnable firstTask;
		private TaskGenerator generator;
		Thread thread;

		public TaskWorker(Runnable firstTask, TaskGenerator generator) {
			this.firstTask = firstTask;
			this.generator = generator;
		}

		public void stop() {
			this.isRunning = false;

			System.out.println(this.getThreadTag() + "wait for worker stop.");
			ThreadPoolWorkerTest.wait(5);
			if (this.thread.isAlive()) {
				// if worker blocked for getting a task
				System.out.println(this.getThreadTag() + "force stop worker.");
				this.thread.interrupt();
			}
		}

		@Override
		public void run() {
			Runnable task = this.firstTask;
			this.firstTask = null;
			try {
				while (task != null || (task = this.generator.getTask()) != null) {
					System.out.println(this.getThreadTag() + "get a task, and work ...");
					this.runTask(task);
					task = null;
					if (!this.isRunning) {
						return;
					}
				}
			} catch (InterruptedException | RuntimeException e) {
				System.out.println(this.getThreadTag() + "interrupted.");
			}
		}

		private void runTask(Runnable task) {
			task.run();
		}

		private String getThreadTag() {
			return String.format("[%s]: ", Thread.currentThread().getName());
		}
	}

	/**
	 * 
	 * Create a task and put task into a queue.
	 */
	private static class TaskGenerator implements Runnable {

		private volatile boolean isRunning = true;
		private BlockingQueue<Runnable> queue;
		Thread thread;

		public TaskGenerator() {
			this.queue = new ArrayBlockingQueue<Runnable>(10);
		}

		public Runnable getTask() throws InterruptedException {
			// if no task in queue, blocked
			return this.queue.take();
			// if timeout, throw InterruptedException
//			return this.queue.poll(10, TimeUnit.SECONDS);
		}

		public void shutDown() {
			this.isRunning = false;
			while (queue.size() != 0) {
				System.out.println(this.getThreadTag() + "shutdown, and queue size: " + this.queue.size());
				ThreadPoolWorkerTest.wait(1);
			}

			if (this.thread.isAlive()) {
				System.out.println(this.getThreadTag() + "force shutdown.");
				this.thread.interrupt();
			}
		}

		@Override
		public void run() {
			try {
				this.enqueueTask();
			} catch (InterruptedException e) {
				System.out.println(this.getThreadTag() + "interrupted.");
			}
		}

		private void enqueueTask() throws InterruptedException {
			while (this.isRunning) {
				Runnable r = new Runnable() {

					@Override
					public void run() {
						System.out.println(TaskGenerator.this.getThreadTag() + "task is running ...");
						try {
							Thread.sleep(new Random().nextInt(5) * 1000L);
						} catch (InterruptedException e) {
							// throw unchecked exception in run()
							throw new RuntimeException(TaskGenerator.this.getThreadTag() + "task is interrupted.");
						}
					}
				};

				Thread.sleep(1000L);
				System.out.println(this.getThreadTag() + "enqueue a task.");
				if (!this.queue.offer(r)) {
					System.out.println(this.getThreadTag() + "WARN: enqueue task failed, task queue is full!");
					System.out.println(this.getThreadTag() + "queue size: " + this.queue.size());
				}
			}
		}

		private String getThreadTag() {
			return String.format("[%s]: ", Thread.currentThread().getName());
		}
	}

}
