 package com.xdarkness.cms.document;
 
  
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

          
/*    com.xdarkness.cms.document.ArticleRelaTask
 * JD-Core Version:    0.6.0
 */