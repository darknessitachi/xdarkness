package com.xdarkness.plugin.webim.business;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xdarkness.framework.util.StringFormat;
import com.xdarkness.framework.util.XString;

public class Profile {
	// Primary Key
	public static String GUID() {
		return "IM_" + Guid.NewGuid().ToString().ToUpper();
	}

	public static final double interval = 30;

	// 在线状态
	public static final int onLineStatus = 0;

	// 离线状态
	public static final int offLineStatus = 2;

	// 系统预定义自己分组
	public static final String ownerGroup = "自己";

	// 默认主题
	public static final String defaultTheme = "dark";

	// 系统预定义陌生人分组
	public static final String unknowGroup = "陌生人";

	// 用户状态集合
	public static final String[] userStatusList = new String[] { "在线", "忙碌",
			"离线" };

	// 用户状态图标集合
	public static final String[] userStatusIconList = new String[] {
			"online.png", "away.png", "offline.png" };

	// 用户个性头像长度
	public static final int userHeadImageCount = 14;

	// 用户默认头像
	public static final String userHeadImage = "1.gif";

	// 发送者标头颜色
	public static final String senderHeadColor = "##008242";

	// 接收者标头颜色
	public static final String receiverHeadColor = "#0000FF";

	// 用户个性头像路径
	public static final String userHeadImagePath = "../upload/head/";

	// 用户状态图标路径
	public static final String userStatusIconPath = "../include/images/";

	// 用户默认头像路径
	public static final String userDefaultHeadImagePath = "../include/images/head/";

	// / <summary>
	// / 获取映射信息
	// / </summary>
	// / <param name="mappingNode">映射的节点</param>
	// / <returns></returns>
	public static Map<String, String> GetMappingList(String mappingNode)
    {
        Map<String, String> result = null;

        try
        {
            SAXReader reader = new SAXReader(false);
            Document doc = reader.read("../profile/profile.config");
			
		    XPath xpath = XPathFactory.newInstance().newXPath();
		    
		    String usedXpathString = XString.format("//profile/{0}/used", mappingNode);
            if (xpath.evaluate(usedXpathString, doc).equals("1"))
            {
                int baseLength = 4;

                String columnXpathString = XString.format("//profile/{0}/mappingcolumn/columnname", mappingNode);
                NodeList columnList = (NodeList)xpath.evaluate(columnXpathString, doc, XPathConstants.NODESET);
                int columnListCount = columnList.getLength();

                if (columnListCount > 0)
                {
                    result = new HashMap<String, String>(baseLength + columnListCount);

                    String strMappingNode = XString.format("//profile/{0}/", mappingNode);
                    result.put("condition", xpath.evaluate(strMappingNode + "mappingcondition", doc));
                    result.put("connectionString",xpath.evaluate(strMappingNode + "connection", doc));
                    result.put("tablename", xpath.evaluate(strMappingNode + "mappingtable/tablename", doc));
                    result.put("providername", xpath.evaluate(strMappingNode + "connection[1]/@providername", doc));

                    
                    for(int i=0;i<columnList.getLength();i++)
                    {
                    	Node column = columnList.item(i);
                        result.put(column.getNodeValue(), column.getAttributes().getNamedItem("sourcename").getNodeValue());
                    }
                }
            }
        }catch (Exception e) {
			e.printStackTrace();
		}

        return result;
    }

	public static String RenderErrorPage(String theme, String errorMessage) {
		StringBuilder errorBuilder = new StringBuilder();

		errorBuilder
				.append(
						new StringFormat("<html><head><link type=\"text/css\" rel=\"Stylesheet\" href=\"../include/themes/{0}/style.css\" />",
								theme).toString());

		errorBuilder
				.append("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/jquery.js\"></script>");

		errorBuilder
				.append("<script language=\"javascript\" type=\"text/javascript\">document.oncontextmenu = function(){return false;};");
		errorBuilder
				.append("window.onload = function(){var windowWidth = $(window).width();var windowHeight = $(window).height();$(document.body).append");
		errorBuilder
				.append("(\"<div id='divDescription' style='height: \" + windowHeight + \"px; width: \" + windowWidth + \"px;'></div><p id='pContent' ");

		errorBuilder
				.append(
						new StringFormat("style='top: \" + (windowHeight / 2 - 23) + \"px; left: \" + (windowWidth / 2 - 175)  + \"px;'>{0}</p>\");}}</script></head><body></body></html>",
						errorMessage).toString());

		return errorBuilder.toString();
	}

	// / <summary>
	// / 呈现页面头信息
	// / </summary>
	// / <param name="writer">输出容器</param>
	// / <param name="theme">主题</param>
	public static void RenderHtmlHead(Writer writer, String theme) {
		try {
			writer
					.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			writer.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");

			writer.write("<head>");
			writer.write("<title>Web Im</title>");
			writer.write("<meta http-equiv=\"Expires\" CONTENT=\"0\"/>");
			writer.write("<meta http-equiv=\"Pragma\" CONTENT=\"no-cache\"/>");
			writer
					.write("<meta http-equiv=\"Cache-Control\" CONTENT=\"no-cache\"/> ");

			writer
					.write(new StringFormat(
							"<link type=\"text/css\" rel=\"Stylesheet\" href=\"../include/themes/{0}/style.css\" />",
							theme).toString());

			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/jquery.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/interface.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/ui.mouse.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/ui.draggable.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/jquery/ui.draggable.ext.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/im/layout.im.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/datepicker/WdatePicker.js\"></script>");
			writer
					.write("<script language=\"javascript\" type=\"text/javascript\" src=\"../include/script/im/common.js\"></script>");

			writer.write("</head>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}