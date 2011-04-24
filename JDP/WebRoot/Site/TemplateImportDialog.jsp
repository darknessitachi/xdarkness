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
	function check(){
		if($V("TemplateFile")==""){
			alert("请选择导入的模板文件！");
			return false;
		}
		return true;
	}
	
	function closeDialog(){
	   Dialog.close();
	}
</script>
</head>
<body>
<p>&nbsp;<iframe src="javascript:void(0);" name="targetFrame"
	width="0" height="0" frameborder="0"></iframe></p>
<form id="form2" enctype="multipart/form-data" target="targetFrame"
	action="TemplateImportSave.jsp?SiteID=<%=request.getParameter("SiteID")%>"
	method="POST" onSubmit="return check();">
<table width="90%" height="150" align="center" cellpadding="2"
	cellspacing="0">
	<tr>
		<td height="55" colspan="2" align="left">
		<p>选择模板： <input name="TemplateFile" type="file" id="TemplateFile">
		<br>
		支持ZIP、HTML、HTM格式文件</p>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<div style="text-align:left;">
		<p><font color="#FF6633">请注意，如遇同名文件，源文件将被覆盖。</font></p>
		</div>
		</td>
	</tr>

	<tr>
		<td colspan="2" align="center"><input name="button"
			type="Submit" class="inputButton" id="button" value=" 确 定 " /> &nbsp;
		<input name="button2" type="button" class="inputButton"
			onClick="Dialog.close();" value=" 取 消 " /></td>
	</tr>
</table>
</form>
</body>
</html>
