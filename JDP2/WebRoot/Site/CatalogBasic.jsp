<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>栏目</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	if($V("InnerCode")){
		$("BtnSave").setAttribute("priv","article_manage");
		$("BtnAdd").setAttribute("priv","article_manage");
		$("BtnDel").setAttribute("priv","article_manage");
		$("BtnMove").setAttribute("priv","article_manage");
		$("BtnBatchAdd").setAttribute("priv","article_manage");
		$("BtnExportStructure").setAttribute("priv","article_manage");
		$("BtnClean").setAttribute("priv","article_manage");
		$("BtnPublish").setAttribute("priv","article_manage");
		Application.setAllPriv("article",$V("InnerCode"));
	}else{
		Application.setAllPriv("site",Application.CurrentSite);
	}
	if($V("hKeywordFlag") == "Y"){
		$("KeywordFlag").checked = true;
		$("spanSetting").style.display="";
	}
});

//鼠标点击当前页面时，隐藏右键菜单
var iFrame =window.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}	
});
var topFrame = window.parent;
function add(){
	if($NV('SingleFlag')=='Y'){
		Dialog.alert("单页栏目不能创建下级栏目!");
		return;
	}
	topFrame.add();	
}

function publish(){
	topFrame.publish();	
}

function publishIndex(){
	topFrame.publishIndex();	
}

function del(){
	topFrame.del();
}

function refreshTree(){
	window.location.reload();
}

function move(){
	topFrame.move();
}

function edit(param){
	topFrame.edit(param);	
}

function batchAdd(){
	if($NV('SingleFlag')=='Y'){
		Dialog.alert("单页栏目不能创建下级栏目!");
		return;
	}
	topFrame.batchAdd();
}

function batchDelete(){
	topFrame.batchDelete();
}

function copyConfig(){
	topFrame.copyConfig();
}
function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览列表页模板";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
		$(ele+"EditBtn").innerHTML = "<a href='javascript:void(0);' onclick=\"openEditor('"+params+"')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14'></a>";
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function save(){
	var dc = Form.getData("form1");
	dc.add("PublishFlag",$NV("PublishFlag"));
	dc.add("SingleFlag",$NV("SingleFlag"));
	dc.add("AllowContribute",$NV("AllowContribute"));
	dc.add("Extend",$V("Extend"));
	dc.add("WorkFlowExtend",$V("WorkFlowExtend"));
	dc.add("Workflow",$V("Workflow"));
	dc.add("ListPageSize",$V("ListPageSize"));
	if(!$("KeywordFlag").checked){
		dc.add("KeywordFlag","N");
		dc.add("KeywordSetting","");
	}
	
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.cms.site.Catalog.save",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("保存成功",function(){
				topFrame.Tree.loadData("tree1",function(){
			    var node = topFrame.Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
					//如果没有展开则载入
					if(!node){
						var code = Cookie.get("DocList.LastCatalogCode");
						while(code&&code.length>6){
							code = code.substring(0,code.length-6);
							node = topFrame.Tree.getNode("tree1","innercode",code);
							if(node){
								topFrame.Tree.lazyLoad(node,function(){
									var img = topFrame.Tree.getLastBranchIcon(node);
									topFrame.Tree.changeIcon(img,node);
									node = topFrame.Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
									topFrame.Tree.selectNode(node,true);
									topFrame.Tree.scrollToNode(node);
								});
								break;
							}
						}
					}else{
						topFrame.Tree.selectNode(node,true);
						topFrame.Tree.scrollToNode(node);
					}
				});
			});
		}
	});
}
function openEditor(path){
	topFrame.openEditor(path);
}

function preview(){
	topFrame.preview();
}

function clean(){
	topFrame.clean();
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

function exportStructure(){
	var diag = new Dialog("Diag4");
	diag.Width = 500;
	diag.Height = 350;
	diag.Title ="导出栏目结构";
	diag.URL = "Site/CatalogExportStructure.jsp?Type="+$V("Type")+"&ID="+$V("ID");
	diag.ShowMessageRow = true;
	diag.Message = "导出的文本可复制后再使用“批量导入”功能导入的其他站点或栏目下。";
	diag.show();
	diag.OKButton.hide();
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
</head>
<body>
<sky:init method="com.xdarkness.cms.site.Catalog.init">
	<div style="padding:2px;">
	<table width="100%" cellpadding='0' cellspacing='0'
		style="margin-bottom:4px;">
		<tr>
			<td><sky:button id="BtnSave" priv="article_manage"
				onClick="save('${ID}');">
				<img src="../Icons/icon002a4.gif" width="20" height="20" />保存</sky:button> <sky:button
				id="BtnAdd" priv="article_manage" onClick="add();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />新建子栏目</sky:button> <sky:button
				id="BtnDel" priv="article_manage" onClick="del();">
				<img src="../Icons/icon002a3.gif" width="20" height="20" />删除</sky:button> <sky:button
				id="BtnMove" priv="article_manage" onClick="move();">
				<img src="../Icons/icon002a7.gif" width="20" height="20" />移动</sky:button> <sky:button
				id="BtnBatchAdd" priv="article_manage" onClick="batchAdd();">
				<img src="../Icons/icon002a8.gif" width="20" height="20" />批量新建</sky:button>
				<sky:button
				id="BtnExportStructure" priv="article_manage" onClick="exportStructure();">
				<img src="../Icons/icon002a11.gif" width="20" height="20" />导出栏目结构</sky:button>
				<sky:button
				id="BtnClean" priv="article_manage" onClick="clean();">
				<img src="../Icons/icon002a9.gif" width="20" height="20" />清空</sky:button> <sky:button
				id="BtnPublish" priv="article_manage" onClick="publish();">
				<img src="../Icons/icon002a1.gif" />发布</sky:button> </td>
		</tr>
	</table>
	<form id="form1">
	<table width="600" border="1" cellpadding="1" cellspacing="0"
		bordercolor="#eeeeee">
		
		<tr id="tr_ID">
			<td width="22%" height="25">ID：</td>
		  <td width="78%">${ID}
			<input name="ID" type="hidden" id="ID" value="${ID}" />
			<input name="SiteID" type="hidden" id="SiteID" value="${SiteID}" />
			<input name="Type" type="hidden" id="Type" value="${Type}" />
			<input name="InnerCode" type="hidden" id="InnerCode"  value="${InnerCode}" /> 
			&nbsp;<span class="gray">(内部编码：${InnerCode})</span> </td>
		</tr>
		<tr>
			<td>栏目名称：</td>
		  <td><input name="Name" type="text" class="input1" id="Name"
				value="${Name}" size="30" verify="NotNull" />
		    <input name="BtnPreview" type="button" id="BtnPreview" value="预览" onclick="preview();">		 </td>
		</tr>
		<tr>
			<td>栏目目录名：</td>
			<td><input name="Alias" type="text" class="input1" id="Alias"
				value="${Alias}" size="30"
				verify="栏目英文名|NotNull&&请输入长度在2-20位之间的字符|Regex=^[\w]{2,20}$"/></td>
		</tr>
		<tr>
			<td>栏目目录URL：</td>
			<td><input name="URL" type="text" class="input1" id="URL"
				value="${URL}" size="40"/>
				 <input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setURLRule();">
				<a href="#" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'文件存放位置；如以http://或https://开头，栏目则链接到指定位置');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a>
				</td>
		</tr>
		<tr>
          <td height="35" >引导图片：</td>
		  <td align="left"><a href="#;" onClick="browseImage()"><font id="PlayerName" color="#FF0000"><img src="${ImageFullPath}" name="CatalogImage" width="80" border=0 id="CatalogImage">
		          <input type="hidden" ID="ImagePath" value="${ImagePath}">
		    </font><br>
	      设置引导图</a> </td>
	    </tr>
		<tr id="IndexTemplateTr" style="display:${IsDisplay}">
			<td>栏目首页模板：</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="选择.."
				onClick="browse('IndexTemplate')" /> <span id="IndexTemplateEditBtn">${edit}</span></td>
		</tr>
		<tr id="ListTemplateTr" style="display:${IsDisplay}">
			<td>列表页模板：</td>
			<td><input name="ListTemplate" type="text" class="input1"
				id="ListTemplate" value="${ListTemplate}" size="30"/> <input
				type="button" name="Submit" value="选择.."
				onClick="browse('ListTemplate')" /> <span id="ListTemplateEditBtn">${edit1}</span></td>
		</tr>

		<tr>
			<td>详细页模板：</td>
			<td><input name="DetailTemplate" type="text" class="input1"
				id="DetailTemplate" value="${DetailTemplate}" size="30"/> <input
				type="button" name="Submit" value="选择.."
				onClick="browse('DetailTemplate')" /> <span id="DetailTemplateEditBtn">${edit2}</span></td>
		</tr>
		<%if(Current.getInt("InitParams.Type")>=4&&Current.getInt("InitParams.Type")<=7){%>
              <tr>
                <td height="35">多媒体文件存储规则：</td>
                <td align="left"><input name="ListNameRule" type="text" class="input1"
						id="ListNameRule" value="${ListNameRule}" size="40" />
                    <input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setListRule();">
                <span class="gray">栏目下的文件存储规则</span> </td>
              </tr>
			  <%}%>		<tr>
			<td>详细页命名规则：</td>
			<td><input name="DetailNameRule" type="text" class="input1"
				id="DetailNameRule" value="${DetailNameRule}" size="40" />
				<input name="SetRuleButton" id="SetRuleButton" type="button" value="设置" onclick="setRule();">				</td>
			
		</tr>
		<tr>
			<td>Rss模板：</td>
			<td><input name="SiteID" type="text" class="input1"
				id="RssTemplate" value="${RssTemplate}" size="30" /> <input
				type="button" name="Submit" value="选择.."
				onClick="browse('RssTemplate')"> <span id="RssTemplateEditBtn">${edit3}</span></td>
		</tr>
		<tr>
			<td>模板沿用：</td>
			<td><sky:select>
				<select id="Extend" style="width:150px;">
					<option value="1">仅本栏目</option>
					<option value="2">所有子栏目沿用相同设置</option>
				</select>
			</sky:select></td>
		</tr>
		<tr>
			<td>工作流：</td>
			<td><sky:select id="Workflow" style="width:150px;"> ${Workflow} </sky:select>
				<a href="#" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'选择文章审核工作流，可到“系统管理”->“工作流定义”菜单进行设置。');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td>工作流沿用：</td>
			<td><sky:select id="WorkFlowExtend" style="width:150px;">
				<select id="WorkFlowExtend" style="width:150px;">
					<option value="1">仅本栏目</option>
					<option value="2">所有子栏目沿用相同设置</option>
				</select>
			</sky:select>  </td>
		</tr>
		<tr style="display:none">
			<td>排序字段：</td>
			<td><input name="OrderColumn" type="text" class="input1"
				id="OrderColumn" value="${OrderColumn}" size="30" /></td>
		</tr>
		<tr>
			<td>列表页显示文档数：</td>
			<td><input name="ListPageSize" type="text" class="input1" style="width:150px;" verify="Int"
				id="ListPageSize" value="${ListPageSize}" size="30" />
				<a href="javascript:void(0);" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'列表页显示多少条记录，如果模板中有指定pagesize属性，则以模板为准');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td>列表页最大分页数：</td>
			<td><input name="ListPage" type="text" class="input1" style="width:150px;" verify="Int"
				id="ListPage" value="${ListPage}" size="30" />
				<a href="javascript:void(0);" class="tip"
				onMouseOut="Tip.close(this)"
				onMouseOver="Tip.show(this,'列表页最多显示多少页，小于等于0的数字表示无限制，按实际页数来生成');"><img
				src="../Framework/Images/icon_tip.gif" width="16" height="16"></a></td>
		</tr>
		<tr>
			<td height=25>相关文章设置：</td>
			<td>
				<input type="hidden" id="hKeywordFlag" value="${KeywordFlag}">
				<label for="KeywordFlag">
				<input name="KeywordFlag" type="checkbox" id="KeywordFlag" value="Y" onclick="changeKeywordFlag()">
				允许文章智能关联				</label>
				<span id="spanSetting" style="display:none">关联分类：
					<input name="KeywordSetting"  id="KeywordSetting"  value="${KeywordSetting}" size="20"/>
				</span>			</td>
		</tr>
		<tr>
			<td height=25>Meta关键字：</td>
			<td><input name="Meta_Keywords" type="text" class="input1"
				id="Meta_Keywords" value="${Meta_Keywords}" size="30"/></td>
		</tr>
		<tr>
			<td height=25>Meta描述：</td>
			<td><textarea id="Meta_Description" name="Meta_Description" 
				style="width:250px;height:60px">${Meta_Description}</textarea></td>
		</tr>
		<tr>
			<td height=20>是否开放显示：</td>
			<td>${PublishFlag}</td>
		</tr>
		<tr>
			<td height=20>是否单页栏目：</td>
			<td><input class="inputRadio" name="SingleFlag" id="SingleFlag_0" value="Y" type="radio" ${SingleFlagY} onClick="changeSingleFlag('Y')"><label for="SingleFlag_0">是</label><input class="inputRadio" name="SingleFlag" id="SingleFlag_1" value="N" ${SingleFlagN} type="radio" onClick="changeSingleFlag('N')"><label for="SingleFlag_1">否</label></td>
		</tr>
        <tr>
			<td height=20>是否允许投稿：</td>
			<td>${AllowContribute}</td>
		</tr>
	</table>
	</form>
	</div>
</sky:init>
</body>
</html>
