
Console = {
	msgs: "",
	append: function(msg){
		Console.msgs += msg + "<br/>";
		Console.show();
	},
	write: function(msg){
		Console.msgs += msg;
		Console.show();
	},
	writeln: function(msg){
		Console.msgs += msg + "<br/>";
		Console.show();
	},
	show: function() {/*
		var console = $TW.$("Console_Window");
		if(!console) {
			var btnClear = $TW.document.createElement("input");
			btnClear.type = "button";
			btnClear.value = "Clear";
			btnClear.onclick = Console.clear;
			$TW.document.body.appendChild(btnClear);
			
			console = $TW.document.createElement("div");
			console.id = "Console_Window";
			console.style.height = "100px";
			console.style.overflow = "auto";
			$TW.document.body.appendChild(console);
		}
		console.innerHTML = Console.msgs;*/
	},
	clear: function() {
		var console = $TW.$("Console_Window");
		if(console) {
			console.innerHTML = "";
			Console.msgs = "";
		}
	}
};

/**
 * @class TextSuggest 自动提示
 */
TextSuggest = Base.extend({

	DataCache: {},
	
    /**
     * @method constructor 构造器，根据配置初始化自动提示，并对需要自动提示的文本框注入键盘监听事件
     * @param {String} anId 自动提示对应的文本框id
     * @param {String} url 服务器端url
     * @param {Object} options 配置参数对象
     */
    constructor: function(anId, url, options){
        /**
         * @property {String} id 自动提示对应的文本框id
         */
        this.id = anId;
        
        /**
         * 
         * @property {Element} id 自动提示对应的文本框
         */
        this.textInput = $(anId);
        var instance = this;
        this.textInput.textSuggest = instance;
        
        this.el = this.textInput;
        
        this.el.oldValue = this.el.value;
        
        this.el.valueChanged = function() {
        	var changed = this.oldValue != this.value;
        	this.oldValue = this.value;
        	return changed;
        };
        
        /**
         * @property {Array} suggestions {text: strText, value: strValue}格式数据
         */
        this.suggestions = [];
        
        /**
         * @property {String} url 服务器端url
         */
        this.url = url;
        
        this.fixTop = (options || {}).fixTop || 0;
        this.fixLeft = (options || {}).fixLeft ||0;
        
        this.setOptions(options);
        
        this.injectSuggestBehavior();
    },
    
    /**
     * @method setOptions 初始化配置参数
     * @param {Object} options 参数
     */
    setOptions: function(options){
        /**
         * @property options 配置参数
         */
        this.options = Sky.apply({
            suggestDivClassName: 'suggestDiv',
            suggestionClassName: 'suggestion',
            matchClassName: 'spanMatchText',
            matchTextWidth: true,
            selectionColor: '#4499ee',
            matchAnywhere: false,
            ignoreCase: false,
            count: 10,
            hiddenInput: false
        }, options || {});
    },
    
    /**
     * @method injectSuggestBehavior 注入Suggest行为：
     * 		为自动提示文本框添加对应的隐藏字段（其id命名为文本框id+"Value"），创建自动提示DIV，并对文本框添加键盘监听
     */
    injectSuggestBehavior: function(){
    
        Console.append("injectSuggest");
        if (Browser.isIE) 
            this.textInput.autocomplete = "off";
        
        /* 添加键盘监听 */
        new TextSuggestKeyHandler(this);
        
        /* 添加隐藏字段 */
        if(this.options.hiddenInput === true) {
	        var hiddenInput = document.createElement("input");
	        hiddenInput.name = this.id + 'Value';
	        hiddenInput.id = this.id + 'Value';
	        hiddenInput.type = "hidden";
	        this.textInput.parentNode.appendChild(hiddenInput);
		}
		
		/**
		 * @property {Element} inputHidden 隐藏字段
		 */
		this.hiddenInput = $(this.id + 'Value');// hiddenInput;
        
        this.createSuggestionsDiv();
        Console.append("end injectSuggest");
    },
	
    /**
     * @method sendRequestForSuggestions 请求数据：如果正在请求，加入等待队列
     */
    sendRequestForSuggestions: function(){
    
        Console.append("request");
        
        /**
         * @property {Boolean} handlingRequest 正在处理请求?
         */
        if (this.handlingRequest) {
            /**
             * @property {Boolean} pendingRequest 有等待的请求？
             */
            this.pendingRequest = true;
            return;
        }
        
        this.handlingRequest = true;
        
        this.callAjaxEngine();
        
        Console.append("end request");
    },
	
    /**
     * @method callAjaxEngine 发送请求
     */
    callAjaxEngine: function(){
    
        Console.append("callAjaxEngine");
        
        var callParms = [];
        callParms.push(this.id + '_request');
        callParms.push('id=' + this.id);
        callParms.push('count=' + this.options.count);
        callParms.push('query=' + this.lastRequestString);
        callParms.push('match_anywhere=' + this.options.matchAnywhere);
        callParms.push('ignore_case=' + this.options.ignoreCase);
        
        var additionalParms = this.options.requestParameters || [];
        for (var i = 0; i < additionalParms.length; i++) 
            callParms.push(additionalParms[i]);
        
        var instance = this;
       /* new Sky.Ajax({
            url: this.url,
            params: callParms.join("&"),
            onload: instance.ajaxUpdate.bind(instance),
            method: "POST"
        });*/
        DWRActionUtil.executeaction(this.url,{query:this.lastRequestString},instance.ajaxUpdate.bind(instance));
        Console.append("end callAjaxEngine");
    },
	
	/**
	 * @method ajaxUpdate 处理请求响应：没有匹配项，隐藏下拉列表，清空隐藏值。
	 * 		否则，更新并显示下拉列表，设置第一个下拉项为选中状态。如果有等待的请求，发送请求（注：只处理最后输入框中的请求）
	 * @param {String} responseText 请求响应的文本
	 */
    ajaxUpdate: function(responseText){
    	this.update(eval(responseText.responseText));
    },
    
    update: function(data) {
        this.createSuggestions(data);
        
        if (this.suggestions.length == 0) {
            this.suggestionsDiv.hide();
            if(this.hiddenInput) {
            	this.hiddenInput.value = "";
            }
        }
        else {
            this.updateSuggestionsDiv();
            this.showSuggestions();
            this.updateSelection(0);
        }
        
        this.handlingRequest = false;
        
        if (this.pendingRequest) {
            this.pendingRequest = false;
            this.lastRequestString = this.textInput.value;
            this.sendRequestForSuggestions();
        }
    },
	
	/**
	 * @method createSuggestions 解析响应文本，并设置匹配项，匹配项为{text: strText, value: strValue}格式数据
	 * @param {String} responseText 响应文本
	 */
    createSuggestions: function(data){
        this.suggestions = [];
        
        //if(!arrOptions) return;
        
        for (var i = 0; i < data.length; i++) {
        	if(this.suggestFilter && !this.suggestFilter(data[i])) {
        		continue;
        	}
        	
            var strText = data[i][0];
            var strValue = data[i][1];
            this.suggestions.push({
                text: strText,
                value: strValue
            });
            Console.writeln("create suggestions:[" + strText + "," + strValue+"]");
        }
    },
	
	/**
	 * @method getElementContent // TODO 这个方法需移除
	 * @param {Object} element
	 */
    getElementContent: function(element){
        return element.firstChild.data;
    },
	
	/**
	 * @method updateSuggestionsDiv 更新下拉列表
	 */
    updateSuggestionsDiv: function(){
        this.suggestionsDiv.innerHTML = "";
        var suggestLines = this.createSuggestionSpans();
        for (var i = 0; i < suggestLines.length; i++) {
            this.suggestionsDiv.appendChild(suggestLines[i]);
            Console.append("adding div " + suggestLines[i]);
        }
    },
    
	/**
	 * @method createSuggestionSpans 创建下拉列表选项
	 */
    createSuggestionSpans: function(){
        var regExpFlags = this.options.ignoreCase ? "i" : "";
        var startRegExp = this.options.matchAnywhere ? "" : "^";
        
        var regExp = new RegExp(startRegExp + this.lastRequestString, regExpFlags);
        
        var suggestionSpans = [];
        for (var i = 0; i < this.suggestions.length; i++) {
            suggestionSpans.push(this.createSuggestionSpan(i, regExp))
            Console.append("suggestion: " + this.suggestions[i].text);
        }
        return suggestionSpans;
    },
    
    addBeforeSpan: function() {
    	return null;
    },
    
	/**
	 * @method createSuggestionSpan 创建单个下拉选项
	 * @param {Number} index 下拉选项索引
	 * @param {RegExp} regExp
	 */
    createSuggestionSpan: function(n, regExp){
        var suggestion = this.suggestions[n];
        
        var suggestionSpan = document.createElement("span");
        suggestionSpan.className = this.options.suggestionClassName;
        suggestionSpan.style.width = '100%';
        suggestionSpan.style.display = 'block';
        suggestionSpan.id = this.id + "_" + n;
        suggestionSpan.onmouseover = this.mouseoverHandler.bindAsEventListener(this);
        addEvent(suggestionSpan, "click", this.itemClickHandler.bindAsEventListener(this));
        
        var textValues = this.splitTextValues(suggestion.text, this.lastRequestString.length, regExp);
        
        var textMatchSpan = document.createElement("span");
        textMatchSpan.id = this.id + "_match_" + n;
        textMatchSpan.className = this.options.matchClassName;
        textMatchSpan.onmouseover = this.mouseoverHandler.bindAsEventListener(this);
        addEvent(textMatchSpan, "click", function(){alert('a');});//this.itemClickHandler.bindAsEventListener(this);
        
        textMatchSpan.appendChild(document.createTextNode(textValues.mid));
        
        var beforeSpan = this.addBeforeSpan();
        if(beforeSpan){
        	suggestionSpan.appendChild(document.createTextNode(beforeSpan));
        }
        suggestionSpan.appendChild(document.createTextNode(textValues.start));
        suggestionSpan.appendChild(textMatchSpan);
        suggestionSpan.appendChild(document.createTextNode(textValues.end));
        
        return suggestionSpan;
    },
	
	/**
	 * @method updateSelection 更新第n个选项为选中，同时改变背景色
	 * @param {Object} n
	 */
    updateSelection: function(n){
        this.selectedIndex = n;
        for (var i = 0; i < this.suggestions.length; i++) {
            var span = $(this.id + "_" + i);
            if (i != this.selectedIndex) 
                span.style.backgroundColor = "";
            else 
                span.style.backgroundColor = this.options.selectionColor;
        }
    },
    
    /**
     * @method handleTextInput 文本框输入改变处理器
     */
    handleTextInput: function(){
        var previousRequest = this.lastRequestString;
        
        this.setLastRequestString();
        
        Console.append("text input: " + previousRequest + " -> " + this.lastRequestString);
        if (this.lastRequestString == "") {
            this.suggestionsDiv.hide();
        }
        else if (this.lastRequestString != previousRequest) {
            this.sendRequestForSuggestions();
        }
    },
    
    setLastRequestString: function() {
    	/**
         * @property {String} lastRequestString 最后输入的字符串
         */
        this.lastRequestString = this.textInput.value;
        
        //if(this.el.oldValue == this.el.value) {
        //	this.el._valueChanged = false;
        //} else {
        //	this.el._valueChanged = true;
        //	this.el.oldValue = this.el.value;
        //}
    },
    
	/**
	 * @method moveSelectionUp 设置当前选项的上一个选项为选中状态
	 */
    moveSelectionUp: function(){
        if (this.selectedIndex == 0) {
        	this.selectedIndex = this.suggestions.length;
        }
        this.updateSelection(this.selectedIndex - 1);
    },
    
	/**
	 * @method moveSelectionDown 设置当前选项的下一个选项为选中状态
	 */
    moveSelectionDown: function(){
        if (this.selectedIndex == (this.suggestions.length - 1)) {
        	this.selectedIndex = -1;
        }
        this.updateSelection(this.selectedIndex + 1);
    },
    
    /**
     * @method createSuggestionsDiv 创建自动提示div
     */
    createSuggestionsDiv: function(){
        /**
         * @property {Element} suggestionsDiv 自动提示div元素
         */
        this.suggestionsDiv = $(document.createElement("div"));
        this.suggestionsDiv.className = this.options.suggestDivClassName;
        
        var divStyle = this.suggestionsDiv.style;
        divStyle.position = 'absolute';
        divStyle.zIndex = 101;
        this.suggestionsDiv.hide();
        
        this.textInput.parentNode.appendChild(this.suggestionsDiv);
    },
	
	/**
	 * @method setInputFromSelection 设置表单文本及隐藏域的值
	 */
    setInputFromSelection: function(){
    	//if(!this.selectedIndex) return;
    	
    	this.selectedIndex = this.selectedIndex || 0;
        var suggestion = this.suggestions[this.selectedIndex];
        
        /* this.textInput.oldValue = this.textInput.value */
        this.textInput.value = $(this.id + "_" + this.selectedIndex).innerText;//suggestion.text;
        
        /*if(this.el.oldValue == this.el.value) {
        	this.el._valueChanged = false;
        } else {
        	this.el._valueChanged = true;
        	this.el.oldValue = this.el.value;
        }*/
        
        if(this.hiddenInput) {
        	this.hiddenInput.value = suggestion.value;
        }
        this.suggestionsDiv.hide();
    },
    
	/**
	 * @method showSuggestions 显示下拉列表
	 */
    showSuggestions: function(){
        var divStyle = this.suggestionsDiv.style;
        if (divStyle.display == '') 
            return;
        this.positionSuggestionsDiv();
        divStyle.display = '';
    },
    
	/**
	 * @method positionSuggestionsDiv 设置下拉列表的位置、大小
	 */
    positionSuggestionsDiv: function(){
    /*
        var textPos = this.textInput.getPositionEx();
        var divStyle = this.suggestionsDiv.style;
        divStyle.top = (textPos.y + this.textInput.offsetHeight + this.fixTop) + "px";
        divStyle.left = textPos.x + this.fixLeft + "px";
        Console.append("position suggest div: " + divStyle.left + "," + divStyle.top);
      */
      	this.suggestionsDiv.positionTo(this.textInput);
      	var divStyle = this.suggestionsDiv.style;
        if (this.options.matchTextWidth) 
            divStyle.width = (this.textInput.offsetWidth) + "px";
    },
    
    fixPosition: function(top, left) {
    	this.fixTop = top;
    	this.fixLeft = left;
    },
    
	/**
	 * @method mouseoverHandler 鼠标移过事件处理器
	 * @param {Event} e
	 */
    mouseoverHandler: function(e){
        var src = e.srcElement ? e.srcElement : e.target;
        var index = parseInt(src.id.substring(src.id.lastIndexOf('_') + 1));
        this.updateSelection(index);
    },
    
	/**
	 * @method itemClickHandler 下拉选项点击事件处理器
	 * @param {Object} e
	 */
    itemClickHandler: function(e){
        this.mouseoverHandler(e);
        this.suggestionsDiv.hide();
        this.textInput.focus();
    },
    
	/**
	 * @method splitTextValues 将为本拆开为开始、中间、结尾三部分，其中中间部分为匹配文本 
	 * @param {String} text 需要匹配的文本
	 * @param {Number} len 匹配文本的长度
	 * @param {RegExp} regExp 匹配文本正则
	 * @return {Object} {start: startText, mid: matchText, end: endText}格式的对象
	 */
    splitTextValues: function(text, len, regExp){
        var startPos = text.search(regExp);
        var matchText = text.substring(startPos, startPos + len);
        var startText = startPos == 0 ? "" : text.substring(0, startPos);
        var endText = text.substring(startPos + len);
        return {
            start: startText,
            mid: matchText,
            end: endText
        };
    }
});

/**
 * @class TextSuggestKeyHandler 自动提示文本监听事件
 */
TextSuggestKeyHandler = Base.extend({
    /**
     * @method constructor 构造器
     * @param {TextSuggest} textSuggest 自动提示组件
     */
    constructor: function(textSuggest){
        /**
         * @property {TextSuggest} textSuggest 自动提示组件
         */
        this.textSuggest = textSuggest;
        
        /**
         * @property {Element} input 自动提示文本框
         */
        this.input = this.textSuggest.textInput;
        
        this.addKeyHandling();
    },
    /**
     * @method addKeyHandling 添加事件处理
     */
    addKeyHandling: function(){
        this.input.on("keyup", this.keyupHandler.bindAsEventListener(this))
        		  .on("keydown", this.keydownHandler.bindAsEventListener(this))
        		  .on("blur", this.onblurHandler.bindAsEventListener(this));
        if (Sky.isOpera) 
            this.input.on("keypress", this.keyupHandler.bindAsEventListener(this));
    },
    /**
     * @method keydownHandler 键盘按下处理器
     * @param {Event} e 事件，为自动注入
     */
    keydownHandler: function(e){
        var upArrow = 38;
        var downArrow = 40;
        
        if (e.keyCode == upArrow) {
            this.textSuggest.moveSelectionUp();
            setTimeout(this.moveCaretToEnd.bind(this), 1);
        }
        else 
            if (e.keyCode == downArrow) 
                this.textSuggest.moveSelectionDown();
    },
    /**
     * @method keyupHandler 键盘弹起处理器
     * @param {Event} e 事件，为自动注入
     */
    keyupHandler: function(e){
        if (this.input.length == 0 && !Sky.isOpera) 
            this.textSuggest.hideSuggestions();
        
        if (!this.handledSpecialKeys(e)) 
            this.textSuggest.handleTextInput();
    },
    /**
     * @method handledSpecialKeys 处理特殊按键
     * @param {Event} e 事件，为自动注入
     */
    handledSpecialKeys: function(e){
        var enterKey = 13;
        var upArrow = 38;
        var downArrow = 40;
        
        if (e.keyCode == upArrow || e.keyCode == downArrow) {
            return true;
        }
        else 
            if (e.keyCode == enterKey) {
                this.textSuggest.setInputFromSelection();
                return true;
            }
        
        return false;
    },
    
    moveCaretToEnd: function(){
        var pos = this.input.value.length;
        if (this.input.setSelectionRange) {
            this.input.setSelectionRange(pos, pos);
        }
        else 
            if (this.input.createTextRange) {
                var m = this.input.createTextRange();
                m.moveStart('character', pos);
                m.collapse();
                m.select();
            }
    },
    
    onblurHandler: function(e){
        if (this.textSuggest.suggestionsDiv.style.display == '') 
            this.textSuggest.setInputFromSelection();
        this.textSuggest.suggestionsDiv.hide();
    }
});


Array.prototype.containsRoute = function(route) {
	for(var i=0;i<this.length;i++) {
		if(this[i].text == route.text && this[i].value == route.value){
			return true;
		}
	}
	return false;
}

/**
1、如果一次返回结果为空，接下来都不查询
2、斜杠不提交，{0}/{1}是不包含{0}
*/
RouteTextSuggest = TextSuggest.extend({
	allRoutes: [],
	constructor: function(anId, options){
		this.maxRoute = 1;
		if(options && options.maxRoute) {
			this.maxRoute = options.maxRoute;
		}
		
		this.base(anId, Server.ContextPath+"chuanhang/RouteEntry-getRoutes", options);
		// this.textField.on("focus", this.keydownHandler.bindAsEventListener(this));
	},
	
	lastRoute: "",/* 上一次请求的航段 */
	lastInputRoute: "",/* 输入框中的航段 */
	excluteCity: "",
	setLastRouteString: function() {
		// 南京-上海/北京-
		var value = this.textInput.value;
		
		var spRoutes = value.split("/");
		if(spRoutes.length >= 2) {
			var spBeforeCitys = spRoutes[spRoutes.length-2].split("-");
			this.excluteCity =  spBeforeCitys[spBeforeCitys.length-1];
			value = spRoutes[spRoutes.length-1];
		}
		
        var routes = value.split("-");
        if(routes.length >= 2) {
	        this.lastRoute = routes[routes.length-2] + "-";
	        this.lastInputRoute = routes[routes.length-2] + "-" + routes[routes.length-1];
        } else {
        	this.lastRoute = value;//this.textInput.value;
        	this.lastInputRoute = value;//this.textInput.value;
        }
    },
    ajaxUpdate: function(responseText){
    	var data = eval(responseText.responseText);
    	this.DataCache[this.lastRoute] = data;
    	this.update(data);
    },
    callAjaxEngine: function() {
    
    	/**
    	 * /Don’t send a request
    	 */
    	if(this.textInput.value.endsWith("/")) {
    		this.handlingRequest = false;
    		return;
    	}
    	
    	/**
    	 * make sure the input route count less than maxRoute, if more, delete them
    	 */
    	var _citys = this.textInput.value.split("-");
    	if(_citys.length-1 > this.maxRoute) {
    		var _value = "";
    		for(var i=0;i<this.maxRoute;i++) {
    			_value += _citys[i] + "-";
    		}
    		this.textInput.value = _value + _citys[this.maxRoute];
    		this.handlingRequest = false;
    		return;
    	}
    	
    	var _lastRoute = this.lastRoute;
    	
    	Console.writeln("last route:" + _lastRoute);
    	Console.writeln("this.textInput.value:" + this.textInput.value);
    	
    	this.setLastRouteString();
    	
    	Console.writeln("now input route:" + this.lastRoute);
    	
   		var cached = false;
        for(var name in this.DataCache) {
        
        	Console.writeln("name:" + name + "，　lastRoute" +　this.lastRoute);
        
        	if(name == this.lastRoute
        		|| (this.lastRoute.indexOf(name) == 0)/* 正在输入的是上次提交的一个子集 */ 
        	) {
        		this.update(this.DataCache[name]);
        		cached = true;
        		this.handlingRequest = false;
        		break;
        	}
        }
        
   		if(!cached) {
   			this.base();
   		}
    },
    
    addBeforeSpan: function(){
    	return this.textInput.value.substring(0,this.textInput.value.lastIndexOf(this.lastRoute));
    },
    
    suggestFilter: function(suggest){
    	if(this.excluteCity != "" && suggest[0].startsWith(this.excluteCity)) {
    		return false;
    	}
    	
    	return suggest[0].indexOf(this.lastInputRoute) == 0;
    },
    
    getRoutes: function(){
    	var routeIds = [];
    	
    	var routes = this.textInput.value.split("-");
    	for(var i=0;i<routes.length-1;i++){
    		var route = routes[i] + "-" + routes[i+1];
    		for(var j=0;j<this.allRoutes.length;j++){
    			if(this.allRoutes[j].text==route){
    				routeIds.push(this.allRoutes[j].value);
    			}
    		}
    	}
    	
    	return routeIds;
    },
    
    /* 获取航班下的所有航段 */
    getFlights: function(route, fnCallback) {
    
    	DWRActionUtil.executeaction(Server.ContextPath + "chuanhang/Flight-getRouteFlightNo",{
			"query": route
		}, fnCallback);
    },
    createSuggestions: function(responseText){
    	this.base(responseText);
    	for (var i = 0; i < this.suggestions.length; i++) {
            if(!this.allRoutes.containsRoute(this.suggestions[i])){
            	this.allRoutes.push(this.suggestions[i]);
            }
        }
    }
});

