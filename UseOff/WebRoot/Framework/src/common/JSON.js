/* 定义JSON对象 */
if(!JSON)	
var JSON =  {
	toString:function(O) {
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
		else if (typeof O == 'string') string.push('"'+O+'"');
		else if (typeof O == 'number' && isFinite(O)) string.push(O);
		return string.join(',');
	},evaluate:function(str) {
		return (typeof str=="string")?eval('(' + str + ')'):str;
	}
};

JSON.jsonToString = function(obj){
	
    switch(typeof(obj)){   
        case 'string':   
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';   
        case 'array':   
            return '[' + obj.map(JSON.jsonToString).join(',') + ']';   
        case 'object':   
             if(obj instanceof Array){   
                var strArr = [];   
                var len = obj.length;   
                for(var i=0; i<len; i++){   
                    strArr.push(JSON.jsonToString(obj[i]));   
                }   
                return '[' + strArr.join(',') + ']';   
            }else if(obj==null){   
                return 'null';   
            }else{   
                var string = [];   
                for (var property in obj) 
                	string.push(
                		JSON.jsonToString(property) + ':' 
			+ JSON.jsonToString(obj[property]));   
                return '{' + string.join(',') + '}';   
            }
        case 'number':   
            return obj;   
        case false:   
            return obj;   
    }
}
JSON.jsonToHTMLString = function(obj,level){
	level = level || 1;
	var blankString = "";
	for(var k=0;k<level;k++){
		blankString += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	
    switch(typeof(obj)){   
        case 'string':   
            return '"' + obj.replace(/(["\\])/g, '\\$1').replace(/\n/g,'<br/>'+blankString) + '"';   
        case 'array':   
            return '[' + obj.map(JSON.jsonToHTMLString).join(',') + ']';   
        case 'object':   
             if(obj instanceof Array){   
                var strArr = [];   
                var len = obj.length;   
                for(var i=0; i<len; i++){   
                    strArr.push(JSON.jsonToHTMLString(obj[i]));   
                }   
                return '[' + strArr.join(',') + ']';   
            }else if(obj==null){   
                return 'null';   
            }else{   
                var string = [];level++;
                for (var property in obj) {
                	string.push("<br/>" + blankString
                		+ JSON.jsonToHTMLString(property,level) + ':' 
						+ JSON.jsonToHTMLString(obj[property],level));
				}
				
                return '{' + string.join(',') + '}';   
            }
        case 'number':   
            return obj;   
        case false:   
            return obj;   
    }
}