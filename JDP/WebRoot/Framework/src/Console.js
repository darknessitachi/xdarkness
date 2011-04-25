
var Console = {};
Console.info = [];

Console.log = function(str){
	Console.info.push(str);
	if(Console.info.length>1200){
		Console.info.splice(1000,Console.info.length-1000);
	}
	if(Console.isShowing){
		//立即显示信息
	}
}

Console.show = function(){
	var html = [];
	html.push("<textarea class='input_textarea' style='width:600px;height:200px'>");
	for(var i=0;i<Console.info.length;i++){
		html.push(htmlEncode(Console.info[i]));
		html.push("<br>");
	}
	html.push("</textarea>");
	Dialog.alert(html.join('\n'),700,250);
}
