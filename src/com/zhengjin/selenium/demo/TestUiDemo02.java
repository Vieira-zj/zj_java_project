package com.zhengjin.selenium.demo;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class TestUiDemo02 {

	@Test
	public void testBaiduSearch() {
		final String url = "https://www.baidu.com";
		WebDriver driver = PageUtil.initialDriver();

		driver.get(url);
		System.out.println("Page url: " + driver.getCurrentUrl());

		PageBaiDu baidu = new PageBaiDu();
		baidu.searchKeyword("selenium test");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.close();
	}
	
}
