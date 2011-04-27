 package com.zving.workflow;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZWWorkflowSchema;
 import com.zving.schema.ZWWorkflowSet;
 import java.util.Date;
 
 public class WorkflowPage extends Page
 {
   public static Mapx init(Mapx params)
   {
     String id = params.getString("ID");
     if (StringUtil.isNotEmpty(id)) {
       ZWWorkflowSchema fc = new ZWWorkflowSchema();
       fc.setID(id);
       fc.fill();
       params.put("Name", fc.getName());
       params.put("ID", fc.getID());
       params.put("Memo", fc.getMemo());
       params.put("XML", StringUtil.javaEncode(fc.getConfigXML()));
     }
 
     return params;
   }
 
   public static void roleDataBind(DataGridAction dga) {
     DataTable dt = new QueryBuilder("select RoleCode,RoleName from ZDRole").executeDataTable();
     dga.bindData(dt);
   }
 
   public void save() {
     ZWWorkflowSchema wd = new ZWWorkflowSchema();
     if (StringUtil.isNotEmpty($V("ID"))) {
       wd.setID($V("ID"));
       wd.fill();
     } else {
       wd.setID(NoUtil.getMaxID("WorkflowID"));
       wd.setAddTime(new Date());
       wd.setAddUser(User.getUserName());
     }
     wd.setModifyTime(new Date());
     wd.setModifyUser(User.getUserName());
     wd.setName($V("Name"));
     wd.setConfigXML(StringUtil.htmlDecode($V("XML")));
     wd.setMemo($V("Memo"));
     this.Response.put("ID", wd.getID());
     boolean flag = true;
     if (StringUtil.isNotEmpty($V("ID")))
       flag = wd.update();
     else {
       flag = wd.insert();
     }
     if (flag) {
       WorkflowUtil.updateCache(wd);
       this.Response.setMessage("保存成功!");
     } else {
       this.Response.setError("保存数据到数据库时发生错误!");
     }
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String sql = " select * from ZWWorkflow";
     dga.bindData(new QueryBuilder(sql));
   }
 
   public void del() {
     String IDs = $V("IDs");
     if (!StringUtil.checkID(IDs)) {
       this.Response.setLogInfo(0, "传入工作流发生错误!");
       return;
     }
     ZWWorkflowSchema wf = new ZWWorkflowSchema();
     ZWWorkflowSet set = wf.query(new QueryBuilder("where ID in (" + IDs + ")"));
     Transaction tran = new Transaction();
     for (int i = 0; i < set.size(); ++i) {
       WorkflowUtil.findAdapter().onWorkflowDelete(tran, set.get(i).getID());
     }
     tran.add(new QueryBuilder("delete from ZWInstance where WorkflowID in (" + IDs + ")"));
     tran.add(new QueryBuilder("delete from ZWStep where WorkflowID in (" + IDs + ")"));
     tran.add(set, 5);
     if (tran.commit()) {
       for (int i = 0; i < set.size(); ++i) {
         WorkflowUtil.deleteCache(set.get(i));
       }
       this.Response.setLogInfo(1, "删除成功！");
     } else {
       this.Response.setLogInfo(0, "删除失败！");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.WorkflowPage
 * JD-Core Version:    0.5.4
 */