package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCBoardMessageSchema;
import com.xdarkness.schema.ZCMessageBoardSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class MessageBoard extends Page {
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID() + "";
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCMessageBoard where SiteID=? order by ID desc",
				siteID);
		dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), "site", ApplicationPage
						.getCurrentSiteID()
						+ "", "site_manage")) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogID = dr.getString("RelaCatalogID");
				if ("0".equals(RelaCatalogID)) {
					return Priv.getPriv(User.getUserName(), "site", ApplicationPage
							.getCurrentSiteID()+"", "article_manage");
				}
				return Priv.getPriv(User.getUserName(), "article", CatalogUtil
						.getInnerCode(RelaCatalogID), "article_modify");
			}
		});
		ta.setRootText("留言板列表");
		ta.setLeafIcon("Icons/icon034a1.gif");
		ta.bindData(dt);
	}

	public static void MessageDataBind(DataListAction dla) {
		String BoardID = dla.getParam("BoardID");
		if (XString.isEmpty(BoardID)) {
			BoardID = "0";
		}
		String ReplyFlag = dla.getParam("ReplyFlag");
		String PublishFlag = dla.getParam("PublishFlag");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCBoardMessage where BoardID=?", BoardID);
		if (XString.isNotEmpty(ReplyFlag)) {
			qb.append(" and ReplyFlag =?", ReplyFlag);
		}
		if (XString.isNotEmpty(PublishFlag)) {
			qb.append(" and PublishFlag =?", PublishFlag);
		}
		dla.setTotal(qb);
		qb.append(" order by AddTime DESC");
		Mapx map = new Mapx();
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dt.insertColumn("ReplyAreaDisplay");
		dt.insertColumn("ReplyContentAreaDisplay");
		dt.insertColumn("DO");
		dt.insertColumn("PublishFlagName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ((dt.getString(i, "ReplyFlag").equals("N"))
					|| (XString.isEmpty(dt.getString(i, "ReplyContent")))) {
				dt.set(i, "ReplyAreaDisplay", "");
				dt.set(i, "ReplyContentAreaDisplay", "none");
			} else {
				dt.set(i, "ReplyAreaDisplay", "none");
				dt.set(i, "ReplyContentAreaDisplay", "");
				dt
						.set(
								i,
								"DO",
								"<div id='DO_"
										+ dt.getString(i, "ID")
										+ "'><input type='button' onClick='editReply("
										+ dt.getString(i, "ID")
										+ ")' value='修改'/>&nbsp;<input type='button' value='删除' onClick='delReply("
										+ dt.getString(i, "ID") + ")' /></div>");
			}
			if (dt.getString(i, "PublishFlag").equals("Y"))
				dt.set(i, "PublishFlagName",
						"<font color='#00ff00'>审核通过</font>");
			else {
				dt.set(i, "PublishFlagName",
						"<font color='#ffcc00'>等待审核</font>");
			}
		}
		map.put("Y", "已回复");
		map.put("N", "未回复");
		dt.decodeColumn("ReplyFlag", map);
		if (dt.getRowCount() == 0) {
//			dt.insertRow(null);
			dt.set(0, "ID", "0");
		}
		dla.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if (XString.isNotEmpty(ID)) {
			ZCMessageBoardSchema messageboard = new ZCMessageBoardSchema();
			messageboard.setID(ID);
			messageboard.fill();
			params.putAll(messageboard.toMapx());
			params.put("RadioIsOpen", HtmlUtil.codeToRadios("IsOpen",
					"YesOrNo", messageboard.getIsOpen()));
		} else {
			params.put("RadioIsOpen", HtmlUtil.codeToRadios("IsOpen",
					"YesOrNo", "N"));
		}
		return params;
	}

	public static Mapx initMessageDialog(Mapx params) {
		String ID = params.getString("ID");
		if (XString.isNotEmpty(ID)) {
			ZCBoardMessageSchema message = new ZCBoardMessageSchema();
			message.setID(ID);
			message.fill();
			params.putAll(message.toMapx());
		}
		return params;
	}

	public void save() {
		String ID = $V("ID");
		String Name = $V("Name");
		String Description = $V("Description");
		String IsOpen = $V("IsOpen");
		ZCMessageBoardSchema messageboard = new ZCMessageBoardSchema();
		Transaction trans = new Transaction();
		boolean exists = false;
		if (XString.isNotEmpty(ID)) {
			messageboard.setID(ID);
			messageboard.fill();
			messageboard.setModifyTime(new Date());
			messageboard.setModifyUser(User.getUserName());
			exists = true;
		} else {
			messageboard.setID(NoUtil.getMaxID("MessageBoardID"));
			messageboard.setSiteID(ApplicationPage.getCurrentSiteID());
			messageboard.setAddTime(new Date());
			messageboard.setAddUser(User.getUserName());
		}
		if (!Name.equals(messageboard.getName())) {
			if (new QueryBuilder(
					"select Count(*) from ZCMessageBoard where Name = ? and SiteID = ?",
					Name, ApplicationPage.getCurrentSiteID()).executeInt() > 0) {
				this.response.setLogInfo(0, "已有相同名称留言板，请更换名称");
				return;
			}
		}
		messageboard.setName(Name);
		messageboard.setDescription(Description);
		messageboard.setIsOpen(IsOpen);
		messageboard.setRelaCatalogID($V("RelaCatalogID"));
		if (exists)
			trans.add(messageboard, OperateType.UPDATE);
		else {
			trans.add(messageboard, OperateType.INSERT);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "保存成功");
		else
			this.response.setLogInfo(0, "保存失败");
	}

	public void saveReply() {
		String ID = $V("MsgID");
		String ReplyContent = $V("ReplyContent");
		ZCBoardMessageSchema message = new ZCBoardMessageSchema();
		message.setID(ID);
		message.fill();
		if ((XString.isEmpty(ReplyContent))
				&& (XString.isEmpty(message.getReplyContent())))
			message.setReplyFlag("N");
		else {
			message.setReplyFlag("Y");
		}
		message.setReplyContent(ReplyContent);
		message.setModifyTime(new Date());
		message.setModifyUser(User.getUserName());
		if (message.update())
			this.response.setLogInfo(1, "回复成功");
		else
			this.response.setLogInfo(0, "回复失败");
	}

	public void delReply() {
		String MsgID = $V("MsgID");
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		boardmessage.setID(MsgID);
		boardmessage.fill();
		boardmessage.setReplyContent("");
		if (boardmessage.update())
			this.response.setLogInfo(1, "删除回复成功");
		else
			this.response.setLogInfo(0, "删除回复失败");
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCMessageBoardSchema board = new ZCMessageBoardSchema();
		ZCBoardMessageSchema message = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			trans
					.add(
							board.query(new QueryBuilder(" where ID = ?",
									ids[i])), OperateType.DELETE_AND_BACKUP);
			trans.add(message.query(new QueryBuilder(" where BoardID = ?",
					ids[i])), OperateType.DELETE_AND_BACKUP);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void delMessage() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(boardmessage.query(new QueryBuilder(" where ID = ?",
					ids[i])), OperateType.DELETE_AND_BACKUP);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void doCheck() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			boardmessage = new ZCBoardMessageSchema();
			boardmessage.setID(ids[i]);
			boardmessage.fill();
			boardmessage.setPublishFlag("Y");
			trans.add(boardmessage, OperateType.UPDATE);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "审核成功");
		else
			this.response.setLogInfo(0, "审核失败");
	}
}

/*
 * com.xdarkness.cms.dataservice.MessageBoard JD-Core Version: 0.6.0
 */