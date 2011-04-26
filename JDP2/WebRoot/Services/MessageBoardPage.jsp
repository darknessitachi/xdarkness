<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.member.Login"%>
<%@taglib uri="controls" prefix="sky"%>
<html>
<sky:init method="com.xdarkness.cms.dataservice.MessageService.init">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言板 - ${BoardName}</title>

<%Login.checkAndLogin(request);%>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css">
<script src="../Framework/Main.js" type="text/javascript"></script>
<script type="text/javascript">

function checkContent(){
  var content = document.getElementById("MsgContent");
   if(!content.value||content.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
     alert("留言内容不能为空!");
     content.focus();
     return;
   }else{
   	 document.getElementById("MegForm").submit();
   }
}

</script>
<style type="text/css">
.messageBox{
	border:1px solid #c8d8f2;
	padding:17px 10px;
	font:12px "宋体";
	margin:10px auto;
	text-align:left;
	max-width:950px
}
.messageBox h2{
	background:url(../Images/comment.gif) no-repeat left 2px;
	font-size:14px;
	margin:0;
	padding:0 0 9px 15px;
	border-bottom:1px dashed #c8d8f2;
}
.messageBox h2 span{
	 float:right;
	 _display:inline;
	 margin-top:-10px;
	 *margin-top:-30px;
	 font-size:12px;
	 color:#666;
	 text-decoration:none;
}

.messageContent{_padding-bottom:1px}
.messageContent .time{
	color:#666;
	line-height:20px;
	padding:4px 0 0 5px;
}
.messageContent .content{
	line-height:20px;
	padding:2px 0 2px 5px;
}
.messageContent .fenxiang{
	color:#0033cc;
	float:right;
	height:21px;
	padding:3px 3px 0;
	text-align:center;
}
.messageContent .fenxiang a{
	color:#0033cc;
	text-decoration:none;
}
.messageContent .fenxiang a:hover, .messageContent .fenxiang a:active{
	text-decoration:underline;
}
.messageContent .line{
	clear:both;
	background:url(../Images/line.gif) repeat-x left top;
	height:1px;
	font-size:1px;
}
.messageContent .page{
	margin:10px 0;
	padding:5px;
	text-align:center;
}
.messageContent .page a{
	font-weight:bold;;
	display:inline-block;
}
.messageContent .page a:link, .messageContent .page a:visited{
	color:#000;
}
.messageContent .page a:hover, .messageContent .page a:active{
	color:#cc0000;
	text-decoration:underline;
}
.messageContent .page a.pagefirst:link, .messageContent .page a.pagefirst:visited{
	text-decoration:underline;
	color:#cc0000;
}
.messageContent .page .pagebtn{
	display:inline-block;
	width:53px;
	border:1px solid #ccc;
	background-color:#fff;
	padding:2px 3px;
	font-weight:normal;
	text-decoration:none;
}
.messageContent form{
	margin:0;
}
.messageContent .textBox textarea{
	width:90%;
	height:100px;
	margin:10px 1%;
	_margin:10px 0.5%;
}
.messageContent input.txt{
	width:100px;
}
.messageContent input.btn{
	width:130px;
}
.message{ text-align:center; padding:10px; text-align:left; color:#333; word-wrap:break-word;}
.message div,
.message div.huifu{ padding:2px; margin:2px;}
.message div.huifu{ border:1px solid #aaa; padding:2px; background-color:#FEFEED; margin:0 auto; text-align:left;}
.message div.time,
.message div.content{ margin:0; padding:2px;}
</style>
<%@ include file="../Include/Head.jsp" %>
</head>

<body>
<%@ include file="../Include/Top.jsp" %>
	<div id="messageBox" class="messageBox" style="width:935px">

	<h2>网友留言<span>留言板：${BoardName}</span></h2>

	<div id="messageContent" class="messageContent">
    
    <sky:datalist id="dg1" method="com.xdarkness.cms.dataservice.MessageService.dg1DataBind" size="10" page="true">

		<div style="line-height:20px;padding:4px 0 0 5px;">${Title}<div class="time" style="float:right">${AddUser} &nbsp;${AddTime}&nbsp; [IP:${IP}]</div></div>
		<div class="message">${Content}</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        ${Prefix}${Reply}
		<div class="line"></div>
		
	</sky:datalist>

	<div class="page">
    <sky:pagebar target="dg1" type="1" />
    </div>

	<div class="textBox">

	<form id="MegForm" action="${ServicesContext}${MessageActionURL}" method="post">
        <div style="margin-bottom:5px;">
        &nbsp;&nbsp;留言标题：<input type="text" id="Title" name="Title" value="" size="45"/>
        &nbsp;&nbsp;&nbsp;E-Mail：<input type="text" id="Email" name="Email" value=""/>
        &nbsp;&nbsp;&nbsp;QQ：<input type="text" id="QQ" name="QQ" value=""/>
        </div>
    	<div><textarea name="MsgContent" id="MsgContent"></textarea></div>
        
        <%
		if(!User.isLogin()){
		%>
        <div id="textLogin" style="margin-bottom:10px;">
        &nbsp;&nbsp;登录名：<input type="text" name="UserName" id="UserName" class="txt" /> 
        密码：<input type="password" name="PassWord" id="PassWord" class="txt" /> <input type="checkbox" name="HiddenUserName" id="HiddenUserName" checked/>匿名留言
        </div>
    	<%}%>
		&nbsp;&nbsp;<input type="button" name="MessageSubmit" id="MessageSubmit" value="提交留言" class="btn" onClick="checkContent();"/>
        <input type="hidden" id="BoardID" name="BoardID" value="${BoardID}" />
	</form>

	</div>

	</div>

	</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</sky:init>
</html>
