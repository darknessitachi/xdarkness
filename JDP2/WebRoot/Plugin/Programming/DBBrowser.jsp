<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Controls/Tabpage.js"></script>
<script>
function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	Tree.selectNode(ele,true);
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("预览栏目",function(){
	},"Icons/icon403a3.gif");
	
	menu.show();
}

var tabMap = {
	Welcome:"../../inc/welcome.htm",
	TablesRemark:"TablesRemark.jsp?1=1",
	ColsRemark:"ColsRemark.jsp?1=1",
	CodeGenerator:"CodeGenerator.jsp?1=1"
};

var TabID = "TablesRemark";//全局的

function onTabClick(tabid){
	if(tabid){
		TabID = tabid;
	}
	var cid = Tree.CurrentItem.getAttribute("cid");
	Tab.onChildTabClick(TabID);
	var URL = Tab.getCurrentTab().src;
	var newURL = tabMap[TabID]+"&tablename="+cid;
	if(URL.indexOf(newURL)<0){
		Tab.getCurrentTab().src = newURL;
	}
	return;
}

function onTreeClick(){
	var cid = Tree.CurrentItem.getAttribute("cid");
	//Cookie.set("Role.LastRoleCode",cid,"2100-01-01");
	Tab.getCurrentTab().src = tabMap[TabID]+"&tablename="+cid;
	Tab.initFrameHeight(Tab.getCurrentTab().id);
}

function showLeftTree(obj){
	$('leftTree').style.display = $('leftTree').style.display=="none"?"":"none";
	obj.src = $('leftTree').style.display=="none"?"../../Platform/Images/sf002.gif":"../../Platform/Images/sf001.gif";
}
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180" id="leftTree">
			<table width="180" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:6px;" class="blockTd">
						<sky:tree id="tree1" style="height:730px" method="com.xdarkness.framework.database.Database.treeDataBind">
							<p cid='${tableName}' cname='${tablename}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">&nbsp;${alias}</p>
						</sky:tree>
					</td>
				</tr>
			</table>
		</td>
		<td valign="middle" width="2">
			<img style="cursor:pointer" onclick="showLeftTree(this)" src="../../Platform/Images/sf001.gif"/>
		</td>
		<td>
			<sky:tab id="dbBrowder">
				<sky:childtab id="TablesRemark" src="../../inc/welcome.htm" selected="true" afterClick="onTabClick('TablesRemark')">
					<img src='../../Icons/icon018a1.gif' />
					<b>基本信息</b>
				</sky:childtab>
				<sky:childtab id="ColsRemark" src="../../inc/welcome.htm" afterClick="onTabClick('ColsRemark')">
					<img src='../../Icons/icon018a1.gif' />
					<b>字段信息</b>
				</sky:childtab>
				<sky:childtab id="CodeGenerator" src="../../inc/welcome.htm" afterClick="onTabClick('CodeGenerator')">
					<img src='../../Icons/icon018a1.gif' />
					<b>编程信息</b>
				</sky:childtab>
			
			</sky:tab>
		</td>
	</tr>
</table>
</body>
</html>
