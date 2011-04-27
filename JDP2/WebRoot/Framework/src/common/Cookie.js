
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