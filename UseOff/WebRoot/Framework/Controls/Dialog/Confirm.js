
/**
 * @class Dialog.Confirm 消息确认窗口
 * @param {Object} config
 */
Dialog.Confirm = Dialog.extend({

    constructor: function(config){
		this.base(config);
		
		this.ID = "_DialogAlert" + $TW.Dialog.AlertNo++;
		this.Width = config.w ? config.w : 300;
        this.Height = config.h ? config.h : 90;
		this.Title = "信息确认";
    	this.URL = "javascript:void(0);";
    	this.AlertFlag = true;
		this.msg = config.msg||"";
		
		var instance = this;
    	this.CancelEvent = function(){
        	instance.close();
        	if (config.func2) {
        	    config.func2();
       		}
    	};
    	this.OKEvent = function(){
        	instance.close();
        	if (config.func1) {
            	config.func1();
        	}
    	};
	},
	initBodyContent: function() {
		var icon_query = Dialog.ImagePath + "icon_query.gif";
		
		this.bodyContent = "<div id='_DialogFrame_" + this.ID + "'><table height='100%' border='0' align='center' cellpadding='10' cellspacing='0'>"
	    	+ "<tr><td align='right'><img id='Icon' src='" + icon_query + "' width='34' height='34' align='absmiddle'></td>"
	    	+ "<td align='left' id='Message' style='font-size:9pt'>" + this.msg + "</td></tr></table></div>";
	},
	show: function() {
		this.superMethod();
		
		$TW.$("_AlertBGDiv").show();
	    
	    this.CancelButton.focus();
	    $TW.$("_DialogButtons_" + this.ID).style.textAlign = "center";
	}
});	

Dialog.confirm = function(msg, func1, func2, w, h){
	
    new Dialog.Confirm({
		msg: msg,
		func1: func1,
		func2: func2,
		w: w,
		h: h
	}).show();
}
