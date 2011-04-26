
<%@page import="com.xdarkness.cms.pub.CMSCache"%>
<%@page import="com.xdarkness.schema.ZCCatalogConfigSchema"%><%@page import="com.xdarkness.member.Login"%>
<%@page import="com.xdarkness.cms.dataservice.CommentService"%>
<%@page import="java.io.*"%>
<%@page import="com.xdarkness.framework.*"%>
<%@page import="com.xdarkness.framework.utility.*"%>
<%@page import="com.xdarkness.cms.pub.SiteUtil"%>
<%@page import="com.xdarkness.framework.data.*"%>
<%@ taglib uri="controls" prefix="sky"%>

<%!
	String header = "";
	String loop = "";
	String footer = "";%>
<%
	String CatalogID = request.getParameter("CatalogID");
	ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(CatalogID);
	if(catalogConfig!=null){
		if(!catalogConfig.getAllowComment().equals("Y")){
			return;
		}
	}
	Login.checkAndLogin(request);
	if (Config.isDebugMode() || "".equals(loop)) {
		File file = new File(application.getRealPath("Services/CommentListPage.jsp"));
		String text = FileUtil.readText(file);
		header = text.substring(text.indexOf("<!-- comment header begin -->") + 29, text.indexOf("<!-- comment header end -->"));
		header = header.replaceAll("</head>","");
		header = header.replaceAll("<body>","");
		loop = text.substring(text.indexOf("<!-- comment loop begin -->") + 27, text.indexOf("<!-- comment loop end -->"));
		footer = text.substring(text.indexOf("<!-- comment footer begin -->") + 29, text.indexOf("<!-- comment footer end -->"));
	}

	String relaID = request.getParameter("RelaID");
	String count = request.getParameter("Count");
	String siteID = request.getParameter("SiteID");
	boolean commentFlag = SiteUtil.getCommentAuditFlag(siteID);
	String WherePart = "";
	if (commentFlag) {
		WherePart = " and verifyflag='Y'"; //评论需要审核
	}
	StringBuffer loopsb = new StringBuffer();
	String sql = "select * from zccomment where relaid=?" + WherePart + " order by id desc ";
	if (StringUtil.isNotEmpty(count)&&Integer.parseInt(count)>0) {
		DataTable dt = new QueryBuilder(sql,relaID).executePagedDataTable(Integer.parseInt(count), 0);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ip = dt.getString(i, "AddUserIP");
			if(ip.indexOf(".")>-1){
				ip = ip.substring(0, ip.lastIndexOf(".") + 1) + "*";
			}
			dt.set(i, "AddUserIP",ip);
			loopsb.append(HtmlUtil.replacePlaceHolder(loop, dt.getDataRow(i).toMapx(), true));
		}
	}
	
	Mapx map = new Mapx();
	map = CommentService.init(map);
	map.put("RelaID", request.getParameter("RelaID"));
	map.put("CatalogID", request.getParameter("CatalogID"));
	map.put("CatalogType", request.getParameter("CatalogType"));
	map.put("SiteID", siteID);
	String LoginHTML = "&nbsp;";
	if(!User.isLogin()){
		LoginHTML = "登录名：<input type=\"text\" name=\"CmntUserName\" id=\"CmntUserName\" class=\"txt\" /> 密码：<input type=\"password\" name=\"CmntPwd\" id=\"CmntPwd\" class=\"txt\" />";
	}
	if(catalogConfig!=null&&StringUtil.isNotEmpty(catalogConfig.getCommentAnonymous())){
		if("N".equalsIgnoreCase(catalogConfig.getCommentAnonymous())){
			LoginHTML += "<label for=\"Anonymous\"><input type=\"checkbox\" id=\"Anonymous\" name=\"CmntCheckbox\" checked/>匿名发表</label>";
		}
	}
	map.put("LoginHTML", LoginHTML);	
	StringBuffer sb = new StringBuffer();
	sb.append(HtmlUtil.replacePlaceHolder(header, map, true));
	sb.append(loopsb);
	sb.append(HtmlUtil.replacePlaceHolder(footer, map, true));

	int index = sb.indexOf("\n");
	int lastIndex = -1;
	while (index != -1) {
		out.println("document.write(\'" + sb.substring(lastIndex + 1, index).trim() + "\');");
		lastIndex = index;
		index = sb.indexOf("\n", index + 1);
	}
%>