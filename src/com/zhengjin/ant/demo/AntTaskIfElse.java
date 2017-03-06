package com.zhengjin.ant.demo;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Sequential;
import org.apache.tools.ant.taskdefs.condition.And;

public class AntTaskIfElse extends Task {
	private boolean mCondition;
	private boolean mConditionIsSet = false;

	private And mAnd;
	private Sequential mThen;
	private Sequential mElse;

	public void setCondition(boolean condition) {
		this.mCondition = condition;
		this.mConditionIsSet = true;
	}

	public Object createCondition() {
		if (this.mConditionIsSet) {
			throw new BuildException(
					"Cannot use both condition attribute and <condition> element");
		}

		this.mAnd = new And();
		this.mAnd.setProject(this.getProject());
		return this.mAnd;
	}

	public Object createThen() {
		this.mThen = new Sequential();
		return this.mThen;
	}

	public Object createElse() {
		this.mElse = new Sequential();
		return this.mElse;
	}

	@Override
	public void execute() throws BuildException {
		if ((!this.mConditionIsSet) && (this.mAnd == null)) {
			throw new BuildException(
					"condition attribute or element must be set.");
		}

		if (this.mAnd != null) {
			this.mCondition = this.mAnd.eval();
		}

		if ((this.mThen == null) && (this.mElse == null)) {
			throw new BuildException("Need at least <then> or <else>");
		}

		if (this.mCondition) {
			if (this.mThen != null) {
				this.mThen.execute();
			}
		} else if (this.mElse != null) {
			this.mElse.execute();
		}
	}
	
}
