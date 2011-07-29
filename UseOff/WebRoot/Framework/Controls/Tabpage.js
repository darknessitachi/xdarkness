

var Tab = {
	BasePath: "/"
};

Tab.getShowTabNumber = function() {
	var _clientWidth = $TW.document.body.clientWidth;
	
	if(_clientWidth<=1024) {
		return 7;
	}
	if(_clientWidth<=1280) {
		return 9;
	}
	return 11;
}



Tab.activeTab = function(id,refresh) {
	Tab.onChildTabClick($(id), refresh);
	Tab.getShowTabs($(id).parentElement.id.replace('tabdiv',''));
}

Tab.closeAll = function(id) {
	
	Dialog.confirm("你确认需要关闭全部选项卡吗？", function(){
		var tabs = $(id + 'tabdiv').childNodes;
		while(tabs.length>0) {
			Tab.closeChild(tabs[0].id);
		}	
	});
}

Tab.getShowTabs = function(id) {
	var tabs = Tab[id].Tabs;
	var lastShowIndex = tabs.LastShowTab.index;
	var activeIndex = Tab.CurrentActiveTab.index;
	var indexAway = activeIndex -lastShowIndex;
	
	var showNumber = Tab.getShowTabNumber();
	//if(!(indexAway>-5 && indexAway<5)) {/* 当前激活tab不在显示区域 */ 
		if(activeIndex<showNumber) {/* 当前激活tab属于前五个tab之一 */
			for(var i=0;i<tabs.length;i++){/* 遍历所有tab */
				if(i<showNumber) {/* 显示前五个tab */
					$(tabs[i]).style.display = "";
					tabs.LastShowTab = {
						id: tabs[i],
						index: i 
					};
				} else {/* 其他隐藏 */
					$(tabs[i]).style.display = "none";
				}
			}
		} else if(activeIndex-tabs.length>-showNumber) {/* 当前激活tab属于最后五个tab之一 */
			for(var i=0;i<tabs.length;i++){/* 遍历所有tab */
				if((tabs.length-i)<=showNumber) {/* 显示后五个tab */
					$(tabs[i]).style.display = "";
					tabs.LastShowTab = {
						id: tabs[i],
						index: i 
					};
				} else {/* 其他隐藏 */
					$(tabs[i]).style.display = "none";
				}
			}
		} else {/* 当前激活tab属于中间的某个tab */
			for(var i=0;i<tabs.length;i++){/* 遍历所有tab */
				if((activeIndex-i)<showNumber/2 && (activeIndex-i)>-showNumber/2) {/* 显示该tab左右相邻的五个tab */
					$(tabs[i]).style.display = "";
					tabs.LastShowTab = {
						id: tabs[i],
						index: i 
					};
				} else {/* 其他隐藏 */
					$(tabs[i]).style.display = "none";
				}
			}
		}
	//}
}

Tab.showLeft = function(id) {
	
	var showNumber = Tab.getShowTabNumber();
	if(Tab[id].Tabs.LastShowTab.index-showNumber>=0) {
		var _id = Tab[id].Tabs[Tab[id].Tabs.LastShowTab.index-showNumber];
		var t = $(_id);
		t.style.display = "";
		
     }
     
     if(Tab[id].Tabs.length > Tab[id].Tabs.LastShowTab.index) {
     	if(Tab[id].Tabs.LastShowTab.index<showNumber) {/* 不超过showNumber个，则不隐藏，默认最多显示showNumber个 */
     		return;
     	}
     	
     	var t = $(Tab[id].Tabs[Tab[id].Tabs.LastShowTab.index]);
		t.style.display = "none";
		var _lastShowIndex = Tab[id].Tabs.LastShowTab.index-1;
		Tab[id].Tabs.LastShowTab = {
			id: Tab[id].Tabs[_lastShowIndex],
			index: _lastShowIndex
		};
     }
/*
	var index = Tab.CurrentActiveTab.index;
	if(index>0) {
		index--;
		Tab.activeTab(Tab[id].Tabs[index]);
	}*/
}

Tab.showRight = function(id) {
     
     if(Tab[id].Tabs.length-1 > Tab[id].Tabs.LastShowTab.index) {
     	var t = $(Tab[id].Tabs[Tab[id].Tabs.LastShowTab.index+1]);
		t.style.display = "";
		
		var _lastShowIndex = Tab[id].Tabs.LastShowTab.index+1;
		Tab[id].Tabs.LastShowTab = {
			id: Tab[id].Tabs[_lastShowIndex],
			index: _lastShowIndex
		};
     }
     
     var showNumber = Tab.getShowTabNumber();
     
     /* 将最后一个显示tab之showNumber的第五个tab隐藏 */
     if(Tab[id].Tabs.LastShowTab.index-showNumber>=0) {
		var t = $(Tab[id].Tabs[Tab[id].Tabs.LastShowTab.index-showNumber]);
		t.style.display = "none";
     }
     
     /*
	var index = Tab.CurrentActiveTab.index;
	if(index < Tab[id].Tabs.length-1) {
		index++;
		Tab.activeTab(Tab[id].Tabs[index]);
	}*/	
}

Tab.resizeFrames = function() {
	
	if(!$("menuTreeTd")){
		return;
	}
	
	var frames = $T("iframe", document.body);
	
	for(var i=0;i<frames.length;i++){
	
		var frame = frames[i];
	
		if(frame.style.display == "none" || frame.parentElement.offsetWidth<=0) {
			continue;
		}
		
		var showLeftMenu = $("menuTreeTd").style.display=="none";
		if(!frame._offsetWidth) {
			frame._offsetWidth = frame.offsetWidth;
			frame._offsetHeight = frame.offsetHeight;
		}
			
		if(showLeftMenu) {
			frame.style.width = frame.parentElement.offsetWidth;
			frame.style.height = frame.parentElement.offsetHeight;
		} else {
			frame.style.width = frame._offsetWidth;
			frame.style.height = frame._offsetHeight;
			
			frame._offsetWidth = null;
			frame._offsetHeight = null;
		}
	}		
}

Tab.addTab = function(id, _config) {

	if(Tab[id].Tabs.length >= Tab[id].max) {
		Dialog.alert("选项卡已达到最大数，请关闭不再使用的选项卡后重试！");
		return;
	}
	
	if(Tab[id].Tabs.contains(_config.id)){
		Tab.activeTab(_config.id);
		return;
	} else {
		var _length = Tab[id].Tabs.length;
		Tab[id].Tabs.LastShowTab = {
			id: _config.id,
			index: _length
		}
		Tab[id].Tabs.push(_config.id);
	}
	
	var onClick = _config.onClick || "";
	if (onClick != "" && !onClick.trim().endsWith(";"))
    	onClick = onClick.trim() + ";";

    var type = (_config.selected || false) ? "Current" : (_config.disabled||false) ? "Disabled" : "";
    var vStr = (_config.visible||true) ? "" : "style='display:none'";
    var paddingString = (_config.showClose || false) ? "style='padding-right:0px'" : "" ;
    
    var closeImgString = (_config.showClose || false) ? "<img src='"+Tab.BasePath+"Framework/resources/icons/close.gif' width='15' onclick='Tab.closeChild(this.parentNode.parentNode.parentNode.id);' />" : "";
	var tabFormatString = "<div title='"+_config.tip+"' style='-moz-user-select:none;' onselectstart='return false' id='{id}' src='{src}' targetURL='{src}' {vStr} class='divchildtab{type}' onclick=\"{onClick}Tab.onChildTabClick(this);{afterClick}\" onMouseOver='Tab.onChildTabMouseOver(this)' onMouseOut='Tab.onChildTabMouseOut(this)'><img src='{icon}' /><b "+ paddingString + "><span style='float:left'>{title}</span><span style='float:left'>"+closeImgString+"</span></b></div>";
	var newTabString = new StringFormat(tabFormatString, {
		id: _config.id || "",
		src: _config.src || "",
		vStr: vStr,
		type: type,
		onClick: onClick,
		afterClick: _config.afterClick || "",
		icon: _config.icon || Tab.BasePath + 'Framework/resources/icons//icon040a1.gif',
		title: _config.title || "tab标题"
	}).toString();
	
	var tabDiv = $(id + "tabdiv");
	tabDiv.innerHTML = tabDiv.innerHTML + newTabString;
	
	var newTabFrame = document.createElement('div');
	newTabFrame.style["padding-left"] = "10px";
	newTabFrame.id = "_ChildTabFrame_" + _config.id;
    
    if(_config.renderTo)
    	newTabFrame.appendChild($(_config.renderTo));
    	
     var tabFrame = $(id + "tabframe");
     tabFrame.appendChild(newTabFrame);
     
     Tab.activeTab(_config.id);
     
     Tab.showRight(id);
}

Tab.closeChild = function(id) {
	
	var tabDiv = $(id);
	var tabId = tabDiv.parentNode.id.replace('tabdiv',"");
	
	var curTabFrameId = tabDiv.parentNode.id.replace('tabdiv','tabframe'); 
	tabDiv.parentNode.removeChild(tabDiv);
	
	$(curTabFrameId).removeChild($('_ChildTabFrame_' + id));
	
	var _index = Tab[tabId].Tabs.indexOf(id);
	if(_index != -1) {
		var tabs = Tab[tabId].Tabs;
		tabs.remove(id);
		tabs.LastShowTab.index--;
		tabs.LastShowTab.id = tabs[tabs.LastShowTab.index];
		
		var flag = true;
		while(flag) {
			if(tabs.LastShowTab.index > -1 && tabs.LastShowTab.index<tabs.length-1) {/* 关闭一个tab后，向后迭代，直到找到一个隐藏的tab后，让其显示 */
				if($(tabs.LastShowTab.id).style.display == "none") {
					$(tabs.LastShowTab.id).style.display = "";
					flag = false;
				} else {
					tabs.LastShowTab.index++;
					tabs.LastShowTab.id = tabs[tabs.LastShowTab.index];
				}
			} else {/* 向后未找到一个隐藏的tab，采用向前迭代方式继续搜索 
				if($(tabs.LastShowTab.id).style.display == "none") {
					$(tabs.LastShowTab.id).style.display = "";
					flag = false;
				} else {
					tabs.LastShowTab.index--;
					tabs.LastShowTab.id = tabs[tabs.LastShowTab.index];
				}*/
				flag = false;
			}
		}
		
		
	}
	if(_index > 0) {
		Tab.activeTab(Tab[tabId].Tabs[--_index]);
	} else {
		if(Tab[tabId].Tabs.length > 0) {
			Tab.activeTab(Tab[tabId].Tabs[0]);	
		}
	}
	
	if(Tab[tabId].Tabs.length == 0) {
		if($("welcomeIframe"))
			$("welcomeIframe").style.display = "";
	}
}

Tab.onTabMouseOver = function(ele){
	if(ele.className=="divtab"){
		ele.className="divtabHover";
	}
}

Tab.onTabMouseOut = function(ele){
	if(ele.className=="divtabHover"){
		ele.className="divtab";
	}
}

Tab.onTabClick = function(ele){
	var arr = $T("div",ele.parentElement);
	var len = arr.length;
	for(var i=0;i<len;i++){
		var c = arr[i];
		var cn = c.className;
		if(cn=="divtabCurrent"){
			c.className = "divtab";
			c.style.zIndex=""+(len-i);
		}
	}
	ele.className="divtabCurrent";
	ele.style.zIndex="33";
	
}

Tab.initTab = function(ele){
	var arr = $T("div",ele.parentElement);
	var arrleng=arr.length;
	for(var i=0;i<arrleng;i++){
		var cn = arr[i].className;
		if(cn=="divtab" || cn=="divtabDisabled"){
			arr[i].style.zIndex=""+(arrleng-i)+"";
		}else if(cn=="divtabCurrent"){
			arr[i].style.zIndex="33";
		}
	}
}

Tab.parentpadding = function(ele){
	if(ele.parentElement.style.paddingLeft!="30px"){
		ele.parentElement.style.paddingLeft="30px";
	}
}

Tab.setDivtabWidth = function(tab){/* 用于调整标签按钮宽度 */
	tab = $(tab);
	var noTitWidth=50;/* 标签宽度-文字宽度=noTitWidth */
	var minSpanWidth=6;/* 最小文本显示宽度 */
	if(Application.isHMenu == true){
		var allTabWidth=0;/* 所有横向标签相加宽度 */
		var tabArr = tab.$T("div");
		var tabArrLen=tabArr.length;
		var cw=document.compatMode == "BackCompat"?document.body.clientWidth:document.documentElement.clientWidth;
		for(var i=0; i<tabArrLen;i++){
				allTabWidth+=tabArr[i].clientWidth;
		}
		/* alert("标签个数"+tabArrLen+" 标签总宽"+allTabWidth+" 文档可视宽度"+document.body.clientWidth) */
		if (allTabWidth+50 > cw){
			if(cw-120-tabArrLen*noTitWidth>0){
				minSpanWidth=Math.ceil((cw-120-tabArrLen*noTitWidth)/tabArrLen);
			}
			for(var i=0; i<tabArrLen;i++){
				$T("span",tabArr[i])[0].style.cssText="width:"+minSpanWidth+"px;";
			}
		}else{
			for(var i=0; i<tabArrLen;i++){
				$T("span",tabArr[i])[0].style.cssText="";
			}
		}
	}
}

Tab.onChildTabMouseOver = function(ele){
	if(ele.className=="divchildtab")	ele.className="divchildtabHover";
}

Tab.onChildTabMouseOut = function(ele){
	if(ele.className=="divchildtabHover")	ele.className="divchildtab";
}

Tab.onChildTabClick = function(ele,refresh){
	
	ele = $(ele);
	
	var tabId = ele.parentNode.id.replace('tabdiv',"");
	Tab.CurrentActiveTab = {
		id: ele.id,
		index: Tab[tabId].Tabs.indexOf(ele.id)
	};
	
	var arr = $T("div",ele.parentElement);
	var lastHeight;
	for(var i=0;i<arr.length;i++){
		var t = $(arr[i]);
		var cn = t.className;
		var f = $("_ChildTabFrame_"+t.$A("id"));
		if(cn=="divchildtabCurrent"){
			t.className = "divchildtab"
			lastHeight = f.getDimensions().height;
		}		
		f.style.height = 0;
		f.height = 0;
		f.style.display = "none";
	}
	ele.className="divchildtabCurrent";
	var f = $("_ChildTabFrame_"+ele.$A("id"));
	if(f.src=="javascript:void(0);"){
		f.src = ele.$A("targetURL");
	}
	
	if(refresh) {
		try {
			var _iframe = f.$T('iframe')[0];
			_iframe.src = _iframe.src; 
		} catch(e) {
			// ignore
		}
	}
	
	f.style.width = "100%";
	lastHeight = lastHeight || 0;
	if(lastHeight<10){
		Tab.initFrameHeight(f);
	}else{
		f.style.height = lastHeight + "px";		
	}
	f.style.display = "";
	
	Tab.resizeFrames();
	
	// TODO 
}

Tab.initFrameHeight = function(id){/* 暂不处理，有调用处指定高度 */
	
	var f = $(id);
	var b = document.body;
	var ch  = document.compatMode == "BackCompat"?document.body.clientHeight:document.documentElement.clientHeight;
	var pos = f.getPosition();
	f.scrolling = "auto";
	f.height = ch-f.getPosition().y;
	var p = f.offsetParent;
	while (p.offsetParent!=document.body){
		p = p.offsetParent;
	}
	p = $(p);
	var d = p.getDimensions().height + p.getPosition().y - f.getDimensions().height - f.getPosition().y;
	f.style.height = Math.abs(f.height - d - (Browser.isIE?1:0))+"px";// IE8需要-1 
	
}

Tab.getChildTab = function(id){
	return $("_ChildTabFrame_"+id);
}

Tab.getCurrentTab = function(){
	var arr = $T("div");
	var arrleng = arr.length;
	for(var i=0;i<arrleng;i++){
		var cn = arr[i].className;
		if(cn=="divchildtabCurrent"){
			return $("_ChildTabFrame_"+arr[i].id);
		}
	}
}

Tab.disableTab = function(ele){
	ele = $(ele);
	ele.className="divchildtabDisabled";
	ele.onclick2 = ele.onclick;
	ele.onclick = null;
	ele.onmouseover=null;
	ele.onmouseout=null;
}

Tab.enableTab = function(ele){
	ele = $(ele);
	ele.className="divchildtab";
	if(ele.onclick2){
		ele.onclick = ele.onclick2;
		ele.onclick2 = null;
	}else{
		ele.onclick=function(){
			Tab.onChildTabClick(this);
		}
		ele.onmouseover=function(){
			Tab.onChildTabMouseOver(this);
		}
		ele.onmouseout=function(){
			Tab.onChildTabMouseOut(this);
		}
	}
}

Page.onReady(function(){
	var arr = $T("div");
	var arrleng = arr.length;
	for(var i=0;i<arrleng;i++){
		var cn = arr[i].className;
		if(cn=="divtab" || cn=="divtabDisabled"){
			arr[i].style.zIndex=""+(arrleng-i)+"";
		}else if(cn=="divtabCurrent"){
			arr[i].style.zIndex="33";
		}
	}
});
