/* 为Gecko系列的浏览器添加IE的API 开始 */
if(Browser.isGecko){
	var p = HTMLElement.prototype;
	p.__defineSetter__("innerText",function(txt){this.textContent = txt;});
	p.__defineGetter__("innerText",function(){return this.textContent;});
	p.insertAdjacentElement = function(where,parsedNode){
		switch(where){
		  case "beforeBegin":
		    this.parentNode.insertBefore(parsedNode,this);
		    break;
		  case "afterBegin":
		    this.insertBefore(parsedNode,this.firstChild);
		    break;
		  case "beforeEnd":
		    this.appendChild(parsedNode);
		    break;
		  case "afterEnd":
		    if(this.nextSibling)
		      this.parentNode.insertBefore(parsedNode,this.nextSibling);
		    else
		      this.parentNode.appendChild(parsedNode);
		    break;
		  }
	};
	p.insertAdjacentHTML = function(where,htmlStr){
		var r=this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML=r.createContextualFragment(htmlStr);
		this.insertAdjacentElement(where,parsedHTML);
	};
	p.attachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.addEventListener(evtName,func,false);
	}
	p.detachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.removeEventListener(evtName,func,false);
	}
	window.attachEvent = p.attachEvent;
	window.detachEvent = p.detachEvent;
	document.attachEvent = p.attachEvent;
	document.detachEvent = p.detachEvent;
	p.__defineGetter__("currentStyle", function(){
		return this.ownerDocument.defaultView.getComputedStyle(this,null);
  });
	p.__defineGetter__("children",function(){
    var tmp=[];
    for(var i=0;i<this.childNodes.length;i++){
    	var n=this.childNodes[i];
      if(n.nodeType==1){
      	tmp.push(n);
      }
    }
    return tmp;
  });
	p.__defineSetter__("outerHTML",function(sHTML){
    var r=this.ownerDocument.createRange();
    r.setStartBefore(this);
    var df=r.createContextualFragment(sHTML);
    this.parentNode.replaceChild(df,this);
    return sHTML;
  });
  p.__defineGetter__("outerHTML",function(){
    var attr;
		var attrs=this.attributes;
		var str="<"+this.tagName.toLowerCase();
		for(var i=0;i<attrs.length;i++){
		    attr=attrs[i];
		    if(attr.specified){
		        str+=" "+attr.name+'="'+attr.value+'"';
		    }
		}
		if(!this.hasChildNodes){
		    return str+">";
		}
		return str+">"+this.innerHTML+"</"+this.tagName.toLowerCase()+">";
  });
	p.__defineGetter__("canHaveChildren",function(){
	  switch(this.tagName.toLowerCase()){
			case "area":
			case "base":
			case "basefont":
			case "col":
			case "frame":
			case "hr":
			case "img":
			case "br":
			case "input":
			case "isindex":
			case "link":
			case "meta":
			case "param":
			return false;
    }
    return true;
  });
  Event.prototype.__defineGetter__("srcElement",function(){
    var node=this.target;
    while(node&&node.nodeType!=1)node=node.parentNode;
    return node;
  });
  p.__defineGetter__("parentElement",function(){
		if(this.parentNode==this.ownerDocument){
			return null;
		}
		return this.parentNode;
	});
}else{
	try {
		document.documentElement.addBehavior("#default#userdata");
		document.execCommand('BackgroundImageCache', false, true);
	} catch(e) {alert(e)}
}
/* 为Gecko系列的浏览器添加IE的API 结束 */