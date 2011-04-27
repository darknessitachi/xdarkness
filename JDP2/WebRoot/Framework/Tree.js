
TreeConfig = {
	imagePath: "/images/webfxtree/xp/",
	rootIcon: "folder.png",
	openRootIcon: "openfolder.png",
	folderIcon: "folder.png",
	openFolderIcon: "openfolder.png",
	fileIcon: "file.png",
	iIcon: "I.png",
	lIcon: "L.png",
	lMinusIcon: "Lminus.png",
	lPlusIcon: "Lplus.png",
	tIcon: "T.png",
	tMinusIcon: "Tminus.png",
	tPlusIcon: "Tplus.png",
	blankIcon: "blank.png",
	defaultText: "Tree Item",
	defaultAction:"javascript:void(0);",
	defaultBehavior:"classic",
	usePersistence:true,
	target:"",
	loadingText: "Loading...",
	loadErrorTextTemplate: "Error loading \"%1%\"",
	emptyErrorTextTemplate: "Error \"%1%\" does not contain any tree items"
}


TreeHandler = {

	all:{},
	behavior:null,
	selected:null,
	onSelect:null,
	 toggle:function (oItem) {
		this.all[oItem.id.replace("-plus", "")].toggle();
	}, select:function (oItem) {
		this.all[oItem.id.replace("-icon", "")].select();
	}, focus:function (oItem) {
		this.all[oItem.id.replace("-anchor", "")].focus();
	}, blur:function (oItem) {
		this.all[oItem.id.replace("-anchor", "")].blur();
	}, keydown:function (oItem, e) {
		return this.all[oItem.id].keydown(e.keyCode);
	},
	insertHTMLBeforeEnd:function (oElement, sHTML, node) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML);
			toggleCurrentNode(node);
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
		toggleCurrentNode(node);
	}
};

/**
 * 获取展开路径
 *
 * @return
 */
function getToggledNodes(tree) {
	var path = "";
	for (var i = 0; i < tree.childNodes.length; i++) {
		var startNode = tree.childNodes[i];
		while (startNode != null && typeof startNode.open != "undefined" && startNode.open) {
			path += startNode.text + ",";
			var subNode = getSubToggledNodes(startNode);
			startNode = subNode;
		}
	}
	return path;
}
function getSubToggledNodes(parent) {
	for (var i = 0; parent.childNodes.length > 0 && i < parent.childNodes.length; i++) {
		if (parent.childNodes[i].open != null && parent.childNodes[i].open) {
			return parent.childNodes[i];
		}
	}
	return null;
}
/**
 * 展开当前节点
 *
 * @return
 */
function toggleCurrentNode(node) {
	if (typeof toggleNodes != "undefined" && toggleNodes.length > 0 && currentNodeIndex < toggleNodes.length) {
		for (var i = 0; i < toggleNodes.length; i++) {
			if (toggleNodes[i] == TreeHandler.all[node.id.replace("-plus", "")].text) {
				TreeHandler.all[node.id.replace("-plus", "")].toggle();
				if (currentNodeIndex < toggleNodes.length - 1) {
					TreeHandler.all[node.id.replace("-anchor", "")].blur();
				}
				currentNodeIndex++;
				if (currentNodeIndex == toggleNodes.length) {
					document.getElementById(node.id + "-anchor").focus();
					document.getElementById(node.id + "-anchor").click();
				}
				break;
			}
		}
	}
}



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

		this.checkClick = this.attributes.checkClick;
        this.checkDisable = this.attributes.checkDisable;
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
			if(typeof this.action == 'function') {
				this["html-element"]["text"].href = "javascript:void(0);";
				this["html-element"]["text"].onclick = this.action;
			} else {
				this["html-element"]["text"].href = this.action;
			}
			
			this["html-element"]["text"].target = this.target || this.getOwnerTree().root.target || TreeConfig.target;
		} else {
			this["html-element"]["text"] = document.createElement("span");
		}
		this["html-element"]["checkbox"] = document.createElement("img");
       // var checkInput = document.createElement("input");
     //   checkInput.type = "image";
        //this["html-element"]["checkbox"] = checkInput;
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

		this.registerCheckedNode(this);

        var ckImg = ownerTree.getIcon(((checked == 2) ? "checkbox2" : (checked == 1) ? "checkbox1" : "checkbox0"));
            
        if(this.isLeaf()){
            var disableValue = this.checkDisable;
            if(disableValue == 1 || disableValue == "1" || disableValue == true || disableValue == "true")   {
                 this["html-element"]["checkbox"].disabled = "disabled";
                 
                 ckImg = ckImg.replace(".gif", "_disabled.gif");
                 
            }
            
        }
		if (this["html-element"]) {
            this["html-element"]["checkbox"].src = ckImg;
		}
	}, setText:function(text) {
		this.text = text;
		this.paintText();
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
	}, onCheckClick: function() {
		if(this.checkClick){
            if(String.isInstance(this.checkClick)){
                eval(this.checkClick);
            } else {
			    this.checkClick();
            }
        }
	}, onCheck:function () {
        
		this.onCheckClick();
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
	}, registerCheckedNode: function() {
		var _tree = this.getOwnerTree();
		if(!_tree.checkedNodeMap) {
			_tree.checkedNodeMap = [];
		}

		if(this.checked == 1 || this.checked== 2) {/* 选中 */
			_tree.checkedNodeMap.append(this, true);
		} else {
			_tree.checkedNodeMap.remove(this);
		}

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

/*
 * AjaxTreeNode class
 */
AjaxTreeNode = TreeNode.extend({


	constructor: function (config) {
	/*
		text,url
		 {'text':'中国','checked':'0','pid':'-1','children':[{
	*/
	//sText, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon) {
		// call super
		//this.base(sText, sAction, sBehavior, sIcon, sOpenIcon);
	    this.base(config);

	    // setup default property values
		this.src = config.url;
		this.action = config.action || false;
		this.target = config.target || TreeConfig.target;
		this.loading = false;
		this.loaded = false;
		this.errorText = "";

		// check start state and load if open

		if (this.open)
			this._startLoadXmlTree(this.src);
		else {
			// and create loading item if not
			this._loadingItem = new TreeNode({
				text: TreeConfig.loadingText
			});
			this.appendChild(this._loadingItem);
		}

	},


	_tree_expand: TreeNode.prototype.expand,
	expand: function() {/* override the expand method to load the xml file */
		if (!this.loaded && !this.loading) {
			// load
			this._startLoadXmlTree();
		}
		this._tree_expand();
		//this.base.prototype.expand();
	},

	_startLoadXmlTree: function () {// creates the xmlhttp object and starts the load of the xml document
		if (this.loading || this.loaded)
			return;
		this.loading = true;
		var jsNode = this;
		new Sky.Ajax({
			url: this.src,
			onload: function () {
				jsNode._xmlFileLoaded(this.req.responseXML);
			}
		});
	},

	_xmlFileLoaded: function (oXmlDoc) {// Inserts an xml document as a subtree to the provided node
		if (this.loaded)
			return;

		var bIndent = false;
		var bAnyChildren = false;
		this.loaded = true;
		this.loading = false;

		// check that the load of the xml file went well
		if( oXmlDoc == null || oXmlDoc.documentElement == null) {
			alert(oXmlDoc.xml);
			this.errorText = this.parseTemplateString(TreeConfig.loadErrorTextTemplate,
								this.src);
		}
		else {
			// there is one extra level of tree elements
			var root = oXmlDoc.documentElement;

			// loop through all tree children
			var cs = root.childNodes;
			var l = cs.length;
			for (var i = 0; i < l; i++) {
				if (cs[i].tagName == "tree") {
					bAnyChildren = true;
					bIndent = true;
					this.appendChild( this._xmlTreeToJsTree(cs[i]));
				}
			}

			// if no children we got an error
			if (!bAnyChildren)
				this.errorText = this.parseTemplateString(TreeConfig.emptyErrorTextTemplate,
											this.src);

			this._tree_expand();
		}

		// remove dummy
		if (this._loadingItem != null) {
			this._loadingItem.remove();
			bIndent = true;
		}

		//if (bIndent) {
			// indent now that all items are added
		//	this.indent();
		//}

		// show error in status bar
		//if (jsParentNode.errorText != "")
			//window.status = jsParentNode.errorText;
	},

	parseTemplateString: function (sTemplate) {// parses a string and replaces %n% with argument nr n
		var args = arguments;
		var s = sTemplate;

		s = s.replace(/\%\%/g, "%");

		for (var i = 1; i < args.length; i++)
			s = s.replace( new RegExp("\%" + i + "\%", "g"), args[i] )

		return s;
	},

	_xmlTreeToJsTree: function (oNode) {// Converts an xml tree to a js tree. See article about xml tree format
		// retreive attributes
		var text = oNode.getAttribute("text");
		var action = oNode.getAttribute("action");
		var parent = null;
		var icon = oNode.getAttribute("icon");
		var openIcon = oNode.getAttribute("openIcon");
		var src = oNode.getAttribute("src");
		var target = oNode.getAttribute("target");
		var isLeaf = oNode.getAttribute("isLeaf");

		// create jsNode
		var jsNode;
		if (src != null && src != ""){
		//text, src, action, parent, icon, openIcon);
			jsNode = new AjaxTreeNode({
				text: text,
				action: action,
				url: src
			});
		} else
			jsNode = new TreeNode({
				text: text,
				action: action
			});

		if (target != "")
			jsNode.target = target;

		//jsNode._isLeaf = isLeaf || false;
		//if(jsNode.isLeaf == "true"){
		//	jsNode.folder = false;
		//	jsNode.open = false;
		//}

		// go through childNOdes
		var cs = oNode.childNodes || [];
		var l = cs.length;
		for (var i = 0; i < l; i++) {
			if (cs[i].tagName == "tree")
				jsNode.appendChild( this._xmlTreeToJsTree(cs[i]), true );
		}

		return jsNode;
	}
});



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

                        if(node.isLeaf()){
                            var disableValue = node.checkDisable;
                            if(disableValue == 1 || disableValue == "1" || disableValue == true || disableValue == "true")   {
                                 node["html-element"]["checkbox"].disabled = "disabled";
                                return;
                            }
                        }

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
	}, getCheckDisabledNodes:function (name) {
		var checkDisabledNodes = [];
		name = name || "id";
		for (var k in this.nodeHash) {
			var node = this.nodeHash[k];
			if (node.checkDisable == 1 || node.checkDisable == "1" || node.checkDisable == true || node.checkDisable == "true") {
				var value = node.attributes[name];
				if (value != null) {
					checkDisabledNodes.push(node);
				}
			}
		}
		return checkDisabledNodes;
	}, getCheckedNodes: function() {
		return this.checkedNodeMap;
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
	checkbox0disabled:"checkbox_0_disabled.gif",
	checkbox1disabled:"checkbox_1_disabled.gif",
	checkbox2disabled:"checkbox_2_disabled.gif",
	org:"org.gif",
	edp:"edp.gif",
	emp:"emp.gif"
};

//<script>
//////////////////
// Helper Stuff //
//////////////////

// used to find the Automation server name
function getDomDocumentPrefix() {
	if (getDomDocumentPrefix.prefix)
		return getDomDocumentPrefix.prefix;

	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".DomDocument");
			return getDomDocumentPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}

	throw new Error("Could not find an installed XML parser");
}

function getXmlHttpPrefix() {
	if (getXmlHttpPrefix.prefix)
		return getXmlHttpPrefix.prefix;

	var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
	var o;
	for (var i = 0; i < prefixes.length; i++) {
		try {
			// try to create the objects
			o = new ActiveXObject(prefixes[i] + ".XmlHttp");
			return getXmlHttpPrefix.prefix = prefixes[i];
		}
		catch (ex) {};
	}

	throw new Error("Could not find an installed XML parser");
}

//////////////////////////
// Start the Real stuff //
//////////////////////////


// XmlDocument factory
function XmlDocument() {}

XmlDocument.create = function () {
	try {
		// DOM2
		if (document.implementation && document.implementation.createDocument) {
			var doc = document.implementation.createDocument("", "", null);

			// some versions of Moz do not support the readyState property
			// and the onreadystate event so we patch it!
			if (doc.readyState == null) {
				doc.readyState = 1;
				doc.addEventListener("load", function () {
					doc.readyState = 4;
					if (typeof doc.onreadystatechange == "function")
						doc.onreadystatechange();
				}, false);
			}

			return doc;
		}
		if (window.ActiveXObject)
			return new ActiveXObject(getDomDocumentPrefix() + ".DomDocument");
	}
	catch (ex) {}
	throw new Error("Your browser does not support XmlDocument objects");
};

// Create the loadXML method and xml getter for Mozilla
if (window.DOMParser &&
	window.XMLSerializer &&
	window.Node && Node.prototype && Node.prototype.__defineGetter__) {

	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	XMLDocument.prototype.loadXML =
	Document.prototype.loadXML = function (s) {

		// parse the string to a new doc
		var doc2 = (new DOMParser()).parseFromString(s, "text/xml");

		// remove all initial children
		while (this.hasChildNodes())
			this.removeChild(this.lastChild);

		// insert and import nodes
		for (var i = 0; i < doc2.childNodes.length; i++) {
			this.appendChild(this.importNode(doc2.childNodes[i], true));
		}
	};


	/*
	 * xml getter
	 *
	 * This serializes the DOM tree to an XML String
	 *
	 * Usage: var sXml = oNode.xml
	 *
	 */
	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	XMLDocument.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
	Document.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
}