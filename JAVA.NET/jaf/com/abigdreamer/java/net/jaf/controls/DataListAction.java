package com.abigdreamer.java.net.jaf.controls;

import java.util.regex.Matcher;

import com.abigdreamer.java.net.Constant;
import com.abigdreamer.java.net.jaf.html.element.HtmlScript;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.HtmlUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class DataListAction implements IControlAction {
	private DataTable DataSource;
	private String ID;
	private String TagBody;
	private boolean page;
	protected Mapx Params = new Mapx();
	private String method;
	private int total;
	private int pageIndex;
	private int pageSize;
	boolean TotalFlag = false;

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return this.Params;
	}

	public void setParams(Mapx params) {
		this.Params = params;
	}

	public String getParam(String key) {
		return this.Params.getString(key);
	}

	public void bindData(DataTable dt) {
		this.DataSource = dt;
		try {
			bindData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
	}

	private void bindData() throws Exception {
	}

	public void bindData(QueryBuilder qb) {
		bindData(qb, this.page);
	}

	public void bindData(QueryBuilder qb, boolean pageFlag) {
		if (pageFlag) {
			if (!this.TotalFlag) {
				setTotal(qb.getCount());
			}
			bindData(qb.executePagedDataTable(this.pageSize, this.pageIndex));
		} else {
			bindData(qb.executeDataTable());
		}
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<!--_SKY_DATALIST_START_" + this.ID + "-->");
		sb.append("<input type='hidden' id='" + this.ID + "' method='"
				+ this.method + "'>");
		sb.append(HtmlUtil.replaceWithDataTable(this.DataSource, this.TagBody));
		HtmlScript script = new HtmlScript();
		script.setAttribute("xtype", "DataList");
		script.setInnerHTML(getScript());
		sb.append(script.getOuterHtml());
		sb.append("<!--_SKY_DATALIST_END_" + this.ID + "-->");
		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();

		sb.append("$('" + this.ID + "').TagBody = \""
				+ XString.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object[] ks = this.Params.keyArray();
		Object[] vs = this.Params.valueArray();
		for (int i = 0; i < this.Params.size(); i++) {
			Object key = ks[i];
			if ((key.equals("_SKY_TAGBODY"))
					|| (key.toString().startsWith("Cookie."))
					|| (key.toString().startsWith("Header."))
					|| (vs[i] == null))
				continue;
			sb.append("DataList.setParam('" + this.ID + "','" + key + "',\""
					+ XString.javaEncode(vs[i].toString()) + "\");");
		}

		if (this.page) {
			int type = 1;
			if (XString.isNotEmpty(this.Params.getString("PageBarType"))) {
				type = Integer.parseInt(this.Params.getString("PageBarType"));
			}
			String html = PageBarTag.getPageBarHtml(this.ID, type, this.total,
					this.pageSize, this.pageIndex);
			sb.append("\ntry{$('_PageBar_" + this.ID + "').outerHTML=\""
					+ XString.javaEncode(html) + "\";}catch(ex){}\n");
		}

		sb.append("DataList.setParam('" + this.ID + "','" + "_SKY_PAGEINDEX"
				+ "'," + this.pageIndex + ");");
		sb.append("DataList.setParam('" + this.ID + "','" + "_SKY_PAGETOTAL"
				+ "'," + this.total + ");");
		sb.append("DataList.setParam('" + this.ID + "','" + "_SKY_PAGE"
				+ "'," + this.page + ");");
		sb.append("DataList.setParam('" + this.ID + "','" + "_SKY_SIZE"
				+ "'," + this.pageSize + ");");
		sb.append("");
		sb.append("DataList.init('" + this.ID + "');");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	public String getTagBody() {
		return this.TagBody;
	}

	public void setTagBody(String tagBody) {
		this.TagBody = tagBody;
	}

	public DataTable getDataSource() {
		return this.DataSource;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public boolean isPage() {
		return this.page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
		if (this.pageIndex > Math.ceil(total * 1.0D / this.pageSize)) {
			this.pageIndex = new Double(Math
					.floor(total * 1.0D / this.pageSize)).intValue();
		}
		this.TotalFlag = true;
	}

	public void setTotal(QueryBuilder qb) {
		if (this.pageIndex == 0)
			setTotal(qb.getCount());
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

/*
 * com.xdarkness.framework.controls.DataListAction JD-Core Version: 0.6.0
 */