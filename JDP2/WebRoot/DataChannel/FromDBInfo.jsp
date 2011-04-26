<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.datachannel.FromDB.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var initFlag = true;
function loadTables(tableName){//初始化时dr有值
	if(initFlag&&$V("ID")){
		initFlag = false;
		return;//编辑时第一次onchange时不执行
	}
	if(!$V("DatabaseID")){
		$("TableName").clear();
		return;
	}
	var dc = new DataCollection();
	dc.add("DatabaseID",$V("DatabaseID"));
	Dialog.wait("正在尝试获取数据库中数据表信息...");
	Server.sendRequest("com.xdarkness.cms.dataservice.OuterDatabase.getTables",dc,function(response){
		Dialog.endWait();
		if(!tableName){
			Dialog.alert(response.Message);
		}
		if(dc.Status!=0){
			$("TableName").clear();
			var arr = response.get("Tables");
			var t = $("TableName");
			var options = [];
			for(var i=0;arr&&i<arr.length;i++){
				options.push([arr[i],arr[i]]);
			}
			t.addBatch(options);
			if(tableName){
				$S(t,tableName);
			}
			window.Init = false;
		}
	});
}

function onTableChange(){
	if(window.Init){
		window.Init = false;
		return;
	}
	var url  = Server.ContextPath+"DataChannel/FromDBMapping.jsp?DatabaseID="+$V("DatabaseID")+"&TableName="+$V("TableName");
	window.parent.Tab.getChildTab("Column").contentWindow.location = url;
}

function conn(){
	var diag = new Dialog("DiagConn");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "新建外部连接";
	diag.URL = "DataService/OuterDatabase.jsp";
	diag.CancelEvent = function(){
		$("DatabaseID").loadData();
		$D.close();
	};
	diag.show();
	diag.OKButton.hide();
}

Page.onLoad(function(){
	window.Init = true;//初始化时不需要重新加载映射
	var CatalogID = "${CatalogID}";
	var CatalogName = "${CatalogName}";
	Selector.setValueEx("CatalogID",CatalogID,CatalogName);
	$S("DatabaseID","${DatabaseID}");
	$S("PathReplacePartOld","${PathReplacePartOld}");
	$S("PathReplacePartNew","${PathReplacePartNew}");
	$S("NewRecordRule","${NewRecordRule}");
	$S("Memo","${Memo}");
	if("${Status}"){
		$NS("Status","${Status}");
	}
	loadTables("${TableName}");
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form id="F1">
  <table width="760" align="center" cellpadding="2" cellspacing="0" id="TableLink">
    <tr>
      <td height="35" align="right" >采集到此样栏目：</td>
      <td height="35" class="tdgrey2"><sky:select id="CatalogID" style="width:220px"  listWidth="300" listHeight="300" verify="NotNull" listURL="Site/CatalogSelectList.jsp"></sky:select></td>
      </tr>
    <tr>
      <td width="21%" height="30" align="right" >任务名称：</td>
      <td width="79%">
	  <input name="Name" type="text" class="inputText" id="Name" style="width:220px" value="${Name}" verify="NotNull"/>
	  <input name="ID" type="hidden" id="ID" value="${ID}"></td>
      </tr>
    <tr>
      <td height="30"  width="21%" align="right">外部数据库连接：</td>
      <td width="79%">
		  <sky:select id="DatabaseID" onChange="loadTables();" method="com.xdarkness.cms.dataservice.OuterDatabase.getDatabases" style="width:220px" verify="NotNull" defaultblank="true" value="${DatabaseID}">		  </sky:select>	  
			<sky:button onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />新建外部连接</sky:button>		</td>
      </tr>
    <tr>
      <td height="30" align="right">选择数据表：</td>
      <td><sky:select id="TableName" style="width:220px;" verify="NotNull" onChange="onTableChange();"> </sky:select></td>
      </tr>
    <tr>
      <td height="30" align="right" >任务状态：</td>
      <td><span class="tdgrey2">
        
          <input name="Status" type="radio" id="StatusY" value="Y" checked>
          <label for="StatusY">启用</label>
          <input type="radio" name="Status" value="N" id="StatusN">
          <label for="StatusN">停用</label>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集后的文章状态：<sky:select id="ArticleStatus"> ${ArticleStatus}</sky:select>
      </span></td>
    </tr>
    <tr>
      <td height="30" align="right" >文件路径替换规则：</td>
      <td>将　　
        <input name="PathReplacePartOld" type="text" class="inputText" id="PathReplacePartOld" value="${PathReplacePartOld}" size=30/></td>
      </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td>替换为<span class="gray">
        <input name="PathReplacePartNew" type="text" class="inputText" id="PathReplacePartNew" value="${PathReplacePartNew}" size=30/>
      </span></td>
    </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td class="gray">如果采集过来文章内容含有图片、附件，则需要使用操作系统或其他软件的文件同步功能，将远程服务器上的文件采集到指定目录下，然后将文章内容中的图片路径替换成同步后存放的路径。</td>
      </tr>
    <tr>
      <td height="30" align="right" >采集条件：</td>
      <td><input name="SQLCondition" type="text" class="inputText" id="SQLCondition" value="${SQLCondition}" size=40/>
        <span class="gray">        即SQL中where关键字之后的查询条件</span></td>
    </tr>
    <tr>
      <td height="30" align="right" >记录查新规则：</td>
      <td><input name="NewRecordRule" type="text" class="inputText" id="NewRecordRule" value="${NewRecordRule}" size=40/></td>
      </tr>
    <tr>
      <td height="30" align="right" >&nbsp;</td>
      <td><p class="gray">记录查新规则是为了达到每次采集只采集变动过的数据而设置的查询条件。<strong>ID&gt;${Max(ID)}</strong>表示只采集远程数据为中ID字段值大于已经采集过的数据中ID字段最大值的记录。条件之间可以用<strong>          Or
            </strong>和<strong> And </strong>相连，便如<strong>AddTime&gt;${Max(AddTime)} or ModifyTime&gt;${Max(ModifyTime)} </strong></p>        </td>
      </tr>
    <tr>
      <td height="30" align="right" >备注：</td>
      <td><input name="Memo" type="text" class="inputText" id="Memo" size=60/></td>
      </tr>
</table>
</form>
</body>
</html>
</sky:init>
