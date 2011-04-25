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