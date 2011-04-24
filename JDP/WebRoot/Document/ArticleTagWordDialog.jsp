<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
function getSelectedTagWord(){
    return $NV("TagWord");
}

/**
选中已经有的TagWord
**/
function setSelectedTagWord(TagWord){
    if(TagWord != ""){
	    var arrSelected = TagWord.split(",");
		var arrKeys = $N("TagWord");
		for(var i = 0;i<arrKeys.length;i++){
		    for(var j = 0;j<arrSelected.length;j++){
			   if(arrKeys[i].value == arrSelected[j]){
			       arrKeys[i].checked = true;
			   }
			}
		}
	}
}
</script>
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td>
		<div style="text-align:left; padding-left:25px;">Tag列表</div>
		</td>
	</tr>
	<tr>
		<td align="center">
		<div
			style='background-color:#f5fbfe; border:#c3ced9 1px solid; padding:4px 3px 10px 3px; overflow:auto;height:255px'>
		<table width="100%" border="0">
			<%
      ZCTagSchema tag = new ZCTagSchema();
	  ZCTagSet set = tag.query(new QueryBuilder(" where 1=1 order by id desc"));
	  for(int i = 0 ;i<set.size();i++){
	     tag = set.get(i);
  %>
			<tr>
				<td style="padding-left: 20px" align="left"><label for="<%=tag.getID()%>"> <input
					name="TagWord" type="checkbox" value="<%=tag.getTag()%>"
					id="<%=tag.getID()%>"> <%=tag.getTag()%> </label></td>
			</tr>
			<%
     }
  %>
		</table>
		</div>
		</td>
	</tr>
</table>
</form>
</body>
</html>
