 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCInnerDeploySet extends SchemaSet
 {
   public ZCInnerDeploySet()
   {
     this(10, 0);
   }
 
   public ZCInnerDeploySet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCInnerDeploySet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCInnerDeploy";
     this.Columns = ZCInnerDeploySchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCInnerDeploy values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCInnerDeploy set ID=?,SiteID=?,Name=?,DeployType=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCInnerDeploy  where ID=?";
     this.DeleteSQL = "delete from ZCInnerDeploy  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCInnerDeploySet();
   }
 
   public boolean add(ZCInnerDeploySchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCInnerDeploySet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCInnerDeploySchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCInnerDeploySchema get(int index) {
     ZCInnerDeploySchema tSchema = (ZCInnerDeploySchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCInnerDeploySchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCInnerDeploySet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCInnerDeploySet
 * JD-Core Version:    0.5.4
 */