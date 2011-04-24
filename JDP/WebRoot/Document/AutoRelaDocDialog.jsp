<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Array.prototype.unique = function(){  
 var  a = {};
 for(var i=0; i <this.length; i++){  
    if(typeof a[this[i]] == "undefined")  
      a[this[i]] = 1;  
 }  
 this.length = 0;  
 for(var i in a)  
    this[this.length] = i;  
 return this;  
}  

function add(){
	var diag  = new Dialog("Diag3");
	diag.Width = 830;
	diag.Height = 460;
	diag.Title ="浏览文章";
	diag.URL = "Document/DocListDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = getDocList;
	diag.show();
}

function getDocList(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请选择文档！");
		return;
	}
	var articleIDs =[];
	for(var i=0;i<drs.length;i++){
		var dr = drs[i];
		articleIDs.push(dr.get("ID"));
	}
	
	var allIDs;
  var oldArticleIDs = $V("ArticleIDs");
  if(oldArticleIDs != ""){
	  oldArticleIDs = oldArticleIDs.split(",");	
	  allIDs = oldArticleIDs.concat(articleIDs).unique();
  }else{
  	allIDs = articleIDs;
  }
	
	allIDs.remove($V("ArticleID"));
	
	$S("ArticleIDs",allIDs.join(","));

	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
	$D.close();
}

function deleteRow(ID){
	var articleIDs = $V("ArticleIDs");
	articleIDs = articleIDs.split(",");	
	articleIDs.remove(ID);
	$S("ArticleIDs",articleIDs.join(","));

	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
}

function del(){
  var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的文章！");
		return;
	}
	
	var articleIDs = $V("ArticleIDs");
	articleIDs = articleIDs.split(",");	
	for(var i = 0;i<arr.length;i++){
		articleIDs.remove(arr[i]);
	}
  
	$S("ArticleIDs",articleIDs.join(","));
	DataGrid.setParam("dg1","RelativeArticle",$V("ArticleIDs"));
  DataGrid.loadData("dg1");
}

function keywordRela(){
	
}
</script>
</head>
<body>
<input type="hidden" id="ArticleIDs"
	value="<%=request.getParameter("RelativeArticle")%>">
<input type="hidden" id="ArticleID"
	value="<%=request.getParameter("ArticleID")%>">
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a1.gif" />系统智能推荐的和本文档相关的文档列表</td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid	id="dg1" method="com.xdarkness.cms.document.Article.autoRelativeDataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="10%" height="30" xtype="RowNo">序号</td>
							<td width="10%" xtype="selector" field="id">&nbsp;</td>
							<td width="80%"><b>标题</b></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="15">&nbsp;</td>
							<td>&nbsp;</td>
							<td title="作者：${author} 发表时间：${addtime}">${Title}</td>
						</tr>
					</table>
				</sky:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
