
var tabMap = {
	Site:"UserTabSite.jsp?PrivType=site",
	Menu:"UserTabMenu.jsp?2=2",
	Article:"UserTabCatalog.jsp?4=4"
};

var TabID = "Site";//全局的
var OldSiteID = "";
var OldPrivType = "";

Page.onLoad(onUserTabClick);

function onUserTabClick(tabid){
	if(tabid){
		TabID = tabid;
	}
	var UserName = $V("UserName");
	Tab.onChildTabClick(TabID);
	var URL = Tab.getCurrentTab().src;
	var newURL = tabMap[TabID]+"&UserName="+UserName+"&OldSiteID="+OldSiteID+"&OldPrivType="+OldPrivType;
	if(URL.indexOf(newURL)<0){
		Tab.getCurrentTab().src = newURL;
	}
	return;
}



