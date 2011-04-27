
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
