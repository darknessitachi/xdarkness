<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.cms.dataservice.VoteResult"%>
<%
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=utf-8");
VoteResult.vote(request,response);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>投票调查结果</title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Chart.js"></script>


</head>
<body>
<script>
window.close();
</script>
</body>
</html>
