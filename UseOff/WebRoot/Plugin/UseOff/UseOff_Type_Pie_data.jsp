<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@page import="com.abigdreamer.java.net.sql.QueryBuilder"%>
<%@page import="com.abigdreamer.java.net.orm.data.DataTable"%>
<%@page import="com.abigdreamer.java.net.orm.data.DataRow"%>
<?xml version="1.0" encoding="utf-8"?>
<chart palette='4' decimals='0' enableSmartLabels='1' formatNumberScale='0'
	enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' 
	bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70'
	baseFont='Arial' baseFontColor='1970d1' baseFontSize='11.5' >
<%
	DataTable dataTable = new QueryBuilder("select u.* from (select sum(money) as allmoney,usefor from useoff where moneyType='out' group by usefor) as u group by u.allmoney desc").executeDataTable();
	for (int i = 0; i < dataTable.getDataRows().length; i++) {
		DataRow row = dataTable.getDataRows()[i];
		String label = row.getString(1);
		String nums = row.getString(0);
%><set label='<%=label %>' value='<%=nums %>'  isSliced='1'/><%}%>
</chart>