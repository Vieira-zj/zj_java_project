package com.zhengjin.selenium.demo;

import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.zhengjin.apis.testutils.TestConstants;

public class PageUtil {

	private static WebDriver DRIVER = null;

	public static WebDriver initialDriver() {
		if (DRIVER == null) {
			System.setProperty("webdriver.chrome.driver",
					TestConstants.DRIVERS_DIR_ABS_PATH + File.separator + "chromedriver2.36.exe");
			DRIVER = new ChromeDriver();
			DRIVER.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		return DRIVER;
	}

	// initial web element with properties
	public static void initialWebElement(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(FindBy.class) && field.getType().equals(WebElementExt.class)) {
				FindBy findBy = field.getAnnotation(FindBy.class);
				FindElement findElement = new FindElement();
				if (!"".equals(findBy.id())) {
					findElement.setId(findBy.id());
				} else if (!"".equals(findBy.name())) {
					findElement.setName(findBy.name());
				} else if (!"".equals(findBy.className())) {
					findElement.setClassName(findBy.className());
				} else if (!"".equals(findBy.css())) {
					findElement.setCss(findBy.css());
				} else if (!"".equals(findBy.tagName())) {
					findElement.setTagName(findBy.tagName());
				} else if (!"".equals(findBy.linkText())) {
					findElement.setLinkText(findBy.linkText());
				} else if (!"".equals(findBy.partialLinkText())) {
					findElement.setPartialLinkText(findBy.partialLinkText());
				} else if (!"".equals(findBy.xpath())) {
					findElement.setXpath(findBy.xpath());
				}
				WebElementExt ext = new WebElementExt(findElement);
				field.setAccessible(true);
				try {
					field.set(obj, ext);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
