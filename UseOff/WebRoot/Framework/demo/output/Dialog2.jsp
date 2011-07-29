<%@ page language="java" pageEncoding="gbk"%>

<html>
  <head>
    <title>My JSP 'testDialog.jsp' starting page</title>
    
	<%@ include file="/views/js/importjs.jsp" %>
	<script type="text/javascript">
	function add() {
		var diag2 =new Dialog({
           title: "新增用户",
           width: 500,
           height: 200,
           url: "Framework/demo/output/Dialog.jsp",
           onLoad: function(){
           },
           OKEvent: function(){
           	diag2.close();
           	if($TW.diag) {
           		$TW.diag.reload();
           	}
        }
       });
       diag2.show();
    }
	</script>
  </head>
  
  <body>
  	<br>
    <input type="button" value="show" onclick="add()"/>
  </body>
</html>
