<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.member.Member"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员密码重置</title>
<link href="default.css" rel="stylesheet" type="text/css">
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function resetPassword(){
	if($V("NewPassWord").replace(/(^\s*)|(\s*$)/g,"")==""||$V("ConfirmPassword").replace(/(^\s*)|(\s*$)/g,"")==""){
		Dialog.alert("请填写密码信息");
		return;
	}
	var dc = Form.getData("passForm");
	Server.sendRequest("com.xdarkness.member.MemberInfo.resetPassword",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location = "Login.jsp?SiteID="+response.get("SiteID");
			}						   
		});
	});
}

</script>
</head>
<%
	String userName = request.getParameter("User");
	String pwq = request.getParameter("pwq");
	boolean flag = false;
	if(StringUtil.isNotEmpty(userName)&&StringUtil.isNotEmpty(pwq)){
		Member member = new Member(userName);
		if(member.fill()){
			if(StringUtil.isNotEmpty(member.getLoginMD5())&&member.getLoginMD5().equalsIgnoreCase(pwq)){
				flag = true;
			}
		}
	}
%>
<body>
<%@ include file="../Include/Top.jsp" %>
<div style="width:950px; margin:auto;">
  <table width="100%" border="0" cellspacing="15" cellpadding="0">
    <tr valign="top">
      <td>
      <div style="border:1px solid #ddd; padding:30px 20px;zoom:1;">
      <div style="border-bottom:1px solid #ddd; padding-bottom:4px; margin-bottom:10px;"><h4>密码重置</h4></div>
      <table width="100%" border="0" cellspacing="50" cellpadding="0">
		<%if(flag){%>
        <tr valign="top">
          <td width="55%">
          <form id="passForm" style="margin:20px 15px;">
          <table width="100%" border="0" cellspacing="9" cellpadding="0">
            <tr>
                <td align="right">新密码:<input type="hidden" id="UserName" name="UserName" value="<%=userName %>"></td>
                <td><input type="password" class="name" name="NewPassWord" id="NewPassWord" value=""/></td>
            </tr>
            <tr>
                <td align="right">确认新密码:</td>
                <td><input type="password" class="name" name="ConfirmPassword" id="ConfirmPassword" value=""/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><input type="button" class="btn" onclick="resetPassword();" value=" 修 改 "/></td>
            </tr>
            </table>
            </form>
            </td>
        </tr>
        <%}else{%>
        <tr valign="middle">
          <td width="55%">
          <table width="100%" border="0" cellspacing="9" cellpadding="0" height="100%">
            <tr>
                <td align="center">无效的连接，链接已过期</td>
            </tr>
            </table>
            </td>
        </tr>
        <%}%>
      </table></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>
