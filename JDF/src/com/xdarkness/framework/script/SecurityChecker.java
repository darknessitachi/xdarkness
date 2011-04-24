package com.xdarkness.framework.script;

import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.util.LogUtil;

public class SecurityChecker {
	protected static String[] CheckPrefixs = { "com.", "org.", "net.",
			"oracle.", "java.", "sun.", "javax.", "System.", "Runtime.",
			"Process.", "Package.", "Thread.", "ThreadGroup." };

	public static String clear(String script) {
		char[] cs = script.toCharArray();
		char lastStringChar = '\000';
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (lastStringChar == 0) {
				if ((c == '\'') || (c == '"')) {
					sb.append(script.substring(index, i));
					lastStringChar = c;
				}
			} else if (!flag) {
				if (c == '\\') {
					flag = true;
				}
				if (c == lastStringChar) {
					lastStringChar = '\000';
					index = i + 1;
				}
			} else {
				flag = false;
			}
		}

		return sb.toString();
	}

	public static boolean check(String script) {
		String str = clear(script);
		for (int i = 0; i < CheckPrefixs.length; i++) {
			String prefix = CheckPrefixs[i];
			int index = str.indexOf(prefix);
			if ((index >= 0)
					&& ((index == 0) || (!Character.isJavaIdentifierPart(str
							.charAt(index - 1))))) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		String str = FileUtil.readText("H:/Script.txt");
		LogUtil.info(clear(str));
	}
}