<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script src="../Editor/fckeditor.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
Page.onLoad(changeInputType);

var input = "1";
var text = "2";
var select = "3";
var radio = "4";
var checkbox = "5";
var dateInput = "6";
var imageInput = "7";
var htmlInput = "8";
var timeInput = "9";

function changeInputType(){
	var inputType = $V("InputType");
	if(input==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").show();
		$("tr_MaxLength").show();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(text==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").show();
		$("tr_MaxLength").show();
		$("tr_Cols_Rows").show();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(select==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(radio==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(checkbox==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").show();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").show();
		$("tr_HTML").hide();
	}else if(dateInput==inputType||timeInput==inputType){
		$("tr_IsMandatory").show();
		$("tr_DefaultValue").hide();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(imageInput==inputType){
		$("tr_DefaultValue").hide();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").hide();
	}else if(htmlInput==inputType){
		$("tr_IsMandatory").hide();
		$("tr_DefaultValue").hide();
		$("tr_VerifyType").hide();
		$("tr_MaxLength").hide();
		$("tr_Cols_Rows").hide();
		$("tr_ListOption").hide();
		$("tr_HTML").show();
	}
}

function setSpell(){
	if($V("Code") == ""){
	  $S("Code",getSpell($V("Name")));
  }
}

var editor;
function getHTML(){
	editor = FCKeditorAPI.GetInstance('HTML');
    return editor.GetXHTML(false);	
}
</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.CatalogColumn.initDialog">
	<form id="form2">
	<table width="100%" height="100" border="0" align="center"
		cellpadding="4" cellspacing="" bordercolor="#DEDEDC"
		style="border-collapse:collapse;">
		<tr>
			<td width="100" height="10"></td>
			<td><input name="ColumnID" id="ColumnID" type="hidden"
				value="${ID}" /></td>
		</tr>
		<tr>
			<td align="right">字段ID：</td>
			<td>${ID}</td>
		</tr>
		<tr>
			<td height="29" align="right">表现形式：</td>
			<td><sky:select id="InputType" onChange="changeInputType()">
      ${InputType}
      </sky:select></td>
		</tr>
		<tr>
			<td align="right">字段名称：</td>
			<td><input name="Name" id="Name" type="text"
				verify="字段名字|NotNull" value="${Name}" onBlur="setSpell();" /></td>
		</tr>
		<tr>
			<td align="right">字段代码：</td>
			<td><input name="Code" id="Code" type="text"
				verify="字段名称|NotNull" value="${Code}" onBlur="setSpell();" /></td>
		</tr>
		<tr id="tr_IsMandatory">
			<td align="right">是否必填：</td>
			<td>${IsMandatory}</td>
		</tr>
		<tr id="tr_DefaultValue">
			<td align="right">默认值：</td>
			<td><input name="DefaultValue" id="DefaultValue" type="text"
				value="${DefaultValue}" /></td>
		</tr>
		<tr id="tr_VerifyType">
			<td height="29" align="right">校验类型：</td>
			<td><sky:select id="VerifyType">
      ${VerifyType}
      </sky:select></td>
		</tr>
		<tr id="tr_MaxLength">
			<td align="right">最大字符数：</td>
			<td><input name="MaxLength" id="MaxLength" type="text"
				verify="最大字符数|NotNull&&Int" value="${MaxLength}" /></td>
		</tr>
		<tr id="tr_Cols_Rows">
			<td align="right">宽、高：</td>
			<td>宽度：<input name="ColSize" id="ColSize" type="text" size="2"
				verify="宽度|NotNull&&Int" value="${ColSize}" /> 高度：<input name="RowSize"
				id="RowSize" type="text" size="2" verify="高度|NotNull&&Int"
				value="${RowSize}" /></td>
		</tr>
		<tr id="tr_ListOption" style="display:none">
			<td height="83" align="right">列表选项：<br>
			<br>
			<font color="red">(每行为一<br>
			个列表项)</font></td>
			<td><textarea id="ListOption" name="ListOption" cols="43"
				rows="5" verify="列表选项|NotNull">${ListOption}</textarea></td>
		</tr>
        <tr id="tr_HTML" style="display:none">
			<td height="83" align="right">HTML内容：</td>
			<td>
           <textarea id="HTML" name="HTML" cols=50 rows=15 style="height:150px">${HTML}</textarea> 
            </td>
		</tr>
		<tr>
			<td align="right" valign="top">字段沿用：</td>
			<td valign="top"><sky:select id="Extend" style="width:150px;">
				<span value="1">仅本栏目</span>
				<span value="2">所有子栏目沿用相同设置</span>
				<span value="3">所有栏目沿用相同设置</span>
			</sky:select></td>
		</tr>

	</table>
	</form>
</sky:init>
</body>
</html>
