Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("CodeValue",$V("CodeValue"));
		dr.set("CodeName",$V("CodeName"));
		dr.set("Memo",$V("Memo"));
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.xdarkness.platform.page.CodePage.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 350;
	diag.Height = 150;
	diag.Title = "新建代码";
	diag.URL = "Platform/Code/CodeDialog.jsp";
	diag.onLoad = function(){
		$DW.$("CodeType").value="${CodeType}";
		$DW.$("CodeType").disabled=true;
		$DW.$("CodeValue").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("ParentCode","${CodeType}");
	if($DW.Verify.hasError()){
		return;
	}
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
	if(dt.getRowCount()==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT",dt);
	Server.sendRequest("com.xdarkness.platform.page.CodePage.del",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData('dg1');
			}		
		});
	});
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	var order = $("dg1").DataSource.get(rowIndex-1,"CodeOrder");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"CodeOrder");
		dc.add("Type","Before");		
	}else{
		dc.add("Type","After");
		target = $("dg1").DataSource.get(1,"CodeOrder");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	dc.add("ParentCode",ds.get(0,"ParentCode"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.xdarkness.platform.page.CodePage.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}