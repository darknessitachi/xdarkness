
DateTimeValidator = Validator.extend({
	type:"DateTime", 
	doValidate:function (value,srcElement,name) {
		if(isDateTime(value)){
			return "";
		}
		
		return name + " " + value+"必须是正确的日期";
	}
});
