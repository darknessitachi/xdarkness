// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HtmlElement.java

package com.xdarkness.jaf.controls.html;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.common.util.Mapx;

public abstract class HtmlElement implements Cloneable {

	public HtmlElement() {
		Attributes = new Mapx();
		Children = new ArrayList();
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

		sb.append(">");
		if (InnerHTML != null) {
			sb.append(InnerHTML);
		} else {
			sb.append("\n");
			for (int i = 0; i < Children.size(); i++)
				sb.append(((HtmlElement) Children.get(i)).getOuterHtml());

		}
		sb.append("</");
		sb.append(TagName);
		sb.append(">\n");
		return sb.toString();
	}

	public String getAttribute(String attrName) {
		return Attributes.getString(attrName.toLowerCase());
	}

	public void setAttribute(String attrName, String attrValue) {
		Attributes.put(attrName.toLowerCase(), attrValue);
	}

	public void removeAttribute(String attrName) {
		Attributes.remove(attrName.toLowerCase());
	}

	public Mapx getAttributes() {
		return Attributes;
	}

	public void setAttributes(Mapx attributes) {
		Attributes.clear();
		Attributes.putAll(attributes);
	}

	public ArrayList getChildren() {
		return Children;
	}

	public void addChild(HtmlElement child) {
		Children.add(child);
		child.setParentElement(this);
	}

	public String getClassName() {
		return Attributes.getString("classname");
	}

	public void setClassName(String className) {
		Attributes.put("classname", className);
	}

	public String getElementType() {
		return ElementType;
	}

	public String getID() {
		return Attributes.getString("id");
	}

	public void setID(String id) {
		Attributes.put("id", id);
	}

	public HtmlElement getParentElement() {
		return ParentElement;
	}

	protected void setParentElement(HtmlElement parentElement) {
		ParentElement = parentElement;
	}

	public String getStyle() {
		return Attributes.getString("style");
	}

	public void setStyle(String style) {
		Attributes.put("style", style);
	}

	public String getTagName() {
		return TagName;
	}

	public String getInnerHTML() {
		if (Children.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < Children.size(); i++)
				sb.append(((HtmlElement) Children.get(i)).getOuterHtml());

			return sb.toString();
		} else {
			return InnerHTML;
		}
	}

	public void setInnerHTML(String innerHTML) {
		InnerHTML = innerHTML;
	}

	public static Mapx parseAttr(String attrs) {
		Matcher m = PAttr.matcher(attrs);
		int lastEndIndex = 0;
		Mapx map = new Mapx();
		for (; m.find(lastEndIndex); lastEndIndex = m.end())
			map.put(m.group(1).toLowerCase(), m.group(3));

		return map;
	}

	private Pattern getParrtenByTagName() {
		Object o = PatternMap.get(TagName);
		if (o == null) {
			Pattern pattern = Pattern.compile("<" + TagName + "(.*?)>(.*?)</"
					+ TagName + ">", 34);
			PatternMap.put(TagName, pattern);
			return pattern;
		} else {
			return (Pattern) o;
		}
	}

	public void parseHtml(String html) throws Exception {
		Pattern pattern = getParrtenByTagName();
		Matcher m = pattern.matcher(html);
		if (!m.find()) {
			throw new Exception(this.TagName + "解析html时发生错误");
		}
		
		String attrs = m.group(1);
		Attributes.clear();
		Children.clear();
		Attributes = parseAttr(attrs);
		InnerHTML = m.group(2).trim();
	}

	public Object clone() {
		HtmlElement ele = null;
		try {
			ele = (HtmlElement) getClass().newInstance();
			ele.setAttributes((Mapx) Attributes.clone());
			for (int i = 0; i < Children.size(); i++)
				ele.addChild((HtmlElement) Children.get(i));

			ele.InnerHTML = InnerHTML;
			ele.TagName = TagName;
			ele.ElementType = ElementType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	public String toString() {
		return getOuterHtml();
	}

	public static final Pattern PAttr = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?(\\\"|\\')(.*?)\\2", Pattern.CASE_INSENSITIVE);
	public static final Pattern PAttr2 = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?([\\S&&[^\\\"\\']]*?)(\\s|>)", Pattern.CASE_INSENSITIVE);
	public static final String TABLE = "TABLE";
	public static final String TR = "TR";
	public static final String TD = "TD";
	public static final String SCRIPT = "SCRIPT";
	public static final String DIV = "DIV";
	public static final String SPAN = "SPAN";
	public static final String P = "P";
	public static final String SELECT = "SELECT";
	protected String ElementType;
	protected String TagName;
	public Mapx Attributes;
	protected HtmlElement ParentElement;
	public ArrayList Children;
	public String InnerHTML;
	protected static Mapx PatternMap = new Mapx();

}
