
DateValidator = Validator.extend({
	type:"date", 
	doValidate:function (value) {
		//value = Date.parse(value);
		                           
		if (isDate(value)) {
			return "";
		}
		
		return "'" + value + "'必须是正确的日期.";
	}
});
