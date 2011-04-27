 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCInnerGatherSet extends SchemaSet
 {
   public BZCInnerGatherSet()
   {
     this(10, 0);
   }
 
   public BZCInnerGatherSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCInnerGatherSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCInnerGather";
     this.Columns = BZCInnerGatherSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCInnerGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCInnerGather set ID=?,SiteID=?,Name=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterInsertStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCInnerGather  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCInnerGather  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCInnerGatherSet();
   }
 
   public boolean add(BZCInnerGatherSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCInnerGatherSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCInnerGatherSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCInnerGatherSchema get(int index) {
     BZCInnerGatherSchema tSchema = (BZCInnerGatherSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCInnerGatherSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCInnerGatherSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCInnerGatherSet
 * JD-Core Version:    0.5.4
 */