 package com.zving.cms.dataservice;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCQuestionSchema;
 import com.zving.schema.ZCQuestionSet;
 import java.util.Date;
 
 public class Question extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String InnerCode = dga.getParam("QuestionInnerCode");
     QueryBuilder qb = new QueryBuilder("select *  from zcquestion where 1=1 ");
     if (StringUtil.isEmpty(InnerCode)) {
       LogUtil.info("没有得到InnerCode的值,历史InnerCode：" + dga.getParams().getString("Cookie.Ask.InnerCode"));
       qb.append(" and QuestionInnerCode = ?");
       InnerCode = dga.getParam("Cookie.Ask.InnerCode");
     } else {
       qb.append(" and QuestionInnerCode = ?");
     }
     qb.append(" order by AddTime desc");
     qb.add(InnerCode.trim());
     dga.bindData(qb);
   }
 
   public static Mapx initDialog(Mapx params)
   {
     String ID = params.getString("ID");
     String questionInnerCode = params.getString("QuestionInnerCode");
     if (StringUtil.isNotEmpty(ID)) {
       ZCQuestionSchema question = new ZCQuestionSchema();
       question.setID(ID);
       if (!question.fill()) {
         return params;
       }
       Mapx map = question.toMapx();
       String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?", 
         questionInnerCode).executeString();
       map.put("QuestionInnerCode", questionInnerCode);
       map.put("QuestionName", questionName);
       return map;
     }
     String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?", 
       questionInnerCode).executeString();
     params.put("QuestionInnerCode", questionInnerCode);
     params.put("QuestionName", questionName);
     return params;
   }
 
   public void add()
   {
     String questionInnerCode = $V("QuestionInnerCode");
     if (StringUtil.isEmpty(questionInnerCode)) {
       this.Response.setLogInfo(0, "操作失败，请选择分类!");
       return;
     }
     ZCQuestionSchema question = new ZCQuestionSchema();
     question.setID(NoUtil.getMaxID("QuestionID"));
     question.setValue(this.Request);
     question.setQuestionInnerCode(questionInnerCode);
     question.setAddUser(User.getUserName());
     Date currentDate = new Date();
     question.setAddTime(currentDate);
     if (question.insert())
       this.Response.setLogInfo(1, "操作成功!");
     else
       this.Response.setLogInfo(0, "操作失败!");
   }
 
   public void edit()
   {
     ZCQuestionSchema question = new ZCQuestionSchema();
     String id = $V("ID");
     question.setID(id);
     question.fill();
     question.setValue(this.Request);
     question.setModifyTime(new Date());
     question.setModifyUser(User.getUserName());
     if (question.update())
       this.Response.setLogInfo(1, "操作成功!");
     else
       this.Response.setLogInfo(0, "操作失败!");
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
     ZCQuestionSchema question = new ZCQuestionSchema();
     ZCQuestionSet questionset = question.query(new QueryBuilder("where ID in (" + ids + ")"));
     trans.add(questionset, 5);
     if (trans.commit())
       this.Response.setLogInfo(1, "操作成功!");
     else
       this.Response.setLogInfo(0, "操作失败!");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.Question
 * JD-Core Version:    0.5.4
 */