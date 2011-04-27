package com.xdarkness.cms.document;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.pub.CMSCache;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.site.Catalog;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.BZCArticleSchema;
import com.xdarkness.schema.BZCArticleSet;
import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.schema.ZCArticleLogSet;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.schema.ZCAttachmentRelaSchema;
import com.xdarkness.schema.ZCAttachmentRelaSet;
import com.xdarkness.schema.ZCAudioRelaSchema;
import com.xdarkness.schema.ZCAudioRelaSet;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.schema.ZCCommentSchema;
import com.xdarkness.schema.ZCCommentSet;
import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.schema.ZCImageRelaSet;
import com.xdarkness.schema.ZCVideoRelaSchema;
import com.xdarkness.schema.ZCVideoRelaSet;
import com.xdarkness.schema.ZCVoteItemSchema;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZDColumnValueSet;
import com.xdarkness.workflow.Workflow;
import com.xdarkness.workflow.WorkflowAction;
import com.xdarkness.workflow.WorkflowUtil;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.SchemaUtil;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.StringFormat;

public class ArticleList extends Page {
	public static void magazineListDataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (XString.isEmpty(catalogID)) {
			catalogID = dga.getParams().getString(
					"Cookie.DocList.LastMagazineCatalog");
			if ((XString.isEmpty(catalogID)) || ("null".equals(catalogID))) {
				catalogID = "0";
			}
			dga.getParams().put("CatalogID", catalogID);
		}
		dg1DataBind(dga);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (XString.isEmpty(catalogID)) {
			catalogID = dga.getParams().getString("Cookie.DocList.LastCatalog");
			if ((XString.isEmpty(catalogID)) || ("null".equals(catalogID))) {
				catalogID = "0";
			}
			dga.getParams().put("CatalogID", catalogID);
		}

		if ((!catalogID.equals("0"))
				&& (!(ApplicationPage.getCurrentSiteID() + "").equals(CatalogUtil
						.getSiteID(catalogID)))) {
			catalogID = "0";
			dga.getParams().put("CatalogID", catalogID);
		}

		if (!Priv.getPriv(User.getUserName(), "article", CatalogUtil
				.getInnerCode(catalogID), "article_browse")) {
			dga.bindData(new DataTable());
			return;
		}

		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		if (XString.isEmpty(listType)) {
			listType = "ALL";
		}
		String Table = "";
		if ("ARCHIVE".equals(listType))
			Table = "BZCArticle";
		else {
			Table = "ZCArticle";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from "
						+ Table + " where CatalogID=?");
		qb.add(catalogID);
		if (XString.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (XString.isNotEmpty(startDate)) {
			startDate = startDate + " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (XString.isNotEmpty(endDate)) {
			endDate = endDate + " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if ("ADD".equals(listType))
			qb.append(" and adduser=?", User.getUserName());
		else if ("WORKFLOW".equals(listType))
			qb.append(" and status=?", 10);
		else if ("TOPUBLISH".equals(listType))
			qb.append(" and status=?", 20);
		else if ("PUBLISHED".equals(listType))
			qb.append(" and status=?", 30);
		else if ("OFFLINE".equals(listType))
			qb.append(" and status=?", 40);
		else if ("ARCHIVE".equals(listType)) {
			qb.append(" and BackUpMemo='Archive'");
		}
		qb.append(dga.getSortString());

		if (XString.isNotEmpty(dga.getSortString()))
			qb.append(" ,orderflag desc");
		else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		if ((dt != null) && (dt.getRowCount() > 0)) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}
		setDetailWorkflowStatus(dt);

		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		dt.insertColumn("Icon");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (XString.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1)
							attributeName = attributeName
									+ attributemap.getString(array[j]) + ",";
						else {
							attributeName = attributeName
									+ attributemap.getString(array[j]);
						}
					}
					dt.set(i, "Title", XString.htmlEncode(dt.getString(i,
							"Title"))
							+ " <font class='lightred'> ["
							+ attributeName
							+ "]</font>");
				}

				StringBuffer icons = new StringBuffer();

				String topFlag = dt.getString(i, "TopFlag");
				if ("1".equals(topFlag)) {
					String topdate = "永久置顶";
					if (XString.isNotEmpty(dt.getString(i, "TopDate"))) {
						topdate = DateUtil.toString(
								(Date) dt.get(i, "TopDate"),
								"yyyy-MM-dd HH:mm:ss");
					}
					icons
							.append("<img src='../Icons/icon13_stick.gif' title='有效期限: "
									+ topdate + "'/>");
				}

				if (XString.isNotEmpty(dt.getString(i, "ReferSourceID"))) {
					int referType = dt.getInt(i, "ReferType");
					if (referType == 1)
						icons
								.append("<img src='../Icons/icon13_copy.gif' title='复制'/>");
					else if (referType == 2) {
						icons
								.append("<img src='../Icons/icon13_refer.gif' title='引用'/>");
					}
				}

				if (XString.isNotEmpty(dt.getString(i, "ReferTarget"))) {
					icons
							.append("<img src='../Icons/icon13_copyout.gif' title='复制源'/>");
				}

				dt.set(i, "Icon", icons.toString());
			}
		}
		dga.bindData(dt);
	}

	private static void setDetailWorkflowStatus(DataTable dt) {
		Mapx instanceIDMap = new Mapx();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (10 == dt.getInt(i, "Status")) {
				instanceIDMap.put(dt.getString(i, "WorkflowID"), "1");
			}
		}
		String ids = XString.join(instanceIDMap.keyArray());
		if ((!XString.checkID(ids)) || (instanceIDMap.size() == 0)) {
			return;
		}
		QueryBuilder qb = new QueryBuilder(
				"select WorkflowID,NodeID,InstanceID,ActionID,State from ZWStep where (State=? or State=?) and InstanceID in ("
						+ ids + ") order by ID asc");
		qb.add("Unread");
		qb.add("Underway");
		DataTable stepTable = qb.executeDataTable();
		Mapx instanceNodeMap = new Mapx();
		Mapx actionMap = new Mapx();
		Mapx stateMap = new Mapx();

		for (int i = 0; i < stepTable.getRowCount(); i++) {
			int flowID = stepTable.getInt(i, "WorkflowID");
			int nodeID = stepTable.getInt(i, "NodeID");
			Workflow.Node node = WorkflowUtil.findWorkflow(flowID).findNode(
					nodeID);
			instanceNodeMap.put(stepTable.getString(i, "InstanceID"), node);

			actionMap.put(stepTable.getString(i, "InstanceID"), stepTable
					.getString(i, "ActionID"));
			stateMap.put(stepTable.getString(i, "InstanceID"), stepTable
					.getString(i, "State"));
		}

		for (int i = 0; i < dt.getRowCount(); i++)
			if (10 == dt.getInt(i, "Status")) {
				String instanceID = dt.getString(i, "WorkflowID");
				if (instanceNodeMap.containsKey(instanceID)) {
					Workflow.Node node = (Workflow.Node) instanceNodeMap
							.get(instanceID);
					String nodeName = node.getName();
					String nodeType = node.getType();
					dt.set(i, "StatusName", nodeName);
					if ("StartNode".equals(nodeType)) {
						WorkflowAction action = WorkflowUtil.findAction(node
								.getWorkflow().getID(), actionMap
								.getInt(instanceID));
						if (action != null)
							dt.set(i, "StatusName", action.getName());
					} else if ("Unread".equals(stateMap.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-未读");
					} else if ("Underway"
							.equals(stateMap.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-处理中");
					}
				}
			}
	}

	public static void dialogDg1DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (XString.isEmpty(catalogID)) {
			catalogID = "0";
		}
		String keyword = (String) dga.getParams().get("Keyword");
		QueryBuilder qb = new QueryBuilder(
				"select ID,Title,author,publishDate,Addtime,catalogID,topflag,SiteID from ZCArticle where catalogid=?",
				catalogID);
		if (XString.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		qb.append(dga.getSortString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		int size = dt.getRowCount();
		String[] columnValue = new String[dt.getRowCount()];
		for (int i = 0; i < size; i++) {
			columnValue[i] = PubFun.getDocURL(dt.get(i));
		}

		dt.insertColumn("Link", columnValue);
		dga.bindData(dt);
	}

	public static void treeDataBind(TreeAction ta) {
		Catalog.treeDataBind(ta);
	}

	public static Mapx init(Mapx params) {
		String catalogID = (String) params.get("CatalogID");
		if (catalogID == null) {
			return params;
		}
		DataTable dtCatalog = new QueryBuilder(
				"select siteid from zccatalog where id=?", catalogID)
				.executeDataTable();
		long siteID = ((Long) dtCatalog.get(0, "siteid")).longValue();
		params.put("SiteID", siteID);
		params.put("ListType", (String) params.get("Type"));
		return params;
	}

	public void add() {
	}

	public void up() {
		String ids = $V("ArticleIDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Article", "UpArticle", "文章上线失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "UP", "上线处理");
		trans
				.add(new QueryBuilder(
						"update zcarticle set Status =30,PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where status = 40 and id in("
								+ ids + ")", new Date()));
		DataTable dt = new QueryBuilder(
				"select Title from ZCArticle where status = 40 and id in ("
						+ ids + ")").executeDataTable();
		if (trans.commit()) {
			upTask(ids);
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLogPage.log("Article", "UpArticle", logs + "上线成功", this.request
					.getClientIP());
			this.response.setStatus(1);
		} else {
			dt = new QueryBuilder("select Title from ZCArticle where id in ("
					+ ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLogPage.log("Article", "UpArticle", logs + "上线失败", this.request
					.getClientIP());

			this.response.setStatus(0);
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				ZCArticleSchema site = new ZCArticleSchema();
				ZCArticleSet set = site.query(new QueryBuilder(
						"where status = 30 and id in (" + ids + ")"));
				if ((set != null) && (set.size() > 0)) {
					try {
						p.publishArticle(set, false, this);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					p.publishCatalog(set.get(0).getCatalogID(), false, false);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void dealArticleHistory(String ids, Transaction trans,
			String dealName, String dealDetail) {
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		String[] idarr = ids.split(",");
		for (int i = 0; i < idarr.length; i++) {
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(idarr[i]);
			articleLog.setAction(dealName);
			articleLog.setActionDetail(dealDetail);
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			trans.add((ZCArticleLogSchema) articleLog.clone(),
					OperateType.INSERT);
		}
	}

	public void down() {
		String ids = $V("ArticleIDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Article", "DownArticle", "文章下线失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();

		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids
				+ ")"));
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);

			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article
					.getCatalogID());
			if (("Y".equals(config.getBranchManageFlag()))
					&& (!"admin".equals(User.getUserName()))) {
				String branchInnerCode = article.getBranchInnerCode();
				if ((XString.isNotEmpty(branchInnerCode))
						&& (!User.getBranchInnerCode().equals(branchInnerCode))) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误：您没有操作文档“"
							+ article.getTitle() + "”的权限！");
					return;
				}
			}
		}

		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "DOWN", "下线处理");
		trans
				.add(new QueryBuilder(
						"update zcarticle set Status = 40,TopFlag='0',DownlineDate = ?,modifyTime=? where status = 30 and id in("
								+ ids + ")", now, now));
		if (trans.commit()) {
			DataTable dt = new QueryBuilder(
					"select Title from ZCArticle where id in (" + ids + ")")
					.executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLogPage.log("Article", "DownArticle", logs + "下线成功", this.request
					.getClientIP());

			ZCArticleSchema site = new ZCArticleSchema();
			set = site.query(new QueryBuilder("where status = 40 and id in ("
					+ ids + ")"));
			downTask(set);

			this.response.setStatus(1);
		} else {
			DataTable dt = new QueryBuilder(
					"select Title from ZCArticle where id in (" + ids + ")")
					.executeDataTable();
			StringBuffer logs = new StringBuffer("文章:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" 等，共" + dt.getRowCount() + "篇");
				}
			}
			UserLogPage.log("Article", "DownArticle", logs + "下线失败", this.request
					.getClientIP());

			this.response.setStatus(0);
		}
	}

	private long downTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				if ((set != null) && (set.size() > 0)) {
					p.deletePubishedFile(set);

					Mapx catalogMap = new Mapx();
					for (int k = 0; k < set.size(); k++) {
						catalogMap.put(set.get(k).getCatalogID(), set.get(k)
								.getCatalogID()
								+ "");
						String pid = CatalogUtil.getParentID(set.get(k)
								.getCatalogID());
						while ((XString.isNotEmpty(pid))
								&& (!"null".equals(pid)) && (!"0".equals(pid))) {
							catalogMap.put(pid, pid);
							pid = CatalogUtil.getParentID(pid);
						}

					}

					Object[] vs = catalogMap.valueArray();
					for (int j = 0; j < catalogMap.size(); j++) {
						String listpage = CatalogUtil.getData(vs[j].toString())
								.getString("ListPage");
						if ((XString.isEmpty(listpage))
								|| ("0".equals(listpage))
								|| ("-1".equals(listpage))) {
							listpage = "20";
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()),
								false, false, Integer.parseInt(listpage));
						setPercent(getPercent() + 5);
						setCurrentInfo("发布栏目页面");
					}
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	private boolean checkArticleStatus(ZCArticleSet set,
			String allowArticleStatus) {
		DataTable dt = set.toDataTable();
		dt.insertColumn("ActionID");
		Mapx map = new Mapx();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (10 == dt.getInt(i, "Status")) {
				map.put(dt.getString(i, "WorkflowID"), "1");
			}
		}

		String ids = XString.join(map.keyArray());
		if ((!XString.checkID(ids)) || (map.size() == 0)) {
			for (int i = 0; i < dt.getRowCount(); i++)
				dt.set(i, "ActionID", "0");
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select InstanceID,ActionID from ZWStep where InstanceID in ("
							+ ids + ") order by ID asc");
			DataTable stepTable = qb.executeDataTable();
			Mapx stepMap = stepTable.toMapx(0, 1);
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (10 == dt.getInt(i, "Status")) {
					String id = dt.getString(i, "WorkflowID");
					if (stepMap.containsKey(id))
						dt.set(i, "ActionID", stepMap.get(id));
					else
						dt.set(i, "ActionID", "0");
				} else {
					dt.set(i, "ActionID", "0");
				}
			}
		}

		if (!allowArticleStatus.startsWith(",")) {
			allowArticleStatus = "," + allowArticleStatus;
		}

		if (!allowArticleStatus.endsWith(",")) {
			allowArticleStatus = allowArticleStatus + ",";
		}

		for (int i = 0; (dt != null) && (i < dt.getRowCount()); i++) {
			if (!checkArticleStatus(dt.get(i), allowArticleStatus)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkArticleStatus(DataRow dr, String notDeleteArticleStatus) {
		if ((XString.isNotEmpty(notDeleteArticleStatus))
				&& (notDeleteArticleStatus.indexOf("," + dr.getString("Status")
						+ ",") != -1)) {
			return (dr.getInt("Status") == 10)
					&& ("0".equals(dr.getString("ActionID")));
		}

		return true;
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Article", "DelArticle", "删除文章失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article
				.query(new QueryBuilder(
						"where id in ("
								+ ids
								+ ") or id in (select id from zcarticle where refersourceid in ("
								+ ids + ") )"));

		String notDeleteArticleStatus = "30,10,20";

		if (!checkArticleStatus(set, notDeleteArticleStatus)) {
			UserLogPage.log("Article", "DelArticle", "已发布的文档或流转中的文档不能删除,请下线后再删除",
					this.request.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("已发布的文档或流转中的文档不能删除,请下线后再删除!");
			return;
		}

		trans.add(set, OperateType.DELETE_AND_BACKUP);
		Mapx catalogMap = new Mapx();
		StringBuffer logs = new StringBuffer("删除文章:");

		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);

			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article
					.getCatalogID());
			if (("Y".equals(config.getBranchManageFlag()))
					&& (!"admin".equals(User.getUserName()))) {
				String branchInnerCode = article.getBranchInnerCode();
				if ((XString.isNotEmpty(branchInnerCode))
						&& (!User.getBranchInnerCode().equals(branchInnerCode))) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误：您没有操作文档“"
							+ article.getTitle() + "”的权限！");
					return;
				}
			}

			String sqlArticleCount = "update zccatalog set total = total-1,isdirty=1 where innercode in("
					+ CatalogUtil.getParentCatalogCode(article
							.getCatalogInnerCode()) + ")";
			trans.add(new QueryBuilder(sqlArticleCount));

			StringFormat sf = new StringFormat("标题为 ? 的文档己被删除");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			String subject = sf.toString();

			sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 删除。");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			sf.add("<font class='red'>" + DateUtil.getCurrentDateTime()
					+ "</font>");
			sf.add("<font class='red'>" + User.getUserName() + "</font>");

			MessageCache.addMessage(trans, subject, sf.toString(),
					new String[] { article.getAddUser() }, "SYSTEM", false);

			ZDColumnValueSchema colValue = new ZDColumnValueSchema();
			colValue.setRelaID(article.getID() + "");
			colValue.setRelaType("2");
			ZDColumnValueSet colValueSet = colValue.query();
			trans.add(colValueSet, OperateType.DELETE_AND_BACKUP);

			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setRelaID(article.getID());
			imageRela.setRelaType("ArticleImage");
			ZCImageRelaSet imageRelaSet = imageRela.query();
			trans.add(imageRelaSet, OperateType.DELETE_AND_BACKUP);

			ZCVideoRelaSchema videoRela = new ZCVideoRelaSchema();
			videoRela.setRelaID(article.getID());
			videoRela.setRelaType("ArticleVideo");
			ZCVideoRelaSet videoRelaSet = videoRela.query();
			trans.add(videoRelaSet, OperateType.DELETE_AND_BACKUP);

			ZCAttachmentRelaSchema attachmentRela = new ZCAttachmentRelaSchema();
			attachmentRela.setRelaID(article.getID());
			attachmentRela.setRelaType("ArticleAttach");
			ZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query();
			trans.add(attachmentRelaSet, OperateType.DELETE_AND_BACKUP);
			ZCAudioRelaSchema audioRela = new ZCAudioRelaSchema();
			audioRela.setRelaID(article.getID());
			ZCAudioRelaSet audioRelaSet = audioRela.query();
			trans.add(audioRelaSet, OperateType.DELETE_AND_BACKUP);

			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setRelaID(article.getID());
			ZCCommentSet commentSet = comment.query();
			trans.add(commentSet, OperateType.DELETE_AND_BACKUP);

			ZCVoteItemSchema voteitem = new ZCVoteItemSchema();
			voteitem.setVoteDocID(article.getID());
			trans.add(voteitem.query(), OperateType.DELETE_AND_BACKUP);

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			ZCArticleLogSet artilceLogSet = articleLog.query();

			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("DELETE");
			articleLog.setActionDetail("删除。删除原因：" + $V("DeleteReason"));
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			artilceLogSet.add(articleLog);
			trans.add(artilceLogSet, OperateType.DELETE_AND_BACKUP);

			catalogMap.put(article.getCatalogID(), article
					.getCatalogInnerCode());

			if (article.getWorkFlowID() != 0L) {
				WorkflowUtil.deleteInstance(trans, article.getWorkFlowID());
			}
		}

		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		if (trans.commit()) {
			downTask(set);
			UserLogPage.log("Article", "DelArticle", logs + "成功", this.request
					.getClientIP());
			this.response.setStatus(1);
		} else {
			UserLogPage.log("Article", "DelArticle", logs + "失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void topublish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Article", "ToPublishArticle", "转为待发布操作失败,ids:" + ids,
					this.request.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入IDs参数错误!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article
				.query(new QueryBuilder(
						"where id in("
								+ ids
								+ ") or id in(select id from zcarticle where refersourceid in ("
								+ ids + ") )"));
		String log = "转为待发布操作成功";
		ZCArticleSet updateset = new ZCArticleSet();
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if (((article.getStatus() == 0L) || (article.getStatus() == 60L))
					&& (article.getWorkFlowID() == 0L)) {
				article.setStatus(20L);
				updateset.add(article);
			} else if (article.getWorkFlowID() != 0L) {
				log = "此文档在工作流转中，不能转为待发布";
			} else {
				log = "只有‘初稿’和‘重新编辑’的文章转为待发布状态了";
			}
		}
		updateset.update();
		UserLogPage.log("Article", "ToPublishArticle", "转为待发布操作成功,ids:" + ids,
				this.request.getClientIP());
		this.response.setLogInfo(1, log);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Article", "PublishArticle", "文章发布失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入IDs参数错误!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(" where id in ("
				+ ids + ")"));

		ZCArticleSet referset = article.query(new QueryBuilder(
				" where refersourceid in (" + ids + ")"));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), "article",
						catalogInnerCode, "article_manage");
				String workflow = CatalogUtil.getWorkflow(referset.get(i)
						.getCatalogID());

				if ((hasPriv) && (XString.isEmpty(workflow))) {
					set.add(referset.get(i));
				}
			}
		}
		StringBuffer logs = new StringBuffer("发布文章: ");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		UserLogPage.log("Article", "PublishArticle", logs + "成功", this.request
				.getClientIP());

		this.response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", id);
	}

	public void changeToPublish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入IDs参数错误!");
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		BZCArticleSet bset = barticle.query(new QueryBuilder(" where id in ("
				+ ids + ") and backupmemo='Archive' "));
		Transaction trans = new Transaction();
		for (int i = 0; i < bset.size(); i++) {
			barticle = bset.get(i);
			ZCArticleSchema article = new ZCArticleSchema();
			SchemaUtil.copyFieldValue(barticle, article);
			article.setStatus(30L);
			article.setArchiveDate(CatalogUtil.getArchiveTime(barticle
					.getCatalogID()));
			trans.add(article, OperateType.INSERT);
			trans.add(barticle, OperateType.DELETE);
		}
		if (trans.commit()) {
			StringBuffer logs = new StringBuffer("从归档文章转为已发布文章: ");
			if (bset.size() > 0) {
				logs.append(bset.get(0).getTitle());
				if (bset.size() > 1) {
					logs.append(" 等，共" + bset.size() + "篇");
				}
			}
			UserLogPage.log("Article", "PublishArticle", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "转为已发布成功");
		} else {
			this.response.setLogInfo(0, "转为已发布失败");
		}
	}

	private long publishSetTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				setPercent(5);
				try {
					p.publishArticle(set, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	private long publishTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				if ((set != null) && (set.size() > 0)) {
					p.publishCatalog(set.get(0).getCatalogID(), false, true);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public void move() {
		String articleIDs = $V("ArticleIDs");
		if (!XString.checkID(articleIDs)) {
			this.response.setError("操作数据库时发生错误!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!XString.checkID(catalogID)) {
			this.response.setError("传入CatalogID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema srcArticle = new ZCArticleSchema();
		ZCArticleSet set = srcArticle.query(new QueryBuilder("where id in ("
				+ articleIDs + ")"));
		long srcCatalogID = 0L;

		String[] srcArticleIDs = (String[]) null;
		if (set.size() > 0) {
			srcArticleIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcArticleIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		StringBuffer logs = new StringBuffer("转移文章:");
		for (int i = 0; i < set.size(); i++) {
			ZCArticleSchema article = set.get(i);

			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article
					.getCatalogID());
			if (("Y".equals(config.getBranchManageFlag()))
					&& (!"admin".equals(User.getUserName()))) {
				String branchInnerCode = article.getBranchInnerCode();
				if ((XString.isNotEmpty(branchInnerCode))
						&& (!User.getBranchInnerCode().equals(branchInnerCode))) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误：您没有操作文档“"
							+ article.getTitle() + "”的权限！");
					return;
				}
			}
			srcCatalogID = article.getCatalogID();
			String destCatalogID = catalogID;
			if ((article.getStatus() == 10L)
					&& (!"admin".equals(User.getUserName()))) {
				this.response.setStatus(0);
				this.response.setMessage("文档处于流转中，不能进行转移操作！");
				return;
			}
			String ReferTarget = article.getReferTarget();
			if (XString.isNotEmpty(ReferTarget)) {
				ReferTarget = "," + ReferTarget + ",";
				ReferTarget = XString.replaceEx(ReferTarget, "," + catalogID
						+ ",", ",");
				ReferTarget = ReferTarget
						.substring(0, ReferTarget.length() - 1);
				article.setReferTarget(ReferTarget);
			}
			article.setClusterTarget(null);

			trans.add(new QueryBuilder(
					"update zccatalog set total = total+1 where id=?",
					destCatalogID));
			trans.add(new QueryBuilder(
					"update zccatalog set total = total-1 where id=?",
					srcCatalogID));
			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setCatalogID(catalogID);
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			String workflowID = CatalogUtil.getWorkflow(destCatalogID);
			if (XString.isNotEmpty(workflowID)) {
				article.setWorkFlowID(null);
				trans
						.add(new QueryBuilder(
								"delete from zwstep where exists (select * from zwinstance where dataid=? and id=zwstep.instanceID)",
								article.getID()));
				trans.add(new QueryBuilder(
						"delete from zwinstance where dataid=?", article
								.getID()));
			}
			article.setStatus(0L);

			trans.add(article, OperateType.UPDATE);

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("MOVE");
			articleLog.setActionDetail("转移。从"
					+ CatalogUtil.getName(srcCatalogID) + "转移到"
					+ CatalogUtil.getName(destCatalogID) + "。");
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			trans.add(articleLog, OperateType.INSERT);
		}
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}
		}
		if (trans.commit()) {
			Publisher p = new Publisher();

			p.deletePubishedFile(set);

			UserLogPage.log("Article", "MoveArticle", logs + "成功", this.request
					.getClientIP());
			this.response.setMessage("转移成功");
		} else {
			UserLogPage.log("Article", "MoveArticle", logs + "失败", this.request
					.getClientIP());
			this.response.setError("操作数据库时发生错误!");
		}
	}

	public void copy() {
		String articleIDs = $V("ArticleIDs");
		if (!XString.checkID(articleIDs)) {
			UserLogPage.log("Article", "CopyArticle", "复制文章失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ArticleID时发生错误!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!XString.checkID(catalogIDs)) {
			UserLogPage.log("Article", "CopyArticle", "复制文章失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		if ((catalogIDs.indexOf("\"") >= 0) || (catalogIDs.indexOf("'") >= 0)) {
			UserLogPage.log("Article", "CopyArticle", "复制文章失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in ("
				+ articleIDs + ")"));

		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if ((article.getStatus() == 10L)
					&& (!"admin".equals(User.getUserName()))) {
				this.response.setStatus(0);
				this.response.setMessage("文档处于流转中，不能进行复制操作！");
				return;
			}

			DataTable customData = new QueryBuilder(
					"select ColumnCode,TextValue from zdcolumnvalue where relaid = ?",
					article.getID()).executeDataTable();
			for (int j = 0; j < customData.getRowCount(); j++) {
				this.request.put("_C_" + customData.getString(j, "ColumnCode"),
						customData.getString(j, "TextValue"));
			}
			this.request.put("ReferTarget", catalogIDs);
			Article.copy(this.request, trans, article);

			article.setReferTarget(catalogIDs);
			article.setReferType($V("ReferType"));
		}

		StringBuffer logs = new StringBuffer("复制文章:");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" 等，共" + set.size() + "篇");
			}

			trans.add(set, OperateType.UPDATE);
		}
		if (trans.commit()) {
			UserLogPage.log("Article", "CopyArticle", logs + "成功", this.request
					.getClientIP());
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			UserLogPage.log("Article", "CopyArticle", logs + "失败", this.request
					.getClientIP());
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void sortArticle() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		boolean topFlag = "true".equals($V("TopFlag"));
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		Transaction tran = new Transaction();
		if (topFlag) {
			QueryBuilder qb = new QueryBuilder(
					"update ZCArticle set TopFlag='1' where OrderFlag in ("
							+ orders + ")");
			tran.add(qb);
		} else {
			QueryBuilder qb = new QueryBuilder(
					"update ZCArticle set TopFlag='0' where OrderFlag in ("
							+ orders + ")");
			tran.add(qb);
		}
		OrderUtil.updateOrder("ZCArticle", "OrderFlag", type, target, orders,
				null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {

				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id).getString(
							"ListPage");
					if ((XString.isEmpty(listpage))
							|| ("0".equals(listpage))
							|| ("-1".equals(listpage))) {
						listpage = "20";
					}
					p.publishCatalog(Long.parseLong(id), false, false, Integer
							.parseInt(listpage));
					setPercent(100);
				}
			};
			ltt.setUser(User.getCurrent());
			ltt.start();

			this.response.setMessage("操作成功");
		} else {
			this.response.setError("操作失败");
		}
	}

	public void setTop() {
		String ids = $V("IDs");
		String topDate = $V("TopDate");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}

		QueryBuilder qb = new QueryBuilder(
				"update ZCArticle set TopFlag='1' where id in (" + ids + ")");
		if (XString.isNotEmpty(topDate)) {
			if (new Date().compareTo(DateUtil.parseDateTime(topDate + " "
					+ $V("TopTime"))) >= 0) {
				this.response.setLogInfo(0, "置顶有效期限应大于当前时间!");
				return;
			}
			qb = new QueryBuilder("update ZCArticle set TopFlag='1',TopDate='"
					+ topDate + " " + $V("TopTime") + "' where id in (" + ids
					+ ")");
		}
		try {
			qb.executeNoQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETTOP", "置顶处理");
		if (!trans.commit()) {
			this.response.setMessage("置顶操作时记录操作历史信息出错！");
		}
		DataTable dt = new QueryBuilder(
				"select Title from ZCArticle where id in (" + ids + ")")
				.executeDataTable();
		StringBuffer logs = new StringBuffer("置顶文章:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" 等，共" + dt.getRowCount() + "篇");
			}
		}
		UserLogPage.log("Article", "TopArticle", logs + "成功", this.request
				.getClientIP());
		this.response.setLogInfo(1, "置顶成功");

		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(
				" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
	}

	public void setNotTop() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}
		QueryBuilder qb = new QueryBuilder(
				"update ZCArticle set TopFlag='0' where id in (" + ids + ")");
		try {
			qb.executeNoQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETNOTTOP", "取消置顶");
		if (!trans.commit()) {
			this.response.setMessage("取消置顶操作时记录操作历史信息出错！");
		}
		DataTable dt = new QueryBuilder(
				"select Title from ZCArticle where id in (" + ids + ")")
				.executeDataTable();
		StringBuffer logs = new StringBuffer("取消置顶文章:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" 等，共" + dt.getRowCount() + "篇");
			}
		}
		UserLogPage.log("Article", "NotTopArticle", logs + "成功", this.request
				.getClientIP());
		this.response.setLogInfo(1, "取消置顶成功");

		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(
				" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
	}
}

/*
 * com.xdarkness.cms.document.ArticleList JD-Core Version: 0.6.0
 */