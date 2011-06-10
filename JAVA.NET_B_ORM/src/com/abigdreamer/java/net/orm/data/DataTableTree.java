package com.abigdreamer.java.net.orm.data;

import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.Treex;

public class DataTableTree extends Treex {


	public static Treex dataTableToTree(DataTable dt) {
		return dataTableToTree(dt, "ID", "ParentID");
	}

	public static Treex dataTableToTree(DataTable dt,
			String identifierColumnName, String parentIdentifierColumnName) {
		Treex tree = new Treex();
		// 用来根据子元素查找父元素的map
		Mapx map = dt.toMapx(identifierColumnName, parentIdentifierColumnName);
		// 用来根据父元素查找子元素的map
		Mapx map2 = dt.toMapx(parentIdentifierColumnName, identifierColumnName);
		for (int i = 0; i < dt.getRowCount(); ++i) {
			Object ID = dt.get(i, identifierColumnName);// 当前元素的主键
			Object parentID = map.get(ID+"");// 当前元素的父元素
			if ((parentID == null) || (map.get(parentID+"") == null)) {// 是一级节点
				DataRow dr = dt.getDataRow(i);
				
				TreeNode tn = tree.root.addChild(dr);// 放入根节点下
				dealNode(dt, tn, map2, identifierColumnName,
						parentIdentifierColumnName);
			}
		}
		return tree;
	}

	/**
	 * 从dt中获取当前节点的所有子节点
	 * 
	 * @param dt
	 *            数据列表
	 * @param tn
	 *            需处理节点
	 * @param map
	 *            包含根据父节点查找子节点的索引列表
	 * @param identifierColumnName
	 *            主键列名称
	 * @param parentIdentifierColumnName
	 *            父节点的列名称——外键
	 */
	private static void dealNode(DataTable dt, TreeNode tn, Mapx map,
			String identifierColumnName, String parentIdentifierColumnName) {
		DataRow dr = (DataRow) tn.getData();
		Object ID = dr.get(identifierColumnName);// id's value
		for (int i = 0; i < dt.getRowCount(); ++i) {// loop all data
			Object ChildID = dt.get(i, identifierColumnName);// get is
			Object parentID = dt.get(i, parentIdentifierColumnName); // get
			// parent's
			// id
			if (parentID.equals(ID)) {// the node is who's child which is
				// current dealing with

				TreeNode childNode = tn.addChild(dt.getDataRow(i));
				if (map.get(ChildID) != null) {
					// deal children
					dealNode(dt, childNode, map, identifierColumnName,
							parentIdentifierColumnName);
				}

			}
		}
	}
}
