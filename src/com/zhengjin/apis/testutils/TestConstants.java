package com.zhengjin.apis.testutils;

public final class TestConstants {

	public static final String TEST_DATA_DIR = "testdata/";
	public static final String EXCEL_TESTCASES_FILE_NAME = "testcases.xlsx";

	// for JMETER ENV, FileUtils.getProjectPath() returns null
	public static final String PROJECT_ROOT_PATH = FileUtils.getProjectPath();
//	public static final String PROJECT_ROOT_PATH = 
//			"D:/GitHub_Workspace/Eclipse_Workspace/FunSettingsInterfaceTest";
	public static final String TEST_DATA_PATH = String.format("%s/%s",
			PROJECT_ROOT_PATH, TEST_DATA_DIR);
	public static final String EXCEL_TESTCASES_FILE_PATH = TEST_DATA_PATH
			+ EXCEL_TESTCASES_FILE_NAME;

	// user defined variables name in Jmeter
	public static final String JMETER_KEY_TEST_DATA = "testdata_path";

	public static final int SHORT_WAIT = 1000;
	public static final int WAIT = 3000;
	public static final int LONG_WAIT = 5000;
	public static final int TIME_OUT = 8000;

	public static final String CHARSET_UFT8 = "UTF-8";

	public static final String WEATHER_SERVER_URL = "http://card.tv.funshion.com/weather/city";
	public static final String SCREEN_SAVER_SERVER_URL = 
			"http://fmg.tv.funshion.com/service/pic-distribute/api/v2";

	public static final int RESPONSE_OK = 200;
	public static final String RESPONSE_MSG_SUCCESS = "Success.";

	// columns in testcases.xlsx
	public static final int COL_APPLICATION = 0;
	public static final int COL_FUNCTION_AREA = 1;
	public static final int COL_CASE_ID = 2;
	public static final int COL_CASE_DESC = 3;
	public static final int COL_CLASS = 4;
	public static final int COL_METHOD = 5;
	public static final int COL_RUN_FLAG = 6;
	public static final int COL_REQUEST_TYPE = 7;
	public static final int COL_URL = 8;
	public static final int COL_REQUEST_DATA = 9;
	public static final int COL_EXPECTED_RESPONSE_DATA = 10;

}
