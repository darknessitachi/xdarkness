<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function add(){
	var diag = new Dialog("Diag1_1");
	diag.Width = 350;
	diag.Height = 300;
	diag.Title = "新增地址";
	diag.URL = "DataService/MemberAddrDialog.jsp?UserName="+$V("UserName");
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.xdarkness.cms.dataservice.MemberAddr.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function display(AddrID){
	var diag = new Dialog("Diag1_2");
	diag.Width = 600;
	diag.Height = 150;
	diag.Title = "查看详细地址信息";
	diag.URL = "DataService/MemberAddrDisplay.jsp?ID="+AddrID+"&UserName="+$V("UserName");
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function edit(){
    var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的地址！");
		return;
	}
	var diag = new Dialog("Diag1_2");
	diag.Width = 350;
	diag.Height = 300;
	diag.Title = "地址信息修改";
	diag.URL = "DataService/MemberAddrDialog.jsp?ID="+arr[0]+"&UserName="+$V("UserName");
	diag.OKEvent = addSave;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除选中的地址吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.cms.dataservice.MemberAddr.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
} 

function setDefault(ID,UserName){
	if(!ID||ID==""){
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",ID);
	dc.add("UserName",UserName);
	Server.sendRequest("com.xdarkness.cms.dataservice.MemberAddr.setDefault",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataGrid.loadData('dg1');
		}
	});
}
</script>
</head>
<sky:init>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd">
                    <div style="float:left; padding-right:20px;"><img src="../Icons/icon043a1.gif" />地址列表</div>
                    <sky:button onClick="add()"><img src="../Icons/icon043a2.gif" />新建</sky:button> 
                    <sky:button onClick="edit()"><img src="../Icons/icon043a4.gif" />修改</sky:button> 
                    <sky:button onClick="del()"><img src="../Icons/icon043a3.gif" />删除</sky:button>
                    <input type="hidden" id="UserName" name="UserName" value="${UserName}" />
                    </td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.cms.dataservice.MemberAddr.dg1DataBind" size="8">
						<table width="99%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
                            	<td width="2%">&nbsp;</td>
								<td width="3%" xtype="selector" field="ID">&nbsp;</td>
								<td width="18%"><b>收货人姓名</b></td>
								<td width="11%"><b>城市</b></td>
                                <td width="11%"><b>邮编</b></td>
                                <td width="20%"><b>手机</b></td>
                                <td width="25%"><b>电子邮件</b></td>
                                <td width="10%"><b>是否默认</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" onDblClick="display(${ID})">
                            	<td width="2%">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${RealName}</td>
								<td>${CityName}</td>
								<td>${ZipCode}</td>
								<td>${Mobile}</td>
								<td>${Email}</td>
								<td>${IsDefaultName}</td>
							</tr>
							<tr xtype="pagebar">
								<td height="25" colspan="9">${PageBar}</td>
							</tr>
						</table>
					</sky:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</sky:init>
</html>
