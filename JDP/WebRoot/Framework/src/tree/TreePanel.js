
TreePanel = Base.extend({
	constructor: function (config) {
		this.nodeHash = {};
		this.root = null;
		this._id = getUniqueID();
		this.iconPath = config.iconPath || TreeConfig.imagePath;
		this.clickListeners = [];
		this.element = document.createElement("div");
		this.element.className = "TreePanel";
		//this.container = null;
		this.focusNode = null;
		this.on = this.addListener;
		this.container = $(config["renderTo"]) || document.body;
		var handler = config["handler"];
		if (Function.isInstance(handler)) {
			this.addListener("click", handler);
		}
		var iconPath = config["iconPath"];
		if (String.isInstance(iconPath)) {
			this.iconPath = iconPath;
		}
		
		var node = null;
		if(config.renderTreeNode){
			config.root.renderTreeNode = config.renderTreeNode;
		}
		if(config.target){
			config.root.target = config.target;
		}
		
		if (config.reader == "xmlReader") {
			node = new AjaxTreeNode({
				text: config.root,
				url: config.url,
				action: config.action
			});
		} else {
			node = new TreeNode(config.root);
		}
		if(config.expandAll===true){
			node.expandAll = true;
		}
		this.setRootNode(node);
		
	}, pathSeparator:"/", getRootNode:function () {
		return this.root;
	}, setRootNode:function (node) {
		this.root = node;
		node.ownerTree = this;
			//this.root.setOwnerTree(this);
		this.registerNode(node);
		node.cascade((function (node) {
			this.registerNode(node);
		}), this);
	}, getNodeById:function (id) {
		return this.nodeHash[id];
	}, registerNode:function (node) {
		var len = this.nodeHash.length || 0;
		this.nodeHash[node.id] = node;
		this.nodeHash.length = len+1;
	}, unregisterNode:function (node) {
		delete this.nodeHash[node.id];
	}, render:function () {
		this.element.innerHTML = "";
		this.root.render();
		if (this.container) {
			this.container.appendChild(this.element);
		}
		this.initEvent();
	}, getIcon:function (icontype) {
		return this.iconPath + this.icon[icontype];
	}, getIconByType:function (type) {
		return type;
	}, initEvent:function () {
		var _this = this;
		this.element.onclick = function (event) {
			var event = event || window.event;
			var elem = (event.srcElement || event.target);
			var _type = elem["_type_"];
			if (typeof (_type) === undefined) {
				return;
			}
			elem = elem.parentNode || elem.parentElement;
			if (_type == "clip") {
				if (elem.indexId != null) {
					var node = _this.nodeHash[elem.indexId];
					if (node.isExpand) {
						node.collapse();
					} else {
						node.expand();
					}
					//toggle: function () {
					//	if (this.folder) {
					//		if (this.open) {
					//			this.collapse();
					//		} else {
					//			this.expand();
					//		}
					//	}
				}
			} else {
				if (_type == "icon" || _type == "text") {
					var node = _this.nodeHash[elem.indexId];
					for (var i = 0; i < _this.clickListeners.length; i++) {
						_this.clickListeners[i](node);
					}
					_this.setFocusNode(node);
				} else {
					if (_type == "checked") {
						var node = _this.nodeHash[elem.indexId];
						node.onCheck();
					}
				}
			}
		};
	}, getChecked:function (name) {
		var checkeds = [];
		name = name || "id";
		for (var k in this.nodeHash) {
			var node = this.nodeHash[k];
			if (node.checked == 1) {
				var value = node.attributes[name];
				if (value != null) {
					checkeds.push(value);
				}
			}
		}
		return checkeds;
	}, addListener:function (type, handler) {
		if (Function.isInstance(type)) {
			handler = type;
			type === "click";
		}
		this.clickListeners.push(handler);
	}, setFocusNode:function (node) {
		if (this.focusNode) {
			this.focusNode.unselect();
		}
		this.focusNode = node;
		if (node.parentNode) {
			node.parentNode.expand();
		}
		node.select();
	}, toString:function () {
		return "[Tree" + (this.id ? " " + this.id : "") + "]";
	}
});

TreePanel.prototype.icon = {
	root:"root.gif", 
	folder:"folder.gif", 
	folderOpen:"folderopen.gif", 
	node:"page.gif", 
	empty:"empty.gif", 
	line:"line.gif", 
	join:"join.gif", 
	joinBottom:"joinbottom.gif", 
	plus:"plus.gif", 
	plusBottom:"plusbottom.gif", 
	minus:"minus.gif", 
	minusBottom:"minusbottom.gif", 
	nlPlus:"nolines_plus.gif", 
	nlMinus:"nolines_minus.gif", 
	checkbox0:"checkbox_0.gif", 
	checkbox1:"checkbox_1.gif", 
	checkbox2:"checkbox_2.gif", 
	org:"org.gif", 
	edp:"edp.gif", 
	emp:"emp.gif"
};

