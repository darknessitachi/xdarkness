var panelTimer = null;

var LayoutPersonInfo = {

    version: 0,
    
    toggleInfoPanel: function(srcId, top, left)
    {
        window.clearTimeout(panelTimer);
        
        if(document.getElementById("divPersonInfo") != null){
            return;
        }
        
//        if(LayoutPersonInfo.version > 0){
//            return;
//        }    
        
        LayoutPersonInfo.version++;
        
        var parent = srcElement = document.getElementById(srcId);
        var userName = srcElement.parentElement.children[1].innerText;
        
        var grade = "";
        
        $.ajax({
            type: "POST",
            cache: false,
            dataType: "html",
            ifModified: true,
            url: "grade.aspx",
            data: "method=getGrade" + 
              "&userName=" + escape(userName),
              
            success: function(result){
                var gradeInfo = result.split(',');
                
                grade = "<br/><img src=\"../include/themes/" + "dark" + "/grade/" + gradeInfo[0] + "\" />";
                grade += "(" + gradeInfo[2] + ")　在线"+gradeInfo[3]+"天<br/>距离下次升级还剩" + parseInt(gradeInfo[1]) + "天";
                
                var panelHtml = "<div id=\"divPersonInfo\" class=\"dialog\" >";
        
                panelHtml +=            "<table id=\"divSettings_row1\" class=\"top table_window\">";
                panelHtml +=                "<tbody>";
                panelHtml +=                    "<tr>";
                panelHtml +=                        "<td id=\"divSettings_nw\" class=\"dialog_nw\"/>";
                panelHtml +=                        "<td id=\"divSettings_n\" class=\"dialog_n\">";
                panelHtml +=                            "<div id=\"divSettings_top\" class=\"dialog_title title_window top_draggable\">个人信息</div>";
                panelHtml +=                        "</td>";
                panelHtml +=                        "<td id=\"divSettings_ne\" class=\"dialog_ne\"/>";
                panelHtml +=                    "</tr>";
                panelHtml +=                "</tbody>";
                panelHtml +=            "</table>";
                panelHtml +=            "<table id=\"divSettings_row2\" class=\"mid table_window\">";
                panelHtml +=                "<tbody>";
                panelHtml +=                    "<tr>";
                panelHtml +=                        "<td class=\"dialog_w\"/>";
                panelHtml +=                        "<td id=\"divSettings_table_content\" class=\"dialog_content\" valign=\"top\">";
                panelHtml +=                            "<div id=\"divSettings_content\" class=\"dialog_content\" style=\"\">";
                panelHtml +=                                "<div id=\"newbuddy_box\" style=\"padding: 14px 14px 10 14px; width: 100%;font-size:11px;\">";
                
                panelHtml +=                                "" + userName + "　" + grade;
                panelHtml +=                                "<br/>" + srcElement.parentElement.children[2].innerText;
                
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
               
               /*
                var width = 200;
                var top = left = 0;
                var i = 0;
                while(parent.parentElement)
                {
                    i++;
                    if(i==4){//第四个父节点高度不需要计算在内，否则偏移量不对
                        parent = parent.parentElement;
                        continue;
                    }
                    
                    top += parent.offsetTop;
                    left += parent.offsetLeft;
                    parent = parent.parentElement;
                }
                top -= srcElement.offsetHeight + 5 + $("#blContainer").attr("scrollTop");
                left -= width + 10;
                
                $("#divPersonInfo").css("top",top);
                $("#divPersonInfo").css("left",left);
                $("#divPersonInfo").css("width",width);
                */
                
                var width = 200;
                top -= 1 + $("#blContainer").attr("scrollTop") - $("#blContainer").attr("scrollTop");
                left -= width + 5;
                
                
                $("#divPersonInfo").css("top",top);
                $("#divPersonInfo").css("left",left);
                $("#divPersonInfo").css("width",width);
            }
        });
    },
    
    createInfoPanel: function()
    {
        if(event.srcElement.id.indexOf("HeadImg") > -1)
        {
            var top = event.clientY - event.offsetY;
            var left = event.clientX - event.offsetX;
            
            panelTimer = window.setTimeout("LayoutPersonInfo.toggleInfoPanel('" + 
                            event.srcElement.id + "','" + top + "','" + left + "')", 300);
        }
    },
    
    removeInfoPanel: function()
    { 
        window.clearTimeout(panelTimer);
        var divInfo = document.getElementById("divPersonInfo");
        
        if(divInfo != null)
        {
            document.body.removeChild(divInfo);
            //LayoutPersonInfo.version--;
        }
    }    
}
