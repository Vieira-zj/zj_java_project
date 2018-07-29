package com.zhengjin.selenium.demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementExt implements WebElement {

	private WebDriver driver = PageUtil.initialDriver();

	private FindElement findElement;

	public WebElementExt(FindElement findElement) {
		this.findElement = findElement;
	}

	private WebElement element;

	// invoke getWebElement() when do element action like click(), sendKeys()
	private WebElement getWebElement() {
		if (element != null) {
			return element;
		}

		if (findElement.getId() != null) {
			element = this.waitForElement(By.id(findElement.getId()));
		} else if (findElement.getName() != null) {
			element = this.waitForElement(By.name(findElement.getName()));
		} else if (findElement.getClassName() != null) {
			element = this.waitForElement(By.className(findElement.getClassName()));
		} else if (findElement.getCss() != null) {
			element = this.waitForElement(By.cssSelector(findElement.getCss()));
		} else if (findElement.getTagName() != null) {
			element = this.waitForElement(By.tagName(findElement.getTagName()));
		} else if (findElement.getLinkText() != null) {
			element = this.waitForElement(By.linkText(findElement.getLinkText()));
		} else if (findElement.getPartialLinkText() != null) {
			element = this.waitForElement(By.partialLinkText(findElement.getPartialLinkText()));
		} else if (findElement.getXpath() != null) {
			element = this.waitForElement(By.xpath(findElement.getXpath()));
		}

		if (this.waitElementToBeDisplayed(this.element)) {
			return this.element;
		}
		return null;
	}

	private WebElement waitForElement(final By by) {
		WebElement element = null;
		element = new WebDriverWait(driver, 20).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(by);
			}
		});
		return element;
	}

	private boolean waitElementToBeDisplayed(final WebElement element) {
		boolean wait = false;
		if (element == null) {
			return wait;
		}

		wait = new WebDriverWait(driver, 20).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return element.isDisplayed();
			}
		});
		return wait;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
		this.getWebElement();
		return this.element.getScreenshotAs(arg0);
	}

	@Override
	public void clear() {
		this.getWebElement();
		this.element.clear();
	}

	@Override
	public void click() {
		this.getWebElement();
		this.element.click();
	}

	@Override
	public WebElement findElement(By arg0) {
		this.getWebElement();
		return this.element.findElement(arg0);
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		this.getWebElement();
		return this.element.findElements(arg0);
	}

	@Override
	public String getAttribute(String arg0) {
		this.getWebElement();
		return this.element.getAttribute(arg0);
	}

	@Override
	public String getCssValue(String arg0) {
		this.getWebElement();
		return this.element.getCssValue(arg0);
	}

	@Override
	public Point getLocation() {
		this.getWebElement();
		return this.element.getLocation();
	}

	@Override
	public Rectangle getRect() {
		this.getWebElement();
		return this.element.getRect();
	}

	@Override
	public Dimension getSize() {
		this.getWebElement();
		return this.element.getSize();
	}

	@Override
	public String getTagName() {
		this.getWebElement();
		return this.element.getTagName();
	}

	@Override
	public String getText() {
		this.getWebElement();
		return this.element.getText();
	}

	@Override
	public boolean isDisplayed() {
		this.getWebElement();
		return this.element.isDisplayed();
	}

	@Override
	public boolean isEnabled() {
		this.getWebElement();
		return this.element.isEnabled();
	}

	@Override
	public boolean isSelected() {
		this.getWebElement();
		return this.element.isSelected();
	}

	@Override
	public void sendKeys(CharSequence... arg0) {
		this.getWebElement();
		this.element.sendKeys(arg0);
	}

	@Override
	public void submit() {
		this.getWebElement();
		this.element.submit();
	}

}
