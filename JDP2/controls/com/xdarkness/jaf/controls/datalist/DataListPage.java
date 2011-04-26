// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataListPage.java

package com.xdarkness.jaf.controls.datalist;

import java.lang.reflect.Method;

import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.Ajax;

//            DataListAction

public class DataListPage extends Ajax {

	public DataListPage() {
	}

	public void doWork() {
		try {
			DataListAction dla = new DataListAction();
			dla.setTagBody(StringUtil.htmlDecode($V("_SKY_TAGBODY")));
			dla.getPageInfo().setPageFlag(
					"true".equalsIgnoreCase($V("_SKY_PAGE")));
			String method = $V("_SKY_METHOD");
			dla.setMethod(method);
			dla.setID($V("_SKY_ID"));
			dla.setParams(Request);
			dla.getPageInfo().setPageSize(Integer.parseInt($V("_SKY_SIZE")));
			if (dla.getPageInfo().isPageFlag()) {
				dla.getPageInfo().setPageNum(0);
				if (Request.get("_SKY_PAGEINDEX") != null
						&& !Request.get("_SKY_PAGEINDEX").equals(""))
					dla.getPageInfo().setPageNum(
							Integer.parseInt(Request.get("_SKY_PAGEINDEX")
									.toString()));
				if (dla.getPageInfo().getPageNum() < 0)
					dla.getPageInfo().setPageNum(0);
				dla.getPageInfo().setRowCount(0);
				if (Request.get("_SKY_PAGETOTAL") != null
						&& !Request.get("_SKY_PAGETOTAL").equals("")) {
					dla.getPageInfo().setPageNum(
							Integer.parseInt(Request.get("_SKY_PAGETOTAL")
									.toString()));
					if ((double) dla.getPageInfo().getPageNum() > Math
							.ceil(((double) dla.getPageInfo().getRowCount() * 1.0D)
									/ (double) dla.getPageInfo().getPageSize()))
						dla.getPageInfo().setPageNum(
								(new Double(Math
										.floor(((double) dla.getPageInfo().getRowCount() * 1.0D)
												/ (double) dla.getPageInfo().getPageSize())))
										.intValue());
				}
			}
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c
					.getMethod(
							method,
							new Class[] { com.xdarkness.jaf.controls.datalist.DataListAction.class });
			m.invoke(null, new Object[] { dla });
			$S("HTML", dla.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
