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
	   <td>�û���</td><td>�Ա�</td><td>�˺�</td>
	   </tr>
	<tr>
		<td>�û�1</td><td>��</td><td>Account1</td>
	</tr><tr>
		<td>�û�2</td><td>Ů</td><td>Account2</td>
	</tr><tr>
		<td>�û�3</td><td>��</td><td>Account3</td>
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
	Դ���룺
	<pre>
	&lt;sky:datagrid id="datagrid1" showNo="true" showSelector="true" pkCell="1">
		&lt;table xtype="datagrid"   border="0" cellpadding="0" cellspacing="0">
			&lt;tr>
				&lt;td>�û���</td><td>�Ա�</td><td>�˺�</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>�û�1</td><td>�Ա�</td><td>�˺�</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>�û�2</td><td>�Ա�</td><td>�˺�</td>
			&lt;/tr>
			&lt;tr>
				&lt;td>�û�3</td><td>�Ա�</td><td>�˺�</td>
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
