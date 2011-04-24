

Date.isInstance = function(obj){
    return (Object.prototype.toString.call(obj) === "[object Date]");
};
Function.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Function]";
};
Number.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Number]";
};
Boolean.isInstance = function(obj){
    return Object.prototype.toString.call(obj) === "[object Boolean]";
};


Function.prototype.bind = function(object) {
  var __method = this;
  __method.binded = true;
  
  var args = [];
  for(var i=0; i<arguments.length; i++){
	  args.push(arguments[i]);
  }
  
  return function() {
		if(arguments && arguments.length>0) {
			args = arguments;
		}
		__method.apply(object, args);
  }
}

Function.prototype.bindAsEventListener = function(object){
    var __method = this;
    
    var args = [];
	for(var i=0; i<arguments.length; i++){
	  args.push(arguments[i]);
	}
    return function(event){
    	if(arguments && arguments.length>0) {
			args = arguments;
		}
    	args.insertAt(0,event || window.event);
        __method.apply(object, args);
    }
};
