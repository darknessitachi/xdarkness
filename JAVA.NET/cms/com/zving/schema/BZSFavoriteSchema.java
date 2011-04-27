 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZSFavoriteSchema extends Schema
 {
   private String UserName;
   private Long GoodsID;
   private Long SiteID;
   private String PriceNoteFlag;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("UserName", 1, 0, 200, 0, true, true), 
     new SchemaColumn("GoodsID", 7, 1, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 2, 0, 0, false, false), 
     new SchemaColumn("PriceNoteFlag", 1, 3, 2, 0, false, false), 
     new SchemaColumn("AddUser", 1, 4, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 5, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 6, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 7, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 8, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 9, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 10, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 11, 50, 0, false, false) };
   public static final String _TableCode = "BZSFavorite";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZSFavorite values(?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and GoodsID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
 
   public BZSFavoriteSchema()
   {
     this.TableCode = "BZSFavorite";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZSFavorite values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and GoodsID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
     this.HasSetFlag = new boolean[12];
   }
 
   protected Schema newInstance() {
     return new BZSFavoriteSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZSFavoriteSet();
   }
 
   public BZSFavoriteSet query() {
     return query(null, -1, -1);
   }
 
   public BZSFavoriteSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZSFavoriteSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZSFavoriteSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZSFavoriteSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { this.UserName = ((String)v); return; }
     if (i == 1) { if (v == null) this.GoodsID = null; else this.GoodsID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 3) { this.PriceNoteFlag = ((String)v); return; }
     if (i == 4) { this.AddUser = ((String)v); return; }
     if (i == 5) { this.AddTime = ((Date)v); return; }
     if (i == 6) { this.ModifyUser = ((String)v); return; }
     if (i == 7) { this.ModifyTime = ((Date)v); return; }
     if (i == 8) { this.BackupNo = ((String)v); return; }
     if (i == 9) { this.BackupOperator = ((String)v); return; }
     if (i == 10) { this.BackupTime = ((Date)v); return; }
     if (i != 11) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.UserName;
     if (i == 1) return this.GoodsID;
     if (i == 2) return this.SiteID;
     if (i == 3) return this.PriceNoteFlag;
     if (i == 4) return this.AddUser;
     if (i == 5) return this.AddTime;
     if (i == 6) return this.ModifyUser;
     if (i == 7) return this.ModifyTime;
     if (i == 8) return this.BackupNo;
     if (i == 9) return this.BackupOperator;
     if (i == 10) return this.BackupTime;
     if (i == 11) return this.BackupMemo;
     return null;
   }
 
   public String getUserName()
   {
     return this.UserName;
   }
 
   public void setUserName(String userName)
   {
     this.UserName = userName;
   }
 
   public long getGoodsID()
   {
     if (this.GoodsID == null) return 0L;
     return this.GoodsID.longValue();
   }
 
   public void setGoodsID(long goodsID)
   {
     this.GoodsID = new Long(goodsID);
   }
 
   public void setGoodsID(String goodsID)
   {
     if (goodsID == null) {
       this.GoodsID = null;
       return;
     }
     this.GoodsID = new Long(goodsID);
   }
 
   public long getSiteID()
   {
     if (this.SiteID == null) return 0L;
     return this.SiteID.longValue();
   }
 
   public void setSiteID(long siteID)
   {
     this.SiteID = new Long(siteID);
   }
 
   public void setSiteID(String siteID)
   {
     if (siteID == null) {
       this.SiteID = null;
       return;
     }
     this.SiteID = new Long(siteID);
   }
 
   public String getPriceNoteFlag()
   {
     return this.PriceNoteFlag;
   }
 
   public void setPriceNoteFlag(String priceNoteFlag)
   {
     this.PriceNoteFlag = priceNoteFlag;
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
 * Qualified Name:     com.zving.schema.BZSFavoriteSchema
 * JD-Core Version:    0.5.4
 */