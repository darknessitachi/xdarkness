 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZDMemberAddrSet extends SchemaSet
 {
   public ZDMemberAddrSet()
   {
     this(10, 0);
   }
 
   public ZDMemberAddrSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZDMemberAddrSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZDMemberAddr";
     this.Columns = ZDMemberAddrSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZDMemberAddr  where ID=?";
     this.DeleteSQL = "delete from ZDMemberAddr  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZDMemberAddrSet();
   }
 
   public boolean add(ZDMemberAddrSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZDMemberAddrSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZDMemberAddrSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZDMemberAddrSchema get(int index) {
     ZDMemberAddrSchema tSchema = (ZDMemberAddrSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZDMemberAddrSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZDMemberAddrSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZDMemberAddrSet
 * JD-Core Version:    0.5.4
 */