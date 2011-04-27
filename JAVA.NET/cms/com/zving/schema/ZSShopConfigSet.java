 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSShopConfigSet extends SchemaSet
 {
   public ZSShopConfigSet()
   {
     this(10, 0);
   }
 
   public ZSShopConfigSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSShopConfigSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSShopConfig";
     this.Columns = ZSShopConfigSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=?";
     this.FillAllSQL = "select * from ZSShopConfig  where SiteID=?";
     this.DeleteSQL = "delete from ZSShopConfig  where SiteID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSShopConfigSet();
   }
 
   public boolean add(ZSShopConfigSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSShopConfigSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSShopConfigSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSShopConfigSchema get(int index) {
     ZSShopConfigSchema tSchema = (ZSShopConfigSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSShopConfigSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSShopConfigSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSShopConfigSet
 * JD-Core Version:    0.5.4
 */