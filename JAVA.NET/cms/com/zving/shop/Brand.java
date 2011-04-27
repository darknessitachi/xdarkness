 package com.zving.shop;
 
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZSBrandSchema;
 import com.zving.schema.ZSBrandSet;
 import java.util.Date;
 
 public class Brand extends Page
 {
   public static Mapx init(Mapx params)
   {
     return null;
   }
 
   public static Mapx initDialog(Mapx params) {
     String id = params.getString("ID");
     if (StringUtil.isNotEmpty(id)) {
       ZSBrandSchema brand = new ZSBrandSchema();
       brand.setID(id);
       if (!brand.fill()) {
         return params;
       }
 
       Mapx brandMap = brand.toMapx();
       brandMap.put("Name", brand.getName());
       brandMap.put("Alias", brand.getAlias());
       brandMap.put("URL", brand.getURL());
       brandMap.put("Info", brand.getInfo());
       brandMap.put("PicSrc1", brand.getImagePath());
       brandMap.put("PicSrc", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + brand.getImagePath()).replaceAll("//", 
         "/"));
       DataTable dt = new QueryBuilder(
         "select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '" + 
         User.getBranchInnerCode() + "%'").executeDataTable();
       PubFun.indentDataTable(dt);
       brandMap.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(dt, brand.getBranchInnerCode()));
       return brandMap;
     }
     params.put("PicSrc", 
       (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
       .replaceAll("//", "/"));
     DataTable dt = new QueryBuilder(
       "select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '" + 
       User.getBranchInnerCode() + "%'").executeDataTable();
     PubFun.indentDataTable(dt);
     params.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(dt, User.getBranchInnerCode()));
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga)
   {
     String searchWord = dga.getParam("SearchWord");
     QueryBuilder qb = new QueryBuilder("select * from ZSBrand where SiteID = ?");
     qb.add(Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(searchWord)) {
       qb.append(" and Name like ?", "%" + searchWord.trim() + "%");
     }
     dga.bindData(qb);
   }
 
   public void add() {
     String ImageID = $V("ImageID");
     ZSBrandSchema brand = new ZSBrandSchema();
     brand.setID(NoUtil.getMaxID("ZSBrandID"));
     if (!brand.fill()) {
       brand.setValue(this.Request);
       brand.setSiteID(Application.getCurrentSiteID());
       DataTable dt = new QueryBuilder("select * from ZSBrand order by orderflag").executeDataTable();
       long orderflag = 0L;
       if ((dt != null) && (dt.getRowCount() > 0)) {
         orderflag = dt.getLong(dt.getRowCount() - 1, "OrderFlag");
       }
 
       if (StringUtil.isNotEmpty(ImageID)) {
         DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
           .executeDataTable();
         brand.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", 
           "/").toString());
       } else {
         brand.setImagePath("upload/Image/nopicture.jpg");
       }
 
       brand.setOrderFlag(orderflag + 1L);
       brand.setPublishFlag("0");
       brand.setAddTime(new Date());
       brand.setAddUser(User.getUserName());
 
       if (brand.insert()) {
         this.Response.setStatus(1);
         this.Response.setMessage("新增成功!");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void edit() {
     String ImageID = $V("ImageID");
     String imagePath = $V("PicSrc1");
     ZSBrandSchema brand = new ZSBrandSchema();
     brand.setID($V("ID"));
     if (brand.fill()) {
       brand.setValue(this.Request);
       brand.setModifyTime(new Date());
       brand.setModifyUser(User.getUserName());
 
       if (StringUtil.isNotEmpty(ImageID)) {
         DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
           .executeDataTable();
         String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
           .toString();
         brand.setImagePath(path);
       } else {
         brand.setImagePath(imagePath);
       }
 
       if (brand.update()) {
         this.Response.setStatus(1);
         this.Response.setMessage("修改成功!");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!不存在的商品品牌!");
     }
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZSBrandSchema brand = new ZSBrandSchema();
     ZSBrandSet set = brand.query(new QueryBuilder(" where id in (" + ids + ")"));
     trans.add(set, 5);
 
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功!");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void getPicSrc() {
     String ID = $V("PicID");
     DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", ID).executeDataTable();
     if (dt.getRowCount() > 0)
       this.Response.put("picSrc", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + dt.get(
         0, "srcfilename")).replaceAll("//", "/").toString());
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.Brand
 * JD-Core Version:    0.5.4
 */