 package com.zving.cms.workflow;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.workflow.Context;
 import com.zving.workflow.WorkflowInstance;
 import com.zving.workflow.methods.NodeMethod;
 
 public class WaitPublishMethod extends NodeMethod
 {
   public void execute(Context context)
   {
     QueryBuilder qb = new QueryBuilder("update ZCArticle set Status=? where ID=?", 20, context
       .getInstance().getDataID());
     context.getTransaction().add(qb);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.workflow.WaitPublishMethod
 * JD-Core Version:    0.5.4
 */