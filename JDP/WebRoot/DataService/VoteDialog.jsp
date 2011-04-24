<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<%
	String catalogID = request.getParameter("RelaCatalogID");
	String value = "0";
	String text = "文档库";
	if (StringUtil.isNotEmpty(catalogID)&&!catalogID.equalsIgnoreCase("null")&&!catalogID.equals("0")) {
		value = catalogID;
		text = CatalogUtil.getName(catalogID);
	}
%>
<sky:init method="com.xdarkness.cms.dataservice.Vote.initDialog">
<script>
Page.onLoad(function(){
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
	Selector.setValueEx("VoteCatalogID",'${VoteCatalogID}','${VoteCatalogName}');
	changeVoteType();
	if($V("ID")!=""){
		$("VoteType_0").disabled = true;
		$("VoteType_1").disabled = true;
	}
});

function changeVoteType(){
	if($NV("VoteType")=="0"){
		$("tr_RelaCatalogID").show();
		$("tr_VoteCatalogID").hide();
	}else{
		$("tr_RelaCatalogID").hide();
		$("tr_VoteCatalogID").show();
	}
}
</script>
</head>
<body class="dialogBody">
<form id="form2">
<table width="500" border="0" align="center" cellpadding="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
	<tr>
      <td width="22%" align="right" ></td>
      <td width="78%"></td>
    </tr>
    <tr>
      <td align="right" >调查类型：</td>
      <td>
		  <input type='radio' name='VoteType' id='VoteType_0' value='0' onclick="changeVoteType()" ${VoteType_0}><label for='VoteType_0'>普通调查</label>
		  <input type='radio' name='VoteType' id='VoteType_1' value='1' onclick="changeVoteType()" ${VoteType_1}><label for='VoteType_1'>栏目调查</label>
	  </td>
    </tr>
    <tr>
      <td align="right" >调查主题：</td>
      <td>
		  <input name="Title" id="Title" value="${Title}" type="text" size="50" verify="调查主题|NotNull" />
		  <input type="hidden" name="ID" id="ID" value="${ID}" />
	  </td>
    </tr>
	<tr>
      <td align="right" >是否限制IP：</td>
      <td>${IPLimit}</td>
    </tr>
    <tr>
      <td align="right" >是否允许查看：</td>
      <td>${Prop4}</td>
    </tr>
    <tr>
      <td align="right" >验证码标志：</td>
      <td>${VerifyFlag}</td>
    </tr>
    <tr style="display:none">
      <td align="right" >宽度显示：</td>
      <td><input name="Width" id="Width" value="${Width}" type="text" size="1" verify="显示宽度|NotNull&&Int" />像素</td>
    </tr>
    <tr>
	  <td align="right">开始时间：</td>
	  <td>
	  <input name="StartDate" id="StartDate" value="${StartDate}" type="text" size="14" xtype="Date"/>
	  <input name="StartTime" id="StartTime" value="${StartTime}" type="text" size="10" xtype="Time"/><font color="red">为空则立即开始</font>
	  </td>
	</tr>
	<tr>
	  <td align="right">终止时间：</td>
	  <td >
	  <input name="EndDate" id="EndDate" value="${EndDate}" type="text" size="14" xtype="Date"/> 
	  <input name="EndTime" id="EndTime" value="${EndTime}" type="text" size="10" xtype="Time"/><font color="red">为空则需要手动终止</font>
	  </td>
	</tr>
    <tr id="tr_RelaCatalogID">
        <td height="25" align="right" >关联栏目：</td>
        <td height="25"><sky:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></sky:select></td>
    </tr>
    <tr id="tr_VoteCatalogID" style="display:none">
        <td height="25" align="right" >调查栏目：</td>
        <td height="25"><sky:select id="VoteCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp" verify="NotNull" condition="$('VoteType_1').checked==true"></sky:select></td>
    </tr>
</table>
</form>
</body>
</sky:init>
</html>
