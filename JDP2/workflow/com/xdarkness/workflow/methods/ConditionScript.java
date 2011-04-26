package com.xdarkness.workflow.methods;

import com.xdarkness.workflow.Context;

public class ConditionScript extends ConditionMethod {
	private String script;

	public void setScript(String script) {
		this.script = script;
	}

	public boolean validate(Context context) throws EvalException {
		if (XString.isEmpty(this.script)) {
			return true;
		}
		ScriptEngine se = new ScriptEngine(0);
		se.importPackage("com.xdarkness.framework.cache");
		se.importPackage("com.xdarkness.framework.data");
		se.importPackage("com.xdarkness.framework.utility");
		se.compileFunction("_tmp", "return " + this.script);
		se.setVar("context", context);
		Object obj = se.executeFunction("_tmp");
		if ((obj instanceof Boolean)) {
			return ((Boolean) obj).booleanValue();
		}
		throw new RuntimeException("流程条件脚本返回的不是布尔型!");
	}
}

/*
 * com.xdarkness.workflow.methods.ConditionScript JD-Core Version: 0.6.0
 */