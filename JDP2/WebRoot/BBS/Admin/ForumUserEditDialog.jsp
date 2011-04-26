<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>
<script type="text/javascript">
	Page.onLoad(viewForums);
	function viewForums(){
		if($V("AdminID")==$V("ForumAdminID")){
			Tip.close($("tip"));
			Tip.show($("tip"),'如果您不属于任何一个版块，请在相应板块下设置');
           $("ForumsTr").show();
		}else{
			Tip.close($("tip"));
			if($V("AdminID")==$V("ForumVisitID")){
				Tip.show($("tip"),'如果您选择了\'禁止访问\'组,那么无论属于该用户的其他组是否允许访问论坛，该用户都将无法访问论坛');
			}
			$("ForumsTr").hide();
		}
	}
</script>
</head>
<sky:init method="com.xdarkness.bbs.admin.ForumUserInfo.initEditDialog">
<body>
<form id="form1">
	<input type="hidden" id="IDs" name="IDs" value="${IDs}">
	<input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
	<input type="hidden" id="flag" name="flag" value="${flag}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0" >
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" >
				<tr>
					<td align="right" width="28%">用户名:</td>
					<td align="left">${UserName}</td>
				</tr>
				<tr>
					<input type="hidden" value="${ID}" id="ID">
					<td align="right">用户组:</td>
					<td>${UserGroup}</td>
				</tr>
				<tr id="View">
					<td align="right">所属自定义组:</td>
					<td><sky:select name="DefinedID" id="DefinedID"
						style="width:100px;"><span value="0">无</span>${DefinedGroup}</sky:select>
					</td>
				</tr>
				<tr id="View">
					<td align="right">所属系统组:</td>
					<td><sky:select name="AdminID" id="AdminID"
						style="width:100px;" onChange="viewForums()"><span value="0">无</span>${SystemGroup}</sky:select>
						<input type="hidden" id="ForumAdminID" value="${ForumAdminID}" />
						<input type="hidden" id="ForumVisitID" value="${ForumVisit}" />
						<a href="#" id="tip" class="tip"
							onMouseOut="Tip.close(this)"
							onMouseOver="viewForums()"><img
							src="../../Framework/Images/icon_tip.gif" width="16" height="16"></a>
					</td>
					
				</tr>
				<tr id="ForumsTr"><td align="right">所属板块</td><td><div id="Forums">${Forums}</div></td></tr>
				<tr>
					<td align="right">发帖数:</td>
					<td><input type="text"  id="ThemeCount"  value="${ThemeCount}" Verify="NotNull&&Number"></td>
				</tr>
				<tr >
					<td align="right">积分:</td>
					<td><input type="text"  id="ForumScore"  value="${ForumScore}" Verify="NotNull&&Number"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
</body>
</sky:init>
</html>