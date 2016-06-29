package com.zhengjin.apis.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.zhengjin.test.utils.FileUtils;
import com.zhengjin.test.utils.HttpUtils;
import com.zhengjin.test.utils.JsonUtils;
import com.zhengjin.test.utils.TestConstants;

import org.junit.Assert;

public final class TestDemo {
	
	@Before
	public void setUp() {
		log("setup in testDemo.");
	}
	
	@After
	public void tearDown() {
		log("teardown in testDemo.");
	}
	
	@Test
	public void testReadFileAndParseJsonString() {
		
		String file = "02_json_pull_allfiles.txt";
		String path = TestConstants.INPUT_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);
		
		JSONArray data = JsonUtils.parseJsonAndRetJsonArray(content, "add");
		log(data);
		Assert.assertTrue((data.size() > 0));
	}
	
	@Test
	public void testSendHttpJsonPostRequest() {
		
		String file = "02_json_push_empty.txt";
		String path = TestConstants.INPUT_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);
		log(content);
		
		String response = HttpUtils.sendJsonPostRequest(TestConstants.URL, content);
		log(response);
		Assert.assertTrue((JsonUtils.parseJsonAndRetIntValue(response, "retCode") == 200));
	}
	
	private static void log(Object text) {
		System.out.println(text);
	}

}
