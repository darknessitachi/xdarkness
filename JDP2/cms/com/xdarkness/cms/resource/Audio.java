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
import com.xdarkness.schema.ZCAudioRelaSchema;
import com.xdarkness.schema.ZCAudioRelaSet;
import com.xdarkness.schema.ZCAudioSchema;
import com.xdarkness.schema.ZCAudioSet;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Audio extends Page {
	public static Mapx initEditDialog(Mapx params) {
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(params.getString("ID"));
		Audio.fill();
		params.putAll(Audio.toMapx());
		params.put("CustomColumn", ColumnUtil.getHtml("1",
				Audio.getCatalogID()+"", "2", Audio.getID()+""));
		return params;
	}

	public static Mapx initUploadDialog(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		params.putAll(catalog.toMapx());
		params.put("allowType", Config.getValue("AllowAudioExt"));
		return params;
	}

	public void dialogEdit() {
		String AudioID = $V("ID");
		Transaction trans = new Transaction();
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(AudioID);
		Audio.fill();
		Audio.setValue(this.request);
		DataTable columnDT = ColumnUtil.getColumnValue("2", Audio.getID());
		if (columnDT.getRowCount() > 0) {
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", Audio.getID()));
		}
		trans.add(ColumnUtil.getValueFromRequest(Audio.getCatalogID(), Audio
				.getID(), this.request), OperateType.INSERT);
		trans.add(Audio, OperateType.UPDATE);
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
		String InnerCode = new QueryBuilder(
				"select InnerCode from zccatalog where ID = ?", catalogID)
				.executeString();
		String newPath = "Audio/" + catalogPath;
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "CopyAudio", "复制音频失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("复制音频:");
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(
				" where ID in (" + IDs + ")"));
		boolean flag = true;
		for (int i = 0; i < AudioSet.size(); i++) {
			ZCAudioSchema Audio = AudioSet.get(i);
			String oldPath = Alias + Audio.getPath();
			String oldFileName = oldPath + Audio.getFileName();
			Audio.setID(NoUtil.getMaxID("DocID"));
			Audio.setCatalogID(catalogID);
			Audio.setCatalogInnerCode(InnerCode);
			Audio.setPath(newPath);
			Audio.setFileName(Audio.getID()
					+ Audio.getFileName().substring(
							Audio.getFileName().lastIndexOf(".")));
			Audio.setAddTime(new Date());
			Audio.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias
					+ newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName,
					Config.getContextRealPath() + Alias + newPath
							+ Audio.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Audio.getName() + ",");
		}
		if ((flag) && (AudioSet.insert())) {
			UserLogPage.log("Resource", "EditAudio", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "复制音频成功");
		} else {
			UserLogPage.log("Resource", "EditAudio", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "复制音频失败");
		}
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "MoveAudio", "音频转移失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(
				"update ZCAudio set catalogid=? ,CatalogInnerCode=? where ID in ("
						+ IDs + ")", catalogID, CatalogUtil
						.getInnerCode(catalogID)));

		StringBuffer logs = new StringBuffer("转移音频:");
		DataTable dt = new QueryBuilder("select Name from ZCAudio where id in("
				+ IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLogPage.log("Resource", "MoveAudio", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "转移成功");
		} else {
			UserLogPage.log("Resource", "MoveAudio", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "转移失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "DelAudio", "删除音频失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除音频:");
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(
				" where id in (" + IDs + ")"));
		ZCAudioRelaSet AudioRelaSet = new ZCAudioRelaSchema()
				.query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AudioSet.size(); i++) {
			FileUtil
					.delete(Config.getContextRealPath() + Alias
							+ AudioSet.get(i).getPath()
							+ AudioSet.get(i).getFileName());
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", AudioSet.get(i).getID()));
			logs.append(AudioSet.get(i).getName() + ",");
		}
		trans.add(AudioSet, OperateType.DELETE_AND_BACKUP);
		trans.add(AudioRelaSet, OperateType.DELETE_AND_BACKUP);
		trans
				.add(new QueryBuilder(
						"update zccatalog set total = (select count(*) from zcaudio where catalogID =?) where ID =?",
						AudioSet.get(0).getCatalogID(), AudioSet.get(0)
								.getCatalogID()));
		if (trans.commit()) {
			Publisher p = new Publisher();
			p.deleteFileTask(AudioSet);

			UserLogPage.log("Resource", "DelAudio", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "删除音频成功");
		} else {
			UserLogPage.log("Resource", "DelAudio", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(0, "删除音频失败");
		}
	}
}

/*
 * com.xdarkness.cms.resource.Audio JD-Core Version: 0.6.0
 */