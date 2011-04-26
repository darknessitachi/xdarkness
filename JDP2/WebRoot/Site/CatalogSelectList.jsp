<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
*{ box-sizing: border-box; -moz-box-sizing: border-box; -khtml-box-sizing: border-box; -webkit-box-sizing: border-box; }
body{_overflow:auto;}
</style>
<script src="../Framework/Main.js"></script>
<script>
function onTreeClick(ele){
	var v =  ele.getAttribute("cid");
	v = v? v:"";
	var t = ele.innerText;
	Selector.setReturn(t,v,document.body.scrollTop);
}
Page.onLoad(function(){
	Tree.select("tree1","cid",window.SelectedValue);
});
</script>
</head>
<body class="dialogBody">
<sky:tree id="tree1" method="com.xdarkness.cms.site.Catalog.treeDataBind"
	level="2" lazy="true">
	<p cid='${ID}' onClick="onTreeClick(this);">${Name}</p>
</sky:tree>
</body>
</html>
