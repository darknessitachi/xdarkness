<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	$("btnSave").onclick = update;
});
function update(){
	var dc = Form.getData($F("form2"));
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("admin.TablesRemark.update", dc, function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
		}
	});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="admin.TablesRemark.init">
<form id="form2">
	<table cellpadding="0" cellspacing="1" border="1" 
		 bordercolor="#468E00" width="100%">
		<TR>
			<TD colspan="2" align=left><input id="Ctr_Boolusecache" type="checkbox" name="Ctr_Boolusecache" /><label for="Ctr_Boolusecache">在生成集合操作类时使用静态存储</label></TD>
		</TR>
		<TR>
			<TD width=30% style="text-align:right;padding-right:15px">表类型:</TD>
			<TD>
				<sky:select name="Ctr_Tabletype" id="Ctr_Tabletype" style="width:99%;">
					<option selected="selected" value="0">核心表</option>
					<option value="5">新闻表</option>
					<option value="50">业务支撑表</option>
				</sky:select>
			</TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">所处目录:</TD>
			<TD>
				<sky:select name="categoryid" id="categoryid" style="width:99%;">
					${categoryid}
				</sky:select>
			</TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">表名:</TD>
			<TD><input name="tablename" type="text" style="width:99%" value="${tablename}" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">别名:</TD>
			<TD><input name="alias" type="text" style="width:99%" value="${alias}" id="alias" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">第一组合主键:</TD>
			<TD><input name="Ctr_primaryex1" type="text" value="${primaryExt1}" id="Ctr_primaryex1"  style="width:99%" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">第二组合主键:</TD>
			<TD><input name="Ctr_primaryex2" type="text" value="${primaryExt2}" id="Ctr_primaryex2"  style="width:99%" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">默认排序规则:</TD>
			<TD><input name="Ctr_ordercol" type="text"  value="${ordercol}" id="Ctr_ordercol"  style="width:99%" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">从属主表名:</TD>
			<TD><input name="Ctr_summarytable" type="text" value="${summarytable}" id="Ctr_summarytable"  style="width:99%" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">使用何字段关联主表主键:</TD>
			<TD><input name="Ctr_summarycol" type="text" value="${summarycol}" id="Ctr_summarycol"  style="width:99%" /></TD>
		</TR>
		<TR>
			<TD style="text-align:right;padding-right:15px">注释:</TD>
			<TD><textarea name="remark" id="remark"  style="width:99%">${remark}</textarea></TD>
		</TR>
		<tr>
			<td colspan=2 align=center>
				<input name="btnSave" type="button" id="btnSave"  value="保存" />
				<INPUT id="Btn_Reset" type="reset" value="重写">
			</td>
		</tr>
	</TABLE>
</form>
</sky:init>
</body>
</html>
