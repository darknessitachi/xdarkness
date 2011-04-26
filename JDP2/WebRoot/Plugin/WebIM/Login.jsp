<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WebIM</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css">
<script src="../../Framework/Main.js" type="text/javascript"></script>

<style type="text/css">
 .modalFrame {
      position: absolute;
      display: block;
      width: 200px;
      height: 300px;
      text-align: center;
      background-repeat: no-repeat;
      cursor: default;
   	 /* filter:alpha(opacity=95);
   	  -moz-opacity: 0.95;
   	  opacity: 0.95;*/
   }
</style>
<script src="Framework/Main.js" charset="UTF-8"></script>
<script src="Login.jsp.js" charset="utf-8"></script>
<script language="javascript" type="text/javascript" src="Login.js"></script>
<script language="javascript" type="text/javascript" src="Effect.js"></script>
<script>
document.onkeydown = function(){
    if((event.ctrlKey && event.keyCode == '78') || 
        (event.ctrlKey && event.keyCode == '65' && event.srcElement.iscontext != "true")){
        event.returnValue = false;
        return;
    }
}
</script>
</head>
<body class="bodycolor">
    <form id="form1">
        <div id="modal" class="modalFrame">
            <div id="loginDialog">
                <table>
                    <tr id="trEmpty" style="display: none; height: 45px;">
                        <td><p>&nbsp;</p></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <p>用户名</p>
                        </td>
                        <td>
                        	<input type="text" id="txtLoginName" width="180" MaxLength="10"  >
                        </td>
                    </tr>
                    <tr  id="trUserName" style="display: none;">
                        <td>
                            <p>中文名</p>
                        </td>
                        <td>
                        	<input type="text" id="txtUserName" width="180" MaxLength="10" >
                        </td>
                    </tr>
                    <tr id="trSex" style="display: none;">
                        <td>
                            <p>性　别</p>
                        </td>
                        <td>
                            <a id="curSex" onclick="Layout.toggleSex();" href="#" style=" width: 180px;" onblur="Layout.removeSexPanel();">男</a>
                        </td>
                        
                    </tr>
                    <tr>
                        <td>
                            <p>密　码</p>
                        </td>
                        <td>
                            <input type="password" id="txtUserPwd"  width="180" maxLength="12" onkeydown="Login.toggleOperate();">
                        </td>
                    </tr>
                    <tr id="trConfirm" style="display: none;">
                        <td>
                            <p>确认密码</p>
                        </td>
                        <td>
                            <input type="password" id="txtConfirmPwd" width="180" maxLength="12" onkeydown="Login.toggleOperate();">
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2" style="height: 50px">
                            <input type="button" id="btnLogin" name="btnLogin" value="登录" onclick="Layout.redirectToLogin();" >
                            <input type="button" id="btnRegister" name="btnRegister" value="注册" onclick="Layout.redirectToRegister();"  >
                            <input type="button" id="btnCancel" name="btnCancel" value="取消" onclick="Layout.redirectToLogin();"  style="display: none;">
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div class="itemSexList" id="divSex">
            <a href="#" onmousedown="Layout.setUserSex('男'); return false;">男</a>
            <a href="#" onmousedown="Layout.setUserSex('女'); return false;">女</a>
        </div>
    </form>
</body>
</html>
