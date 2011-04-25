
NotNullValidator = Validator.extend({
    type: "NotNull", 

    doValidate: function(value,srcElement,name) {
    
		if (value==null||value=="") {
			if(srcElement.$A("xtype")&&srcElement.$A("xtype").toLowerCase()=="select"){
				if(srcElement.value.length==0){//可输入时已经有字符了但value值为空
					return "必须选择"+name;
				}
			}
	
			return name+"不能为空";
		}
	
		return "";
    }
});
