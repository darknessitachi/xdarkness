<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    
    <title>My JSP 'Datepicker.jsp' starting page</title>
	<%@ include file="/views/js/importjs.jsp" %>
	
  </head>
  
  <body>
  	�������ڣ�<sky:datePicker name="params.birthDay" readonly="false" dateFmt="yyyy-MM-dd"/><br/>
    ˫���ڣ�<sky:datePicker name="params.birthDay1,params.birthDay2" readonly="true" dateFmt="yyyy-MM-dd"/>
    
    <br><br>
    Դ���룺
    <pre>
    �������ڣ�&lt;sky:datePicker name="params.birthDay" readonly="false" dateFmt="yyyy-MM-dd"/><br/>
    ˫���ڣ�&lt;sky:datePicker name="params.birthDay1,params.birthDay2" readonly="true" dateFmt="yyyy-MM-dd"/> 
    </pre> 
  </body>
</html>
