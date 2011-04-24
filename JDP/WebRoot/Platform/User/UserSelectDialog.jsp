<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%	String type = request.getParameter("Type");
	String inputType = "";
	if ("radio".equalsIgnoreCase(type)) {
		inputType = "radio";
	} else {
		inputType = "checkbox";
	}
%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择用户</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
label{ margin-right:10px; white-space:nowrap;}
</style>
<script src="../../Framework/Main.js"></script>
<script src="UserSelectDialog.jsp.js" type="text/javascript"></script>
</head>
<body>
<sky:init method="com.xdarkness.platform.page.UserSelectPage.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table id="test" width="100%" border="0" cellspacing="0" cellpadding="6">
			<tr>
				<td style="padding:6px;"><img src="../../Icons/icon021a2.gif"/>显示方式
					<sky:select id="ShowType" name="ShowType" onChange="changeType();">${ShowType}</sky:select>
					<input id="SelectedUser" name="SelectedUser" type="hidden" value="${SelectedUser}"/>
					<input id="Type" name="Type" type="hidden" value="<%=type%>"/>
					<%if ("checkbox".equalsIgnoreCase(type)) { %>
					<input id="checkAllBox" name="checkAllBox" type="checkbox" onChange="checkAll();"/>全选
					<%} %>
					
				</td>
				<td align="right"><input type="text" id="searchValue" name="searchValue" value="" onfocus="clearRange();"/><input type="button" value="查找" onclick="findString();" /></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;" colspan="2">
				<sky:datagrid id="dg1" method="com.xdarkness.platform.page.UserSelectPage.dg1DataBind">
					<table width="100%" border="1" cellpadding="2" cellspacing="0" bordercolor="#eeeeee">
						<tr bgcolor="#f6f6f6">
							<td width="150" align="center"><b>分类类别</b></td>
							<td align="center"><b>用户</b></td>
						</tr>
						<tr valign="top">
							<td align="left" nowrap>${LevelStr}
							<%if ("checkbox".equalsIgnoreCase(type)) { %>
							<input id="check${ID}" name="check${ID}" type="<%=inputType%>" onClick="checkUser('${ID}');"/>
							<%} %><span>${Name}</span></td>
							<td><span>${UserName}</span></td>
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
<script language="JavaScript">
<!--
// author:TangYan
// date:2009-07-24
// email:ty80style@126.com
var IERange = null;
var FFRange = null;
var PreEle = null;
var callCount = 0;
var selectBGColor = "green";

// 输入关键字的文本框获得焦点时必须响应的函数
function clearRange(){
	if(window.find){
		FFRange = null;		
		if(PreEle){
			PreEle.innerHTML = PreEle.innerText;
			PreEle = null;		
		}
	}	
}

function findString () {
	// 取得关键字
	var str = $V("searchValue");
	if(!str||str==""){
		Dialog.alert("关键字不能为空！");
		return;
	}
	var strFound;
	if(window.find){
		// for firefox
		// firefox下必须查找两次，第一次查找到的是关键字输入框中的文本				
  		if (FFRange==null){		
  			// 查找到关键字输入框中的文本
  			strFound = window.find(str);
  			FFRange = window.getSelection();  		 				
  		}  		
  		try{
	  		FFRange.getRangeAt(0).startContainer.nodeName; 		
	  		strFound = window.find(str);	  		
  		}catch(e){
  			// 抛出此异常说明search文本框被focus了，需要再补查一次
  			strFound = window.find(str);  	
  		}
  		
  		if(strFound){
  			callCount = 0;
  			if(PreEle){// 清除上次的高亮显示
  				PreEle.innerHTML = PreEle.innerText;
  			}  			
  			var spanEle = document.createElement("SPAN");			
			spanEle.style.color = "green";			
			// 将查找到的文本红色显示
			FFRange.getRangeAt(0).surroundContents(spanEle);			
			PreEle = spanEle.parentNode;
  		}else{  			
  			if(PreEle){
				PreEle.innerHTML = PreEle.innerText;
				PreEle = null;		
			}				
			FFRange.collapse(document.body,0);
			FFRange = null;
			if(callCount <= 0){
				callCount++;
				findString();
				return;
			}
  		}
  	}
  	else{
  		// for IE
  		if(IERange==null){
			IERange = document.body.createTextRange();
		}		
		strFound = IERange.findText(str);		
		if(strFound){		
			callCount = 0;			
			if(PreEle){
				PreEle.innerHTML = PreEle.innerText;	
			}	
			IERange.pasteHTML(IERange.text.fontcolor(selectBGColor));	
		    IERange.select();	  
		    PreEle = IERange.parentElement();	
		}	
		else{
			if(PreEle){
				PreEle.innerHTML = PreEle.innerText;
				PreEle = null;		
			}		
			IERange = null;	
			if(callCount <= 0){
				callCount++;
				findString();
				return;
			}			
		}	
	}
	if (!strFound){
	 	Dialog.alert ("未找到关键字 '"+str+"' ！");
	} 	 
}
//-->
</script>
</html>
