 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZDMemberFieldSet extends SchemaSet
 {
   public BZDMemberFieldSet()
   {
     this(10, 0);
   }
 
   public BZDMemberFieldSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZDMemberFieldSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZDMemberField";
     this.Columns = BZDMemberFieldSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZDMemberField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZDMemberField set SiteID=?,Name=?,Code=?,RealField=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and Code=? and BackupNo=?";
     this.FillAllSQL = "select * from BZDMemberField  where SiteID=? and Code=? and BackupNo=?";
     this.DeleteSQL = "delete from BZDMemberField  where SiteID=? and Code=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZDMemberFieldSet();
   }
 
   public boolean add(BZDMemberFieldSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZDMemberFieldSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZDMemberFieldSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZDMemberFieldSchema get(int index) {
     BZDMemberFieldSchema tSchema = (BZDMemberFieldSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZDMemberFieldSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZDMemberFieldSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZDMemberFieldSet
 * JD-Core Version:    0.5.4
 */