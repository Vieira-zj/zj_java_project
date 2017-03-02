package com.zhengjin.apis.testcases;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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

	private final static boolean isJmeterEnv = false;

	// cache test cases data
	private static List<List<String>> ROWS;

	@BeforeClass
	public static void classSetUp() throws Exception {
		String filePath = isJmeterEnv ? JmeterUtils
				.getCustomizedVarFromJmeterEnv(new JUnitSampler(),
						TestConstants.JMETER_KEY_TEST_DATA)
				: TestConstants.EXCEL_TESTCASES_FILE_PATH;

		ROWS = FileUtils.readExcelRows(filePath, "Settings");
		if (ROWS.size() > 0) {
			Assert.assertTrue("Verify read test data from excel.", true);
		} else {
			throw new Exception("Read test data from excel failed!");
		}
	}

	@AfterClass
	public static void classTearDown() {
		ROWS.clear();
	}

	@Test
	@Category({ CategoryScreenSaverTest.class, CategoryRetCodeTest.class })
	public void test11HttpOkForSendData() {
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, pushData);
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response);

		TestUtils.assertRespReturnCodeForJson(retJsonObj);
		TestUtils.assertRespReturnMsgForJson(retJsonObj);
	}

	@Ignore
	@Category(CategoryScreenSaverTest.class)
	public void test12RespEmptyForSendAllData() {
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_01");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, pushData);
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response).getJSONObject("data");

		final int empty = 0;
		JSONArray addList = retJsonObj.getJSONArray("add");
		Assert.assertEquals(
				"Verify the add list is empty, when push all screen saver files.",
				empty, addList.size());

		JSONArray deleteList = retJsonObj.getJSONArray("delete");
		Assert.assertEquals(
				"Verify the delete list is empty, when push all screen saver files.",
				empty, deleteList.size());

		JSONArray updateList = retJsonObj.getJSONArray("update");
		Assert.assertEquals(
				"Verify the update list is empty, when push all screen saver files.",
				empty, updateList.size());
	}

	@Test
	@Category({ CategoryScreenSaverTest.class, CategoryRetCodeTest.class })
	public void test21HttpOkForSendEmpty() {
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, pushData);
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response);

		TestUtils.assertRespReturnCodeForJson(retJsonObj);
		TestUtils.assertRespReturnMsgForJson(retJsonObj);
	}

	@Test
	@Category(CategoryScreenSaverTest.class)
	public void test22RespUpdAndDelEleEmptyForSendEmpty() {
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, pushData);
		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response).getJSONObject("data");

		final int empty = 0;
		JSONArray retUpdateList = retJsonObj.getJSONArray("update");
		Assert.assertEquals(
				"Verify the update list is empty, when push empty.", empty,
				retUpdateList.size());

		JSONArray retDeleteList = retJsonObj.getJSONArray("delete");
		Assert.assertEquals(
				"Verify the delete list is empty, when push empty.", empty,
				retDeleteList.size());
	}

	@Ignore
	@Category(CategoryScreenSaverTest.class)
	public void test23RespPicsNameForSendEmpty() {
		List<String> dataRow = FileUtils.getSpecifiedRow(ROWS, "SS_02");
		String pushData = dataRow.get(TestConstants.COL_REQUEST_DATA);
		String response = HttpUtils.sendHttpPostRequest(
				TestConstants.SCREEN_SAVER_SERVER_URL, pushData);

		JSONObject retJsonObj = JsonUtils
				.parseJsonContentAndRetObject(response).getJSONObject("data");
		JSONArray retAddList = retJsonObj.getJSONArray("add");
		Assert.assertTrue(
				"Verify the return add list is not empty when push empty.",
				(retAddList.size() > 0));

		String expectedStr = dataRow
				.get(TestConstants.COL_EXPECTED_RESPONSE_DATA);
		JSONObject expectedJsonObj = JsonUtils.parseJsonContentAndRetObject(
				expectedStr).getJSONObject("data");
		JSONArray expectedAddList = expectedJsonObj.getJSONArray("add");
		Assert.assertTrue(expectedAddList.size() > 0);
		TestUtils
				.assertElementsInJsonArray(expectedAddList, retAddList, "name");
	}

}
