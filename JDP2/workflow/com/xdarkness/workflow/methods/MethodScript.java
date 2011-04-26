package com.xdarkness.workflow.methods;

import com.xdarkness.workflow.Context;

public class MethodScript extends NodeMethod {
	private String script;

	public MethodScript(String script) {
		this.script = script;
	}

	public void execute(Context context) throws EvalException {
		if (XString.isEmpty(this.script)) {
			return;
		}
		ScriptEngine se = new ScriptEngine(0);
		se.importPackage("com.xdarkness.framework.cache");
		se.importPackage("com.xdarkness.framework.data");
		se.importPackage("com.xdarkness.framework.utility");
		se.importPackage("com.xdarkness.workflow");
		se.compileFunction("_tmp", "return " + this.script);
		se.setVar("context", context);
		se.executeFunction("_tmp");
	}
}

/*
 * com.xdarkness.workflow.methods.MethodScript JD-Core Version: 0.6.0
 */