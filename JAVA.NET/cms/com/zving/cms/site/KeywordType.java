 package com.zving.cms.site;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCKeywordSchema;
 import com.zving.schema.ZCKeywordTypeSchema;
 import java.util.Date;
 
 public class KeywordType extends Page
 {
   public void add()
   {
     String typeName = $V("TypeName").trim();
     if (new QueryBuilder("select count(*) from ZCKeyWordType where SiteID=? and TypeName=?", 
       Application.getCurrentSiteID(), typeName).executeInt() == 0) {
       ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
       keywordType.setID(NoUtil.getMaxID("KeyWordTypeID"));
       keywordType.setTypeName(typeName);
       keywordType.setSiteID(Application.getCurrentSiteID());
       keywordType.setAddTime(new Date());
       keywordType.setAddUser(User.getUserName());
       if (keywordType.insert()) {
         this.Response.setStatus(1);
         this.Response.setMessage("新增成功！");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("已经存在的分类!");
     }
   }
 
   public static DataTable loadType(Mapx params) {
     DataTable dt = new DataTable();
     dt = new QueryBuilder("select ID,TypeName from ZCKeywordType where SiteID = ?", 
       Application.getCurrentSiteID()).executeDataTable();
 
     return dt;
   }
 
   public static void loadTypeTree(TreeAction ta) {
     String ID = ta.getParam("ID");
     String selectedCID = ta.getParam("selectedCID");
     if (StringUtil.isEmpty(selectedCID)) {
       selectedCID = null;
     }
     ZCKeywordSchema keyword = new ZCKeywordSchema();
     keyword.setID(ID);
     DataTable dt = null;
     QueryBuilder qb = null;
     if ((StringUtil.isNotEmpty(ID)) && (keyword.fill())) {
       qb = new QueryBuilder(
         "select ID,TypeName,(select 'Checked' from ZCKeyword k where k.ID = ? and ZCKeywordType.ID in (" + 
         keyword.getKeywordType().substring(1, keyword.getKeywordType().length() - 1) + ")) as Checked " + 
         "from ZCKeywordType where SiteID = ? ");
       qb.add(ID);
       qb.add(Application.getCurrentSiteID());
     } else {
       qb = new QueryBuilder(
         "select ID,TypeName,(select 'Checked' from ZCKeywordType k where k.ID=ZCKeywordType.ID and k.ID = ?) as Checked from ZCKeywordType where SiteID = ?", 
         selectedCID, Application.getCurrentSiteID());
     }
     dt = qb.executeDataTable();
     ta.setRootText("请选择类别");
     ta.bindData(dt);
   }
 
   public void del() {
     Transaction trans = new Transaction();
     String ID = $V("ID");
     ZCKeywordTypeSchema keyWordType = new ZCKeywordTypeSchema();
     keyWordType.setID(ID);
     trans.add(keyWordType, 3);
     trans.add(new QueryBuilder("update ZCKeyword set KeywordType = replace(KeywordType,'," + ID + ",',',') where KeywordType like ?", "%," + ID + ",%"));
     trans.add(new QueryBuilder("delete from ZCKeyword where KeywordType = ?", ID));
 
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功!");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void edit() {
     String ID = $V("ID");
     ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
     keywordType.setID(ID);
     String typeName = $V("TypeName").trim();
     if ((keywordType.fill()) && 
       (new QueryBuilder("select count(*) from ZCKeyWordType where SiteID=? and TypeName=?", 
       Application.getCurrentSiteID(), typeName).executeInt() == 0)) {
       keywordType.setTypeName(typeName);
       keywordType.setModifyTime(new Date());
       keywordType.setModifyUser(User.getUserName());
       if (keywordType.update()) {
         this.Response.setStatus(1);
         this.Response.setMessage("修改成功！");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("已经存在的分类!");
     }
   }
 
   public static Mapx initEditDialog(Mapx params) {
     String ID = params.getString("id");
     params.put("ID", ID);
     ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
     if (StringUtil.isNotEmpty(ID)) {
       keywordType.setID(ID);
       keywordType.fill();
       params.put("TypeName", keywordType.getTypeName());
     }
 
     return params;
   }
 
   public static DataTable loadKeywordType(Mapx params) {
     DataTable dt = new DataTable();
     dt.insertRow(new String[] { "", "" });
     dt.union(
       new QueryBuilder("select ID, TypeName from ZCKeywordType where siteID = ?", 
       Application.getCurrentSiteID()).executeDataTable());
 
     return dt;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.KeywordType
 * JD-Core Version:    0.5.4
 */