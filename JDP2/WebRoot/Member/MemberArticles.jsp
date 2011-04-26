<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员中心</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function doLogout(){
	Dialog.confirm("您确认退出吗？",function(){
		window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;											
	});
}

function preview(ID){
	var SiteID = $V("SiteID");
	if(SiteID==""){
		Dialog.alert("站点错误");	
		return;
	}else{
		window.open("Preview.jsp?ArticleID="+ID+"&SiteID="+SiteID);
	}
}

function del(ID){
	if(confirm("确认删除这篇文章吗？")){
		var dc = new DataCollection();
		dc.add("ID",ID);
		Server.sendRequest("com.xdarkness.member.Article.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.location.reload();
				}						   
			});
		});
	}
}

</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  <div class="forumbox">
    <h4>我发表的文章</h4>
    <ul class="tabs">
	<li class="current"><a href="#;">我的文章</a></li>
    <li style="float:right"><a href="WriteArticle.jsp">发表新文章</a></li>
	</ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
      <sky:init method="com.xdarkness.member.Article.initSiteID">
        <thead>
          <tr>
            <th width="12%">栏目<input type="hidden" id="SiteID" name="SiteID" value="${SiteID}"/></th>
            <th width="50%">标题</th>
            <th width="16%">发表时间</th>
            <th width="8%">状态</th>
            <th width="14%">操作</th>
          </tr>
        </thead>
       </sky:init>
         <sky:datalist id="dg1" method="com.xdarkness.member.Article.dg1DataList" size="10" page="true">
        <tbody>
          <tr>
            <td>${CatalogName}</td>
            <td ><span id="thread_6417"><a href="#;" onclick="preview(${ID});">${Title}</a></span></td>
            <td><em>${AddTime}</em></td>
            <td>${StatusName}</td>
            <td>${DO}</td>
          </tr>
        </tbody>
         </sky:datalist>
         <tbody>
          <tr>
            <td colspan="5"><sky:pagebar target="dg1" /></td>
          </tr>
        </tbody>
      </table>
  </div>
</td>
  </tr>
</table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>