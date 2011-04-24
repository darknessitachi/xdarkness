<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onTypeChange(){
	if($NV("Type")=="Custom"){
		$("TableLink").hide();
		parent.$("Column").show();
	}else{
		$("TableLink").show();
		parent.$("Column").hide();
	}
}

function onServerTypeChange(){
	var st = $V("ServerType");
	if(st=="ORACLE"){
		$S("Port","1521");
	}
	if(st=="DB2"){
		$S("Port","50000");
	}
	if(st.startWith("MSSQL")){
		$S("Port","1433");
	}
	if(st=="MYSQL"){
		$S("Port","3306");
	}
}
var initFlag = true;
function loadTables(dr){//初始化时dr有值
	if(initFlag&&$V("ID")){
		initFlag = false;
		return;//编辑时第一次onchange时不执行
	}
	if(!$V("DatabaseID")){
		$("OldCode").clear();
		return;
	}
	var dc = new DataCollection();
	dc.add("DatabaseID",$V("DatabaseID"));
	Dialog.wait("正在尝试获取数据库中数据表信息...");
	Server.sendRequest("com.xdarkness.cms.dataservice.OuterDatabase.getTables",dc,function(response){
		Dialog.endWait();
		if(!dr){
			Dialog.alert(response.Message);
		}
		if(dc.Status!=0){
			$("OldCode").clear();
			var arr = response.get("Tables");
			var t = $("OldCode");
			var options = [];
			for(var i=0;arr&&i<arr.length;i++){
				options.push([arr[i],arr[i]]);
			}
			t.addBatch(options);
			if(dr){
				$S(t,dr.get("OldCode"));
			}
		}
	});
}

function conn(){
	var diag = new Dialog("DiagConn");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "新建外部连接";
	diag.URL = "DataService/OuterDatabase.jsp";
	diag.CancelEvent = function(){
		$("DatabaseID").loadData();
		$D.close();
	};
	diag.show();
	diag.OKButton.hide();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<sky:init method="com.xdarkness.cms.dataservice.CustomTable.init">
<form id="F1">
<table width="500" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td width="23%" height="30" align="right" >表类型：</td>
      <td width="77%"><input type="radio" name="Type" id="TypeCustom" value="Custom" checked onClick="onTypeChange()">
          <label for="TypeCustom">自定义表</label>
          <input type="radio" name="Type" id="TypeLink"  value="Link" onClick="onTypeChange()">
          <label for="TypeLink">外部数据库挂载表
          <input name="ID" type="hidden" id="ID">
          </label></td>
    </tr>
    <tr>
      <td colspan="2" align="center"  height="5"></td>
    </tr>
</table>
<table width="500" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="30" align="right" >表代码：</td>
      <td><input name="Code" type="text" class="inputText" id="Code" size=40 verify="NotNull"/></td>
    </tr>
    <tr>
      <td width="23%" height="30" align="right" >表名称：</td>
      <td width="77%">
	  <input name="Name" type="text" class="inputText" id="Name" size=40 verify="NotNull"/>	  </td>
    </tr>
    <tr>
      <td height="30" align="right" >选项：</td>
      <td><input name="AllowView" type="checkbox" id="AllowView" value="Y" checked>
        <label for="AllowView">允许前台查看</label>
		<input name="AllowModify" type="checkbox" id="AllowModify" value="Y" checked>
        <label for="AllowModify">允许前台提交数据</label></td>
    </tr>
    <tr>
      <td height="30" align="right" >备注：</td>
      <td><input name="Memo" type="text" class="inputText" id="Memo" size=60/></td>
    </tr>
</table>
<table width="500" align="center" cellpadding="2" cellspacing="0" style="display:none" id="TableLink">
    <tr>
      <td height="30"  width="23%" align="right">外部数据库连接：</td>
      <td width="77%">
	  <sky:select id="DatabaseID" method="com.xdarkness.cms.dataservice.OuterDatabase.getDatabases" onChange="loadTables();" style="width:225px" verify="NotNull"  condition="$NV('Type')=='Link'" defaultblank="true">
	  </sky:select>
	  <sky:button onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />新建外部连接</sky:button>
	  </td>
    </tr>
    <tr>
      <td height="30" align="right">选择表：</td>
      <td><sky:select id="OldCode" style="width:225px" verify="NotNull"  condition="$NV('Type')=='Link'"></sky:select></td>
    </tr>
</table>
</form>
</sky:init>
</body>
</html>
