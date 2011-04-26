// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeItem.java

package com.xdarkness.jaf.controls.tree;


import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.MainServlet;
import com.xdarkness.jaf.controls.html.HtmlP;

//            HtmlP, TreeAction

public class TreeItem extends HtmlP implements Cloneable {

	public TreeItem() {
		isExpanded = true;
	}

	public String getOuterHtml() {
		HtmlP item = new HtmlP();
		item.Attributes.putAll(Attributes);
		item.setAttribute("level", "" + level);
		item.setAttribute("id", action.getID() + "_" + ID);
		item.setAttribute("parentID", ParentID);
		item.setAttribute("lazy", lazy ? "1" : "0");
		String onclick = getAttributeExt("onclick");
		if (StringUtil.isEmpty(onclick))
			onclick = "";
		item.setAttribute("onclick", "Tree.onItemClick(event,this);" + onclick);
		item.setAttribute("ondblclick", "Tree.onItemDblClick(event,this);");
		item.setAttribute("oncontextmenu", getAttributeExt("oncontextmenu"));
		String afterDrag = getAttribute("afterDrag");
		if (StringUtil.isNotEmpty(afterDrag)) {
			item.setAttribute("dragEnd", "Tree.dragEnd");
			item.setAttribute("onMouseUp", "DragManager.onMouseUp(event,this)");
			if (action.Params.getString("Header.UserAgent").indexOf("msie") >= 0)
				item.setAttribute("onMouseEnter",
						"DragManager.onMouseOver(event,this)");
			else
				item.setAttribute("onMouseOver",
						"DragManager.onMouseOver(event,this)");
			item.setAttribute("dragOver", "Tree.dragOver");
			if (action.Params.getString("Header.UserAgent").indexOf("msie") >= 0)
				item.setAttribute("onMouseLeave",
						"DragManager.onMouseOut(event,this)");
			else
				item.setAttribute("onMouseOut",
						"DragManager.onMouseOut(event,this)");
			item.setAttribute("dragOut", "Tree.dragOut");
		}
		String text = getText();
		String prefix = MainServlet.CONTEXT_PATH;
		text = "<img src='" + prefix + getIcon() + "'>" + text;
		StringBuffer levelSb = new StringBuffer();
		if (isBranch && isLast && isExpanded) {
			text = "<img onclick='Tree.onBranchIconClick(event);' src='"
					+ prefix + "Icons/treeicon05.gif" + "'>" + text;
			if (lazy)
				levelSb.insert(0, "0");
			item.setAttribute("expand", "4");
		}
		if (isBranch && isLast && !isExpanded) {
			text = "<img onclick='Tree.onBranchIconClick(event);' src='"
					+ prefix + "Icons/treeicon04.gif" + "'>" + text;
			if (lazy)
				levelSb.insert(0, "0");
			item.setAttribute("expand", "3");
		}
		if (isBranch && !isLast && !isExpanded) {
			text = "<img onclick='Tree.onBranchIconClick(event);' src='"
					+ prefix + "Icons/treeicon01.gif" + "'>" + text;
			if (lazy)
				levelSb.insert(0, "1");
			item.setAttribute("expand", "1");
		}
		if (isBranch && !isLast && isExpanded) {
			text = "<img onclick='Tree.onBranchIconClick(event);' src='"
					+ prefix + "Icons/treeicon02.gif" + "'>" + text;
			if (lazy)
				levelSb.insert(0, "1");
			item.setAttribute("expand", "2");
		}
		if (isLeaf && isLast)
			text = "<img src='" + prefix + "Icons/treeicon07.gif" + "'>" + text;
		if (isLeaf && !isLast)
			text = "<img src='" + prefix + "Icons/treeicon06.gif" + "'>" + text;
		for (TreeItem pTI = parent; pTI != null && !pTI.isRoot; pTI = pTI.parent)
			if (pTI.isLast) {
				text = "<img src='" + prefix + "Icons/treeicon08.gif" + "'>"
						+ text;
				if (lazy)
					levelSb.insert(0, "0");
			} else {
				text = "<img src='" + prefix + "Icons/treeicon03.gif" + "'>"
						+ text;
				if (lazy)
					levelSb.insert(0, "1");
			}

		if (levelStr != null) {
			for (int j = levelStr.length() - 1; j >= 0; j--)
				if (levelStr.charAt(j) == '0')
					text = "<img src='" + prefix + "Icons/treeicon08.gif"
							+ "'>" + text;
				else
					text = "<img src='" + prefix + "Icons/treeicon03.gif"
							+ "'>" + text;

			if (lazy)
				levelSb.insert(0, levelStr);
		}
		if (lazy)
			item.setAttribute("levelstr", levelSb.toString());
		item.setInnerHTML(text);
		return item.getOuterHtml();
	}

	private String getAttributeExt(String attr) {
		String v = getAttribute(attr);
		if (StringUtil.isEmpty(v)) {
			if (parent != null)
				return parent.getAttributeExt(attr);
			else
				return null;
		} else {
			return v;
		}
	}

	public String getIcon() {
		if (icon == null) {
			if (isRoot)
				return action.getRootIcon();
			if (isLeaf)
				return action.getLeafIcon();
			if (isBranch)
				return action.getBranchIcon();
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isBranch() {
		return isBranch;
	}

	public void setBranch(boolean isBranch) {
		this.isBranch = isBranch;
		isLeaf = !isBranch;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
		isBranch = !isLeaf;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOnClick() {
		return getAttributeExt("onclick");
	}

	public void setOnClick(String onClick) {
		setAttribute("onclick", onClick);
	}

	public String getOnContextMenu() {
		return getAttributeExt("oncontextmenu");
	}

	public void setOnContextMenu(String onContextMenu) {
		setAttribute("oncontextmenu", onContextMenu);
	}

	public String getOnMouseOut() {
		return getAttributeExt("onmouseout");
	}

	public void setOnMouseOut(String onMouseOut) {
		setAttribute("onmouseout", onMouseOut);
	}

	public String getOnMouseOver() {
		return getAttributeExt("onmouseover");
	}

	public void setOnMouseOver(String onMouseOver) {
		setAttribute("onmouseover", onMouseOver);
	}

	public TreeItem getParent() {
		return parent;
	}

	public void setParent(TreeItem parent) {
		this.parent = parent;
	}

	public String getText() {
		return getInnerHTML();
	}

	public void setText(String text) {
		setInnerHTML(text);
	}

	public TreeAction getAction() {
		return action;
	}

	public void setAction(TreeAction action) {
		this.action = action;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public DataRow getData() {
		if (isRoot && data == null)
			throw new RuntimeException("Root\u8282\u70B9\u6CA1\u6709Data.");
		else
			return data;
	}

	public void setData(DataRow data) {
		this.data = data;
	}

	public static final String Icon_Branch_NotLast_NotExpand = "Icons/treeicon01.gif";
	public static final String Icon_Branch_Last_NotExpand = "Icons/treeicon04.gif";
	public static final String Icon_Branch_NotLast_Expand = "Icons/treeicon02.gif";
	public static final String Icon_Branch_Last_Expand = "Icons/treeicon05.gif";
	public static final String Icon_Line_Vertical = "Icons/treeicon03.gif";
	public static final String Icon_Line_Null = "Icons/treeicon08.gif";
	public static final String Icon_Leaf_Last = "Icons/treeicon07.gif";
	public static final String Icon_Leaf_NotLast = "Icons/treeicon06.gif";
	public static final String Branch_NotLast_NotExpand = "1";
	public static final String Branch_NotLast_Expand = "2";
	public static final String Branch_Last_NotExpand = "3";
	public static final String Branch_Last_Expand = "4";
	public static final String Icon_Special = "Framework/Images/icon_folder_special.gif";
	public static final String Icon_Image = "Framework/Images/icon_folder_image.gif";
	public static final String Icon_Video = "Framework/Images/icon_folder_video.gif";
	public static final String Icon_Audio = "Framework/Images/icon_folder_audio.gif";
	private String icon;
	private int level;
	private boolean isLast;
	private boolean isRoot;
	private boolean isBranch;
	private boolean isLeaf;
	private boolean lazy;
	private TreeItem parent;
	private TreeAction action;
	private String ID;
	private String ParentID;
	private String levelStr;
	private boolean isExpanded;
	private DataRow data;
}
