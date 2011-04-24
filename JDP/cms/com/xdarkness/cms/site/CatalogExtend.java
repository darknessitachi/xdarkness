 package com.xdarkness.cms.site;
 
 import java.util.Date;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZDColumnRelaSchema;
import com.xdarkness.schema.ZDColumnRelaSet;
import com.xdarkness.schema.ZDColumnSchema;
import com.xdarkness.schema.ZDColumnSet;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZDColumnValueSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
 
 public class CatalogExtend extends Page
 {
   public static String Input = "1";
 
   public static String ImageInput = "7";
 
   public static String HtmlInput = "8";
 
   public static Mapx InputTypeMap = new Mapx();
 
   static {
     InputTypeMap.put(Input, "文本框");
     InputTypeMap.put(ImageInput, "媒体库图片框");
     InputTypeMap.put(HtmlInput, "HTML");
   }
 
   public static Mapx initDialog(Mapx params) {
     String ID = params.getString("ColumnID");
     if (XString.isEmpty(ID)) {
       params.put("ImagePath", "upload/Image/nopicture.jpg");
       params.put("ImageFile", 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         ApplicationPage.getCurrentSiteAlias() + "/" + "upload/Image/nopicture.jpg").replaceAll("//", "/"));
       params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap));
     } else {
       String CatalogID = params.getString("CatalogID");
       QueryBuilder qb = new QueryBuilder(
         "select b.ColumnID as id,a.Code,a.InputType,a.Name,b.ID as ValueID,b.ColumnID,b.TextValue from ZDColumn a, ZDColumnValue b where a.ID = b.ColumnID and b.RelaType='0' and b.RelaID =? and b.ColumnID= ? order by a.OrderFlag asc", 
         CatalogID, ID);
       DataTable dt = qb.executeDataTable();
       DataRow dr = dt.getDataRow(0);
       params = dr.toMapx();
       String inputType = dr.getString("InputType");
       if (Input.equals(inputType)) {
         params.put("Text", dr.getString("TextValue"));
       } else if (ImageInput.equals(inputType)) {
         params.put("ImagePath", dr.getString("TextValue"));
         params.put("ImageFile", 
           (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
           ApplicationPage.getCurrentSiteAlias() + "/" + dr.getString("TextValue")).replaceAll("//", "/"));
       } else if (HtmlInput.equals(inputType)) {
         params.put("Content", dr.getString("TextValue"));
       }
       params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap, inputType));
     }
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String CatalogID = dga.getParam("CatalogID");
     QueryBuilder qb = new QueryBuilder(
       "select a.Code,a.InputType,a.Name,b.ID,b.ColumnID,b.TextValue from ZDColumn a, ZDColumnValue b where a.ID = b.ColumnID and b.RelaType='0' and b.RelaID =? order by a.OrderFlag asc", 
       CatalogID);
     DataTable dt = qb.executeDataTable();
     String path = (Config.getContextPath() + Config.getValue("UploadDir") + "/" + ApplicationPage.getCurrentSiteAlias() + "/")
       .replaceAll("//", "/");
     String Str = "";
     for (int i = 0; i < dt.getRowCount(); i++) {
       if (ImageInput.equals(dt.getString(i, "InputType"))) {
         dt.set(i, "TextValue", "<img src='" + path + dt.getString(i, "TextValue") + 
           "' width='100' height='75'>");
       }
       if (HtmlInput.equals(dt.getString(i, "InputType"))) {
         Str = dt.getString(i, "TextValue");
         if (Str.length() > 50) {
           Str = Str.substring(0, 50) + "...";
         }
         dt.set(i, "TextValue", XString.htmlEncode(Str));
       }
     }
     dt.decodeColumn("InputType", InputTypeMap);
     dga.bindData(dt);
   }
 
   public void add() {
     String catalogID = $V("CatalogID");
     String columnCode = $V("Code");
     String textValue = "";
     long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='0' and RelaID = ? and ColumnCode =? ", 
       catalogID, columnCode)
       .executeLong();
     if (count > 0L) {
       this.response.setLogInfo(0, "已经存在相同的字段");
       return;
     }
     Transaction trans = new Transaction();
     ZDColumnSchema column = new ZDColumnSchema();
     column.setID(NoUtil.getMaxID("ColumnID"));
     column.setCode($V("Code"));
     column.setName($V("Name"));
     column.setSiteID(ApplicationPage.getCurrentSiteID());
     column.setInputType($V("InputType"));
     column.setVerifyType(ColumnUtil.STRING);
     column.setIsMandatory("N");
     column.setOrderFlag(OrderUtil.getDefaultOrder());
     column.setAddTime(new Date());
     column.setAddUser(User.getUserName());
 
     ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
     rela.setID(NoUtil.getMaxID("ColumnRelaID"));
     rela.setColumnID(column.getID());
     rela.setColumnCode(columnCode);
     rela.setRelaType("0");
     rela.setRelaID(catalogID);
     rela.setAddTime(column.getAddTime());
     rela.setAddUser(column.getAddUser());
 
     if (Input.equals(column.getInputType())) {
       textValue = $V("TextValue");
     } else if (ImageInput.equals(column.getInputType())) {
       textValue = $V("ImagePath");
       if ((XString.isNotEmpty(textValue)) && (textValue.indexOf("upload") > 0))
         textValue = textValue.substring(textValue.indexOf("upload"));
     }
     else if (HtmlInput.equals(column.getInputType())) {
       textValue = $V("Content");
     }
     ZDColumnValueSchema value = new ZDColumnValueSchema();
     value.setID(NoUtil.getMaxID("ColumnValueID"));
     value.setColumnID(column.getID());
     value.setColumnCode(columnCode);
     value.setRelaType("0");
     value.setRelaID(catalogID);
     value.setTextValue(textValue);
 
     ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
     catalog.setModifyTime(new Date());
     catalog.setModifyUser(User.getUserName());
     trans.add(catalog, OperateType.UPDATE);
 
     trans.add(column, OperateType.INSERT);
     trans.add(rela, OperateType.INSERT);
     trans.add(value, OperateType.INSERT);
     if (trans.commit())
       this.response.setLogInfo(1, "新建成功");
     else
       this.response.setLogInfo(0, "新建失败");
   }
 
   public void save()
   {
     String catalogID = $V("CatalogID");
     String columnID = $V("ColumnID");
     String valueID = $V("ValueID");
     String columnCode = $V("Code");
     String textValue = "";
     QueryBuilder qb = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='0' and RelaID = ? and ColumnCode =? and ColumnID<>?", 
       catalogID, columnCode);
     qb.add(columnID);
     long count = qb.executeLong();
     if (count > 0L) {
       this.response.setLogInfo(0, "已经存在相同的字段");
       return;
     }
     Transaction trans = new Transaction();
     ZDColumnSchema column = new ZDColumnSchema();
     column.setID(columnID);
     column.fill();
     column.setCode($V("Code"));
     column.setName($V("Name"));
     column.setInputType($V("InputType"));
     column.setVerifyType(ColumnUtil.STRING);
     column.setIsMandatory("N");
     column.setModifyTime(new Date());
     column.setModifyUser(User.getUserName());
 
     ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
     rela.setColumnID(column.getID());
     rela.setRelaType("0");
     rela.setRelaID(catalogID);
     rela = rela.query().get(0);
 
     rela.setColumnID(column.getID());
     rela.setColumnCode(columnCode);
     rela.setRelaType("0");
     rela.setRelaID(catalogID);
     rela.setModifyTime(column.getModifyTime());
     rela.setModifyUser(column.getModifyUser());
 
     if (Input.equals(column.getInputType())) {
       textValue = $V("TextValue");
     } else if (ImageInput.equals(column.getInputType())) {
       textValue = $V("ImagePath");
       if ((XString.isNotEmpty(textValue)) && (textValue.indexOf("upload") > 0))
         textValue = textValue.substring(textValue.indexOf("upload"));
     }
     else if (HtmlInput.equals(column.getInputType())) {
       textValue = $V("Content");
     }
     ZDColumnValueSchema value = new ZDColumnValueSchema();
     value.setID(valueID);
     value.setColumnID(column.getID());
     value.setColumnCode(columnCode);
     value.setRelaType("0");
     value.setRelaID(catalogID);
     value.setTextValue(textValue);
 
     ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
     catalog.setModifyTime(new Date());
     catalog.setModifyUser(User.getUserName());
     trans.add(catalog, OperateType.UPDATE);
 
     trans.add(column, OperateType.UPDATE);
     trans.add(rela, OperateType.UPDATE);
     trans.add(value, OperateType.UPDATE);
     if (trans.commit())
       this.response.setLogInfo(1, "修改成功");
     else
       this.response.setLogInfo(0, "修改失败");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!XString.checkID(ids)) {
       this.response.setStatus(0);
       this.response.setMessage("传入ID时发生错误!");
       return;
     }
     long catalogID = Long.parseLong($V("CatalogID"));
     Transaction trans = new Transaction();
     ZDColumnSet columnSet = new ZDColumnSchema().query(new QueryBuilder("where ID in (" + ids + ") "));
     ZDColumnRelaSet relaSet = new ZDColumnRelaSchema().query(
       new QueryBuilder("where columnID in (" + ids + 
       ") and RelaType='" + "0" + "' and RelaID='" + catalogID + "'"));
     ZDColumnValueSet valueSet = new ZDColumnValueSchema().query(
       new QueryBuilder("where columnID in (" + ids + 
       ") and RelaType='" + "0" + "' and RelaID='" + catalogID + "'"));
 
     trans.add(columnSet, OperateType.DELETE_AND_BACKUP);
     trans.add(relaSet, OperateType.DELETE_AND_BACKUP);
     trans.add(valueSet, OperateType.DELETE_AND_BACKUP);
 
     ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
     catalog.setModifyTime(new Date());
     catalog.setModifyUser(User.getUserName());
     trans.add(catalog, OperateType.UPDATE);
 
     if (trans.commit())
       this.response.setLogInfo(1, "删除成功");
     else
       this.response.setLogInfo(0, "删除失败");
   }
 
   public void getPicSrc()
   {
     String ID = $V("PicID");
     DataTable dt = new QueryBuilder("select path,filename,srcfilename from zcimage where id=?", ID)
       .executeDataTable();
     if (dt.getRowCount() > 0) {
       String imageSrc = Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_" + 
         dt.get(0, "filename").toString();
       imageSrc = imageSrc.replaceAll("/+", "/");
       this.response.put("picSrc", imageSrc);
       this.response.put("ImagePath", dt.get(0, "path").toString() + dt.get(0, "srcfilename").toString());
     }
   }
 }

          
/*    com.xdarkness.cms.site.CatalogExtend
 * JD-Core Version:    0.6.0
 */