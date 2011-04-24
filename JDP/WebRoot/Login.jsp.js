
function login(){
	if($V("UserName").trim()==""||$V("Password").trim()==""){
		alert("请输入用户名和密码");
		return;
	}
	var dc = Form.getData("form1");
	Server.sendRequest("com.xdarkness.platform.page.LoginPage.submit",dc,function(response){
		if(response&&response.Status==0){
			alert(response.Message);
			if($("spanVerifyCode").innerText.trim()==""){
				var sb = [];
				sb.push("&nbsp;验证码：");
				sb.push("<img src=\"AuthCode.jsp?Height=18&Width=60\" alt=\"点击刷新验证码\" height=\"18\" ");
				sb.push("align=\"absmiddle\" style=\"cursor:pointer;\" ");
				sb.push("onClick=\"this.src='AuthCode.jsp?Height=18&Width=60&'+new Date().getTime()\" />&nbsp; <input ");
				sb.push("name=\"VerifyCode\" type=\"text\" style=\"width:60px\" id=\"VerifyCode\" ");
				sb.push("class=\"inputText\" onfocus=\"this.select();\"/>");
				$("spanVerifyCode").innerHTML = sb.join('');
			}
			$("UserName").style.width = "80px";
			$("Password").style.width = "80px";
		}
	});
}
document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		login();
	}
}
Page.onLoad(function(){
	if(window.top.location != window.self.location){
		window.top.location = window.self.location;
	}else{
		$("UserName").focusEx();
		$S("UserName","admin");
		$S("Password","admin");
	}
});

