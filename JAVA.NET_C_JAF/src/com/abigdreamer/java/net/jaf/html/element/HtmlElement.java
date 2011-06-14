package com.abigdreamer.java.net.jaf.html.element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.abigdreamer.java.net.util.CaseIgnoreMapx;
import com.abigdreamer.java.net.util.Mapx;

/**
 * @author Darkness 
 * create on 2011 2011-1-7 上午10:22:51
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public abstract class HtmlElement implements Cloneable {
	
	// 属性正则 ，匹配key="value"或 key='value'
	public static final Pattern PAttr = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?(\\\"|\\')(.*?)\\2", Pattern.CASE_INSENSITIVE);

	
	public static final Pattern PAttr2 = Pattern.compile(
			"\\s+?(\\w+?)\\s*?=\\s*?([\\S&&[^\\\"\\']]*?)(\\s|>)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * 常量
	 */
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
	public CaseIgnoreMapx<Object> Attributes = new CaseIgnoreMapx<Object>();
	protected HtmlElement ParentElement;
	public List<HtmlElement> Children = new ArrayList<HtmlElement>();
	public String InnerHTML;
	protected static Mapx<String,Pattern> PatternMap = new Mapx<String,Pattern>();

	/**
	 * 生成id，格式如下："_SKY_GenID_" + 自1970-01-01 00:00:00 GMT以来的毫秒数
	 * 如："_SKY_GenID_1294367495984" 
	 * @return
	 */
	public static String generateId(){
		return "_SKY_GenID_" + new Date().getTime();
	}
	
	/**
	 * 获取元素的outerHTML
	 * 所有非null属性都会附加进属性列表
	 * 当InnerHTML非null时，添加InnerHTML，否则递归遍历所有Children节点，获取Children节点的outerHTML
	 * @return
	 */
	public String getOuterHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(this.TagName);
		
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
				sb.append(this.Children.get(i).getOuterHtml());
			}
		}

		sb.append("</").append(this.TagName).append(">\n");
		return sb.toString();
	}

	/**
	 * 获取属性
	 * @param attrName
	 * @return
	 */
	public String getAttribute(String attrName) {
		return this.Attributes.getString(attrName);
	}
	
	/**
	 * 获取属性int值
	 * @param attrName
	 * @return
	 */
	public int getIntAttribute(String attrName) {
		return this.Attributes.getInt(attrName);
	}

	/**
	 * 设置属性值
	 * @param attrName
	 * @param attrValue
	 */
	public void setAttribute(String attrName, Object attrValue) {
		this.Attributes.put(attrName, attrValue);
	}

	/**
	 * 删除属性
	 * @param attrName
	 */
	public void removeAttribute(String attrName) {
		this.Attributes.remove(attrName);
	}

	/**
	 * 获取属性列表
	 * @return
	 */
	public CaseIgnoreMapx<Object> getAttributes() {
		return this.Attributes;
	}

	/**
	 * 设置属性列表
	 * @param attributes
	 */
	public void setAttributes(Mapx<Object, Object> attributes) {
		this.Attributes.clear();
		this.Attributes.putAll(attributes);
	}

	/**
	 * 获取子节点
	 * @return
	 */
	public List<HtmlElement> getChildren() {
		return this.Children;
	}

	/**
	 * 添加子节点
	 * @param child
	 */
	public void addChild(HtmlElement child) {
		this.Children.add(child);
		child.setParentElement(this);
	}

	/**
	 * 获取样式
	 * @return
	 */
	public String getClassName() {
		return this.Attributes.getString("classname");
	}

	/**
	 * 设置样式
	 * @param className
	 */
	public void setClassName(String className) {
		this.Attributes.put("classname", className);
	}

	/**
	 * 获取元素类型
	 * @return
	 */
	public String getElementType() {
		return this.ElementType;
	}

	/**
	 * 获取元素id
	 * @return
	 */
	public String getID() {
		return this.Attributes.getString("id");
	}

	/**
	 * 设置元素id
	 * @param id
	 */
	public void setID(String id) {
		this.Attributes.put("id", id);
	}

	/**
	 * 获取元素父元素
	 * @return
	 */
	public HtmlElement getParentElement() {
		return this.ParentElement;
	}

	/**
	 * 设置元素父元素
	 * @param parentElement
	 */
	protected void setParentElement(HtmlElement parentElement) {
		this.ParentElement = parentElement;
	}

	/**
	 * 获取style
	 * @return
	 */
	public String getStyle() {
		return this.Attributes.getString("style");
	}

	/**
	 * 设置元素style
	 * @param style
	 */
	public void setStyle(String style) {
		this.Attributes.put("style", style);
	}

	/**
	 * 获取标签名称
	 * @return
	 */
	public String getTagName() {
		return this.TagName;
	}

	/**
	 * 获取innerHTML
	 * @return
	 */
	public String getInnerHTML() {
		if (this.Children.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.Children.size(); i++) {
				sb.append(this.Children.get(i).getOuterHtml());
			}
			return sb.toString();
		}
		return this.InnerHTML;
	}

	/**
	 * 设置innerHTML
	 * @param innerHTML
	 */
	public void setInnerHTML(String innerHTML) {
		this.InnerHTML = innerHTML;
	}

	/**
	 * 解析字符串，提取属性列表
	 * @param attrs
	 * @return
	 */
	public static CaseIgnoreMapx<Object> parseAttr(String attrs) {
		Matcher m = PAttr.matcher(attrs);
		int lastEndIndex = 0;
		CaseIgnoreMapx<Object> map = new CaseIgnoreMapx<Object>();
		while (m.find(lastEndIndex)) {
			map.put(m.group(1), m.group(3));
			lastEndIndex = m.end();
		}
		return map;
	}

	/**
	 * 获取标签正则
	 * @return
	 */
	protected Pattern getParrtenByTagName() {
		Pattern o = PatternMap.get(this.TagName);
		if (o == null) {
			Pattern pattern = Pattern.compile("<" + this.TagName
					+ "(.*?)>(.*?)</" + this.TagName + ">", Pattern.CASE_INSENSITIVE);
			PatternMap.put(this.TagName, pattern);
			return pattern;
		}
		return o;
	}

	/**
	 * 解析html
	 * @param html
	 * @throws Exception
	 */
	public void parseHtml(String html) throws Exception {
		Pattern pattern = getParrtenByTagName();
		Matcher m = pattern.matcher(html);
		if (!m.find()) {
			throw new Exception(this.TagName + "解析html时发生错误");
		}
		String attrs = m.group(1);

		this.Attributes.clear();
		this.Children.clear();

		// @TODO 解析后Children为空，只是给innerHTML赋值，没考虑递归解析子节点
		this.Attributes = parseAttr(attrs);
		this.InnerHTML = m.group(2).trim();
	}

	/**
	 * 克隆
	 */
	@SuppressWarnings("unchecked")
	public Object clone() {
		HtmlElement ele = null;
		try {
			ele = getClass().newInstance();
			ele.setAttributes((Mapx) this.Attributes.clone());
			for (int i = 0; i < this.Children.size(); i++) {
				ele.addChild(this.Children.get(i));
			}
			ele.InnerHTML = this.InnerHTML;
			ele.TagName = this.TagName;
			ele.ElementType = this.ElementType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	/**
	 * 覆写toString，获取该元素的outerHTML
	 */
	@Override
	public String toString() {
		return getOuterHtml();
	}
	
	public static void main(String[] args) {
		System.out.println(generateId());
	}
}
