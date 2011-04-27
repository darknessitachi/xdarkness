 package com.zving.misc;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.framework.User;
 import com.zving.framework.User.UserData;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import java.io.PrintStream;
 
 public class TestPublish
 {
   public static void main(String[] args)
   {
     long t = System.currentTimeMillis();
     User.UserData u = new User.UserData();
     u.setUserName("admin");
     u.setLogin(true);
     u.setManager(true);
     User.setCurrent(u);
     ZCArticleSet set = new ZCArticleSchema().query(new QueryBuilder("where id=?", 10193));
     Publisher p = new Publisher();
     p.publishArticle(set);
     System.out.println(System.currentTimeMillis() - t);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.misc.TestPublish
 * JD-Core Version:    0.5.4
 */