 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCInnerGatherSet extends SchemaSet
 {
   public ZCInnerGatherSet()
   {
     this(10, 0);
   }
 
   public ZCInnerGatherSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCInnerGatherSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCInnerGather";
     this.Columns = ZCInnerGatherSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCInnerGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCInnerGather set ID=?,SiteID=?,Name=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterInsertStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCInnerGather  where ID=?";
     this.DeleteSQL = "delete from ZCInnerGather  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCInnerGatherSet();
   }
 
   public boolean add(ZCInnerGatherSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCInnerGatherSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCInnerGatherSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCInnerGatherSchema get(int index) {
     ZCInnerGatherSchema tSchema = (ZCInnerGatherSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCInnerGatherSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCInnerGatherSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCInnerGatherSet
 * JD-Core Version:    0.5.4
 */