 package com.xdarkness.cms.dataservice;
 
 import java.util.Date;
import java.util.List;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.resource.AudioLib;
import com.xdarkness.cms.resource.ImageLib;
import com.xdarkness.cms.resource.VideoLib;
import com.xdarkness.cms.site.Catalog;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCFullTextSchema;
import com.xdarkness.schema.ZCFullTextSet;
import com.xdarkness.schema.ZDScheduleSchema;
import com.xdarkness.schema.ZDScheduleSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.TreeItem;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
 
 public class FullText extends Page
 {
   public static Mapx init(Mapx map)
   {
     return map;
   }
 
   public static void treeDataBind(TreeAction ta) {
     String id = ta.getParam("ID");
     ZCFullTextSchema ft = new ZCFullTextSchema();
     ft.setID(id);
     if (XString.isEmpty(id)) {
       ta.getParams().put("CatalogType", OperateType.INSERT);
       Catalog.treeDataBind(ta);
     } else {
       ft.fill();
       if (!ft.getRelaText().equalsIgnoreCase("-1")) {
         ta.getParams().put("IDs", ft.getRelaText());
       }
     }
     if ("Article".equals(ta.getParam("Type"))) {
       ta.getParams().put("CatalogType", OperateType.INSERT);
       Catalog.treeDataBind(ta);
     }
     if ("Image".equals(ta.getParam("Type"))) {
       ta.getParams().put("CatalogType", 4);
       ImageLib.treeDataBind(ta);
     }
     if ("Video".equals(ta.getParam("Type"))) {
       ta.getParams().put("CatalogType", OperateType.DELETE_AND_BACKUP);
       VideoLib.treeDataBind(ta);
     }
     if ("Audio".equals(ta.getParam("Type"))) {
       ta.getParams().put("CatalogType", 6);
       AudioLib.treeDataBind(ta);
     }
     if ((ta.getDataSource() != null) && (XString.isEmpty(ta.getParam("ParentID")))) {
       DataTable dt = ta.getDataSource();
       dt.insertRow(new Object[dt.getColCount()], 0);
       dt.set(0, 0, "-1");
       dt.set(0, 1, "0");
       dt.set(0, 2, "1");
       dt.set(0, 3, "<font class='red'>全部</font>");
       DataRow dr = dt.getDataRow(0);
       TreeItem root = ta.getItem(0);
       TreeItem item = new TreeItem();
       item.setData(dr);
       item.setAction(ta);
       item.setID(dr.getString(ta.getIdentifierColumnName()));
       item.setParentID(root.getID());
       item.setLevel(root.getLevel() + 1);
       item.setParent(root);
       item.setIcon("Icons/treeicon09.gif");
       item.setLast(false);
       try {
         item.parseHtml(ta.getItemInnerHtml(dr));
       } catch (Exception e) {
         e.printStackTrace();
       }
       ta.getItemList().add(1, item);
       if ("Article".equals(ta.getParam("Type"))) {
         List items = ta.getItemList();
         for (int i = 1; i < items.size(); i++) {
           item = (TreeItem)items.get(i);
           if ("Y".equals(item.getData().getString("SingleFlag")))
             item.setIcon("Icons/treeicon11.gif");
         }
       }
     }
   }
 
   public static void dg1DataBind(DataGridAction dga)
   {
     DataTable dt = new QueryBuilder("select * from ZCFullText where siteid=?", ApplicationPage.getCurrentSiteID())
       .executeDataTable();
     Mapx map = new Mapx();
     map.put("Article", "文章检索");
     map.put("Image", "图片检索");
     map.put("Video", "视频检索");
     map.put("Radio", "音频检索");
     dt.decodeColumn("Type", map);
     dga.bindData(dt);
   }
 
   public void add() {
     Transaction tran = new Transaction();
     ZCFullTextSchema ft = new ZCFullTextSchema();
     if (XString.isEmpty($V("ID"))) {
       ft.setValue(this.request);
       ft.setID(NoUtil.getMaxID("FullTextID"));
       ft.setSiteID(ApplicationPage.getCurrentSiteID());
       ft.setAddTime(new Date());
       ft.setAddUser(User.getUserName());
       tran.add(ft, OperateType.INSERT);
     } else {
       ft.setID(Long.parseLong($V("ID")));
       ft.fill();
       ft.setValue(this.request);
       ft.setModifyTime(new Date());
       ft.setModifyUser(User.getUserName());
       tran.add(ft, OperateType.UPDATE);
     }
     if (tran.commit())
       this.response.setMessage("保存成功");
     else
       this.response.setError("发生错误,保存失败");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     Transaction tran = new Transaction();
     String[] arr = ids.split("\\,");
     for (int i = 0; i < arr.length; i++) {
       tran.add(new QueryBuilder("delete from ZCFullText where id=?", arr[i]));
       tran
         .add(new QueryBuilder("delete from ZDSchedule where SourceID=? and TypeCode='IndexMaintenance'", 
         arr[i]));
     }
     if (tran.commit())
       this.response.setMessage("删除成功");
     else
       this.response.setError("发生错误,删除失败");
   }
 
   public void manual()
   {
     String ids = $V("IDs");
     String[] arr = ids.split("\\,");
     FullTextTaskManager ftm = (FullTextTaskManager)CronManager.getInstance()
       .getCronTaskManager("IndexMaintenance");
     for (int i = 0; i < arr.length; i++) {
       long id = Long.parseLong(arr[i]);
       if (ftm.isRunning(id)) {
         this.response.setMessage("索引维护定时任务正在进行，请稍候重试!");
         return;
       }
       String file = Config.getContextRealPath() + "WEB-INF/data/index/" + id + "/time.lock";
       FileUtil.delete(file);
       ftm.execute(id);
       do
         try {
           Thread.sleep(100L);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
       while (ftm.isRunning(id));
     }
 
     this.response.setMessage("生成索引成功");
   }
 
   public static void dealAutoIndex(long siteID, boolean autoIndex) {
     ZCFullTextSchema ft = new ZCFullTextSchema();
     ft.setSiteID(siteID);
     ft.setProp1("AutoIndex");
     ZCFullTextSet set = ft.query();
     if (!autoIndex) {
       if (set.size() == 0) {
         return;
       }
       long sourceID = set.get(0).getID();
       ZDScheduleSchema sd = new ZDScheduleSchema();
       sd.setSourceID(sourceID);
       ZDScheduleSet sdSet = sd.query();
       if (sdSet.size() == 0) {
         return;
       }
       sd = sdSet.get(0);
       if ("N".equals(sd.getIsUsing())) {
         return;
       }
       sd.setIsUsing("N");
       sd.update();
     }
     else if (set.size() == 0) {
       ft.setID(NoUtil.getMaxID("FullTextID"));
       ft.setRelaText("-1");
       ft.setType("Article");
       ft.setName("全站索引-" + SiteUtil.getName(ft.getSiteID()));
       ft.setCode("AllArticle");
       ft.setAddUser("SYS");
       ft.setAddTime(new Date());
 
       ZDScheduleSchema sd = new ZDScheduleSchema();
       sd.setAddTime(new Date());
       sd.setAddUser("SYS");
       sd.setCronExpression("*/3 * * * *");
       sd.setID(NoUtil.getMaxID("ScheduleID"));
       sd.setIsUsing("Y");
       sd.setPlanType("Period");
       sd.setTypeCode("IndexMaintenance");
       sd.setStartTime(new Date());
       sd.setSourceID(ft.getID());
 
       Transaction tran = new Transaction();
       tran.add(ft, OperateType.INSERT);
       tran.add(sd, OperateType.INSERT);
       tran.commit();
     } else {
       ft = set.get(0);
       long sourceID = set.get(0).getID();
       ZDScheduleSchema sd = new ZDScheduleSchema();
       sd.setSourceID(sourceID);
       ZDScheduleSet sdSet = sd.query();
       if (sdSet.size() == 0) {
         sd.setAddTime(new Date());
         sd.setAddUser("SYS");
         sd.setCronExpression("*/3 * * * *");
         sd.setID(NoUtil.getMaxID("ScheduleID"));
         sd.setIsUsing("Y");
         sd.setPlanType("Period");
         sd.setTypeCode("IndexMaintenance");
         sd.setStartTime(new Date());
         sd.setSourceID(ft.getID());
         sd.insert();
       }
     }
   }
 }

          
/*    com.xdarkness.cms.dataservice.FullText
 * JD-Core Version:    0.6.0
 */