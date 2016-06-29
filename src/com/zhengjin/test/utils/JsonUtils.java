package com.zhengjin.test.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public final class JsonUtils {

	public static JSONArray parseJsonAndRetJsonArray(String content, String key) {
		JSONObject data = parseJsonAndRetJsonObject(content);
		return data.getJSONArray(key);
	}
	
	public static String parseJsonAndRetString(String content, String key) {
		JSONObject data = parseJsonAndRetJsonObject(content);
		return data.getString(key);
	}
	
	public static int parseJsonAndRetIntValue(String content, String key) {
		JSONObject data = parseJsonAndRetJsonObject(content);
		return data.getIntValue(key);
	}
	
	private static JSONObject parseJsonAndRetJsonObject(String content) {
		return JSON.parseObject(content);
	}
	
}
