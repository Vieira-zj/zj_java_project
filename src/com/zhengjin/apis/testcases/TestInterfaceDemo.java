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
import com.zhengjin.apis.testcategory.CategoryDemoTest;
import com.zhengjin.apis.testutils.FileUtils;
import com.zhengjin.apis.testutils.HttpUtils;
import com.zhengjin.apis.testutils.JsonUtils;
import com.zhengjin.apis.testutils.TestConstants;
import com.zhengjin.apis.testutils.TestUtils;

import org.junit.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestInterfaceDemo {
	
	static List<List<String>> ROWS;
	
	@BeforeClass
	public static void classSetUp() {
		printLog("class setup in TestDemo.");
		ROWS = FileUtils.readExcelRows(TestConstants.TEST_DATA_PATH + "testcases.xlsx", "Settings");
	}
	
	@AfterClass
	public static void classTearDown() {
		printLog("class teardown in TestDemo.");
		ROWS.clear();
	}
	
	@Before
	public void setUp() {
		printLog("setup in TestDemo.");
	}
	
	@After
	public void tearDown() {
		printLog("teardown in TestDemo.");
	}
	
	@Test
	@Category(CategoryDemoTest.class)
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
	@Category(CategoryDemoTest.class)
	public void test2SendHttpJsonPostRequest() {
		
		String file = "01_json_push_allfiles.txt";
//		String file = "02_json_push_empty.txt";
		String path = TestConstants.TEST_DATA_PATH + file;
		String content = FileUtils.readFileContent(path);

		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, content);
		printLog(response);
		Assert.assertTrue((JsonUtils.parseJsonContentAndRetJsonObject(response).getIntValue("retCode") == 200));
	}
	
	@Test
	@Category(CategoryDemoTest.class)
	public void test3ReadTestCaseFromExcel() {
		
		List<String> row = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String response = HttpUtils.sendJsonPostRequest(
				TestConstants.SCREEN_SAVER_URL_TEST, row.get(TestConstants.COL_REQUEST_DATA));
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response);

		TestUtils.assertReturnCodeInJsonResponse(retJsonObj);
		TestUtils.assertReturnMessageInJsonResponse(retJsonObj);
	}
	
	@Test
	public void test4SendHttpGetRequest() {
		// test Baidu weather APIs
		
		String httpUrl = "http://apis.baidu.com/apistore/weatherservice/recentweathers";
		String httpArg = "cityid=101010100";
		
		String response = HttpUtils.sendGetRequest(httpUrl, httpArg);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response);
		
		Assert.assertEquals(retJsonObj.getIntValue("errNum"), 0);
		Assert.assertEquals(retJsonObj.getString("errMsg"), "success");
		
		JSONObject retData = retJsonObj.getJSONObject("retData");
		JSONObject retTodayData = retData.getJSONObject("today");
		Assert.assertTrue(retTodayData.getString("curTemp").startsWith("24"));
	}

	@Ignore
	public void test5Demo() {
		// TODO:
	}
	
	private static void printLog(Object text) {
		System.out.println(text);
	}

}
