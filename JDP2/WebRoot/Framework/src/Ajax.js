
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

