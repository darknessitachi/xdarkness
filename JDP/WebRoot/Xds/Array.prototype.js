
/**
 * @class Array 这些函数在每一个Array对象中可用.
 * 
 * @xmethod push
 * @xmethod pop
 * @xmethod unshift
 * @xmethod shift
 * @xmethod join
 * @xmethod reverse
 * @xmethod sort
 * @xmethod concat
 * @xmethod slice
 * @xmethod splice
 */

/**
 * @method append 添加元素到数组的末尾，对重复的元素进行可选的检查
 * @param {Object} obj 需添加的对象
 * @param {Boolean} nodup 是否包含重复对象
 */
Array.prototype.append = function(obj, nodup) {
	if (!(nodup && this.contains(obj))) {
		this[this.length] = obj;
	}
}

/**
 * @method clone 数组拷贝
 * @return {Array} 拷贝后的新数组
 */
Array.prototype.clone = function() {
	var len = this.length;
	var r = [];
	for ( var i = 0; i < len; i++) {
		if (typeof (this[i]) == "undefined" || this[i] == null) {
			r[i] = this[i];
			continue;
		}
		if (this[i].constructor == Array) {
			r[i] = this[i].clone();
		} else {
			r[i] = this[i];
		}
	}
	return r;
}
/**
 * @method indexOf 返回元素在数组中的索引
 * @param {Object} o 查找的元素
 * @return {Number} 元素在数组中的索引
 */
Array.prototype.indexOf = function(o) {
	for ( var i = 0, len = this.length; i < len; i = i + 1) {
		if (this[i] != null && typeof (this[i].equals) == "function"
				&& this[i].equals(o)) {
			return i;
		}
		if (this[i] == o) {
			return i;
		}
	}
	return -1;
};

/**
 * @method contains 检测数组中是否包含该元素
 * @param {Object} obj 是否包含的元素
 * @return {Boolean} 是否包含该元素
 */
Array.prototype.contains = function(obj) {
	return (this.indexOf(obj) >= 0);
}

/**
 * @method clear 清空数组
 */
Array.prototype.clear = function() {
	this.length = 0;
}

/**
 * @method insert 在指定位置插入新元素
 * @param {Number} index 索引
 * @param {Object} data 元素
 * @return {Array} 数组
 */
Array.prototype.insert = function(index, data) {
	if (isNaN(index) || index < 0 || index > this.length) {
		this.push(data);
	} else {
		var temp = this.slice(index);
		this[index] = data;
		for ( var i = 0; i < temp.length; i++) {
			this[index + 1 + i] = temp[i];
		}
	}
	return this;
}

/**
 * @method insertAt 在数组的指定位置插入一个元素
 * @param {Number} index 插入元素的位置
 * @param {Object} obj 元素
 */
Array.prototype.insertAt = function(index, obj) {
	this.splice(index, 0, obj);
}

/**
 * @method removeAt 移除指定位置处的元素
 * @param {Number} index 移除元素的位置
 */
Array.prototype.removeAt = function(index) {
	this.splice(index, 1);
}

/**
 * @method remove 移除指定元素
 * @param {Object} s 需移除的元素 
 * @param {Boolean} dust 是否返回移除的元素
 * @return {Array} 如果dust为true，返回移除的元素数组。否则，返回移除元素后的数组
 */
Array.prototype.remove = function(s, dust) {//if dust is ture, return the element that has been deleted
	if (dust) {
		var dustArr = [];
		for ( var i = 0; i < this.length; i++) {
			if (s == this[i]) {
				dustArr.push(this.splice(i, 1)[0]);
			}
		}
		return dustArr;
	}

	for ( var i = 0; i < this.length; i++) {
		if (s == this[i]) {
			this.splice(i, 1);
		}
	}
	return this;
}

/**
 * @method each 对数组中的每个元素进行指定操作
 * @param {Function} func 操作函数
 */
Array.prototype.each = function(func) {
	var len = this.length;
	for ( var i = 0; i < len; i++) {
		try {
			func(this[i], i);
		} catch (ex) {
			alert(func);
			alert("Array.prototype.each:" + ex.message);
			throw ex;
		}
	}
}

/**
 * @method isInstance 判断元素是否是数组
 * @param {Object} obj 元素
 * @return {Boolean} 是否是数组
 */
Array.isInstance = function(obj) {
	return Object.prototype.toString.call(obj) === "[object Array]";
};

/**
 * @method equals 检测两个数组是否相同
 * @param {Array} _array 目标数组
 * @return {Boolean} 是否相同
 */
Array.prototype.equals = function(_array) {
	if (this == _array) {
		return true;
	}
	if (!Array.isInstance(_array)) {
		return false;
	}
	if (this.length != _array.length) {
		return false;
	}
	for ( var i = 0, len = this.length; i < len; i = i + 1) {
		var o1 = this[i];
		var o2 = _array[i];
		if (o1 != o2) {
			if (!(typeof (o1.equals) == "function" && o1.equals(o2))) {
				return false;
			}
		}
	}
	return true;
};

/**
 * @method containsAll 检测数组中是否包含目标数组中的所有元素
 * @param {Array} oArray 目标数组
 * @return {Boolean} 是否完全包含该数组
 */
Array.prototype.containsAll = function(oArray) {
	if (this == oArray) {
		return true;
	}
	for ( var i = 0; i < oArray.length; i = i + 1) {
		var o = oArray[i];
		if (!this.contains(o)) {
			return false;
		}
	}
	return true;
};

/**
 * @method add 将数组中的所有元素加入自己
 * @param {Array} arr 需要添加的数组
 * @return {Array} 添加后的数组
 */
Array.prototype.add = function(arr) {
	var instance = this;
	arr.each(function(obj) {
		instance.append(obj);
	})
	return instance;
}

/**
 * @method filter 过滤器<br/>var a = [-1,2,1,-3,4,-2];<br/>var b = a.filter(function(v) {<br/>return v > 0 ? v : 0;<br/>});<br/>
 * @param {Function} filterRule 过滤规则函数
 * @return {Array} 过滤后的数组
 */
Array.prototype.filter = function(filterRule) {
	var ret = [];
	var arr = this;
	for ( var i in arr) {
		ret.push(filterRule(arr[i]));
	}
	return ret;
};
