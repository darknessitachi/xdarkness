package com.abigdreamer.java.net.jaf.html.element;

import java.util.ArrayList;
import java.util.regex.Matcher;

import com.abigdreamer.java.net.util.XString;

/**
 * 
 * @author Darkness create on 2011 2011-1-7 下午04:35:52
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class HtmlTR extends HtmlElement {
	private HtmlTable parent;
	private ArrayList<String> pList = null;

	public HtmlTR() {
		this(null);
	}

	public HtmlTR(HtmlTable parent) {
		this.parent = parent;
		this.ElementType = "TR";
		this.TagName = "tr";
	}

	/**
	 * 添加列
	 * @param td
	 */
	public void addTD(HtmlTD td) {
		addChild(td);
	}

	/**
	 * 获取列
	 * @param index
	 * @return
	 */
	public HtmlTD getTD(int index) {
		return (HtmlTD) this.Children.get(index);
	}

	/**
	 * 删除列
	 * @param index
	 */
	public void removeTD(int index) {
		if ((index < 0) || (index > this.Children.size())) {
			throw new RuntimeException("错误的索引");
		}
		this.Children.remove(index);
	}

	/**
	 * 获取当前列在行中的位置
	 * @return
	 */
	public int getRowIndex() {
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
		Matcher m = HtmlTable.PTR.matcher(html);
		if (!m.find()) {
			throw new Exception("TR解析html时发生错误");
		}
		String attrs = m.group(1);
		String tds = m.group(2).trim();

		this.Attributes.clear();
		this.Children.clear();

		m = HtmlTable.PInnerTable.matcher(tds);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			if (this.pList == null) {
				this.pList = new ArrayList<String>();
			}
			this.pList.add(m.group(0));
			lastEndIndex = m.end();
		}
		if (this.pList != null) {
			for (int i = 0; i < this.pList.size(); i++) {
				tds = XString.replaceEx(tds, this.pList.get(i).toString(),
						"<!--_SKY_INNERTABLE_PROTECTED_" + i + "-->");
			}
		}

		this.Attributes = parseAttr(attrs);

		m = HtmlTable.PTDPre.matcher(tds);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String t = tds.substring(m.start(), m.end());
			HtmlTD td = new HtmlTD(this);
			td.parseHtml(t);
			addTD(td);
			lastEndIndex = m.end();
		}
	}

	/**
	 * 重建InnerTable
	 * @param html
	 * @return
	 */
	public String restoreInnerTable(String html) {
		if ((this.pList == null) || (this.pList.size() == 0)) {
			return html;
		}
		String[] arr = XString.splitEx(html, "<!--_SKY_INNERTABLE_PROTECTED_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (XString.isNotEmpty(arr[i])) {
				if (i != 0) {
					int index = Integer.parseInt(arr[i].substring(0, arr[i]
							.indexOf("-")));
					sb.append(this.pList.get(index).toString());
					arr[i] = arr[i].substring(arr[i].indexOf(">") + 1);
				}
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}

	public HtmlTable getParent() {
		return this.parent;
	}
}
