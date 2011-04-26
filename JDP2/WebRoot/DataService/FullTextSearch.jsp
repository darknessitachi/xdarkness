<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 540;
	diag.Height = 400;
	diag.Title = "新建搜索类别";
	diag.URL = "DataService/FullTextSearchDialog.jsp";
	diag.onLoad = function(){
		$DW.onTypeChange();
	};
	diag.OKEvent = save;
	diag.show();
	diag.OKButton.value = "保存";
}

function save(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("F1");
	if(!$DW.$NV("Catalog")){
		Dialog.alert("没有选择任何栏目!");
		return;
	}
	dc.add("RelaText",$DW.$NV("Catalog"));
	Server.sendRequest("com.xdarkness.cms.dataservice.FullText.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			$D.close();
			DataGrid.loadData("dg1");
		});		
	});
}

function edit(dr){
	if(!dr){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt.getRowCount()==0){
			Dialog.alert("请先选择要修改的行!");
			return;
		}
		dr = dt.getDataRow(0);
	}
	var diag = new Dialog("Diag1");
	diag.Width = 540;
	diag.Height = 400;
	diag.Title = "修改搜索类别";
	diag.URL = "DataService/FullTextSearchDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.Form.setValue(dr,"F1");
		$DW.RelaStr = dr.get("RelaText");
		$DW.onTypeChange();
	};
	diag.OKEvent = save;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确认删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.cms.dataservice.FullText.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				DataGrid.loadData('dg1');
			});
		});
	})
}

function manual(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择行！");
		return;
	}
	Dialog.confirm("确认重新生成索引吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Dialog.wait("正在生成索引...");
		Server.sendRequest("com.xdarkness.cms.dataservice.FullText.manual",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message);
		});
	})
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon033a15.gif" width="20" height="20" /> 全文检索管理 </td>
            </tr>
				<tr>
					<td style="padding:0 8px 4px;"><sky:button onClick="add()"><img src="../Icons/icon033a2.gif" width="20" height="20"/>添加</sky:button>
				<sky:button onClick="edit()"><img src="../Icons/icon033a4.gif" width="20" height="20"/>修改</sky:button>
				<sky:button onClick="del()"><img src="../Icons/icon033a3.gif" width="20" height="20"/>删除</sky:button>
				<sky:button onClick="manual()"><img src="../Icons/icon033a16.gif" width="20" height="20"/>生成索引</sky:button>
			  </td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <sky:datagrid id="dg1" method="com.xdarkness.cms.dataservice.FullText.dg1DataBind" page="false">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                  <tr xtype="head" class="dataTableHead">
                    <td  width="2%" xtype="rowno">&nbsp;</td>
                    <td width="3%" xtype="selector" field="id">&nbsp;</td>
                    <td width="6%">ID</td>
                    <td width="12%">代码</td>
                    <td width="21%"><b>名称</b></td>
                    <td width="12%"><b>类型</b></td>
                    <td width="35%"><b>备注</b></td>
                  </tr>
                  <tr onDblClick="edit()" style1="background-color:#FFFFFF" style2="background-color:#F7F8FF">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${ID}</td>
                    <td>${Code}</td>
                    <td>${Name}</td>
                    <td>${TypeName}</td>
                    <td>${Memo}</td>
                  </tr>
                </table>
              </sky:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
