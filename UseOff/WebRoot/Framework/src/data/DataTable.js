
function DataTable(){
	this.Columns = null;
	this.Values = null;
	this.Rows = null;
	this.ColMap = {};

	DataTable.prototype.getRowCount = function(){
		return this.Rows.length;
	}

	DataTable.prototype.getColCount = function(){
		return this.Columns.length;
	}

	DataTable.prototype.getColName = function(i){
		return this.Columns[i];
	}

	DataTable.prototype.get2 = function(i,j){
		return this.Rows[i].get2(j);
	}

	DataTable.prototype.get = function(i,str){
		return this.Rows[i].get(str);
	}

	DataTable.prototype.getDataRow = function(i){
		return this.Rows[i];
	}

	DataTable.prototype.deleteRow = function(i){
		this.Values.splice(i,1);
		this.Rows.splice(i,1);
		for(var k=i;k<this.Rows.length;k++){
			this.Rows[k].Index = k;
		}
	}

	DataTable.prototype.insertRow = function(i,values){
		this.Values.splice(i,0,values);
		this.Rows.splice(i,0,new DataRow(this,i));
		for(var k=i;k<this.Rows.length;k++){
			this.Rows[k].Index = k;
		}
	}
	
	DataTable.prototype.insertColumn = function(name){
		if(this.hasColumn(name)){
			return;
		}
		var col = {};
		col.Name = name.toLowerCase();
		col.Type = 1;//string
		this.Columns.push(col);
		this.ColMap[col.Name] = this.Columns.length-1;
		for(var i=0;i<this.Values.length;i++){
			this.Values[i].push(null);//置空值
		}
	}	
	
	DataTable.prototype.hasColumn = function(name){
		name = name.toLowerCase();
		for(var j=0;j<this.Columns.length;j++){
			if(this.Columns[j].Name==name){
				return true;
			}
		}
		return false;
	}

	DataTable.prototype.init = function(cols,values){
		this.Values = values;
		this.Columns = [];
		this.Rows = [];
		for(var i=0;i<cols.length;i++){
			var col = {};
			col.Name = cols[i][0].toLowerCase();
			col.Type = cols[i][1];
			this.Columns[i] = col;
			this.ColMap[col.Name] = i;
		}
		for(var i=0;i<values.length;i++){
			var row = new DataRow(this,i);
			this.Rows[i] = row;
		}
	}

	DataTable.prototype.toString = function(){
		var arr = [];
		arr.push("<columns><![CDATA[[");
		for(var i=0;i<this.Columns.length;i++){
			if(i!=0){
				arr.push(",");
			}
			arr.push("[");
			arr.push("\""+this.Columns[i].Name+"\",")
			arr.push(this.Columns[i].Type)
			arr.push("]");
		}
		arr.push("]]]></columns>");
		arr.push("<values><![CDATA[[");
		for(var i=0;i<this.Values.length;i++){
			if(i!=0){
				arr.push(",");
			}
			arr.push("[");
			for(var j=0;j<this.Columns.length;j++){
				if(j!=0){
					arr.push(",");
				}
				if(this.Values[i][j]==null||typeof(this.Values[i][j])=="undefined"){
					arr.push("\"_SKY_NULL\"");
				}else{
					var v = this.Values[i][j];
					if(typeof(v)=="string"){
						v = javaEncode(v);
					}
					arr.push("\""+v+"\"");
				}
			}
			arr.push("]");
		}
		arr.push("]]]></values>");
		return arr.join('');
	}
}