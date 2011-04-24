package com.xdarkness.cms.resource;

import java.io.File;
import java.util.Date;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCAttachmentRelaSchema;
import com.xdarkness.schema.ZCAttachmentRelaSet;
import com.xdarkness.schema.ZCAttachmentSchema;
import com.xdarkness.schema.ZCAttachmentSet;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;

public class Attachment extends Page {
	public static Mapx init(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		if (XString.isEmpty(CatalogID)) {
			CatalogID = (String) new QueryBuilder(
					"select ID from ZCCatalog where Type = 7 and SiteID =? and Name = '默认附件'",
					ApplicationPage.getCurrentSiteID()).executeOneValue();
		}

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		DataTable dt = new QueryBuilder("select name,id from zccatalog ")
				.executePagedDataTable(100, 0);
		params.put("Who", HtmlUtil.dataTableToOptions(dt));
		params.putAll(catalog.toMapx());

		String imagePath = "upload/Image/nopicture.jpg";
		params.put("ImagePath", imagePath);
		params.put("PicSrc", Config.getContextPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
				+ imagePath);
		params.put("allowType", Config.getValue("AllowAttachExt"));

		String fileSize = Config.getValue("AllowAttachSize");
		if (XString.isEmpty(fileSize)) {
			fileSize = "20971520";
		}
		params.put("fileMaxSize", fileSize);
		long size = Long.parseLong(fileSize);
		String fileMaxSizeHuman = "";
		if (size < 1048576L)
			fileMaxSizeHuman = NumberUtil.round(size * 1.0D / 1024.0D, 1) + "K";
		else if (size < 1073741824L) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0D / 1048576.0D, 1)
					+ "M";
		}
		params.put("fileMaxSizeHuman", fileMaxSizeHuman);
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		String attachID = params.getString("ID");
		String imagePath = "upload/Image/nopicture.jpg";
		DataTable dt = new QueryBuilder(
				"select imagepath from ZCAttachment where id=?", attachID)
				.executeDataTable();
		if ((dt.getRowCount() == 0)
				|| (XString.isEmpty((String) dt.get(0, 0)))) {
			params.put("Name", new QueryBuilder(
					"select Name from ZCAttachment where id=?", attachID)
					.executeString());
			params.put("Info", new QueryBuilder(
					"select Info from ZCAttachment where id=?", attachID)
					.executeString());
			params.put("ImagePath", imagePath);
			params.put("PicSrc", Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ imagePath);
			return params;
		}
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		params = attach.toMapx();
		imagePath = attach.getImagePath();
		params.put("PicSrc", Config.getContextPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
				+ imagePath);
		params.put("CustomColumn", ColumnUtil.getHtml("1", attach
				.getCatalogID()
				+ "", "2", attach.getID() + ""));
		params.put("AttachmentLink", (Config.getContextPath()
				+ "/Services/AttachDownLoad.jsp?id=" + attach.getID())
				.replaceAll("//", "/"));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZCAttachmentSchema Attachment = new ZCAttachmentSchema();
		Attachment.setID(params.getString("ID"));
		Attachment.fill();
		params.putAll(Attachment.toMapx());

		String fileSize = Config.getValue("AllowAttachSize");
		if (XString.isEmpty(fileSize)) {
			fileSize = "20971520";
		}
		params.put("fileMaxSize", fileSize);
		return params;
	}

	public void dialogEdit() {
		String attachID = $V("ID");
		Transaction trans = new Transaction();
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		attach.setValue(this.request);
		DataTable columnDT = ColumnUtil.getColumnValue("2", attach.getID());
		if (columnDT.getRowCount() > 0) {
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", attach.getID()));
		}
		trans.add(ColumnUtil.getValueFromRequest(attach.getCatalogID(), attach
				.getID(), this.request), OperateType.INSERT);
		trans.add(attach, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "编辑成功！");
		else
			this.response.setLogInfo(1, "编辑失败！");
	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String newPath = "Attachment/" + catalogPath;
		String InnerCode = new QueryBuilder(
				"select InnerCode from zccatalog where ID = ?", catalogID)
				.executeString();
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "CopyAttachment", "复制附件失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema()
				.query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		StringBuffer logs = new StringBuffer("复制附件:");
		for (int i = 0; i < AttachmentSet.size(); i++) {
			ZCAttachmentSchema Attachment = AttachmentSet.get(i);
			String oldFileName = Alias + Attachment.getPath()
					+ Attachment.getFileName();
			Attachment.setID(NoUtil.getMaxID("DocID"));
			Attachment.setCatalogID(catalogID);
			Attachment.setCatalogInnerCode(InnerCode);
			Attachment.setPath(newPath);
			Attachment.setFileName(Attachment.getID()
					+ Attachment.getFileName().substring(
							Attachment.getFileName().lastIndexOf(".")));
			Attachment.setAddTime(new Date());
			Attachment.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias
					+ newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName,
					Config.getContextRealPath() + Alias + newPath
							+ Attachment.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Attachment.getName() + ",");
		}
		if ((flag) && (AttachmentSet.insert())) {
			UserLogPage.log("Resource", "CopyAttachment", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "复制附件成功！");
		} else {
			UserLogPage.log("Resource", "CopyAttachment", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "复制附件失败！");
		}
	}

	public void getAttachSrc() {
		String ID = $V("AttachID");
		DataTable dt = new QueryBuilder(
				"select name,siteid,path,srcfilename from zcattachment where id=?",
				ID).executeDataTable();
		if (dt.getRowCount() > 0)
			this.response.put("AttachPath", (Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dt.getLong(0, "siteid")) + "/"
					+ dt.get(0, "path").toString()
					+ dt.get(0, "srcfilename").toString() + "|" + dt.getString(
					0, "name")).replaceAll("//", "/"));
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(
				"update ZCAttachment set catalogid=? ,CatalogInnerCode=? where ID in ("
						+ IDs + ")", catalogID, CatalogUtil
						.getInnerCode(catalogID)));
		StringBuffer logs = new StringBuffer("转移附件:");
		DataTable dt = new QueryBuilder(
				"select Name from ZCAttachment where id in(" + IDs + ")")
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLogPage.log("Resource", "MoveAttachment", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "转移成功");
		} else {
			UserLogPage.log("Resource", "MoveAttachment", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(0, "转移失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "DelAttachment", "删除附件失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除附件:");
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema()
				.query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCAttachmentRelaSet AttachmentRelaSet = new ZCAttachmentRelaSchema()
				.query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AttachmentSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias
					+ AttachmentSet.get(i).getPath()
					+ AttachmentSet.get(i).getFileName());
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", AttachmentSet.get(i).getID()));
			logs.append(AttachmentSet.get(i).getName() + ",");
		}
		trans.add(AttachmentSet, OperateType.DELETE_AND_BACKUP);
		trans.add(AttachmentRelaSet, OperateType.DELETE_AND_BACKUP);
		trans
				.add(new QueryBuilder(
						"update zccatalog set total = (select count(*) from zcattachment where catalogID =?) where ID =?",
						AttachmentSet.get(0).getCatalogID(), AttachmentSet.get(
								0).getCatalogID()));
		if (trans.commit()) {
			Publisher p = new Publisher();
			p.deleteFileTask(AttachmentSet);

			UserLogPage.log("Resource", "DelAttachment", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "删除附件成功！");
		} else {
			UserLogPage.log("Resource", "DelAttachment", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "删除附件失败！");
		}
	}
}

/*
 * com.xdarkness.cms.resource.Attachment JD-Core Version: 0.6.0
 */