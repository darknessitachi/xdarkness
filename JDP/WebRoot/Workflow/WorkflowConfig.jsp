<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<sky:init method="com.xdarkness.workflow.WorkflowPage.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Config.getAppCode()%>流程定制</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
.thinTable{ margin:3px 0;}
.thinTable td{ padding-left:4px;}
.thinTable img{ cursor:pointer;}
.attribPanel {width:200px; border:1px solid #d2dbe5; padding:1px; margin-bottom:10px;}
.attribPanel h5{background:#ddeeff; color:#226699; padding:2px 6px; margin-bottom:1px;}
.attribPanel {width:240px; border:1px solid #d2dbe5; padding:1px; margin-bottom:10px;}
</style>
<script src="../Framework/Main.js"></script>
<script src="../Tools/Swfobject.js"></script>
<script language="JavaScript" type="text/javascript">
var fe = null;

var xml = "${XML}";

Page.onLoad(function(){
	$("ConditionProp").hide();
	$("NodeProp").hide();
	$("NodeAction").hide();

	var so = new SWFObject("../Workflow/ZFlow.swf", "FlowEditor", "742", "910", "9", "#ffffff");
	if(isGecko){
		so.addParam("wmode", "Opaque");
	}else{
		so.addParam("wmode", "Opaque");
	}
  	so.write("FlashDiv");
});

function addStart(){
	fe.addStart()
}

function addNode(){
	fe.addNode()
}

function addActionNode(){
	fe.addActionNode()
}

function addEnd(){
	fe.addEnd()
}

function autoLayout(){
	fe.autoLayout()
}

function deleteSelected(){
	var flag = fe.deleteSelected();
	if(flag==0){
		Dialog.alert("没有选中项!");
	}
	if(flag==1){
		Dialog.alert("开始步骤和结束步骤不能被删除!");
	}
}

function addCondition(){
	var id = fe.getSelectedElementID();
	if(!id||id.indexOf("Node")<0){
		Dialog.alert("请先选择一个节点!");
	}else{
		var diag = new Dialog("Node");
		diag.URL = "Workflow/NodeListDialog.html";
		diag.Width = 300;
		diag.Height = 100;
		diag.Title = "选择目标节点";
		var arr = fe.getNodeList();
		diag.onLoad = function(){
			$DW.$("Target").clear();
			for(var i=0;i<arr.length;i++){
				if(id.startsWith("Action")&&!arr[i][1].startsWith("ActionNode")){
					$DW.$("Target").add(arr[i][0],arr[i][1]);
				}
				if(!id.startsWith("Action")&&arr[i][1].startsWith("ActionNode")){
					$DW.$("Target").add(arr[i][0],arr[i][1]);
				}				
			}			
		}
		diag.OKEvent = function(){
			fe.addCondition(fe.getSelectedElementID(),$DW.$V("Target"));
			$D.close();
		}
		diag.show();
	}
}

function onNodeSelected(){
	var id = fe.getSelectedElementID();
	
	$("NodeProp").show();
	$("NodeAction").show();
	$("ConditionProp").hide();
	
	$("trUser").show();
	$("trOrgan").show();
	$("trRole").show();

	$("trPostAction").show();
	$("trPostScript1").show();
	$("trPostScript2").show();
	$("trPreAction").show();
	$("trPreScript1").show();
	$("trPreScript2").show();
	$("trAllowSelectUser").hide();

	$("NodeName").value = fe.getSelectedElementText();
	if(id.startWith("StartNode")||id.startWith("EndNode")||id.startWith("ActionNode")){
		$("trUser").hide();
		$("trOrgan").hide();
		$("trRole").hide();
		if(id.startWith("EndNode")){//结束节点隐藏一些元素
			$("trPostAction").hide();
			$("trPostScript1").hide();
			$("trPostScript2").hide();
			if(fe.getData("PreAction")==""){
				fe.setData("PreAction","com.xdarkness.cms.workflow.WaitPublishMethod");//CMS默认为待发布
			}
		}
		if(id.startWith("StartNode")){//开始节点隐藏一些元素
			$("trPreAction").hide();
			$("trPreScript1").hide();
			$("trPreScript2").hide();
			
		}
		if(id.startWith("ActionNode")){//动作节点隐藏一些元素
			$("trPreAction").hide();
			$("trPreScript1").hide();
			$("trPreScript2").hide();
			$("trAllowSelectUser").show();
		}
		$("trSignJointly").hide();
	}else{
		$("trSignJointly").show();
	}
	if(id.startsWith("ActionNode")&&fe.getLinkedNodeCount()>1){
		$("trAllowSelectLine").show();
	}else{
		$("trAllowSelectLine").hide();
	}
	$S("AllowSelectUser",fe.getData("AllowSelectUser"));
	$S("AllowSelectLine",fe.getData("AllowSelectLine"));
	$S("SignJointly",fe.getData("SignJointly"));
	$S("PreAction",fe.getData("PreAction"));
	$S("PostAction",fe.getData("PostAction"));
	$S("PreScript",fe.getData("PreScript"));
	$S("PostScript",fe.getData("PostScript"));
	
	var organs = fe.getData("Organ");
	if(!organs){
		$("tdOrgan").innerHTML = "没有";
	}else{
		var html = [];
		var arr = organs.split(",");
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		for(var i=0;i<arr.length;i+=2){
			html.push("<tr><td>"+arr[i+1]+"</td></tr>");
		}
		html.push("</table>");
		$("tdOrgan").innerHTML = html.join('');
	}
	
	var roles = fe.getData("Role");
	if(!roles){
		$("tdRole").innerHTML = "没有";
	}else{
		var html = [];
		var arr = roles.split(",");
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		for(var i=0;i<arr.length;i+=2){
			html.push("<tr><td>"+arr[i]+"("+arr[i+1]+")"+"</td></tr>");
		}
		html.push("</table>");
		$("tdRole").innerHTML = html.join('');
	}
		
	var users = fe.getData("User");
	if(!users){
		$("tdUser").innerHTML = "没有";
	}else{
		var html = [];
		var arr = users.split(",");
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		for(var i=0;i<arr.length;i+=2){
			html.push("<tr><td>"+arr[i]+"("+arr[i+1]+")"+"</td></tr>");
		}
		html.push("</table>");
		$("tdUser").innerHTML = html.join('');
	}
}

function onNodeUnSelected(){
	$("NodeProp").hide();
	$("NodeAction").hide();
}

function onLineSelected(){
	$("NodeProp").hide();
	$("NodeAction").hide();
	$("ConditionProp").show();
	$("ConditionName").value = fe.getSelectedElementText();
	$("ConditionScript").value = fe.getData("Script");
	$("ConditionMethod").value = fe.getData("Method");
}

function onLineUnSelected(){
	$("ConditionProp").hide();
}

function setSelectedElementText(ele){
	fe.setSelectedElementText($V(ele));
}

function afterInit(){
	fe = $('FlowEditor');
	if(!xml){
		fe.addStart();
		fe.addEnd();
		fe.autoLayout();
		return;
	}
	
	if(isIE){
		xml = xml.substring(xml.indexOf(">")+1);
	}
	
	var doc = toXMLDOM(xml);
	var nodes = doc.firstChild.childNodes;
	var len = nodes.length;
	var map = {};
	for(var i=0;i<len;i++){
		var node = nodes[i];
		var name = node.nodeName;
		if (name=="config") {
			map[node.getAttribute("name")] = node.getAttribute("value");
		}
	}
	fe.setImageOptions(map["VDistance"],map["HDistance"],map["NodeWidth"],map["NodeHeight"],map["OnlyBeeLine"],map["TopPositionFirst"]);
	try{
		fe.setFlowData("NotNeedApply",map["NotNeedApply"]);
		fe.setFlowData("NotifyNextStep",map["NotifyNextStep"]);
		if(map["NotNeedApply"]=="1"){
			$("NotNeedApply").checked = true;
		}
		if(map["NotifyNextStep"]=="1"){
			$("NotifyNextStep").checked = true;
		}
	}catch(ex){alert(ex.message)}
	for(var i=0;i<len;i++){
		var node = nodes[i];
		var name = node.nodeName;
		if (name=="node") {
			fe.addNodeEx(node.getAttribute("name"),node.getAttribute("id"),node.getAttribute("x"),node.getAttribute("y"));
			var datas = node.childNodes;
			if(!datas){
				continue;
			}
			for(var j=0;j<datas.length;j++){
				name = datas[j].nodeName;
				 if (name=="data") {					
					var k = datas[j].getAttribute("type");
					var v = datas[j].firstChild.nodeValue;
					fe.setData(k,v);
				}
			}
		}
	}
	for(var i=0;i<len;i++){//先添加所有节点，然后才能添加条件
		var node = nodes[i];
		var name = node.nodeName;
		if (name=="node") {
			var datas = node.childNodes;
			if(!datas){
				continue;
			}
			for(var j=0;j<datas.length;j++){
				name = datas[j].nodeName;
				if (name=="line") {
					dealOneLine(node.getAttribute("id"),datas[j]);
				}
			}
		}
	}
}
	
function dealOneLine(srcID,line){
	var name = line.getAttribute("name");
	var target = line.getAttribute("target");
	fe.addCondition(srcID,target);
	fe.setSelectedElementText(name);
	var datas = line.childNodes;
	if(!datas){
		return;
	}
	for(var j=0;j<datas.length;j++){
		if (datas[j].nodeName=="data") {	
			var k = datas[j].getAttribute("type");
			var v = datas[j].firstChild.nodeValue;
			fe.setData(k,v);
		}
	}
}

function check(){
	var msg = fe.check();
	if(!msg){
		msg = "未发现问题.";
	}
	Dialog.alert(msg);
}

function imageOptions(){
	var diag = new Dialog("Node");
	diag.URL = "Workflow/ImageOptions.html";
	diag.Width = 400;
	diag.Height = 250;
	diag.Title = "图形选项";
	var arr = fe.getImageOptions().split(",");
	diag.onLoad = function(){
		$DW.$S("VDistance",arr[0]);
		$DW.$S("HDistance",arr[1]);
		$DW.$S("NodeWidth",arr[2]);
		$DW.$S("NodeHeight",arr[3]);
		$DW.$S("OnlyBeeLine",arr[4]);
		$DW.$NS("TopPositionFirst",arr[5]);
	}
	diag.OKEvent = function(){
		var b = $DW.$V("OnlyBeeLine");
		var t = $DW.$NV("TopPositionFirst")[0];
		fe.setImageOptions($DW.$V("VDistance"),$DW.$V("HDistance"),$DW.$V("NodeWidth"),$DW.$V("NodeHeight"),b,t);
		$D.close();
	}
	diag.show();
}

function addOrgan(){
	var diag = new Dialog("Organ");
	diag.URL = "Workflow/OrganDialog.jsp";
	diag.Width = 510;
	diag.Height = 300;
	diag.Title = "关联部门";
	diag.onLoad = function(){
		var arr = fe.getData("Organ").split(",");
		for(var i=0;i<arr.length;i+=2){
			$DW.DataGrid.select("dg1",arr[i]);
		}
	}
	diag.OKEvent = function(){		
		var dt = $DW.DataGrid.getSelectedData("dg1");
		var arr = [];
		var html = [];
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		for(var i=0;i<dt.getRowCount();i++){
			html.push("<tr><td>"+dt.Rows[i].get("Name")+"</td></tr>");
			arr.push([dt.Rows[i].get("BranchInnerCode"),dt.Rows[i].get("Name")]);
		}
		html.push("</table>");
		if(dt.getRowCount()==0){
			$("tdOrgan").innerHTML = "没有";
		}else{
			$("tdOrgan").innerHTML = html.join('');
		}
		fe.setData("Organ",arr);
		$D.close();
	}
	diag.show();
}
function addRole(){
	var diag = new Dialog("Role");
	diag.URL = "Workflow/RoleDialog.jsp";
	diag.Width = 510;
	diag.Height = 300;
	diag.Title = "关联角色";
	diag.onLoad = function(){
		var arr = fe.getData("Role").split(",");
		for(var i=0;i<arr.length;i+=2){
			$DW.DataGrid.select("dg1",arr[i]);
		}
	}
	diag.OKEvent = function(){		
		var dt = $DW.DataGrid.getSelectedData("dg1");
		var arr = [];
		var html = [];
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		for(var i=0;i<dt.getRowCount();i++){
			html.push("<tr><td>"+dt.Rows[i].get("RoleCode")+"("+dt.Rows[i].get("RoleName")+")"+"</td></tr>");
			arr.push([dt.Rows[i].get("RoleCode"),dt.Rows[i].get("RoleName")]);
		}
		html.push("</table>");
		if(dt.getRowCount()==0){
			$("tdRole").innerHTML = "没有";
		}else{
			$("tdRole").innerHTML = html.join('');
		}
		fe.setData("Role",arr);
		$D.close();
	}
	diag.show();
}

function addUser(){
	var diag = new Dialog("User");
	var uArr = fe.getData("User").split(",");
	var userArr = [];
	var nameArr = [];
	if(uArr){
		for(var i=0;i<uArr.length;i++){
			if(i%2!=0){
				nameArr.push(uArr[i]);
			}else{
				userArr.push(uArr[i]);
			}
		}
	}
	diag.URL = "Platform/UserSelectDialog.jsp?Type=checkbox&SelectedUser="+userArr.join(',') + "|" + nameArr.join(',');
	diag.Width = 510;
	diag.Height = 330;
	diag.Title = "关联用户";
	
	diag.OKEvent = function(){		
		var arr = [];
		var html = [];
		html.push("<table width='90%' border='1' cellpadding='0' cellspacing='0' rules='rows' bordercolor='#FFFFFF' class='thinTable'>");
		
		if(!$DW.getSelectUser()[0]){
			$("tdUser").innerHTML = "没有";
		}else{
			userArr = $DW.getSelectUser()[0].split(",");
			nameArr = $DW.getSelectUser()[1].split(",");
			
			for(var i=0;i<userArr.length;i++){
				html.push("<tr><td>"+userArr[i]+"("+nameArr[i]+")"+"</td></tr>");
				arr.push([userArr[i],nameArr[i]]);			
			}
			html.push("</table>");
			if(userArr.length==0){
				$("tdUser").innerHTML = "没有";
			}else{
				$("tdUser").innerHTML = html.join('');
			}
		}
		fe.setData("User",arr);
		$D.close();
	}
	diag.show();
}

function save(){
	if(Verify.hasError()){
		return;
	}
	var msg = fe.check();
	if(msg){
		Dialog.confirm(msg+"<br><br>确认要保存？",function(){
			executeSave();
		});
	}else{
		executeSave();
	}
}

function executeSave(){
	var dc = Form.getData();
	dc.add("XML",htmlEncode(fe.getXML()));
	Server.sendRequest("comskyg.workflow.WorkflowPage.save",dc,function(response){
		Dialog.alert(response.Message);
		$S("ID",response.get("ID"));
		opener.$("dg1").loadData();
	});
}

</script>
</head>
<body>
  <form id="f1">
  <table width="98%" border="0" cellpadding="0" cellspacing="0" class="blockTable" style="margin:6px;">
    <tr>
      <td class="blockTd"><div style="margin:6px;">
          <!--<sky:button onClick="addStart()"><img src="../Icons/icon404a8.gif"/>开始</sky:button>-->
          <sky:button onClick="addNode()"><img src="../Icons/icon042a2.gif"/>新建步骤</sky:button>
          <sky:button onClick="addActionNode()"><img src="../Icons/icon042a2.gif"/>新建动作</sky:button>
          <sky:button onClick="addCondition()"><img src="../Icons/icon400a9.gif"/>新建转移条件</sky:button>
          <sky:button onClick="deleteSelected()"><img src="../Icons/icon042a3.gif" width="20" height="20"/>删除选中</sky:button>
          <!--<sky:button onClick="addEnd()"><img src="../Icons/icon404a7.gif"/>结束</sky:button>-->
          <sky:button onClick="autoLayout()"><img src="../Icons/icon400a19.gif"/>自动排列</sky:button>
          <sky:button onClick="check()"><img src="../Icons/icon042a9.gif" width="20" height="20"/>检查</sky:button>
          <sky:button onClick="imageOptions()"><img src="../Icons/icon023a7.gif" width="20" height="20"/>图形选项</sky:button>
          <sky:button onClick="save()"><img src="../Icons/icon042a16.gif"/>保存</sky:button>
        </div>
        <table border="0" cellspacing="5" class="cellspacing" style="clear:both;">
          <tr valign="top">
            <td><div id="FlashDiv" style="width:742px"></div></td>
            <td width="240" ><div class="attribPanel">
                <h5>流程属性</h5>
                <table width="240" border="0" cellpadding="4" cellspacing="0" id="PropTable" cellspace="4">
                  <tr>
                    <td width="60" align="right" valign="top">流程名称:</td>
                    <td><input name="Name" type="text" id="Name" value="${Name}" style="width:140px" verify="流程名称|NotNull">
                      <input name="ID" type="hidden" id="ID" value="${ID}"></td>
                  </tr>
                  <tr>
                    <td align="right" valign="top">流程选项:</td>
                    <td>
					<input type="checkbox" id="NotNeedApply" value="1" onclick="fe.setFlowData('NotNeedApply',this.checked?1:0)">不需申请直接处理<br>
					<input type="checkbox" id="NotifyNextStep" value="1" onclick="fe.setFlowData('NotifyNextStep',this.checked?1:0)">通知下一步处理人
					</td>
                  </tr>
                  <tr>
                    <td align="right" valign="top">备注:</td>
                    <td><textarea id="Memo" style="width:140px;height:50px" >${Memo}</textarea>                    </td>
                  </tr>
                </table>
              </div>
              <div class="attribPanel" id="ConditionProp">
                <h5>转移条件属性</h5>
                <table width="240" border="0" cellpadding="4" cellspacing="0" id="PropTable" cellspace="4">
                  <tr>
                    <td width="60" align="right" valign="top">条件名称:</td>
                    <td><input name="ConditionName" type="text" id="ConditionName" style="width:150px" 
							onKeyUp="setSelectedElementText(this)"></td>
                  </tr>
                  <tr>
                    <td align="right" valign="top">条件脚本:</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="2" align="center" valign="top"><textarea id="ConditionScript" style="width:210px" 
						onblur="fe.setData('Script',this.value)"></textarea></td>
                  </tr>
                  <tr>
                    <td align="right" valign="top">条件方法:</td>
                    <td><input  style="width:150px" name="ConditionMethod" type="text" id="ConditionMethod"
						 onblur="setSelectedElementText(this)"></td>
                  </tr>
                </table>
              </div>
              <div class="attribPanel" id="NodeProp">
                <h5>节点属性</h5>
                <table width="240" border="0" cellpadding="4" cellspacing="0" id="PropTable" cellspace="4">
                  <tr>
                    <td width="60" align="right" valign="top">名称:</td>
                    <td><input  style="width:140px" name="NodeName" type="text" id="NodeName" 
						onKeyUp="setSelectedElementText(this)"></td>
                  </tr>
                  <tr id='trSignJointly'>
                    <td align="right" valign="top">&nbsp;</td>
                    <td align="left"><label>
                    <input name="checkbox" type="checkbox" id="SignJointly" onclick="fe.setData('SignJointly',this.checked?1:0)" value="1">
会签模式</label></td>
                  </tr>
                  <tr id='trAllowSelectLine'>
                    <td align="right" valign="top">&nbsp;</td>
                    <td align="left"><label>
                      <input type="checkbox" id="AllowSelectLine" value="1" onclick="fe.setData('AllowSelectLine',this.checked?1:0)">
                      允许选择分支</label></td>
                  </tr>
                  <tr id='trAllowSelectUser'>
                    <td align="right" valign="top">&nbsp;</td>
                    <td align="left"><label>
                      <input type="checkbox" id="AllowSelectUser" value="1" onclick="fe.setData('AllowSelectUser',this.checked?1:0)">
                      允许指定下一步操作人</label></td>
                  </tr>
                  <tr  id="trOrgan">
                    <td align="right" valign="top">
					<img src="../Icons/icon403a20.gif" width="18" height="18" onclick="addOrgan();">&nbsp;部门:</td>
                    <td align="left" id="tdOrgan">没有</td>
                  </tr>
                  <tr  id="trRole">
                    <td align="right" valign="top">
					<img src="../Icons/icon403a20.gif" width="18" height="18" onclick="addRole();">&nbsp;角色:</td>
                    <td align="left" id="tdRole">没有</td>
                  </tr>
                  <tr  id="trUser">
                    <td align="right" valign="top">
					<img src="../Icons/icon403a20.gif" width="18" height="18" onclick="addUser();">&nbsp;用户:</td>
                    <td align="left" id="tdUser">没有</td>
                  </tr>
                </table>
              </div>
              <div class="attribPanel" id="NodeAction">
                <h5>节点动作</h5>
                <table width="240" border="0" cellpadding="4" cellspacing="0" id="PropTable" cellspace="4">
                 <tr id="trPreScript1">
                    <td align="right" valign="top">前置脚本:</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr id="trPreScript2">
                    <td colspan="2" align="center" valign="top"><textarea id="PreScript" style="width:210px" 
						onblur="fe.setData('PreScript',this.value)"></textarea></td>
                  </tr>                  
                  <tr id="trPreAction">
                    <td width="60" align="right" valign="top">前置方法:</td>
                    <td><input  style="width:150px" name="PreAction" type="text" id="PreAction" 
						onblur="fe.setData('PreAction',this.value);return true;"></td>
                  </tr>
                  <tr id="trPostScript1">
                    <td align="right" valign="top">后置脚本:</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr id="trPostScript2">
                    <td colspan="2" align="center" valign="top"><textarea id="PostScript" style="width:210px" 
						onblur="fe.setData('PostScript',this.value)"></textarea></td>
                  </tr>                  
                  <tr id="trPostAction">
                    <td align="right" valign="top">后置方法:</td>
                    <td><input  style="width:150px" name="PostAction" type="text" id="PostAction" 
						onblur="fe.setData('PostAction',this.value)"></td>
                  </tr>
                </table>
              </div>
			  </td>
          </tr>
        </table></td>
    </tr>
  </table>
  </form>
</body>
</html>
</sky:init>