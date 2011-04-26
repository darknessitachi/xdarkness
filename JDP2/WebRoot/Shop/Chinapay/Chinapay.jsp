<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="com.xdarkness.framework.Config"%>
<%
String MerId, OrdId, TransAmt, CuryId, TransDate, TransType,ChkValue;
SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");        
MerId = "808080081102269";
OrdId =request.getParameter("orderID");
OrdId = "2010022690323001";
TransAmt =(String)request.getSession().getAttribute(OrdId);
TransAmt = "000000000001";
request.getSession().removeAttribute(OrdId);
CuryId = "156";
TransDate = sf.format(new Date()).toString();
TransType = "0001";

chinapay.PrivateKey key=new chinapay.PrivateKey();
chinapay.SecureLink t;
boolean flag;
flag=key.buildKey(MerId,0, Config.getContextRealPath()+"Shop/Chinapay/MerPrK_808080081102269.key");
                                       
if (flag==false) {
	out.println("build key error!");
} else {
	out.println("");
}
t=new chinapay.SecureLink (key);
ChkValue= t.signOrder(MerId, OrdId, TransAmt, CuryId, TransDate, TransType) ;
%>
<script>
function mySubmit()
{
	document.forms[0].action=document.forms[0].myAction.value;
	document.forms[0].submit();
	
}
</script>
<form action="https://payment.ChinaPay.com/pay/TransGet" METHOD=POST>
<input type=hidden name="MerId" value="<%=MerId%>" >
您的订单号:<input type=hidden name="OrdId" value="<%=OrdId%>" ><%=OrdId%><br>
订单总金额:<input type=hidden name="TransAmt" value="<%=TransAmt%>"><%=TransAmt%><br>
<input type=hidden name="CuryId" value="<%=CuryId%>">
<input type=hidden name="TransDate" value="<%=TransDate%>">
<input type=hidden name="TransType" value="<%=TransType%>">
<input type=hidden name="Version" value="20040916">
<input type=hidden name="BgRetUrl" value="http://localhost:8080/ZCMS/Shop/Chinapay/ChinapayReturn.jsp">
<input type=hidden name="PageRetUrl" value="http://localhost:8080/ZCMS/Shop/Chinapay/ChinapayReturn.jsp">
<input type=hidden name="GateId" value="">
<input type=hidden name="Priv1" value="kaixinren">
<input type=hidden name="ChkValue" value="<%=ChkValue%>">
<input type="submit" name="submit" value="确定付款" ><br>
</form> 
