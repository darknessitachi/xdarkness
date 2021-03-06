package com.xdarkness.framework.jaf.controls;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.framework.util.Mapx;

public abstract class HtmlElement implements Cloneable {
	public static final Pattern PAttr = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?(\\\"|\\')(.*?)\\2", 34);

	public static final Pattern PAttr2 = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?([\\S&&[^\\\"\\']]*?)(\\s|>)", 34);
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
	public Mapx Attributes = new Mapx();
	protected HtmlElement ParentElement;
	public ArrayList Children = new ArrayList();
	public String InnerHTML;
	protected static Mapx PatternMap = new Mapx();

	/**
	 * 生成id
	 * @return
	 */
	public static String generateId(){
		return "_SKY_GenID_" + new Date().getTime();
	}
	public String getOuterHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(this.TagName);
		Object[] ks = this.Attributes.keyArray();
		Object[] vs = this.Attributes.valueArray();
		for (int i = 0; i < this.Attributes.size(); i++) {
			if (vs[i] != null) {
				sb.append(" ");
				sb.append(ks[i]);
				sb.append("=\"");
				sb.append(vs[i]);
				sb.append("\"");
			}
		}
		sb.append(">");

		if (this.InnerHTML != null) {
			sb.append(this.InnerHTML);
		} else {
			sb.append("\n");
			for (int i = 0; i < this.Children.size(); i++) {
				sb.append(((HtmlElement) this.Children.get(i)).getOuterHtml());
			}
		}

		sb.append("</");
		sb.append(this.TagName);
		sb.append(">\n");
		return sb.toString();
	}

	public String getAttribute(String attrName) {
		return this.Attributes.getString(attrName.toLowerCase());
	}
	
	public int getIntAttribute(String attrName) {
		return this.Attributes.getInt(attrName.toLowerCase());
	}

	public void setAttribute(String attrName, String attrValue) {
		this.Attributes.put(attrName.toLowerCase(), attrValue);
	}

	public void setAttribute(String attrName, Object attrValue) {
		this.Attributes.put(attrName.toLowerCase(), attrValue);
	}

	public void removeAttribute(String attrName) {
		this.Attributes.remove(attrName.toLowerCase());
	}

	public Mapx getAttributes() {
		return this.Attributes;
	}

	public void setAttributes(Mapx attributes) {
		this.Attributes.clear();
		this.Attributes.putAll(attributes);
	}

	public ArrayList getChildren() {
		return this.Children;
	}

	public void addChild(HtmlElement child) {
		this.Children.add(child);
		child.setParentElement(this);
	}

	public String getClassName() {
		return this.Attributes.getString("classname");
	}

	public void setClassName(String className) {
		this.Attributes.put("classname", className);
	}

	public String getElementType() {
		return this.ElementType;
	}

	public String getID() {
		return this.Attributes.getString("id");
	}

	public void setID(String id) {
		this.Attributes.put("id", id);
	}

	public HtmlElement getParentElement() {
		return this.ParentElement;
	}

	protected void setParentElement(HtmlElement parentElement) {
		this.ParentElement = parentElement;
	}

	public String getStyle() {
		return this.Attributes.getString("style");
	}

	public void setStyle(String style) {
		this.Attributes.put("style", style);
	}

	public String getTagName() {
		return this.TagName;
	}

	public String getInnerHTML() {
		if (this.Children.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.Children.size(); i++) {
				sb.append(((HtmlElement) this.Children.get(i)).getOuterHtml());
			}
			return sb.toString();
		}
		return this.InnerHTML;
	}

	public void setInnerHTML(String innerHTML) {
		this.InnerHTML = innerHTML;
	}

	public static Mapx parseAttr(String attrs) {
		Matcher m = PAttr.matcher(attrs);
		int lastEndIndex = 0;
		Mapx map = new Mapx();
		while (m.find(lastEndIndex)) {
			map.put(m.group(1).toLowerCase(), m.group(3));
			lastEndIndex = m.end();
		}
		return map;
	}

	private Pattern getParrtenByTagName() {
		Object o = PatternMap.get(this.TagName);
		if (o == null) {
			Pattern pattern = Pattern.compile("<" + this.TagName
					+ "(.*?)>(.*?)</" + this.TagName + ">", 34);
			PatternMap.put(this.TagName, pattern);
			return pattern;
		}
		return (Pattern) o;
	}

	public void parseHtml(String html) throws Exception {
		Pattern pattern = getParrtenByTagName();
		Matcher m = pattern.matcher(html);
		if (!m.find()) {
			throw new Exception(this.TagName + "解析html时发生错误");
		}
		String attrs = m.group(1);

		this.Attributes.clear();
		this.Children.clear();

		this.Attributes = parseAttr(attrs);
		this.InnerHTML = m.group(2).trim();
	}

	public Object clone() {
		HtmlElement ele = null;
		try {
			ele = (HtmlElement) getClass().newInstance();
			ele.setAttributes((Mapx) this.Attributes.clone());
			for (int i = 0; i < this.Children.size(); i++) {
				ele.addChild((HtmlElement) this.Children.get(i));
			}
			ele.InnerHTML = this.InnerHTML;
			ele.TagName = this.TagName;
			ele.ElementType = this.ElementType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	public String toString() {
		return getOuterHtml();
	}
}

/*
 * com.xdarkness.framework.controls.HtmlElement JD-Core Version: 0.6.0
 */