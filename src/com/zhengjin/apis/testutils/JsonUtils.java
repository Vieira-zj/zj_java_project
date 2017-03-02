package com.zhengjin.apis.testutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public final class JsonUtils {

	public static JSONObject parseJsonContentAndRetObject(String content) {
		return JSON.parseObject(content);
	}
	
}
