<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var Type = "<%=request.getParameter("Type")%>";
function getSiteInfo(func){
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	Dialog.wait("请等待...");
	Server.sendRequest("com.xdarkness.datachannel.CatalogInfoService.getRemoteSiteInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("SiteTable");
		$("SiteID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
		   		$("SiteID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("SiteID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("获取数据失败!");	
		}
	});
}
function getCatalogInfo(func){
	if(!$V("SiteID")){
		return;
	}
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	dc.add("SiteID",$V("SiteID"));
	Dialog.wait("请等待...");
	Server.sendRequest("com.xdarkness.datachannel.CatalogInfoService.getRemoteCatalogInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("CatalogTable");		
		$("CatalogID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
			   $("CatalogID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("CatalogID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("获取数据失败!");	
		}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form2">
<table width="500" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td width="112" height="10" class="tdgrey2">
		<input type="hidden" id="Type" value="USER">
	  <input type="hidden" id="ID" value="">		</td>
		<td width="378" class="tdgrey2"></td>
    </tr>
	<tr>
	  <td height="35" align="right" class="tdgrey1">服务器地址：</td>
	  <td height="35" class="tdgrey2"><input name="ServerAddr" type="text" verify="NotNull" id="ServerAddr" value="localhost" style="width:250px"></td>
    </tr>
	<tr>
	  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">选择站点：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">
	  <sky:select id="SiteID" verify="NotNull" listHeight="300" onChange="getCatalogInfo()" style="width:250px" method="com.xdarkness.datachannel.CatalogInfoService.getLocalSites"> </sky:select>
	  <input type="button" name="Submit" value="获取站点信息" onClick="getSiteInfo()"></td>
	</tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">选择栏目：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2">
	  <sky:select id="CatalogID" verify="NotNull" listHeight="300" style="width:250px"> </sky:select> 
	  <input type="button" name="Submit2" value="获取栏目信息" onClick="getCatalogInfo()"></td>
    </tr>
	<tr>
	  <td height="28" align="right" bordercolor="#eeeeee" class="tdgrey1">密钥：</td>
	  <td bordercolor="#eeeeee" class="tdgrey2"><input name="Password" type="text" id="Password" style="width:250px"></td>
    </tr>
</table>
</form>
</body>
</html>
