 package com.zving.shop;
 
 import com.zving.cms.dataservice.ColumnUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.orm.SchemaSet;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.member.Member;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZDColumnValueSchema;
 import com.zving.schema.ZSFavoriteSchema;
 import com.zving.schema.ZSFavoriteSet;
 import com.zving.schema.ZSGoodsSchema;
 import com.zving.schema.ZSGoodsSet;
 import java.util.Date;
 import org.apache.commons.mail.EmailException;
 import org.apache.commons.mail.SimpleEmail;
 
 public class Goods extends Page
 {
   public static Mapx initDialog(Mapx params)
   {
     if (StringUtil.isNotEmpty(params.getString("ID"))) {
       ZSGoodsSchema goods = new ZSGoodsSchema();
       goods.setID(params.getLong("ID"));
       if (!goods.fill()) {
         return params;
       }
 
       params.put("GoodsLibID", goods.getCatalogID());
       params.put("CatalogName", new QueryBuilder("select Name from ZCCatalog where ID = ?", goods.getCatalogID())
         .executeString());
       params.putAll(goods.toMapx());
       params.put("PublishDate", DateUtil.toString(goods.getPublishDate()));
       params.put("PicSrc1", goods.getImage0());
       params.put("PicSrc", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + goods.getImage0())
         .replaceAll("//", "/"));
       DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", 
         Application.getCurrentSiteID()).executeDataTable();
       params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, String.valueOf(goods.getBrandID()), false));
 
       params.put("CustomColumn", ColumnUtil.getHtml("1", String.valueOf(goods
         .getCatalogID()), "2", goods.getID()));
     } else {
       params.put("PicSrc", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
         .replaceAll("//", "/"));
       DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", 
         Application.getCurrentSiteID()).executeDataTable();
       params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, false));
       String catalogID = params.getString("CatalogID");
       params.put("CustomColumn", ColumnUtil.getHtml("1", catalogID));
       params.put("UpTime", DateUtil.getCurrentDate());
     }
     return params;
   }
 
   public void add() {
     Transaction trans = new Transaction();
     long ID = NoUtil.getMaxID("GoodsID");
     ZCCatalogSchema catalog = new ZCCatalogSchema();
 
     String good = $V("CatalogID");
 
     if ((good.equals(null)) && (good.length() > 0)) {
       this.Response.setLogInfo(0, "请选择商品类别");
       return;
     }
     catalog.setID(good);
     catalog.fill();
     ZSGoodsSchema goods = new ZSGoodsSchema();
     goods.setCatalogID(good);
     goods.setCatalogInnerCode(catalog.getInnerCode());
     goods.setType("1");
     goods.setTopFlag("1");
     goods.setStickTime(123213L);
     goods.setBranchInnerCode(catalog.getBranchInnerCode());
     goods.setID(ID);
     goods.setStatus("0");
     goods.setSiteID(Application.getCurrentSiteID());
     goods.setCommentCount(0L);
     goods.setHitCount(0L);
 
     String ImageID = $V("ImageID");
     if (StringUtil.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       goods.setImage0((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString());
     } else {
       goods.setImage0("upload/Image/nopicture.jpg");
     }
     goods.setAddTime(new Date());
     goods.setAddUser(User.getUserName());
     goods.setValue(this.Request);
     goods.setOrderFlag(OrderUtil.getDefaultOrder());
 
     SchemaSet ss = ColumnUtil.getValueFromRequest(goods.getCatalogID(), goods.getID(), this.Request);
 
     trans.add(ss, 1);
     trans.add(goods, 1);
 
     if (trans.commit())
       this.Response.setLogInfo(1, "新建成功");
     else
       this.Response.setLogInfo(0, "新建失败");
   }
 
   public void save()
   {
     DataTable dt = (DataTable)this.Request.get("DT");
     Transaction trans = new Transaction();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZSGoodsSchema goods = new ZSGoodsSchema();
       goods.setID(dt.getString(i, "ID"));
       if (!goods.fill()) {
         this.Response.setLogInfo(0, "您要修改的项" + goods.getID() + "不存在!");
         return;
       }
       goods.setValue(dt.getDataRow(i));
       goods.setModifyUser(User.getUserName());
       goods.setModifyTime(new Date());
       trans.add(goods, 2);
     }
     if (trans.commit())
       this.Response.setLogInfo(1, "保存成功!");
     else
       this.Response.setLogInfo(0, "保存失败!");
   }
 
   public void dg1Edit()
   {
     String ImageID = $V("ImageID");
     String imagePath = $V("PicSrc1");
     ZSGoodsSchema goods = new ZSGoodsSchema();
     String ID = $V("ID");
     goods.setID(ID);
     if (!goods.fill()) {
       this.Response.setLogInfo(0, "您要修改的商品" + goods.getName() + "不存在!");
       return;
     }
     Transaction trans = new Transaction();
     if (goods.getPrice() > Double.parseDouble(this.Request.getString("Price")))
     {
       DataTable dt = new QueryBuilder("select a.Name, b.UserName from ZSFavorite b, ZSGoods a where b.GoodsID = ? and b.GoodsID = a.ID and b.SiteID = ? and a.SiteID = b.SiteID and b.PriceNoteFlag = 'Y'", 
         ID, Application.getCurrentSiteID()).executeDataTable();
 
       for (int i = 0; i < dt.getRowCount(); ++i) {
         Member member = new Member(dt.getString(i, "UserName"));
         if (!member.fill()) {
           continue;
         }
         SimpleEmail email = new SimpleEmail();
         email.setHostName("smtp.163.com");
         try {
           String siteName = SiteUtil.getName(Application.getCurrentSiteID());
           StringBuffer sb = new StringBuffer();
           sb.append("尊敬的" + siteName + "用户：<br/>");
           sb.append("你好！<br/>");
           sb.append("您关注的商品" + dt.getString(i, "Name") + "已经降价，请点击一下链接查看相关信息：<br/>");
 
           sb.append("<br/><br/>注：此邮件为系统自动发送，请勿回复。<br/>");
           sb.append("　　　　　　　　　　　　　　　　　　　　　　　————" + siteName);
           email.setAuthentication("0871huhu@163.com", "08715121182");
           email.addTo(member.getEmail(), member.getUserName());
           email.setFrom("0871huhu@163.com", siteName);
           email.setSubject(siteName + "：商品降价提醒！");
           email.setContent(sb.toString(), "text/html;charset=utf-8");
         }
         catch (EmailException e) {
           this.Response.setLogInfo(0, "邮件发送错误");
           e.printStackTrace();
         }
       }
     }
 
     goods.setValue(this.Request);
     goods.setModifyUser(User.getUserName());
     goods.setModifyTime(new Date());
     goods.setStatus("60");
 
     if (StringUtil.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString();
       goods.setImage0(path);
     } else {
       goods.setImage0(imagePath);
     }
 
     trans.add(goods, 2);
 
     DataTable dt = ColumnUtil.getColumnValue("2", goods.getID());
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZDColumnValueSchema value = new ZDColumnValueSchema();
       value.setValue(dt.getDataRow(i));
       value.setTextValue($V("_C_" + value.getColumnCode()));
       trans.add(value, 2);
     }
 
     if (trans.commit())
       this.Response.setLogInfo(1, "修改成功");
     else
       this.Response.setLogInfo(0, "修改" + goods.getID() + "失败!");
   }
 
   public void del()
   {
     String IDs = $V("IDs");
     if (!StringUtil.checkID(IDs)) {
       return;
     }
     Transaction trans = new Transaction();
     ZSGoodsSchema goods = new ZSGoodsSchema();
     ZSGoodsSet set = goods.query(new QueryBuilder("where id in (" + IDs + ")"));
     trans.add(set, 5);
 
     ZSFavoriteSchema fav = new ZSFavoriteSchema();
     ZSFavoriteSet favs = fav.query(new QueryBuilder("where GoodsID in (" + IDs + ")"));
     trans.add(favs, 3);
 
     if (trans.commit())
       this.Response.setLogInfo(1, "删除成功");
     else
       this.Response.setLogInfo(0, "删除失败");
   }
 
   public boolean checkSN()
   {
     String SN = $V("SN");
     if (StringUtil.isEmpty(SN)) {
       this.Response.setLogInfo(0, "不能为空");
       return false;
     }
     int count = new QueryBuilder("select count(1) from ZSGoods where SN=?", SN).executeInt();
     if (count > 0) {
       this.Response.setLogInfo(0, "已经存在此类编号的药品:" + $V("SN"));
       return false;
     }
     return true;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.Goods
 * JD-Core Version:    0.5.4
 */