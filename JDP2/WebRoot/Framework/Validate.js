

DateTimeValidator = {
	type:"datetime",
	doValidate:function (value,name) {
		if(isDateTime(value)){
			return "";
		}
		
		return name + " " + value+"必须是正确的日期";
	}
};


DateValidator = {
	type:"date", 
	doValidate:function (value) {
		                           
		if (isDate(value)) {
			return "";
		}
		
		return "'" + value + "'必须是正确的日期.";
	}
};


EmailValidator = {
    type: "email", 

    doValidate: function(value,name) {
      var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      
      if (pattern.test(value)) {
        return "";
      }
      return name + "'" + value + "'不是有效的电子邮件地址." ;
    }
};


IntValidator = {
	type:"int", 
	doValidate:function (value) {
		if(isInt(value)){
			return "";
		}
		return value+"必须是整数";
	}
};


LengthValidator = {
	type:"Length", 
	doValidate:function (value,name,op,fValue) {
	
		if(String.isInstance(fValue)) fValue = fValue.trim();
		
		if(isNaN(fValue)) {
			return "校验规则错误，Length后面必须是数字";
		}

		try {
			var len = parseInt(fValue);
			if(op=="="&&value.length!=len) {
				return name+"长度必须是" + len;
			}else if (op==">"&&value.length<=len) {
				return name+"长度必须大于" + len;
			}else if (op=="<"&& value.length>=len) {
				return name+"长度必须小于" + len;
			}
		} catch (ex) {
			return "校验规则错误，Length后面必须是整数"+ex.message;
		}
		
		return "";
	}
};


NotNullValidator = {
    type: "NotNull", 

    doValidate: function(value,name) {
    
		if (value==null||value=="") {
	
			return name+"不能为空";
		}
	
		return "";
    }
};


NumberValidator = {
    type: "number", 

    doValidate: function(value,name) {
      var numberPattern=/(^\d+$)|(^\d+\.\d+$)/;
      
      if (numberPattern.test(value)) {
        return "";
      }
      
      return name + "'" + value + "' 不是有效的数字." ;
    }
};


TimeValidator = {
    type: "Time", 

    doValidate: function(value) {
		if(isTime(value)){
			return "";
		}
		return "值"+value+"不是正确的时间!";
    }
};

Validator = {};

Validator.validateNumber = function(value,name) {
	return NumberValidator.doValidate(value, name);
}

Validator.validateDate = function(value,name) {
	return DateValidator.doValidate(value, name);
}

Validator.validateDateTime = function(value,name) {
	return DateTimeValidator.doValidate(value, name);
}

Validator.validateTime = function(value,name) {
	return TimeValidator.doValidate(value, name);
}

Validator.validateEmail = function(value,name) {
	return EmailValidator.doValidate(value, name);
}

Validator.validateNotNull = function(value,name) {
	return NotNullValidator.doValidate(value, name);
}

Validator.validateInt = function(value,name) {
	return IntValidator.doValidate(value, name);
}

Validator.validateLength = function(value,name,op,fValue) {
	return LengthValidator.doValidate(value,name,op,fValue);
}

TIP_MAP = {};
      
/** 显示消息 */
function showMessage(id, msg) {
	
	if(msg != "") {
		if(TIP_MAP[id]) {
			TIP_MAP[id].close();
		} 
		
		var tip = Tip.show(id, msg);
		TIP_MAP[id] = tip;
	} else {
		if(TIP_MAP[id]){
			TIP_MAP[id].close();
		}
	}
}
      
Message = {
	show: function(data, type) {
		
		for(var id in TIP_MAP) {
			TIP_MAP[id].close();
		}
		
		if(type == "tip") {
			var fieldErrors = data.fieldErrors;
		    if (fieldErrors != null) {
		        for (var property in fieldErrors) {
					var errMsgs = fieldErrors[property];
					var msg = "";
					for(var i=0;i<errMsgs.length;i++) {
						/* 过滤ONGL注入值发生的错误 */
						if(errMsgs[i].indexOf("Invalid") == -1)
		            		msg += errMsgs[i] + "<br/>";
		            }
		            showMessage(property, msg);
		        }
		    }
		} else {
			var msg = data.msg || dwr.struts2.getErrMsg(data);
     		Dialog.alert(msg);
		}
	}
};
