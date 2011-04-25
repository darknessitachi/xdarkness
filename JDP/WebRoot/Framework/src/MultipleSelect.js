/**
 * 	多行SELECT 开始 // TODO 代码需整理
 * 
 * @class MultipleSelect 多行SELECT
 */
/**
 * @method constructor 构造器
 * @param {Object} leftPart 左边的id
 * @param {Object} rightPart 右边的id
 */
MultipleSelect = function(leftPart, rightPart) {
	this.leftPart = $(leftPart);
	this.rightPart = $(rightPart);
}

MultipleSelect.prototype = {
	createOption: function (ele, data, config) {
		ele = $(ele);
		var _instance = this;
		
    	var divOpt = document.createElement('div');
    		divOpt.innerText = data[config.text];
    		divOpt.text = data[config.text];
    		divOpt.value = data[config.value];
    		divOpt.onmouseover = function() {
    			this.style.cursor = 'hand';
    		};
    		divOpt.onclick = function() {
    			
    			this.selected = this.selected?false:true;
    			
    			if(this.selected) {
    				this.style.background = '#7BC9F6';
    			} else {
    				this.style.background = '';
    			}
    		};
    		divOpt.ondblclick = function() {
    		
    			/* 是右边的part，双击直接删除 */
    			if(this.parentElement==_instance.rightPart) {
    				this.parentElement.removeChild(this);
    				return;
    			}
    			var rightPart = _instance.rightPart;//$('rightPart');
    			var childs = rightPart.$T('div');
    			var exist = false;
    			for(var i=0;i<childs.length;i++) {
    				var child = childs[i];
    				if(child.value == this.value) {
    					exist = true;
    					break;
    				}
    			}
    			if(!exist) {
    				var divOpt = document.createElement('div');
		    		divOpt.innerText = this.text;
		    		divOpt.text = this.text;
		    		divOpt.value = this.value
		    		divOpt.onmouseover = function() {
		    			this.style.cursor = 'hand';
		    		};
		    		divOpt.ondblclick = function() {
		    			rightPart.removeChild(this);
		    		};
		    		divOpt.onclick = function() {
    			
		    			this.selected = this.selected?false:true;
		    			
		    			if(this.selected) {
		    				this.style.background = '#7BC9F6';
		    			} else {
		    				this.style.background = '';
		    			}
		    		};
		    		
		    		rightPart.appendChild(divOpt);
    			}
    		};
    		
    		ele.appendChild(divOpt);
    },deleteSelected: function () {
		
		this.deleteOptions(this.rightPart, this.getSelectedOptions(this.rightPart));
	},deleteAll: function () {

		this.deleteOptions(this.rightPart, this.rightPart.$T('div'));
	}, deleteOptions: function (ele, options) {
		
		for(var i=0;i<options.length;i++) {
			var option = options[i];
			ele.removeChild(option);
		}
	}, addSelected: function () {
		
		this.addOptions(this.rightPart, this.getSelectedOptions(this.leftPart));
	}, addAll: function () {
	
		this.addOptions(this.rightPart, this.leftPart.$T('div'));
	}, each: function (elements, fn) {
		var ret = [];
		for(var i=0;i<elements.length;i++) {
			var element = elements[i];
			if(fn(element)) {
				ret.push(element);
			}
		}
		return ret;
	}, getSelectedOptions: function (ele) {
		
		return this.each($(ele).$T('div'), function(option){
			return option.selected;
		});
	}, addOptions: function (ele, options) {
		ele = $(ele);
		var childs = ele.$T('div');
		for(var i=0;i<options.length;i++) {
			var option = options[i];
			var exist = false;
			for(var j=0;j<childs.length;j++) {
				child = childs[j];
				if(child.value == option.value) {
					exist = true;
					break;
				}
			}
			if(!exist) {
				var divOpt = document.createElement('div');
	    		divOpt.innerText = option.innerText;divOpt.text = option.text;
	    		divOpt.value = option.value;	
	    		divOpt.onmouseover = function() {
	    			this.style.cursor = 'hand';
	    		};
	    		divOpt.ondblclick = function() {
	    			ele.removeChild(this);
	    		};
	    		divOpt.onclick = function() {
   			
	    			this.selected = this.selected?false:true;
	    			
	    			if(this.selected) {
	    				this.style.background = '#7BC9F6';
	    			} else {
	    				this.style.background = '';
	    			}
	    		};
	    		
	    		ele.appendChild(divOpt);
			}
		}
	}
	
}


/* 多行SELECT 结束 */	