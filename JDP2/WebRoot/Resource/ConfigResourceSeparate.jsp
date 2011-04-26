<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.cms.resource.ConfigImageLib.initSeparate">
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function save() {
	var dc = Form.getData($F("form1"));	
	Server.sendRequest("com.xdarkness.cms.resource.ConfigImageLib.saveSeparate",dc,function(response){
		Dialog.alert(response.Message);
	});
}

function onChange(type){
	if($NV(type+"Flag")=="Y"){
		$(type+"Div").show();
	}else{
		$(type+"Div").hide();
	}
}

Page.onLoad(function(){
	$NS("ImageSeparateFlag","${ImageSeparateFlag}");
	$NS("VideoSeparateFlag","${VideoSeparateFlag}");
	$NS("AudioSeparateFlag","${AudioSeparateFlag}");
	$NS("AttachmentSeparateFlag","${AttachmentSeparateFlag}");
	onChange("ImageSeparate");
	onChange("VideoSeparate");
	onChange("AudioSeparate");
	onChange("AttachmentSeparate");
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form1" >
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon014a8.gif" width="20" height="20" /> 独立分发设置 </td>
            </tr>
            <tr>
              <td align="left" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"><table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td><input name="hidden" type="hidden" id="ID" value="${ID}"></td>
                </tr>
                <tr>
                  <td><fieldset>
                    <legend>
                      <label>站点信息</label>
                      </legend>
                    <table width="100%" cellpadding="4" align="left" cellspacing="0">
                      <tr>
                        <td height="10" align="center" ></td>
                        <td width="18%"></td>
                        <td width="62%"></td>
                        </tr>
                      <tr>
                        <td align="right">图片放置在单独服务器上？</td>
                        <td><input type="radio" name="ImageSeparateFlag" onchange="onChange('ImageSeparate')" value="Y" id="ImageSeparateFlag1">
                          <label for="ImageSeparateFlag1">是</label>
                          <input name="ImageSeparateFlag" type="radio" id="radio" onchange="onChange('ImageSeparate')" value="N" checked>
                          <label for="ImageSeparateFlag2">否</label>
                          &nbsp;&nbsp;</td>
                        <td><div id="ImageSeparateDiv" style="display:none">
						  图片发布后URL前缀： 
                          <input id="ImageSeparateURLPrefix" type="text" size="40" value="${ImageSeparateURLPrefix}">
						  </div></td></tr>
                      <tr>
                        <td width="20%" height="25" align="right">视频放置在单独服务器上？</td>
                        <td><input type="radio" name="VideoSeparateFlag" onchange="onChange('VideoSeparate')" value="Y" id="VideoSeparateFlag1">
                          <label for="VideoSeparateFlag1">是</label>
                          <input name="VideoSeparateFlag" type="radio" id="radio" onchange="onChange('VideoSeparate')" value="N" checked>
                          <label for="VideoSeparateFlag2">否</label>
                          &nbsp;&nbsp;</td>
                        <td><div id="VideoSeparateDiv" style="display:none">
						  视频发布后URL前缀： 
                          <input id="VideoSeparateURLPrefix" type="text" size="40" value="${VideoSeparateURLPrefix}">
						  </div></td></tr>
                      <tr>
                        <td height="25" align="right">音频放置在单独服务器上？</td>
                        <td><input type="radio" name="AudioSeparateFlag" onchange="onChange('AudioSeparate')" value="Y" id="AudioSeparateFlag1">
                          <label for="AudioSeparateFlag1">是</label>
                          <input name="AudioSeparateFlag" type="radio" id="radio" onchange="onChange('AudioSeparate')" value="N" checked>
                          <label for="AudioSeparateFlag2">否</label>
                          &nbsp;&nbsp;</td>
                        <td><div id="AudioSeparateDiv" style="display:none">
						  音频发布后URL前缀： 
                          <input id="AudioSeparateURLPrefix" type="text" size="40" value="${AudioSeparateURLPrefix}">
						  </div></td></tr>
                      <tr>
                        <td height="25" align="right">附件放置在单独服务器上？</td>
                        <td><input type="radio" name="AttachmentSeparateFlag" onchange="onChange('AttachmentSeparate')" value="Y" id="AttachmentSeparateFlag1">
                          <label for="AttachmentSeparateFlag1">是</label>
                          <input name="AttachmentSeparateFlag" type="radio" id="radio" onchange="onChange('AttachmentSeparate')" value="N" checked>
                          <label for="AttachmentSeparateFlag2">否</label>
                          &nbsp;&nbsp;</td>
                        <td><div id="AttachmentSeparateDiv" style="display:none">
						  附件发布后URL前缀： 
                          <input id="AttachmentSeparateURLPrefix" type="text" size="40" value="${AttachmentSeparateURLPrefix}">
						  </div></td></tr>
                    </table>
                  </fieldset></td>
                </tr>
              </table></td>
            </tr>
        </table></td>
      </tr>
    </table>
	<table width="100%" border='0' cellpadding='0' cellspacing='0'>
	<tr>
		<td align="center"><input id="savebtn" name="savebtn" onClick="save();" type='button'
			value='    保存    ' /></td></tr>
</table>
</form>
</body>
</html>
</sky:init>
