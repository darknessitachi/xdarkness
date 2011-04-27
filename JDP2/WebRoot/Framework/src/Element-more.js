/* 元素方法扩展 开始 */
/**
 * @class ExtElement DOM元素的扩展，提供一些常用辅助函数
 */
var $E = {};
Sky.ExtElement = $E;

/**
 * @method $A 获取元素的属性
 * @param {String} attr 属性名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 属性的值
 */
$E.$A = function(attr, ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getAttribute ? ele.getAttribute(attr) : null;
}

$E.on = function(eventName, func) {
	addEvent(this, eventName, func);
	return this;
}

/**
 * @method clear 清除value
 * @memberOf {TypeName} 
 */
$E.clearValue = function(){
	this.value = "";
}


/**
 * @method $T 获取元素下指定的标签对象
 * @param {String} tagName 标签的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Array} 对象数组
 */
$E.find = $E.$T = function(tagName, ele) {
	ele = ele || this;
	ele = window.$(ele);
	return window.$T(tagName, ele);
}

/**
 * @method visible 查看元素是否可见
 * @param {Object} ele DOM元素对象或其id
 * @return {Boolean} 元素是否可见
 */
$E.visible = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if (ele.style.display == "none") {
		return false;
	}
	return true;
}

/**
 * @method toggle 展开或收缩元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.toggle = function(ele) {
	ele = ele || this;
	ele = $(ele);
  $E[$E.visible(ele) ? 'hide' : 'show'](ele);
}

/**
 * @method toString 查看元素的所有属性
 * @param {Boolean} flag 是否显示函数内容
 * @param {Number} index 从第几个下标开始
 * @param {Object} ele DOM元素
 * @return {String} 包含元素所有属性的字符串
 */
$E.toString = function(flag,index,ele) {//flag表示是否显示函数内容
	ele = ele || this;
	var arr = [];
	var i = 0;
	for(var prop in ele){
		if(!index||i>=index){
			var v = null;
			try{v = ele[prop];}catch(ex){}//gecko下可能会报错
			if(!flag){
				if(typeof(v)=="function"){
					v = "function()";
				}else if((""+v).length>100){
					v = (""+v).substring(0,100)+"...";
				}
			}
			arr.push(prop+":"+v);
		}
		i++;
	}
  return arr.join("\n");
}

/**
 * @method focusEx 函数获得焦点
 * @param {Object} ele DOM元素对象或其id
 */
$E.focusEx = function(ele) {
	ele = ele || this;
	ele = $(ele);
	try{
  	ele.focus();
	}catch(ex){}
}

/**
 * @method getForm 获取该元素所在的form对象
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 表单对象
 */
$E.getForm = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(Browser.isIE){
		ele = ele.parentElement;
	}else if(Browser.isGecko){
		ele = ele.parentNode;
	}
	if(!ele){
		return null;
	}
	if(ele.tagName.toLowerCase()=="form"){
		return ele;
	}else{
		return $E.GetForm(ele);
	}
}



/**
 * @method hide 隐藏元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.hide = function(ele) {
	if(!ele){
		ele = this;
	}
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("xtype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = 'none';
		}
	}
  ele.style.display = 'none';
}

/**
 * @method show 显示元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.show = function(ele) {
	if(!ele){
		ele = this;
	}
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("xtype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = '';
		}
	}
  ele.style.display = '';
}

/**
 * @method disable 禁用元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.disable = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
		for (var i = 0; i < elements.length; i++) {
		  var element = elements[i];
		  ele.blur();
		  if(ele.hasClassName("zPushBtn")){
			  ele.addClassName("zPushBtnDisabled");
			  if(ele.onclick){
				 ele.onclickbak = ele.onclick;
			  }
			  ele.onclick=null;
		  }else{
			  ele.disabled = 'true';
		  }
		}
	}else{
		if(ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"){
			Selector.setDisabled(ele,true);
		}else if(ele.hasClassName("zPushBtn")){
			ele.addClassName("zPushBtnDisabled");
			if(ele.onclick){
				ele.onclickbak = ele.onclick;
			}
			ele.onclick=null;
		}else{
			ele.addClassName("disabled");
	    	ele.disabled = 'true';
		}
	}
}

/**
 * @method enable 启用元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.enable = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
	    for (var i = 0; i < elements.length; i++) {
	      var element = elements[i];
			if(ele.hasClassName("zPushBtnDisabled")){
				ele.className="zPushBtn";
				if(ele.onclickbak){
					ele.onclick = ele.onclickbak;
				}
			}else{
		    	ele.disabled = '';
			}
	    }
	}else{
		if(ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"){
			Selector.setDisabled(ele,false);
		}else if(ele.hasClassName("zPushBtnDisabled")){
			ele.className="zPushBtn";
			if(ele.onclickbak){
				ele.onclick = ele.onclickbak;
			}
		}else{
			if(ele.hasClassName("disabled")){
				ele.removeClassName("disabled");
			}
	    	ele.disabled = '';
		}
	}
}

/**
 * @method scrollTo 滚动到改元素的位置
 * @param {Object} ele DOM元素对象或其id
 * @private
 */
$E.scrollTo = function(ele) {
	ele = ele || this;
	ele = $(ele);
  var x = ele.x ? ele.x : ele.offsetLeft,
      y = ele.y ? ele.y : ele.offsetTop;
  window.scrollTo(x, y);
}

/**
 * @method getWidth 获取元素宽度
 * @param {Object} ele
 * @return {Number} 
 */
$E.getWidth = function(ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getDimensions().width;
}

/**
 * @method getHeight 获取元素高度
 * @param {Object} ele
 * @return {Number} 
 */
$E.getHeight = function(ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getDimensions().height;
}

/**
 * @method css 获取或设置style样式
 * @param {Object} name
 * @param {Object} value
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$E.css = function(name, value) {
	
	if(String.isInstance(name)) {
		if(!value) {
			return this.style[name];
		}
		
		this.style[name] = value;
	} else {
		for(var p in name) {
			this.style[p] = name[p];
		}
	}
}

/**
 * 设置元素到屏幕中央
 * @param {Object} ele
 */
$E.center = function() {
	var viewport = $E.getViewportDimensions();
	var top = viewport.height / 2 - this.getHeight() / 2;
	var left = viewport.width / 2 - this.getWidth() / 2;
	this.css( {
		"top" : top,
		"left" : left
	});
}

/**
 * @method getDimensions 获取元素的大小
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素大小的对象，格式如：{width: 10, height: 20}
 */
$E.getDimensions = function(ele){
  ele = ele || this;
  ele = $(ele);
  var dim;
  if(ele.tagName.toLowerCase()=="script"){
  	dim = {width:0,height:0};
  }else if ($E.visible(ele)){
		if(Browser.isIE && ele.offsetWidth ==0 && ele.offsetHeight ==0){
			if(Browser.isBorderBox){
				dim = {width: ele.currentStyle.pixelWidth, height: ele.currentStyle.pixelHeight};
			}else{
				dim = {width: +ele.currentStyle.pixelWidth+ele.currentStyle.borderLeftWidth.replace(/\D/g,'')+ele.currentStyle.borderRightWidth.replace(/\D/g,'')+ele.currentStyle.paddingLeft.replace(/\D/g,'')+ele.currentStyle.paddingRight.replace(/\D/g,''),
						height: +ele.currentStyle.pixelHeight+ele.currentStyle.borderTopWidth.replace(/\D/g,'')+ele.currentStyle.borderBottomWidth.replace(/\D/g,'')+ele.currentStyle.paddingTop.replace(/\D/g,'')+ele.currentStyle.paddingBottom.replace(/\D/g,'')
				};
/**
				dim = {width: +curStyle.pixelWidth+parseInt(curStyle.borderLeftWidth)+parseInt(curStyle.borderRightWidth)+parseInt(curStyle.paddingLeft)+parseInt(curStyle.paddingRight),
						height: +curStyle.pixelHeight+parseInt(curStyle.borderTopWidth)+parseInt(curStyle.borderBottomWidth)+parseInt(curStyle.paddingTop)+parseInt(curStyle.paddingBottom)
				};
				*/
			}
		}else{
			dim = {width: ele.offsetWidth, height: ele.offsetHeight};
		}
  }else{
	  var style = ele.style;
	  var vis = style.visibility;
	  var pos = style.position;
	  var dis = style.display;
	  style.visibility = 'hidden';
	  style.position = 'absolute';
	  style.display = 'block';
	  var w = ele.offsetWidth;
	  var h = ele.offsetHeight;
	  style.display = dis;
	  style.position = pos;
	  style.visibility = vis;
	  dim = {width: w, height: h};
	}
	return dim;
}

/**
 * @method getPosition 获取元素的坐标
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素宽高的对象，格式如：{x: 10, y: 20}
 */
$E.getPosition = function(ele){
	ele = ele || this;
	ele = $(ele);
	var doc = ele.ownerDocument;
  if(ele.parentNode===null){//||ele.style.display=='none'){
    return false;
  }
  var parent = null;
  var pos = [];
  var box;
  if(ele.getBoundingClientRect){//IE,FF3,己很精确，但还没有非常确定无误的定位
    box = ele.getBoundingClientRect();
    var scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
    var scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
    var X = box.left + scrollLeft - doc.documentElement.clientLeft;
    var Y = box.top + scrollTop - doc.documentElement.clientTop;
    if(Browser.isIE){// new version is deleted
    	X--;
    	Y--;
    }
	/**
	var X = box.left + scrollLeft - doc.documentElement.clientLeft-doc.body.clientLeft;
    var Y = box.top + scrollTop - doc.documentElement.clientTop-doc.body.clientTop;

	*/
    return {x:X, y:Y};
  }else if(doc.getBoxObjectFor){ // FF2
    box = doc.getBoxObjectFor(ele);
    var borderLeft = (ele.style.borderLeftWidth)?parseInt(ele.style.borderLeftWidth):0;
    var borderTop = (ele.style.borderTopWidth)?parseInt(ele.style.borderTopWidth):0;
    pos = [box.x - borderLeft, box.y - borderTop];
  }
  if (ele.parentNode) {
    parent = ele.parentNode;
  }else {
    parent = null;
  }
  while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML'){
    pos[0] -= parent.scrollLeft;
    pos[1] -= parent.scrollTop;
    if (parent.parentNode){
      parent = parent.parentNode;
    }else{
      parent = null;
    }
  }
  return {x:pos[0],y:pos[1]}
}

$E.getViewportDimensions = function (win) {
	var win = win || window,
		doc = win.document,
		viewportWidth,
		viewportHeight;
	if(Browser.isIE){
		viewportWidth=Browser.isQuirks?doc.body.clientWidth :doc.documentElement.clientWidth ;
		viewportHeight=Browser.isQuirks?doc.body.clientHeight :doc.documentElement.clientHeight;
	}else{
		viewportWidth=win.innerWidth;
		viewportHeight=win.innerHeight;
	}
	return {width:viewportWidth, height:viewportHeight};
}
/**
 * @method getPositionEx 获取元素的坐标，计算滚动条的宽高
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素宽高的对象，格式如：{x: 10, y: 20}
 */
$E.getPositionEx = function(ele){
	ele = ele || this;
	ele = $(ele);
	var pos = $E.getPosition(ele);
	var win = window;
	var sw,sh;
	while(win!=win.parent){
		//if(win.frameElement&&win.parent.DataCollection){
		//	pos2 = $E.getPosition(win.frameElement);
		//	pos.x += pos2.x;
		//	pos.y += pos2.y;
		//}
		sw = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		sh = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		//pos.x -= sw;
		//pos.y -= sh;
		
		//if(!win.parent.DataCollection){
		//	break;
		//}
		win = win.parent;
	}
	return pos;
}

/**
 * @method getParent 获取元素指定标签名的父节点
 * @param {String} tagName 父节点的标签名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 父节点
 */
$E.getParent = function(tagName,ele){
	ele = ele || this;
	ele = $(ele);
	while(ele){
		if(ele.tagName.toLowerCase()==tagName.toLowerCase()){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

/**
 * @method getParentByAttr 根据父节点元素的属性名称获取指定父节点
 * @param {String} attrName 父节点的属性名称
 * @param {String} attrValue 父节点属性的值
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 父节点
 */
$E.getParentByAttr = function(attrName,attrValue,ele){
	ele = ele || this;
	ele = $(ele);
	while(ele){
		if(ele.getAttribute(attrName)==attrValue){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

/**
 * @method nextElement 获取该节点的下一个兄弟节点
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 兄弟节点
 */
$E.nextElement = function(ele){
	ele = ele || this;
	ele = $(ele);
	var x = ele.nextSibling;
	while (x&&x.nodeType!=1){
		x = x.nextSibling;
	}
	return $(x);
}

/**
 * @method previousElement 获取该节点的上一个兄弟节点
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 兄弟节点
 */
$E.previousElement = function(ele){
	ele = ele || this;
	ele = $(ele);
	var x = ele.previousSibling;
	while (x&&x.nodeType!=1){
		x = x.previousSibling;
	}
	return $(x);
}

/**
 * @method getTopLevelWindow 获取最顶层window对象
 * @return {window} 最顶层window对象
 */
$E.getTopLevelWindow = function(){
	var pw = window;
	while(pw!=pw.parent){
		//if(!pw.parent.DataCollection){
		//	return pw;
		//}
		pw = pw.parent;
	}
	return pw;
};

$TW = $E.getTopLevelWindow();

/**
 * @method hasClassName 检测元素是否拥有某class样式
 * @param {String} className class样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Boolean} 元素是否拥有某class样式
 */
$E.hasClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	return (new RegExp(("(^|\\s)" + className + "(\\s|$)"), "i").test(ele.className));
}

/**
 * @method addClassName 给元素添加样式
 * @param {String} className 样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @param {Boolean} before 是否加在当前样式之前
 * @return {String} 该元素的样式
 */
$E.addClass = $E.addClassName = function(className,ele,before){
	
	ele = ele || this;
	ele = $(ele);
	
	if(Array.isInstance(className)) {
		for(var i=0; i<className.length; i++) {
			ele.addClassName(className[i]);
		}
	}
		
	var currentClass = ele.className;
	currentClass = currentClass?currentClass:"";
	if(!ele.hasClassName(className)){
		if(before){
			ele.className = className + ((currentClass.length > 0)? " " : "") + currentClass;
		}else{
			ele.className = currentClass + ((currentClass.length > 0)? " " : "") + className;
		}
	}
	return ele.className;
}

$E.addClassOnOver = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	ele.on("mouseover", function() {
		ele.addClass(className);
	});
	ele.on("mouseout", function() {
		ele.removeClass(className);
	});
}
		
/**
 * @method removeClassName 移除样式
 * @param {String} className 需要移除的样式
 * @param {Object} ele DOM元素对象或其id
 * @return {String} 该元素的样式
 */
$E.removeClass = $E.removeClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
  var classToRemove = new RegExp(("(^|\\s)" + className + "(?=\\s|$)"), "i");
  ele.className = ele.className.replace(classToRemove, "").replace(/^\s+|\s+$/g, "");
  return ele.className;
}

/**
 * @method setClassName 给元素设置样式，原来样式将被替换
 * @param {String} className 样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 返回该对象
 */
$E.setClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	var _cn = ele.className || "";
	if(_cn.indexOf("z-btn-text") != -1) {
		ele.className = "z-btn-text " + className;
	} else {
		ele.className = className;
	}
  	return ele;
}

/**
 * @method setValue 设置值
 * @param {Object} value 需要设置的值
 * @param {Element} ele DOM元素对象或其id
 * @return {Element} 返回该对象
 */
$E.setValue = function(value, ele){
	ele = ele || this;
	ele = $(ele);
	$S(ele, value);
	return ele;
}

/**
 * @method getValue 获取元素的值
 * @param {Element} ele DOM元素对象或其id
 * @return {Object} 该元素的值
 */
$E.getValue = function(ele){
	ele = ele || this;
	ele = $(ele);
	return $V(ele);
}

$E.positionTo = function(target, ele) {
	ele = ele || this;
	ele = $(ele);
	var pos = target.getPositionEx();
    var divStyle = ele.style;
    divStyle.top = (pos.y + target.offsetHeight) + "px";
    divStyle.left = pos.x + "px";
    divStyle.position = 'absolute';
}

/*
给定p1(x1/y1)和p2(x2/y2)，p1在p2的左上方(也可重合)，计算一个起始坐标，
使得元素ele(宽为w,高为h)能够全部在p1之上，或者p2之下，并且尽可能显示ele的全部
flag="all"表示ele能够显示在x1的两边或者x2的两边
flag="left"表示ele能够显示在x1的左边或者x2的左边
flag="right"表示ele能够显示在x1的右边或者x2的右边
右键菜单、日期控件、下拉框控件需要这个函数
*/
$E.computePosition = function(x1,y1,x2,y2,flag,w,h,win){
	var doc = win?win.document:document;
	var cw = Browser.isQuirks?doc.body.clientWidth:doc.documentElement.clientWidth;
	var ch = Browser.isQuirks?doc.body.clientHeight:doc.documentElement.clientHeight;
	var sw = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
	var sh = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
	if(!flag||flag.toLowerCase()=="all"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//从P2往右展开可行
				return {x:x2,y:y2};
			}else{//往左展开
				return {x:x2-w,y:y2};
			}
		}
		//考虑p1
		if(x1-sw+w-cw<0){//从P1往右展开可行
			return {x:x1,y:y1-h};
		}else{//往左展开
			return {x:x1-w,y:y1-h};
		}
	}else	if(flag.toLowerCase()=="right"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//从P2往右展开可行
				return {x:x2,y:y2};
			}
		}
		
		//考虑p1
		if(y1>h){
			return {x:x1, y:y1-h};
		}
		
		if(y1 > ch-y2) {// y1上面的高度大于y2下面的高度
			return {x:x1, y:y1-h};
		}
		
		return {x:x2, y:y2};
	}else if(flag.toLowerCase()=="left"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw-w>0){//从P2往左展开可行
				return {x:x2,y:y2};
			}
		}
		//考虑p1
		return {x:x1-w,y:y1-h};
	}
}
/* 元素方法扩展 结束 */
