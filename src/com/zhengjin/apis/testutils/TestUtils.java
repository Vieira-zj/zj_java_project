package com.zhengjin.apis.testutils;

import org.junit.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public final class TestUtils {

	public static void assertReturnCodeInJsonResponse(JSONObject jsonObj) {
		Assert.assertEquals(
				"Verify the ret code in response.", 
				TestConstants.RESPONSE_OK, jsonObj.getIntValue("retCode"));
	}
	
	public static void assertReturnMessageInJsonResponse(JSONObject jsonObj) {
		Assert.assertEquals(
				"Verify the ret message in response.", 
				TestConstants.RESPONSE_MSG_SUCCESS, jsonObj.getString("retMsg"));
	}
	
	public static void assertJsonArrayEqualsForSpecifiedField(
			JSONArray exptectedJson, JSONArray actualJson, String key) {
		
		Assert.assertEquals("Verify two Json Array length is equal.", exptectedJson.size(), actualJson.size());
		
		for (int i = 0, size = exptectedJson.size(); i < size; i++) {
			JSONObject expectedJsonObj = exptectedJson.getJSONObject(i);
			JSONObject actualJsonObj = actualJson.getJSONObject(i);
			Assert.assertEquals("Verify Json value is equal.", 
					expectedJsonObj.getString(key), actualJsonObj.getString(key));
		}
	}
	
	public static void printLog(Object text) {
		System.out.println(text);
	}

}
