 package com.zving.cms.datachannel;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import java.util.Date;
 
 public class ArticleCancelTopTask extends GeneralTask
 {
   public void execute()
   {
     QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where topflag='1' and topdate is not null and topdate<=?", 
       new Date());
     qb.executeNoQuery();
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder("where topflag='1' and topdate is not null and topdate<=?", new Date()));
     if ((set != null) && (set.size() > 0)) {
       Publisher p = new Publisher();
       p.publishArticle(set);
     }
   }
 
   public long getID() {
     return 200912091105L;
   }
 
   public String getName() {
     return "过期的置顶文章取消置顶";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.ArticleCancelTopTask
 * JD-Core Version:    0.5.4
 */