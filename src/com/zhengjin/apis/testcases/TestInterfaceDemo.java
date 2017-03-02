package com.zhengjin.apis.testcases;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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

	private final static String TAG = TestInterfaceDemo.class.getSimpleName();

	private static List<List<String>> ROWS;

	@BeforeClass
	public static void classSetUp() {
		TestUtils.printLogWithTag(TAG, "class setup");
		ROWS = FileUtils.readExcelRows(TestConstants.TEST_DATA_PATH
				+ "testcases.xlsx", "Settings");
	}

	@AfterClass
	public static void classTearDown() {
		TestUtils.printLogWithTag(TAG, "class clearup");
		ROWS.clear();
	}

	@Before
	public void setUp() {
		TestUtils.printLogWithTag(TAG, "method setup");
	}

	@After
	public void tearDown() {
		TestUtils.printLogWithTag(TAG, "method clearup");
	}

	@Test
	@Category(CategoryDemoTest.class)
	public void test01ReadFileAndParseJsonString() {
		final String fileName = "02_json_pull_allfiles.txt";

		String path = TestConstants.TEST_DATA_PATH + fileName;
		String content = FileUtils.readFileContent(path);
		Assert.assertTrue("Verify read content from txt file.",
				content.length() > 0);

		JSONArray addArr = JsonUtils.parseJsonContentAndRetObject(content)
				.getJSONObject("data").getJSONArray("add");
		Assert.assertTrue("Verify parse json object.", addArr.size() > 0);

		TestUtils.printLog("Items from Json: ");
		for (int i = 0, dataSize = addArr.size(); i < dataSize; i++) {
			JSONObject item = addArr.getJSONObject(i);
			TestUtils.printLog(item.getString("name"));
		}
	}

	@Test
	@Category(CategoryDemoTest.class)
	public void test02SendHttpJsonPostRequest() {
		final String fileName = "02_json_push_empty.txt";

		String path = TestConstants.TEST_DATA_PATH + fileName;
		String content = FileUtils.readFileContent(path);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, content);
		TestUtils.printLog("Response for screen saver server: ");
		TestUtils.printLog(response);
		Assert.assertEquals("Verify the return code is 200.", 200, JsonUtils
				.parseJsonContentAndRetObject(response).getIntValue("retCode"));
	}

	@Test
	@Category(CategoryDemoTest.class)
	public void test03RunInterfaceTestCaseFromExcel() {
		List<String> row = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL,
				row.get(TestConstants.COL_REQUEST_DATA));
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response);

		TestUtils.assertRespReturnCodeForJson(retJsonObj);
		TestUtils.assertRespReturnMsgForJson(retJsonObj);
	}

	@Test
	@Category(CategoryDemoTest.class)
	public void test04SendHttpGetRequest() {
		final String cityId = "101010100";
		String httpArg = "plat_type=funtv&version=2.8.0.8_s&sid=FD5551A-SU&mac=28:76:CD:01:96:F6"
				+ "&random=1483946026114266&sign=f56d8ebc07bd370101f06b2d84be0e2f"
				+ "&province=&city=&area=&cityId=%s";

		String response = HttpUtils.sendHttpGetRequest(
				TestConstants.WEATHER_SERVER_URL,
				String.format(httpArg, cityId));
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response);
		Assert.assertEquals("Verify return code of GET request.",
				retJsonObj.getString("retCode"), "200");
		Assert.assertEquals("Verify return message of GET request.",
				retJsonObj.getString("retMsg"), "ok");

		JSONObject retData = retJsonObj.getJSONObject("data");
		Assert.assertEquals("Verify the city cn name.",
				retData.getString("city"), "北京");
	}

}
