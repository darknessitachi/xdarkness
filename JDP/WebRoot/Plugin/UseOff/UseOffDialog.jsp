<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="jaf-controls" prefix="jaf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/Include/Default.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/Framework/Main.js"></script>
</head>
<body>
<form id="form2">
<jaf:init method="com.xdarkness.plugin.useoff.UseOffPage.init">
	<table width="100%" height="100%" border="0">
		<tr>
			<td>
			<table width="590" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td width="168" height="10" class="tdgrey2"></td>
					<td width="412" class="tdgrey2"></td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">金额：</td>
					<td class="tdgrey2">
						<input name="money" type="text"
							value="" class="inputText" id="money" size=40 />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">类别：</td>
					<td class="tdgrey2">
					<jaf:select id="useFor" style="width:100px;">
						<span value="吃饭">吃饭</span>
						<span value="房租水电">房租水电</span>
						<span value="生活用品">生活用品</span>
						<span value="坐车">坐车</span>
						<span value="看病">看病</span>
						<span value="家">家</span>
						<span value="买书">买书</span>
						<span value="话费">话费</span>
						<span value="其他">其他</span>
						
					</jaf:select>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">方式：</td>
					<td class="tdgrey2">
						<label> <input type="radio" name="moneyType" value="out" id="type0" checked>支出</label>
						<label> <input type="radio" name="moneyType" value="in" id="type1">收入</label>
					</td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">日期：</td>
					<td class="tdgrey2">
						<input name="createTime" type="text" class="inputText"
						id="StartTime" xtype="Date" size=14 verify="NotNull" />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">描述：</td>
					<td class="tdgrey2">
						<input name="description" id="description" type="text"
							value="" class="inputText" size=40 />
					</td>
				</tr>
				<tr>
					<td height="30" align="right" class="tdgrey1">&nbsp;</td>
					<td class="tdgrey2">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</jaf:init></form>
</body>
</html>
