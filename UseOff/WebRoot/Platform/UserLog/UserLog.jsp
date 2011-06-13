<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.platform.page.UserLogPage.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户日志</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>
function doSearch(){
	var name = "";
	name = $V("SearchUser").trim();
	var ip = "";
	ip = $V("IP").trim();
	var logMessage = "";
	logMessage = $V("LogMessage").trim();
	var startDate = "";
	startDate = $V("StartDate").trim();
	var endDate = "";
	endDate = $V("EndDate").trim();
	var logType = "";
	logType = $V("LogType").trim();
	var subType = "";
	subType = $V("SubType").trim();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUser",name);
	DataGrid.setParam("dg1","IP",ip);
	DataGrid.setParam("dg1","LogType",logType);
	DataGrid.setParam("dg1","SubType",subType);
	DataGrid.setParam("dg1","LogMessage",logMessage);
	DataGrid.setParam("dg1","StartDate",startDate);
	DataGrid.setParam("dg1","EndDate",endDate);
	DataGrid.loadData("dg1");
}

function changeLogType(){
	$("SubType").setParam("LogType",$V("LogType"));
	$("SubType").loadData();
}

function del(){
	var dc = new DataCollection();
	dc.add("month",$V("month"));
	Server.sendRequest("com.xdarkness.platform.page.UserLogPage.delByTime",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});	
}

function browse(logID,userName){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "详细消息";
	diag.URL = "Platform/UserLogDialog.jsp?LogID="+logID+"&UserName="+userName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.platform.page.UserLogPage.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../../Icons/icon018a1.gif" />用户日志				
				</td>
			</tr>
			<tr><td  style="padding-top:0px">
				从&nbsp;<input value="" type="text" id="StartDate" size="14" name="StartDate" xtype="Date"  class="inputText" verify="NotNull">
				至&nbsp;<input value="${Time}" type="text" id="EndDate" size="14" name="EndDate" xtype="Date"  class="inputText" verify="NotNull" >				
				IP：<input type="text" name="IP" id="IP" value="" style="width:65px">
				类型：<sky:select id="LogType" style="width:70px" onChange="changeLogType()">${LogType}</sky:select>
				子类型：<sky:select id="SubType" style="width:70px"  method="com.xdarkness.platform.page.UserLogPage.getSubType" lazy="true"></sky:select>
			</td>
			</tr>
			<tr>
			  <td style="padding-top:0px">用户名：<input type="text" name="SearchUser" id="SearchUser"  value="" style="width:60px">消息内容：
				<input type="text" name="LogMessage" id="LogMessage" value="" style="width:80px">
				<input type="button" name="Submit" value="查询" onClick="doSearch()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<sky:select>
				<select name="select" id="month">
                  <option value="3">3个月之前 </option>
                  <option value="6">6个月之前 </option>
                  <option value="12">12个月之前 </option>
                  <option value="24">24个月之前 </option>
                </select>
				</sky:select>
				<input name="button" type="button" onclick="del()" value="删除"></td>
			</tr>
			<tr><td style="padding:0px 5px;">
				<sky:datagrid id="dg1" method="com.xdarkness.platform.page.UserLogPage.dg1DataBind" page="true" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="3%" align="center" xtype="selector" field="LogID">&nbsp;</td>
							<td width="8%"><b>用户名</b></td>
							<td width="8%"><b>姓名</b></td>
							<td width="16%"><strong>时间</strong></td>
							<td width="10%"><strong>消息类型</strong></td>
							<td width="39%"><strong>消息</strong></td>
							<td width="16%"><strong>IP</strong></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td align="center">&nbsp;</td>
							<td>${UserName}</td>
							<td>${RealName}</td>
							<td>${AddTime}</td>
							<td>${LogTypeName}</td>
							<td title="${LogMessage}"><a href="#" onclick="browse('${LogID}','${UserName}');">${LogMessage}</a></td>
							<td>${IP}</td>
						</tr>
						<tr xtype="pagebar">
							<td colspan="8">${PageBar}</td>
						</tr>
					</table>
				</sky:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</sky:init>
</body>
</html>
</sky:init>
