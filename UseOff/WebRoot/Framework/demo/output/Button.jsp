<%@ page language="java" pageEncoding="gbk"%>
<html>
  <head>
    
    <title>My JSP 'testButton.jsp' starting page</title>
	
	<%@ include file="/views/js/importjs.jsp" %>
	<script type="text/javascript">
		 window.onload = function(){
		 	var button = new Button("修改","icon-edit");
			 button.appendEvent("click",function(){
			 	Dialog.alert('test');
			 });
			 $('divButton').appendChild(button);
		 };
	</script>
  </head>
  
  <body>
    <div id="divButton"></div>
    <br><br>
    源代码：
    <pre>
    
	&lt;div id="divButton">&lt;/div>
    
	var button = new Button("修改","icon-edit");
	button.appendEvent("click",function(){
		Dialog.alert('test');
	});
	$('divButton').appendChild(button);
    </pre>
  </body>
</html>
