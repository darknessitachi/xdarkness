$(document).ready(function(){
    LayoutIm.setLayoutPostion();
    LayoutIm.sequenceUserTree();
    IM.refreshUser();
});
   
$(window).resize(function(){
    LayoutIm.setLayoutPostion();
});

var selectedUser = null;

var LayoutIm = {    
    toggleDraggableDisable: function()
    {
        $("#bl").draggable('disable');
    },
    
    toggleDraggableEnable: function()
    {
       $("#bl").draggable('enable');
    },
    
    setLayoutPostion: function()
    {
        var documentWidth = $(window).width();
        var documentHeight = $(window).height();
        
        $("#bl").css("left", documentWidth - Profile.imPanelLeft);
        $("#bl_content").height(documentHeight - Profile.imPanelHeight);
        $("#blContainer").height(documentHeight - Profile.userPanelHeight);
        $("#blBottomToolbar").css("top", documentHeight - Profile.logoutPanelTop);
        
        $("#bl").draggable({ axis: 'x' });
    },
    
    extendGroupList: function(imgPath)
    {
        var objSrcElement = event.srcElement;
        if(objSrcElement.tagName.toLowerCase() == "span")
        {
            objSrcElement = objSrcElement.parentElement;
        }
        if(objSrcElement.tagName.toLowerCase() == "li")
        {            
            if(objSrcElement.groupid != null)
            {
                var groupId = $(objSrcElement).attr("groupid");
                var ulGroup = $("#ul" + groupId + "Group");
                
                if(ulGroup.css("display") == "none")
                {
                    ulGroup.show();
                    document.getElementById("img" + groupId + "GroupArrow").src = Profile.groupArrowTop + imgPath + Profile.gruopArrowFoot + Profile.groupArrowTopImage;
                }
                else
                {
                    ulGroup.hide();
                    document.getElementById("img" + groupId + "GroupArrow").src = Profile.groupArrowTop + imgPath + Profile.gruopArrowFoot + Profile.groupArrowFootImage;
                }
            }
        }
    },
    
    toggleChatPanel: function(chatter, chatterName, theme)
    {       
        if(selectedUser != null)
        {
            selectedUser.attr("class", "listNotSelected");
        }
        
        if(event.srcElement.tagName.toLowerCase() == "li")
        {
            selectedUser = $(event.srcElement);
        }
        else
        {
            selectedUser = $(event.srcElement).parent("li");
        }
        
        selectedUser.attr("class", "listSelected");
        
        LayoutChat.createChatPanel(chatter, chatterName, theme);
        
        if(document.getElementById("div" + chatter + "Message") != null)
        {
            $("#div" + chatter + "Content").append($("#div" + chatter + "Message").html());
            this.removeTempMessage(chatter);
            
            refreshCount--;
            IM.removeRefreshTimer();
            $("#hidMessageCount").val(refreshCount);
            $("#li" + chatter + "blItem").attr("isrefresh", "0");
        }
    },
    
    setSelectedUserOverColor: function()
    {
        if(event.srcElement.tagName.toLowerCase() == 'li')
        {
            $(event.srcElement).attr("class", "listHover");
        }
    },
    
    setSelectedUserOutColor: function()
    {            
        if(event.srcElement.tagName.toLowerCase() == 'li')
        {
            if(selectedUser != event.srcElement)
            {
                $(event.srcElement).attr("class", "listNotSelected");
            }
            else
            {
                $(event.srcElement).attr("class", "listSelected");
            }
        }
    },
    
    creatUserStatus: function()
    {
        var panelHtml = "<div class=\"itemList\" id=\"divUserStatus\">";
        var userStatusArray = Profile.userStatus;
        
        for(var i = 0; i < userStatusArray.length; i++)
        {
            panelHtml += "<a href=\"#\" onmousedown=\"Effect.setUserStatus('" 
                      + userStatusArray[i] + "', '" + Profile.userStatusIconPath + Profile.userStatusIconList[i] + "', " + i + "); return false;\" style=\"font-size: 12px;\">"
                      + "<img style=\"border: 0pt none ;\" src=\"" + Profile.userStatusIconPath + Profile.userStatusIconList[i] + "\"/> " 
                      + userStatusArray[i] + "</a>";
        }
        
        panelHtml += "</div>";
        $("#bl").append(panelHtml);
    },
    
    createTempMessage: function(chatter)
    {
        $(document.body).append("<div id=\"div" + chatter + "Message\" style=\"display: none;\"></div>");
    },
    
    removeTempMessage: function(chatter)
    {
        document.body.removeChild(document.getElementById("div" + chatter + "Message"));
    },
    
    sequenceUserTree: function()
    {
        var statusCount = Profile.userStatus.length;
        
        $(".group").each(
            function()
            {
                var group = this;
                
                for(var i = 0; i < statusCount; i++)
                {
                    $(this).children("li[userstatus = '" + i + "']").each(
                        function()
                        {                          
                            var liEmpty = document.getElementById("li" + this.userid + "blEmpty");
                            
                            try
                            {
                                group.removeChild(liEmpty);
                                group.removeChild(this);
                                
                                group.appendChild(liEmpty);
                                group.appendChild(this);
                            }
                            catch(e)
                            {}
                        }
                    );
                }
            }
        );
    }
};