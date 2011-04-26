<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function del(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的评论！");
		return;
	}
	Dialog.confirm("您确认要删除评论吗?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.cms.dataservice.Comment.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	})
}

function changeVerifyStatus(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.loadData("dg1");
}

function changeCatalogList(){
	if(isIE){
		$("SelectCatalogID").listURL="DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType");
	}else{
		$("SelectCatalogID").setAttribute("listURL","DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType"));
	}
	Selector.setValueEx("SelectCatalogID",'','');
	$S("CatalogID","");
	DataList.setParam("dg1","CatalogID","");
	DataList.setParam("dg1","CatalogType",$V("CatalogType"));
	DataList.loadData("dg1");
}

function setCatalogID(){
	if($V("SelectCatalogID")!=0){
		$S("CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogType",$V("CatalogType"));
		DataList.loadData("dg1");
	}
}

function reloadList(){
	DataList.loadData("dg1");
}

function Pass(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的评论！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","Pass");
	Server.sendRequest("com.xdarkness.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function NoPass(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的评论！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","NoPass");
	Server.sendRequest("com.xdarkness.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

var selectFlag = false;
function selectAll(){
	if(selectFlag){
		selectFlag = false;
	}else{
		selectFlag = true;
	}
	var arr = $N("CommentID");
	for(var i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 评论管理</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <sky:button onClick="selectAll()"><img src="../Icons/icon018a11.gif" />全选</sky:button>
                    <sky:button onClick="Pass()"><img src="../Icons/icon018a2.gif" />审核通过</sky:button>
                    <sky:button onClick="NoPass()"><img src="../Icons/icon018a1.gif" />审核不通过</sky:button>
                    <sky:button onClick="del()"><img src="../Icons/icon018a3.gif" />删除</sky:button>
                    &nbsp;&nbsp;<font color="#999999">选择栏目类型</font>
                    <sky:select id="CatalogType" style="width:60px;" onChange="changeCatalogList();">
                        <span value="1">文档</span>
                        <span value="4">图片</span>
                        <span value="5">视频</span>
                        <span value="6">音频</span>
                        <span value="7">附件</span>
                    </sky:select>
                    &nbsp;<font color="#999999">选择栏目</font>
                    <sky:select id="SelectCatalogID" style="width:120px" onChange="setCatalogID()" listWidth="200" listHeight="300" listURL="DataService/CommentCatalogSelector.jsp?Type=1"> </sky:select>
                     &nbsp;<font color="#999999">审核状态</font>
                    <sky:select id="VerifyStatus" style="width:60px;" onChange="changeVerifyStatus();">
                        <span value="X">未审核</span>
                        <span value="Y">已通过</span>
                        <span value="N">未通过</span>
                        <span value="All">全部</span>
                    </sky:select>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<sky:datalist id="dg1" method="com.xdarkness.cms.dataservice.Comment.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr xtype="head" class="dataTableHead">
                                <td width="8%" align="right"><input type="checkbox" name="CommentID" value="${ID}" /></td>
								<td width="65%"><a href="${PreLink}" title="${Name}" target="_blank"><b>${Name}</b></a>&nbsp;&nbsp;
                                关联ID: ${RelaID}</td>
                                <td width="27%" align="right" style=" font-weight:normal;">
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Pass(${ID})">审核通过</a>
                                <img src="../Icons/icon404a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="NoPass(${ID})">审核不通过</a>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">删除</a>
                                </td>
							</tr>
                            <tr>
                            	<td align="right">评论标题：</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" height="120">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">评论者：</td>
                                        <td width="59%" style=" border-bottom:none;">${AddUser}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">所属栏目：</td>
                                        <td style=" border-bottom:none;"><span title="${CatalogName}">${CatalogName}</span></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">IP：</td>
                                        <td style=" border-bottom:none;">${AddUserIP}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">留言时间：</td>
                                        <td style=" border-bottom:none;">${AddTime}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">审核状态：</td>
                                        <td style=" border-bottom:none;"><span title="${VerifyFlagName}" style="color:${FlagColor}">${VerifyFlagName}</span></td>
                                    </tr>
                                </table>
                                </td>
                            </tr>
                            <tr>
                              <td align="right">内容：</td>
                              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal" height="100">
                              <span title="${Content}">${Content}</span>
                              </td>
                            </tr>
						</table>
				  </sky:datalist>
                  <sky:pagebar target="dg1" />
                  </td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
	</body>
</html>
