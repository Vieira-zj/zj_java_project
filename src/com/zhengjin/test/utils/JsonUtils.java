package com.zhengjin.test.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	public static JSONArray parseJsonContent(String content, String key) {

		JSONObject data = JSON.parseObject(content);
		return data.getJSONArray(key);
	}
	
	
}
