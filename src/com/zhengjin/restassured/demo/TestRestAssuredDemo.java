package com.zhengjin.restassured.demo;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;

import com.zhengjin.apis.testutils.TestConstants;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRestAssuredDemo {

	// rest-assured usage:
	// https://github.com/rest-assured/rest-assured/wiki/Usage

	private static final String baseUrl = "http://card.tv.funshion.com/api/rest/weather/tv/get";

	private static RequestSpecification requestParams = null;
	private static Response responseJson = null;

	@BeforeClass
	public static void classSetup() {
		requestParams = buildRequestParams();
	}

	private static RequestSpecification buildRequestParams() {
		return given().param("plat_type", "funtv")
				.param("version", "2.8.0.8_s").param("sid", "FD5551A-SU")
				.param("account", "28:76:CD:01:96:F6")
				.param("random", "1483947198639484")
				.param("sign", "1cec28e1006cee8f5fd7f678487dd28f")
				.param("province").param("city").param("area")
				.param("cityId", "101200101");
	}

	@Before
	public void setup() {
		Assert.assertNotNull(requestParams);
	}

	@Test
	public void test01RespStatusCode() {
		requestParams.log().all(); // print request data
		requestParams.when().get(baseUrl).then().assertThat().statusCode(200);
	}

	@Test
	public void test02RespTime() {
		requestParams.when().get(baseUrl).then()
				.time(lessThan(1000L), TimeUnit.MILLISECONDS);
	}

	@Test
	public void test03PrintRespBody() {
		if (responseJson == null) {
			responseJson = requestParams.when().get(baseUrl);
		}
		responseJson.getBody().prettyPrint(); // print response data
	}

	@Test
	public void test04RespRetCodeAndMessage() {
		if (responseJson == null) {
			responseJson = requestParams.when().get(baseUrl);
		}
		responseJson.then().body("retCode", equalTo("200"));
		responseJson.then().body("retMsg", equalTo("ok"));
	}

	@Test
	public void test05RespDataElements() {
		if (responseJson == null) {
			responseJson = requestParams.when().get(baseUrl);
		}
		responseJson.then().body("data.cityId", equalTo("101200101"));
		responseJson.then().body("data.forecast", hasSize(5));
		responseJson.then().body("data.forecast[0].highTemp", lessThan(40));
	}

	@Test
	public void test06JsonSchemaValidation() {
		if (responseJson == null) {
			responseJson = requestParams.when().get(baseUrl);
		}

		final String filePath = TestConstants.TEST_DATA_PATH
				+ "json_schema_test.json";
		File jsonSchemaFile = new File(filePath);
		if (!jsonSchemaFile.exists()) {
			Assert.fail("The json schema file not exist!");
		}
		responseJson.then().assertThat()
				.body(matchesJsonSchema(jsonSchemaFile));
	}

}
