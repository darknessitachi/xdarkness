Array.prototype.contain = function (value)
{
    if(!(typeof(value) == "string" || typeof(value) == "number")) 
        return false;

    for(var i=0;i< this.length;i++)
    {
        if(this[i] == value) 
        {
           return true;
        }
    }
    return false;
};
  
Array.prototype.remove = function (value)
{
    if(!(typeof(value) == "string" || typeof(value) == "number")) 
        return false;

    for(var i=0;i< this.length;i++)
    {
        if(this[i] == value) 
        {
           this.splice(i,1);
           break;
        }
    }
       
};

Array.prototype.containUser = function (userId)
{
    for(var i=0;i< this.length;i++)
    {
        if(this[i].userId == userId) 
        {
           return true;
        }
    }
    return false;
};

Array.prototype.removeUser = function (userId)
{
    for(var i=0;i< this.length;i++)
    {
        if(this[i].userId == userId) 
        {
           this.splice(i,1);
           break;
        }
    }
};
  
User = function(config)
{
    this.userId = config.userId;
    this.loginName = config.loginName;
    this.userName = config.userName;
    this.isOutter = config.isOutter;
}

var LayoutAddFriends = {
    createFriendsPanel: function()
    {
        if(document.getElementById("divFriends"))
            return;
            
        var panelHtml = "<div id=\"divFriends\" class=\"dialog\" style=\" border:1px;\">";
        panelHtml +=            "<div id=\"divSettings_close\" class=\"dialog_close\" onclick=\"LayoutAddFriends.closeFriendsPanel();\"> </div>";
        panelHtml +=            "<table id=\"divSettings_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"divSettings_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"divSettings_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"divSettings_top\" class=\"dialog_title title_window top_draggable\">添加好友</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td id=\"divSettings_ne\" class=\"dialog_ne\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"divSettings_row2\" class=\"mid table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_w\"/>";
        panelHtml +=                        "<td  class=\"dialog_content\" valign=\"top\">";
        panelHtml +=                            "<div id=\"divSettings_content\" class=\"dialog_content\" style=\" width: 340px;\">";
        panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding: 14px 14px 10 14px; width: 100%;\">";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 2px; padding-top: 4px;padding-left: -2px;font-size: 12px;\">好友：</div>";
        panelHtml +=                                    "<input style=\"width:180px;height:18px;\" id=\"txtSearch\" type=\"text\" maxlength=\"10\" /><a onclick=\"LayoutAddFriends.search();\"  href=\"#\"><span class=\"btnSearch\" >查找</span></a>";
        panelHtml +=                                "</div>";  
        panelHtml +=                                "<div id=\"divUserList\" style=\"width: 340px;height:280px\"/>";       
        panelHtml +=                                "<div id=\"divPage_buttons\" style=\"padding-top:10px;\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<span id='personCount' style='font-size:12px;' />";
        panelHtml +=                                    "<a id=\"aFirstPage\" onclick=\"LayoutAddFriends.onFirstPage();\" href=\"#\"><span class=\"buttons\">首页</span></a>";
        panelHtml +=                                    "<a id=\"aPrePage\"  onclick=\"LayoutAddFriends.onPrePage();\" href=\"#\"><span class=\"buttons\">上一页</span></a>";
        panelHtml +=                                    "<a id=\"aNextPage\" onclick=\"LayoutAddFriends.onNextPage();\" href=\"#\"><span class=\"buttons\">下一页</span></a>";
        panelHtml +=                                    "<a id=\"aLastPage\" onclick=\"LayoutAddFriends.onLastPage();\" href=\"#\"><span class=\"buttons\">尾页</span></a>";
        panelHtml +=                                "</div>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_e\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_w\"/>";
        panelHtml +=                        "<td class=\"dialog_content\">";
        panelHtml +=                                    "<div align=center class=\"div_buttons\"><a onclick=\"LayoutAddFriends.chooseGroup();\" href=\"#\"><span class=\"buttons\">添加</span></a><a  onclick=\"LayoutAddFriends.closeFriendsPanel();\" href=\"#\"><span class=\"buttons\">取消</span></a></div>";
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
        panelHtml +=            "<input id=\"start\" type=\"hidden\" value=\"0\">";
        panelHtml +=            "<input id=\"limit\" type=\"hidden\" value=\"10\">";
        panelHtml += "</div>";
            
        $(document.body).append(panelHtml);
        
        $("#divFriends").draggable({containment: 'parent' });
        
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        
        var width = 344;
        var height = document.getElementById("divFriends").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#divFriends").css("top",top);
        $("#divFriends").css("left",left);
        $("#divFriends").css("width",width);
        
        this.getUserList();
    },
    
    closeFriendsPanel: function() 
    {
        document.body.removeChild(document.getElementById('divFriends'));
        LayoutAddFriends.arrFriendsInfo = [];
    },
    
    search: function()
    {
        document.getElementById("start").value = 0;
        LayoutAddFriends.getUserList();
    },
    
    getUserList: function()
    {
        $.ajax({
            type: "GET",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "friend.aspx",
            data: "method=getFriends" + 
              "&userId=" + $("#hidSender").val() +
              "&search=" + escape($("#txtSearch").val()) +
              "&start=" + $("#start").val() +
              "&limit=" + $("#limit").val(),
            
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br/>获取用户列表失败，请稍候再试！", true);
                }
                else
                {
                    document.getElementById("divUserList").innerHTML = result;
                    var ckUser = document.getElementsByName("ckUserId");
                    var friendInfo = LayoutAddFriends.arrFriendsInfo;
                    for(var i=0;i<ckUser.length;i++)
                    {
                        if(friendInfo.containUser(ckUser[i].userId))
                        {
                            ckUser[i].checked = true;
                        }
                    }
                    
                    if(!LayoutAddFriends.isSelectAll)
                    {
                        if(LayoutAddFriends.checkSelectAll())
                        {
                            var objCkAll = document.getElementById("ckAllId");
                            if(objCkAll)
                                objCkAll.checked = true;
                        }
                        else
                        {
                            var objCkAll = document.getElementById("ckAllId");
                            if(objCkAll)
                                objCkAll.checked = false;
                        }
                     }  
                    
                    var count = parseInt($("#tbUsers").attr("count"));
                    var limitValue = parseInt(document.getElementById("limit").value);
                    var page = parseInt((count+limitValue-1) / limitValue) - 1;
                    
                    // 如果是第一页
                    if(document.getElementById("start").value == 0)
                    {
                        document.getElementById("aFirstPage").disabled = "disabled";
                        document.getElementById("aPrePage").disabled = "disabled";
                        document.getElementById("aFirstPage").style.cursor = "default";
                        document.getElementById("aFirstPage").firstChild.style.cursor = "default";
                        document.getElementById("aPrePage").style.cursor = "default";
                        document.getElementById("aPrePage").firstChild.style.cursor = "default";
                    }
                    else
                    {
                        document.getElementById("aFirstPage").disabled = "";
                        document.getElementById("aPrePage").disabled = "";
                        document.getElementById("aFirstPage").style.cursor = "";
                        document.getElementById("aFirstPage").firstChild.style.cursor = "";
                        document.getElementById("aPrePage").style.cursor = "";
                        document.getElementById("aPrePage").firstChild.style.cursor = "";
                    }
                    
                    // 如果是最后一页
                    if(document.getElementById("start").value == page * limitValue)
                    {
                        document.getElementById("aLastPage").disabled = "disabled";
                        document.getElementById("aNextPage").disabled = "disabled";
                        document.getElementById("aLastPage").style.cursor = "default";
                        document.getElementById("aLastPage").firstChild.style.cursor = "default";
                        document.getElementById("aNextPage").style.cursor = "default";
                        document.getElementById("aNextPage").firstChild.style.cursor = "default";
                    }
                    else
                    {
                        document.getElementById("aLastPage").disabled = "";
                        document.getElementById("aNextPage").disabled = "";
                        document.getElementById("aLastPage").style.cursor = "";
                        document.getElementById("aLastPage").firstChild.style.cursor = "";
                        document.getElementById("aNextPage").style.cursor = "";
                        document.getElementById("aNextPage").firstChild.style.cursor = "";
                    }
                    
                    try{
                        count = parseInt(count);
                        if(!count){
                            count = 0;
                        }
                    }catch(e){
                        count = 0;
                    }
                    
                    document.getElementById("personCount").innerText = "总人数：" + count + "　";
                    
                }
            }
        });
    },
    
    isSelectAll: false,
    
    selectAll: function(el)
    {
        LayoutAddFriends.isSelectAll = true;
        
        var ckUser = document.getElementsByName("ckUserId");
        for(var i=0;i<ckUser.length;i++)
        {
            if(ckUser[i].checked != el.checked)
            {
                ckUser[i].click();
            }
        }
        
        LayoutAddFriends.isSelectAll = false;
    },
    
    chooseGroup: function()
    {
        if(LayoutAddFriends.arrFriendsInfo.length < 1)
        {
            Effect.appendDiv("<br/>请选择您需要添加的人员！", true, 250);
            return;
        }
        LayoutAddFriends.createGroupPanel();
    },
    
    onPrePage: function()
    {
        var startValue = document.getElementById("start").value;
        var limitValue = document.getElementById("limit").value;
        
        var startValue = startValue - limitValue;
        if(startValue < 0)
        {
            return;
        }
            
        document.getElementById("start").value = startValue;
        LayoutAddFriends.getUserList();
        
    },
    
    onNextPage: function()
    {
        var startValue = document.getElementById("start").value;
        var limitValue = document.getElementById("limit").value;
        
        startValue = parseInt(startValue) + parseInt(limitValue);
        
        if(startValue >= parseInt($("#tbUsers").attr("count")))
        {
            return;
        }
        
        document.getElementById("start").value = startValue;
        LayoutAddFriends.getUserList();
        
    },
    
    onFirstPage: function()
    {
        document.getElementById("start").value = 0;
        LayoutAddFriends.getUserList();
        
    },
    
    onLastPage: function()
    {
        var count = parseInt($("#tbUsers").attr("count"));
        var limitValue = parseInt(document.getElementById("limit").value);
        var page = parseInt((count+limitValue-1) / limitValue) - 1;
        document.getElementById("start").value = page * limitValue;
        
        LayoutAddFriends.getUserList();
        
    },
    
    saveFriendsInfo: function(el, checkSelAll)
    {
        
        var friendsInfo = LayoutAddFriends.arrFriendsInfo;
        
        if(el.checked)
        {
            var friend = new User({
                userId: el.userId,
                userName: el.userName,
                loginName: el.loginName,
                isOutter: el.isOutter
            });
            friendsInfo.push(friend);
        }
        else
        {
            friendsInfo.removeUser(el.userId);
        }
        
        if(!LayoutAddFriends.isSelectAll)
        {
            if(LayoutAddFriends.checkSelectAll())
            {
                document.getElementById("ckAllId").checked = true;
            }
            else
            {
               document.getElementById("ckAllId").checked = false;
            }
         }   
        
    },
    
    checkSelectAll: function(){
        
        var ckUser = document.getElementsByName("ckUserId");
        for(var i=0;i<ckUser.length;i++)
        {
            if(!ckUser[i].checked)
            {
                return false;
            }
        }
        
        return true;
    },
    
    arrFriendsInfo: [],
    
    createGroupPanel: function()
    {
    
        if(document.getElementById("newBuddy"))
        {
            return;
        }
        
        var panelHtml = "<div id=\"newBuddy\" class=\"dialog\" style=\"height: 198px; z-index: 138;\">";
        
        panelHtml +=            "<div id=\"newBuddy_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"newBuddy_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"newBuddy_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"newBuddy_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"newBuddy_top\" class=\"dialog_title title_window top_draggable\">给好友分组</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td id=\"newBuddy_ne\" class=\"dialog_ne\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"newBuddy_row2\" class=\"mid table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_w\"/>";
        panelHtml +=                        "<td id=\"newBuddy_table_content\" class=\"dialog_content\" valign=\"top\">";
        panelHtml +=                            "<div id=\"newBuddy_content\" class=\"dialog_content\" style=\"height: 120px; width: 240px;\">";
        panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 12px;\">&nbsp;&nbsp;将添加人员添加到：</div>";
        panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding-left: 22px; width: 100%;\">";
      
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 14px; padding-top: 4px;font-size: 12px;\">组名：</div>";
        panelHtml +=                                    "<input id=\"newBuddyGroup\" groupId=\"\" type=\"text\" onkeypress=\"\" onmousedown=\"Effect.toggleUserGroups();\" onfocus=\"\" onblur=\"Effect.removeUserGroups();\" value=\"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/>";
        
        panelHtml +=                                "</div>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutAddFriends.addFriends();\" href=\"#\"><span>确定</span></a><a class=\"buttons\" onclick=\"LayoutGroup.closeGroup();\" href=\"#\"><span>取消</span></a>";
        panelHtml +=                                "</div>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_e\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"newBuddy_row3\" class=\"bot table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_sw bottom_draggable\"/>";
        panelHtml +=                        "<td class=\"dialog_s bottom_draggable\">";
        panelHtml +=                            "<div id=\"newBuddy_bottom\" class=\"status_bar\">";
        panelHtml +=                                "<span style=\"float: left; width: 1px; height: 1px;\"/>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_se bottom_draggable\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=           "</table>";
        panelHtml += "</div>";
            
        $(document.body).append(panelHtml);
        $("#newBuddy").draggable({containment: 'parent' });
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        
        var width = 244;
        var height = document.getElementById("newBuddy").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#newBuddy").css("top",top);
        $("#newBuddy").css("left",left);
        $("#newBuddy").css("width",width);
        
        LayoutGroup.createUserGroups();

    },
    
    addFriends: function(config)
    {
        var groupName = $("#newBuddyGroup").val();
        
        if(config)
        {
            if(document.getElementById("li" + config.userid + "blItem"))
            {
                return;
            }
            groupName = "陌生人";
        }
        
        if(groupName.trim() == "")
        {
            Effect.appendDiv("<br/>请选择您需要添加到的组名！", true, 230);
            return;
        }
        
        if(groupName.len() > 24){
            Effect.appendDiv("<br/>组名中文长度不可以大于12，<br/>英文长度不可以大于24！", true, 260);
            return;
        }
        
        if(cannotValid(groupName)){
            Effect.appendDiv("<br/>组名中不可以包含‘<、>、\"、\'、\&’等特殊字符！！", true, 260);
            return;
        }
        
        // 获取到陌生人组的对象
        var buggyList = document.getElementById("buddylist");
        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
        var strangerGroup = document.getElementById("ul" + strangerNode.groupid + "Group");
        
        var params = "";
        var strFriendsInfo = "";
        if(config)
        {   
            var groupId = strangerNode.groupid;
            var tempCopyToGroupId = strangerGroup;
            var theme = $("#hidSender").attr("theme");
                    
            var liItem = document.createElement("li");
            
            liItem.id = "li" + config.userid + "blEmpty";
            liItem.style.listStyleType = "none";
            liItem.style.paddingTop = 0;
            liItem.style.marginTop = -9;
            
            tempCopyToGroupId.appendChild(liItem);
            
            liItem = document.createElement("li");
            
            liItem.title = "";
            liItem.className = "buddy";
            liItem.style["cursor"] = "pointer";
            liItem.style["listStyleType"] = "none"; 
            liItem.id = "li" + config.userid + "blItem";
            liItem.setAttribute("userid", config.userid);
            liItem.setAttribute("userstatus", Profile.onLineStatus);
            
            liItem.onmouseover = function(){
                LayoutIm.setSelectedUserOverColor();
            };
            
            liItem.onmouseout = function(){
                LayoutIm.setSelectedUserOutColor();
            };
            
            var userId = config.userid;
            var userName = config.username;
            
            liItem.oncontextmenu = (function(userId, userName){
            
                return function(){
                    event.returnValue = false;
                    LayoutGroup.createContextmenu(
                        userId, userName, 'li' + userId + 'blItem'
                    );
                }
            })(userId, userName);
            
            var theme = $("#hidSender").attr("theme");
            liItem.ondblclick = (function(userId, userName, theme){
            
                return function(){
                    LayoutIm.toggleChatPanel(userId, userName, theme);
                }
            })(userId, userName, theme);
                                    
            //var liItemNode = document.createTextNode("  ");
            //liItem.appendChild(liItemNode);
            liItem.innerHTML = "&nbsp;&nbsp;";
            
            var imgItem = document.createElement("img");
            
            imgItem.alt = "";
            imgItem.style.border = "none";
            imgItem.style.width = "32px";
            imgItem.style.height = "32px";
            imgItem.id = "img" + userId + "HeadImg";
            imgItem.src = Profile.userDefaultHeadImagePath + Profile.userHeadImage;
            
            
            imgItem.onmouseover = function(){
                LayoutPersonInfo.createInfoPanel();
            };
            
            imgItem.onmouseout = function(){
                LayoutPersonInfo.removeInfoPanel();
            };
                                    
            liItem.appendChild(imgItem);
            
            var liItemNode = document.createTextNode(" ");
            liItem.appendChild(liItemNode);
            
            var divItem = document.createElement("div");
            
            divItem.style["margin"] = "-35px 0px 0px 44px"; 
            divItem.style["padding"] = "0px";
            
            imgItem = document.createElement("img");
            
            imgItem.id = "img" + userId + "blImg";
            imgItem.style["border"] = "none";
            imgItem.style["width"] = "8px";
            imgItem.style["height"] = "8px";
            imgItem.src = Profile.userStatusIconPath + Profile.userStatusIconList[Profile.offLineStatus];
            imgItem.alt = "";
            
            divItem.appendChild(imgItem);
            
            liItemNode = document.createTextNode(" " + userName);
            divItem.appendChild(liItemNode);
            
            liItem.appendChild(divItem);
            
            divItem = document.createElement("div");
            
            divItem.id = "div" + userId + "scratch";
            divItem.style["margin"] = "5px 0px 0px 44px";
            divItem.style["padding"] = "0px;";
            divItem.style["color"] = "#808080";
            divItem.innerHTML = "&nbsp;&nbsp;";
            
            liItem.appendChild(divItem);
            tempCopyToGroupId.appendChild(liItem);
            
            IM.toggleUserProrate(groupId, '1');
            
            return;
        }
        else
        {
            var friendsInfo = LayoutAddFriends.arrFriendsInfo;
            
            for(var i=0;i<friendsInfo.length;i++)
            {
                strFriendsInfo += "&friendId=" + friendsInfo[i].userId 
                             + "&loginName=" + friendsInfo[i].loginName
                             + "&userName=" + escape(friendsInfo[i].userName)
                             + "&isOutter=" + friendsInfo[i].isOutter;
            }
            
            params = "method=addFriends" + 
                      "&userId=" + $("#hidSender").val() + 
                      "&uName=" + escape($("#hidCnSender").val()) +
                      "&lName=" + $("#hidCnSender").attr("lname") +
                      "&iIsOutter=" + $("#hidCnSender").attr("outter") + strFriendsInfo +
                      "&groupId=" + $("#newBuddyGroup").attr("groupId") +
                      "&groupName=" + escape($("#newBuddyGroup").val());
        }
        

        $.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "friend.aspx",
            data: params,
            
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br/>添加用户失败，请稍候再试！", true);
                }
                else if(result == "self")
                {
                    Effect.appendDiv("<br/>不允许创建名称为‘自己’的组！", true, 230);
                }
                else
                {
                    // var groupId = $("#newBuddyGroup").attr("groupId") || result;
                    var groupId = result;
                    var tempCopyToGroupId = document.getElementById("ul" + groupId + "Group");
                    var theme = $("#hidSender").attr("theme");
                    
                        if(tempCopyToGroupId == null)
                        {
                            var groupLi = document.createElement("li");
                            
                            groupLi.className = "groupTop";
                            groupLi.id = "li" + groupId + "GroupTop";
                            groupLi.setAttribute("groupid", groupId);
                            
                            groupLi.onclick = function(){
                                LayoutIm.extendGroupList(theme);
                            };
                            
                            groupLi.oncontextmenu = function(){
                                event.returnValue = false;
                                LayoutGroup.groupManager(groupId, groupName);
                            };
                            
                            var groupLiImg = document.createElement("img");
                            
                            groupLiImg.alt = "";
                            groupLiImg.src = Profile.groupArrowTop + theme + Profile.gruopArrowFoot + Profile.groupArrowFootImage;
                            groupLiImg.id = "img" + groupId + "GroupArrow";
                            groupLiImg.setAttribute("groupid", groupId);
                            
                            groupLiImg.onclick = function(){
                                event.srcElement.parentElement.click();
                            };
                            
                            groupLi.appendChild(groupLiImg);
                            
                            var groupNameNode = document.createTextNode("\u00A0\u00A0" + groupName);
                            groupLi.appendChild(groupNameNode);
                           
                            var groupSpan = document.createElement("span");
                            var groupSpanText = document.createTextNode("(0/" + friendsInfo.length + ")");
                            groupSpan.appendChild(groupSpanText);
                            groupSpan.oncontextmenu = function(){
                                event.returnValue = false;
                                LayoutGroup.groupManager(groupId, groupName);
                            };
                            groupLi.appendChild(groupSpan);
                                                    
                            tempCopyToGroupId = document.createElement("ul");
                            tempCopyToGroupId.className = "group";
                            tempCopyToGroupId.id = "ul" + groupId + "Group"
                            tempCopyToGroupId.setAttribute("groupid",groupId);
                            tempCopyToGroupId.style.display = "none";
                            
                            groupLi.appendChild(tempCopyToGroupId);
                            
                            
                            
                            strangerGroup.parentElement.parentElement.insertBefore(groupLi, strangerGroup.parentElement);
                            //document.getElementById("buddylist").appendChild(groupLi);
                        }
                                            
                        for(var i = 0; i < friendsInfo.length; i++)
                        {
                            var liItem = document.createElement("li");
                            
                            liItem.id = "li" + friendsInfo[i].userId  + "blEmpty";
                            liItem.style.listStyleType = "none";
                            liItem.style.paddingTop = 0;
                            liItem.style.marginTop = -9;
                            
                            tempCopyToGroupId.appendChild(liItem);
                            
                            liItem = document.createElement("li");
                            
                            liItem.title = "";
                            liItem.className = "buddy";
                            liItem.style["cursor"] = "pointer";
                            liItem.style["listStyleType"] = "none"; 
                            liItem.id = "li" + friendsInfo[i].userId + "blItem";
                            liItem.setAttribute("userid", friendsInfo[i].userId);
                            liItem.setAttribute("userstatus", Profile.offLineStatus);
                            
                            liItem.onmouseover = function(){
                                LayoutIm.setSelectedUserOverColor();
                            };
                            
                            liItem.onmouseout = function(){
                                LayoutIm.setSelectedUserOutColor();
                            };
                            
                            var userId = friendsInfo[i].userId;
                            var userName = friendsInfo[i].userName;
                            
                            liItem.oncontextmenu = (function(userId, userName){
                            
                                return function(){
                                    event.returnValue = false;
                                    LayoutGroup.createContextmenu(
                                        userId, userName, 'li' + userId + 'blItem'
                                    );
                                }
                            })(userId, userName);
                            
                            var theme = $("#hidSender").attr("theme");
                            liItem.ondblclick = (function(userId, userName, theme){
                            
                                return function(){
                                    LayoutIm.toggleChatPanel(userId, userName, theme);
                                }
                            })(userId, userName, theme);
                                                    
                            //var liItemNode = document.createTextNode("  ");
                            //liItem.appendChild(liItemNode);
                            liItem.innerHTML = "&nbsp;&nbsp;";
                            
                            var imgItem = document.createElement("img");
                            
                            imgItem.alt = "";
                            imgItem.style.border = "none";
                            imgItem.style.width = "32px";
                            imgItem.style.height = "32px";
                            imgItem.id = "img" + friendsInfo[i].userId + "HeadImg";
                            imgItem.src = Profile.userDefaultHeadImagePath + Profile.userHeadImage;
                            
                            
                            imgItem.onmouseover = function(){
                                LayoutPersonInfo.createInfoPanel();
                            };
                            
                            imgItem.onmouseout = function(){
                                LayoutPersonInfo.removeInfoPanel();
                            };
                                                    
                            liItem.appendChild(imgItem);
                            
                            var liItemNode = document.createTextNode(" ");
                            liItem.appendChild(liItemNode);
                            
                            var divItem = document.createElement("div");
                            
                            divItem.style["margin"] = "-35px 0px 0px 44px"; 
                            divItem.style["padding"] = "0px";
                            
                            imgItem = document.createElement("img");
                            
                            imgItem.id = "img" + friendsInfo[i].userId + "blImg";
                            imgItem.style["border"] = "none";
                            imgItem.style["width"] = "8px";
                            imgItem.style["height"] = "8px";
                            imgItem.src = Profile.userStatusIconPath + Profile.userStatusIconList[Profile.offLineStatus];
                            imgItem.alt = "";
                            
                            divItem.appendChild(imgItem);
                            
                            liItemNode = document.createTextNode(" " + friendsInfo[i].userName);
                            divItem.appendChild(liItemNode);
                            
                            liItem.appendChild(divItem);
                            
                            divItem = document.createElement("div");
                            
                            divItem.id = "div" + friendsInfo[i].userId + "scratch";
                            divItem.style["margin"] = "5px 0px 0px 44px";
                            divItem.style["padding"] = "0px;";
                            divItem.style["color"] = "#808080";
                            divItem.innerHTML = "&nbsp;&nbsp;";
                            
                            liItem.appendChild(divItem);
                            tempCopyToGroupId.appendChild(liItem);
                        }
                        
                        LayoutGroup.closeGroup();
                        
                        // 重置选中人员列表为空，避免在添加人员时会将此人员列表在添加一遍（为重复添加）
                        LayoutAddFriends.arrFriendsInfo = [];
                        
                        // 重新获取用户列表，应为此时，刚才已经添加的人员应该为不可选中状态
                        LayoutAddFriends.getUserList();
                        
                        IM.toggleUserProrate(groupId, '1');
                    
                }
            }
        });
    },
      
    closeGroup: function() 
    {
        document.body.removeChild(document.getElementById('newBuddy'));
    }
};