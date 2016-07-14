package com.zhengjin.apis.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.zhengjin.apis.testcases.TestInterfaceDemo;
import com.zhengjin.apis.testcases.TestScreenSaverJsonInterface;
import com.zhengjin.apis.testcategory.CategoryDemoTest;

@RunWith(Categories.class)
@ExcludeCategory(CategoryDemoTest.class)
@SuiteClasses({TestInterfaceDemo.class, TestScreenSaverJsonInterface.class})
public final class SuiteAllTestCases {
}
