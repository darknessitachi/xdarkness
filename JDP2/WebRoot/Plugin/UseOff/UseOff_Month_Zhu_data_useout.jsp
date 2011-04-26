<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@page import="com.xdarkness.framework.sql.QueryBuilder"%>
<%@page import="com.xdarkness.framework.orm.data.DataTable"%>
<%@page import="com.xdarkness.framework.orm.data.DataRow"%>
<?xml version="1.0" encoding="UTF-8"?>
<chart xAxisName='out' rotateYAxisName='0' yAxisName='' 
	showValues='0' decimals='0' formatNumberScale='0' baseFont='Arial'
	baseFontColor='1970d1' baseFontSize='11.5'>
<%

DataTable dataTable = new QueryBuilder("select sum(money) as money, substring(createTime,3,5) as theMonth from useoff where moneyType='out' and usefor <> '家' group  by theMonth").executeDataTable();
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
