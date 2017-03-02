package com.zhengjin.ant.demo;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.zhengjin.apis.testutils.TestUtils;

public final class AntTaskDemo01 extends Task {

	private String msg = "default message";
	
	public void setMessage(String msg) {
		this.msg = msg;
	}

	@Override
	public void execute() throws BuildException {
		// get data from ant task and print
		TestUtils.printLog("Ant task demo: " + AntTaskDemo01.class.getSimpleName());
		TestUtils.printLog("Receive message: " + msg);
	}

}
