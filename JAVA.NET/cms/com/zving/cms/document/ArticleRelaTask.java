 package com.zving.cms.document;
 
 import com.zving.framework.schedule.GeneralTask;
 
 public class ArticleRelaTask extends GeneralTask
 {
   public void execute()
   {
     ArticleRelaIndexer ari = new ArticleRelaIndexer();
     ari.start();
   }
 
   public String getCronExpression()
   {
     return "* * * * *";
   }
 
   public long getID()
   {
     return 200911102214L;
   }
 
   public String getName()
   {
     return "相关文章索引";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.ArticleRelaTask
 * JD-Core Version:    0.5.4
 */