<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员登陆</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}
if(User.isLogin()&&User.isMember()){
	response.sendRedirect("MemberInfo.jsp?SiteID="+SiteID);
}
String referer = request.getParameter("referer");
if(StringUtil.isEmpty(referer)){
	referer = request.getHeader("referer");
}
if(StringUtil.isEmpty(referer)){
	referer = "";
}
%>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<script type="text/javascript">
Page.onLoad(function(){
	$("UserName").focus();					 
});

function doLogin(){
	var userName = $V("UserName").trim();
	var passWord = $V("PassWord").trim();
	if(userName==""||passWord==""){
		Dialog.alert("请输入用户名和密码再登录",function(){
			$("UserName").focus();								   
		});
	}else{
		var dc = Form.getData("loginform");
		Server.sendRequest("com.xdarkness.member.Login.doLogin",dc,function(response){
			if(response.Status!=1){
				Dialog.alert(response.Message);
				if(!$("trVerifyCode").visible()){
					$("trVerifyCode").show();
					var sb = [];
					sb.push("<img src=\"../Services/AuthCode.jsp?Height=20&Width=80\" alt=\"点击刷新验证码\" height=\"18\" "); 
					sb.push(" onClick=\"this.src='../Services/AuthCode.jsp?Height=20&Width=80&'+new Date().getTime()\" ");
					sb.push(" align=\"absmiddle\" style=\"cursor:pointer;\"/>&nbsp; <input ");
					sb.push(" name=\"VerifyCode\" type=\"text\" style=\"width:60px\" id=\"VerifyCode\" ");
					sb.push("class=\"inputText\" onfocus=\"this.select();\"/>");
					$("tdVerifyCode").innerHTML = sb.join('');
				}
			}else{
				if($V("Referer")!="" && $V("Referer")!='null' && $V("Referer").indexOf("Register.jsp")<0){
					window.location = $V("Referer");
				}else{
					window.location = "MemberInfo.jsp?SiteID=<%=SiteID%>";
				}							   
			}
		});	
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		doLogin();
	}
}

function toRegister(){
	window.location = "Register.jsp?SiteID=<%=SiteID%>";	
}
</script>
<div class="wrap">
  <div id="menu" class="forumbox"> <span class="fl"> <a href="Register.jsp?SiteID=<%=SiteID%>">注册</a> <a href="#;">登录</a> </span> <span class="fr"><a href="#;">标签</a> | <a href="#;">帮助</a>
    </span> </div>
  <div id="nav"><a href="#;">首页</a> &raquo; 登陆</div>
  <table width="100%" border="0" cellspacing="40" cellpadding="0" class="cellspacing">
    <tr valign="top">
      <td width="50%"><div class="forumbox" style="height:280px">
          <h4>会员登陆</h4>
          <div style="padding:20px;">
            <p><b>已注册用户请从这里登陆</b></p>
            <form id="loginform">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
                <tbody>
                  <tr>
                    <td width="80" height="30"><label>用户名：</label></td>
                    <td><input type="text" class="name" name="UserName" id="UserName"/>
                    <input type="hidden" id="Referer" name="Referer" value="<%=referer%>"/></td>
                  </tr>
                  <tr>
                    <td height="30"><label for="password">密码：</label></td>
                    <td><input id="PassWord" name="PassWord" type="password" class="mima"/> <a href="ForgetPassword.jsp?SiteID=<%=SiteID%>"> 忘记密码？</a></td>
                  </tr>
                  <tr style="display:none" id="trVerifyCode">
                    <td height="30">验证码：</td>
                    <td id="tdVerifyCode"></td>
                  </tr>
                  <tr>
                    <td height="30">登录有效期：</td>
                    <td><label><input type="radio" tabindex="8" value="315360000" name="CookieTime" class="radio"/>永久</label>
                      <label><input type="radio" checked="checked" tabindex="9" value="2592000" name="CookieTime" class="radio"/>一个月</label>
                      <label><input type="radio" tabindex="10" value="86400" name="CookieTime" class="radio"/>一天</label>
                      <label><input type="radio" tabindex="12" value="0" name="CookieTime" class="radio"/>浏览器进程</label></td>
                  </tr>
                  <tr>
                    <td height="30"><label for="loginmode">隐身登录：</label></td>
                    <td><select tabindex="13" name="loginmode" id="loginmode">
                        <option value="Normal"> 不隐身</option>
                        <option value="invisible"> 隐身</option>
                      </select></td>
                  </tr>
                  <tr>
                    <th height="30"> </th>
                    <td><button tabindex="100" value="true" name="loginsubmit" type="button" class="submit" onclick="doLogin();">提 交</button></td>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div></td>
      <td width="50%"><div class="forumbox" style="height:280px">
          <h4>新用户注册</h4>
          <div style="padding:20px;">
            <p><b>未注册用户请注册新帐户</b></p>
            <p>注册后，您可享受以下服务</p>
            <ul style="font-family:'宋体'; color:#999; padding-left:12px;">
              <li>·以客户为中心，更贴心、更专业的服务</li>
              <li>·发布个人信息、企业信息</li>
              <li>·浏览会员发布的信息、检索网站内容</li>
              <li>……</li>
            </ul>
            <br>
            <button tabindex="100" value="true" name="register" type="button" class="button" onClick="toRegister();">立即注册</button>
          </div>
        </div></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>