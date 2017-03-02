package com.zhengjin.ant.demo;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.zhengjin.apis.testutils.TestUtils;

public final class AntTaskDemo01 extends Task {

	private String mText = "default message";

	// get input data from ant
	public void setMessage(String msg) {
		this.mText = msg;
	}

	@Override
	public void execute() throws BuildException {
		TestUtils.printLog("Ant task demo: "
				+ AntTaskDemo01.class.getSimpleName());
		TestUtils.printLog("Receive message: " + mText);
	}

}
