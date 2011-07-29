
ValidatorFramework = {
	
	validators:[], 
	crossValidators:[], 
	
	constructor:function () {
	}, rigisterValidator:function (validator){
		ValidatorFramework.validators[validator.type] = validator;
	}, 
	/* 邮件|email && NotNull && Length>=9 */
	validateField: function (evt,ele) {
		
		if(!ele){
			evt = getEvent(evt);
			ele = $(evt.srcElement);
		}
		
		ele = $(ele);
		var v = ele.$A("valid");
		if(!v){//valid属性可能有变动
			ValidatorFramework.closeTip(ele);
			return true;
		}
		var condition = ele.$A("condition");
		if(condition && !eval(condition)){
			ValidatorFramework.closeTip(ele);
			return;
		}
		var msg = [];
	
		var anyFlag = false;
		
		var features = v.split("\&\&");	
			
		var value = $V(ele);
		if(ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"){
			value = $V(ele.parentElement);
		}
		if(value){
			value = (""+value).trim();
		}
		
		for(var i = 0; i < features.length; i++) {
			
			var arr = features[i].split("\|");
			
			var name = "";
			var rule;
			if(arr.length==2){
				name = arr[0];
				rule = arr[1];
			}else{
				rule = features[i];
			}
			var op = "=";
			
			if(rule.indexOf("=")<0){
				if(rule.indexOf('>') > 0) {
					op = ">";
				}else if (rule.indexOf('<') > 0) {
					op = "<";
				}		
			}else{
				if(rule.charAt(rule.indexOf("=")-1)=='>') {
					op = ">=";
				}else if(rule.charAt(rule.indexOf("=")-1)=='<') {
					op = "<=";
				}		
			}
			var fName = null;
			var fValue = null;
			if(rule.indexOf(op)>0) {
				fName = rule.substring(0,rule.indexOf(op));
				fValue = rule.substring(rule.indexOf(op)+1);
			}else{
				fName = rule;
			}
			
			fName = fName.trim();
			if(fName=="Any") {
				anyFlag = true;
			}else if (fName=="Regex") {
				fValue = rule.substring(6);
				if (value==null||value==""||!fValue) {continue;}
				var reg = fValue;
				if(!reg.startWith("^")){
					reg = "^"+reg;
				}
				if(!reg.endWith("$")){
					reg += "$";
				}
				if(!new RegExp(reg).test(value)){
					msg.push(name);
				}
			}else if (fName=="Script") {
				if (!fValue) {continue;}
				if(!eval(fValue)){
					msg.push(name);
				}
			}else{
				var errMsg = ValidatorFramework.validate(name, fName, value, ele, op, fValue);
				if(errMsg != ""){
					msg.push(errMsg);
				}
			}
		}
		
		/*if(!anyFlag&&ele.$A("xtype")&&ele.$A("xtype").toLowerCase()=="select"&&ele.parentNode.input){
			if(!ele.parentNode.$A("listURL")&&!Selector.verifyInput(ele)){
				msg.push("输入的值不是可选项");
			}
		}*/

		if(msg.length>0){
			var txt = msg.join("<br>");
			if(txt!=ele._VerifyMsg){
				ValidatorFramework.closeTip(ele);
				var tip;
				var afterEle = ele.$A("element");
				if(afterEle){
					tip = Tip.show($(afterEle),txt);
				}else{
					tip = Tip.show(ele,txt);
				}
				
				ele._VerifyTip = tip;
				ele._VerifyMsg = txt;
			}
			return false;
		}else{
			ValidatorFramework.closeTip(ele);
			return true;
		}
		
	},
	initCtrl: function(ele){
		ele = $(ele);
		//ele.attachEvent("onfocus",Verify.autoCloseOther);
		var v = ele.$A("valid");
		if(v){
			var validatorFramework = ValidatorFramework;
			ele.attachEvent("onfocus",validatorFramework.validateField);
			ele.attachEvent("onkeyup",validatorFramework.validateField);
			ele.attachEvent("onchange",validatorFramework.validateField);
			ele.attachEvent("onblur",validatorFramework.closeTip);
			var condition = ele.$A("condition");
			if(v.indexOf("NotNull")>=0&&!condition){
				var xtype = ele.$A("xtype");
				if(xtype){
					xtype = xtype.toLowerCase();
				}
				if(xtype=="select"){
					ele = ele.getParent("div");
				}
				if(xtype=="date"||xtype=="time"){
					ele = ele.nextSibling;
				}
				if(!ele.nextSibling||!ele.nextSibling.getAttribute||ele.nextSibling.getAttribute("xtype")!="Verify"){
					var display = '';
					if(!$E.visible(ele)){
						display = 'display:none';
					}
					ele.insertAdjacentHTML("afterEnd","<span style='color:red;padding-left:2px;padding-top:13px;"+display+"' xtype='Verify'>*</span>");
				}
			}
		}
	},
	validateForm:function (form) {
		var retval = true;
		for (var i = 0; i < form.length; i++) {
			
			var valid = ValidatorFramework.validateField(null, form[i]);
		
			if (!valid) {
				retval = false;
			}
		}
		
		for (var i = 0; i < ValidatorFramework.crossValidators.length; i++) {
			ValidatorFramework.crossValidators[i].clearErrors();
		}
		if (retval) {
			for (i = 0; i < ValidatorFramework.crossValidators.length; i++) {
				valid = ValidatorFramework.crossValidators[i].validate();
				if (!valid) {
					retval = false;
				}
			}
		}
		
		return retval;
	}, 
	initForm: function(form){
		form = $(form);
		
		for (var i = 0; i < form.length; i++) {
		  ValidatorFramework.initCtrl(form[i]);
		}
		addEvent(form,'submit',function(event) {
          event = getEvent(event);
          var form = event.target || event.srcElement;
          if (!ValidatorFramework.validateForm(form))
            return false;
        });
	},
	validate:function (name, type, value, srcElement, op, fValue) {
		var validator = ValidatorFramework.validators[type];
		if (!validator) {
			alert("找不到'" + type + "'类型的验证器.");
			return "";
		}
		return validator.validate(value,srcElement,name,op,fValue);
	},closeTip: function(ele,evt){
		if(!ele){
			evt = getEvent(evt);
			ele = $(evt.srcElement);
		}
		if(ele.type == "blur"){
			ele = $(ele.srcElement);
		}
		if(ele._VerifyTip){
			ele._VerifyTip.close();
			ele._VerifyTip = null;
			ele._VerifyMsg = null;
		}
	}
}

ValidatorFramework.rigisterValidator(new Validator());
ValidatorFramework.rigisterValidator(new NumberValidator());
ValidatorFramework.rigisterValidator(new DateValidator());
ValidatorFramework.rigisterValidator(new DateTimeValidator());
ValidatorFramework.rigisterValidator(new TimeValidator());
ValidatorFramework.rigisterValidator(new EmailValidator());
ValidatorFramework.rigisterValidator(new NotNullValidator());
ValidatorFramework.rigisterValidator(new IntValidator());
ValidatorFramework.rigisterValidator(new LengthValidator());
