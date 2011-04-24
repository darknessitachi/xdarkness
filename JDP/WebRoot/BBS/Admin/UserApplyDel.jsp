<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=ForumUtil.getCurrentName(request)%></title>
<style type="text/css">
#yinyong{ border:1px solid #ccc;}
#yinyong h2{ font-size:13px; font-weight:bold; color:#333; margin:0; padding:5px; border-bottom:1px solid #ccc; border-top:2px solid #ccc;}

#yinyong{ width:958px;}
.comment{ width:938px; background-color:#f5f8fd; text-align:center; padding:10px; text-align:left;}
.comment div,
.comment div.huifu{ padding:2px; margin:2px;}
.comment div.huifu{ border:1px solid #aaa; padding:2px; background-color:#FEFEED; margin:0 auto; text-align:left;}
.comment div.time,
.comment div.content{ margin:0; padding:2px;}
</style>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../forum.css" rel="stylesheet" type="text/css">
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
	function exeAudit() {
		var dc = Form.getData($F("form1"));
		var ids = "";
		var i = 0;
		while($("post_"+i)){
			if(i!=0){
				ids = ids+",";
			}
				ids+=$V("post_"+i);
			i++;
		}
		if(ids!=""){
			dc.add("ids",ids);
			Server.sendRequest("com.xdarkness.bbs.admin.PostAudit.exeApplyDel",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataList.loadData("list1");	
					}
				});
			});
		}
	}

	function changeAudit(flag) {
		DataList.setParam("list1", "auditFlag", flag);
		DataList.loadData("list1");
	}
	
	
	function changeOption() {
		DataList.setParam("list1", "ForumID", $V("ForumOptions"));
		DataList.setParam("list1", "TypeID", $V("TypeOptions"));
		DataList.setParam("list1", "First", $V("First"));
		DataList.loadData("list1");
	}
	
</script>
<style type="text/css">
.defaultpost { height: auto !important; height:120px; min-height:120px !important; }
.signatures { height: expression(signature(this)); max-height: 100px; }
</style>
</head>
<body>
	<form method="post" id="form1">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
	<tr><td>
	<sky:init method="com.xdarkness.bbs.admin.PostAudit.init">
		请选择：
		<sky:select id="TypeOptions" style="width:100px;" onChange="changeOption();">
			<span value="Y">未审核</span><span value="X">已忽略</span>
		</sky:select>
		<sky:select id="ForumOptions" style="width:100px;" onChange="changeOption();"><span value="0">所有板块</span>${ForumOptions}</sky:select>
	<sky:select id="First" style="width:100px;" onChange="changeOption();"><span value="Y">新主题</span><span value="N">新回复</span></sky:select>
	</sky:init></td>
	</tr><tr><td>
	<div style="padding:5px;">
		<sky:datalist id="list1" method="com.xdarkness.bbs.admin.PostAudit.getApplyDelPost" page="true" size="12">
		<div class="forumbox">
			<h4>${ForumName}>>${Subject}</h4>
			<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
				<tr>
					<td class="postauthor" valign="middle" align="center">
						<div id="userinfo17435_menu" style="valign:middle">
							<ul>
								<li>${AuditFlag}</li>
							</ul>
						</div>
					</td>
					<td class="postcontent" ondblclick="edit(${ID})">
				    	<div ><h5>${Subject}</h5>
				    	<input type="hidden" id="post_${StatusCount}" value="${ID}">
							<div id="edit${ID}">
								作者：${AddUser} 时间：${AddTime}<br/>
								${Message}
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		</sky:datalist>
		<input type="hidden" id="auditFlag" value="Y">
	</div>
	<input type="button" name="submit" value="提交" onclick="exeAudit();">
	<a href="javascript:void(0)" onclick="changeAudit('N');">全部通过</a>&nbsp;
	<a href="javascript:void(0)" onclick="changeAudit('Z');">全部拒绝 </a>&nbsp;
	<a href="javascript:void(0)" onclick="changeAudit('X');">全部忽略</a>
	</td></tr>
	</table>
	</form>

<sky:pagebar target="list1"></sky:pagebar>
</body>
</html>
