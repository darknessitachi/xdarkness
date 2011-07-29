
function Button(_value, _icon) {
	var id = "gen"+Math.random();
	var icon = _icon || 'icon-btncom';
	var val = _value || 'Button';

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
	_table.onmouseover = function(){
		this.className += " z-btn-over";
		//this.style.cursor = "hand";
	}
	_table.onmouseout = function(){
		this.className = this.className.replace(" z-btn-over","");
	}
	
	_table.instence = _td2_btn;// instence指向真实的button
	
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
	
	_table.appendEvent = function(event, func) {
		addEvent(this.instence, event, func);
	}
	return _table;
}

