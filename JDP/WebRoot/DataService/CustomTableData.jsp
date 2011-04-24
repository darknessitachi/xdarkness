<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择列</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function save(){
	if(Verify.hasError()){
		return;
	}
	var inputs = $($("dg1").rows[1]).$T("input");
	var cols = [];
	var arrs = [];
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].type=="checkbox"){
			continue;
		}
		cols.push([inputs[i].name,1]);//1表示String类型
		arrs.push($NV(inputs[i].name));
	}
	if(inputs.length==0){
		return;
	}
	var data = [];
	for(var i=0;i<arrs[0].length;i++){
		var row = [];
		for(var j=0;j<cols.length;j++){
			row.push(arrs[j][i]);
		}
		data.push(row);
	}
	
	var dt = new DataTable();
	dt.init(cols,data);
	var dc = new DataCollection();
	dc.add("Data",dt);
	dc.add("ID",$V("_TableID"));
	Server.sendRequest("com.xdarkness.cms.dataservice.CustomTable.saveData",dc,function(response){
		Dialog.alert(response.Message,function(){
			$("dg1").loadData();
		});
	});
}

function del(){
	var dt = $("dg1").getSelectedData();
	if(dt.Rows.length==0){
		Dialog.alert("请先选择记录!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Data",dt);
	dc.add("ID",$V("_TableID"));
	Server.sendRequest("com.xdarkness.cms.dataservice.CustomTable.delData",dc,function(response){
		Dialog.alert(response.Message,function(){
			$("dg1").loadData();
		});
	});
}

function query(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "导入自定义数据";
	diag.URL = "DataService/CustomTableDataImportStep1.jsp";
	diag.OKEvent = function(){
		$DW.$("form1").submit();
		Dialog.wait("开始上传文件，请稍等....");
	}
	diag.onLoad = function(){
		$DW.$("Name").innerHTML = dr.get("Name");
	}
	diag.show();
}

function importData(){
	var diag = new Dialog("DialogImport");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "导入自定义数据";
	diag.URL = "DataService/CustomTableDataImportStep1.jsp?ID="+$V("_TableID");
	diag.OKEvent = function(){
		$DW.$("form1").submit();
		Dialog.wait("开始上传文件，请稍等....");
	}
	diag.show();
}

function executeImportData(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("_TableID"));
	dc.add("FileName",$DW.$V("FileName"));
	Dialog.wait("正在导入数据...");
	Server.sendRequest("com.xdarkness.cms.dataservice.CustomTable.importData",dc,function(response){
		Dialog.endWait();
		$D.close();
		Dialog.alert(response.Message);
		$("dg1").loadData();
	});
}

function exportData(){
	window.location = "CustomTableDataExport.jsp?ID="+$V("_TableID");
}

function changeMode(ele){
	$("trDataGrid").toggle();
	$("trDataList").toggle();
}

function addData(){
	var diag = new Dialog("DiagForm");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "自定义表单数据录入";
	diag.URL = "DataService/FormDataInput.jsp?_TableID="+$V("_TableID");
	diag.ShowButtonRow = false;
	diag.OKEvent = function(){
		$("dg1").loadData();
		$("dg2").loadData();
	}
	diag.show();
}

function refreshData(){
	$("dg1").loadData();
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.cms.dataservice.CustomTable.initDataEditGrid">
  <table width="100%" border="0" cellspacing="0" cellpadding="6">
    <tr>
      <td style="padding:4px 5px;">
	  <sky:button onClick="addData()"><img src="../Icons/icon005a2.gif" width="20" height="20"/>添加</sky:button>
	  <sky:button onClick="save()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>保存</sky:button>
	  <!--<sky:button onClick="query()"><img src="../Icons/icon005a15.gif" width="20" height="20"/>查询</sky:button>-->
	  <sky:button onClick="del()"><img src="../Icons/icon005a3.gif" width="20" height="20"/>删除</sky:button>
	  <sky:button onClick="refreshData()"><img src="../Icons/icon005a5.gif" width="20" height="20"/>刷新</sky:button>
	  <sky:button onClick="exportData()"><img src="../Icons/icon005a7.gif" width="20" height="20"/>导出数据</sky:button>
	  <sky:button onClick="importData()"><img src="../Icons/icon005a9.gif" width="20" height="20"/>导入数据</sky:button>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="checkbox" name="checkbox" value="checkbox" id="checkbox" onclick="changeMode(this)" ${checked} >
      <label for="checkbox">以表单方式浏览数据</label></td>
    </tr>
   <tr id="trDataGrid">
      <td style="padding:2px;">
	  	<input type="hidden" id="_TableID" value="${ID}">
		${HTML}
	  </td>
    </tr>
   <tr style="display:none" id="trDataList">
      <td align="center" style="padding:2px;">
	  <table width="700" height="100%" border="0">
        <tr>
          <td height="400">
		  <sky:datalist id="dg2" method="com.xdarkness.cms.dataservice.CustomTable.dataListDataBind" size="1" page="true">
			${HTML}
		</sky:datalist>		 </td>
        </tr>
        <tr>
          <td>
			<sky:pagebar target="dg2" afloat="true" />
		 </td>
        </tr>
      </table>
	  </td>
    </tr>
  </table>
</sky:init>
</body>
</html>
