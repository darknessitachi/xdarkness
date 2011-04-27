 package com.zving.cms.dataservice;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCVoteItemSchema;
 import com.zving.schema.ZCVoteSchema;
 import com.zving.schema.ZCVoteSubjectSchema;
 
 public class VoteItem extends Page
 {
   public static Mapx init(Mapx params)
   {
     String VoteID = params.get("ID").toString();
     ZCVoteSchema vote = new ZCVoteSchema();
     vote.setID(VoteID);
     vote.fill();
     params.put("VoteName", vote.getTitle());
     return params;
   }
 
   public void save() {
     Mapx map = this.Request;
     String voteID = $V("ID");
     ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
     ZCVoteItemSchema item = new ZCVoteItemSchema();
     subject.setVoteID(voteID);
     item.setVoteID(voteID);
     String key = null;
     Transaction trans = new Transaction();
     trans.add(subject.query(), 5);
     trans.add(item.query(), 5);
 
     Object[] ks = map.keyArray();
     for (int i = 0; i < map.size(); ++i) {
       key = (String)ks[i];
       if (key.startsWith("Subject_")) {
         String subjectID = key.substring(8);
         subject = new ZCVoteSubjectSchema();
         subject.setID(subjectID);
         if (!subject.fill()) {
           subject.setID(NoUtil.getMaxID("VoteSubjectID"));
         }
         subject.setVoteID(voteID);
         subject.setType(map.getString("Type_" + subjectID));
         if (StringUtil.isEmpty(subject.getType())) {
           subject.setType("S");
         }
         subject.setSubject(map.getString(key));
         subject.setOrderFlag(OrderUtil.getDefaultOrder());
         trans.add(subject, 1);
 
         for (int j = 0; j < map.size(); ++j) {
           key = (String)ks[j];
           if (key.startsWith("Item_" + subjectID + "_")) {
             item = new ZCVoteItemSchema();
             item.setID(key.substring(("Item_" + subjectID + "_").length()));
             if (!item.fill()) {
               item.setID(NoUtil.getMaxID("VoteItemID"));
             }
             item.setVoteID(subject.getVoteID());
             item.setSubjectID(subject.getID());
             if (StringUtil.isNotEmpty(map.getString(key))) {
               item.setItem(map.getString(key));
             } else if ("W".equals(map.getString("Type_" + subjectID))) {
               item.setItem("其他");
             } else if (("1".equals(map.getString("ItemType_" + key.substring(5)))) || 
               ("2".equals(map.getString("ItemType_" + key.substring(5))))) {
               item.setItem("其他");
             } else {
               this.Response.setLogInfo(0, "选项内容不能为空！");
               return;
             }
             item.setScore(map.getString("Score_" + key.substring(5)));
             item.setItemType(map.getString("ItemType_" + key.substring(5)));
             if (StringUtil.isEmpty(item.getItemType())) {
               item.setItemType("0");
             }
             item.setOrderFlag(OrderUtil.getDefaultOrder());
             trans.add(item, 1);
           } else if (("W".equals(map.getString("Type_" + subjectID))) && (key.startsWith("ItemType_" + subjectID + "_"))) {
             item = new ZCVoteItemSchema();
             item.setID(key.substring(("ItemType_" + subjectID + "_").length()));
             if (!item.fill()) {
               item.setID(NoUtil.getMaxID("VoteItemID"));
             }
             item.setVoteID(subject.getVoteID());
             item.setSubjectID(subject.getID());
             item.setItem(subject.getSubject());
             item.setScore(map.getString("Score_" + subjectID + "_" + key.substring(new StringBuffer("ItemType_").append(subjectID).append("_").toString().length())));
             item.setItemType(map.getString("ItemType_" + subjectID + "_" + key.substring(new StringBuffer("ItemType_").append(subjectID).append("_").toString().length())));
             if (StringUtil.isEmpty(item.getItemType())) {
               item.setItemType("1");
             }
             item.setOrderFlag(OrderUtil.getDefaultOrder());
             trans.add(item, 1);
           }
         }
       }
     }
     if (trans.commit()) {
       this.Response.setLogInfo(1, "保存成功");
       Vote.generateJS(voteID);
     } else {
       this.Response.setLogInfo(0, "保存失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.VoteItem
 * JD-Core Version:    0.5.4
 */