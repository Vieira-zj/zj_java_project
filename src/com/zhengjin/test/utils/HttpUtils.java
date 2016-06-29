package com.zhengjin.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;

public final class HttpUtils {

	private static final int BUFFER_SIZE = 1000;
	
	public static String sendGetRequest(String path) {
		
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		
		try {
			URL url = new URL(path);
			httpConn = (HttpURLConnection)url.openConnection();
			
			// read response
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer(BUFFER_SIZE);
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				while((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				return content.toString();
			} else {
				Assert.assertTrue("Error, the http GET status code is NOT 200!", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format("Error, IOException(%s) in http GET request!", e.getMessage()), false);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpConn.disconnect();
		}
		
		return "";
	}
	
	public static String sendJsonPostRequest(String path, String parms) {
		
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			URL url = new URL(path);
			httpConn = (HttpURLConnection)url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setConnectTimeout(TestConstants.TIME_OUT);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			
			// send post request
			out = new PrintWriter(httpConn.getOutputStream());
			out.println(parms);
			out.flush();
			
			// read response
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer(BUFFER_SIZE);
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				while((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				return content.toString();
			} else {  // use assert here instead of throw exception
				Assert.assertTrue("Error, the http POST status code is NOT 200!", false);
			}
		} catch(IOException e) {
			e.printStackTrace();
			Assert.assertTrue(String.format("Error, IOException(%s) in http POST request!", e.getMessage()), false);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				out.close();
			}
			httpConn.disconnect();
		}
		
		return "";
	}
}
