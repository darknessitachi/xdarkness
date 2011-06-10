package com.abigdreamer.java.net.jaf.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.abigdreamer.java.net.jaf.Constant;
import com.abigdreamer.java.net.jaf.html.element.HtmlP;
import com.abigdreamer.java.net.jaf.html.element.HtmlScript;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class TreeAction implements IControlAction {
	private String rootIcon = "Icons/treeicon10.gif";

	private String leafIcon = "Icons/treeicon09.gif";

	private String branchIcon = "Icons/treeicon09.gif";
	private String onClick;
	private String onMouseOver;
	private String onMouseOut;
	private String onContextMenu;
	private HtmlP template;
	private ArrayList items = new ArrayList();
	private DataTable DataSource;
	ArrayList a1 = new ArrayList();

	ArrayList a2 = new ArrayList();

	private String IdentifierColumnName = "ID";

	private String ParentIdentifierColumnName = "ParentID";
	private String rootText;
	private String ID;
	private int level;
	private int parentLevel;
	private boolean lazy;
	private boolean resizeable;
	private boolean lazyLoad;
	private boolean expand;
	private String style;
	private String TagBody;
	private TreeItem root;
	protected Mapx Params = new Mapx();
	private String method;

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return this.Params;
	}

	public void setParams(Mapx params) {
		this.Params = params;
	}

	public String getParam(String key) {
		return this.Params.getString(key);
	}

	public void setTemplate(HtmlP p) {
		this.onMouseOver = p.getAttribute("onMouseOver");
		this.onMouseOut = p.getAttribute("onMouseOut");
		this.onClick = p.getAttribute("onClick");
		this.onContextMenu = p.getAttribute("onContextMenu");

		p.removeAttribute("onClick");
		p.removeAttribute("onContextMenu");

		this.template = p;
		String html = this.template.getOuterHtml();
		Matcher m = Constant.PatternField.matcher(html);
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			this.a1.add(html.substring(lastEndIndex, m.start()));
			this.a2.add(m.group(1));
			lastEndIndex = m.end();
		}
		this.a1.add(html.substring(lastEndIndex));
	}

	public List getItemList() {
		return this.items;
	}

	public TreeItem getItem(int index) {
		return (TreeItem) this.items.get(index);
	}

	public int getItemSize() {
		return this.items.size();
	}

	public void addItem(TreeItem item) {
		this.items.add(item);
	}

	public void addItem(TreeItem item, int index) {
		this.items.add(index, item);
	}

	public void bindData(DataTable dt) {
		this.DataSource = dt;
		try {
			bindData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
	}

	private void bindData() throws Exception {
		if (this.DataSource == null) {
			throw new RuntimeException("必须在bindData方法中设定DataSource");
		}

		this.items.clear();

		this.root = new TreeItem();
		this.root.setID("_TreeRoot");
		this.root.setParentID("");
		this.root.setRoot(true);
		this.root.setText(this.rootText);
		this.root.setAction(this);
		this.root.setLevel(0);

		this.root.setAttribute("onMouseOver", this.onMouseOver);
		this.root.setAttribute("onContextMenu", this.onContextMenu);
		this.root.setAttribute("onClick", this.onClick);
		this.root.setAttribute("onMouseOut", this.onMouseOut);

		this.items.add(this.root);

		Mapx map = new Mapx();
		for (int i = 0; i < this.DataSource.getRowCount(); i++)
			map.put(this.DataSource.getString(i, this.IdentifierColumnName),
					this.DataSource.getString(i,
							this.ParentIdentifierColumnName));
		try {
			TreeItem last = null;
			for (int i = 0; i < this.DataSource.getRowCount(); i++) {
				DataRow dr = this.DataSource.getDataRow(i);
				String parentID = dr.getString(this.ParentIdentifierColumnName);
				if ((XString.isEmpty(parentID))
						|| (!map.containsKey(parentID))) {
					TreeItem item = new TreeItem();
					item.setData(dr);
					item.parseHtml(getItemInnerHtml(dr));
					item.setAction(this);
					item.setID(dr.getString(this.IdentifierColumnName));
					item.setParentID(parentID);
					if (this.lazyLoad) {
						item.setLevel(this.parentLevel + 1);
						item.setLevelStr((String) getParams().get("LevelStr"));
					} else {
						item.setLevel(1);
					}
					item.setParent(this.root);
					this.items.add(item);
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
		int index = -1;
		for (int i = this.items.size() - 1; i >= 0; i--) {
			if (this.items.get(i) == parent) {
				index = i;
				break;
			}
		}
		ArrayList list = new ArrayList();
		for (int i = 0; i < this.DataSource.getRowCount(); i++) {
			DataRow dr = this.DataSource.getDataRow(i);
			String pid = dr.getString(this.ParentIdentifierColumnName);
			String id = dr.getString(this.IdentifierColumnName);
			if ((parent.getID().equals(pid)) && (!XString.isEmpty(id))
					&& (!id.equals(pid))) {
				childFlag = true;
				if ((parent.getLevel() >= this.level)
						&& ((!this.lazyLoad) || (!this.expand))) {
					parent.setLazy(this.lazy);
					parent.setExpanded(false);
					parent.setBranch(childFlag);
					return;
				}
				TreeItem item = new TreeItem();
				item.setData(dr);
				item.parseHtml(getItemInnerHtml(dr));
				item.setAction(this);
				item.setID(dr.getString(this.IdentifierColumnName));
				item.setParentID(parent.getID());
				item.setLevel(parent.getLevel() + 1);
				item.setParent(parent);
				if (this.lazyLoad) {
					item.setLevelStr((String) getParams().get("LevelStr"));
				}
				list.add(item);
				this.items.add(index + list.size(), item);
				last = item;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			addChild((TreeItem) list.get(i));
		}
		if (last != null) {
			last.setLast(true);
		}
		if ((!this.lazy) && (parent.getLevel() + 1 == this.level)) {
			parent.setExpanded(false);
		}
		parent.setBranch(childFlag);
	}

	public String getItemInnerHtml(DataRow dr) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < this.a1.size(); j++) {
			sb.append(this.a1.get(j));
			if (j < this.a2.size()) {
				sb.append(dr.getString(this.a2.get(j).toString()));
			}
		}
		return sb.toString();
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		String styleStr = "";
		if (XString.isNotEmpty(this.style)) {
			styleStr = styleStr + this.style;
		}
		if (!this.lazyLoad) {
			sb
					.append("<div id='"
							+ this.ID
							+ "_container' class='treeContainer' style='-moz-user-select:none;"
							+ styleStr
							+ "'><div xtype='_Tree' onselectstart='stopEvent(event);' id='"
							+ this.ID + "' method='" + this.method
							+ "' class='treeItem'><table><tr><td>");
		}
		for (int i = 0; i < this.items.size(); i++) {
			if ((this.lazyLoad) && (getItem(i).getLevel() <= this.parentLevel)) {
				continue;
			}
			if ((i != 0) && (getItem(i).getLevel() > getItem(i - 1).getLevel())) {
				if ((getItem(i).getLevel() == this.level) && (!this.lazyLoad)
						&& (!this.lazy))
					sb.append("<div style='display:none'>");
				else {
					sb.append("<div>");
				}
			}

			sb.append(((TreeItem) this.items.get(i)).getOuterHtml());
			if ((i != this.items.size() - 1)
					&& (getItem(i).getLevel() > getItem(i + 1).getLevel())) {
				for (int j = 0; j < getItem(i).getLevel()
						- getItem(i + 1).getLevel(); j++) {
					sb.append("</div>");
				}
			}
			if (i == this.items.size() - 1) {
				for (int j = 0; j < getItem(i).getLevel() - this.parentLevel; j++) {
					sb.append("</div>");
				}
			}
		}
		if (!this.lazyLoad) {
			sb.append("</td></tr></table></div></div>\n\r");
			HtmlScript script = new HtmlScript();
			script.setInnerHTML(getScript());
			sb.append(script.getOuterHtml());
		}

		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();

		sb.append("$('" + this.ID + "').TagBody = \""
				+ XString.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object[] ks = this.Params.keyArray();
		Object[] vs = this.Params.valueArray();
		for (int i = 0; i < this.Params.size(); i++) {
			Object key = ks[i];
			if ((key.equals("_SKY_TAGBODY"))
					|| (key.toString().startsWith("Cookie."))
					|| (key.toString().startsWith("Header."))
					|| (vs[i] == null))
				continue;
			sb.append("Tree.setParam('" + this.ID + "','" + key + "',\""
					+ XString.javaEncode(vs[i].toString()) + "\");");
		}

		sb.append("Tree.setParam('" + this.ID + "','" + "_SKY_TREE_STYLE"
				+ "',\"" + this.style + "\");");
		sb.append("Tree.setParam('" + this.ID + "','" + "_SKY_TREE_LEVEL"
				+ "'," + this.level + ");");
		sb.append("Tree.setParam('" + this.ID + "','" + "_SKY_TREE_LAZY"
				+ "',\"" + this.lazy + "\");");
		sb.append("Tree.setParam('" + this.ID + "','" + "_SKY_TREE_EXPAND"
				+ "',\"" + this.expand + "\");");
		sb.append("Tree.init('" + this.ID + "'," + this.resizeable + ");");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	public String getRootText() {
		return this.rootText;
	}

	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	public void setRootIcon(String iconFileName) {
		this.rootIcon = iconFileName;
	}

	public void setLeafIcon(String iconFileName) {
		this.leafIcon = iconFileName;
	}

	public void setBranchIcon(String iconFileName) {
		this.branchIcon = iconFileName;
	}

	public String getBranchIcon() {
		return this.branchIcon;
	}

	public String getLeafIcon() {
		return this.leafIcon;
	}

	public String getRootIcon() {
		return this.rootIcon;
	}

	public String getIdentifierColumnName() {
		return this.IdentifierColumnName;
	}

	public void setIdentifierColumnName(String identifierColumnName) {
		this.IdentifierColumnName = identifierColumnName;
	}

	public String getParentIdentifierColumnName() {
		return this.ParentIdentifierColumnName;
	}

	public void setParentIdentifierColumnName(String parentIdentifierColumnName) {
		this.ParentIdentifierColumnName = parentIdentifierColumnName;
	}

	public boolean isLazy() {
		return this.lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isResizeable() {
		return this.resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnContextMenu() {
		return this.onContextMenu;
	}

	public void setOnContextMenu(String onContextMenu) {
		this.onContextMenu = onContextMenu;
	}

	public String getOnMouseOut() {
		return this.onMouseOut;
	}

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public String getOnMouseOver() {
		return this.onMouseOver;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTagBody() {
		return this.TagBody;
	}

	public void setTagBody(String tagBody) {
		this.TagBody = tagBody;
	}

	public boolean isLazyLoad() {
		return this.lazyLoad;
	}

	public void setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}

	public int getParentLevel() {
		return this.parentLevel;
	}

	public void setParentLevel(int parentLevel) {
		this.parentLevel = parentLevel;
	}

	public boolean isExpand() {
		return this.expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public DataTable getDataSource() {
		return this.DataSource;
	}
}

/*
 * com.xdarkness.framework.controls.TreeAction JD-Core Version: 0.6.0
 */