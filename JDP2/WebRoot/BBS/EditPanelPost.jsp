<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="ForumInit.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=ForumUtil.getCurrentName(request)%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="forum.css" rel="stylesheet" type="text/css">
<!--<link href="skins/zving/default.css" rel="stylesheet" type="text/css">-->
<script src="../Editor/fckeditor.js"></script>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">


function insertFace(value){ 
	AddText(value,""); 
}

function AddBBCode(value){ 
	AddText("["+value+"]","[/"+value+"]");	
}
function AddText(str,str2){ 
   var ubb=document.getElementById("content"); 
   var ubbLength=ubb.value.length; 
   ubb.focus();  
   var strSelection = "";
   if(document.selection){
  	 strSelection = document.selection.createRange().text;
   }else{
	   if (ubb.selectionStart != undefined && ubb.selectionEnd != undefined) {	
		 var start = ubb.selectionStart;
		 var end = ubb.selectionEnd; 
		 strSelection = ubb.value.substring(start, end);
	   }else{
		 strSelection = "";
	   }
   }
   if(strSelection==""){    
  	 strSelection = str+str2; 
   }else{
  	 strSelection = str + strSelection + str2; 
   } 
   if(document.selection){ 
   	document.selection.createRange().text = strSelection; 
   }else{ 
   	ubb.value=ubb.value.substr(0,ubb.selectionStart)+strSelection+ubb.value.substring(ubb.selectionEnd,ubbLength); 
   } 
  }
  
function save(){
	 if(Verify.hasError()){
		return;		  
    }
	var dc = Form.getData($F("postform")); 
    var content = $("content").value;
	  if(content.length==0){
	  	Dialog.alert("内容不能为空");
	  	return;
	  }
    dc.add("Message",content);
    dc.add("ID",$V("PostID"));
	Server.sendRequest("com.xdarkness.bbs.PostAdd.editPost",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				Parent.$D.close();
				//Parent.eval("setTimeout(function(){$D.close()},100)");
			});
		}else{
			Dialog.alert(response.Message);
		}
	});
}
</script>

</head>
<body>

<sky:init method="com.xdarkness.bbs.PostAdd.initEdit">
	<form method="post" id="postform"
		action="post.jsp?action=newtheme&fid=5&extra=page%3D1&topicsubmit=yes"
		enctype="multipart/form-data"><input
		type="hidden" name="ForumID" id="ForumID" value="${ForumID}">
	<input type="hidden" name="ThemeID" id="ThemeID" value="${ThemeID}">
	<input type="hidden" name="PostID" id="PostID" value="${ID}">
	<div class="forumbox">
	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="table-layout: auto;"
		bordercolor="#eeeeee" class="forumTable">
		<thead>

		</thead>
		<tr>
			<th align="right" style="text-align:right"><label for="subject">标题</label></th>
			<td><input type="text" name="subject" id="subject" size="45"
				tabindex="3" verify="${verify}" value="${Subject}" ></td>
		</tr>

		<tr>
			<td align="right" valign="middle" width="120px">
			<div style="width:160px" class="face_post">
			<a href="javascript:void(0)"><img onclick="insertFace(':)')" value=":)" src="Images/face/smile.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':lol')" value=":lol" src="Images/face/lol.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':hug:')" value=":hug:" src="Images/face/hug.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':victory:')" value=":victory:" src="Images/face/victory.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':time:')" value=":time:" src="Images/face/time.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':kiss:')" value=":kiss:" src="Images/face/kiss.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':handshake')" value=":handshake" src="Images/face/handshake.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':call:')" value=":call:" src="Images/face/call.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':loveliness:')" value=":loveliness:" src="Images/face/loveliness.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':Q')" value=":Q" src="Images/face/mad.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':L')" value=":L" src="Images/face/sweat.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':(')" value=":(" src="Images/face/sad.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':D')" value=":D" src="Images/face/biggrin.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace('cry')" value=":'(" src="Images/face/cry.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':@')" value=":@" src="Images/face/huffy.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':o')" value=":o" src="Images/face/shocked.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':P')" value=":P" src="Images/face/tongue.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':$')" value=":$" src="Images/face/shy.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(';P')" value=";P" src="Images/face/titter.gif"/></a>
			<a href="javascript:void(0)"><img onclick="insertFace(':funk:')" value=":funk:" src="Images/face/funk.gif"/></a>
			</div>
			<!--<label for="posteditor_textarea">
			<b>内容</b> </label>
			<div id="posteditor_left">
			<ul>
				<li>Html 代码 <em>禁用</em></li>
				<li><a href="faq.jsp?action=message&id=18" target="_blank">Discuz!代码</a>
				<em>可用</em></li>
				<li>[img] 代码 <em>可用</em></li>
			</ul>
			<div style="clear: both;">
			<ul>
				<li><label><input type="checkbox" name="parseurloff"
					id="parseurloff" value="1"> 禁用 URL 识别</label></li>
				<li><label><input type="checkbox" name="smileyoff"
					id="smileyoff" value="1"> 禁用 <a
					href="faq.jsp?action=message&id=32" target="_blank">表情</a></label></li>
				<li><label><input type="checkbox" name="bbcodeoff"
					id="bbcodeoff" value="1"> 禁用 <a
					href="faq.jsp?action=message&id=18" target="_blank">Discuz!代码</a></label></li>
				<li><label><input type="checkbox" name="tagoff"
					id="tagoff" value="1"> 禁用 标签解析</label></li>
				<li><label><input type="checkbox" name="usesig"
					value="1"> 使用个人签名</label></li>
				<li><label><input type="checkbox" name="emailnotify"
					value="1"> 接收新回复邮件通知</label></li>
			</ul>
			</div>-->
            </td>
			<td valign="top">
			<div id="Toolbar" style="height:52px;" ></div>
                    <div style="width:100%;">
						<input type="button" value="Bold" onclick="AddBBCode('b')" title="粗体:[b]text[/b]" />
						<input type="button" value="Italic"	onclick="AddBBCode('i')" title="斜体:[i]text[/i]" />
						<input type="button" value="Underline"	onclick="AddBBCode('u')" title="下划线:[u]text[/u]" />
						<input type="button" value="Code"	onclick="AddBBCode('code')" title="代码:[u]text[/u]" />
						<input type="button" value="Url"	onclick="AddBBCode('url')" title="插入链接:[url]http://url[/url]" />
						<input type="button" value="Img"	onclick="AddBBCode('img')" title="插入图片:[img]http://img_url[/img]" />
					</div>
					<div style="height:10px;"></div>
					<div>
						<textarea id="content" rows="80" cols="" style="width:98%"></textarea>
					</div>

	</td>
	</tr>
	<!--tr>
			<th>&nbsp;</th>
			<td colspan="6" align="middle"><input name="topicsubmit"
				type="button" value="修改" onClick="save();">&nbsp;&nbsp; &nbsp;</td>
		</tr-->
	</table>
	</div></form>
	</sky:init>
</body>
</html>
