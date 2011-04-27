
Afloat=function(id,pos){//漂浮元素使之固定在窗口底部或项部，pos值为字符串'top'或'bottom'，默认为'bottom'
	this.pos=pos||'bottom';
	this.dom=$(id);
	if(this.dom.tagName=='TD'){
		var wrap=document.createElement('div');
		var children=this.dom.children;
		children=toArray(children);
		children.each(function(el){wrap.appendChild(el);})
		this.dom.appendChild(wrap);
		this.dom=$(wrap);
	}
	this.init();
	var me=this;
	window.attachEvent("onscroll",function(){me.setPosition()})
	onWindowResize(function(){me.setPosition()})
}
Afloat.prototype={
	init:function(){
		this.dom.style.position="static";
		var domPosition=this.dom.getPosition();
		this.fixX=Math.round(domPosition.x);
		this.fixY=Math.round(domPosition.y);//当目标元素处于隐藏状态的该值可能为0
		this.fixWidth=this.dom.offsetWidth;//当目标元素处于隐藏状态的该值可能为0
		this._width=this.dom.style.width;
	    this.fixHeight=this.dom.offsetHeight;
		this.dom.style.left=this.fixX+'px';
	    this.viewportH=$E.getViewportDimensions().height;
	},
	setPosition:function(){
		 if(this.fixY==0||this.fixWidth==0){
			this.init();
		 }
		 var st = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
		 if((this.pos=='bottom'&&st+this.viewportH<this.fixY)
			||(this.pos=='top'&&st>this.fixY)
		 ){
			 if(isIE6){
				 this.dom.style.position="absolute";
				 this.dom.style.top = this.pos=='bottom'?
					st+this.viewportH-this.fixHeight+'px' : st+'px';
			 }else{
				 this.dom.style.position="fixed";
				 this.pos=='bottom'?this.dom.style.bottom='0':this.dom.style.top='0';
			 }
			this.dom.style.width=this.fixWidth+'px';
		 }else{
			this.dom.style.position="static";
			this.dom.style.width=this._width;
		 }
	 }
}
