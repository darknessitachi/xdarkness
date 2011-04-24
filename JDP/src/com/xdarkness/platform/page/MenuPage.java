package com.xdarkness.platform.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.db2.jcc.b.s;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.User;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.jaf.BasePage;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.controls.grid.ITreeDataGrid;
import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.TwoTuple;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.service.MenuService;
import com.xdarkness.schema.ZDMenuSchema;
import com.xdarkness.schema.ZDMenuSet;

/**
 * @author Darkness create on 2010 2010-12-15 上午11:24:12
 * @version 1.0
 * @since JDP 1.0
 */
public class MenuPage extends BasePage<ZDMenuSchema> {
	public static Mapx<String, String> MenuCacheMap = new Mapx<String, String>();
	public static MenuService menuService = new MenuService();

	static {
		updateCache();
	}

	/**
	 * update MenuCacheMap from the DB
	 */
	private static void updateCache() {
		DataTable dt = menuService.getVisibleMenus();
		for (int i = 0; i < dt.getRowCount(); i++)
			MenuCacheMap.put(dt.getString(i, "URL"), dt.getString(i, "ID"));
	}

	/**
	 * 初始化树状表格
	 */
	@Override
	public ITreeDataGrid initExpandAndTreeLevel() {
		return new ITreeDataGrid() {

			public String getExpand(DataRow dr) {
				return "1".equals(dr.get("Type")) ? "Y" : "N";
			}

			public String getTreeLevel(DataRow dr) {
				return "2".equals(dr.get("Type")) ? "1" : "0";
			}

		};
	}

	/**
	 * 保存时的特殊处理
	 */
	@Override
	public void processingSchema(DataRow dr, Schema schema) {
		ZDMenuSchema menuSchema = (ZDMenuSchema) schema;
		if (menuSchema.getParentID() == 0L) {
			if (dr.getString("Expand").equals("Y"))
				menuSchema.setType("1");
			else {
				menuSchema.setType("3");
			}
		}
	}

	/**
	 * update all the menu into DB, and update the cache at the same time
	 */
	@Override
	public void onDataGridEditSuccess() {
		updateCache();
	}

	/**
	 * initialize ParentMenu with the html Options
	 * 
	 * @param params
	 * @return
	 */
	public Mapx<String, Object> init(Mapx<String, Object> params) {
		Mapx<String, Object> map = new Mapx<String, Object>();
		DataTable dt = menuService.getFirstLevelMenusIdAndName();
		map.put("ParentMenu", HtmlUtil.dataTableToOptions(dt));
		return map;
	}

	/**
	 * add menu
	 */
	public void add() {
		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setIcon($V("Icon").substring($V("Icon").indexOf("Icons/")));
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setAddTime(new Date());
		menu.setAddUser(User.getUserName());
		menu.setMemo($V("Memo"));
		menu.setName($V("Name"));
		menu.setURL($V("URL"));
		menu.setVisiable($V("Visiable"));
		menu.setParentID(Long.parseLong($V("ParentID")));

		String parentId = $V("ParentID");
		DataTable parentDT = null;
		if ("0".equals(parentId)) {
			parentDT = menuService.getAllMenus();
		} else {
			parentDT = menuService.getMenusByParentId(parentId);
		}

		long orderflag = 0L;
		if ((parentDT != null) && (parentDT.getRowCount() > 0)) {
			// get last menu's OrderFlag in parent menu's children
			orderflag = Long.parseLong(parentDT.getString(parentDT
					.getRowCount() - 1, "OrderFlag"));
		} else {
			// get parent menu's OrderFlag
			orderflag = menuService.getOrderFlagById(parentId);
			if ("0".equals(parentId)) {
				orderflag = 0L;
			}
		}
		menu.setOrderFlag(orderflag + 1L);
		Transaction trans = new Transaction();
		if (menu.getParentID() == 0L)
			menu.setType("1");
		else {
			menu.setType("2");
		}

		// set all menus's orderFlag++ which after current orderFlag
		trans
				.add(new QueryBuilder(
						"update zdmenu set orderflag = orderflag + 1 where orderflag > ?",
						orderflag));
		trans.add(menu, OperateType.INSERT);
		if (trans.commit()) {
			updateCache();
			this.response.setStatus(1);
			this.response.setMessage("添加成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("添加失败，操作数据库时发生错误!");
		}
	}

	/**
	 * delete the selected menus from DB, when the menu has child, and the child
	 * not selected, delete failed
	 */
	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ZDMenuSchema menu = new ZDMenuSchema();

		// get all menus which need to delete
		ZDMenuSet set = menu
				.query(new QueryBuilder("where id in (" + ids + ")"));

		StringBuffer menuLog = new StringBuffer("删除菜单：");
		for (int i = 0; i < set.size(); i++) {
			menu = set.get(i);
			if (menu.getParentID() == 0L) {// is first level menu
				if (menuService.hasChildMenus(menu.getID(), ids)) {
					this.response.setStatus(0);
					UserLogPage
							.log("System", "DelMenu", "删除菜单" + menu.getName()
									+ "失败", this.request.getClientIP());
					this.response.setMessage("不能删除菜单\"" + menu.getName()
							+ "\",该菜单下还有子菜单未被删除!");
					return;
				}
			}
			menuLog.append(menu.getName() + ",");
		}
		if (set.delete()) {
			updateCache();
			this.response.setStatus(1);
			UserLogPage.log("System", "DelMenu", menuLog + "成功", this.request
					.getClientIP());
			this.response.setMessage("删除成功!");
		} else {
			this.response.setStatus(0);
			UserLogPage.log("System", "DelMenu", menuLog + "失败", this.request
					.getClientIP());
			this.response.setMessage("删除失败，操作数据库时发生错误!");
		}
	}

	public void sortMenu() {
		String orderMenu = $V("OrderMenu");
		String nextMenu = $V("NextMenu");
		String ordertype = $V("OrderType");
		if ((XString.isEmpty(orderMenu)) || (XString.isEmpty(nextMenu))
				|| (XString.isEmpty(ordertype))) {
			this.response.setLogInfo(0, "传递数据有误！");
			return;
		}

		Transaction trans = new Transaction();

		// initialize DT with first, second level menu
		DataTable DT = new DataTable();
		DataTable parentDT = menuService.getFirstLevelMenus();
		for (int i = 0; i < parentDT.getRowCount(); i++) {
			DT.insertRow(parentDT.getDataRow(i));
			DataTable childDT = menuService.getMenusByParentId(parentDT.get(i,
					"ID")
					+ "");
			for (int j = 0; j < childDT.getRowCount(); j++) {
				DT.insertRow(childDT.getDataRow(j));
			}
		}

		List branchList = new ArrayList();

		DataTable orderDT = menuService.getMenusByIdOrParentId(orderMenu,
				orderMenu);
		DataTable nextDT = menuService.getMenusByIdOrParentId(nextMenu,
				nextMenu);

		if ("before".equalsIgnoreCase(ordertype)) {
			int i = 0;
			do {
				// drag B before A,
				// nextMenu: A, orderMenu: B
				// when loop to A, insert B
				if (DT.getString(i, "ID").equals(nextMenu)) {
					int m = 0;
					do {
						branchList.add(orderDT.getDataRow(m));

						m++;
						if (orderDT == null)
							break;
					} while (m < orderDT.getRowCount());
				} else if (DT.getString(i, "ID").equals(orderMenu)) {// when
					// loop
					// to B
					i = i - 1 + orderDT.getRowCount();
					break;
				}
				branchList.add(DT.getDataRow(i));

				i++;
				if (DT == null)
					break;
			} while (i < DT.getRowCount());
		} else if ("after".equalsIgnoreCase(ordertype)) {
			for (int i = 0; (DT != null) && (i < DT.getRowCount()); i++) {
				if (DT.getString(i, "ID").equals(orderMenu)) {
					i = i - 1 + orderDT.getRowCount();
				} else if (DT.getString(i, "ID").equals(nextMenu)) {
					for (int m = 0; (nextDT != null)
							&& (m < nextDT.getRowCount()); m++) {
						branchList.add(nextDT.getDataRow(m));
					}

					for (int j = 0; (orderDT != null)
							&& (j < orderDT.getRowCount()); j++) {
						branchList.add(orderDT.getDataRow(j));
					}

					i = i - 1 + nextDT.getRowCount();
				} else {
					branchList.add(DT.getDataRow(i));
				}
			}
		}

		for (int i = 0; i < branchList.size(); i++) {
			DataRow dr = (DataRow) branchList.get(i);
			trans.add(new QueryBuilder(
					"update zdmenu set orderflag = ? where ID = ?", i, dr
							.getString("ID")));
		}
		if (trans.commit())
			this.response.setLogInfo(1, "排序成功！");
		else
			this.response.setLogInfo(0, "排序失败！");
	}
}
