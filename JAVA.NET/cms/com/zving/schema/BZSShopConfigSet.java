 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSShopConfigSet extends SchemaSet
 {
   public BZSShopConfigSet()
   {
     this(10, 0);
   }
 
   public BZSShopConfigSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSShopConfigSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSShopConfig";
     this.Columns = BZSShopConfigSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSShopConfig  where SiteID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSShopConfig  where SiteID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSShopConfigSet();
   }
 
   public boolean add(BZSShopConfigSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSShopConfigSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSShopConfigSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSShopConfigSchema get(int index) {
     BZSShopConfigSchema tSchema = (BZSShopConfigSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSShopConfigSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSShopConfigSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSShopConfigSet
 * JD-Core Version:    0.5.4
 */