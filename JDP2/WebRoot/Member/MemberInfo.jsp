<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky" %>
<%@page import="com.xdarkness.framework.Config"%>
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

function save(){
	var dc = Form.getData("InfoForm");
	Server.sendRequest("com.xdarkness.member.MemberInfo.doSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location.reload();
			}						   
		});
	});
}

</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">会员中心</a>  &raquo; 基本资料
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
      <sky:init method="com.xdarkness.member.MemberInfo.init">
      <form id="InfoForm">
          <div class="forumbox"> <span class="fr">带 <span style=" font-family:'宋体'; color:#F60">*</span> 标示为必填项</span>
            <h4>编辑个人资料</h4>
            <ul class="tabs ">
              <li class="current"><a href="#action=profile&amp;typeid=2">基本资料</a></li>
              <li><a href="MemberInfoDetail.jsp?SiteID=<%=request.getParameter("SiteID")%>">详细资料</a></li>
              <li><a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">上传头像</a></li>
            </ul>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">基本信息</h5>
            <table width="80%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="18%" align="right">用户名：</td>
                <td width="39%"><b>${UserName}</b><input type="hidden" id="UserName" name="UserName" value="${UserName}"/></td>
                <td width="43%" rowspan="5" align="left" valign="middle"><img id="LogoPic" src="${Logo}" width="100" height="100" style="border:#DFDFDF 1px solid"/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">上传头像</a></td>
              </tr>
               <tr>
                <td width="18%" align="right">验证状态：</td>
                <td width="39%"><b style="color:#999999">${StatusName}</b></td>
              </tr>
              <tr>
                <td width="18%" align="right">会员级别：</td>
                <td width="39%"><b>${MemberLevelName}</b></td>
              </tr>
              <tr>
                <td width="18%" align="right">注册时间：</td>
                <td width="39%"><b>${RegTime}</b></td>
                </tr>
              <tr>
                <td width="18%" align="right">上次登录：</td>
                <td width="39%"><b>${LastLoginTime}</b></td>
                </tr>
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">扩展信息</h5>
             <table width="99%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="14%" align="right">
                <%if("Person".equals(User.getValue("Type"))){%>昵称：<%}else{%>公司全称<%}%>
                </td>
                <td width="86%"><input id="Name" name="Name" type="text" class="textInput" size="35" value="${Name}"/></td>
              </tr>
                <tr>
                <td align="right">E-mail：</td>
                <td><input id="Email" name="Email" type="text" class="textInput" size="35" value="${Email}"/></td>
              </tr>
              <tr>
                <td width="14%" align="right">性别：</td>
                <td width="86%">${Gender}</td>
              </tr>
              ${Columns}
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <div style=" padding-left:150px;">
              <button type="button" onClick="save()" name="tiajiao" value="true" class="submit">提交</button>
              &nbsp;
              <button type="reset" name="reset" value="false" class="button">重 置</button>
            </div>
          </div>
        </form></sky:init></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>