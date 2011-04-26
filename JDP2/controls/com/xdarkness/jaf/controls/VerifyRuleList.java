// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VerifyRuleList.java

package com.xdarkness.jaf.controls;

import java.util.ArrayList;

import com.xdarkness.jaf.JafRequest;
import com.xdarkness.jaf.JafResponse;

public class VerifyRuleList {

	public VerifyRuleList() {
		array = new ArrayList();
	}

	public void add(String fieldID, String fieldName, String rule) {
		array.add(new String[] { fieldID, fieldName, rule });
	}

	public boolean doVerify() {
		VerifyRule rule = new VerifyRule();
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.size(); i++) {
			String f[] = (String[]) array.get(i);
			rule.setRule(f[2]);
			if (!rule.verify(Request.getString(f[0]))) {
				sb.append(rule.getMessages(f[1]));
				flag = false;
			}
		}

		if (!flag) {
			Response.setStatus(0);
			Message = sb.toString();
			Response.setMessage(sb.toString());
		}
		return flag;
	}

	public String getMessage() {
		return Message;
	}

	public JafRequest getRequest() {
		return Request;
	}

	public void setRequest(JafRequest request) {
		Request = request;
	}

	public JafResponse getResponse() {
		return Response;
	}

	public void setResponse(JafResponse response) {
		Response = response;
	}

	private ArrayList array;
	private String Message;
	private JafRequest Request;
	private JafResponse Response;
}
