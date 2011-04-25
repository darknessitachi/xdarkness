
/**
 * @method getSelectElements 获取当前文档中的所有select控件
 * @param {Document} doc 文档对象
 * @return {Array} Select对象数组
 */
getSelectElements = function(doc){
    var selectElements = [];
    var selects = doc.getElementsByTagName('select');
    
    for (var k = 0; k < selects.length; k++) {
        var select = selects[k];
        if (select.style.display != 'none') {
            selectElements.push(select);
        }
    }
    
    var innerIframes = doc.getElementsByTagName('iframe');
    for (var i = 0; i < innerIframes.length; i++) {
        var _selectElements = getSelectElements(innerIframes[i].contentWindow.document);
        for (var j = 0; j < _selectElements.length; j++) {
            var select = _selectElements[j];
            if (select.style.display != 'none') {
                selectElements.push(select);
            }
        }
    }
    
    var innerFrames = doc.getElementsByTagName('frame');
    for (var i = 0; i < innerFrames.length; i++) {
        var _selectElements = getSelectElements(innerFrames[i].contentWindow.document);
        for (var j = 0; j < _selectElements.length; j++) {
            var select = _selectElements[j];
            if (select.style.display != 'none') {
                selectElements.push(select);
            }
        }
    }
    
    return selectElements;
}

/**
 * @class Dialog 窗体对话框
 * @param {Object} _config
 * @version 0.4
 */
var Dialog = Base.extend({
	
 	constructor: function (_config) {
	    var strID;
	    if (arguments.length == 1 && String.isInstance(arguments[0])) {//兼容旧写法
	        strID = arguments[0];
	    }
	    
	    //if (!Dialog.MaxID) {
	    //    Dialog.MaxID = 0;
	   // }
	    //Dialog.MaxID++;
	    
	    this.drag = _config.drag || true;
	    this.onLoad = _config.onLoad;
	    this.OKEvent = _config.OKEvent;
	    
	    this.ID = strID || _config.id || "Dialog" + getUniqueID();//Dialog.MaxID;
	    this.isModal = true;
	    this.coverSelect = false;
	    this.noIframe = _config.noIframe;
	    
	    this.Width = _config.width || 400;
	    this.Height = _config.height || this.Width/2 || 300;
	    this.Title = _config.title || "";
	    this.URL = _config.url;
	    
	    this.Top = 0;
	    this.Left = 0;
	    this.ParentWindow = null;
	    this.Window = null;
	    
	    this.DialogArguments = {};
	    this.WindowFlag = false; //使用弹出窗口?
	    this.Message = null;
	    this.MessageTitle = null;
	    this.ShowMessageRow = false;
	    this.ShowButtonRow = true;
	    this.Icon = null;
	    this.bgdivID = null;
	    this.Animator = true;//遮罩层使用渐变效果？
	    this.resizeable=false;
	},
	
    /**
     * @method displayAllSelect 隐藏浏览器中的所有select控件，防止select穿透div层
     */
	displayAllSelect: function(){
	
	    var selects = getSelectElements($TW.document);
	    for (var i = 0; i < selects.length; i++) {
	        selects[i].style.display = "none";
	    }
	    this.selects = selects;
	},

	/**
	 * @method showAllSelect 显示被当前窗体控制的所有select控件
	 */
	showAllSelect: function(){
	    if (this.selects) {
	        var selects = this.selects;
	        for (var i = 0; i < selects.length; i++) {
	            selects[i].style.display = "";
	        }
	        this.selects = null;
	    }
	},
	
	setPosition: function(){
		
		Dialog.initWindowInfo();
		
		this.Top = ($TW.clientHeight - this.Height - 30) / 2 + $TW.scrollTop - 8;//有8像素的透明背景
		this.Left = ($TW.clientWidth - this.Width - 12) / 2 + $TW.scrollLeft;
		if(this.ShowButtonRow){//按钮行高36
			this.Top -= 18;
		}
		
		this.DialogDiv.style.top=Math.round(this.Top)+"px";
		this.DialogDiv.style.left=Math.round(this.Left)+"px";
		
		$TW.$(this.bgdivID).style.width= Math.max($TW.scrollWidth, $TW.clientWidth) + "px";
		$TW.$(this.bgdivID).style.height= Math.max($TW.scrollHeight, $TW.clientHeight) + "px";
		
	},
		
	/**
	 * @method initBody 初始化body
	 */
	initBodyContent: function() {
		
		var src = (this.URL.indexOf(":") == -1) ? (Server.ContextPath + this.URL) : this.URL;
		src = Sky.getUrl(src);
	    if(this.noIframe) {
	    	var instance = this;
		    new Sky.Ajax({
				url: src,
				onload: function(data) {
					if(instance.loading) {// 界面显示快于数据请求
						var id = "_DialogFrame_" + instance.ID;
						var aim = $TW.$(id);
						aim.innerHTML = data;
						$TW.Effect.initChildren(aim);
						
						var scripts =aim.getElementsByTagName("script");
						var _len = scripts.length;
						
						var _head = $TW.document.getElementsByTagName('head')[0];
		                for(var i = 0;i<_len;i++){
		                	var _script = $TW.document.createElement("script");
		                	_script.defer = true;
		                	_script.type = "text/javascript";
		                	_script.text = scripts[i].innerHTML;
		                	_head.appendChild(_script);
		                }
					} else {// 数据请求快于界面显示
						instance.ajaxBodyContent = data;
					}	
				}
			});
			this.bodyContent = "<div style='background-color: #f2f4f5;overflow:auto;width:" + this.Width + ";height:" + this.Height + ";' id='_DialogFrame_" + this.ID + "' ></div>";
	    } else {
			this.bodyContent = "<iframe src='" + src + "' id='_DialogFrame_" + this.ID + "' allowTransparency='true'  width='" + this.Width + "' height='" + this.Height + "' frameborder='0' style='background-color: #transparent; border:none;'></iframe>";
		}
	},
	
	/**
	 * @method initDialogDiv 初始化对话框
	 */
	initDialogDiv: function(){
		
		var dialog_lt_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_lt.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_lt.png', sizingMethod='crop');\"";
		var dialog_ct_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_ct.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_ct.png', sizingMethod='crop');\"";
		var dialog_rt_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_rt.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_rt.png', sizingMethod='crop');\"";
		
		var dialog_mlm_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_mlm.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_mlm.png', sizingMethod='crop');\"";
		var dialog_mrm_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_mrm.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_mrm.png', sizingMethod='crop');\"";
		var dialog_lb_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_lb.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_lb.png', sizingMethod='crop');\"";
		var dialog_cb_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_cb.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_cb.png', sizingMethod='crop');\"";
		var dialog_rb_style = "style=\"background-image:url(" + Dialog.ImagePath + "dialog_rb.png) !important;background-image: none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Dialog.ImagePath + "dialog_rb.png', sizingMethod='crop');\"";
		
		var dialog_icon = Dialog.ImagePath + "icon_dialog.gif";
		var dialog_closebtn = Dialog.ImagePath + "dialog_closebtn.gif";
		var dialog_closebtn_over = Dialog.ImagePath + "dialog_closebtn_over.gif";
		var dialog_bg = Dialog.ImagePath + "dialog_bg.jpg";
		var dialog_window = Dialog.ImagePath + "window.gif";
		
	    var arr = [];
		//width='100%'
	    arr.push("<table style='-moz-user-select:none;' oncontextmenu='stopEvent(event);' onselectstart='stopEvent(event);' border='0' cellpadding='0' cellspacing='0' width='" + (this.Width + 26) + "'>");
	    arr.push("  <tr xtype='dragTr' style='cursor:move;'>");
	    arr.push("    <td width='13' height='33' "+dialog_lt_style+"><div style='width:13px;'></div></td>");
	    arr.push("    <td height='33' "+dialog_ct_style+"><div style=\"float:left;font-weight:bold; color:#FFFFFF; padding:9px 0 0 4px;\"><img src=\""+dialog_icon+"\" align=\"absmiddle\">&nbsp;" + this.Title + "</div>");
	    arr.push("      <div style=\"position: relative;cursor:pointer; float:right; margin:5px 0 0; _margin:4px 0 0;height:17px; width:28px; background-image:url(" + dialog_closebtn + ")\" onMouseOver=\"this.style.backgroundImage='url(" + dialog_closebtn_over + ")'\" onMouseOut=\"this.style.backgroundImage='url(" + dialog_closebtn + ")'\" drag='false' onClick=\"Dialog.getInstance('" + this.ID + "').CancelButton.onclick.apply(Dialog.getInstance('" + this.ID + "').CancelButton,[]);\"></div></td>");
	    arr.push("    <td width='13' height='33' "+dialog_rt_style+"><div style=\"width:13px;\"></div></td>");
	    arr.push("  </tr>");
	    arr.push("  <tr drag='false'><td width='13' "+dialog_mlm_style+"></td>");
	    arr.push("    <td align='center' valign='top'><div xtype='innerDiv' style='filter:alpha(opacity=30);opacity:0.6;-moz-opacity:0.6;background-color:#7BC9F6;'></div>");
	    arr.push("    <table xtype='innerTable' width='100%' border='0' cellpadding='0' cellspacing='0' bgcolor='#FFFFFF'>");
	    arr.push("        <tr id='_MessageRow_" + this.ID + "' style='display:none'>");
	    arr.push("          <td height='50' valign='top'><table id='_MessageTable_" + this.ID + "' width='100%' border='0' cellspacing='0' cellpadding='8' style=\" background:#EAECE9 url(" + dialog_bg + ") no-repeat right top;\">");
	    arr.push("              <tr><td width='25' height='50' align='right'><img id='_MessageIcon_" + this.ID + "' src='" + dialog_window + "' width='32' height='32'></td>");
	    arr.push("                <td align='left' style='line-height:16px;'>");
	    arr.push("                <h5 class='fb' id='_MessageTitle_" + this.ID + "'>&nbsp;</h5>");
	    arr.push("                <div id='_Message_" + this.ID + "'>&nbsp;</div></td>");
	    arr.push("              </tr></table></td></tr>");
	    arr.push("        <tr><td align='center' valign='top'>");
		
		this.initBodyContent();
	    arr.push(this.bodyContent);
		
		arr.push("		  </td></tr>");
	    arr.push("        <tr drag='false' id='_ButtonRow_" + this.ID + "'><td height='36'>");
	    arr.push("            <div id='_DialogButtons_" + this.ID + "' style='text-align:right; border-top:#dadee5 1px solid; padding:8px 20px; background-color:#f6f6f6;'>");
	    
	    if (this.OKEvent) {
	        arr.push("           	<input id='_ButtonOK_" + this.ID + "'  type='button' value='确 定'>");
	    }
	    
	    arr.push("           	<input id='_ButtonCancel_" + this.ID + "'  type='button' onclick=\"Dialog.getInstance('" + this.ID + "').close();\" value='取 消'>");
	    arr.push("            </div></td></tr>");
	    arr.push("      </table><a href='#;' onfocus='$(\"_DialogFrame_" + this.ID + "\").focus();'></a></td>");
	    arr.push("    <td width='13' "+dialog_mrm_style+"></td></tr>");
	    arr.push("  <tr><td width='13' height='13' "+dialog_lb_style+"></td>");
	    arr.push("    <td "+dialog_cb_style+"></td>");
	    arr.push("    <td width='13' height='13' "+dialog_rb_style+"></td>");
	    arr.push("  </tr></table>");
	    
	    var div = $TW.$("_DialogDiv_" + this.ID);
	    if (!div) {
	        div = $TW.document.createElement("div");
	        $E.hide(div);
	        div.id = "_DialogDiv_" + this.ID;
	        div.className = "dialogdiv";
	        
	        //if(this.drag)
	        div.setAttribute("dragStart", "Dialog.dragStart");
			//div.setAttribute("dragExit","Dialog.dragExit");
	        $TW.$T("body")[0].appendChild(div);
	    }
	    div.onmousedown = function(evt){
	        var w = $E.getTopLevelWindow();
	        w.DragManager.onMouseDown(evt || w.event, div);//注意在ie下，通过attachEvent给dom元素事件注册方法时，this并不指向元素本身，所以在此处必须传递div
	    }
		
	    
	    this.DialogDiv = div;
	    
	    div.innerHTML = arr.join('\n');
	    if(this.noIframe) {
	    	var id = "_DialogFrame_" + this.ID;
	    	if(this.ajaxBodyContent) {
	    		var aim = $TW.$(id);
				aim.innerHTML = this.ajaxBodyContent;
				$TW.Effect.initChildren(aim);
				
				var scripts =aim.getElementsByTagName("script");
				var _len = scripts.length;
				var _head = $TW.document.getElementsByTagName('head')[0];
	            for(var i = 0;i<_len;i++){
	            	var _script = $TW.document.createElement("script");
	            	_script.defer = true;
	            	_script.type = "text/javascript";
	            	_script.text = scripts[i].innerHTML;
	            	_head.appendChild(_script);
	            }
	    	} else {
	    		this.loading = true;
	    		$TW.$(id).innerHTML = "数据加载中，请稍后......";
	    	}
	    }
	},
	
	/**
	 * @method showMessageRow 显示标题图片
	 */
	initMessageRow: function() {
	    if (this.ShowMessageRow) {
	        $E.show($TW.$("_MessageRow_" + this.ID));
	        if (this.MessageTitle) {
	            $TW.$("_MessageTitle_" + this.ID).innerHTML = this.MessageTitle;
	        }
	        if (this.Message) {
	            $TW.$("_Message_" + this.ID).innerHTML = this.Message;
	        }
	    }
	},
	
	/**
	 * @method show 显示窗口
	 */
	show: function(){
	
	    this.displayAllSelect();
	    
	    if (!this.ParentWindow) {
	        this.ParentWindow = window;
	    }
		
		Dialog.initWindowInfo();
		
	    this.TopWindow = $TW;
	    this.DialogArguments._DialogInstance = this;
	    this.DialogArguments.ID = this.ID;
	    
		/**
		 * this.dialogDivWidth=this.Width+13+13;//Dialog容器div的宽
		this.dialogDivHeight=this.Height+33+13;//Dialog容器div的高
		if(this.ShowButtonRow)//如果有显示按钮栏高度加38
			this.dialogDivHeight += 38;
		if(this.ShowMessageRow)//如果有显示消息栏高度加50
			this.dialogDivHeight += 48;
		if(this.Left==0){
			this.Left = (cw - this.dialogDivWidth) / 2 +sw;
		}
		if(this.Top==0){
			this.Top = (ch - this.dialogDivHeight) / 2 + sh ;
		}
	
	
		if(this.resizeable&&window.Resize){
			//new Resize(div);//可调整宽度
			var self=this;
			new Resize(div,{ 
						Max:		true, 
						mxContainer:{clientWidth:cw,clientHeight:ch},
						proxy:	true,
						endResize:function(size){//调整宽度实现
							selfDialogDivWidth=size.width;
							selfDialogDivHeight=size.height;
							selfWidth=selfDialogDivWidth-13-13;//Dialog中iframe的宽
							selfHeight=selfDialogDivHeight-33-13;//Dialog中iframe的高
							if(self.ShowButtonRow)//如果有显示按钮栏
								selfHeight -= 38;
							if(self.ShowMessageRow)//如果有显示消息栏
								selfHeight -= 48;
							self.resize(selfWidth,selfHeight,true);
						}
			});
		}
		
		 */
	    if (!this.Height) {
	        this.Height = this.Width / 2;
	    }
	   if(this.Top==0){
			this.Top = ($TW.clientHeight - this.Height - 30) / 2 + $TW.scrollTop - 8;//有8像素的透明背景
		}
		if(this.Left==0){
			this.Left = ($TW.clientWidth - this.Width - 24) / 2 +$TW.scrollLeft;
		}
	    if (this.ShowButtonRow) {//按钮行高36
	        this.Top -= 18;
	    }
	    if (this.WindowFlag) {
	        this.showWindow();
	        return;
	    }
	    
	    this.initDialogDiv();
	    
	    $TW.$("_DialogFrame_" + this.ID).DialogInstance = this;
	    
		this.initMessageRow();
	
		if (this.OKEvent) {
	        $TW.Effect.initCtrlStyle($TW.$("_ButtonOK_" + this.ID));
			this.OKButton = $TW.$("_ButtonOK_" + this.ID);
			this.OKButton.onclick = this.OKEvent;
	    }
	    $TW.Effect.initCtrlStyle($TW.$("_ButtonCancel_" + this.ID));
	    this.CancelButton = $TW.$("_ButtonCancel_" + this.ID);
		if (this.CancelEvent) {
	        this.CancelButton.onclick = this.CancelEvent;
	    }
		
	    //显示按钮栏
	    if (!this.ShowButtonRow) {
	        $TW.$("_ButtonRow_" + this.ID).hide();
	    }
	    
	   
		var bgdiv;
	    if (!this.AlertFlag) {
			bgdiv = Dialog.getBGDiv();
	        this.bgdivID = "_DialogBGDiv";
	    }
	    else {
			bgdiv = Dialog.getAlertBGDiv();
	        this.bgdivID = "_AlertBGDiv";
	    }
		$E.show(bgdiv);
	    if (bgdiv.style.display == "none") {
	        if (this.coverSelect) {
	            bgdiv.innerHTML = '<iframe src="about:blank" style="filter:alpha(opacity=0);" width="100%" height="100%"></iframe>';
	        }
	        if (this.Animator) {
	            $E.show(bgdiv);
				// pw.Effect.fade(bgdiv,0,10,isIE6?5:2);
	            $TW.Effect.fade(bgdiv, 0, 3, 0.2);
	        }
	        else {
	            $TW.Effect.setAlpha(bgdiv, 3);//pw.Effect.setAlpha(bgdiv,10)
	            $E.show(bgdiv);
	        }
	    }
		//this.DialogDiv.style.cssText = "position:absolute; display:block;z-index:"+(this.AlertFlag?992:990)+";left:"+Math.round(this.Left)+"px;top:"+Math.round(this.Top)+"px;width:"+this.dialogDivWidth+"px;height:"+this.dialogDivHeight+"px";
	    this.DialogDiv.style.cssText = "position:absolute; display:block;z-index:" + (this.AlertFlag ? 992 : 990) + ";left:" + this.Left + "px;top:" + this.Top + "px";
	    //diag.DialogDiv.style.cssText = "position:absolute; display:block;z-index:992;left:"+left+"px;top:"+top+"px;width:"+diag.dialogDivWidth+"px;height:"+diag.dialogDivHeight+"px";
	    //判断当前窗口是否是对话框，如果是，则将其置在bgdiv之后
	    if (!this.AlertFlag) {
	        var win = window;
	        var flag = false;
	        while (win != win.parent) {//需要考虑父窗口是弹出窗口中的一个iframe的情况
	            if (win._DialogInstance) {
	                win._DialogInstance.DialogDiv.style.zIndex = 959;
	                flag = true;
	                break;
	            }
	            win = win.parent;
	        }
	        if (!flag) {
	            bgdiv.style.cssText = "background-color:#ffffff;position:absolute;left:0px;top:0px;opacity:0;filter:alpha(opacity=0);width:100%;height:" + $TW.scrollHeight + "px;z-index:960";
	            $TW.Effect.setAlpha(bgdiv, 3);
	            $E.show(bgdiv);
	        }
			// 设置子窗口在父窗口中的引用
	        this.ParentWindow.$D = this;
	    }
	    if (Browser.isIE) {
	        var $TWbody = $TW.document.getElementsByTagName(Browser.isQuirks ? "BODY" : "HTML")[0];
	        $TWbody.style.overflow = "hidden";//禁止出现滚动条
	    }
	    
	    //放入队列中，以便于ESC时正确关闭
	    $TW.Dialog._Array.push(this.ID);
	},
	
	/**
	 * @method showWindow 使用弹出窗口形式打开窗口
	 */
	showWindow: function(){
	    if (Browser.isIE) {
	        this.ParentWindow.showModalessDialog(this.URL, this.DialogArguments, "dialogWidth:" + this.Width + ";dialogHeight:" + this.Height + ";help:no;scroll:no;status:no");
	    }
	    if (Browser.isGecko) {
	        var sOption = "location=no,menubar=no,status=no;toolbar=no,dependent=yes,dialog=yes,minimizable=no,modal=yes,alwaysRaised=yes,resizable=no";
	        this.Window = this.ParentWindow.open('', this.URL, sOption, true);
	        var w = this.Window;
	        if (!w) {
	            alert("发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!");
	            return;
	        }
	        w.moveTo(this.Left, this.Top);
	        w.resizeTo(this.Width, this.Height + 30);
	        w.focus();
	        w.location.href = this.URL;
	        w.Parent = this.ParentWindow;
	        w.dialogArguments = this.DialogArguments;
	    }
	},
	
	/**
	 * @method reload 窗体内容重新加载
	 * @param {Object} _params get的参数
	 */
	reload: function(_params){
	    var _ifrmae = $E.getTopLevelWindow().$("_DialogFrame_" + this.ID);
	    _ifrmae.src = Sky.getUrl(_ifrmae.src, _params);
	},
	
	
	/**
	 * @method addParam 设置对话框参数
	 * @param {Object} paramName
	 * @param {Object} paramValue
	 */
	addParam: function(paramName, paramValue){
	    this.DialogArguments[paramName] = paramValue;
	},
	
	/**
	 * @method close 关闭对话框
	 */
	close: function(){
	
	    this.showAllSelect();
	    
	    if (this.WindowFlag) {
	        this.ParentWindow.$D = null;
	        this.ParentWindow.$DW = null;
	        this.Window.opener = null;
	        this.Window.close();
	        this.Window = null;
	    }
	    else {
	        //如果上级窗口是对话框，则将其置于bgdiv前
	        
	        var doc = $TW.document;
	        var win = window;
	        var flag = false;
	        while (win != win.parent) {
	            if (win._DialogInstance) {
	                flag = true;
	                win._DialogInstance.DialogDiv.style.zIndex = 961;
	                break;
	            }
	            win = win.parent;
	        }
	        if (this.AlertFlag) {
	            if (this.coverSelect) {
	                $TW.$("_AlertBGDiv").innerHTML = '';
	            }
	            if (this.Animator) {
	                try {
	                    $TW.Effect.fade($TW.$("_AlertBGDiv"), 3, 0, 0.5, function(){
	                        $E.hide($TW.$("_AlertBGDiv"));
	                    });
	                } 
	                catch (e) {
	                }
	            }
	            else {
	                $E.hide($TW.$("_AlertBGDiv"));
	            }
				
				//pw.eval('window._OpacityFunc = function(id){var w = $E.getTopLevelWindow();w.$E.hide(w.$("_AlertBGDiv"));}');
				//pw._OpacityFunc();
	        }
	        if (!flag && !this.AlertFlag) {//此处是为处理弹出窗口被关闭后iframe立即被重定向时背景层不消失的问题
	            if (this.coverSelect) {
	                $TW.$("_DialogBGDiv").innerHTML = '';
	            }
	            $TW.eval('window._OpacityFunc = function(){var w = $E.getTopLevelWindow();$E.hide(w.$("_DialogBGDiv"));}');
	            if (this.Animator) {
	                $TW.Effect.fade($TW.$("_DialogBGDiv"), 3, 0, 0.5, $TW._OpacityFunc);
	            }
	            else {
	                $TW._OpacityFunc();
	            }
	        }
	        this.DialogDiv.outerHTML = "";
	        if (Browser.isIE) {
	            var $TWbody = doc.getElementsByTagName(Browser.isQuirks ? "BODY" : "HTML")[0];
	            $TWbody.style.overflow = "auto";//还原滚动条
	        }
	        $TW.Dialog._Array.remove(this.ID);
	    }
	    
	},
	
	/**
	 * @method addButton 添加按钮
	 * @param {Object} id
	 * @param {Object} txt
	 * @param {Object} func
	 */
	addButton: function(id, txt, func){
	    var html = "<input id='_Button_" + this.ID + "_" + id + "' type='button' value='" + txt + "'> ";
	    $TW.$("_DialogButtons_" + this.ID).$T("input")[0].getParent("a").insertAdjacentHTML("beforeBegin", html);
	    Effect.initCtrlStyle($TW.$("_Button_" + this.ID + "_" + id));
	    $TW.$("_Button_" + this.ID + "_" + id).onclick = func;
	},
	
	/**
	 * @method resize 改变对话框大小
	 * @param {Object} w
	 * @param {Object} h
	 */
	resize: function(w, h){
	    this.Width = w;
		this.Height = h;
		
		Dialog.initWindowInfo();
		
		this.Top = ($TW.clientHeight - this.Height - 30) / 2 + $TW.scrollHeight - 8;//有8像素的透明背景
		this.Left = ($TW.clientWidth - this.Width - 24) / 2 + $TW.scrollWidth;
		if(this.ShowButtonRow){//按钮行高36
			this.Top -= 18;
		}
		
		this.DialogDiv.style.left = this.Left+"px";
		this.DialogDiv.style.top = this.Top+"px";
		this.DialogDiv.$T("table")[0].width = w+26;
		var frame = $TW.$("_DialogFrame_"+this.ID);
		frame.width = this.Width;
		frame.height = this.Height;
	},
	resize: function(w, h, _static){
	    this.Width = w;
	    this.Height = h;
	    var pw = $E.getTopLevelWindow();
	    var doc = pw.document;
	    var vp = $E.getViewportDimensions(pw);
	    var cw = vp.width;
	    var ch = vp.height;
	    var sw = Math.max(document.documentElement.scrollLeft, doc.body.scrollLeft);
	    var sh = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
	    this.dialogDivWidth = this.Width + 13 + 13;//Dialog容器div的宽
	    this.dialogDivHeight = this.Height + 33 + 13;//Dialog容器div的高
	    if (this.ShowButtonRow)//如果有显示按钮栏高度加38
	        this.dialogDivHeight += 38;
	    if (this.ShowMessageRow)//如果有显示消息栏高度加50
	        this.dialogDivHeight += 48;
	    if (!_static) {
	        this.Left = (cw - this.dialogDivWidth) / 2 + sw;
	        this.Top = (ch - this.dialogDivHeight) / 2 + sh;
	        this.DialogDiv.style.left = Math.round(this.Left) + "px";
	        this.DialogDiv.style.top = Math.round(this.Top) + "px";
	    }
	    var frame = pw.$("_DialogFrame_" + this.ID);
	    frame.width = this.Width;
	    frame.height = this.Height;
	    this.DialogDiv.style.width = this.dialogDivWidth + "px";
	    this.DialogDiv.style.height = this.dialogDivHeight + "px";
	}
},{
	ImagePath: Server.ContextPath + "Framework/resources/images/default/dialog/",
	_Array: [],
	
	/**
	 * @method getBGDiv 初始化对话框背景遮罩层
	 * @param {Object} mh
	 * @static
	 */
	getBGDiv: function(id, zIndex){
	    id = id || "_DialogBGDiv";
		zIndex = zIndex || 960;
		var bgdiv = $TW.$(id);
	    if (!bgdiv) {
	        bgdiv = $TW.document.createElement("div");
	        bgdiv.id = id;
	        bgdiv.style.cssText = "background-color:#ffffff;position:absolute;left:0px;top:0px;opacity:0;filter:alpha(opacity=0);width:100%;height:100%;z-index:" + zIndex;
	        $E.hide(bgdiv);
	        $TW.$T("body")[0].appendChild(bgdiv);
	    }
	    return bgdiv;
	},
	
	/**
	 * @method initBackgroundDiv 初始化对话框背景遮罩层
	 * @param {Object} mh
	 * @static
	 */
	getAlertBGDiv: function(){
		return Dialog.getBGDiv("_AlertBGDiv", 991);
	},
	
	/**
	 * @method dragStart 开始拖动对话框
	 * @param {Object} evt
	 */
	dragStart: function(evt){
	    DragManager.doDrag(evt, this.getParent("div"));
	},
	
	/**
	 * @method setPosition 重新设置所有对话框的位置
	 */
	setPosition: function(){
	    if (window.parent != window) {
	        return;
	    } 
	    
	    var DialogArr = $TW.Dialog._Array;
	    if (DialogArr == null || DialogArr.length == 0) { 
	        return;
	    }    
	    
	    for (i = 0; i < DialogArr.length; i++) {
	        $TW.$("_DialogFrame_" + DialogArr[i]).DialogInstance.setPosition();
	    }
	},
	
	
	/**
	 * @method hideAllFlash 非IE浏览器在对话框弹出时必须手工隐藏flash
	 * @param {Window} win window对象
	 */
	hideAllFlash: function(win){
	    if (!win || !win.$T) {//有可能是Dialog.alert()
	        return;
	    }
	    return;//
	    var swfs = win.$T("embed");
	    for (var i = 0; i < swfs.length; i++) {
	        try {
	            swfs[i].OldStyle = swfs[i].style.display;
	            swfs[i].style.display = 'none';
	        } 
	        catch (ex) {
	        }
	    }
	    var fs = win.$T("iframe");
	    for (var i = 0; fs && i < fs.length; i++) {
	        Dialog.hideAllFlash(fs[i].contentWindow);
	    }
	},
	
	/**
	 * @method showAllFlash 显示所有Flash
	 * @param {Window} win window对象
	 */
	showAllFlash: function(win){
	    if (!win || !win.$T) {
	        return;
	    }
	    return;//
	    var swfs = win.$T("embed");
	    for (var i = 0; i < swfs.length; i++) {
	        try {
	            swfs[i].style.display = swfs[i].OldStyle;
	        } 
	        catch (ex) {
	        }
	    }
	    var fs = win.$T("iframe");
	    for (var i = 0; fs && i < fs.length; i++) {
	        Dialog.hideAllFlash(fs[i].contentWindow);
	    }
	},
	
	close: function(evt){
	    try {
	        window.Args._DialogInstance.close();
	    } 
	    catch (ex) {
	    }
	},
	closeAlert: Dialog.closeEx,
	endWait: Dialog.closeEx,
	closeEx:  function(){
	    try {
	        $TW.Dialog.getInstance("_DialogAlert" + (--$TW.Dialog.AlertNo)).close();
	        $TW.clearTimeout($TW.Dialog.WaitID);
	    } 
	    catch (ex) {
	        alert(ex.message);
	    }
	},
	
	getInstance: function(id){
	    var f = $TW.$("_DialogFrame_" + id);
	    if (!f) {
	        return null;
	    }
	    return f.DialogInstance;
	},
	onKeyDown: function(event){
	    if (event.shiftKey && event.keyCode == 9) {//屏蔽shift+tab键
	        
	        if ($TW.Dialog._Array.length > 0) {
	            stopEvent(event);
	            return false;
	        }
	    }
	    if (event.keyCode == 27) {//按ESC键关闭Dialog
	        
	        if ($TW.Dialog._Array.length > 0) {
	            Page.mousedown();
	            Page.click();
	            var diag = $TW.Dialog.getInstance($TW.Dialog._Array[$TW.Dialog._Array.length - 1]);
	            diag.CancelButton.onclick.apply(diag.CancelButton, []);
	        }
	    }
	},dragExit: function(evt){
		var pw = $E.getTopLevelWindow();
		var doc = pw.document;
		var vp = $E.getViewportDimensions(pw);
		var cw = vp.width;
		var ch = vp.height;
		var sw = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
		var sh = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);
		var div = this.getParent("div");
		var left=parseInt(div.style.left);
		var top=parseInt(div.style.top);
	
		//if(left<0)
		//	div.style.left='0px';
		if(left+div.clientWidth-93<0)
			div.style.left=93-div.clientWidth+'px';
		if(left+div.clientWidth>cw)
			div.style.left=cw-div.clientWidth+'px';
		if(top<sh)
			div.style.top=sh+'px';
		if(top+44>sh+ch)
			div.style.top=sh+ch-44+'px';
	},
	
	initWindowInfo: function() {
	    var doc = $TW.document;
		
		/**
		 * var vp = $E.getViewportDimensions(pw);
		var cw = vp.width;
		var ch = vp.height;
		 */
	    $TW.clientWidth = doc.compatMode == "BackCompat" ? doc.body.clientWidth : doc.documentElement.clientWidth;
	    $TW.clientHeight = doc.compatMode == "BackCompat" ? doc.body.clientHeight : doc.documentElement.clientHeight;//必须考虑文本框处于页面边缘处，控件显示不全的问题
	    
		$TW.scrollLeft = Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft);
	    $TW.scrollTop = Math.max(doc.documentElement.scrollTop, doc.body.scrollTop);//考虑滚动的情况
	    $TW.scrollWidth = Math.max(doc.documentElement.scrollWidth, doc.body.scrollWidth);
	    $TW.scrollHeight = Math.max(doc.documentElement.scrollHeight, doc.body.scrollHeight);
	}
});





var _DialogInstance = window.frameElement ? window.frameElement.DialogInstance : null;
Page.onDialogLoad = function(){
    if (_DialogInstance) {
        if (_DialogInstance.Title) {
            document.title = _DialogInstance.Title;
        }
        window.Args = _DialogInstance.DialogArguments;
        _DialogInstance.Window = window;
        window.Parent = _DialogInstance.ParentWindow;
    }
}
Page.onDialogLoad();

Page.onReady(function(){
    var d = _DialogInstance;
    if (d) {
        //try{
        d.ParentWindow.$DW = d.Window;
        var flag = false;
        if (!d.AlertFlag) {
            var win = d.ParentWindow;
            while (win != win.parent) {
                if (win._DialogInstance) {
                    flag = true;
                    break;
                }
                win = win.parent;
            }
            if (!flag) {
                //if(d.Animator)
                //Effect.fade($E.getTopLevelWindow().$("_DialogBGDiv"),0,3,0.2);//如果不是对话框里弹出的对话框，则显示渐变效果
            }
        }
        if (d.AlertFlag) {
            $E.show($TW.$("_AlertBGDiv"));
        }
        if (d.ShowButtonRow && $E.visible(d.CancelButton)) {
            d.CancelButton.focus();
        }
        if (d.onLoad) {
            Page.onLoad(function(){
                d.onLoad();
            });
        }
        //}catch(ex){alert("DialogOnLoad:"+ex.message+"\t("+ex.fileName+" "+ex.lineNumber+")");}
    }
}, 4);

Sky.EventManager.addEvent(document, "keydown", Dialog.onKeyDown);
Sky.EventManager.addEvent(window, "resize", Dialog.setPosition);

