package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZDMemberLevelSchema;
import com.xdarkness.schema.ZDMemberLevelSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class MemberLevel extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDMemberLevel order by TreeLevel asc");
		dga.bindData(qb);
	}

	public void add() {
		ZDMemberLevelSchema ZDmemberlevel = new ZDMemberLevelSchema();
		ZDmemberlevel.setValue(this.request);
		ZDmemberlevel.setID(NoUtil.getMaxID("MemberLevelID"));
		ZDmemberlevel.setDiscount("1.0");
		ZDmemberlevel.setIsDefault("Y");
		ZDmemberlevel.setIsValidate("Y");
		ZDmemberlevel.setType("用户");
		ZDmemberlevel.setAddUser(User.getUserName());
		ZDmemberlevel.setAddTime(new Date());
		if (ZDmemberlevel.insert()) {
			this.response.setStatus(1);
			this.response.setMessage("新增会员项成功！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if ((ids.indexOf("\"") >= 0) || (ids.indexOf("'") >= 0)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZDMemberLevelSchema ZDmemberlevel = new ZDMemberLevelSchema();
		ZDMemberLevelSet set = ZDmemberlevel.query(new QueryBuilder(
				"where ID in ('" + ids + "')"));
		trans.add(set, OperateType.DELETE);
		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("删除成功！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void save() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDMemberLevelSchema MemberLevel = new ZDMemberLevelSchema();
			MemberLevel.setValue(dt.getDataRow(i));
			MemberLevel.fill();
			MemberLevel.setValue(dt.getDataRow(i));
			trans.add(MemberLevel, OperateType.UPDATE);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "保存成功!");
		else
			this.response.setLogInfo(0, "保存失败!");
	}
}

/*
 * com.xdarkness.cms.dataservice.MemberLevel JD-Core Version: 0.6.0
 */