
function BtnBeautifier(){
	var ipts = document.getElementsByTagName("input");
	if(ipts.length == 0)
		return null;
	this.btns = [];
	this.re_btns = [];
	for(var i=0,j=ipts.length; i<j; i++){
		var _ipt = ipts[i];
		if(!_ipt.type)
			continue;
		if(_ipt.type == "button" 
			|| _ipt.type == "submit" 
			|| _ipt.type == "reset"){
			//if(_ipt.rendered!="true")		
				//this.btns.push(_ipt);
		}	
	}
	return this;
}

BtnBeautifier.prototype.init = function(){
	this.re_btns = [];
	if(!this.btns) return;
	for(var i=0,j=this.btns.length; i<j; i++){
		var oldBtn = this.btns[i];
        if(oldBtn.type=="reset"){
            //alert("222222222");
        var myform=oldBtn.form
    }

         //  alert(oldBtn.name+":::"+oldBtn.form);
        var newBtn = this.b2t(oldBtn);
		newBtn.onclick = oldBtn.onclick;
		newBtn.onmouseover = function(){
			this.className += " z-btn-over";
			//this.style.cursor = "hand";
		}
		newBtn.onmouseout = function(){
			this.className = "z-btn";
		}
        if(oldBtn.type=="reset"){
            //alert("222222222");
         newBtn.onclick= function(){
               
     myform.reset();
         }
    }

        this.replaceWith(oldBtn, newBtn);
		this.re_btns.push(newBtn);
	}
	return this.re_btns;
}

BtnBeautifier.prototype.replaceWith = function(_old,_new){
	if(!_old.parentNode)
		return;
	var p = _old.parentNode;
	try{
		p.insertBefore(_new, _old.nextSibling);
		p.removeChild(_old);
	} catch (e){
		alert(e);
	}
}

BtnBeautifier.prototype.b2t = function(_btn){
	var id = _btn.id || "gen"+Math.random();
	var icon = _btn.icon || 'icon-btncom';
	var val = _btn.value || 'Button';

	var _table = document.createElement("table");
	_table.setAttribute("cellSpacing","0");
	_table.setAttribute("cellPadding","0");
	_table.setAttribute("border","0");
	_table.className = "z-btn";
	_table.id = id;

	var _tbody = document.createElement("tbody");
	_table.appendChild(_tbody);

	var _tr = document.createElement("tr");
	_tbody.appendChild(_tr);

	//第一列，左括弧

	var _td1 = document.createElement("td");
	_td1.className = "z-btn-left";
	var _td1_i = document.createElement("i");
	_td1_i.innerHTML = "&nbsp;";
	_td1.appendChild(_td1_i);
	_tr.appendChild(_td1);

	//第二列，中间背景
	var _td2 = document.createElement("td");
	_td2.className = "z-btn-center";
	var _td2_em = document.createElement("em");
	_td2.appendChild(_td2_em);
	var _td2_btn = document.createElement("button");
	_td2_btn.className = "z-btn-text " + icon;
    //_td2_btn.typeName="reset";
    //_td2_btn.type = "reset";
    _td2_btn.appendChild(document.createTextNode(val))
    //_td2_btn.type="reset";
    _td2_em.appendChild(_td2_btn);
	_tr.appendChild(_td2);

	//第三列，右括弧

	var _td3 = document.createElement("td");
	_td3.className = "z-btn-right";
	var _td3_i = document.createElement("i");
	_td3_i.innerHTML = "&nbsp;";
	_td3.appendChild(_td3_i);
	_tr.appendChild(_td3);

	return _table;
}

window.onload = function(){
	var ss = (new BtnBeautifier());
	ss.init();
}