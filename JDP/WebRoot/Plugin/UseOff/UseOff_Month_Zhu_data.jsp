<%@ page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<%@page import="com.xdarkness.framework.sql.QueryBuilder"%>
<%@page import="com.xdarkness.framework.orm.data.DataTable"%>
<%@page import="com.xdarkness.framework.orm.data.DataRow"%>
<?xml version="1.0" encoding="GBK"?>
<chart xAxisName='Month' rotateYAxisName='0' yAxisName='' 
	showValues='0' decimals='0' formatNumberScale='0' baseFont='Arial'
	baseFontColor='1970d1' baseFontSize='11.5'>
<%

DataTable dataTable = new QueryBuilder("select u.* from (select sum(money) as allmoney,usefor from useoff where moneyType='out' group by usefor ) as u group by u.allmoney desc").executeDataTable();
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
