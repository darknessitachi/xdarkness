<%@ page language="java"  pageEncoding="gbk"%>

<html>
  <head>
    <title>My JSP 'testDialog.jsp' starting page</title>
    
	<%@ include file="/views/js/importjs.jsp" %>
	<script type="text/javascript">
	var diag = null;
	function add() {
		diag =new Dialog({
           title: "新增用户",
           width: 700,
           height: 300,
           url: "Framework/demo/output/Dialog2.jsp",
           onLoad: function(){
           },
           OKEvent: function(){
           }
       });
       diag.show();
    }
	</script>
  </head>
  
  <body>
  <br>
    <input type="button" value="show" onclick="add()"/>
    <br><br>
    源代码：
    <pre>
    var diag =new Dialog({
           title: "新增用户",
           width: 700,
           height: 300,
           url: "Framework/demo/output/Dialog2.jsp",
           onLoad: function(){
           },
           OKEvent: function(){
           }
       });
       diag.show();
    </pre>
  </body>
</html>
