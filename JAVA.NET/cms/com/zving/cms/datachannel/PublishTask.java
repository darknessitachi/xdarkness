 package com.zving.cms.datachannel;
 
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCSiteSchema;
 import com.zving.schema.ZCSiteSet;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.commons.logging.Log;
 
 public class PublishTask extends GeneralTask
 {
   public void execute()
   {
     LogUtil.getLogger().info("PublishTask定时发布任务");
     Publisher p = new Publisher();
 
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(
       new QueryBuilder(" where publishdate<=? and status =20", new Date()));
 
     if ((set != null) && (set.size() > 0)) {
       LogUtil.getLogger().info("需发布文章篇数：" + set.size());
       p.publishArticle(set);
     }
 
     set = article.query(
       new QueryBuilder("where downlinedate<=? and status=?", 
       new Date(), 30));
     if ((set != null) && (set.size() > 0)) {
       LogUtil.getLogger().info("需下线文章篇数：" + set.size());
 
       Mapx catalogMap = new Mapx();
       for (int i = 0; i < set.size(); ++i) {
         set.get(i).setStatus(40L);
         set.get(i).setTopFlag("0");
         catalogMap.put(set.get(i).getCatalogID(), set.get(i).getCatalogID());
       }
 
       if (set.update()) {
         p.deletePubishedFile(set);
         Object[] vs = catalogMap.valueArray();
         for (int i = 0; i < catalogMap.size(); ++i) {
           p.publishCatalog(Long.parseLong(vs[i].toString()), false, false);
         }
 
       }
 
     }
 
     if ("Y".equals(Config.getValue("RewriteIndex"))) {
       ZCSiteSet sites = new ZCSiteSchema().query();
       for (int i = 0; i < sites.size(); ++i) {
         long siteID = sites.get(i).getID();
         String url = SiteUtil.getURL(siteID) + "/index.shtml";
         String content = FileUtil.readURLText(url);
         if (StringUtil.isNotEmpty(content)) {
           FileUtil.writeText(SiteUtil.getAbsolutePath(siteID) + "/index.html", content);
           ArrayList list = new ArrayList();
           list.add(SiteUtil.getAbsolutePath(siteID) + "/index.html");
           Deploy d = new Deploy();
           d.addJobs(siteID, list);
         }
       }
     }
 
     LogUtil.getLogger().info("PublishTask发布任务结束");
   }
 
   public long getID() {
     return 200904241314L;
   }
 
   public String getName() {
     return "发布指定日期的文章";
   }
 
   public static void main(String[] args) {
     PublishTask p = new PublishTask();
     p.execute();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.PublishTask
 * JD-Core Version:    0.5.4
 */