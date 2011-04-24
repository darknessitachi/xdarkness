<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
	  <td height="35" valign="middle" class="tdgrey1">&nbsp;&nbsp;选择要分发到的网站群栏目</td>
    </tr>
	<tr>
	  <td height="35" align="center" class="tdgrey2"><sky:datagrid id="dg1" method="com.xdarkness.cms.document.ArticleDeployCatalog.articleDialogDataBind" page="false" size="4" autoFill="false">
        <table width="98%" cellpadding="2" cellspacing="0" class="dataTable">
          <tr xtype="head" class="dataTableHead">
            <td width="9%" xtype="Selector" field="MD5"></td>
            <td width="31%"><b>站点名称</b></td>
            <td width="20%"><b>栏目名称</b></td>
            <td width="40%"><b>服务器地址</b></td>
          </tr>
          <tr onclick="this" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
            <td></td>
            <td>${SiteName}</td>
            <td>${CatalogName}</td>
             <td>${ServerAddr}</td>
         </tr>
        </table>
	    </sky:datagrid></td>
    </tr>
</table>
</form>
</body>
</html>
