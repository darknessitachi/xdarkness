 package com.zving.cms.dataservice;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.Config;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 
 public class MessageService extends Ajax
 {
   public static Mapx init(Mapx params)
   {
     String BoardID = params.getString("BoardID");
     if (StringUtil.isNotEmpty(BoardID)) {
       params.put("BoardName", new QueryBuilder("select Name from ZCMessageBoard where ID = ?", BoardID)
         .executeString());
     }
     params.put("ServicesContext", Config.getValue("ServicesContext"));
     params.put("MessageActionURL", Config.getValue("MessageActionURL"));
     return params;
   }
 
   public static void dg1DataBind(DataListAction dla) {
     String BoardID = dla.getParam("BoardID");
     QueryBuilder qb = new QueryBuilder(
       "select * from ZCBoardMessage where BoardID = ? and PublishFlag = 'Y' order by ID desc", BoardID);
     dla.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     dt.insertColumn("Reply");
     dt.insertColumn("Prefix");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.getString(i, "ReplyFlag").equals("Y")) {
         dt.set(i, "Prefix", "<font color='#9B0D17'>[回复]：</font>");
         dt.set(i, "Reply", "<font color='#9B0D17'>" + dt.getString(i, "ReplyContent") + "</font>");
       }
     }
     dla.bindData(dt);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.MessageService
 * JD-Core Version:    0.5.4
 */