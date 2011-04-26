package com.xdarkness.bbs;

import java.util.Date;

import com.xdarkness.schema.ZCPostSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class MyPost extends Ajax {
	public static void getMyPost(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder(
				"select p.*, t.Subject TSubject, f.Name from ZCPost p, ZCTheme t, ZCForum f where p.SiteID=? and p.ThemeID=t.ID and p.Invisible='Y' and t.ForumID=f.ID and p.first='N' and p.AddUser=? and t.status='Y' and t.VerifyFlag='Y' ",
				User.getUserName(), dla.getParam("SiteID"));

		ForumPriv priv = new ForumPriv(dla.getParam("SiteID"));
		if (!priv.hasPriv("AllowPanel")) {
			return;
		}
		if ((!XString.isEmpty(addTime)) && (!"0".equals(addTime))) {
			Date date = new Date(new Date().getTime() - Long.parseLong(addTime));
			qb.append(" and p.addTime >?", date);
		}

		qb.append(" order by p.AddTime");

		if (!XString.isEmpty(ascdesc)) {
			if ("DESC".equals(ascdesc))
				qb.append(" desc ");
		} else
			qb.append(" desc");

		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dt.insertColumn("AuditStatus");
		dt.insertColumn("Operation");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "AuditStatus", "正常");
				if (dt.get(i, "ApplyDel") == null)
					dt.set(i, "Operation", "<a href='javascript:applyDel("
							+ dt.get(i, "ID") + ")'>申请删除</a>");
				else
					dt.set(i, "Operation", "已申请删除");
			} else {
				dt.set(i, "AuditStatus", "待审核");
				dt.set(i, "Operation", "<cite><a href='javascript:editPost("
						+ dt.get(i, "ID") + "," + dt.get(i, "ForumID") + ","
						+ dt.get(i, "ThemeID")
						+ ")'>修改</a></cite> <em><a href='javascript:del("
						+ dt.get(i, "ID") + ")' >删除</a></em>");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("AddUser", User.getUserName());
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void del() {
		String ID = $V("ID");
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		post.setID(ID);
		post.fill();
		post.setInvisible("N");
		trans.add(post, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败!");
	}
}

/*
 * com.xdarkness.bbs.MyPost JD-Core Version: 0.6.0
 */