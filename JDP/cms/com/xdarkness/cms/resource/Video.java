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
import com.xdarkness.schema.ZCVideoRelaSchema;
import com.xdarkness.schema.ZCVideoRelaSet;
import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.schema.ZCVideoSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;
import com.xdarkness.framework.util.VideoUtil;

public class Video extends Page {
	public static Mapx initEditDialog(Mapx params) {
		ZCVideoSchema Video = new ZCVideoSchema();
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		Video.setID(Long.parseLong(params.get("ID").toString()));
		Video.fill();
		params = Video.toMapx();
		params.put("Alias", Alias);
		params.put("IsOriginal", HtmlUtil.codeToRadios("IsOriginal", "YesOrNo",
				Video.getIsOriginal()));
		params.put("CustomColumn", ColumnUtil.getHtml("1",
				Video.getCatalogID()+"", "2", Video.getID()+""));
		return params;
	}

	public static Mapx initPlayDialog(Mapx params) {
		ZCVideoSchema Video = new ZCVideoSchema();
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		Video.setID(Long.parseLong(params.get("ID").toString()));
		Video.fill();
		String files = "";
		files = files + ".." + Alias + Video.getPath() + Video.getFileName();
		Mapx map = Video.toMapx();
		map.put("files", files);
		return map;
	}

	public void edit() {
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong($V("ID")));
		video.fill();
		video.setValue(this.request);
		video.setModifyTime(new Date());
		video.setModifyUser(User.getUserName());
		String StartSecond = $V("StartSecond");
		if (XString.isNotEmpty(StartSecond)) {
			StartSecond = StartSecond.trim();
		}
		if (XString.isNotEmpty(StartSecond)) {
			int ss = Integer.parseInt(StartSecond);
			VideoUtil.captureImage(Config.getContextRealPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(video.getSiteID()) + "/"
					+ video.getPath() + video.getFileName(), Config
					.getContextRealPath()
					+ Config.getValue("UploadDir")
					+ "/"
					+ SiteUtil.getAlias(video.getSiteID())
					+ "/"
					+ video.getPath() + video.getImageName(), ss);
		}
		DataTable columnDT = ColumnUtil.getColumnValue("2", video.getID());
		Transaction trans = new Transaction();
		if (columnDT.getRowCount() > 0) {
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", video.getID()));
		}
		trans.add(ColumnUtil.getValueFromRequest(video.getCatalogID(), video
				.getID(), this.request), OperateType.INSERT);
		trans.add(video, OperateType.UPDATE);
		if (trans.commit()) {
			UserLogPage.log("Resource", "EditVideo", "编辑视频:" + video.getName()
					+ " 成功", this.request.getClientIP());
			this.response.setLogInfo(1, "修改成功!");
		} else {
			UserLogPage.log("Resource", "EditVideo", "编辑视频:" + video.getName()
					+ " 失败", this.request.getClientIP());
			this.response.setLogInfo(0, "修改失败!");
		}
	}

	public static Mapx initUploadDialog(Mapx params) {
		params.put("IsOriginal", HtmlUtil.codeToRadios("IsOriginal", "YesOrNo",
				"N"));
		DataTable dt = new QueryBuilder("select name,id from zccatalog ")
				.executePagedDataTable(100, 0);
		params.put("Who", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(
				"update ZCVideo set catalogid=? ,CatalogInnerCode=? where ID in ("
						+ ids + ")", catalogID, CatalogUtil
						.getInnerCode(catalogID)));

		StringBuffer logs = new StringBuffer("转移视频:");
		DataTable dt = new QueryBuilder("select Name from ZCVideo where id in("
				+ ids + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLogPage.log("Resource", "MoveVideo", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "转移成功");
		} else {
			UserLogPage.log("Resource", "MoveVideo", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "转移失败");
		}
	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String InnerCode = new QueryBuilder(
				"select InnerCode from zccatalog where ID = ?", catalogID)
				.executeString();
		String newPath = "upload/Video/" + catalogPath;
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "CopyVideo", "复制视频失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCVideoSet videoSet = new ZCVideoSchema().query(new QueryBuilder(
				" where ID in (" + IDs + ")"));
		boolean flag = true;
		String msg = "";
		StringBuffer logs = new StringBuffer("复制图片:");
		for (int i = 0; i < videoSet.size(); i++) {
			ZCVideoSchema video = videoSet.get(i);
			String oldPath = Alias + video.getPath();
			String oldFileName = video.getFileName();
			String oldSrcFileName = video.getSrcFileName();
			String oldImageName = video.getImageName();
			video.setID(NoUtil.getMaxID("DocID"));
			video.setCatalogID(catalogID);
			video.setCatalogInnerCode(InnerCode);
			video.setPath(newPath);
			int random = NumberUtil.getRandomInt(10000);
			video.setFileName(video.getID() + random + ".flv");
			video.setImageName(video.getID() + random + ".jpg");
			video.setSrcFileName(video.getID() + NumberUtil.getRandomInt(10000)
					+ "." + video.getSuffix());

			if (!FileUtil.copy(Config.getContextRealPath() + oldPath
					+ oldImageName, Config.getContextRealPath() + Alias
					+ newPath + video.getImageName())) {
				msg = "复制失败，原因是：'" + video.getName() + "'的视频缩略图复制失败";
				flag = false;
				break;
			}

			if (!FileUtil.copy(Config.getContextRealPath() + oldPath
					+ oldSrcFileName, Config.getContextRealPath() + Alias
					+ newPath + video.getSrcFileName())) {
				msg = "复制失败，原因是：'" + video.getName() + "'的原文件复制失败";
				flag = false;
				break;
			}

			if (!FileUtil.copy(Config.getContextRealPath() + oldPath
					+ oldFileName, Config.getContextRealPath() + Alias
					+ newPath + video.getFileName())) {
				msg = "复制失败，原因是：'" + video.getName() + "'的flv文件复制失败";
				flag = false;
				break;
			}
			if (!flag) {
				break;
			}
			video.setAddTime(new Date());
			video.setAddUser(User.getUserName());
			logs.append(video.getName() + ",");
		}
		if ((flag) && (videoSet.insert())) {
			UserLogPage.log("Resource", "CopyVideo", "复制视频失败", this.request
					.getClientIP());
			this.response.setLogInfo(1, "复制视频成功");
		} else {
			UserLogPage.log("Resource", "CopyVideo", msg, this.request
					.getClientIP());
			this.response.setLogInfo(0, msg);
		}
	}

	public void del() {
		String IDs = $V("IDs");
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		if (!XString.checkID(IDs)) {
			UserLogPage.log("Resource", "DelVideo", "删除视频失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除视频");
		ZCVideoSet VideoSet = new ZCVideoSchema().query(new QueryBuilder(
				" where id in (" + IDs + ")"));
		ZCVideoRelaSet VideoRelaSet = new ZCVideoRelaSchema()
				.query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < VideoSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias
					+ VideoSet.get(i).getPath()
					+ VideoSet.get(i).getSrcFileName());

			FileUtil.delete(Config.getContextRealPath() + Alias
					+ VideoSet.get(i).getPath()
					+ VideoSet.get(i).getImageName());

			FileUtil
					.delete(Config.getContextRealPath() + Alias
							+ VideoSet.get(i).getPath()
							+ VideoSet.get(i).getFileName());
			logs.append(VideoSet.get(i).getName() + ",");
			trans
					.add(new QueryBuilder(
							"delete from zdcolumnvalue where relatype=? and relaid = ?",
							"2", VideoSet.get(i).getID()));
		}
		trans.add(VideoSet, OperateType.DELETE_AND_BACKUP);
		trans.add(VideoRelaSet, OperateType.DELETE_AND_BACKUP);
		trans
				.add(new QueryBuilder(
						"update zccatalog set total = (select count(*) from zcvideo where catalogID = ?) where ID = ?",
						VideoSet.get(0).getCatalogID(), VideoSet.get(0)
								.getCatalogID()));
		if (trans.commit()) {
			Publisher p = new Publisher();
			p.deleteFileTask(VideoSet);

			UserLogPage.log("Resource", "DelVideo", logs + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "删除视频成功");
		} else {
			UserLogPage.log("Resource", "DelVideo", logs + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "删除视频失败");
		}
	}
}

/*
 * com.xdarkness.cms.resource.Video JD-Core Version: 0.6.0
 */