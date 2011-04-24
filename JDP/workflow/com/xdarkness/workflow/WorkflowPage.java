package com.xdarkness.workflow;

import java.util.Date;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZWWorkflowSchema;
import com.xdarkness.schema.ZWWorkflowSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class WorkflowPage extends Page {
	public static Mapx init(Mapx params) {
		String id = params.getString("ID");
		if (XString.isNotEmpty(id)) {
			ZWWorkflowSchema fc = new ZWWorkflowSchema();
			fc.setID(id);
			fc.fill();
			params.put("Name", fc.getName());
			params.put("ID", fc.getID());
			params.put("Memo", fc.getMemo());
			params.put("XML", XString.javaEncode(fc.getConfigXML()));
		}

		return params;
	}

	public static void roleDataBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder("select RoleCode,RoleName from ZDRole")
				.executeDataTable();
		dga.bindData(dt);
	}

	public void save() {
		ZWWorkflowSchema wd = new ZWWorkflowSchema();
		if (XString.isNotEmpty($V("ID"))) {
			wd.setID($V("ID"));
			wd.fill();
		} else {
			wd.setID(NoUtil.getMaxID("WorkflowID"));
			wd.setAddTime(new Date());
			wd.setAddUser(User.getUserName());
		}
		wd.setModifyTime(new Date());
		wd.setModifyUser(User.getUserName());
		wd.setName($V("Name"));
		wd.setConfigXML(XString.htmlDecode($V("XML")));
		wd.setMemo($V("Memo"));
		this.response.put("ID", wd.getID());
		boolean flag = true;
		if (XString.isNotEmpty($V("ID")))
			flag = wd.update();
		else {
			flag = wd.insert();
		}
		if (flag) {
			WorkflowUtil.updateCache(wd);
			this.response.setMessage("保存成功!");
		} else {
			this.response.setError("保存数据到数据库时发生错误!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = " select * from ZWWorkflow";
		dga.bindData(new QueryBuilder(sql));
	}

	public void del() {
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			this.response.setLogInfo(0, "传入工作流发生错误!");
			return;
		}
		ZWWorkflowSchema wf = new ZWWorkflowSchema();
		ZWWorkflowSet set = wf.query(new QueryBuilder("where ID in (" + IDs
				+ ")"));
		Transaction tran = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			WorkflowUtil.findAdapter().onWorkflowDelete(tran,
					set.get(i).getID());
		}
		tran.add(new QueryBuilder(
				"delete from ZWInstance where WorkflowID in (" + IDs + ")"));
		tran.add(new QueryBuilder("delete from ZWStep where WorkflowID in ("
				+ IDs + ")"));
		tran.add(set, OperateType.DELETE_AND_BACKUP);
		if (tran.commit()) {
			for (int i = 0; i < set.size(); i++) {
				WorkflowUtil.deleteCache(set.get(i));
			}
			this.response.setLogInfo(1, "删除成功！");
		} else {
			this.response.setLogInfo(0, "删除失败！");
		}
	}
}

/*
 * com.xdarkness.workflow.WorkflowPage JD-Core Version: 0.6.0
 */