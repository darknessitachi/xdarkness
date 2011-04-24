/* 覆写setTimeout方法 开始 */
var _setTimeout = window.setTimeout;
window.setTimeout = function(callback, timeout, param) {
	if (typeof callback == 'function') {
		var args = Array.prototype.slice.call(arguments, 2);
		var _callback = function() {
			callback.apply(null, args);
		}
		return _setTimeout(_callback, timeout);
	}
	return _setTimeout(callback, timeout);
}
/* 覆写setTimeout方法 结束 */

/**
 * @class Sky 框架核心基础类
 */
var Sky = new Object();

/**
 * @method namespace 提供命名空间的注册
 * @static
 */
Sky.namespace = function() {
    var o, d;
    Array.each(arguments, function(v) {
        d = v.split(".");
        o = window[d[0]] = window[d[0]] || {};
        Array.each(d.slice(1), function(v2){
            o = o[v2] = o[v2] || {};
        });
    });
	return o;
}
/**
 * @method apply 将源对象中的属性都复制到目标对象
 * @param {Object} destination 目标
 * @param {Object} source 源对象
 * @return {Object} 复制后的对象 
 * @static
 */
Sky.apply = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}
/**
 * @method getUniqueID 获取唯一id
 * @return {String} 唯一id，由当前时间+一个10000以内的随机数组成
 * @static
 */
Sky.getUniqueID = getUniqueID = function () {
	return ("_" + (new Date().getTime()) + "_" + parseInt(Math.random() * 10000));
}
/**
 * @method attachMethod 为对象添加额外的方法
 * @param {Element} ele dom元素
 * @return {XElement} 扩展后的元素
 * @private
 */
Sky.attachMethod = function(ele){
	if(!ele||ele["$A"]){
		return;
	}
	
	if(ele["attachedMethod"] === true) {
		return;
	}
	
	if(ele.nodeType==9){
		return;
	}
	var win;
	try{
		if(Browser.isGecko){
			win = ele.ownerDocument.defaultView;
		}else{
			win = ele.ownerDocument.parentWindow;
		}
		for(var prop in win.$E){
			ele[prop] = win.$E[prop];
		}
	}catch(ex){
		//alert("Sky.attachMethod:"+ele)//有些对象不能附加属性，如flash
	}
	
	ele["attachedMethod"] = true;
}
/**
 * @method get 获取DOM元素对象
 * @param {String/Element} ele DOM元素的id或者DOM元素
 * @return {Element} DOM元素
 * @static
 */
Sky.get = $ = function (ele) {
	
	if(!ele) {
		return null;
	}
	
	if (typeof(ele) == 'string'){
		ele = document.getElementById(ele);
		if(!ele){
			return Sky.getName(ele);
		}
	}
  
  /**
   * @demo $({xtype: 'password'}), $({xtype: ['text','password']})
   * @xparam {Object} ele
   */
  if(ele.xtype && !ele.attachedMethod) {
	  var types = Sky.getByType(ele.xtype)
	  types.each(function(ele){
		  Sky.attachMethod(ele);
	  });
	  /**
	   * 添加clear方法，用来清除所有元素
	   * @memberOf {TypeName} 
	   */
	  types.clear = function(){
		  this.each(function(ele){
			  ele.clear();
		  });
	  }
	  return types;
  }
  
  if(ele){
  	Sky.attachMethod(ele);
  }
  return ele;
}

/**
 * @method goForward 页面向后一页
 * @static
 */
Sky.goForward = function() {
	window.history.forward(1);
}
/**
 * @method createEl 创建Element
 * @param {Object} config 属性配置项
 * @return {XElement} 扩展后的对象 
 * @static
 */
Sky.createEl = function(config) {
	var el = $(document.createElement(config.tag));
	
	el.id = config.id || config.name || "";
	el.name = config.name || config.id || "";
	
	if(config.className) {
		el.addClass(config.className);
	}
	if(config.onClick) {
		el.on("click", config.onClick);
	}
	if(config.value) {
		el.value = config.value;
	}
	if(config.readonly === true) {
		el.disable();
	}
	return el;
}

/**
 * @method createText 创建Element
 * @param {Object} config 属性配置项
 * @return {Element} 文本对象
 * @static
 */
Sky.createText = function(text) {
	return document.createTextNode(text);
}

/**
 * @method getValue 获取表单元素值 
 * @param {Object} ele DOM元素的id或者DOM元素
 * @return {String} 元素的值
 * @static
 */
Sky.getValue = $V = function (ele){
	var eleId = ele;
	ele = $(ele);
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return null;
	}
	switch (ele.type.toLowerCase()) {
	    case 'submit':
	    case 'reset':
	    case 'button':
	    	//break;// buttons, we don't care
	    case 'hidden':
	    case 'password':
	    case 'textarea':
	    case 'file':
	    case 'image':
	    case 'select-one':
	    case 'text':
	      return ele.value;
	      
	    // checkbox/radio buttons, only return the value if the control is checked.  
	    case 'checkbox':
	    case 'radio':
	      if (ele.checked){
	    		return ele.value;
	      }else{
	    		return null;
	      }
	    default:
	    		return ele.value;
 	}
}

/**
 * @method encodeNameAndValue 编码为"name=value"形式
 * @param {String} name 键
 * @param {String} value 值
 * @return {String} 编码后的字符串
 * @static
 */
Sky.encodeNameAndValue = function(name, value){
	return encodeURIComponent(name) + "=" + encodeURIComponent(value);
}

/**
 * @method getRequestBody 获取form中的所有元素为"key=value"形式，用&连接
 * @param {Form} form 表单元素
 * @return {String} 获取的字符串
 * @static
 */
Sky.getRequestBody = function(form) {
	var params = [];
	
	for(var i=0; i<form.elements.length; i++){
		var field = from.elements[i];
		var value = Sky.getValue(field);
		if(value) {
			params.push(encodeURIComponent(field.name) + "=" + encodeURIComponent(value));
		}
	}
	
	return params.join("&");
}

/**
 * @method setValue 设置表单元素值
 * @param {String/Element} ele DOM元素的id或者DOM元素
 * @param {String} v 需要设置的值
 * @static
 */
Sky.setValue = $S = function (ele,v){
	var eleId = ele;
	ele = $(ele);
	if(!v&&v!=0){
		v = "";
	}
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return;
	}
  switch (ele.type.toLowerCase()) {
    case 'submit':
    case 'hidden':
    case 'password':
    case 'textarea':
    case 'button':
    case 'file':
    case 'image':
    case 'select-one':
    case 'text':
      ele.value = v;
      break;
    case 'checkbox':
    case 'radio':
      if(ele.value==""+v){
      	ele.checked = true;
      }else{
      	ele.checked=false;
      }
      break;
   }
}

/**
 * @method getTag 获取ele元素下的所有标签名称为tagName的对象
 * @param {String} tagName 标签的名称
 * @param {Object} ele DOM元素或其ID，此参数可选，默认为document
 * @return {Array} DOM对象数组
 * @static
 */
Sky.getTag = $T = function (tagName,ele){
	ele = $(ele);
	ele = ele || this || document;
	if(!ele.getElementsByTagName) {
		ele = document;
	}
	var ts = ele.getElementsByTagName(tagName);//此处返回的不是数组
	var arr = [];
	var len = ts.length;
	for(var i=0;i<len;i++){
		arr.push($(ts[i]));
	}
	return arr;
}

/**
 * @method getByType 根据type查询
 * @param {Object} types 可以为字符串，或数组
 * @return {Array} 元素数组 
 */
Sky.getByType = function(types){
	if(String.isInstance(types)) {
		return Sky.getTag(types);
	}
	
	var allTypes = [];
	types.each(function(type){
		allTypes.add(Sky.getTag(types));
	});
	return allTypes;
}

/**
 * @method getControls 获取所有的控件
 * @param {XType/String} xtype 控件类型
 * @return {Array：XControl} 控件数组
 * @static
 */
Sky.getControls = function(xtype) {
	var inputs = $T("input");
	var controls = [];
	for(var i=0;i<inputs.length;i++) {
		var e = inputs[i];
		if(e.getAttribute("xtype") == xtype) {
			if(xtype.toLowerCase() == "select"){
	      		e = e.parentNode;
	      	}
			controls.push(e);
		}
	}
	return controls;
}

/**
 * @method getName 获取所有name属性为ele的元素
 * @param {String} ele DOM元素的name
 * @return {Array} 所有name属性为ele的元素
 * @static
 */
Sky.getName = function (ele){
    if (typeof(ele) == 'string'){
      ele = $N(ele);
      
      if(ele.length == 1){
    	return ele[0];
      }
    }
    
    return ele;
}

/**
 * @method getUrl 从新组装url
 * @param {String} src url
 * @param {Object} _params 参数对象
 * @return {String} 新的url 
 */
Sky.getUrl = function(src, _params){
	if (src.indexOf("?") > 0) {
        src += "&" + param;
    }
    else {
        src += "?" + param;
    }
    // @TODO 需要重新考虑考虑，注意：需考虑中文，及编码后的字符
    var params = src.match(/([\w\.]*)=([^&]*)/g);//(/([\w\.]*)=([\w\.\[\]\,\s]*)/g);
    var paramsObj = {};
    if (params != null) {/* 将原来url路径的参数解析放入paramsObj对象中 */
        for (var i = 0; i < params.length; i++) {
            var paramObj = params[i].split('=');
            paramsObj[paramObj[0]] = paramObj[1];
        }
    }
    if (_params && (typeof _params === "object")) {/* 将传递过来的参数(需为对象方式)_params放入paramsObj对象中，有则替换 */
        for (var _param in _params) {
            paramsObj[_param] = _params[_param];
        }
    }
    paramsObj["currentTime"] = new Date().getTime();
    var strParams = "";
    for (var param in paramsObj) {/* 拼装新的参数字符串 */
        strParams += "&" + param + "=" + paramsObj[param];
    }
    strParams = strParams.replace("&", "?");
    src = src.split("?")[0] + strParams;
    return src;
}

/**
 * @method getElementByName 根据名称返回元素列表
 * @param {String} name 元素的名称
 * @return {Array：XElement} 数组 
 */
Sky.getElementByName = $N = function (ele){
    if (typeof(ele) == 'string'){
      ele = document.getElementsByName(ele);
      if(!ele||ele.length==0){
    		return null;
      }
      var arr = [];
      for(var i=0;i<ele.length;i++){
      	var e = ele[i];
      	var xtype = e.getAttribute("xtype") || ""; 
      	if(xtype.toLowerCase() == "select"){
      		e = e.parentNode;
      	}
      	Sky.attachMethod(e);
      	arr.push(e);
      }
      ele = arr;
      
    }
    
    return ele;
}

/**
 * @method getValuesByName 获取所有name属性为ele的元素的值
 * @param {String} ele DOM元素的name
 * @return {Array} name属性为ele的元素的值的数组
 * @static
 */
Sky.getValuesByName = $NV = function (ele){
	ele = $N(ele);
	if(!ele){
		return null;
	}
	var arr = [];
	for(var i=0;i<ele.length;i++){
		var v = $V(ele[i]);
		if(v!=null){
			arr.push(v);
		}
	}
	return arr.length==0? null:arr;
}

/**
 * @method setValueByName 设置所有name属性为ele的元素的值为value
 * @param {String} ele DOM元素的name
 * @param {String} value 需要设置的值
 * @static
 */
Sky.setValueByName = $NS = function (ele,value){
	ele = $N(ele);
	if(!ele){
		return;
	}
	if(!ele[0]){
		return $S(ele,value);
	}
	if(ele[0].type=="checkbox"){
		if(value==null){
			value = new Array(4);
		}
		var arr = value;
		if(!isArray(value)){
			arr = value.split(",");
		}
		for(var j=0;j<arr.length;j++){
			for(var i=0;i<ele.length;i++){
				$S(ele[i],arr[j]);
			}
		}
		/**
		 * for(var i=0;i<ele.length;i++){
			for(var j=0;j<arr.length;j++){
				if(ele[i].value==arr[j]){
					$S(ele[i],arr[j]);
					break;
				}
				$S(ele[i],arr[j]);
			}
		}
		 */
		return;
	}
	for(var i=0;i<ele.length;i++){
		$S(ele[i],value);
	}
}

/**
 * @method getForm 获取表单
 * @param {String} ele 表单元素的id，可选参数，默认获取当前文档下的第一个表单对象
 * @return {Element} form表单元素
 * @static
 */
Sky.getForm = $F = function (ele){
	if(!ele)
		return document.forms[0];
	else{
		ele = $(ele);
		if(ele&&ele.tagName.toLowerCase()!="form")
			return null;
		return ele;
	}
}

/**
 * @method javaEncode 编码成java字符串
 * @param {String} txt 字符串
 * @return {String} 编码后的字符串
 * @static
 */
Sky.javaEncode = function (txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\", "\\\\").replace("\r\n", "\n").replace("\n", "\\n").replace("\"", "\\\"").replace("\'", "\\\'");
}

/**
 * @method javaDecode 将java字符串解码
 * @param {String} txt 字符串
 * @return {String} 解码后的字符串
 * @static
 */
Sky.javaDecode = function (txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\\\", "\\").replace( "\\n", "\n").replace("\\r", "\r").replace("\\\"", "\"").replace("\\\'", "\'");
}

/**
 * @method encodeURL 编码URL
 * @param {String} str 需要编码的字符串
 * @return {String} 编码后的字符串
 * @static
 */
Sky.encodeURL = encodeURL = function (str){
	return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
}

/**
 * @method htmlEncode 编码html
 * @param {String} str 需要编码的html片段
 * @return {String} 编码后的字符串
 * @static
 */
Sky.htmlEncode = htmlEncode = function (str) {
	return str.replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/ /g,"&nbsp;");
}

/**
 * @method htmlDecode 解码html
 * @param {String} str 需要解码的html片段
 * @return {String} 解码后的字符串
 * @static
 */
Sky.htmlDecode = htmlDecode = function (str) {
	return str.replace(/\&quot;/g,"\"").replace(/\&lt;/g,"<").replace(/\&gt;/g,">").replace(/\&nbsp;/g," ").replace(/\&amp;/g,"&");
}

/**
 * @method isInt 是否为整型
 * @param {Object} obj 需要判断的对象
 * @return {Boolean} 是否为整型
 * @static
 */
Sky.isInt = isInt = function (str){
	return /^\-?\d+$/.test(""+str);
}

/**
 * @method isNumber 是否为数字
 * @param {Object} obj 需要判断的对象
 * @return {Boolean} 是否为数字
 * @static
 */
Sky.isNumber = isNumber = function (str){
	var t = ""+str;
	for(var i=0;i<str.length;i++){
		var chr = str.charAt(i);
		if(chr!="."&&chr!="E"&&isNaN(parseInt(chr))){
			return false;
		}
	}
	return true;
}
/**
 * function isNumber(str){
	var t = ""+str;
	var dotCount = 0;
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(c=="."){
			if(i==0||i==str.length-1||dotCount>0){
				return false;
			}else{
				dotCount++;
			}
		}else if(c=='-'){
			if(i!=0){
				return false;
			}
		}else	if(isNaN(parseInt(c))){
			return false;
		}
	}
	return true;
}
 */

/**
 * @method isTime 是否为时间型，如 12:25:28
 * @param {String} str 需要判断的字符串
 * @return {Boolean} 是否为时间型
 * @static
 */
Sky.isTime = isTime = function (str){
	if(!str){
		return false;
	}
	var arr = str.split(":");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setHours(arr[0]);
	date.setMinutes(arr[1]);
	date.setSeconds(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

/**
 * @method isDate 是否为日期型，如 2010-01-28
 * @param {String} str 需要判断的字符串
 * @return {Boolean} 是否为日期型
 * @static
 */
Sky.isDate = isDate = function (str){
	if(!str){
		return false;
	}
	var arr = str.split("-");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setFullYear(arr[0]);
	date.setMonth(arr[1]);
	date.setDate(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

/**
 * @method isDateTime 是否为时间日期型，如 2010-01-28 12:25:28
 * @param {String} str 需要判断的字符串
 * @return {Boolean} 是否为时间日期型
 * @static
 */
Sky.isDateTime = isDateTime = function (str){
	if(!str){
		return false;
	}
	if(str.indexOf(" ")<0){
		return isDate(str);
	}
	var arr = str.split(" ");
	if(arr.length<2){
		return false;
	}
	return isDate(arr[0])&&isTime(arr[1]);
}

/**
 * @method isArray 是否为数组
 * @param {Object} obj 需要检测的对象
 * @return {Boolean} 是否为数组
 * @static
 */
Sky.isArray = isArray = function (obj) {
	 if(!obj){
	 	 return false;
	 }
   if (obj.constructor.toString().indexOf("Array") == -1){
      return false;
   } else{
      return true;
   }
}

/**
 * @method isNull 是否为null或undefined 
 * @param {Object} v 需要检测的对象
 * @return {Boolean} 是否为null或undefined
 * @static
 */
Sky.isNull = isNull = function (v){
	return v===null||typeof(v)=="undefined";
}

/**
 * @method getEvent 获取事件对象，该方法为了兼容个浏览器
 * @param {Event} evt 事件对象
 * @return {Event} 事件对象
 * @static
 */
Sky.getEvent = getEvent = function (evt){
	return window.event||evt;
}
/**
 * 
function getEvent(evt){
	if(document.all) return window.event;
	if (evt) {
		if((evt.constructor  == Event || evt.constructor == MouseEvent) || (typeof(evt) == "object" && evt.preventDefault && evt.stopPropagation)) {
			return evt;
		}
	}
	func = getEvent.caller;
	while(func != null) {
		var arg0 = func.arguments[0];
		if (arg0) {
			if((arg0.constructor  == Event || arg0.constructor == MouseEvent) || (typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func=func.caller;
	}
	return null;
}

 * @param {Object} evt
 */

/**
 * @method stopEvent 阻止一切事件执行,包括浏览器默认的事件
 * @param {Event} evt 事件对象
 * @static
 */
Sky.stopEvent = stopEvent = function (evt){
	evt = getEvent(evt);
	if(!evt){
		return;
	}
	if(Browser.isGecko){
		evt.preventDefault();
		evt.stopPropagation();
	}
	evt.cancelBubble = true
	evt.returnValue = false;
}

/**
 * @method cancelEvent 仅阻止用户定义的事件
 * @param {Event} evt 事件对象
 * @static
 */
Sky.cancelEvent = cancelEvent = function (evt){
	evt = getEvent(evt);
	evt.cancelBubble = true;
}

/**
 * @method getEventPosition 返回相对于最上级窗口的左上角原点的坐标
 * @param {Event} evt 事件对象
 * @return {Object} {x:10,y:20}形式的对象
 * @static
 */
Sky.getEventPosition = getEventPosition = function (evt){
	evt = getEvent(evt);
	var pos = {x:evt.clientX, y:evt.clientY};
	//debugger;
	//Console.debug("src:"+evt.srcElement+" EvtPos:{" + pos.x + "," + pos.y + "}\n");
	//Console.debug(" EvtPos:{" + evt.x + "," + evt.y + "}\n");
	var win;
	if(Browser.isGecko){
		win = evt.srcElement.ownerDocument.defaultView;
	}else{
		win = evt.srcElement.ownerDocument.parentWindow;
	}
	var sw,sh;
	while(win!=win.parent){
		if(win.frameElement&&win.parent.DataCollection){
			pos2 = $E.getPosition(win.frameElement);
			pos.x += pos2.x;
			pos.y += pos2.y;
		}
		sw = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		sh = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		pos.x -= sw;
		pos.y -= sh;
		if(!win.parent.DataCollection){
			break;
		}
		win = win.parent;
	}
	//Console.debug("After EvtPos:{" + pos.x + "," + pos.y + "}\n");
	return pos;
}

/**
 * @method getEventPositionLocal 返回事件在当前页面上的坐标
 * @param {Event} evt 事件对象
 * @return {Object} {x:10,y:20}形式的对象
 * @static
 */
Sky.getEventPositionLocal = getEventPositionLocal = function (evt){
	evt = getEvent(evt);
	var pos = {x:evt.clientX, y:evt.clientY};
	var win;
	if(Browser.isGecko){
		win = evt.srcElement.ownerDocument.defaultView;
	}else{
		win = evt.srcElement.ownerDocument.parentWindow;
	}
	pos.x += Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
	pos.y += Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
	return pos;
}

/**
 * @method toXMLDOM 将xml转换为DOC对象
 * @param {String} xml xml文本
 * @return {DOMDocument} DOM文档对象
 * @static
 */
Sky.toXMLDOM = toXMLDOM = function (xml){
	var doc;
	if(Browser.isIE){
		try{
			doc = new ActiveXObject("Microsoft.XMLDOM");
		}catch(ex){
			doc = new ActiveXObject("Msxml2.DOMDocument");
		}
		doc.loadXML(xml);
	}else{
	   var p =new DOMParser();
	   doc= p.parseFromString(xml,"text/xml");
	}
	return doc;
}

/**
 * @method toArray 把可枚举的集合转换为数组
 * @param {Object} a 可枚举的对象
 * @param {Number} start 开始索引
 * @param {Number} length 长度
 * @return {Array} 数组
 */
Sky.toArray = function (a,i,j) { 
	 if(Browser.isIE){
		 var res = [];
		 for (var x = 0, len = a.length; x < len; x++) {
			 res.push(a[x]);
		 }
		 return res.slice(i || 0, j || res.length);
	 }else{
		 return Array.prototype.slice.call(a, i || 0, j || a.length)
	 }
}
/**
onWindowResize(func)
在非ie6浏览器中使用此方法，等同于window.attachEvent('onresize',func)
在ie6下使用此方法注册的方法，在仅仅页面尺寸变化，而窗口尺寸并没有变化时，不会执行。
用于修正ie6下body尺寸改变时会激发onresize事件的问题，
**/
var onWindowResize=(function(){
	var viewportBackup=null;//通过闭包来避免使用全局变量
	var doViewportBackup=function(){
		viewportBackup=$E.getViewportDimensions();
	}
	window.attachEvent('onload',doViewportBackup);
	var onResize = function(fn){
		if(!fn||typeof(fn)!='function'){
			throw ' Main.js#onWindowResize: 参数错误，请确定参数类型为function';
			return;
		}
		var onWindowResizeFn=null;
		if(Browser.isIE6){
			onWindowResizeFn=function(){
				var viewport=$E.getViewportDimensions();
				if(viewportBackup){
					if(viewport.width!==viewportBackup.width||viewport.height!==viewportBackup.height){
						viewportBackup={width:viewport.width,height:viewport.height};
						try{
							fn();
						}catch(ex){
							throw location.pathname+" Main.js#onWindowResize: 传递给OnWindowResize的方法执行错误！";
						}
					}
				}else{
					try{
						fn()
					}catch(ex){}
				}
			}
		}else{
			onWindowResizeFn=fn;
		}
		window.attachEvent('onresize',onWindowResizeFn);
	};
	return onResize;
})();

