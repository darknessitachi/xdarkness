//多选框全选
function selectAll(ele,eles){
	var flag = $V(ele);
	var arr = $N(eles);
	if(arr){
		for(var i=0;i< arr.length;i++){
			arr[i].checked = flag;
	  }
	}
}
 DynamicDiv = Base.extend({

    constructor: function(config){
        this.url = config.url;
        this.target = $(config.id);
        
        var container = $(document.createElement("div"));
        container.addClass("dynamicDiv");
        
        this.el = container;
        this.el.hide();
        document.body.appendChild(this.el);
    },
    
    bind: function(target) {
    	this.target = $(target);
    	return this;
    },
    
    show: function(params) {
   
    	var div = this;
    	new Sky.Ajax({
        	url: Sky.getUrl(this.url, params),
        	//params: strParams, @TODO this way can't carray the params, but don't know why
        	onload: function(data) {
				div.el.innerHTML = data; 
				div.el.positionTo(div.target);
    
    			div.el.show();
        	}
        });
        /* @TODO Thinking about it's necessary to use dwr, it just can't use basic ajax call
        DWRActionUtil.executeActionURL(this.url.replace(".action",""), params, function(data) {debugger;
				div.el.innerHTML = data; 
				div.el.positionTo(div.target);
    
    			div.el.show();
        	});*/
    	
    },
    
    hide: function() {
    	this.el.hide();
    }
});
 
/**
 * @class Selector 下拉框
 * new Selector({
 data: data.flightList,
 text: "code",
 value: "id",
 id: "",
 appendTo: routeFlightTd
 });
 * @param {Object} config 配置项
 */
Selector = Base.extend({

    constructor: function(config){
        this.create(config);
    },
    
    /**
     * @method create 创建下拉框el对象
     * @param {Object} config 配置项
     * @private
     */
    create: function(config){
		/**
		 * @property config 配置项
		 * @private
		 */
		this.config = config;
		
		var divSelect = null, hiddenField = null;
		if(!(config && (config.tagName && config.tagName.toLowerCase() == "div"))){
	        /* create  select div */
	        divSelect = $(document.createElement("div"));
	        divSelect.id = "select_" + config.id;
	        divSelect.onChange = config.onChange || "";
	        (config.appendTo || document.body).appendChild(divSelect);
	        
	        /* get the data */
	        var datas = config.data || [];
	        for (var j = 0; j < datas.length; j = j + 1) {
	            /* create span */
	            var span = document.createElement('span');
	            span.value = datas[j][config.value || "value"];
	            span.innerHTML = datas[j][config.text || "text"];
	            
	            /* add to select */
	            divSelect.appendChild(span);
	        }
	        
	        hiddenField = $(document.createElement("input"));
	        hiddenField.type = 'hidden';
	        hiddenField.id = config.id;
	        hiddenField.name = config.name || config.id;
	        (config.appendTo || document.body).appendChild(hiddenField);
        } else {
        	divSelect = config;
        	hiddenField = $(divSelect.id.replace("select_",""));
        	
        	/**
        	 * @property multi 是否是多选
        	 */
        	this.multi = divSelect.multi == "true" || divSelect.multi == true;
        }
        
        /**
         * @property el 下拉框对应的页面div元素
         */
        this.el = divSelect;
        
        divSelect.srcClass = this;
        
        
        /**
         * @property hiddenInput 对应的隐藏框
         */
        this.hiddenInput = hiddenField;
		
        /* convert to select control */
        this.initHtml();
        
        try {
            /* bind event */
            this.initMethod();
        } 
        catch (ex) {
            alert(ex);
        }
        if (this.items.length > 10) {
        	this.itemContainer = $(divSelect.id + "_ul");
            this.itemContainer.style.height = "15em";//"120px";//
        }
        
        this.initList();
    },
    
    getItem: function(prop) {
    	var index;
    	if(Number.isInstance(prop)) {
    		index = prop;
    	} else {
    		for(var i=0;i<this.items.length;i++) {
	    		if(this.items[i].value == prop) {
	    			index = i;//this.items[i];
	    			break;
	    		}
	    	}
    	}
    	
    	if(this.multi) {
    		return this.items[index].parentElement.parentElement;
    	}
    	
    	return $(this.items[index]);
    },
    
    showAllItem: function() {
    	for(var i = 0;i < this.items.length;i++){
			this.getItem(i).show();
		}
    },
    
    hideAllItem: function() {
    	for(var i = 0;i < this.items.length;i++){
			this.getItem(i).hide();
		}
    },
    
    setItemHidden: function(prop, hidden) {
    	if(hidden == true) {
    		$E.hide(this.getItem(prop));
    	} else {
    		$E.show(this.getItem(prop));
    	}	
    },
    
    setSelectedIndexs: function(indexs) {
    	if(this.multi) {
    		if(!Array.isInstance(indexs)) {
    			if(Number.isInstance(indexs)) {
    				indexs = [indexs];
    			} else {
    				return;
    			}
    		}
    		this.selectedIndex = indexs;
   			var _v = "", _text="";
   			
   			for(var i=0;i<this.items.length;i++) {
   				$(this.items[i]).checked = false;
   			}
   			
   			for(var i=0;i<this.selectedIndex.length;i++) {
   				if(i != 0) {
   					_v += ",";
   					_text += ",";
   				}
   				
   				try {
   					var item = $(this.items[this.selectedIndex[i]]);
   					item.checked = true;
   					_v += item.$A("value") || "";
   					_text += item.innerText || "";
   				} catch(ex) {
   					// ingore
   				}
   			}
   			this.hiddenInput.value = _v;
   			this.textField.value = _text;
      	} 
    },
    
    getSelectedValue: function() {
    	return this.hiddenInput.value;
    },
    
	initList: function() {
		/*考虑掉form提交数据量，注释下列代码|20100929|wg*/
		/*var forms = document.forms;
		for(var i=0;i<forms.length;i++) {
	
			var inputs = $T("input");
			for(var j=0;j<inputs.length;j++) {
				//if(inputs[j].name && (inputs[j].name.indexOf("SelectList") != -1)) {
				if(inputs[j].xtype && (inputs[j].xtype == "option")) {
					forms[i].removeChild(inputs[j]);
				}
			}

			var selectors = Selector.getSelects();
			
			for(var j=0; j<selectors.length; j++) {
				var name = selectors[j].name.replace("select_","");
				
				if(selectors[j].el.isCategory == "true") {
					continue;
				}
				
				for(var k=0; k<selectors[j].items.length; k++) {
					var input = document.createElement("input");
					input.type = "hidden";
					input.xtype = "option";
					input.name = name + "Options[" + k + "].text";
					input.value = selectors[j].items[k].innerText;
					forms[i].appendChild(input);
					
					input = document.createElement("input");
					input.type = "hidden";
					input.xtype = "option";
					input.name = name + "Options[" + k + "].value";
					input.value = selectors[j].items[k].value;
					forms[i].appendChild(input);
				}
			}
		}*/
	},
	
	/**
	 * @method getValue 获取value
	 * @private
	 */
	getValue: function() {
		if(!this.value) {
			/**
			 * @property value 下拉框的value
			 */
			this.value = this.config.value || this.el.$A("value") || "";
			
			if(this.value.indexOf(",") != -1) {
				this.value = this.value.split(",");
			}
		}
		
		return this.value;
	},
    
    /**
     * @method initIdName 初始化元素id
     * @private
     */
    initIdName: function(){
        var id = this.el.id;
        if (id == null || id == "" || id == "_SKY_NOID_") {
            this.el.id = id = Selector.LastID++;//产生随机ID
        }
		
		var name = this.el.$A("name"); 
        if (!name) {
            this.name = id;
        }
        else {
			this.name = name;
            this.el.removeAttribute("name");
        }
    },
	
	/**
	 * @method getVerifyStr 获取验证字符串
	 * @return String 验证字符串
	 * @private
	 */
	getVerifyStr: function() {
		var verify = this.el.$A("verify");
        var condition = this.el.$A("condition");
        var verifyStr = "";
        if (verify) {
            verifyStr = "verify='" + verify + "'";
            if (condition) {
                verifyStr += " condition='" + condition + "'";
            }
        }
		
		return verifyStr;
	},
    
    getMultiItemString: function(text, value) {
		return "<div style='border:0px;'><div style='float:left;border:0px;'><input type='checkbox' divId='"+this.el.id+"' /></div><div style='border:0px;'><a href=\"javascript:void(0);\" divId='"+this.el.id+"' onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' hidefocus value=\"" + value + "\">" + text + "</a></div></div>";
	},
	
	/**
	 * @method getClassStr 获取Class样式字符串
	 * @return String 样式字符串
	 * @private
	 */
	getClassStr: function() {
		var classStr = this.el.$A("zclass");
        return classStr ? "class='" + classStr + "'" : "";
	},
	
	/**
	 * @method getStyleStr 获取Style样式字符串
	 * @return String 样式字符串
	 * @private
	 */
	getStyleStr: function() {
		var styleText = "";
        if (this.el.$A("styleOriginal") && this.el.$A("styleOriginal") != "NULL") {
            styleText = this.el.$A("styleOriginal");
        }
        else {
            var getWidth = this.el.style.cssText;
            var WidthPattern = /(.*width: *)([0-9]+)(px *;*.*)/gi;
            if (WidthPattern.test(getWidth)) {
                getWidth = getWidth.replace(WidthPattern, function($1, $2, $3, $4){
                    //alert("\n$1: "+$1+"\n$2: "+$2+"\n$3: "+$3+"\n$4: "+$4+"\n");
                    return $3;
                });
                styleText = "width:" + getWidth + "px;";
            }
            else {
                styleText = Browser.isIE ? "width:115px;" : "width:118px;";
            }
        }
		
		return styleText;
	},
	
	/**
	 * @method initDefaultStyle 初始化默认样式
	 * @private
	 */
	initDefaultStyle: function() {
		this.el.className = "zSelect";
        this.el.style.cssText = "display:inline-block; *zoom: 1;*display: inline;vertical-align:middle;position:relative;height:20px;white-space: nowrap;padding:0 0 0 5px;margin-right:3px;";	
	},
	
	createItemString: function(text, value) {
		return "<a href=\"javascript:void(0);\" divId='select_" + this.el.id + "' onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' hidefocus value=\"" + value + "\">" + text + "</a>";
	},
	
    initHtml: function(){
    
        this.initIdName();
        //// 如果div下面有a标签，需要先将其备份，防止被覆盖
        var items = this.el.$T("span").add(this.el.$T("a"));
        var selectedIndex = -1;
        var selectedFlag = true;
        //以下兼容旧写法
        var len = items.length;
        
        if (len > 0 || this.el.innerHTML.trim() == "") {
            
            this.initDefaultStyle();
            
            var arr = [];
            arr.push("<input type='text' id='" + this.el.id + "_textField' xtype='select' " + this.getVerifyStr() + " " + this.getClassStr() + " name='" + this.name + "' autocomplete='off' style='height:18px;line-height:18px;_line-height:12px; padding:0;padding-top:2px;vertical-align:top;border:0 none; background:transparent none; cursor:default;" + (Browser.isIE8 ? "line-height:18px;" : "") + this.getStyleStr() + "' value=''/>");
            arr.push("<img class='arrowimg' src='" + Server.ContextPath + "Framework/resources/images/default/blank.gif' width='18' height='20' id='" + this.el.id + "_arrow' style='position:relative; left:-17px; margin-right:-18px; cursor:pointer; width:18px; height:20px;vertical-align:top; '/>");
            arr.push("<div id='" + this.el.id + "_list' class='optgroup' style='text-align:left;display:none;'>");
            arr.push("<div id='" + this.el.id + "_ul' style='left:-1px; width:-1px;'>");
            
            for (var i = 0; i < len; i++) {
                var iv = items[i].getAttribute("value");
                if (selectedFlag && iv == this.getValue()) {
                    selectedIndex = i;
                    selectedFlag = false;
                }
                if (selectedFlag && items[i].getAttribute("selected") != null) {
                    selectedIndex = i;
                }
                arr.push(this.createItemString(items[i].innerHTML, iv));
            }
            arr.push("</div>");
            arr.push("</div>");
            var html = arr.join('');
            if (html.indexOf("_SKY_NOID_") > 0) {
                html = html.replace("_SKY_NOID_", this.el.id);
            }
            this.el.innerHTML = html;
        }
        else {
            this.el.textField = this.el.childNodes[0];
            if (this.el.textField.style.width == "") {
                this.el.textField.style.width = Browser.isIE ? "115px" : "118px";
            }
            selectedIndex = parseInt(this.el.$A("selectedIndex")) || -1;
            var html = this.el.innerHTML;
            if (html.indexOf("_SKY_NOID_") > 0) {
                this.el.innerHTML = html.replace("_SKY_NOID_", this.el.id);
            }
        }
        
        items = this.el.$T("a");
        var value = this.getValue();
        
        var _selValue = "";
        if(selectedIndex != -1) {
        	_selValue = items[selectedIndex].$A("value");
        }
        
        if (items.length > 0 && value != _selValue) {//此处是因为sky:init中可能会替换掉value的值
            var len = items.length;
            for (var i = 0; i < len; i++) {
                if (value != null && value != '') {
                    if ($(items[i]).$A("value") == value || (Array.isInstance(value) && value.contains($(items[i]).$A("value")))) {
                        
                        if(this.multi) {
                        	if(!Array.isInstance(selectedIndex)) {
                        		selectedIndex = [];
                        	}
                        	selectedIndex.append(i, true);
                        } else {
                        	selectedIndex = i;
                        	break;
                        }
                        this.hiddenInput.value = value;
                    }
                }
                else {
                    if (items[i].$A("selected") == "true") {
                        
                        if(this.multi) {
                        	if(!Array.isInstance(selectedIndex)) {
                        		selectedIndex = [];
                        	}
                        	selectedIndex.append(i, true);
                        } else {
                        	selectedIndex = i;
                        	break;
                        }
                        this.hiddenInput.value = items[i].$A("value");
                    }
                }
            }
        }
        
		/**
		 * 默认隐藏值为第一个选项的值
		 */
        if (!this.hiddenInput.value || this.hiddenInput.value == "") {
            try {
                this.hiddenInput.value = $(items[0]).$A("value") || "";
            } 
            catch (e) {
                // ingore
            }
        }
        
        this.el.InitFlag = true;
        if (Browser.isGecko) {
            this._selectedIndex = selectedIndex;
        }
        else {
			/**
			 * @property selectedIndex 选中项下标索引
			 */
            this.selectedIndex = selectedIndex;
        }
    },
	
	/**
	 * @method setParam 设置参数
	 * @TODO 暂未实现
	 * @param {Object} k
	 * @param {Object} v
	 * @private
	 */
	setParam: function(k, v){
        if (!this.Params) {
            this.Params = new DataCollection();
        }
        this.Params.add(k, v);
	},
	
	/**
	 * @TODO 暂未实现
	 * @param {Object} field
	 * @param {Object} target
	 */
	setLinkAge: function(field, target){
	    if (this.DataSource) {
	        if (this.selectedIndex == 0) {
	            $S(target, null);
	        }
	        else {
	            var dr = this.DataSource.Rows[this.selectedIndex - 1];//前边默认有空行
	            if (dr) {
	                $S(target, dr.get(field));
	            }
	        }
	    }
	},

	/**
	 * @method clear 清空值
	 */
	clear: function(){
	    try {
	    	this.value = "";
		    this.textField.value = "";
			this.hiddenInput.value = "";
			this.selectedIndex = -1;
			
		    if (!this.el.$T("DIV")[0].childNodes[0]) {
		        return;//已经是空的了
		    }
		    this.el.$T("DIV")[0].childNodes[0].innerHTML = "";
		    this.items = this.el.$T("DIV")[0].childNodes[0].$T("a");
	    } catch(e) {
	    }
	},
			
	/**
	 * @method getText 获取文本框显示的值
	 * @return {String} 文本框显示的值
	 */		
	getText: function(){
        return this.textField.value;
    },
	
	/**
	 * @method remove 移除指定索引项
	 * @param {Number} index 
	 */
	remove: function(index){
	    if (index < 0 || index >= this.items.length) {
	        alert("下拉框不能移除index=" + index + "的选项!");
	        return;
	    }
	    if(this.multi) {
	    	this.items[index].parentElement.parentElement.outerHTML = "";
	    } else {
	    	this.items[index].outerHTML = "";
	    }
	    this.options = this.items = this.el.$T("DIV")[0].childNodes[0].$T("a");
	    if (this.selectedIndex == index) {
	        this.selectedIndex = index;//重新设置一遍
	    }
	},
	
	/**
	 * @method addBatch 添加多个下拉项
	 * @param {Array} arr array里面的对象为数组【】
	 * @param {Object} index
	 */
	addBatch: function(arr, index){
        var showValue = this.el.$A("showValue") == "true";
        var html = [];
        for (var i = 0; i < arr.length; i++) {
            var text = arr[i][0];
            var value = arr[i][1];
            if (showValue) {
                if (text) {
                    text = value + '-' + text;
                }
                else {
                    text = "";
                }
            }
            if(this.multi) {
            	html.push(this.getMultiItemString(text, value));
            } else {
            	html.push(this.createItemString(text, value));
            }
        }
        if (!this.items || this.items.length == 0) {
            this.el.$T("DIV")[0].childNodes[0].innerHTML = html.join('\n');
            this.options = this.items = this.el.$T("DIV")[0].childNodes[0].$T("a");
            return;
        }
        var lastIndex = this.items.length - 1;
        if (index != null) {
            index = parseInt(index);
            if (index > lastIndex) {
                index = lastIndex;
            }
        }
        else {
            index = lastIndex;
        }
        if(this.multi) {
        	this.items[index].parentElement.parentElement.insertAdjacentHTML("afterEnd", html.join('\n'));
        } else {
        	this.items[index].insertAdjacentHTML("afterEnd", html.join('\n'));
        }
        this.options = this.items = this.el.$T("DIV")[0].childNodes[0].$T("a");
        if (this.items.length > 10) {
            this.el.$T("DIV")[0].childNodes[0].style.height = "15em";
        }
        
        /* when add a option,and it's value is in the initValues,set it checked and put the text to the show input */
        if(this.el.initValue) {
	        var initValues = this.el.initValue.split(",");
	        for(var i=0;i<this.items.length;i++) {
	        	if(initValues.contains(this.items[i].value) && !this.items[i].checked) {
	        		Selector.onItemClick(this.items[i]);
	        	}
	        }
        }
        
    },
	       
	add: function(text, value, index){
        this.addBatch([[text, value]], index);
        this.initList();
        return this;
    },
	 
	initMethod: function(){
		var ele = this.el;
		
		/**
		 * @property textField 界面文本框
		 */
	    this.textField = ele.childNodes[0];
	    ele.name = this.textField.name;
	    ele.type = "select-one";
		
		/**
		 * @property arrow 下拉框箭头图标
		 */
	    this.arrow = ele.childNodes[1];
	    
	    /**
	     * @property ul 下拉框选项ul
	     */
	    this.ul =  $(this.el.id + "_ul");
		
	    this.options = this.items = $(ele.id + "_ul").$T("a");
	    if (!ele.$A("listurl")) {//自定义页面不需要如下方法
	        //ele.Items = $(ele.id+"_ul").$T("a");	
	        if (Browser.isIE) {
	        	if(this.multi) {
	        		if(Array.isInstance(this.selectedIndex)) {
	        			var _v = "";
	        			for(var i=0;i<this.selectedIndex.length;i++) {
	        				if(i != 0) {
	        					_v += ",";
	        				}
	        				
	        				try {
	        					_v += $(this.items[this.selectedIndex[i]]).$A("value") || "";
	        				} catch(ex) {
	        					// ingore
	        				}
	        			}
	        			ele.value = _v;
	        		}
	        	} else {
	        		if(this.selectedIndex != -1) {
	            		ele.value = this.items.length > 0 ? $(this.items[this.selectedIndex]).$A("value") : "";
	            	} else {
	            		ele.value = "";
	            	}
	            }
	        }
	        else {
	            ele._value = this.items.length > 0 ? $(this.items[this._selectedIndex]).$A("value") : "";
	        }
	        //this.form = ele.getParent("form");
	        this.length = this.options.length;
	        
	        try {
	            this.selectedIndex = this.selectedIndex;
	            if (this.items.length > 0) {
	                if(this.multi) {
	                	if(Array.isInstance(this.selectedIndex)) {
	                		var _v = "";
	                		for(var i=0;i<this.selectedIndex.length;i++) {
	                			if(i != 0) {
	                				_v += ",";
	                			}
	                			_v += Browser.isGecko ? this.items[this._selectedIndex].textContent : this.items[this.selectedIndex[i]].innerText; 
	                			this.items[this.selectedIndex[i]].checked = true;
	                		}
	                		this.textField.value = _v;
	                	} else {
	                		if(this.selectedIndex != -1) {
		                		this.textField.value = Browser.isGecko ? this.items[this._selectedIndex].textContent : this.items[this.selectedIndex].innerText;
		                		this.items[this.selectedIndex].checked = true;
		                	} else {
		                		this.textField.value = "";
		                	}
	                	}
	                } else {
	                	if(this.selectedIndex != -1) {
		                	this.items[this.selectedIndex].checked = true;
		                	this.textField.value = Browser.isGecko ? this.items[this._selectedIndex].textContent : this.items[this.selectedIndex].innerText;
		                } else {
		                	this.textField.value = "";
		                }
	                }
	            }
	        } 
	        catch (ex) {
	            alert(ex.message + this.el.id)
	        }
	    }
	    
	    this.textField.onfocus = this.show.bind(this);
	    
	    var disabled = ele.$A("disabled");
	    ele.disabled = false;
	    if (Browser.isIE) {
	        this.initMethodIE();
	    }
	    else {
	        this.initMethodGecko(ele);
	    }
	    ele.input = ele.$A("input") == 'true' ? true : false;
	    ele.disabled = disabled;
	    //alert([ele.$A("input"),ele.$A("input")=='true']);
	    if (Browser.isIE) {
	        this.setInput(ele.input);
	        this.setDisabled(disabled);
	    }
	    ele.InitFlag = false;
	},
	
	show: function(){
		var ele = this.el;
	    var pw = window;// $E.getTopLevelWindow();
	    ele = ele.getParent("div");
	    var url = ele.$A("listurl");
	    pw.SourceWindow = window;
	    if (ele.id != pw.Selector.showingID || !pw.Selector.showingID) {
	        var div = pw.$("_SelectorDiv_");
	        if (!div) {
	            div = pw.document.createElement("div");
	            div.id = "_SelectorDiv_";
	            div.style.position = "absolute";//不能直接隐藏，否则会导致Gecko下currentStyle失败。
	            div.style.left = 0;
	            div.style.top = 0;
	            div.style.width = 0;
	            div.style.height = 0;
	            pw.$T("body")[0].appendChild(div);
	            div = $(div);
	        }
	        if (url) {
	            if (!pw.$("_SelectorFrame" + ele.id) || pw.$("_SelectorFrame" + ele.id).SourcePathName != window.location.pathname) {
	                if (url.indexOf(":") == -1) {
	                    url = Server.ContextPath + url;
	                }
	                div.innerHTML = "<iframe id='_SelectorFrame" + ele.id + "' frameborder='0' width='100%' src='" + url + "'></iframe>";
	                var fwin = pw.$("_SelectorFrame" + ele.id).contentWindow;
	                pw.$("_SelectorFrame" + ele.id).SourcePathName = window.location.pathname;
	                fwin._OnLoad = function(){
	                    Selector.removeFrameMouseDownEvent(fwin);
	                    fwin.SelectedText = ele.textField.value;
	                    fwin.SelectedValue = ele.value;
	                    fwin.SelectorID = ele.id;
	                    if (ele._ScrollTop) {
	                        fwin.document.body.scrollTop = ele._ScrollTop;
	                    }
	                    Selector.setListURLStyle(ele.id);
	                }
	            }
	            else {
	                Selector.setListURLStyle(ele.id);
	                var fwin = pw.$("_SelectorFrame" + ele.id).contentWindow;
	                fwin.SelectedText = ele.textField.value;
	                fwin.SelectedValue = ele.value;
	            }
	        }
	        else {
	            if (!pw.$("_SelectorFrame")) {
	                div.innerHTML = "<iframe id='_SelectorFrame' frameborder='0' width='100%' src='about:blank'></iframe>";
	                var win = pw.$("_SelectorFrame").contentWindow;
	                var doc = win.document;
	                doc.open();
	                doc.write("<style type='text/css'>* { box-sizing: border-box; -moz-box-sizing: border-box; -khtml-box-sizing: border-box; -webkit-box-sizing: border-box; }");
	                doc.write("html,body {scrollbar-arrow-color: #68a;scrollbar-3dlight-color: #acd;scrollbar-shadow-color: #9bc;scrollbar-face-color: #def;scrollbar-darkshadow-color: #def;scrollbar-highlight-color: #fff;scrollbar-track-color: #eee;}");
	                doc.write("body{margin: 0;padding: 0;color: #444;min-height:100%;height: auto;_height:100%;_overflow:auto;text-align: justify;text-justify: inter-ideograph;font: 12px/1.4 Tahoma, SimSun, Verdana, sans-serif;background: #ffffff;}");
	                doc.write("body,a,div,img{ margin: 0; padding: 0;box-sizing: border-box; -moz-box-sizing: border-box; -khtml-box-sizing: border-box; -webkit-box-sizing: border-box;}");
	                doc.write("div,p,span{font: 12px/1.4 Tahoma, SimSun, Verdana, sans-serif;word-break: break-all;};");
	                doc.write(".optgroup {position:absolute;z-index:666;left:0;top:0;color: #369;}");
	                doc.write(".optgroup div{padding:2px;overflow: auto;overflow-x: hidden;max-height:300px;color: #369;border: 1px solid #678;background: #f7fafc url(" + Server.ContextPath + "Platform/Images/textarea_bg.gif) repeat 0 2px;width:auto;z-index:888;}");
	                doc.write(".optgroup a{cursor:default;display:block;color: #369;white-space: nowrap;padding:1px 3px 2px 6px;_padding:0 3px 0 6px;height:18px;min-width:2em;text-decoration:none;}");
	                doc.write(".optgroup a:hover,.optgroup a.ahover{color: #cff;text-decoration:none;background:#49e url(" + Server.ContextPath + "Framework/Images/optionbg_over.gif) repeat-x center;}");
	                doc.write(".optgroup a.ahover{background-image:none;}</style>");
	                doc.write("<body onselectstart='return false;' style='margin: 0px;-moz-user-select:none;' oncontextmenu='return false;'></body>");
	                try {
	                    doc.close();
	                } 
	                catch (ex) {
	                }//Firefox下可能会报错
	                this.setListStyle();
	            }
	            else {
	                this.setListStyle();
	            }
	        }
	    }
	    //Misc.lockScroll(window);
	},
	
	setListURLStyle: function(){
		var id = this.el.id;
	    var pw = window;//$E.getTopLevelWindow();
	    pw.Selector.showingID = id;
	    var ele = this.el;
	    var div = pw.$("_SelectorDiv_");
	    div.show();
	    var dim = ele.getDimensions();
	    var pos = ele.getPositionEx();
	    var lw = ele.$A("listwidth") ? parseInt(ele.$A("listwidth")) : 0;
	    var lh = ele.$A("listheight") ? parseInt(ele.$A("listheight")) : 0;
	    var frame = pw.$("_SelectorFrame" + id);
	    if (!lw) {
	        lw = dim.width;
	    }
	    frame.width = lw;
	    if (!lh) {
	        lh = 150;
	    }
	    frame.height = lh;
	    pos = $E.computePosition(pos.x, pos.y, pos.x, pos.y + dim.height, "all", lw, lh, pw);
	    div.style.cssText = "position:absolute;display:inline-block;z-index:999;width:" + lw + "px;height:" + lh + "px;left:" + pos.x + "px;top:" + pos.y + "px";
	    frame.style.border = "1px solid #678";
	},
	
	setListStyle: function(){
		var id = this.el.id;
	    var pw = window;//$E.getTopLevelWindow();;
	    pw.Selector.showingID = id;
	    var html = $(id + "_list").outerHTML;
	    var div = pw.$("_SelectorDiv_");
	    div.show();
	    var dim = this.el.getDimensions();
	    var pos = this.el.getPositionEx();
	    div.style.cssText = "position:absolute; display:inline-block;z-index:999;width:" + dim.width + "px;left:-1000px;top:-1000px";
	    var frame = pw.$("_SelectorFrame");
	    var doc = frame.contentWindow.document;
	    frame.contentWindow.TopWindow = pw;
	    html = html.replace(/Selector\./g, "TopWindow.Selector.");
	    if (doc.body.childNodes.length == 0) {
	        var listdiv = doc.createElement("div");
	        listdiv.innerHTML = html;
	        doc.body.appendChild(listdiv);
	    }
	    else {
	        doc.body.childNodes[0].innerHTML = html;
	    }
	    var list = doc.getElementById(id + "_list");
	    list.style.display = 'inline';
	    list = doc.getElementById(id + "_ul");
	    //设置listwidht,listheight
	    var dimlist = $E.getDimensions(list);
	    div = this.el;//Selector.getSourceDiv(id);
	    var lw = div.$A("listwidth") ? parseInt(div.$A("listwidth")) : 0;
	    var lh = div.$A("listheight") ? parseInt(div.$A("listheight")) : 0;
	    //alert([lw,dim.width]);
	    if (!lw) {
	        lw = dim.width;
	    }
	    frame.width = lw;
	    list.style.width = lw + "px";
	    if (!lh || lh > dimlist.height) {
	        lh = dimlist.height;
	    }
	    list.style.height = lh + "px";
	    frame.height = lh;
	    
	    if (this.options.length > 0) {
	    	// TODO div.selectedIndex 需要修改成数组
	    	if(this.multi) {
	    		if(Array.isInstance(this.selectedIndex)) {
	    			for(var i=0; i<this.selectedIndex.length; i++) {
	    				list.getElementsByTagName("a")[this.selectedIndex[i]].className = "ahover";//选中
	    			}
	    		} else {
	    			if(this.selectedIndex != -1) {
	    				list.getElementsByTagName("a")[this.selectedIndex].className = "ahover";//选中
	    			}
	    		}
	    		
	    	} else {
	    		if(this.selectedIndex != -1) {
		        	list.getElementsByTagName("a")[this.selectedIndex].className = "ahover";//选中
		        }
		    }    
	        if (div._ScrollTop) {
	            list.scrollTop = div._ScrollTop;
	        }
	    }
	    
	    pos = $E.computePosition(pos.x, pos.y, pos.x, pos.y + dim.height, "right", lw, lh, pw);
	    div = pw.$("_SelectorDiv_");
	    div.style.left = pos.x + 2 + "px";
	    div.style.top = pos.y + 2 + "px";
	    
	    if(pos.y + 2 <= 0) {/* 上面显示不全，高度减少显示不全的高度 */
	    	div.style.height = (div.getDimensions().height +  pos.y + 2) + "px";
	    	div.style.top = 0 + "px";
	    }
	    
	    var doc = pw?pw.document:document;
		var ch = Browser.isQuirks?doc.body.clientHeight:doc.documentElement.clientHeight;
		ch +=  document.body.scrollTop;
	    if(pos.y+2+div.getDimensions().height > ch) {/* 下面显示不全，高度减少显示不全的高度 */
	    	div.style.height = (ch -  pos.y -2) + "px";
	    	
	    	this.ul.style["height"] = (ch -  pos.y -2) + "px";
	    	//this.ul.height = (ch -  pos.y -2) + "px";
	    	
	    	frame.height = (ch -  pos.y -2) + "px";
	    }
	},
	
	onArrowMouseOver: function(){
	   /* if (this.options.length > 0) {
	    	if(this.multi) {
	    		if(Array.isInstance(this.selectedIndex)) {
	    			for(var i=0; i<this.selectedIndex.length; i++) {
	    				this.options[this.selectedIndex[i]].className = "ahover";
	    			}
	    		} else {
	    			this.options[this.selectedIndex].className = "";
	    		}
	    	}	
	        
	    }
	    */
	   // $E.addClassName("zSelectMouseOver", this.el);
	},
	
	onArrowMouseOut: function(){
	    //$E.removeClassName("zSelectMouseOver", this.el);
	},
	
	getEl: function() {
		return this.el;	
	},
	
	setSelectedIndex: function(index){
		var ele = this.el;
	    var oldValue = ele.value;
	    var item = $(this.items[index]);
	    ele.ItemClickFlag = true;
	    var _v = item.$A("value");
	    if (Browser.isIE) {
	        ele.value = _v;
	        $(ele.id.replace("select_", "")).value = _v;
	    }
	    else {
	        ele._value = _v;
	    }
	    this.textField.value = item.innerText;
	    var pw = window;//$E.getTopLevelWindow();;
	    ele.ItemClickFlag = false;
	    if (oldValue != _v) {
	        try {
	            Selector.invokeOnChange(ele, _v, item.innerText);
	        } 
	        catch (ex) {debubber;
	            alert("Selector.invokeOnChange():" + ex.message);
	        };
	    }
	},
	
	setInput: function(flag){
		var ele = this.el;
	    if (!flag || flag == "false") {
	        if (Browser.isIE) {
	            this.textField.onselectstart = stopEvent;
	            this.textField.onmousedown = stopEvent;
	        }
	        else {
	            this.textField.style.MozUserSelect = "none";
	            this.textField.onmousedown = function(evt){
	                evt = getEvent(evt);
	                var pw = window;//$E.getTopLevelWindow();;
	                var div = evt.srcElement.parentElement;
	                if (div.id == pw.Selector.showingID && pw.SourceWindow == window) {
	                    return stopEvent(evt);
	                }
	            }
	        }
	        ele.removeClassName("zSelectEditable");
	        this.textField.oncontextmenu = stopEvent;
	        this.textField.onblur = null;
	    }
	    else {
	        if (Browser.isIE) {
	            this.textField.onselectstart = null;
	            this.textField.onmousedown = null;
	        }
	        else {
	            this.textField.style.MozUserSelect = "";
	        }
	        ele.addClassName("zSelectEditable");
	        this.textField.onkeydown = this.onKeyDown.bindAsEventListener(this);
	        this.textField.onkeyup = this.onKeyUp.bindAsEventListener(this);
	    }
	    if (Browser.isGecko) {
	        this._input = flag === true || flag == "true";
	    }
	},
	
	onDoubleClick: function(evt){
	    this.show(evt.srcElement);
	},
	
	onClick: function(evt){
	    var ele = $(evt.srcElement.parentNode);
	    if ("true" == ele.$A("lazy")) {
	        ele.loadData(function(){
	            window.Selector.showingID = 0;
	            Selector.show(ele.textField);
	        });
	    }
	},
	
	onKeyDown: function(evt){
	    evt = getEvent(evt);
	    var txt = evt.srcElement;
	    var div = txt.parentNode;
	    
	    //未加载
	    if ("true" == div.$A("lazy")) {
	        ele.loadData(function(){
	            window.Selector.showingID = 0;
	            Selector.show(ele.textField);
	        });
	        return;
	    }
	    
	    var pw = window;//$E.getTopLevelWindow();;
	    if (div.$A("listurl")) {//自定义的下拉框调用自定义的nextItem(),previousItem()
	        var win = pw.$("_SelectorFrame" + div.id).contentWindow;
	        if (evt.keyCode == 38) {
	            if (win.nextItem) {
	                win.nextItem();
	            }
	        }
	        else 
	            if (evt.keyCode == 40) {
	                if (win.previousItem) {
	                    win.previousItem();
	                }
	            }
	        stopEvent(evt);
	        return;
	    }
	    if (!pw.$("_SelectorDiv_") || !pw.$("_SelectorDiv_").visible()) {
	        this.show();
	        div.KeyShowFlag = true;
	        stopEvent(evt);
	        return;
	    }
	    if (evt.keyCode == 38) {
	        this.moveItem(false);
	    }
	    else 
	        if (evt.keyCode == 40) {
	            this.moveItem(true);
	        }
	        else 
	            if (!evt.ctrlKey && !evt.shiftKey && !evt.altKey && evt.keyCode != 9 && !div.input) {//tab键
	                stopEvent(evt);
	            }
	},
	
	moveItem: function(flag){//移动下拉列表中的当前选中项，要考虑有一部分被隐藏的情况
		var ele = this.el;
		if(this.selectedIndex == -1) {
			this.selectedIndex = 0;
		}
		
	    var pw = window;//$E.getTopLevelWindow();;
	    var frame = pw.$("_SelectorFrame");
	    var doc = frame.contentWindow.document;
	    var list = doc.getElementById(ele.id + "_ul");
	    var arr = doc.getElementsByTagName("a");
	    var currentItem = arr[ele.selectedIndex];
	    var nextItem = null;
	    var start = 0;
	    if ($E.visible(currentItem)) {
	        start = this.selectedIndex;
	    }
	    var len = arr.length;
	    ele.ItemClickFlag = true;
	    if (flag) {
	        for (var i = start + 1; i < len; i++) {
	            var a = arr[i];
	            var d = a.style.display;
	            if (!d || d.toLowerCase() != 'none') {
	                this.selectedIndex = ele.selectedIndex = i;
	                nextItem = a;
	                break;
	            }
	        }
	    }
	    else {
	        for (var i = start - 1; i >= 0; i--) {
	            var a = arr[i];
	            var d = a.style.display;
	            if (!d || d.toLowerCase() != 'none') {
	                this.selectedIndex = ele.selectedIndex = i;
	                nextItem = a;
	                break;
	            }
	        }
	    }
	    ele.ItemClickFlag = false;
	    if (nextItem) {
	        currentItem.className = "hover";//取消选中
	        nextItem.className = "ahover";//选中
	        ele.textField.value = Browser.isGecko ? nextItem.textContent : nextItem.innerText;
	        
	        var _select = $(ele.id.replace("select_", ""));
	        if (!_select) {
	            _select = parent.$(ele.id.replace("select_", ""));
	        }
	        _select.value = nextItem.value;
	        
	        var pos1 = $E.getPosition(currentItem);
	        var pos2 = $E.getPosition(nextItem);
	        list.scrollTop = list.scrollTop + pos2.y - pos1.y;
	    }
	},
	
	onKeyUp: function(evt){
	    evt = getEvent(evt);
	    var pw = window;//$E.getTopLevelWindow();;
	    if (evt.keyCode >= 37 && evt.keyCode <= 40) {
	        return;
	    }
	    var txt = evt.srcElement;
	    var div = txt.parentElement;
	    if (div.KeyShowFlag) {
	        div.KeyShowFlag = false;
	        return;
	    }
	    
	    var v = txt.value.replace(/　/g, "").trim();
	    if (div.$A("listurl")) {//自定义的下拉框调用自定义的onKeyUp
	        var win = pw.$("_SelectorFrame" + div.id).contentWindow;
	        if (win.onTextChange) {
	            win.onTextChange(v);
	        }
	        return;
	    }
	    var frame = pw.$("_SelectorFrame");
	    var doc = frame.contentWindow.document;
	    var arr = doc.getElementsByTagName("a");
	    if (evt.keyCode == 13) {//回车
	        Selector.onItemClick(arr[div.selectedIndex]);
	    }
	    if (!evt.ctrlKey && !evt.shiftKey && !evt.altKey && evt.keyCode != 9 && div.input) {//按Tab从别的控件转移焦点时应该显示全部选项
	        Selector.filter(div, v);
	    }
	},
	
	filter: function(ele, v){
	    var pw = window;//$E.getTopLevelWindow();;
	    var frame = pw.$("_SelectorFrame");
	    var doc = frame.contentWindow.document;
	    var arr = doc.getElementsByTagName("a");
	    var len = arr.length;
	    v = v.toLowerCase();
	    for (var i = 0; i < len; i = i + 1) {
	        var a = arr[i];
	        var str = Browser.isGecko ? a.innerHTML : a.innerText;
	        str = str.replace(/　/g, "").trim().toLowerCase();
	        if (str.indexOf(v) >= 0) {
	            a.style.display = '';
	        }
	        else {
	            a.style.display = 'none';
	        }
	    }
	},
	
	
	setValue: function(ele, v){
	    if (ele.$A("listURL")) {
	        ele.textField.value = v;
	        ele._value = v;
	    }
	    else {
	        var flag = false;
	        var len = ele.Items.length;
	        for (var i = 0; i < len; i = i + 1) {
	            if ($(ele.Items[i]).$A("value") == v) {
	                ele.textField.value = ele.Items[i].innerText;
	                ele._value = v;
	                
	                ele.childNodes[0].value = t;
	                
	                var _select = $(ele.id.replace("select_", ""));
	                if (!_select) {
	                    _select = parent.$(ele.id.replace("select_", ""));
	                }
	                _select.value = v;
	                
	                if (Browser.isGecko) {
	                    ele._selectedIndex = i;
	                }
	                else {
	                    ele.ItemClickFlag = true;
	                    ele.selectedIndex = i;
	                    ele.ItemClickFlag = false;
	                }
	                flag = true;
	                break;
	            }
	        }
	        if (!flag && ele.input) {
	            ele.textField.value = v;
	            ele._value = v;
	            
	            ele.childNodes[0].value = t;
	            
	            var _select = $(ele.id.replace("select_", ""));
	            if (!_select) {
	                _select = parent.$(ele.id.replace("select_", ""));
	            }
	            _select.value = v;
	        }
	    }
	    Selector.invokeOnChange(ele);
	},
	
	setDisabled: function(flag){
		
	    if (flag || flag == "true") {
	        this.textField.disabled = true;
	        this.el.addClassName("zSelectDisabled");
	        this.arrow.onmouseover = stopEvent;
	        this.arrow.onmouseout = stopEvent;
	        this.arrow.onclick = stopEvent;
	        this.arrow.onmousedown = stopEvent;
	        this.textField.style.color = "#aaa";
	        this.textField.ondblclick = stopEvent;
	        this.textField.onkeydown = stopEvent;
	        this.textField.onkeyup = stopEvent;
	        this.textField.onclick = stopEvent;
	    }
	    else {
	        this.textField.disabled = false;
	        this.el.removeClassName("zSelectDisabled");
	        this.textField.ondblclick = this.onDoubleClick.bindAsEventListener(this);
	        this.arrow.onmouseover = this.onArrowMouseOver.bind(this);
	        this.arrow.onmouseout = this.onArrowMouseOut.bind(this);
	        this.arrow.onmousedown = (function(evt){
	            var pw = window;//$E.getTopLevelWindow();
	            //var div = this.getParent("div");
	            if (pw.Selector && pw.Selector.showingID == this.el.id) {
	                return;
	            }
	            this.show();
	            this.textField.onfocus.apply(this.textField, arguments);
	            stopEvent(evt);
	        }).bindAsEventListener(this);
	        
	        this.textField.style.color = "";
	        this.textField.onkeydown = this.onKeyDown.bindAsEventListener(this);
	        this.textField.onkeyup = this.onKeyUp.bindAsEventListener(this);
	        this.textField.onclick = this.onClick.bindAsEventListener(this);
	        this.arrow.onclick = this.onClick.bindAsEventListener(this);
	    }
	},
	
	initMethodIE: function(){
	
	    var onPropertyChange = function(event){
	        var s = event.srcElement;
	        var v = s[event.propertyName];
	        switch (event.propertyName.toLowerCase()) {
	            case "disabled":
	                this.setDisabled(v);
	                break;
	            case "selectedindex":
	                if (!s.ItemClickFlag) {
	                    if (this.items.length > 0) {
	                        this.setSelectedIndex(s.selectedIndex);
	                    }
	                }
	                break;
	            case "input":
	                this.setInput(s.input);
	                break;
	            case "size":
	                break;
	            case "value":
	                if (!s.ItemClickFlag) {
	                    this.setValue(v);
	                }
	                break;
	        };
		}
		
		this.el.onpropertychange = onPropertyChange.bindAsEventListener(this);
	},
	
	initMethodGecko: function(ele){
	    ele.__defineGetter__("disabled", function(flag){
	        return this.textField.disabled;
	    });
	    
	    ele.__defineSetter__("disabled", function(flag){
	        Selector.setDisabled(this, flag);
	    });
	    
	    ele.__defineGetter__("selectedIndex", function(){
	        return this._selectedIndex;
	    });
	    
	    ele.__defineSetter__("selectedIndex", function(index){
	        index = parseInt(index);
	        if (index >= 0 && index < this.Items.length) {
	            this._selectedIndex = index
	        }
	        else {
	            return;
	        }
	        Selector.setSelectedIndex(this, this._selectedIndex);
	    });
	    
	    ele.__defineGetter__("input", function(){
	        return this._input;
	    });
	    
	    ele.__defineSetter__("input", function(flag){
	        this._input = flag != "false";
	        Selector.setInput(this, this._input);
	    });
	    
	    ele.__defineGetter__("size", function(){
	        return this._size;
	    });
	    
	    ele.__defineSetter__("size", function(size){
	        this._Size = size;
	        
	    });
	    
	    ele.__defineGetter__("value", function(){
	        return this._value;
	    });
	    
	    ele.__defineSetter__("value", function(v){
	        Selector.setValue(this, v);
	    });
	}
},{
	initHtml: function(div) {
		return new Selector(div);
	},
	getText: function(id){
	    return $("select_" + id + "_textField").value;
	},
	setListURLStyle: function(id) {
		Selector.getSelect(id).setListURLStyle();
	},
	setValueEx: function(ele, v, t){
	    ele = $(ele);
	    ele.value = v;
	    ele.childNodes[0].value = t;//ele.textField.value = t;
	    var _select = $(ele.id.replace("select_", ""));
	    if (!_select) {
	        _select = parent.$(ele.id.replace("select_", ""));
	    }
	    _select.value = v;
	},
	
	invokeOnChange: function(_ele, value, option){
	    if (!_ele.InitFlag) {
	        var _cv = _ele.getAttribute("onChange");
	        if (_cv) {
	            var pw = window;//$E.getTopLevelWindow();;
	            var params = _cv.match(/\(.*\)/)[0].replace("(","").replace(")","").trim();
	            if(params != "") {
	            	params = ",";
	            }
	            new Function(_cv.replace("(", "('" + value + "','" + option + "'" + params)).call(_ele);
	            //(pw.SourceWindow || window);
	        }
	    }
	},
	
	/**
	 * @deprecated 使用getEl方法代替
	 */
	getSourceDiv: function(id){
	
		if(id.startsWith("select_select_")) {
			id = id.replace("select_","");
		}
		
		//要兼顾初次加载的情况
	    var div = null;
	    var pw = window;//$E.getTopLevelWindow();;
	    if (pw.SourceWindow && pw.SourceWindow.$) {
	        div = pw.SourceWindow.$(id);
	    }
	    if (!div) {
	        div = $(id);
	        if (!div) {
	            div = $(id.replace("select_",""));
	        }
	        if (!div) {
	            div = parent.$(id);
	        }
	        
	        if (!div) {//||!div.InitFlag){
	            alert("发生致命错误，显示列表时未找到" + id + "对应的Selector元素!");
	        }
	    }
	    
	    if(!div.srcClass) {
	    	new Selector(div);
	    }
	    
	    return div;
	},
	onItemMouseOver: function(ele){
	    var id = ele.divId || ele.parentElement.id.replace("_ul", "");//$E.getParentByAttr("className", "zSelect", ele).id;// 兼容旧的使用方式，新的推荐在生成item时添加divId
	    //id = id.substring(0, id.lastIndexOf("_"));
	    var div = Selector.getSourceDiv(id);
	    var selector = div.srcClass;
	    
	    ///var list = document.getElementById(ele.divId + "_list");
	   // list.style.display = 'inline';
	     var frame = window.$("_SelectorFrame");
	    var doc = frame.contentWindow.document;
	    var items = //div.$T("DIV")[0].childNodes[0].$T("a");
	    doc.getElementById(ele.divId + "_ul");
	    if(selector.multi) {
			if(Array.isInstance(selector.selectedIndex)) {
				for(var i=0;i<selector.selectedIndex.length;i++) {
					//items[selector.selectedIndex[i]].className = "ahover";
					items.getElementsByTagName("a")[selector.selectedIndex[i]].className = "ahover";		
				}
			}else {
				if(selector.selectedIndex != -1) {
	    			items.getElementsByTagName("a")[selector.selectedIndex].className = "ahover";//选中
	    		}
	    	}
	    } else {
	    	if(selector.selectedIndex != -1) {
	    		selector.items[selector.selectedIndex].className = "ahover";
	    	}	
	    }	
	    
	   /*
	    var list = doc.getElementById(id + "_list");
	    doc.style.display = 'inline';
	    list = doc.getElementById(id + "_ul");
if(selector.multi) {
	    		if(Array.isInstance(selector.selectedIndex)) {
	    			for(var i=0; i<selector.selectedIndex.length; i++) {
	    				list.getElementsByTagName("a")[selector.selectedIndex[i]].className = "ahover";//选中
	    			}
	    		} else {
	    			list.getElementsByTagName("a")[selector.selectedIndex].className = "ahover";//选中
	    		}
	    		
	    	} else {
		        list.getElementsByTagName("a")[selector.selectedIndex].className = "ahover";//选中
		    }
		    */
	    ele.className = "liOver";
	},
	onItemClick: function(ele, flag){
		if(ele.innerText == "请选择") {
			return ;
		}
		
		// 这样做也不行
		// var checked = ele.getAttribute("checked");
		// ele.setAttribute("checked", checked=="true"?"false":"true");
		 
	    var pw = window;//$E.getTopLevelWindow();;
	    var div = Selector.getSourceDiv(ele.divId || ele.parentElement.id.replace("_ul", ""));
	    var selector = div.srcClass;// 自定义控件对象
	    var oldValue = div.value;
	    
	    div.ItemClickFlag = true;

		var _v = ele.getAttribute("value");
		
		var items = selector.options;
	    for (var i = 0; i < items.length; i++) {
	        if (items[i].getAttribute("value") == ele.getAttribute("value")) {
	            items[i].checked = !items[i].checked;
	        }
	    }
	    
	    var _text = "";
	    if (Browser.isIE) {
	    
	        div.value = _v;
	    
	        if(selector.multi) {
	        	_v = "";
	        	
	        	var selectedIndexs = [];
		        for(var i=0;i<selector.items.length;i++) {
		        	
		        	//if(selector.items[i].getAttribute("checked") == "true") {
					if(selector.items[i].checked) {
		        		selectedIndexs.push(i);
		        		_v += selector.items[i].getAttribute("value") + ",";
		        		_text += selector.items[i].innerText + ",";
		        	}
		        }
		        
		        selector.selectedIndex = selectedIndexs;
		        div.selectedIndex = selectedIndexs;/* 这边会自动触发div的onPropertyChange事件 */
		        
		        if(_v.length > 0) {
					_v = _v.substring(0, _v.length-1);
				}
				if(_text.length > 0) {
					_text = _text.substring(0, _text.length-1);
				}
				
				selector.hiddenInput.value = _v;	
	        } else {
	        	selector.hiddenInput.value = _v;
	        	
	        	var items = selector.options;
			    for (var i = 0; i < items.length; i++) {
			        if (items[i].getAttribute("value") == ele.getAttribute("value")) {
			            selector._selectedIndex = i;
			            if (Browser.isIE) {
			                selector.selectedIndex = i;
			                div.selectedIndex = i;/* 这边会自动触发div的onPropertyChange事件 */
			            }
			        }
			    }
			    
			    _text = ele.innerText;
	        }
	    } else {
	        div._value = _v;
	    }
	    
	    selector.textField.value = _text;
	    
	    div.ItemClickFlag = false;
	    
	    if (pw.$("_SelectorFrame")) {
	        div._ScrollTop = ele.parentNode.scrollTop;//列表再次展开时定位用
	    }
	    if (oldValue != ele.getAttribute("value")) {
	        try {
	            Selector.invokeOnChange(div, ele.getAttribute("value"), ele.innerText);
	        } catch (ex) {debugger;
	            alert("Selector.invokeOnChange():" + ex.message);
	        };
		}
		
		if(!selector.multi) {
	    	Selector.close();
	    }
	},
	close: function(evt){
	    var pw = window;//$E.getTopLevelWindow();;
	    if (pw.Selector && pw.Selector.showingID) {
	        if (pw.SourceWindow) {
	            var ctrl = pw.SourceWindow.$(pw.Selector.showingID).srcClass.textField;
	            if (evt) {//在文本框上点击也会引发全局的onMouseDown事件，从而调用Selector.close
	                evt = getEvent(evt);
	                if (evt.srcElement == ctrl) {
	                    return;
	                }
	            }
	            //需要检查输入的值
	            var ele = ctrl.parentNode;
	            var verify = ele.$A("verify");
	            var anyFlag = false;
	            if (verify) {
	                var arr = verify.split("\&\&");
	                for (var i = 0; i < arr.length; i = i + 1) {
	                    if (arr[i] == "Any") {
	                        anyFlag = true;
	                    }
	                }
	            }
	            if (!anyFlag && ele.input) {
	                var txt = ele.textField.value.trim();
	                var arr = ele.options;
	                var len = arr.length;
	                var flag = false;
	                for (var i = 0; i < len; i = i + 1) {
	                    var innerText = Browser.isGecko ? arr[i].textContent : arr[i].innerText;//Firefox下未始化innerText属性，原因待查
	                    if (innerText.trim() == txt) {
	                        if (arr[i].getAttribute("value") != ele.value) {
	                            var frame = pw.$("_SelectorFrame");
	                            var doc = frame.contentWindow.document;
	                            var list = doc.getElementById(ele.id + "_ul");
	                            Selector.onItemClick(list.getElementsByTagName("a")[i]);
	                        }
	                        flag = true;
	                        break;
	                    }
	                }
	                if (!flag && arr.length > 0) {
	                	if(ele.selectedIndex != -1) {
	                    	ele.textField.value = Browser.isGecko ? arr[ele.selectedIndex].textContent : arr[ele.selectedIndex].innerText;
	                    }	
	                }
	            }
	            
	            try {
	                ctrl.onblur.apply(ctrl, []);
	            } 
	            catch (ex) {
	            }
	            //Misc.unlockScroll(pw.SourceWindow);
	        }
	        $E.hide(pw.$("_SelectorDiv_"));
	        //pw.SourceWindow = null;//有可能导致找不到DIV
	        pw.Selector.showingID = false;
	    }
	},
	
	removeFrameMouseDownEvent: function(win){
	    var arr = win.Page.mouseDownFunctions;
	    if (arr) {
	        for (var i = 0; i < arr.length; i = i + 1) {
	            if (arr[i] == win.Selector.close) {
	                arr.remove(arr[i]);
	            }
	        }
	    }
	},
	
	LastID: new Date().getTime(),
	
	initCtrl: function(ele){
	    ele = $(ele);
	    Selector.initHtml(ele);
	    Selector.initMethod(ele);
	},
	
	setReturn: function(t, v){
	    var id = window.SelectorID;
	    var pw = window;//$E.getTopLevelWindow();;
	    var div = Selector.getSourceDiv(id);
	    var oldValue = div.value;
	    div.textField.value = t;
	    if (Browser.isIE) {
	        div.ItemClickFlag = true;
	        div.value = v;
	        div.ItemClickFlag = false;
	    }
	    else {
	        div._value = v;
	    }
	    var _select = $(div.id.replace("select_", ""));
	    if (!_select) {
	        _select = parent.$(div.id.replace("select_", ""));
	    }
	    _select.value = v;
	    div._ScrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
	    if (oldValue != v) {
	        try {
	            Selector.invokeOnChange(div);
	        } 
	        catch (ex) {debugger;
	            alert("Selector.invokeOnChange():" + ex.message);
	        };
	            }
	    if (pw.Selector.showingID) {
	        $E.hide(pw.$("_SelectorDiv_"));
	        pw.Selector.showingID = 0;
	    }
	    if (parent.Selector.showingID) {
	        $E.hide(parent.$("_SelectorDiv_"));
	        parent.Selector.showingID = 0;
	    }
	},
	
	getSelects: function() {
		 //+ this.el.id + "_textField' xtype='select' "
		 var selectDivs = Sky.getControls("select");
		 var selects = [];
		 for(var i=0;i<selectDivs.length;i++) {
		 	selects.push(Selector.getSelect(selectDivs[i].id));
		 }
		 return selects;
	},
	
	getSelect: function(id){
		if(!id.startsWith("select_")) {
			id = "select_" + id;
		}
	    var _select = $(id).srcClass || new Selector($(id));
	    
	    _select.addItem = function(text, value){
	        var _items = $(this.id + "_ul");
	        _items.innerHTML = _items.innerHTML + "<a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' hidefocus value=\"" + value + "\">" + text + "</a>";
	        this.initList();
	        return this;
	    };
	    
	    _select.deleteItem = function(index){
	        if (Number.isInstance(index)) {
	            this.Items = this.$T("a");
	            this.Items[index].parentElement.removeChild(this.Items[index]);
	        }
	        else {
	            this.Items = this.$T("a");
	            var len = this.Items.length;
	            for (var i = 0; i < len; i++) {
	                if ($(this.Items[i]).$A("value") == index) {
	                    this.Items[i].parentElement.removeChild(this.Items[i]);
	                }
	            }
	        }
	        
	        this.initList();
	        return this;
	    }
	    
	    return _select;
	}
	
});


/*
 Selector.verifyInput = function(ele){//检查input=true时输入的值是否正确
 var ele = ele.parentNode;
 var txt = ele.textField.value.trim();
 var arr = ele.options;
 var len = arr.length;
 var flag = false;
 for(var i=0;i<len;i++){
 var innerText = Browser.isGecko?arr[i].textContent:arr[i].innerText;
 if(innerText.trim()==txt){
 flag = true;
 break;
 }
 }
 return flag;
 }
 Selector.loadData = function(ele,func){
 if(!ele.Params){
 ele.Params = new DataCollection();
 }
 if(!ele.$A("code")&&!ele.$A("method")){
 alert("未设置code或method属性!");
 return;
 }
 if(ele.Params.get("CodeType")==null){//第一次Params中没有数据，所以从HTML属性中取，以后则以Params中数据为准
 ele.Params.add("CodeType",ele.$A("code"));
 }
 if(ele.Params.get("Method")==null){
 ele.Params.add("Method",ele.$A("method"));
 }
 if(ele.Params.get("ConditionField")==null){
 ele.Params.add("ConditionField",ele.$A("conditionField"));
 }
 if(ele.Params.get("ConditionValue")==null){
 ele.Params.add("ConditionValue",ele.$A("conditionValue"));
 }
 Server.sendRequest("com.xdarkness.jaf.controls.CodeSourcePage.getData",ele.Params,function(response){
 var dt = response.get("DT");
 ele.clear();
 if(dt){
 var vs = dt.Values;
 var len = vs.length;
 var html = [];
 if(ele.$A("defaultblank")!="false"){
 html.push("<a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\" hidefocus value=\"\"></a>");
 }
 var flag = "true"==ele.$A("showvalue");
 for(var i=0;i<len;i++){
 var text=vs[i][1],value=vs[i][0];
 if(flag){
 text = value+"-"+text;
 }
 html.push("<a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\" hidefocus value=\""+value+"\">"+text+"</a>");
 }
 ele.$T("DIV")[0].childNodes[0].innerHTML = html.join('\n');
 ele.options = ele.Items = ele.$T("DIV")[0].childNodes[0].$T("a");
 if(ele.Items.length>10){
 ele.$T("DIV")[0].childNodes[0].style.height = "15em";
 }
 }
 ele.setAttribute("lazy",false);
 ele.selectedIndex = 0;
 ele.DataSource = dt;
 if(func){
 try{
 func();
 }catch(ex){}
 }
 });
 }
 */
Page.onMouseDown(Selector.close);
Page.onKeyDown(function(){/* 当前Select失去焦点，关闭下拉框 */
    if (event.srcElement.id.startWith('select')) {
        if (event.keyCode == 9) 
            //if(!event.ctrlKey&&!event.shiftKey&&!event.altKey&&event.keyCode!=9&&!div.input) {
            Selector.close();
        //}
    }
});


MultiSelector = Selector.extend({
	createItemString: function(text, value) {
		return "<div style='border:0px;'><div style='float:left;border:0px;'><input type='checkbox' /></div><div style='border:0px;'><a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' hidefocus value=\"" + value + "\">" + text + "</a></div></div>";
	}
});


DatePicker = function(config){
    config.appendTo.innerHTML += '<input id="" name="' + config.name + '" onclick="WdatePicker()" style="background-repeat:no-repeat;background-position-x:right;background-image:url(' + Server.ContextPath + 'Framework/plugins/My97DatePicker/skin/datePicker.gif)" type="text">';
};
