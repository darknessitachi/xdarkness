 package com.zving.cms.dataservice;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.SelectTag;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.member.Member;
 import com.zving.platform.Application;
 import com.zving.schema.ZDMemberFieldSchema;
 import com.zving.schema.ZDMemberFieldSet;
 import com.zving.schema.ZDMemberSchema;
 import java.util.Date;
 
 public class MemberField extends Page
 {
   public static final String PREFIX = "_MC_";
   public static String Input = "input";
 
   public static String Text = "textarea";
 
   public static String Selecter = "select";
 
   public static String Radio = "radio";
 
   public static String Checkbox = "checkbox";
 
   public static String DateInput = "date";
 
   public static String HTMLInput = "html";
 
   public static String TimeInput = "time";
 
   public static Mapx InputTypeMap = new Mapx();
   public static String STRING;
   public static String NUMBER;
   public static String INT;
   public static String EMAIL;
   public static Mapx VerifyTypeMap;
 
   static
   {
     InputTypeMap.put(Input, "文本框");
     InputTypeMap.put(Text, "多行文本框");
     InputTypeMap.put(Selecter, "下拉框");
     InputTypeMap.put(Radio, "单选框");
     InputTypeMap.put(Checkbox, "多选框");
     InputTypeMap.put(DateInput, "日期框");
     InputTypeMap.put(TimeInput, "时间框");
     InputTypeMap.put(HTMLInput, "HTML");
 
     STRING = "1";
 
     NUMBER = "2";
 
     INT = "3";
 
     EMAIL = "5";
 
     VerifyTypeMap = new Mapx();
 
     VerifyTypeMap.put(STRING, "无");
     VerifyTypeMap.put(NUMBER, "数字");
     VerifyTypeMap.put(INT, "整数");
     VerifyTypeMap.put(EMAIL, "邮箱");
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     DataTable dt = new QueryBuilder("select * from ZDMemberField order by AddTime desc").executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.decodeColumn("InputType", InputTypeMap);
     dt.decodeColumn("VerifyType", VerifyTypeMap);
     dga.bindData(dt);
   }
 
   public static Mapx initDialog(Mapx params) {
     String Code = params.getString("Code");
     if (StringUtil.isEmpty(Code)) {
       params.put("VerifyType", HtmlUtil.mapxToOptions(VerifyTypeMap));
       params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap));
       params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
       params.put("MaxLength", "100");
       params.put("Cols", "265");
       params.put("Rows", "90");
     } else {
       ZDMemberFieldSchema field = new ZDMemberFieldSchema();
       field.setCode(Code);
       field.setSiteID(Application.getCurrentSiteID());
       field.fill();
       params = field.toMapx();
       params.put("VerifyType", HtmlUtil.mapxToOptions(VerifyTypeMap, field.getVerifyType()));
       params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap, field.getInputType()));
       params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", field.getIsMandatory()));
     }
     return params;
   }
 
   public void add() {
     String hCode = $V("hCode");
     boolean update = false;
     int FieldCount = new QueryBuilder("select count(*) from ZDMemberField where SiteID = ?", Application.getCurrentSiteID()).executeInt();
     ZDMemberFieldSchema field = new ZDMemberFieldSchema();
     field.setCode($V("Code").trim());
     field.setSiteID(Application.getCurrentSiteID());
     if ((StringUtil.isEmpty(hCode)) && (field.fill())) {
       this.Response.setLogInfo(0, "已有相同字段");
       return;
     }if ((StringUtil.isNotEmpty(hCode)) && (field.fill())) {
       update = true;
     }
     if (!update) {
       if (FieldCount < 20) {
         int index = 1;
         for (int i = 1; i <= 20; ++i) {
           if (new QueryBuilder("select count(*) from ZDMemberField where SiteID = ? and RealField = 'Prop" + i + "'", Application.getCurrentSiteID()).executeInt() == 0) {
             index = i;
             break;
           }
         }
         field.setRealField("Prop" + index);
       } else {
         this.Response.setLogInfo(0, "达到扩展字段上限");
         return;
       }
     }
     field.setValue(this.Request);
     field.setName($V("Name"));
     field.setCode($V("Code"));
     field.setAddUser(User.getUserName());
     field.setAddTime(new Date());
 
     String defaultValue = field.getDefaultValue();
     defaultValue = defaultValue.replaceAll("　　", ",");
     defaultValue = defaultValue.replaceAll("　", ",");
     defaultValue = defaultValue.replaceAll("  ", ",");
     defaultValue = defaultValue.replaceAll(" ", ",");
     defaultValue = defaultValue.replaceAll(",,", ",");
     defaultValue = defaultValue.replaceAll("，，", ",");
     defaultValue = defaultValue.replaceAll("，", ",");
     if (StringUtil.isEmpty(defaultValue)) {
       defaultValue = "";
     }
     field.setDefaultValue(defaultValue);
 
     if (Input.equals(field.getInputType())) {
       field.setColSize(null);
       field.setRowSize(null);
       field.setListOption("");
     } else if (Text.equals(field.getInputType())) {
       field.setListOption("");
     } else if (Selecter.equals(field.getInputType())) {
       field.setColSize(null);
       field.setRowSize(null);
       field.setMaxLength(null);
       field.setVerifyType(STRING);
     } else if (Radio.equals(field.getInputType())) {
       field.setIsMandatory("N");
       field.setColSize(null);
       field.setRowSize(null);
       field.setMaxLength(null);
       field.setVerifyType(STRING);
     } else if (Checkbox.equals(field.getInputType())) {
       field.setIsMandatory("N");
       field.setColSize(null);
       field.setRowSize(null);
       field.setMaxLength(null);
       field.setVerifyType(STRING);
     } else if ((DateInput.equals(field.getInputType())) || 
       (TimeInput.equals(field.getInputType()))) {
       field.setColSize(null);
       field.setRowSize(null);
       field.setMaxLength(null);
       field.setListOption("");
       field.setVerifyType(STRING);
     } else if (HTMLInput.equals(field.getInputType())) {
       field.setIsMandatory("N");
       field.setColSize(null);
       field.setRowSize(null);
       field.setMaxLength(null);
       field.setListOption("");
       field.setVerifyType(STRING);
     }
 
     Transaction trans = new Transaction();
     if (update)
       trans.add(field, 2);
     else {
       trans.add(field, 1);
     }
     if (trans.commit())
       this.Response.setLogInfo(1, "保存会员扩展字段成功！");
     else
       this.Response.setLogInfo(0, "发生错误!");
   }
 
   public static String getColumns(String SiteID)
   {
     DataTable dt = new QueryBuilder("select * from ZDMemberField where SiteID = ? order by AddTime asc", SiteID).executeDataTable();
     String Columns = "";
     for (int i = 0; i < dt.getRowCount(); ++i) {
       Columns = Columns + getColumn(dt.getDataRow(i));
     }
     return Columns;
   }
 
   public static String getColumnAndValue(ZDMemberSchema member) {
     DataTable dt = new QueryBuilder("select * from ZDMemberField where SiteID = ? order by AddTime asc", member.getSiteID()).executeDataTable();
     String Columns = "";
     for (int i = 0; i < dt.getRowCount(); ++i) {
       Columns = Columns + getColumn(dt.getDataRow(i), member, dt.getString(i, "RealField"));
     }
     return Columns;
   }
 
   private static String getColumn(DataRow dr) {
     return getColumn(dr, null, null);
   }
 
   private static String getColumn(DataRow dr, ZDMemberSchema member, String realField) {
     String columnName = dr.getString("Name");
     String columnCode = dr.getString("Code");
     String inputType = dr.getString("InputType");
     String verifyType = dr.getString("VerifyType");
     String listOption = dr.getString("ListOption");
     String defaultValue = dr.getString("DefaultValue");
     String isMandatory = dr.getString("IsMandatory");
     String maxLength = dr.getString("MaxLength");
     String HTML = dr.getString("HTML");
     String verifyStr = "verify='" + columnName + "|";
     if ("Y".equals(isMandatory)) {
       verifyStr = verifyStr + "NotNull";
     }
     if (!STRING.equals(verifyType))
     {
       if (NUMBER.equals(verifyType))
         verifyStr = verifyStr + "&&Number";
       else if (INT.equals(verifyType))
         verifyStr = verifyStr + "&&Int";
       else if (EMAIL.equals(verifyType))
         verifyStr = verifyStr + "&&Email";
     }
     if ((StringUtil.isNotEmpty(maxLength)) && (!"0".equals(maxLength)))
       verifyStr = verifyStr + "&&Length<" + maxLength + "'";
     else {
       verifyStr = verifyStr + "'";
     }
 
     if ((member != null) && (realField != null)) {
       Mapx map = member.toMapx();
       defaultValue = map.getString(realField);
       if (StringUtil.isEmpty(defaultValue)) {
         defaultValue = "";
       }
     }
 
     columnCode = "_MC_" + columnCode;
     StringBuffer sb = new StringBuffer();
     sb.append("<tr><td height='25' align='right' >");
     sb.append(columnName);
     sb.append("：</td><td align='left' >");
 
     if (inputType.equals(Input)) {
       sb.append("<input type='text' size='26' id='" + columnCode + "' name='" + columnCode + "' value='" + defaultValue + "' " + verifyStr + " />");
     }
 
     if (inputType.equals(Text)) {
       sb.append("<textarea style='width:" + dr.getString("ColSize") + "px;height:" + dr.getString("RowSize") + "px' id='" + 
         columnCode + "' name='" + columnCode + "' " + verifyStr + ">" + defaultValue + "</textarea>");
     }
 
     if (inputType.equals(Selecter)) {
       SelectTag select = new SelectTag();
       select.setId(columnCode);
       if ("Y".equals(isMandatory)) {
         select.setVerify(columnName + "|NotNull");
       }
       String[] array = listOption.split("\\n");
       sb.append(select.getHtml(HtmlUtil.arrayToOptions(array, defaultValue, true)));
     }
 
     if (inputType.equals(Radio)) {
       String[] array = listOption.split("\\n");
       if ((StringUtil.isEmpty(defaultValue)) && (array.length > 0)) {
         defaultValue = array[0];
       }
       sb.append(HtmlUtil.arrayToRadios(columnCode, array, defaultValue));
     }
 
     if (inputType.equals(Checkbox)) {
       String[] array = listOption.split("\\n");
       defaultValue = defaultValue.replaceAll("　　", ",");
       defaultValue = defaultValue.replaceAll("　", ",");
       defaultValue = defaultValue.replaceAll("  ", ",");
       defaultValue = defaultValue.replaceAll(" ", ",");
       defaultValue = defaultValue.replaceAll(",,", ",");
       defaultValue = defaultValue.replaceAll("，，", ",");
       defaultValue = defaultValue.replaceAll("，", ",");
       String[] checkedArray = defaultValue.split(",");
       sb.append(HtmlUtil.arrayToCheckboxes(columnCode, array, checkedArray));
     }
 
     if (inputType.equals(DateInput)) {
       sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue + 
         "' type='text'  size='20' ztype='Date' " + verifyStr + " />");
     }
 
     if (inputType.equals(TimeInput)) {
       sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue + 
         "' type='text' size='10' ztype='Time' " + verifyStr + " />");
     }
 
     if (inputType.equals(HTMLInput)) {
       sb.append(HTML);
     }
     sb.append("</td></tr>");
     return sb.toString();
   }
 
   public static Member setPropValues(Member member, Mapx Request) {
     ZDMemberSchema m = new ZDMemberSchema();
     String siteID = "";
     m.setUserName(member.getUserName());
     if (m.fill())
       siteID = m.getSiteID();
     else {
       siteID = Request.getString("SiteID");
     }
     m = setPropValues(m, Request, siteID);
     member.setValue(m.toMapx());
     return member;
   }
 
   public static ZDMemberSchema setPropValues(ZDMemberSchema member, Mapx map, String SiteID) {
     ZDMemberFieldSchema field = new ZDMemberFieldSchema();
     ZDMemberFieldSet set = field.query(new QueryBuilder(" where SiteID = ?", SiteID));
     for (int i = 0; i < set.size(); ++i) {
       String Value = "";
       String RealField = "";
       String Code = "";
       field = new ZDMemberFieldSchema();
       field = set.get(i);
       Code = field.getCode();
       RealField = field.getRealField();
       Value = map.getString("_MC_" + Code);
       map.put(RealField, Value);
     }
     member.setValue(map);
     return member;
   }
 
   public void del() {
     String Codes = $V("Codes");
     if ((Codes.indexOf("\"") >= 0) || (Codes.indexOf("'") >= 0)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Codes = StringUtil.replaceEx(Codes, ",", "','");
     Transaction trans = new Transaction();
     ZDMemberFieldSchema field = new ZDMemberFieldSchema();
     ZDMemberFieldSet set = field.query(new QueryBuilder("where SiteID = " + Application.getCurrentSiteID() + " and Code in ('" + Codes + "')"));
     trans.add(set, 3);
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功！");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.MemberField
 * JD-Core Version:    0.5.4
 */