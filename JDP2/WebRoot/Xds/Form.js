/**
 * 
 * @param {Object} dr
 * @param {Object} ele
 */
var Form = {

	setValue : function(dr, ele) {/* 根据字典dr设置表单的值 */
		ele = $F(ele);
		for ( var i = 0; i < ele.elements.length; i++) {
			var c = $(ele.elements[i]);
			if (c.$A("xtype") == "select") {
				c = c.parentElement;
			}
			if (c.type == "checkbox" || c.type == "radio") {
				if (c.name) {
					$NS(c.name, dr.get(c.name));
					continue;
				}
			}
			var id = c.id.toLowerCase();
			if (dr.get(id)) {
				$S(c, dr.get(id));
			}
		}
	},

	getData : function(ele) {/* 获取表单元素值的集合 */
		ele = $F(ele);
		if (!ele) {
			alert("查找表单元素失败!" + ele);
			return;
		}
		var dc = new DataCollection();
		var arr = ele.elements;
		for ( var i = 0; i < arr.length; i++) {
			var c = $(arr[i]);

			if (!c.type) {
				continue;
			}
			if (c.type == "checkbox" || c.type == "radio") {
				if (c.name) {
					dc.add(c.name, $NV(c.name));
					continue;
				}
			}
			if (!c.id && !c.name) {
				continue;
			}
			if (c.$A("xtype") == "select") {
				c = c.parentElement;
			}
			dc.add(c.id || c.name, $V(c));
		}
		return dc;
	}
};
