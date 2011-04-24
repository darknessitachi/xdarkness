<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function importColumn(){
	var txt=$V("BatchColumn");
	if(!txt){
		Dialog.alert("请填写栏目名称");
		return;
	}
	if(!checkFormat(txt)){
		return;
	}
	$("form3").submit();
}

function closeDialog(){
   Dialog.close();
}

function init(){
	var obj=document.getElementById('BatchColumn');
	var str=obj.value;
	obj.setAttribute('oldValue',format(str));
}

//检查填写的字符串格式是否正确
function checkFormat(str){
	if(!str){
		return false;
	}
	if(str.indexOf("'")>=0||str.indexOf("\"")>=0||str.indexOf(",")>=0){
		Dialog.alert("栏目名称中不能有引号和逗号！");
		return false;
	}
	str = str.replace("\t","  ");
	str = str.replace(/\r\b\f/g,"");
	$S("BatchColumn",str);
	return true;
}
</script>
<style type="text/css">
<!--
.style1 {color: #445566; font-weight:bold;}
-->
</style>
</head>
<body class="dialogBody">
<form id="form3" method="POST" target="_self"
			action="CatalogImport2.jsp?ParentID=<%=request.getParameter("ParentID")%>&Type=<%=request.getParameter("Type")%>">
  <table width="100%" height="100%" align="center" cellpadding="1" cellspacing="0"	>
    <tr>
      <td width="3%" align="center">&nbsp;</td>
      <td valign="top"><p><span class="style1"><br>
        请按如下要求输入栏目名称：</span>        
        <p><span>1.每行填写一个栏目；</span><br>
          <span>2.子栏目相对父栏目使用两个英文空格或一个制表符缩进。</span><br />
        &nbsp;
        <p>
          <textarea style="height:220px;width:500px;vertical-align:top;"
			id="BatchColumn" name="BatchColumn"></textarea>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center"></td>
    </tr>
    <tr>
      <td colspan="2" align="center"></td>
    </tr>
    <tr>
      <td height="50" colspan="2" align="center" class="dialogButtonBg"><input
			name="button" type="button" class="inputButton" id="button"
			value=" 下一步 " onClick="importColumn()" />
        &nbsp;
        <input
			name="button2" type="button" class="inputButton"
			onClick="Dialog.close();" value=" 取 消 " /></td>
    </tr>
  </table>
</form>
</body>
</html>
