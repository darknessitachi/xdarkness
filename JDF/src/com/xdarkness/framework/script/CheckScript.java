package com.xdarkness.framework.script;

import com.xdarkness.framework.jaf.Page;

public class CheckScript extends Page {
	public void check() {
		String script = $V("Script");
		String lang = $V("Language");
		ScriptEngine se = new ScriptEngine(lang.equalsIgnoreCase("java") ? 1
				: 0);
		try {
			se.compileFunction("Test", script);
		} catch (EvalException ex) {
			StringBuffer sb = new StringBuffer();
			sb.append("第");
			sb.append(ex.getRowNo());
			sb.append("有语法错误:<br><font color='red'>");
			sb.append(ex.getLineSource());
			sb.append("</font>");
			this.response.setError(sb.toString());
			return;
		}
		this.response.setStatus(1);
	}
}