

TreeNode = Base.extend({
	idCounter:0, 
	idPrefix:"sky-tree-", 
	generatorId:function () {
		return this.idPrefix + this.idCounter++;
	},
	constructor: function (attributes) {
		if(String.isInstance(attributes)){
			attributes = {
				text: attributes
			};
		}
		this.attributes = attributes || {};
		this["html-element"] = false;//null
		if (!attributes.id) {
			attributes.id = getUniqueID();
		}
		this.id = attributes.id;
		this.text = attributes.text || TreeConfig.defaultText;
		this.renderTreeNode = attributes.renderTreeNode || this.renderTreeNode;
		this.parentNode = null;
		this.childNodes = [];
		this.firstChild = null;
		this.lastChild = null;
		this.previousSibling = null;
		this.nextSibling = null;
		this.childrenRendered = false;
		this.action = attributes.action || TreeConfig.defaultAction;
		this.target = attributes.target;
		this.isExpand = false;
		this.checked = this.attributes.checked || false;
		this.leaf = this.attributes.leaf;
		var children = this.attributes.children || [];
		for (var i = 0, j = children.length; i < j; i++) {
			if(this.renderTreeNode){
				children[i].renderTreeNode = this.renderTreeNode;
			}
			var node = new TreeNode(children[i]);
			
			this.appendChild(node);
		}
	},initEl:function () {
		this["html-element"] = {};
		this["html-element"]["element"] = document.createElement("div");
		this["html-element"]["line"] = document.createElement("span");
		this["html-element"]["clip"] = document.createElement("img");
		this["html-element"]["icon"] = document.createElement("img");
		if(this.action){
			this["html-element"]["text"] = document.createElement("a");
			this["html-element"]["text"].href = this.action;
			this["html-element"]["text"].target = this.target || this.getOwnerTree().root.target || TreeConfig.target;
		} else {
			this["html-element"]["text"] = document.createElement("span");
		}
		this["html-element"]["checkbox"] = document.createElement("img");
		this["html-element"]["child"] = document.createElement("div");
		this["html-element"]["element"].appendChild(this["html-element"]["line"]);
		this["html-element"]["element"].appendChild(this["html-element"]["clip"]);
		this["html-element"]["element"].appendChild(this["html-element"]["icon"]);
		this["html-element"]["element"].appendChild(this["html-element"]["checkbox"]);
		this["html-element"]["element"].appendChild(this["html-element"]["text"]);
		this["html-element"]["element"].appendChild(this["html-element"]["child"]);
		this["html-element"]["text"].className = "TreeNode";
		this["html-element"]["element"].noWrap = "true";
		this["html-element"]["line"]["_type_"] = "line";
		this["html-element"]["clip"]["_type_"] = "clip";
		this["html-element"]["icon"]["_type_"] = "icon";
		this["html-element"]["text"]["_type_"] = "text";
		this["html-element"]["checkbox"]["_type_"] = "checked";
		this["html-element"]["child"].style.display = "none";
		if (this.checked === false) {
			this["html-element"]["checkbox"].style.display = "none";
		}
	}, render:function () {
		if (!this["html-element"]) {
			this.initEl();
		}
		if (this.isRoot()) {
			this.ownerTree.element.appendChild(this["html-element"]["element"]);
			this.expand();
		} else {
			this.parentNode["html-element"]["child"].appendChild(this["html-element"]["element"]);
			if(this.ownerTree.root.expandAll===true)
				this.expand();
		}
		this.paintPrefix();
		this["html-element"]["element"].indexId = this.id;
	}, paintPrefix:function () {
		this.paintLine();
		this.paintClipIcoImg();
		this.paintCheckboxImg();
		this.paintIconImg();
		this.paintText();
	}, paintLine:function () {
		var ownerTree = this.getOwnerTree();
		this["html-element"]["line"].innerHTML = "";
		var pathNodes = this.getPathNodes();
		for (var i = 1, count = pathNodes.length - 1; i < count; i++) {
			var node = pathNodes[i];
			var img = document.createElement("img");
			if (node.parentNode == null || node.nextSibling == null) {
				img.src = ownerTree.getIcon("empty");
			} else {
				img.src = ownerTree.getIcon("line");
			}
			this["html-element"]["line"].appendChild(img);
		}
	}, paintClipIcoImg:function () {
		if (this.isRoot()) {
			this["html-element"]["clip"].style.display = "none";//不显示根节点的clip
			return;
		}
		var ownerTree = this.getOwnerTree();
		var icon = "empty";
		if (this.isRoot()) {/* 这边的判断完全不需要 */ 
			icon = this.isExpand ? "nlMinus" : "nlPlus";
		} else {
			if (this.isLeaf()) { /*是叶节点*/
				if (this.isLast()) {
					icon = "joinBottom";
				} else {
					icon = "join";
				}
			} else { /*非叶节点*/
				if (this.isExpand) { /*展开*/
					if (this.isLast()) {
						icon = "minusBottom";
					} else {
						icon = "minus";
					}
				} else { /*折叠*/
					if (this.isLast()) {
						icon = "plusBottom";
					} else {
						icon = "plus";
					}
				}
			}
		}
		this["html-element"]["clip"].src = ownerTree.getIcon(icon);
	}, paintIconImg:function () {
		var ownerTree = this.getOwnerTree();
		var icon = this["attributes"]["icon"];
		if (!icon) {
			var type = this["attributes"]["type"];
			if (type) {
				icon = ownerTree.getIconByType(type);
			}
			if (!icon) {
				if (this.isRoot()) {
					icon = "root";
				} else {
					if (this.isLeaf()) {
						icon = "node";
					} else {
						if (this.isExpand) {
							icon = "folderOpen";
						} else {
							icon = "folder";
						}
					}
				}
			}
		}
		this["html-element"]["icon"].src = ownerTree.getIcon(icon);
	}, paintCheckboxImg:function () {
		var ownerTree = this.getOwnerTree();
		var checked = this.checked;
		if (this["html-element"]) {
			this["html-element"]["checkbox"].src = ownerTree.getIcon(((checked == 2) ? "checkbox2" : (checked == 1) ? "checkbox1" : "checkbox0"));
		}
	}, paintText:function () {
		//var text = this["attributes"]["text"];
		var text = this.text;
		this["html-element"]["text"].style.cursor = "hand";
		this["html-element"]["text"].title = text;
		if(this.isLeaf() && this.renderTreeNode){
			var obj = this;
			var renderHTML = this.renderTreeNode(text);
			this["html-element"]["text"].outerHTML = renderHTML;
			this["html-element"]["text"].renderHTML = renderHTML;
		}else{
			this["html-element"]["text"].innerText = text;
		}	
		this["html-element"]["text"].textContent = text;
	}, paintChildren:function () {
		if (!this.childrenRendered) {
			this["html-element"]["child"].innerHTML = "";
			this.childrenRendered = true;
			var childNodes = this.childNodes;
			for (var i = 0; i < childNodes.length; i++) {
				childNodes[i].render();
			}
		}
	}, collapse:function () {
		this.isExpand = false;
		this["html-element"]["child"].style.display = "none";
		this.paintIconImg();
		this.paintClipIcoImg();
	}, expand:function () {
		if (!this.isLeaf() && this.childNodes.length > 0) {
			this.isExpand = true;
			this.paintChildren();
			this["html-element"]["child"].style.display = "block";
		}
		this.paintIconImg();
		this.paintClipIcoImg();
	},
	 select:function () {
		this.isSelect = true;
		this["html-element"]["text"].style.backgroundColor = "#CCCCFF";
	}, unselect:function () {
		this.isSelect = false;
		this["html-element"]["text"].style.backgroundColor = "";
	}, getEl:function () {
		return this["html-element"];
	}, setCheck:function (checked) {
		if (checked == 2 || checked == 3) {
			var childNodes = this.childNodes;
			var count = childNodes.length;
			if (count == 0) {
				this.checked = checked == 2 ? 0 : 1;
			} else {
				var checked1 = 0;
				var checked2 = 0;
				for (var i = 0; i < count; i++) {
					var checked = childNodes[i].checked;
					if (checked == 1) {
						checked1++;
					} else {
						if (checked == 2) {
							checked2++;
						}
					}
				}
				this.checked = (childNodes.length == checked1) ? 1 : (checked1 > 0 || checked2 > 0) ? 2 : 0;
			}
		} else {
			this.checked = checked;
		}
		this.paintCheckboxImg();
	}, onCheck:function () {
		if (this.checked !== false) {
			if (this.checked == 1) {
				this.cascade((function (checked) {
					this.setCheck(checked);
				}), null, 0);
				this.bubble((function (checked) {
					this.setCheck(checked);
				}), null, 2);
			} else {
				this.cascade((function (checked) {
					this.setCheck(checked);
				}), null, 1);
				this.bubble((function (checked) {
					this.setCheck(checked);
				}), null, 3);
			}
		}
	}, isRoot:function () {
		return (this.ownerTree != null) && (this.ownerTree.root === this);
	}, isLeaf:function () {
		return this.childNodes.length === 0;
			//return this.leaf === true;
	}, isLast:function () {
		return (!this.parentNode ? true : this.parentNode.lastChild == this);
	}, isFirst:function () {
		return (!this.parentNode ? true : this.parentNode.firstChild == this);
	}, hasChildNodes:function () {
		return !this.isLeaf() && this.childNodes.length > 0;
	}, setFirstChild:function (node) {// private
		this.firstChild = node;
	}, setLastChild:function (node) {//private
		this.lastChild = node;
	}, appendChild:function (node) {
		var multi = false;
		if (Array.isInstance(node)) {
			multi = node;
		} else {
			if (arguments.length > 1) {
				multi = arguments;
			}
		}
		if (multi) {
			for (var i = 0, len = multi.length; i < len; i++) {
				this.appendChild(multi[i]);
			}
		} else {
				//>>beforeappend
			var oldParent = node.parentNode;
		      //>>beforemove
			if (oldParent) {
				oldParent.removeChild(node);
			}
			var index = this.childNodes.length;
			if (index == 0) {
				this.setFirstChild(node);
			}
			this.childNodes.push(node);
			/*不知道这两句那句效率更高*/ 
			//this.childNodes[this.childNodes.length] = node;
			node.parentNode = this;
				//
			var ps = this.childNodes[index - 1];
			if (ps) {
				node.previousSibling = ps;
				ps.nextSibling = node;
			} else {
				node.previousSibling = null;
			}
			node.nextSibling = null;
			this.setLastChild(node);
			node.setOwnerTree(this.getOwnerTree());
				//>>append
				//if(oldParent) >>move
			if (node && this.childrenRendered) {
				node.render();
				if (node.previousSibling) {
					node.previousSibling.paintPrefix();//paintLine();
				}
			}
			if (this["html-element"]) {
				this.paintPrefix();
			}
			return node;//true
		}
	}, remove: function() {
		this.parentNode.removeChild(this);
	}, removeChild:function (node) {
		var index = this.childNodes.indexOf(node);
		if (index == -1) {
			return false;
		}
			//>>beforeremove
		this.childNodes.splice(index, 1);
		if (node.previousSibling) {
			node.previousSibling.nextSibling = node.nextSibling;
		}
		if (node.nextSibling) {
			node.nextSibling.previousSibling = node.previousSibling;
		}
		if (this.firstChild == node) {
			this.setFirstChild(node.nextSibling);
		}
		if (this.lastChild == node) {
			this.setLastChild(node.previousSibling);
		}
		node.setOwnerTree(null);
			//clear
		node.parentNode = null;
		node.previousSibling = null;
		node.nextSibling = null;
			//>>remove UI
		if (this.childrenRendered) {
			if (node["html-element"] && node["html-element"]["element"]) {
				this["html-element"]["child"].removeChild(node["html-element"]["element"]);
			}
			if (this.childNodes.length == 0) {
				this.collapse();
			}
		}
		if (this["html-element"]) {
			this.paintPrefix();
		}
		return node;
	}, insertBefore:function (node, refNode) {
		if (!refNode) {
			return this.appendChild(node);
		}
			/*移动位置是自身位置(不需要移动)*/
		if (node == refNode) {
			return false;
		}
		var index = this.childNodes.indexOf(refNode);
		var oldParent = node.parentNode;
		var refIndex = index;
			/*是子节点，并且是向后移动*/
		if (oldParent == this && this.childNodes.indexOf(node) < index) {
			refIndex--;
		}
		if (oldParent) {
			oldParent.removeChild(node);
		}
			/* 设置节点间关系 */
		if (refIndex == 0) {
			this.setFirstChild(node);
		}
		this.childNodes.splice(refIndex, 0, node);
		node.parentNode = this;
		var ps = this.childNodes[refIndex - 1];
		if (ps) {
			node.previousSibling = ps;
			ps.nextSibling = node;
		} else {
			node.previousSibling = null;
		}
		node.nextSibling = refNode;
		refNode.previousSibling = node;
		node.setOwnerTree(this.getOwnerTree());
		return node;
	}, replaceChild:function (newChild, oldChild) {
		this.insertBefore(newChild, oldChild);
		this.removeChild(oldChild);
		return oldChild;
	}, indexOf:function (child) {
		return this.childNodes.indexOf(child);
	}, getOwnerTree:function () {
		if (!this.ownerTree) {
			var p = this;
			while (p) {
				if (p.ownerTree) {
					this.ownerTree = p.ownerTree;
					break;
				}
				p = p.parentNode;
			}
		}
		return this.ownerTree;
	}, getDepth:function () {//获得节点深度
		var depth = 0;
		var p = this;
		while (p.parentNode) {
			depth++;
			p = p.parentNode;
		}
		return depth;
	}, setOwnerTree:function (tree) {//private
		if (tree != this.ownerTree) {
			if (this.ownerTree) {
				this.ownerTree.unregisterNode(this);
			}
			this.ownerTree = tree;
			var cs = this.childNodes;
			for (var i = 0, len = cs.length; i < len; i++) {
				cs[i].setOwnerTree(tree);
			}
			if (tree) {
				tree.registerNode(this);
			}
		}
	}, getPathNodes:function () {
		var nodes = [];
		for (var parent = this; parent != null; parent = parent.parentNode) {
			nodes.push(parent);
		}
		return nodes.reverse();
	}, getPath:function (attr) {
		attr = attr || "id";
		var p = this.parentNode;
		var b = [this["attributes"][attr]];
		while (p) {
			b.unshift(p.attributes[attr]);
			p = p.parentNode;
		}
		var sep = this.getOwnerTree().pathSeparator;
		return sep + b.join(sep);
	}, bubble:function (fn, scope, args) {//冒泡(遍历所有父节点)
		var p = this;
		while (p) {
			if (fn.call(scope || p, args || p) === false) {
				break;
			}
			p = p.parentNode;
		}
	}, cascade:function (fn, scope, args) {//瀑布(遍历所有子节点)
		if (fn.call(scope || this, args || this) !== false) {
			var cs = this.childNodes;
			for (var i = 0, len = cs.length; i < len; i++) {
				cs[i].cascade(fn, scope, args);
			}
		}
	}, findChild:function (attribute, value) {//查找
		var cs = this.childNodes;
		for (var i = 0, len = cs.length; i < len; i++) {
			if (cs[i].attributes[attribute] == value) {
				return cs[i];
			}
		}
		return null;
	}, findChildBy:function (fn, scope) {
		var cs = this.childNodes;
		for (var i = 0, len = cs.length; i < len; i++) {
			if (fn.call(scope || cs[i], cs[i]) === true) {
				return cs[i];
			}
		}
		return null;
	}, sort:function (fn, scope) {
		var cs = this.childNodes;
		var len = cs.length;
		if (len > 0) {
			var sortFn = scope ? function () {
				fn.apply(scope, arguments);
			} : fn;
			cs.sort(sortFn);
			for (var i = 0; i < len; i++) {
				var n = cs[i];
				n.previousSibling = cs[i - 1];
				n.nextSibling = cs[i + 1];
				if (i == 0) {
					this.setFirstChild(n);
				}
				if (i == len - 1) {
					this.setLastChild(n);
				}
			}
		}
	}, contains:function (node) {
		var p = node.parentNode;
		while (p) {
			if (p == this) {
				return true;
			}
			p = p.parentNode;
		}
		return false;
	}, toString:function () {
		return "[Node" + (this.id ? " " + this.id : "") + "]";
	}
});
