<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.dataservice.MessageBoard.initMessageDialog">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0" align="center">
    <tr>
        <td width="23%" height="10">&nbsp;</td>
        <td width="77%"><input type="hidden" id="ID" name="ID" value="${ID}"/></td>
    </tr>
    <tr>
        <td height="25" align="right" >留言人：</td>
        <td height="25">&nbsp;<b>${AddUser}</b>&nbsp;[IP:${IP}]</td>
    </tr>
    <tr>
        <td height="25" align="right" >留言时间：</td>
        <td height="25">&nbsp;<b>${AddTime}</b></td>
    </tr>
        <tr>
        <td height="25" align="right" >QQ：</td>
        <td height="25">&nbsp;<a href="tencent://message/?uin=${QQ}&Menu=yes">${QQ}</a></td>
    </tr>
        <tr>
        <td height="25" align="right" >E-mail：</td>
        <td height="25">&nbsp;<a href="mailto:${EMail}">${EMail}</a></td>
    </tr>
    <tr>
        <td height="25" align="right" >标题：</td>
        <td height="25">
        <input name="Title" type="text" value="${Title}" class="inputText" id="Title" size="60" readonly/>
        </td>
    </tr>
    <tr>
        <td height="25" align="right" >内容：</td>
        <td height="25">
        <textarea id="Content" name="Content" style="width:320px; height:100px;" readonly>${Content}</textarea>
        </td>
    </tr>
    <tr>
        <td height="25" align="right" >回复：</td>
        <td height="25">
        <textarea id="ReplyContent" name="ReplyContent" style="width:320px; height:50px;">${ReplyContent}</textarea>
        </td>
    </tr>
</table>
</form>
</sky:init>
</body>
</html>
