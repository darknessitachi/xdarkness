 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSStoreSet extends SchemaSet
 {
   public BZSStoreSet()
   {
     this(10, 0);
   }
 
   public BZSStoreSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSStoreSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSStore";
     this.Columns = BZSStoreSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where StoreCode=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSStore  where StoreCode=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSStore  where StoreCode=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSStoreSet();
   }
 
   public boolean add(BZSStoreSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSStoreSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSStoreSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSStoreSchema get(int index) {
     BZSStoreSchema tSchema = (BZSStoreSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSStoreSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSStoreSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSStoreSet
 * JD-Core Version:    0.5.4
 */