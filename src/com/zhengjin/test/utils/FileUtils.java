package com.zhengjin.test.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileUtils {
	
	public static String getProjectPath() {
		
		String path = FileUtils.class.getResource("/").getPath();
		path = path.substring(1, (path.length() - 1));
		return path.substring(0, path.lastIndexOf("/"));
	}

	public static String readFileContent(String path) {
		
		BufferedReader reader = null;
		String content = "";
		
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, TestConstants.CHARSET);
			reader = new BufferedReader(isr);
			
			String line = null;
			while((line = reader.readLine()) != null) {
				content += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return content;
	}
	
}
