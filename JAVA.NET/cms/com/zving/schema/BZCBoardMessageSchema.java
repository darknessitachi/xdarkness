 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCBoardMessageSchema extends Schema
 {
   private Long ID;
   private Long BoardID;
   private String Title;
   private String Content;
   private String PublishFlag;
   private String ReplyFlag;
   private String ReplyContent;
   private String EMail;
   private String QQ;
   private String Prop1;
   private String Prop2;
   private String Prop4;
   private String Prop3;
   private String IP;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("BoardID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Title", 1, 2, 100, 0, true, false), 
     new SchemaColumn("Content", 10, 3, 0, 0, true, false), 
     new SchemaColumn("PublishFlag", 1, 4, 2, 0, true, false), 
     new SchemaColumn("ReplyFlag", 1, 5, 2, 0, true, false), 
     new SchemaColumn("ReplyContent", 1, 6, 1000, 0, false, false), 
     new SchemaColumn("EMail", 1, 7, 100, 0, false, false), 
     new SchemaColumn("QQ", 1, 8, 20, 0, false, false), 
     new SchemaColumn("Prop1", 1, 9, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 10, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 11, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 12, 50, 0, false, false), 
     new SchemaColumn("IP", 1, 13, 20, 0, true, false), 
     new SchemaColumn("AddUser", 1, 14, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 15, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 16, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 17, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 18, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 19, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 20, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 21, 50, 0, false, false) };
   public static final String _TableCode = "BZCBoardMessage";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCBoardMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCBoardMessage set ID=?,BoardID=?,Title=?,Content=?,PublishFlag=?,ReplyFlag=?,ReplyContent=?,EMail=?,QQ=?,Prop1=?,Prop2=?,Prop4=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCBoardMessage  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCBoardMessage  where ID=? and BackupNo=?";
 
   public BZCBoardMessageSchema()
   {
     this.TableCode = "BZCBoardMessage";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCBoardMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCBoardMessage set ID=?,BoardID=?,Title=?,Content=?,PublishFlag=?,ReplyFlag=?,ReplyContent=?,EMail=?,QQ=?,Prop1=?,Prop2=?,Prop4=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCBoardMessage  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCBoardMessage  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[22];
   }
 
   protected Schema newInstance() {
     return new BZCBoardMessageSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCBoardMessageSet();
   }
 
   public BZCBoardMessageSet query() {
     return query(null, -1, -1);
   }
 
   public BZCBoardMessageSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCBoardMessageSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCBoardMessageSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCBoardMessageSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.BoardID = null; else this.BoardID = new Long(v.toString()); return; }
     if (i == 2) { this.Title = ((String)v); return; }
     if (i == 3) { this.Content = ((String)v); return; }
     if (i == 4) { this.PublishFlag = ((String)v); return; }
     if (i == 5) { this.ReplyFlag = ((String)v); return; }
     if (i == 6) { this.ReplyContent = ((String)v); return; }
     if (i == 7) { this.EMail = ((String)v); return; }
     if (i == 8) { this.QQ = ((String)v); return; }
     if (i == 9) { this.Prop1 = ((String)v); return; }
     if (i == 10) { this.Prop2 = ((String)v); return; }
     if (i == 11) { this.Prop4 = ((String)v); return; }
     if (i == 12) { this.Prop3 = ((String)v); return; }
     if (i == 13) { this.IP = ((String)v); return; }
     if (i == 14) { this.AddUser = ((String)v); return; }
     if (i == 15) { this.AddTime = ((Date)v); return; }
     if (i == 16) { this.ModifyUser = ((String)v); return; }
     if (i == 17) { this.ModifyTime = ((Date)v); return; }
     if (i == 18) { this.BackupNo = ((String)v); return; }
     if (i == 19) { this.BackupOperator = ((String)v); return; }
     if (i == 20) { this.BackupTime = ((Date)v); return; }
     if (i != 21) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.BoardID;
     if (i == 2) return this.Title;
     if (i == 3) return this.Content;
     if (i == 4) return this.PublishFlag;
     if (i == 5) return this.ReplyFlag;
     if (i == 6) return this.ReplyContent;
     if (i == 7) return this.EMail;
     if (i == 8) return this.QQ;
     if (i == 9) return this.Prop1;
     if (i == 10) return this.Prop2;
     if (i == 11) return this.Prop4;
     if (i == 12) return this.Prop3;
     if (i == 13) return this.IP;
     if (i == 14) return this.AddUser;
     if (i == 15) return this.AddTime;
     if (i == 16) return this.ModifyUser;
     if (i == 17) return this.ModifyTime;
     if (i == 18) return this.BackupNo;
     if (i == 19) return this.BackupOperator;
     if (i == 20) return this.BackupTime;
     if (i == 21) return this.BackupMemo;
     return null;
   }
 
   public long getID()
   {
     if (this.ID == null) return 0L;
     return this.ID.longValue();
   }
 
   public void setID(long iD)
   {
     this.ID = new Long(iD);
   }
 
   public void setID(String iD)
   {
     if (iD == null) {
       this.ID = null;
       return;
     }
     this.ID = new Long(iD);
   }
 
   public long getBoardID()
   {
     if (this.BoardID == null) return 0L;
     return this.BoardID.longValue();
   }
 
   public void setBoardID(long boardID)
   {
     this.BoardID = new Long(boardID);
   }
 
   public void setBoardID(String boardID)
   {
     if (boardID == null) {
       this.BoardID = null;
       return;
     }
     this.BoardID = new Long(boardID);
   }
 
   public String getTitle()
   {
     return this.Title;
   }
 
   public void setTitle(String title)
   {
     this.Title = title;
   }
 
   public String getContent()
   {
     return this.Content;
   }
 
   public void setContent(String content)
   {
     this.Content = content;
   }
 
   public String getPublishFlag()
   {
     return this.PublishFlag;
   }
 
   public void setPublishFlag(String publishFlag)
   {
     this.PublishFlag = publishFlag;
   }
 
   public String getReplyFlag()
   {
     return this.ReplyFlag;
   }
 
   public void setReplyFlag(String replyFlag)
   {
     this.ReplyFlag = replyFlag;
   }
 
   public String getReplyContent()
   {
     return this.ReplyContent;
   }
 
   public void setReplyContent(String replyContent)
   {
     this.ReplyContent = replyContent;
   }
 
   public String getEMail()
   {
     return this.EMail;
   }
 
   public void setEMail(String eMail)
   {
     this.EMail = eMail;
   }
 
   public String getQQ()
   {
     return this.QQ;
   }
 
   public void setQQ(String qQ)
   {
     this.QQ = qQ;
   }
 
   public String getProp1()
   {
     return this.Prop1;
   }
 
   public void setProp1(String prop1)
   {
     this.Prop1 = prop1;
   }
 
   public String getProp2()
   {
     return this.Prop2;
   }
 
   public void setProp2(String prop2)
   {
     this.Prop2 = prop2;
   }
 
   public String getProp4()
   {
     return this.Prop4;
   }
 
   public void setProp4(String prop4)
   {
     this.Prop4 = prop4;
   }
 
   public String getProp3()
   {
     return this.Prop3;
   }
 
   public void setProp3(String prop3)
   {
     this.Prop3 = prop3;
   }
 
   public String getIP()
   {
     return this.IP;
   }
 
   public void setIP(String iP)
   {
     this.IP = iP;
   }
 
   public String getAddUser()
   {
     return this.AddUser;
   }
 
   public void setAddUser(String addUser)
   {
     this.AddUser = addUser;
   }
 
   public Date getAddTime()
   {
     return this.AddTime;
   }
 
   public void setAddTime(Date addTime)
   {
     this.AddTime = addTime;
   }
 
   public String getModifyUser()
   {
     return this.ModifyUser;
   }
 
   public void setModifyUser(String modifyUser)
   {
     this.ModifyUser = modifyUser;
   }
 
   public Date getModifyTime()
   {
     return this.ModifyTime;
   }
 
   public void setModifyTime(Date modifyTime)
   {
     this.ModifyTime = modifyTime;
   }
 
   public String getBackupNo()
   {
     return this.BackupNo;
   }
 
   public void setBackupNo(String backupNo)
   {
     this.BackupNo = backupNo;
   }
 
   public String getBackupOperator()
   {
     return this.BackupOperator;
   }
 
   public void setBackupOperator(String backupOperator)
   {
     this.BackupOperator = backupOperator;
   }
 
   public Date getBackupTime()
   {
     return this.BackupTime;
   }
 
   public void setBackupTime(Date backupTime)
   {
     this.BackupTime = backupTime;
   }
 
   public String getBackupMemo()
   {
     return this.BackupMemo;
   }
 
   public void setBackupMemo(String backupMemo)
   {
     this.BackupMemo = backupMemo;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCBoardMessageSchema
 * JD-Core Version:    0.5.4
 */