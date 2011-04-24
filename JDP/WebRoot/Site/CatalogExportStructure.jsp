<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
.style1 {color: #445566; }
-->
</style>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.Catalog.initStructure">
  <table width="100%" height="100%" align="center" cellpadding="1" cellspacing="0"	>
    <tr>
      <td align="center"><textarea name="textarea" style="height:320px;width:400px;vertical-align:top;">${Structure}</textarea>        <p></td>
    </tr>
    <tr>
      <td align="center"></td>
    </tr>
    <tr>
      <td align="center"></td>
    </tr>
  </table>
</sky:init>
</body>
</html>
