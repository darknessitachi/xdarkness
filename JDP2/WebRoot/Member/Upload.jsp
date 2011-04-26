<%@page import="com.xdarkness.schema.ZDUserSchema"%>
<%@page import="com.xdarkness.platform.pub.ImageUtilEx"%>
<%@page import="com.xdarkness.schema.ZCImageSchema"%>
<%@page import="com.xdarkness.cms.pub.PubFun"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.platform.pub.OrderUtil"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="com.xdarkness.framework.utility.NumberUtil"%>
<%@page import="com.xdarkness.framework.utility.ChineseSpelling"%>
<%@page import="com.xdarkness.schema.ZCCatalogSchema"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="com.xdarkness.platform.pub.NoUtil"%>
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%
String Path = request.getParameter("Path");
if(StringUtil.isEmpty(Path)){
	Path = "";
}
String UserName = request.getParameter("UserName");
if(StringUtil.isEmpty(UserName)){
	UserName = User.getUserName()+"";
}
ZDUserSchema member = new ZDUserSchema();
member.setUserName(UserName);
member.fill();
File f = new File(Config.getContextRealPath() + "/Member/UserFiles/" + member.getUserName() + "/"+Path);
if(!f.exists()){
	f.mkdirs();
}
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
String oldFileName = "";
String ext = "";
String AliasName = Config.getContextPath();
fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();
FileItem uploadItem = null;
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (!item.isFormField()){
		oldFileName = item.getName();
		System.out.println("-----UploadFileName:-----" + oldFileName);
		long size = item.getSize();
		if((oldFileName==null||oldFileName.equals("")) && size==0){
			continue;
		} else {
			if(size>200000){
				out.println("<script>alert('文件太大，请上传合适大小的文件(不大于200KB)');</script>");
				return;
			}
			uploadItem = item;
			ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			ext = ext.toLowerCase();
			if(PubFun.isAllowExt(ext,"Image")){
				String srcFileName =  member.getUserName() + "_head." + ext;
				if(uploadItem !=null){
					uploadItem.write(new File(Config.getContextRealPath()+ "/Member/UserFiles/" + member.getUserName() + "/"+Path+srcFileName));
					out.println("<script>window.parent.afterUpload('"+AliasName + "Member/UserFiles/" + member.getUserName() + "/"+Path+srcFileName+"');</script>");
				}
			}else{
				out.println("<script>alert('图片格式错误');</script>");
				return;
			}
		}
	}
}

%>