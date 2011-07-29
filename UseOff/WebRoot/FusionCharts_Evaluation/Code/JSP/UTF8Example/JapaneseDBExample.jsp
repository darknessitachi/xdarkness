﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8"/> 
		<TITLE>FusionCharts - UTF8 日本語 (Japanese) Database Example</TITLE>
		<%
			/*You need to include the following JS file, if you intend to embed the chart using JavaScript.
			Embedding using JavaScripts avoids the "Click to Activate..." issue in Internet Explorer
			When you make your own charts, make sure that the path to this JS file is correct. Else, you would get JavaScript errors.
			*/
			%>
		<SCRIPT LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js"></SCRIPT>
		<style type="text/css">
			<!--
			body {
				font-family: Arial, Helvetica, sans-serif;
				font-size: 12px;
			}
			.text{
				font-family: Arial, Helvetica, sans-serif;
				font-size: 12px;
			}
			-->
		</style>
	</HEAD>
	<BODY>
		<CENTER>
			<h2>FusionCharts UTF8 日本語 (Japanese) Example With Data from Database</h2>
			<%
		
				/*
				In this example, we show how to use UTF characters in chart created with FusionCharts 
				Here, the XML data for the chart is dynamically generated by PieDataJapanese.jsp. 
				*/
				/*
				In this example, FusionCharts uses dataURL method to get the xml from the data in the database. 
				In order to store and retrieve UTF8 characters from database, in our example, MySQL,
				we have to alter the database default charset to UTF8. This can be done using the command:
				ALTER DATABASE DEFAULT CHARACTER SET = utf8; on the "factorydb" database.
			    For this example, we will use another table Japanese_Factory_Master containing the names of the factories
			    in Japanese language. This table should also be defined to use UTF8 as DEFAULT CHARSET. 
			    The sql script that needs to be executed before running this example is UTFExampleTablesCreation.sql 
			    present in Code/JSP/DB folder.
				*/
				/*
				The pageEncoding and chartSet headers for the page have been set to UTF-8
				in the first line of this jsp file.
				*/	
				//Variable to contain dataURL
				//NOTE: It's necessary to encode the dataURL if you've added parameters to it
				String strDataURL="PieDataJapanese.jsp";
				
				//Create the chart - Pie 3D Chart with dataURL as strDataURL
			%> 
			<jsp:include page="../Includes/FusionChartsRenderer.jsp" flush="true"> 
							<jsp:param name="chartSWF" value="../../FusionCharts/Pie3D.swf" /> 
							<jsp:param name="strURL" value="<%=strDataURL%>" /> 
							<jsp:param name="strXML" value="" /> 
							<jsp:param name="chartId" value="FactorySum" /> 
							<jsp:param name="chartWidth" value="650" /> 
							<jsp:param name="chartHeight" value="450" /> 
						</jsp:include>
			<BR>
			<BR>
			<a href='../NoChart.html' target="_blank">Unable to see the chart above?</a><BR>
			<H5><a href='../default.htm'>&laquo; Back to list of examples</a></h5>
		</CENTER>
	</BODY>
</HTML>