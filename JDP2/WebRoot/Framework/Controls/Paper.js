/**
 * 报纸热区绘制类
 * sky)wangzhaohui
 **/

/*************************一些公用方法和属性****************************/
if(window.CONTEXTPATH&&window.$E){//如果本页面同时调用了Main.js，则使用Main.js中的公用方法
	var ieVer = isIE ? parseInt(agt.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1]) : 0;
	var ielt7 = isIE && ieVer<7;
	var $id=$;
	var getPosition=$E.getPosition;
	var hasClassName=$E.hasClassName;
	var addClassName=$E.addClassName;
	var removeClassName=$E.removeClassName;
}else{//如果页面没有调用Main.js，则重新定义公用方法
	var agt =   window.navigator.userAgent;
	var isIE = agt.toLowerCase().indexOf("msie") != -1;
	var isGecko = agt.toLowerCase().indexOf("gecko") != -1;
	var ieVer = isIE ? parseInt(agt.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1]) : 0;
	if(!!window.XDomainRequest&&!!document.documentMode)ieVer=8;//在ie8兼容模式下userAgent会返回'MSIE 7'
	var ielt7 = isIE && ieVer<7;

	function $id(id){
		return typeof(id) == 'string'?document.getElementById(id):id;
	}
	function getEventPosition(evt){
		var evt=window.event||evt
		var pos = {};
		pos.x = evt.clientX+document.body.scrollLeft+document.documentElement.scrollLeft;
		pos.y = evt.clientY+document.body.scrollTop+document.documentElement.scrollTop;
		return pos;
	}
	function stopEvent(evt){//阻止一切事件执行,包括浏览器默认的事件
		evt = window.event||evt;
		if(!evt)
			return;
		if(isGecko){
			evt.preventDefault();
			evt.stopPropagation();
		}
		evt.cancelBubble = true
		evt.returnValue = false;
	}

	function getPosition(ele){
		ele = $id(ele);
		var doc = ele.ownerDocument;
		if(ele.parentNode===null||ele.style.display=='none')
			return false;
		var parent = null;
		var pos = [];
		var box;
		if(ele.getBoundingClientRect){//IE,FF3,己很精确，但还没有非常确定无误的定位
			box = ele.getBoundingClientRect();
			var scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
			var scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
			var X = box.left + scrollLeft - doc.documentElement.clientLeft;
			var Y = box.top + scrollTop - doc.documentElement.clientTop;
			if(isIE){
				X--;
				Y--;
			}
			return {x:X, y:Y};
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

	function hasClassName(strClassName,oElm){
		return (new RegExp(("(^|\\s)" + strClassName + "(\\s|$)"), "i").test(oElm.className));
	}
	function addClassName(strClassName,oElm){
		var strCurrentClass = oElm.className;
		if(!new RegExp(strClassName, "i").test(strCurrentClass)){
			oElm.className = strCurrentClass + ((strCurrentClass.length > 0)? " " : "") + strClassName;
		}
	}
	function removeClassName(strClassName,oElm){
		var oClassToRemove = new RegExp((strClassName + "\s?"), "i");
		oElm.className = oElm.className.replace(oClassToRemove, "").replace(/^\s?|\s?$/g, "");
	}
	if(!isIE&&HTMLElement){
		if(!HTMLElement.prototype.attachEvent){
			window.attachEvent = document.attachEvent = HTMLElement.prototype.attachEvent = function(evtName,func){
				evtName = evtName.substring(2);
				this.addEventListener(evtName,func,false);
			}
			window.detachEvent = document.detachEvent = HTMLElement.prototype.detachEvent = function(evtName,func){
				evtName = evtName.substring(2);
				this.removeEventListener(evtName,func,false);
			}
		}
		HTMLElement.prototype.__defineGetter__("currentStyle", function(){
			return this.ownerDocument.defaultView.getComputedStyle(this,null);
		});
	}

	var JSON = {};
	JSON.toString=function(O) {
		var string = [];
		var isArray = function(a) {
			var string = [];
			for(var i=0; i< a.length; i++) string.push(JSON.toString(a[i]));
			return string.join(',');
		};
		var isObject = function(obj) {
			var string = [];
			for (var p in obj){
				if(obj.hasOwnProperty(p) && p!='prototype'){
					string.push('"'+p+'":'+JSON.toString(obj[p]));
				}
			};
			return string.join(',');
		};
		if (!O) return false;
		if (O instanceof Function) string.push(O);
		else if (O instanceof Array) string.push('['+isArray(O)+']');
		else if (typeof O == 'object') string.push('{'+isObject(O)+'}');
		//else if (typeof O == 'string') string.push('"'+O.replace(/(\/|\")/gm,'\\$1')+'"');
		else if (typeof O == 'string') string.push('"'+O+'"');
		else if (typeof O == 'number' && isFinite(O)) string.push(O);
		return string.join(',');
	}
	JSON.evaluate=function(str) {
		return (typeof str=="string")?eval('(' + str + ')'):str;
	}
}
/*************************报纸类****************************/

var Paper=function(id,func,hotareaData,drawable){//id为图片的id或图片对象，hotareaHash为初始化时要预置的热区的JSON字符串数据，func为双击报纸热区时要调用的方法，drawable为是否可在报纸上绘制热区。
	this.init(id,func,hotareaData,drawable);
}
Paper.getInstance=function(id){
	return $id(id).PaperInstance;
};
Paper.prototype={
	init:function(id,func,hotareaData,drawable){
		var ele=$id(id);
		if(!ele)return alert('不存在元素'+id);
		if(ele.PaperInstance){
			document.getElementsByTagName('BODY')[0].removeChild(ele.PaperInstance.elem.parentNode);
			ele.PaperInstance=null;
		}
		ele.PaperInstance=this;
		if(drawable)this.drawable=true;
		var pos=getPosition(ele);
		var posDiv=document.createElement('div');
		posDiv.style.cssText='position:absolute;border:solid black 1px;left:'+pos.x+'px;top:'+pos.y+'px;';
		posDiv.innerHTML='<div id="_paper_'+ele.id+'" class="blankBg" style="position:relative;cursor:default;width:'+ele.width+'px;height:'+ele.height+'px;-moz-user-select:-moz-none;"></div>';
		document.getElementsByTagName('BODY')[0].appendChild(posDiv)
		this.callFunc=func;
		this.elem=$id('_paper_'+ele.id);
		this.rectArray=[];
		if(hotareaData)
			this.initFromJOSN(hotareaData);
		this.attachBehaviors()
	},
	initFromJOSN : function (hotareaData) {
		if(typeof(hotareaData)=='string')
			var hotareaData=JSON.evaluate(hotareaData);
		for(var i=0,len=hotareaData.length;i<len;i++){
			var hotarea=hotareaData[i];
			var r=new Rect();
			if(this.drawable)
				r.editable=true;
			r.container=this.elem;
			r.callFunc=this.callFunc;
			r.rectArray=this.rectArray;
			var ele=r.create(hotarea.hashData);
			if(this.drawable){
				ele.className='editableRect';
				r.addTitle(hotarea.hashData);
			}else{
				ele.className='rect';
			}
			ele.style.cssText=hotarea.css;
			r.attachBehaviors();
		}
	},
	attachBehaviors : function () {
		var self=this;
		if(this.drawable){
			this.elem.attachEvent("onmousedown", function(evt){self.onMouseDown(evt)});
		}
	},
	onMouseDown:function(evt){
		var evt=window.event||evt
		var target = evt.srcElement||evt.target;
		//console.log(target);
		if(this.elem==target){//如果鼠标是点在报纸上，而非其它矩形上，就开始画矩形
			var r=new Rect();
			if(this.drawable)r.editable=true;
			r.container=this.elem;
			r.callFunc=this.callFunc;
			r.rectArray=this.rectArray;
			r.start(evt);
		}
	},
	hotarea2JSON : function () {
		var tempObj=[];
		for(var i=0,len=this.rectArray.length;i<len;i++){
			if(this.rectArray[i]==0)continue;
			var r=this.rectArray[i].elem;
			tempObj.push({id:r.id,css:r.style.cssText,hashData:JSON.evaluate($id(r.id+'_hashData').value)});
		}
		var json2Str=JSON.toString(tempObj);
		//console.log(json2Str);
		return json2Str;
	}
}

/**** 矩形类 ****/
var Rect=function(){
	this.init();
}
Rect.selected=null;

Rect.attachBehaviors = function () {
	document.attachEvent("onkeydown", Rect.onKeyDown);
};

Rect.onKeyDown = function (evt) {
	var evt=window.event||evt
	if (evt.keyCode == 46) { //Delete键
		var target = evt.srcElement||evt.target;
		//console.log(target.tagName)
		if(target.tagName=='INPUT')//如果在输入状态中
			return;
		if(Rect.selected)Rect.selected.remove();
	}
};

Rect.prototype={
	init:function(){
		this.rectArray = null;
		this.container=null;
		this.callFunc=null;
		this.editable=false;
	},
	start:function(evt){
		Rect.selected&&Rect.selected.blur();
		var pos=this.container.pos=getPosition(this.container);
		var evtPos=getEventPosition(evt);
		var ele=this.create();
		ele.className='editableRect';
		ele.style.left=evtPos.x-pos.x+'px';
		ele.style.top=evtPos.y-pos.y+'px';
		ele.style.height = 1 + "px";
		ele.style.width = 1 + "px";
		//mouseBeginX，mouseBeginY是辅助变量，记录下鼠标按下时的位置
		ele.mouseBeginX = evtPos.x;
		ele.mouseBeginY = evtPos.y;
		ele.className='editableRect drawing';
		var self=this;
		this._fMove=function(evt){self.move(evt)}
		this._fFinish=function(evt){self.finish(evt)}
		document.attachEvent("onmousemove", this._fMove);
		document.attachEvent("onmouseup", this._fFinish);
	},
	create:function(hashData){
		this.id = this.rectArray.length + "";
		var ele=this.elem = document.createElement('div');
		ele.style.position = "absolute";
		ele.style.padding = "0";
		ele.style.zIndex = "10";
		ele.id='_rect'+this.id;
		ele.innerHTML='<input type="hidden" id="'+ele.id+'_hashData" value="{title:\'双击编辑属性\'}" />';
		this.container.appendChild(ele);
		this.hashDataEle=$id(ele.id+'_hashData');
		if(hashData)
			this.hashDataEle.value=typeof(hashData)=='string'?hashData:JSON.toString(hashData);
		if(!window.RectAttachBehaviors){
			Rect.attachBehaviors();
			window.RectAttachBehaviors=true;
		}
		this.rectArray.push(this);
		return ele;
	},
	move:function(evt){
		var evt=window.event||evt
		var ele = this.elem;
		//dx，dy是鼠标移动的距离
		var dx = getEventPosition(evt).x - ele.mouseBeginX;
		var dy = getEventPosition(evt).y - ele.mouseBeginY;
		//如果dx，dy <0,说明鼠标朝左上角移动，需要做特别的处理
		var el=getEventPosition(evt).x-this.container.pos.x;
		var et=getEventPosition(evt).y-this.container.pos.y;
		if(dx<0&&el>0){
			ele.style.left = el+'px';
			ele.style.width = Math.abs(dx)+'px';
		}else if(dx>0&&ele.offsetLeft+dx<this.container.clientWidth){
			ele.style.width = Math.abs(dx)+'px';
		}
		if(dy<0&&et>0){
			ele.style.top = et+'px';
			ele.style.height = Math.abs(dy)+'px';
		}else if(dy>0&&ele.offsetTop+dy<this.container.clientHeight){
			ele.style.height = Math.abs(dy)+'px';
		}
	},
	finish:function(evt){
		var evt=window.event||evt
		var self=this;
		//onmouseup时释放onmousemove，onmouseup事件句柄
		document.detachEvent("onmousemove", this._fMove);
		document.detachEvent("onmouseup", this._fFinish);
		if(this.elem.clientWidth<30&&this.elem.clientHeight<20){
			this.remove();
			return;
		}
		removeClassName('drawing',this.elem);
		this.addTitle();
		this.attachBehaviors();
		this.focus();
	},
	attachBehaviors:function(){
		var self=this;
		if(this.editable){
			this.elem.attachEvent("ondblclick", function(evt){self.callFunc(self.hashDataEle,evt)})
			this.elem.attachEvent("onmousedown", function(evt){self.focus(evt)});
			if (window.Drag)
				new Drag(this.elem,{Limit:true, mxContainer:this.container});//注册拖拽方法
			if (window.Resize)
				new Resize(this.elem,{ Max: true, mxContainer:this.container});//注册为可调整大小
		}else{
			this.elem.attachEvent("onclick", function(evt){self.callFunc(self.hashDataEle,evt)});
			this.elem.attachEvent("onmouseover", function(evt){self.focus(evt),self.tipOn(evt);;});
			this.elem.attachEvent("onmousemove", function(evt){self.focus(evt),self.tipMove(evt);});
			this.elem.attachEvent("onmouseout", function(evt){self.blur(evt),self.tipOff(evt);});
		}
	},
	remove:function(evt){
		var evt=window.event||evt
		this.container.removeChild(this.elem);
		Rect.selected=null;
		for(var i=0,len=this.rectArray.length;i<len;i++){
			if(this == this.rectArray[i]){
				this.rectArray[i]=0;
			}
		}
	},
	addTitle:function(){
		var ele=this.titleDiv=document.createElement('div');
		ele.className='titleArea';
		ele.innerHTML='<span id="'+this.elem.id+'_title">双击编辑属性</span>';
		this.elem.appendChild(ele);
		this.titleEle=$id(this.elem.id+'_title');
		var self=this;
		if(isIE){
			this.hashDataEle.attachEvent("onpropertychange", function(evt){self.onHashDataChange(evt)})
		}else{
			this.hashDataEle.attachEvent("onDOMAttrModified", function(evt){self.onHashDataChange(evt)})
		}
			this.onHashDataChange();
	},
	onHashDataChange:function(evt){
		var evt=window.event||evt
		this.titleEle.innerHTML=JSON.evaluate(this.hashDataEle.value).title;
	},
	focus:function(){
		Rect.selected&&Rect.selected.blur();
		if(this.editable){
			this.elem.className='editableRectActive';
		}else{
			this.elem.className='rectHover';
		}
		Rect.selected=this;
	},
	blur:function(){
		if(this.editable){
			this.elem.className='editableRect';
		}else{
			this.elem.className='rect';
		}
		Rect.selected=null;
	},
	tipOn:function(evt){
		var evt=window.event||evt;
		var tiper=this.tiper=$id('_paper_tip');
		if(!tiper){
			tiper=this.tiper=document.createElement('div');
			tiper.id='_paper_tip';
			tiper.style.position='absolute';
			document.getElementsByTagName('BODY')[0].appendChild(tiper)
		}
		tiper.style.display='block';
		tiper.innerHTML=JSON.evaluate(this.hashDataEle.value).title;
		this.tipMove(evt);
	},
	tipMove:function(evt){
		if(!this.tiper)return;
		var evt=window.event||evt;
		var evtPos=getEventPosition(evt);
		this.tiper.style.left=evtPos.x+10+"px"
		this.tiper.style.top=evtPos.y+"px"
	},
	tipOff:function(evt){
		$id('_paper_tip').style.display="none";
	}
}

/**** 拖拽类 ****/
var Drag=function(drag, options){
	this.init(drag, options)
}
Drag.prototype = {
  //拖放对象
  init: function(drag, options) {
	this.Drag = $id(drag);//拖放对象
	this._x = this._y = 0;//记录鼠标相对拖放对象的位置
	this._marginLeft = this._marginTop = 0;//记录margin
	var self=this;
	this._fM = function(evt){self.Move(evt)};
	this._fS = function(){self.Stop()};

	this.SetOptions(options);

	this.Limit = !!this.options.Limit;
	this.mxLeft = parseInt(this.options.mxLeft);
	this.mxRight = parseInt(this.options.mxRight);
	this.mxTop = parseInt(this.options.mxTop);
	this.mxBottom = parseInt(this.options.mxBottom);

	this.LockX = !!this.options.LockX;
	this.LockY = !!this.options.LockY;
	this.Lock = !!this.options.Lock;

	this.onStart = this.options.onStart;
	this.onMove = this.options.onMove;
	this.onStop = this.options.onStop;

	this._Handle = $id(this.options.Handle) || this.Drag;
	this._mxContainer = $id(this.options.mxContainer) || null;

	this.Drag.style.position = "absolute";
	//透明
	if(isIE && !!this.options.Transparent){
		//填充拖放对象
		with(this._Handle.appendChild(document.createElement("div")).style){
			width = height = "100%"; backgroundColor = "#fff"; filter = "alpha(opacity:0)"; fontSize = 0;
		}
	}
	//修正范围
	this.repair();
	this._Handle.attachEvent("onmousedown", function(evt){self.Start(evt)})
  },
  //设置默认属性
  SetOptions: function(options) {
	this.options = {//默认值
		Handle:			"",//设置触发对象（不设置则使用拖放对象）
		Limit:			false,//是否设置范围限制(为true时下面参数有用,可以是负数)
		mxLeft:			0,//左边限制
		mxRight:		9999,//右边限制
		mxTop:			0,//上边限制
		mxBottom:		9999,//下边限制
		mxContainer:	"",//指定限制在容器内
		LockX:			false,//是否锁定水平方向拖放
		LockY:			false,//是否锁定垂直方向拖放
		Lock:			false,//是否锁定
		Transparent:	false,//是否透明
		onStart:		function(){},//开始移动时执行
		onMove:			function(){},//移动时执行
		onStop:			function(){}//结束移动时执行
	};
	if(options)
	for (var property in options) {
		this.options[property] = options[property];
	}
  },
  //准备拖动
  Start: function(oEvent) {
	if(this.Lock){ return; }
	this.repair();
	//记录鼠标相对拖放对象的位置
	this._x = oEvent.clientX - this.Drag.offsetLeft;
	this._y = oEvent.clientY - this.Drag.offsetTop;
	//记录margin
	this._marginLeft = parseInt(this.Drag.currentStyle.marginLeft) || 0;
	this._marginTop = parseInt(this.Drag.currentStyle.marginTop) || 0;
	//mousemove时移动 mouseup时停止
	document.attachEvent("onmousemove", this._fM)
	document.attachEvent("onmouseup", this._fS)
	if(isIE){
		//焦点丢失
		this._Handle.attachEvent("onlosecapture",this._fS)
		//设置鼠标捕获
		this._Handle.setCapture();
	}else{
		//焦点丢失
		window.attachEvent("onblur", this._fS)
		//阻止默认动作
		oEvent.preventDefault();
	};
	//附加程序
	this.onStart();
  },
  //修正范围
  Repair: function() {
	if(this.Limit){
		//修正错误范围参数
		this.mxRight = Math.max(this.mxRight, this.mxLeft + this.Drag.offsetWidth);
		this.mxBottom = Math.max(this.mxBottom, this.mxTop + this.Drag.offsetHeight);
		//如果有容器必须设置position为relative或absolute来相对或绝对定位，并在获取offset之前设置
		!this._mxContainer || this._mxContainer.currentStyle.position == "relative" || this._mxContainer.currentStyle.position == "absolute" || (this._mxContainer.style.position = "relative");
	}
  },
  //拖动
  Move: function(oEvent) {
	//判断是否锁定
	if(this.Lock){ this.Stop(); return; };
	//清除选择
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//设置移动参数
	var iLeft = oEvent.clientX - this._x, iTop = oEvent.clientY - this._y;
	//设置范围限制
	if(this.Limit){
		//设置范围参数
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		//如果设置了容器，再修正范围参数
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		//修正移动参数
		iLeft = Math.max(Math.min(iLeft, mxRight - this.Drag.offsetWidth), mxLeft);
		iTop = Math.max(Math.min(iTop, mxBottom - this.Drag.offsetHeight), mxTop);
	}
	//设置位置，并修正margin
	if(!this.LockX){ this.Drag.style.left = iLeft - this._marginLeft + "px"; }
	if(!this.LockY){ this.Drag.style.top = iTop - this._marginTop + "px"; }
	//附加程序
	this.onMove();
  },
  //停止拖动
  Stop: function() {
	//移除事件
	document.detachEvent("onmousemove", this._fM);
	document.detachEvent("onmouseup", this._fS);
	if(isIE){
		//removeEventHandler(this._Handle, "losecapture", this._fS);
		this._Handle.detachEvent("onlosecapture", this._fS)
		this._Handle.releaseCapture();
	}else{
		//removeEventHandler(window, "blur", this._fS);
		window.detachEvent("onblur", this._fS)
	};
	//附加程序
	this.onStop();
  }
};