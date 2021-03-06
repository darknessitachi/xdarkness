package com.xdarkness.jaf.controls.datagrid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.Current;
import com.xdarkness.jaf.controls.html.HtmlTable;

public class DataGridTag extends BodyTagSupport {
    
	private static final long serialVersionUID = 1L;
	private String method;
	private String sql;
	private String id;
	private boolean page = true;
	private int size;
	private boolean multiSelect = true;
	private boolean autoFill = true;
	private boolean scroll = false;
	private boolean lazy = false;
	private int cacheSize;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.method = null;
		this.sql = null;
		this.id = null;
		this.page = true;
		this.size = 0;
		this.multiSelect = true;
		this.autoFill = true;
		this.scroll = false;
		this.lazy = false;
		this.cacheSize = 0;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = getBodyContent();
		String content = body.getString().trim();
		try {
			if ((StringUtil.isEmpty(this.method))
					&& (StringUtil.isEmpty(this.sql))) {
				throw new RuntimeException("DataGrid既未指定Action，亦未指定SQL");
			}

			DataGridAction dga = new DataGridAction();
			dga.setMethod(this.method);
			dga.setTagBody(content);

			dga.setID(this.id);
			dga.getPageInfo().setPageFlag(this.page);
			dga.setMultiSelect(this.multiSelect);
			dga.setAutoFill(this.autoFill);
			dga.setScroll(this.scroll);
			dga.setCacheSize(this.cacheSize);
			dga.setLazy(this.lazy);

			HtmlTable table = new HtmlTable();
			table.parseHtml(content);
			dga.setTemplate(table);
			dga.parse();

			if (this.page) {
				dga.getPageInfo().setPageNum(0);
				// in my thinking,this is some trashy code here
				if (StringUtil.isNotEmpty(dga.getParam("_SKY_PAGEINDEX"))) {
					dga.getPageInfo().setPageNum(Integer.parseInt(dga
							.getParam("_SKY_PAGEINDEX")));
				}
				if (dga.getPageInfo().getPageNum() < 0) {
					dga.getPageInfo().setPageNum(0);
				}
				dga.getPageInfo().setPageSize(this.size);
			}

			if (this.lazy) {
				dga.bindData(new DataTable());
			} else {
				HttpServletRequest request = (HttpServletRequest) this.pageContext
						.getRequest();
				HttpServletResponse response = (HttpServletResponse) this.pageContext
						.getResponse();

				Current.init(request, response, this.method);
				dga.setParams(Current.getRequest());
				dga.Response = Current.getResponse();

				if (StringUtil.isNotEmpty(this.sql)) {
					this.method = "com.xdarkness.jaf.controls.DataGridPage.sqlBind";
					dga.getParams().put("_SKY_DATAGRID_SQL", this.sql);
				}

				Current.invokeMethod(this.method, new Object[] { dga });
			}
			getPreviousOut().write(dga.getHtml());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public boolean isPage() {
		return this.page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isMultiSelect() {
		return this.multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public boolean isAutoFill() {
		return this.autoFill;
	}

	public void setAutoFill(boolean autoFill) {
		this.autoFill = autoFill;
	}

	public boolean isScroll() {
		return this.scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	public int getCacheSize() {
		return this.cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public boolean isLazy() {
		return this.lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
}