package com.abigdreamer.java.net.jaf.html.element;

/**
 * @author Darkness 
 * create on 2011-1-7 下午04:52:29
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class AttributeHtmlElement extends HtmlElement {
	/**
	 * 设置宽度
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		setAttribute("width", width);
	}

	/**
	 * 获取宽度
	 * 
	 * @return
	 */
	public int getWidth() {
		return getIntAttribute("width");
	}

	/**
	 * 设置高度
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		setAttribute("height", height);
	}

	/**
	 * 获取高度
	 * 
	 * @return
	 */
	public int getHeight() {
		return getIntAttribute("height");
	}

	/**
	 * 设置对其方式
	 * 
	 * @param align
	 */
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/**
	 * 获取对其方式
	 * 
	 * @return
	 */
	public String getAlign() {
		return getAttribute("align");
	}
	
	/**
	 * 获取垂直对其方式
	 * @return
	 */
	public String getVAlign() {
		return (String) this.Attributes.get("vAlign");
	}

	/**
	 * 设置垂直对其方式
	 * @param vAlign
	 */
	public void setVAlign(String vAlign) {
		this.Attributes.put("vAlign", vAlign);
	}
	
	/**
	 * 设置背景色：bgColor
	 * 
	 * @param bgColor
	 */
	public void setBgColor(String bgColor) {
		setAttribute("bgColor", bgColor);
	}

	/**
	 * 获取背景色：bgColor
	 * 
	 * @return
	 */
	public String getBgColor() {
		return getAttribute("bgColor");
	}

	/**
	 * 设置背景色：backgroud
	 * 
	 * @param backgroud
	 */
	public void setBackgroud(String backgroud) {
		setAttribute("backgroud", backgroud);
	}

	/**
	 * 获取背景色：backgroud
	 * 
	 * @return
	 */
	public String getBackgroud() {
		return getAttribute("backgroud");
	}

	/**
	 * 设置列间距
	 * 
	 * @param cellSpacing
	 */
	public void setCellSpacing(String cellSpacing) {
		setAttribute("cellSpacing", cellSpacing);
	}

	/**
	 * 获取列间距
	 * 
	 * @return
	 */
	public String getCellSpacing() {
		return getAttribute("cellSpacing");
	}

	/**
	 * 设置列填充
	 * 
	 * @param cellPadding
	 */
	public void setCellPadding(String cellPadding) {
		setAttribute("cellPadding", cellPadding);
	}

	/**
	 * 获取列填充
	 * 
	 * @return
	 */
	public String getCellPadding() {
		return getAttribute("cellPadding");
	}
}
