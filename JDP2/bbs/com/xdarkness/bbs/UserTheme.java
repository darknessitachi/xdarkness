package com.xdarkness.bbs;

import java.util.Date;

import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class UserTheme extends Ajax {
	public static void getList(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String orderBy = dla.getParams().getString("orderby");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder(
				"select * from ZCTheme  where Status='Y' and VerifyFlag='Y' and AddUser=?",
				dla.getParam("LastPoster"));

		if ((!XString.isEmpty(addTime)) && (!"0".equals(addTime))) {
			Date date = new Date(new Date().getTime() - Long.parseLong(addTime));
			qb.append(" and addTime >?", date);
		}

		if (!XString.isEmpty(orderBy)) {
			if (!XString.checkID(orderBy)) {
				LogUtil.warn("可能的SQL注入,UserTheme.getList:" + orderBy);
				return;
			}
			qb.append(" order by " + orderBy);
			if ((!XString.isEmpty(ascdesc)) && ("DESC".equals(ascdesc)))
				qb.append(" desc ");
		} else {
			qb.append(" order by OrderTime desc");
		}

		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("AddUser", User.getUserName());
		return params;
	}
}

/*
 * com.xdarkness.bbs.UserTheme JD-Core Version: 0.6.0
 */