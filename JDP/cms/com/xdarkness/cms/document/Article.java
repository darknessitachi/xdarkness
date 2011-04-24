package com.xdarkness.cms.document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.cms.datachannel.PublishMonitor;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.dataservice.Vote;
import com.xdarkness.cms.pub.CMSCache;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.site.BadWord;
import com.xdarkness.cms.site.Tag;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.schema.ZCAttachmentRelaSchema;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCImageSet;
import com.xdarkness.schema.ZCInnerDeploySchema;
import com.xdarkness.schema.ZCInnerDeploySet;
import com.xdarkness.schema.ZCTagSchema;
import com.xdarkness.schema.ZCVideoRelaSchema;
import com.xdarkness.schema.ZCVoteItemSchema;
import com.xdarkness.schema.ZCVoteSchema;
import com.xdarkness.schema.ZCVoteSet;
import com.xdarkness.search.SearchResult;
import com.xdarkness.search.index.IndexUtil;
import com.xdarkness.workflow.Context;
import com.xdarkness.workflow.WorkflowAction;
import com.xdarkness.workflow.WorkflowUtil;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.ButtonTag;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.orm.data.DataTableUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class Article extends Page {
	public static final String TYPE_COMMON = "1";
	public static final String TYPE_IMAGE = "2";
	public static final String TYPE_VIDEO = "3";
	public static final String TYPE_URL = "4";
	public static final String TYPE_ATTACH = "5";
	public static final String RELA_ATTACH = "ArticleAttach";
	public static final String RELA_VIDEO = "ArticleVideo";
	public static final String RELA_IMAGE = "ArticleImage";
	public static final int STATUS_DRAFT = 0;
	public static final int STATUS_WORKFLOW = 10;
	public static final int STATUS_TOPUBLISH = 20;
	public static final int STATUS_PUBLISHED = 30;
	public static final int STATUS_OFFLINE = 40;
	public static final int STATUS_ARCHIVE = 50;
	public static final int STATUS_EDITING = 60;
	public static final Mapx STATUS_MAP = new Mapx();

	private String siteAlias = SiteUtil
			.getAlias(ApplicationPage.getCurrentSiteID());

	static {
		STATUS_MAP.put("0", "初稿");
		STATUS_MAP.put("10", "流转中");
		STATUS_MAP.put("20", "待发布");
		STATUS_MAP.put("30", "已发布");
		STATUS_MAP.put("40", "已下线");
		STATUS_MAP.put("50", "已归档");
		STATUS_MAP.put("60", "重新编辑");
	}

	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String catalogID = (String) params.get("CatalogID");
		String issueID = (String) params.get("IssueID");
		String articleID = (String) params.get("ArticleID");
		String title = (String) params.get("Title");
		try {
			if (XString.isNotEmpty(title))
				title = URLDecoder.decode(title, "utf-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		if ((articleID != null) && (!"".equals(articleID))
				&& (!"null".equals(articleID))) {
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(Integer.parseInt(articleID));
			ZCArticleSet set = article.query();
			if (set.size() < 1) {
				return null;
			}
			article = set.get(0);
			catalogID = article.getCatalogID()+"";
			map.putAll(article.toMapx());

			String content = article.getContent();
			if (content == null) {
				content = "";
			}
			String[] pages = content.split("<!--_SKY_PAGE_BREAK_-->", -1);

			map.put("Pages", new Integer(pages.length));
			map.put("Content", pages[0]);
			map.put("Contents", content);

			String pageTitle = article.getPageTitle();
			if (pageTitle == null) {
				pageTitle = "";
			}
			String[] pageTitles = pageTitle.split("\\|", -1);
			map.put("PageTitle", pageTitles[0]);
			map.put("PageTitles", pageTitle);

			map.put("CustomColumn", ColumnUtil.getHtml("1", catalogID, "2",
					article.getID()+""));

			if (article.getAttribute() != null)
				map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute",
						"ArticleAttribute", article.getAttribute().split(",")));
			else {
				map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute",
						"ArticleAttribute"));
			}

			Date publishDate = article.getPublishDate();
			Date downlineDate = article.getDownlineDate();
			Date archiveDate = article.getArchiveDate();
			if (publishDate != null) {
				map.put("PublishDate", DateUtil.toString(publishDate,
						"yyyy-MM-dd"));
				map.put("PublishTime", DateUtil.toString(publishDate,
						"HH:mm:ss"));
			}

			if (downlineDate != null) {
				map.put("DownlineDate", DateUtil.toString(downlineDate,
						"yyyy-MM-dd"));
				map.put("DownlineTime", DateUtil.toString(downlineDate,
						"HH:mm:ss"));
			}

			if (archiveDate != null) {
				map.put("ArchiveDate", DateUtil.toString(archiveDate,
						"yyyy-MM-dd"));
				map.put("ArchiveTime", DateUtil.toString(archiveDate,
						"HH:mm:ss"));
			}

			Date lastModify = article.getModifyTime() == null ? article
					.getAddTime() : article.getModifyTime();
			map.put("LastModify", lastModify);

			map.put("Method", "UPDATE");

			boolean publishFlag = (30L == article.getStatus())
					|| (20L == article.getStatus());
			String html = getInitButtons(Long.parseLong(catalogID), article
					.getWorkFlowID(), publishFlag);
			map.put("buttonDiv", html);

			DataTable imgDt = new QueryBuilder(
					"select ID from ZCImageRela where RelaType = 'ArticleImage' and RelaID = ?",
					article.getID()).executeDataTable();
			String imageIDs = "";
			for (int i = 0; i < imgDt.getRowCount(); i++) {
				if (i != 0) {
					imageIDs = imageIDs + ",";
				}
				imageIDs = imageIDs + imgDt.getString(i, 0);
			}
			String relaImagePlayerID = article.getProp3();
			String relaImageIDs = "";
			if (XString.isEmpty(relaImagePlayerID)) {
				relaImagePlayerID = "";
			} else if (XString.isNotEmpty(imageIDs)) {
				imgDt = new QueryBuilder(
						"select ID from ZCImageRela where RelaType='ImagePlayerImage' and RelaID=? and ID in ("
								+ imageIDs + ")", relaImagePlayerID)
						.executeDataTable();
				for (int i = 0; i < imgDt.getRowCount(); i++) {
					if (i != 0) {
						relaImageIDs = relaImageIDs + ",";
					}
					relaImageIDs = relaImageIDs + imgDt.getString(i, 0);
				}
			}
			map.put("ImageIDs", imageIDs);
			map.put("RelaImageIDs", relaImageIDs);
			map.put("RelaImagePlayerID", relaImagePlayerID);

			String imgLogo = "../Images/addpicture.jpg";
			String sitePath = Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(article.getSiteID());
			sitePath = sitePath.replaceAll("/+", "/");
			String logo = article.getLogo();
			if (XString.isNotEmpty(logo)) {
				logo = sitePath + "/" + logo;
				imgLogo = logo;
			}
			map.put("Logo", logo);
			map.put("ImgLogo", imgLogo);

			if (XString.isNotEmpty(article.getReferTarget()))
				map.put("ReferTargetCount", XString.count(article
						.getReferTarget(), ",") + 1);
			else {
				map.put("ReferTargetCount", 0);
			}

			if (XString.isNotEmpty(article.getClusterTarget())) {
				map.put("ClusterTargetCount", XString.count(article
						.getClusterTarget(), ",") + 1);
				map.put("ClusterTarget", article.getClusterTarget());
			} else {
				map.put("ClusterTargetCount", 0);
			}

			long referCatalogID = new QueryBuilder(
					"select catalogID from zcarticle where id=?", article
							.getReferSourceID()).executeLong();
			if (referCatalogID != 0L) {
				map.put("ReferDisplay", "style='display:'");
				map.put("ReferSourceName", CatalogUtil
						.getFullName(referCatalogID));
			} else {
				map.put("ReferDisplay", "style='display:none'");
			}
		} else {
			map.put("CatalogID", catalogID);
			articleID = NoUtil.getMaxID("DocID")+"";
			map.put("ID", articleID);
			map.put("Method", "ADD");
			map.put("CatalogID", catalogID);
			map.put("CommentFlag", "1");

			map.put("DownlineDate", "2099-12-31");
			map.put("DownlineTime", "23:59:59");

			String archiveTime = "";
			if (CatalogUtil.getArchiveTime(catalogID) != null) {
				archiveTime = DateUtil.toDateTimeString(CatalogUtil
						.getArchiveTime(catalogID));
			}

			if (XString.isNotEmpty(archiveTime)) {
				map.put("ArchiveDate", archiveTime.substring(0, 10));
				map.put("ArchiveTime", archiveTime.substring(11));
			}

			map.put("Pages", new Integer(1));
			map.put("ContentPages", "''");
			map.put("Title", title);

			map.put("ReferTarget", "");
			map.put("ReferType", "1");

			map.put("Author", User.getRealName());

			String html = getInitButtons(Long.parseLong(catalogID), 0L, false);
			map.put("buttonDiv", html);

			map.put("CustomColumn", ColumnUtil.getHtml("1", catalogID));

			map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute",
					"ArticleAttribute"));

			String imageLogo = "../Images/addpicture.jpg";
			map.put("Logo", "");
			map.put("ImgLogo", imageLogo);

			map.put("ReferTargetCount", 0);
			map.put("ClusterTargetCount", 0);

			map.put("ReferDisplay", "style='display:none'");
		}
		ZCInnerDeploySchema deploy = new ZCInnerDeploySchema();
		deploy.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
		ZCInnerDeploySet set = deploy.query();
		if (set.size() > 0) { 
			DataTable dt = DataTableUtil.txtToDataTable(set.get(0)
					.getTargetCatalog(), "", "\t", "\n");
			if (dt.getDataColumn("MD5") == null) {
				dt.insertColumn("MD5");
			}

			if ("1".equals(set.get(0).getDeployType())) {
				map.put("ClusterTargetCount", dt.getRowCount() + "/"
						+ dt.getRowCount());
				for (int i = 0; i < dt.getRowCount(); i++) {
					dt.set(i, "MD5", XString.md5Hex(dt.getString(i,
							"ServerAddr")
							+ ","
							+ dt.getString(i, "SiteID")
							+ ","
							+ dt.getString(i, "CatalogID")));
				}
				map.put("ClusterTarget", XString.join(dt
						.getColumnValues("MD5")));
			} else {
				map.put("ClusterTargetCount", "0/" + dt.getRowCount());
			}
		}

		String siteID = CatalogUtil.getSiteID(catalogID);
		map.put("SiteID", CatalogUtil.getSiteID(catalogID));
		map.put("CatalogName", CatalogUtil.getName(catalogID));
		map.put("IssueID", issueID);
		map.put("InnerCode", CatalogUtil.getInnerCode(catalogID));
		map.put("URL", CatalogUtil.getPath(catalogID) + articleID + ".shtml");

		String cssFile = new QueryBuilder(
				"select editorcss from zcsite where id=?", siteID)
				.executeString();
		if (XString.isNotEmpty(cssFile)) {
			String staticDir = Config.getContextPath()
					+ Config.getValue("Statical.TargetDir").replace('\\', '/');
			staticDir = staticDir + "/" + ApplicationPage.getCurrentSiteAlias()
					+ "/" + cssFile;
			staticDir = staticDir.replaceAll("/+", "/");
			map.put("CssPath", staticDir);
		} else {
			map.put("CssPath", Config.getContextPath()
					+ "Editor/editor/css/fck_editorarea.css");
		}

		String defaultImageValue = Config.getContextPath()
				+ Config.getValue("UploadDir") + "/"
				+ ApplicationPage.getCurrentSiteAlias()
				+ "/upload/Image/nopicture.jpg";
		defaultImageValue = defaultImageValue.replaceAll("/+", "/");
		map.put("defaultImageValue", defaultImageValue);
		map.put("Title", XString.htmlEncode(map.getString("Title")));
		return map;
	}

	public void applyStep() {
		long instanceID = Long.parseLong($V("InstanceID"));
		int nodeID = Integer.parseInt($V("NodeID"));
		String catalogID = $V("CatalogID");
		try {
			WorkflowUtil.applyStep(instanceID, nodeID);
			WorkflowAction[] actions;
			try {
				 actions = WorkflowUtil.findAvaiableActions(instanceID);
			} catch (Exception e) {
				e.printStackTrace();
				this.response.setMessage("申请失败：" + e.getMessage());
				return;
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < actions.length; i++) {
				WorkflowAction action = actions[i];
				if (action.getID() == -1) {
					if (Priv.getPriv("article", CatalogUtil
							.getInnerCode(catalogID), "article_modify"))
						sb.append(ButtonTag.getHtml("ClickMethod='"
								+ action.getName() + "';save('" + instanceID
								+ "','" + action.getID() + "')", null,
								"<img src='../Icons/icon003a16.gif'/>", action
										.getName()));
				} else {
					boolean allowSelectUser = "1".equals(action.getData().get(
							"AllowSelectUser"));
					sb.append(ButtonTag.getHtml("ClickMethod='"
							+ action.getName() + "';save('" + instanceID
							+ "','" + action.getID() + "'," + allowSelectUser
							+ ")", null,
							"<img src='../Icons/icon003a16.gif'/>", action
									.getName()));
				}
			}
			$S("buttonDiv", sb.toString());
			this.response.setMessage("申请成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.response.setMessage("申请失败:" + e.getMessage());
		}
	}

	private static String getInitButtons(long catalogID, long instanceID, boolean publishFlag) {
     String workflowID = CatalogUtil.getWorkflow(catalogID);
     if ((XString.isNotEmpty(workflowID)) && 
       (WorkflowUtil.findWorkflow(Long.parseLong(workflowID)) == null)) {
       workflowID = null;
     }
 
     if (publishFlag) {
       String buttonDiv = null;
       if (XString.isNotEmpty(workflowID)) {
         if ("admin".equals(User.getUserName()))
           buttonDiv = ButtonTag.getHtml("publish(true)", null, "<img src='../Icons/icon003a13.gif'/>", "发布");
         else {
           buttonDiv = "";
         }
         if (Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(catalogID), 
           "article_modify")) {
           buttonDiv = buttonDiv + ButtonTag.getHtml(new StringBuffer("ClickMethod='重新修改';save('").append(instanceID).append("','")
             .append(-5).append("')").toString(), null, "<img src='../Icons/icon003a16.gif'/>", 
             "申请修改");
         }
       }
       else if (Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(catalogID), 
         "article_modify")) {
         buttonDiv = 
           ButtonTag.getHtml("save()", "article_modify", "<img src='../Icons/icon003a16.gif'/>", 
           "保存") + 
           ButtonTag.getHtml("topublish()", "article_modify", 
           "<img src='../Icons/icon003a6.gif'/>", "待发布") + 
           ButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", 
           "发布");
       } else {
         buttonDiv = "";
       }
 
       return buttonDiv;
     }
 
     if (XString.isNotEmpty(workflowID))
     {
       WorkflowAction[] actions = (WorkflowAction[])null;
       StringBuffer sb = null;
	if (instanceID != 0L)
         try {
           if ((!WorkflowUtil.isStartStep(instanceID)) && 
             (!Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(catalogID), 
             "article_audit"))) 
        	    sb = new StringBuffer();
           for (int i = 0; (actions != null) && (i < actions.length); i++) {
             WorkflowAction action = actions[i];
             if (action.getID() == -3) {
               sb.append(ButtonTag.getHtml("ClickMethod='" + action.getName() + "';applyStep('" + instanceID + 
                 "','" + action.getData().getInt("NodeID") + "')", null, 
                 "<img src='../Icons/icon003a16.gif'/>", action.getName()));
             } else if (action.getID() == -1) {
               if ((instanceID != 0L) && 
                 (!Priv.getPriv("article", CatalogUtil.getInnerCode(catalogID), "article_modify"))) continue;
               sb.append(ButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + 
                 "','" + action.getID() + "')", null, "<img src='../Icons/icon003a16.gif'/>", action
                 .getName()));
             }
             else {
               boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
               sb.append(ButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','" + 
                 action.getID() + "'," + allowSelectUser + ")", null, 
                 "<img src='../Icons/icon003a16.gif'/>", action.getName()));
             }
           }
     
           
        	   actions = WorkflowUtil.findAvaiableActions(instanceID);
         }
         catch (Exception e) {
           LogUtil.warn("Article.init:" + e.getMessage());
           return "";
         }
       else {
         try {
           actions = WorkflowUtil.findInitActions(Long.parseLong(workflowID));
         } catch (NumberFormatException e) {
           e.printStackTrace();
         } catch (Exception e) {
           e.printStackTrace();
           return "<script>Page.onLoad(function(){Dialog.alert('初始化工具流按钮出错：" + e.getMessage() + 
             "');});</script>";
         }
       }
       
       return sb.toString();
     }
 
     if (Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(catalogID), "article_modify")) {
       String buttonDiv = 
         ButtonTag.getHtml("save()", "article_modify", 
         "<img src='../Icons/icon003a16.gif'/>", "保存") + 
         ButtonTag.getHtml("topublish()", "article_modify", "<img src='../Icons/icon003a6.gif'/>", 
         "待发布") + 
         ButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", 
         "发布");
       return buttonDiv;
     }
 
     return "";
   }

	public static Mapx initPreview(Mapx params) {
		String articleID = (String) params.get("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(Integer.parseInt(articleID));
		article.fill();
		params = article.toDataRow().toCaseIgnoreMapx();
		return params;
	}

	public static void relativeDg1DataBind(DataGridAction dga) {
		String relaIDs = (String) dga.getParams().get("RelativeArticle");
		if (!XString.checkID(relaIDs)) {
			return;
		}
		if ("".equals(relaIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder(
				"select title,id from zcarticle where id in (" + relaIDs + ")")
				.executeDataTable();

		String[] ids = relaIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	public static Mapx initPlayerList(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		DataTable dt = new DataTable();
		String defaultPlayer = params.getString("ImagePlayerID");
		if (XString.isNotEmpty(CatalogID)) {
			String tempCode = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(CatalogID);
			catalog.fill();
			tempCode = catalog.getInnerCode();
			if (XString.isEmpty(defaultPlayer)) {
				defaultPlayer = new QueryBuilder(
						"select ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode = ?",
						ApplicationPage.getCurrentSiteID(), catalog.getInnerCode())
						.executeString();
			}
			while (catalog.getParentID() != 0L) {
				String temp = catalog.getParentID()+"";
				catalog = new ZCCatalogSchema();
				catalog.setID(temp);
				catalog.fill();
				tempCode = tempCode + "," + catalog.getInnerCode();
			}
			tempCode = tempCode + ",0";
			dt = new QueryBuilder(
					"select Name,ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode in("
							+ tempCode + ")", ApplicationPage.getCurrentSiteID())
					.executeDataTable();
		}
		if (XString.isEmpty(defaultPlayer)) {
			defaultPlayer = "";
		}
		params.put("ImagePlayers", HtmlUtil.dataTableToOptions(dt,
				defaultPlayer));
		return params;
	}

	public void saveRelaImage() {
		String articleID = $V("ArticleID");
		String imageIDs = $V("ImageIDs");
		String imagePlayerID = $V("ImagePlayerID");
		String[] ids = imageIDs.split(",");
		Transaction trans = new Transaction();
		int count = new QueryBuilder(
				"select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?",
				imagePlayerID).executeInt();
		count = 6 - count;
		String tempID =(String) new QueryBuilder(
				"select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc",
				imagePlayerID).executeOneValue();

		if (count == 0) {
			trans
					.add(new QueryBuilder(
							"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID=?",
							tempID));
			count = 1;
		}
		ZCImageSchema image = new ZCImageSchema();
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(articleID);
		article.fill();
		for (int i = 0; i < ids.length; i++) {
			if (i >= count) {
				break;
			}
			trans
					.add(new QueryBuilder(
							"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?",
							imagePlayerID, ids[i]));
			image = new ZCImageSchema();
			image.setID(ids[i]);
			image.fill();
			String isSingle = CatalogUtil.getSingleFlag(article.getCatalogID());
			String path = "";
			if ("Y".equals(isSingle))
				path = (SiteUtil.getURL(ApplicationPage.getCurrentSiteID()) + "/" + CatalogUtil
						.getPath(article.getCatalogID())).replaceAll("/+", "/");
			else {
				path = (SiteUtil.getURL(ApplicationPage.getCurrentSiteID()) + "/" + PubFun
						.getArticleURL(article)).replaceAll("/+", "/");
			}
			image.setLinkURL(path);
			image.setLinkText(article.getTitle());
			trans.add(image, OperateType.UPDATE);
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setID(image.getID());
			imageRela.setRelaID(imagePlayerID);
			imageRela.setRelaType("ImagePlayerImage");
			imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
			imageRela.setAddUser(User.getUserName());
			imageRela.setAddTime(new Date());
			trans.add(imageRela, OperateType.INSERT);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "关联至图片播放器成功");
		else
			this.response.setLogInfo(0, "关联失败");
	}

	public static void relaImageDataBind(DataListAction dla) {
		String ImageIDs = dla.getParam("ImageIDs");
		String RelaImageIDs = dla.getParam("RelaImageIDs");
		if ((!XString.checkID(ImageIDs))
				|| (!XString.checkID(RelaImageIDs))) {
			return;
		}
		DataTable dt = new DataTable();
		if (XString.isNotEmpty(ImageIDs)) {
			String path = Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
			dt = new QueryBuilder("select * from zcimage where ID in("
					+ ImageIDs + ")").executeDataTable();
			dt.insertColumn("Alias");
			dt.insertColumn("checkStatus");
			String[] IDs = RelaImageIDs.split(",");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "Alias", path);
				for (int j = 0; j < IDs.length; j++) {
					if (IDs[j].equals(dt.getString(i, "ID"))) {
						dt.set(i, "checkStatus", "checked");
					}
				}
			}
		}
		dla.bindData(dt);
	}

	public static void dealVote(ZCArticleSchema article, Transaction trans) {
		DataTable vote = new QueryBuilder(
				"select * from zcvote where siteid=? and votecatalogid = ?",
				article.getSiteID(), article.getCatalogID()).executeDataTable();
		if (vote.getRowCount() > 0) {
			DataTable subject = new QueryBuilder(
					"select * from zcvotesubject where voteid=? and votecatalogid = ?",
					vote.getString(0, "ID"), article.getCatalogID())
					.executeDataTable();
			if (subject.getRowCount() > 0) {
				ZCVoteItemSchema item = new ZCVoteItemSchema();
				item.setID(NoUtil.getMaxID("VoteItemID"));
				item.setVoteID(subject.getString(0, "VoteID"));
				item.setSubjectID(subject.getString(0, "ID"));
				item.setItem(article.getTitle());
				item.setScore(0L);
				item.setItemType("0");
				item.setVoteDocID(article.getID());
				item.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(item, OperateType.INSERT);
			}
		}
	}

	public static void autoRelativeDataBind(DataGridAction dga) {
		String relaIDs = (String) dga.getParams().get("IDs");
		if (!XString.checkID(relaIDs)) {
			return;
		}
		if ("".equals(relaIDs)) {
			relaIDs = "''";
		}
		DataTable dt = new QueryBuilder(
				"select title,id from zcarticle where id in (" + relaIDs + ")")
				.executeDataTable();
		dga.bindData(dt);
	}

	public static void recommendDg1DataBind(DataGridAction dga) {
		String recIDs = (String) dga.getParams().get("RecommendArticle");
		if (!XString.checkID(recIDs)) {
			return;
		}
		if ("".equals(recIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder(
				"select title,id,addtime,author from zcarticle where id in ("
						+ recIDs + ")").executeDataTable();

		String[] ids = recIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	public boolean save() {
		String clickMethod = $V("ClickMethod");
		String logMessage = "";
		try {
			Transaction trans = new Transaction();

			ZCArticleSchema article = new ZCArticleSchema();
			long articleID = Long.parseLong((String) this.request
					.get("ArticleID"));
			article.setID(articleID);

			String method = $V("Method");

			if ("UPDATE".equals(method)) {
				article.fill();
			}
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);
			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);

			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
			if (("Y".equals(config.getBranchManageFlag()))
					&& (!"admin".equals(User.getUserName()))) {
				String branchInnerCode = article.getBranchInnerCode();
				if ((XString.isNotEmpty(branchInnerCode))
						&& (!User.getBranchInnerCode().equals(branchInnerCode))) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误：您没有操作本文档权限！");
					return false;
				}
			}

			String innerCode = CatalogUtil.getInnerCode(catalogID);
			article.setCatalogInnerCode(innerCode);
			article.setType($V("Type"));
			String title = $V("Title");
			article.setTitle(title);
			article.setTitleStyle($V("TitleStyle"));
			article.setShortTitle($V("ShortTitle"));
			article.setShortTitleStyle($V("ShortTitleStyle"));
			article.setSubTitle($V("SubTitle"));
			article.setReferURL($V("ReferURL"));
			article.setReferName($V("ReferName"));
			article.setRelativeArticle($V("RelativeArticle"));
			article.setRecommendArticle($V("RecommendArticle"));
			article.setTopFlag($V("TopFlag"));
			article.setCommentFlag($V("CommentFlag"));
			article.setPriority($V("Priority"));
			article.setAttribute($V("Attribute"));

			String sitePath = Config.getContextPath()
					+ Config.getValue("UploadDir") + "/" + this.siteAlias + "/";
			sitePath = sitePath.replaceAll("/+", "/");
			String logo = $V("Logo");
			if (XString.isNotEmpty(logo)) {
				logo = logo.replaceAll(sitePath, "");
			}
			article.setLogo(logo);

			article.setClusterTarget($V("ClusterTarget"));

			article.setReferTarget($V("ReferTarget"));

			String author = $V("Author");
			article.setAuthor(author);
			article.setSummary($V("Summary"));

			String content = $V("Content");
			if (XString.isEmpty(content)) {
				content = " ";
			}

			String pageTitles = $V("PageTitles");
			if (XString.isEmpty(pageTitles)) {
				pageTitles = " ";
			}
			article.setPageTitle(pageTitles);

			if ("1".equals($V("LocalImageFlag"))) {
				String tempContent = content;
				if (Priv.getPriv("article", innerCode, "article_modify")) {
					content = copyRemoteFiles(content, trans, articleID);
				}
				if (!tempContent.equals(content)) {
					$S("ContentChanged", "Y");
					$S("Content", content);
				}
				article.setContent(content);
				article.setCopyImageFlag("Y");
			} else {
				article.setContent(content);
				article.setCopyImageFlag("N");
			}
			if (Priv.getPriv("article", innerCode, "article_modify")) {
				String keyword = $V("Keyword");
				dealKeyword(trans, article, keyword);

				dealTag(trans, article);

				dealRelaImage(trans, article);

				dealRelaAttach(trans, article);

				dealRelaVideo(trans, article);
			}

			$S("Keyword", article.getKeyword());
			$S("TagWord", article.getTag());

			if (XString.isNotEmpty($V("PublishDate"))) {
				String publishTime = $V("PublishTime");
				if (XString.isEmpty(publishTime)) {
					publishTime = "00:00:00";
				}
				article.setPublishDate(DateUtil.parse($V("PublishDate") + " "
						+ publishTime, "yyyy-MM-dd HH:mm:ss"));
			}

			if (XString.isEmpty($V("DownlineDate"))) {
				article.setDownlineDate(DateUtil.parse("2099-12-31 23:59:59",
						"yyyy-MM-dd HH:mm:ss"));
			} else {
				String downTime = $V("DownlineTime");
				if (XString.isEmpty(downTime)) {
					downTime = "00:00:00";
				}
				article.setDownlineDate(DateUtil.parse($V("DownlineDate") + " "
						+ downTime, "yyyy-MM-dd HH:mm:ss"));
			}

			if (XString.isEmpty($V("ArchiveDate"))) {
				article.setArchiveDate(null);
			} else {
				String archiveTime = $V("ArchiveTime");
				if (XString.isEmpty(archiveTime)) {
					archiveTime = "00:00:00";
				}
				article.setArchiveDate(DateUtil.parse($V("ArchiveDate") + " "
						+ archiveTime, "yyyy-MM-dd HH:mm:ss"));
			}

			article.setURL(PubFun.getArticleURL(article));
			article.setRedirectURL($V("RedirectURL"));
			article.setTemplateFlag($V("TemplateFlag"));
			article.setTemplate($V("Template"));

			String issueStr = $V("IssueID");
			if ((issueStr != null) && (!"".equals(issueStr))) {
				article.setIssueID(Long.parseLong($V("IssueID")));
			}

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(articleID);

			String entryId = $V("entryId");
			String actionId = $V("actionId");

			String workflowID = CatalogUtil.getWorkflow(catalogID);
			Context context = null;
			if ("UPDATE".equals(method)) {
				article.setModifyTime(new Date());
				article.setModifyUser(User.getUserName());
				articleLog.setAction("UPDATE");
				if (XString.isNotEmpty(clickMethod))
					articleLog.setActionDetail(clickMethod);
				else {
					articleLog.setActionDetail("更新文章");
				}
				if (Priv.getPriv("article", innerCode, "article_modify")) {
					trans.add(article, OperateType.UPDATE);

					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set isdirty=1 where id=?",
							catalogID);
					trans.add(qb);
				}

				if (XString.isNotEmpty(workflowID)) {
					trans.add(new QueryBuilder(
							"update zcarticle set Status=10 where id=?",
							article.getID()));
					if ((XString.isEmpty(entryId)) || (entryId.equals("0"))) {
						context = WorkflowUtil.createInstance(trans, Long
								.parseLong(workflowID), "CMS", article.getID()+"",
								"0");
						article.setWorkFlowID(context.getInstance().getID());
					} else {
						if (XString.isEmpty(actionId)) {
							this.response.setError("工作流执行错误：entryId=" + entryId
									+ ",actionId=" + actionId);
							return false;
						}
						WorkflowAction action = WorkflowUtil.findAction(Long
								.parseLong(workflowID), Integer
								.parseInt(actionId));
						action.execute(trans, Long.parseLong(entryId),
								$V("NextStepUser"), $V("Memo"));
					}

				} else if ((article.getStatus() == 30L)
						|| (article.getStatus() == 20L)) {
					article.setStatus(60L);
				}

				logMessage = "更新";
			} else if ("ADD".equals(method)) {
				dealVote(article, trans);

				article.setPublishFlag("1");
				article.setOrderFlag(OrderUtil.getDefaultOrder());
				article.setHitCount(0L);
				article.setStickTime(0L);
				article.setStatus(0L);
				article.setAddTime(new Date(article.getOrderFlag()));

				articleLog.setAction("INSERT");
				articleLog.setActionDetail("新建文章");

				article.setAddUser(User.getUserName());
				article.setBranchInnerCode(User.getBranchInnerCode());
				if (XString.isNotEmpty(workflowID)) {
					context = WorkflowUtil
							.createInstance(trans, Long.parseLong(workflowID),
									"CMS", article.getID()+"", "0");
					article.setWorkFlowID(context.getInstance().getID());

					if ((XString.isNotEmpty(actionId))
							&& (!"-1".equals(actionId))) {
						article.setStatus(10L);
					}
				}

				trans.add(article, OperateType.INSERT);
				String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, catalogID));
				logMessage = "增加";
			}

			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			if (XString.isEmpty(workflowID)) {
				trans.add(articleLog, OperateType.INSERT);
			}

			if (XString.isNotEmpty($V("NoteContent"))) {
				ArticleNote.add(trans, articleID+"", $V("NoteContent"));
			}

			if (Priv.getPriv("article", innerCode, "article_modify")) {
				saveCustomColumn(trans, this.request, catalogID, articleID,
						"ADD".equals(method));

				copy(this.request, trans, article);
			}
			article.setProp3($V("RelaImagePlayerID"));
			if (ExtendManager.hasAction("Article.BeforeSave")) {
				ExtendManager.executeAll("Article.BeforeSave", new Object[] {
						trans, article });
			}
			if (trans.commit()) {
				Transaction relaImgTrans = new Transaction();
				String RelaChange = $V("RelaChange");
				if ((XString.isNotEmpty(RelaChange))
						&& (RelaChange.equals("true"))) {
					String RelaImageIDs = $V("RelaImageIDs");
					String RelaImagePlayerID = $V("RelaImagePlayerID");
					String[] ids = RelaImageIDs.split(",");
					int count = new QueryBuilder(
							"select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?",
							RelaImagePlayerID).executeInt();
					int delCount = 0;
					delCount = count - (6 - ids.length);
					String[] tempID = new QueryBuilder(
							"select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc",
							RelaImagePlayerID).executeDataTable().toString()
							.split("\n");
					if (delCount > 0) {
						String temp = "";
						for (int i = 0; i < delCount; i++) {
							if (i != 0) {
								temp = temp + ",";
							}
							temp = temp + tempID[(i + 1)];
						}
						relaImgTrans.add(new QueryBuilder(
								"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID in ("
										+ temp + ")"));
					}
					ZCImageSchema image = new ZCImageSchema();
					for (int i = 0; i < ids.length; i++) {
						relaImgTrans
								.add(new QueryBuilder(
										"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?",
										RelaImagePlayerID, ids[i]));
						image = new ZCImageSchema();
						image.setID(ids[i]);
						image.fill();
						String isSingle = CatalogUtil.getSingleFlag(article
								.getCatalogID());
						if ("Y".equals(isSingle)) {
							sitePath = SiteUtil.getURL(ApplicationPage
									.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath = sitePath + "/";
							}
							sitePath = sitePath
									+ CatalogUtil.getPath(article
											.getCatalogID());
						} else {
							sitePath = SiteUtil.getURL(ApplicationPage
									.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath = sitePath + "/";
							}
							sitePath = sitePath + PubFun.getArticleURL(article);
						}
						image.setLinkURL(sitePath);
						image.setLinkText(article.getTitle());
						relaImgTrans.add(image, OperateType.UPDATE);
						ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
						imageRela.setID(image.getID());
						imageRela.setRelaID(RelaImagePlayerID);
						imageRela.setRelaType("ImagePlayerImage");
						imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
						imageRela.setAddUser(User.getUserName());
						imageRela.setAddTime(new Date());
						relaImgTrans.add(imageRela, OperateType.INSERT);
					}
				}
				relaImgTrans.commit();
				this.response.put("SaveTime", DateUtil.getCurrentDateTime());
				UserLogPage.log("Article", "AddArticle", logMessage + "文章:"
						+ article.getTitle() + "成功！", this.request
						.getClientIP());

				if ((XString.isNotEmpty(workflowID))
						&& ((XString.isEmpty(entryId)) || ("0"
								.equals(entryId)))
						&& (XString.isNotEmpty(actionId))
						&& (Integer.parseInt(actionId) != -1)) {
					WorkflowAction action = WorkflowUtil.findAction(Long
							.parseLong(workflowID), Integer.parseInt(actionId));
					action.execute(context, $V("NextStepUser"), $V("Memo"));
					context.getTransaction().commit();
				}

				String buttonDiv = "";
				QueryBuilder qb = new QueryBuilder(
						"select Status from ZCArticle where ID=?", article
								.getID());
				int status = qb.executeInt();
				if ((status == 20) || (status == 30)) {
					if (XString.isNotEmpty(workflowID)) {
						buttonDiv = "";
						if (Priv.getPriv("article", CatalogUtil
								.getInnerCode(catalogID), "article_modify")) {
							buttonDiv = buttonDiv
									+ ButtonTag
											.getHtml(
													"publish(true)",
													null,
													"<img src='../Icons/icon003a13.gif'/>",
													"发布");
							buttonDiv = buttonDiv
									+ ButtonTag
											.getHtml(
													new StringBuffer(
															"ClickMethod='重新修改';save('")
															.append(
																	article
																			.getWorkFlowID())
															.append("','")
															.append(-5).append(
																	"')")
															.toString(),
													null,
													"<img src='../Icons/icon003a16.gif'/>",
													"申请修改");
						}
					} else {
						buttonDiv = buttonDiv
								+ ButtonTag.getHtml("save()",
										"article_modify",
										"<img src='../Icons/icon003a16.gif'/>",
										"保存")
								+ ButtonTag.getHtml("publish()",
										"article_modify",
										"<img src='../Icons/icon003a13.gif'/>",
										"发布");
						this.response.put("buttonDiv", buttonDiv);
					}

					this.response.put("buttonDiv", buttonDiv);
				} else if (XString.isNotEmpty(workflowID)) {
					WorkflowAction[] actions = (WorkflowAction[]) null;
					actions = WorkflowUtil.findAvaiableActions(article
							.getWorkFlowID());
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < actions.length; i++) {
						WorkflowAction action = actions[i];
						if (action.getID() == -3) {
							sb.append(ButtonTag.getHtml("ClickMethod='"
									+ action.getName() + "';applyStep('"
									+ article.getWorkFlowID() + "','"
									+ action.getData().getInt("NodeID") + "')",
									null,
									"<img src='../Icons/icon003a16.gif'/>",
									action.getName()));
						} else {
							boolean allowSelectUser = "1".equals(action
									.getData().get("AllowSelectUser"));
							sb.append(ButtonTag.getHtml("ClickMethod='"
									+ action.getName() + "';save('"
									+ article.getWorkFlowID() + "','"
									+ action.getID() + "'," + allowSelectUser
									+ ")", null,
									"<img src='../Icons/icon003a16.gif'/>",
									action.getName()));
						}
					}
					this.response.put("buttonDiv", sb.toString());
				} else {
					buttonDiv = ButtonTag.getHtml("save()", "article_modify",
							"<img src='../Icons/icon003a16.gif'/>", "保存")
							+ ButtonTag.getHtml("topublish()",
									"article_modify",
									"<img src='../Icons/icon003a6.gif'/>",
									"待发布")
							+ ButtonTag.getHtml("publish()", "article_modify",
									"<img src='../Icons/icon003a13.gif'/>",
									"发布");
					this.response.put("buttonDiv", buttonDiv);
				}
			} else {
				this.response.setStatus(0);
				UserLogPage.log("Article", "AddArticle", logMessage + "文章:"
						+ article.getTitle() + "失败！", this.request
						.getClientIP());
				this.response.setMessage("发生错误：文档保存失败！");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.response.setStatus(0);
			UserLogPage.log("Article", "AddArticle", logMessage + "文章失败！",
					this.request.getClientIP());
			this.response.setMessage("发生错误：" + e.getMessage());
			return false;
		}
		return true;
	}

	public static void saveCustomColumn(Transaction trans, Mapx map,
			long catalogID, long articleID, boolean newFlag) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue("2", articleID);
			if (columnDT.getRowCount() > 0) {
				trans
						.add(new QueryBuilder(
								"delete from zdcolumnvalue where relatype=? and relaid = ?",
								"2", articleID));
			}
			trans.add(
					ColumnUtil.getValueFromRequest(catalogID, articleID, map),
					OperateType.INSERT);
		} else {
			trans.add(
					ColumnUtil.getValueFromRequest(catalogID, articleID, map),
					OperateType.INSERT);
		}
	}

	public void checkBadWord() {
		String priority = $V("Priority");
		String title = $V("Title");

		String badMsg = "";
		String badTitle = BadWord.checkBadWord(title, priority);
		if (XString.isNotEmpty(badTitle)) {
			badMsg = badMsg + "标题:" + badTitle + ",";
		}

		String shortTitle = $V("ShortTitle");
		String badShortTitle = BadWord.checkBadWord(shortTitle, priority);
		if (XString.isNotEmpty(badShortTitle)) {
			badMsg = badMsg + "短标题:" + badShortTitle + ",";
		}

		String badSubTitle = BadWord.checkBadWord($V("SubTitle"), priority);
		if (XString.isNotEmpty(badSubTitle)) {
			badMsg = badMsg + "副标题:" + badSubTitle + ",";
		}

		String badAuthor = BadWord.checkBadWord($V("Author"), priority);
		if (XString.isNotEmpty(badAuthor)) {
			badMsg = badMsg + "作者:" + badAuthor + ",";
		}

		String badKeyword = BadWord.checkBadWord($V("Keyword"), priority);
		if (XString.isNotEmpty(badKeyword)) {
			badMsg = badMsg + "关键词:" + badKeyword + ",";
		}

		String badReferName = BadWord.checkBadWord($V("ReferName"), priority);
		if (XString.isNotEmpty(badReferName)) {
			badMsg = badMsg + "来源:" + badReferName + ",";
		}

		String badContent = BadWord.checkBadWord($V("Content"), priority);
		if (XString.isNotEmpty(badContent)) {
			badMsg = badMsg + "正文:" + badContent + ",";
		}

		String badSummary = BadWord.checkBadWord($V("Summary"), priority);
		if (XString.isNotEmpty(badSummary)) {
			badMsg = badMsg + "摘要:" + badSummary + ",";
		}

		if (XString.isNotEmpty(badMsg)) {
			this.response.setStatus(1);
			this.response.put("BadMsg", badMsg);
		} else {
			this.response.setStatus(0);
		}
	}

	private void dealKeyword(Transaction trans, ZCArticleSchema article,
			String keyword) {
		if (XString.isNotEmpty(keyword)) {
			keyword = keyword.trim().replaceAll("\\s+", " ");
			keyword = keyword.replaceAll("，", " ");
			keyword = keyword.replaceAll("；", " ");
			keyword = keyword.replaceAll(";", " ");
			keyword = keyword.replaceAll("\\,+", " ");
			if (",".equals(keyword)) {
				keyword = "";
			} else {
				if (keyword.indexOf(",") == 0) {
					keyword = keyword.substring(1);
				}
				if (keyword.lastIndexOf(",") == keyword.length() - 1)
					keyword = keyword.substring(0, keyword.length() - 1);
			}
		} else {
			String keywordFlag = new QueryBuilder(
					"select keywordFlag from zccatalog where id=?", article
							.getCatalogID()).executeString();
			if ((XString.isNotEmpty(keywordFlag))
					&& (!"N".equals(keywordFlag))) {
				keyword = XString.join(IndexUtil.getKeyword(article
						.getTitle()
						+ " " + article.getContent()), " ");
			}
		}
		article.setKeyword(keyword);
	}

	private void dealTag(Transaction trans, ZCArticleSchema article) {
		String tagword = $V("TagWord");
		if (XString.isNotEmpty(tagword)) {
			tagword = tagword.trim().replaceAll("\\s+", " ");
			tagword = tagword.replaceAll("，", " ");
			tagword = tagword.replaceAll("；", " ");
			tagword = tagword.replaceAll(";", " ");
			tagword = tagword.replaceAll("\\,+", " ");
			if (",".equals(tagword)) {
				tagword = "";
			} else {
				if (tagword.indexOf(",") == 0) {
					tagword = tagword.substring(1);
				}
				if (tagword.lastIndexOf(",") == tagword.length() - 1) {
					tagword = tagword.substring(0, tagword.length() - 1);
				}
			}
			if (XString.isNotEmpty(tagword)) {
				String[] tagwords = tagword.split(" ");
				for (int i = 0; i < tagwords.length; i++) {
					if (Tag.checkTagWord(ApplicationPage.getCurrentSiteID(),
							tagwords[i])) {
						ZCTagSchema tag = new ZCTagSchema();
						tag.setID(NoUtil.getMaxID("TagID"));
						tag.setTag(tagwords[i]);
						tag.setAddTime(new Date());
						tag.setAddUser(User.getUserName());
						tag.setUsedCount(1L);
						tag.setSiteID(ApplicationPage.getCurrentSiteID());
						trans.add(tag, OperateType.INSERT);
					}
				}
			}
		}

		article.setTag(tagword);
	}

	public static void copy(DataCollection dc, Transaction trans,
			ZCArticleSchema article) {
		long articleID = article.getID();
		long catalogID = article.getCatalogID();

		String catalogIDs = dc.getString("ReferTarget");
		String referType = dc.getString("ReferType");

		String otherCatalogID = dc.getString("_C_DestCatalog");
		if (XString.isNotEmpty(otherCatalogID)) {
			if (XString.isEmpty(catalogIDs)) {
				catalogIDs = otherCatalogID;
				referType = "2";
			} else {
				catalogIDs = catalogIDs + "," + otherCatalogID;
			}

		}

		if (XString.isEmpty(referType)) {
			referType = "1";
		}
		int refer = Integer.parseInt(referType);
		if (XString.isNotEmpty(catalogIDs)) {
			String[] catalogArr = catalogIDs.split(",");
			String copySiteID = null;
			for (int j = 0; j < catalogArr.length; j++) {
				long toCatalogID = Long.parseLong(catalogArr[j]);
				if (toCatalogID == article.getCatalogID()) {
					continue;
				}
				ZCArticleSchema articleRefer = new ZCArticleSchema();
				articleRefer.setReferSourceID(articleID);
				articleRefer.setCatalogID(toCatalogID);
				ZCArticleSet set = articleRefer.query();

				if ((set != null) && (set.size() > 0)) {
					articleRefer = set.get(0);
					articleRefer.setTitle(article.getTitle());
					articleRefer.setShortTitle(article.getShortTitle());
					articleRefer.setSubTitle(article.getSubTitle());
					articleRefer.setAuthor(article.getAuthor());
					articleRefer.setKeyword(article.getKeyword());
					articleRefer.setTag(article.getTag());
					articleRefer.setSummary(articleRefer.getSummary());
					articleRefer.setReferTarget("");

					if (refer == 1) {
						articleRefer.setContent(article.getContent());
					}

					if (Priv.getPriv("article", CatalogUtil
							.getInnerCode(toCatalogID), "article_modify")) {
						dealRelaImage(trans, articleRefer);

						dealRelaAttach(trans, articleRefer);

						dealRelaVideo(trans, articleRefer);

						dealVote(articleRefer, trans);

						if (XString.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, articleRefer.getID()+"", dc
									.getString("NoteContent"));
						}

						saveCustomColumn(trans, dc, toCatalogID, articleRefer
								.getID(), false);
					}

					trans.add(articleRefer, OperateType.UPDATE);
				} else {
					ZCArticleSchema articleColone = (ZCArticleSchema) article
							.clone();
					articleColone.setID(NoUtil.getMaxID("DocID"));

					articleColone.setCatalogID(toCatalogID);
					articleColone.setCatalogInnerCode(CatalogUtil
							.getInnerCode(toCatalogID));
					articleColone.setReferType(refer);
					articleColone.setReferSourceID(articleID);
					articleColone.setReferTarget("");

					if (XString.isEmpty(copySiteID)) {
						copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
					}
					articleColone.setSiteID(copySiteID);

					articleColone.setTopFlag("0");
					articleColone.setTemplateFlag("0");
					articleColone.setTemplate("");

					if (refer == 2) {
						articleColone.setType("4");
						String referURL = (SiteUtil.getURL(articleColone
								.getSiteID())
								+ "/"
								+ CatalogUtil.getPath(catalogID)
								+ articleID + ".shtml").replaceAll("///", "/")
								.replaceAll("//", "/");
						articleColone.setRedirectURL(referURL);
						articleColone.setContent("");
					}

					if (Priv.getPriv("article", CatalogUtil
							.getInnerCode(toCatalogID), "article_modify")) {
						dealRelaImage(trans, articleColone);

						dealRelaAttach(trans, articleColone);

						dealRelaVideo(trans, articleColone);

						dealVote(articleColone, trans);

						if (XString.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, articleColone.getID()+"", dc
									.getString("NoteContent"));
						}

						saveCustomColumn(trans, dc, toCatalogID, articleColone
								.getID(), true);
					}

					trans.add(articleColone, OperateType.INSERT);
				}

				String sqlArticleCount = "update zccatalog set total=total+1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, catalogArr[j]));

				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(article.getID());
				articleLog.setAction("COPY");
				articleLog.setActionDetail("复制。从"
						+ CatalogUtil.getName(article.getCatalogID()) + "复制到"
						+ CatalogUtil.getName(toCatalogID) + "。");
				articleLog.setAddUser(User.getUserName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, OperateType.INSERT);
			}
		}
	}

	public static void dealRelaImage(Transaction trans, ZCArticleSchema article) {
		String regex = "zcmsimagerela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String imageID = picSrc.substring("zcmsimagerela=".length() + 1,
					picSrc.length() - 1);
			if (XString.isDigit(imageID)) {
				map.put(imageID, imageID);
			}
			flag = true;
		}
		String[] IDs = null;
		int i = 0;
		if ((flag) && (map.size() > 0)) {
			String str = XString.join(map.keyArray(), ",");
			if (!XString.checkID(str)) {
				LogUtil.warn("Article.dealRelaImage:相关图片ID中有恶意字符");
				return;
			}
			trans.add(new QueryBuilder(
					"delete from zcimagerela where relaid=? and relaType=?",
					article.getID(), "ArticleImage"));

			String siteUrl = SiteUtil.getURL(article.getSiteID());
			if (siteUrl.endsWith("shtml")) {
				siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
			}

			if (!siteUrl.endsWith("/")) {
				siteUrl = siteUrl + "/";
			}
			trans.add(new QueryBuilder("update zcimage set linkurl='" + siteUrl
					+ PubFun.getArticleURL(article) + "',linktext='"
					+ article.getTitle() + "' where ID in (" + str + ")"));
			IDs = str.split(",");
			i = 0;
		}
		while (true) {
			if ((!XString.isEmpty(IDs[i])) && (XString.isDigit(IDs[i]))) {
				ZCImageRelaSchema rela = new ZCImageRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType("ArticleImage");
				rela.setOrderFlag(OrderUtil.getDefaultOrder());
				rela.setAddUser(User.getUserName());
				rela.setAddTime(new Date());
				trans.add(rela, OperateType.INSERT);
			}
			i++;
			if (IDs == null)
				break;
			if (i < IDs.length)
				continue;

			trans.add(new QueryBuilder(
					"delete from zcimagerela where relaid=? and relaType=?",
					article.getID(), "ArticleImage"));
		}
	}

	public static void dealRelaAttach(Transaction trans, ZCArticleSchema article) {
		if (XString.isEmpty(article.getContent())) {
			return;
		}

		String regex = "zcmsattachrela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String attachID = picSrc.substring("zcmsattachrela=".length() + 1,
					picSrc.length() - 1);
			if (XString.isDigit(attachID)) {
				map.put(attachID, attachID);
			}
			flag = true;
		}

		if (flag) {
			String str = XString.join(map.keyArray(), ",");
			if (!XString.checkID(str)) {
				LogUtil.warn("Article.dealRelaVideo:相关附件ID中有恶意字符");
				return;
			}
			if (XString.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder(
					"delete from zcattachmentrela where relaid=? and relaType=? and ID not in ("
							+ str + ")", article.getID(), "ArticleAttach"));
			String[] IDs = str.split(",");
			int i = 0;
			do {
				ZCAttachmentRelaSchema rela = new ZCAttachmentRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType("ArticleAttach");
				if (!rela.fill())
					trans.add(rela, OperateType.INSERT);
				i++;
				if (IDs == null)
					break;
			} while (i < IDs.length);
		} else {
			trans
					.add(new QueryBuilder(
							"delete from ZCAttachmentRela where relaid=? and relaType=?",
							article.getID(), "ArticleAttach"));
		}
	}

	public static void dealRelaVideo(Transaction trans, ZCArticleSchema article) {
		if (XString.isEmpty(article.getContent())) {
			return;
		}

		String regex = "zcmsvideorela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String videoID = picSrc.substring("zcmsvideorela=".length() + 1,
					picSrc.length() - 1);
			if (XString.isDigit(videoID)) {
				map.put(videoID, videoID);
			}
			flag = true;
		}

		if (flag) {
			String str = XString.join(map.keyArray(), ",");
			if (!XString.checkID(str)) {
				LogUtil.warn("Article.dealRelaVideo:相关附件ID中有恶意字符");
				return;
			}
			if (XString.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder(
					"delete from zcvideorela where relaid=? and relaType=? and ID not in ("
							+ str + ")", article.getID(), "ArticleVideo"));
			String[] IDs = str.split(",");
			int i = 0;
			do {
				ZCVideoRelaSchema rela = new ZCVideoRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType("ArticleVideo");
				if (!rela.fill())
					trans.add(rela, OperateType.INSERT);
				i++;
				if (IDs == null)
					break;
			} while (i < IDs.length);
		} else {
			trans.add(new QueryBuilder(
					"delete from zcvideorela where relaid=? and relaType=?",
					article.getID(), "ArticleVideo"));
		}
	}

	private boolean getRemoteFile(String destUrl, String fileName) {
		if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) {
			System.setProperty("http.proxyHost", Config.getValue("Proxy.Host"));
			System.setProperty("http.proxyPort", Config.getValue("Proxy.Port"));
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Config
							.getValue("Proxy.UserName"), new String(Config
							.getValue("Proxy.Password")).toCharArray());
				}
			});
		}
		try {
			byte[] buf = new byte[8096];
			int size = 0;

			URL url = new URL(destUrl);

			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			bis.toString();

			FileOutputStream fos = new FileOutputStream(fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			bis.close();
		} catch (MalformedURLException e) {
			System.out.println("非法地址");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("没有找到对应的文件");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String copyRemoteFiles(String content, Transaction trans,
			long articleID) {
		Pattern varPattern = Pattern.compile(
				"src=([\"|'| ])*?(http.*?\\.(gif|jpg|jpeg|bmp|png))\\1", 34);

		Matcher m = varPattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int s = m.start();
			int e = m.end();
			sb.append(content.substring(lastEndIndex, s));
			String imagePath = m.group(2);
			String fileName = imagePath
					.substring(imagePath.lastIndexOf("/") + 1);
			String tempDir = "defaultTemp/";
			String local = Config.getContextRealPath()
					+ Config.getValue("UploadDir") + "/" + this.siteAlias
					+ "/upload/Image/";
			File file = new File(local + tempDir);
			if (!file.exists()) {
				file.mkdirs();
			}

			String imageID = "";
			if (getRemoteFile(imagePath, local + tempDir + fileName)) {
				ZCImageSchema image = AutoUpload.dealImage(local, fileName,
						trans);
				if (XString.isNotEmpty(image.getFileName())) {
					String uploadPath = Config.getContextPath()
							+ Config.getValue("UploadDir") + "/"
							+ this.siteAlias + "/" + image.getPath();
					imagePath = uploadPath.replaceAll("//", "/")
							+ image.getFileName();
					System.out.println("复制成功" + m.group(2));
				} else {
					System.out.println("复制失败" + imagePath);
				}
				imageID = image.getID()+"";
			} else {
				System.out.println("复制失败" + imagePath);
			}
			sb.append("zcmsimagerela=\"" + imageID + "\" src=\"" + imagePath
					+ "\"");
			lastEndIndex = e;
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public void saveVersion() {
		String articleID = $V("ArticleID");
		long id = Long.parseLong(articleID);
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(id);

		if (!article.fill()) {
			article.setID(articleID);
			article.setValue(this.request);
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);

			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);

			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setHitCount(0L);
			article.setPublishFlag("Y");
			article.setStickTime(0L);

			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setAddTime(new Date());
			article.setAddUser(User.getUserName());
		}

		Transaction trans = new Transaction();
		trans.add(article, OperateType.BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.put("SaveTime", DateUtil.getCurrentDateTime());
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void topublish() {
		String ArticleID = $V("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(ArticleID);
		if (!article.fill()) {
			this.response.setLogInfo(0, "请先保存文档，再进行此项操作");
			return;
		}
		if (((article.getStatus() == 0L) || (article.getStatus() == 60L))
				&& (article.getWorkFlowID() == 0L)) {
			ZCArticleSet set = article
					.query(new QueryBuilder(
							"where workflowid is null and  status in (0,60) and id ="
									+ ArticleID
									+ " or id in(select id from zcarticle where refersourceid ="
									+ ArticleID + " )"));
			for (int i = 0; i < set.size(); i++) {
				set.get(i).setStatus(20L);
			}
			set.update();
			UserLogPage.log("Article", "ToPublishArticle", "转为待发布操作成功,id:"
					+ ArticleID, this.request.getClientIP());
			this.response.setLogInfo(1, "转为待发布操作成功。");
		} else if (article.getWorkFlowID() != 0L) {
			this.response.setLogInfo(0, "操作失败，此文章的状态为："
					+ STATUS_MAP.getString(new StringBuffer(String
							.valueOf(article.getStatus())).toString())
					+ "，此文档在工作流转中，不能转为待发布");
		} else {
			this.response.setLogInfo(0, "操作失败，此文章的状态为："
					+ STATUS_MAP.getString(new StringBuffer(String
							.valueOf(article.getStatus())).toString())
					+ "，只有‘初稿’和‘重新编辑’的文章转为待发布状态了");
		}
	}

	public boolean publish() {
		ZCArticleSchema article = new ZCArticleSchema();
		int articleID = Integer
				.parseInt((String) this.request.get("ArticleID"));

		ZCArticleSet set = new ZCArticleSet();
		article.setID(articleID);
		if (article.fill()) {
			set.add(article);
		}
		ZCArticleSet referset = article.query(new QueryBuilder(
				" where refersourceid=? ", articleID));
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

		Transaction trans = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			Date date = new Date();
			ZCArticleSchema article2 = set.get(i);
			QueryBuilder qb = new QueryBuilder(
					"update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
			if (article2.getPublishDate() == null) {
				qb = new QueryBuilder(
						"update zcarticle set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				article2.setPublishDate(date);
			}
			qb.add(User.getUserName());
			qb.add(date);
			if (article2.getPublishDate().getTime() > System
					.currentTimeMillis())
				qb.add(20);
			else {
				qb.add(30);
			}
			qb.add(article2.getID());
			try {
				qb.executeNoQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			article2.setModifyUser(User.getUserName());
			article.setModifyTime(date);
			article.setStatus(30L);
		}

		UserLogPage.log("Article", "PublishArticle", "发布文章:" + article.getTitle()
				+ "成功！", this.request.getClientIP(), trans);

		if (trans.commit()) {
			PublishMonitor m = new PublishMonitor();
			m.addJob(set);

			this.response.setStatus(1);
			return true;
		}
		this.response.setStatus(0);
		return false;
	}

	public void saveAndPublish() {
		if ((save()) && (!publish())) {
			this.response.setStatus(1);
			this.response.setMessage("操作成功，" + this.response.Message);
		}
	}

	public void getArticle() {
		ZCArticleSchema article = new ZCArticleSchema();
		long articleID = Long.parseLong((String) this.request.get("ArticleID"));
		article.setID(articleID);
		if (article.fill()) {
			String content = article.getContent();
			String[] pages = content.split("<!--_SKY_PAGE_BREAK_-->", -1);
			StringBuffer pageStr = new StringBuffer();
			for (int i = 0; i < pages.length; i++) {
				pageStr.append("'" + XString.htmlEncode(pages[i]) + "'");
				if (i != pages.length - 1) {
					pageStr.append(",");
				}
			}

			this.response.setStatus(1);
			this.response.put("NewsType", article.getType());
			this.response.put("TopFlag", article.getTopFlag());
			this.response.put("CommentFlag", article.getCommentFlag());
			this.response.put("Priority", article.getPriority());
			this.response.put("TemplateFlag", article.getTemplate());
			this.response.put("ShortTitle", article.getShortTitle());
			this.response.put("Title", article.getTitle());
			this.response.put("SubTitle", article.getSubTitle());
			this.response.put("Keyword", article.getKeyword());
			this.response.put("Summary", article.getSummary());
			this.response.put("Template", article.getTemplate());
			this.response.put("Author", article.getAuthor());
			this.response.put("RedirectURL", article.getRedirectURL());
			this.response.put("TagWord", article.getTag());

			this.response.put("Pages", new Integer(pages.length));
			this.response.put("ContentPages", pageStr.toString());
		}
	}

	public void getPlayerName() {
		String RelaImagePlayerID = $V("RelaImagePlayerID");
		String PlayerName = (String)new QueryBuilder(
				"select Name from zcimageplayer where ID = ?",
				RelaImagePlayerID).executeOneValue();

		PlayerName = "[" + PlayerName + "]";
		this.response.put("PlayerName", PlayerName);
	}

	public void getPicSrc() {
		String ids = $V("PicID");
		String customFlag = $V("Custom");
		String isRela = $V("isRela");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(ids)) || (!XString.checkID(catalogID))) {
			return;
		}
		if (XString.isEmpty(ids)) {
			ids = "''";
		}
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder(
				" where id in (" + ids + ") order by id desc"));

		String path = Config.getContextPath() + Config.getValue("UploadDir")
				+ "/" + this.siteAlias;
		path = path.replaceAll("/+", "/");
		StringBuffer images = new StringBuffer();

		if ((XString.isNotEmpty(customFlag)) && (imageSet.size() > 0)) {
			images.append(path + "/" + imageSet.get(0).getPath() + "1_"
					+ imageSet.get(0).getFileName());
			this.response.put("1_picSrc", images.toString());
			images = new StringBuffer();
			images.append(path + "/" + imageSet.get(0).getPath() + "s_"
					+ imageSet.get(0).getFileName());
			this.response.put("s_picSrc", images.toString());
		} else {
			for (int i = 0; i < imageSet.size(); i++) {
				if (XString.isEmpty(isRela)) {
					images.append("<p style='text-align: center;'>");
					if (((XString.isNotEmpty(Config
							.getValue("ArticleImageWidth"))) && (imageSet
							.get(i).getWidth() > Integer.parseInt(Config
							.getValue("ArticleImageWidth"))))
							|| ((XString.isNotEmpty(Config
									.getValue("ArticleImageHeight"))) && (imageSet
									.get(i).getHeight() > Integer
									.parseInt(Config
											.getValue("ArticleImageHeight"))))) {
						images.append("<a alt='点击查看大图' href='" + path + "/"
								+ imageSet.get(i).getPath()
								+ imageSet.get(i).getSrcFileName()
								+ "' target='_blank' >");
					}
					images.append("<img border=0 zcmsimagerela='"
							+ imageSet.get(i).getID() + "' src='" + path + "/"
							+ imageSet.get(i).getPath() + "1_"
							+ imageSet.get(i).getFileName() + "'>");
					if (((XString.isNotEmpty(Config
							.getValue("ArticleImageWidth"))) && (imageSet
							.get(i).getWidth() > Integer.parseInt(Config
							.getValue("ArticleImageWidth"))))
							|| ((XString.isNotEmpty(Config
									.getValue("ArticleImageHeight"))) && (imageSet
									.get(i).getHeight() > Integer
									.parseInt(Config
											.getValue("ArticleImageHeight"))))) {
						images.append("</a>");
					}
					images.append("</p>");
					images.append("<p style='text-align: center;'>"
							+ imageSet.get(i).getName() + "</p>");
				} else {
					images
							.append("<img src='"
									+ path
									+ "/"
									+ imageSet.get(i).getPath()
									+ "s_"
									+ imageSet.get(i).getFileName()
									+ "' width='80' onload=\"if(this.width>80)this.width='80';\" >");
				}
			}
			this.response.put("1_picSrc", images.toString());
		}
	}

	public void getFilePath() {
		String ids = $V("FileID");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(ids)) || (!XString.checkID(catalogID))) {
			return;
		}
		if (XString.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename from zcattachment where id in ("
						+ ids + ")").executeDataTable();

		StringBuffer filePath = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String path = Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ ApplicationPage.getCurrentSiteAlias();
			path = path.replaceAll("/+", "/");
			if ((XString.isNotEmpty(catalogID))
					&& ("N".equals(CatalogUtil.getAttachDownFlag(catalogID))))
				filePath.append("<a href='"
						+ new StringBuffer(String.valueOf(path)).append("/")
								.append(dt.getString(i, "path")).append(
										dt.getString(i, "filename")).toString()
								.replaceAll("/+", "/") + "' zcmsattachrela='"
						+ dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "."
						+ dt.getString(i, "suffix") + "</a>");
			else if ("N".equals(SiteUtil.getAttachDownFlag(ApplicationPage
					.getCurrentSiteID())))
				filePath.append("<a href='"
						+ new StringBuffer(String.valueOf(path)).append("/")
								.append(dt.getString(i, "path")).append(
										dt.getString(i, "filename")).toString()
								.replaceAll("/+", "/") + "' zcmsattachrela='"
						+ dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "."
						+ dt.getString(i, "suffix") + "</a>");
			else {
				filePath.append("<a href='" + Config.getContextPath()
						+ "Services/AttachDownLoad.jsp?id="
						+ dt.getString(i, "id") + "' zcmsattachrela='"
						+ dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "."
						+ dt.getString(i, "suffix") + "</a>");
			}
		}
		this.response.put("FilePath", filePath.toString());
	}

	public void getFlashPath() {
		String ids = $V("FlashID");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(ids)) || (!XString.checkID(catalogID))) {
			return;
		}
		if (XString.isEmpty(ids)) {
			ids = "''";
		}
		String path = Config.getContextPath() + Config.getValue("UploadDir")
				+ "/" + ApplicationPage.getCurrentSiteAlias();
		path = path.replaceAll("/+", "/");
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename from zcattachment where id in ("
						+ ids + ")").executeDataTable();

		StringBuffer flashs = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			flashs
					.append("<embed src='"
							+ new StringBuffer(String.valueOf(path))
									.append("/").append(dt.get(i, "Path"))
									.append(dt.get(i, "FileName")).toString()
									.replaceAll("/+", "/")
							+ "' type='application/x-shockwave-flash' width='320' height='240' play='true' loop='true' menu='true'></embed>");
		}
		this.response.put("FlashPath", flashs.toString());
	}

	public void getVideoPath() {
		String ids = $V("VideoID");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(ids)) || (!XString.checkID(catalogID))) {
			return;
		}
		if (XString.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename,imageName from zcvideo where id in ("
						+ ids + ")").executeDataTable();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String fileName = "../" + dt.getString(i, "path")
					+ dt.getString(i, "filename");
			String imageName = "../" + dt.getString(i, "path")
					+ dt.getString(i, "imageName");
			String playerPath = SiteUtil.getURL(CatalogUtil
					.getSiteID(catalogID))
					+ "/images/player.swf";
			fileName = fileName.replaceAll("/+", "/");
			imageName = imageName.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll("http://", XString
					.md5Hex("http://"));
			playerPath = playerPath.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll(XString.md5Hex("http://"),
					"http://");
			sb
					.append("<div style='text-align: center;'><embed wmode=\"transparent\" ");
			sb.append("flashvars=\"file=" + fileName + "&image=" + imageName
					+ "&stretching=fill\"");
			sb
					.append(" zcmsvideorela=\""
							+ dt.getString(i, "id")
							+ "\" src=\""
							+ playerPath
							+ "\" quality=\"high\" allowfullscreen=\"true\" type=\"application/x-shockwave-flash\"");
			sb.append(" width=\"480\" height=\"360\"></embed><br/>");
			sb.append(dt.getString(i, "Name") + "<br/></div>");
		}
		this.response.put("VideoPath", sb.toString());
	}

	public void getKeywordOrSummary() {
		String type = $V("Type");
		String title = $V("Title");
		String content = $V("Content");
		if ("Keyword".equals(type))
			$S("Text", XString.join(IndexUtil.getKeyword(title + " "
					+ content), " "));
		else
			$S("Text", IndexUtil.getTextAbstract(title, content));
	}

	public static String getKeywordSetting(String catalogID) {
		String flag = CatalogUtil.getSchema(catalogID).getKeywordFlag();
		if (!"Y".equals(flag)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			if (innerCode.length() > 6) {
				innerCode = innerCode.substring(0, innerCode.length() - 6);
				flag = CatalogUtil.getSchema(
						CatalogUtil.getIDByInnerCode(innerCode))
						.getKeywordFlag();
				return CatalogUtil.getSchema(
						CatalogUtil.getIDByInnerCode(innerCode))
						.getKeywordSetting();
			}
			return null;
		}

		return CatalogUtil.getSchema(catalogID).getKeywordSetting();
	}

	public void getAutoRelaIDs() {
		String catalogID = $V("CatalogID");
		String articleID = $V("ArticleID");
		String title = $V("Title");
		String content = $V("Content");
		String keyword = $V("Keyword");
		String setting = getKeywordSetting(catalogID);
		if (setting == null) {
			this.response.setError("不能进行智能相关文章匹配，请为本栏目开启“抽取关键词并自动关联文章”");
			return;
		}
		if (XString.isEmpty(keyword)) {
			keyword = XString.join(IndexUtil.getKeyword(title + " "
					+ content), " ");
		}
		SearchResult sr = null;
		if (setting.equals(""))
			sr = ArticleRelaIndexer.getRelaArticles(catalogID, articleID, "",
					title, keyword);
		else {
			sr = ArticleRelaIndexer.getRelaArticles(articleID, setting, title,
					keyword);
		}
		StringBuffer sb = new StringBuffer();
		if ((sr != null) && (sr.Data != null)) {
			for (int i = 0; i < sr.Data.getRowCount(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(sr.Data.getString(i, "ID"));
			}
		}
		$S("IDs", sb.toString());
	}

	public void getJSCode() {
		String ids = $V("ID");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(ids)) || (!XString.checkID(catalogID))) {
			return;
		}
		if (XString.isEmpty(ids)) {
			ids = "''";
		}
		ZCVoteSet set = new ZCVoteSchema().query(new QueryBuilder(
				"where id in (" + ids + ")"));
		StringBuffer jsCodes = new StringBuffer();
		String levelStr = PubFun.getLevelStr(CatalogUtil
				.getDetailLevel(catalogID));
		for (int i = 0; i < set.size(); i++) {
			ZCVoteSchema vote = set.get(i);
			String code = "";
			code = code + "<div>调查：" + vote.getTitle() + "\n";
			code = code + "<!--" + vote.getTitle() + "-->\n";
			code = code
					+ "<link href=\""
					+ levelStr
					+ "images/vote.css\" type=\"text/css\" rel=\"stylesheet\" />";
			code = code + "<script language='javascript' src='" + levelStr
					+ "images/vote.js'></script>";
			code = code
					+ "<script language='javascript' src='"
					+ new StringBuffer(String.valueOf(levelStr)).append(
							"js/vote_").append(vote.getID()).toString()
							.replaceAll("/+", "/") + ".js'></script>";
			code = code + "\n</div>";
			jsCodes.append(code);

			if (!new File(new StringBuffer(String.valueOf(Config
					.getContextRealPath())).append(
					Config.getValue("Statical.TargetDir")).append("/").append(
					SiteUtil.getAlias(vote.getSiteID())).append("/js/")
					.toString().replaceAll("/+", "/")
					+ "vote_" + vote.getID() + ".js").exists()) {
				Vote.generateJS(vote.getID());
			}
		}
		$S("JSCode", jsCodes.toString());
	}

	public void verifySameTitle() {
		String Title = $V("Title");
		String CatalogID = $V("CatalogID");
		QueryBuilder qb = new QueryBuilder(
				"select count(*) from ZCArticle where CatalogID=? and Title=? and ID<>?",
				CatalogID, Title);
		qb.add($V("ID"));
		int Count = qb.executeInt();
		this.response.setStatus(Count);
	}
}

/*
 * com.xdarkness.cms.document.Article JD-Core Version: 0.6.0
 */