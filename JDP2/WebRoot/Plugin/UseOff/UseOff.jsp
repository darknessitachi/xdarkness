<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="jaf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>资金管理</title>
<link href="<%=request.getContextPath() %>/Include/Default.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "添加资金明细";
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加资金明细";
	diag.Message = "资金的来源、使用去处的详细信息";
	diag.URL = "Plugin/UseOff/UseOffDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = save;
	diag.show();
}

function save(){debugger;
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.plugin.useoff.UseOffPage.save",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.setParam("dg1",Constant.PageIndex,0);
		    DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
		}
	});
}
</script>
</head>
<jaf:init method="com.xdarkness.plugin.useoff.UseOffPage.init">
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../../Icons/icon020a1.gif" width="20" height="20" /> 资金明细列表</td>
            </tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<jaf:button onClick="add()">新建</jaf:button>
               		<jaf:button onClick="edit()">修改</jaf:button>
               		<jaf:button onClick="del()">删除</jaf:button>
		 		</td>
           	</tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <jaf:datagrid id="dg1" method="com.xdarkness.plugin.useoff.UseOffPage.dg1DataBind" size="15">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr xtype="head" class="dataTableHead">
                    <td  width="3%" align="center" xtype="RowNo">&nbsp;</td>
                    <td width="3%" align="center" xtype="selector" field="id">&nbsp;</td>
                    <td width="12%"><b>金额</b></td>
                    <td width="20%"><b>类别</b></td>
                    <td width="8%"><b>方式</b></td>
                    <td width="16%"><b>描述</b></td>
                    <td width="16%"><b>日期</b></td>
                  </tr>
                  <tr>
                  	<td></td>
                  </tr>
                  <tr onDblClick="edit();">
                    <td align="center">&nbsp;</td>
                    <td align="center">&nbsp;</td>
                    <td>${money}</td>
                    <td>${useFor}</td>
                    <td>${moneyType}</td>
                    <td>${description}</td>
                    <td>${createTime}</td>
                  </tr>
                  <tr xtype="pagebar">
                    <td colspan="7">${PageBar}</td>
                  </tr>
                </table>
              </jaf:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</jaf:init>
</html>
