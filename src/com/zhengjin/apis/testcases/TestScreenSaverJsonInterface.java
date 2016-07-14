package com.zhengjin.apis.testcases;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengjin.apis.testcategory.CategoryRetCodeTest;
import com.zhengjin.apis.testcategory.CategoryScreenSaverTest;
import com.zhengjin.apis.testutils.FileUtils;
import com.zhengjin.apis.testutils.HttpUtils;
import com.zhengjin.apis.testutils.JmeterUtils;
import com.zhengjin.apis.testutils.JsonUtils;
import com.zhengjin.apis.testutils.TestConstants;
import com.zhengjin.apis.testutils.TestUtils;

import org.apache.jmeter.protocol.java.sampler.JUnitSampler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class TestScreenSaverJsonInterface {

	private static List<List<String>> ROWS;
	
	@BeforeClass
	public static void classSetUp() {
		// get the test cases file path via Jmeter var instead of static var in Java
		ROWS = FileUtils.readExcelRows(TestConstants.EXCEL_TESTCASES_FILE_PATH, "Settings");
	}
	
	@AfterClass
	public static void classTearDown() {
		ROWS.clear();
	}
	
	@Before
	public void setUp() {
		TestUtils.printLog("setup in TestScreenSaver.");
	}
	
	@After
	public void tearDown() {
		TestUtils.printLog("teardown in TestScreenSaver.");
	}

	@Test
	@Category(CategoryScreenSaverTest.class)
	public void test11GetJmeterUserDefinedVars() {
		
		// get the Jmeter sampler when running the test cases
		String testcases_file_path = 
				JmeterUtils.getUserDefinedVarFromJmeterEnv(new JUnitSampler(), TestConstants.JMETER_KEY_TEST_DATA);
		ROWS = FileUtils.readExcelRows(testcases_file_path, "Settings");
		
		if (ROWS.size() > 0) {
			Assert.assertTrue("Verify read the test data.", true);
		} else {
			int errCode = 1;
			System.exit(errCode);
		}
	}
	
	@Test
	@Category({CategoryScreenSaverTest.class, CategoryRetCodeTest.class})
	public void test12HttpOkWhenPushAllScreenSaverPictures() {
		
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, pushData);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response);
		
		TestUtils.assertReturnCodeInJsonResponse(retJsonObj);
		TestUtils.assertReturnMessageInJsonResponse(retJsonObj);
	}
	
	@Test
	@Category(CategoryScreenSaverTest.class)
	public void test13PullEmptyWhenPushAllScreenSaverPictures() {
	
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, pushData);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response).getJSONObject("data");

		final int empty = 0;
		JSONArray addList = retJsonObj.getJSONArray("add");
		Assert.assertEquals("Verify the add list is empty, when push all screen saver files.",
				empty, addList.size());

		JSONArray deleteList = retJsonObj.getJSONArray("delete");
		Assert.assertEquals("Verify the delete list is empty, when push all screen saver files.", 
				empty, deleteList.size());

		JSONArray updateList = retJsonObj.getJSONArray("update");
		Assert.assertEquals("Verify the update list is empty, when push all screen saver files.", 
				empty, updateList.size());
	}
	
	@Test
	@Category({CategoryScreenSaverTest.class, CategoryRetCodeTest.class})
	public void test21HttpOkWhenPushNoScreenSaverPictures() {
		
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, pushData);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response);
		
		TestUtils.assertReturnCodeInJsonResponse(retJsonObj);
		TestUtils.assertReturnMessageInJsonResponse(retJsonObj);
	}
	
	@Test
	@Category(CategoryScreenSaverTest.class)
	public void test22PullEmptyForUpdateAndDeleteWhenPushEmpty() {
	
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, pushData);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response).getJSONObject("data");
		
		final int empty = 0;
		JSONArray retUpdateList = retJsonObj.getJSONArray("update");
		Assert.assertEquals("Verify the update list is empty, when push empty.", 
				empty, retUpdateList.size());
		
		JSONArray retDeleteList = retJsonObj.getJSONArray("delete");
		Assert.assertEquals("Verify the delete list is empty, when push empty.", 
				empty, retDeleteList.size());
	}
	
	@Test
	@Category(CategoryScreenSaverTest.class)
	public void test23PulledScreenSaverPicturesNameForAddWhenPushEmpty() {
		
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendJsonPostRequest(TestConstants.SCREEN_SAVER_URL_TEST, pushData);
		JSONObject retJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(response).getJSONObject("data");
		JSONArray retAddList = retJsonObj.getJSONArray("add");
		Assert.assertTrue("Verify the return add list is not empty when push empty.", (retAddList.size() > 0));
		
		String expectedStr = dataRow.get(TestConstants.COL_EXPECTED_RESPONSE_DATA);
		JSONObject expectedJsonObj = JsonUtils.parseJsonContentAndRetJsonObject(expectedStr).getJSONObject("data");
		JSONArray expectedAddList = expectedJsonObj.getJSONArray("add");
		Assert.assertTrue(expectedAddList.size() > 0);
		
		TestUtils.assertJsonArrayEqualsForSpecifiedField(expectedAddList, retAddList, "name");
	}
	
}
