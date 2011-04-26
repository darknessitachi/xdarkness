
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "新建"+this.document.title;
	diag.URL = "Platform/User/UserAddDialog.jsp";
	diag.onLoad = function(){
		$DW.$NS("IsBranchAdmin","N");
		$DW.$NS("Status","N");
		$DW.$S("Password","");
		$DW.$("UserName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if ($DW.$V("ConfirmPassword") != $DW.$V("Password")){
	   Dialog.alert("两次输入密码不相同，请重新输入...");
	   return ;
	}
	Server.sendRequest("com.xdarkness.platform.page.UserListPage.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});	
	});
}

function save(){
	DataGrid.save("dg1","com.xdarkness.platform.page.UserListPage.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的用户！");
		return;
	}
	Dialog.confirm("您确认要删除这些用户吗？</br><b style='color:#F00'>"+arr.join(',</br>')+"</b>",function(){
		var dc = new DataCollection();
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.xdarkness.platform.page.UserListPage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function stopUser(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要停用的用户！");
		return;
	}
	Dialog.confirm("您确认要停用这些用户吗？</br><b style='color:#F00'>"+arr.join(',</br>')+"</b>",function(){
		var dc = new DataCollection();
		dc.add("UserNames",arr.join());
		Server.sendRequest("com.xdarkness.platform.page.UserListPage.stopUser",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function setPriv(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的用户！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "设置用户"+arr[0]+"权限";
	diag.URL = "Platform/User/UserTab.jsp?UserName="+arr[0];
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭"; 
}

function doSearch(){
	var name = "";
	if ($V("SearchUserName") != "请输入用户名或真实姓名") {
		name = $V("SearchUserName").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchUserName",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchUserName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchUserName") == "请输入用户名或真实姓名") {
		$S("SearchUserName","");
	}
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的用户！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 380;
	diag.Title = "编辑用户"+arr[0];
	diag.URL = "Platform/User/UserEditDialog.jsp?UserName="+arr[0];
	diag.onLoad = function(){
		$DW.$("RealName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
    if ($DW.$V("NewConfirmPassword") != $DW.$V("NewPassword")){
	   Dialog.alert("两次输入密码不相同，请重新输入",function(){
	       $DW.$S("NewPassword","");
		   $DW.$S("NewConfirmPassword","");
		   return;
	   });
	   return;
	}
	Server.sendRequest("com.xdarkness.platform.page.UserListPage.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		})
	});
}

