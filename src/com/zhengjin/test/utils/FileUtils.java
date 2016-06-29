package com.zhengjin.test.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;


public final class FileUtils {
	
	private static final int BUFFER_SIZE = 1000;
	
	public static String getProjectPath() {
		
		String path = FileUtils.class.getResource("/").getPath();
		if (path == null || path == "") {
			Assert.assertTrue("Error, the project path is null or empty!", false);
		}
		
		path = path.substring(1, (path.length() - 1));
		return path.substring(0, path.lastIndexOf("/"));
	}

	public static String readFileContent(String path) {
		
		BufferedReader reader = null;
		StringBuffer content = new StringBuffer(BUFFER_SIZE);
		
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, TestConstants.CHARSET);
			reader = new BufferedReader(isr);
			
			String tempStr = null;
			while((tempStr = reader.readLine()) != null) {
				content.append(tempStr);
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format("Error, IOException(%s) when read file content!", e.getMessage()), false);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
	
}
