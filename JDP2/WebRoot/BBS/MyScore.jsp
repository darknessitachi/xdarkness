<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="ForumInit.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title><%=ForumUtil.getCurrentName(request)%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="forum.css" rel="stylesheet" type="text/css">
<style type="text/css">
.mainbox table th,
.mainbox table td{ padding-left:35px;}
</style>
<!--<link href="skins/zving/default.css" rel="stylesheet" type="text/css">-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<%@ include file="../Include/Head.jsp" %>
</head>
<script type="text/javascript">
	function PassWordSave() {
		var dc = Form.getData($F("postform"));
		Server.sendRequest("com.xdarkness.bbs.ControlPanel.passwordSave",dc,function(response){
			Dialog.alert(response.Message);
			if(response.Status==1){
				$D.close();
			}
		});
	}
	
</script>
<body>
<%@ include file="../Include/Top.jsp" %>
<sky:init method="com.xdarkness.bbs.ControlPanel.init">
	<div class="wrap">
	${redirect}
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=${SiteID}">会员中心</a>  &raquo; 我的论坛积分
</div>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
<input type="hidden" value="${SiteID}" id="SiteID">
  <tr valign="top">
    <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>
	<div class="mainbox forumbox">
        <h4>积分记录</h4>
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20%">您目前总积分</td>
                <td>${ForumScore}</td>
            </tr>
            <tr>
                <td>会员等级</td>
                <td>普通会员</td>
            </tr>
            <tr>
                <td>升级提示</td>
                <td>您还需 85 个积分可升级</td>
            </tr>
            <tr>
                <td>创建时间</td>
                <td>2009-09-09</td>
            </tr>
            <tr>
                <td>上次登录</td>
                <td>49秒前</td>
            </tr>
        </table>
         <h4 style="border-top:1px solid #ccc;">积分增加规则</h4>
         <table border="0" cellspacing="0" cellpadding="0" class="forumTable">
         	<thead>
         	<tr>
            	<th width="25%">增加积分操作</th>
                <th width="25%">增加积分数</th>
                <th>次数</th>
            </tr>
            </thead>
            <tr>
            	<td width="25%">补充个人资料</td>
                <td width="25%">30</td>
                <td>1</td>
            </tr>
            <tr>
            	<td>更新头像</td>
                <td>15</td>
                <td>1</td>
            </tr>
            <tr>
            	<td>发表帖子</td>
                <td>10</td>
                <td>无限</td>
            </tr>
            <tr>
            	<td>回复帖子</td>
                <td>5</td>
                <td>无限</td>
            </tr>
            <tr>
            	<td>发布评论/留言</td>
                <td>3</td>
                <td>无限</td>
            </tr>
         </table>
	</div>
	</td></tr>
</table>
</div>
</sky:init>
<%@ include file="../Include/Bottom.jsp" %>

</body>

</html>