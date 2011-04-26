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
	$("Logo").src = $DW.Icon;
	$D.close();
}

function goBack(params){
	alert(params)
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览列表页模板";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 800;	
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.xdarkness.cms.site.Catalog.getPicSrc",dc,function(response){
		$("PicSrc").value = response.get("picSrc");
		$S("ImagePath",response.get("ImagePath"));
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function setRule(){
	var diag=new Dialog("diag3");
	diag.Width=500;
	diag.Height=230;
	diag.Title="设置详细页命名规则";
	diag.URL = "Site/CatalogDetailNameRuleDialog.jsp";
	diag.onLoad = function(){
		$DW.$S("DetailNameRule","/${catalogpath}/${document.id}.shtml");
	};
	diag.OKEvent=editDetailNameRule;
	diag.show();
}

function editDetailNameRule(){
	$S("DetailNameRule", $DW.$V("DetailNameRule"));
	$D.close();
}
function setListRule(){
   var diag=new Dialog("diag3");
   diag.Width=500;
   diag.Height=230;
   diag.Title="设置多媒体文件存储规则";
   diag.URL = "Site/CatalogListNameRuleDialog.jsp";
   diag.onLoad = function(){
     $DW.$S("ListNameRule","/${catalogpath}/");
	};
   diag.OKEvent=editListNameRule;
   diag.show();
}

function editListNameRule(){
   $S("ListNameRule", $DW.$V("ListNameRule"));
   $D.close();
}

function setURLRule(){
   var diag=new Dialog("diag3");
   diag.Width=500;
   diag.Height=230;
   diag.Title="设置文件存储规则";
   diag.URL = "Site/CatalogURLNameRuleDialog.jsp";
   diag.onLoad = function(){
     $DW.$S("URL","/${catalogpath}/");
	};
   diag.OKEvent=editURLNameRule;
   diag.show();
}

function editURLNameRule(){
   $S("URL", $DW.$V("URL"));
   $D.close();
}

function changeKeywordFlag(){
	if($("KeywordFlag").checked){
		$("spanSetting").style.display="";
	}else{
		$("spanSetting").style.display="none";
	}
} 

function browseImage(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = onImageSelected;
	diag.show();
}

function onImageSelected(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}
function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.xdarkness.cms.site.CatalogExtend.getPicSrc",dc,function(response){
		$("CatalogImage").src = response.get("picSrc");
		$("ImagePath").value = response.get("ImagePath");
	})
}

function changeSingleFlag(FlagType){
	if(FlagType=="Y"){
		$("IndexTemplateTr").style.display="none";
		$("ListTemplateTr").style.display="none";
	}else{
		$("IndexTemplateTr").style.display="";
		$("ListTemplateTr").style.display="";
	}
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<sky:init method="com.xdarkness.cms.site.Catalog.initDialog">
<body class="dialogBody">
	<form id="form2">
	<input type="hidden" id="ID" value="${ID}" />
	<input type="hidden" id="ParentID" value="${ParentID}" /> 
	<input type="hidden" id="SiteID" value="${SiteID}" />
	<input type="hidden" id="Level" value="${Level}" /> 
	<input type="hidden" id="Type" value="${Type}"> 
	<input type="hidden" id="ChildCount" value="${ChildCount}" /> 
	<input type="hidden" id="IsLeaf" value="${IsLeaf}" />
	<table width="740" align="center" cellpadding="4" cellspacing="0">
		<tr valign="top">
			<td align=center>
			<fieldset><legend> 基本属性 </legend>
			<table width=100%>
				<tr>
					<td width="19%" height="35" align="right">上级结点：</td>
					<td width="81%" align="left"><input disabled name="ParentName"
						type="text" class="input1" id="ParentName" value="${ParentName}"
						size="30" />
				    <span class="gray">上级结点为您点击新建按钮时选中的栏目树结点</span></td>
			    </tr>
				<tr>
					<td width="19%" height="35" align="right">栏目名称：</td>
					<td align="left"><input name="Name" type="text"
						class="input1" id="Name" value="${Name}" size="30"
						onChange="setAlias();" onBlur="setAlias();" verify="名称|NotNull" />
				    <span class="gray">栏目在后台显示的名称</span></td>
			    </tr>
				<tr>
					<td height="35" align="right">栏目目录名：</td>
					<td align="left"><input name="Alias" type="text"
						class="input1" id="Alias" value="${Alias}" size="30"
						verify="栏目英文名|NotNull&&请输入长度在2-20位之间的字符|Regex=^[\w]{2,20}$" />
				    <span class="gray">上级结点目录下存放本栏目静态化文件的子目录名称</span></td>
			    </tr>
				<tr>
					<td height="35" align="right">栏目目录URL：</td>
					<td align="left"><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40"/> <input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setURLRule();">
				    <span class="gray"><br>
				    栏目文件存放相对于站点目录的位置；<br>
				    如果填写是以http://或https://开头的链接地址，栏目则链接到指定位置</span></td>
			    </tr>
				<tr>
				<td height="35" align="right">引导图片：</td>
					<td align="left">
					<a href="#;" onClick="browseImage()"><font id="PlayerName" color="#FF0000"><img src="../Images/addpicture.jpg" name="CatalogImage" width="80" border=0 id="CatalogImage">
					<input type="hidden" ID="ImagePath" value="${ImagePath}"></font><br>设置引导图</a>
			      </td>
			    </tr>
			</table>
			</fieldset>
			<fieldset>
			<legend>模板及命名 </legend>
			<table width=100%>
              <tr id="IndexTemplateTr">
                <td width="19%" height="35" align="right">栏目首页模板：</td>
                <td width="81%" align="left"><input name="IndexTemplate" type="text"
						class="input1" id="IndexTemplate" value="${IndexTemplate}"
						size="30" />
                  <input type="button" name="Submit" value="浏览..."
						onClick="browse('IndexTemplate')">
                  ${edit}<br>
                  <span class="gray">栏目首页使用的模板文件，可以不填写。该模板会静态化成栏目目录下的index.shtml文件</span></td>
              </tr>
              <tr id="ListTemplateTr">
                <td height="35" align="right">栏目列表页模板：</td>
                <td align="left"><input name="ListTemplate" type="text"
						class="input1" id="ListTemplate" value="${ListTemplate}" size="30">
                    <input name="button" type="button" onClick="browse('ListTemplate');" value="浏览..." />
                ${edit1}<span class="gray"><br>
                栏目列表页使用的模板文件，如果有设置栏目首页模权，则列表页的第一页会静态化为index.shtml；<br>
                如果没有设置栏目首页模板，则列表页第一页会静态化为list.html</span></td>
              </tr>
              <tr>
                <td height="35" align="right">文档详细页模板：</td>
                <td align="left"><input name="DetailTemplate" type="text"
						class="input1" id="DetailTemplate" value="${DetailTemplate}"
						size="30" />
                    <input name="button" type="button" 
						onClick="browse('DetailTemplate');" value="浏览..." />
                ${edit2}<span class="gray">栏目下的文档静态化时使用的模板文件</span></td>
              </tr>
			  <%if(Current.getInt("InitParams.Type")>=4&&Current.getInt("InitParams.Type")<=7){%>
              <tr>
                <td height="35" align="right">多媒体文件存储规则：</td>
                <td align="left"><input name="ListNameRule" type="text" class="input1"
						id="ListNameRule" value="${ListNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setListRule();">
                <span class="gray">栏目下的文件存储规则</span> </td>
              </tr>
			  <%}%>
              <tr>
                <td height="35" align="right">详细页命名规则：</td>
                <td align="left"><input name="DetailNameRule" type="text" class="input1"
						id="DetailNameRule" value="${DetailNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setRule();">
                <span class="gray">栏目下的文档详细页存储规则</span> </td>
              </tr>
              <tr>
                <td height="35" align="right">Rss模板：</td>
                <td align="left"><input name="RssTemplate" type="text"
						class="input1" id="RssTemplate" value="${RssTemplate}" size="30" />
                    <input name="button" type="button" 
						onClick="browse('RssTemplate');" value="浏览..." />
                <span class="gray">生成RSS文件使用的模板文件</span></td>
              </tr>
              <tr>
                <td height="35" align="right">模板沿用：</td>
                <td align="left"><sky:select>
                  <select name="select" id="Extend" style="width:150px;">
                    <option value="1">仅本栏目</option>
                    <option value="2">所有子栏目沿用相同设置</option>
                  </select>
                </sky:select>
                <span class="gray">本次模板设置影响的范围</span></td>
              </tr>
            </table>
			</fieldset>
			<fieldset>
			<legend>工作流 </legend>
			<table width=100%>
              <tr>
                <td width="19%" height="35" align="right">工作流：</td>
                <td width="81%" align="left"><sky:select id="Workflow" style="width:150px;"> ${Workflow} </sky:select>                
                  <span class="gray">本栏目下文档审核使用的工作流，如果没有设置，则可以直接发布</span></td>
              </tr>
              <tr>
                <td height="35" align="right">工作流沿用：</td>
                <td align="left"><sky:select id="WorkFlowExtend" style="width:150px;">
                  <select name="select2" id="WorkFlowExtend" style="width:150px;">
                    <option value="1">仅本栏目</option>
                    <option value="2">所有子栏目沿用相同设置</option>
                  </select>
                  </sky:select><span class="gray">本次工作流设置影响的范围</span></td>
              </tr>
            </table>
			</fieldset>
			<fieldset>
			<legend>其他属性 </legend>
			<table width="100%" style="margin:3px auto">
				<tr>
					<td width="19%" height="25" align="right">列表页显示文档数：</td>
					<td width="81%" align="left"><input name="ListPageSize" type="text" class="input1" style="width:150px;" verify="Int"
						id="ListPageSize" value="${ListPageSize}" size="20" />
						<span class="gray">列表页显示多少条记录，如果模板中有指定pagesize属性，则以模板为准</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">列表页最大分页数：</td>
					<td align="left"><input name="ListPage" type="text" class="input1" style="width:150px;" verify="Int"
						id="ListPage" value="${ListPage}" size="20" />
						<span class="gray">列表页最多显示多少页，小于等于0的数字表示无限制，按实际页数来生成</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">Meta关键字：</td>
					<td align="left"><input name="Meta_Keywords" type="text" class="input1"
						id="Meta_Keywords" value="${Meta_Keywords}" size="40"/>
				    <span class="gray">可以在模板中使用${Catalog.Meta_Keywords}，以便于SEO</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">Meta描述：</td>
					<td align="left"><textarea id="Meta_Description" name="Meta_Description" 
						style="width:220px;height:60px">${Meta_Description}</textarea>
				    <span class="gray">可以在模板中使用${Catalog.Meta_Description}，以便于SEO</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">是否开放显示：</td>
					<td align="left">${PublishFlag}&nbsp;&nbsp;<span class="gray">如果不开放显示，则栏目将不出现在栏目列表和导航中</span></td>
			    </tr>
				<tr>
					<td height="25" align="right">是否单页栏目：</td>
					<td align="left"><input class="inputRadio" name="SingleFlag" id="SingleFlag_0" value="Y" type="radio" ${SingleFlagY} onClick="changeSingleFlag('Y')"><label for="SingleFlag_0">是</label><input class="inputRadio" name="SingleFlag" id="SingleFlag_1" value="N" ${SingleFlagN} type="radio" onClick="changeSingleFlag('N')"><label for="SingleFlag_1">否</label>&nbsp;&nbsp;<span class="gray">如果是单页栏目则栏目列表和导航中的栏目链接指向栏目下的第一篇文章</span></td>
			    </tr>
                <tr>
					<td height="25" align="right">是否允许投稿：</td>
					<td align="left">${AllowContribute}<span class="gray">&nbsp;&nbsp;是否允许前台会员通过投稿功能添加文章</span></td>
			    </tr>
				<tr>
				  <td height=35 align="right">相关文章设置：</td>
				  <td align="left"><input type="hidden" id="hKeywordFlag" value="${KeywordFlag}">
                      <label for="KeywordFlag">
                      <input name="KeywordFlag" type="checkbox" id="KeywordFlag" value="Y" onclick="changeKeywordFlag()">
                        允许文章智能关联 </label>
                    <span id="spanSetting" style="display:none">关联分类：
                        <input name="KeywordSetting"  id="KeywordSetting"  value="${KeywordSetting}" size="10"/>
                    </span> <span class="gray"><br>
                    如果允许文章智能关联并设置了关联分类，则在文章编辑器里可以使用“相关文章”-“智能相关”功能</span></td>
				</tr>
			</table>
			</fieldset>			</td>
	    </tr>
	</table>
	</form>
	</body>
	</sky:init>
</html>
