package com.abigdreamer.java.net.jaf.html.element;

import java.util.regex.Matcher;

/**
 * 
 * @author Darkness create on 2011 2011-1-7 下午04:56:22
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class HtmlTD extends HtmlElement {
	private HtmlTR parent;

	public HtmlTD() {
		this(null);
	}

	public HtmlTD(HtmlTR parent) {
		this.parent = parent;
		this.ElementType = "TD";
		this.TagName = "td";
	}

	/**
	 * 设置跨列
	 * 
	 * @param colSpan
	 */
	public void setColSpan(String colSpan) {
		setAttribute("colSpan", colSpan);
	}

	/**
	 * 获取跨列
	 * 
	 * @return
	 */
	public String getColSpan() {
		return getAttribute("colSpan");
	}

	/**
	 * 设置跨行
	 * 
	 * @param rowSpan
	 */
	public void setRowSpan(String rowSpan) {
		setAttribute("rowSpan", rowSpan);
	}

	/**
	 * 获取跨行
	 * 
	 * @return
	 */
	public String getRowSpan() {
		return getAttribute("rowSpan");
	}

	/**
	 * 获取在行中的位置
	 * 
	 * @return
	 */
	public int getCellIndex() {
		for (int i = 0; i < this.ParentElement.Children.size(); i++) {
			if (this.ParentElement.Children.get(i).equals(this)) {
				return i;
			}
		}
		throw new RuntimeException("得到RowIndex时发生错误");
	}

	/**
	 * 解析html
	 */
	public void parseHtml(String html) throws Exception {
		Matcher m = HtmlTable.PTD.matcher(html);
		if (!m.find()) {
			throw new Exception(this.TagName + "解析html时发生错误");
		}
		this.TagName = m.group(1);
		String attrs = m.group(2);

		this.Attributes.clear();
		this.Children.clear();

		this.Attributes = parseAttr(attrs);
		this.InnerHTML = m.group(3).trim();

		if (this.parent != null) {
			String newHtml = this.parent.restoreInnerTable(this.InnerHTML);
			if (newHtml.equals(this.InnerHTML)) {
				if (this.parent.getParent() != null)
					setInnerHTML(this.parent.getParent().restoreInnerTable(
							this.InnerHTML));
			} else
				setInnerHTML(newHtml);
		}
	}

	public HtmlTR getParent() {
		return this.parent;
	}

	/**
	 * 是否是表头
	 * 
	 * @return
	 */
	public boolean isHead() {
		return this.TagName.equalsIgnoreCase("th");
	}

	/**
	 * 设置表头
	 * 
	 * @param isHead
	 */
	public void setHead(boolean isHead) {
		this.TagName = isHead ? "th" : "tr";
	}
}
