/**
 * @class String 这些函数在每一个String对象中可用.
 * @method isInstance 判断是否为字符串
 * @param {Object} obj 需检查的对象
 * @return {Boolean} 是否为字符串
 * @static
 */
String.isInstance = function(_string) {
	return (typeof (_string) === "string");
}

/**
 * @method isEmpty 判断字符串是否为空或长度为0
 * @param {String} str 需检查的字符串
 * @return {Boolean} 字符串是否为空
 * @static
 */
String.isEmpty = function(str) {
	return (typeof (str) === "undefined" || str === null || (str.length === 0));
}
/**
 * @method startsWith 判断字符串是否以某些字符开始
 * @param {String} str 起始的子字符串
 * @return {Boolean} 是否以该字符串开始
 */
String.prototype.startsWith = function(str) {
	return this.indexOf(str) == 0;
}

/**
 * @method endWith 判断字符串是否以某些字符结尾
 * @param {String} str 结尾的子字符串
 * @return {Boolean} 是否以该字符串结尾
 */
String.prototype.endsWith = function(str) {
	var i = this.lastIndexOf(str);
	return i >= 0 && this.lastIndexOf(str) == this.length - str.length;
}

/**
 * @method firstToUpperCase 字符串首字母大写
 * @return {String} 首字母大写的字符串 
 */
String.prototype.firstToUpperCase = function() {
	return this.replace(/^([a-z])/, function(s, lowerStr) {
		return lowerStr.toUpperCase();
	});
}

/**
 * @method trim 去除字符串两边的空格
 * @return {String} 去除空格后的字符串
 */
String.prototype.trim = function() {
	return this.replace(/^(\u00A0*\s*\u00A0*\s*)\u00A0*|\s*$/g, "");
}

/**
 * @method cnLength 获取字符串的长度，一个中文字符长度占两位
 * @return {Number} 字符串的长度
 */
String.prototype.cnLength = function() {
	return ((this.replace(/[\u4e00-\u9fa5]/g, "**")).length);
}
/**
 * @method leftPad 在字符串的左边追加字符串
 * @param {String} str 追加字符
 * @param {Number} count 追加后的字符串长度
 * @return {String} 追加后的字符串
 * @private
 */
String.prototype.leftPad = function(c, count) {
	if (!isNaN(count)) {
		var a = "";
		for ( var i = this.length; i < count; i++) {
			a = a.concat(c);
		}
		a = a.concat(this);
		return a;
	}
	return null;
}

/**
 * @method rightPad 在字符串的右边追加字符串
 * @param {String} str 追加字符
 * @param {Number} count 追加后的字符串长度
 * @return {String} 追加后的字符串
 * @private
 */
String.prototype.rightPad = function(c, count) {
	if (!isNaN(count)) {
		var a = this;
		for ( var i = this.length; i < count; i++) {
			a = a.concat(c);
		}
		return a;
	}
	return null;
}
/**
 * @method getAttribute 获取字符串中的属性值
 * @param {String} name 属性名称
 * @return {String} 属性值
 * @private
 */
String.prototype.getAttribute = function(name) {
	var reg = new RegExp("(^|;|\\s)" + name + "\\s*:\\s*([^;]*)(\\s|;|$)", "i");
	if (reg.test(this)) {
		return RegExp.$2.replace(/[\x0f]/g, ";");
	}
	return null;
}

/**
 * @method toJSON 将字符串转换成JSON对象
 * @param {String} srcStr json格式的字符串
 * @return {Object} JSON对象
 */
String.prototype.toJSON = function(){   
        return eval('(' + this + ')');   
}

/**
 * @method isSpecialCharacter 是否是特殊字符
 * @return {Boolean} 是否是特殊字符
 */
String.prototype.isSpecialCharacter = function () {
    var rexStr = /\<|\>|\"|\'|\&|\~|\!|\@|\#|\$|\%|\^|\*/g;
    var pattern = new RegExp(rexStr);
    return pattern.test(this);
}

/**
 * @class StringFormat 格式化字符串，如:new StringFormat("Is you, {name}, {message}!", {name: "darkness", message: "Hello"}).toString()
 * @method StringFormat 构造器
 * @param {String} str 原始字符串
 * @param {Object} params 参数
 */
var StringFormat = function(str, params) {
	this.formatString = str;
	this.params = params;
}

/**
 * @method toString 转换成字符串
 * @return {String} 字符串 
 */
StringFormat.prototype.toString = function() {
	var instance = this;
	return this.formatString.replace(/\{([\w-]+)\}/g , function(m, name, format, args){
		return instance.params[name];
	});
}