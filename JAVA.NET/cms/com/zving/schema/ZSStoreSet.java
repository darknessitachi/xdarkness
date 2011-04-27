 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSStoreSet extends SchemaSet
 {
   public ZSStoreSet()
   {
     this(10, 0);
   }
 
   public ZSStoreSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSStoreSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSStore";
     this.Columns = ZSStoreSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where StoreCode=?";
     this.FillAllSQL = "select * from ZSStore  where StoreCode=?";
     this.DeleteSQL = "delete from ZSStore  where StoreCode=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSStoreSet();
   }
 
   public boolean add(ZSStoreSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSStoreSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSStoreSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSStoreSchema get(int index) {
     ZSStoreSchema tSchema = (ZSStoreSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSStoreSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSStoreSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSStoreSet
 * JD-Core Version:    0.5.4
 */