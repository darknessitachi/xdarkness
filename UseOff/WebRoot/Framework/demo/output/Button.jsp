<%@ page language="java" pageEncoding="gbk"%>
<html>
  <head>
    
    <title>My JSP 'testButton.jsp' starting page</title>
	
	<%@ include file="/views/js/importjs.jsp" %>
	<script type="text/javascript">
		 window.onload = function(){
		 	var button = new Button("�޸�","icon-edit");
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
    Դ���룺
    <pre>
    
	&lt;div id="divButton">&lt;/div>
    
	var button = new Button("�޸�","icon-edit");
	button.appendEvent("click",function(){
		Dialog.alert('test');
	});
	$('divButton').appendChild(button);
    </pre>
  </body>
</html>
