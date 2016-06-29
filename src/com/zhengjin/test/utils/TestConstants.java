package com.zhengjin.test.utils;

public final class TestConstants {

	public static final String PROJECT_ROOT_PATH = FileUtils.getProjectPath();
	private static final String INPUT_DATA_DIR = "input_data";
	public static final String INPUT_DATA_PATH = String.format("%s/%s/", FileUtils.getProjectPath(), INPUT_DATA_DIR);
	
	public static final int SHORT_WAIT = 1000;
	public static final int WAIT = 3000;
	public static final int LONG_WAIT = 5000;
	public static final int TIME_OUT = 8000;
	
	public static final String CHARSET = "UTF-8";
	public static final String URL_TEST = "http://172.17.12.36:8080/file-ms-service/service/pic-distribute/api/v2";
	public static final String URL = "http://fmg.tv.funshion.com/service/pic-distribute/api/v2";
	
}
