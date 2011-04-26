<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>
<script>


function searchAll(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","UserGroup","");
	DataGrid.setParam("dg1","LowerScore","");
	DataGrid.setParam("dg1","UpperScore","");
	DataGrid.setParam("dg1","UserSystemGroup","");
	DataGrid.setParam("dg1","UserDefinedGroup","");
	DataGrid.setParam("dg1","ForumUserName","");
	DataGrid.loadData("dg1");
}

function doSearch(){
	if(Verify.hasError()){
			return;
	}
	var name = "";
	if ($V("ForumUserName") != "查询用户名") {
		name = $V("ForumUserName").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","UserGroup",$V("UserGroup"));
	DataGrid.setParam("dg1","UserSystemGroup",$V("UserSystemGroup"));
	DataGrid.setParam("dg1","UserDefinedGroup",$V("UserDefinedGroup"));
	DataGrid.setParam("dg1","LowerScore",$V("LowerScore"));
	DataGrid.setParam("dg1","UpperScore",$V("UpperScore"));
	DataGrid.setParam("dg1","ForumUserName",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'ForumUserName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}


function delKeyWord() {
	if ($V("ForumUserName") == "查询用户名") {
		$S("ForumUserName","");
	}
}
 function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	dr = drs[0]; 
	var diag = new Dialog("Diag3");
	diag.Width = 450;
	diag.Height =230;
	diag.Title = "编辑用户";
	diag.URL = "BBS/Admin/ForumUserEditDialog.jsp?UserName="+dr.get("UserName");
	diag.OKEvent = editSave;
	diag.show();
}
function editSave(){
	var dc = Form.getData($DW.$F("form1"));
	if($DW.Verify.hasError()){
		return;
	}
	if($DW.$V("AdminID")==$DW.$V("ForumAdminID")){
		if($DW.$("Forums").innerHTML.length==0){
			Dialog.alert("所属板块为空,请到板块管理下设置相应的版主");
			return;
		}
	}
	Server.sendRequest("com.xdarkness.bbs.admin.ForumUserInfo.editUserGroup",dc,function(response){
		Dialog.alert(response.Message, function() {
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}
/*function save(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请双击修改后再进行保存！");
		return;
	}
	var dc = new DataCollection();
	dc.add("UserName",$V("UserName"));
	// alert(dc.get("UserName"));
	dc.add("Member",$V("MemberGroup"));
	// alert(dc.get("Member"));
	dc.add("System",$V("SystemGroup"));
	dc.add("Special",$V("SpecialGroup"));
	dc.add("ThemeCount",$V("ThemeCount"));
	dc.add("ForumScore",$V("ForumScore"));
	Server.sendRequest("com.xdarkness.bbs.ForumUserInfo.editSave",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			DataGrid.loadData('dg1');
		}
	});
}*/



</script>
</head>
<body>
<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../../Icons/icon018a6.gif" />论坛用户列表</td>
            </tr>
            <tr nowrap="nowrap">
            <sky:init method="com.xdarkness.bbs.admin.ForumUserInfo.init">
              	  	<td >
		                  <div style="float:left; white-space: nowrap;">
		                  <sky:select id="UserGroup" style="width:100px;"><span value="0">选择组积分组</span>${UserGroup}</sky:select>
		                  <sky:select id="UserSystemGroup" style="width:100px;"><span value="0">选择组系统组</span>${UserSystemGroup}</sky:select>
		                  <sky:select id="UserDefinedGroup" style="width:100px;"><span value="0">选择组自定义组</span>${UserDefinedGroup}</sky:select>
		                  积分介于：<input type="text" value="" id="LowerScore" verify="Int" size="7">~<input type="text" value="" id="UpperScore" verify="Int" size="7" />
		                 
		             	  用户名：<input name="ForumUserName" type="text" id="ForumUserName" value="查询用户名" size="15" onFocus="delKeyWord();" />
		            	  </div>
		             	  <sky:button onClick="doSearch()">
		             	  <img src="../../Icons/icon022a16.gif" width="20" height="20" />查询</sky:button> 
			 		</td>
			 </sky:init>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <sky:datagrid id="dg1" method="com.xdarkness.bbs.admin.ForumUserInfo.getList" size="15">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr xtype="head" class="dataTableHead">
  				    <td width="5%" xtype="RowNo"><strong>序号</strong></td>
                    <td width="3%" xtype="selector" field="UserName">&nbsp;</td>
                    <td width="9%"><b>用户名</b></td>
                    <td width="15%"><b>用户组</b></td>
                    <td width="15%"><b>管理组</b></td>
                    <td width="15%"><b>自定义组</b></td>
                    <td width="10%"><b>发帖数</b></td>
                    <td width="5%"><b>积分</b></td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" ondblclick="edit()">        
					<td width="6%">&nbsp;</td>            
                    <td>&nbsp;</td>
                    <td>${UserName}</td>
                    <td>${Name}</td>
                    <td>${AdminName}</td>
                    <td>${DefinedName}</td>
                    <td>${ThemeCount}</td>
                    <td>${ForumScore}</td>
                  </tr>  
                             
                  <tr xtype="pagebar">
                    <td colspan="10">${PageBar}</td>
                  </tr>
                </table>
              </sky:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>

</body>
</html>

