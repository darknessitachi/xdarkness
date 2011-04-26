package com.xdarkness.jaf.controls.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.controls.Constant;
import com.xdarkness.jaf.controls.IControlAction;
import com.xdarkness.jaf.controls.html.HtmlP;
import com.xdarkness.jaf.controls.html.HtmlScript;

public class TreeAction implements IControlAction {

	public TreeAction() {
		rootIcon = "Icons/treeicon10.gif";
		leafIcon = "Icons/treeicon09.gif";
		branchIcon = "Icons/treeicon09.gif";
		items = new ArrayList();
		templateAreas = new ArrayList();
		templateFields = new ArrayList();
		IdentifierColumnName = "ID";
		ParentIdentifierColumnName = "ParentID";
		Params = new Mapx();
	}

	public void setTemplate(HtmlP p) {
		onMouseOver = p.getAttribute("onMouseOver");
		onMouseOut = p.getAttribute("onMouseOut");
		onClick = p.getAttribute("onClick");
		onContextMenu = p.getAttribute("onContextMenu");
		p.removeAttribute("onClick");
		p.removeAttribute("onContextMenu");
		template = p;
		String html = template.getOuterHtml();
		Matcher m = Constant.PatternField.matcher(html);
		int lastEndIndex;
		for (lastEndIndex = 0; m.find(lastEndIndex); lastEndIndex = m.end()) {
			templateAreas.add(html.substring(lastEndIndex, m.start()));
			templateFields.add(m.group(1));
		}

		templateAreas.add(html.substring(lastEndIndex));
	}

	public void bindData(DataTable dt) {
		DataSource = dt;
		try {
			bindData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindData() throws Exception {
		if (DataSource == null)
			throw new RuntimeException("必须在bindData方法中设定DataSource");
		items.clear();
		root = new TreeItem();
		root.setID("_TreeRoot");
		root.setParentID("");
		root.setRoot(true);
		root.setText(rootText);
		root.setAction(this);
		root.setLevel(0);
		root.setAttribute("onMouseOver", onMouseOver);
		root.setAttribute("onContextMenu", onContextMenu);
		root.setAttribute("onClick", onClick);
		root.setAttribute("onMouseOut", onMouseOut);
		items.add(root);
		Mapx map = new Mapx();
		// 节点id：父节点id 映射表
		for (int i = 0; i < DataSource.getRowCount(); i++)
			map.put(DataSource.getString(i, IdentifierColumnName), DataSource
					.getString(i, ParentIdentifierColumnName));

		try {
			TreeItem last = null;
			for (int i = 0; i < DataSource.getRowCount(); i++) {
				DataRow dr = DataSource.getDataRow(i);
				String parentID = dr.getString(ParentIdentifierColumnName);
				if (StringUtil.isEmpty(parentID) || !map.containsKey(parentID)) {
					TreeItem item = new TreeItem();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < templateAreas.size(); j++) {
						sb.append(templateAreas.get(j));
						if (j < templateFields.size())
							sb.append(dr.getString(templateFields.get(j).toString()));
					}

					item.setData(dr);
					item.parseHtml(sb.toString());
					item.setAction(this);
					item.setID(dr.getString(IdentifierColumnName));
					item.setParentID(parentID);
					if (lazyLoad) {
						item.setLevel(parentLevel + 1);
						item.setLevelStr((String) getParams().get("LevelStr"));
					} else {
						item.setLevel(1);
					}
					item.setParent(root);
					items.add(item);
					addChild(item);
					last = item;
				}
			}

			if (last != null)
				last.setLast(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChild(TreeItem parent) throws Exception {
		boolean childFlag = false;
		TreeItem last = null;
		for (int i = 0; i < DataSource.getRowCount(); i++) {
			DataRow dr = DataSource.getDataRow(i);
			if (parent.getID().equals(dr.getString(ParentIdentifierColumnName))) {
				childFlag = true;
				if (parent.getLevel() >= level && (!lazyLoad || expand)) {
					parent.setLazy(lazy);
					parent.setExpanded(false);
					parent.setBranch(childFlag);
					return;
				}
				TreeItem item = new TreeItem();
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < templateAreas.size(); j++) {
					sb.append(templateAreas.get(j));
					if (j < templateFields.size())
						sb.append(dr.getString(templateFields.get(j).toString()));
				}

				item.setData(dr);
				item.parseHtml(sb.toString());
				item.setAction(this);
				item.setID(dr.getString(IdentifierColumnName));
				item.setParentID(parent.getID());
				item.setLevel(parent.getLevel() + 1);
				item.setParent(parent);
				if (lazyLoad)
					item.setLevelStr((String) getParams().get("LevelStr"));
				items.add(item);
				addChild(item);
				last = item;
			}
		}

		if (last != null)
			last.setLast(true);
		if (!lazy && parent.getLevel() + 1 == level)
			parent.setExpanded(false);
		parent.setBranch(childFlag);
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		String styleStr = "";
		
		if (StringUtil.isNotEmpty(style))
			styleStr = styleStr + style;
		
		if (!lazyLoad)
			sb.append("<div xtype='_Tree' style='-moz-user-select:none;"
					+ styleStr + "'  onselectstart='stopEvent(event);' id='"
					+ ID + "' method='" + method
					+ "' class='treeItem'><table><tr><td>");
		
		for (int i = 0; i < items.size(); i++)
			if (!lazyLoad || getItem(i).getLevel() > parentLevel) {
				if (i != 0 && getItem(i).getLevel() > getItem(i - 1).getLevel())
					if (getItem(i).getLevel() == level && !lazyLoad && !lazy)
						sb.append("<div style='display:none'>");
					else
						sb.append("<div>");
				sb.append(((TreeItem) items.get(i)).getOuterHtml());
				if (i != items.size() - 1
						&& getItem(i).getLevel() > getItem(i + 1).getLevel()) {
					for (int j = 0; j < getItem(i).getLevel()
							- getItem(i + 1).getLevel(); j++)
						sb.append("</div>");

				}
				if (i == items.size() - 1) {
					for (int j = 0; j < getItem(i).getLevel() - parentLevel; j++)
						sb.append("</div>");

				}
			}

		if (!lazyLoad) {
			HtmlScript script = new HtmlScript();
			script.setInnerHTML(getScript());
			sb.append(script.getOuterHtml());
			sb.append("</td></tr></table></div>\n\r");
		}
		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("$('" + ID + "').TagBody = \""
				+ StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object ks[] = Params.keyArray();
		Object vs[] = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals("_SKY_TAGBODY")
					&& !key.toString().startsWith("Cookie.")
					&& !key.toString().startsWith("Header."))
				sb.append("Tree.setParam('" + ID + "','" + key + "',\"" + vs[i]
						+ "\");");
		}

		sb.append("Tree.setParam('" + ID + "','" + "_SKY_TREE_STYLE" + "',\""
				+ style + "\");");
		sb.append("Tree.setParam('" + ID + "','" + "_SKY_TREE_LEVEL" + "',"
				+ level + ");");
		sb.append("Tree.setParam('" + ID + "','" + "_SKY_TREE_LAZY" + "',\""
				+ lazy + "\");");
		sb.append("Tree.setParam('" + ID + "','" + "_SKY_TREE_EXPAND"
				+ "',\"" + expand + "\");");
		sb.append("Tree.init('" + ID + "');");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex;
		for (lastEndIndex = 0; matcher.find(lastEndIndex); lastEndIndex = matcher
				.end()) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
		}

		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public String getRootText() {
		return rootText;
	}

	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	public void setRootIcon(String iconFileName) {
		rootIcon = iconFileName;
	}

	public void setLeafIcon(String iconFileName) {
		leafIcon = iconFileName;
	}

	public void setBranchIcon(String iconFileName) {
		branchIcon = iconFileName;
	}

	public String getBranchIcon() {
		return branchIcon;
	}

	public String getLeafIcon() {
		return leafIcon;
	}

	public String getRootIcon() {
		return rootIcon;
	}

	public String getIdentifierColumnName() {
		return IdentifierColumnName;
	}

	public void setIdentifierColumnName(String identifierColumnName) {
		IdentifierColumnName = identifierColumnName;
	}

	public String getParentIdentifierColumnName() {
		return ParentIdentifierColumnName;
	}

	public void setParentIdentifierColumnName(String parentIdentifierColumnName) {
		ParentIdentifierColumnName = parentIdentifierColumnName;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnContextMenu() {
		return onContextMenu;
	}

	public void setOnContextMenu(String onContextMenu) {
		this.onContextMenu = onContextMenu;
	}

	public String getOnMouseOut() {
		return onMouseOut;
	}

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public String getOnMouseOver() {
		return onMouseOver;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public boolean isLazyLoad() {
		return lazyLoad;
	}

	public void setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}

	public int getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(int parentLevel) {
		this.parentLevel = parentLevel;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public DataTable getDataSource() {
		return DataSource;
	}
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	public String getParam(String key) {
		return Params.getString(key);
	}
	public List getItemList() {
		return items;
	}

	public TreeItem getItem(int index) {
		return (TreeItem) items.get(index);
	}

	public int getItemSize() {
		return items.size();
	}

	public void addItem(TreeItem item) {
		items.add(item);
	}

	public void addItem(TreeItem item, int index) {
		items.add(index, item);
	}
	private String rootIcon;
	private String leafIcon;
	private String branchIcon;
	private String onClick;
	private String onMouseOver;
	private String onMouseOut;
	private String onContextMenu;
	private HtmlP template;
	private ArrayList items;
	private DataTable DataSource;
	ArrayList templateAreas;
	ArrayList templateFields;
	private String IdentifierColumnName;
	private String ParentIdentifierColumnName;
	private String rootText;
	private String ID;
	private int level;
	private int parentLevel;
	private boolean lazy;
	private boolean lazyLoad;
	private boolean expand;
	private String style;
	private String TagBody;
	private TreeItem root;
	protected Mapx Params;
	private String method;
}
