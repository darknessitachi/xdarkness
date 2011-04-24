 package com.xdarkness.shop;
 
 import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZSSendSchema;
import com.xdarkness.schema.ZSSendSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
 
 public class SendType extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String sql = "select * from ZSSend where siteid = ?";
     QueryBuilder qb = new QueryBuilder(sql, ApplicationPage.getCurrentSiteID());
     dga.bindData(qb);
   }
 
   public void add() {
     ZSSendSchema send = new ZSSendSchema();
     send.setValue(this.request);
     send.setID(NoUtil.getMaxNo("SendID"));
     send.setSiteID(ApplicationPage.getCurrentSiteID());
     send.setAddUser(User.getUserName());
     send.setAddTime(new Date());
 
     String ImageID = $V("ImageID");
     if (XString.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       send.setProp1((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString());
     } else {
       send.setProp1("upload/Image/nopicture.jpg");
     }
 
     if (send.insert()) {
       this.response.setStatus(1);
       this.response.setMessage("新增配送项成功！");
     } else {
       this.response.setStatus(0);
       this.response.setMessage("发生错误!");
     }
   }
 
   public void dg1Edit() {
     ZSSendSchema send = new ZSSendSchema();
     String ID = $V("ID");
     send.setID(ID);
     send.fill();
     send.setValue(this.request);
     send.setModifyTime(new Date());
     send.setModifyUser(User.getUserName());
 
     String ImageID = $V("ImageID");
     String imagePath = $V("PicSrc1");
     if (XString.isNotEmpty(ImageID)) {
       DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
         .executeDataTable();
       String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
         .toString();
       send.setProp1(path);
     } else {
       send.setProp1(imagePath);
     }
 
     if (send.update())
       this.response.setLogInfo(1, "修改成功");
     else
       this.response.setLogInfo(0, "保存失败");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!XString.checkID(ids)) {
       this.response.setStatus(0);
       this.response.setMessage("传入ID时发生错误!");
       return;
     }
     ids = ids.replaceAll(",", "','");
     Transaction trans = new Transaction();
     ZSSendSchema send = new ZSSendSchema();
     ZSSendSet set = send.query(new QueryBuilder("where ID in ('" + ids + "')"));
     trans.add(set, OperateType.DELETE);
     if (trans.commit()) {
       this.response.setStatus(1);
       this.response.setMessage("删除成功！");
     } else {
       this.response.setStatus(0);
       this.response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public static Mapx initEditDialog(Mapx params) {
     String ID = params.getString("ID");
     ZSSendSchema send = new ZSSendSchema();
     if (XString.isNotEmpty(ID)) {
       send.setID(ID);
       send.fill();
       Mapx map = send.toMapx();
       map.put("PicSrc1", send.getProp1());
       map.put("Prop1", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/" + send.getProp1()).replaceAll("//", "/"));
       return map;
     }
     params.put("Prop1", 
       (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
       .replaceAll("//", "/"));
     return params;
   }
 }

          
/*    com.xdarkness.shop.SendType
 * JD-Core Version:    0.6.0
 */