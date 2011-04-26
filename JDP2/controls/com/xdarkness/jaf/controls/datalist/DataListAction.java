// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataListAction.java

package com.xdarkness.jaf.controls.datalist;

import java.util.regex.Matcher;

import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.controls.Constant;
import com.xdarkness.jaf.controls.HtmlUtil;
import com.xdarkness.jaf.controls.IControlAction;
import com.xdarkness.jaf.controls.PageBarTag;
import com.xdarkness.jaf.controls.datagrid.PageInfo;
import com.xdarkness.jaf.controls.html.HtmlScript;

public class DataListAction implements IControlAction {

	public DataListAction() {
		Params = new Mapx();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	public String getParam(String key) {
		return Params.getString(key);
	}

	public void bindData(DataTable dt) {
		DataSource = dt;
		try {
			bindData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindData() throws Exception {
		HtmlUtil.replaceWithDataTable(DataSource, TagBody);
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<!--_SKY_DATALIST_START_" + ID + "-->");
		sb.append("<input type='hidden' id='" + ID + "' method='" + method
				+ "'>");
		sb.append(HtmlUtil.replaceWithDataTable(DataSource, TagBody));
		HtmlScript script = new HtmlScript();
		script.setInnerHTML(getScript());
		sb.append(script.getOuterHtml());
		sb.append("<!--_SKY_DATALIST_END_" + ID + "-->");
		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("$('" + ID + "').TagBody = \""
				+ StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object ks[] = Params.keyArray();
		Object vs[] = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals("_SKY_TAGBODY")
					&& !key.toString().startsWith("Cookie.")
					&& !key.toString().startsWith("Header."))
				sb.append("DataList.setParam('" + ID + "','" + key + "',\""
						+ vs[i] + "\");");
		}

		if (page) {
			int type = 1;
			if (StringUtil.isNotEmpty(Params.getString("PageBarType")))
				type = Integer.parseInt(Params.getString("PageBarType"));
			String html = PageBarTag.getPageBarHtml(ID, type, total, pageSize,
					pageIndex);
			sb.append("\ntry{$('_PageBar_" + ID + "').innerHTML=\""
					+ StringUtil.javaEncode(html) + "\";}catch(ex){}\n");
		}
		sb.append("DataList.setParam('" + ID + "','" + "_SKY_PAGEINDEX" + "',"
				+ pageIndex + ");");
		sb.append("DataList.setParam('" + ID + "','" + "_SKY_PAGETOTAL" + "',"
				+ total + ");");
		sb.append("DataList.setParam('" + ID + "','" + "_SKY_PAGE" + "',"
				+ page + ");");
		sb.append("DataList.setParam('" + ID + "','" + "_SKY_SIZE" + "',"
				+ pageSize + ");");
		sb.append("");
		sb.append("DataList.init('" + ID + "');");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex;
		for (lastEndIndex = 0; matcher.find(lastEndIndex); lastEndIndex = matcher
				.end()) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
		}

		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public DataTable getDataSource() {
		return DataSource;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	private DataTable DataSource;
	private String ID;
	private String TagBody;
	private boolean page;
	protected PageInfo pageInfo;
	public PageInfo getPageInfo() {
		if(pageInfo == null)
			pageInfo = new PageInfo();
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	protected Mapx Params;
	private String method;
	private int total;
	private int pageIndex;
	private int pageSize;
}
