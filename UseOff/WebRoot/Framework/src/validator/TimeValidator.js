
TimeValidator = Validator.extend({
    type: "Time", 

    doValidate: function(value) {
		if(isTime(value)){
			return "";
		}
		return "值"+value+"不是正确的时间!";
    }
});
