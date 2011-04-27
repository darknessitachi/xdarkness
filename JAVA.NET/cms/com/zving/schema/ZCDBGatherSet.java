 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCDBGatherSet extends SchemaSet
 {
   public ZCDBGatherSet()
   {
     this(10, 0);
   }
 
   public ZCDBGatherSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCDBGatherSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCDBGather";
     this.Columns = ZCDBGatherSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCDBGather  where ID=?";
     this.DeleteSQL = "delete from ZCDBGather  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCDBGatherSet();
   }
 
   public boolean add(ZCDBGatherSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCDBGatherSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCDBGatherSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCDBGatherSchema get(int index) {
     ZCDBGatherSchema tSchema = (ZCDBGatherSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCDBGatherSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCDBGatherSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCDBGatherSet
 * JD-Core Version:    0.5.4
 */