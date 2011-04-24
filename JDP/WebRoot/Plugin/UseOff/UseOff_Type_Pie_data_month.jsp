<%@ page contentType="text/html; charset=GBK" language="java" errorPage=""%>
<%@page import="com.xdarkness.framework.sql.QueryBuilder"%>
<%@page import="com.xdarkness.framework.orm.data.DataTable"%>
<%@page import="com.xdarkness.framework.orm.data.DataRow"%>
<?xml version="1.0" encoding="GBK"?>
<!--  
<chart xAxisName='out' rotateYAxisName='0' yAxisName='' 
	showValues='0' decimals='0' formatNumberScale='0' baseFont='Arial'
	baseFontColor='1970d1' baseFontSize='11.5'>-->
<chart palette='4' decimals='0' enableSmartLabels='1' formatNumberScale='0'
	enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='40,100' 
	bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70'
	baseFont='Arial' baseFontColor='1970d1' baseFontSize='11.5' >
<%

DataTable dataTable = new QueryBuilder("select sum(money), usefor from useoff where moneyType='out' and usefor <> '家' and substring(createTime,3,5) like '"+request.getParameter("month")+"' group  by usefor").executeDataTable();
for (int i = 0; i < dataTable.getDataRows().length; i++) {
	DataRow row = dataTable.getDataRows()[i];
	String label = row.getString(1);
	String nums = row.getString(0);
%>
		<set label='<%=label %>' value='<%=nums %>'  isSliced='1' />
<%
	}
%>

</chart>	
