<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style type="text/css">

th.col2, th.col3, td.col2, td.col3 {
	border-left-width:1px;
}
.col2 {
	table-layout:fixed;
	width:150px !important;
}
.col2 div {
	padding-right:100px;
	position:relative;
	text-align:right;
	width:50px; 
	line-height:24px;
}
.percent_bg {
	background-color:#D9E4F8;
	position:absolute;
	right:20px;
	top:10px;
	width:67px;
}
.percent_bg, .percent {
	display:block;
	height:8px;
	overflow:hidden;
	text-align:left;
	text-indent:-999em;
}
.percent {
	background:url(../Images/imgbg01.gif) no-repeat scroll 0 0;
}
</style>
<script src="../Framework/Main.js"></script>
<script>
function save(){
	if(Verify.hasError()){
		return;
	}
	var dc = Form.getData($F("form1"));
	Server.sendRequest("com.xdarkness.cms.dataservice.VoteItem.save",dc,function(response){
		Dialog.alert(response.Message);
	});
}

function preview(){
	if(screen.width==800){
		var width = 800,height = 600,leftm = 0,topm = 0;
	}
	else if(screen.width>800){
	  	var width  = Math.floor( screen.width  * .78 );
  		var height = Math.floor( screen.height * .8 );
  		var leftm  = Math.floor( screen.width  * .1);
 		var topm   = Math.floor( screen.height * .1);
	}
	else{
		return;
	}
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url="VotePreview.jsp?ID=" + $V("ID");
  	window.open(url,"VotePreview");
}

function result(){
	if(screen.width==800){
		var width = 800,height = 600,leftm = 0,topm = 0;
	}
	else if(screen.width>800){
	  	var width  = Math.floor( screen.width  * .78 );
  		var height = Math.floor( screen.height * .8 );
  		var leftm  = Math.floor( screen.width  * .1);
 		var topm   = Math.floor( screen.height * .1);
	} else{
		return;
	}
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url="../Services/VoteResult.jsp?ID=" + $V("ID");
  	window.open(url,"VoteResult");
}

var subjectIndex = 0;
function addSubject(ele){
	ele = $(ele);
	subjectIndex++;
	var table1=$("table1").getParent("table");
	var row=table1.insertRow(ele.getParent("table").getParent("tr").rowIndex+1);
    var td;
    td = document.createElement("TD");
    td.setAttribute('width', '770');
    td.setAttribute('style', 'padding:10px 0;');
    row.appendChild(td);
    td.innerHTML = "asdf";
    td.innerHTML = "<table width='100%' border='0' cellspacing='2' cellpadding='0'>\
              <tr>\
                <td width='50' align='center'><img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upSubject(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnSubject(this);' /></td>\
                <td><div style='border:1px solid #CDDAE6; background-color:#fafafa; padding-bottom:5px;'>\
                    <table border='0' cellpadding='3' cellspacing='0' class='tableborder' style=' background-color:#def; position:relative; top:-15px; left:55px; border:1px solid #CDDAE6; margin-bottom:-10px;'>\
                      <tr>\
                        <td width='50' align='right' nowrap><strong>标题:</strong></td>\
                        <td width='330'><input id='Subject_"+subjectIndex+"' value='' type='text' size='60' style='width:300px;' verify='标题|NotNull'/></td>\
                        <td width='160' nowrap>\
                            <input name='Type_"+subjectIndex+"' type='radio' value='S' checked  onclick='changeSubjectType(\"S\", subjectIndex);'>单选\
                            <input name='Type_"+subjectIndex+"' type='radio' value='D'  onclick='changeSubjectType(\"S\", subjectIndex);'>多选\
                            <input name='Type_"+subjectIndex+"' type='radio' value='W' onclick='changeSubjectType(\"S\", subjectIndex);'>录入</td>\
                      </tr>\
                    </table>\
                    <table width='100%' border='0' cellpadding='2' cellspacing='0' subjectIndex='"+subjectIndex+"' id='ItemType_"+subjectIndex+"_table'>\
                      <tr id='ItemType_"+subjectIndex+"_0_tr'>\
                        <td width='50' align='center'><img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upItem(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnItem(this);' /></td>\
                        <td>\
                            <table width='100%' border='0' cellpadding='2' cellspacing='0' style='border:1px solid #ddd;'>\
							<tr>\
							<td width='50' align='right' nowrap>选项:</td>\
							<td width='220' align='left'><input id='Item_"+subjectIndex+"_"+itemIndex+"' value='' type='text' size='40' style='width:200px;'/></td>\
							<td nowrap>\
							<input name='ItemType_"+subjectIndex+"_"+itemIndex+"' type='radio' value='0' checked onclick='changeItemType(\""+subjectIndex+"_"+itemIndex+"\");'>选择\
				    		<input name='ItemType_"+subjectIndex+"_"+itemIndex+"' type='radio' value='1' onclick='changeItemType(\""+subjectIndex+"_"+itemIndex+"\");'>单行文本\
				    		<input name='ItemType_"+subjectIndex+"_"+itemIndex+"' type='radio' value='2' onclick='changeItemType(\""+subjectIndex+"_"+itemIndex+"\");'>多行文本\
				    		</td>\
							<td nowrap>票数:0<input id='Score_"+subjectIndex+"_"+itemIndex+"' value='0' type='input' size='4' /></td>\
							<td width='160' class='col2'><div>0.0%<span class='percent_bg'><span class='percent' style='width: 0%;'></span></span></div></td>\
							</tr>\
							</table>\
                          </td>\
                        <td width='50' align='center' nowrap><img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addItem(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delItem(this);' /></td>\
                      </tr>\
                    </table>\
                  </div></td>\
                <td width='50' align='center' nowrap><img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addSubject(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delSubject(this);' /></td>\
              </tr>\
            </table>";

}

function delSubject(ele){
	ele = $(ele);
	var table=$("table1").getParent("table");
	if(table.rows.length==3)
	{
		Dialog.alert("还剩最后一标题，不能删除。");return;
	}
	table.deleteRow(ele.getParent("table").getParent("tr").rowIndex);
}

var itemIndex = 0;
function addItem(ele){
	itemIndex++;
	var index = subjectIndex;
	ele = $(ele);
	var row=ele.getParent("table").insertRow(ele.getParent("tr").rowIndex+1);
	if(ele.getParent("table").$A("subjectIndex")){
		index =ele.getParent("table").$A("subjectIndex");
	}
	addItemToRow(row,index);
}

function changeSubjectType(subjecttypevalue, index) {
	var ele = $N("Type_"+index);
	var subjecttype = $NV("Type_"+index);
	for (var i=0; ele!=null && i<ele.length; i++) {
		ele[i].onclick = function(){
			changeSubjectType(subjecttype, index);
		};
	}

	var table = $("ItemType_"+index+"_table");
    
	if ($NV("Type_"+index) && $NV("Type_"+index) == "W" && $NV("Type_"+index) != subjecttypevalue) {
		for (var m=table.rows.length; m>0; m--) {
			table.deleteRow(m-1);
		}
		var row = table.insertRow(0);
		var td;
	    td = document.createElement("TD");
	    td.setAttribute('id', 'ItemType_"+index+"_"+itemIndex+"_td');
	    td.setAttribute('width', '50');
	    td.setAttribute('align', 'center');
	    row.appendChild(td);
		td.innerHTML = "<img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upItem(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnItem(this);' />";
		td = document.createElement("TD");
	    row.appendChild(td);
		td.innerHTML = "<table width='100%' border='0' cellpadding='2' cellspacing='0' style='border:1px solid #ddd;'>\
    		<tr id='ItemType_"+index+"_tr'>\
    		<td width='50' align='right' nowrap>选项:</td>\
    		<td nowrap>\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='1' checked>单行文本\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='2'>多行文本\
    		</td>\
    		<td nowrap>票数:0<input id='Score_"+index+"_"+itemIndex+"' value='0' type='input' size='4' /></td>\
    		<td width='160' class='col2'><div>0.0%<span class='percent_bg'><span class='percent' style='width: 0%;'></span></span></div></td>\
    		</tr>\
    		</table>";
	} else if (subjecttypevalue == "W") {
		for (var m=table.rows.length; m>0; m--) {
			table.deleteRow(m-1);
		}
		var row = table.insertRow(0);
		var td;
	    td = document.createElement("TD");
	    td.setAttribute('id', 'ItemType_"+index+"_"+itemIndex+"_td');
	    td.setAttribute('width', '50');
	    td.setAttribute('align', 'center');
	    row.appendChild(td);
		td.innerHTML = "<img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upItem(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnItem(this);' />";
		td = document.createElement("TD");
	    row.appendChild(td);
		td.innerHTML = "<table width='100%' border='0' cellpadding='2' cellspacing='0' style='border:1px solid #ddd;'>\
    		<tr id='ItemType_"+index+"_tr'>\
    		<td width='50' align='right' nowrap>选项:</td>\
    		<td width='220' align='left'><input id='Item_"+index+"_"+itemIndex+"' value='' type='text' size='40' style='width:200px;' verify='选项|NotNull'/></td>\
    		<td nowrap>\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='0' checked onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>选择\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='1' onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>单行文本\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='2' onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>多行文本\
    		</td>\
    		<td nowrap>票数:0<input id='Score_"+index+"_"+itemIndex+"' value='0' type='input' size='4' /></td>\
    		<td width='160' class='col2'><div>0.0%<span class='percent_bg'><span class='percent' style='width: 0%;'></span></span></div></td>\
    		</tr>\
    		</table>";
    	td = document.createElement("TD");
    	td.setAttribute('width', '50');
    	td.setAttribute('align', 'center');
    	row.appendChild(td);
    	td.innerHTML = "<img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addItem(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delItem(this);' />";
	}
}

function addItemToRow(row,index){
    var td;
    td = document.createElement("TD");
    td.setAttribute('id', 'ItemType_"+index+"_"+itemIndex+"_td');
    td.setAttribute('width', '50');
    td.setAttribute('align', 'center');
    row.appendChild(td);
	td.innerHTML = "<img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upItem(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnItem(this);' />";
	td = document.createElement("TD");
    row.appendChild(td);
    if ($NV("Type_"+index) && $NV("Type_"+index) == "W") {
    	td.innerHTML = "<table width='100%' border='0' cellpadding='2' cellspacing='0' style='border:1px solid #ddd;'>\
    		<tr id='ItemType_"+index+"_tr'>\
    		<td width='50' align='right' nowrap>选项:</td>\
    		<td nowrap>\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='1' checked>单行文本\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='2'>多行文本\
    		</td>\
    		<td nowrap>票数:0<input id='Score_"+index+"_"+itemIndex+"' value='0' type='input' size='4' /></td>\
    		<td width='160' class='col2'><div>0.0%<span class='percent_bg'><span class='percent' style='width: 0%;'></span></span></div></td>\
    		</tr>\
    		</table>";
    } else {
    	td.innerHTML = "<table width='100%' border='0' cellpadding='2' cellspacing='0' style='border:1px solid #ddd;'>\
    		<tr id='ItemType_"+index+"_tr'>\
    		<td width='50' align='right' nowrap>选项:</td>\
    		<td width='220' align='left'><input id='Item_"+index+"_"+itemIndex+"' value='' type='text' size='40' style='width:200px;' verify='选项|NotNull'/></td>\
    		<td nowrap>\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='0' checked onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>选择\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='1' onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>单行文本\
    		<input name='ItemType_"+index+"_"+itemIndex+"' type='radio' value='2' onclick='changeItemType(\""+index+"_"+itemIndex+"\");'>多行文本\
    		</td>\
    		<td nowrap>票数:0<input id='Score_"+index+"_"+itemIndex+"' value='0' type='input' size='4' /></td>\
    		<td width='160' class='col2'><div>0.0%<span class='percent_bg'><span class='percent' style='width: 0%;'></span></span></div></td>\
    		</tr>\
    		</table>";
    	td = document.createElement("TD");
    	td.setAttribute('width', '50');
    	td.setAttribute('align', 'center');
    	row.appendChild(td);
    	td.innerHTML = "<img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addItem(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delItem(this);' />";
    }
}

function delItem(ele){
	ele = $(ele);
	var table=ele.getParent("table");
	if(table.rows.length=="1")
	{
		Dialog.alert("还剩最后一行，不能删除。");return;
	}
	table.deleteRow(ele.getParent("tr").rowIndex);
}

function upSubject(ele){
	ele = $(ele);
	var self = ele.getParent("table").getParent("tr");
	if(self.rowIndex==2){
		return;
	}
	var p = self.parentElement;
	var dest = self.previousSibling;
	while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.previousSibling;
	}
	p.insertBefore(self,dest);
}

function dnSubject(ele){
	ele = $(ele);
	var self = ele.getParent("table").getParent("tr");
	var p = self.parentElement;
	if(self.rowIndex==p.rows.length-1){
		return;
	}else if(self.rowIndex==p.rows.length-2){
		p.appendChild(self);
		return;
	}
	var dest = self.nextSibling;
    while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.nextSibling;
	}
	dest = dest.nextSibling;
    while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.nextSibling;
	}
	p.insertBefore(self,dest);
}

function upItem(ele){
	ele = $(ele);
	if(ele.getParent("tr").rowIndex==0){
		return;
	}
	var self = ele.getParent("tr");
	var p = self.parentElement;
	var dest = self.previousSibling;
	while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.previousSibling;
	}
	p.insertBefore(self,dest);
}

function dnItem(ele){
	ele = $(ele);
	var self = ele.getParent("tr");
	var p = self.parentElement;
	if(self.rowIndex==p.rows.length-1){
		return;
	}else if(self.rowIndex==p.rows.length-2){
		p.appendChild(self);
		return;
	}
	var dest = self.nextSibling;
    while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.nextSibling;
	}
	dest = dest.nextSibling;
    while(!dest||!dest.getAttribute||!dest.tagName=='TR'){
		dest = dest.nextSibling;
	}
	p.insertBefore(self,dest);
}

function changeItemType(itemIndex) {
	if ($NV("ItemType_"+itemIndex) == "0") {
		$("Item_"+itemIndex).setAttribute("verify","不能为空|NotNull");
		Verify.initCtrl($("Item_"+itemIndex));
	} else {
		$("Item_"+itemIndex).setAttribute("verify","");
		Verify.initCtrl($("Item_"+itemIndex));
	}
}
</script>
</head>
<body>
<form id="form1">
<div id="alert"></div>
<table id="table0" width="100%" border="0" cellspacing="6"
	cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table id="table1" width="100%" border="0" cellspacing="0"
			cellpadding="6" class="blockTable">
			<%
			String ID = request.getParameter("ID");
			ZCVoteSchema vote = new ZCVoteSchema();
			vote.setID(ID);
			vote.fill();
			
			%>
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon018a1.gif" /> <b><%=vote.getTitle() %></b>&nbsp;<span
					style="color:#666666;">调查明细录入</span> <input type="hidden" name="ID"
					id="ID" value="<%=vote.getID()%>" /></td>
			</tr>
			<tr>
				<td style="padding:0 8px 10px;"><sky:button onClick="save()">
					<img src="../Icons/icon032a16.gif" />保存</sky:button> <sky:button
					onClick="preview()">
					<img src="../Icons/icon032a15.gif" />预览</sky:button> <sky:button
					onClick="result()">
					<img src="../Icons/icon032a15.gif" />结果</sky:button></td>
			</tr>
			<%
				ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
				ZCVoteSubjectSet subjectSet = subject.query(new QueryBuilder(" where voteID =? order by OrderFlag,ID",vote.getID()));
				if(subjectSet.size()==0){
					subject.setID(0);
					subject.setSubject("");
					subjectSet.add(subject);
				}
				for(int i = 0;i<subjectSet.size();i++){
					subject = subjectSet.get(i);
				%>
			<tr>
				<td width="770" style="padding:10px 0;">
				<table width="100%" border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td width="50" align="center">
						<%if(vote.getVoteCatalogID()==0){%>
						<img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upSubject(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnSubject(this);' />
						<%}%>
						</td>
						<td>
						<div style="border:1px solid #CDDAE6; background-color:#fafafa; padding-bottom:5px;">
						<table border="0" cellpadding='3' cellspacing='0' class='tableborder' style=" background-color:#def; position:relative; top:-15px; left:55px; border:1px solid #CDDAE6; margin-bottom:-10px;">
							<tr>
								<td width="50" align='right' nowrap><strong>标题:</strong></td>
								<td width="330">
								<input id='Subject_<%=subject.getID()%>' value='<%=subject.getSubject()%>' type='text' size='60' style="width:300px;" verify='标题|NotNull' /></td>
								<td width="160" nowrap> 
								<%if("D".equals(subject.getType()) || StringUtil.isEmpty(subject.getType())) {%>
								<input name='Type_<%=subject.getID()%>' type='radio' value='S' 
									onclick="changeSubjectType('D', <%=subject.getID()%>);">单选
								<input name='Type_<%=subject.getID()%>' type='radio' value='D' 
									onclick="changeSubjectType('D', <%=subject.getID()%>);" checked>多选
								<input name='Type_<%=subject.getID()%>' type='radio' value='W'  
									onclick="changeSubjectType('D', <%=subject.getID()%>);">录入
								<%} else if ("W".equals(subject.getType())) {%>
								<input name='Type_<%=subject.getID()%>' type='radio' value='S' 
									onclick="changeSubjectType('W', <%=subject.getID()%>);">单选
								<input name='Type_<%=subject.getID()%>' type='radio' value='D' 
									onclick="changeSubjectType('W', <%=subject.getID()%>);">多选
								<input name='Type_<%=subject.getID()%>' type='radio' value='W' 
									onclick="changeSubjectType('W', <%=subject.getID()%>);" checked>录入
								<%} else {%>
								<input name='Type_<%=subject.getID()%>' type='radio' value='S' 
									onclick="changeSubjectType('S', <%=subject.getID()%>);" checked>单选
								<input name='Type_<%=subject.getID()%>' type='radio' value='D' 
									onclick="changeSubjectType('S', <%=subject.getID()%>);">多选
								<input name='Type_<%=subject.getID()%>' type='radio' value='W' 
									onclick="changeSubjectType('S', <%=subject.getID()%>);">录入
								<%} %></td>
							</tr>
						</table>
						<table width='100%' border="0" cellpadding="2" cellspacing="0" subjectindex='<%=subject.getID()%>' id='ItemType_<%=subject.getID()%>_table'>
							<%
									ZCVoteItemSchema item = new ZCVoteItemSchema();
									long total = new QueryBuilder("select sum(score) from zcvoteitem where voteID = ? and subjectID = ? order by ID",vote.getID(),subject.getID()).executeLong();
									ZCVoteItemSet itemSet = item.query(new QueryBuilder("where voteID = ? and subjectID = ? order by OrderFlag,ID",vote.getID(),subject.getID()));
									if(itemSet.size()==0){
										item.setItem("");
										itemSet.add(item);
									}
									for(int j=0;j<itemSet.size();j++){
										item = itemSet.get(j);
										double percent = 100.0;
										if(total==0){
											percent = 0;
										}else if(item.getScore()<total){
											percent = Math.round((item.getScore()*100.0)/total);
										}

									%>
							<tr>
								<td width="50" align="center">
								<%if(vote.getVoteCatalogID()==0){%>
								<img name='imgup' style='cursor:pointer' src='../Icons/icon_upwards.gif' onClick='upItem(this);' />&nbsp;<img name='imgdn' style='cursor:pointer' src='../Icons/icon_adown.gif' onClick='dnItem(this);' />
								<%}%>
								</td>
							  <td>
								<table width="100%" border="0" cellpadding="2" cellspacing="0" style="border:1px solid #ddd;">
									<tr>
										<td width='50' align='right' nowrap>选项:</td>
										<%if ("W".equals(subject.getType())) {%>
										<td nowrap>
										<input name='ItemType_<%=subject.getID()%>_<%=item.getID()%>' type='radio' value='1' <%if("1".equals(item.getItemType())) out.print("checked");%>>单行文本
										<input name='ItemType_<%=subject.getID()%>_<%=item.getID()%>' type='radio' value='2' <%if("2".equals(item.getItemType())) out.print("checked");%>>多行文本</td>
										<td nowrap>票数:<input id='Score_<%=subject.getID()%>_<%=item.getID()%>' value='<%=item.getScore()%>' type='input' size="4"/></td>
										<td width="160" class='col2'>
										<div><%=percent%>%<span class='percent_bg'><span class='percent' style='width: <%=percent%>%;'></span></span></div>
										</td>
										<%} else {%>
										<td width="220" align='left'>
										<input id='Item_<%=subject.getID()%>_<%=item.getID()%>' value='<%=item.getItem()%>' type='text' size='40' style="width:200px;" /></td>
										<td nowrap>
										<input name='ItemType_<%=subject.getID()%>_<%=item.getID()%>' type='radio' 
											value='0' <%if("0".equals(item.getItemType()) 
												|| StringUtil.isEmpty(item.getItemType())) out.print("checked");%> 
												onclick="changeItemType('<%=subject.getID()%>_<%=item.getID()%>');">选择
										<input name='ItemType_<%=subject.getID()%>_<%=item.getID()%>' type='radio' 
											value='1' <%if("1".equals(item.getItemType())) out.print("checked");%> 
											onclick="changeItemType('<%=subject.getID()%>_<%=item.getID()%>');">单行文本
										<input name='ItemType_<%=subject.getID()%>_<%=item.getID()%>' type='radio' 
											value='2' <%if("2".equals(item.getItemType())) out.print("checked");%> 
											onclick="changeItemType('<%=subject.getID()%>_<%=item.getID()%>');">多行文本</td>
										<td nowrap>票数:<input id='Score_<%=subject.getID()%>_<%=item.getID()%>' value='<%=item.getScore()%>' type='input' size="4"/></td>
										<td width="160" class='col2'>
										<div><%=percent%>%<span class='percent_bg'><span class='percent' style='width: <%=percent%>%;'></span></span></div>
										</td>
										<%} %>
									</tr>
								</table>
								</td>
								<td width="50" align="center" nowrap>
								<%if(vote.getVoteCatalogID()==0 && !"W".equals(subject.getType())){%>
								<img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addItem(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delItem(this);' />
								<%}%>
								</td>
							</tr>
							<%} %>
						</table>
						</div>
						</td>
						<td width="50" align="center" nowrap>
						<%if(vote.getVoteCatalogID()==0){%>
						<img name='addimg' style='cursor:pointer' src='../Icons/icon403a20.gif' onClick='addSubject(this);' />&nbsp;<img name='delimg' style='cursor:pointer' src='../Icons/icon403a19.gif' onClick='delSubject(this);' />
						<%}%>
						</td>
					</tr>
				</table>
				<table width='100%' cellpadding='2' cellspacing='0' class='tableborder'>
					<tr>
						<td></td>
					</tr>
				</table>
				</td>
			</tr>
			<%} %>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
