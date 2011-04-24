<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="ForumInit.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=ForumUtil.getCurrentName(request)%></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="forum.css" rel="stylesheet" type="text/css">
<!--<link href="skins/zving/default.css" rel="stylesheet" type="text/css">-->
<%@ include file="../Include/Head.jsp" %>
<script>

function checkForum(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.xdarkness.bbs.Forum.check",dc,function(response){		
				if(response.Status==1){
					window.location = "./Theme.jsp?SiteID="+$V("SiteID")+"&ForumID="+ID;
				}
				if(response.Status==0){
					 password(ID);
				}
				if(response.Status==2){
					Dialog.alert(response.Message);
				}
		
		});
}
function password(ID){
var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 60;
	diag.Title = "输入密码";
	diag.URL = "BBS/PasswordDialog.jsp?ID="+ID;
	diag.OKEvent = submitPassword;
	diag.show();
}

function submitPassword(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = Form.getData($DW.$F("form1"))
	Server.sendRequest("com.xdarkness.bbs.Forum.checkPassword",dc,function(response){		
		if(response.Status==1){
				$D.close();
				window.location = "./Theme.jsp?ForumID="+dc.get("ID")+"&SiteID="+$V("SiteID");
		}
		if(response.Status==0){
			Dialog.alert(response.Message);
		}
		
	});
}

function userinfo(UserName){
	var dc = new DataCollection();
	dc.add("UserName",UserName);
	dc.add("SiteID",$V("SiteID"));
	Server.sendRequest("com.xdarkness.bbs.UserPersonalInfo.checkPriv",dc,function(response){
		if(response.Status==1){
			window.open("UserPersonalInfo.jsp?UserName="+UserName+"&SiteID="+$V("SiteID"));
		}else{
			
		}
	});
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp"%>
<sky:init method="com.xdarkness.bbs.Forum.init">
	<input type="hidden" id="SiteID" value="${SiteID}" >
	<div class="wrap">
	<div id="menu" class="forumbox"><span class="fl"> 欢迎<b> <a id="viewpro">${UserName}</a> </b>到来&nbsp;&nbsp;&nbsp;&nbsp; ${button}</span> <span class="fr">${Priv}</span></div>

	<sky:simplelist method="com.xdarkness.bbs.Forum.getList1">
		<div class="mainbox forumlist forumbox">
		  <h4><a href="Index.jsp?ForumID=${ID}&SiteID=${SiteID}"><b>${Name}</b></a></h4>
		<table width="100%" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
			<thead class="category">
				<tr>
					<th width="4%"></th>
					<th width="53%">版块</th>
					<th width="4%" align="center">主题</th>
					<th width="4%" align="center">帖数</th>
					<th width="25%">最后发表</th>
					<th width="10%">版主</th>
				</tr>
			</thead>
			<sky:simplelist method="com.xdarkness.bbs.Forum.getList2">
				<tbody>
					<tr valign="middle">
						<td align="center"><img src="../Icons/icon_board.gif"></td>
						<td align="left"><a href="javascript:void(0);" onclick="checkForum(${ID})"><b>${Name}</b></a>
						<span style="color:#777">${Info}</span>
						</td>
						<td>${ThemeCount}</td>
						<td>${PostCount}</td>
						<td nowrap="nowrap">${LastPostInfo}</td>
						<td>${ForumAdmin}</td>
					</tr>
				</tbody>
			</sky:simplelist>
		</table>
		</div>
	</sky:simplelist></div>
</sky:init>
<%@ include file="../Include/Bottom.jsp"%>
</body>
</html>
