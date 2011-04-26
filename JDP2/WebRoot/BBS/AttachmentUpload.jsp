<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="com.xdarkness.platform.pub.NoUtil"%>
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.cms.pub.PubFun"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%
String Path = "/BBS/Upload/File";
File dir = new File(Config.getContextRealPath()+Path);
if(!dir.exists()){
	dir.mkdirs();
}
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
String oldFileName = "";
String RealFileName = "";
String ext = "";
String AliasName = Config.getAppCode();
fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();
FileItem uploadItem = null;
StringBuffer pathAll = new StringBuffer();
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (!item.isFormField()){
		oldFileName = item.getName();
		System.out.println("-----UploadFileName:-----" + oldFileName);
		long size = item.getSize();
		if((oldFileName==null||oldFileName.equals("")) && size==0){
			continue;
		} else {
			if(size>5120000){
				out.println("<script>alert('文件太大，请上传合适大小的文件(不大于5MB)');</script>");
				return;
			}
			uploadItem = item;
			oldFileName = oldFileName.replaceAll("\\\\", "/");
			oldFileName = oldFileName.substring(oldFileName.lastIndexOf("/") + 1);
			ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			ext = ext.toLowerCase();
			if(PubFun.isAllowExt(ext)){
				long webAttachID = NoUtil.getMaxID("UserFileID");
				if(uploadItem !=null){
					RealFileName = webAttachID+"."+ext;
					uploadItem.write(new File(Config.getContextRealPath()+Path+RealFileName));
					pathAll.append(Path+RealFileName+"#"+size+",");
					//out.println("<script>window.parent.afterUpload('/"+AliasName+Path+RealFileName+"');</script>");
				}
			}else{
				out.println("<script>alert('附件格式错误');</script>");
				return;
			}
		}
	}
}
out.println("<script>window.parent.afterUpload('"+pathAll.toString()+"');</script>");
%>



