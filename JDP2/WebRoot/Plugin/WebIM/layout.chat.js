var chatPanelIndex = 0;

var LayoutChat = {
    toggleDraggableDisable: function(chatter)
    {
        $("#div" + chatter).draggable('disable');
    },
    
    toggleDraggableEnable: function(chatter)
    {
       IM.renderTop(chatter);
       $("#div" + chatter).draggable('enable');
    },
    
    createChatPanel: function(chatter, chatterName, theme)
    {   
        if($("#statusSettings").attr("userstatus") != Profile.offLineStatus)
        {
            var clicked = ( (event.srcElement.tagName.toLowerCase() == "li") ? $(event.srcElement) : $(event.srcElement).parent("li"));
            
            if(parseInt(clicked.attr("userstatus")) < Profile.offLineStatus || refreshTimer != null)
            {     
                if(document.getElementById("div" + chatter) == null)
                {
                    var divLeft = 26 + chatPanelIndex * 326;
                    chatPanelIndex++;
                    
                    if(chatPanelIndex <= Profile.chatPanelTotal)
                    {
                        var panelHtml = "<div id=\"div" + chatter + "\" class=\"dialog\" style=\"left: " + divLeft + "px; top: 26px; width: 324px; height: 373px;\" as=\"1\">";
                        
                        panelHtml += "<div class=\"dialog_close\" onclick=\"Effect.removeChatPanel();\" id=\"3d\"></div>";
                        panelHtml += "<div class=\"dialog_minimize\" onclick=\"Effect.toggleChatPanel();\"></div>";
                        panelHtml += "<div class=\"dialog_maximize\" onclick=\"Effect.maximizeChatPanel('" + chatter + "');\"></div>";
                        panelHtml += "<table class=\"top table_window\" onmousedown=\"LayoutChat.toggleDraggableEnable('" + chatter + "');\">";
                        panelHtml += "    <tr>";
                        panelHtml += "        <td class=\"dialog_nw\" />";
                        panelHtml += "        <td class=\"dialog_n\">";
                        panelHtml += "            <div class=\"dialog_title title_window top_draggable\">与 " + chatterName + " 聊天中</div>";
                        panelHtml += "        </td>";
                        panelHtml += "        <td class=\"dialog_ne\" />";
                        panelHtml += "    </tr>";
                        panelHtml += "</table>";
                        panelHtml += "<table class=\"mid table_window\" onmousedown=\"LayoutChat.toggleDraggableDisable('" + chatter + "');\">";
                        panelHtml += "    <tr>";
                        panelHtml += "        <td class=\"dialog_w\" />";
                        panelHtml += "        <td class=\"dialog_content\" valign=\"top\">";
                        panelHtml += "            <div id=\"div" + chatter + "Dialog\" class=\"dialog_content\" style=\"overflow: hidden; height: 335px; width: 320px;\">";
                        panelHtml += "                <div id=\"div" + chatter + "Content\" class=\"rcvdMessages\" style=\"height: 229px; width: 310px;\" onfocus=\"this.blur();\"></div>";
                        panelHtml += "            </div>";
                        panelHtml += "            <div id=\"div" + chatter + "Toolbar\" class=\"imToolbar\" style=\"top: 252px; width: 320px;\">";
                        panelHtml += "                <img alt=\"Bold\" src=\"../include/themes/" + theme +"/window/bold_off.png\" style=\"cursor: pointer;\" onclick=\"Effect.setContentFontWeight('" + chatter + "', 'bold');\"/>";
                        panelHtml += "                <img alt=\"Italic\" src=\"../include/themes/" + theme + "/window/italic_off.png\" style=\"cursor: pointer;\" onclick=\"Effect.setContentFontStyle('" + chatter + "', 'italic');\" />";
                        panelHtml += "                <img alt=\"Underline\" src=\"../include/themes/" + theme + "/window/underline_off.png\" style=\"cursor: pointer;\" onclick=\"Effect.setContentTextDecoration('" + chatter + "', 'underline ')\" />";
                        panelHtml += "            </div>";
                        panelHtml += "            <a id=\"toggle" + chatter + "FontFamily\" class=\"setFontLink\" href=\"#\" style=\"top: 260px;\" onclick=\"Effect.toggleContentFontFamily('" + chatter + "');\" onblur=\"Effect.removeChatFontPanel('" + chatter + "');\">Tahoma</a>";
                        panelHtml += "            <a id=\"toggle" + chatter + "FontSize\" class=\"setFontSizeLink\" href=\"#\" style=\"top: 260px;\" onclick=\"Effect.toggleContentFontSize('" + chatter + "');\" onblur=\"Effect.removeChatFontPanel('" + chatter + "');\">12</a>";
                        panelHtml += "            <a class=\"setFontColorLink\" href=\"#\" style=\"top: 260px;\" onclick=\"Effect.toggleContentFontColor('" + chatter + "');\" onblur=\"Effect.removeChatFontPanel('" + chatter + "');\">";
                        panelHtml += "                <p id=\"toggle" + chatter + "FontColor\" style=\"background-color: #000000; width: 14px; height: 14px;\"></p>";
                        panelHtml += "            </a>";
                        panelHtml += "            <a id=\"toggle" + chatter + "Emotion\" class=\"insertEmoticonLink\" href=\"#\" style=\"top: 260px;\" onclick=\"Effect.toggleContentEmotion('" + chatter + "');\" onblur=\"Effect.removeChatFontPanel('" + chatter + "');\">";
                        panelHtml += "                <img alt=\"\" src=\"../include/images/mini_smile.gif\" style=\"cursor: pointer; border: none;\" />";
                        panelHtml += "            </a>";
                        panelHtml += "            <div style=\"overflow: auto;\">";
                        panelHtml += "                <div iscontext=\"true\" id=\"txt" + chatter + "Content\" class=\"inputText\" contenteditable=\"true\" style=\"top: 280px; left: 5px; width: 304px; font-weight: 400; font-size:12px; color: #000000;\"";
                        panelHtml += "                    font-style: normal; text-decoration: none; \" onkeydown=\"IM.shortcut('" + chatter + "', '" + chatterName + "');\" onfocus=\"IM.renderTop('" + chatter + "');\"></div>";
                        panelHtml += "            </div>";
                        panelHtml += "            <div id=\"div" + chatter + "Button\" style=\"height: 26px; margin-right: -2px; margin-top: -10px; margin-bottom: -2px; margin-left: 88px; width: 233px;\">";
                        panelHtml += "            <input type=\"button\" id=\"btn" + chatter + "Send\" name=\"btn" + chatter + "Send\" value=\"发送\" class=\"buttons\"";
                        panelHtml += "                    onclick=\"IM.sendContent('" + chatter + "', '" + chatterName + "');\" style=\"padding-top:1px;\" title=\"Alt+S、Ctrl+Enter,可以快速发送消息\" />";
                        panelHtml += "            <input type=\"button\" id=\"btn" + chatter + "SaveMessage\" name=\"btn" + chatter + "SaveMessage\" value=\"聊天记录\" class=\"buttons\"";
                        panelHtml += "                    onclick=\"IM.viewCurrentMessage('" + chatter + "');\" style=\"padding-top:1px;\" />";
                        panelHtml += "            </div>";
                        panelHtml += "        </td>";
                        panelHtml += "        <td class=\"dialog_content\" valign=\"top\"><div id=\"div" + chatter + "Date\" style=\"display: none; margin-top: 318px; left: 515px; position: absolute;\">";
                        panelHtml += "            <input type=\"text\" id=\"txt" + chatter + "Date\" class=\"Wdate\" readonly=\"true\"";
                        panelHtml += "                    onfocus=\"WdatePicker({isShowClear: false, maxDate: '%y-%M-%d', onpicked: function(){IM.toggleCurrentMessage('" + chatter + "');}});\"";
                        panelHtml += "                    value=\"" + $("#hidSender").attr("current") + "\"></div>";
                        panelHtml += "            <div id=\"div" + chatter + "Current\" class=\"currentMessage\" style=\"height: 310px; width: 318px;\" onfocus=\"this.blur();\"></div></td>";
                        panelHtml += "        <td class=\"dialog_e\" />";
                        panelHtml += "    </tr>";
                        panelHtml += "</table>";
                        panelHtml += "<table class=\"bot table_window\">";
                        panelHtml += "    <tr>";
                        panelHtml += "        <td class=\"dialog_sw bottom_draggable\" />";
                        panelHtml += "        <td class=\"dialog_s bottom_draggable\">";
                        panelHtml += "            <div class=\"status_bar\">";
                        panelHtml += "                <span style=\"float: left; width: 1px; height: 1px;\" />";
                        panelHtml += "            </div>";
                        panelHtml += "        </td>";
                        panelHtml += "        <td class=\"dialog_sizer bottom_draggable\" />";
                        panelHtml += "    </tr>";
                        panelHtml += "</table>";
                        panelHtml += "</div>";
                        
                        $(document.body).append(panelHtml);
                        $("#div" + chatter).draggable({containment: 'parent', zIndex: 300});
                        
                        $("#txt" + chatter + "Content").focus();
                        
                        LayoutChat.creatChatFontSizePanel(chatter);
                        LayoutChat.createChatFontColorPanel(chatter);
                        LayoutChat.createChatFontFamilyPanel(chatter);
                        LayoutChat.createChatEmotionPanel(chatter);
                    }
                    else
                    {
                        chatPanelIndex--;
                        Effect.appendDiv(Profile.chatPanelOverError);
                    }
                }
            }
        }
    },
    
    creatChatFontSizePanel: function(chatter)
    {
        var panelHtml = "<div class=\"itemList\" id=\"div" + chatter + "FontSize\">";
        var sizeList = Profile.contentFontSize.split(',');
        
        for(var i = 0; i < sizeList.length; i ++)
        {
            panelHtml += "<a href=\"#\" onmousedown=\"Effect.setContentFontSize('" + chatter + "', " + sizeList[i] + "); return false;\">" + sizeList[i] + "</a>";
        }
        
        panelHtml += "</div>";
        $(document.body).append(panelHtml);
    },
     
    createChatFontColorPanel: function(chatter)
    {
        var panelHtml = "<div class=\"itemList\" id=\"div" + chatter + "FontColor\" style=\"cursor: pointer;\"><table class=\"tTable\">";
        var colorList = Profile.contentFontColor.split(',');
        var newLine = Profile.contentFontColorNewLine;
        
        for(var i = 0; i < colorList.length; i++)
        {
            if((i % newLine) == 0)
            {
                panelHtml += "<tr>";
            }
            
            panelHtml += "<td class=\"colorItem\" style=\"width:13px;height:13px;border:1px solid #000;background-color:" + colorList[i] + ";\" onmousedown=\"Effect.setContentFontColor('" + chatter + "', '" + colorList[i] + "');\"></td>";
            
            if((i % newLine) == (newLine - 1))
            {
                panelHtml += "</tr>";
            }
        }
        
        panelHtml += "</table></div>";
        $(document.body).append(panelHtml);
    },
    
    createChatFontFamilyPanel: function(chatter)
    {
        var panelHtml = "<div class=\"itemList\" id=\"div" + chatter + "FontFamily\">";
        var familyList = Profile.contentFontFamily.split(',');
        
        for(var i = 0; i < familyList.length; i ++)
        {
            panelHtml += "<a href=\"#\" onmousedown=\"Effect.setContentFontFamily('" + chatter + "', '" + familyList[i]  + "');return false;\">" + familyList[i] + "</a>";
        }
        
        panelHtml += "</div>";
        $(document.body).append(panelHtml);
    },
    
    createChatEmotionPanel: function(chatter)
    {
        $(document.body).append("<div class=\"itemList\" id=\"div" + chatter + "Emotion\" emotioner=\"" + chatter + "\"></div>");
    }
};