<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<html style=" overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字母导航</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script language="javascript">
function tabchange(id2){
	var tabint = id2;
	var id1=id2-1;
	var id3=id2-1+2; 
	tabchangeall();
	
	id2=id2-2+2;
	
	if(id2<10){
		tab1="tab00"+id1;
		tab2="tab00"+id2;
		tab3="tab00"+id3;
	}else if (id2==10){
		tab1="tab00"+id1;
		tab2="tab0"+id2;
		tab3="tab0"+id3;
	}else{
		tab1="tab0"+id1;
		tab2="tab0"+id2;
		tab3="tab0"+id3;
	}
	var tabs = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"];
	changeTab(tabs[tabint/2-1].toUpperCase());;
}


function tabchangelast(){
	tabchangeall();
	changeTab("*");;
}

function tabchangestart(){
	tabchangeall();
	changeTab("%");;
}

function tabchangeall(){	
	for(var i=1;i<52;){
		if (i<9){
			tab1="tab00"+i;
			tt=i-1+2;
			tab2="tab00"+tt;
		} else	if (i==9){
			tab1="tab00"+i;
			tt=i-1+2;
			tab2="tab0"+tt;	
		}else	if (i>9){
			tab1="tab0"+i;
			tt=i-1+2;
			tab2="tab0"+tt;	
		}
		if(tab2=="tab052"){
			tab2="tab050";
		}
		i=i+2;
	}
}

function changeTab(c){
	changePage();
	parent.vFrame1.window.onTabChange(c);
}

function changePage(evt){
	var ele = getEvent(evt).srcElement;
	if(ele.nodeName == "B"){
		ele = ele.parentElement;
	}
	var arr = ele.parentElement.getElementsByTagName("A");
	for(var i=0,j=arr.length;i<j;i++){
		 if (arr[i].className=="current"){
	     arr[i].className = "";
	   }
	}
	ele.className="current";
}
</script>
</head>
<body scroll="no">
<table width="100%"><tr>
<td bgcolor="#f7f8fd" class="pagetab">
<div id="pageList">
<a class="current" href="#;" id="start" onmouseover="tabchangestart()"><b>最近使用</b></a>
<a href="#;" id="tab002" onmouseover="tabchange('002')"><b>A</b></a>
<a href="#;" id="tab004" onmouseover="tabchange('004')"><b>B</b></a>
<a href="#;" id="tab006" onmouseover="tabchange('006')"><b>C</b></a>
<a href="#;" id="tab008" onmouseover="tabchange('008')"><b>D</b></a>
<a href="#;" id="tab010" onmouseover="tabchange('010')"><b>E</b></a>
<a href="#;" id="tab012" onmouseover="tabchange('012')"><b>F</b></a>
<a href="#;" id="tab014" onmouseover="tabchange('014')"><b>G</b></a>
<a href="#;" id="tab016" onmouseover="tabchange('016')"><b>H</b></a>
<a href="#;" id="tab018" onmouseover="tabchange('018')"><b>I</b></a>
<a href="#;" id="tab020" onmouseover="tabchange('020')"><b>J</b></a>
<a href="#;" id="tab022" onmouseover="tabchange('022')"><b>K</b></a>
<a href="#;" id="tab024" onmouseover="tabchange('024')"><b>L</b></a>
<a href="#;" id="tab026" onmouseover="tabchange('026')"><b>M</b></a>
<a href="#;" id="tab028" onmouseover="tabchange('028')"><b>N</b></a>
<a href="#;" id="tab030" onmouseover="tabchange('030')"><b>O</b></a>
<a href="#;" id="tab032" onmouseover="tabchange('032')"><b>P</b></a>
<a href="#;" id="tab034" onmouseover="tabchange('034')"><b>Q</b></a>
<a href="#;" id="tab036" onmouseover="tabchange('036')"><b>R</b></a>
<a href="#;" id="tab038" onmouseover="tabchange('038')"><b>S</b></a>
<a href="#;" id="tab040" onmouseover="tabchange('040')"><b>T</b></a>
<a href="#;" id="tab042" onmouseover="tabchange('042')"><b>U</b></a>
<a href="#;" id="tab044" onmouseover="tabchange('044')"><b>V</b></a>
<a href="#;" id="tab046" onmouseover="tabchange('046')"><b>W</b></a>
<a href="#;" id="tab048" onmouseover="tabchange('048')"><b>X</b></a>
<a href="#;" id="tab050" onmouseover="tabchange('050')"><b>Y</b></a>
<a href="#;" id="tab051" onmouseover="tabchange('052')"><b>Z</b></a>
<a href="#;" id="last" onmouseover="tabchangelast()"><b>其他</b></a>
</div>
</td>
</tr>
</table>
</body>
</html>
