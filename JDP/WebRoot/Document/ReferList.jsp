<%@include file="../Include/Init.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>来源列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<style>
.linksTable a{ color:#333; display:block; height:100%; line-height:24px;}
.linksTable a:hover{ text-decoration:none; background-color:#FFF8E8; color:#CC3300}
</style>
</head>
<script>
function onLinkClick(ele){
	window.parent.Parent.$S("ReferName",ele.innerText);
	var names = Cookie.get("ReferNames");
	var txt = ele.innerText;
	if(!names){
		names = txt;
	}else{
		names = (","+names+",").replace(","+txt+",",",");
		names += txt;
		names = names.substring(1);
		if(names.split(",")>36){
			names = names.substring(0,names.indexOf(","));
		}
	}
	Cookie.set("ReferNames",names,36500*24*3600,Server.ContextPath+"Document/");
	window.parent.Parent.$D.close();
}

Page.onLoad(function(){
	onTabChange("%");	
});

//输出字符串
<%=com.xdarkness.cms.site.ReferConfig.getReferList()%>

function onTabChange(c){
	var result = [];
	if(c=='%'){
		$("trContent").hide();
		$("trLast").show();
		showLast();
		return;	
	}
	$("tabContent").innerHTML = "没有相应的来源项!";
	$("trContent").show();
	$("trLast").hide();
	for(var i=0;i<AllNames.length;i++){
		var arr = AllNames[i];
		if(arr.length==0){
			return;
		}
		var abbr = arr[1];
		if(c=="*"&&!isNaN(abbr.substring(0,1))){
			showTable(arr,'tabContent');
			return;
		}
		if(c==abbr.substring(0,1)){
			showTable(arr,'tabContent');
			return;
		}
	}
}

function showLast(){
	var names = Cookie.get("ReferNames");
	if(!names){
		return;
	}
	var arr1 = names.split(",");
	var arr2 = [];
	for(var i=arr1.length-1;i>=0;i--){
		arr2.push(arr1[i]);
		arr2.push("");
	}
	showTable(arr2,'tabLast');
}

function showTable(names,id){
	var arr = [];
	arr.push("<table border='1' bordercolor='#EEEEEE' class='linksTable' width='98%'>");
	for(var i=0;i<names.length;i+=2){
		if(i/2%6==0){
			if(i!=0){
				arr.push("</tr>");	
			}
			arr.push("<tr height='25'>");
		}
		arr.push("<td align='center'><a onclick='onLinkClick(this)' href='#;'>"+names[i]+"</a></td>");
	}
	if(names.length/2%6!=0){
		for(var i=0;i<6-names.length/2%6;i++){
			arr.push("<td align='center'>&nbsp;</td>");			
		}
		arr.push("</tr>");
	}
	arr.push("</table>");
	$(id).innerHTML = arr.join('');
}

function query(){
	$("trSearch").show();
	var keyword = $V("Keyword");
	if(keyword==""){
		return;
	}
	keyword = keyword.toUpperCase();
	var flag = false;
	if(/^[A-Z]+$/.test(keyword)){
		flag = true;
	}
	var r = [];
	for(var i=0;i<AllNames.length;i++){
		var arr = AllNames[i];
		for(var j=0;j<arr.length;j+=2){
			if(flag){
				if(arr[j+1].startsWith(keyword)){
					r.push(arr[j]);
					r.push(arr[j+1]);
				}
			}else{
				if(arr[j].indexOf(keyword)>=0){
					r.push(arr[j]);
					r.push(arr[j+1]);
				}
			}
		}
	}
	showTable(r,"tabSearch");
}
</script>
<body>
<table width="100%" height="100%" border="0">
  <tr id="trContent">
    <td align="center" valign="middle" style="padding:10px 0;" id='tabContent'> </td>
  </tr>
  <tr id="trLast">
    <td align="center" valign="top" style="padding:10px 0;"><table width="100%" border="0" cellspacing="4">
      <tr>
        <td height="30" valign="middle">&nbsp;&nbsp;通过名称或首字母缩写来快速查询来源：
          <input name="Keyword" type="text" class="inputText" id="Keyword" onkeyup="query()" onkeydown="query()" size="40">
          <input name="button" type="button" class="inputButton" onClick="query()" value="查询"></td>
      </tr>
      <tr id="trSearch" style="display:none">
        <td height="40" align="center" valign="middle" id="tabSearch">&nbsp;</td>
      </tr>
      <tr>
        <td height="30">&nbsp;&nbsp;最近使用过的来源：</td>
      </tr>
      <tr>
        <td id='tabLast'>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
