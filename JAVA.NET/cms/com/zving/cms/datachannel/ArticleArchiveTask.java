 package com.zving.cms.datachannel;
 
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.schema.ZCArticleSchema;
 import org.apache.commons.logging.Log;
 
 public class ArticleArchiveTask extends GeneralTask
 {
   public static void main(String[] args)
   {
     ArticleArchiveTask t = new ArticleArchiveTask();
     t.execute();
   }
   public void execute() {
     LogUtil.getLogger().info("定时归档任务");
     Transaction trans = new Transaction();
     int size = 500;
     int count = new QueryBuilder("select count(*) from ZCArticle where ArchiveDate<=?", DateUtil.getCurrentDateTime()).executeInt();
     int page = count / size;
     if (count % size == 0) {
       --page;
     }
     DataTable articleDt = new DataTable();
     for (int i = 0; i < page; ++i) {
       articleDt = new QueryBuilder("select * from ZCArticle where ArchiveDate<=?", DateUtil.getCurrentDateTime()).executePagedDataTable(size, page);
       for (int j = 0; j < articleDt.getRowCount(); ++j) {
         ZCArticleSchema article = new ZCArticleSchema();
         article.setID(articleDt.getString(j, "ID"));
         article.fill();
         article.setStatus(50L);
         trans.add(article, 5);
       }
 
       trans.setBackupMemo("Archive");
       trans.commit();
       trans.clear();
     }
     LogUtil.getLogger().info("扫描定时归档任务结束");
   }
 
   public long getID() {
     return 200912081905L;
   }
 
   public String getName() {
     return "对文章进行归档";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.ArticleArchiveTask
 * JD-Core Version:    0.5.4
 */