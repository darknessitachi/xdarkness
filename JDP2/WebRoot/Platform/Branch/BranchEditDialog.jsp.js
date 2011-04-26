
var usertype = "";
function selectUser(type) {
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "选择用户";
	var selecteduser = $V(type) + "|" + $V(type+"Name");
	usertype = type;
	diag.URL = "Platform/UserSelectDialog.jsp?Type=radio&SelectedUser="+selecteduser;
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect() {
	var user = $DW.getSelectUser();
	$S(usertype, user[0]);
	$S(usertype+"Name", user[1]);
	$D.close();
}

function clearUser(type) {
	$S(type, "");
	$S(type+"Name", "");
}

