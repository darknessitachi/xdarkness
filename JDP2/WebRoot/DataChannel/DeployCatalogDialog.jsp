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
function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "添加栏目";
	diag.URL = "DataChannel/AddCatalogDialog.jsp?Type=Deploy";
	diag.onLoad = function(){
		$DW.getCatalogInfo();
	};
	diag.OKEvent = addSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function addSave(){
	var dt = $("dg1").DataSource;
	var addr = $DW.$V("ServerAddr");
	var site = $DW.$V("SiteID");
	var catalog = $DW.$V("CatalogID");
	if(addr=='localhost'&&site==$V("SiteID")&&catalog==$V("CatalogID")){
		Dialog.alert("不能分发到栏目自身!");
		return;
	}
	dt.insertRow(dt.Rows.length,[addr,site,$DW.$("SiteID").getText(),catalog,$DW.$("CatalogID").getText(),$DW.$V("Password")]);
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
	$D.close();
}

function edit(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("请先选择一行记录");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "修改栏目";
	diag.URL = "DataChannel/AddCatalogDialog.jsp";
	diag.onLoad = function(){
		$DW.$S("ServerAddr",dt.Rows[0].get("ServerAddr"));
		$DW.$S("Password",dt.Rows[0].get("Password"));
		$DW.getSiteInfo(function(){
			$DW.$S("SiteID",dt.Rows[0].get("SiteID"));
			$DW.getCatalogInfo(function(){
				$DW.$S("CatalogID",dt.Rows[0].get("CatalogID"));
			});
		});
	};
	diag.OKEvent = editSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function editSave(){
	var i = $("dg1").getSelectedRows()[0].rowIndex-1;
	var dt = $("dg1").DataSource;
	dt.deleteRow(i);
	dt.insertRow(i,[$DW.$V("ServerAddr"),$DW.$V("SiteID"),$DW.$("SiteID").getText(),$DW.$V("CatalogID"),$DW.$("CatalogID").getText(),$DW.$V("Password")]);	
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
	$D.close();
}

function del(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("请先选择一行记录");
		return;
	}
	var i = $("dg1").getSelectedRows()[0].rowIndex-1;
	var dt = $("dg1").DataSource;
	dt.deleteRow(i);
	$("dg1").setParam("Data",dt);
	$("dg1").loadData();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.datachannel.DeployCatalog.init">
<form id="form2">
<table width="780" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td width="151" height="10" class="tdgrey2">
	  <input type="hidden" id="ID" value="${ID}">
	  <input type="hidden" id="SiteID" value="${SiteID}">
	  </td>
		<td width="209" class="tdgrey2"></td>
	    <td width="151" class="tdgrey2"></td>
	    <td width="251" class="tdgrey2"></td>
	</tr>
	<tr>
	  <td height="35" align="right" class="tdgrey1">分发任务名称：</td>
	  <td height="35" class="tdgrey2"><input name="Name" type="text" id="Name" value="${Name}" style="width:150px" verify="NotNull"></td>
	  <td height="35" align="right" class="tdgrey1">从此栏目分发：</td>
	  <td height="35" class="tdgrey2"><sky:select id="CatalogID" listWidth="300" listHeight="300" verify="NotNull" listURL="Site/CatalogSelectList.jsp"> </sky:select></td>
	</tr>
	<tr>
	  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">同步文章修改/删除：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncArticleModify}</td>
		<td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">初次分发后目标文章状态：</td>
		<td bordercolor="#eeeeee" class="tdgrey2"><sky:select id="AfterSyncStatus">${AfterSyncStatus}</sky:select></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">同步栏目添加：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncCatalogInsert}</td>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">再次分发后目标文章状态：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2"><sky:select id="AfterModifyStatus">${AfterModifyStatus}</sky:select></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">同步栏目修改：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${SyncCatalogModify}</td>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">分发类型：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">${DeployTypeOptions}</td>
	</tr>
	<tr>
	  <td height="35" align="right" valign="middle" class="tdgrey1">目标栏目列表：</td>
	  <td height="35" class="tdgrey2">				
	    <sky:button onClick="add()"><img src="../Icons/icon007a2.gif" width="20" height="20" />添加</sky:button> 
		<sky:button onClick="edit()"><img src="../Icons/icon007a4.gif" width="20" height="20" />修改</sky:button> 
		<sky:button onClick="del()"><img src="../Icons/icon007a3.gif" width="20" height="20" />删除</sky:button>		</td>
      <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">任务状态：</td>
      <td height="35" class="tdgrey2">
	  	<input name="Status" type="radio" id="StatusY" value="Y" checked>
        <label for="StatusY">启用</label>
        <input type="radio" name="Status" value="N" id="StatusN">
        <label for="StatusN">停用</label>
          <script>Page.onLoad(function(){if("${Status}")$NS("Status","${Status}")});</script>
     </td>
	</tr>
	<tr>
	  <td height="35" colspan="4" align="center" class="tdgrey2"><sky:datagrid id="dg1" multiSelect="false" method="com.xdarkness.datachannel.DeployCatalog.dialogDataBind" page="false" size="8">
        <table width="700" cellpadding="2" cellspacing="0" class="dataTable">
          <tr xtype="head" class="dataTableHead">
            <td width="7%" xtype="Selector" multi='false' field="_RowNo"></td>
            <td width="33%"><b>站点名称</b></td>
            <td width="20%"><b>栏目名称</b></td>
            <td width="40%"><b>服务器地址</b></td>
          </tr>
          <tr onDblClick='edit(this);' style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
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
</sky:init>
</body>
</html>
