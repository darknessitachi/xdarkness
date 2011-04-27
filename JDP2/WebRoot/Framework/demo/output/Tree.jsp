
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/views/js/importjs.jsp" %>
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<script type=text/javascript src="china_2.js"></script>
	</head>
<body>
<input type='button' value='删除当前节点' onclick='deleteNode()'>
<input type='button' value='查看当前节点' onclick='seeFocusNode()'>
<input type='button' value='查看选中节点数' onclick='fChecked()'>
<input type='button' value='添加节点' onclick='addNode()'>
<input type='button' value='查看选中节点' onclick='showNode()'>
<input type='button' value='查看选中节点（包含父节点）' onclick='showAllNode()'>
</body>
<script type=text/javascript>

	//var l0 = new Date().getTime();
	
	tree = new TreePanel({
		'root' : root,
		
		iconPath: "<%=request.getContextPath()%>/Framework/resources/images/default/tree/"		
	});

	tree.render();
	
	
	//alert( (new Date().getTime()-l0));
	
	function showNode() {
		Dialog.alert(tree.getChecked());
	}
	
	function showAllNode() {
		Dialog.alert(tree.getCheckedNodes());
	}
	
	function deleteNode(){
		var node = tree.focusNode;
		if(node){
			var parentNode = node.parentNode;
			if(parentNode){
				parentNode.removeChild(node);
			}
		}
	}
	
	function seeFocusNode(){
		var node = tree.focusNode;
		if(node!=null){
			Dialog.alert('你选中的是'+ node.attributes.text );
		}else{
			Dialog.alert('没有选中节点');
		}
	}
	
	function addNode(){
		var node = tree.focusNode;
		if(node!=null){
			var text = window.prompt('输入节点名称!','')
			if(text){
				var newNode = new TreeNode({text:text});
				node.appendChild(newNode);
			}
		}else{
			Dialog.alert('没有选中节点')
		}
	}
	
	function fChecked(){
		Dialog.alert( tree.getChecked().length );
	}
</script>

<br><br>
源代码：
<pre>
	var root = {
			'text':'',icon:'api',
			'children':[{
				text:'',icon:'pkg',
				children:[{
					text: '',icon:'cmp', 
					action: ''
				},{
					text: '',icon:'cmp', 
					action: ''
				}]
			}]
	};		
	
	var tree = new TreePanel({
		'root' : root,
		
		iconPath: "<%=request.getContextPath()%>/Framework/resources/images/default/tree/"		
	});

	tree.render();
	
</pre>
</html>