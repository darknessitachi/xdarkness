 package com.zving.cms.workflow;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Executor;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.workflow.Context;
 import com.zving.workflow.WorkflowInstance;
 import com.zving.workflow.methods.NodeMethod;
 import java.util.Date;
 
 public class PublishMethod extends NodeMethod
 {
   public void execute(Context context)
   {
     context.getTransaction().addExecutor(new Executor(context.getInstance().getDataID()) {
       public boolean execute() {
         ZCArticleSet set = new ZCArticleSet();
         ZCArticleSchema article = new ZCArticleSchema();
         article.setID(this.param.toString());
         article.fill();
         if ((article.getPublishDate() != null) && (article.getPublishDate().getTime() > System.currentTimeMillis()))
           article.setStatus(20L);
         else {
           article.setStatus(30L);
         }
         set.add(article);
         Publisher p = new Publisher();
         p.publishArticle(set);
         return true;
       }
     });
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.workflow.PublishMethod
 * JD-Core Version:    0.5.4
 */