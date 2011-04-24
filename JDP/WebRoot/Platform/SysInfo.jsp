
<%@page import="com.xdarkness.platform.page.UserListPage"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.platform.page.SysInfoPage"%>
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
<script>
function changeLoginStatus(){
	if($("LoginStatusButton").innerHTML.indexOf("临时禁止")>=0){
		Dialog.confirm("临时禁止登录后，除<%=UserListPage.ADMINISTRATOR%>之外的其他用户都不能登录。是否确认？",function(){
			changeLoginStatusServer();
		});
	}else{
		changeLoginStatusServer();
	}
}

function changeLoginStatusServer(){
	Server.sendRequest("com.xdarkness.platform.page.SysInfoPage.changeLoginStatus",null,function(response){
		Dialog.alert("设置成功");
		$("LoginStatusButton").$T("b")[0].innerHTML = response.get("LoginStatus");
	});
}

function forceExit(){
	Dialog.confirm("除当前用户之外的其他用户都将被强制注销。是否确认？",function(){
		Server.sendRequest("com.xdarkness.platform.page.SysInfoPage.forceExit",null,function(response){
			if(response.Status==1){
				Dialog.alert("操作成功!");
			}else{
				Dialog.alert("操作失败!");
			}
		});
	});
}

function exportDB(){
	window.location = "DB/DBDownload.jsp";
}

function importDB(){
	var diag = new Dialog("DBUpload");
	diag.Title = "导入数据库";
	diag.URL = "Platform/DB/DBUpload.jsp";
	diag.Width = 500;
	diag.Height = 100;
	diag.OKEvent = function(){
		$DW.$("f1").submit();
	}
	diag.show();
}

Page.onLoad(function(){
//使用demo站
	//var username="<%=User.getUserName()%>";
	//if(username!=="admin"){
	//	$("LoginStatusButton").disable();
	//	$("b1").disable();
	//	$("b2").disable();
	//	$("b3").disable();
	//}
});
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />系统信息</td>
      </tr>
      <tr>
      <td style="padding:0 8px 4px;">	  
	    <sky:button onClick="changeLoginStatus()" id="LoginStatusButton">
		<img src="../Icons/icon021a8.gif" width="20" height="20"/><%=SysInfoPage.getLoginStatus()%></sky:button>
        <sky:button id="b1" onClick="forceExit()"><img src="../Icons/icon021a5.gif" width="20" height="20"/>强制注销所有会话</sky:button>
        <sky:button id="b2" onClick="exportDB()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>导出数据库</sky:button>
        <sky:button id="b3" onClick="importDB()"><img src="../Icons/icon005a6.gif" width="20" height="20"/>导入数据库</sky:button>
		</td>
      </tr>
      <tr>
        <td style="padding:0"><table width="100%" border="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
          <tr>
            <td width="55%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="36%" height="30" align="right" type="Tree"><b>系统信息项&nbsp;</b></td>
                <td width="64%" type="Data" field="count"><b>值</b></td>
              </tr>
              <sky:datalist id="list1" method="com.xdarkness.platform.page.SysInfoPage.list1DataBind">
                <tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">
                  <td align="right">${Name}：</td>
                  <td>${Value}</td>
                </tr>
              </sky:datalist>
            </table></td>
            <td width="45%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="38%" height="30" align="right" type="Tree"><b>数据库配置项&nbsp;</b></td>
                <td width="62%" type="Data" field="count"><b>值</b></td>
              </tr>
              <sky:datalist id="list2" method="com.xdarkness.platform.page.SysInfoPage.list2DataBind">
                <tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">
                  <td align="right">${Name}：</td>
                  <td>${Value}</td>
                </tr>
              </sky:datalist>
            </table>
              <p>&nbsp;</p>
              <table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
                <tr class="dataTableHead">
                  <td width="38%" height="30" align="right" type="Tree"><b>授权信息项&nbsp;</b></td>
                  <td width="62%" type="Data" field="count"><b>值</b></td>
                </tr>
                <sky:datalist id="list3" method="com.xdarkness.platform.page.SysInfoPage.list3DataBind">
                  <tr style1="backgroundcolor:#FEFEFE" style2="backgroundcolor:#F9FBFC">
                    <td align="right">${Name}：</td>
                    <td>${Value}</td>
                  </tr>
                </sky:datalist>
              </table>
              </td>
          </tr>
          
        </table>
          <br>
          <p>&nbsp;</p>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>