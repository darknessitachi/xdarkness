package com.xdarkness.bbs;

import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ThemeSearch extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (XString.isEmpty(User.getUserName())) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID="
					+ SiteID + "'</script>");
			return params;
		}
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowSearch")) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID="
					+ SiteID + "'</script>");
			return params;
		}

		params.put("AddUser", User.getUserName());
		params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public static void dg1DataBind(DataListAction dla) {
		String SiteID = dla.getParam("SiteID");
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowSearch")) {
			return;
		}
		String searchAddUser = dla.getParams().getString("SearchAddUser")
				.replace('*', '%');
		String searchSubject = dla.getParams().getString("SearchSubject");

		QueryBuilder qb = new QueryBuilder(
				"select * from ZCTheme where SiteID=? and Status='Y' ", SiteID);
		if (XString.isNotEmpty(searchAddUser)) {
			qb.append(" and AddUser like ?", searchAddUser);
		}
		if (XString.isNotEmpty(searchSubject)) {
			qb.append(" and Subject like ?", "%" + searchSubject + "%");
		}
		qb.append(" order by ID desc");
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx initAddDialog(Mapx params) {
		return params;
	}
}

/*
 * com.xdarkness.bbs.ThemeSearch JD-Core Version: 0.6.0
 */