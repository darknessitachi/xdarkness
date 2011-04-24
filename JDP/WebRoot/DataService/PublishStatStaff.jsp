<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.cms.dataservice.PublishStaff.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>人员发布统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statStaff(){
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}
function statAll(){
	DataGrid.setParam("dg1","startDate","");
	DataGrid.setParam("dg1","endDate","");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

function exportStaff(){
	DataGrid.toExcel("dg1",1);
}

function statInputor(username){
	document.body.style.overflow="hidden";
	document.getElementById("InputorStat").src="PublishStatInputor.jsp?Username="+username;
	document.getElementById("InputorStat").style.display="block";
	document.getElementById("StaffStat").style.display="none";
}
</script>
</head>
<body>
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:300px">
						统计时间：从
						<input value="${today}" type="text" id="startDate" 
							name="startDate" xtype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" xtype="Date"  class="inputText" size="14" >
						</span>
						<sky:button onClick="statStaff()"> <img src="../Icons/icon031a1.gif" />统计</sky:button>
						<sky:button onClick="statAll()"> <img src="../Icons/icon031a15.gif" />全部统计</sky:button>
						<sky:button onClick="exportStaff()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</sky:button>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.cms.dataservice.PublishStaff.dg1DataBind">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="5%" xtype="RowNo"><b>序号</b></td>
								<td width="17%">机构</td>
								<td width="20%">录入用户</td>
								<td width="8%" ><b>初稿数</b></td>
								<td width="8%" ><b>流转数</b></td>
								<td width="8%" ><b>待发布数</b></td>
								<td width="8%" ><b>已发布数</b></td>
								<td width="8%" ><b>已下线数</b></td>
								<td width="8%" ><b>已归档数</b></td>
								<td width="8%" ><b>文章总数</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" >
								<td align="center">&nbsp;</td>
								<td>${BranchName}</td>
								<td><a href="#" onClick="statInputor('${UserName}')">${UserName}(${Inputor})</a></td>
								<td>${DraftCount}</td>
								<td>${WorkflowCount}</td>
								<td>${ToPublishCount}</td>
								<td>${PublishCount}</td>
								<td>${OfflineCount}</td>
								<td>${ArchiveCount}</td>
								<td>${ArticleCount}</td>
							</tr>
						</table>
					</sky:datagrid>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>
</html>
</sky:init>