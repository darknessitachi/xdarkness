<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.dataservice.MemberAddr.initDialog">
	<table width="100%" cellpadding="2" cellspacing="0">
    	<tr>
            <td width="13%" align="right">&nbsp;</td>
            <td width="37%">&nbsp;</td>
            <td width="13%" align="right">&nbsp;</td>
            <td width="37%">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">收货人：</td>
            <td>${RealName}</td>
            <td align="right">固定电话：</td>
            <td>${Tel}</td>
        </tr>
        <tr>
            <td align="right">省份：</td>
            <td>${ProvinceName}&nbsp;${CityName}&nbsp;${DistrictName}</td>
            <td align="right">手机：</td>
            <td>${Mobile}</td>
        </tr>
        <tr>
            <td align="right">地址：</td>
            <td>${Address}&nbsp;&nbsp;邮编：${ZipCode}</td>
            <td align="right">电子邮件：</td>
            <td>${Email}</td>
        </tr>
    </table>
</sky:init>
</body>
</html>
