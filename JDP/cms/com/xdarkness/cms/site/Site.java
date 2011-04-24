package com.xdarkness.cms.site;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xdarkness.cms.dataservice.FullText;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteExporter;
import com.xdarkness.cms.pub.SiteImporter;
import com.xdarkness.cms.pub.SiteTableRela;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.resource.ConfigImageLib;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCPageBlockSchema;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.schema.ZCSiteSet;
import com.xdarkness.schema.ZDPrivilegeSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.data.BlockingTransaction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class Site extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(ApplicationPage.getCurrentSiteID());
		site.fill();

		return site.toMapx();
	}

	public static Mapx initDialog(Mapx params) {
		Object o1 = params.get("ID");
		if (o1 != null) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(ID);
			site.fill();

			params.putAll(site.toMapx());

			String indexFlag = site.getAutoIndexFlag();
			if (XString.isEmpty(indexFlag)) {
				indexFlag = "Y";
			}
			params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios(
					"AutoIndexFlag", "YesOrNo", indexFlag));

			String statFlag = site.getAutoStatFlag();
			if (XString.isEmpty(statFlag)) {
				statFlag = "Y";
			}
			params.put("radioAutoStatFlag", HtmlUtil.codeToRadios(
					"AutoStatFlag", "YesOrNo", statFlag));

			params.put("radioBBSEnableFlag", HtmlUtil.codeToRadios(
					"BBSEnableFlag", "YesOrNo", site.getBBSEnableFlag()));
			params.put("radioShopEnableFlag", HtmlUtil.codeToRadios(
					"ShopEnableFlag", "YesOrNo", site.getShopEnableFlag()));

			String auditFlag = site.getCommentAuditFlag();
			params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios(
					"CommentAuditFlag", "YesOrNo", auditFlag));
			params.put("radioAllowContribute", HtmlUtil.codeToRadios(
					"AllowContribute", "YesOrNo", site.getAllowContribute()));
			return params;
		}
		params.put("URL", "http://");
		params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios("AutoIndexFlag",
				"YesOrNo", "Y"));
		params.put("radioAutoStatFlag", HtmlUtil.codeToRadios("AutoStatFlag",
				"YesOrNo", "Y"));
		params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios(
				"CommentAuditFlag", "YesOrNo", "Y"));
		params.put("radioAllowContribute", HtmlUtil.codeToRadios(
				"AllowContribute", "YesOrNo", "N"));
		return params;
	}

	public void saveTemplate() {
		Transaction trans = new Transaction();
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Long.parseLong($V("ID")));
		site.fill();
		site.setValue(this.request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		trans.add(site, OperateType.UPDATE);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("保存数据发生错误!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCSite order by orderflag");
		dga.bindData(qb);
	}

	public void add() {
		Transaction trans = new Transaction();
		long id = NoUtil.getMaxID("SiteID");
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?",
				$V("Alias")).executeInt() > 0) {
			this.response.setLogInfo(0, "新建站点失败,重复的目录名");
			return;
		}
		if ((!add(trans, id)) || (!trans.commit())) {
			this.response.setLogInfo(0, "新建站点失败");
			UserLogPage.log("Site", "AddSite", "增加站点失败！", this.request
					.getClientIP());
		} else {
			updatePrivAndFile($V("Alias"));

			ZCSiteSchema site = SiteUtil.getSchema(id);
			String path = SiteUtil.getAbsolutePath(site.getID());
			if (XString.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site
						.getHeaderTemplate(), "动态应用头部引用", "include");
			}
			if (XString.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site.getTopTemplate(),
						"动态应用顶部", "top");
			}
			if (XString.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site
						.getBottomTemplate(), "动态应用底部", "bottom");
			}

			SiteUtil.update(id);
			this.response.setLogInfo(1, "新建站点成功");
			UserLogPage.log("Site", "AddSite", "增加站点:" + $V("Name") + ",成功！",
					this.request.getClientIP());
		}
	}

	public boolean addDefaultPageBlock(String SiteID, String AbsolutePath,
			String template, String PageBlockName, String PageBlockCode) {
		File file = new File(AbsolutePath + template);
		if (!file.exists()) {
			file.mkdirs();
		}

		if (new QueryBuilder("select count(*) from ZCPageBlock where SiteID = "
				+ SiteID + " and Name=? and Code =?", PageBlockName,
				PageBlockCode).executeInt() > 0) {
			return true;
		}
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		block.setID(NoUtil.getMaxID("PageBlockID"));
		block.setSiteID(SiteID);
		block.setName(PageBlockName);
		block.setCode(PageBlockCode);
		block.setFileName("/include/" + PageBlockCode + ".html");
		block.setTemplate(template);
		block.setType(1L);
		block.setAddTime(new Date());
		block.setAddUser(User.getUserName());
		return block.insert();
	}

	public boolean add(Transaction trans, long siteID) {
		if ((LicenseInfo.getName().equals("TrailUser"))
				&& (new QueryBuilder("select count(*) from ZCSite")
						.executeInt() >= 1)) {
			this.response.setError("站点数超出限制，请联系泽元软件更换License！");
			return false;
		}
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?",
				$V("Alias")).executeInt() > 0) {
			this.response.setLogInfo(0, "新建站点失败,重复的目录名");
			this.response.setError("新建站点失败,已存在的目录名");
			return false;
		}
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(this.request);
		site.setID(siteID);
		site.setHitCount(0L);
		site.setChannelCount(0L);
		site.setSpecialCount(0L);
		site.setMagzineCount(0L);
		site.setArticleCount(0L);
		site.setImageLibCount(1L);
		site.setVideoLibCount(1L);
		site.setAudioLibCount(1L);
		site.setAttachmentLibCount(1L);
		site.setOrderFlag(OrderUtil.getDefaultOrder());
		site.setBranchInnerCode(User.getBranchInnerCode());
		site.setAddTime(new Date());
		site.setAddUser(User.getUserName());
		site.setConfigXML(ConfigImageLib.imageLibConfigDefault);
		trans.add(site, OperateType.INSERT);

		addDefaultResourceLib(site.getID(), trans);

		addDefaultPriv(site.getID(), trans);

		initSiteConfig(site.getID(), trans);
		return true;
	}

	public static void updatePrivAndFile(String alias) {
		RolePriv.updateAllPriv("admin");

		String oldPath = Config.getContextRealPath() + "Images";
		String path = (Config.getContextRealPath()
				+ Config.getValue("UploadDir") + "/" + alias + "/upload/Image")
				.replaceAll("//", "/");
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtil.copy(oldPath + "/nocover.jpg", path + "/nocover.jpg");
		FileUtil.copy(oldPath + "/nophoto.jpg", path + "/nophoto.jpg");
		FileUtil.copy(oldPath + "/nopicture.jpg", path + "/nopicture.jpg");
		FileUtil.copy(oldPath + "/WaterMarkImage1.png", path
				+ "/WaterMarkImage1.png");
		FileUtil.copy(oldPath + "/WaterMarkImage.png", path
				+ "/WaterMarkImage.png");

		String templatePath = Config.getContextRealPath()
				+ Config.getValue("Statical.TemplateDir") + "/" + alias
				+ "/template";
		dir = new File(templatePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String imagePath = Config.getContextRealPath()
				+ Config.getValue("Statical.TemplateDir") + "/" + alias
				+ "/images";
		dir = new File(imagePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		FileUtil.copy(Config.getContextRealPath() + "/Tools/ImagePlayer.swf",
				imagePath + "/ImagePlayer.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/player.swf",
				imagePath + "/player.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/Swfobject.js",
				imagePath + "/Swfobject.js");

		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.js", imagePath
				+ "/vote.js");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.css",
				imagePath + "/vote.css");
	}

	public static void addDefaultCatalogLib(long siteID, Transaction trans,
			String catalogType, String[] catalogNames) {
		for (int i = 0; i < catalogNames.length; i++) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(NoUtil.getMaxID("CatalogID"));
			catalog.setSiteID(siteID);
			catalog.setParentID(0L);
			catalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
			catalog.setTreeLevel(1L);
			catalog.setName(catalogNames[i]);
			catalog.setURL("");
			catalog.setAlias(XString.getChineseFirstAlpha(catalogNames[i]));
			catalog.setType(catalogType);
			catalog.setListTemplate("");
			catalog.setListNameRule("");
			catalog.setDetailTemplate("");
			catalog.setDetailNameRule("");
			catalog.setChildCount(0L);
			catalog.setIsLeaf(1L);
			catalog.setTotal(0L);
			catalog.setOrderFlag(Catalog.getCatalogOrderFlag(0L, catalog
					.getType()));
			catalog.setLogo("");
			catalog.setListPageSize(10L);
			catalog.setPublishFlag("Y");
			catalog.setHitCount(0L);
			catalog.setMeta_Keywords("");
			catalog.setMeta_Description("");
			catalog.setOrderColumn("");
			catalog.setAddUser(User.getUserName());
			catalog.setAddTime(new Date());
			trans.add(catalog, OperateType.INSERT);
		}
	}

	public static void addDefaultResourceLib(long siteID, Transaction trans) {
		ZCCatalogSchema imageCatalog = new ZCCatalogSchema();
		imageCatalog.setID(NoUtil.getMaxID("CatalogID"));
		imageCatalog.setSiteID(siteID);
		imageCatalog.setParentID(0L);
		imageCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		imageCatalog.setTreeLevel(1L);
		imageCatalog.setName("默认图片");
		imageCatalog.setURL("");
		imageCatalog.setAlias(XString.getChineseFirstAlpha(imageCatalog
				.getName()));
		imageCatalog.setType(4L);
		imageCatalog.setListTemplate("");
		imageCatalog.setListNameRule("");
		imageCatalog.setDetailTemplate("");
		imageCatalog.setDetailNameRule("");
		imageCatalog.setChildCount(0L);
		imageCatalog.setIsLeaf(1L);
		imageCatalog.setTotal(0L);
		imageCatalog.setOrderFlag(Catalog.getCatalogOrderFlag(0L, imageCatalog
				.getType()));
		imageCatalog.setLogo("");
		imageCatalog.setListPageSize(10L);
		imageCatalog.setListPage(-1L);
		imageCatalog.setPublishFlag("Y");
		imageCatalog.setHitCount(0L);
		imageCatalog.setMeta_Keywords("");
		imageCatalog.setMeta_Description("");
		imageCatalog.setOrderColumn("");
		imageCatalog.setAddUser(User.getUserName());
		imageCatalog.setAddTime(new Date());
		trans.add(imageCatalog, OperateType.INSERT);

		ZCCatalogSchema videoCatalog = (ZCCatalogSchema) imageCatalog.clone();
		videoCatalog.setID(NoUtil.getMaxID("CatalogID"));
		videoCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		videoCatalog.setType(5L);
		videoCatalog.setName("默认视频");
		videoCatalog.setAlias(XString.getChineseFirstAlpha(videoCatalog
				.getName()));
		trans.add(videoCatalog, OperateType.INSERT);

		ZCCatalogSchema audioCatalog = (ZCCatalogSchema) imageCatalog.clone();
		audioCatalog.setID(NoUtil.getMaxID("CatalogID"));
		audioCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		audioCatalog.setType(6L);
		audioCatalog.setName("默认音频");
		audioCatalog.setAlias(XString.getChineseFirstAlpha(audioCatalog
				.getName()));
		trans.add(audioCatalog, OperateType.INSERT);

		ZCCatalogSchema attachCatalog = (ZCCatalogSchema) imageCatalog.clone();
		attachCatalog.setID(NoUtil.getMaxID("CatalogID"));
		attachCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		attachCatalog.setType(7L);
		attachCatalog.setName("默认附件");
		attachCatalog.setAlias(XString.getChineseFirstAlpha(attachCatalog
				.getName()));
		trans.add(attachCatalog, OperateType.INSERT);
	}

	public static void addDefaultPriv(long siteID, Transaction trans) {
		Set set = Priv.SITE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType("R");
			priv.setOwner("admin");
			priv.setID(siteID+"");
			priv.setPrivType("site");
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, OperateType.INSERT);
		}

		set = Priv.ARTICLE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType("R");
			priv.setOwner("admin");
			priv.setID(siteID+"");
			priv.setPrivType("site");
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, OperateType.INSERT);
		}
	}

	public static void initSiteConfig(long siteID, Transaction trans) {
     ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
     config.setID(NoUtil.getMaxID("CatalogConfigID"));
     config.setAddTime(new Date());
     config.setAddUser(User.getUserName());
     config.setStartTime(new Date());
     config.setArchiveTime("12");
     config.setAttachDownFlag("Y");
     config.setSiteID(siteID);
     config.setCatalogID(0L);
     config.setIsUsing("N");
     config.setPlanType("Period");
     Calendar c = Calendar.getInstance();
     c.set(c.get(1), c.get(2), c.get(5), 22, 0, 0);
     StringBuffer sb = new StringBuffer();
     int minute = c.get(12);
     int hour = c.get(11);
     int day = c.get(5);
     sb.append(minute);
     sb.append(" ");
     sb.append(hour);
     sb.append(" ");
     sb.append(day);
     sb.append("-");
     sb.append(day - 1);
     sb.append("/1");
     sb.append(" * *");
     config.setCronExpression(sb.toString());
     trans.add(config, OperateType.INSERT);
   }

	public void save() {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(this.request);
		site.fill();
		String oldAlias = site.getAlias();

		if (new QueryBuilder(
				"select count(*) from zcsite where ID != ? and Alias = ?", site
						.getID(), $V("Alias")).executeInt() > 0) {
			this.response.setLogInfo(0, "保存失败,重复的目录名");
			return;
		}
		site.setValue(this.request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		if (site.update()) {
			String path = SiteUtil.getAbsolutePath(site.getID());
			if (XString.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site
						.getHeaderTemplate(), "动态应用头部引用", "include");
			}
			if (XString.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site.getTopTemplate(),
						"动态应用顶部", "top");
			}
			if (XString.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID()+"", path, site
						.getBottomTemplate(), "动态应用底部", "bottom");
			}
			SiteUtil.update(site.getID());
			this.response.setLogInfo(1, "保存成功");
			UserLogPage.log("Site", "UpdateSite", "修改站点:" + site.getName() + "成功！",
					this.request.getClientIP());
			final String indexFlag = site.getAutoIndexFlag();
			final long siteID = site.getID();
			new Thread() {

				public void run() {
					FullText.dealAutoIndex(siteID, "Y"
							.equals(indexFlag));
				}
			}.start();

			String oldPath = Config.getContextRealPath()
					+ Config.getValue("Statical.TargetDir") + "/" + oldAlias
					+ "/";
			String newPath = Config.getContextRealPath()
					+ Config.getValue("Statical.TargetDir") + "/"
					+ site.getAlias() + "/";
			if (!oldAlias.equals(site.getAlias())) {
				updateSiteAlias(siteID, oldAlias, site.getAlias());
				File f = new File(oldPath);
				if (!f.renameTo(new File(newPath)))
					this.response.setLogInfo(1,
							"数据保存成功，但根据站点英文名重命名站点文件夹失败，请手工修改站点文件夹!");
			}
		} else {
			UserLogPage.log("Site", "UpdateSite", "修改站点:" + site.getName() + "失败！",
					this.request.getClientIP());
			this.response.setLogInfo(0, "保存失败");
		}
	}

	public static void updateSiteAlias(long siteID, String oldAlias,
			String newAlias) {
		int total = new QueryBuilder(
				"select count(1) from ZCArticle where SiteID=?", siteID)
				.executeInt();
		int size = 200;
		QueryBuilder qb = new QueryBuilder(
				"select id,content from ZCArticle where SiteID=?", siteID);
		for (int i = 0; i <= total * 1.0D / size; i++) {
			DataTable dt = qb.executePagedDataTable(size, i);
			QueryBuilder qb2 = new QueryBuilder(
					"update ZCArticle set Content=? where ID=?");
			qb2.setBatchMode(true);
			for (int j = 0; j < dt.getRowCount(); j++) {
				String content = dt.getString(j, "Content");
				content = XString.replaceEx(content, "/" + oldAlias + "/",
						"/" + newAlias + "/");
				qb2.add(content);
				qb2.add(dt.getString(j, "ID"));
				qb2.addBatch();
			}
			try {
				qb2.executeNoQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}

		ZCSiteSchema site = new ZCSiteSchema();
		ZCSiteSet set = site
				.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer siteLog = new StringBuffer("删除站点:");
		for (int i = 0; i < set.size(); i++) {
			site = set.get(i);
			siteLog.append(site.getName() + ",");

			if ("Y".equals($V("BackupFlag"))) {
				SiteExporter se = new SiteExporter(site.getID());
				se.exportSite(Config.getContextRealPath()
						+ "WEB-INF/data/backup/" + site.getAlias() + "_"
						+ System.currentTimeMillis() + ".dat");
			}
		}

		BlockingTransaction trans = new BlockingTransaction();
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		delSiteRela(ids, trans);

		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				site = set.get(i);
				SiteUtil.update(site.getID());
				if (ids.indexOf(ApplicationPage.getCurrentSiteID()+"") >= 0) {
					DataTable dt = new QueryBuilder(
							"select name,id from zcsite order by BranchInnerCode ,orderflag ,id")
							.executeDataTable();
					dt = dt.filter(new Filter() {
						public boolean filter(Object obj) {
							DataRow dr = (DataRow) obj;
							return Priv.getPriv(User.getUserName(), "site", dr
									.getString("ID"), "site_browse");
						}
					});
					if (dt.getRowCount() > 0)
						ApplicationPage.setCurrentSiteID(dt.getString(0, 1));
					else {
						ApplicationPage.setCurrentSiteID("");
					}
				}

				String sitePath = Config.getContextRealPath()
						+ Config.getValue("Statical.TemplateDir") + "/"
						+ site.getAlias();
				FileUtil.delete(sitePath);
			}
			UserLogPage.log("Site", "DelSite", siteLog + "成功", this.request
					.getClientIP());
			this.response.setLogInfo(1, "删除成功");
		} else {
			UserLogPage.log("Site", "DelSite", siteLog + "失败", this.request
					.getClientIP());
			this.response.setLogInfo(0, "删除失败");
		}
	}

	public static void delSiteRela(String siteIDs, BlockingTransaction trans) {
		if (!XString.checkID(siteIDs)) {
			return;
		}
		String[] tables = SiteTableRela.getSiteIDTables();
		for (int i = 0; i < tables.length; i++) {
			deleteTableData(tables[i], siteIDs, trans);
		}
		SiteTableRela.TableRela[] trs = SiteTableRela.getRelas();
		for (int i = 0; i < trs.length; i++) {
			SiteTableRela.TableRela tr = trs[i];
			if (tr.isExportData)
				deleteRelaTableData(tr.TableCode, tr.KeyField, tr.RelaTable,
						tr.RelaField, siteIDs, trans);
		}
	}

	public static void deleteTableData(String tableName, String siteIDs,
			BlockingTransaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(
				1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil
				.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName
				+ ".*,'" + backupNO + "','" + backupOperator + "','"
				+ backupTime + "',null from " + tableName + " where SiteID in("
				+ siteIDs + ")";
		String delete = "delete from " + tableName + " where SiteID in ("
				+ siteIDs + ")";
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			LogUtil.warn(e.getMessage());
		}
	}

	public static void deleteRelaTableData(String tableName, String foreinKey,
			String relaTableName, String relaField, String siteIDs,
			BlockingTransaction trans) {
		String wherePart = " where exists(select '' from " + relaTableName
				+ " where " + relaField + "=" + tableName + "." + foreinKey
				+ " and SiteID in (" + siteIDs + "))";
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(
				1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil
				.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName
				+ ".*,'" + backupNO + "','" + backupOperator + "','"
				+ backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			LogUtil.warn(e.getMessage());
		}
	}

	public static void uploadSite(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload fu = new ServletFileUpload(fileFactory);
			List fileItems = fu.parseRequest(request);
			fu.setHeaderEncoding("UTF-8");
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String OldFileName = item.getName();
					LogUtil.info("Upload Site FileName:" + OldFileName);
					long size = item.getSize();
					if (((OldFileName == null) || (OldFileName.equals("")))
							&& (size == 0L)) {
						continue;
					}
					OldFileName = OldFileName.substring(OldFileName
							.lastIndexOf("\\") + 1);
					String ext = OldFileName.substring(OldFileName
							.lastIndexOf("."));
					if (!ext.toLowerCase().equals(".dat")) {
						response.sendRedirect("SiteImportStep1.jsp?Error=1");
						return;
					}
					String FileName = "SiteUpload_"
							+ DateUtil.getCurrentDate("yyyyMMddHHmmss")
							+ ".dat";
					String Path = Config.getContextRealPath()
							+ "WEB-INF/data/backup/";
					item.write(new File(Path + FileName));
					response.sendRedirect("SiteImportStep2.jsp?FileName="
							+ FileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void importStep2DataBind(DataGridAction dga) {
		String sql = "select * from ZCSite order by orderflag ";
		dga.setTotal(new QueryBuilder("select * from ZCSite"));
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga
				.getPageSize(), dga.getPageIndex());
		dt.insertColumn("Type");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Type", "<font class='red'>替换站点</font>");
		}
		if (dga.getPageIndex() == 0) {
			dt.insertRow( 0);
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			Mapx map = new Mapx();
			try {
				map = SiteImporter.getSiteInfo(Path + $V("FileName"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dt.set(0, "ID", "0");
			dt.set(0, "Type", "<font class='green'>新建站点</font>");
			dt.set(0, "Name",
					"<input class='inputText' style='width:150px' id='Name' value='"
							+ map.getString("Name") + "'>");
			dt.set(0, "Alias",
					"<input class='inputText' style='width:100px' id='Alias' value='"
							+ map.getString("Alias") + "'>");
			dt.set(0, "URL",
					"<input class='inputText' style='width:250px' id='URL' value='"
							+ map.getString("URL") + "'>");
		}
		dga.bindData(dt);
	}

	public void checkImportSite() {
		String ID = $V("ID");
		String Name = $V("Name");
		String Alias = $V("Alias");
		QueryBuilder qb = new QueryBuilder(
				"select count(1) from ZCSite where Alias=? and ID<>?", Alias,
				ID);
		if (qb.executeInt() != 0) {
			this.response.setError("别名 <font class='red'>" + Alias
					+ "</font> 已经被占用，请修改!");
			return;
		}
		qb = new QueryBuilder(
				"select count(1) from ZCSite where Name=? and ID<>?", Name, ID);
		if (qb.executeInt() != 0) {
			this.response.setError("站点名称 <font class='red'>" + Name
					+ "</font> 已经被占用，请修改称!");
			return;
		}
		LongTimeTask ltt = LongTimeTask.getInstanceByType("SiteImport");
		if (ltt != null) {
			this.response.setError("目前有站点导入任务正在运行中，请等待！");
			return;
		}
		final String Path = Config.getContextRealPath() + "WEB-INF/data/backup/"
				+ $V("FileName");
		final Mapx map = this.request;
		ltt = new LongTimeTask() {

			public void execute() {
				SiteImporter si = new SiteImporter(Path, this);
				si.setSiteInfo(map);
				si.importSite();
				setPercent(100);
			}
		};
		ltt.setType("SiteImport");
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", ltt.getTaskID());
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		if (OrderUtil.updateOrder("ZCSite", type, target, orders, "1 = 1"))
			this.response.setMessage("排序成功");
		else
			this.response.setError("排序失败");
	}
}

/*
 * com.xdarkness.cms.site.Site JD-Core Version: 0.6.0
 */