<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.abigdreamer.java.net.*"%>
<%@page import="com.abigdreamer.java.net.jaf.*"%>
<%@page import="com.abigdreamer.java.net.util.*"%>
<%@page import="com.xdarkness.platform.*"%>
<%@page import="com.abigdreamer.java.net.license.*"%>
<%
if(Config.isInstalled){
	return;
}
if(XString.isNotEmpty(request.getParameter("SQL"))){
	com.xdarkness.platform.pub.Install.generateSQL(request,response);
	return;
}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>初始化<%=WebConfig.getAppCode()%></title>
<link href="Include/Default.css" rel="stylesheet" type="text/css" />
<script src="Framework/Main.js"></script>
<script>
function onServerTypeChange(){
	var st = $V("ServerType");
	$("DB2Info").hide();
	$("trOracleMisc").hide();
	if(st=="ORACLE"){
		$S("Port","1521");
		$("trOracleMisc").show();
	}
	if(st=="DB2"){
		$S("Port","50000");
		$("DB2Info").show();
	}
	if(st.startWith("MSSQL")){
		$S("Port","1433");
	}
	if(st=="MYSQL"){
		$S("Port","3306");
	}
}

function execute(){
	if(Verify.hasError()){
		return;
	}
	Dialog.alert("请稍等......");
	var dc = Form.getData("F1");
	Server.sendRequest("com.xdarkness.platform.pub.Install.execute",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			taskID = response.get("TaskID");
			var p = new Progress(taskID,"正在执行系统初始化...",500,150);
			p.show(function(){
				window.location = Server.ContextPath+"Login.jsp";
			});			
			p.Dialog.OKButton.hide();
			p.Dialog.CancelButton.hide();
			p.Dialog.CancelButton.onclick = function(){}
		}else{
			Dialog.alert(response.Message,null,600,100);	
		}
	});
}

function onAutoCreateClick(){
	if(!$("AutoCreate").checked){
		$("trSQL").show();
	}else{
		$("trSQL").hide();
	}
}

function onJNDIPoolClick(){
	if($("isJNDIPool").checked){
		$("trJNDI").show();
		$("trAddress").hide();
		$("trPort").hide();
		$("trDBName").hide();
		$("trPassword").hide();
		$("trUserName").hide();
		
	}else{
		$("trJNDI").hide();
		$("trSQL").hide();
		$("trAddress").show();
		$("trPort").show();
		$("trDBName").show();
		$("trPassword").show();
		$("trUserName").show();
	}
}

function onImportDataClick(){
	if($NV("ImportData")=="0"){
		$("AutoCreate").checked = false;
		$("AutoCreate").disable();
	}else{
		$("AutoCreate").checked = true;
		$("AutoCreate").enable();
	}
}

Page.onLoad(function (){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}
	onJNDIPoolClick();
	onAutoCreateClick();
	onServerTypeChange();
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
body{ background-color:#F6FAFF;_position:relative; color:#222;}
h4{ color:#226699}
a.zInputBtn {_position:relative;}
.red {color: #FF0000}
</style>
</head>
<body>
<form id="F1">
  <table width="100%" height="100%" border="0">
    <tr>
      <td valign="middle"><div style="margin:0 auto; background-color:#fff; padding:1px; border:1px solid #bbccdd; width:760px;">
          <table width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="40" align="center"><table width="100%" cellspacing="0" cellpadding="10" class="cellspacing" border="0" style="background: rgb(234, 236, 233) url(Platform/Images/form_titleBg.gif) no-repeat scroll right top;">
                  <tbody>
                    <tr>
                      <td width="25" align="right"><img src="Framework/Images/window.gif" name="_MessageIcon_Diag1" width="32" height="32" id="_MessageIcon_Diag1"></td>
                      <td align="left" style="line-height: 16px;"><h4>初始化<%=WebConfig.getAppCode()%></h4>
                        <div>请为<%=WebConfig.getAppCode()%>配置数据库初始化参数，数据库初始化完成后将自动建立全文检索并发布全站。</div></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td><table width="100%" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td colspan="4" height="10"></td>
                  </tr>
				  <%if(Runtime.getRuntime().maxMemory()<127*1024*1024){%>
                  <tr>
                    <td width="15%" height="30" align="right" ><em>特别提示：</em></td>
                    <td colspan="3"><span class="red">当前JVM最大内存数较小，建议调整-Xmx参数到128M以上,以避免出现运行一段时间后内存溢出并停止响应的状况。</span></td>
                  </tr>
				  <%}%>
                  <tr>
                    <td width="15%" height="30" align="right" ><em>数据库类型：</em></td>
                    <td width="35%"><div style="_position: relative;">
                        <sky:select id="ServerType" onChange="onServerTypeChange();"> <span value="ORACLE">Oracle</span> <span value="DB2">DB2</span> <span value="MSSQL">SQL
                            Server 2005</span> <span value="MYSQL">Mysql</span> </sky:select>
                      </div></td>
                    <td width="45%"><span id="DB2Info" style="display:none">注意：<span class="red">DB2下默认表空间页大小要求大于等于16K，否则不能正确创建表结构。</span></span></td>
                    <td width="5%">&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="30" align="right">&nbsp;</td>
                    <td><label>
                      <input name="ImportData" type="radio" id="ImportData1" onClick="onImportDataClick()" value="1" checked>
                      初始化数据库</label>
                      <label>
                      <input name="ImportData" type="radio" id="ImportData2" onClick="onImportDataClick()" value="0">
                      仅配置连接</label></td>
                    <td class="gray"><strong>初始化数据库</strong>会初始化所有表中的数据；<strong>仅配置连接</strong>则会保留数据库中原有数据</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr>
                    <td height="30" align="right"><em>表结构：</em></td>
                    <td><label>
                      <input name="AutoCreate" type="checkbox" id="AutoCreate" onClick="onAutoCreateClick()" value="1" checked>
                      自动创建数据库表结构</label></td>
                    <td class="gray"><strong>自动创建数据库表结构</strong>会自动创建表结构，如果数据库中原来就有同名的表，则会先删除同名表</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trSQL">
                    <td height="50" align="right">&nbsp;</td>
                    <td><p>请下载对应数据库的SQL文件并手动执行。</p>
                      <p><a href="Install.jsp?SQL=1&Type=DB2"><strong>DB2</strong></a>， <a href="Install.jsp?SQL=1&Type=ORACLE"><strong>Oracle</strong></a>， <a href="Install.jsp?SQL=1&Type=MSSQL"><strong>SQLServer</strong></a>， <a href="Install.jsp?SQL=1&Type=MYSQL"><strong>Mysql</strong></a></p></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr>
                    <td height="30" align="right"><em>连接池：</em></td>
                    <td><label>
                      <input name="isJNDIPool" type="checkbox" id="isJNDIPool" onClick="onJNDIPoolClick()" value="1">
                      使用JNDI连接池</label></td>
                    <td class="gray"><strong>使用JNDI连接池</strong>会从中间件获得数据连接</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trJNDI">
                    <td height="30" align="right"></td>
                    <td>JNDI名称：
                      <input name="JNDIName" type="text" id="JNDIName" value="jdbc/zcms" size=20  /></td>
                    <td class="gray">请注意JNDI名称是否有前缀&quot;<strong>jdbc/</strong>&quot;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4"><hr></td>
                  </tr>
                  <tr style="display:none" id="trAddress">
                    <td height="30" align="right"><em>服务器地址：</em></td>
                    <td><input name="Address" type="text" id="Address" value="" size=20  verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">数据库服务器域名或IP地址</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trPort">
                    <td height="30" align="right"><em>服务器端口：</em></td>
                    <td><input name="Port" type="text" id="Port" value="" size=20 verify="NotNull&&Int" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">访问数据库使用的端口</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trUserName">
                    <td height="30" align="right"><em>用户名：</em></td>
                    <td><input name="UserName" type="text" id="UserName" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">访问数据库使用的用户名</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trPassword">
                    <td height="30" align="right"><em>密码：</em></td>
                    <td><input name="Password" type="password" id="Password" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">访问数据库使用的密码</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="display:none" id="trDBName">
                    <td height="30" align="right"><em>数据库名称：</em></td>
                    <td><input name="DBName" type="text" id="DBName" value="" size=20 verify="NotNull" condition="!$('isJNDIPool').checked"/></td>
                    <td class="gray">Oracle下此处填实例名；Mysql和SQLServer下若数据库不存在，则会自动创建数据库</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr id="trOracleMisc" style="display:none">
                    <td height="40" align="right"><em>Oracle附加选项：</em></td>
                    <td><input type="checkbox" name="isLatin1Charset" value="1" id="isLatin1Charset">
                    <label for="isLatin1Charset">数据字符集为US7ASCII</label></td>
                    <td><span class="gray">如果Oracle字符集为US7ASCII则必须勾选此选项，否则可能会出现乱码。</span></td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td height="40" align="right">&nbsp;</td>
                    <td><div style="_position:relative;">
                        <input type="button" name="Submit" value="" class="btnOK" style="border:0 none; background:url(Framework/Images/btn_OK.gif) no-repeat; width:70px; height:23px;" onClick="execute()">
                      </div></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
