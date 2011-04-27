 package com.zving.shop;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.template.HtmlNameParser;
 import com.zving.cms.template.HtmlNameRule;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCSiteSchema;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class HotList
 {
   public static void deal(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String type = request.getParameter("Type");
     if (StringUtil.isEmpty(type)) {
       return;
     }
     if (type.equals("Catalog")) {
       getCatalogHotList(request, response);
     }
     if (type.equals("Site")) {
       getSiteHotList(request, response);
     }
     type.equals("New");
 
     if (type.equals("History"))
       getHistoryHotList(request, response);
   }
 
   public static void getCatalogHotList(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
     String catalogID = request.getParameter("CatalogID");
     String count = request.getParameter("Count");
     String strWidth = request.getParameter("CharWidth");
     PrintWriter out = response.getWriter();
     out.println("document.write('<ul>');");
     if (StringUtil.isEmpty(count)) {
       count = "10";
     }
     if (StringUtil.isEmpty(count)) {
       strWidth = "100";
     }
     if (StringUtil.isEmpty(catalogID)) {
       out.println("document.write('CatalogID不能为空')");
     }
     String innerCode = CatalogUtil.getSchema(catalogID).getInnerCode();
     DataTable dt = new QueryBuilder("select * from zsgoods where CatalogInnerCode like ? order by SaleCount desc", 
       innerCode + "%").executePagedDataTable(Integer.parseInt(count), 0);
     int width = Integer.parseInt(strWidth);
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String name = dt.getString(i, "Name");
       int len = StringUtil.lengthEx(name) / 2;
       name = StringUtil.subStringEx(name, width);
       if (len > width + 1) {
         name = name + "...";
       }
       String link = getLink(dt.getDataRow(i));
       out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
     }
     out.println("document.write('</ul>');");
   }
 
   public static void getSiteHotList(HttpServletRequest request, HttpServletResponse response) throws Exception {
     String siteID = request.getParameter("SiteID");
     String count = request.getParameter("Count");
     String strWidth = request.getParameter("CharWidth");
     PrintWriter out = response.getWriter();
     out.println("document.write('<ul>');");
     if (StringUtil.isEmpty(count)) {
       count = "10";
     }
     if (StringUtil.isEmpty(count)) {
       strWidth = "100";
     }
     if (StringUtil.isEmpty(siteID)) {
       out.println("document.write('SiteID不能为空')");
     }
     DataTable dt = new QueryBuilder("select * from zsgoods where SiteID=? order by SaleCount desc", siteID)
       .executePagedDataTable(Integer.parseInt(count), 0);
     int width = Integer.parseInt(strWidth);
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String name = dt.getString(i, "Name");
       int len = StringUtil.lengthEx(name) / 2;
       name = StringUtil.subStringEx(name, width);
       if (len > width + 1) {
         name = name + "...";
       }
       String link = getLink(dt.getDataRow(i));
       out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
     }
     out.println("document.write('</ul>');");
   }
 
   public static void getHistoryHotList(HttpServletRequest request, HttpServletResponse response) throws Exception {
     String siteID = request.getParameter("SiteID");
     String count = request.getParameter("Count");
     String strWidth = request.getParameter("CharWidth");
     PrintWriter out = response.getWriter();
     out.println("document.write('<ul>');");
     if (StringUtil.isEmpty(count)) {
       count = "10";
     }
     if (StringUtil.isEmpty(count)) {
       strWidth = "100";
     }
     if (StringUtil.isEmpty(siteID)) {
       out.println("document.write('SiteID不能为空')");
     }
     DataTable dt = new QueryBuilder("select * from zsgoods where SiteID=? order by SaleCount desc", siteID)
       .executePagedDataTable(Integer.parseInt(count), 0);
     int width = Integer.parseInt(strWidth);
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String name = dt.getString(i, "Name");
       int len = StringUtil.lengthEx(name) / 2;
       name = StringUtil.subStringEx(name, width);
       if (len > width + 1) {
         name = name + "...";
       }
       String link = getLink(dt.getDataRow(i));
       out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
     }
     out.println("document.write('</ul>');");
   }
 
   public static String getLink(DataRow goodsDr) {
     String nameRule = CatalogUtil.getSchema(goodsDr.getString("CatalogID")).getDetailNameRule();
     ZCCatalogSchema catalog = CatalogUtil.getSchema(goodsDr.getString("CatalogID"));
     if (StringUtil.isNotEmpty(nameRule)) {
       HtmlNameParser nameParser = new HtmlNameParser(SiteUtil.getSchema(goodsDr.getString("SiteID")).toDataRow(), 
         null, goodsDr, nameRule);
       HtmlNameRule h = nameParser.getNameRule();
       return PubFun.getLevelStr(catalog.getTreeLevel()) + h.getFullPath();
     }
     return PubFun.getLevelStr(catalog.getTreeLevel()) + CatalogUtil.getPath(catalog.getID()) + 
       goodsDr.get("ID") + ".shtml";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.HotList
 * JD-Core Version:    0.5.4
 */