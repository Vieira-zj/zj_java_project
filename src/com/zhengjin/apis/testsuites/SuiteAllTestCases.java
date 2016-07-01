package com.zhengjin.apis.testsuites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.zhengjin.apis.testcases.TestInterfaceDemo;
import com.zhengjin.apis.testcases.TestScreenSaverJsonInterface;

@RunWith(Categories.class)
@SuiteClasses({TestInterfaceDemo.class, TestScreenSaverJsonInterface.class})
public final class SuiteAllTestCases {

}
