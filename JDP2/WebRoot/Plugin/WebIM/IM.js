var totalUser = 0;
var totalOnLine = 0;
var refreshCount = 0;
var refreshTimer = null;
var autoRefresh = false;

var IM = {
	renderTop : function(chatter) {
		var zIndex = 299;
		document.getElementById("div" + chatter).style.zIndex = zIndex;

		$("div[as = '1']").each(function() {
			if (this.id != "div" + chatter) {
				zIndex--;
				this.style.zIndex = zIndex;
			}
		});
	},

	shortcut : function(chatter, chatterName) {
		if ((event.altKey && event.keyCode == "83")
				|| (event.ctrlKey && event.keyCode == "13")) {
			this.sendContent(chatter, chatterName);
		}

		if (event.keyCode == "13") {
			var textRange = document.selection.createRange();

			textRange.text = "\r\n";
			textRange.select();

			event.returnValue = false;
		}
	},

	sendContent : function(chatter, chatterName) {
		if (parseInt($("#statusSettings").attr("userstatus")) == parseInt(Profile.offLineStatus)) {
			Effect.appendDiv("<br />离线状态，不能发送信息！", true);
		} else {
			var sendContent = $("#txt" + chatter + "Content");

			if (sendContent.html().length == 0) {
				Effect.appendDiv("<br />不能发送空信息！", true);
				sendContent.blur();
			} else {
				if (sendContent.text().length > Profile.contentLength) {
					Effect.appendDiv("<br />发送信息长度过长（" + Profile.contentLength
							+ "）！", true, 300);
					sendContent.blur();
				} else {
					window.clearInterval(contentTimer);

					var contentStyle = "color: " + sendContent.css("color")
							+ "; " + "font-size: "
							+ sendContent.css("font-size") + "; "
							+ "font-style: " + sendContent.css("font-style")
							+ "; " + "font-weight: "
							+ sendContent.css("font-weight") + "; "
							+ "font-family: " + sendContent.css("font-family")
							+ "; " + "text-decoration: "
							+ sendContent.css("text-decoration") + ";";

					$.ajax( {
						type : "POST",
						cache : false,
						dataType : "html",
						ifModified : true,
						url : "sendcontent.aspx",
						data : "flag=0" + "&receiver=" + chatter + "&sender="
								+ $("#hidSender").val() + "&contentstyle="
								+ escape(contentStyle) + "&content="
								+ escape(sendContent.html()) + "&isouter="
								+ $("#hidCnSender").attr("outter")
								+ "&cnsender="
								+ escape($("#hidCnSender").val())
								+ "&loginname="
								+ escape($("#hidCnSender").attr("lname")),
						success : function(result) {
							if (result == "0") {
								Effect.appendDiv("<br>信息发送失败！");
							} else {
								if (result == "1") {
									Effect.appendDiv("<br>用户【" + chatterName
											+ "】已经离线，不能发送信息！", true);
								} else {
									if (result == "2") {
										Effect.appendDiv("<br>保存聊天记录失败！");
									} else {
										if (refreshCount == 0) {
											$("#hidMessageCount").val("0");
										}

										var divContent = $("#div" + chatter
												+ "Content");

										divContent.append(result + "<br />");

										divContent.attr("scrollTop", divContent
												.attr("scrollHeight"));
									}
								}
							}

							$("#txt" + chatter + "Content").html("");
						}
					});

					contentTimer = window.setInterval("IM.receiveContent()",
							3000);
				}
			}
		}
	},

	receiveContent : function() {
		window.clearInterval(contentTimer);

		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "removecontent.aspx?receiver=" + $("#hidSender").val(),
			success : function(result) {
				if (result == "2") {
					Effect.appendDiv("<br>保存聊天记录失败！");
				} else {
					if (result.length > 0) {
						IM.resolveResult(result);
					}
				}
			}
		});

		contentTimer = window.setInterval("IM.receiveContent()", 3000);
	},

	resolveResult : function(result) {
		var xmlDocument = this.getXmlDocument(result);

		var sender = xmlDocument.selectSingleNode("//result/sender").text;
		var content = xmlDocument.selectSingleNode("//result/content").text;

		LayoutAddFriends.addFriends( {
			userid : sender,
			isouter : xmlDocument.selectSingleNode("//result/isouter").text,
			username : xmlDocument.selectSingleNode("//result/username").text,
			loginname : xmlDocument.selectSingleNode("//result/loginname").text
		});

		var divContent = document.getElementById("div" + sender + "Content");

		if (divContent != null) {
			$("#hidMessageCount").val("1");
			$(divContent).append(content + "<br />");
			divContent.scrollTop = divContent.scrollHeight;
		} else {
			//var autoMessage = $("#hidrevertMessage").val();
			//var auto = ( ($("#statusSettings").attr("userstatus") == Profile.busyStatus) && (autoMessage.length > 0));

			if (document.getElementById("div" + sender + "Message") == null) {
				LayoutIm.createTempMessage(sender);

				//if(auto == false)
				//{
				refreshCount++;
				this.createRefreshTimer();
				$("#hidMessageCount").val(refreshCount);
				$("#li" + sender + "blItem").attr("isrefresh", "1");
				//}
			}

			$("#div" + sender + "Message").append(content + "<br />");
		}

		var autoMessage = $("#hidrevertMessage").val();
		var auto = ((parseInt($("#statusSettings").attr("userstatus")) == parseInt(Profile.busyStatus)) && (autoMessage.length > 0));
		auto = auto
				&& (parseInt($("#li" + sender + "blItem").attr("userstatus")) == parseInt(Profile.onLineStatus));

		if (auto == true) {
			$
					.ajax( {
						type : "POST",
						cache : false,
						dataType : "html",
						ifModified : true,
						url : "sendcontent.aspx",
						data : "flag=1" + "&receiver=" + sender + "&sender="
								+ $("#hidSender").val() + "&content="
								+ escape(autoMessage) + "&contentstyle="
								+ escape("color: black;") + "&isouter="
								+ $("#hidCnSender").attr("outter")
								+ "&cnsender="
								+ escape($("#hidCnSender").val())
								+ "&loginname="
								+ escape($("#hidCnSender").attr("lname")),
						success : function(result) {
							if (result == "0") {
								Effect.appendDiv("<br>自动回复信息失败！");
							} else {
								if (result == "1") {
									Effect.appendDiv("<br>用户【" + chatterName
											+ "】已经离线，不能发送信息！", true);
								} else {
									if (result == "2") {
										Effect.appendDiv("<br>保存聊天记录失败！");
									} else {
										if (divContent != null) {
											$(divContent).append(
													result + "<br />");
											divContent.scrollTop = divContent.scrollHeight;
										} else {
											$("#div" + sender + "Message")
													.append(result + "<br />");
										}
									}
								}
							}
						}
					});
		}

		xmlDocument = null;
	},

	removeUser : function() {
		//this.deleteCurrentMessage();

		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "removeuser.aspx?logout=" + $("#hidSender").val(),
			success : function(result) {
				window.location.href = "../imlogin.aspx";
			}
		});
	},

	refreshUser : function() {
		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "refreshuser.aspx?flag=1&owner=" + $("#hidSender").val(),
			success : function(result) {
				if (result != "0") {
					IM.refreshUserTree(result);
					LayoutIm.sequenceUserTree();

					IM.toggleUserProrate("", "0");
				} else {
					IM.removeTimer();
					Effect.appendDiv("<br /> 关闭窗口，重新登录！", false);
				}
			}
		});
	},

	refreshUserTree : function(result) {
		try {
			var xmlDocument = this.getXmlDocument(result);

			var user = xmlDocument.selectSingleNode("//result/user[@userid = '"
					+ $("#hidSender").val() + "']");
			var status = parseInt(user.selectSingleNode("status").text);
			var imageStatus = document.getElementById("imgCurUserStatusIcon");

			imageStatus.src = Profile.userStatusIconPath
					+ Profile.userStatusIconList[status];
			document.getElementById("curStatus").innerHTML = imageStatus.outerHTML
					+ "&nbsp;" + Profile.userStatus[status];

			$(
					"li[userstatus = '" + Profile.busyStatus
							+ "'], li[userstatus = '" + Profile.onLineStatus
							+ "'], li[userstatus = '" + Profile.offLineStatus
							+ "']")
					.each(
							function() {
								var headImage = this.children[0];
								user = xmlDocument
										.selectSingleNode("//result/user[@userid = '"
												+ this.userid + "']");

								if (user == null) {
									if (headImage.src
											.indexOf(Profile.userHeadImage) > -1) {
										headImage.src = Profile.userDefaultHeadImagePath
												+ Profile.userHeadImage;
									}

									this.userstatus = Profile.offLineStatus;
									this.children[1].children[0].src = Profile.userStatusIconPath
											+ Profile.userStatusIconList[this.userstatus];
								} else {
									if (status == parseInt(Profile.offLineStatus)) {
										this.userstatus = Profile.offLineStatus;
										this.children[1].children[0].src = Profile.userStatusIconPath
												+ Profile.userStatusIconList[parseInt(Profile.offLineStatus)];
									} else {
										//                            if(parseInt(this.userstatus) != parseInt(user.selectSingleNode("status").text))
										//                            {
										this.userstatus = user
												.selectSingleNode("status").text;
										this.children[1].children[0].src = Profile.userStatusIconPath
												+ Profile.userStatusIconList[parseInt(this.userstatus)];
										//                            }

										var head = user
												.selectSingleNode("headimg").text;
										this.title = user
												.selectSingleNode("scratch").text;
										this.children[1].childNodes[1].nodeValue = " "
												+ user
														.selectSingleNode("username").text;

										if (document
												.getElementById("hidSender").value == this.userid) {
											document.getElementById("bl_top").innerHTML = "IM "
													+ user
															.selectSingleNode("username").text;
										}

										this.children[2].innerText = IM
												.getSubString(
														user
																.selectSingleNode("scratch").text,
														10);
										headImage.src = (head.length < Profile.userHeadImageCount ? Profile.userDefaultHeadImagePath
												: Profile.userHeadImagePath)
												+ (head.length < 0 ? Profile.userHeadImage
														: head);
									}
								}

								if ((parseInt(this.userstatus) == parseInt(Profile.offLineStatus))
										|| (status == parseInt(Profile.offLineStatus))) {
									headImage.className = "gray";
								} else {
									headImage.className = "";
								}
							});
		} catch (e) {
		}
	},

	toggleUserProrate : function(groupId, operate) {
		if (groupId.length == 0) {
			totalUser = 0;
			totalOnLine = 0;

			$("li[class = 'groupTop']").each(function() {
				IM.refreshUserProrate(this.groupid, operate);
			});

			$("#spanTotal").html(totalOnLine + " / " + totalUser);
		} else {
			IM.refreshUserProrate(groupId, operate);
		}
	},

	refreshUserProrate : function(groupId, operate) {
		var onLineStatus = document.getElementById("ul" + groupId + "Group");
		var onLineStatusCount = onLineStatus.children.length;
		var unOffLineStatusCount = 0;

		for ( var i = 1; i < onLineStatusCount; i += 2) {
			if (parseInt(onLineStatus.children[i].userstatus) < parseInt(Profile.offLineStatus)) {
				unOffLineStatusCount++;
			}
		}

		if (operate == "0") {
			totalOnLine += unOffLineStatusCount;
			totalUser += (onLineStatusCount / 2);
		}

		$("#li" + groupId + "GroupTop").children("span").html(
				"(" + unOffLineStatusCount + "/" + (onLineStatusCount / 2)
						+ ")");
	},

	getSubString : function(content, endLength) {
		if (endLength < content.length) {
			return content.substring(0, endLength) + "...";
		}

		return content;
	},

	refreshUserStatus : function(statusIndex, statusContainer) {
		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "refreshuser.aspx?flag=0&owner=" + $("#hidSender").val()
					+ "&status=" + statusIndex,
			success : function(result) {
				if (result == "1") {
					$("#statusSettings").attr("userstatus", statusIndex);
				} else {
					IM.removeTimer();
					Effect.appendDiv("<br />关闭窗口，重新登录！", false);
				}
			}
		});
	},

	toggleRemoveUser : function() {
		var remove = false;
		var y = event.clientY;
		var x = event.clientX;

		if ($.browser.version > 6) {
			if (window.parent.length == 3) {
				if (y < -90) {
					remove = true;
				}

				//                if(
				//                    (x >= 405 && x <= 680) &&
				//                    (y >= 208 && y <= 450)
				//                  )
				//                {
				//                    
				//                    remove = true;
				//                }

				if (y >= -30 && y <= -9) {
					remove = true;
				}
			} else {
				remove = true;
			}
		} else {
			if (window.parent.length != 3) {
				remove = true;
			}

			//remove = (remove || y < -9000);
			remove = (remove || y < -80);
		}

		if ((autoRefresh == false) && (remove == true)) {
			this.removeUser();
		}
	},

	getXmlDocument : function(result) {
		var xmlDocument = new ActiveXObject("Msxml2.DOMDocument.5.0");

		xmlDocument.async = false;
		xmlDocument.loadXML(result);

		return xmlDocument;
	},

	toggleRefresh : function() {
		var groupId = "";

		$("li[isrefresh = '1']").each(function() {
			var group = $(this).parent("ul");

			if (group.css("display") == "none") {
				if (groupId != group.attr("groupid")) {
					groupId = group.attr("groupid");
					var groupTop = $("#li" + groupId + "GroupTop");

					groupTop.fadeOut("500", function() {
						groupTop.fadeIn("500");
					});
				}
			} else {
				$(this).fadeOut("500", function() {
					$(this).fadeIn("500");
				});
			}
		});
	},

	saveContent : function(chatter, chatterName) {
		/*var writeContent = $("#div" + chatter + "Content").text();
		
		if(writeContent.length > 0)
		{
		    var frmWriter = document.getElementById("frmWrite");
		    
		    frmWriter.document.frames[1].document.getElementById("txtCnReceiver").value = chatterName;
		    frmWriter.document.frames[1].document.getElementById("txtWriteContent").value = writeContent;
		    frmWriter.document.frames[1].document.getElementById("txtCnSender").value = $("#hidCnSender").val();
		    
		    frmWriter.document.frames[1].document.getElementById("btnWrite").click();
		}*/
	},

	viewCurrentMessage : function(chatter) {
		var divCurrent = $("#div" + chatter + "Current");

		if (divCurrent.css("display") == "none") {
			IM.toggleCurrentMessage(chatter);

			divCurrent.css("display", "block");
			Effect.renderCurrentMessage(chatter, true);
			$("#div" + chatter + "Date").css("display", "block");
			divCurrent.attr("scrollTop", divCurrent.attr("scrollHeight"));
		} else {
			divCurrent.css("display", "none");
			Effect.renderCurrentMessage(chatter, false);
			$("#div" + chatter + "Date").css("display", "none");
		}
	},

	toggleCurrentMessage : function(chatter) {
		var searchDate = $("#txt" + chatter + "Date").val();
		var isMatch = searchDate == $("#hidSender").attr("current");

		if (isMatch) {
			window.clearInterval(contentTimer);
		}

		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "currentmessage.aspx?operate=view&sender="
					+ $("#hidSender").val() + "&receiver=" + chatter + "&date="
					+ searchDate,
			success : function(result) {
				if (result == "2") {
					Effect.appendDiv("<br>读取聊天记录失败！");
				} else {
					$("#div" + chatter + "Current").html(result);
				}
			}
		});

		if (isMatch) {
			contentTimer = window.setInterval("IM.receiveContent()", 3000);
		}
	},

	deleteCurrentMessage : function() {
		window.clearInterval(contentTimer);

		$.ajax( {
			type : "GET",
			cache : false,
			dataType : "html",
			ifModified : true,
			url : "currentmessage.aspx?operate=delete&sender="
					+ $("#hidSender").val(),
			success : function(result) {
			}
		});
	},

	createRefreshTimer : function() {
		if (refreshTimer == null) {
			refreshTimer = window.setInterval("IM.toggleRefresh()", 800);
		}
	},

	removeRefreshTimer : function() {
		if (refreshCount == 0) {
			if (refreshTimer != null) {
				window.clearInterval(refreshTimer);
				refreshTimer = null;
			}
		}
	},

	removeTimer : function() {
		window.clearInterval(userTimer);
		window.clearInterval(contentTimer);

		userTimer = null;
		contentTimer = null;
	}
};

document.oncontextmenu = function() {
	if (event.srcElement.iscontext != "true") {
		event.returnValue = false;
	}
}

var userTimer = window.setInterval("IM.refreshUser()", 6000);
var contentTimer = window.setInterval("IM.receiveContent()", 3000);
