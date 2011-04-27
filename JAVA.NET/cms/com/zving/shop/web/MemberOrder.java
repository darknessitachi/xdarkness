 package com.zving.shop.web;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZSOrderSchema;
 import java.util.Date;
 
 public class MemberOrder extends Ajax
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String orderID = dga.getParam("OrderID");
     String startDate = dga.getParam("StartDate");
     String endDate = dga.getParam("EndDate");
     String siteID = dga.getParam("SiteID");
 
     QueryBuilder qb = new QueryBuilder(
       "select * from ZSOrder where SiteID = ? and UserName = ?", siteID, User.getUserName());
 
     if (StringUtil.isNotEmpty(orderID)) {
       qb.append(" and ID = ?", orderID);
     }
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and AddTime > ?", startDate);
     }
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and AddTime < ?", endDate);
     }
 
     qb.append(" order by id desc");
     dga.setTotal(qb);
 
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
       .getPageIndex());
     dt.insertColumn("CancelCol");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.getInt(i, "Status") == 0)
         dt.set(i, "CancelCol", "<a href='javascript:void(0)' onClick=\"cancel(" + dt.getString(i, "ID") + ")\">取消订单</a>");
       else if (dt.getInt(i, "Status") == 5)
         dt.set(i, "CancelCol", "已取消");
       else {
         dt.set(i, "CancelCol", "已生效");
       }
     }
     dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
     Mapx paymentMapx = new QueryBuilder("select ID, Name from ZSPayment")
       .executeDataTable().toMapx(0, 1);
     dt.decodeColumn("PaymentType", paymentMapx);
 
     dga.bindData(dt);
   }
 
   public void cancel()
   {
     ZSOrderSchema order = new ZSOrderSchema();
     String orderID = $V("OrderID");
     order.setID(orderID);
     if (order.fill())
       if (StringUtil.isNotEmpty(order.getStatus())) {
         order.setIsValid("N");
         order.setStatus("5");
         order.setModifyTime(new Date());
         order.setModifyUser(User.getUserName());
         if (order.update())
           this.Response.setLogInfo(1, "取消订单成功！");
         else
           this.Response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
       }
       else {
         this.Response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
       }
     else
       this.Response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.web.MemberOrder
 * JD-Core Version:    0.5.4
 */