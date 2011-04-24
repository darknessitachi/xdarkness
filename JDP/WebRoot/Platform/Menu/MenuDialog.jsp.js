
function selectIcon(){
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "选择图标";
	diag.URL = "Platform/Icon.jsp";
	diag.OKEvent = afterSelectIcon;
	diag.show();
}

function afterSelectIcon(){
	if(!$DW.Icon){
		Dialog.alert("请选择菜单要使用的图标");
		return;
	}
	$("Icon").src = $DW.Icon;
	$D.close();
}

function onParentChange(){
	if($V("ParentID")==0){
		$("URL").value = "";
		$E.disable("URL");
	}else{
		$E.enable("URL");
	}
}
