<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="sky" uri="/WEB-INF/tags/sky.tld" %>
<html>
  <head>
    
    <title>My JSP 'Datagrid.jsp' starting page</title>
	<%@ include file="/views/js/importjs.jsp" %>
	
  </head>
  
  <body>
  <sky:datagrid id="datagrid1" showNo="true" showSelector="true" pkCell="1">
	<table xtype="datagrid"   border="0" cellpadding="0" cellspacing="0">
	   <tr>
	   <td>用户名</td><td>性别</td><td>账号</td>
	   </tr>
	<tr>
		<td>用户1</td><td>男</td><td>Account1</td>
	</tr><tr>
		<td>用户2</td><td>女</td><td>Account2</td>
	</tr><tr>
		<td>用户3</td><td>男</td><td>Account3</td>
	</tr>
	        <tr xtype="pagebar">
	    <td colspan="7" height="24" class="txt" bgcolor="#f1f1f1" >
	        <div align="right">
	        	
	        </div>
	    </td>
	</tr>
	</table>
	</sky:datagrid>
	
	<br><br>
	源代码：
	<pre>
	&lt;sky:datagrid id="datagrid1" showNo="true" showSelector="true" pkCell="1">
		&lt;table xtype="datagrid"   border="0" cellpadding="0" cellspacing="0">
			&lt;tr>
				&lt;td>用户名</td><td>性别</td><td>账号</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>用户1</td><td>性别</td><td>账号</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>用户2</td><td>性别</td><td>账号</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>用户3</td><td>性别</td><td>账号</td>
			&lt;/tr>

			&lt;tr xtype="pagebar">
				&lt;td colspan="7" height="24" class="txt" bgcolor="#f1f1f1" >
					&lt;div align="right">
					&lt;/div>
				&lt;/td>
			&lt;/tr>
		&lt;/table>
	&lt;/sky:datagrid>
	</pre>
  </body>
</html>
