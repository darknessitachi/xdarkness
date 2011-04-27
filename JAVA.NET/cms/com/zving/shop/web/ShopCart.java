 package com.zving.shop.web;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.CookieImpl;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZSFavoriteSchema;
 import com.zving.schema.ZSGoodsSchema;
 import java.text.DecimalFormat;
 import java.util.Date;
 
 public class ShopCart extends Ajax
 {
   public void dg1DataBind(DataGridAction dga)
   {
     String shopCart = this.Request.getString("Cookie.ShopCart");
     Mapx map = new Mapx();
     if (StringUtil.isNotEmpty(shopCart)) {
       String[] cartGoods = shopCart.split("X");
       for (int i = 0; i < cartGoods.length; ++i) {
         map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
       }
     }
     Object[] ids = map.keyArray();
 
     DecimalFormat fmt = new DecimalFormat("0.00");
     String idStr = StringUtil.join(ids);
     if (StringUtil.isEmpty(idStr)) {
       idStr = "0";
     }
     DataTable dt = new QueryBuilder(
       "select zsgoods.*,0 as count,0 as SavePrice,0 as sumPrice,0 as RowNo from zsgoods where siteid =? and id in (" + 
       idStr + ")", dga.getParam("SiteID")).executeDataTable();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       int count = map.getInt(dt.getString(i, "ID"));
       double MarketPrice = dt.getDouble(i, "MarketPrice");
       double Price = dt.getDouble(i, "Price");
       double sumPrice = Price * count;
       dt.set(i, "count", count);
       dt.set(i, "SavePrice", fmt.format(MarketPrice - Price));
       dt.set(i, "sumPrice", fmt.format(sumPrice));
       dt.set(i, "RowNo", i + 1);
     }
     dga.bindData(dt);
   }
 
   public void dg2DataBind(DataListAction dla) {
     String shopCart = dla.getParam("Cookie.ShopCart");
     Mapx map = new Mapx();
     if (StringUtil.isNotEmpty(shopCart)) {
       String[] cartGoods = shopCart.split("X");
       for (int i = 0; i < cartGoods.length; ++i) {
         map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
       }
     }
     Object[] ids = map.keyArray();
     ZSGoodsSchema goods = new ZSGoodsSchema();
     DecimalFormat fmt = new DecimalFormat("0.00");
     DataTable dt = new DataTable();
     float sumMarkets = 0.0F;
     float sumPrices = 0.0F;
 
     for (int i = 0; i < ids.length; ++i)
     {
       goods.setID(ids[i].toString());
       goods.fill();
       int count = map.getInt(ids[i]);
       float sumMarket = goods.getMarketPrice() * count;
       float sumPrice = goods.getPrice() * count;
       sumMarkets += sumMarket;
       sumPrices += sumPrice;
       DataRow dr = goods.toDataRow();
 
       dr.insertColumn("sumMarkets", fmt.format(sumMarkets));
       dr.insertColumn("sumPrices", fmt.format(sumPrices));
 
       dt.insertRow(dr);
     }
     dla.bindData(dt);
   }
 
   public void del() {
     String IDs = $V("IDs");
     if (!StringUtil.checkID(IDs)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     if ((IDs.indexOf("\"") >= 0) || (IDs.indexOf("'") >= 0)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
 
     String shopCart = this.Request.getString("Cookie.ShopCart");
     Mapx map = new Mapx();
     if (StringUtil.isNotEmpty(shopCart)) {
       String[] cartGoods = shopCart.split("X");
       for (int i = 0; i < cartGoods.length; ++i) {
         map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
       }
     }
     String[] ids = IDs.split(",");
     for (int i = 0; i < ids.length; ++i) {
       map.remove(ids[i]);
     }
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < map.keyArray().length; ++i) {
       sb.append(map.keyArray()[i]);
       sb.append("-");
       sb.append(map.getString(map.keyArray()[i]));
       if (i < map.keyArray().length - 1) {
         sb.append("X");
       }
     }
     this.Cookie.setCookie("ShopCart", sb.toString());
     this.Response.setStatus(1);
     this.Response.setMessage("删除成功!");
   }
 
   public void update() {
     String goodsID = $V("GoodsID");
     String count = $V("Count");
     String shopCart = this.Request.getString("Cookie.ShopCart");
     Mapx map = new Mapx();
     if (StringUtil.isNotEmpty(shopCart)) {
       String[] cartGoods = shopCart.split("X");
       for (int i = 0; i < cartGoods.length; ++i) {
         map.put(cartGoods[i].split("-")[0], cartGoods[i].split("-")[1]);
       }
     }
     map.put(goodsID, count);
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < map.keyArray().length; ++i) {
       sb.append(map.keyArray()[i]);
       sb.append("-");
       sb.append(map.getString(map.keyArray()[i]));
       if (i < map.keyArray().length - 1) {
         sb.append("X");
       }
     }
     this.Cookie.setCookie("ShopCart", sb.toString());
 
     ZSGoodsSchema goods = new ZSGoodsSchema();
     DecimalFormat fmt = new DecimalFormat("0.00");
     goods.setID(goodsID);
     goods.fill();
     float sumPrice = goods.getPrice() * Float.parseFloat(count);
 
     this.Response.setMessage(fmt.format(sumPrice));
   }
 
   public void addToFavorite() {
     String IDs = $V("IDs");
     if (!StringUtil.checkID(IDs)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     String cSiteID = $V("SiteID");
     if ((!User.isMember()) || (!User.isLogin())) {
       this.Response.setStatus(0);
       this.Response.setMessage("收藏夹需要登录后才能使用!");
     } else {
       Transaction tran = new Transaction();
       String[] goodsIds = IDs.split(",");
       for (int i = 0; i < goodsIds.length; ++i) {
         ZSFavoriteSchema fav = new ZSFavoriteSchema();
         fav.setGoodsID(goodsIds[i]);
         fav.setUserName(User.getValue("UserName").toString());
         fav.setSiteID(cSiteID);
         if (!fav.fill()) {
           fav.setAddUser(fav.getUserName());
           fav.setAddTime(new Date());
           tran.add(fav, 1);
         }
       }
       if (tran.commit()) {
         this.Response.setStatus(1);
         this.Response.setMessage("收藏成功!");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("收藏失败！");
       }
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.web.ShopCart
 * JD-Core Version:    0.5.4
 */