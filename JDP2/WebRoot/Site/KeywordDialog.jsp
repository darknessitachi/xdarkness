<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<sky:init method="com.xdarkness.cms.site.Keyword.initDialog">
<form id="form2">
<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td >
   <fieldset>
    <legend><label>基本信息</label></legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" height="200">
        <tr>
          <td width="39%" height="30" align="right" >关键字：</td>
          <td width="61%"><input name="Keyword" type="text" value="${Keyword}" style="width:100px" class="input1" id="Keyword" verify="关键字|NotNull" size=15 /> <input name="ID" type="hidden" id="ID" /></td>
        </tr>
        <tr>
          <td height="30" align="right" >链接地址：</td>
          <td><input name="LinkURL" type="text" value="${LinkUrl}" class="input1" id="LinkURL" size=36 /></td>
        </tr>
        <tr id ="tr_Password2">
          <td height="30" align="right" >链接提示：</td>
          <td><input name="LinkAlt" type="text" value="${LinkAlt}" class="input1" id="LinkAlt" size="36" /></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30" align="right" >打开方式：</td>
          <td><sky:select name="LinkTarget" id="LinkTarget" value="${LinkTarget}"><span value="_self">原窗口</span> <span value="_blank">新窗口</span> <span value="_parent">父窗口</span></sky:select></td>
        </tr>
      </table></fieldset></td>
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>所属分类</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0" height="200">
			<tr>
			<td>
	    <sky:tree id="tree1" style="width:230px;height:200px" method="com.xdarkness.cms.site.KeywordType.loadTypeTree">
	      <p cid='${ID}' >
	        <input type="checkbox" name="keywordType" value='${ID}' ${checked}>
	        ${TypeName}</p>
	      </sky:tree>
	      <input type="hidden" id="ID" value="${ID}" />
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
</table>
</form>
</sky:init>
</body>
</html>