 package com.zving.shop;
 
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZDMemberSchema;
 import com.zving.schema.ZSOrderItemSchema;
 import com.zving.schema.ZSOrderItemSet;
 import com.zving.schema.ZSOrderSchema;
 import java.util.Date;
 
 public class OrderItem extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     QueryBuilder qb = new QueryBuilder("select * from ZSOrderItem where OrderID =? order by GoodsID", dga.getParam("OrderID"));
     dga.bindData(qb);
   }
 
   public static Mapx init(Mapx params) {
     return params;
   }
 
   public void checkSN()
   {
     String sn = $V("SN").trim();
     if (sn.length() == 0) {
       return;
     }
     DataTable dt = new QueryBuilder("select * from zsgoods where SN=?", sn).executeDataTable();
     if ((dt == null) || (dt.getRowCount() == 0)) {
       this.Response.setStatus(0);
       return;
     }
     this.Response.setStatus(1);
     this.Response.put("Name", dt.get(0, "Name").toString());
     this.Response.put("Price", dt.get(0, "Price").toString());
     this.Response.put("DiscountPrice", dt.get(0, "MemberPrice").toString());
     this.Response.put("Score", dt.get(0, "Score").toString());
     this.Response.put("GoodsID", dt.get(0, "ID").toString());
     this.Response.put("Factory", dt.get(0, "Factory").toString());
     this.Response.put("Standard", dt.get(0, "Standard").toString());
     float price = Float.valueOf(dt.get(0, "MemberPrice").toString()).floatValue();
     float memberPrice = Float.valueOf(dt.get(0, "Price").toString()).floatValue();
     int discount = (int)(price / memberPrice) * 100 / 100;
     this.Response.put("Discount", discount);
   }
 
   public void checkName()
   {
     String name = $V("Name").trim();
     if (name.length() == 0) {
       return;
     }
     DataTable dt = new QueryBuilder("select * from zsgoods where Name=?", name).executeDataTable();
     if ((dt == null) || (dt.getRowCount() == 0)) {
       this.Response.setStatus(0);
       return;
     }
     this.Response.setStatus(1);
     this.Response.put("SN", dt.get(0, "SN").toString());
     this.Response.put("Price", (StringUtil.isEmpty(dt.getString(0, "Price"))) ? "0" : dt.getString(0, "Price"));
     this.Response.put("DiscountPrice", (StringUtil.isEmpty(dt.getString(0, "MemberPrice"))) ? "0" : dt.getString(0, "MemberPrice"));
     this.Response.put("Score", (StringUtil.isEmpty(dt.getString(0, "Score"))) ? "0" : dt.getString(0, "Score"));
     this.Response.put("GoodsID", dt.getString(0, "ID"));
     this.Response.put("Factory", (StringUtil.isEmpty(dt.getString(0, "Factory"))) ? "" : dt.getString(0, "Factory"));
     this.Response.put("Standard", (StringUtil.isEmpty(dt.getString(0, "Standard"))) ? "" : dt.getString(0, "Standard"));
     float price = Float.valueOf((StringUtil.isEmpty(dt.getString(0, "MemberPrice"))) ? "0" : dt.getString(0, "MemberPrice")).floatValue();
     float memberPrice = Float.valueOf((StringUtil.isEmpty(dt.getString(0, "Price"))) ? "0" : dt.getString(0, "Price")).floatValue();
     int discount = (int)(price / memberPrice) * 100 / 100;
     this.Response.put("Discount", discount);
   }
 
   public void add()
   {
     Transaction trans = new Transaction();
 
     ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
     ZSOrderItem.setValue(this.Request);
     ZSOrderItem.setSiteID(Application.getCurrentSiteID());
     ZSOrderItem.setAddUser(User.getUserName());
     ZSOrderItem.setAddTime(new Date());
     trans.add(ZSOrderItem, 1);
 
     String orderID = $V("OrderID");
     if (StringUtil.isEmpty(orderID)) {
       this.Response.setLogInfo(1, "订单号不能为空！");
       return;
     }
     ZSOrderSchema order = new ZSOrderSchema();
     order.setID(orderID);
     if (order.fill()) {
       String amount = this.Request.getString("Amount");
       order.setAmount(order.getAmount() + Float.parseFloat(amount));
       order.setOrderAmount(order.getOrderAmount() + Float.parseFloat(amount));
       trans.add(order, 2);
     } else {
       this.Response.setLogInfo(1, "订单号不存在！");
       return;
     }
 
     String memberID = this.Request.getString("MemberID");
     String score = this.Request.getString("Score");
 
     if ((StringUtil.isNotEmpty(memberID)) && (!memberID.equalsIgnoreCase("0"))) {
       ZDMemberSchema member = new ZDMemberSchema();
       member.setUserName(memberID);
       if (member.fill()) {
         member.setScore(member.getScore() + Integer.parseInt(score));
         trans.add(member, 2);
       }
     }
     if (trans.commit())
       this.Response.setLogInfo(1, "新增订单详细项成功！");
     else
       this.Response.setLogInfo(0, "发生错误!");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setLogInfo(0, "传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
 
     String memberID = $V("MemberID");
     String sqlScore = "select sum(score) from zsorderitem where GoodsID in ('" + ids + "')";
     Object obj = new QueryBuilder(sqlScore).executeOneValue();
     String score = (obj == null) ? "0" : obj.toString();
 
     if ((StringUtil.isNotEmpty(memberID)) && (!memberID.equalsIgnoreCase("0"))) {
       ZDMemberSchema member = new ZDMemberSchema();
       member.setUserName(memberID);
       if (member.fill()) {
         member.setScore(String.valueOf(Integer.parseInt(member.getScore()) - Integer.parseInt(score)));
         trans.add(member, 2);
       }
 
     }
 
     ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
     ZSOrderItemSet set = ZSOrderItem.query(new QueryBuilder("where GoodsID in ('" + ids + "')"));
     trans.add(set, 3);
 
     String orderID = $V("OrderID");
     String sqlAmount = "select sum(Amount) from zsorderitem where GoodsID in ('" + ids + "')";
     String amount = new QueryBuilder(sqlAmount).executeString();
     ZSOrderSchema order = new ZSOrderSchema();
     order.setID(orderID);
     order.fill();
     order.setAmount(order.getAmount() - Float.parseFloat(amount));
     trans.add(order, 2);
     if (trans.commit())
       this.Response.setLogInfo(1, "删除成功");
     else
       this.Response.setLogInfo(0, "删除失败");
   }
 
   public void dg1Edit()
   {
     DataTable dt = this.Request.getDataTable("DT");
     Transaction trans = new Transaction();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZSOrderItemSchema item = new ZSOrderItemSchema();
       item.setValue(dt.getDataRow(i));
       item.fill();
       item.setValue(dt.getDataRow(i));
 
       item.setModifyTime(new Date());
       item.setModifyUser(User.getUserName());
 
       trans.add(item, 2);
     }
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("修改成功!");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("修改失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.OrderItem
 * JD-Core Version:    0.5.4
 */