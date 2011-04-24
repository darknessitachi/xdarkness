<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>配送项目表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 return;
    }														
		dr.set("Name",$V("Name"));
		dr.set("SendInfo",$V("SendInfo"));
		dr.set("ArriveInfo",$V("ArriveInfo"));
		dr.set("Info",$V("Info"));
		dr.set("Price",$V("Price"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 350;
	diag.Title = "新建配送项";
	diag.URL = "Shop/SendTypeDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.MessageTitle = "修改信息";
	diag.Message = "修改配送说明、费用等";
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if( $DW.$V("Name").indexOf(",")!=-1 || $DW.$V("Name").indexOf("^")!=-1){
		alert("类别和名称中含有不规则字符\",\^\"");
		return;
	}
	Server.sendRequest("com.xdarkness.shop.SendType.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			window.location.reload();
		}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该项吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.SendType.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
		},function(){
		return;
	} );
	}

function edit(){
  var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的选项！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 350;
	diag.Title = "配送信息修改";
	diag.URL = "Shop/SendTypeDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "配送修改信息";
	diag.Message = "修改配送说明，费用等";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("ID",$DW.$V("ID"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.shop.SendType.dg1Edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}
</script>
</head>
<%
String path = (Config.getContextPath() + Config.getValue("UploadDir") + "/" 
	+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/").replaceAll("//","/");
%>
<body>
<form id="form2">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon043a12.gif" />配送方式表</td>
			</tr>
			<tr>
				<td style="padding:8px 10px;"><sky:button onClick="add()">
					<img src="../Icons/icon043a2.gif" />新建</sky:button> <sky:button onClick="edit()">
					<img src="../Icons/icon043a4.gif" />修改</sky:button> <sky:button onClick="del()">
					<img src="../Icons/icon043a3.gif" />删除</sky:button> </td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.shop.SendType.dg1DataBind"
					size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="4%" xtype="RowNo"><b>序号</b></td>
							<td width="4%" xtype="selector" field="id">&nbsp;</td>
							<td width="10%">配送名称</td>
							<td width="13%" align="center">图片</td>
							<td width="9%">配送费用</td>
							<td width="20%">发货说明</td>
							<td width="20%">到货说明</td>
							<td width="20%">配送说明</td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td><span title="${Name}">${Name}</span></td>
							<td align="center" title="${Name}"><img src="<%=path%>${Prop1}" width="150" height="40" /></td>
							<td>${Price}</td>
							<td><span title="${SendInfo}">${SendInfo}</span></td>
							<td><span title="${ArriveInfo}">${ArriveInfo}</span></td>
							<td><span title="${Info}">${Info}</span></td>
						</tr>
						<tr xtype="pagebar">
							<td height="25" colspan="6">${PageBar}</td>
						</tr>
					</table>
				</sky:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
