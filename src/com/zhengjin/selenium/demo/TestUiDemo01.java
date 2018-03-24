package com.zhengjin.selenium.demo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zhengjin.apis.testutils.TestConstants;

public class TestUiDemo01 {

	private final String url = "https://www.baidu.com";

	@Test
	public void test01BaiduTitleFromChrome() {
		System.setProperty("webdriver.chrome.driver",
				TestConstants.DRIVERS_DIR_ABS_PATH + File.separator + "chromedriver2.36.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.get(url);
		System.out.println("Page title: " + driver.getTitle());

		WebElement input = driver.findElement(By.id("kw"));
		input.sendKeys("Selenium examples");
		WebElement submitBtn = driver.findElement(By.id("su"));
		submitBtn.click();

		driver.close();
	}

	@Test
	@Ignore
	public void test02BaiduUrlFromFirefox() {
		System.setProperty("webdriver.firefox.bin", "D:\\Program_Files\\Mozilla Firefox\\firefox.exe");
		System.setProperty("webdriver.gecko.driver",
				TestConstants.DRIVERS_DIR_ABS_PATH + File.separator + "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		driver.get(url);
		System.out.println("Page url: " + driver.getCurrentUrl());

		driver.close();
	}

}
