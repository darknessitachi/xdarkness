
/**
 * Declare an object to which we can add real functions.
 */
if (dwr == null) var dwr = {};
if (dwr.struts2 == null) dwr.struts2 = {};
if (DWRActionUtil == null) var DWRActionUtil = dwr.struts2;

var separator = "-";
//Struts2请求URL中Action与Method的分隔符
var NEED_DEBUG = false;

/** Execute a remote request using DWR */
dwr.struts2.executeActionURL = function(actionURL, values, callbackObjOrName) {
    var action = DWRActionUtil.parseactionurl(actionURL);
    if (NEED_DEBUG) {
        DWRActionUtil.debugObj(action);
    }
    dwr.struts2.execute(action, values, callbackObjOrName);
}

dwr.struts2.execute = function(action, values, callbackObjOrName) {

    var params = {};
    if (dwr.struts2.isElement(values)) {
        var element = $(values);
        var elementName = element.nodeName.toLowerCase();

        if (elementName == 'form') {
            for (var i = 0; i < element.elements.length; i = i + 1) {
                var e = element.elements[i];
                if (e.name != null && e.name != '') {
					
                    params[e.name] = dwr.util.getValue(e);
                }
            }
        }
        else {
            params[element.name] = dwr.util.getValue(element);
        }
    }
    else {
        if (values && values != "") {
            for (var prop in values) {
                if(values[prop] || values[prop]==0) {
                	params[prop] = values[prop].toString();
                }
            }
        }
    }
    // prepare action invocation object
    var actionObj = {};
    if (typeof action == 'string') {
        var lastIdx = action.lastIndexOf('/');
        actionObj.executeResult = 'true';
        if (lastIdx != -1) {
            actionObj.namespace = action.substring(0, lastIdx);
            actionObj.action = action.substring(lastIdx + 1);
        }
        else {
            actionObj.namespace = '';
            actionObj.action = action;
        }
    }
    else {
        actionObj = action;
    }

    // prepare the DWR callback object
    var callbackObj = {};
    var mustCall = false;
    if (typeof callbackObjOrName == 'string') {
        callbackObj.callback = function(dt) {
            dwr.struts2.callback(dt, eval(callbackObjOrName));
        };
        mustCall = true;
    }
    else if (typeof callbackObjOrName == 'function') {
        callbackObj.callback = function(dt) {
            dwr.struts2.callback(dt, callbackObjOrName);
        };
        mustCall = true;
    }
    else if (typeof callbackObjOrName == 'object' && typeof callbackObjOrName.callback == 'function') {
            for (var prop in callbackObjOrName) {
                callbackObj[prop] = callbackObjOrName[prop];
            }
            callbackObj.callback = function(dt) {
                dwr.struts2.callback(dt, callbackObjOrName.callback);
            };
            mustCall = true;
        }
    if (mustCall) {
    	var _params = {};
    	for(var prop in params){
    		if(!prop.startWith("select_")) {
				_params[prop] = params[prop]; 
			}
    	}
    	
        DWRDispatcher.execute(actionObj, _params, callbackObj);
    }
};

/**
 * 从ActionUrl中获得执行参数，以JSON风格组织
 * @author: zhr_sh
 * @param actionurl
 * @param executeparams
 */
dwr.struts2.populateurlparams = function(actionurl, executeparams) {
    var pos = actionurl.indexOf("?") < 0 ? actionurl.length : actionurl.indexOf("?");
    actionurl = actionurl.substring(pos + 1, actionurl.length);
    var paramsarray = actionurl.split("&");
    for (var i = 0; i < paramsarray.length; i++) {
        var paramelement = paramsarray[i];
        var paramname = paramelement.substring(0, paramelement.indexOf("="));
        var paramvalue = paramelement.substring(paramelement.indexOf("=") + 1, paramelement.length);
        executeparams[paramname] = paramvalue;
    }
};

/**
 * 从Form中获得执行参数，以JSON风格组织
 * 多个同名的元素，用逗号分隔值
 * @author: zhr_sh
 * @param formobj
 * @param executeparams
 */
dwr.struts2.populateformparams = function(formobj, executeparams) { 
	for (var i = 0; i < formobj.elements.length; i = i + 1) {
		var e = formobj.elements[i];
		if (e.name == null || e.name == '') {
			continue;
		}
         
		if (e.type == "checkbox" || e.type == "radio") {
			if (!e.checked) {
				continue;
			}
		} 

		var _value = executeparams[e.name] || "";
		if(executeparams[e.name] != undefined){
			_value = _value + ",";
		}
		executeparams[e.name] = _value + e.value; 
	}
};
/**
 * @author: zhr_sh
 * 从Action分析namespache\method\actionname，并封装成DWR执行对象
 * @param actionurl
 */
dwr.struts2.parseactionurl = function(actionurl) {
    var namespace = "/";
    var actionname = "";
    var method = "";
    var pos = actionurl.lastIndexOf("/") < 0 ? 0 : actionurl.lastIndexOf("/");
    var pos2 = actionurl.lastIndexOf(separator);
    var pos3 = actionurl.indexOf("?") < 0 ? actionurl.length : actionurl.indexOf("?");
    if (pos >= 0) {
        namespace = actionurl.substring(0, pos);
    }
    if (pos >= 0 && pos2 >= 0) {
        actionname = actionurl.substring(pos + 1, pos2);
    }
    if (pos2 >= 0) {
        method = actionurl.substring(pos2 + 1, pos3);
    }
    var executeobj = {
        namespace:namespace,
        action:actionname,
        method:method,
        executeResult:"false"
    };
    return executeobj;
};
/**
 * @author: zhr_sh
 * 通过某个URL执行执行FORM表单,执行URL默认取Form的Action
 * @param formname   需要执行的FORM表单名称,注意是Id名称
 * @param callbackObjOrName  //回调函数名称或对象
 * @param needvalidate   //表单是否需要在提交前验证，这里会调用Struts2验证框架进行验证
 */
dwr.struts2.executeformnormal = function(formname, callbackObjOrName, needvalidate) {
    //    alert(1);
     var formobj ;
    if(typeof  formname=="string"){
      formobj = $(formname);
        }else{
       formobj=formname; 
    }


    if (typeof formobj != "object") {
        DWRActionUtil.errorinfo(formname + ":不是一个Form对象");
        return;
    }
    var actionurl = formobj.action;
    actionurl = actionurl.replace(".action", "");
    dwr.struts2.executeform(actionurl, formname, callbackObjOrName, needvalidate);
};
/**
 * @author: zhr_sh
 * 通过某个URL执行执行FORM表单
 * @param actionurl  Struts2 Action的URL，可以带参数
 * @param formname   需要执行的FORM表单名称,注意是Id名称
 * @param callbackObjOrName  //回调函数名称或对象
 * @param needvalidate   //表单是否需要在提交前验证，这里会调用Struts2验证框架进行验证
 */
dwr.struts2.executeform = function(actionurl, formname, callbackObjOrName, needvalidate) {
    if (arguments.length == 3) {
        dwr.struts2.executeformnormal(arguments[0], arguments[1], arguments[2]);
        return;
    }
    var formobj;
      if(typeof  formname=="string"){
      formobj = $(formname);
        }else{
       formobj=formname;
    }
      
  //  var formobj = $(formname);
    if (typeof formobj != "object") {
        DWRActionUtil.errorinfo(formname + ":不是一个Form对象");
        return;
    }
    var executeparams = {};
    //执行参数，JSON形式
    try {
        //从ActionURL中获得DWR执行的Action对象
        var executeobj = DWRActionUtil.parseactionurl(actionurl);
        //从ActionURL字符串中获得需要提交的参数
        DWRActionUtil.populateurlparams(actionurl, executeparams);
        //从Form中获得需要提交的参数
        DWRActionUtil.populateformparams(formobj, executeparams);
        //如果需要debug
        if (NEED_DEBUG) {
            var debug_str = "";
            var executeparams_str = "";
            var executeobj_str = "";
            for (var property in executeparams) {
                executeparams_str += property + ":" + executeparams[property] + "\n";
            }
            debug_str = "executeparams=" + executeparams_str;
            for (var property in executeobj) {
                executeobj_str += property + ":" + executeobj[property] + "\n";
            }
            debug_str = "executeparams={" + executeparams_str + "}\n" + "------------------------------\n";
            debug_str += "executeobj={" + executeobj_str + "}";

            DWRActionUtil.debuginfo(debug_str);

        }
    } catch(e) {
        DWRActionUtil.errorinfo("解析请求字符串异常 actionurl=" + actionurl);
        return;
    }
    //执行Struts2的表单验证框架的验证方法
    if (needvalidate) {
        try{
			var validate = eval("$DW.validateForm_" + formobj.name + "()");    //2010-6-28 todo 避免在使用DIALOG时无法正确进行前台校验
        }catch(e){
            try {
            	var validate = eval("validateForm_" + formobj.name + "()");
            }catch(e){
            	validate = true;// 说明没有验证函数，跳过验证
            }
        }
        if (!validate) return;
    }

    DWRActionUtil.execute(executeobj, executeparams, callbackObjOrName);
};

/**
 *
 * @author: zhr_sh
 * @param actionurl  Struts2 Action的URL，可以带参数
 * @param executeparams  需要传递的参数，JSON风格
 * @param callbackObjOrName  回调函数名称或对象
 */
dwr.struts2.executeaction = function(actionurl, executeparams, callbackObjOrName) {
    try {
        //从ActionURL中获得DWR执行的Action对象
        var executeobj = DWRActionUtil.parseactionurl(actionurl);
        //从ActionURL字符串中获得需要提交的参数
        DWRActionUtil.populateurlparams(actionurl, executeparams);
        //如果需要debug
        if (NEED_DEBUG) {
            var debug_str = "";
            var executeparams_str = "";
            var executeobj_str = "";
            for (var property in executeparams) {
                executeparams_str += property + ":" + executeparams[property] + "\n";
            }
            debug_str = "executeparams=" + executeparams_str;
            for (var property in executeobj) {
                executeobj_str += property + ":" + executeobj[property] + "\n";
            }
            debug_str = "executeparams={" + executeparams_str + "}\n" + "------------------------------\n";
            debug_str += "executeobj={" + executeobj_str + "}";

            DWRActionUtil.debuginfo(debug_str);

        }
    } catch(e) {
        DWRActionUtil.errorinfo("解析请求字符串异常 actionurl=" + actionurl);
        return;
    }
    DWRActionUtil.execute(executeobj, executeparams, callbackObjOrName);
};

/** Execute a remote request using DWR */
dwr.struts2.callback = function(dt, originalCallback) {
    if (dt.data) originalCallback(dt.data);
    else if (dt.text) originalCallback(dt.text);
    else originalCallback(dt);
};

/** Utility to check to see if the passed object is an input element / element id */
dwr.struts2.isElement = function(elementOrId) {
    if (typeof elementOrId == "string" && elementOrId != "") {
        return true;
    }
    if (elementOrId.nodeName) {
        var name = elementOrId.nodeName.toLowerCase();
        if (name == 'input' || name == 'form') {
            return true;
        }
    }
  
    return false;
};


dwr.struts2.errorinfo = function(str) {
    //    alert("DWR ERROR:--------------------------\n" + str);
    Dialog.alert("DWR ERROR:--------------------------\n" + str);
};
dwr.struts2.debuginfo = function(str) {
    //    alert("DWR DEBUG:--------------------------\n" + str);
    Dialog.alert("DWR DEBUG:--------------------------\n" + str);
};

dwr.struts2.debugObj = function(obj) {
    var debu_str ;
    for (var property in obj) {
        debu_str += property + ":" + obj[property] + "\n";
    }
    DWRActionUtil.debuginfo(debu_str);
};

dwr.struts2.showErrMsg = function(obj) {  //ALERT,ERR信息
    Dialog.alert(dwr.struts2.getErrMsg(obj));
}

dwr.struts2.getErrMsg = function(obj) {  //获得全部的ERR信息

    var errstr = "" ;

    var fieldErrors = obj.fieldErrors;
    var actionErrors = obj.actionErrors;
    if (fieldErrors != null) {
        for (var property in fieldErrors) {
			var errMsgs = fieldErrors[property];
			for(var i=0;i<errMsgs.length;i++) {
            	errstr += errMsgs[i] + "<br>";
            }
        }
    }
    if (actionErrors != null) {
        for (var property in actionErrors) {
            if (typeof(eval(actionErrors[property])) != "function") {

                errstr += actionErrors[property];
            }
        }
    }
    if (obj.msg != null) {
        errstr += obj.msg;
    }
    return errstr;

}
