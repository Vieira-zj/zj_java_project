package com.zhengjin.apis.testutils;

import org.junit.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class TestUtils {

	private TestUtils() {
	}

	public static void assertRespReturnCodeForJson(JSONObject jsonObj) {
		Assert.assertEquals("Verify response ret code.",
				TestConstants.RESPONSE_OK, jsonObj.getIntValue("retCode"));
	}

	public static void assertRespReturnMsgForJson(JSONObject jsonObj) {
		Assert.assertEquals("Verify response ret message.",
				TestConstants.RESPONSE_MSG_SUCCESS, jsonObj.getString("retMsg"));
	}

	public static void assertElementsInJsonArray(JSONArray exptectedJsonArr,
			JSONArray actualJsonArr, String key) {
		Assert.assertEquals("Verify two Json Array length is equal.",
				exptectedJsonArr.size(), actualJsonArr.size());

		for (int i = 0, size = exptectedJsonArr.size(); i < size; i++) {
			Assert.assertEquals("Verify Json value is equal.", exptectedJsonArr
					.getJSONObject(i).getString(key), actualJsonArr
					.getJSONObject(i).getString(key));
		}
	}

	public static void printLog(Object text) {
		System.out.println(text);
	}

	public static void printLogWithTag(String tag, String text) {
		System.out.println(String.format("%s: %s", tag, text));
	}

}
