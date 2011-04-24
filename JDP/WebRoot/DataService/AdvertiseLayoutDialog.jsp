<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=Config.getValue("App.Name")%></title>
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
<script>
Page.onLoad(function(){
	if($V("hPositionType")!=""){
		$S("PositionType",$V("hPositionType"));
	}
	if($V("Align")=="Y"){
		$("AlignBox").checked = true;
		$("PaddingLeft").disabled = true;
		$("PaddingTop").disabled = true;
	}else{
		$("AlignBox").checked = false;
	}
	if($V("Scroll")=="Y"){
		$("ScrollBox").checked = true;
	}else{
		$("ScrollBox").checked = false;
	}
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
});

function  ChangePositionType(){
	var PositionType = $V("PositionType");
	if(PositionType=="code"){
		$("trSize").hide();
	}else{
		$("trSize").show();
	}
	if(PositionType=="text"){
		$("trPosition").hide();
		$("trSize").hide();
	}
	if(PositionType=="couplet"||PositionType=="float"||PositionType=="fixure"){
		$("trPosition").show();
	}else{
		$("trPosition").hide();
	}
	if(PositionType=="fixure"){
		$("AlignSpan").style.display="";
		$("ScrollSpan").style.display="none";
	}else if(PositionType=="couplet"){
		$("ScrollSpan").style.display="";
		$("AlignBox").checked = false;
		$("PaddingLeft").disabled = false;
		$("PaddingTop").disabled = false;
		$S("Align","N");
		$("AlignSpan").style.display="none";
	}else{
		$("AlignSpan").style.display="none";
		$("ScrollSpan").style.display="none";
	}
	if($V("ID")!=""){
		if(PositionType!=$V("hPositionType")){
			$("alertTr").style.display="";
		}else{
			$("alertTr").style.display="none";
		}
	}
}

function changeAlign(){
	var Align = $V("Align");	
	var AlignBox = $("AlignBox");
	if(Align=="Y"){
		AlignBox.checked = false;
		$("PaddingLeft").disabled = false;
		$("PaddingTop").disabled = false;
		$S("Align","N");
	}else{
		AlignBox.checked = true;
		$("PaddingLeft").disabled = true;
		$("PaddingTop").disabled = true;
		$S("Align","Y");
	}
}

function changeScroll(){
	var Scroll = $V("Scroll");	
	var ScrollBox = $("ScrollBox");
	if(Scroll=="Y"){
		ScrollBox.checked = false;
		$S("Scroll","N");
	}else{
		ScrollBox.checked = true;
		$S("Scroll","Y");
	}
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.cms.dataservice.AdvertiseLayout.DialogInit">
<form id="form2">
<table width="100%" border="0" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
	<tr>
		<td width="25%" height="12"></td>
		<td colspan="2">
        <input type="hidden" id="ID" name="ID" value="${ID}"/>
        <input type="hidden" id="hPositionType" name="hPositionType" value="${PositionType}"/>
        </td>
	</tr>
    <tr>
      <td  align="right" valign="top">版位名称：</td>
      <td width="30%"><input name="PositionName" type="text" id="PositionName" value="${PositionName}" size="20" verify="广告版位名称|NotNull"/></td>
      <td width="45%">关联栏目:
      <sky:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></sky:select></td>
    </tr>
    <tr>
      <td  align="right" valign="top">版位类型：</td>
      <td colspan="2"><sky:select name="PositionType" id="PositionType" onChange="ChangePositionType();">
        <span value="banner" selected>矩形横幅</span>
        <span value="fixure">固定位置</span>
        <span value="float">漂浮移动</span>
        <span value="couplet">对联广告</span>
        <span value="imagechange">图片轮换广告</span>
        <span value="imagelist">图片列表广告</span>
        <span value="text">文字广告</span>
        <span value="code">代码调用</span>
      </sky:select>
      <span id="ScrollSpan" style="padding-left:30px;display:none;"><label for="ScrollBox"><input type="checkbox" id="ScrollBox" onClick="changeScroll();" checked/>随屏滚动</label></span>
      <span id="AlignSpan" style="padding-left:30px;display:none;"><label for="AlignBox"><input type="checkbox" id="AlignBox" onClick="changeAlign();" checked/>全屏居中</label></span><input type="hidden" id="Scroll" name="Scroll" value="${Scroll}"/><input id="Align" name="Align" type="hidden" value="${Align}"/>
      </td>
    </tr>
    <tr id="trPosition" style="display:none;">
    	<td align="right"  valign="top">版位位置：</td>
        <td valign="top" colspan="2">
        左边距：<input name='PaddingLeft' id='PaddingLeft' type='text' size='5' value='${PaddingLeft}' verify="左边距|Number&&NotNull" condition="$V('PositionType')!='banner'&&$V('PositionType')!='imagechange'&&$V('PositionType')!='code'&&$('PaddingLeft').disabled!=true&&$V('PositionType')!='text'"> px&nbsp;&nbsp;
        上边距：<input name='PaddingTop' id='PaddingTop' type='text' size='5' value='${PaddingTop}' verify="上边距|Number&&NotNull" condition="$V('PositionType')!='banner'&&$V('PositionType')!='imagechange'&&$V('PositionType')!='code'&&$('PaddingTop').disabled!=true&&$V('PositionType')!='text'" /> px
        </td>
    </tr>
    <tr id="trSize">
      <td align="right" valign="top">版位尺寸：</td>
      <td colspan="2">&nbsp;&nbsp;&nbsp;宽度：<input name="PositionWidth" type="text" id="PositionWidth"  value="${PositionWidth}" size="5" verify="版位宽度|Number&&NotNull" condition="$V('PositionType')!='code'&&$V('PositionType')!='text'"/> px&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      高度：<input name="PositionHeight" type="text" id="PositionHeight" value="${PositionHeight}" size="5" verify="版位高度|Number&&NotNull" condition="$V('PositionType')!='code'&&$V('PositionType')!='text'" /> px</td>
    </tr>
    <tr>
      <td  align="right" valign="top">版位描述：</td>
      <td colspan="2"><textarea id="Description" name="Description" cols="52" rows="4">${Description}</textarea></td>
    </tr>
    <tr id="alertTr" style="display:none;">
      <td>&nbsp;</td>
      <td align="left" colspan="2"><font color="#FF0000">注意：变更版位类型将导致版位下已有的广告内容清空</font></td>
    </tr>
</table>
</form>
</sky:init>
</body>
</html>