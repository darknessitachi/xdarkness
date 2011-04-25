
LengthValidator = Validator.extend({
	type:"Length", 
	doValidate:function (value,srcElement,name,op,fValue) {
	
		fValue = fValue.trim();
		
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
});
