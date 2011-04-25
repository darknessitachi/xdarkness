/* 定义DataCollection对象 开始 */
function DataCollection(){
	this.map = {};
	this.valuetype = {};
	this.keys = [];

	DataCollection.prototype.get = function(ID){
		if(typeof(ID)=="number"){
			return this.map[this.keys[ID]];
		}
		return this.map[ID];
	}

	DataCollection.prototype.getKey = function(index){
		return this.keys[index];
	}

	DataCollection.prototype.size = function(){
		return this.keys.length;
	}

	DataCollection.prototype.remove = function(ID){
		if(typeof(ID)=="number"){
			if(ID<this.keys.length){
				var obj = this.map[this.keys[ID]];
				this.map[this.keys[ID]] = null;
				this.keys.splice(ID);
				return obj;
			}
		}else{
			for(var i=0;i<this.keys.length;i++){
				if(this.keys[i]==ID){
					var obj = this.map[ID];
					this.map[ID] = null;
					this.keys.splice(i);
					return obj;
					break;
				}
			}
		}
		return null;
	}

	DataCollection.prototype.toQueryString = function(){
		var arr = [];
		for(var i=0;i<this.keys.length;i++){
			if(this.map[this.keys[i]]==null||this.map[this.keys[i]]==""){continue;}
			if(i!=0){
				arr.push("&");
			}
			arr.push(this.keys[i]+"="+this.map[this.keys[i]]);
		}
		return arr.join('');
	}

	DataCollection.prototype.parseXML = function(xmlDoc){
		var coll = xmlDoc.documentElement;
		if(!coll){
			return false;
		}
		var nodes = coll.childNodes;
		var len = nodes.length;
		for(var i=0;i<len;i++){
			var node = nodes[i];
			var Type = node.getAttribute("Type");
			var ID = node.getAttribute("ID");
			this.valuetype[ID] = Type;
			if(Type=="String"){
				var v = node.firstChild.nodeValue;
				if(v==Constant.Null){
					v = null;
				}
				this.map[ID] = v;
			}else if(Type=="StringArray"){
				this.map[ID] = eval("["+node.firstChild.nodeValue+"]");
			}else if(Type=="Map"){
				this.map[ID] = eval("("+node.firstChild.nodeValue+")");
			}else if(Type=="DataTable"||Type=="Schema"||Type=="SchemaSet"){
				this.parseDataTable(node,"DataTable");
			}else{
				this.map[ID] = node.getAttribute("Value");
			}
			this.keys.push(ID);
		}
		return true;
	}

	DataCollection.prototype.parseDataTable = function(node,strType){
		var cols = node.childNodes[0].childNodes[0].nodeValue;
		cols = "var _TMP1 = "+cols+"";
		eval(cols);
		cols = _TMP1;
		var values = node.childNodes[1].childNodes[0].nodeValue;
		values = "var _TMP2 = "+values+"";
		eval(values);
		values = _TMP2;
		var obj;
		obj = new DataTable();
		obj.init(cols,values);
		this.add(node.getAttribute("ID"),obj);
	}

	DataCollection.prototype.toXML = function(){
		var arr = [];
		arr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		arr.push("<collection>");
		for(var i=0;i<this.keys.length;i++){
			var ID = this.keys[i];
			try{
				var v = this.map[ID];
				arr.push("<element ID=\""+ID+"\" Type=\""+this.valuetype[ID]+"\">");
				if(this.valuetype[ID]=="DataTable"){
					arr.push(v.toString());
				}else if(this.valuetype[ID]=="String"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+v+"]]>");
					}
				}else if(this.valuetype[ID]=="Map"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+JSON.toString(v)+"]]>");
					}
				}else{
					arr.push(v);
				}
				arr.push("</element>");
			}catch(ex){alert("DataCollection.toXML():"+ID+","+ex.message);}
		}
		arr.push("</collection>");
		return arr.join('');
	}

	DataCollection.prototype.add = function(ID,Value,Type){
		this.map[ID] = Value;
		this.keys.push(ID);
		if(Type){
			this.valuetype[ID] = Type;
		}else	if( Value && Value.getDataRow){//DataTable可能不是本页面中的
			this.valuetype[ID] = "DataTable";
		}else{
			this.valuetype[ID] = "String";
		}
	}

	DataCollection.prototype.addAll = function(dc){
		if(!dc){
			return;
		}
		if(!dc.valuetype){
			alert("DataCollection.addAll()要求参数必须是一个DataCollection!");
		}
		var size = dc.size();
		for(var i=0;i<size;i++){
			var k = dc.keys[i];
			var v = dc.map[k];
			var t = dc.valuetype[k];
			this.add(k,v,t);
		}
	}
}
/* 定义DataCollection对象 结束 */