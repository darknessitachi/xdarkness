 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCVoteItemSchema extends Schema
 {
   private Long ID;
   private Long SubjectID;
   private Long VoteID;
   private String Item;
   private Long Score;
   private String ItemType;
   private Long VoteDocID;
   private Long OrderFlag;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("SubjectID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("VoteID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("Item", 1, 3, 100, 0, true, false), 
     new SchemaColumn("Score", 7, 4, 0, 0, true, false), 
     new SchemaColumn("ItemType", 1, 5, 1, 0, true, false), 
     new SchemaColumn("VoteDocID", 7, 6, 0, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 7, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 8, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 9, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 10, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 11, 50, 0, false, false) };
   public static final String _TableCode = "BZCVoteItem";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCVoteItem values(?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCVoteItem set ID=?,SubjectID=?,VoteID=?,Item=?,Score=?,ItemType=?,VoteDocID=?,OrderFlag=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCVoteItem  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCVoteItem  where ID=? and BackupNo=?";
 
   public BZCVoteItemSchema()
   {
     this.TableCode = "BZCVoteItem";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCVoteItem values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCVoteItem set ID=?,SubjectID=?,VoteID=?,Item=?,Score=?,ItemType=?,VoteDocID=?,OrderFlag=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCVoteItem  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCVoteItem  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[12];
   }
 
   protected Schema newInstance() {
     return new BZCVoteItemSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCVoteItemSet();
   }
 
   public BZCVoteItemSet query() {
     return query(null, -1, -1);
   }
 
   public BZCVoteItemSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCVoteItemSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCVoteItemSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCVoteItemSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SubjectID = null; else this.SubjectID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.VoteID = null; else this.VoteID = new Long(v.toString()); return; }
     if (i == 3) { this.Item = ((String)v); return; }
     if (i == 4) { if (v == null) this.Score = null; else this.Score = new Long(v.toString()); return; }
     if (i == 5) { this.ItemType = ((String)v); return; }
     if (i == 6) { if (v == null) this.VoteDocID = null; else this.VoteDocID = new Long(v.toString()); return; }
     if (i == 7) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 8) { this.BackupNo = ((String)v); return; }
     if (i == 9) { this.BackupOperator = ((String)v); return; }
     if (i == 10) { this.BackupTime = ((Date)v); return; }
     if (i != 11) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SubjectID;
     if (i == 2) return this.VoteID;
     if (i == 3) return this.Item;
     if (i == 4) return this.Score;
     if (i == 5) return this.ItemType;
     if (i == 6) return this.VoteDocID;
     if (i == 7) return this.OrderFlag;
     if (i == 8) return this.BackupNo;
     if (i == 9) return this.BackupOperator;
     if (i == 10) return this.BackupTime;
     if (i == 11) return this.BackupMemo;
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
 
   public long getSubjectID()
   {
     if (this.SubjectID == null) return 0L;
     return this.SubjectID.longValue();
   }
 
   public void setSubjectID(long subjectID)
   {
     this.SubjectID = new Long(subjectID);
   }
 
   public void setSubjectID(String subjectID)
   {
     if (subjectID == null) {
       this.SubjectID = null;
       return;
     }
     this.SubjectID = new Long(subjectID);
   }
 
   public long getVoteID()
   {
     if (this.VoteID == null) return 0L;
     return this.VoteID.longValue();
   }
 
   public void setVoteID(long voteID)
   {
     this.VoteID = new Long(voteID);
   }
 
   public void setVoteID(String voteID)
   {
     if (voteID == null) {
       this.VoteID = null;
       return;
     }
     this.VoteID = new Long(voteID);
   }
 
   public String getItem()
   {
     return this.Item;
   }
 
   public void setItem(String item)
   {
     this.Item = item;
   }
 
   public long getScore()
   {
     if (this.Score == null) return 0L;
     return this.Score.longValue();
   }
 
   public void setScore(long score)
   {
     this.Score = new Long(score);
   }
 
   public void setScore(String score)
   {
     if (score == null) {
       this.Score = null;
       return;
     }
     this.Score = new Long(score);
   }
 
   public String getItemType()
   {
     return this.ItemType;
   }
 
   public void setItemType(String itemType)
   {
     this.ItemType = itemType;
   }
 
   public long getVoteDocID()
   {
     if (this.VoteDocID == null) return 0L;
     return this.VoteDocID.longValue();
   }
 
   public void setVoteDocID(long voteDocID)
   {
     this.VoteDocID = new Long(voteDocID);
   }
 
   public void setVoteDocID(String voteDocID)
   {
     if (voteDocID == null) {
       this.VoteDocID = null;
       return;
     }
     this.VoteDocID = new Long(voteDocID);
   }
 
   public long getOrderFlag()
   {
     if (this.OrderFlag == null) return 0L;
     return this.OrderFlag.longValue();
   }
 
   public void setOrderFlag(long orderFlag)
   {
     this.OrderFlag = new Long(orderFlag);
   }
 
   public void setOrderFlag(String orderFlag)
   {
     if (orderFlag == null) {
       this.OrderFlag = null;
       return;
     }
     this.OrderFlag = new Long(orderFlag);
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
 * Qualified Name:     com.zving.schema.BZCVoteItemSchema
 * JD-Core Version:    0.5.4
 */