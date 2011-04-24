var Layout = {
	/**
	 * 设置登录/注册窗体居中
	 */
	setLayoutPostion : function() {
		
		$("modal").center();

		if(!$("trConfirm").visible()){
			$("trEmpty").show();
		} else {
			$("trEmpty").hide();
		}
	},

	/**
	 * 显示登录窗体、隐藏注册相关选项，设置状态为登录
	 * @memberOf {TypeName} 
	 */
	redirectToLogin : function() {
		if (!$("trEmpty").visible()) {
			this.resetInput("1");

			$("trSex").hide();
			$("trConfirm").hide();
			$("btnCancel").hide();
			$("trUserName").hide();
			$("trEmpty").show();
		} else {
			var login = Login.constructors();
			login.excuteAccount(1);
		}
	},

	/**
	 * 显示注册窗体、显示注册相关选项，设置状态为注册
	 * @memberOf {TypeName} 
	 */
	redirectToRegister : function() {
		if (!$("trEmpty").visible()) {
			var login = Login.constructors();
			login.excuteAccount(0);
		} else {
			this.resetInput("1");

			$("trSex").show();
			$("trConfirm").show();
			$("btnCancel").show();
			$("trUserName").show();
			$("trEmpty").hide();
		}
	},

	resetInput : function(flag) {
		if (flag == "0") {
			$({xtype: 'password'}).clear();
		} else {
			$({xtype: ['text','password']}).clear();

			$('curSex').text = "男";
		}
	},

	toggleSex : function() {
		var userSexPanel = $('curSex');
		var userSex = $("divSex");

		userSex.find("a").each(function() {
			$(this).width = 178;
		});

		userSex.css("display", "block");
		userSex.css( {
			"top" : userSexPanel.getPosition().y + userSexPanel.getHeight() + 5,
			"left" : userSexPanelgetPosition().x
		});
	},

	setUserSex : function(sex) {
		$("curSex").text = sex;
	},

	removeSexPanel : function() {
		$("divSex").hide();
	},

	getTheme : function() {
		var cookiesArr = document.cookie.split(";");

		for ( var i = 0; i < cookiesArr.length; i++) {
			var arr = cookiesArr[i].split("=");

			if (arr[0].replace(/\s/, '') == "webImTheme") {
				return arr[1];
			}
		}
	}
};

document.oncontextmenu = function() {
	return false;
}

Page.onLoad(function() {
	Layout.setLayoutPostion();

	var theme = Layout.getTheme() || "dark";
	$("themeCss").href = "include/themes/" + theme + "/style.css";

});

onWindowResize(function() {
	Layout.setLayoutPostion();
});
