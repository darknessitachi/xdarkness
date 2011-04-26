

var Login = {
	parameter : "?",
	newLine : "<br />",
	sendPage : "webim/login.jsp",
	redirectPage : "webim/im.jsp",

	toggleOperate : function() {
		if (event.keyCode == "13") {
			if (!$("trConfirm").visible()) {
				document.getElementById("btnLogin").click();
				document.getElementById("txtUserPwd").blur();
			} else {
				document.getElementById("btnRegister").click();
				document.getElementById("txtConfirmPwd").blur();
			}
		}
	},

	constructors : function() {
		this.parameter = "?";
		return this;
	},

	excuteAccount : function(operate) {
		switch (operate) {
		case 0:
			break;
		case 1:
			break;
		}

		if (this.checkAccount(operate)) {
			$.ajax( {
				type : "GET",
				cache : false,
				dataType : "html",
				ifModified : true,
				url : this.sendPage + this.parameter,
				success : function(result) {
					if (result.substring(0, 1) == ",") {
						$("#hidLoginer").val(result.substring(1));
						document.getElementById("btnHidLogin").click();
						window.location.href = Login.redirectPage + "?login=" + hidLoginer.Value;//result.substring(1);
			} else {
				Effect.appendDiv(result, true);

				if (result == "<br />注册成功！") {
					Layout.resetInput("1");
				} else {
					Layout.resetInput("0");
				}
			}
		}
			});
		}
	},

	checkAccount : function(operate) {
		var result = true;
		var description = "";
		var width = 230;

		this.parameter += "operate=" + operate;
		this.parameter += "&sex=" + escape($("curSex").text);

		if ($("txtLoginName").value.trim().length == 0) {
			description += "<br/>用户名不能为空！" + this.newLine;
		} else if (cannotValid($("#txtLoginName").val())) {
			description += "<br/>用户名不能包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符!";
			width = 400;
		} else {
			this.parameter += "&loginname="
					+ escape($("#txtLoginName").val().trim());
		}

		if ($("#txtUserPwd").val().length == 0) {
			description += "<br/>密码不能为空！" + this.newLine;
		} else if (cannotValid($("#txtUserPwd").val())) {
			description += "<br/>密码不能包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符!";
			width = 400;
		} else {
			this.parameter += "&userpwd=" + escape($("#txtUserPwd").val());
		}

		if (operate == 0) {
			if ($("#txtUserName").val().trim().length == 0) {
				description += "<br/>中文名不能为空！" + this.newLine;
			}
			if ($("#txtUserName").val().length > 10) {
				description += "<br/>中文名长度不能大于10个字符！" + this.newLine;
				width = 250;
			} else if (cannotValid($("#txtUserName").val())) {
				description += "<br/>中文名不能包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符!";
				width = 400;
			} else {
				this.parameter += "&username="
						+ escape($("#txtUserName").val().trim());
			}

			if ($("#txtConfirmPwd").val().length == 0) {
				description += "<br/>确认密码不能为空！" + this.newLine;
			} else if (cannotValid($("#txtConfirmPwd").val())) {
				description += "<br/>确认密码不能包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符!";
				width = 400;
			}
			if ($("#txtUserPwd").val() != $("#txtConfirmPwd").val()) {
				description += "<br/>两次输入的密码不一致！" + this.newLine;
			}
		}

		if (description.length > 0) {
			result = false;
			Effect.appendDiv(this.newLine + description, true, width);
		}

		return result;
	}
};