package com.zhengjin.restassured.demo;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	private static RequestSpecification requestWithParams = null;
	private static Response responseJson = null;

	@BeforeClass
	public static void classSetup() {
		requestWithParams = buildRequestWithParams();
	}

	private static RequestSpecification buildRequestWithParams() {
		return given().param("plat_type", "funtv").param("version", "2.8.0.8_s").param("sid", "FD5551A-SU")
				.param("account", "28:76:CD:01:96:F6").param("random", "1483947198639484")
				.param("sign", "1cec28e1006cee8f5fd7f678487dd28f").param("province").param("city").param("area")
				.param("cityId", "101200101");
	}

	@Before
	public void setup() {
		if (responseJson == null) {
			Assert.assertNotNull(requestWithParams);

			responseJson = requestWithParams.when().get(baseUrl);
			responseJson.then().log().ifError();
			responseJson.then().log().ifValidationFails();
		}
	}

	@Test
	public void test01ResponseTime() {
		requestWithParams.when().get(baseUrl).then().time(lessThan(800L), TimeUnit.MILLISECONDS);
	}

	@Test
	public void test02RespStatusCode() {
		// requestWithParams.log().all();
		requestWithParams.log().params();
		responseJson.then().assertThat().statusCode(200);
	}

	@Test
	public void test03RespRetCodeAndMessage() {
		responseJson.then().assertThat().contentType(ContentType.JSON);
		responseJson.then().body("retCode", equalTo("200"));
		responseJson.then().body("retMsg", equalTo("ok"));
	}

	@Test
	public void test04PrintRespBody() {
		// responseJson.then().log().all();
		responseJson.then().log().body();
		// responseJson.getBody().prettyPrint();
	}

	@Test
	public void test05RespDataElements() {
		responseJson.then().body("data.cityId", equalTo("101200101"));
		responseJson.then().body("data.forecast", hasSize(5));
		responseJson.then().body("data.forecast[0].highTemp", lessThan(40));
	}

	@Test
	public void test06RespDataElementsByRoot() {
		responseJson.then().root("data.today").body("date", equalTo(this.getCurrentDate()))
				.body("highTemp", lessThan(40)).body("lowTemp", greaterThan(-10));
	}

	private String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	@Test
	public void test07JsonSchemaValidation() {
		final String filePath = TestConstants.TEST_DATA_PATH + "json_schema_test.json";
		File jsonSchemaFile = new File(filePath);
		if (!jsonSchemaFile.exists()) {
			Assert.fail("The json schema file not exist!");
		}

		responseJson.then().assertThat().body(matchesJsonSchema(jsonSchemaFile));
	}

}
