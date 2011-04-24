<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.schema.ZCAttachmentSchema"%>
<%@page import="com.xdarkness.platform.Application"%>
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.*"%>
<%@page import="com.xdarkness.framework.utility.*"%>
<%@page import="com.xdarkness.framework.data.*"%>
<%@page import="com.xdarkness.cms.pub.SiteUtil"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function getPassword(ID, IsFirst) {
	if (IsFirst == 'true') {
		var password = window.prompt("请输入密码：");
		if (password == null) {
			window.close();
		}
		window.location = Server.ContextPath + "Services/AttachDownLoad.jsp?id="+ID+"&Password="+password;
	} else {
		var password = window.prompt("您输入的密码有误！请重新输入：");
		if (password == null) {
			window.close();
		}
		window.location = Server.ContextPath + "Services/AttachDownLoad.jsp?id="+ID+"&Password="+password;
	}
}
</script>
</head>
<body>
<%
	String userAgent = request.getHeader("User-Agent");
	String ID = request.getParameter("id");
	String fileName = request.getParameter("FileName");
	String password = request.getParameter("Password");
	if (StringUtil.isNotEmpty(ID) && StringUtil.isDigit(ID)) {
		ZCAttachmentSchema attachment = new ZCAttachmentSchema();
		attachment.setID(ID);
		if (!attachment.fill()) {
			out.println("<script>alert('数据传入错误！');window.close();</script>");
		}

		if ("Y".equals(attachment.getIsLocked()) && !attachment.getAddUser().equals(User.getUserName())) {
			if (StringUtil.isEmpty(password)) {
				out.println("<script>getPassword(" + ID + ",'true');</script>");
				return;
			} else if (!attachment.getPassword().equals(StringUtil.md5Hex(password))) {
				out.println("<script>getPassword(" + ID + ",'false');</script>");
				return;
			}
		}

		String FilePath = (Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(attachment.getSiteID()) + "/" + attachment.getPath() + attachment
				.getFileName()).replaceAll("//", "/");
		out.clear();
		try {
			if (new File(FilePath).exists()) {
				OutputStream os = response.getOutputStream();
				if ("pdf".equalsIgnoreCase(attachment.getSuffix())) {
					response.setContentType("application/pdf");
				} else {
					response.setContentType("application/octet-stream");
					String strFileName = attachment.getName() + "." + attachment.getSuffix();
					strFileName = StringUtil.replaceEx(strFileName," ","-");
					if(StringUtil.isNotEmpty(userAgent)&&userAgent.toLowerCase().indexOf("msie")>=0){
						response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(strFileName, "UTF-8"));
					}else{
						response.setHeader("Content-Disposition", "attachment; filename="+ new String(strFileName.getBytes("UTF-8"),"ISO-8859-1"));
					}
				}
				FileInputStream in = new FileInputStream(FilePath);
				IOUtils.copy(in, os);
				os.flush();
				os.close();
				in.close();
				out.clear();
				out = pageContext.pushBody();
				new QueryBuilder("update zcattachment set Prop1 = Prop1+1 where ID = ?", ID).executeNoQuery();
				return;
			} else {
				out.println("<script>alert('文件不存在或已经被删除！');window.close();</script>");
			}
		} catch (IOException e) {
			LogUtil.warn("附件下载未正确结束");
		}
	} else {
		String FilePath = (Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ Application.getCurrentSiteAlias() + "/upload/Attach/" + fileName).replaceAll("//", "/");
		try {
			if (new File(FilePath).exists()) {
				OutputStream os = response.getOutputStream();
				if ("pdf".endsWith(fileName)) {
					response.setContentType("application/pdf");
				} else {
					response.setContentType("application/octet-stream");
					fileName = StringUtil.replaceEx(fileName," ","-");
					if(StringUtil.isNotEmpty(userAgent)&&userAgent.toLowerCase().indexOf("msie")>=0){
						response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
					}else{
						response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
					}
				}
				FileInputStream in = new FileInputStream(FilePath);
				IOUtils.copy(in, os);
				os.flush();
				os.close();
				in.close();
				out.clear();
				out = pageContext.pushBody();
				return;
			} else {
				out.println("<script>alert('文件不存在或已经被删除！');window.close();</script>");
			}
		} catch (IOException e) {
			LogUtil.warn("附件下载未正确结束");
		}
	}
	out.println("<script>window.close();</script>");
%>
</body>
</html>