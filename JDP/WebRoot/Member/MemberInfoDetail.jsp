<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky" %>
<%@page import="com.xdarkness.framework.Config"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员中心</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function doLogout(){
	Dialog.confirm("您确认退出吗？",function(){
		window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;												
	});
}

function save(){
	var dc = Form.getData("InfoForm");
	Server.sendRequest("com.xdarkness.member.MemberInfo.doDetailSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location.reload();
			}								   
		});
	});
}

function UpdatePic(){
	if($V("PicFile")!=""){
		$("InfoForm").submit();
	}
}

function afterUpload(path){
	$S("Pic",path);
	$("companypic").src = path+"?time="+new Date().getTime();
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">会员中心</a>  &raquo; 编辑个人资料
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
    	<td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
      <sky:init method="com.xdarkness.member.MemberInfo.initDetail">
      <form id="InfoForm" target="formTarget" method="POST" enctype="multipart/form-data" action="Upload.jsp?Path=CompanyLogo/">
          <div class="forumbox"> <span class="fr">带 <span style=" font-family:'宋体'; color:#F60">*</span> 标示为必填项</span>
            <h4>编辑个人资料</h4>
            <ul class="tabs ">
              <li><a href="MemberInfo.jsp?SiteID=<%=request.getParameter("SiteID")%>">基本资料</a></li>
              <li class="current"><a href="#;">详细资料</a></li>
              <li><a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">上传头像</a></li>
            </ul>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">${MemberType}详细信息
            <input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
            <input type="hidden" id="Pic" name="Pic" value="${Pic}"/></h5>
            <div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
             <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <%if("Person".equals(User.getValue("Type"))){%>
              <tr>
                <td width="15%" align="right">生日：</td>
                <td width="30%"><input id="Birthday" name="Birthday" type="text" class="textInput" size="30" value="${Birthday}"/></td>
                <td width="55%"><div class="paraphrastic">您的出生日期</div></td>
              </tr>
              <tr>
                <td align="right">QQ：</td>
                <td><input id="QQ" name="QQ" type="text" class="textInput" size="30" value="${QQ}"/></td>
                <td><div class="paraphrastic">您的QQ号码</div></td>
              </tr>
              <tr>
                <td align="right">MSN：</td>
                <td><input id="MSN" name="MSN" type="text" class="textInput" size="30" value="${MSN}"/></td>
                <td><div class="paraphrastic">您的MSN账号</div></td>
              </tr>
              <%}else{ %>
              <tr>
              	<td width="15%" align="right">公司名称：</td>
                <td width="30%"><input id="CompanyName" name="CompanyName" type="text" class="textInput" size="30" value="${CompanyName}"/></td>
                <td width="55%"><input id="PicFile" name="PicFile" type="File" class="textInput" size="25" value=""/>
                <input type="button" onClick="UpdatePic();" value="更新形象照片"/></td>
              </tr>
              <tr>
              	<td width="11%" align="right">公司规模：</td>
                <td width="34%"><input id="Scale" name="Scale" type="text" class="textInput" size="30" value="${Scale}"/></td>
                <td width="55%" rowspan="5" valign="middle">企业形象照片:<br/>
                  <img id="companypic" width="240" height="180" src="${PicPath}" style="border:#DFDFDF 1px solid"/>&nbsp;建议240*180</td>
              </tr>
              <tr>
              	<td width="11%" align="right">主营业务：</td>
                <td width="34%"><input id="BusinessType" name="BusinessType" type="text" class="textInput" size="30" value="${BusinessType}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">公司产品：</td>
                <td width="34%"><input id="Products" name="Products" type="text" class="textInput" size="30" value="${Products}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">公司网站：</td>
                <td width="34%"><input id="CompanySite" name="CompanySite" type="text" class="textInput" size="30" value="${CompanySite}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">传真：</td>
                <td width="34%"><input id="Fax" name="Fax" type="text" class="textInput" size="30" value="${Fax}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">联系人：</td>
                <td width="34%"><input id="LinkMan" name="LinkMan" type="text" class="textInput" size="30" value="${LinkMan}"/></td>
                 <td width="55%" rowspan="3">公司简介：<br/><textarea id="Intro" name="Intro" cols="50" class="textInput">${Intro}</textarea></td>
                </tr>
              <%} %>
                <td align="right">固定电话：</td>
                <td><input id="Tel" name="Tel" type="text" class="textInput" size="30" value="${Tel}"/></td>
                </tr>
              <tr>
                <td align="right">手机：</td>
                <td><input id="Mobile" name="Mobile" type="text" class="textInput" size="30" value="${Mobile}"/></td>
                </tr>
              <tr>
                <td align="right">详细联系地址：</td>
                <td><input id="Address" name="Address" type="text" class="textInput" size="30" value="${Address}"/></td>
                <td><div class="paraphrastic">您的详细联系地址</div></td>
              </tr>
              <tr>
                <td align="right">邮政编码：</td>
                <td><input id="ZipCode" name="ZipCode" type="text" class="textInput" size="30" value="${ZipCode}"/></td>
                <td><div class="paraphrastic">您的邮政编码</div></td>
              </tr>
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <div style=" padding-left:140px;">
              <button type="button" onClick="save()" name="tiajiao" value="true" class="submit">提交</button>
              &nbsp;
              <button type="reset" name="reset" value="false" class="button">重 置</button>
            </div>
          </div>
        </form></sky:init></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>