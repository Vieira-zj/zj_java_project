package com.zhengjin.ant.demo;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.zhengjin.apis.testutils.TestUtils;

public final class AntTaskDemo02 extends Task {

	private String mPropertyName;

	// get name of property
	public void setProperty(String text) {
		this.mPropertyName = text;
	}

	@Override
	public void execute() throws BuildException {
		TestUtils.printLog("Ant task demo: "
				+ AntTaskDemo02.class.getSimpleName());
		TestUtils.printLog("Property name: " + mPropertyName);

		// relate property with value
		this.getProject().setNewProperty(this.mPropertyName,
				"init value in Java");
	}

}
