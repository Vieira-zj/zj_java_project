package com.zhengjin.apis.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zhengjin.test.utils.FileUtils;
import com.zhengjin.test.utils.JsonUtils;
import com.zhengjin.test.utils.TestConstants;

public class TestDemo {
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testDemo() {
		
		String file = "02_json_pull_allfiles.txt";
		String path = TestConstants.INPUT_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);
		
		log(JsonUtils.parseJsonContent(content, "add"));
	}
	
	private static void log(Object text) {
		System.out.println(text);
	}

}
