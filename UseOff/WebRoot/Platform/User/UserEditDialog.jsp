<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.platform.page.UserListPage.initEditDialog">
<form id="form2">
<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td >
   <fieldset>
    <legend><label>基本信息</label></legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="39%" height="30" align="right" >用户名：</td>
          <td width="61%"><input name="UserName"  type="text" value="${UserName}" id="UserName" verify="用户名|NotNull" readonly="true"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >真实姓名：</td>
          <td><input name="RealName"  type="text" value="${RealName}" id="RealName"  /></td>
        </tr>
        <tr id ="tr_Password2">
          <td height="30" align="right" >密码：</td>
          <td><input name="NewPassword" type="password" id="NewPassword" verify="密码为4-20位|Regex=\S{4,20}&&NotNull" value="${Password}" /></td>
        </tr>
        <tr id ="tr_ConfirmPassword2">
          <td height="30" align="right" >重复密码：</td>
          <td><input name="NewConfirmPassword" type="password" id="NewConfirmPassword" verify="重复密码为4-20位|Regex=\S{4,20}&&NotNull" value="${Password}"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >所属机构：</td>
          <td ><sky:select id="BranchInnerCode"> ${BranchInnerCode} </sky:select></td>
        </tr>
        <tr id ="tr_IsBranchAdmin" style="display:none">
          <td height="30" align="right" >机构管理员：</td>
          <td >${IsBranchAdmin}</td>
        </tr>
        <tr>
          <td height="30" align="right" >是否停用：</td>
          <td >${Status}</td>
        </tr>
        <tr>
          <td height="30" align="right" >电子邮件：</td>
          <td><input name="Email"  type="text"  id="Email" value="${Email}" verify="电子邮件|Email"/></td>
        </tr>
        <tr>
          <td height="30" align="right" >联系电话：</td>
          <td><input name="Tel"  type="text" value="${Tel}"  id="Tel" verify="联系电话|Number" size="12" />
            <br>01012345678(示例)</td>
        </tr>
        <tr>
          <td height="30" align="right" >手机号码：</td>
          <td><input name="Mobile"  type="text" value="${Mobile}"  id="Mobile" verify="手机号码|Number&&Length=11" size="12"/>
            <br>13912345678(示例)</td>
        </tr>
        <tr>
          <td height="30" align="right" >备注：</td>
          <td><input name="Memo" type="text"  id="Memo" value="${Memo}"/></td>
        </tr>
      </table></fieldset></td>
      <td  width="240"  valign="top">
	  <fieldset>
		<legend><label>所属角色</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
			<td>
	    <sky:tree id="tree1" style="width:230px;height:300px" 
	    	method="com.xdarkness.platform.page.UserListPage.initEditRoleTree">
	      <p cid='${RoleCode}' >
	        <input type="checkbox" name="RoleCode" value='${RoleCode}' ${checked}>
	        ${RoleCode}(${RoleName})</p>
	      </sky:tree>
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
	</table>
</form>
</sky:init>
</body>
</html>