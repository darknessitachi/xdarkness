
<%@page import="com.abigdreamer.java.net.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html style="overflow:auto">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=Config.getAppCode()%><%=Config.getAppName()%></title>
<link rel="shortcut icon" href="Include/favicon.ico" type="image/x-icon" />
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<script src="Framework/Main.js" type="text/javascript"></script>
<script src="Application.jsp.js" type="text/javascript"></script>
</head>
<body style="min-width:1003px">
<sky:init method="com.xdarkness.platform.page.ApplicationPage.init">
	<table id="_TableHeader" width="100%" border="0" cellpadding="0"
		cellspacing="0" class="bluebg"
		style="background:#3388bb url(Platform/Images/vistaBlue.jpg) repeat-x left top;">
		<tr>
			<td height="70" valign="bottom">
			<table height="70" border="0" cellpadding="0" cellspacing="0"
				style="position:relative;">
				<tr>
					<td style="padding:0">
					<!-- <img src="Platform/Images/logo4.gif"> -->
					</td>
				</tr>
				<tr>
					<td valign="bottom" nowrap="nowrap">
					<div style=" float:left; background:url(Platform/Images/selectsitebg.gif) no-repeat right top; color:#666666; padding:6px 23px 0 10px; margin-bottom:-2px;"><span
						id="selectsite" style="display:inline">当前站点: <sky:select
						id="_SiteSelector" style="width:130px;" listWidth="250"
						onChange="Application.onParentSiteChange();">
					 ${Sites} 
               </sky:select> </span> <img src="Platform/Images/selectsite_hide.gif"
						width="13" height="13" align="absmiddle" style="cursor:pointer;"
						title="隐藏"
						onClick="a=$('selectsite').style.display=='none'?false:true;$('selectsite').style.display=a?'none':'inline';this.src=a?'Platform/Images/selectsite_show.gif':'Platform/Images/selectsite_hide.gif';this.title=a?'展开':'隐藏'"></div>
					</td>
				</tr>
			</table>
			</td>
			<td valign="bottom">
			<div style="text-align:right; margin:0 20px 15px 0;">当前用户：<b><%=User.getUserName()%></b>
			&nbsp;[&nbsp;<a href="javascript:void(0);" onClick="getMessage()">短消息(<span
				id="MsgCount">0</span>)</a> | <a href="javascript:void(0);"
				onClick="Application.logout();">退出登录</a> | <a
				href="javascript:Application.changePassword(0);">修改密码</a> ]</div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
					<div id="_Navigation" class="navigation">
						${Menu}
					</div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding:6px 3px 3px 3px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="2" height="2"
						style="background:url(Platform/Images/jiao.gif) no-repeat right bottom;"></td>
					<td width="100%" id="_HMenutable" class="tabpageBar"></td>
				</tr>
				<tr valign="top">
					<td align="right" id="_VMenutable" class="verticalTabpageBar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right" valign="bottom">
							<div id="_ChildMenu"></div>
							</td>
						</tr>
					</table>
					</td>
					<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						id="maintable"
						style="border-bottom:#999999 1px solid; border-right:#999999 1px solid;">
						<tr>
							<td><iframe id='_MainArea' frameborder="0" width="100%"
								height="500" src='about:blank' scrolling="auto"></iframe></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		var privDC = new DataCollection();
		privDC.parseXML(toXMLDOM(htmlDecode("${Privileges}")));
		parserPriv(privDC);
	</script>
</sky:init>
</body>
</html>
