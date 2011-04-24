<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function onTreeClick(ele){
	var v =  ele.getAttribute("cid");
	var t = ele.innerText;
	if(v==null){
		Selector.setReturn(t,"0");
	}else{
		Selector.setReturn(t,v);
	}
}
Page.onLoad(function(){
	Tree.select("tree1","cid",window.SelectedValue);
});
</script>
</head>
<body>
<sky:tree id="tree1"
	style="width:200px;height:300px"
	method="com.xdarkness.shop.GoodsLib.treeDataBind" level="2" lazy="true"
	expand="false">
	<p cid='${ID}' onClick="onTreeClick(this);"
		onContextMenu="showMenu(event,this);">${Name}</p>
</sky:tree>
</body>
</html>
