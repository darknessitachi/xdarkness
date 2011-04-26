<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.xdarkness.framework.User"%>
<%@taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.cms.dataservice.CommentService.init">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论</title>
<script src="../Framework/Main.js" type="text/javascript"></script>
<script>
var flag = 0;

function reply(ID,CatalogID,RelaID,CatalogType,SiteID){
	if(flag==0) {
		var subStr='${LoginHTML}';
		var str = "<form action='Comment.jsp' method='post' "+
				"onsubmit='return checkComment();'><input type='hidden' "+
				"id='RelaID' name='RelaID' value='"+RelaID+"' /> <input type='hidden' "+
				"id='CatalogID' name='CatalogID' value='"+ CatalogID +"' /> <input "+
				"type='hidden' id='CatalogType' name='CatalogType' value='"+CatalogType+"' /> "+
				"<input type='hidden' id='SiteID' name='SiteID' value='"+SiteID+"' /> "+
				"<input type='hidden' id='ParentID' name='ParentID' value='"+ID+"'/> "+
				"<textarea cols='78' rows='8' name='CmntContent' "+
				"id='CmntContent'></textarea><br/>";
		str+=subStr;
		str+="<input type='submit' align='right' name='CmntSubmit' "+
				"id='CmntSubmit' value='提交评论'	class='btn' /></form>";
		$(ID+"textarea").innerHTML=str;
		flag = 1;
	} else {
		$(ID+"textarea").innerHTML="";
		flag = 0;
	}
}

function updateComment(ID,AddUser,AddTime,AddUserIP) {
	var dc = new DataCollection();
	dc.add("ID",ID);
	dc.add("replyContent",$V('CmntContent'));
	dc.add("replyAddUser",AddUser);
	dc.add("replyAddTime",AddTime);
	dc.add("replyAddUserIP",AddUserIP);
	
	Server.sendRequest("com.xdarkness.cms.dataservice.Comment.updateSupporterCount",dc,function(response){
		if(response.Status==1){	
			Dialog.alert(response.Message);
			window.parent.location.reload();
		}
	});
}

function addSupporterCount(ID){
	
	var id = ID+"supporter";
	var dc = new DataCollection();
	dc.add("ID",ID);
	
	Server.sendRequest("com.xdarkness.cms.dataservice.Comment.addSupporterCount",dc,function(response){
		  if(response.Status==1){
		  	$(id).innerHTML = "(" + response.get("count") + ")";
    		// alert("您的评论提交成功！");
    		Dialog.alert(response.Message);
		  }
	});
}

function addAntiCount(ID){
	var id = ID+"anti";
	var dc = new DataCollection();
	dc.add("ID",ID);
	
	Server.sendRequest("com.xdarkness.cms.dataservice.Comment.addAntiCount",dc,function(response){
	  	if(response.Status==1){
	  		$(id).innerHTML = "(" + response.get("count") + ")";
	  		// alert("您的评论提交成功！");
	  		Dialog.alert(response.Message);
	  	}
	});
}
</script>
<!-- comment header begin -->
<script>
function checkComment(){
  var content = document.getElementById("CmntContent");
   if(content.value.trim() == ""){
     alert("评论内容不能为空!");
     content.focus();
     return false;
   }
  return true;
}
</script>
<style type="text/css">
.commentContainer{
	width:100%;
	max-width:950px;
	margin:10px auto;
	font:12px "宋体";
	text-align:left;
}
.commentBox{
	border:1px solid #c8d8f2;
	background-color:#f5f8fd;
	padding:17px 10px;
}
.commentBox h2{
	background:url(../Images/comment.gif) no-repeat left 2px;
	font-size:14px;
	margin:0;
	padding:0 0 9px 15px;
	border-bottom:1px dashed #c8d8f2;
}
.commentBox h2 a{
	 float:right;
	 _display:inline;
	 margin-top:-10px;
	 *margin-top:-30px;
	 font-size:12px;
	 color:#000099;
	 text-decoration:none;
}
.commentBox h2 a span{
	color:#cc0000;
}
.commentBox h2 a:hover, .commentBox h2 a:active{
	color:#c00;
	text-decoration:underline;
}
.commentContent{_padding-bottom:1px}
.commentContent .time{
	color:#666;
	line-height:20px;
	padding:4px 0 0 5px;
}
.commentContent .content{
	line-height:20px;
	padding:2px 0 2px 5px;
}
.commentContent .fenxiang{
	color:#0033cc;
	float:right;
	height:21px;
	padding:3px 3px 0;
	text-align:center;
}
.commentContent .fenxiang a{
	color:#0033cc;
	text-decoration:none;
}
.commentContent .fenxiang a:hover, .commentContent .fenxiang a:active{
	text-decoration:underline;
}
.commentContent .line{
	clear:both;
	background:url(../Images/line.gif) repeat-x left top;
	height:1px;
	font-size:1px;
}
.commentContent .page{
	margin:10px 0;
	padding:5px;
	text-align:center;
}
.commentContent .page a{
	font-weight:bold;;
	display:inline-block;
}
.commentContent .page a:link, .commentContent .page a:visited{
	color:#000;
}
.commentContent .page a:hover, .commentContent .page a:active{
	color:#cc0000;
	text-decoration:underline;
}
.commentContent .page a.pagefirst:link, .commentContent .page a.pagefirst:visited{
	text-decoration:underline;
	color:#cc0000;
}
.commentContent .page .pagebtn{
	display:inline-block;
	width:53px;
	border:1px solid #ccc;
	background-color:#fff;
	padding:2px 3px;
	font-weight:normal;
	text-decoration:none;
}
.commentContent form{
	margin:0;
}
.commentContent .textBox textarea{
	width:98%;
	height:100px;
	margin:10px 1%;
	_margin:10px 0.5%;
}
.commentContent input.txt{
	width:100px;
}
.commentContent input.btn{
	width:130px;
}
.comment{ background-color:#f5f8fd; text-align:center; padding:10px; text-align:left; word-wrap:break-word; word-break:break-all;}
.comment div,
.comment div.huifu{ padding:2px; margin:2px;}
.comment div.huifu{ border:1px solid #aaa; padding:2px; background-color:#FEFEED; margin:0 auto; text-align:left;}
.comment div.time,
.comment div.content{ margin:0; padding:2px;}
</style>
</head>
<body>
<div class="commentContainer">
	<div id="commentBox" class="commentBox">

	<h2>网友评论 <a
		href="${ServicesContext}${CommentListPageJS}?RelaID=${RelaID}&CatalogID=${CatalogID}&CatalogType=${CatalogType}&SiteID=${SiteID}"
		target="_blank">已有<span id="CmntCount"><script src="${ServicesContext}${CommentCountJS}?RelaID=${RelaID}&SiteID=${SiteID}"></script></span>位网友发表评论</a>
	</h2>
	<div id="commentContent" class="commentContent">
	<!-- comment header end -->
	<sky:datalist
		id="dg1" method="com.xdarkness.cms.dataservice.CommentService.dg1DataBind"
		size="10" page="true">

		<!-- comment loop begin -->

		<div class="time">${AddUser} ${AddTime} IP:${AddUserIP}</div>
		<div class="comment">${Content}</div> 
		<!-- comment loop end -->
		<div>
        	<div class="fenxiang">
            	<img src="../Images/fenxiang.gif" width="11" height="11" />[<a href="javascript:void(1)" onclick="addAntiCount(${ID})">反对</a><span id="${ID}anti">(${antiCount})</span>]
            </div>
            <div class="fenxiang">
            	<img src="../Images/fenxiang.gif" width="11" height="11" />[<a href="javascript:void(1)" onclick="reply('${ID}','${CatalogID}','${RelaID}','${CatalogType}','${SiteID}')">回复</a>]
            </div>
            <div class="fenxiang">
            	<img src="../Images/fenxiang.gif" width="11" height="11" />[<a href="javascript:void(1)" onclick="addSupporterCount(${ID})">支持</a><span id="${ID}supporter">(${supporterCount})</span>]
            </div>
            <div id="${ID}textarea"></div>
            
            <div class="line"></div> 
        </div>
	</sky:datalist>
	<div class="page"><sky:pagebar target="dg1" type="1" /><script type="text/javascript">document.getElementById("_PageBar_dg1").style.backgroundColor="#f5f8fd";</script></div>

	<!-- comment footer begin -->
	<div class="textBox">
	<form style="width: 100%;" action="${ServicesContext}${CommentActionURL}" method="post"
		onsubmit="return checkComment();"><input type="hidden"
		id="RelaID" name="RelaID" value="${RelaID}" /> <input type="hidden"
		id="CatalogID" name="CatalogID" value="${CatalogID}" /> <input type="hidden" id="CatalogType" name="CatalogType"
		value="${CatalogType}" /> <input type="hidden" id="SiteID"
		name="SiteID" value="${SiteID}" /> <textarea name="CmntContent"
		id="CmntContent"></textarea>
        <span style="float:right;padding-right:5px;"><input type="submit" name="CmntSubmit" id="CmntSubmit" value="提交评论" class="btn" /></span>
		${LoginHTML}
		<!-- end div -->
		</form>
	</div>
	</div>
	</div>
	<!-- comment footer end -->
</div>
</body>
</html>
</sky:init>