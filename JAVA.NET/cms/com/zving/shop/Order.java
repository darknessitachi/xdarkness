 package com.zving.shop;
 
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZSGoodsSchema;
 import com.zving.schema.ZSOrderSchema;
 import java.io.PrintStream;
 import java.util.Date;
 
 public class Order extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     if (StringUtil.isNotEmpty(dga.getParam("PageIndex"))) {
       dga.setPageIndex(Integer.parseInt(dga.getParam("PageIndex")));
       dga.getParams().put("PageIndex", "");
     }
     String searchUserName = dga.getParam("searchUserName");
     String searchStatus = dga.getParam("searchStatus");
     QueryBuilder qb = new QueryBuilder("select * from ZSOrder where SiteID = ?");
     qb.add(Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(searchUserName)) {
       qb.append(" and UserName like ? ", "%" + searchUserName + "%");
     }
     if (StringUtil.isNotEmpty(searchStatus)) {
       qb.append(" and Status = ? ");
       qb.add(searchStatus);
       if (!searchStatus.equals("5"))
         qb.append(" and IsValid = 'Y' ");
     }
     else {
       qb.append(" and IsValid = 'Y' ");
     }
     qb.append(" and status not in ('8','9') order by id desc");
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.decodeColumn("IsValid", HtmlUtil.codeToMapx("Order.IsValid"));
     dt.decodeColumn("HasInvoice", HtmlUtil.codeToMapx("Order.HasInvoice"));
     dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
     DataTable dc = new QueryBuilder("select Name,Code from zddistrict Order by Code").executeDataTable();
     Mapx map = dc.toMapx("Code", "Name");
     dt.decodeColumn("Province", map);
     dt.decodeColumn("City", map);
     dt.decodeColumn("District", map);
     dga.bindData(dt);
   }
 
   public static Mapx initStatusSelect(Mapx params) {
     params.put("StatusSelect", HtmlUtil.codeToOptions("Order.Status", true));
     return params;
   }
 
   public static Mapx initStore(Mapx params) {
     DataTable storeDT = new QueryBuilder("select name,storecode from zsstore where Prop1 = 'Y'").executeDataTable();
     params.put("StoreCode", HtmlUtil.dataTableToOptions(storeDT));
     return params;
   }
 
   public static Mapx initAddDialog(Mapx params) {
     Mapx map = new Mapx();
     map.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid", "Y"));
     map.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice", "Order.HasInvoice", "Y"));
     map.put("Status", HtmlUtil.codeToOptions("Order.Status"));
     map.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice"));
     DataTable sendTypeDT = new QueryBuilder("select name,id from zssend order by id").executeDataTable();
     map.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT));
     DataTable paymentTypeDT = new QueryBuilder("select name,id from zspayment order by id").executeDataTable();
     map.put("PaymentType", HtmlUtil.dataTableToOptions(paymentTypeDT));
     return map;
   }
 
   public static Mapx initEditDialog(Mapx params) {
     String ID = params.getString("OrderID");
     if (StringUtil.isNotEmpty(ID)) {
       ZSOrderSchema order = new ZSOrderSchema();
       order.setID(ID);
       if (order.fill()) {
         params.putAll(order.toMapx());
         params.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid", order.getIsValid()));
         params
           .put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice", "Order.HasInvoice", order
           .getHasInvoice()));
         params.put("Status", HtmlUtil.codeToOptions("Order.Status", order.getStatus()));
         params.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice", order.getSendTimeSlice()));
         DataTable sendTypeDT = new QueryBuilder("select name,id from zssend order by id").executeDataTable();
         DataTable paymentTypeDT = new QueryBuilder("select name,id from zspayment order by id")
           .executeDataTable();
         params.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT, order.getSendType()));
         params.put("PaymentType", HtmlUtil.dataTableToOptions(paymentTypeDT, order.getPaymentType()));
         params.put("OrderID", ID);
       }
       return params;
     }
     return params;
   }
 
   public void add()
   {
     ZSOrderSchema ZSorder = new ZSOrderSchema();
     String UserName = $V("UserName");
     String date = DateUtil.getCurrentDate("yyyyMMdd");
     String OrderID = NoUtil.getMaxNo("OrderID", date, 4);
     ZSorder.setID(OrderID);
     ZSorder.setValue(this.Request);
     ZSorder.setAddUser(User.getUserName());
     ZSorder.setAddTime(new Date());
     ZSorder.setUserName(UserName);
     ZSorder.setSiteID(Application.getCurrentSiteID());
     ZSorder.setAmount(0.0F);
     if (ZSorder.insert())
       this.Response.setLogInfo(1, "新增订单成功！");
     else
       this.Response.setLogInfo(0, "发生错误!");
   }
 
   public void orderEdit()
   {
     ZSOrderSchema order = new ZSOrderSchema();
     order.setValue(this.Request);
     order.fill();
     if ((order.getStatus().equals("7")) && 
       (!this.Request.getString("Status").equals("8")) && (!this.Request.getString("Status").equals("9"))) {
       this.Response.setLogInfo(0, "已完成交易的订单只能接受投诉或退货!");
       return;
     }
 
     order.setValue(this.Request);
 
     boolean flag = false;
     StringBuffer sb = new StringBuffer();
     Transaction trans = new Transaction();
 
     if (order.getStatus().equals("1")) {
       DataTable orderItems = new QueryBuilder("select GoodsID, Name, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
       for (int j = 0; j < orderItems.getRowCount(); ++j) {
         ZSGoodsSchema goods = new ZSGoodsSchema();
         goods.setID(orderItems.getString(j, "GoodsID"));
         if (goods.fill()) {
           if (goods.getStore() < orderItems.getInt(j, "Count")) {
             sb.append("商品<span style='color:red'>" + goods.getName() + "</span>的库存不够！<br/>");
             flag = true;
           }
         } else {
           sb.append("商品<span style='color:red'>" + orderItems.getString(j, "Name") + "</span>不存在！<br/>");
           flag = true;
         }
       }
     }
     DataTable orderItems;
     if (order.getStatus().equals("7")) {
       orderItems = new QueryBuilder("select GoodsID, Name, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
       for (int j = 0; j < orderItems.getRowCount(); ++j) {
         ZSGoodsSchema goods = new ZSGoodsSchema();
         goods.setID(orderItems.getString(j, "GoodsID"));
         if (goods.fill()) {
           goods.setStore(goods.getStore() - orderItems.getLong(j, "Count"));
           trans.add(goods, 2);
         } else {
           sb.append("商品<span style='color:red'>" + orderItems.getString(j, "Name") + "</span>不存在！<br/>");
           flag = true;
         }
       }
     }
     if (flag) {
       this.Response.setLogInfo(0, sb.toString());
       return;
     }
 
     order.setSiteID(Application.getCurrentSiteID());
     order.setModifyUser(User.getUserName());
     order.setModifyTime(new Date());
     trans.add(order, 2);
     if ("N".equals(order.getIsValid())) {
       orderItems = User.getRealName() + "于" + DateUtil.getCurrentDateTime("yyyy-MM-dd") + "设置订单" + 
         order.getID() + "为无效状态";
     }
 
     if (trans.commit())
       this.Response.setLogInfo(1, "修改成功");
     else
       this.Response.setLogInfo(0, "修改" + order.getUserName() + "失败!");
   }
 
   public void dg1Edit()
   {
     DataTable dt = this.Request.getDataTable("DT");
     System.out.println(this.Request);
     Transaction trans = new Transaction();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZSOrderSchema order = new ZSOrderSchema();
       order.setValue(dt.getDataRow(i));
       order.fill();
       if ((order.getStatus().equals("7")) && 
         (!dt.getString(i, "Status").equals("8")) && (!dt.getString(i, "Status").equals("9"))) {
         this.Response.setLogInfo(0, "已完成交易的订单只能接受投诉或退货!");
         return;
       }
 
       order.setValue(dt.getDataRow(i));
 
       if (order.getStatus().equals("1")) {
         DataTable orderItems = new QueryBuilder("select GoodsID, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
         boolean flag = false;
         StringBuffer sb = new StringBuffer();
         for (int j = 0; j < orderItems.getRowCount(); ++j) {
           ZSGoodsSchema goods = new ZSGoodsSchema();
           goods.setID(orderItems.getString(j, "GoodsID"));
           if (goods.fill()) {
             if (goods.getStore() < orderItems.getInt(j, "Count")) {
               sb.append("商品" + goods.getName() + "的库存不够！<br/>");
               flag = true;
             }
           } else {
             sb.append("ID为" + orderItems.getString(j, "GoodsID") + "的商品不存在！<br/>");
             flag = true;
           }
         }
         if (flag) {
           this.Response.setLogInfo(0, sb.toString());
           return;
         }
       }
       DataTable orderItems;
       if (order.getStatus().equals("7")) {
         orderItems = new QueryBuilder("select GoodsID, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
         for (int j = 0; j < orderItems.getRowCount(); ++j) {
           ZSGoodsSchema goods = new ZSGoodsSchema();
           goods.setID(orderItems.getString(j, "GoodsID"));
           if (goods.fill()) {
             goods.setStore(goods.getStore() - orderItems.getLong(j, "Count"));
             trans.add(goods, 2);
           } else {
             this.Response.setLogInfo(0, "ID为" + orderItems.getString(j, "GoodsID") + "的商品已被删除！");
             return;
           }
         }
       }
 
       order.setModifyTime(new Date());
       order.setModifyUser(User.getUserName());
       trans.add(order, 2);
 
       if ("N".equals(order.getIsValid())) {
         orderItems = User.getRealName() + "于" + DateUtil.getCurrentDateTime("yyyy-MM-dd") + "设置订单" + 
           order.getID() + "为无效状态";
       }
 
     }
 
     if (trans.commit())
       this.Response.setLogInfo(1, "修改成功");
     else
       this.Response.setLogInfo(0, "修改失败");
   }
 
   public void createSending()
   {
     String ids = $V("IDs");
 
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     ids = ids.replaceAll(",", "','");
     Transaction trans = new Transaction();
     trans.add(new QueryBuilder("update zsorder set status=10 where ID in ('" + ids + "')"));
     if (trans.commit())
       this.Response.setLogInfo(1, "生成配送单成功!");
     else
       this.Response.setLogInfo(0, "生成配送单失败!");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.Order
 * JD-Core Version:    0.5.4
 */