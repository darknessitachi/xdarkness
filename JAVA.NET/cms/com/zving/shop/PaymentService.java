 package com.zving.shop;
 
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZSPaymentPropSchema;
 import com.zving.schema.ZSPaymentPropSet;
 import com.zving.schema.ZSPaymentSchema;
 import com.zving.schema.ZSPaymentSet;
 import java.util.Date;
 
 public class PaymentService extends Page
 {
   public void save()
   {
     String paymentName = new QueryBuilder("select CodeName from ZDCode where ParentCode='Payment' and CodeValue=?", 
       this.Request.get("PaymentPath")).executeOneValue().toString();
     ZSPaymentSet payments = new ZSPaymentSchema().query(
       new QueryBuilder("where SiteID = ? and name = ?", 
       Application.getCurrentSiteID(), paymentName));
     if (payments.size() > 0) {
       ZSPaymentSchema payment = payments.get(0);
       payment.setValue(this.Request);
       payment.setModifyUser(User.getUserName());
       payment.setModifyTime(new Date());
 
       String ImageID = $V("ImageID");
       if (StringUtil.isNotEmpty(ImageID)) {
         DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
           .executeDataTable();
         payment.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
           .toString());
       } else {
         payment.setImagePath("upload/Image/nopicture.jpg");
       }
 
       Object[] reqKeys = this.Request.keyArray();
       for (int i = 0; i < reqKeys.length; ++i) {
         if (reqKeys[i].toString().split("_")[0].equals("Pmt")) {
           ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema().query(new QueryBuilder("where PropName = ? and PaymentID = ?", this.Request.get(reqKeys[i]), payment.getID()));
 
           if (pmtProp.size() > 0) {
             pmtProp.get(0).setPropName(reqKeys[i].toString().split("_")[1]);
             pmtProp.get(0).setPropValue(this.Request.getString(reqKeys[i]));
             pmtProp.get(0).setModifyUser(User.getUserName());
             pmtProp.get(0).setModifyTime(new Date());
             pmtProp.get(0).update();
           } else {
             ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
             prop.setID(NoUtil.getMaxNo("PaymentPropID"));
             prop.setPaymentID(payment.getID());
             prop.setPropName(reqKeys[i].toString().split("_")[1]);
             prop.setPropValue(this.Request.getString(reqKeys[i]));
             prop.setAddUser(User.getUserName());
             prop.setAddTime(new Date());
             prop.insert();
           }
         }
       }
 
       if (payment.update()) {
         this.Response.setStatus(1);
         this.Response.setMessage("保存成功！");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     } else {
       ZSPaymentSchema payment = new ZSPaymentSchema();
       payment.setValue(this.Request);
       payment.setID(NoUtil.getMaxNo("paymentID"));
       payment.setName(paymentName);
       payment.setSiteID(Application.getCurrentSiteID());
       payment.setAddUser(User.getUserName());
       payment.setAddTime(new Date());
 
       String ImageID = $V("ImageID");
       if (StringUtil.isNotEmpty(ImageID)) {
         DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
           .executeDataTable();
         payment.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
           .toString());
       } else {
         payment.setImagePath("upload/Image/nopicture.jpg");
       }
 
       Object[] reqKeys = this.Request.keyArray();
       for (int i = 0; i < reqKeys.length; ++i) {
         if (reqKeys[i].toString().split("_")[0].equals("Pmt")) {
           ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema().query(new QueryBuilder("where PropName = ? and PaymentID = ?", this.Request.get(reqKeys[i]), payment.getID()));
 
           if (pmtProp.size() > 0) {
             pmtProp.get(0).setPropName(reqKeys[i].toString().split("_")[1]);
             pmtProp.get(0).setPropValue(this.Request.getString(reqKeys[i]));
             pmtProp.get(0).setModifyUser(User.getUserName());
             pmtProp.get(0).setModifyTime(new Date());
             pmtProp.get(0).update();
           } else {
             ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
             prop.setID(NoUtil.getMaxNo("PaymentPropID"));
             prop.setPaymentID(payment.getID());
             prop.setPropName(reqKeys[i].toString().split("_")[1]);
             prop.setPropValue(this.Request.getString(reqKeys[i]));
             prop.setAddUser(User.getUserName());
             prop.setAddTime(new Date());
             prop.insert();
           }
         }
       }
 
       if (payment.insert()) {
         this.Response.setStatus(1);
         this.Response.setMessage("保存成功！");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     }
   }
 
   public static Mapx init(Mapx params) {
     String ID = params.getString("ID");
     if (StringUtil.isNotEmpty(ID)) {
       ZSPaymentSchema zspayment = new ZSPaymentSchema();
       zspayment.setID(ID);
       zspayment.fill();
       Mapx map = zspayment.toMapx();
       map.put("PicSrc1", zspayment.getImagePath());
       map.put("ImagePath", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + zspayment.getImagePath()).replaceAll("//", 
         "/"));
       ZSPaymentPropSet props = new ZSPaymentPropSchema().query(new QueryBuilder("where PaymentID = ?", ID));
       for (int i = 0; i < props.size(); ++i) {
         map.put(props.get(i).getPropName(), props.get(i).getPropValue());
       }
       map.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", 
         (StringUtil.isEmpty(params.get("PaymentSelect").toString())) ? "Shop/PaymentService/PmtAliPay.jsp" : params.get("PaymentSelect").toString(), 
         false));
       return map;
     }
     params.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", 
       (StringUtil.isEmpty(params.get("PaymentSelect").toString())) ? "Shop/PaymentService/PmtAliPay.jsp" : params.get("PaymentSelect").toString(), 
       false));
     params.put("ImagePath", 
       (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
       .replaceAll("//", "/"));
     return params;
   }
 
   public void getPicSrc()
   {
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
 * Qualified Name:     com.zving.shop.PaymentService
 * JD-Core Version:    0.5.4
 */