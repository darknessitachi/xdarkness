// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageBarTag.java

package com.xdarkness.jaf.controls;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageBarTag extends TagSupport {

	public PageBarTag() {
	}

	public int doStartTag() throws JspException {
		try {
			total = Integer.parseInt(""
					+ pageContext.getAttribute(target + "_SKY_PAGETOTAL"));
			pageIndex = Integer.parseInt(""
					+ pageContext.getAttribute(target + "_SKY_PAGEINDEX"));
			pageSize = Integer.parseInt(""
					+ pageContext.getAttribute(target + "_SKY_SIZE"));
			String html = null;
			html = getPageBarHtml(target, type, total, pageSize, pageIndex);
			html = html + "<script>DataGrid.setParam('" + target
					+ "','PageBarType'," + type + ");</script>";
			type = 0;
			pageContext.getOut().write(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getPageBarHtml(String target, int type, int total,
			int pageSize, int pageIndex) {
		if (type == 2)
			return getType2(target, total, pageSize, pageIndex);
		if (type == 1)
			return getType1(target, total, pageSize, pageIndex);
		else
			return getDefault(target, total, pageSize, pageIndex);
	}

	private static String getDefault(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int totalPages = (new Double(Math.ceil(((double) total * 1.0D)
				/ (double) pageSize))).intValue();
		sb
				.append("<div id='_PageBar_"
						+ target
						+ "' style=\"clear:both;bottom:0px;background:#fff; height:30px; line-height:30px;\">");
		sb.append("<div style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.firstPage('"
							+ target
							+ "');\">\u7B2C\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target
							+ "');\">\u4E0A\u4E00\u9875</a>&nbsp;|&nbsp;");
		} else {
			sb.append("\u7B2C\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u4E0A\u4E00\u9875&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target
							+ "');\">\u4E0B\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.lastPage('"
							+ target + "');\">\u6700\u672B\u9875</a>");
		} else {
			sb.append("\u4E0B\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u6700\u672B\u9875&nbsp;&nbsp;");
		}
		sb
				.append("&nbsp;&nbsp;\u8F6C\u5230\u7B2C&nbsp;<input id='_PageBar_Index' type='text' class='inputText' style='width:40px' ");
		sb.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;\u9875");
		sb
				.append("&nbsp;&nbsp;<input type='button' onclick=\"if(!/^\\d+$/.test($V('_PageBar_Index'))||$V('_PageBar_Index')<1||$V('_PageBar_Index')>"
						+ totalPages
						+ "){alert('\u9519\u8BEF\u7684\u9875\u7801');$('_PageBar_Index').focus();}"
						+ "else{var pageIndex = ($V('_PageBar_Index')-1)>0?$V('_PageBar_Index')-1:0;"
						+ "DataList.setParam('"
						+ target
						+ "','"
						+ "_SKY_PAGEINDEX"
						+ "',pageIndex);DataList.loadData('"
						+ target
						+ "');}\" class='inputButton' value='\u8DF3\u8F6C'>");
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("\u5171 " + total + " \u6761\u8BB0\u5F55\uFF0C\u6BCF\u9875 "
				+ pageSize + " \u6761\uFF0C\u5F53\u524D\u7B2C "
				+ (totalPages != 0 ? pageIndex + 1 : 0) + " / " + totalPages
				+ " \u9875</div>");
		sb.append("</div>");
		return sb.toString();
	}

	private static String getType1(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int totalPages = (new Double(Math.ceil(((double) total * 1.0D)
				/ (double) pageSize))).intValue();
		sb
				.append("<div id='_PageBar_"
						+ target
						+ "' style=\"clear:both;bottom:0px;background:#fff; height:30px; line-height:30px;\">");
		sb.append("<div style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.firstPage('"
							+ target
							+ "');\">\u7B2C\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target
							+ "');\">\u4E0A\u4E00\u9875</a>&nbsp;|&nbsp;");
		} else {
			sb.append("\u7B2C\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u4E0A\u4E00\u9875&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target
							+ "');\">\u4E0B\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.lastPage('"
							+ target + "');\">\u6700\u672B\u9875</a>");
		} else {
			sb.append("\u4E0B\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u6700\u672B\u9875&nbsp;&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("\u5171 " + total + " \u6761\u8BB0\u5F55\uFF0C\u6BCF\u9875 "
				+ pageSize + " \u6761\uFF0C\u5F53\u524D\u7B2C "
				+ (totalPages != 0 ? pageIndex + 1 : 0) + " / " + totalPages
				+ " \u9875</div>");
		sb.append("</div>");
		return sb.toString();
	}

	private static String getType2(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int pageCount = (new Double(Math.ceil(((double) total * 1.0D)
				/ (double) pageSize))).intValue();
		int start = pageIndex - 9 >= 1 ? pageIndex - 9 : 1;
		int end = pageIndex + 9 <= pageCount ? pageIndex + 9 : pageCount;
		if (end == 0)
			end = 1;
		sb.append("<div id='_PageBar_" + target + "'>");
		if (start > 1)
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target + "');\">\u4E0A\u4E00\u9875</a>&nbsp;");
		for (int i = start; i <= end; i++)
			if (i == pageIndex + 1)
				sb.append("&nbsp;<span>" + i + "</span>&nbsp;");
			else
				sb
						.append("&nbsp;<span><a href='javascript:void(0);' onclick=\"DataList.setParam('"
								+ target
								+ "','"
								+ "_SKY_PAGEINDEX"
								+ "',"
								+ i
								+ ");DataList.loadData('"
								+ target
								+ "');\">"
								+ i + "</a></span>&nbsp;");

		if (pageIndex < pageCount && pageCount != 1)
			sb
					.append("&nbsp;<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target + "');\">\u4E0B\u4E00\u9875</a>");
		sb.append("</div>");
		return sb.toString();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private static final long serialVersionUID = 1L;
	private String target;
	private int type;
	private int total;
	private int pageIndex;
	private int pageSize;
}
