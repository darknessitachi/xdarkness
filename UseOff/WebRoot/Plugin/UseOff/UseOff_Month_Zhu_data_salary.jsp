<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%@page import="com.abigdreamer.java.net.sql.QueryBuilder"%>
<%@page import="com.abigdreamer.java.net.orm.data.DataTable"%>
<%@page import="com.abigdreamer.java.net.orm.data.DataRow"%>
<?xml version="1.0" encoding="utf-8"?>
<chart xAxisName='in' rotateYAxisName='0' yAxisName='' 
	showValues='0' decimals='0' formatNumberScale='0' baseFont='Arial'
	baseFontColor='1970d1' baseFontSize='11.5'>
<%
DataTable dataTable = new QueryBuilder("select sum(money) as money, substring(createTime,3,5) as theMonth from useoff where moneyType='in' and useFor='salary' and description is null group  by theMonth").executeDataTable();
for (int i = 0; i < dataTable.getDataRows().length; i++) {
	DataRow row = dataTable.getDataRows()[i];
	String label = row.getString(1);
	String nums = row.getString(0);
%>
		<set label='<%=label %>' value='<%=nums %>' />
<%
	}
%>

</chart>	
