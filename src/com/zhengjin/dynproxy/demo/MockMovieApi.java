package com.zhengjin.dynproxy.demo;

@Server("zheng.proxy.test")
public class MockMovieApi implements MovieApi {

	@Override
	public String top10(Integer start, Integer count) {
		String[] films = { "film1", "film2", "film3", "film4", "film5", "film6" };

		if (start >= films.length) {
			return "null";
		}

		int end = start + count;
		if (end >= films.length) {
			return "null";
		}

		String ret = "";
		for (int i = start; i < end; i++) {
			ret += films[i] + " ";
		}
		return ret;
	}

}
