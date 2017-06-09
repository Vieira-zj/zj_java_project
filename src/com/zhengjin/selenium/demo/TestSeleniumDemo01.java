package com.zhengjin.selenium.demo;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zhengjin.apis.testutils.TestConstants;

public class TestSeleniumDemo01 {

	private final String DRIVERS_DIR_ABS_PATH = TestConstants.PROJECT_ROOT_PATH + File.separator + "drivers";

	@Test
	public void test01BaiduTitleFromChrome() {
		final String url = "http://www.baidu.com";

		System.setProperty("webdriver.chrome.driver", DRIVERS_DIR_ABS_PATH + File.separator + "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		driver.get(url);
		System.out.println("Page title: " + driver.getTitle());

		WebElement input = driver.findElement(By.id("kw"));
		input.sendKeys("Selenium examples");

		WebElement submitBtn = driver.findElement(By.id("su"));
		submitBtn.click();

		driver.close();
	}

	@Test
	public void test02BaiduUrlFromFirefox() {
		final String url = "http://www.baidu.com";

		System.setProperty("webdriver.firefox.bin", "D:\\Program_Files\\Mozilla Firefox\\firefox.exe");
		System.setProperty("webdriver.gecko.driver", DRIVERS_DIR_ABS_PATH + File.separator + "geckodriver.exe");

		WebDriver driver = new FirefoxDriver();
		driver.get(url);

		System.out.println("Page url: " + driver.getCurrentUrl());

		driver.close();
	}

}
