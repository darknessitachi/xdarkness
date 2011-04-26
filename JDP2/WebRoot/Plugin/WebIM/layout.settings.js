	
var LayoutSettings = {
    createSettingsPanel: function()
    {
        if(document.getElementById("divSettings"))
            return;
            
        var panelHtml = "<div id=\"divSettings\" class=\"dialog\" >";
        panelHtml +=            "<div id=\"divSettings_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"divSettings_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"divSettings_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"divSettings_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"divSettings_top\" class=\"dialog_title title_window top_draggable\">系统设置</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td id=\"divSettings_ne\" class=\"dialog_ne\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"divSettings_row2\" class=\"mid table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_w\"/>";
        panelHtml +=                        "<td valign=\"top\">";
        panelHtml +=                            "<div id=\"divSettings_content\" class=\"dialog_content\" style=\"width: 380px;\">";
        panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding: 14px 14px 10 14px; width: 100%;font-size:12px;\">";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 2px; padding-top: 4px;padding-left: -2px;\" >个性签名：</div>";
        //panelHtml +=                                    "<textarea style=\"width:262px;height:50px;overflow: auto;\" id=\"txtScratch\" type=\"text\" onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" maxlength=\"16\" style=\"width: 110px;\"/>";
        panelHtml +=                                    "<input style=\"width:262px;\" id=\"txtScratch\" type=\"text\" onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" maxlength=\"16\"/>";

        if($("#hidCnSender").attr("outter") == 0)
        {
            panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 2px; padding-top: 4px;padding-left: -2px;\" >中&nbsp;文&nbsp;&nbsp;名：</div>";
            panelHtml +=                                    "<input type=\"text\" style=\"width:262px;\" id=\"txtUserName\" value=\"\" maxlength=\"10\"/>";
        }
        else
        {
            //panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 2px; padding-top: 4px;padding-left: -2px;\" >中 文 名：</div>";
            panelHtml +=                                    "<input type=\"text\" style=\"width:262px;display: none; \" id=\"txtUserName\" value=\"\" maxlength=\"10\"/>";
        
        }
        
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 5px; padding-top: 4px;\">个性头像：</div>";
        panelHtml +=                                    "<div style=\"padding-top: 4px;\"><a id=\"showDivImg\" href=\"#\" style=\"padding-top: 4px;\" onclick=\"LayoutSettings.showDivImg();\"  style=\"width: 263px;\"/></div>";
        panelHtml +=                                    "<div id=\"divImg\" style=\"margin-right: 0px;border:1px;border-color:Highlight;padding-top: 4px;padding-left:69px;display:none;\">";
        panelHtml +=                                        "<table border=\"0\">";
        panelHtml +=                                            "<tr height=\"30\">";
        panelHtml +=                                                "<td  height=\"200\">";
        panelHtml +=                                                    "<div id=\"defaultImgs\" style=\"width:160px;height:200px;border:1px;overflow:auto;background-color:#FFFFFF;\"></div>";
        panelHtml +=                                                "</td>";
        panelHtml +=                                                "<td style=\"font-size:11px;padding: 10 10 10 10;\">";
        panelHtml +=                                                   "&nbsp;预览：<br/>";
        panelHtml +=                                                   "<input type=\"image\" id=\"viewImg\" src=\"\" style=\"width:32px;height:32px;\" value=\"本地上传\"/>";
        panelHtml +=                                                   "<div id=\"uploadbox\" style=\"margin-right: 0px;padding-top: 4px;\">";
        panelHtml +=                                                        "<div class='uploadcontrol'><iframe frameborder=\"0\" src=\"upload.aspx?id=0\" id=\"ifUpload0\" name=\"ifUpload0\" frameborder=\"no\" scrolling=\"no\" style=\"width:0px; height:0px;\"></iframe>";
	    panelHtml +=                                                        "<div class=\"uploading\" style=\"height:20px;\" id=\"uploading0\" style=\"display:none;\" />";
	    panelHtml +=                                                        "<div class=\"image\" style=\"height:0px;\" id=\"panelViewPic0\" style=\"display:none;\" /></div>";
	    panelHtml +=                                                    "</div>";
        panelHtml +=                                                    "<input type=\"button\" value=\"本地上传\" onclick=\"LayoutSettings.uploadImg();\"/>";
        panelHtml +=                                                "</td>";
        panelHtml +=                                            "</tr>";
        panelHtml +=                                        "</table>";
        panelHtml +=                                    "</div>";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 2px; padding-top: 4px;\">回复信息：</div>";
        //panelHtml +=                                    "<div style=\"padding-top: 4px;\"><textarea style=\"width:262px;height:50px;padding-top: 4px;overflow: auto;\" id=\"txtRevertMessage\" maxlength=\"50\"  onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/></div>";
        panelHtml +=                                    "<div><input style=\"width:262px;\" id=\"txtRevertMessage\" maxlength=\"16\" value=\"\" /></div>";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 5px; padding-top: 4px;\">主题皮肤：</div>";
        panelHtml +=                                    "<div style=\"padding-top: 4px;\"><a id=\"theme\" href=\"#\" style=\"padding-top: 0px;align:left\" onclick=\"LayoutSettings.toggleThemes();\" onblur=\"LayoutSettings.removeThemes();\" style=\"width: 263px;\"/></div>";
        
        panelHtml +=                                "</div>";
        panelHtml +=                                "<div id=\"divSettings_buttons\" class=\"div_buttons\">";
        panelHtml +=                             "<a onclick=\"LayoutSettings.saveSettings();\" href=\"#\" class=\"buttons\">保存</a><a  onclick=\"LayoutSettings.closeSettingsPanel();\" href=\"#\" class=\"buttons\">取消</a>";
        panelHtml +=                                "</div>";
        panelHtml +=                            "</div>";
        
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_e\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"divSettings_row3\" class=\"bot table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_sw bottom_draggable\"/>";
        panelHtml +=                        "<td class=\"dialog_s bottom_draggable\">";
        panelHtml +=                            "<div id=\"divSettings_bottom\" class=\"status_bar\">";
        panelHtml +=                                "<span style=\"float: left; width: 1px; height: 1px;\"/>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_se bottom_draggable\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=           "</table>";
        panelHtml += "</div>";
            
        $(document.body).append(panelHtml);
        
        LayoutSettings.creatThemes();
        
        $("#divSettings").draggable({containment: 'parent' });
        
        
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        
        var width = 384;
        var height = document.getElementById("divSettings").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#divSettings").css("top",top);
        $("#divSettings").css("left",left);
        $("#divSettings").css("width",width);
        
        LayoutSettings.getSettings();
        LayoutSettings.createDefaultImgs();
    },
    
    showDivImg: function()
    {
        if($("#divImg").css("display") == "none")
        {
            $("#divImg").fadeIn("fast");
        }
        else
        {
            $("#divImg").fadeOut("fast");
        }
    },
    
    closeSettingsPanel: function() 
    {
        document.body.removeChild(document.getElementById('divSettings'));
    },
    
    getSettings: function()
    {
        $.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "settings.aspx",
            data: "method=getSettings" + 
              "&userId=" + $("#hidSender").val(),
              
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br />读取用户设置失败，请稍候再试！", true);
                }
                else
                {
                    //Effect.appendDiv("<br />" + result, true);
                    var messages = result.split(",");
                    
                    //$("#txtScratch").text( messages[0]);
                    //$("#txtRevertMessage").text(messages[1]);
                    $("#txtScratch").attr("value",messages[0]);
                    $("#txtRevertMessage").attr("value",messages[1]);
                    $("#txtUserName").attr("value",messages[4]);
                    
                    LayoutSettings.currentHeadImg = messages[2];
                    if(messages[2].length <= 6)
                    {
                        document.getElementById("viewImg").src = "../include/images/head/" + messages[2];
                    }
                    else
                    {
                        document.getElementById("viewImg").src = "../upload/head/" + messages[2];
                    }
                    
                    $("#theme").text(messages[3]);
                }
            }
        });
    },
    
    uploadImg: function()
    {
        
        document.frames["ifUpload0"].document.getElementById("fileImg").click();
    },
    
    saveSettings: function()
    {
        LayoutSettings.saveOtherSettings(LayoutSettings.currentHeadImg);
    },
    
    saveOtherSettings: function(img) 
    {
        var scratch = $("#txtScratch").val();
        var revertMessage = $("#txtRevertMessage").val();
        var theme = $("#theme").text();
        var userName = $("#txtUserName").attr("value");
       
        var msg = "";
        var width = 250;
        if(scratch.length > 25){
            msg += "个性签名长度不能大于25！";
        }
        
        if($("#hidCnSender").attr("outter") == 0)
        {
            if(userName.trim().length < 1){
                msg += "中文名不能为空！";
            }
        }    
        if(revertMessage.length > 25){
            msg += "<br/>自定义回复消息长度不能大于25！";
            width = 450;
        }
        
        if(cannotValid(scratch)){
            msg += "<br/>个性签名中不可以包含‘\<\>\"\'\&\~\!\@\#\$\%\^\*’等特殊字符！";
            width = 450;
        }
        if(cannotValid(userName)){
            msg += "<br/>中文名中不可以包含‘\<\>\"\'\&\~\!\@\#\$\%\^\*’等特殊字符！";
            width = 450;
        }
        if(cannotValid(revertMessage)){
            msg += "<br/>自定义回复消息中不可以包含‘\<\>\"\'\&\~\!\@\#\$\%\^\*’等特殊字符！";
            width = 470;
        }
        if(msg != ""){
            Effect.appendDiv("<br />" + msg, true, width);
            return;
        }
       
        var params = "method=changeSettings" + 
              "&scratch=" + scratch +
              "&revertMessage=" + revertMessage + 
              "&theme=" + theme + 
              "&headImg=" + escape(LayoutSettings.currentHeadImg) + 
              "&userId=" + $("#hidSender").val();
              
        if($("#hidCnSender").attr("outter") == 0)
        {
            params += "&userName=" + escape(userName);
        }
        
        $.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "settings.aspx",
            data: params,
              
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br />修改用户设置失败，请稍候再试！", true);
                }
                else
                {
                    Effect.appendDiv("<br />" + result, true);
                    LayoutSettings.closeSettingsPanel();
                    
                    var tempImg = img.split("/");
                    document.getElementById("hidrevertMessage").value = revertMessage;
                    $.ajax({
                        type: "POST",
                        cache: false,
                        dataType: "html",
                        ifModified: true,
                        url: "refreshuser.aspx",
                        data: "flag=2" + 
                          "&owner=" + $("#hidSender").val() + 
                          "&headimg=" + tempImg[tempImg.length-1] + 
                          "&username=" + escape(userName) + 
                          "&scratch=" + escape(scratch),
                          
                        success: function(result){
                            if(result != "0")
                            {
                                document.cookie = "webImTheme=" + theme + ";expires=360*24*60*60*1000;path=/;";
                                
                                if(theme != $("#hidSender").attr("theme"))
                                {
                                    autoRefresh = true;
                                    window.location.reload();
                                }
                            }
                        }
                    });
                }
            }
        });
    },
    
    uploadcreate: function(el){//创建一个上传控件
        var currentItemID = 0;
        var strContent = "<div class='uploadcontrol'><iframe src=\"upload.aspx?id="+currentItemID+"\" id=\"ifUpload"+currentItemID+"\" frameborder=\"no\" scrolling=\"no\" style=\"width:400px; height:28px;\"></iframe>";
	    strContent += "<div class=\"uploading\" id=\"uploading"+currentItemID+"\" style=\"display:none;\" ></div>";
	    strContent += "<div class=\"image\" id=\"panelViewPic"+currentItemID+"\" style=\"display:none;\"></div></div>";
	    el.append(strContent);
    },
    
    uploadshowpic: function(el){
 	    var isshowpic = false;//是否显示上传后的图片
 	    isshowpic = !(isshowpic);
 	    if(isshowpic) {
 	        el.html("图片显示关闭");
 	    } else {
 	        el.html("图片显示开启");
 	    }
 	},
 	
    uploading: function(imgsrc){ //选择文件后的事件,iframe里面调用的
        //载入中的GIF动画 
        var loadingUrl = "../include/images/ajax-loader.gif";
        var el = $("#uploading0");
        $("#ifUpload0").fadeOut("fast");
        el.fadeIn("fast");
	    el.html("<img src='"+loadingUrl+"' align='absmiddle' /> <font size='2'>上传中...</font>");
	    return el;
    },
    
    uploadinit: function(){//重新上传方法
        $("#uploading0").fadeOut("fast",function(){
             $("#ifUpload0").fadeIn("fast");
             $("#panelViewPic0").fadeOut("fast");
        });
    },
    
    uploaderror: function(itemid){//上传时程序发生错误时，给提示，并返回上传状态
        alert("头像上传失败，请重试！");
        LayoutSettings.uploadinit();
    },
    
    uploadFail: function(msg){
        alert(msg);
        LayoutSettings.uploadinit();
    },
    
    uploadsuccess: function(newpath,itemid){//上传成功后的处理
        LayoutSettings.currentHeadImg = newpath;
        document.getElementById("viewImg").src = "../upload/head/" + newpath;
        $("#uploading"+itemid).html("");
    },
    
    toggleThemes: function()
    {
        var themePanel = $(event.srcElement);
        var divGroups = $("#divThemes");
        
        divGroups.find("a").each(
            function()
            {
                $(this).width(260);
            }
        );
        
        divGroups.css("display", "block");
        divGroups.css("font-size", 12);
        divGroups.css({"top": themePanel.offset().top + 19, "left": themePanel.offset().left - 1});
    },
    
    creatThemes: function()
    {
        if(document.getElementById("divThemes"))
            return;
            
        var themesArray = "";
        $.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "settings.aspx",
            data: "method=getThemes",
              
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br />获取主题失败，请稍候再试！", true);
                }
                else
                {
                    themesArray = result.split(",");
                    var panelHtml = "<div class=\"itemList\" id=\"divThemes\">";
                    //themesArray = Profile.themes;
                    
                    for(var i = 0; i < themesArray.length; i++)
                    {
                        panelHtml += "<a href=\"#\" onmousedown=\"LayoutSettings.setTheme('" 
                                  + themesArray[i] + "'); return false;\" style=\"\">"
                                  + themesArray[i] + "</a>";
                    }
                    
                    panelHtml += "</div>";
                    $(document.body).append(panelHtml);
                }
            }
        });
    },
    
    setTheme: function(theme)
    {
        $("#theme").text(theme);
        
        LayoutSettings.removeThemes();
    },
    
    removeThemes: function() 
    {
        $("#divThemes").css("display", "none");
    },
    
    checkImgExt: function(img) 
    {
        var fileExt = ".gif.jpg.png";
        
        var ext = LayoutSettings.getExt(img);
        if(fileExt.indexOf(ext) == -1)
        {
            Effect.appendDiv("<br />请上传gif、png、jpg格式的图片！", true);
            return false;
        }
        return true;
    },
    
    getExt: function(img) 
    {
        img = img || "";
        var ext = img.substring(img.lastIndexOf(".")+1).toLowerCase();
        return ext || "";
    },
    
    createDefaultImgs: function()
	{
	    var el = $("#defaultImgs");
		var imgs = "";
		
		$.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "settings.aspx",
            data: "method=getImgs",
              
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br />修改用户设置失败，请稍候再试！", true);
                }
                else
                {
                    imgs = result;
                    el.html(imgs);
                }
            }
        });
	},
	
	currentHeadImg: "",
	
	setCurrentImg: function(el)
	{
	    LayoutSettings.currentHeadImg = el.src;
	    document.getElementById("viewImg").src = el.src;
	}
};
