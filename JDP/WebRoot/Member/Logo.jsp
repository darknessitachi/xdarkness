<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员中心</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function doLogout(){
	Dialog.confirm("您确认退出吗？",function(){
			window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;
	});
}

function uploadLogo(){
	if($V("LogoFile")!=""){
		$("LogoForm").submit();
	}
}

function afterUpload(path){
	$S("Logo",path);
	var dc = Form.getData("LogoForm");
	Server.sendRequest("com.xdarkness.member.MemberInfo.doSave",dc,function(response){
		if(response.Status!=1){
			Dialog.alert(response.Message);
		}else{
			$("LogoPic").src = path + "?" + new Date().getTime();
		}
	});
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<%@ include file="Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">会员中心</a>  &raquo; 上传头像
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
          <div class="forumbox"> <span class="fr">带 <span style=" font-family:'宋体'; color:#F60">*</span> 标示为必填项</span>
            <h4>编辑个人资料</h4>
            <ul class="tabs ">
              <li><a href="MemberInfo.jsp?SiteID=<%=request.getParameter("SiteID")%>">基本资料</a></li>
              <li><a href="MemberInfoDetail.jsp?SiteID=<%=request.getParameter("SiteID")%>">详细资料</a></li>
              <li class="current"><a href="#;">上传头像</a></li>
            </ul>
            <div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">设置头像</h5>
             <sky:init method="com.xdarkness.member.MemberInfo.init">
              <form id="LogoForm" target="formTarget" method="POST" enctype="multipart/form-data" action="Upload.jsp?Path=">
              <table width="80%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="20%" align="right">用户头像：</td>
                <td width="80%"><img id="LogoPic" src="${Logo}" width="100" height="100" style="border:#DFDFDF 1px solid"/>
                <input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
                <input type="hidden" id="Logo" name="Logo" value="${Logo}"/></td>
              </tr>
              <tr>
                <td width="20%" align="right">选择头像文件：</td>
                <td width="80%"><input type="file" id="LogoFile" name="LogoFile" /> 推荐100x100像素</td>
              </tr>
            </table>
        	<h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <br>
            <div style="padding-left:15%;">
            <input type="button" onClick="uploadLogo()" name="tiajiao" value="更 新" class="button" />
            </div>
            </form></sky:init>
          </div></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>