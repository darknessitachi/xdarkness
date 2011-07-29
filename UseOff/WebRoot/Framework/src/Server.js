(function() {/* 获取CONTEXTPATH */
	CONTEXTPATH = '/';

	var scripts = document.getElementsByTagName("script");
	for ( var i = 0; i < scripts.length; i++) {
		if (/.*Framework\/Main\.js$/g.test(scripts[i].getAttribute("src"))) {
			var jsPath = scripts[i].getAttribute("src").replace(
					/Framework\/Main\.js$/g, '');
			if (jsPath.indexOf("/") == 0 || jsPath.indexOf("://") > 0) {
				CONTEXTPATH = jsPath;
				break;
			}
			var arr1 = jsPath.split("/");
			var path = window.location.href;
			if (path.indexOf("?") != -1) {
				path = path.substring(0, path.indexOf("?"));
			}
			var arr2 = path.split("/");
			arr2.splice(arr2.length - 1, 1);
			for ( var i = 0; i < arr1.length; i++) {
				if (arr1[i] == "..") {
					arr2.splice(arr2.length - 1, 1);
				}
			}
			CONTEXTPATH = arr2.join('/') + '/';
			break;
		}
	}
})();

/* 定义Server对象 开始 */
var Server = {
	RequestMap : {},
	MainServletURL : "MainServlet.jsp",
	ContextPath : CONTEXTPATH,
	Pool : [],
	/**
	 * 获取XMLHttpRequest
	 */
	getXMLHttpRequest : function() {
		/**
		 * 从XMLHttpRequest池中获取XMLHttpRequest
		 */
		for ( var i = 0; i < Server.Pool.length; i++) {
			if (Server.Pool[i][1] == "0") {
				Server.Pool[i][1] = "1";
				return Server.Pool[i][0];
			}
		}

		/**
		 * 创建XMLHttpRequest，并加入XMLHttpRequest池中
		 * @param {Object} url
		 * @param {Object} func
		 */
		var request;
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			for ( var i = 5; i > 1; i--) {
				try {
					if (i == 2) {
						request = new ActiveXObject("Microsoft.XMLHTTP");
					} else {
						request = new ActiveXObject("Msxml2.XMLHTTP." + i
								+ ".0");
					}
				} catch (ex) {
				}
			}
		}
		Server.Pool.push( [ request, "1" ]);
		return request;
	},
	
	/**
	 * 加载URL
	 * @param {String} url 路径
	 * @param {Function} func 回调函数
	 */
	loadURL : function(url, func) {
		var request = Server.getXMLHttpRequest();
		request.open("GET", Server.ContextPath + url, true);
		request.setRequestHeader('Content-type',
				'application/x-www-form-urlencoded');
		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				try {
					if (func) {
						func(request.responseText);
					}
				} finally {
					/**
					 * 将该request置为空闲
					 */
					for ( var i = 0; i < Server.Pool.length; i++) {
						if (Server.Pool[i][0] == request) {
							Server.Pool[i][1] = "0";
							break;
						}
					}
					request = null;
					func = null;
				}
			}
		}
		request.send(null);
	},
	
	loadScript : function(url) {
		document.write('<script type="text/javascript" src="' + Server.ContextPath
				+ url + '"><\/script>');
	},
	
	loadCSS : function(url) {
		if (Browser.isGecko) {
			var e = document.createElement('LINK');
			e.rel = 'stylesheet';
			e.type = 'text/css';
			e.href = url;
			document.getElementsByTagName("HEAD")[0].appendChild(e);
		} else {
			document.createStyleSheet(url);
		}
	}
};


/**
 * @param {String} methodName
 * @param {String/DataCollection} dc dc既可是一个DataCollection，也可以是一个单值
 * @param {Function} func
 */
Server.getOneValue = function(methodName, dc, func) {
	var _dc = dc;
	if (!(dc && dc.prototype == DataCollection.prototype)) {
		_dc = new DataCollection();
		_dc.add("_Param0", dc);
	}
	
	Server.sendRequest(methodName, _dc, func);
}


Server.sendRequest = function(methodName, dataCollection, func, id, waitMsg) {//参数id用来限定id相同的请求同一时间只能有一个
	if (!Server.executeRegisteredEvent(methodName, dataCollection)) {
		Console.log(methodName + "的调用被注册事件阻止!");
		return;
	}
	var request;
	if (id != null && Server.RequestMap[id]) {
		request = Server.RequestMap[id];
		request.abort();
	} else {
		request = Server.getXMLHttpRequest();
	}
	request.open("POST", Server.ContextPath + Server.MainServletURL, true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var url = "_SKY_METHOD=" + methodName + "&_SKY_DATA=";
	if (dataCollection) {
		url += encodeURL(htmlEncode(dataCollection.toXML()));
	}
	url += "&_SKY_URL=" + encodeURL(window.location.pathname);
	Server._ResponseDC = null;
	request.onreadystatechange = function() {
		Server.onRequestComplete(request, func);
	};
	request.send(url);
}

Server.onRequestComplete = function(request, func) {
	if (request.readyState == 4 && request.status == 200) {
		try {
			var xmlDoc = request.responseXML;
			var dc = new DataCollection();
			if (xmlDoc) {
				if (dc.parseXML(xmlDoc)) {
					dc["Status"] = dc.get("_SKY_STATUS");
					dc["Message"] = dc.get("_SKY_MESSAGE");
					if (dc.get("_SKY_SCRIPT")) {
						eval(dc.get("_SKY_SCRIPT"));
					}
				}
				Server._ResponseDC = dc;
				xmlDoc = null;
			} else {
				dc["Status"] = 0;
				dc["Message"] = "服务器发生异常,未获取到数据!";
			}
			if (func) {
				try {
					func(dc);
				} catch (ex) {
					alert("Server.onRequestComplete:" + ex.message + "\t"
							+ ex.lineNumber);
				}
			}
		} finally {
			for ( var i = 0; i < Server.Pool.length; i++) {
				if (Server.Pool[i][0] == request) {
					Server.Pool[i][1] = "0";
					break;
				}
			}
			request = null;
			func = null;
		}
	}
}

Server.getResponse = function() {
	return Server._ResponseDC;
}

Server.Events = {};
Server.registerEvent = function(methodName, func) {//当调用method时执行相应的函数，当该函数值为false退出调用，不向后台发送指令
	var arr = Server.Events[methodName];
	if (!arr) {
		arr = [];
	}
	arr.push(func);
}

Server.executeRegisteredEvent = function(methodName, dc) {
	var arr = Server.Events[methodName];
	if (!arr) {
		return true;
	}
	for ( var i = 0; i < arr.length; i++) {
		if (!arr[i].apply(null, [ dc, methodName ])) {
			return false;
		}
	}
	return true;
}
/* 定义Server对象 结束 */