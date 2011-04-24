<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">

function selectIcon(){
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "选择图标";
	diag.URL = "Platform/Icon.jsp";
	diag.onLoad = function(){
	};
	diag.show();
}

function afterSelectIcon(){
	$("LogoFileName").src = $DW.Icon;
	$D.close();
}

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览列表页模板";
	diag.URL = "Site/TemplateExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function browseFile(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览文件";
	diag.URL = "Site/FileExplorer.jsp?SiteID="+$V("ID")+"&Alias=default";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.Site.initDialog">
	<form id="form2" method="post">
	<table width="740" cellpadding="2" cellspacing="0">
		<tr>
			<td width="135"></td>
			<td width="263" height="10"></td>
			<td width="328"></td>
		</tr>
		<tr>
			<td align="right">站点名称：</td>
			<td><input name="Name" type="text" class="input1" id="Name"
				onBlur="setAlias();" onChange="setAlias();" value="${Name}"
				size="40" verify="站点名称|NotNull" />
                <input name="ID" type="hidden"
				id="ID" value="${ID}" /></td>
			<td class="gray">站点在后台显示的名称</td>
		</tr>
		<tr>
			<td align="right">站点目录名：</td>
			<td><input name="Alias" type="text" class="input1" id="Alias"
				value="${Alias}" size="40"
				verify="英文名称|NotNull&&只允许大小写字母、数字、英文句号、下划线及英文破折线|Regex=^[\w\.\_\-]+$" /></td>
			<td><span class="gray">存放站点文件的目录名称，建议只使用数字和字母</span></td>
		</tr>
		<tr>
			<td align="right">站点描述：</td>
			<td><input name="Info" type="text" class="input1" id="Info"
				value="${Info}" size="40" /></td>
			<td><span class="gray">站点的其他备注信息</span></td>
		</tr>
		<tr>
			<td align="right">站点URL：</td>
			<td><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40" verify="URL|NotNull" /></td>
			<td><span class="gray">浏览者访问站点使用的URL</span></td>
		</tr>
		<tr>
			<td align="right">首页模板：</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" />
                <input
				type="button" onClick="browse('IndexTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">站点首页使用的模板文件</span></td>
		</tr>
		<tr>
			<td align="right">默认栏目列表页模板：</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30" />
                <input
				type="button" onClick="browse('ListTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">站点下栏目列表页默认使用的模板</span></td>
		</tr>
		<tr>
			<td align="right">默认文章详细页模板：</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30" />
                <input
				type="button" onClick="browse('DetailTemplate');"
				value="浏览..." /></td>
			<td><span class="gray">站点下文章详细页默认使用的模板文件</span></td>
		</tr>
		<tr>
			<td align="right">编辑器样式：</td>
			<td><input id="EditorCss" name="EditorCss" type="text"
				class="input1" value="${EditorCss}" size="30" />
                <input
				type="button" onClick="browseFile('EditorCss');"
				value="浏览..." />              </td>
		  <td class="gray">选择的样式文件将会被文章编辑器引入，使文章编辑时的效果更接近最后发布的效果</td>
		</tr>
		<tr>
          <td height="35" align="right">Meta关键字：</td>
		  <td align="left"><input name="Meta_Keywords" type="text" class="input1"
						id="Meta_Keywords" value="${Meta_Keywords}" size="40"/></td>
		  <td align="left" class="gray">可以在模板中使用${Site.Meta_Keywords}，以便于SEO</td>
	  </tr>
		<tr>
          <td height="25" align="right">Meta描述：</td>
		  <td align="left"><textarea id="Meta_Description" name="Meta_Description" 
						style="width:220px;height:60px">${Meta_Description}</textarea></td>
		  <td align="left" class="gray">可以在模板中使用${Site.Meta_Description}，以便于SEO</td>
	  </tr>
		
		<tr>
			<td height="30" align="right">是否自动索引：</td>
			<td>${radioAutoIndexFlag}</td>
			<td><span class="gray">只有自动索引选项开启，站内全文检索才可使用</span></td>
		</tr>
		<tr>
			<td height="30" align="right">是否自动统计：</td>
			<td>${radioAutoStatFlag}</td>
			<td><span class="gray">只有自动统计选项开启，站内统计分析才可使用。开启此选项后，系统会在每个静态化页面自动加入统计Javascript代码</span></td>
		</tr>
		<tr>
			<td height="30" align="right">评论是否需审核：</td>
			<td>${radioCommentAuditFlag}</td>
			<td><span class="gray">此选项开启后，所有文章评论都需要审核后才会显示</span></td>
		</tr>
		<tr>
			<td height="30" align="right">是否允许投稿：</td>
			<td>${radioAllowContribute}</td>
			<td><span class="gray">此选项开启后，注册会员将拥有投稿功能</span></td>
		</tr>
		<tr style="display:none">
			<td height="30" align="right">是否开启论坛：</td>
			<td>${radioBBSEnableFlag}</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="display:none">
			<td height="30" align="right">是否开启商城：</td>
			<td>${radioShopEnableFlag}</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		  <td height="35" align="right">动态应用头部引用：</td>
		  <td><input type="text" class="input1" id="HeaderTemplate" value="${HeaderTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('HeaderTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用(留言板、评论、搜索、调查结果等)的&lt;head&gt;与&lt;/head&gt;之间</td>
	  </tr>
		<tr>
		  <td height="35" align="right">动态应用顶部模板：</td>
		  <td><input name="TopTemplate" type="text" class="input1" id="TopTemplate" value="${TopTemplate}" size="30" />
              <input name="button" type="button" onClick="browse('TopTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用的&lt;body&gt;之下</td>
	  </tr>
		<tr>
		  <td height="35" align="right">动态应用底部模板：</td>
		  <td><input name="BottomTemplate" type="text" class="input1" id="BottomTemplate" value="${BottomTemplate}" size="30" />
              <input name="button2"	type="button" onClick="browse('BottomTemplate');" value="浏览..." />            </td>
		  <td class="gray">指定的模板文件的输出将会被放置到动态应用的&lt;/body&gt;之上</td>
	  </tr>
		<!--<tr>
			<td align="right">排序权值：</td>
			<td>&nbsp;</td>
			<td><input id="OrderFlag" name="OrderFlag" type="text"
				class="input1" value="${OrderFlag}" /></td>
		</tr>
		tr>
      <td>留言板标志：</td>
      <td>&nbsp;</td>
      <td>
      <label><input type="radio" name="MessageBoardFlag" value="Y"  />是</label>
      <label><input type="radio" name="MessageBoardFlag" value="N" checked/>否</label>      </td>
    </tr-->
	</table>
	</form>
</sky:init>
</body>
</html>
