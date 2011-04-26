<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>栏目发布统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function statColumn(){
	DataGrid.setParam("dg2","startDate",$V("startDate"));
	DataGrid.setParam("dg2","endDate",$V("endDate"));
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function statColumnAll(){
	DataGrid.setParam("dg2","startDate","");
	DataGrid.setParam("dg2","endDate","");
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.loadData("dg2");
}

function exportColumn(){
	DataGrid.toExcel("dg2",0);
}
</script>
</head>
<body>
	<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<sky:init method="com.xdarkness.cms.dataservice.PublishColumn.initSearch">
						<span style="float: left;width:300px">
						统计时间：从
						<input value="${today}" type="text" id="startDate" 
							name="startDate" xtype="Date"  class="inputText" size="14" >
						到<input value="${today}" type="text" id="endDate" 
							name="endDate" xtype="Date"  class="inputText" size="14" >
						</span>
						
						<sky:button onClick="statColumn()"> <img src="../Icons/icon031a1.gif" />统计</sky:button>
						<sky:button onClick="statColumnAll()"> <img src="../Icons/icon031a15.gif" />全部统计</sky:button>
						<sky:button onClick="exportColumn()"> <img src="../Icons/icon031a7.gif" />导出EXCEL</sky:button>
						
						</sky:init>
					</td>
				</tr>
				
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg2" method="com.xdarkness.cms.dataservice.PublishStaff.dg11DataBind" page="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="4%" xtype="RowNo"><b>序号</b></td>
								<td width="7%">栏目ID</td>
								<td width="33%"  xtype="tree" level="treelevel" >栏目名称</td>
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
								<td >${ID}</td>
								<td >${CatalogName}</td>
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
</body>

</html>
