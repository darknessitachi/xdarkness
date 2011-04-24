// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataListTag.java

package com.xdarkness.jaf.controls.datalist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.Current;

//            DataListAction

public class DataListTag extends BodyTagSupport {

	public DataListTag() {
	}

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		method = null;
		id = null;
		page = true;
		size = 0;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = getBodyContent();
		String content = body.getString().trim();
		try {
			if (StringUtil.isEmpty(method))
				throw new RuntimeException("DataList\u672A\u6307\u5B9AMethod");
			DataListAction dla = new DataListAction();
			dla.setTagBody(content);
			dla.getPageInfo().setPageFlag(page);
			dla.setMethod(method);
			dla.setID(id);
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			dla.getPageInfo().setPageSize(size);
			if (page) {
				dla.getPageInfo().setPageNum(0);
				if (StringUtil.isNotEmpty(dla.getParam("_SKY_PAGEINDEX")))
					dla.getPageInfo().setPageNum(
							Integer.parseInt(dla.getParam("_SKY_PAGEINDEX")));
				if (dla.getPageInfo().getPageNum() < 0)
					dla.getPageInfo().setPageNum(0);
				dla.getPageInfo().setPageSize(size);
			}
			Current.init(request, response, method);
			dla.setParams(Current.getRequest());
			Current.invokeMethod(method, new Object[] { dla });
			pageContext.setAttribute(id + "_SKY_PAGETOTAL", dla.getPageInfo().getRowCount());
			pageContext.setAttribute(id + "_SKY_PAGEINDEX", dla.getPageInfo().getPageNum());
			pageContext.setAttribute(id + "_SKY_SIZE", dla.getPageInfo().getPageSize());
			getPreviousOut().write(dla.getHtml());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return 6;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	private static final long serialVersionUID = 1L;
	private String method;
	private String id;
	private int size;
	private boolean page;
}
