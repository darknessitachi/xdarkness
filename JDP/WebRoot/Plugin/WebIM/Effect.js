var Effect = {
	appendDiv : function(content, auto, w, h) {
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();

		var width = w || 204;
		var height = h || 100;
		var top = windowHeight / 2 - height / 2 - 10;
		var left = windowWidth / 2 - width / 2;

		var panelHtml = "<div id='divDescription' style='height: "
				+ windowHeight + "px; width: " + windowWidth
				+ "px; z-index: 9998;'></div>";

		panelHtml += "<div id=\"divContianer\" class=\"dialog\" style=\"left:"
				+ left + "px;top:" + top + " px;width:" + width
				+ "px; z-index: 9999;\">";

		panelHtml += "<div id=\"divDescription_close\" class=\"dialog_close\" onclick=\"Effect.removeDiv();\"> </div>";
		panelHtml += "<table id=\"divDescription_row1\" class=\"top table_window\">";
		panelHtml += "<tbody>";
		panelHtml += "<tr>";
		panelHtml += "<td id=\"divDescription_nw\" class=\"dialog_nw\"/>";
		panelHtml += "<td id=\"divDescription_n\" class=\"dialog_n\">";
		panelHtml += "<div id=\"divDescription_top\" class=\"dialog_title title_window top_draggable\">信息提示</div>";
		panelHtml += "</td>";
		panelHtml += "<td id=\"divDescription_ne\" class=\"dialog_ne\"/>";
		panelHtml += "</tr>";
		panelHtml += "</tbody>";
		panelHtml += "</table>";
		panelHtml += "<table id=\"divDescription_row2\" class=\"mid table_window\">";
		panelHtml += "<tbody>";
		panelHtml += "<tr>";
		panelHtml += "<td class=\"dialog_w\"/>";
		panelHtml += "<td id=\"divDescription_table_content\" class=\"dialog_content\" valign=\"top\">";
		panelHtml += "<div id=\"divDescription_content\" class=\"dialog_content\" style=\"width: "
				+ (width - 4) + "px;\">";
		panelHtml += "<div id=\"newbuddy_box\" style=\"padding: 0px 10px 10px 10px; width: 90%;font-size:12px;text-align:center;\">";
		panelHtml += "<table><tr><td><div  class=\"alert\" style='align:left;'></div></td>"
		panelHtml += "<td  style='padding-left:10px;'><div align='left' style='padding-left:10px;'>"
				+ content + "</div></td></tr></table>";

		panelHtml += "</div>";
		panelHtml += "<div id=\"divDescription_buttons\" align=center class=\"div_buttons\">";
		panelHtml += "<a class=\"buttons\" onclick=\"Effect.removeDiv();\" href=\"#\">确定</a>";
		panelHtml += "　　　";
		panelHtml += "</div>";
		panelHtml += "</div>";
		panelHtml += "</td>";
		panelHtml += "<td class=\"dialog_e\"/>";
		panelHtml += "</tr>";
		panelHtml += "</tbody>";
		panelHtml += "</table>";
		panelHtml += "<table id=\"divDescription_row3\" class=\"bot table_window\">";
		panelHtml += "<tbody>";
		panelHtml += "<tr>";
		panelHtml += "<td class=\"dialog_sw bottom_draggable\"/>";
		panelHtml += "<td class=\"dialog_s bottom_draggable\">";
		panelHtml += "<div id=\"divDescription_bottom\" class=\"status_bar\">";
		panelHtml += "<span style=\"float: left; width: 1px; height: 1px;\"/>";
		panelHtml += "</div>";
		panelHtml += "</td>";
		panelHtml += "<td class=\"dialog_se bottom_draggable\"/>";
		panelHtml += "</tr>";
		panelHtml += "</tbody>";
		panelHtml += "</table>";
		panelHtml += "</div>";

		$(document.body).append(panelHtml);
		$("#divContianer").draggable( {
			containment : 'parent'
		});
	},

	removeDiv : function() {
		document.body.removeChild(document.getElementById("divContianer"));
		document.body.removeChild(document.getElementById("divDescription"));
	},

	removePanel : function() {
		document.body.removeChild(event.srcElement.parentElement);
	},

	removeChatPanel : function() {
		chatPanelIndex--;
		this.removePanel();
	},

	toggleChatPanel : function() {
		$(event.srcElement.parentElement).find("table").eq(1).toggle();
	},

	maximizeChatPanel : function(chatter) {
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();
		var divCurrent = $("#div" + chatter + "Current");
		var container = $(event.srcElement.parentElement);

		if ((container.width() > Profile.chatContainerSourceWidth)
				&& (container.width() > Profile.chatContainerSourceWidth
						+ Profile.chatCurrentMessageSourceWidth + 5)) {
			$("#div" + chatter + "Button").width(Profile.chatButtonSourceWidth);
			$("#div" + chatter + "Dialog").height(
					Profile.chatDialogSourceHeight);
			$("#div" + chatter + "Toolbar").css("top",
					Profile.chatToolbarSourceTop);
			$("#toggle" + chatter + "Emotion").css("top",
					Profile.chatFontLinkSourceTop);
			$("#toggle" + chatter + "FontSize").css("top",
					Profile.chatFontLinkSourceTop);
			$("#div" + chatter + "Button").css("margin-left",
					Profile.chatButtonSourceLeft);
			$("#toggle" + chatter + "FontFamily").css("top",
					Profile.chatFontLinkSourceTop);
			$("#toggle" + chatter + "FontColor").parent("a").css("top",
					Profile.chatFontLinkSourceTop);
			$("#div" + chatter + "Content").css( {
				"width" : Profile.chatContentSourceWidth,
				"height" : Profile.chatContentSourceHeight
			});
			$("#txt" + chatter + "Content").css( {
				"top" : Profile.chatSendContentSourceTop,
				"width" : Profile.chatSendContentSourceWidth
			});

			if (divCurrent.css("display") == "none") {
				container.css("width", Profile.chatContainerSourceWidth);
			} else {
				container.css("width", Profile.chatContainerSourceWidth
						+ Profile.chatCurrentMessageSourceWidth + 5);
				divCurrent.css( {
					"left" : Profile.chatCurrentMessageSourceLeft,
					"height" : Profile.chatCurrentMessageSourceHeight
				});
				$("#div" + chatter + "Date").css( {
					"left" : Profile.chatDivDateSourceLeft,
					"margin-top" : Profile.chatDivDateSourceTop
				});
			}
		} else {
			container.width(windowWidth);
			container.css( {
				"top" : "0",
				"left" : "0"
			});

			$("#div" + chatter + "Dialog").height(
					windowHeight - Profile.chatDialogHeight);
			$("#div" + chatter + "Toolbar").css("top",
					windowHeight - Profile.chatToolbarTop);
			$("#toggle" + chatter + "Emotion").css("top",
					windowHeight - Profile.chatFontLinkTop);
			$("#txt" + chatter + "Content").css("top",
					windowHeight - Profile.chatSendContentTop);
			$("#toggle" + chatter + "FontSize").css("top",
					windowHeight - Profile.chatFontLinkTop);
			$("#div" + chatter + "Content").css("height",
					windowHeight - Profile.chatContentHeight);
			$("#toggle" + chatter + "FontFamily").css("top",
					windowHeight - Profile.chatFontLinkTop);
			$("#toggle" + chatter + "FontColor").parent("a").css("top",
					windowHeight - Profile.chatFontLinkTop);

			if (divCurrent.css("display") == "none") {
				var buttonWidth = (windowWidth - Profile.chatButtonSourceWidth)
						/ 2 + Profile.chatButtonLeft;

				$("#div" + chatter + "Button").width(windowWidth - buttonWidth);
				$("#div" + chatter + "Button").css("margin-left",
						buttonWidth - 1);
				$("#div" + chatter + "Content").css("width",
						windowWidth - Profile.chatContentWidth);
				$("#txt" + chatter + "Content").css("width",
						windowWidth - Profile.chatSendContentWidth);
			} else {
				var buttonWidth = (windowWidth - Profile.chatButtonSourceWidth
						- Profile.chatCurrentMessageSourceWidth - 5)
						/ 2 + Profile.chatButtonLeft;

				$("#div" + chatter + "Button").width(windowWidth - buttonWidth);
				$("#div" + chatter + "Button").css("margin-left",
						buttonWidth - 2);
				$("#div" + chatter + "Content").css(
						"width",
						windowWidth - Profile.chatContentWidth
								- Profile.chatCurrentMessageLeft + 7);
				$("#txt" + chatter + "Content").css(
						"width",
						windowWidth - Profile.chatSendContentWidth
								- Profile.chatCurrentMessageLeft + 7);
				divCurrent.css( {
					"left" : windowWidth - Profile.chatCurrentMessageLeft,
					"height" : windowHeight - Profile.chatCurrentMessageHeight
				});
				$("#div" + chatter + "Date").css( {
					"left" : windowWidth - Profile.chatDivDateLeft,
					"margin-top" : windowHeight - Profile.chatDivDateTop
				});
			}
		}

		divCurrent.attr("scrollTop", divCurrent.attr("scrollHeight"));
		$("#div" + chatter + "Content").attr("scrollTop",
				$("#div" + chatter + "Content").attr("scrollHeight"));
		;
	},

	renderCurrentMessage : function(chatter, render) {
		var container = $("#div" + chatter);
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();
		var divCurrent = $("#div" + chatter + "Current");

		if ((container.width() > Profile.chatContainerSourceWidth)
				&& (container.width() > Profile.chatContainerSourceWidth
						+ Profile.chatCurrentMessageSourceWidth + 5)) {
			if (!render) {
				var buttonWidth = (windowWidth - Profile.chatButtonSourceWidth)
						/ 2 + Profile.chatButtonLeft;

				$("#div" + chatter + "Button").width(windowWidth - buttonWidth);
				$("#div" + chatter + "Button").css("margin-left",
						buttonWidth - 1);
				$("#div" + chatter + "Content").css("width",
						windowWidth - Profile.chatContentWidth);
				$("#txt" + chatter + "Content").css("width",
						windowWidth - Profile.chatSendContentWidth);
			} else {
				var buttonWidth = (windowWidth - Profile.chatButtonSourceWidth
						- divCurrent.width() - 5)
						/ 2 + Profile.chatButtonLeft;

				$("#div" + chatter + "Button").width(windowWidth - buttonWidth);
				$("#div" + chatter + "Button").css("margin-left",
						buttonWidth - 2);
				$("#div" + chatter + "Content").css(
						"width",
						windowWidth - Profile.chatContentWidth
								- Profile.chatCurrentMessageLeft + 7);
				$("#txt" + chatter + "Content").css(
						"width",
						windowWidth - Profile.chatSendContentWidth
								- Profile.chatCurrentMessageLeft + 7);
				divCurrent.css( {
					"left" : windowWidth - Profile.chatCurrentMessageLeft,
					"height" : windowHeight - Profile.chatCurrentMessageHeight
				});
				$("#div" + chatter + "Date").css( {
					"left" : windowWidth - Profile.chatDivDateLeft,
					"margin-top" : windowHeight - Profile.chatDivDateTop
				});
			}
		} else {
			if (!render) {
				container.css("width", Profile.chatContainerSourceWidth);
			} else {
				container.css("width", container.width() + divCurrent.width()
						+ 5);
				divCurrent.css( {
					"left" : Profile.chatCurrentMessageSourceLeft,
					"height" : Profile.chatCurrentMessageSourceHeight
				});
				$("#div" + chatter + "Date").css( {
					"left" : Profile.chatDivDateSourceLeft,
					"margin-top" : Profile.chatDivDateSourceTop
				});
			}
		}
	},

	removeChatFontPanel : function(chatter) {
		$("#div" + chatter + "FontSize").hide();
		$("#div" + chatter + "FontColor").hide();
		$("#div" + chatter + "FontFamily").hide();
		$("#div" + chatter + "Emotion").hide();
	},

	toggleContentFontSize : function(chatter) {
		var fontPanel = $(event.srcElement);
		var fontSize = $("#div" + chatter + "FontSize");

		fontSize.find("a").each(function() {
			$(this).width(24);
		});

		fontSize.css("display", "block");
		fontSize.css( {
			"top" : fontPanel.offset().top + 19,
			"left" : fontPanel.offset().left
		});
	},

	setContentFontSize : function(chatter, fontSize) {
		$("#txt" + chatter + "Content").css("font-size", fontSize);
		$("#div" + chatter + "FontSize").css("display", "none");
		$("#toggle" + chatter + "FontSize").text(fontSize);
	},

	toggleContentFontColor : function(chatter) {
		var fontPanel = $(event.srcElement);
		var fontColor = $("#div" + chatter + "FontColor");

		fontColor.css("display", "block");
		fontColor.css( {
			"top" : fontPanel.offset().top + 19,
			"left" : fontPanel.offset().left
		});
	},

	setContentFontColor : function(chatter, fontColor) {
		$("#txt" + chatter + "Content").css("color", fontColor);
		$("#div" + chatter + "FontColor").css("display", "none");
		$("#toggle" + chatter + "FontColor").css("background-color", fontColor);
	},

	setContentFontStyle : function(chatter, fontStyle) {
		var container = $("#txt" + chatter + "Content");

		if (container.css("font-style") != "normal") {
			container.css("font-style", "normal");
		} else {
			container.css("font-style", fontStyle);
		}
	},

	toggleContentFontFamily : function(chatter) {
		var fontPanel = $(event.srcElement);
		var fontFamily = $("#div" + chatter + "FontFamily");

		fontFamily.find("a").each(function() {
			$(this).width(85);
		});

		fontFamily.css("display", "block");
		fontFamily.css( {
			"top" : fontPanel.offset().top + 19,
			"left" : fontPanel.offset().left
		});
	},

	setContentFontFamily : function(chatter, fontFamily) {
		$("#txt" + chatter + "Content").css("font-family", fontFamily);
		$("#div" + chatter + "FontFamily").css("display", "none");
		$("#toggle" + chatter + "FontFamily").text(fontFamily);
	},

	setContentFontWeight : function(chatter, fontWeight) {
		var container = $("#txt" + chatter + "Content");

		if (container.css("font-weight") != 400) {
			container.css("font-weight", "normal");
		} else {
			container.css("font-weight", fontWeight);
		}
	},

	setContentTextDecoration : function(chatter, textDecoration) {
		var container = $("#txt" + chatter + "Content");

		if (container.css("text-decoration") != "none") {
			container.css("text-decoration", "none");
		} else {
			container.css("text-decoration", textDecoration);
		}
	},

	toggleContentEmotion : function(chatter) {
		var emotionPanel = $(event.srcElement);
		var emotion = $("#div" + chatter + "Emotion");

		emotion
				.html(document.frames[0].document
						.getElementById("tableEmotion").outerHTML);

		emotion.css("display", "block");
		emotion.css( {
			"top" : emotionPanel.offset().top + 19,
			"left" : emotionPanel.offset().left
		});
	},

	toggleUserStatus : function() {
		LayoutIm.creatUserStatus();

		var userStatusPanel = $('#bl');
		var userStatus = $("#divUserStatus");

		userStatus.find("a").each(function() {
			$(this).width(203);
		});

		userStatus.css("display", "block");
		userStatus.css( {
			"top" : userStatusPanel.offset().top + userStatus.height() + 20,
			"left" : 5
		});
	},

	removeUserStatusPanel : function() {
		$("#divUserStatus").hide();
	},

	setUserStatus : function(userStatus, userStatusIcon, statusIndex) {
		var imgUserStatusIcon = document.getElementById("imgCurUserStatusIcon");

		imgUserStatusIcon.src = userStatusIcon;
		document.getElementById("curStatus").innerHTML = imgUserStatusIcon.outerHTML
				+ " " + userStatus;

		//        if($("#statusSettings").attr("userstatus") != statusIndex)
		//        {
		IM.refreshUserStatus(statusIndex);
		//        }
	},

	toggleUserGroups : function() {
		var groupPanel = $(event.srcElement);
		var divGroups = $("#divUserGroups");

		divGroups.find("a").each(function() {
			$(this).width(105);
		});

		divGroups.css("display", "block");
		divGroups.css("font-size", 14);
		divGroups.css( {
			"top" : groupPanel.offset().top + 19,
			"left" : groupPanel.offset().left - 1
		});
	},

	setUserGroup : function(groupName, groupId) {
		try {
			document.getElementById("newBuddyGroup").innerHTML = groupName;
		} catch (e) {
			document.getElementById("newBuddyGroup").value = groupName;
		}

		$("#newBuddyGroup").attr("groupId", groupId);

		$("#divUserGroups").css("display", "none");
	},

	removeUserGroups : function() {
		$("#divUserGroups").css("display", "none");
	}
};