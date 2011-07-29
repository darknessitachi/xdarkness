/**
 * @class Dialog.Alert 消息提示窗口
 * @param {Object} config
 */
Dialog.Alert = Dialog.extend({

    constructor: function(config){
    	this.base(config);
    	
        this.ID = "_DialogAlert" + $TW.Dialog.AlertNo++;
        this.ParentWindow = $TW;
        this.Width = config.w ? config.w : 300;
        this.Height = config.h ? config.h : 90;
        this.Title = "系统提示";
        this.URL = "javascript:void(0);";
        this.AlertFlag = true;
        this.msg = config.msg || "";
        
        var _instance = this;
        this.CancelEvent = function(){
            _instance.close();
            if (config.func) {
                config.func();
            }
        };
    },
    initBodyContent: function() {
		var icon_alert = Dialog.ImagePath + "icon_alert.gif";
		this.bodyContent = "<div id='_DialogFrame_" + this.ID + "'><table border='0' align='center' cellpadding='10' cellspacing='0'>"
			+ "<tr><td align='right'><img id='Icon' src='" + icon_alert + "'  align='absmiddle'></td>"
			+ "<td align='left' id='Message' style='font-size:9pt'>" + this.msg + "</td></tr></table><div>";
	},
    show: function(){
		this.superMethod();

        $TW.$("_AlertBGDiv").show();

        if ($TW.$("_ButtonOK_" + this.ID)) 
            $E.hide($TW.$("_ButtonOK_" + this.ID));

        this.CancelButton.value = "确 定";
        this.CancelButton.focus();
        $TW.$("_DialogButtons_" + this.ID).style.textAlign = "center";
    }
});

Dialog.AlertNo = 0;

Dialog.alert = function(msg, func, w, h){

    new Dialog.Alert({
        msg: msg,
        func: func,
        w: w,
        h: h
    }).show();
}
