 package com.zving.cms.site;
 
 import com.zving.cms.document.Article;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCMagazineIssueSchema;
 import com.zving.schema.ZCMagazineIssueSet;
 import com.zving.schema.ZCMagazineSchema;
 import java.util.Date;
 
 public class MagazineIssue extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     long magazineID = Long.parseLong(dga.getParam("MagazineID"));
     QueryBuilder qb = new QueryBuilder(
       "select ID,MagazineID,year,PeriodNum,CoverImage,Status,Memo,publishDate as pubDate,addtime from ZCMagazineIssue where magazineID=? order by ID desc", 
       magazineID);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     if ((dt != null) && (dt.getRowCount() > 0)) {
       dt.decodeColumn("Status", Article.STATUS_MAP);
       dt.getDataColumn("pubDate").setDateFormat("yy-MM-dd");
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 
   public static Mapx init(Mapx params) {
     return params;
   }
 
   public static Mapx initDialog(Mapx params) {
     String magazineIssueID = params.getString("ID");
     String magazineID = params.getString("MagazineID");
     String coverImage = "upload/Image/nopicture.jpg";
     if (StringUtil.isNotEmpty(magazineIssueID)) {
       ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
       magazineIssue.setID(magazineIssueID);
       magazineIssue.fill();
       params = magazineIssue.toMapx();
       coverImage = magazineIssue.getCoverImage();
       params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + coverImage);
     } else {
       params.put("SiteID", Application.getCurrentSiteID());
       params.put("CoverImage", coverImage);
       params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + coverImage);
       if (StringUtil.isNotEmpty(magazineID)) {
         DataTable catalogDt = new QueryBuilder(
           "select * from zccatalog where parentid=(select max(id) from zccatalog where parentid=?)", 
           magazineID).executeDataTable();
         if (catalogDt != null) {
           StringBuffer sb = new StringBuffer();
           for (int i = 0; i < catalogDt.getRowCount(); ++i) {
             sb.append("<input type=\"checkbox\" name=\"catalog\" value=\"" + catalogDt.getString(i, "id") + 
               "\" checked>" + catalogDt.getString(i, "name"));
             if (i == 3) {
               sb.append("<br>");
             }
           }
           params.put("LastCatalog", sb.toString());
         }
       }
     }
     return params;
   }
 
   public void add() {
     long magazineID = Long.parseLong($V("MagazineID"));
     Transaction trans = new Transaction();
 
     Catalog catalog = new Catalog();
     this.Request.put("Name", $V("Year") + "年第" + $V("PeriodNum") + "期");
     this.Request.put("ParentID", magazineID);
     this.Request.put("Alias", $V("Year") + $V("PeriodNum"));
     this.Request.put("Type", "3");
     this.Request.put("ImagePath", $V("CoverImage"));
 
     ZCCatalogSchema catalogSchema = catalog.add(this.Request, trans);
 
     ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
     issue.setID(catalogSchema.getID());
     issue.setValue(this.Request);
     issue.setAddTime(new Date());
     issue.setAddUser(User.getUserName());
     issue.setStatus(1L);
     trans.add(issue, 1);
 
     QueryBuilder qb = new QueryBuilder("select DetailTemplate,ListTemplate from ZCCatalog where Type=? and ParentID=? order by ID desc", 
       3L, issue.getMagazineID());
     DataTable dt = qb.executePagedDataTable(1, 0);
     if (dt.getRowCount() > 0) {
       if (StringUtil.isNotEmpty(dt.getString(0, "ListTemplate")))
         catalogSchema.setListTemplate(dt.getString(0, "ListTemplate"));
       else {
         catalogSchema.setListTemplate("/template/list.html");
       }
       if (StringUtil.isNotEmpty(dt.getString(0, "DetailTemplate")))
         catalogSchema.setDetailTemplate(dt.getString(0, "DetailTemplate"));
       else
         catalogSchema.setDetailTemplate("/template/detail.html");
     }
     else {
       catalogSchema.setListTemplate("/template/list.html");
       catalogSchema.setDetailTemplate("/template/detail.html");
     }
     ZCMagazineSchema magazine = new ZCMagazineSchema();
     magazine.setID(magazineID);
     if (magazine.fill()) {
       long totalissue = magazine.getTotal();
       String currentyear = "";
       String periodnum = "";
       String coverimage = "";
       if ((StringUtil.isEmpty(magazine.getCurrentYear())) || (Long.parseLong($V("Year")) > Long.parseLong(magazine.getCurrentYear()))) {
         currentyear = $V("Year");
         periodnum = $V("PeriodNum");
         coverimage = $V("CoverImage");
       } else {
         currentyear = magazine.getCurrentYear();
         coverimage = magazine.getCoverImage();
         periodnum = (Long.parseLong($V("PeriodNum")) > Long.parseLong(magazine.getCurrentPeriodNum())) ? $V("PeriodNum") : magazine.getCurrentPeriodNum();
       }
       magazine.setTotal(totalissue + 1L);
       magazine.setCurrentYear(currentyear);
       magazine.setCurrentPeriodNum(periodnum);
       magazine.setCoverImage(coverimage);
       magazine.setModifyTime(new Date());
       magazine.setModifyUser(User.getUserName());
       trans.add(magazine, 2);
     }
 
     trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), magazineID));
 
     if (trans.commit()) {
       CatalogUtil.update(catalogSchema.getID());
       if (StringUtil.isNotEmpty($V("CatalogIDs"))) {
         if (!StringUtil.checkID($V("CatalogIDs"))) {
           this.Response.setStatus(0);
           this.Response.setMessage("发生错误!");
           return;
         }
         ZCCatalogSchema catalogLastIssue = new ZCCatalogSchema();
         if (!StringUtil.checkID($V("CatalogIDs"))) {
           return;
         }
         ZCCatalogSet set = catalogLastIssue.query(new QueryBuilder("where id in(" + $V("CatalogIDs") + ")"));
         for (int i = 0; i < set.size(); ++i) {
           catalog.add(catalogSchema, set.get(i), trans);
         }
         trans.commit();
       }
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void edit() {
     long magazineID = Long.parseLong($V("MagazineID"));
     Transaction trans = new Transaction();
 
     ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
     issue.setID(Long.parseLong($V("ID")));
     if (!issue.fill()) {
       this.Response.setStatus(0);
       this.Response.setMessage("没有找到期号!");
       return;
     }
     issue.setValue(this.Request);
     issue.setModifyTime(new Date());
     issue.setModifyUser(User.getUserName());
     issue.setStatus(1L);
     trans.add(issue, 2);
 
     ZCMagazineSchema magazine = new ZCMagazineSchema();
     magazine.setID(magazineID);
     if (magazine.fill()) {
       magazine.setTotal(magazine.getTotal() + 1L);
       magazine.setCurrentYear($V("Year"));
       magazine.setCurrentPeriodNum($V("PeriodNum"));
       magazine.setCoverImage($V("CoverImage"));
       magazine.setModifyTime(new Date());
       magazine.setModifyUser(User.getUserName());
       trans.add(magazine, 2);
     }
     trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), magazineID));
     trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), issue.getID()));
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     if ((ids.indexOf("\"") >= 0) || (ids.indexOf("'") >= 0)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCMagazineIssueSchema MagazineIssue = new ZCMagazineIssueSchema();
     ZCMagazineIssueSet set = MagazineIssue.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
 
     String[] idArray = ids.split("\\,");
     for (int i = 0; i < idArray.length; ++i) {
       Catalog.deleteCatalog(trans, Long.parseLong(idArray[i]));
     }
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void getPicSrc() {
     String ID = $V("PicID");
     String id = $V("ID");
     DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", ID).executeDataTable();
     if (dt.getRowCount() > 0) {
       this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_" + 
         dt.get(0, "filename").toString());
       this.Response.put("CoverImage", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
     }
     Transaction trans = new Transaction();
     ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
     if (StringUtil.isNotEmpty(id)) {
       magazineIssue.setID(id);
       magazineIssue.fill();
       magazineIssue.setValue(this.Request);
       magazineIssue.setCoverImage((String)this.Response.get("CoverImage"));
       trans.add(magazineIssue, 2);
       trans.commit();
     } else {
       return;
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.MagazineIssue
 * JD-Core Version:    0.5.4
 */