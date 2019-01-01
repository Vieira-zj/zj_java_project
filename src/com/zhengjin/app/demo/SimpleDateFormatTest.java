package com.zhengjin.app.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class SimpleDateFormatTest {

	// 定义一个全局的SimpleDateFormat, 线程安全问题
//	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 使用ThreadLocal定义一个全局的SimpleDateFormat
	private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static void main(String[] args) {

		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
		ExecutorService pool = new ThreadPoolExecutor(5, 20, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(128), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

		final int runCount = 100;
		// 定义一个CountDownLatch，保证所有子线程执行完之后主线程再执行
		CountDownLatch countDownLatch = new CountDownLatch(runCount);
		// 定义一个线程安全的HashSet
		Set<String> dates = Collections.synchronizedSet(new HashSet<String>());

		for (int i = 0; i < runCount; i++) {
			// 获取当前时间
			Calendar calendar = Calendar.getInstance();
			final int finalI = i;
			pool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("execution thread: " + Thread.currentThread().getName());
					// 增加时间
					calendar.add(Calendar.DATE, finalI);
					// 通过simpleDateFormat把时间转换成字符串
//					String dateString = simpleDateFormat.format(calendar.getTime());
					String dateString = simpleDateFormatThreadLocal.get().format(calendar.getTime());
					dates.add(dateString);
					countDownLatch.countDown();
				}
			});
		}

		// 阻塞，直到countDown数量为0
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 输出去重后的时间个数
		System.out.println(dates.size());
		System.out.println("SimpleDateFormat test done.");
	}

}
