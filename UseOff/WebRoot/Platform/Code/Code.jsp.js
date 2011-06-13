Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeType",$V("CodeType"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.xdarkness.platform.page.CodePage.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "新建代码类别";
	diag.URL = "Platform/Code/CodeDialog.jsp";
	diag.onLoad = function(){
		$DW.$("CodeType").focus();
		$DW.$("tr_CodeValue").style.display="none";
		$DW.$("CodeValue").setAttribute("verify","");
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("ParentCode","System");
	dc.add("CodeValue","System");
	Server.sendRequest("com.xdarkness.platform.page.CodePage.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
					$D.close();
					DataGrid.loadData('dg1');
			}	
		});
	});
}

function del(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.Values.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT",dt);
	Dialog.confirm("您确认要删除吗？",function(){
		Server.sendRequest("com.xdarkness.platform.page.CodePage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchCodeType'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchCodeType") == "请输入代码类别或名称") {
		$S("SearchCodeType","");
	}
}

function doSearch(){
	var searchName = "";
	if($V("SearchCodeType") != "请输入代码类别或名称"){
		searchName = $V("SearchCodeType").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchCodeType",searchName);
	DataGrid.loadData("dg1");
}

function codeList(codeType,codeName){
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 380;
	diag.Title = "代码";
	diag.URL = "Platform/Code/CodeList.jsp?CodeType="+codeType+"&CodeName="+codeName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}