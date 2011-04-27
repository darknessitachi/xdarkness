 package com.zving.cms.site;
 
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCTagSchema;
 import com.zving.schema.ZCTagSet;
 import java.util.Date;
 
 public class Tag extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String TagWord = dga.getParam("TagWord");
     QueryBuilder qb = new QueryBuilder("select * from ZCTag where siteID=?");
     qb.add(Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(TagWord)) {
       qb.append(" and Tag like ?", "%" + TagWord.trim() + "%");
     }
     if (StringUtil.isNotEmpty(dga.getSortString()))
       qb.append(dga.getSortString());
     else {
       qb.append(" order by ID desc");
     }
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dga.bindData(dt);
   }
 
   public void dg1Edit() {
     DataTable dt = (DataTable)this.Request.get("DT");
     ZCTagSet set = new ZCTagSet();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZCTagSchema tag = new ZCTagSchema();
       tag.setID(Integer.parseInt(dt.getString(i, "ID")));
       tag.fill();
       tag.setValue(dt.getDataRow(i));
       tag.setModifyTime(new Date());
       tag.setModifyUser(User.getUserName());
       if (!checkTagWord(tag.getID(), tag.getTag())) {
         this.Response.setStatus(0);
         this.Response.setMessage("更改Tag内容不允许和其他数据的Tag内容重复!");
         return;
       }
       set.add(tag);
     }
     if (set.update()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public static Mapx init(Mapx params) {
     return null;
   }
 
   public static boolean checkTagWord(long SiteID, String TagWord) {
     int count = new QueryBuilder("select count(1) from ZCTag where Tag=? and SiteID=?", TagWord, SiteID)
       .executeInt();
     return count == 0;
   }
 
   public void add() {
     ZCTagSchema tag = new ZCTagSchema();
     String TagWord = $V("Tag").trim();
     tag.setID(NoUtil.getMaxID("TagID"));
     tag.setValue(this.Request);
     tag.setSiteID(Application.getCurrentSiteID());
     tag.setAddTime(new Date());
     tag.setAddUser(User.getUserName());
     tag.setUsedCount(0L);
     if (checkTagWord(Application.getCurrentSiteID(), TagWord)) {
       if (tag.insert())
         this.Response.setLogInfo(1, "新增成功");
       else
         this.Response.setLogInfo(0, "发生错误！");
     }
     else {
       this.Response.setStatus(0);
       this.Response.setMessage("已经存在的Tag内容!");
     }
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCTagSchema Tag = new ZCTagSchema();
     QueryBuilder qb = new QueryBuilder("where id in (" + ids + ")");
     ZCTagSet set = Tag.query(qb);
     trans.add(set, 5);
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.Tag
 * JD-Core Version:    0.5.4
 */