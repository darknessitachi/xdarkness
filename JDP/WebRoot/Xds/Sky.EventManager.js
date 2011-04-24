
/**
 * 
 * Attachment of event handlers in a cross-browser way
 * Enabling event capturing
 * Providing access to a global Event object
 * Providing access to the element on which the event fired
 * Providing access to the element on which the event was handled
 * Preventing the Internet Explorer memory leak
 */
Sky.EventManager = {
	handlerGuid: 1,// a counter used to create unique IDs
	elementGuid: 1,// Increment our unique id to keep it unique
	_elements: [],
	addEvent: function (element, type, handler, context) {
		
		// Check if the handler already has a unique identifier or not
		// assign each event handler a unique ID
		if (!handler.$$guid) handler.$$guid = this.handlerGuid++;
		
		if (!element.$$guid){
			element.$$guid = this.elementGuid++;
			// Add element to private elements array
			this._elements[element.$$guid] = element;
		}
	  
		// create a hash table of event types for the element
		if (!element.events) element.events = {};
		// create a hash table of event handlers for each element/event pair
		var handlers = element.events[type];
		if (!handlers) {
			handlers = element.events[type] = {};
			// store the existing event handler (if there is one)
			if (element["on" + type]) {
				handlers[0] = {
					"handler": element["on" + type]
				};
			}
		}
		// store the event handler in the hash table
		// Add the handler to the list, track handler _and_ context
		handlers[handler.$$guid] = {
		    "handler": handler, 
		    "context": context
		};
		
		// assign a global event handler to do all the work
		element["on" + type] = Sky.EventManager.handleEvent;
	},
	
	removeEvent: function (element, type, handler) {
		// delete the event handler from the hash table
		if (element.events && element.events[type]) {
			delete element.events[type][handler.$$guid];
		}
	},
	
	handleEvent: function (event) {
		var returnValue = true;
		// grab the event object (IE uses a global event object)
		event = event || Sky.EventManager.fixEvent(window.event);
		// get a reference to the hash table of event handlers
		var handlers = this.events[event.type];
		// execute each event handler
		for (var i in handlers) {
			var handler = handlers[i];
			var result = "";
			if (typeof handler.context == "object")
		      // Call handler in context of JavaScript object
		      result = handler.handler.call(handler.context, event, this);
		    else
		      // Call handler in context of element on which event was fired
		      result = handler.handler.call(this, event, this);
			
			if (result === false) {
				returnValue = false;
			}
		}
		return returnValue;
	},
	
	fixEvent: function (event) {
		// add W3C standard event methods
		event.preventDefault = Sky.EventManager.preventDefault;
		event.stopPropagation = Sky.EventManager.stopPropagation;
		return event;
	},
	
	preventDefault: function() {
		this.returnValue = false;
	},
	
	stopPropagation: function() {
		this.cancelBubble = true;
	},
	
	attachAfter: function(context, type, newContext, newType){
	  var fFunc = context[type] || function() {};
	  context[type] = function() {
	    fFunc.apply(context || this, arguments);
	    newContext[newType].apply(newContext, arguments);
	  }
	},
	
	detachAll: function(){
	}
};

// entAjax.attachAfter(window, "onunload", entAjax.EventManager, "detachAll");

