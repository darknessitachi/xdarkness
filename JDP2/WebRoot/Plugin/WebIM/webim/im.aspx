<%@ Page Language="C#" AutoEventWireup="true" Codebehind="im.aspx.cs" Inherits="Sky.WebIm.im"%>

<script type="text/javascript">
    
    Idea = {
        createPanel: function(){
           
            var panelHtml = '<div id="divIdea" class="dialog" style=" z-index: 138;">';
            
            panelHtml +=            '<div id="newBuddy_close" class="dialog_close" onclick="Effect.removePanel();"> </div>';
            panelHtml +=            '<table id="newBuddy_row1" class="top table_window">';
            panelHtml +=                '<tbody>';
            panelHtml +=                    '<tr>';
            panelHtml +=                        '<td id="newBuddy_nw" class="dialog_nw"/>';
            panelHtml +=                        '<td id="newBuddy_n" class="dialog_n">';
            panelHtml +=                            '<div id="newBuddy_top" class="dialog_title title_window top_draggable">意见反馈</div>';
            panelHtml +=                        '</td>';
            panelHtml +=                        '<td id="newBuddy_ne" class="dialog_ne"/>';
            panelHtml +=                    '</tr>';
            panelHtml +=                '</tbody>';
            panelHtml +=            '</table>';
            panelHtml +=            '<table id="newBuddy_row2" class="mid table_window">';
            panelHtml +=                '<tbody>';
            panelHtml +=                    '<tr>';
            panelHtml +=                        '<td class="dialog_w"/>';
            panelHtml +=                        '<td id="newBuddy_table_content" class="dialog_content" valign="top">';
            panelHtml +=                            '<div id="newBuddy_content" class="dialog_content">';
            
            panelHtml +=                                '<div class="dialog_info" style="padding: 15 15 15 15 ;font-size: 12px;">您对本系统的建议或意见：</div>';
            
            panelHtml +=                                '<div style="padding-left: 22px; width: 100%;">';
            panelHtml +=                                     '<textarea id="ideaContent" style="height:200px;width:80%;" ></textarea>';
            panelHtml +=                                '</div>';
            panelHtml +=                                '<div style="padding: 15 15 15 15 ;" align=center class="div_buttons">';
            panelHtml +=                                    '<a class="buttons" onclick="Idea.sendIdeaBack();" href="#">发送</a><a class="buttons" onclick="Idea.close();" href="#">取消</a>';
            panelHtml +=                                '</div>';
            panelHtml +=                            '</div>';
            panelHtml +=                        '</td>';
            panelHtml +=                        '<td class="dialog_e"/>';
            panelHtml +=                    '</tr>';
            panelHtml +=                '</tbody>';
            panelHtml +=            '</table>';
            panelHtml +=            '<table id="newBuddy_row3" class="bot table_window">';
            panelHtml +=                '<tbody>';
            panelHtml +=                    '<tr>';
            panelHtml +=                        '<td class="dialog_sw bottom_draggable"/>';
            panelHtml +=                        '<td class="dialog_s bottom_draggable">';
            panelHtml +=                            '<div id="newBuddy_bottom" class="status_bar">';
            panelHtml +=                                '<span style="float: left; width: 1px; height: 1px;"/>';
            panelHtml +=                            '</div>';
            panelHtml +=                        '</td>';
            panelHtml +=                        '<td class="dialog_se bottom_draggable"/>';
            panelHtml +=                    '</tr>';
            panelHtml +=                '</tbody>';
            panelHtml +=          '</table>';
            panelHtml += '</div>';
                
            $(document.body).append(panelHtml);
            $('#divIdea').draggable({containment: 'parent' });
            
            var windowWidth = $(window).width();
            var windowHeight = $(window).height();
            
            var width = 300;
            var height = document.getElementById('divIdea').offsetHeight;
            var top = windowHeight/2 - height/2;
            var left = windowWidth/2 - width/2;
            
            $('#divIdea').css("top",50);
            $('#divIdea').css("left",50);
            $('#divIdea').css("width",width);
        },    
            
        sendIdeaBack: function(){
            if($("#ideaContent").val().trim() == ""){
                alert('请输入反馈意见！');
                return;
            }
            
            $.ajax({
                type: "POST",
                cache: false,
                dataType: "html",
                ifModified: true,
                url: "buginfo.aspx",
                data: "userName=" + escape($("#hidCnSender").val()) +
                      "&content=" + escape($("#ideaContent").val()),
                
                success: function(result){
                    
                    alert('反馈发送成功，感谢您对本系统提出的宝贵意见，谢谢！');
                    Idea.close();
                }
            });
            
        },
        
        close: function(){
            var objDivIdea = document.getElementById('divIdea');
            if(objDivIdea) document.body.removeChild(objDivIdea);   
        }
    }
</script>
<body onbeforeunload="IM.toggleRemoveUser();">
    <form id="form1" runat="server">
        <div>
            <input type="button" value="内部使用期间意见反馈" style="cursor:pointer;" onclick="Idea.createPanel();" />
        </div>
        <div id="bl" class="dialog" style="left: 1050px; top: 13px; width: 222px; height: 418px;">
            <div id="bl_minimize" class="dialog_minimize" onclick="Effect.toggleChatPanel();" style="left: 189px;"></div>
            <table id="bl_row1" class="top table_window" onmousedown="LayoutIm.toggleDraggableEnable();">
                <tr>
                    <td id="bl_nw" class="dialog_nw" />
                    <td id="bl_n" class="dialog_n">
                        <div id="bl_top" class="dialog_title title_window top_draggable" runat="server"></div>
                    </td>
                    <td id="bl_ne" class="dialog_ne" />
                </tr>
            </table>
            <table id="bl_row2" class="mid table_window">
                <tr>
                    <td class="dialog_w" />
                    <td id="bl_table_content" class="dialog_content" valign="top">
                        <div id="bl_content" class="dialog_content" style="height: 380px; width: 218px;" onmousedown="LayoutIm.toggleDraggableDisable();">
                            <div id="blTopToolbar" style="left: 5px; top: 23px">
                                <span class="toolbarButton toolbarSpacer">
                                    <img id="addbuddy" class="toolbarButton" alt="添加好友" onclick="LayoutAddFriends.createFriendsPanel();" runat="server" src="" />
                                </span>
                                <span class="toolbarButton toolbarSpacer">
                                    <img id="joinroom" class="toolbarButton" alt="系统设置" onclick="LayoutSettings.createSettingsPanel();" runat="server" src="" />
                                </span>
                                <span class="toolbarButton toolbarSpacer">
                                    <img id="changepassword" class="toolbarButton" alt="修改密码" onclick="LayoutGroup.changePassword();" runat="server" src="" />
                                </span>
                                <div id="statusSettings" runat="server">
                                    <a id="curStatus" onclick="Effect.toggleUserStatus();" href="#" style="font-size: 11px; width: 205px;" onblur="Effect.removeUserStatusPanel();">
                                        <img id="imgCurUserStatusIcon" alt="" runat="server" style="border: 0pt none ;" src="../include/images/online.png"/> 在线
                                    </a>
                                </div>
                            </div>
                            <div id="blContainer" style="width: 210px; height: 287px;">
                                <ul class="sortable box" id="buddylist" runat="server">
                                </ul>
                            </div>
                            <div id="blBottomToolbar" style="width: 210px; top: 379px;">
                                <div style="float: left; font-size: 12px; padding: -2px 0px 0px 0px; margin-left: 1px;">在线人数：<span id="spanTotal">0 / 0</span></div>
                                <a id="btnLogout" onclick="IMLogout();" style="outline-style: none; padding-left:0px;" href="#">
                                    <img alt="注销" style="border: 0pt none;" src="" id="imgLogout" runat="server"/>
                                </a>
                            </div>
                        </div>
                    </td>
                    <td class="dialog_e" />
                </tr>
            </table>
            <table id="bl_row3" class="bot table_window">
                <tr>
                    <td class="dialog_sw bottom_draggable" />
                    <td class="dialog_s bottom_draggable">
                        <div id="bl_bottom" class="status_bar">
                            <span style="float: left; width: 1px; height: 1px;" />
                        </div>
                    </td>
                    <td id="bl_sizer" class="dialog_sizer bottom_draggable" />
                </tr>
            </table>
        </div>
                
        <input id="hidSender" name="hidSender" runat="server" type="hidden" />
        <input id="hidCnSender" name="hidCnSender" runat="server" type="hidden" />
        <input id="hidrevertMessage" name="hidrevertMessage" runat="server" type="hidden" />
        <input id="hidMessageCount" name="hidMessageCount" runat="server" type="hidden" value="0" />
        <iframe frameborder="0" src="emotion.aspx" width="153" height="97" id="frmEmotion" style="display: none;"></iframe>
        <iframe frameborder="0" src="savemessage.aspx" width="0" height="0" id="frmWrite" style="display: none;"></iframe>
        
        <script language="javascript" type="text/javascript" src="../include/script/im/im.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/effect.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/emotion.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/profile.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/layout.chat.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/layout.group.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/layout.friend.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/layout.settings.js"></script>
        <script language="javascript" type="text/javascript" src="../include/script/im/layout.personinfo.js"></script>
        
        <script language="javascript" type="text/javascript">
        function IMLogout()
        {   
            IM.removeUser();
        }
        </script>
    </form>
</body>
</html>