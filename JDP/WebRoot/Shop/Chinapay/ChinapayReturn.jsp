<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.io.*,java.lang.*,java.util.*,chinapay.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link TYPE="text/css" HREF="mystyle.css" REL="stylesheet" TITLE="mystyle">
</head>
<%!
 public String strStrim(String val) {
 	if (val != null) {
		val = val.trim();
	}
	return val;
 }
%>
<%
	String  TransDate	= null ;
	String  MerId		= null ;
	String  OrdId		= null ;
	String  TransType	= null ;
	String  TransAmt	= null ;
	String  CuryId	= null ;
	String  ChkValue	= null ;
	String  OrderStatus= null ;
	String  GateId	= null ;
	
	

try
{
	
	
	TransDate	=       request.getParameter("transdate") ;
	MerId		=       request.getParameter("merid") ;
	OrdId		=       request.getParameter("orderno");
	TransType	=       request.getParameter("transtype") ;
	TransAmt	=       request.getParameter("amount") ;
	CuryId		=       request.getParameter("currencycode") ;
	OrderStatus	=       request.getParameter("status") ;
	ChkValue		=       request.getParameter("checkvalue") ;
	
	TransDate	=       strStrim(TransDate);
	MerId		=       strStrim(MerId);
	OrdId		=       strStrim(OrdId);
	TransType	=       strStrim(TransType);
	TransAmt	=      strStrim(TransAmt);
	CuryId		=       strStrim(CuryId);
	OrderStatus	=      strStrim(OrderStatus);
	ChkValue		=       strStrim(ChkValue);
	
%>

<%
	chinapay.PrivateKey key=new chinapay.PrivateKey();
	chinapay.SecureLink t;
	boolean flag;
	boolean flag1;
	String msg="";
			
	//flag=key.buildKey("808080081102269",0,"M:/kaixinren/MerPrK_808080081102269_20090831152543.key");
	//flag=key.buildKey(MerId,0, "M:/kaixinren/MerPrK_808080081102269_20090831152543.key");
	flag=key.buildKey("999999999999999", 0, "M:/kaixinren/PgPubk.key");
	if (flag==false) 
	{
		
		msg="build key error!";
		out.println(msg);
		return;
	} else {
		msg="build key OK!";
		out.println(msg);
		t=new chinapay.SecureLink(key);
		flag1=t.verifyTransResponse(MerId,OrdId, TransAmt, CuryId, TransDate, TransType, OrderStatus, ChkValue);
		if( flag1!=true) 
		{
			out.println("交易验证失败!");
			msg="交易验证失败!";
		}
		else
		{
			out.println("交易验证OK!请关闭页面");
			/* …... 数据库更新等相关处理过程 */
		}
	}
}
catch(Exception e)
{
		out.println("<br>Exception occur:"+ e.getMessage());
		return;
}

%>
