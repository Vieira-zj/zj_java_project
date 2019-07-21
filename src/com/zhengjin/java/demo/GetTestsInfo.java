package com.zhengjin.java.demo;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zhengjin.apis.testutils.TestUtils;

public class GetTestsInfo {

	private List<String> testClazz = new ArrayList<>(100);

	public static void main(String[] args) {
		final String projectRoot = System.getenv("HOME") + File.separator
				+ "/Workspaces/EclipseWorkspaces/FunSettingsInterfaceTest";
		final String testRoot = projectRoot + File.separator + "src/com/zhengjin/java";

		int totalMethods = 0;
		int totalClazz = 0;

		GetTestsInfo getTestInfo = new GetTestsInfo();
		getTestInfo.getTestClazz(new File(testRoot));
		for (String cls : getTestInfo.testClazz) {
			List<Method> methodsList = getTestInfo.getTestMethods(cls.substring(0, (cls.length() - ".java".length())));
//          List<Method> methodsList = getTestMethods(cls.replace(".java", ""), "zhengjin");
			if (methodsList.size() == 0) {
				continue;
			}

			TestUtils.printLog("test class ===> " + cls);
			totalClazz++;

			totalMethods += methodsList.size();
			for (Method m : methodsList) {
				TestInfo a = getTestInfo.getTestInfoAnnotation(m);
				if (a != null) {
					TestUtils.printLog(String.format("test method: %s, author: %s, date: %s, comments: %s", m.getName(),
							a.author(), a.date(), a.comments()));
				} else {
					TestUtils.printLog("test method: " + m.getName());
				}
			}
		}

		TestUtils.printLog("TEST CLASS COUNT: " + totalClazz);
		TestUtils.printLog("TEST METHODS COUNT: " + totalMethods);
	}

	private void getTestClazz(File root) {
		File[] testFiles = root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				return pathname.getName().startsWith("Test") && pathname.getName().endsWith(".java");
			}
		});

		if (testFiles != null && testFiles.length > 0) {
			for (File f : testFiles) {
				if (f.isDirectory()) {
					getTestClazz(f);
					continue;
				}
				int idx = f.getAbsolutePath().indexOf("src");
				testClazz.add(f.getAbsolutePath().substring(idx + "src".length() + 1).replace("/", "."));
			}
		}
	}

	private List<Method> getTestMethods(String filename) {
		return getTestMethods(filename, "");
	}

	private List<Method> getTestMethods(String filename, String authorName) {
		Class<?> clz = null;
		try {
			clz = Class.forName(filename);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method[] methods = clz.getDeclaredMethods();
		if (methods == null)
			return Collections.emptyList();

		List<Method> methodsList = new ArrayList<>(200);
		for (Method m : methods) {
			Annotation a = m.getDeclaredAnnotation(org.junit.Test.class);
			if (a == null) {
				continue;
			}

			if (authorName.length() > 0) {
				TestInfo info = getTestInfoAnnotation(m);
				if (info != null && authorName.equals(info.author())) {
					methodsList.add(m);
				}
			} else {
				methodsList.add(m);
			}
		}

		return methodsList;
	}

	private TestInfo getTestInfoAnnotation(Method m) {
		return m.getDeclaredAnnotation(com.zhengjin.java.demo.TestInfo.class);
	}

}
