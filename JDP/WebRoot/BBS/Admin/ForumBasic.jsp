<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
	function userList(){
		var diag = new Dialog("Diag2");
		diag.Width = 750;
		diag.Height =400;
		diag.Title = "选择用户";
		diag.URL = "BBS/Admin/ForumUserInfo.jsp";
		diag.OKEvent = editSave;
		diag.show();
	}
	function editSave(){
		var arr = $DW.DataGrid.getSelectedValue("dg1");
		$("ForumAdmin").innerHTML = arr.join();
		$D.close();
	}
	function groupListUnLock(){
		var diag = new Dialog("Diag2");
		diag.Width = 350;
		diag.Height =400;
		diag.Title = "选择组";
		diag.URL = "BBS/Admin/ForumGroupDialog.jsp";
		diag.OKEvent = groupSaveUnLock;
		diag.show();
	}
	function groupSaveUnLock(){
		var arr = $DW.DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			arr=[0];
		}
		$S("UnLockID",arr.join());
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.bbs.admin.ForumOption.saveGroup",dc,function(response){
			$D.close();
			$("GroupUnLock").innerHTML = response.get("Group");
		});
	}

	function groupListUnPassword(){
		var diag = new Dialog("Diag2");
		diag.Width = 350;
		diag.Height =400;
		diag.Title = "选择组";
		diag.URL = "BBS/Admin/ForumGroupDialog.jsp";
		diag.OKEvent = groupSaveUnPassword;
		diag.show();
	}
	function groupSaveUnPassword(){
		var arr = $DW.DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			arr=[0];
		}
		$S("UnPasswordID",arr.join());
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.bbs.admin.ForumOption.saveGroup",dc,function(response){
			$D.close();
			$("GroupUnPassword").innerHTML = response.get("Group");
		});
	}
	function isShow(){
		if($NV("Locked")=="N"){
			$("UnlockGroup").hide();
		}else{
			$("UnlockGroup").show();
		}
		if($V("Password").trim().length==0){
			$("UnPasswordGroup").hide();
		}else{
			$("UnPasswordGroup").show();
		}
	}
	Page.onLoad(isShow);
</script>
</head>
<sky:init method="com.xdarkness.bbs.admin.ForumOption.basicInit">
<body>
<form id="form1" >
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<input type="hidden" value="${ID}" id="ID" >
				<tr>
					<td width="100" align="right">板块名称:</td>
					<td><input type="text" id="Name" verify="NotNull" value="${Name}"></td>
				</tr>
				<tr>
					<td align="right">上级分区:</td>
					<td><sky:select name="ParentID" id="ParentID"
						style="width:100px;">${ParentForum}</sky:select>
					</td>
				</tr>
				<tr>
					<td align="right">设定版主:</td>
					<td><img onclick="userList()" src="../../Icons/icon022a16.gif">&nbsp;&nbsp;<span id="ForumAdmin">${ForumAdmin}</span></td>
				</tr>
				<tr>
					<td align="right">显示板块:</td>
					<td>${Visible}</td>
				</tr>
				<tr>
					<td align="right">锁定板块:</td>
					<td><input type="radio" value="Y" ${checkY} name="Locked" id="Locked" onclick="isShow()">锁定
					<input type="radio" value="N" ${checkN} name="Locked" id="Locked" onclick="isShow()">开启</td>
				</tr>
				<tr id="UnlockGroup"><td>锁定对下列组无效</td><td><img onclick="groupListUnLock()" src="../../Icons/icon022a16.gif">&nbsp;&nbsp;<span id="GroupUnLock">${GroupUnLock}</span>
				<input type="hidden" value="${UnLockID}" id="UnLockID"/></td></tr>
				
				<tr>
					<td width="100" align="right">访问密码:</td>
					<td><input type="password" id="Password"  name="Password" value="${Password}" onblur="isShow()">
				</tr>
				<tr id="UnPasswordGroup"><td>密码对下列组无效</td><td><img onclick="groupListUnPassword()" src="../../Icons/icon022a16.gif">&nbsp;&nbsp;<span id="GroupUnPassword">${GroupUnPassword}</span>
				<input type="hidden" value="${UnPasswordID}" id="UnPasswordID"/></td></tr>
				<!--tr>
					<td width="100" align="right">URL:</td>
					<td><input type="text" name="URL" id="URL" value="${URL}">
				</tr>
				<tr>
					<td width="100" align="right">图片地址:</td>
					<td><input type="text" id="Image" value="${Image}">
				</tr>
				<tr>
					<td width="100" align="right">关键字:</td>
					<td><input type="text" id="Work" value="${Word}">
				</tr-->
				<tr>
					<td width="100" align="right">板块简介:</td>
					<td align="left"><textarea name="Info" style="height:80px; width:300px;"
							id="Info"/>${Info}</textarea></td>
				</tr>
			</table>
			</td>
			
		</tr>
	</table>
</form>
</body>
</sky:init>
</html>