<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sky" uri="/pages" %>
<%@ include file="../../views/js/importjs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>tabTest</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script type="text/javascript">
		Page.onLoad(function(){
			
			Tab.BasePath = "<%=request.getContextPath()%>/";
			/**
			第一个参数对应tab标签中的id
			第二个配置项
				id：新tab项的id
				title：新tab项的标题
				renderTo：tab对应的内容的id，这边对应下边id="newTabContent"的div
				showClose：是否显示关闭按钮
			*/
			Tab.addTab('tab', {
				id: 'newTab',
				title: '新加Tab',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab1',
				title: '新加Tab1',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab2',
				title: '新加Tab2',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab3',
				title: '新加Tab3',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab4',
				title: '新加Tab4',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab5',
				title: '新加Tab5',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab6',
				title: '新加Tab6',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab7',
				title: '新加Tab7',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab8',
				title: '新加Tab8',
				renderTo: 'newTabContent',
				showClose: true
			});
			Tab.addTab('tab', {
				id: 'newTab9',
				title: '新加Tab9',
				renderTo: 'newTabContent',
				showClose: true
			});
			
		});
		
	</script>
  </head>
  
  <body style="background-color:#ffffff">
    <sky:tab id="tab"> 
		<sky:childtab src="" id="ImageUpload" title="图片上传" showClose="true" icon="Framework/resources/icons/icon030a13.gif" selected="true">
			<iframe src="" height="800"></iframe>
		</sky:childtab>
		<sky:childtab src="" id="ImageBrowse" title="图片浏览" icon="Framework/resources/icons/icon030a11.gif">
			<div><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1><h1>你好</h1></div>
			<table>
				<tr>
					<td>s</td>
				</tr>
			</table>
		</sky:childtab>
	</sky:tab>
	
	<div id="newTabContent">
		这个是新添加的tab中的内容
	</div>
  </body>
</html>
