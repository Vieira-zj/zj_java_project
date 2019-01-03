package com.zhengjin.restassured.demo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.junit.Test;

import com.zhengjin.apis.testutils.FileUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestJsonRequest {

	@Test
	public void testJsonPathDemo01() {
		String path = "/Users/zhengjin/Downloads/tmp_files/jq.test.txt";
		String jsonBody = FileUtils.readFileContent(path);
		JsonPath json = new JsonPath(jsonBody);

		System.out.println("status: " + json.getString("status"));
	}

	@Test
	public void testJsonPathDemo02() {
		String path = "/Users/zhengjin/Downloads/tmp_files/jq.test.txt";
		JsonPath json = JsonPath.from(new File(path));

		System.out.println("status: " + json.getString("status"));
		System.out.println("scores: " + json.getDouble("instances[0].scores[0]"));
	}

	@Test
	public void testJsonRequestDemo01() {
		String path = "/Users/zhengjin/Downloads/tmp_files/json.test.txt";
		String jsonBody = FileUtils.readFileContent(path);

		String url = "http://172.27.133.111:30423/api/predict";
		Response resp = given().contentType("application/json").body(jsonBody).post(url);
		resp.then().assertThat().statusCode(200);
		resp.then().body("status", equalTo("OK"));
		System.out.println("response: " + resp.asString());

		JsonPath json = JsonPath.from(resp.asString());
		System.out.println("scores: " + json.getDouble("instances[0].scores[0]"));
	}

}
