package com.xdarkness.framework.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串格式化处理类
 * 
 * @author Darkness Create on 2010-6-1 上午11:03:53
 * @version 1.0
 * @since JDF1.0
 */
public class StringFormat {

	public static final String LEFT_BRACE = "\\u007B";// u007B为{的转义字符

	public static final String POINT = "\\u002E";// .的转义字符

	private static final Pattern PField = Pattern.compile(LEFT_BRACE + ".*?}",
			Pattern.CASE_INSENSITIVE);

	private String formatStr;

	private ArrayList<String> params;

	private ArrayList<String> fields;

	public StringFormat(String str, String... inParams) {

		params = new ArrayList<String>();

		if (inParams != null) {
			for (int i = 0; i < inParams.length; i++) {
				params.add(inParams[i]);
			}
		}

		fields = new ArrayList<String>();
		int lastEndIndex = 0;
		Matcher matcher = PField.matcher(str);
		while (matcher.find(lastEndIndex)) {
			String fieldString = str.substring(matcher.start(), matcher.end());
			fields.add(fieldString);
			lastEndIndex = matcher.end();
		}

		formatStr = str;
	}

	public StringFormat add(String v) {
		params.add(v);
		return this;
	}

	public StringFormat add(long v) {
		add(String.valueOf(v));
		return this;
	}

	public StringFormat add(int v) {
		add(String.valueOf(v));
		return this;
	}

	public StringFormat add(float v) {
		add(String.valueOf(v));
		return this;
	}

	public StringFormat add(double v) {
		add(String.valueOf(v));
		return this;
	}

	public StringFormat add(Object v) {
		add(String.valueOf(v));
		return this;
	}

	public String toString() {

		if (fields.size() == 0) {
			String arr[] = XString.splitEx(formatStr, "?");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length - 1; i++) {
				sb.append(arr[i]);
				sb.append(params.get(i));
			}
			sb.append(arr[arr.length - 1]);
			return sb.toString();
		}

		for (int i = 0; i < fields.size(); i++) {
			String field = fields.get(i);//getIncodeString(fields.get(i));
			String param = params.get(i);
			formatStr = XString.replaceFirst(formatStr, field, param);
		}
		return formatStr;

	}

	/**
	 * 点的转义：. ==> u002E 美元符号的转义：$ ==> u0024 乘方符号的转义：^ ==> u005E 左大括号的转义：{ ==>
	 * u007B 左方括号的转义：[ ==> u005B 左圆括号的转义：( ==> u0028 竖线的转义：| ==> u007C 右圆括号的转义：)
	 * ==> u0029 星号的转义：* ==> u002A 加号的转义：+ ==> u002B 问号的转义：? ==> u003F 反斜杠的转义：\
	 * ==> u005C
	 * 
	 * @param string
	 * @return
	 */
	private String getIncodeString(String string) {
		return string.replace(".", "\\u002E").replace("$", "\\u0024").replace(
				"^", "\\u005E")
		// .replace("\\", "\\u005C")
				.replace("?", "\\u003F").replace("+", "\\u002B").replace("*",
						"\\u002A").replace(")", "\\u0029").replace("|",
						"\\u007C").replace("(", "\\u0028").replace("[",
						"\\u005B").replace("{", "\\u007B");
	}

	public static void main(String args[]) {
		StringFormat sf = new StringFormat("c ? b ? a ");
		sf.add(1);
		sf.add(2);
		sf.add(3);
		System.out.println(sf);
		
		StringFormat stringFormat = new StringFormat("Hello {name}, {1}!", "darkness", "Welcome");
		System.out.println(stringFormat);
	}

}
