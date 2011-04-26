 package com.xdarkness.shop.web;
 
 import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCCommentSchema;
import com.xdarkness.schema.ZSGoodsSchema;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
 
 public class MemberEvaluation extends Ajax
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     if (dga.getTotal() == 0) {
       String sql2 = "select count(*) from ZSOrderItem where SiteID = ? and UserName = ?";
       QueryBuilder qb = new QueryBuilder(sql2);
       qb.add(ApplicationPage.getCurrentSiteID());
       qb.add(User.getValue("UserName"));
       dga.setTotal(qb.executeInt());
     }
 
     String sql1 = "select a.*, b.Image0 from ZSOrderItem a, ZSGoods b, zsorder c where a.SiteID = ? and a.UserName = ? and a.orderid = c.id and c.status='7' and a.GoodsID = b.ID order by AddTime";
     QueryBuilder qb = new QueryBuilder(sql1);
     qb.add(dga.getParam("SiteID"));
     qb.add(User.getValue("UserName"));
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
 
     dt.insertColumn("Image");
     dt.insertColumn("Evaluate");
     for (int i = dt.getRowCount() - 1; i >= 0; i--) {
       dt.set(i, "Image", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt.getString(i, "Image0")).replaceAll(
         "//", "/"));
       int flag = new QueryBuilder("select count(*) from ZCComment where RelaID = ? and AddUser = ?", dt.get(i, 
         "GoodsID"), dt.get(i, "UserName")).executeInt();
 
       if (DateUtil.addMonth(dt.getDate(i, "AddTime"), 6).before(new Date()))
         dt.set(i, "Evaluate", flag > 0 ? "已评价" : "未评价");
       else {
         dt.set(i, "Evaluate", "<button type='button' onClick='evaluate(" + 
           dt.getString(i, "GoodsID") + ");'>评价</button>");
       }
     }
     dga.bindData(dt);
   }
 
   public void evaluate()
   {
     String goodsID = $V("GoodsID");
     ZSGoodsSchema goods = new ZSGoodsSchema();
     goods.setID(goodsID);
     if (goods.fill()) {
       ZCCommentSchema comment = new ZCCommentSchema();
       comment.setID(NoUtil.getMaxID("ZCCommentID"));
       comment.setValue(this.request);
 
       comment.setRelaID(goodsID);
       comment.setCatalogID(goods.getCatalogID());
       comment.setSiteID(goods.getSiteID());
       comment.setCatalogType("9");
       comment.setVerifyFlag("X");
 
       comment.setAddUser(User.getValue("UserName").toString());
       comment.setAddTime(new Date());
       comment.setAddUserIP($V("MemberIP"));
       if (comment.insert()) {
         this.response.setStatus(1);
         this.response.setMessage("添加评价成功!");
       } else {
         this.response.setStatus(0);
         this.response.setMessage("对不起!发生错误!请您联系客服!");
       }
     } else {
       this.response.setStatus(0);
       this.response.setMessage("对不起!发生错误!请您联系客服!");
     }
   }
 }

          
/*    com.xdarkness.shop.web.MemberEvaluation
 * JD-Core Version:    0.6.0
 */