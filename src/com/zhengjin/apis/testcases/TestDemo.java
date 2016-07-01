package com.zhengjin.apis.testcases;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengjin.apis.testcategory.DemoTest;
import com.zhengjin.apis.testutils.FileUtils;
import com.zhengjin.apis.testutils.HttpUtils;
import com.zhengjin.apis.testutils.JsonUtils;
import com.zhengjin.apis.testutils.TestConstants;
import com.zhengjin.apis.testutils.TestUtils;

import org.junit.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestDemo {
	
	static List<List<String>> ROWS;
	
	@BeforeClass
	public static void classSetUp() {
		printLog("class setup in testDemo.");
		ROWS = FileUtils.readExcelRows(TestConstants.TEST_DATA_PATH + "testcases.xlsx", "Settings");
	}
	
	@AfterClass
	public static void classTearDown() {
		printLog("class teardown in testDemo.");
		ROWS.clear();
	}
	
	@Before
	public void setUp() {
		printLog("setup in testDemo.");
	}
	
	@After
	public void tearDown() {
		printLog("teardown in testDemo.");
	}
	
	@Test
	@Category(DemoTest.class)
	public void test1ReadFileAndParseJsonString() {
		
		String file = "02_json_pull_allfiles.txt";
		String path = TestConstants.TEST_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);
		Assert.assertTrue((content.length() > 0));
		
		JSONArray data = JsonUtils.parseJsonContentAndRetJsonObject(content).getJSONArray("add");
		Assert.assertTrue((data.size() > 0 ));

		for (int i = 0, dataSize = data.size(); i < dataSize; i++) {
			JSONObject item = data.getJSONObject(i);
			printLog(item.getString("name"));
		}
	}
	
	@Test
	@Category(DemoTest.class)
	public void test2SendHttpJsonPostRequest() {
		
		String file = "02_json_push_empty.txt";
		String path = TestConstants.TEST_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);

		String response = HttpUtils.sendJsonPostRequest(TestConstants.URL, content);
		printLog(response);
		Assert.assertTrue((JsonUtils.parseJsonContentAndRetJsonObject(response).getIntValue("retCode") == 200));
	}
	
	@Test
	@Category(DemoTest.class)
	public void test3ReadTestCaseFromExcel() {
		
		List<String> row = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String response = HttpUtils.sendJsonPostRequest(TestConstants.URL,row.get(TestConstants.COL_REQUEST_DATA));
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response);

		TestUtils.assertReturnCodeInJsonResponse(retJsonObj);
		TestUtils.assertReturnMessageInJsonResponse(retJsonObj);
	}
	
	@Ignore
	public void test4Demo() {
		// TODO
	}
	
	private static void printLog(Object text) {
		System.out.println(text);
	}

}
