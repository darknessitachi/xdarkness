/**
 * @class Page 页面处理相关
 * 监听document/window对象上的click、mousedown、load、mouseup、mousemove事件 
 */
var Page = {
	onClick : function(fun) {
		Sky.EventManager.addEvent(document, "click", fun);
	},
	onLoad : function(fun, index) {
		Sky.EventManager.addEvent(window, "load", fun, index);
	},
	onMouseDown : function(fun) {
		Sky.EventManager.addEvent(document, "mousedown", fun);
	},
	onMouseUp : function(fun) {
		Sky.EventManager.addEvent(document, "mouseup", fun);
	},
	onMouseMove : function(fun) {
		Sky.EventManager.addEvent(document, "mousemove", fun);
	},
	onKeyDown : function(fun) {
		Sky.EventManager.addEvent(document, "keydown", fun);
	}
};
