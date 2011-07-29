/* 定义DataRow对象 开始 */
function DataRow(dt,index){
	this.DT = dt;
	this.Index = index;

	DataRow.prototype.get2 = function(i){
		return this.DT.Values[this.Index][i];
	}

	DataRow.prototype.getColCount = function(){
		return this.DT.Columns.length;
	}

	DataTable.prototype.getColName = function(i){
		return this.DT.Columns[i];
	}

	DataRow.prototype.get = function(str){
		str = str.toLowerCase();
		var c = this.DT.ColMap[str];
		if(typeof(c)=="undefined"){
			return null;
		}
		return this.DT.Values[this.Index][c];
	}

	DataRow.prototype.set = function(str,value){
		str = str.toLowerCase();
		var c = this.DT.ColMap[str];
		if(typeof(c)=="undefined"){
			return;
		}
		this.DT.Values[this.Index][c] = value;
	}

	DataRow.prototype.set2 = function(i,value){
		this.DT.Values[this.Index][i] = value;
	}
}
/* 定义DataRow对象 结束 */