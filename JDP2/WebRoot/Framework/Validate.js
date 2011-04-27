

DateTimeValidator = {
	type:"datetime",
	doValidate:function (value,name) {
		if(isDateTime(value)){
			return "";
		}
		
		return name + " " + value+"��������ȷ������";
	}
};


DateValidator = {
	type:"date", 
	doValidate:function (value) {
		                           
		if (isDate(value)) {
			return "";
		}
		
		return "'" + value + "'��������ȷ������.";
	}
};


EmailValidator = {
    type: "email", 

    doValidate: function(value,name) {
      var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      
      if (pattern.test(value)) {
        return "";
      }
      return name + "'" + value + "'������Ч�ĵ����ʼ���ַ." ;
    }
};


IntValidator = {
	type:"int", 
	doValidate:function (value) {
		if(isInt(value)){
			return "";
		}
		return value+"����������";
	}
};


LengthValidator = {
	type:"Length", 
	doValidate:function (value,name,op,fValue) {
	
		if(String.isInstance(fValue)) fValue = fValue.trim();
		
		if(isNaN(fValue)) {
			return "У��������Length�������������";
		}

		try {
			var len = parseInt(fValue);
			if(op=="="&&value.length!=len) {
				return name+"���ȱ�����" + len;
			}else if (op==">"&&value.length<=len) {
				return name+"���ȱ������" + len;
			}else if (op=="<"&& value.length>=len) {
				return name+"���ȱ���С��" + len;
			}
		} catch (ex) {
			return "У��������Length�������������"+ex.message;
		}
		
		return "";
	}
};


NotNullValidator = {
    type: "NotNull", 

    doValidate: function(value,name) {
    
		if (value==null||value=="") {
	
			return name+"����Ϊ��";
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
      
      return name + "'" + value + "' ������Ч������." ;
    }
};


TimeValidator = {
    type: "Time", 

    doValidate: function(value) {
		if(isTime(value)){
			return "";
		}
		return "ֵ"+value+"������ȷ��ʱ��!";
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
      
/** ��ʾ��Ϣ */
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
						/* ����ONGLע��ֵ�����Ĵ��� */
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
