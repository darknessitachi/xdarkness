// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlTable.java

package com.xdarkness.jaf.controls.html;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.common.util.FileUtil;
import com.xdarkness.common.util.StringUtil;

//            HtmlElement, HtmlTR, HtmlTD

public class HtmlTable extends HtmlElement {

	public HtmlTable() {
		pList = null;
		ElementType = "TABLE";
		TagName = "table";
	}

	public void addTR(HtmlTR tr) {
		addChild(tr);
	}

	public HtmlTR getTR(int index) {
		return (HtmlTR) Children.get(index);
	}

	public void removeTR(int index) {
		if (index < 0 || index > Children.size()) {
			throw new RuntimeException("\u9519\u8BEF\u7684\u7D22\u5F15");
		} else {
			Children.remove(index);
			return;
		}
	}

	public void removeColumn(int index) {
		for (int i = 0; i < Children.size(); i++) {
			HtmlTR tr = getTR(i);
			if (index < tr.Children.size())
				tr.removeTD(index);
		}

	}

	public void setWidth(int width) {
		Attributes.put("width", new Integer(width));
	}

	public int getWidth() {
		return ((Integer) Attributes.get("width")).intValue();
	}

	public void setHeight(int height) {
		Attributes.put("height", new Integer(height));
	}

	public int getHeight() {
		return ((Integer) Attributes.get("height")).intValue();
	}

	public void setAlign(String align) {
		Attributes.put("align", align);
	}

	public String getAlign() {
		return (String) Attributes.get("align");
	}

	public void setBgColor(String bgColor) {
		Attributes.put("bgColor", bgColor);
	}

	public String getBgColor() {
		return (String) Attributes.get("bgColor");
	}

	public void setBackgroud(String backgroud) {
		Attributes.put("backgroud", backgroud);
	}

	public String getBackgroud() {
		return (String) Attributes.get("backgroud");
	}

	public void setCellSpacing(String cellSpacing) {
		setAttribute("cellSpacing", cellSpacing);
	}

	public String getCellSpacing() {
		return getAttribute("cellSpacing");
	}

	public void setCellPadding(String cellPadding) {
		setAttribute("cellPadding", cellPadding);
	}

	public String getCellPadding() {
		return getAttribute("cellPadding");
	}

	public void parseHtml(String html) throws Exception {
		Matcher m = PTable.matcher(html);
		if (!m.find())
			throw new Exception(
					"Table\u89E3\u6790html\u65F6\u53D1\u751F\u9519\u8BEF");
		String attrs = m.group(1);
		String trs = m.group(2).trim();
		Attributes.clear();
		Children.clear();
		Attributes = parseAttr(attrs);
		m = PInnerTable.matcher(trs);
		for (int lastEndIndex = 0; m.find(lastEndIndex); lastEndIndex = m.end()) {
			if (pList == null)
				pList = new ArrayList();
			pList.add(m.group(0));
		}

		if (pList != null) {
			for (int i = 0; i < pList.size(); i++)
				trs = StringUtil.replaceEx(trs, pList.get(i).toString(),
						"<!--_SKY_INNERTABLE_PROTECTED_" + i + "-->");

		}
		m = PTRPre.matcher(trs);
		for (int lastEndIndex = 0; m.find(lastEndIndex); lastEndIndex = m.end()) {
			String t = trs.substring(m.start(), m.end());
			HtmlTR tr = new HtmlTR(this);
			tr.parseHtml(t);
			addTR(tr);
		}

	}

	public String restoreInnerTable(String html) {
		if (pList == null || pList.size() == 0)
			return html;
		String arr[] = StringUtil.splitEx(html,
				"<!--_SKY_INNERTABLE_PROTECTED_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++)
			if (StringUtil.isNotEmpty(arr[i])) {
				if (i != 0) {
					int index = Integer.parseInt(arr[i].substring(0, arr[i]
							.indexOf("-")));
					sb.append(pList.get(index).toString());
					arr[i] = arr[i].substring(arr[i].indexOf(">") + 1);
				}
				sb.append(arr[i]);
			}

		return sb.toString();
	}

	public String getOuterHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(TagName);
		Object ks[] = Attributes.keyArray();
		Object vs[] = Attributes.valueArray();
		for (int i = 0; i < Attributes.size(); i++)
			if (vs[i] != null) {
				sb.append(" ");
				sb.append(ks[i]);
				sb.append("=\"");
				sb.append(vs[i]);
				sb.append("\"");
			}

		sb.append(">\n");
		if (getTR(0).getTD(0).isHead())
			sb.append("<thead>\n");
		sb.append(getTR(0).getOuterHtml());
		if (getTR(0).getTD(0).isHead()) {
			sb.append("</thead>\n");
			if (Children.size() > 1)
				sb.append("<tbody>\n");
		}
		for (int i = 1; i < Children.size(); i++)
			sb.append(((HtmlElement) Children.get(i)).getOuterHtml());

		if (getTR(0).getTD(0).isHead() && Children.size() > 1)
			sb.append("</tbody>\n");
		sb.append("</");
		sb.append(TagName);
		sb.append(">");
		return sb.toString();
	}

	public static void test() {
		HtmlTable table = new HtmlTable();
		try {
			String html = FileUtil.readText("G:/Test.txt");
			table.parseHtml(html);
			System.out.println(table.getOuterHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Pattern PTR = Pattern.compile("^\\s*<(tr|th)(.*?)>(.*)</(tr|th)>\\s*$",
				34);
		Matcher m = PTR.matcher("<th>dsfsd</th>");
		if (m.find())
			System.out.println(m.group(3));
	}

	public static final Pattern PTable = Pattern.compile(
			"^\\s*<table(.*?)>(.*)</table>\\s*$", 34);
	public static final Pattern PInnerTable = Pattern.compile(
			"<table(.*?)>(.*?)</table>", 34);
	public static final Pattern PTR = Pattern.compile(
			"^\\s*<tr(.*?)>(.*)</tr>\\s*$", 34);
	public static final Pattern PTRPre = Pattern.compile("<tr.*?>.*?</tr>", 34);
	public static final Pattern PTD = Pattern.compile(
			"^\\s*<(td|th)(.*?)>(.*)</(td|th)>\\s*$", 34);
	public static final Pattern PTDPre = Pattern.compile(
			"<(td|th).*?>.*?</(td|th)>", 34);
	public static final String ProtectedTableStart = "<!--_SKY_INNERTABLE_PROTECTED_";
	private ArrayList pList;

}
