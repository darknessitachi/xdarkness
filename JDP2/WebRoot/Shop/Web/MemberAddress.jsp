<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员中心</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css">
<link href="<%=Config.getContextPath()%>Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<script src="<%=Config.getContextPath()%>Framework/District.js"></script>
<%@ include file="../../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}
%>
<script type="text/javascript">
function add(){
	$S("ID","");
	$S("RealName","");
	$S("UserName","");
	$S("Tel","");
	$S("Address","");
	$S("ZipCode","");
	$S("Email","");
	$S("Mobile","");
	initProvince();
	$("modifyAddress").style.display="";
}

function edit(AddrID){
	var dc = new DataCollection();
	dc.add("AddrID",AddrID);
	Server.sendRequest("com.xdarkness.member.MemberAddress.getAddress",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			$S("ID",response.get("ID"));
			$S("RealName",response.get("RealName"));
			$S("UserName",response.get("UserName"));
			$S("Tel",response.get("Tel"));
			initProvince($("Province"),$("City"),$("District"),response.get("Province"),response.get("City"),response.get("District"));
			$S("Address",response.get("Address"));
			$S("ZipCode",response.get("ZipCode"));
			$S("Email",response.get("Email"));
			$S("Mobile",response.get("Mobile"));
			$S("IsDefault",response.get("IsDefault"));
			$("modifyAddress").style.display="";
		}
	});	
}

function doSave(){
	if(Verify.hasError()){
	  return;
    }
	var dc = Form.getData($F("addrForm"));
	Server.sendRequest("com.xdarkness.member.MemberAddress.doSave", dc, function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){	
				$("modifyAddress").style.display="none";
				DataList.loadData("dg1");
			}
		});
	});	
}

function setDefault(ID,UserName){
	var dc = new DataCollection();
	dc.add("ID",ID);
	dc.add("UserName",UserName);
	Server.sendRequest("com.xdarkness.member.MemberAddress.setDefault",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataList.loadData("dg1");
		}
	});
}

function del(ID){
	if(confirm("确认删除这个地址吗？")){
		var dc = new DataCollection();
		dc.add("ID",ID);
		Server.sendRequest("com.xdarkness.member.MemberAddress.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}						   
			});
		});
	}
}

</script>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<%@ include file="../../Member/Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=SiteID%>">会员中心</a>  &raquo; 收货地址管理
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200"><%@ include file="../../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  
    <div class="forumbox">
        <ul class="tabs">
            <li class="current"><a href="#;">地址列表</a></li>
        </ul>
        <sky:datalist id="dg1" method="com.xdarkness.member.MemberAddress.dg1DataList" page="false">
        <div style="border:1px dotted #D0EBFF; margin-bottom:3px;">
    	<table width="100%" cellpadding="2" cellspacing="0">
        	<tr>
            	<td width="8%" align="right" valign="top">收货人：</td>
                <td width="39%" valign="top">${RealName}</td>
                <td width="12%" align="right" valign="top">固定电话：</td>
                <td width="24%" valign="top">${Tel}</td>
                <td width="17%" rowspan="3" align="center" valign="middle">
                <p style="margin-bottom:0px;"><a href="#;" onClick="edit(${ID})">修改</a></p>
                <p style="margin-bottom:0px;"><a href="#;" onClick="del(${ID})">删除</a></p>
                <p style="margin-bottom:0px;">${IsDefaultName}</p></td>
            </tr>
            <tr>
            	<td align="right" valign="top">省份：</td>
                <td valign="top">${ProvinceName}&nbsp;${CityName}&nbsp;${DistrictName}</td>
                <td align="right" valign="top">手机：</td>
                <td valign="top">${Mobile}</td>
            </tr>
            <tr>
            	<td align="right" valign="top">地址：</td>
                <td valign="top">${Address}&nbsp;&nbsp;邮编：${ZipCode}</td>
                <td align="right" valign="top">电子邮件：</td>
                <td valign="top">${Email}</td>
            </tr>
        </table>
        </div>
        </sky:datalist>
        <span style=" height:30px;"><br/>
        如果您有其他收货地址，请<a href="#;" onClick="add();">添加新地址</a></span>
	</div>
    <div id="modifyAddress" style="border:#CCCCCC 1px solid;padding:5px;overflow:hidden;_overflow: visible;_height:1%;margin-bottom:8px;clear:both;display:none;">
    <div style="border-bottom:#CCCCCC 1px solid; width:100%; height:20px;margin-bottom:5px;"><b>地址维护</b></div>
    <form id="addrForm">
    <table width="100%" cellpadding="2" cellspacing="0">
    	<tr>
            <td width="12%" align="right">收货人：</td>
            <td width="39%"><input name="RealName" type="text" value="" id="RealName" verify="NotNull" />
            <input name="UserName" id="UserName" type="hidden" value="" />
            <input name="ID" id="ID" type="hidden" value="" />
            <input name="IsDefault" id="IsDefault" type="hidden" value="N" /></td>
            <td width="9%" align="right">固定电话：</td>
            <td width="40%"><input name="Tel" type="text" id="Tel" value=""/></td>
        </tr>
        <tr>
            <td align="right">省份：</td>
            <td colspan="3">
            <sky:select id="Province" onChange="changeProvince();" listHeight="300" value="" verify="NotNull"> </sky:select>&nbsp;
            <sky:select id="City" onChange="changeCity();" listHeight="300" value="" verify="NotNull"> </sky:select>&nbsp;
            <sky:select id="District" listHeight="300" value="" verify="NotNull"> </sky:select>
            </td>
            </tr>
        <tr>
            <td align="right">地址：</td>
            <td><input name="Address" type="text" id="Address" style="width:250px;" value="" verify="NotNull"/></td>
            <td align="right">邮编：</td>
            <td><input name="ZipCode" type="text" id="ZipCode" value=""/></td>
        </tr>
        <tr>
            <td align="right">电子邮件：</td>
            <td><input name="Email" type="text" id="Email" value=""/></td>
            <td align="right">手机：</td>
            <td><input name="Mobile" type="text" id="Mobile" value=""/></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td align="right"><input type="button" onClick="doSave()" value="提交地址"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
    </form>
    </div>
</td>
</tr>
</table>
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>