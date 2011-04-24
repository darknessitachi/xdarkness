package com.xdarkness.test;

import com.xdarkness.platform.service.MenuService;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.Treex;

public class TreexTest {

	@Test
	public void s(){
		DataTable dt = new MenuService().getMenusWithExpandAndTreeLevel();
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Expand", "1".equals(dt.get(i, "Type")) ? "N" : "N");
			dt.set(i, "TreeLevel", "2".equals(dt.get(i, "Type")) ? "1" : "0");
		}
		System.out.println(Treex.dataTableToTree(dt, "ID","ParentID"));
	}
}
