 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCInnerDeploySet extends SchemaSet
 {
   public BZCInnerDeploySet()
   {
     this(10, 0);
   }
 
   public BZCInnerDeploySet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCInnerDeploySet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCInnerDeploy";
     this.Columns = BZCInnerDeploySchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCInnerDeploy values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCInnerDeploy set ID=?,SiteID=?,Name=?,DeployType=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCInnerDeploy  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCInnerDeploy  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCInnerDeploySet();
   }
 
   public boolean add(BZCInnerDeploySchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCInnerDeploySet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCInnerDeploySchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCInnerDeploySchema get(int index) {
     BZCInnerDeploySchema tSchema = (BZCInnerDeploySchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCInnerDeploySchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCInnerDeploySet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCInnerDeploySet
 * JD-Core Version:    0.5.4
 */