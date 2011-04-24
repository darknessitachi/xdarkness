
function search(){
	DataGrid.setParam("dg1","Keyword",$V("keyword"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

var Priv = {};
var privTypes = [];

function getAllPriv() {
	var dc = new DataCollection();
	Server.sendRequest("com.xdarkness.platform.page.LoginPage.getAllPriv",dc,function(response){
		parserPriv(response);
	});
}
 
function parserPriv(response){
	Priv.isBranchAdmin = response.get("isBranchAdmin")=="Y"? true:false;
	if(!Priv.isBranchAdmin){
		privTypes = response.get("privTypes").split(',');
		for(var k=0;k<privTypes.length;k++){
			Priv[privTypes[k]+"DT"] = response.get(privTypes[k]+"DT");
		}
	}
}

Priv.setBtn = function(privType,id,code,button){
	if(Priv.getPriv(privType,id,code)){
		button.enable();
	}else{
		button.disable();
	}
}

Priv.getPriv = function(privType,id,code){
	if(Priv.isBranchAdmin){
		return true;
	}
	if(privType=="site"){
		var dt = Priv.siteDT;
		if(dt){
			for(var i=0;i < dt.getRowCount(); i++){
				if(dt.get(i,"id")==id){
					return dt.get(i,code)=='1';
				}
			}
		}
		return null;
	}else {
		var dt = Priv[privType+"DT"];
		if(dt){
			for(var i=0;i < dt.getRowCount(); i++){
				if(dt.get(i,"id")==id){
					return dt.get(i,code)=='1';
				}
			}
			if(id==""||id==0||id=="0"){
				var siteid = $V("_SiteSelector");
				for(var i=0;i < dt.getRowCount()&&i < 0; i++){
					if(dt.get(i,"id")==id){
						return dt.get(i,code)=='1';
					}
				}
			}
			return false;
		}
		return false;
	}
}

Page.onLoad(function(){
	//getNewMessageCount();
	//setInterval(getNewMessageCount, 1000*10);
});

//获取短消息
function getNewMessageCount(){
	var dc = new DataCollection();
	Server.sendRequest("com.xdarkness.cms.document.Message.getNewMessage",dc,function(response){
		var count = response.get("Count");
		$("MsgCount").innerHTML = count;
		if(response.get("PopFlag")=="1"){
			var mp = new MsgPop(response.get("Message"),null,450,null,30);
			mp.show();
		}
	});
}

//定位到短消息菜单
function getMessage(){
	var mainEle = $("_Menu_120");
	Application.onMainMenuClick(mainEle);
	var ele = $("_ChildMenuItem_132");
	Application.onChildMenuClick(ele);
}

//定位到系统管理菜单
function getSystemManage(){
	var mainEle = $("_Menu_37");
	Application.onMainMenuClick(mainEle);
	var ele = $("_ChildMenuItem_132");
	Application.onChildMenuClick(ele);
};
