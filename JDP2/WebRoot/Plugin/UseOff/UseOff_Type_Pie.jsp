<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统计</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/FusionCharts_Evaluation/Contents/Style.css" type="text/css" />
<script language="JavaScript" src="<%=request.getContextPath() %>/FusionCharts_Evaluation/JSClass/FusionCharts.js"></script>
</head>
 
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
  <tr> 
    <td valign="top" class="text" align="center">
    	<div id="chartdiv" align="center">FusionCharts. </div>
	</td>
    <td valign="top" class="text" align="center">
		<div id="chartdiv1" align="left" style="padding:0 0 0 0"></div>
      <script type="text/javascript">
      	   var width = window.screen.width > 1024 ? 450 : 420;
		   var chart1 = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Column3D.swf", "ChartId", width, "350", "0", "0");
		   chart1.setDataURL("UseOff_Month_Zhu_data.jsp");		   
		   chart1.render("chartdiv1");
		</script> </td>
  </tr>
  <tr> 
    <td valign="top" class="text" align="center">
    	<div id="chartdiv3" align="center">FusionCharts. </div>
      <script type="text/javascript">
           var width = window.screen.width > 1024 ? 450 : 435;
		   var chart3 = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Column3D.swf", "ChartId", width, "330", "0", "0");
		   chart3.setDataURL("UseOff_Month_Zhu_data_usein.jsp");		   
		   chart3.render("chartdiv3");
		</script> </td>
    <td valign="top" class="text" align="center">
		<div id="chartdiv4" align="left" style="padding:0 0 0 0"></div>
      <script type="text/javascript">
      	   var width = window.screen.width > 1024 ? 450 : 420;
		   var chart1 = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Column3D.swf", "ChartId", width, "350", "0", "0");
		   chart1.setDataURL("UseOff_Month_Zhu_data_useout.jsp");		   
		   chart1.render("chartdiv4");
		</script> </td>
  </tr>
  <tr> 
    <td valign="top" class="text" align="center">
    	<div id="chartdiv_salary" align="center">FusionCharts. </div>
      <script type="text/javascript">
           var width = window.screen.width > 1024 ? 450 : 435;
		   var chart3 = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Column3D.swf", "ChartId", width, "330", "0", "0");
		   chart3.setDataURL("UseOff_Month_Zhu_data_salary.jsp");		   
		   chart3.render("chartdiv_salary");
		</script> </td>
  </tr>
  <%
  String[] months = new String[]{
		"09-01","09-02","09-03","09-04","09-05","09-06",
		"09-07","09-08","09-09","09-10","09-11","09-12",
		"10-01","10-02","10-03","10-04","10-05","10-06",
		"10-07","10-08","10-09","10-10","10-11","10-12",
		"11-01","11-02","11-03","11-04"};
  for(int i=0;i<months.length;i++){
	  String month = months[i];
	  boolean writeTr = (i%2==0);
	  if(writeTr){
  %>
  <tr>
  <%} %>
  	 <td valign="top" class="text" align="center"> <%=month %>
  	 	<div id="chartdiv_month<%=month%>" align="center">FusionCharts. </div>
      <script type="text/javascript">
           var width = window.screen.width > 1024 ? 450 : 435;
		   var chart = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Pie3D.swf", "ChartId", width, "330", "0", "0");
		   chart.setDataURL("UseOff_Type_Pie_data_month.jsp?month=<%=month%>");		   
		   chart.render("chartdiv_month<%=month%>");
		</script> </td>
  <%
		if(!writeTr){
  %>
  </tr>
  <%} }%>
</table>
</body>
<script type="text/javascript">
         var width = window.screen.width > 1024 ? 450 : 435;
   var chart = new FusionCharts("<%=request.getContextPath() %>/FusionCharts_Evaluation/Charts/Pie3D.swf", "ChartId", width, "330", "0", "0");
   chart.setDataURL("UseOff_Type_Pie_data.jsp");		   
   chart.render("chartdiv");
</script> 
</html>
