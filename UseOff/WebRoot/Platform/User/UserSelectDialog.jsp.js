
function changeType(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","ShowType",$V("ShowType"));
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.loadData("dg1");
}

function checkAll(){
	var tagList = $T("input");
	if ($("checkAllBox").checked) {
		for (var i=0; tagList!=null && i<tagList.length; i++) {
			if (tagList[i].type=="checkbox" && tagList[i].id.startWith("UserList_")) {
				$(tagList[i]).checked = true;
			}
		}
	} else {
		for (var i=0; tagList!=null && i<tagList.length; i++) {
			if (tagList[i].type=="checkbox" && tagList[i].id.startWith("UserList_")) {
				$(tagList[i]).checked = false;
			}
		}
	}
}

function checkUser(ID){
	var list = $N("UserList_" + ID);
	if (!list || list==null) {
		return;
	}
	
	if ($("check"+ID).checked==true) {
		for (var i=0; i<list.length; i++) {
			$(list[i]).checked = true;
		}
	} else {
		for (var i=0; i<list.length; i++) {
			$(list[i]).checked = false;
		}
	}
}

function getSelectUser(){
	var tagList = $T("input");
	var userNames;
	var realNames;
	var user = new Array();
	var inputType = "<%=inputType%>";
	if (inputType == "radio") {
		var name = $NV("UserList");
		var name=name+"";//object 转换成string
		userNames = name.substring(0, name.indexOf(","));
		realNames = name.substring(name.indexOf(",") + 1);
	} else {
		var checkuser = new Array();
		var checknum = 0;
		for (var i=0; tagList!=null && i<tagList.length; i++) {
			if (tagList[i].type=="checkbox" && tagList[i].id.startWith("UserList_") && $(tagList[i]).checked) {
				if (!userNames) {
					userNames = $V(tagList[i]).substring(0, $V(tagList[i]).indexOf(","));
					realNames = $V(tagList[i]).substring($V(tagList[i]).indexOf(",") + 1);
					checkuser[checknum] = $V(tagList[i]).substring(0, $V(tagList[i]).indexOf(","));
					checknum++;
				} else {
					var ischeck = false;
					for (var m=0; m<checkuser.length; m++) {
						if ($V(tagList[i]).substring(0, $V(tagList[i]).indexOf(",")) == checkuser[m]) {
							ischeck = true;
							break;
						}
					}
					
					if (!ischeck) {
						userNames += "," + $V(tagList[i]).substring(0, $V(tagList[i]).indexOf(","));
						realNames += "," + $V(tagList[i]).substring($V(tagList[i]).indexOf(",") + 1);
						checkuser[checknum] = $V(tagList[i]).substring(0, $V(tagList[i]).indexOf(","));
						checknum++
					}
				}
			}
		}
	}
	user[0] = userNames;
	user[1] = realNames;
	return user;
}


