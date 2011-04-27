
IntValidator = Validator.extend({
	type:"int", 
	doValidate:function (value) {
		if(isInt(value)){
			return "";
		}
		return value+"必须是整数";
	}
});
