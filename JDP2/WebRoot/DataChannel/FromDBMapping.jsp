<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.datachannel.FromDB.initColumn">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择列</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function checkData(){
	if($V("TitleUniteFlag")=="Y"&&$V("TitleUniteRule").trim()==""){
		Dialog.alert("标题合并模板不能为空!");
		return false;
	}
	if($V("ContentUniteFlag")=="Y"&&$V("ContentUniteRule").trim()==""){
		Dialog.alert("内容合并模板不能为空!");
		return false;
	}
	var dt = $("dg1").DataSource;
	if(dt.Rows.length==0){
		Dialog.alert("没有映射任何字段!");
		return false;
	}
	var str = ","+$NV("Mapping").join(",")+",";
	if($V("TitleUniteFlag")!="Y"&&str.indexOf(",Title,")<0){
		Dialog.alert("没有字段映射到文章标题!");
		return false;
	}
	if($V("ContentUniteFlag")!="Y"&&str.indexOf(",Content,")<0){
		Dialog.alert("没有字段映射到文章内容!");
		return false;
	}
	return true;
}

function getData(){
	var dg = $("dg1");
	var dt = dg.DataSource;
	dt.insertColumn("Mapping");
	var arr = $N("Mapping");
	for(var i=0;i<dt.getRowCount();i++){
		dt.Rows[i].set("Mapping",$V(arr[i]));
	}
	var map = {};
	map.DataTable = dt;
	map.TitleUniteFlag = $V("TitleUniteFlag");
	map.TitleUniteRule = $V("TitleUniteRule");
	map.ContentUniteFlag = $V("ContentUniteFlag");
	map.ContentUniteRule = $V("ContentUniteRule");
	map.RedirectURLUniteFlag = $V("RedirectURLUniteFlag");
	map.RedirectURLUniteRule = $V("RedirectURLUniteRule");
	return map;
}

function onColumnSelectedChange(ele){
	if($V(ele)==""){
		return;
	}
	var arr = $N("Mapping");
	for(var i=0;i<arr.length;i++){
		if(arr[i].id!=ele.id&&$V(arr[i])==$V(ele)){
			$S(arr[i],"");
		}
	}
}

var titleRemoveFlag = false;
var contentRemoveFlag = false;
function onTitleUniteClick(){
	var arr = $N("Mapping");
	if($V("TitleUniteFlag")=="Y"){
		$S("TitleUniteRule","");
		$("TitleUniteRule").enable();
		if(!arr||arr.length==0){
			return;
		}
		var options = arr[0].options;
		var index = 0;
		for(var i=0;i<options.length;i++){
			if(options[i].innerText == "文章标题"){
				index = i;
				break;
			}
		}
		if(index!=0){
			titleRemoveFlag = true;
			for(var i=0;i<arr.length;i++){
				if($V(arr[i])=="Title"){
					arr[i].selectedIndex = 0;
				}
				arr[i].remove(index);//文章标题不再可用
			}
		}
	}else{
		if(titleRemoveFlag){
			titleRemoveFlag = false;
			for(var i=0;arr&&i<arr.length;i++){
				arr[i].add("文章标题","Title");
			}
		}
	}
}

function onRedirectURLUniteClick(){
	if($V("RedirectURLUniteFlag")=="Y"){
		$("RedirectURLUniteRule").enable();
	}else{
		$("RedirectURLUniteRule").disable();
	}
}

function onContentUniteClick(){
	var arr = $N("Mapping");
	if($V("ContentUniteFlag")=="Y"){
		$S("ContentUniteRule","");
		$("ContentUniteRule").enable();
		if(!arr||arr.length==0){
			return;
		}
		var options = arr[0].options;
		var index = 0;
		for(var i=0;i<options.length;i++){
			if(options[i].innerText == "文章内容"){
				index = i;
				break;
			}
		}
		if(index!=0){
			contentRemoveFlag = true;
			for(var i=0;i<arr.length;i++){
				if($V(arr[i])=="Content"){
					arr[i].selectedIndex = 0;
				}
				arr[i].remove(index);
			}
		}
	}else{
		$("ContentUniteRule").disable();
		if(contentRemoveFlag){
			contentRemoveFlag = false;
			for(var i=0;arr&&i<arr.length;i++){
				arr[i].add("文章内容","Content");
			}
		}
	}
}

Page.onLoad(function(){
	$S("TitleUniteFlag","${TitleUniteFlag}");
	$S("TitleUniteRule","${TitleUniteRule}");
	$S("ContentUniteFlag","${ContentUniteFlag}");
	$S("ContentUniteRule","${ContentUniteRule}");
	$S("RedirectURLUniteFlag","${RedirectURLUniteFlag}");
	$S("RedirectURLUniteRule","${RedirectURLUniteRule}");
	onTitleUniteClick();
	onRedirectURLUniteClick();
	onContentUniteClick();
});
</script>
</head>
<body>
<table width="760" border="0" cellspacing="0" cellpadding="6">
  <tr>
    <td valign="top" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"></td>
    <td valign="top" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"></td>
  </tr>
  <tr>
    <td width="400" valign="top" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
	<sky:datagrid id="dg1" method="com.xdarkness.datachannel.FromDB.columnDataBind" page="false">
        <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
          <tr xtype="head" class="dataTableHead">
            <td  width="12%" xtype="rowno">&nbsp;</td>
            <td width="42%">原字段名</td>
            <td width="46%">映射到</td>
          </tr>
          <tr style1="background-color:#FFFFFF" style2="background-color:#F7F8FF">
            <td align="center">&nbsp;</td>
            <td>${Code}
              <textarea name="ListOptions" style="display:none">${listOptions}</textarea></td>
            <td><sky:select style="wdith:100px" name="Mapping" onChange="onColumnSelectedChange(this);" value="${Mapping}">
               ${@Columns}
              </sky:select>
            </td>
          </tr>
        </table>
      </sky:datagrid>
    </td>
    <td valign="top" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"><table width="100%" border="0">
        <tr>
          <td height="25"><input type="checkbox" name="TitleUniteFlag" value="Y" id="TitleUniteFlag" onclick="onTitleUniteClick();">
            <label for="TitleUniteFlag">标题由多字段合并而来</label>
            &nbsp;</td>
        </tr>
        <tr>
          <td><input name="TitleUniteRule" type="text" disabled="true" id="TitleUniteRule" style="width:340px" value="请在此填写标题合并规则..."></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="25"><input type="checkbox" name="RedirectURLUniteFlag" value="Y" id="RedirectURLUniteFlag" onclick="onRedirectURLUniteClick();">
 <label for="RedirectURLUniteFlag">跳转URL由多字段合并而来</label>
&nbsp;</td>
        </tr>
        <tr>
          <td height="25"><input name="RedirectURLUniteRule" type="text" disabled="true" id="RedirectURLUniteRule" style="width:340px" value="请在此填写跳转合并规则..."></td>
        </tr>
        <tr>
          <td height="25">&nbsp;</td>
        </tr>
        <tr>
          <td height="25"><p>
              <input type="checkbox" name="ContentUniteFlag" value="Y" id="ContentUniteFlag" onclick="onContentUniteClick();">
              <label for="ContentUniteFlag">内容由多字段合并而来</label>
            </p></td>
        </tr>
        <tr>
          <td><textarea name="ContentUniteRule" id="ContentUniteRule" disabled="true" style="width:340px;height:100px">请在此填写内容合并模板...</textarea></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td class="gray">在标题合并规则、跳转URL合并规则和内容合并模板中，可以使用<strong> $</strong><strong>{FieldName}</strong> 的方式引用数据表中的字段，采集任务执行时，会将&nbsp;<strong><strong>$</strong><strong>{FieldName}</strong>&nbsp;</strong>替换成字段的实际值。</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <p><br>
      </p></td>
  </tr>
</table>
</body>
</html>
</sky:init>
