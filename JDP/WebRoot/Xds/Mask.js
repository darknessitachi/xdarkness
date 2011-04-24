var Mask = {

	/**
	 * 通过在当前页面加入透明层的方式阻止页面继续响应事件，主要用于防止用户两次点击按钮
	 */
	show : function() {
		var bgdiv = $("_WaitBGDiv");
		if (!bgdiv) {
			var bgdiv = document.createElement("div");
			bgdiv.id = "_WaitBGDiv";
			$E.hide(bgdiv);
			$T("body")[0].appendChild(bgdiv);
			bgdiv.style.cssText = "background-color:#333;position:absolute;left:0px;top:0px;opacity:0.03;filter:alpha(opacity=3);width:100%;height:100%;z-index:991";
		}
		var mh = Math.max(document.documentElement.scrollHeight,
				document.body.scrollHeight);
		bgdiv.style.height = mh + "px";
		$E.show(bgdiv);
	},

	hide : function() {
		var bgdiv = $("_WaitBGDiv");
		if (bgdiv) {
			$E.hide(bgdiv);
		}
	}
};