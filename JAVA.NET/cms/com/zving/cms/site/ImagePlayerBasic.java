 package com.zving.cms.site;
 
 import com.zving.cms.pub.CatalogUtil;
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
 import com.zving.schema.ZCImagePlayerSchema;
 import com.zving.schema.ZCImagePlayerSet;
 import java.util.Date;
 
 public class ImagePlayerBasic extends Page
 {
   public static final String IMAGESOURCE_LOCAL = "0";
   public static final String IMAGESOURCE_CATALOG_FIRST = "1";
   public static final String IMAGESOURCE_CATALOG_SELECT = "2";
   public static final Mapx IMAGESOURCE_MAP = new Mapx();
 
   static {
     IMAGESOURCE_MAP.put("0", "本地上传");
     IMAGESOURCE_MAP.put("1", "所属栏目文章中的图片(自动取第一张)");
     IMAGESOURCE_MAP.put("2", "所属栏目文章中的图片(编辑手工选择)");
   }
 
   public static Mapx init(Mapx params) {
     String imagePlayerID = params.getString("ImagePlayerID");
     if (StringUtil.isNotEmpty(imagePlayerID)) {
       long ID = Long.parseLong(imagePlayerID);
       ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
       ImagePlayer.setID(ID);
       ImagePlayer.fill();
       Mapx map = ImagePlayer.toMapx();
       map.put("ImagePlayerID", ImagePlayer.getID());
       map.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText", "YesOrNo", ImagePlayer.getIsShowText()));
       map.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, ImagePlayer
         .getImageSource()));
       return map;
     }
     params.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText", "YesOrNo", "Y"));
     params.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, "0"));
     params.put("display", "none");
 
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     QueryBuilder qb = new QueryBuilder(
       "select ID,Name,Code,SiteID,DisplayType,ImageSource,Height,Width,Displaycount from ZCImagePlayer order by ID ");
     dga.bindData(qb);
   }
 
   public void add() {
     String imagePlayerID = $V("ImagePlayerID");
     ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
     if (StringUtil.isNotEmpty(imagePlayerID))
     {
       ImagePlayer.setID(imagePlayerID);
       ImagePlayer.fill();
       ImagePlayer.setValue(this.Request);
       ImagePlayer.setModifyTime(new Date());
       ImagePlayer.setModifyUser(User.getUserName());
       ImagePlayer.setDisplayType("1");
       if (StringUtil.isNotEmpty($V("RelaCatalogID")))
         ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
       else {
         ImagePlayer.setRelaCatalogInnerCode("0");
       }
       if (ImagePlayer.update()) {
         this.Response.setStatus(1);
         this.Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource=" + 
           ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode());
         this.Response.setMessage("保存成功,您可以去‘预览’查看修改后的效果!");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     }
     else {
       DataTable checkDT = new QueryBuilder("select * from zcimageplayer where code=? and siteID=?", $V("Code"), 
         Application.getCurrentSiteID()).executeDataTable();
       if (checkDT.getRowCount() > 0) {
         this.Response.setLogInfo(0, "已经存在代码为‘ <b style='color:#F00'>" + $V("Code") + "</b>’ 的图片播放器，请更换播放器代码！");
         return;
       }
       ImagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
       ImagePlayer.setValue(this.Request);
       ImagePlayer.setDisplayType("1");
       ImagePlayer.setSiteID(Application.getCurrentSiteID());
       ImagePlayer.setAddTime(new Date());
       ImagePlayer.setAddUser(User.getUserName());
 
       if (StringUtil.isNotEmpty($V("RelaCatalogID")))
         ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
       else {
         ImagePlayer.setRelaCatalogInnerCode("0");
       }
 
       if (ImagePlayer.insert()) {
         this.Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource=" + 
           ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode());
         this.Response.setStatus(1);
         this.Response.setMessage("新建成功,您现在可以关联图片了!");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("发生错误!");
       }
     }
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
     ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
 
     if (trans.commit()) {
       this.Response.setMessage("删除成功,您可以去‘预览’查看删除后的效果!");
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.ImagePlayerBasic
 * JD-Core Version:    0.5.4
 */