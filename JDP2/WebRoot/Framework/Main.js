


/**
 * @class Array 这些函数在每一个Array对象中可用.
 * 
 * @method push
 * @method pop
 * @method unshift
 * @method shift
 * @method join
 * @method reverse
 * @method sort
 * @method concat
 * @method slice
 * @method splice
 */

/**
 * @method append 添加元素到数组的末尾，对重复的元素进行可选的检查
 * @param {Array} srcObj 原数组
 * @param {Object} obj 需添加的对象
 * @param {Boolean} nodup 是否包含重复对象
 * @static
 */
Array.prototype.append=function(obj,nodup){
  if (!(nodup && this.contains(obj))){
    this[this.length]=obj;
  }
}

/**
 * @method clone 数组拷贝
 * @param {Array} srcObj 数组
 * @return {Array} 拷贝后的新数组
 * @static
 */
Array.prototype.clone = function(){
	var len = this.length;
	var r = [];
	for(var i=0;i<len;i++){
		if(typeof(this[i])=="undefined"||this[i]==null){
			r[i] = this[i];
			continue;
		}
		if(this[i].constructor==Array){
			r[i] = this[i].clone();
		}else{
			r[i] = this[i];
		}
	}
	return r;
}
/**
 * @method indexOf 返回元素在数组中的索引
 * @param {Array} obj 原数组
 * @param {Object} o 查找的元素
 * @return {Number} 元素在数组中的索引
 * @static
 */
Array.prototype.indexOf = function (o) {
	for (var i = 0, len = this.length; i < len; i = i + 1) {
		if (this[i] != null && typeof (this[i].equals) == "function" && this[i].equals(o)) {
			return i;
		}
		if (this[i] == o) {
			return i;
		}
	}
	return -1;
};

/**
 * @method contains 检测数组中是否包含该元素
 * @param {Array} srcObj 数组
 * @param {Object} obj 是否包含的元素
 * @return {Boolean} 是否包含该元素
 * @static
 */
Array.prototype.contains=function(obj){
  return (this.indexOf(obj)>=0);
}

/**
 * @method clear 清空数组
 * @param {Array} srcObj 数组
 * @static
 */
Array.prototype.clear=function(){
  this.length=0;
}

/**
 * @method insert 在指定位置插入新元素
 * @param {Array} srcObj 数组
 * @param {Number} index 索引
 * @param {Object} data 元素
 * @return {Array} 数组
 * @private
 */
Array.prototype.insert = function(index,data){
	if(isNaN(index) || index<0 || index>this.length) {
		this.push(data);
	}else{
		var temp = this.slice(index);
		this[index]=data;
		for (var i=0; i<temp.length; i++){
			this[index+1+i]=temp[i];
		}
	}
	return this;
}

/**
 * @method insertAt 在数组的指定位置插入一个元素
 * @param {Array} srcObj 数组
 * @param {Number} index 插入元素的位置
 * @param {Object} obj 元素
 * @static
 */
Array.prototype.insertAt=function(index,obj){
  this.splice(index,0,obj);
}

/**
 * @method removeAt 移除指定位置处的元素
 * @param {Array} srcObj 数组
 * @param {Number} index 移除元素的位置
 * @static
 */
Array.prototype.removeAt=function(index){
  this.splice(index,1);
}

/**
 * @method remove 移除指定元素
 * @param {Array} srcObj 数组
 * @param {Object} s 需移除的元素 
 * @param {Boolean} dust 是否返回移除的元素
 * @return {Array} 如果dust为true，返回移除的元素数组。否则，返回移除元素后的数组
 * @static
 */
Array.prototype.remove = function(s,dust){//if dust is ture锟斤拷return the element that has been deleted
	if(dust){
		var dustArr=[];
	  	for(var i=0;i<this.length;i++){
		if(s == this[i]){
			dustArr.push(this.splice(i, 1)[0]);
		}
	  }
	  return dustArr;
	}

	for(var i=0;i<this.length;i++){
		if(s == this[i]){
			this.splice(i, 1);
	    }
	}
	return this;
}


/**
 * @method each 对数组中的每个元素进行指定操作
 * @param {Array} srcObj 数组
 * @param {Function} func 操作函数
 * @static
 */
Array.prototype.each = function(func){
	var len = this.length;
	for(var i=0;i<len;i++){
		try{
			func(this[i],i);
		}catch(ex){
			alert(func);
			alert("Array.prototype.each:"+ex.message);
		    throw ex;
		}
	}
}

/**
 * @method isInstance 判断元素是否是数组
 * @param {Object} obj 元素
 * @return {Boolean} 是否是数组
 */
Array.isInstance = function (obj) {
	return Object.prototype.toString.call(obj) === "[object Array]";
};

/**
 * @method equals 检测两个数组是否相同
 * @param {Array} srcObj 原数组
 * @param {Array} _array 目标数组
 * @return {Boolean} 是否相同
 * @static
 */
Array.prototype.equals = function (_array) {
	if (this == _array) {
		return true;
	}
	if (!Array.isInstance(_array)) {
		return false;
	}
	if (this.length != _array.length) {
		return false;
	}
	for (var i = 0, len = this.length; i < len; i = i + 1) {
		var o1 = this[i];
		var o2 = _array[i];
		if (o1 != o2) {
			if (!(typeof (o1.equals) == "function" && o1.equals(o2))) {
				return false;
			}
		}
	}
	return true;
};

/**
 * @method containsAll 检测数组中是否包含目标数组中的所有元素
 * @param {Array} srcObj 数组
 * @param {Array} oArray 目标数组
 * @return {Boolean} 是否完全包含该数组
 * @static
 */
Array.prototype.containsAll = function (oArray) {
	if (this == oArray) {
		return true;
	}
	for (var i = 0; i < oArray.length; i = i + 1) {
		var o = oArray[i];
		if (!this.contains(o)) {
			return false;
		}
	}
	return true;
};

Array.prototype.add = function(arr){
	var instance = this;
	arr.each(function(obj){
		instance.append(obj);
	})
	return instance;
}

/**
 * @method filter 过滤器
 * var a = [-1,2,1,-3,4,-2];
 * var b = Array.filter(a, function(v) {
 * 		return v > 0 ? v : 0;
 * });
 * @param {Object} arr
 * @param {Object} filterRule
 */
Array.filter = function(arr, filterRule) {
	var ret = [];
	for(var i in arr) {
		ret.push(filterRule(arr[i]));
	}
	return ret;
};
/**     
* 全选的所有指定名称的checkbox    
*@state 全选的checkbox的状态    
*@name   表格中的所有checkbox的名称  
*@type void    
*/ 
function selectAll(state,name) {      
    var ids = document.getElementsByName(name);      
	for (var i = 0; i < ids.length; i++) {             
		ids[i].checked = state;      
    }      
}      

/**     
* 全选的所有指定id名称的同名checkbox    
*@state 全选的checkbox的状态    
*@name   表格中的所有checkbox的名称    
*@name   表格中的所有checkbox的id        
*@type void    
*/ 
function selectAllCheckboxByID(state,name,id) {      
    var ids = document.getElementsByName(name);      
	for (var i = 0; i < ids.length; i++) {             
		if(ids[i].id == id) {      
             ids[i].checked = state;      
         }      

    }      
}      

/**     
* 全选页面上所有的checkbox    
*@state 全选的checkbox的状态      
*@type void    
*/ 
function selectAlls(state) {
    var inputs = document.getElementsByTagName("input");
	for(var i =0;inputs.length;i++){
	if(inputs[i].type == "checkbox"){
            inputs[i].checked =state;       
        }
    }
}      

/**    
*得到鼠标所单击的行    
*@type Object    
*/ 
function GetRow(oElem) {      
	while (oElem) {      
		if (oElem.tagName.toLowerCase() == "tr" 
			&& oElem.parentElement.tagName.toLowerCase() == "tbody") {      
			return oElem;
        }
		if (oElem.tagName.toLowerCase() == "table"
			|| oElem.tagName.toLowerCase() == "th") {
			return false;
        }
        oElemoElem = oElem.parentElement;
    }
}

/**     
* 全选当前行的checkbox    
*@state 全选的checkbox的状态     
*@type void    
*/ 
function selectRowCheckbox(state){      
	var row = GetRow(window.event.srcElement);      
	var cells = row.childNodes;       
	for(var i=0;i<cells.length;i++){
		var cell = cells[i].childNodes[0];      
		if(cell.tagName == "INPUT"){      
			cell.checked = state;      
		}      
	}      
}

/**
*选中指定值的Radio
*如：有两个radio,
*第一个的name="ids",value="1"
*第二个的name="ids",value="２"
*调用方法selectRadio("ids","1");
*那么数值为１的Radio将被选中
*@name radio的名称
*@value radio的值
*@type void
*/
function selectRadio(name,value) {
    var radioObject = document.getElementsByName(name);
	if(value === ""){
        radioObject[0].checked = true;
		return;
    }
	for (var i = 0; i < radioObject.length; i++){
		if(radioObject[i].value == value){
            radioObject[i].checked = true;
			break;
        }
    }
}

/**
*选中指定值的checkbox
*如：有两个checkbox,
*第一个的name="ids",value="1"
*第二个的name="ids",value="２"
*第三个的name="ids",value="3"
*调用这个方法selectCheckbox("ids","1,2")那么数值为１，２的checkbox将被选中    
*
*@name 要选中的checkbox数组的名称
*@value 判断时候选中的值
*@type void
*/
function selectCheckbox(name,values) {
    var checkObject = document.getElementsByName(name);
    var valuevalues = values.split(",");
	for(var j = 0; j < values.length; j++){
		for (var i = 0; i < checkObject.length; i++){
			if(checkObject[i].value == values[j]){
                checkObject[i].checked = true;
				break;
            }
        }
    }
}

/**     
*选中指定值的select    
*如：有一个名称为user的select
*   
*调用这个方法selectOption("user","0")那么选项为0的选项就被选中    
*
*@name  String  select的名称
*@value String  判断时候选中的值
*@type void
*/
function selectOption(name,value){
    var options = document.getElementsByName(name)[0].options;
	for (var i = 0; i < options.length; i++){
		if(options[i].value === value){
            options[i].selected = true;
			break;
        }
    }
}

/*
 * Cookie class
 */
Cookie = {

	setCookie:function (key, value) {
		document.cookie = key + "=" + escape(value);
	}, getCookie:function (key) {
		
		/* 采用这种正则的方式更加简单
		var re = new RegExp("(?:; )?" + encodeURIComponent(key) + "=([^;]*);?");
		if(re.test(document.cookie)){
			return decodeURIComponent(RegExp["$1"]);
		}
		*/
		
		
		var cookies = " " + document.cookie;
		if (cookies) {
			var start = cookies.indexOf(" " + key + "=");
			if (start == -1) {
				return null;
			}
			var end = cookies.indexOf(";", start);
			if (end == -1) {
				end = cookies.length;
			}
			end -= start;
			var cookie = cookies.substr(start, end);
			return unescape(cookie.substr(cookie.indexOf("=") + 1, cookie.length - cookie.indexOf("=") + 1));
		} else {
			return null;
		}
	}
};
	
/* Cookie操作类，支持大于4K的Cookie 开始 */
var Cookie = {};

Cookie.Spliter = "_SKY_SPLITER_";

Cookie.get = function(name){
  var cs = document.cookie.split("; ");
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = arr[0].trim();
	  var v = arr[1]?arr[1].trim():"";
	  if(n==name){
	  	return decodeURI(v);
	  }
	}
	return null;
}

Cookie.getAll = function(){
  var cs = document.cookie.split("; ");
  var r = [];
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = arr[0].trim();
	  var v = arr[1]?arr[1].trim():"";
	  if(n.indexOf(Cookie.Spliter)>=0){
	  	continue;
	  }
	  if(v.indexOf("^"+Cookie.Spliter)==0){
	      var max = v.substring(Cookie.Spliter.length+1,v.indexOf("$"));
	      var vs = [v];
	      for(var j=1;j<max;j++){
	      	vs.push(Cookie.get(n+Cookie.Spliter+j));
	      }
	      v = vs.join('');
	      v = v.substring(v.indexOf("$")+1);
	   }
	   r.push([n,decodeURI(v)]);
	}
	return r;
}

Cookie.set = function(name, value, expires, path, domain, secure, isPart){
	if(!isPart){
		var value = encodeURI(value);
	}
	if(!name || !value){
		return false;
	}
	if(!path){
		path = Server.ContextPath.replace(/^\w+:\/\/[.\w]+:?\d*/g, '');/* 特别注意，此处是为了实现不管当前页面在哪个路径下，Cookie中同名名值对只有一份 */
	}
	if(expires!=null){
	  if(/^[0-9]+$/.test(expires)){
	    expires = new Date(new Date().getTime()+expires*1000).toGMTString();
		}else{
			var date = DateTime.parseDate(expires);
			if(date){
				expires = date.toGMTString();
			}else{
		  	expires = undefined;
		  }
		}
	}
	if(!isPart){
	  Cookie.remove(name, path, domain);
	}
	var cv = name+"="+value+";"
		+ ((expires) ? " expires="+expires+";" : "")
		+ ((path) ? "path="+path+";" : "")
		+ ((domain) ? "domain="+domain+";" : "")
		+ ((secure && secure != 0) ? "secure" : "");
  if(cv.length < 4096){
		document.cookie = cv;
	}else{
		var max = Math.ceil(value.length*1.0/3800);
		for(var i=0; i<max; i++){
			if(i==0){
				Cookie.set(name, '^'+Cookie.Spliter+'|'+max+'$'+value.substr(0,3800), expires, path, domain, secure, true);
			}else{
				Cookie.set(name+Cookie.Spliter+i, value.substr(i*3800,3800), expires, path, domain, secure, true);
			}
		}
	}
  return true;
}

Cookie.remove = function(name, path, domain){
	var v = Cookie.get(name);
  if(!name||v==null){
  	return false;
  }
  if(encodeURI(v).length > 3800){
		var max = Math.ceil(encodeURI(v).length*1.0/3800);
		for(i=1; i<max; i++){
			document.cookie = name+Cookie.Spliter+i+"=;"
				+ ((path)?"path="+path+";":"")
				+ ((domain)?"domain="+domain+";":"")
				+ "expires=Thu, 01-Jan-1970 00:00:01 GMT;";
		}
	}
	document.cookie = name+"=;"
		+ ((path)?"path="+path+";":"")
		+ ((domain)?"domain="+domain+";":"")
		+ "expires=Thu, 01-Jan-1970 00:00:01 GMT;";
	return true;
}
/* Cookie操作类，支持大于4K的Cookie 结束 */
/**  
 * 时间对象的格式化;  
 */  
Date.prototype.format = function(format) {   
    /*  
     * eg:format="YYYY-MM-dd hh:mm:ss";  
     */  
    var o = {   
        "M+" :this.getMonth() + 1, // month   
        "d+" :this.getDate(), // day   
        "h+" :this.getHours(), // hour   
        "m+" :this.getMinutes(), // minute   
        "s+" :this.getSeconds(), // second   
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter   
        "S" :this.getMilliseconds()   
    // millisecond   
    }   
  
    if (/(y+)/.test(format)) {   
        format = format.replace(RegExp.$1, (this.getFullYear() + "")   
                .substr(4 - RegExp.$1.length));   
    }   
  
    for ( var k in o) {   
        if (new RegExp("(" + k + ")").test(format)) {   
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]   
                    : ("00" + o[k]).substr(("" + o[k]).length));   
        }   
    }   
    return format;   
} 

/**
 * 程序员第定律：用同一种模式解决同一类问题
 */
/**
 * @method encodeURIComponent
 */
/**
 * @Class document
 * 
 * @property images
 * 
 * @method getElementById
 * @method getElementsByTagName
 * @mehtod createElement
 */

/**
 * @Class Math
 * @method floor
 * @method random
 * @method ceil
 */

/**
 * @Class Number
 */

/**
 * @class Arguments
 * @property callee 引用当前正在执行的函数，它提供了一种匿名的递归调用能力
 * 
 * 	如果一个函数是作为构造函数执行，那么满足 arguments.callee == this.constructor
 *  将参数转换为数组：Array.apply(this, arguments);
 * 	例：用必包计算10的阶乘
 * 		(function(x){
 * 			return x > 1 ? x * arguments.callee(x-1) : 1;
 * 		})(10);
 */

/**
 * @class Function
 * @property caller
 * @method call(Caller, args)
 * @method apply(Caller, Collection)
 */

/**
 * @class Object
 * 
 * @property constructor
 * 
 * @method hasOwnProperty
 * @method isPrototypeOf
 * @method propertyIsEmuerable
 * @method toLocaleString
 * @method toString
 * @method valueOf
 */ 
 
/**
 * @class Screen
 * 
 * @property width
 * @property height
 * @property availWidth
 * @property availHeight
 * @property colorDepth
 */

/**
 * @class Location
 * 
 * @property href
 * 
 * @method reload
 * @method replace
 */

/**
 * @class Element
 * 
 * @method setAttribute
 * @method removeAttribute
 */
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
/**
 * 根据参数将原值小数点后转化为指定长度
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param normailNumber  str  需要处理的数值
 *@param precision  int     小数点位数
 *
 *@return      str
 */
function getPrecision(normalNumber, precision) {
    var baseNumber = Math.pow(10, precision);
    normalNumber = Math.round(normalNumber * baseNumber);
    return normalNumber / baseNumber;
}

/**
 *获得指定格式(小数位数)的double的String类型
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str str 待处理的数值字符串
 *@param  fractionLength int 小数位数
 *@return   返回处理后的数值字符串
 */
function getFormatedDoubleStr(str, fractionLength)
{
    var numstrs = str.split(".");
    var fraction = "";
    if (numstrs.length == 2)
    {
        fraction = numstrs[1];   //小数部分
        if (fractionLength > 0)   //保留fractionLength位小数
        {
            if (fraction.length <= fractionLength)
            {
                var range = fractionLength - fraction.length;
                for (var i = 0; i < range; i ++)
                {
                    fraction = fraction + "0";
                }
                return numstrs[0] + "." + fraction;                       //补足少位返回 end
            }
            else               //小数部分超过需要长度，从末尾开始四舍五入
            {
                var tail = fraction.substring(fractionLength);  //超出部分 （位数大于0）
                var remain = fraction.substring(0, fractionLength);  //保留部分 (位数大于0)
                var toadd = getRoundNum(tail);   //对tail四舍五入的结果
                var originLength = remain.length;
                remain = new Number(remain) + toadd + "";
                var currLength = remain.length;
                if (currLength < originLength)
                {
                    for (var m = 0; m < originLength - currLength; m ++)
                        remain = "0" + remain;
                }
                else if (currLength > originLength)
                {
                    numstrs[0] = new Number(numstrs[0]) + 1 + "";
                    remain = remain.substring(1);
                }
                return numstrs[0] + "." + remain;
            }
        }
        else   //取零位小数
        {
            return new Number(numstrs[0]) + getRoundNum(numstrs[1]) + "";
        }
    }
    else         //小数位为fractionLength个０
    {
        if (fractionLength > 0)
        {
            for (var n = 0; n < fractionLength; n ++)
                fraction += "0";
            return numstrs[0] + "." + fraction;
        }
        else return numstrs[0];
    }
}

/**
 * 对目标字符串判断四舍五入返回0还是1
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str 待处理的字符串
 *
 *@return str
 */
function getRoundNum(str)
{
    var firstdigit = str.charAt(0);
    if (firstdigit > 4) return 1;
    else if (firstdigit < 4) return 0;
    else
    {
        for (var i = 1; i < str.length; i ++)
        {
            var current = str.charAt(i);
            if (current < 4) return 0;
            else if (current > 4) return 1;
        }
        return 0;
    }
}

/**
 * 过滤“123,234,34”这样的数值中的“,”
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param fnumStr 待过滤的字符串
 *@return 如果"123,234,34"，处理后，则返回“12323434”
 */
function filterCommaNumber(fnumStr) {
    if (fnumStr.charAt(fnumStr.length - 1) == ".") fnumStr = fnumStr.substring(0, fnumStr.length - 1);
    var normalNum = fnumStr;
    var firstcomma = normalNum.indexOf(",");
    while (firstcomma > 0) {
        normalNum = normalNum.substring(0, firstcomma) + normalNum.substring(firstcomma + 1);
        firstcomma = normalNum.indexOf(",");
    }
    return normalNum;
}

/**
 * 根据新输入的数字或小数点格式化num的格式
 * @param num
 */
function formatNum(num)
{
    var numStr = new String(num);
    if (numStr == null || numStr.length == 0) return "";
    var parCount = 0;
    var result = "";

    var dotIndex = numStr.indexOf(".");
    var integer = dotIndex >= 0 ? (dotIndex == 0 ? "0" : filterCommaNumber(numStr.substring(0, dotIndex))) : filterCommaNumber(numStr);
    var fraction = dotIndex >= 0 ? filterCommaNumber(numStr.substring(dotIndex).replace(".", "")).replace(".", "") : "";//考虑集中情况
    for (var i = integer.length - 1; i >= 0; i--)
    {
        var figure = integer.charAt(i);
        var code = integer.charCodeAt(i);
        if (code >= 48 && code <= 57)
        {
            if (parCount == 3)
            {
                result = figure + "," + result;
                parCount = 0;
            }
            else result = figure + result;
            parCount++;
        }
    }

    if (fraction.length == 0 && numStr.charAt(numStr.length - 1) == ".") return result + ".";
    else return result + (fraction.length > 0 ? "." + fraction : "");
}

function Money(){
	var ipts = document.getElementsByTagName("input");
	if(ipts.length == 0)
		return null;
	
	this.moneyInputs = [];

	for(var i=0,j=ipts.length; i<j; i++){
		var _ipt = ipts[i];
		if(_ipt.xtype == "money")
			this.moneyInputs.push(_ipt);
	}
	return this;
}

Money.prototype = {
	init: function(){
		var moneyInputs = this.moneyInputs;
		if(moneyInputs) {
			for(var i=0,j=moneyInputs.length;i<j;i++){
				addEvent(moneyInputs[i],"blur",function(){
					if(/(^\d+$)|(^\d+\.\d+$)/.test(this.value)){
						this.value = Money.formatNum(Math.getPrecision(this.value,2));
					}
				});
				addEvent(moneyInputs[i],"focus",function(){
					//if(/(^\d+$)|(^\d+\.\d+$)/.test(this.value)){
						this.value = Money.filterCommaNumber(this.value);
					//}
				});
			}
		}
	}
}

//domReady(function(){
//	new Money().init();
//});

/**
 * 根据新输入的数字或小数点格式化num的格式
 * @param num
 */
Money.formatNum = function (num){
    var numStr = new String(num);
    if (numStr == null || numStr.length == 0) return "";
    var parCount = 0;
    var result = "";

    var dotIndex = numStr.indexOf(".");
    var integer = dotIndex >= 0 ? (dotIndex == 0 ? "0" : Money.filterCommaNumber(numStr.substring(0, dotIndex))) : Money.filterCommaNumber(numStr);
    var fraction = dotIndex >= 0 ? Money.filterCommaNumber(numStr.substring(dotIndex).replace(".", "")).replace(".", "") : "";//考虑集中情况
    for (var i = integer.length - 1; i >= 0; i--){
        var figure = integer.charAt(i);
        var code = integer.charCodeAt(i);
        if (code >= 48 && code <= 57){
            if (parCount == 3){
                result = figure + "," + result;
                parCount = 0;
            }
            else result = figure + result;
            parCount++;
        }
    }

    if (fraction.length == 0 && numStr.charAt(numStr.length - 1) == ".") 
    	return result + ".";
    else 
    	return result + (fraction.length > 0 ? "." + fraction : "");
}
	
/**
 * 过滤“123,234,34”这样的数值中的“,”
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param fnumStr 待过滤的字符串
 *@return 如果"123,234,34"，处理后，则返回“12323434”
 */
Money.filterCommaNumber = function (fnumStr) {
    if (fnumStr.charAt(fnumStr.length - 1) == ".") fnumStr = fnumStr.substring(0, fnumStr.length - 1);
    var normalNum = fnumStr;
    var firstcomma = normalNum.indexOf(",");
    while (firstcomma > 0) {
        normalNum = normalNum.substring(0, firstcomma) + normalNum.substring(firstcomma + 1);
        firstcomma = normalNum.indexOf(",");
    }
    return normalNum;
}

/**
 * 根据参数将原值小数点后转化为指定长度
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param normailNumber  str  需要处理的数值
 *@param precision  int     小数点位数
 *
 *@return      str
 */
Math.getPrecision = function(normalNumber, precision) {
	var baseNumber = Math.pow(10, precision);
	normalNumber = Math.round(normalNumber * baseNumber);
	return normalNumber / baseNumber;
}

/**
 *获得指定格式(小数位数)的double的String类型
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str str 待处理的数值字符串
 *@param  fractionLength int 小数位数
 *@return   返回处理后的数值字符串
 */
function getFormatedDoubleStr(str, fractionLength){
    var numstrs = str.split(".");
    var fraction = "";
    if (numstrs.length == 2) {
        fraction = numstrs[1];   //小数部分
        if (fractionLength > 0){   //保留fractionLength位小数
            if (fraction.length <= fractionLength){
                var range = fractionLength - fraction.length;
                for (var i = 0; i < range; i ++){
                    fraction = fraction + "0";
                }
                return numstrs[0] + "." + fraction; //补足少位返回 end
            }else{//小数部分超过需要长度，从末尾开始四舍五入
                var tail = fraction.substring(fractionLength);  //超出部分 （位数大于0）
                var remain = fraction.substring(0, fractionLength);  //保留部分 (位数大于0)
                var toadd = getRoundNum(tail);   //对tail四舍五入的结果
                var originLength = remain.length;
                remain = new Number(remain) + toadd + "";
                var currLength = remain.length;
                if (currLength < originLength){
                    for (var m = 0; m < originLength - currLength; m ++)
                        remain = "0" + remain;
                }else if (currLength > originLength){
                    numstrs[0] = new Number(numstrs[0]) + 1 + "";
                    remain = remain.substring(1);
                }
                return numstrs[0] + "." + remain;
            }
        }else{  //取零位小数
            return new Number(numstrs[0]) + getRoundNum(numstrs[1]) + "";
        }
    }else{         //小数位为fractionLength个０
        if (fractionLength > 0) {
            for (var n = 0; n < fractionLength; n ++)
                fraction += "0";
            return numstrs[0] + "." + fraction;
        }
        else return numstrs[0];
    }
}

/**
 * 对目标字符串判断四舍五入返回0还是1
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str 待处理的字符串
 *
 *@return str
 */
function getRoundNum(str){
    var firstdigit = str.charAt(0);
    if (firstdigit > 4) return 1;
    else if (firstdigit < 4) return 0;
    else{
        for (var i = 1; i < str.length; i ++){
            var current = str.charAt(i);
            if (current < 4) return 0;
            else if (current > 4) return 1;
        }
        return 0;
    }
}


Date.isInstance = function(obj){
    return (Object.prototype.toString.call(obj) === "[object Date]");
};
Function.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Function]";
};
Number.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Number]";
};
Boolean.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Boolean]";
};


Function.prototype.bind = function(object) {
  var __method = this;
  __method.binded = true;
  
  var args = [];
  for(var i=0; i<arguments.length; i++){
	  args.push(arguments[i]);
  }
  
  return function() {
		if(arguments && arguments.length>0) {
			args = arguments;
		}
		__method.apply(object, args);
  }
}

Function.prototype.bindAsEventListener = function(object){
    var __method = this;
    
    var args = [];
	for(var i=0; i<arguments.length; i++){
	  args.push(arguments[i]);
	}
    return function(event){
    	if(arguments && arguments.length>0) {
			args = arguments;
		}
    	args.insertAt(0,event || window.event);
        __method.apply(object, args);
    }
};
/*
1判断select选项中 是否存在Value="paraValue"的Item 
2向select选项中 加入一个Item 
3从select选项中 删除一个Item 
4删除select中选中的项 
5修改select选项中 value="paraValue"的text为"paraText" 
6设置select中text="paraText"的第一个Item为选中 
7设置select中value="paraValue"的Item为选中 
8得到select的当前选中项的value 
9得到select的当前选中项的text 
10得到select的当前选中项的Index 
11清空select的项 
*/

/*判断select选项中 是否存在Value="paraValue"的Item */       
function selectIsExitItem(objSelect, objItemValue) {           
    for (var i = 0; i < objSelect.options.length; i++) {        
        if (objSelect.options[i].value == objItemValue) {        
            return true;        
        }        
    }        
    return false;        
}         
   
/*向select选项中 加入一个Item */       
function addItemToSelect(objSelect, objItemText, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
    	alert("该Item的Value值已存在");
    } else {        
        var varItem = new Option(objItemText, objItemValue);      
        objSelect.options.add(varItem);
    }        
}        
   
/*从select选项中 删除一个Item */
function removeItemFromSelect(objSelect, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options.remove(i);        
                break;        
            }        
        }        
    }        
}    
   
   
/*删除select中选中的项 */ 
function removeSelectedItemFromSelect(objSelect) {        
    var length = objSelect.options.length - 1;    
    for(var i = length; i >= 0; i--){
        if(objSelect[i].selected == true){    
            objSelect.options[i] = null;    
        }    
    }    
}      
   
/*修改select选项中 value="paraValue"的text为"paraText"*/        
function updateItemToSelect(objSelect, objItemText, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options[i].text = objItemText;        
                break;        
            }        
        }
    }        
}        
   
/*设置select中text="paraText"的第一个Item为选中*/        
function selectItemByValue(objSelect, objItemText) {
    for (var i = 0; i < objSelect.options.length; i++) {
        if (objSelect.options[i].text == objItemText) {
            objSelect.options[i].selected = true;
            isExit = true;
            break;
        }        
    }      
}

/*清空select的项*/
function clear(objSelect){    
	objSelect.options.length = 0;   
}

/*得到select的当前选中项的Index*/
function selectedIndex(objSelect){
	return objSelect.selectedIndex;
}
   
/*设置select中value="paraValue"的Item为选中*/
function selectValue(objSelect,value){
	objSelect.value = value;
}
       
/*得到select的当前选中项的value*/
function selectedValue(objSelect){
	return objSelect.value;
}
       
// 9.得到select的当前选中项的text 
function selectedText(objSelect){
	return objSelect.options[objSelect.selectedIndex].text;
}
String.prototype.firstToUpperCase = function() {
	return this.replace(/^([a-z])/,function(s,lowerStr){
		return lowerStr.toUpperCase();
	});
}
String.prototype.startsWith = String.prototype.startWith = function(str) {
  return this.indexOf(str) == 0;
}
String.prototype.endsWith = String.prototype.endWith = function(str) {
	var i = this.lastIndexOf(str);
  return i>=0 && this.lastIndexOf(str) == this.length-str.length;
}

/**
 * 
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
String.prototype.trim = function(){
    return this.replace(/^(\u00A0*\s*\u00A0*\s*)\u00A0*|\s*$/g,"");
}

String.prototype.leftPad = function(c,count){
	if(!isNaN(count)){
		var a = "";
		for(var i=this.length;i<count;i++){
			a = a.concat(c);
		}
		a = a.concat(this);
		return a;
	}
	return null;
}
String.prototype.rightPad = function(c,count){
	if(!isNaN(count)){
		var a = this;
		for(var i=this.length;i<count;i++){
			a = a.concat(c);
		}
		return a;
	}
	return null;
}
String.isInstance = function (_string) {
	return (typeof (_string) === "string");
}
String.isEmpty = function (str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
}
String.isNotEmpty = function (str) {
	return (!String.isEmpty(str));
}
String.prototype.getAttribute = function (name) {
	var reg = new RegExp("(^|;|\\s)" + name + "\\s*:\\s*([^;]*)(\\s|;|$)", "i");
	if (reg.test(this)) {
		return RegExp.$2.replace(/[\x0f]/g, ";");
	}
	return null;
}

String.prototype.cnLength = function () {
	//return ((this.replace(/[^x00-xFF]/g, "**")).length);
	return ((this.replace(/[\u4e00-\u9fa5]/g, "**")).length);
}

/*
String.prototype.len = function(){
    return this.replace(/[^\x00-\xff]/g,"aa").length;
}*/

String.prototype.toJSON = function(){   
        return eval('(' + this + ')');   
}

var StringFormat = function(str, params) {

	this.formatString = str;
	this.params = params;
}

StringFormat.prototype.toString = function() {
	var instance = this;
	return this.formatString.replace(/\{([\w-]+)\}/g , function(m, name, format, args){
		return instance.params[name];
	});
}

String.prototype.isSpecialCharacter = function () {
    var rexStr = /\<|\>|\"|\'|\&|\~|\!|\@|\#|\$|\%|\^|\*/g;
    var pattern = new RegExp(rexStr);
    return pattern.test(this);
}

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

Constant = {
	Null: "_SKY_NULL"
};
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
	}catch(ex){debugger;
		alert("Sky.attachMethod:"+ele)//有些对象不能附加属性，如flash
	}
	
	ele["attachedMethod"] = true;
}
/**
 * @method get 获取DOM元素对象
 * @param {Object} ele DOM元素的id或者DOM元素
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
   * @param {Object} ele
   */
	/*
  if(ele.xtype && !ele.attachedMethod) {
	  var types = Sky.getByType(ele.xtype)
	  types.each(function(ele){
		  Sky.attachMethod(ele);
	  });
	  /**
	   * 添加clear方法，用来清除所有元素
	   * @memberOf {TypeName} 
	   
	  types.clear = function(){
		  this.each(function(ele){
			  ele.clear();
		  });
	  }
	  return types;
  }*/
  
  if(ele){
  	Sky.attachMethod(ele);
  }
  return ele;
}

/**
*/
Sky.goForward = function() {
	window.history.forward(1);
}

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

function encodeNameAndValue(name, value){
	return encodeURIComponent(name) + "=" + encodeURIComponent(value);
}

function getRequestBody(form) {
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
 * @param {Object} ele DOM元素的id或者DOM元素
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
 * @return {TypeName} 
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

/*
	返回数组
*/
$N = function (ele){
    if (typeof(ele) == 'string'){
      ele = document.getElementsByName(ele)
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
//多选框全选
function selectAll(ele,eles){
	var flag = $V(ele);
	var arr = $N(eles);
	if(arr){
		for(var i=0;i< arr.length;i++){
			arr[i].checked = flag;
	  }
	}
}


function javaEncode(txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\", "\\\\").replace("\r\n", "\n").replace("\n", "\\n").replace("\"", "\\\"").replace("\'", "\\\'");
}

function javaDecode(txt) {
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


function toArray(a,i,j) {//把可枚举的集合转换为数组 
	 if(isIE){
		 var res = [];
		 for (var x = 0,
		 len = a.length; x < len; x++) {
			 res.push(a[x])
		 }
		 return res.slice(i || 0, j || res.length)
	 }else{
		 return Array.prototype.slice.call(a, i || 0, j || a.length)
	 }
}

/**
 * 
 * Attachment of event handlers in a cross-browser way
 * Enabling event capturing
 * Providing access to a global Event object
 * Providing access to the element on which the event fired
 * Providing access to the element on which the event was handled
 * Preventing the Internet Explorer memory leak
 */
Sky.EventManager = {
	handlerGuid: 1,// a counter used to create unique IDs
	elementGuid: 1,// Increment our unique id to keep it unique
	_elements: [],
	addEvent: function (element, type, handler, context) {
		
		// Check if the handler already has a unique identifier or not
		// assign each event handler a unique ID
		if (!handler.$$guid) handler.$$guid = this.handlerGuid++;
		
		if (!element.$$guid){
			element.$$guid = this.elementGuid++;
			// Add element to private elements array
			this._elements[element.$$guid] = element;
		}
	  
		// create a hash table of event types for the element
		if (!element.events) element.events = {};
		// create a hash table of event handlers for each element/event pair
		var handlers = element.events[type];
		if (!handlers) {
			handlers = element.events[type] = {};
			// store the existing event handler (if there is one)
			if (element["on" + type]) {
				handlers[0] = {
					"handler": element["on" + type]
				};
			}
		}
		// store the event handler in the hash table
		// Add the handler to the list, track handler _and_ context
		handlers[handler.$$guid] = {
		    "handler": handler, 
		    "context": context
		};
		
		// assign a global event handler to do all the work
		element["on" + type] = Sky.EventManager.handleEvent;
	},
	
	removeEvent: function (element, type, handler) {
		// delete the event handler from the hash table
		if (element.events && element.events[type]) {
			delete element.events[type][handler.$$guid];
		}
	},
	
	handleEvent: function (event) {
		var returnValue = true;
		// grab the event object (IE uses a global event object)
		event = event || Sky.EventManager.fixEvent(window.event);
		// get a reference to the hash table of event handlers
		var handlers = this.events[event.type];
		// execute each event handler
		for (var i in handlers) {
			var handler = handlers[i];
			var result = "";
			if (typeof handler.context == "object")
		      // Call handler in context of JavaScript object
		      result = handler.handler.call(handler.context, event, this);
		    else
		      // Call handler in context of element on which event was fired
		      result = handler.handler.call(this, event, this);
			
			if (result === false) {
				returnValue = false;
			}
		}
		return returnValue;
	},
	
	fixEvent: function (event) {
		// add W3C standard event methods
		event.preventDefault = Sky.EventManager.preventDefault;
		event.stopPropagation = Sky.EventManager.stopPropagation;
		return event;
	},
	
	preventDefault: function() {
		this.returnValue = false;
	},
	
	stopPropagation: function() {
		this.cancelBubble = true;
	},
	
	attachAfter: function(context, type, newContext, newType){
	  var fFunc = context[type] || function() {};
	  context[type] = function() {
	    fFunc.apply(context || this, arguments);
	    newContext[newType].apply(newContext, arguments);
	  }
	},
	
	detachAll: function(){
	
	}
};

// entAjax.attachAfter(window, "onunload", entAjax.EventManager, "detachAll");



var isIE = navigator.userAgent.toLowerCase().indexOf("msie") != -1;
var isIE8 = !!window.XDomainRequest&&!!document.documentMode;
var isIE7 = navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1 && !isIE8;
var isIE6 = navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1;
var isGecko = navigator.userAgent.toLowerCase().indexOf("gecko") != -1;
var isOpera = navigator.userAgent.toLowerCase().indexOf("opera") != -1;
var isQuirks = document.compatMode == "BackCompat";
var isStrict = document.compatMode == "CSS1Compat";
var isBorderBox = isIE && isQuirks;
/**/

/**
 * @class Browser 判断浏览器类型和版本，可以通过alert(Browser.ie)判断是否是ie浏览器，alert(Browser.version)判断浏览器的版本
 */
window["Browser"] = {};

(function () {
	if (Browser.platform) {
		return;
	}
	var ua = window.navigator.userAgent.toLowerCase();

	Browser.platform = window.navigator.platform;
	
	/**
	 * @property {Boolean} isFirefox 是否是Forefox浏览器
	 */
	Browser.isFirefox = ua.indexOf("Firefox") > 0;

	/**
	 * @property {Boolean} isOpera 是否是Opera浏览器
	 */
	Browser.isOpera = typeof (window.opera) == "object";
	
	/**
	 * @property {Boolean} isIE 是否是isIE浏览器
	 */
	Browser.isIE = ua.indexOf("msie") != -1;
	
	/**
	 * @property {Boolean} isIE8 是否是IE8浏览器
	 */
	Browser.isIE8 = !!window.XDomainRequest&&!!document.documentMode;
	
	/**
	 * @property {Boolean} isIE7 是否是IE7浏览器
	 */
	Browser.isIE7 = ua.indexOf("msie 7.0") != -1 && !Browser.isIE8;
	
	/**
	 * @property {Boolean} isIE6 是否是IE6浏览器
	 */
	Browser.isIE6 = ua.indexOf("msie 6.0") != -1;
	
	/**
	 * @property {Boolean} isMozilla 是否是Mozilla浏览器
	 */
	Browser.isMozilla = window.navigator.product == "Gecko";
	
	/**
	 * @property {Boolean} isNetscape 是否是Netscape浏览器
	 */
	Browser.isNetscape = window.navigator.vendor == "Netscape";
	
	/**
	 * @property {Boolean} isSafari 是否是Safari浏览器
	 */
	Browser.isSafari = ua.indexOf("Safari") > -1;
	
	/**
	 * @property {Boolean} isGecko 是否是Gecko浏览器
	 */
	Browser.isGecko = ua.indexOf("gecko") != -1;
	
	/**
	 * @property {Boolean} isQuirks 是否是Quirks浏览器
	 */
	Browser.isQuirks = document.compatMode == "BackCompat";
	
	/**
	 * @property {Boolean} isBorderBox 是否是BorderBox模型
	 */
	Browser.isBorderBox = Browser.isIE && Browser.isQuirks;

	if (Browser.isFirefox) {
		var re = /Firefox(\s|\/)(\d+(\.\d+)?)/;
	} else {
		if (Browser.isIE) {
			var re = /MSIE( )(\d+(\.\d+)?)/;
		} else {
			if (Browser.isOpera) {
				var re = /Opera(\s|\/)(\d+(\.\d+)?)/;
			} else {
				if (Browser.isNetscape) {
					var re = /Netscape(\s|\/)(\d+(\.\d+)?)/;
				} else {
					if (Browser.isSafari) {
						var re = /Version(\/)(\d+(\.\d+)?)/;
					} else {
						if (Browser.isMozilla) {
							var re = /rv(\:)(\d+(\.\d+)?)/;
						}
					}
				}
			}
		}
	}
	if ("undefined" != typeof (re) && re.test(ua)) {
		/**
		 * @property {Float} version 浏览器的版本
		 */
		Browser.version = parseFloat(RegExp.$2);
	}
})();
function domReady( f ) {
    // If the DOM is already loaded, execute the function right away
    if ( domReady.done ) return f();

    // If we’ve already added a function
    if ( domReady.timer ) {
        // Add it to the list of functions to execute
        domReady.ready.push( f  );
    } else {
        // Attach an event for when the page finishes  loading,
        // just in case it finishes first. Uses addEvent.
        Sky.EventManager.addEvent( window, "load", isDOMReady );

        // Initialize the array of functions to execute
        domReady.ready = [ f ];

        //  Check to see if the DOM is ready as quickly as possible
        domReady.timer = setInterval( isDOMReady, 13 );
    }
}

// Checks to see if the DOM is ready for navigation
function isDOMReady() {
	// 防止13毫秒内正在检测isDOMReady，下面代码还在执行中
	// 代码 domReady.done = true;还未执行
	// 此时domReady.done的真实值还是为false
	// if ( domReady.done===true ) return false;判断失败
	// 代码会继续执行，由此引发一次domReady中的函数会被调用多次的bug
	if ( domReady.onChecking===true ) return false;
	
	// Now, isDOMReady is on checking
	// and for sure no second isDOMReady excute in 13 ms
	domReady.onChecking = true;

    // If we already figured out that the page is ready, ignore
    if ( domReady.done===true ) return false;

    // Check to see if a number of functions and elements are
    // able to be accessed
    
    if ( document && document.getElementsByTagName && 
          document.getElementById && document.body 
        // && (!Browser.isIE || (document.documentElement && document.documentElement.doScroll && document.documentElement.doScroll('left')))  
         ) {/*在IE下用能否执行doScroll判断dom是否加载完毕 && document.documentElement.doScroll('left')*/
		
        // If they’re ready, we can stop checking
        clearInterval( domReady.timer );
        domReady.timer = null;

        // Execute all the functions that were waiting
        for ( var i = 0; i < domReady.ready.length; i++ ) {
            domReady.ready[i]();
        }
		
        // Remember that we’re now done
        domReady.ready = null;
        domReady.done = true;
    }
    domReady.onChecking = false;
}
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
/* 定义Page对象 开始 */
/**
 * @class Page 页面处理相关
 */
var Page = {};

Page.wait = function(){//通过在当前页面加入透明层的方式阻止页面继续响应事件，主要用于防止用户两次点击按钮
	var bgdiv = $("_WaitBGDiv");
	if(!bgdiv){
		var bgdiv = document.createElement("div");
		bgdiv.id = "_WaitBGDiv";
		$E.hide(bgdiv);
	 	$T("body")[0].appendChild(bgdiv);
		bgdiv.style.cssText = "background-color:#333;position:absolute;left:0px;top:0px;opacity:0.03;filter:alpha(opacity=3);width:100%;height:100%;z-index:991";
	}
	var mh = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
	bgdiv.style.height = mh+"px";
	$E.show(bgdiv);
}

Page.endWait = function(){
	var bgdiv = $("_WaitBGDiv");
	if(bgdiv){
		$E.hide(bgdiv);
	}
}

Page.clickFunctions = [];
Page.click = function(event){
	for(var i=0;i<Page.clickFunctions.length;i++){
		Page.clickFunctions[i](event);
	}
	if(window!=window.parent&&window.parent.Page){
		window.parent.Page.click();
	}
}
Page.onClick = function(f){
	Page.clickFunctions.push(f);
}

Page._Sort = function(a1,a2){
	var i1 = a1[1];
	var i2 = a2[1];
	if(typeof(i1)=="number"){
		if(typeof(i2)=="number"){
			if(i1>i2){
				return 1;
			}else if(i1==i2){
				return 0;
			}else{
				return -1;
			}
		}
		return -1;
	}else{
		if(typeof(i2)=="number"){
			return 1;
		}else{
			return 0;
		}
	}
}

Page.loadFunctions = [];
Page.load = function(){
	//var t = new Date().getTime();
	/*
	if(!Page.isReadyExecuted){//要保证ready,load先后顺序
		Page.isLoadWaiting = true;
		return;
	}
	*/
	if(window._OnLoad){//Select控件会用到
		try{window._OnLoad();}catch(ex){}
	}
	if(window.frameElement&&window.frameElement._OnLoad){//Select控件会用到
		try{window.frameElement._OnLoad();}catch(ex){alert(ex.message)}
	}
	Page.loadFunctions.sort(Page._Sort);
	for(var i=0;i<Page.loadFunctions.length;i++){
		try{
			Page.loadFunctions[i][0]();
		}catch(ex){
		}
	}
	//t = new Date().getTime()-t;
	//Console.log(window.location+"初始化耗时: "+t+" 秒.");
}

/**
 * @method onLoad DOM加载完成后处理动作
 * @param {Function} f 处理函数 
 * @param {Number} index 执行的优先级，数字越大，优先级越高
 */
Page.onLoad = function(f,index){
	index = index || 1;
	Page.loadFunctions.push([f,index]);
}
Page.readyFunctions = [];
Page.ready=function(){
	if(window._OnReady){
		try{window._OnReady();}catch(ex){}
	}
	Page.readyFunctions.sort(Page._Sort);
	for(var i=0;i<Page.readyFunctions.length;i++){
		try{Page.readyFunctions[i][0]();}catch(ex){}
	}
	/*
	Page.isReadyExecuted = true;
	if(Page.isLoadWaiting){
		Page.load();
	}
	*/
};
Page.onReady= function(f,index){
	Page.readyFunctions.push([f,index]);
};

Page.mouseDownFunctions = [];
Page.mousedown = function(event){
	for(var i=0;i<Page.mouseDownFunctions.length;i++){
		Page.mouseDownFunctions[i](event);
	}
}

Page.onMouseDown = function(f){
	Page.mouseDownFunctions.push(f);
}

Page.mouseUpFunctions = [];
Page.mouseup = function(event){
	for(var i=0;i<Page.mouseUpFunctions.length;i++){
		Page.mouseUpFunctions[i](event);
	}
}

Page.onMouseUp = function(f){
	Page.mouseUpFunctions.push(f);
}

Page.mouseMoveFunctions = [];
Page.mousemove = function(event){
	for(var i=0;i<Page.mouseMoveFunctions.length;i++){
		Page.mouseMoveFunctions[i](event);
	}
}

Page.onMouseMove = function(f){
	Page.mouseMoveFunctions.push(f);
}

Page.keyDownFunctions = [];
Page.keydown = function(event){
	for(var i=0;i<Page.keyDownFunctions.length;i++){
		Page.keyDownFunctions[i](event);
	}
}

Page.onKeyDown = function(f){
	Page.keyDownFunctions.push(f);
}
/* 定义Page对象 结束 */



/* 监听document对象上的click、mousedown、load、mouseup、mousemove事件 */
if(document.attachEvent){
	document.attachEvent('onclick',Page.click);
	document.attachEvent('onmousedown',Page.mousedown);
	window.attachEvent('onload',Page.load);
	document.attachEvent('onmouseup',Page.mouseup);
	document.attachEvent('onmousemove',Page.mousemove);
	document.attachEvent("onkeydown",Page.keydown);
}else{
	document.addEventListener('click',Page.click,false);
	document.addEventListener('mousedown',Page.mousedown,false);
	window.addEventListener('load',Page.load,false);
	document.addEventListener('mouseup',Page.mouseup,false);
	document.addEventListener('mousemove',Page.mousemove,false);
	document.addEventListener("keydown",Page.keydown,false);
}

/*
addEvent(document, "click" , Page.click);
addEvent(document, "mousedown" , Page.mousedown);
addEvent(window, "load" , Page.load);
addEvent(document, "mouseup" , Page.mouseup);
addEvent(document, "mousemove" , Page.mousemove);
addEvent(document, "keydown" , Page.keydown);
*/

/**begin onPage.onLoad**/
/**

Page.isReady=false;
Page.bindReady = function(evt){
	if(Page.isReady) return;
	Page.isReady=true;
	Page.ready.call(window);
	if(document.removeEventListener){
		document.removeEventListener("DOMContentLoaded", Page.bindReady, false);
	}else if(document.attachEvent){
		document.detachEvent("onreadystatechange", Page.bindReady);
		if(window == window.top){
			clearInterval(Page._Timer);
			Page._Timer = null;
		}
	}
};
*/
isReady=false;
var timer;
var bindReady = function(evt){
	if(isReady) return;
	isReady=true;
	Page.ready.call(window);
	if(document.removeEventListener){
		document.removeEventListener("DOMContentLoaded", bindReady, false);
	}else{
		if(window == window.top){
			clearInterval(timer);
			timer = null;
		}else{
			document.detachEvent("onreadystatechange", bindReady);
		}
	}
};
if(document.addEventListener){
	document.addEventListener("DOMContentLoaded", bindReady, false);
}else{
	if(window == window.top){
		timer = setInterval(function(){
			try{
				isReady||document.documentElement.doScroll('left');//在IE下用能否执行doScroll判断dom是否加载完毕
			}catch(e){
				return;
			}
			bindReady();
		},5);
	}else{
		document.attachEvent("onreadystatechange", function(){
			if((/loaded|complete/).test(document.readyState))
				bindReady();
		});
	}
}
/**

if(document.addEventListener){
	document.addEventListener("DOMContentLoaded", Page.bindReady, false);
}else if(document.attachEvent){
	document.attachEvent("onreadystatechange", function(){
		if((/loaded|complete/).test(document.readyState))
			Page.bindReady();
	});
	if(window == window.top){
		Page._Timer = setInterval(function(){
			try{
				Page.isReady||document.documentElement.doScroll('left');//在IE下用能否执行doScroll判断dom是否加载完毕
			}catch(e){
				return;
			}
			Page.bindReady();
		},5);
	}
}
end onPage.onLoad**/

/* 获取CONTEXTPATH 开始 */
var CONTEXTPATH = '/platform/';

var scripts = document.getElementsByTagName("script");
for(var i=0;i<scripts.length;i++){
	if(/.*Framework\/Main\.js$/g.test(scripts[i].getAttribute("src"))){
		var jsPath = scripts[i].getAttribute("src").replace(/Framework\/Main\.js$/g,'');
		if(jsPath.indexOf("/")==0||jsPath.indexOf("://")>0){
			CONTEXTPATH = jsPath;
			break;
		}
		var arr1 = jsPath.split("/");
		var path = window.location.href;
		if(path.indexOf("?")!=-1){
			path = path.substring(0,path.indexOf("?"));
		}
		var arr2 = path.split("/");
		arr2.splice(arr2.length-1,1);
		for(var i=0;i<arr1.length;i++){
			if(arr1[i]==".."){
				arr2.splice(arr2.length-1,1);
			}
		}
		CONTEXTPATH = arr2.join('/')+'/';
		break;
	}
}
/* 获取CONTEXTPATH 结束 */

/* 定义Server对象 开始 */
var Server = {};
Server.RequestMap = {};
Server.MainServletURL = "MainServlet.jsp";

var currentLevel = window.location.href.split('/').length - 4;
var currentLevelStr = "";
for(var i=0;i<currentLevel;i++){
	
	if(i==currentLevel-1){
		//currentLevelStr += "..";
	} else {
		currentLevelStr += "../";
	}
}
// Server.ContextPath = currentLevelStr + CONTEXTPATH;

Server.ContextPath = CONTEXTPATH;

Server.Pool = [];

Server.getXMLHttpRequest = function(){
	for(var i=0;i<Server.Pool.length;i++){
		if(Server.Pool[i][1]=="0"){
			Server.Pool[i][1] = "1";
			return Server.Pool[i][0];
		}
	}
	var request;
	if (window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		for(var i =5;i>1;i--){
      try{
        if(i==2){
					request = new ActiveXObject( "Microsoft.XMLHTTP" );
        }else{
					request = new ActiveXObject( "Msxml2.XMLHTTP." + i + ".0" );
        }
      }catch(ex){}
    }
	}
	Server.Pool.push([request,"1"]);
	return request;
}

Server.loadURL = function(url,func){
	var Request = Server.getXMLHttpRequest();
	Request.open("GET", Server.ContextPath+url, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	Request.onreadystatechange = function(){
		if (Request.readyState==4&&Request.status==200) {
			try{
				if(func){
					func(Request.responseText);
				};
			}finally{
				for(var i=0;i<Server.Pool.length;i++){
					if(Server.Pool[i][0]==Request){
						Server.Pool[i][1] = "0";
						break;
					}
				}
				Request = null;
				func = null;
			}
		}
	}
	Request.send(null);
}

Server.loadScript = function(url){
	document.write('<script type="text/javascript" src="' + Server.ContextPath+url + '"><\/script>') ;
}

Server.loadCSS = function(url){
	if(Browser.isGecko){
		var e = document.createElement('LINK') ;
		e.rel	= 'stylesheet' ;
		e.type	= 'text/css' ;
		e.href	= url ;
		document.getElementsByTagName("HEAD")[0].appendChild(e) ;
	}else{
		document.createStyleSheet(url);
	}
}

Server.getOneValue = function(methodName,dc,func){//dc既可是一个DataCollection，也可以是一个单值
	if(dc&&dc.prototype==DataCollection.prototype){
		Server.sendRequest(methodName,dc,func);
	}else{
		var dc1 = new DataCollection();
		dc1.add("_Param0",dc);
		Server.sendRequest(methodName,dc1,func);
	}
}

Server.sendRequest = function(methodName,dataCollection,func,id,waitMsg){//参数id用来限定id相同的请求同一时间只能有一个
	if(!Server.executeRegisteredEvent(methodName,dataCollection)){
		Console.log(methodName+"的调用被注册事件阻止!");
		return;
	}
	var Request;
	if(id!=null && Server.RequestMap[id]){
		Request = Server.RequestMap[id];
		Request.abort();
	}else{
		Request = Server.getXMLHttpRequest();
	}
	Request.open("POST", Server.ContextPath+Server.MainServletURL, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var url = "_SKY_METHOD="+methodName+"&_SKY_DATA=";
	if(dataCollection){
		url += encodeURL(htmlEncode(dataCollection.toXML()));
	}
	url += "&_SKY_URL="+encodeURL(window.location.pathname);
	Server._ResponseDC = null;
	Request.onreadystatechange = function(){Server.onRequestComplete(Request,func);};
	Request.send(url);
}

Server.onRequestComplete = function(Request,func){
	if (Request.readyState==4&&Request.status==200) {
		try{
			var xmlDoc = Request.responseXML;
			var dc = new DataCollection();
			if(xmlDoc){
				if(dc.parseXML(xmlDoc)){
					dc["Status"] = dc.get("_SKY_STATUS");
					dc["Message"] = dc.get("_SKY_MESSAGE");
					if(dc.get("_SKY_SCRIPT")){
						eval(dc.get("_SKY_SCRIPT"));
					}
				}
				Server._ResponseDC = dc;
				xmlDoc = null;
			}else{
					dc["Status"] = 0;
					dc["Message"] = "服务器发生异常,未获取到数据!";
			}
			if(func){
				try{
					func(dc);
				}catch(ex){
					alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
				}
			}
		//}catch(ex){
		//	alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
		}finally{
			for(var i=0;i<Server.Pool.length;i++){
				if(Server.Pool[i][0]==Request){
					Server.Pool[i][1] = "0";
					break;
				}
			}
			Request = null;
			func = null;
		}
	}
}

Server.getResponse = function(){
	return Server._ResponseDC;
}

Server.Events = {};
Server.registerEvent = function(methodName,func){//当调用method时执行相应的函数，当该函数值为false退出调用，不向后台发送指令
	var arr = Server.Events[methodName];
	if(!arr){
		arr = [];
	}
	arr.push(func);
}

Server.executeRegisteredEvent = function(methodName,dc){
	var arr = Server.Events[methodName];
	if(!arr){
		return true;
	}
	for(var i=0;i<arr.length;i++){
		if(!arr[i].apply(null,[dc,methodName])){
			return false;
		}
	}
	return true;
}
/* 定义Server对象 结束 */
Server.loadScript("Framework/Drag.js");
Server.loadScript("Framework/Controls/Tabpage.js");
//Server.loadScript("Framework/Controls/Select.js");// @DOTO 废弃
Server.loadScript("Framework/Controls/Selector.js");
//Server.loadScript("Framework/Controls/Dialog/Dialog.js");
//Server.loadScript("Framework/Controls/Dialog/Alert.js");
//Server.loadScript("Framework/Controls/Dialog/Confirm.js");
//Server.loadScript("Framework/Controls/Dialog/wait.js");
Server.loadScript("Framework/Controls/Dialog.js");
Server.loadScript("Framework/Controls/MessagePop.js");
Server.loadScript("Framework/Controls/DataGrid.js");
Server.loadScript("Framework/Controls/DataList.js");
Server.loadScript("Framework/Controls/Menu.js");
Server.loadScript("Framework/Controls/DateTime.js");
Server.loadScript("Framework/Controls/Tree.js");
Server.loadScript("Framework/Controls/Tip.js");
Server.loadScript("Framework/Controls/Progress.js");
Server.loadScript("Framework/Application.js");
Server.loadScript("Framework/Verify.js");
Server.loadScript("Framework/Style.js");
Server.loadScript("Framework/Resize.js");
Server.loadScript("Framework/Console.js");
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

/*
url-loading object and a request queue built on top of it
*/
Sky.Ajax = function (paramObj) {//url, onload, onerror, method, params, contentType) {
	this.req = null;
	this.onload = paramObj.onload || function(){};
	this.onerror = (paramObj.onerror) ? paramObj.onerror : this.defaultError;
	this.url = paramObj.url;
	this.method = paramObj.method;
	this.params = paramObj.params;
	this.contentType = paramObj.contentType;
	this.sendRequest(paramObj.url, paramObj.method, paramObj.params, paramObj.contentType);
};

Sky.Ajax.READY_STATE_UNINITIALIZED = 0;
Sky.Ajax.READY_STATE_LOADING = 1;
Sky.Ajax.READY_STATE_LOADED = 2;
Sky.Ajax.READY_STATE_INTERACTIVE = 3;
Sky.Ajax.READY_STATE_COMPLETE = 4;

Sky.Ajax.prototype.reload = function() {
	this.sendRequest(this.url, this.method, this.params, this.contentType);
};

Sky.Ajax.prototype.sendRequest = function (url, method, params, contentType) {
	method = method || "GET";
	if (!contentType && method == "POST") {
		contentType = "application/x-www-form-urlencoded";
	}
	if (window.XMLHttpRequest) {
		this.req = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			this.req = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	if (this.req) {
		try {
			var loader = this;
			this.req.onreadystatechange = function () {
				Sky.Ajax.onReadyState.call(loader);
			};
			this.req.open(method, url, true);
			if (contentType) {
				this.req.setRequestHeader("Content-Type", contentType);
			}
			this.req.send(params);
		}
		catch (err) {
			this.onerror.call(this);
		}
	}
};

Sky.Ajax.onReadyState = function () {
	var req = this.req;
	var ready = req.readyState;
	
	if (ready == Sky.Ajax.READY_STATE_COMPLETE) {
		var httpStatus = req.status;
		if (httpStatus == 200 || httpStatus == 0) {
			this.onload.call(this, req.responseText);
		} else {
			this.onerror.call(this);
		}
	}
};

Sky.Ajax.prototype.defaultError = function () {
	alert("error fetching data!" + "\n\nreadyState:" + this.req.readyState + "\nstatus: " + this.req.status + "\nheaders: " + this.req.getAllResponseHeaders());
};

/*
	Base.js, version 1.1a
	Copyright 2006-2009, Dean Edwards
	License: http://www.opensource.org/licenses/mit-license.php
*/

var Base = function() {
	// dummy
};

Base.extend = function(_instance, _static) { // subclass
	var extend = Base.prototype.extend;
	
	// build the prototype
	Base._prototyping = true;
	var proto = new this;
	extend.call(proto, _instance);
	proto.base = function() {
		// call this method from any other method to invoke that method's ancestor
	};
	proto.superMethod = (function(sp) {
		var s = function() {
			var name = (s.caller || {}).name;
			//var len = arguments.length, 
			var t=this;
			var supper = arguments.callee.superclass;
			
			if(!name) {
				for(var n in t) {
					if(t[n] == s.caller) {
						name = n;
						break;
					}
				}
				
				if(name) {
					//var callArgs = Array.prototype.slice.call(arguments, 0);
					//Array.prototype.splice.apply(callArgs, [0,1]);(callArgs
					// 这个也是正确的，不过代码好长，好复杂，呜呜 
					 return this.constructor.superClass.prototype[name].call(this);
					//return proto[name].call(this)这个会堆栈溢出
				}
				return supper;
			}
			s.superclass = sp;
		}
		return s;
	})(proto);
	delete Base._prototyping;
	
	// create the wrapper for the constructor function
	//var constructor = proto.constructor.valueOf(); //-dean
	var constructor = proto.constructor;
	var klass = proto.constructor = function() {
		if (!Base._prototyping) {
			if (this._constructing || this.constructor == klass) { // instantiation
				this._constructing = true;
				constructor.apply(this, arguments);
				delete this._constructing;
			} else if (arguments[0] != null) { // casting
				return (arguments[0].extend || extend).call(arguments[0], proto);
			}
		}
	};
	
	// build the class interface
	klass.ancestor = this;
	klass.superClass = this;
	klass.extend = this.extend;
	klass.forEach = this.forEach;
	klass.implement = this.implement;
	klass.prototype = proto;
	klass.toString = this.toString;
	klass.valueOf = function(type) {
		//return (type == "object") ? klass : constructor; //-dean
		return (type == "object") ? klass : constructor.valueOf();
	};
	extend.call(klass, _static);
	// class initialisation
	if (typeof klass.init == "function") klass.init();
	return klass;
};

Base.prototype = {	
	extend: function(source, value) {
		if (arguments.length > 1) { // extending with a name/value pair
			var ancestor = this[source];
			// overriding a method?
			// the valueOf() comparison is to avoid circular references
			if (ancestor && (typeof value == "function") && 
				(!ancestor.valueOf || ancestor.valueOf() != value.valueOf()) &&
				/\bbase\b/.test(value)) {
				// get the underlying method
				var method = value.valueOf();
				// override
				value = function() {
					var previous = this.base || Base.prototype.base;
					this.base = ancestor;
					var returnValue = method.apply(this, arguments);
					this.base = previous;
					return returnValue;
				};
				// point to the underlying method
				value.valueOf = function(type) {
					return (type == "object") ? value : method;
				};
				value.toString = Base.toString;
			}
			this[source] = value;
		} else if (source) { // extending with an object literal
			var extend = Base.prototype.extend;
			// if this object has a customised extend method then use it
			if (!Base._prototyping && typeof this != "function") {
				extend = this.extend || extend;
			}
			var proto = {toSource: null};
			// do the "toString" and other methods manually
			var hidden = ["constructor", "toString", "valueOf"];
			// if we are prototyping then include the constructor
			var i = Base._prototyping ? 0 : 1;
			while (key = hidden[i++]) {
				if (source[key] != proto[key]) {
					extend.call(this, key, source[key]);

				}
			}
			// copy each of the source object's properties to this object
			for (var key in source) {
				if (!proto[key]) extend.call(this, key, source[key]);
			}
		}
		return this;
	}
};

// initialise
Base = Base.extend({
	constructor: function() {
		this.extend(arguments[0]);
	}
}, {
	ancestor: Object,
	version: "1.1",
	
	forEach: function(object, block, context) {
		for (var key in object) {
			if (this.prototype[key] === undefined) {
				block.call(context, object[key], key, object);
			}
		}
	},
		
	implement: function() {
		for (var i = 0; i < arguments.length; i++) {
			if (typeof arguments[i] == "function") {
				// if it's a function, call it
				arguments[i](this.prototype);
			} else {
				// add the interface using the extend method
				this.prototype.extend(arguments[i]);
			}
		}
		return this;
	},
	
	toString: function() {
		return String(this.valueOf());
	}
});

var Console = {};
Console.info = [];

Console.log = function(str){
	Console.info.push(str);
	if(Console.info.length>1200){
		Console.info.splice(1000,Console.info.length-1000);
	}
	if(Console.isShowing){
		//立即显示信息
	}
}

Console.show = function(){
	var html = [];
	html.push("<textarea class='input_textarea' style='width:600px;height:200px'>");
	for(var i=0;i<Console.info.length;i++){
		html.push(htmlEncode(Console.info[i]));
		html.push("<br>");
	}
	html.push("</textarea>");
	Dialog.alert(html.join('\n'),700,250);
}

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
/* 元素方法扩展 开始 */
/**
 * @class ExtElement DOM元素的扩展，提供一些常用辅助函数
 */
var $E = {};
Sky.ExtElement = $E;

/**
 * @method $A 获取元素的属性
 * @param {String} attr 属性名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 属性的值
 */
$E.$A = function(attr, ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getAttribute ? ele.getAttribute(attr) : null;
}

$E.on = function(eventName, func) {
	addEvent(this, eventName, func);
	return this;
}

/**
 * @method clear 清除value
 * @memberOf {TypeName} 
 */
$E.clearValue = function(){
	this.value = "";
}

/**
 * @method $T 获取元素下指定的标签对象
 * @param {String} tagName 标签的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Array} 对象数组
 */
$E.find = $E.$T = function(tagName, ele) {
	ele = ele || this;
	ele = window.$(ele);
	return window.$T(tagName, ele);
}

/**
 * @method visible 查看元素是否可见
 * @param {Object} ele DOM元素对象或其id
 * @return {Boolean} 元素是否可见
 */
$E.visible = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if (ele.style.display == "none") {
		return false;
	}
	return true;
}

/**
 * @method toggle 展开或收缩元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.toggle = function(ele) {
	ele = ele || this;
	ele = $(ele);
  $E[$E.visible(ele) ? 'hide' : 'show'](ele);
}

/**
 * @method toString 查看元素的所有属性
 * @param {Boolean} flag 是否显示函数内容
 * @param {Number} index 从第几个下标开始
 * @param {Object} ele DOM元素
 * @return {String} 包含元素所有属性的字符串
 */
$E.toString = function(flag,index,ele) {//flag表示是否显示函数内容
	ele = ele || this;
	var arr = [];
	var i = 0;
	for(var prop in ele){
		if(!index||i>=index){
			var v = null;
			try{v = ele[prop];}catch(ex){}//gecko下可能会报错
			if(!flag){
				if(typeof(v)=="function"){
					v = "function()";
				}else if((""+v).length>100){
					v = (""+v).substring(0,100)+"...";
				}
			}
			arr.push(prop+":"+v);
		}
		i++;
	}
  return arr.join("\n");
}

/**
 * @method focusEx 函数获得焦点
 * @param {Object} ele DOM元素对象或其id
 */
$E.focusEx = function(ele) {
	ele = ele || this;
	ele = $(ele);
	try{
  	ele.focus();
	}catch(ex){}
}

/**
 * @method getForm 获取该元素所在的form对象
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 表单对象
 */
$E.getForm = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(Browser.isIE){
		ele = ele.parentElement;
	}else if(Browser.isGecko){
		ele = ele.parentNode;
	}
	if(!ele){
		return null;
	}
	if(ele.tagName.toLowerCase()=="form"){
		return ele;
	}else{
		return $E.GetForm(ele);
	}
}



/**
 * @method hide 隐藏元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.hide = function(ele) {
	if(!ele){
		ele = this;
	}
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("xtype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = 'none';
		}
	}
  ele.style.display = 'none';
}

/**
 * @method show 显示元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.show = function(ele) {
	if(!ele){
		ele = this;
	}
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="input"&&ele.type=="button"){
		if(ele.parentElement&&ele.parentElement.getAttribute("xtype")=="zInputBtnWrapper"){
			ele.parentElement.style.display = '';
		}
	}
  ele.style.display = '';
}

/**
 * @method disable 禁用元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.disable = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
		for (var i = 0; i < elements.length; i++) {
		  var element = elements[i];
		  ele.blur();
		  if(ele.hasClassName("zPushBtn")){
			  ele.addClassName("zPushBtnDisabled");
			  if(ele.onclick){
				 ele.onclickbak = ele.onclick;
			  }
			  ele.onclick=null;
		  }else{
			  ele.disabled = 'true';
		  }
		}
	}else{
		if(ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"){
			Selector.setDisabled(ele,true);
		}else if(ele.hasClassName("zPushBtn")){
			ele.addClassName("zPushBtnDisabled");
			if(ele.onclick){
				ele.onclickbak = ele.onclick;
			}
			ele.onclick=null;
		}else{
			ele.addClassName("disabled");
	    	ele.disabled = 'true';
		}
	}
}

/**
 * @method enable 启用元素
 * @param {Object} ele DOM元素对象或其id
 */
$E.enable = function(ele) {
	ele = ele || this;
	ele = $(ele);
	if(ele.tagName.toLowerCase()=="form"){
		var elements = ele.elements;
	    for (var i = 0; i < elements.length; i++) {
	      var element = elements[i];
			if(ele.hasClassName("zPushBtnDisabled")){
				ele.className="zPushBtn";
				if(ele.onclickbak){
					ele.onclick = ele.onclickbak;
				}
			}else{
		    	ele.disabled = '';
			}
	    }
	}else{
		if(ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"){
			Selector.setDisabled(ele,false);
		}else if(ele.hasClassName("zPushBtnDisabled")){
			ele.className="zPushBtn";
			if(ele.onclickbak){
				ele.onclick = ele.onclickbak;
			}
		}else{
			if(ele.hasClassName("disabled")){
				ele.removeClassName("disabled");
			}
	    	ele.disabled = '';
		}
	}
}

/**
 * @method scrollTo 滚动到改元素的位置
 * @param {Object} ele DOM元素对象或其id
 * @private
 */
$E.scrollTo = function(ele) {
	ele = ele || this;
	ele = $(ele);
  var x = ele.x ? ele.x : ele.offsetLeft,
      y = ele.y ? ele.y : ele.offsetTop;
  window.scrollTo(x, y);
}

/**
 * @method getWidth 获取元素宽度
 * @param {Object} ele
 * @return {Number} 
 */
$E.getWidth = function(ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getDimensions().width;
}

/**
 * @method getHeight 获取元素高度
 * @param {Object} ele
 * @return {Number} 
 */
$E.getHeight = function(ele) {
	ele = ele || this;
	ele = $(ele);
	return ele.getDimensions().height;
}

/**
 * @method css 获取或设置style样式
 * @param {Object} name
 * @param {Object} value
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
$E.css = function(name, value) {
	
	if(String.isInstance(name)) {
		if(!value) {
			return this.style[name];
		}
		
		this.style[name] = value;
	} else {
		for(var p in name) {
			this.style[p] = name[p];
		}
	}
}

/**
 * 设置元素到屏幕中央
 * @param {Object} ele
 */
$E.center = function() {
	var viewport = $E.getViewportDimensions();
	var top = viewport.height / 2 - this.getHeight() / 2;
	var left = viewport.width / 2 - this.getWidth() / 2;
	this.css( {
		"top" : top,
		"left" : left
	});
}

/**
 * @method getDimensions 获取元素的大小
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素大小的对象，格式如：{width: 10, height: 20}
 */
$E.getDimensions = function(ele){
  ele = ele || this;
  ele = $(ele);
  var dim;
  if(ele.tagName.toLowerCase()=="script"){
  	dim = {width:0,height:0};
  }else if ($E.visible(ele)){
		if(Browser.isIE && ele.offsetWidth ==0 && ele.offsetHeight ==0){
			if(Browser.isBorderBox){
				dim = {width: ele.currentStyle.pixelWidth, height: ele.currentStyle.pixelHeight};
			}else{
				dim = {width: +ele.currentStyle.pixelWidth+ele.currentStyle.borderLeftWidth.replace(/\D/g,'')+ele.currentStyle.borderRightWidth.replace(/\D/g,'')+ele.currentStyle.paddingLeft.replace(/\D/g,'')+ele.currentStyle.paddingRight.replace(/\D/g,''),
						height: +ele.currentStyle.pixelHeight+ele.currentStyle.borderTopWidth.replace(/\D/g,'')+ele.currentStyle.borderBottomWidth.replace(/\D/g,'')+ele.currentStyle.paddingTop.replace(/\D/g,'')+ele.currentStyle.paddingBottom.replace(/\D/g,'')
				};
/**
				dim = {width: +curStyle.pixelWidth+parseInt(curStyle.borderLeftWidth)+parseInt(curStyle.borderRightWidth)+parseInt(curStyle.paddingLeft)+parseInt(curStyle.paddingRight),
						height: +curStyle.pixelHeight+parseInt(curStyle.borderTopWidth)+parseInt(curStyle.borderBottomWidth)+parseInt(curStyle.paddingTop)+parseInt(curStyle.paddingBottom)
				};
				*/
			}
		}else{
			dim = {width: ele.offsetWidth, height: ele.offsetHeight};
		}
  }else{
	  var style = ele.style;
	  var vis = style.visibility;
	  var pos = style.position;
	  var dis = style.display;
	  style.visibility = 'hidden';
	  style.position = 'absolute';
	  style.display = 'block';
	  var w = ele.offsetWidth;
	  var h = ele.offsetHeight;
	  style.display = dis;
	  style.position = pos;
	  style.visibility = vis;
	  dim = {width: w, height: h};
	}
	return dim;
}

/**
 * @method getPosition 获取元素的坐标
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素宽高的对象，格式如：{x: 10, y: 20}
 */
$E.getPosition = function(ele){
	ele = ele || this;
	ele = $(ele);
	var doc = ele.ownerDocument;
  if(ele.parentNode===null){//||ele.style.display=='none'){
    return false;
  }
  var parent = null;
  var pos = [];
  var box;
  if(ele.getBoundingClientRect){//IE,FF3,己很精确，但还没有非常确定无误的定位
    box = ele.getBoundingClientRect();
    var scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
    var scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
    var X = box.left + scrollLeft - doc.documentElement.clientLeft;
    var Y = box.top + scrollTop - doc.documentElement.clientTop;
    if(Browser.isIE){// new version is deleted
    	X--;
    	Y--;
    }
	/**
	var X = box.left + scrollLeft - doc.documentElement.clientLeft-doc.body.clientLeft;
    var Y = box.top + scrollTop - doc.documentElement.clientTop-doc.body.clientTop;

	*/
    return {x:X, y:Y};
  }else if(doc.getBoxObjectFor){ // FF2
    box = doc.getBoxObjectFor(ele);
    var borderLeft = (ele.style.borderLeftWidth)?parseInt(ele.style.borderLeftWidth):0;
    var borderTop = (ele.style.borderTopWidth)?parseInt(ele.style.borderTopWidth):0;
    pos = [box.x - borderLeft, box.y - borderTop];
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

$E.getViewportDimensions = function (win) {
	var win = win || window,
		doc = win.document,
		viewportWidth,
		viewportHeight;
	if(Browser.isIE){
		viewportWidth=Browser.isQuirks?doc.body.clientWidth :doc.documentElement.clientWidth ;
		viewportHeight=Browser.isQuirks?doc.body.clientHeight :doc.documentElement.clientHeight;
	}else{
		viewportWidth=win.innerWidth;
		viewportHeight=win.innerHeight;
	}
	return {width:viewportWidth, height:viewportHeight};
}
/**
 * @method getPositionEx 获取元素的坐标，计算滚动条的宽高
 * @param {Object} ele DOM元素对象或其id
 * @return {Object} 元素宽高的对象，格式如：{x: 10, y: 20}
 */
$E.getPositionEx = function(ele){
	ele = ele || this;
	ele = $(ele);
	var pos = $E.getPosition(ele);
	var win = window;
	var sw,sh;
	while(win!=win.parent){
		//if(win.frameElement&&win.parent.DataCollection){
		//	pos2 = $E.getPosition(win.frameElement);
		//	pos.x += pos2.x;
		//	pos.y += pos2.y;
		//}
		sw = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		sh = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		//pos.x -= sw;
		//pos.y -= sh;
		
		//if(!win.parent.DataCollection){
		//	break;
		//}
		win = win.parent;
	}
	return pos;
}

/**
 * @method getParent 获取元素指定标签名的父节点
 * @param {String} tagName 父节点的标签名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 父节点
 */
$E.getParent = function(tagName,ele){
	ele = ele || this;
	ele = $(ele);
	while(ele){
		if(ele.tagName.toLowerCase()==tagName.toLowerCase()){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

/**
 * @method getParentByAttr 根据父节点元素的属性名称获取指定父节点
 * @param {String} attrName 父节点的属性名称
 * @param {String} attrValue 父节点属性的值
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 父节点
 */
$E.getParentByAttr = function(attrName,attrValue,ele){
	ele = ele || this;
	ele = $(ele);
	while(ele){
		if(ele.getAttribute(attrName)==attrValue){
			return $(ele);
		}
		ele = ele.parentElement;
	}
	return null;
}

/**
 * @method nextElement 获取该节点的下一个兄弟节点
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 兄弟节点
 */
$E.nextElement = function(ele){
	ele = ele || this;
	ele = $(ele);
	var x = ele.nextSibling;
	while (x&&x.nodeType!=1){
		x = x.nextSibling;
	}
	return $(x);
}

/**
 * @method previousElement 获取该节点的上一个兄弟节点
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 兄弟节点
 */
$E.previousElement = function(ele){
	ele = ele || this;
	ele = $(ele);
	var x = ele.previousSibling;
	while (x&&x.nodeType!=1){
		x = x.previousSibling;
	}
	return $(x);
}

/**
 * @method getTopLevelWindow 获取最顶层window对象
 * @return {window} 最顶层window对象
 */
$E.getTopLevelWindow = function(){
	var pw = window;
	while(pw!=pw.parent){
		//if(!pw.parent.DataCollection){
		//	return pw;
		//}
		pw = pw.parent;
	}
	return pw;
};

$TW = $E.getTopLevelWindow();

/**
 * @method hasClassName 检测元素是否拥有某class样式
 * @param {String} className class样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Boolean} 元素是否拥有某class样式
 */
$E.hasClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	return (new RegExp(("(^|\\s)" + className + "(\\s|$)"), "i").test(ele.className));
}

/**
 * @method addClassName 给元素添加样式
 * @param {String} className 样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @param {Boolean} before 是否加在当前样式之前
 * @return {String} 该元素的样式
 */
$E.addClass = $E.addClassName = function(className,ele,before){
	
	ele = ele || this;
	ele = $(ele);
	
	if(Array.isInstance(className)) {
		for(var i=0; i<className.length; i++) {
			ele.addClassName(className[i]);
		}
	}
		
	var currentClass = ele.className;
	currentClass = currentClass?currentClass:"";
	if(!ele.hasClassName(className)){
		if(before){
			ele.className = className + ((currentClass.length > 0)? " " : "") + currentClass;
		}else{
			ele.className = currentClass + ((currentClass.length > 0)? " " : "") + className;
		}
	}
	return ele.className;
}

$E.addClassOnOver = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	ele.on("mouseover", function() {
		ele.addClass(className);
	});
	ele.on("mouseout", function() {
		ele.removeClass(className);
	});
}
		
/**
 * @method removeClassName 移除样式
 * @param {String} className 需要移除的样式
 * @param {Object} ele DOM元素对象或其id
 * @return {String} 该元素的样式
 */
$E.removeClass = $E.removeClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
  var classToRemove = new RegExp(("(^|\\s)" + className + "(?=\\s|$)"), "i");
  ele.className = ele.className.replace(classToRemove, "").replace(/^\s+|\s+$/g, "");
  return ele.className;
}

/**
 * @method setClassName 给元素设置样式，原来样式将被替换
 * @param {String} className 样式的名称
 * @param {Object} ele DOM元素对象或其id
 * @return {Element} 返回该对象
 */
$E.setClassName = function(className,ele){
	ele = ele || this;
	ele = $(ele);
	var _cn = ele.className || "";
	if(_cn.indexOf("z-btn-text") != -1) {
		ele.className = "z-btn-text " + className;
	} else {
		ele.className = className;
	}
  	return ele;
}

/**
 * @method setValue 设置值
 * @param {Object} value 需要设置的值
 * @param {Element} ele DOM元素对象或其id
 * @return {Element} 返回该对象
 */
$E.setValue = function(value, ele){
	ele = ele || this;
	ele = $(ele);
	$S(ele, value);
	return ele;
}

/**
 * @method getValue 获取元素的值
 * @param {Element} ele DOM元素对象或其id
 * @return {Object} 该元素的值
 */
$E.getValue = function(ele){
	ele = ele || this;
	ele = $(ele);
	return $V(ele);
}

$E.positionTo = function(target, ele) {
	ele = ele || this;
	ele = $(ele);
	var pos = target.getPositionEx();
    var divStyle = ele.style;
    divStyle.top = (pos.y + target.offsetHeight) + "px";
    divStyle.left = pos.x + "px";
    divStyle.position = 'absolute';
}

/*
给定p1(x1/y1)和p2(x2/y2)，p1在p2的左上方(也可重合)，计算一个起始坐标，
使得元素ele(宽为w,高为h)能够全部在p1之上，或者p2之下，并且尽可能显示ele的全部
flag="all"表示ele能够显示在x1的两边或者x2的两边
flag="left"表示ele能够显示在x1的左边或者x2的左边
flag="right"表示ele能够显示在x1的右边或者x2的右边
右键菜单、日期控件、下拉框控件需要这个函数
*/
$E.computePosition = function(x1,y1,x2,y2,flag,w,h,win){
	var doc = win?win.document:document;
	var cw = Browser.isQuirks?doc.body.clientWidth:doc.documentElement.clientWidth;
	var ch = Browser.isQuirks?doc.body.clientHeight:doc.documentElement.clientHeight;
	var sw = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
	var sh = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
	if(!flag||flag.toLowerCase()=="all"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//从P2往右展开可行
				return {x:x2,y:y2};
			}else{//往左展开
				return {x:x2-w,y:y2};
			}
		}
		//考虑p1
		if(x1-sw+w-cw<0){//从P1往右展开可行
			return {x:x1,y:y1-h};
		}else{//往左展开
			return {x:x1-w,y:y1-h};
		}
	}else	if(flag.toLowerCase()=="right"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw+w-cw<0){//从P2往右展开可行
				return {x:x2,y:y2};
			}
		}
		
		//考虑p1
		if(y1>h){
			return {x:x1, y:y1-h};
		}
		
		if(y1 > ch-y2) {// y1上面的高度大于y2下面的高度
			return {x:x1, y:y1-h};
		}
		
		return {x:x2, y:y2};
	}else if(flag.toLowerCase()=="left"){
		//先考虑p2
		if(y2-sh+h-ch<0){
			if(x2-sw-w>0){//从P2往左展开可行
				return {x:x2,y:y2};
			}
		}
		//考虑p1
		return {x:x1-w,y:y1-h};
	}
}
/* 元素方法扩展 结束 */
/* 定义Form对象 开始 */
var Form = {
	
	setValue: function(dr,ele){/* 根据字典dr设置表单的值 */
		ele = $F(ele);
		for(var i=0;i<ele.elements.length;i++){
			var c = $(ele.elements[i]);
			if(c.$A("xtype")=="select"){
				c = c.parentElement;
			}
			if(c.type=="checkbox"||c.type=="radio"){
				if(c.name){
					$NS(c.name,dr.get(c.name));
					continue;
				}
			}
			var id = c.id.toLowerCase();
			if(dr.get(id)){
				$S(c,dr.get(id));
			}
		}
	},
	
	getData: function(ele){/* 获取表单元素值的集合 */
		ele = $F(ele);
		if(!ele){
			alert("查找表单元素失败!"+ele);
			return;
		}
		var dc = new DataCollection();
		var arr = ele.elements;
		for(var i=0;i<arr.length;i++){
			var c = $(arr[i]);
			
			if(!c.type){
				continue;
			}
			if(c.type=="checkbox"||c.type=="radio"){
				if(c.name){
					dc.add(c.name,$NV(c.name));
					continue;
				}
			}
			if(!c.id && !c.name ){
				continue;
			}
			if(c.$A("xtype")=="select"){
				c = c.parentElement;
			}
			dc.add(c.id || c.name,$V(c));
		}
		return dc;
	}
};
/* 定义Form对象 结束 */
/* 定义Misc类 开始 */
var Misc = {};
Misc.setButtonText = function(ele,text){//为button设置文本
	$(ele).childNodes[1].innerHTML = text+"&nbsp;";
}

Misc.withinElement = function(event, ele) {//仅适用于Gecko,判断onmouseover,onmouseout是否是一次元素内部重复触发
	
	var parent = event.relatedTarget;
	while(parent&&parent!=ele&&parent!=document.body){
		try{
			parent = parent.parentNode;
		}catch(ex){
			alert("Misc.withinElement:"+ex.message);
			return false;
		}
	}
	return parent == ele;
}

Misc.copyToClipboard = function(text){
	if(text==null){
		return;
	}
	if (window.clipboardData){
		window.clipboardData.setData("Text", text);
	}else if (window.netscape){
		try{
			netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		}catch(ex){
			Dialog.alert("Firefox自动复制功能未启用！<br>请<a href='about:config' target='_blank'>点击此处</a> 将'signed.applets.codebase_principal_support'设置为'true'");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if(!clip){return;}
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if(!trans){return;}
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext=text;
		str.data=copytext;
		trans.setTransferData("text/unicode",str,copytext.length*2);
		var clipid=Components.interfaces.nsIClipboard;
		if(!clip){return;}
		clip.setData(trans,null,clipid.kGlobalClipboard);
	}else{
		alert("该浏览器不支持自动复制功能！");
		return;
	}
	Dialog.alert("己复制文字：<br><br><font color='red'>"+text+"</font><br><br>到剪贴板",null,400,200);
}

Misc.lockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(Browser.isGecko){
		ele.style.MozUserSelect = "none";
		ele.style.MozUserInput = "none";
	}else{
		document.selection.empty();
		ele.onselectstart = stopEvent;
	}
}

Misc.unlockSelect = function(ele){
	if(!ele){
		ele = document.body;
	}
	if(Browser.isGecko){
		ele.style.MozUserSelect = "";
		ele.style.MozUserInput = "";
	}else{
		ele.onselectstart = null;
	}
}

Misc.lockScroll = function(win){
	//if(!win){
	//	win = window;
	//}
	//if(Browser.isIE){
	//	win.document.body.attachEvent("onmousewheel",win.stopEvent);
	//}else{
	//	win.addEventListener("DOMMouseScroll", win.stopEvent, false);
	//}
}

Misc.unlockScroll = function(win){
	/*
	if(!win){
		win = window;
	}
	if(!win.document) {
		win = window;
	}
	if(Browser.isIE){
		win.document.body.detachEvent("onmousewheel",win.stopEvent);
		win.document.body.detachEvent("onmousewheel",win.stopEvent);//Select.js中可能会连续attch两次,所以必须detach两次
	}else{
		win.removeEventListener("DOMMouseScroll", win.stopEvent, false);
	}*/
}
/* 定义Misc类 结束 */

//()()
function(){

    /**
     * @Class Request 定义外部接口
     * 	Request开放接口给外部，提供两个接口：getParameter和getParameterValues
     *  这样外部的JavaScript文件就可以通过调用Request.getParameter()来执行相应的动作
     */
    Request = {
        /**
         * @method getParameter 得到URL后的参数
         * 	例如URL：http://abc?x=1&y=2， getParameter("x") 得到1
         */
        getParameter: getParameter,
		
		/**
		 * @method getParameterValues 如果有多个重复的paraName的情况下，下面这个方法返回一个包含了所有值的数组
		 * 例如http://abc?x=1&x=2&x=3，getParameterValues("x")得到[1,2,3]
		 */
        getParameterValues: getParameterValues
    };
    
    function getParameter(paraName, wnd){
    	var values = getParameterValues(paraName, wnd);
		return values.length > 0 ? values[0] : null;
    }
    
    function getParameterValues(paraName, wnd){
        
		var paraStr = getSearchStr(wnd);
		//根据“&”符号分割字符串
        var paraList = paraStr.split(/\&/g);
        
        var values = new Array();
        for (var i = 0; i < paraList.length; i++) {
            ///用正则表达式判断字符串是否是“paraName=value”的格式
            var pattern = new RegExp("^" + paraName + "[?=\\=]", "g");
            if (pattern.test(paraList[i])) {
                //将所有满足paramName=value的结果的value(解码后的value的内容)都放入一个数组中
                values.push(decodeURIComponent(paraList[i].split(/\=/g)[1]));
            }
        }
        //返回结果数组
        return values;
    }
	
	/**
	 * 得到地址栏中的get参数字符串
	 * @param {Object} wnd
	 */
	function getSearchStr(wnd) {
		//如果不提供wnd参数，则默认为当前窗口
        if (wnd == null) 
            wnd = self;
        
        //得到地址栏上“?”后边的字符串
        return wnd.location.search.slice(1);
	}
};
/**
 * 	多行SELECT 开始 // TODO 代码需整理
 * 
 * @class MultipleSelect 多行SELECT
 */
/**
 * @method constructor 构造器
 * @param {Object} leftPart 左边的id
 * @param {Object} rightPart 右边的id
 */
MultipleSelect = function(leftPart, rightPart) {
	this.leftPart = $(leftPart);
	this.rightPart = $(rightPart);
}

MultipleSelect.prototype = {
	createOption: function (ele, data, config) {
		ele = $(ele);
		var _instance = this;
		
    	var divOpt = document.createElement('div');
    		divOpt.innerText = data[config.text];
    		divOpt.text = data[config.text];
    		divOpt.value = data[config.value];
    		divOpt.onmouseover = function() {
    			this.style.cursor = 'hand';
    		};
    		divOpt.onclick = function() {
    			
    			this.selected = this.selected?false:true;
    			
    			if(this.selected) {
    				this.style.background = '#7BC9F6';
    			} else {
    				this.style.background = '';
    			}
    		};
    		divOpt.ondblclick = function() {
    		
    			/* 是右边的part，双击直接删除 */
    			if(this.parentElement==_instance.rightPart) {
    				this.parentElement.removeChild(this);
    				return;
    			}
    			var rightPart = _instance.rightPart;//$('rightPart');
    			var childs = rightPart.$T('div');
    			var exist = false;
    			for(var i=0;i<childs.length;i++) {
    				var child = childs[i];
    				if(child.value == this.value) {
    					exist = true;
    					break;
    				}
    			}
    			if(!exist) {
    				var divOpt = document.createElement('div');
		    		divOpt.innerText = this.text;
		    		divOpt.text = this.text;
		    		divOpt.value = this.value
		    		divOpt.onmouseover = function() {
		    			this.style.cursor = 'hand';
		    		};
		    		divOpt.ondblclick = function() {
		    			rightPart.removeChild(this);
		    		};
		    		divOpt.onclick = function() {
    			
		    			this.selected = this.selected?false:true;
		    			
		    			if(this.selected) {
		    				this.style.background = '#7BC9F6';
		    			} else {
		    				this.style.background = '';
		    			}
		    		};
		    		
		    		rightPart.appendChild(divOpt);
    			}
    		};
    		
    		ele.appendChild(divOpt);
    },deleteSelected: function () {
		
		this.deleteOptions(this.rightPart, this.getSelectedOptions(this.rightPart));
	},deleteAll: function () {

		this.deleteOptions(this.rightPart, this.rightPart.$T('div'));
	}, deleteOptions: function (ele, options) {
		
		for(var i=0;i<options.length;i++) {
			var option = options[i];
			ele.removeChild(option);
		}
	}, addSelected: function () {
		
		this.addOptions(this.rightPart, this.getSelectedOptions(this.leftPart));
	}, addAll: function () {
	
		this.addOptions(this.rightPart, this.leftPart.$T('div'));
	}, each: function (elements, fn) {
		var ret = [];
		for(var i=0;i<elements.length;i++) {
			var element = elements[i];
			if(fn(element)) {
				ret.push(element);
			}
		}
		return ret;
	}, getSelectedOptions: function (ele) {
		
		return this.each($(ele).$T('div'), function(option){
			return option.selected;
		});
	}, addOptions: function (ele, options) {
		ele = $(ele);
		var childs = ele.$T('div');
		for(var i=0;i<options.length;i++) {
			var option = options[i];
			var exist = false;
			for(var j=0;j<childs.length;j++) {
				child = childs[j];
				if(child.value == option.value) {
					exist = true;
					break;
				}
			}
			if(!exist) {
				var divOpt = document.createElement('div');
	    		divOpt.innerText = option.innerText;divOpt.text = option.text;
	    		divOpt.value = option.value;	
	    		divOpt.onmouseover = function() {
	    			this.style.cursor = 'hand';
	    		};
	    		divOpt.ondblclick = function() {
	    			ele.removeChild(this);
	    		};
	    		divOpt.onclick = function() {
   			
	    			this.selected = this.selected?false:true;
	    			
	    			if(this.selected) {
	    				this.style.background = '#7BC9F6';
	    			} else {
	    				this.style.background = '';
	    			}
	    		};
	    		
	    		ele.appendChild(divOpt);
			}
		}
	}
	
}


/* 多行SELECT 结束 */	
