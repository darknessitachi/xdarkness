/**
 * @class Browser 判断浏览器类型和版本，可以通过Browser.isIE判断是否是ie浏览器，Browser.version判断浏览器的版本...
 * @author Darkness 
 * @version 1.0
 * @since JDF 1.0
 */
window["Browser"] = {};

(function() {
	if (Browser.platform) {
		return;
	}
	var ua = window.navigator.userAgent.toLowerCase();

	Browser.platform = window.navigator.platform;

	/**
	 * @property {Boolean} isFirefox 是否是Forefox浏览器
	 */
	Browser.isFirefox = ua.indexOf("Firefox") > 0;

	/**
	 * @property {Boolean} isOpera 是否是Opera浏览器
	 */
	Browser.isOpera = typeof (window.opera) == "object";

	/**
	 * @property {Boolean} isIE 是否是isIE浏览器
	 */
	Browser.isIE = ua.indexOf("msie") != -1;

	/**
	 * @property {Boolean} isIE8 是否是IE8浏览器
	 */
	Browser.isIE8 = !!window.XDomainRequest && !!document.documentMode;

	/**
	 * @property {Boolean} isIE7 是否是IE7浏览器
	 */
	Browser.isIE7 = ua.indexOf("msie 7.0") != -1 && !Browser.isIE8;

	/**
	 * @property {Boolean} isIE6 是否是IE6浏览器
	 */
	Browser.isIE6 = ua.indexOf("msie 6.0") != -1;

	/**
	 * @property {Boolean} isMozilla 是否是Mozilla浏览器
	 */
	Browser.isMozilla = window.navigator.product == "Gecko";

	/**
	 * @property {Boolean} isNetscape 是否是Netscape浏览器
	 */
	Browser.isNetscape = window.navigator.vendor == "Netscape";

	/**
	 * @property {Boolean} isSafari 是否是Safari浏览器
	 */
	Browser.isSafari = ua.indexOf("Safari") > -1;

	/**
	 * @property {Boolean} isGecko 是否是Gecko浏览器
	 */
	Browser.isGecko = ua.indexOf("gecko") != -1;

	/**
	 * @property {Boolean} isQuirks 是否是Quirks浏览器
	 */
	Browser.isQuirks = document.compatMode == "BackCompat";

	/**
	 * @property {Boolean} isStrict 是否是Strict
	 */
	Browser.isStrict = document.compatMode == "CSS1Compat";

	/**
	 * @property {Boolean} isBorderBox 是否是BorderBox模型
	 */
	Browser.isBorderBox = Browser.isIE && Browser.isQuirks;

	if (Browser.isFirefox) {
		var re = /Firefox(\s|\/)(\d+(\.\d+)?)/;
	} else {
		if (Browser.isIE) {
			var re = /MSIE( )(\d+(\.\d+)?)/;
		} else {
			if (Browser.isOpera) {
				var re = /Opera(\s|\/)(\d+(\.\d+)?)/;
			} else {
				if (Browser.isNetscape) {
					var re = /Netscape(\s|\/)(\d+(\.\d+)?)/;
				} else {
					if (Browser.isSafari) {
						var re = /Version(\/)(\d+(\.\d+)?)/;
					} else {
						if (Browser.isMozilla) {
							var re = /rv(\:)(\d+(\.\d+)?)/;
						}
					}
				}
			}
		}
	}
	if ("undefined" != typeof (re) && re.test(ua)) {
		/**
		 * @property {Float} version 浏览器的版本
		 */
		Browser.version = parseFloat(RegExp.$2);
	}
})();