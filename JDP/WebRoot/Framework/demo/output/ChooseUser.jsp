<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    
    <title>chooseUser</title>
	<%@ include file="/views/js/importjs.jsp" %>
	
  </head>
  
  <body>
    <sky:chooseUser deptCode="D001"/>
    
    <br><br>
    Դ���룺
    <pre>
	&lt;sky:chooseUser deptCode="D001"/>
    </pre>
  </body>
</html>
