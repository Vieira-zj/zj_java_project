package com.zhengjin.dynproxy.demo;

public interface MovieApi {

	@Get(path = "/top10", description = "film top 10")
	String top10(@Param("start") Integer start, @Param("count") Integer count);
}
