 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZDMemberAddrSet extends SchemaSet
 {
   public BZDMemberAddrSet()
   {
     this(10, 0);
   }
 
   public BZDMemberAddrSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZDMemberAddrSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZDMemberAddr";
     this.Columns = BZDMemberAddrSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZDMemberAddr  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZDMemberAddr  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZDMemberAddrSet();
   }
 
   public boolean add(BZDMemberAddrSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZDMemberAddrSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZDMemberAddrSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZDMemberAddrSchema get(int index) {
     BZDMemberAddrSchema tSchema = (BZDMemberAddrSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZDMemberAddrSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZDMemberAddrSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZDMemberAddrSet
 * JD-Core Version:    0.5.4
 */