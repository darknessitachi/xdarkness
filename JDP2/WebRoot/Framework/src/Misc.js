/* 定义Misc类 开始 */
var Misc = {};
Misc.setButtonText = function(ele,text){//为button设置文本
	$(ele).childNodes[1].innerHTML = text+"&nbsp;";
}

Misc.withinElement = function(event, ele) {//仅适用于Gecko,判断onmouseover,onmouseout是否是一次元素内部重复触发
	
	var parent = event.relatedTarget;
	while(parent&&parent!=ele&&parent!=document.body){
		try{
			parent = parent.parentNode;
		}catch(ex){
			alert("Misc.withinElement:"+ex.message);
			return false;
		}
	}
	return parent == ele;
}

Misc.copyToClipboard = function(text){
	if(text==null){
		return;
	}
	if (window.clipboardData){
		window.clipboardData.setData("Text", text);
	}else if (window.netscape){
		try{
			netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		}catch(ex){
			Dialog.alert("Firefox自动复制功能未启用！<br>请<a href='about:config' target='_blank'>点击此处</a> 将'signed.applets.codebase_principal_support'设置为'true'");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if(!clip){return;}
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if(!trans){return;}
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext=text;
		str.data=copytext;
		trans.setTransferData("text/unicode",str,copytext.length*2);
		var clipid=Components.interfaces.nsIClipboard;
		if(!clip){return;}
		clip.setData(trans,null,clipid.kGlobalClipboard);
	}else{
		alert("该浏览器不支持自动复制功能！");
		return;
	}
	Dialog.alert("己复制文字：<br><br><font color='red'>"+text+"</font><br><br>到剪贴板",null,400,200);
}

Misc.lockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(Browser.isGecko){
		ele.style.MozUserSelect = "none";
		ele.style.MozUserInput = "none";
	}else{
		document.selection.empty();
		ele.onselectstart = stopEvent;
	}
}

Misc.unlockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(Browser.isGecko){
		ele.style.MozUserSelect = "";
		ele.style.MozUserInput = "";
	}else{
		ele.onselectstart = null;
	}
}

Misc.lockScroll = function(win){
	//if(!win){
	//	win = window;
	//}
	//if(Browser.isIE){
	//	win.document.body.attachEvent("onmousewheel",win.stopEvent);
	//}else{
	//	win.addEventListener("DOMMouseScroll", win.stopEvent, false);
	//}
}

Misc.unlockScroll = function(win){
	/*
	if(!win){
		win = window;
	}
	if(!win.document) {
		win = window;
	}
	if(Browser.isIE){
		win.document.body.detachEvent("onmousewheel",win.stopEvent);
		win.document.body.detachEvent("onmousewheel",win.stopEvent);//Select.js中可能会连续attch两次,所以必须detach两次
	}else{
		win.removeEventListener("DOMMouseScroll", win.stopEvent, false);
	}*/
}
/* 定义Misc类 结束 */