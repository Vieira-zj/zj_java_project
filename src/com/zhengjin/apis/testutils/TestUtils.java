package com.zhengjin.apis.testutils;

import org.junit.Assert;

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
	
	public static void printLog(Object text) {
		System.out.println(text);
	}

}
