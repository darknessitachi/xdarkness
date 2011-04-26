<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>会员等级项目表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSave(){
	if(Verify.hasError()){
	  return;
    }
	var dc = Form.getData("form2");
	Server.sendRequest("com.xdarkness.cms.site.CatalogShowConfig.save",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
	});	
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.cms.site.CatalogShowConfig.init">
<table width="800" border="0" cellpadding="0">
	<tr valign="top">
		<td>
        <form id="form2">
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                <fieldset><legend>文章栏目树</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">默认加载层级：</td>
                        <td width="30%"><input type="text" id="ArticleCatalogShowLevel" name="ArticleCatalogShowLevel" value="${ArticleCatalogShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">子节点延迟加载方式：</td>
                        <td width="35%">
                        <sky:select id="ArticleCatalogLoadType" value="${ArticleCatalogLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">加载全部子节点</option><option value="NextLevel">仅加载下一层级</option>
                        </select></sky:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>图片库栏目树</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">默认加载层级：</td>
                        <td width="30%"><input type="text" id="ImageLibShowLevel" name="ImageLibShowLevel" value="${ImageLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">子节点延迟加载方式：</td>
                        <td width="35%">
                        <sky:select id="ImageLibLoadType" value="${ImageLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">加载全部子节点</option><option value="NextLevel">仅加载下一层级</option>
                        </select></sky:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>视频库栏目树</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">默认加载层级：</td>
                        <td width="30%"><input type="text" id="VideoLibShowLevel" name="VideoLibShowLevel" value="${VideoLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">子节点延迟加载方式：</td>
                        <td width="35%">
                        <sky:select id="VideoLibLoadType" value="${VideoLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">加载全部子节点</option><option value="NextLevel">仅加载下一层级</option>
                        </select></sky:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>附件库栏目树</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">默认加载层级：</td>
                        <td width="30%"><input type="text" id="AttachLibShowLevel" name="AttachLibShowLevel" value="${AttachLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">子节点延迟加载方式：</td>
                        <td width="35%">
                        <sky:select id="AttachLibLoadType" value="${AttachLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">加载全部子节点</option><option value="NextLevel">仅加载下一层级</option>
                        </select></sky:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <fieldset><legend>音频库栏目树</legend>
                <table cellpadding="2" cellspacing="2" width="100%" border="0">
                	<tr>
                        <td width="15%" align="right">默认加载层级：</td>
                        <td width="30%"><input type="text" id="AudioLibShowLevel" name="AudioLibShowLevel" value="${AudioLibShowLevel}" verify="NotNull&&Int"/></td>
                        <td width="20%" align="right">子节点延迟加载方式：</td>
                        <td width="35%">
                        <sky:select id="AudioLibLoadType" value="${AudioLibLoadType}" style="width:120px;">
                        <select>
                        <option value="AllChild" selected="true">加载全部子节点</option><option value="NextLevel">仅加载下一层级</option>
                        </select></sky:select>
                        </td>
                	</tr>
                </table>
                </fieldset>
                <div style="width:100%; text-align:center; padding-top:10px;"><input type="button" value=" 保 存 " onClick="doSave();"/></div>
                </td>
			</tr>
		</table>
        </form>
		</td>
	</tr>
</table>
</sky:init>
</body>
</html>