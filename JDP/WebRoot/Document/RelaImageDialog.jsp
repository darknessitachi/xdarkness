<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
		box.checked = false;
	}else{
		box.checked = true;
	}
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.cms.document.Article.initPlayerList">
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td style="padding:8px 6px;" class="blockTd">
                <div>
                &nbsp;将选择的图片添加到图片播放器：
                <sky:select id="SelectPlayerID"> ${ImagePlayers}</sky:select>
                </div>
                <input type="hidden" id="ArticleID" name="ArticleID" value="${ArticleID}">
                <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}">
                </td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;" id="tdImages">
                <ul class="img-wrapper" style="overflow:hidden; display:block; margin:0;" id="ulImages">
					<sky:datalist id="dg1" method="com.xdarkness.cms.document.Article.relaImageDataBind">
						<li style="height:150px">
						<dl>
							<dt><a href='#;' hidefocus><em><img imageid='${id}' src='..${Alias}${Path}s_${FileName}?${ModifyTime}' title='${name}' onClick='clickImage(this)'></em></a></dt>
							<dd style="text-align:center"><em><label><input id='ImageID_box${id}' name='ImageID' type='checkbox' value='${id}' ${checkStatus}>${name}</label></em></dd>
						</dl>
						</li>
					</sky:datalist>
				</ul>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</sky:init>
</body>
</html>