
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
<input type='button' value='ɾ����ǰ�ڵ�' onclick='deleteNode()'>
<input type='button' value='�鿴��ǰ�ڵ�' onclick='seeFocusNode()'>
<input type='button' value='�鿴ѡ�нڵ���' onclick='fChecked()'>
<input type='button' value='��ӽڵ�' onclick='addNode()'>
<input type='button' value='�鿴ѡ�нڵ�' onclick='showNode()'>
<input type='button' value='�鿴ѡ�нڵ㣨�������ڵ㣩' onclick='showAllNode()'>
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
			Dialog.alert('��ѡ�е���'+ node.attributes.text );
		}else{
			Dialog.alert('û��ѡ�нڵ�');
		}
	}
	
	function addNode(){
		var node = tree.focusNode;
		if(node!=null){
			var text = window.prompt('����ڵ�����!','')
			if(text){
				var newNode = new TreeNode({text:text});
				node.appendChild(newNode);
			}
		}else{
			Dialog.alert('û��ѡ�нڵ�')
		}
	}
	
	function fChecked(){
		Dialog.alert( tree.getChecked().length );
	}
</script>

<br><br>
Դ���룺
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