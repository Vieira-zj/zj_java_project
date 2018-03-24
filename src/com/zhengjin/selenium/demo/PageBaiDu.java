package com.zhengjin.selenium.demo;

public class PageBaiDu {

	public PageBaiDu() {
		PageUtil.initialWebElement(this);
	}

	@FindBy(id = "kw")
	WebElementExt input;

	@FindBy(id = "su")
	WebElementExt submitBtn;

	public void searchKeyword(String keyword) {
		input.sendKeys(keyword);
		submitBtn.click();
	}

}
