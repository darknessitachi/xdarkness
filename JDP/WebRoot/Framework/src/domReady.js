
isReady = false;
var timer;
var bindReady = function(evt) {
	if (isReady)
		return;
	isReady = true;
	Page.ready.call(window);
	if (document.removeEventListener) {
		document.removeEventListener("DOMContentLoaded", bindReady, false);
	} else {
		if (window == window.top) {
			clearInterval(timer);
			timer = null;
		} else {
			document.detachEvent("onreadystatechange", bindReady);
		}
	}
};
if (document.addEventListener) {
	document.addEventListener("DOMContentLoaded", bindReady, false);
} else {
	if (window == window.top) {
		timer = setInterval(function() {
			try {
				isReady || document.documentElement.doScroll('left');//在IE下用能否执行doScroll判断dom是否加载完毕
			} catch (e) {
				return;
			}
			bindReady();
		}, 5);
	} else {
		document.attachEvent("onreadystatechange", function() {
			if ((/loaded|complete/).test(document.readyState))
				bindReady();
		});
	}
}


function domReady( f ) {
    // If the DOM is already loaded, execute the function right away
    if ( domReady.done ) return f();

    // If we’ve already added a function
    if ( domReady.timer ) {
        // Add it to the list of functions to execute
        domReady.ready.push( f  );
    } else {
        // Attach an event for when the page finishes  loading,
        // just in case it finishes first. Uses addEvent.
        Sky.EventManager.addEvent( window, "load", isDOMReady );

        // Initialize the array of functions to execute
        domReady.ready = [ f ];

        //  Check to see if the DOM is ready as quickly as possible
        domReady.timer = setInterval( isDOMReady, 13 );
    }
}

// Checks to see if the DOM is ready for navigation
function isDOMReady() {
	// 防止13毫秒内正在检测isDOMReady，下面代码还在执行中
	// 代码 domReady.done = true;还未执行
	// 此时domReady.done的真实值还是为false
	// if ( domReady.done===true ) return false;判断失败
	// 代码会继续执行，由此引发一次domReady中的函数会被调用多次的bug
	if ( domReady.onChecking===true ) return false;
	
	// Now, isDOMReady is on checking
	// and for sure no second isDOMReady excute in 13 ms
	domReady.onChecking = true;

    // If we already figured out that the page is ready, ignore
    if ( domReady.done===true ) return false;

    // Check to see if a number of functions and elements are
    // able to be accessed
    
    if ( document && document.getElementsByTagName && 
          document.getElementById && document.body 
        // && (!Browser.isIE || (document.documentElement && document.documentElement.doScroll && document.documentElement.doScroll('left')))  
         ) {/*在IE下用能否执行doScroll判断dom是否加载完毕 && document.documentElement.doScroll('left')*/
		
        // If they’re ready, we can stop checking
        clearInterval( domReady.timer );
        domReady.timer = null;

        // Execute all the functions that were waiting
        for ( var i = 0; i < domReady.ready.length; i++ ) {
            domReady.ready[i]();
        }
		
        // Remember that we’re now done
        domReady.ready = null;
        domReady.done = true;
    }
    domReady.onChecking = false;
}
