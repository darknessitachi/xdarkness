 package com.xdarkness.bbs;
 
 import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.framework.util.Mapx;
 
 public class ForumRule
 {
   static final String NO = "N";
   static final String YES = "Y";
   Mapx map = new Mapx();
 
   public ForumRule(long ForumID) {
     initRule(ForumID);
   }
   public ForumRule(String ForumID) {
     this(Long.parseLong(ForumID));
   }
 
   public boolean getRule(String ruleType)
   {
     return this.map.get(ruleType).equals("Y");
   }
 
   private void initRule(long ForumID) {
     ZCForumSchema forum = new ZCForumSchema();
     forum.setID(ForumID);
     forum.fill();
     initMap(forum);
   }
 
   private void initMap(ZCForumSchema forum) {
     this.map.put("AllowTheme", forum.getAllowTheme());
     this.map.put("EditPost", forum.getEditPost());
     this.map.put("ReplyPost", forum.getReplyPost());
     this.map.put("Locked", forum.getLocked());
     this.map.put("AllowFace", forum.getAllowFace());
   }
 }

          
/*    com.xdarkness.bbs.ForumRule
 * JD-Core Version:    0.6.0
 */