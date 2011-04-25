<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    
    <title>My JSP 'Datepicker.jsp' starting page</title>
	<%@ include file="/views/js/importjs.jsp" %>
	
  </head>
  
  <body>
  	单个日期：<sky:datePicker name="params.birthDay" readonly="false" dateFmt="yyyy-MM-dd"/><br/>
    双日期：<sky:datePicker name="params.birthDay1,params.birthDay2" readonly="true" dateFmt="yyyy-MM-dd"/>
    
    <br><br>
    源代码：
    <pre>
    单个日期：&lt;sky:datePicker name="params.birthDay" readonly="false" dateFmt="yyyy-MM-dd"/><br/>
    双日期：&lt;sky:datePicker name="params.birthDay1,params.birthDay2" readonly="true" dateFmt="yyyy-MM-dd"/> 
    </pre> 
  </body>
</html>
