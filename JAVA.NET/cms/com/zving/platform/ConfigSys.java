 package com.zving.platform;
 
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDConfigSchema;
 import com.zving.schema.ZDConfigSet;
 import java.util.Date;
 
 public class ConfigSys extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String SearchType = (String)dga.getParams().get("SearchType");
     QueryBuilder qb = new QueryBuilder(
       "select type,name,value,type as type_key from zdconfig where type not like ? ");
     qb.add("System.");
     if (StringUtil.isNotEmpty(SearchType)) {
       qb.append(" and (type like ? or name like ?)");
       qb.add("%" + SearchType + "%");
       qb.add("%" + SearchType + "%");
     }
     dga.bindData(qb);
   }
 
   public void add() {
     ZDConfigSchema zdconfig = new ZDConfigSchema();
     zdconfig.setValue(this.Request);
     zdconfig.setAddTime(new Date());
     zdconfig.setAddUser(User.getUserName());
     if (zdconfig.getType().startsWith("System.")) {
       this.Response.setStatus(0);
       this.Response.setMessage("不允许添加以“System.”开头的配置项！");
       return;
     }
     if (zdconfig.insert()) {
       Config.update();
       this.Response.setStatus(1);
       this.Response.setMessage("新增类别成功！");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void del() {
     String ids = $V("IDs");
     ids = ids.replaceAll(",", "','");
     Transaction trans = new Transaction();
     ZDConfigSchema zdconfig = new ZDConfigSchema();
     ZDConfigSet set = zdconfig.query(new QueryBuilder("where type in ('" + ids + "')"));
     trans.add(set, 5);
 
     StringBuffer configLog = new StringBuffer("删除配置项:");
     if (set.size() > 0) {
       configLog.append(set.get(0).getName() + " 等");
     }
     if (trans.commit()) {
       Config.update();
       UserLog.log("System", "DelConfig", configLog + "成功", this.Request.getClientIP());
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功！");
     } else {
       UserLog.log("System", "DelConfig", configLog + "失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void dg1Edit() {
     DataTable dt = (DataTable)this.Request.get("DT");
     Transaction trans = new Transaction();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       QueryBuilder qb = new QueryBuilder("update ZDConfig set type=?,name=?,value=? where type=?");
       qb.add(dt.getString(i, "type"));
       qb.add(dt.getString(i, "name"));
       qb.add(dt.getString(i, "value"));
       qb.add(dt.getString(i, "type_key"));
       trans.add(qb);
     }
     if (trans.commit()) {
       Config.update();
       this.Response.setStatus(1);
       this.Response.setMessage("修改成功!");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("修改失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.ConfigSys
 * JD-Core Version:    0.5.4
 */