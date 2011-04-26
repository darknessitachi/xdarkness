<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.cms.site.ImagePlayerBasic.init">
<html>
<head>
<%
	String catalogInnerCode = request.getParameter("RelaCatalog");
	String value = "";
	String text = "文档库";
	if (StringUtil.isNotEmpty(catalogInnerCode)&&!catalogInnerCode.equalsIgnoreCase("null")&&!catalogInnerCode.equals("0")) {
		value = CatalogUtil.getIDByInnerCode(catalogInnerCode);
		text = CatalogUtil.getNameByInnerCode(catalogInnerCode);
	}
%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>图片播放器</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
	var attr = $N("ImageSource");
	for (var i=0;i<attr.length;i++) {
		var ele = attr[i];
		$(ele).attachEvent("onchange",changeSource);
	}
});

function add(){
	var dc = Form.getData($F("form2"));
	if(Verify.hasError()){
		return;
	}
	dc.add("Prop1",$NV("Prop1"));
	Server.sendRequest("com.xdarkness.cms.site.ImagePlayerBasic.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.parent.Parent.DataGrid.loadData("dg1");
				if(window.parent.Parent.currentTreeItem){
					window.parent.Parent.Tree.loadData("tree1",function(){Tree.select("tree1","cid",window.parent.Parent.currentTreeItem.getAttribute("cid"))});
				}else{
					window.parent.Parent.Tree.loadData("tree1");
				}
				window.parent.location = Server.ContextPath+"Site/ImagePlayerDialog.jsp?"+response.get("ImagePlayerUrl");
			}
		});
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<form id="form2" method="post">
	<table width="100%"
		style="border-bottom:1px solid #DFE3EE; line-height:34px;">
		<tr>
			<td style="padding:4px 5px;"><sky:button onClick="add();">
				<img src="../Icons/icon039a16.gif" />保存</sky:button></td>
		</tr>
		<tr>
			<td style="padding:0px 5px;">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr class="dataTableHead">
					<td height="30" width="3%">&nbsp;</td>
					<td height="30" width="15%"><b>项目</b></td>
					<td height="30" width="82%"><b>值</b></td>
				</tr>
				<tr style="background-color:#FFFFFF">
					<td>&nbsp;</td>
					<td align="right">图片播放器名称：</td>
					<td><input name="Name" type="text" value="${Name}"
						class="input1" id="Name" verify="图片播放器名称|NotNull" /> <input
						name="ImagePlayerID" type="hidden" id="ImagePlayerID"
						value="${ImagePlayerID}" /></td>
				</tr>
				<tr style="background-color:#FFFFFF">
					<td>&nbsp;</td>
					<td align="right"><span style="text-align:right;">图片播放器代码：</span></td>
					<td><input name="Code" type="text" value="${Code}"
						class="input1" id="Code" verify="图片播放器代码|NotNull" /></td>
				</tr>
				<tr style="background-color:#FFFFFF">
					<td>&nbsp;</td>
					<td align="right"><span style="text-align:right;">宽度：</span></td>
					<td><input name="Width" type="text" value="${Width}"
						class="input1" id="Width" verify="宽度|NotNull&&Int" />像素(px)</td>
				</tr>
				<tr style="background-color:#FFFFFF">
					<td>&nbsp;</td>
					<td align="right"><span style="text-align:right;">高度：</span></td>
					<td><input name="Height" type="text" value="${Height}"
						class="input1" id="Height" verify="高度|NotNull&&Int" />像素(px)</td>
				</tr>
				<tr>
				  <td>&nbsp;</td>
				  <td align="right"><span style="text-align:right;">是否显示文字</span></td>
				  <td>${radiosShowText}</td>
				</tr>
				<tr style="background-color:#FFFFFF">
					<td>&nbsp;</td>
					<td align="right"><span style="text-align:right;">图片来源：</span></td>
					<td>${radiosImageSource}</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="right"><span style="text-align:right;">所属栏目：</span></td>
					<td><sky:select id="RelaCatalogID" listWidth="200"
						listHeight="300" listURL="Site/CatalogSelectList.jsp"></sky:select></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
</sky:init>
