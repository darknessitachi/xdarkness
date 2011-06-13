package com.xdarkness.platform.service;

import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;

public class MenuService {

	/**
	 * get all menus
	 * 
	 * @return
	 */
	public DataTable getAllMenus() {
		return new QueryBuilder(
		"select * from zdmenu order by orderflag,id")
		.executeDataTable();
	}
	
	/**
	 * get all menus
	 * 		and add columns: Expand, TreeLevel with ''
	 * @return
	 */
	public DataTable getMenusWithExpandAndTreeLevel() {
		String sql = "select ID,ParentID,Name,Icon,URL,Visiable,Addtime,Memo,Type,'' as Expand,'' as TreeLevel from ZDMenu order by OrderFlag,id";
		return new QueryBuilder(sql).executeDataTable();
	}

	/**
	 * get the menus which's visiable is 'Y' and level is 1 or 2
	 * 
	 * @return
	 */
	public DataTable getVisibleMenus() {
		String sql = "select * from ZDMenu where (parentid in(select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag,id";
		return new QueryBuilder(sql).executeDataTable();
	}

	/**
	 * get first level menus's id, name
	 * @return
	 */
	public DataTable getFirstLevelMenusIdAndName() {
		return new QueryBuilder(
				"select name,id from zdmenu where ParentID=0 order by OrderFlag,id")
				.executeDataTable();
	}
	
	public DataTable getFirstLevelMenus(){
		 return new QueryBuilder(
			"select * from zdMenu where parentID = 0 order by orderflag,id")
			.executeDataTable();
	}

	/**
	 * get the parent menu's all child menus
	 * @param parentId
	 * @return
	 */
	public DataTable getMenusByParentId(String parentId) {
		return new QueryBuilder(
				"select * from zdmenu where parentID = ? order by orderflag,id",
				parentId).executeDataTable();
	}

	public long getOrderFlagById(String id) {
		return new QueryBuilder(
				"select OrderFlag from zdmenu where ID = ?",
				id).executeLong();
	}

	/**
	 * get child menus's count
	 * @param parentId
	 * @param excludeIds which are not in consider
	 * @return
	 */
	public long getChildMenusCount(long parentId, String excludeIds) {
		return new QueryBuilder(
				"select count(id) from zdmenu where parentid="
						+ parentId + " and id not in (" + excludeIds + ")")
				.executeLong();
	}
	
	/**
	 * judge the child menus's count more than 0
	 * @param parentId
	 * @param excludeIds which are not in consider
	 * @return
	 */
	public boolean hasChildMenus(long parentId, String excludeIds) {
		return getChildMenusCount(parentId, excludeIds) > 0L;
	}

	/**
	 * get menu by id or parent id
	 * @param parentId
	 * @param id
	 * @return
	 */
	public DataTable getMenusByIdOrParentId(String parentId, String id) {
		return new QueryBuilder(
				"select * from zdMenu where parentID = ? or id = ? order by orderflag,id",
				parentId, id).executeDataTable();
	}

}
