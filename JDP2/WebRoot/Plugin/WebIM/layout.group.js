String.prototype.lTrim = function(){
    return this.replace(/^(\u00A0|\s*)/g, ""); 
}

String.prototype.rTrim = function(){
    return this.replace(/\u00A0|\s*$/g, ""); 
}

String.prototype.trim = function(){
    return this.replace(/^(\u00A0*\s*\u00A0*\s*)\u00A0*|\s*$/g,"");
}


String.prototype.replaceSpecialChar = function (str){
    var rexStr = /\<|\>|\"|\'|\&/g;
    str = str.replace(rexStr, function(MatchStr){
                        switch(MatchStr){
                            case "&":
                                return "&amp;";
                                break;
                            case "<":
                                return "&lt;";
                                break;
                            case ">":
                                return "&gt;";
                                break;
                            case "\"":
                                return "&quot;";
                                break;
                            case "'":
                                return "&#39;";
                                break;
                            default :
                                break;
                        }
                    });
    return str;
}

Sky = {

    idSeed : 0,
    
    generateId : function(){
        var prefix = "darkness-gen";
        return prefix + "-" + (++this.idSeed) + "-" + new Date().getTime();
    }
}


$removeElementById = function(id) 
{
    var obj = document.getElementById(id);
    if(obj) document.body.removeChild(obj);
}

var LayoutGroup = {
    createGroupPanel: function(userId, userName, userItemId)
    {
        if($("div[divChangeUserGroupId = '1']").length == 1)
        {
            return;
        }
        
        var genId = Sky.generateId();
        var panelHtml = "<div id=\"" + genId + "\" divChangeUserGroupId=\"1\" class=\"dialog\" style=\" z-index: 138;\">";
        
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
        panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 14px;\">&nbsp;&nbsp;将好友'" + userName + "'移动到：</div>";
        panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding-left: 22px; width: 100%;\">";
      
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 14px; padding-top: 4px;font-size: 14px;\">组名：</div>";
        panelHtml +=                                    "<a id=\"newBuddyGroup\" groupId=\"\" href=\"#\" type=\"text\" onkeypress=\"\" onmousedown=\"Effect.toggleUserGroups();\" onfocus=\"\" onblur=\"Effect.removeUserGroups();\" value=\"dd\" name=\"newBuddyGroup\" style=\"width: 110px;\"></a>";
        
        panelHtml +=                                    "<input id=\"newBuddyUsername\" type=\"hidden\" onkeypress=\"\" name=\"newBuddyUsername\" value=\"" + userName + "\" relationshiper=\"" + userId + "\" userItemId=\"" + userItemId + "\" style=\"width: 110px;\"/>";
        panelHtml +=                                    "<br/>";
        
        panelHtml +=                                "</div>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\"  align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutGroup.changeUserGroup('"+genId+"');\" href=\"#\">确定</a><a class=\"buttons\" onclick=\"$removeElementById('"+genId+"');\" href=\"#\">取消</a>";
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
        $("#" + genId).draggable({containment: 'parent' });
        
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        
        var width = 244;
        var height = document.getElementById(genId).offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        genId = "#" + genId;
        $(genId).css("top",top);
        $(genId).css("left",left);
        $(genId).css("width",width);
        
        LayoutGroup.createUserGroups();

    },
    
    groupManager:function(groupId, groupName)
    {
        //e = e ? e : window.e;
        e = event;
        var obj = e.srcElement ? e.srcElement : e.target;
        
        var isSpan = (event.srcElement.tagName == "SPAN");
        
        if(obj.className == "groupTop" || isSpan)
        {
            if(isSpan)
            {
                LayoutGroup.createGroupMenu(groupId, obj.parentElement.childNodes[1].nodeValue.lTrim());   
            }
            else
            {
                LayoutGroup.createGroupMenu(groupId, obj.childNodes[1].nodeValue.lTrim());
            }
        }
    },
    
    addGroupManager: function(groupId, groupName)
    {
        
        e = event;
        var obj = e.srcElement ? e.srcElement : e.target;
        
        var isSpan = (event.srcElement.tagName == "SPAN");
        
        if(obj.className == "groupTop" || isSpan)
        {
            
            if(document.getElementById(groupId))
                return;
            
            var panelHtml = "<div class=\"itemList\" id=\"divAddGroupContextmenu\" style=\"width:15px;\" onblur=\"LayoutGroup.removeContextmenu('divAddGroupContextmenu');\">";
            panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createAddGroupPanel();\" style=\"font-size: 11px;\">添加分组</a>";
            panelHtml += "</div>";
            
            $("#bl").append(panelHtml);
            
            var userPanel = $(event.srcElement);
            var divUserContextmenu = $("#divAddGroupContextmenu");
            
            divUserContextmenu.find("a").each(
                function()
                {
                    $(this).width(100);
                }
            );
            
            divUserContextmenu.css("display", "block");
            divUserContextmenu.css({"top": event.clientY - 15, "left": event.clientX- userPanel.offset().left + 15});
            divUserContextmenu.focus();
        }
        
    },
    
    createGroupMenu: function(groupId, groupName)
    {
        if(document.getElementById("divGroupContextmenu"))
            return;
            
        var panelHtml = "<div class=\"itemList\" id=\"divGroupContextmenu\" style=\"width:15px;\" onblur=\"LayoutGroup.removeContextmenu('divGroupContextmenu');\">";
        panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createAddGroupPanel('" + groupId + "','" + groupName + "','li" + groupId + "GroupTop');\" style=\"font-size: 11px;\">添加分组</a>";
        
        var objChildren = $("#li" + groupId + "GroupTop").children("ul").children("li");
        if(objChildren && objChildren.length <= 0)
        {//如果该组下有人员，就不显示删除选项（现已去除）
            panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createDeleteGroupPanel('" + groupId + "','" + groupName + "','li" + groupId + "GroupTop',true);\" style=\"font-size: 11px;\">删除分组</a>";
        }
        else
        {
            panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createDeleteGroupPanel('" + groupId + "','" + groupName + "','li" + groupId + "GroupTop',false);\" style=\"font-size: 11px;\">删除分组</a>";
        }
        
        panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createGroupManagePanel('" + groupId + "','" + groupName + "','li" + groupId + "GroupTop');\" style=\"font-size: 11px;\">重命名分组</a>";
        
        panelHtml += "</div>";
        
        $("#bl").append(panelHtml);
        
        var userPanel = $(event.srcElement);
        var divUserContextmenu = $("#divGroupContextmenu");
        
        divUserContextmenu.find("a").each(
            function()
            {
                $(this).width(100);
            }
        );
        
        divUserContextmenu.css("display", "block");
        divUserContextmenu.css({"top": event.clientY - 15, "left": event.clientX- userPanel.offset().left + 15});
        divUserContextmenu.focus();
    },
    
    createAddGroupPanel: function(groupId, groupName, groupItemId, flag)
    {
        if($("div[divAddGroup = '1']").length == 1)
        {
            return;
        }
        
        var panelHtml = "<div id=\"newBuddy\" divAddGroup=\"1\" class=\"dialog\" style=\"z-index: 138;\">";
        
        panelHtml +=            "<div id=\"newBuddy_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"newBuddy_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"newBuddy_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"newBuddy_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"newBuddy_top\" class=\"dialog_title title_window top_draggable\">添加分组</div>";
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
        panelHtml +=                            "<div id=\"newBuddy_content\" class=\"dialog_content\" style=\" width: 280px;\">";
        panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 14px;\">&nbsp;&nbsp;组名：";
        
        panelHtml +=                                    "<input id=\"txtGroup\" type=\"text\" style=\"width: 70%;\"/>";
        panelHtml +=                                 "</div>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutGroup.addGroup();\" href=\"#\">确定</a><a class=\"buttons\" onclick=\"LayoutGroup.closeGroup();\" href=\"#\">取消</a>";
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
        
        var width = 284;
        var height = document.getElementById("newBuddy").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#newBuddy").css("top",top);
        $("#newBuddy").css("left",left);
        $("#newBuddy").css("width",width);

    },
    
    addGroup: function()
    {
        var realGroups = "";
        var groupNames = $("#txtGroup").val().replace(/，/g,",").replace(/\s/g,"").split(",");
        
        if(groupNames.indexOf("陌生人") != -1)
        {
            Effect.appendDiv("<br/>不允许添加名称为‘陌生人’的分组！", true, 240);
            return;
        }
        if(groupNames.indexOf("自己") != -1)
        {
            Effect.appendDiv("<br/>不允许添加名称为‘自己’的分组！", true, 240);
            return;
        }
        
        for(var i=0;i<groupNames.length;i++)
        {
            if(groupNames[i] == "")
            {
                Effect.appendDiv("<br/>不可以添加空组！", true, 260);
                return ;
            }
            
            if(groupNames[i].len() > 24){
                Effect.appendDiv("<br/>组名中文长度不可以大于12，<br/>英文长度不可以大于24！", true, 260);
                return;
            }
            
            if(cannotValid(groupNames[i])){
                Effect.appendDiv("<br/>组名中不可以包含‘<、>、\"、\'、\&’等特殊字符！！", true, 260);
                return;
            }
    
            realGroups += groupNames[i] + ",";
            
        }
        realGroups = realGroups.substring(0,realGroups.length-1);
        
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "changegroup.aspx",
                data: "method=addGroup" + 
                  "&userId=" + $("#hidSender").val() + 
                  "&groups=" + escape(realGroups),
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br/>更新失败，请稍候再试！", true);
                    }
                    else
                    {
                        var groupInfo = result.split("→");
                        var groupIds = groupInfo[0].split(","); 
                        var changedGroupNames = groupInfo[1].split(",");
                        var groupNames = realGroups.split(",");
                        
                        for(var i=0;i<changedGroupNames.length;i++)
                        {
                            var changedGroupName = changedGroupNames[i].split(":");
                            groupNames[changedGroupName[0]] = changedGroupName[1];
                        }
                        
                        
                        // 获取到陌生人组的对象
                        var buggyList = document.getElementById("buddylist");
                        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
                        var strangerGroup = document.getElementById("ul" + strangerNode.groupid + "Group");
                        
                        for(var i=0;i<groupIds.length;i++)
                        {//创建组ui开始
                            var groupId = groupIds[i];
                            var groupName = groupNames[i];
                            var theme = $("#hidSender").attr("theme");
                            var groupLi = document.createElement("li");
                            
                            groupLi.className = "groupTop";
                            groupLi.id = "li" + groupId + "GroupTop";
                            groupLi.setAttribute("groupid", groupId);
                            
                            groupLi.onclick = function(){
                                LayoutIm.extendGroupList(theme);
                            };
                            
                            groupLi.oncontextmenu = function(){
                                event.returnValue = false;
                                LayoutGroup.groupManager(groupId, groupName, event);
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
                            
                            // 在陌生人分组上添加新组
                            strangerGroup.parentElement.parentElement.insertBefore(groupLi, strangerGroup.parentElement);
                            
                        }//创建组ui结束
                        
                        if(document.getElementById("newBuddy"))
                        {
                           document.body.removeChild(document.getElementById("newBuddy"));
                        }      
                    }
                }
            });
    },
    
    createGroupManagePanel: function(groupId, groupName, groupItemId)
    {
        var panelHtml = "<div id=\"newBuddy\" class=\"dialog\" style=\" z-index: 138;\">";
        
        panelHtml +=            "<div id=\"newBuddy_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"newBuddy_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"newBuddy_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"newBuddy_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"newBuddy_top\" class=\"dialog_title title_window top_draggable\">重命名组</div>";
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
        panelHtml +=                            "<div id=\"newBuddy_content\" class=\"dialog_content\" style=\"width: 240px;\">";
        panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 14px;\">&nbsp;&nbsp;组名：";
        
        panelHtml +=                                    "<input id=\"txtGroup\" groupItemId=\""+groupItemId+"\" groupId=\""+groupId+"\" type=\"text\" onkeypress=\"\" value=\""+ groupName.trim() +"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/>";
        panelHtml +=                                 "</div>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutGroup.changeGroupName();\" href=\"#\">确定</a><a class=\"buttons\" onclick=\"LayoutGroup.closeGroup();\" href=\"#\">取消</a>";
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
        
    },
    
    changeGroupName: function()
    {
        var objGroup = $("#txtGroup");
        
        if(cannotValid(objGroup.val())){
            Effect.appendDiv("<br/>组名中不可以包含‘<、>、\"、\'、\&’等特殊字符！！", true, 260);
            return;
        }
            
        if(objGroup.val().len() > 24){
            Effect.appendDiv("<br/>组名中文长度不可以大于12，<br/>英文长度不可以大于24！", true, 260);
            return;
        }
        
        if(objGroup.val().len() < 1){
            Effect.appendDiv("<br/>组名不能为空！", true);
            return;
        }
        
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "changegroup.aspx",
                data: "method=changeName" + 
                  "&groupId=" + objGroup.attr("groupId") + 
                  "&userId=" + $("#hidSender").val() + 
                  "&groupName=" + escape(objGroup.val()),
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br/>更新失败，请确认该组名是否已经存在！", true, 320);
                    }
                    else
                    {   
                        var groupItemId = objGroup.attr("groupItemId");
                        document.getElementById(objGroup.attr("groupItemId")).childNodes[1].nodeValue = "\u00A0\u00A0" + objGroup.val();
                        
                        if(document.getElementById("newBuddy"))
                        {
                           document.body.removeChild(document.getElementById("newBuddy"));
                        }      
                    }
                }
            });
    },
    
    createDeletePanel: function(userId, userName, userItemId)
    {
        var panelHtml = "<div id=\"newBuddy\" class=\"dialog\" style=\" z-index: 138;\">";
        
        panelHtml +=            "<div id=\"newBuddy_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"newBuddy_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"newBuddy_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"newBuddy_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"newBuddy_top\" class=\"dialog_title title_window top_draggable\">删除好友</div>";
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
        panelHtml +=                            "<div id=\"newBuddy_content\" class=\"dialog_content\" style=\" width: 300px;\">";
        panelHtml +=                                "<div class=\"alert\" />";
        panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 12px;\">删除后该好友将移动到陌生人分组中,您确定要将好友'" + userName + "'删除吗？</div>";
        panelHtml +=                                    "<input id=\"newBuddyUsername\" type=\"hidden\" onkeypress=\"\" name=\"newBuddyUsername\" value=\"" + userName + "\" relationshiper=\"" + userId + "\" userItemId=\"" + userItemId + "\" style=\"width: 110px;\"/>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutGroup.deleteUser();\" href=\"#\">确定</a><a class=\"buttons\" onclick=\"LayoutGroup.closeGroup();\" href=\"#\">取消</a>";
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
        
        var width = 304;
        var height = document.getElementById("newBuddy").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#newBuddy").css("top",top);
        $("#newBuddy").css("left",left);
        $("#newBuddy").css("width",width);
        
        //LayoutGroup.createUserGroups();

    },
    
    
    createDeleteGroupPanel: function(groupId, groupName, groupItemId, flag)
    {
        var panelHtml = "<div id=\"newBuddy\" class=\"dialog\" style=\"z-index: 138;\">";
        
        panelHtml +=            "<div id=\"newBuddy_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"newBuddy_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"newBuddy_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"newBuddy_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"newBuddy_top\" class=\"dialog_title title_window top_draggable\">删除分组</div>";
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
        panelHtml +=                            "<div id=\"newBuddy_content\" class=\"dialog_content\" style=\" width: 100%;\">";
        panelHtml +=                                    "<div class='alert'/>";
        if(flag == true){
            panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 12px;\">&nbsp;&nbsp;您确定要将分组'" + groupName + "'删除吗？</div>";
        }else{
            panelHtml +=                                "<div class=\"dialog_info\" style=\"padding: 15 15 15 15 ;font-size: 12px;\">删除分组'" + groupName + "',该组下的好友将被移动到'陌生人'分组下,您确定要将该分组删除吗？</div>";
        }
        panelHtml +=                                    "<input id=\"newBuddyUsername\" type=\"hidden\" onkeypress=\"\" name=\"newBuddyUsername\" value=\"" + groupName + "\" groupId=\"" + groupId + "\" groupItemId=\"" + groupItemId + "\" style=\"width: 110px;\"/>";
        panelHtml +=                                "<div id=\"newbuddy_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a class=\"buttons\" onclick=\"LayoutGroup.deleteGroup('"+groupId+"','" +groupItemId+"');\" href=\"#\">确定</a><a class=\"buttons\" onclick=\"LayoutGroup.closeGroup();\" href=\"#\">取消</a>";
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
        
        var width = 330;
        var height = document.getElementById("newBuddy").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#newBuddy").css("top",top);
        $("#newBuddy").css("left",left);
        $("#newBuddy").css("width",width);

    },
    
    deleteGroup: function(groupId, groupItemId)
    { 
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "changegroup.aspx",
                data: "method=deleteGroup" + 
                  "&userId=" + $("#hidSender").val() + 
                  "&groupId=" + groupId ,
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br/>删除失败，请稍候再试！", true);
                    }
                    else
                    {
                        // 需要删除的组的对象
                        var objGroupItem = document.getElementById(groupItemId);
                        
                        // 获取到陌生人组的对象
                        var buggyList = document.getElementById("buddylist");
                        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
                        var strangerGroup = document.getElementById("ul" + strangerNode.groupid + "Group");
                        
                        // 将该组下的人员添加到陌生人组中
                        var userUiNode = objGroupItem.childNodes[3];
                        
                        while(userUiNode.childNodes.length > 0){
                        // for(var i=0;i<userUiNode.childNodes.length;i++){
                        // for循环在这边不起作用，当执行strangerGroup.appendChild(userUiNode.childNodes[i])后，
                        // userUiNode中的childNodes[i]会被删除掉，userUiNode长度减一
                        // 所以，这边每次都添加userUiNode中的第一个元素，直到userUiNode的长度为0
                            
                            strangerGroup.appendChild(userUiNode.childNodes[0]);
                            //userUiNode.removeChild(userUiNode.childNodes[0]);
                        }
                        
                        // 删除该组
                        var parentGroupItem = objGroupItem.parentElement;
                        parentGroupItem.removeChild(objGroupItem);  
                          
                        if(document.getElementById("newBuddy"))
                        {// 删除弹出的删除该组的div
                           document.body.removeChild(document.getElementById("newBuddy"));
                        }
                        
                        var p = strangerGroup;
                        p.style.display= "none";
                        p.style.display= "block";
                        
                        IM.toggleUserProrate(strangerNode.groupid, '');
                        
                    }
                }
            });
    },
    
    deleteUser: function()
    {
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "friend.aspx",
                data: "method=deleteFriend" + 
                  "&userId=" + $("#hidSender").val() + 
                  "&relationshiper=" + $("#newBuddyUsername").attr("relationshiper"),
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br/>删除失败，请稍候再试！", true);
                    }
                    else
                    {
                        var tempUserItemId = $("#newBuddyUsername").attr("userItemId");
                        var relationshiper = $("#newBuddyUsername").attr("relationshiper")
                        var tempEmptyItem = document.getElementById("li" + relationshiper + "blEmpty");
                        var tempUserItem = document.getElementById(tempUserItemId);
                        
                        var parentContainer = document.getElementById(tempEmptyItem.parentElement.id);
                        
                        var p = tempEmptyItem.parentElement;
                        
                        parentContainer.removeChild(tempUserItem);                      
                        parentContainer.removeChild(tempEmptyItem);
                        
                        p.style.display= "none";
                        p.style.display= "block";
                        
                        if(document.getElementById("newBuddy"))
                        {
                           document.body.removeChild(document.getElementById("newBuddy"));
                        }
                        
                        //------------------------------------------------------------
                        var buggyList = document.getElementById("buddylist");
                        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
                        var tempCopyToGroup = document.getElementById("ul" + strangerNode.groupid + "Group");
                        
                        tempCopyToGroup.appendChild(tempEmptyItem);
                        tempCopyToGroup.appendChild(tempUserItem);
                        
                        // 获取到陌生人组的对象
                        var buggyList = document.getElementById("buddylist");
                        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
                        
                        IM.toggleUserProrate(strangerNode.groupid, '');
                        IM.toggleUserProrate(parentContainer.id.replace(/^ul/,'').replace(/Group$/g,''), '');
                    }
                }
            });
    },
    
    createUserGroups: function()
    {
        if(document.getElementById("divUserGroups"))
        {
            document.body.removeChild(document.getElementById("divUserGroups"));
        }
            
        $.ajax({
            type: "GET",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "changegroup.aspx?method=getUserGroups&userId=" + $("#hidSender").val(),
            
            success: function(result){
                if(result == "0")
                {
                    Effect.appendDiv("<br/>生成用户组列表失败，请稍候再试！", true);
                }
                else
                {
                    $(document.body).append(result);
                    var firstGroup = document.getElementById("aDefaultUserGroup").innerText;
                    document.getElementById("newBuddyGroup").innerText = firstGroup;
                    document.getElementById("newBuddyGroup").value = firstGroup;
                   
                    $("#newBuddyGroup").attr("groupId", $("#aDefaultUserGroup").attr("groupId"));
                    
                    //document.getElementById("aDefaultUserGroup").fireEvent("onmousedown", event);
                }
            }
        });
    },
    
    changeUserGroup: function(genId)
    {
        if($("#newBuddyGroup").attr("groupId") == "" || $("#newBuddyGroup").attr("value") == "")
        {
            Effect.appendDiv("<br/>请选择一个分组！", true);
            return;
        }
        
        var groupId = $("#newBuddyGroup").attr("groupId");
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "changegroup.aspx",
                data: "method=changeUserGroup" + 
                  "&userId=" + $("#hidSender").val() + 
                  "&relationshiper=" + $("#newBuddyUsername").attr("relationshiper") +  
                  "&groupId=" + groupId +
                  "&groupName=" + escape($("#newBuddyGroup").val()),
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br/>移动用户到所选组失败，请稍候再试！", true);
                    }
                    else
                    {
                        var tempUserItemId = $("#newBuddyUsername").attr("userItemId");
                        var relationshiper = $("#newBuddyUsername").attr("relationshiper")
                        var tempEmptyItem = document.getElementById("li" + relationshiper + "blEmpty");
                        var tempUserItem = document.getElementById(tempUserItemId);
                        var tempCopyToGroup = document.getElementById("ul" + $("#newBuddyGroup").attr("groupId") + "Group");
                        
                        var parentContainer = document.getElementById(tempEmptyItem.parentElement.id);
                        
                        parentContainer.removeChild(tempUserItem);                      
                        parentContainer.removeChild(tempEmptyItem);
                        
                        tempCopyToGroup.appendChild(tempEmptyItem);
                        tempCopyToGroup.appendChild(tempUserItem);
                        
                        //var p = tempEmptyItem.parentElement;
                        //p.style.display= "none";
                        //p.style.display= "block";
                        
                        $removeElementById(genId);
                        // 获取到陌生人组的对象
                        var buggyList = document.getElementById("buddylist");
                        var strangerNode = buggyList.childNodes[buggyList.childNodes.length-2];
                        
                        IM.toggleUserProrate(parentContainer.id.replace(/^ul/,'').replace(/Group$/g,''), '');
                        IM.toggleUserProrate(groupId,'');
                    }
                }
            });
    },
    
    createContextmenu: function(userId, userName, userItemId) 
    {
        if(document.getElementById("newBuddy"))
            return;
            
        var panelHtml = "<div class=\"itemList\" id=\"divUserContextmenu\" style=\"width:15px;\" onblur=\"LayoutGroup.removeContextmenu('divUserContextmenu');\">";
        panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createGroupPanel('" + userId + "','" + userName + "','" + userItemId + "');\" style=\"font-size: 11px;\">移动好友</a>";
    
        var strangerUlId = "ul" + $("#hidSender").attr("unknow") + "Group";
        if(event.srcElement.parentElement.id != strangerUlId
         && event.srcElement.parentElement.parentElement.id != strangerUlId)
        {
           panelHtml += "<a href=\"#\" onmousedown=\"LayoutGroup.createDeletePanel('" + userId + "','" + userName + "','" + userItemId + "');\" style=\"font-size: 11px;\">删除好友</a>"; 
        }
        panelHtml += "</div>";
        
        $("#bl").append(panelHtml);
        
        var userPanel = $(event.srcElement);
        var divUserContextmenu = $("#divUserContextmenu");
        
        divUserContextmenu.find("a").each(
            function()
            {
                $(this).width(100);
            }
        );
        
        divUserContextmenu.css("display", "block");
        divUserContextmenu.css({"top": event.clientY - 15, "left": event.clientX- userPanel.offset().left + 15});
        divUserContextmenu.focus();
    },
    
    removeContextmenu: function(delted)
    {
        document.getElementById("bl").removeChild(document.getElementById(delted));
    },
    
    closeGroup: function() 
    {
        var objNewBuddy = document.getElementById('newBuddy');
        if(objNewBuddy) document.body.removeChild(objNewBuddy);
    },
    
    closeChangePasswordPanel: function() 
    {
        var objDivChangePassword = document.getElementById('divChangePassword');
        if(objDivChangePassword) document.body.removeChild(objDivChangePassword);
    },
    
    changePassword: function()
    {
        if(event.srcElement.outter == "0")
        {
            LayoutGroup.createChangePasswordPanel();
        }
    },
    
    changeThePassword: function()
    {
        var passwordBefore = $("#passwordBefore").val();
        var passwordNew = $("#passwordNew").val();
        var passwordConfirm = $("#passwordConfirm").val();
        
        if(passwordBefore == "")
        {
            Effect.appendDiv("<br/>请输入您的原始密码！", true, 230);
            return;
        }
        if(cannotValid(passwordBefore))
        {
            Effect.appendDiv("<br/>原始密码中不能含有包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符！", true, 450);
            return;
        }
        
        
        if(passwordNew == "")
        {
            Effect.appendDiv("<br/>新密码不能为空！", true, 280);
            return;
        }
        if(cannotValid(passwordNew))
        {
            Effect.appendDiv("<br/>新密码中不能含有包含 '\<\>\"\'\&\~\!\@\#\$\%\^\*'等特殊字符！", true, 450);
            return;
        }
        
        if(passwordConfirm != passwordNew)
        {
            Effect.appendDiv("<br/>您输入的新密码跟确认密码不相同！", true, 280);
            return;
        }
        
        $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "changegroup.aspx",
                data: "method=changePassword" + 
                  "&userId=" + $("#hidSender").val() +
                  "&passwordBefore=" + passwordBefore + 
                  "&passwordNew=" + passwordNew,
                  
                success: function(result){
                    if(result == "0")
                    {
                        Effect.appendDiv("<br />修改用户密码失败，请稍候再试！", true);
                    }
                    else if(result == "1")
                    {
                        Effect.appendDiv("<br />用户密码不正确！", true);
                    }
                    else
                    {
                        Effect.appendDiv("<br />" + result, true);
                        LayoutGroup.closeChangePasswordPanel();
                    }
                }
            });
    },
    
    createChangePasswordPanel: function()
    {
        if(document.getElementById("divChangePassword"))
            return;
            
        var panelHtml = "<div id=\"divChangePassword\" class=\"dialog\" >";
        
        panelHtml +=            "<div id=\"divChangePassword_close\" class=\"dialog_close\" onclick=\"Effect.removePanel();\"> </div>";
        panelHtml +=            "<table id=\"divChangePassword_row1\" class=\"top table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td id=\"divChangePassword_nw\" class=\"dialog_nw\"/>";
        panelHtml +=                        "<td id=\"divChangePassword_n\" class=\"dialog_n\">";
        panelHtml +=                            "<div id=\"divChangePassword_top\" class=\"dialog_title title_window top_draggable\">修改密码</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td id=\"divChangePassword_ne\" class=\"dialog_ne\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"divChangePassword_row2\" class=\"mid table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_w\"/>";
        panelHtml +=                        "<td id=\"divChangePassword_table_content\" class=\"dialog_content\" valign=\"top\">";
        panelHtml +=                            "<div id=\"divChangePassword_content\" class=\"dialog_content\" style=\"height: 120px; width: 240px;\">";
        panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding: 14px 14px 10 14px; width: 100%;\">";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 14px; padding-top: 4px;font-size: 12px;\">原始密码：</div>";
        panelHtml +=                                    "<input id=\"passwordBefore\" type=\"password\" maxlength=\"12\" onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/>";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 14px; padding-top: 4px;font-size: 12px;\">新置密码：</div>";
        panelHtml +=                                    "<input id=\"passwordNew\" type=\"password\" maxlength=\"12\" onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/>";
        panelHtml +=                                    "<div style=\"display: block; float: left; margin-right: 14px; padding-top: 4px;font-size: 12px;\">确认密码：</div>";
        panelHtml +=                                    "<input id=\"passwordConfirm\" type=\"password\" maxlength=\"12\" onkeypress=\"\" onmousedown=\"\" onfocus=\"\" onblur=\"\" value=\"\" name=\"newBuddyGroup\" style=\"width: 110px;\"/>";
        
        panelHtml +=                                "</div>";
        panelHtml +=                                "<div id=\"divChangePassword_buttons\" align=center class=\"div_buttons\">";
        panelHtml +=                                    "<a onclick=\"LayoutGroup.changeThePassword();\" href=\"#\"><span class=\"buttons\">保存</span></a><a  onclick=\"LayoutGroup.closeChangePasswordPanel();\" href=\"#\"><span class=\"buttons\">取消</span></a>";
        panelHtml +=                                "</div>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_e\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=            "</table>";
        panelHtml +=            "<table id=\"divChangePassword_row3\" class=\"bot table_window\">";
        panelHtml +=                "<tbody>";
        panelHtml +=                    "<tr>";
        panelHtml +=                        "<td class=\"dialog_sw bottom_draggable\"/>";
        panelHtml +=                        "<td class=\"dialog_s bottom_draggable\">";
        panelHtml +=                            "<div id=\"divChangePassword_bottom\" class=\"status_bar\">";
        panelHtml +=                                "<span style=\"float: left; width: 1px; height: 1px;\"/>";
        panelHtml +=                            "</div>";
        panelHtml +=                        "</td>";
        panelHtml +=                        "<td class=\"dialog_se bottom_draggable\"/>";
        panelHtml +=                    "</tr>";
        panelHtml +=                "</tbody>";
        panelHtml +=           "</table>";
        panelHtml += "</div>";
            
        $(document.body).append(panelHtml);
        $("#divChangePassword").draggable({containment: 'parent' });
        
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        
        var width = 244;
        var height = document.getElementById("divChangePassword").offsetHeight;
        var top = windowHeight/2 - height/2;
        var left = windowWidth/2 - width/2;
        
        $("#divChangePassword").css("top",top);
        $("#divChangePassword").css("left",left);
        $("#divChangePassword").css("width",width);
    }
};
