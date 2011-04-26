<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">
function saveSetting() {
	var dc = Form.getData($F("form1"));
	
	Server.sendRequest("com.xdarkness.bbs.admin.ForumSetting.add",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			$D.close();
		}
	});
}
function DBInit() {		
	Server.sendRequest("com.xdarkness.bbs.admin.DBInit.DBDataInit",null,function(response){
		Dialog.alert(response.Message);
	});
}
function view(){
	 window.open(Server.ContextPath+"BBS/Index.jsp?SiteID=<%=Application.getCurrentSiteID()%>");
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form1" >
<sky:init method="com.xdarkness.bbs.admin.ForumSetting.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../../Icons/icon023a1.gif" width="20" height="20" /> 论坛设置 </td>
            </tr>
				<tr>
			<td style="padding:0 8px 4px;"><sky:button onClick="saveSetting()"><img src="../../Icons/icon023a16.gif" width="20" height="20" />保存</sky:button>
                  <sky:button onClick="DBInit()"> <img src="../../Icons/icon023a9.gif" width="20" height="20" />论坛数据初始化</sky:button>
                  <sky:button onClick="view()"> <img src="../../Icons/icon034a12.gif" width="20" height="20" />浏览论坛</sky:button>
			 </td>
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
                        <td></td>
                      </tr>
                      <tr>
                        <td align="right">名称：</td>
                        <td><input name="Name" id="Name" type="text" value="${Name}" class="input1" verify="NotNull" size="40"/>
                        </td>
                      </tr>
                      <tr>
                        <td width="20%" height="25" align="right">二级域名：</td>
                        <td><input name="Subdomains" id="Subdomains" type="text" value="${Subdomains}" class="input1"  size="40"/>
                        </td>
                      </tr>
                      <tr>
                        <td height="25" align="right">描述：</td>
                        <td><textArea id="Des" name="Des" cols="60">${Des}</textArea></td>
                      </tr>
                      <tr>
                        <td height="25" align="right">临时关闭论坛：</td>
                        <td>${TempCloseFlag}</td>
                      </tr>
                    </table>
                  </fieldset></td>
                </tr>
              </table></td>
            </tr>
        </table></td>
      </tr>
    </table>
</sky:init>
</form>
</body>
</html>