package com.xdarkness.cms.document;

import java.sql.SQLException;

import org.apache.commons.lang.ArrayUtils;

import com.xdarkness.platform.page.UserListPage;
import com.xdarkness.schema.ZCMessageSchema;
import com.xdarkness.schema.ZCMessageSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Message extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDetailDialog(Mapx params) {
		String id = params.getString("ID");
		String Type = params.getString("Type");
		if (XString.isEmpty(id)) {
			return null;
		}
		DataTable dt = new QueryBuilder("select * from ZCMessage where ID=?",
				id).executeDataTable();
		if ((dt != null) && (dt.getRowCount() > 0)) {
			params.putAll(dt.getDataRow(0).toMapx());
			if ("history".equals(Type)) {
				params.put("UserType", "收");
				params.put("FromUser", "");
			} else {
				params.put("UserType", "发");
				params.put("ToUser", "");

				int readFlag = Integer.parseInt(dt.getDataRow(0).getString(
						"ReadFlag"));
				if (readFlag == 0) {
					try {
						new QueryBuilder(
								"update ZCMessage set ReadFlag = 1 where ID=?", id)
								.executeNoQuery();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					QueryBuilder qb = new QueryBuilder(
							"select count(1) from ZCMessage where ReadFlag=0 and ToUser=?",
							User.getUserName());
					CacheManager.set("Message", "Count", User.getUserName(), qb
							.executeInt());
				}
			}
		}
		return params;
	}

	public static Mapx initReplyDialog(Mapx params) {
		String id = params.getString("ID");
		if (XString.isEmpty(id)) {
			return null;
		}
		DataTable dt = new QueryBuilder("select * from ZCMessage where ID=?",
				id).executeDataTable();
		if ((dt != null) && (dt.getRowCount() > 0)) {
			return dt.getDataRow(0).toMapx();
		}
		return null;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ZCMessage.*,case readFlag when 1 then '已读' else '未读' end as ReadFlagStr,case readFlag when 1 then '' else 'red' end as color from ZCMessage where touser=?",
				User.getUserName());
		qb.append(dga.getSortString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("ReadFlagIcon");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String flag = dt.getString(i, "ReadFlag");
			if (!"1".equals(flag))
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a7.gif'>");
			else {
				dt
						.set(i, "ReadFlagIcon",
								"<img src='../Icons/icon037a17.gif'>");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void historyDataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ZCMessage.*,case readFlag when 1 then '已读' else '未读' end as ReadFlagStr,case readFlag when 1 then '' else 'red' end as color from ZCMessage where fromuser=?",
				User.getUserName());
		qb.append(dga.getSortString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("ReadFlagIcon");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String flag = dt.getString(i, "ReadFlag");
			if (!"1".equals(flag))
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a7.gif'>");
			else {
				dt
						.set(i, "ReadFlagIcon",
								"<img src='../Icons/icon037a17.gif'>");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void getNewMessage() {
		if (!Config.isInstalled) {
			redirect(Config.getContextPath() + "Install.jsp");
			return;
		}
		this.response.put("Count", MessageCache.getNoReadCount());
		String message = MessageCache.getFirstPopMessage();
		if (XString.isEmpty(message)) {
			this.response.put("PopFlag", "0");
		} else {
			this.response.put("Message", message);
			this.response.put("PopFlag", "1");
		}
	}

	public void updateReadFlag() {
		QueryBuilder qb = new QueryBuilder(
				"update ZCMessage set ReadFlag=1 where ID=?", $V("_Param0"));
		try {
			qb.executeNoQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String count = (String) CacheManager.get("Message", "Count", User
				.getUserName());
		CacheManager.set("Message", "Count", User.getUserName(), Integer
				.parseInt(count) - 1);
	}

	public void add() {
		String toUser = $V("ToUser");
		if (!XString.checkID(toUser)) {
			this.response.setLogInfo(0, "传入参数错误！");
			return;
		}
		String[] userList = toUser.split(",");

		String toRole = $V("ToRole");
		if (!XString.checkID(toRole)) {
			this.response.setLogInfo(0, "传入参数错误！");
			return;
		}
		String[] roleList = toRole.split(",");

		if (roleList.length > 0) {
			String roleStr = "";
			for (int j = 0; j < roleList.length; j++) {
				if (XString.isNotEmpty(roleList[j])) {
					if (j == 0)
						roleStr = roleStr + "'" + roleList[j] + "'";
					else {
						roleStr = roleStr + ",'" + roleList[j] + "'";
					}
				}
			}
			if (XString.isNotEmpty(roleStr)) {
				DataTable dt = new QueryBuilder(
						"select UserName from zduserRole where rolecode in ("
								+ roleStr + ")").executeDataTable();
				for (int k = 0; k < dt.getRowCount(); k++) {
					String userName = dt.getString(k, "UserName");
					if ((!User.getUserName().equals(userName))
							&& (!ArrayUtils.contains(userList, userName))) {
						userList = (String[]) ArrayUtils
								.add(userList, userName);
					}
				}
			}
		}

		if (MessageCache.addMessage($V("Subject"), $V("Content"), userList,
				User.getUserName()))
			this.response.setLogInfo(1, "新建成功！");
		else
			this.response.setLogInfo(0, "新建失败！");
	}

	public void reply() {
		String toUser = $V("ToUser");
		if (!XString.checkID(toUser)) {
			this.response.setLogInfo(0, "传入参数错误！");
			return;
		}
		if (MessageCache.addMessage($V("Subject"), $V("Content"), toUser))
			this.response.setLogInfo(1, "添加回复成功！");
		else
			this.response.setLogInfo(0, "添加回复失败！");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();

		ZCMessageSchema message = new ZCMessageSchema();
		ZCMessageSet set = message.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			MessageCache.removeIDs(set);
			QueryBuilder qb = new QueryBuilder(
					"select count(1) from ZCMessage where ReadFlag=0 and ToUser=?",
					User.getUserName());
			CacheManager.set("Message", "Count", User.getUserName(), qb
					.executeInt());
			this.response.setLogInfo(1, "删除成功");
		} else {
			this.response.setLogInfo(0, "删除失败");
		}
	}

	public void setReadFlag() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		ZCMessageSet set = new ZCMessageSchema().query(new QueryBuilder(
				"where ReadFlag=0 and id in (" + ids + ")"));
		QueryBuilder qb = new QueryBuilder(
				"update ZCMessage set ReadFlag=1 where id in (" + ids + ")");
		try {
			qb.executeNoQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.response.setLogInfo(1, "标记成功");
		MessageCache.removeIDs(set);
		qb = new QueryBuilder(
				"select count(1) from ZCMessage where ReadFlag=0 and ToUser=?",
				User.getUserName());
		CacheManager.set("Message", "Count", User.getUserName(), qb
				.executeInt());
	}

	public static void bindUserList(DataGridAction dga) {
		String searchUserName = dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode()
				+ "%");
		qb.append(" and UserName <> ?", User.getUserName());
		if (XString.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ?", "%" + searchUserName.trim()
					+ "%");

			qb
					.append(" or realname like ?)", "%" + searchUserName.trim()
							+ "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("Status", UserListPage.STATUS_MAP);
		dga.bindData(dt);
	}

	public static void bindRoleList(DataGridAction dga) {
		String searchRoleName = dga.getParam("SearchRoleName");
		QueryBuilder qb = new QueryBuilder("select * from ZDRole");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode()
				+ "%");
		if (XString.isNotEmpty(searchRoleName)) {
			qb.append(" and (RoleCode like ?", "%" + searchRoleName.trim()
					+ "%");

			qb
					.append(" or RoleName like ?)", "%" + searchRoleName.trim()
							+ "%");
		}
		qb.append(" order by AddTime desc");
		dga.bindData(qb);
	}
}

/*
 * com.xdarkness.cms.document.Message JD-Core Version: 0.6.0
 */