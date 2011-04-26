<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body >
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td>
		 <sky:tab>
			<sky:childtab id="ConfigCatalogShow" src="CatalogShowConfig.jsp" selected="true"><img src="../Icons/icon023a11.gif" /><b>栏目树设置</b></sky:childtab>
			<sky:childtab id="ConfigRefer" src="ReferConfig.jsp" ><img src="../Icons/icon003a16.gif" /><b>文章来源管理</b></sky:childtab>
		 </sky:tab>
	  </td>
   </tr>
</table>
</body>
</html>