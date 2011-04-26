package com.xdarkness.platform.page;

import java.util.Date;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.User;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.XString;
import com.xdarkness.schema.ZDConfigSchema;
import com.xdarkness.schema.ZDConfigSet;

public class ConfigSysPage extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String SearchType = (String) dga.getParams().get("SearchType");
		QueryBuilder qb = new QueryBuilder(
				"select type,name,value,type as type_key from zdconfig where type not like ? ");
		qb.add("System.");
		if (XString.isNotEmpty(SearchType)) {
			qb.append(" and (type like ? or name like ?)");
			qb.add("%" + SearchType + "%");
			qb.add("%" + SearchType + "%");
		}
		dga.bindData(qb);
	}

	public void add() {
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		zdconfig.setValue(this.request);
		zdconfig.setAddTime(new Date());
		zdconfig.setAddUser(User.getUserName());
		if (zdconfig.getType().startsWith("System.")) {
			this.response.setStatus(0);
			this.response.setMessage("不允许添加以“System.”开头的配置项！");
			return;
		}
		if (zdconfig.insert()) {
			Config.update();
			this.response.setStatus(1);
			this.response.setMessage("新增类别成功！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		ZDConfigSet set = zdconfig.query(new QueryBuilder("where type in ('"
				+ ids + "')"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		StringBuffer configLog = new StringBuffer("删除配置项:");
		if (set.size() > 0) {
			configLog.append(set.get(0).getName() + " 等");
		}
		if (trans.commit()) {
			Config.update();
			UserLogPage.log("System", "DelConfig", configLog + "成功", this.request
					.getClientIP());
			this.response.setStatus(1);
			this.response.setMessage("删除成功！");
		} else {
			UserLogPage.log("System", "DelConfig", configLog + "失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder(
					"update ZDConfig set type=?,name=?,value=? where type=?");
			qb.add(dt.getString(i, "type"));
			qb.add(dt.getString(i, "name"));
			qb.add(dt.getString(i, "value"));
			qb.add(dt.getString(i, "type_key"));
			trans.add(qb);
		}
		if (trans.commit()) {
			Config.update();
			this.response.setStatus(1);
			this.response.setMessage("修改成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("修改失败!");
		}
	}
}

/*
 * com.xdarkness.platform.ConfigSys JD-Core Version: 0.6.0
 */