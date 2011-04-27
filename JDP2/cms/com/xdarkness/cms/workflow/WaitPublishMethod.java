 package com.xdarkness.cms.workflow;
 
 import com.xdarkness.workflow.Context;
import com.xdarkness.workflow.methods.NodeMethod;
import com.xdarkness.framework.sql.QueryBuilder;
 
 public class WaitPublishMethod extends NodeMethod
 {
   public void execute(Context context)
   {
     QueryBuilder qb = new QueryBuilder("update ZCArticle set Status=? where ID=?", 20, context
       .getInstance().getDataID());
     context.getTransaction().add(qb);
   }
 }

          
/*    com.xdarkness.cms.workflow.WaitPublishMethod
 * JD-Core Version:    0.6.0
 */