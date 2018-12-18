package com.zhengjin.java.demo;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 背景: 一个测试用例中(测试类)包含1个或多个测试方法. 
 * 目的: 根据测试用例的维度来生成测试报告.
 *
 */
public class TestngRptListenerByCls implements ITestListener {

	private static final String filePath = System.getProperty("user.dir") + File.separator + "test-output"
			+ File.separator + "TestCasesReport.txt";

	private Map<String, String> resultsMap = new LinkedHashMap<>(200);
	private Map<String, List<String>> methodsMap = new LinkedHashMap<>(200);

	private final String tagPass = "pass";
	private final String tagFailed = "failed";

	static {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			if (!file.delete()) {
				System.out.println("Delete file failed: " + file.getAbsolutePath());
			}
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ITestNGMethod method = result.getMethod();
		String fullClassName = method.getTestClass().getName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		resultsMap.putIfAbsent(className, tagPass);

		String tmpLine = method.getMethodName() + "\t" + tagPass;
		List<String> tmpList = methodsMap.get(className);
		if (tmpList == null) {
			List<String> initList = new ArrayList<>();
			initList.add(tmpLine);
			methodsMap.put(className, initList);
		} else {
			tmpList.add(tmpLine);
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ITestNGMethod method = result.getMethod();
		String fullClassName = method.getTestClass().getName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		resultsMap.put(className, tagFailed);

		String tmpLine = method.getMethodName() + "\t" + tagFailed + "\t" + result.getThrowable().getClass().getName();
		List<String> tmpList = methodsMap.get(className);
		if (tmpList == null) {
			List<String> initList = new ArrayList<>();
			initList.add(tmpLine);
			methodsMap.put(className, initList);
		} else {
			tmpList.add(tmpLine);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		if (resultsMap.size() == 0) {
			return;
		}

		appendLineToFile("Test Report (By Class)\n");
		appendLineToFile("Start Date: " + context.getStartDate().toString() + "\n");

		int totalFailed = 0;
		for (String key : resultsMap.keySet()) {
			String tcResults = resultsMap.get(key);
			appendLineToFile(String.format("\n%s\t%s\n", key, tcResults));
			if (tagFailed.equals(tcResults)) {
				totalFailed++;
			}

			List<String> tmpList = methodsMap.get(key);
			for (String item : tmpList) {
				appendLineToFile("\t" + item + "\n");
			}
		}

		appendLineToFile("\nTest Report Summary\n");
		int total = resultsMap.size();
		appendLineToFile(String.format("Total Test Cases (Class): %s,\tPassed: %s,\tFailed: %s\n", total,
				(total - totalFailed), totalFailed));
		appendLineToFile("End Date: " + context.getEndDate().toString() + "\n");

		resultsMap.clear();
		methodsMap.clear();
	}

	private void appendLineToFile(String line) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filePath, true));
			out.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
