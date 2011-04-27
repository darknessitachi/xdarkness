 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCDBGatherSet extends SchemaSet
 {
   public BZCDBGatherSet()
   {
     this(10, 0);
   }
 
   public BZCDBGatherSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCDBGatherSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCDBGather";
     this.Columns = BZCDBGatherSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCDBGather  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCDBGather  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCDBGatherSet();
   }
 
   public boolean add(BZCDBGatherSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCDBGatherSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCDBGatherSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCDBGatherSchema get(int index) {
     BZCDBGatherSchema tSchema = (BZCDBGatherSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCDBGatherSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCDBGatherSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCDBGatherSet
 * JD-Core Version:    0.5.4
 */