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
<script src="../Framework/Controls/StyleToolbar.js"></script>
<script src="../Editor/fckeditor.js"></script>
 <script src="../Framework/Spell.js"></script>
 <%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">
iIndex = 0;
var annex = 0;

function afterUpload(pathAll){
    if(Verify.hasError()){
		return;		  
    }
	var dc = Form.getData($F("postform"));
	var indexs = "";
	for(var i=0;i<=annex;i++){
		if(dc.get("file"+i)){
			indexs += i+",";
		}
	}
	dc.add("indexs",indexs);
	dc.add("file",pathAll);	
	dc.add("SiteID",$V("SiteID"));	
    var content = $("content").value;
	  if(content.length==0){
	  	Dialog.alert("内容不能为空");
	  	return;
	  }
    dc.add("Message",content);
	Server.sendRequest("com.xdarkness.bbs.PostAdd.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location = "./Post.jsp"+window.location.search;
			}
		});
	});
}
function check(){
	var dc = Form.getData($F("postform"));
	editor = FCKeditorAPI.GetInstance('Content');
    var content = editor.GetXHTML(true);
    Dialog.alert(content.length);
}

function getIndex()
{
 iIndex = event.srcElement.parentElement.parentElement.rowIndex;
 return iIndex;
}


function insertRow(iPos)
{
 var myTable = document.getElementById("myTable");
 var otr=myTable.insertRow(annex);//插入一个tr

 var ocell=otr.insertCell(0);//插入一个td
    ocell.innerHTML="<label for='fileupload'>文件:</label> <input type='file'  id='file"+annex+"' size='30' name=annexValue(" + annex + ")>";
 
 var ocell=otr.insertCell(1);//插入一个td
 ocell.innerHTML=" <input type=button onclick=deleteRow(getIndex(this)) value='删除附件'>";
 
 annex++;
}

function deleteRow(iPos)
{
 var myTable = document.getElementById("myTable");
 myTable.deleteRow(iPos);
 annex--;
}

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


</script>

</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<sky:init method="com.xdarkness.bbs.PostAdd.init">
<input type="hidden" value="${SiteID}" id="SiteID">
<div id="menu" class="forumbox"><span class="fl"> <a href="Index.jsp?SiteID=${SiteID}">${BBSName}</a> &raquo; <a
	href="Theme.jsp?ForumID=${ForumID}&Name=${Name}&SiteID=${SiteID}">${Name}</a> &raquo; <a
	href="Post.jsp?ThemeID=${ID}&ForumID=${ForumID}&SiteID=${SiteID}">${Subject}</a> &raquo; 引用回复 </span> <span class="fr">${Priv}</span>
</div>

<input type="hidden" value="${AddUser}" id="AddUser">
</sky:init>
<br>
<sky:init method="com.xdarkness.bbs.PostAdd.initAddDialog">
<div style="display:none"><iframe name="formTarget"  src="javascript:void(0)"></iframe></div>
	<form method="post" id="postform"
		action="AttachmentUpload.jsp?Path=/BBS/Upload/File/"
		target="formTarget" 
		enctype="multipart/form-data"><input
		type="hidden" name="ForumID" id="ForumID" value="${ForumID}">
	<input type="hidden" name="ThemeID" id="ThemeID" value="${ThemeID}">
	<input type="hidden" name="PostID" id="PostID" value="${ID}">
	<div class="forumbox">
	<h4>发表回复</h4>
	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="table-layout: auto;"
		bordercolor="#eeeeee" class="forumTable">
		<tr>
			<th align="right" style="text-align:right"><label for="subject">标题</label></th>
			<td><input type="text" name="subject" id="subject" size="45"
				tabindex="3" value="${subject}" ></td>
		</tr>

		<tbody id="themetypes"></tbody>

		<tr>
			<td align="right" valign="middle" width="160px">		 
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
                    <div style="width:100%;">
					<input type="button" value="粗体" onclick="AddBBCode('b')" title="粗体:[b]text[/b]" />
					<input type="button" value="斜体"	onclick="AddBBCode('i')" title="斜体:[i]text[/i]" />
					<input type="button" value="下划线"	onclick="AddBBCode('u')" title="下划线:[u]text[/u]" />
					<input type="button" value="代码"	onclick="AddBBCode('code')" title="代码:[u]text[/u]" />
					<input type="button" value="插入链接"	onclick="AddBBCode('url')" title="插入链接:[url]http://url[/url]" />
					<input type="button" value="插入图片"	onclick="AddBBCode('img')" title="插入图片:[img]http://img_url[/img]" />
					</div>
					<div style="height:10px;"></div>
					<div>
						<textarea id="content" rows="80" cols="" style="width:98%;height:300px"></textarea>
					</div>
			<table align="right" cellpadding="0" cellspacing="0">
				<!-- tr>
					<td>&nbsp;</td>
					<td align="right">
					<button type="button" id="checklength">字数检查</button>
					&nbsp;
					<button type="button" name="previewbutton" id="previewbutton"
						tabindex="102">预览帖子</button>
					&nbsp;
					<button type="reset" tabindex="103" id="clearcontent">清空内容</button>
					</td>
				</tr-->
			</table>
			<fieldset  class="fields2" style="clear:both;"><legend>上传附件</legend>
				<input type="button" value="添加附件" onClick="insertRow(0)">
				<table id="myTable"></table>
			</fieldset>
			</div>

			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td colspan="6" align="middle"><input name="topicsubmit"
				type="submit" value="发表新主题" > &nbsp;&nbsp; &nbsp;</td>
		</tr>
	</table>
	</div>
	</form>
</sky:init>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>










