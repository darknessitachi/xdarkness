<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=ForumUtil.getCurrentName(request)%></title>
<script src="../../Framework/Main.js"></script>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="../forum.css" rel="stylesheet" type="text/css">
<script>
	function saveSetting(){
		if(Verify.hasError()){
			return;
		}
		var dc = Form.getData($F("form1"));
		Server.sendRequest("com.xdarkness.bbs.admin.ForumScoreSetting.save",dc,function(response){
			Dialog.alert(response.Message);
				if(response.Status==1){
					DataList.loadData('list1');
				}
		});
	}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
        <tr>
          <td valign="middle" class="blockTd"><img src="../../Icons/icon023a1.gif" width="20" height="20" /> 积分设置 </td>
        </tr>
        <tr>
          <td style="padding:0 8px 4px;"><sky:button onClick="saveSetting()"><img src="../../Icons/icon023a16.gif" width="20" height="20" />保存</sky:button>
          </td>
        </tr>
        <tr>
          <td align="left" style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		  <sky:init method="com.xdarkness.bbs.admin.ForumScoreSetting.init">
		  <form id="form1" method="post" name="moderate">
              <input id="ForumID"  type="hidden" value="${ID}">
              <input type="hidden" id="ID" value="${ID}"/>
              <fieldset>
				<legend>
				<label>积分设置</label>
				</legend>
			  <table width="100%" cellpadding="4" align="left" cellspacing="0">
                <tr>
                  <td height="10" align="center" ></td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td align="right">初始积分</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${InitScore}" id="InitScore" /></td>
                  <td>发主题</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${PublishTheme}" id="PublishTheme" /></td>
                </tr>
                <tr>
                  <td width="20%" height="25" align="right">删主题</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${DeleteTheme}" id="DeleteTheme" /></td>
                  <td>发回复</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${PublishPost}" id="PublishPost" /></td>
                </tr>
                <tr>
                  <td height="25" align="right">删回复</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${DeletePost}" id="DeletePost" /></td>
                  <td>加精华</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${Best}" id="Best" /></td>
                </tr>
                <tr>
                  <td height="25" align="right">取消精华</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${CancelBest}" id="CancelBest" /></td>
                  <td>设高亮</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${Bright}" id="Bright" /></td>
                </tr>
                <tr>
                  <td height="25" align="right">设顶置</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${TopTheme}" id="TopTheme" /></td>
                  <td>取消顶置</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${CancelTop}" id="CancelTop" /></td>
                </tr>
                <tr>
                  <td height="25" align="right">提升主题</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${UpTheme}" id="UpTheme" /></td>
                  <td>下沉主题</td>
                    <td><input size="15" type="text" verify="NotNull&&Int" value="${DownTheme}" id="DownTheme" /></td>
                </tr>
              </table>
			  </fieldset>
            </form>
			</sky:init>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</div>
</body>
</html>
