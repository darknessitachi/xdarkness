package com.xdarkness.cms.document;

import java.util.List;

import com.xdarkness.cms.pub.CMSCache;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.workflow.WorkflowUtil;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class WorkList extends Page {
	private WorkList instance = this;

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = null;
		DataTable dt = null;
		String listType = dga.getParam("Type");
		if (("admin".equals(User.getUserName()))
				&& (XString.isEmpty(listType))) {
			listType = "ALL";
		}
		if ((XString.isEmpty(listType)) || ("TOME".equals(listType))) {
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName, a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.adduser,a.addtime,a.status, b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName from ZCArticle a,ZWStep b,ZWInstance c where a.ID=c.DataID and b.InstanceID=c.ID and a.siteID=? and a.status = 10 and (b.State=? or (b.State=? and Owner=?))",
					ApplicationPage.getCurrentSiteID());
			qb.add("Unread");
			qb.add("Underway");
			qb.add(User.getUserName());

			String keyword = dga.getParam("Keyword");
			if (XString.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executeDataTable();

			List roleList = PubFun.getRoleCodesByUserName(User.getUserName());
			dt = dt.filter(new Filter(roleList) {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					if ((!Priv.getPriv(User.getUserName(), "article", dr
							.getString("cataloginnercode"), "article_browse"))
							|| (!Priv.getPriv(User.getUserName(), "article", dr
									.getString("cataloginnercode"),
									"article_audit"))) {
						return false;
					}
					String state = dr.getString("State");
					String allowUser = "," + dr.getString("AllowUser") + ",";
					String allowRole = "," + dr.getString("AllowRole") + ",";
					String allowOrgan = "," + dr.getString("AllowOrgan") + ",";
					if ("Unread".equals(state)) {
						dr.set("ActionName", WorkflowUtil.getActionNodeName(dr
								.getLong("workflowconfigid"), dr
								.getInt("ActionID")));
						dr.set("StepName", WorkflowUtil.getStepName(dr
								.getLong("workflowconfigid"), dr
								.getInt("nodeid")));
						dr.set("StateName", "未读");
						if (allowUser.indexOf("," + User.getUserName() + ",") >= 0) {
							return true;
						}
						if ((XString.isNotEmpty(dr.getString("AllowOrgan")))
								&& (allowOrgan.indexOf(","
										+ User.getBranchInnerCode() + ",") != 0)) {
							return false;
						}
						String[] roles = allowRole.split(",");
						for (int i = 0; i < roles.length; i++) {
							// if (instance.contains(roles[i])) {
							// return true;
							// }
						}
						return false;
					}
					if (dr.getInt("ActionID") != 0)
						dr.set("ActionName", WorkflowUtil.getActionNodeName(dr
								.getLong("workflowconfigid"), dr
								.getInt("ActionID")));
					else {
						dr.set("ActionName", "新建");
					}
					dr.set("StepName", WorkflowUtil.getStepName(dr
							.getLong("workflowconfigid"), dr.getInt("nodeid")));
					dr.set("StateName", "处理中");

					return true;
				}
			});
			DataTable newdt = new DataTable(dt.getDataColumns(), null);
			for (int i = dga.getPageIndex() * dga.getPageSize(); (i < dt
					.getRowCount())
					&& (i < (dga.getPageIndex() + 1) * dga.getPageSize());) {
				newdt.insertRow(dt.getDataRow(i));

				i++;
			}

			newdt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(dt.getRowCount());
			dga.bindData(newdt);
		} else if ("ALL".equals(listType)) {
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName, a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.adduser,a.addtime,a.status, b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName from ZCArticle a,ZWStep b,ZWInstance c where a.ID=c.DataID and b.InstanceID=c.ID and a.siteID=? and a.status = 10 and b.State in('Unread','Underway')",
					ApplicationPage.getCurrentSiteID());

			String keyword = dga.getParam("Keyword");
			if (XString.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb
					.executePagedDataTable(dga.getPageSize(), dga
							.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				if (dr.getInt("ActionID") != 0)
					dr.set("ActionName", WorkflowUtil
							.getActionNodeName(dr.getLong("workflowconfigid"),
									dr.getInt("ActionID")));
				else {
					dr.set("ActionName", "新建");
				}
				dr.set("StepName", WorkflowUtil.getStepName(dr
						.getLong("workflowconfigid"), dr.getInt("nodeid")));
				String state = dr.getString("State");
				if ("Unread".equals(state))
					dr.set("StateName", "未读");
				else {
					dr.set("StateName", "处理中");
				}
				if (!dr.getString("State").equals("Unread"))
					continue;
				String allowUser = dr.getString("AllowUser");
				String allowOrgan = dr.getString("AllowOrgan");
				String allowRole = dr.getString("AllowRole");
				if ((!XString.isEmpty(allowRole))
						|| (!XString.isEmpty(allowOrgan))
						|| (!XString.isNotEmpty(allowUser))
						|| (allowUser.indexOf(",") >= 0))
					continue;
				dr.set("Owner", allowUser);
			}

			dga.bindData(dt);
		} else if ("HANDLED".equals(listType)) {
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName, a.id,a.catalogid,a.title,a.workflowid,a.adduser,a.addtime,a.status, (select workflowid from zwinstance  where zwinstance.id = a.workflowid) as workflowconfigid from ZCArticle a where exists (select '' from zwstep s where s.instanceid=a.workflowid and s.owner=?) and a.siteID=? and a.status<>10",
					User.getUserName(), ApplicationPage.getCurrentSiteID());

			String keyword = dga.getParam("Keyword");
			if (XString.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb
					.executePagedDataTable(dga.getPageSize(), dga
							.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			dga.bindData(dt);
		}
	}

	public void applyStep() {
		DataTable dt = this.request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				String id = dt.getString(i, "ID");
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(id);
				if (!article.fill()) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误：未找到对应的文章，ID“" + id);
					return;
				}
				ZCCatalogConfigSchema config = CMSCache
						.getCatalogConfig(article.getCatalogID());
				if (("Y".equals(config.getBranchManageFlag()))
						&& (!"admin".equals(User.getUserName()))) {
					String branchInnerCode = article.getBranchInnerCode();
					if ((XString.isNotEmpty(branchInnerCode))
							&& (!User.getBranchInnerCode().equals(
									branchInnerCode))) {
						this.response.setStatus(0);
						this.response.setMessage("发生错误：您没有操作文档“"
								+ article.getTitle() + "”的权限！");
						return;
					}
				}

				WorkflowUtil.applyStep(dt.getLong(i, "WorkflowID"), dt.getInt(
						i, "NodeID"));
			} catch (Exception e) {
				e.printStackTrace();
				failCount++;
				sb.append(failCount + "、" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0)
			this.response.setMessage("申请成功");
		else
			this.response.setMessage(dt.getRowCount() - failCount + "个申请成功，"
					+ failCount + "个申请失败!<br><br>" + sb);
	}

	public void forceEnd() {
		DataTable dt = this.request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				long flowID = dt.getLong(i, "WorkflowID");
				if (WorkflowUtil.findWorkflow(flowID) == null) {
					new QueryBuilder(
							"update ZCArticle set Status=? where ID=?", 20, dt
									.getString(i, "ID")).executeNoQuery();
					sb.append("第" + (i + 1) + "行、未找到工作流定义，己将文件状态转为待发布<br>");
				}
				WorkflowUtil.forceEnd(flowID, dt.getInt(i, "NodeID"));
			} catch (Exception e) {
				e.printStackTrace();
				failCount++;
				sb.append("第" + (i + 1) + "行、" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0)
			this.response.setMessage("强制结束流程成功<br><br>" + sb);
		else
			this.response.setMessage(dt.getRowCount() - failCount + "个成功，"
					+ failCount + "个失败!<br><br>" + sb);
	}
}

/*
 * com.xdarkness.cms.document.WorkList JD-Core Version: 0.6.0
 */