 package com.zving.shop;
 
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZSSendSchema;
 import com.zving.schema.ZSSendSet;
 import java.util.Date;
 
 public class SendType extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String sql = "select * from ZSSend where siteid = ?";
     QueryBuilder qb = new QueryBuilder(sql, Application.getCurrentSiteID());
     dga.bindData(qb);
   }
 
   public void add() {
     ZSSendSchema send = new ZSSendSchema();
     send.setValue(this.Request);
     send.setID(NoUtil.getMaxNo("SendID"));
     send.setSiteID(Application.getCurrentSiteID());
     send.setAddUser(User.getUserName());
     send.setAddTime(new Date());
 
     String ImageID = $V("ImageID");
     if (StringUtil.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       send.setProp1((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString());
     } else {
       send.setProp1("upload/Image/nopicture.jpg");
     }
 
     if (send.insert()) {
       this.Response.setStatus(1);
       this.Response.setMessage("新增配送项成功！");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void dg1Edit() {
     ZSSendSchema send = new ZSSendSchema();
     String ID = $V("ID");
     send.setID(ID);
     send.fill();
     send.setValue(this.Request);
     send.setModifyTime(new Date());
     send.setModifyUser(User.getUserName());
 
     String ImageID = $V("ImageID");
     String imagePath = $V("PicSrc1");
     if (StringUtil.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString();
       send.setProp1(path);
     } else {
       send.setProp1(imagePath);
     }
 
     if (send.update())
       this.Response.setLogInfo(1, "修改成功");
     else
       this.Response.setLogInfo(0, "保存失败");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     ids = ids.replaceAll(",", "','");
     Transaction trans = new Transaction();
     ZSSendSchema send = new ZSSendSchema();
     ZSSendSet set = send.query(new QueryBuilder("where ID in ('" + ids + "')"));
     trans.add(set, 3);
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功！");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public static Mapx initEditDialog(Mapx params) {
     String ID = params.getString("ID");
     ZSSendSchema send = new ZSSendSchema();
     if (StringUtil.isNotEmpty(ID)) {
       send.setID(ID);
       send.fill();
       Mapx map = send.toMapx();
       map.put("PicSrc1", send.getProp1());
       map.put("Prop1", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + send.getProp1()).replaceAll("//", "/"));
       return map;
     }
     params.put("Prop1", 
       (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
       .replaceAll("//", "/"));
     return params;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.SendType
 * JD-Core Version:    0.5.4
 */